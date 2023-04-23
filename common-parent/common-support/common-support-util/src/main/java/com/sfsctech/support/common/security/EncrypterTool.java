package com.sfsctech.support.common.security;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.support.common.security.aes.Aes;
import com.sfsctech.support.common.security.base64.Base64;
import com.sfsctech.support.common.security.des3.Des3;
import com.sfsctech.support.common.security.des3.Des3Manager;
import com.sfsctech.support.common.security.md5.Md5;
import com.sfsctech.support.common.util.Cn2SpellUtil;
import com.sfsctech.support.common.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * Class EncrypterTool
 *
 * @author 张麒 2016/4/15.
 * @version Description:
 */
public class EncrypterTool {

    private static final Logger logger = LoggerFactory.getLogger(EncrypterTool.class);

    public enum Security {
        Base64, Des3, Des3ECBBase64, Des3ECBHex, Des3CBCBase64, Des3CBCHex, Md5, Aes, AesCBC
    }

    /**
     * 一般加密
     */
    public static String encrypt(Security security, String info) {
        if (security.equals(Security.Base64)) {
            return Base64.encrypt(info.getBytes());
        } else if (security.equals(Security.Des3)) {
            return Des3.encrypt(info);
        } else if (security.equals(Security.Des3ECBBase64)) {
            return Des3.encryptECBToBase64(info);
        } else if (security.equals(Security.Des3ECBHex)) {
            return Des3.encryptECBToHex(info);
        } else if (security.equals(Security.Des3CBCBase64)) {
            return Des3.encryptCBCToBase64(info);
        } else if (security.equals(Security.Des3CBCHex)) {
            return Des3.encryptCBCToHex(info);
        } else if (security.equals(Security.Md5)) {
            return Md5.encode(info);
        } else if (security.equals(Security.Aes)) {
            return Aes.encrypt(info);
        } else if (security.equals(Security.AesCBC)) {
            return Aes.encryptCBC(info);
        }
        return "";
    }

    /**
     * 一般解密
     */
    public static String decrypt(Security security, String info) {
        if (security.equals(Security.Base64)) {
            return new String(Base64.decrypt(info));
        } else if (security.equals(Security.Des3)) {
            return Des3.decrypt(info);
        } else if (security.equals(Security.Des3ECBBase64)) {
            return Des3.decryptECBByBase64(info);
        } else if (security.equals(Security.Des3ECBHex)) {
            return Des3.decryptECBByHex(info);
        } else if (security.equals(Security.Des3CBCBase64)) {
            return Des3.decryptCBCByBase64(info);
        } else if (security.equals(Security.Des3CBCHex)) {
            return Des3.decryptCBCByHex(info);
        } else if (security.equals(Security.Md5)) {
            return info;
        } else if (security.equals(Security.Aes)) {
            return Aes.decrypt(info);
        } else if (security.equals(Security.AesCBC)) {
            return Aes.decryptCBC(info);
        }
        return "";
    }

    /**
     * 解密字符串
     *
     * @param data 需解密的字符串
     * @param key  24位长度密钥
     * @return 解密后的字符串, 异常返回空
     */
    public static String decrypt(String data, String key) {
        try {
            logger.debug("待解密的字符串:............." + data);
            logger.debug("待解密的字符串密钥:..............." + key);
            return Des3Manager.getInstance().decrypt(data, key);
        } catch (Exception ex) {
            logger.error("3DES解密出错:" + ex.getMessage() + " key:" + key, ex);
        }
        return null;
    }

    /**
     * 加密字符串
     *
     * @param data 待加密的字符串
     * @param key  24位长度密钥
     * @return 3DES加密后的字符串
     */
    public static String encrypt(String data, String key) {
        try {
            logger.debug("待加密的字符串:............." + data);
            logger.debug("待加密的字符串密钥:..............." + key);
            return Des3Manager.getInstance().encrypt(data, key);
        } catch (Exception ex) {
            logger.error("3DES加密出错:" + ex.getMessage() + " key:" + key, ex);
            return null;
        }
    }

    public static int reverse(int x) {
        long result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            x = x / 10;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(EncrypterTool.reverse(123456789));
        String data = EncrypterTool.encrypt(Security.Des3ECBHex, "transwarp");
        System.out.println(data);
//        new Thread(() -> {
//            while (true) {
//                System.out.println(1);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            while (true) {
//                System.out.println(2);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            while (true) {
//                System.out.println(3);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            while (true) {
//                System.out.println(4);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            while (true) {
//                System.out.println(5);
//                String value = EncrypterTool.decrypt(Security.Des3ECBHex, data);
//                if (!value.equals("待加密的字符串@#@#SSS23s2433!*(")) {
//                    System.out.println("error:" + value);
//                }
//            }
//        }).start();
//        try {
////            String key = URLEncoder.encode(encrypt(Security.AesCBC, "0000792773"), "UTF-8");
////            System.out.println(key);
////            System.out.println(decrypt(Security.AesCBC, URLDecoder.decode(key, "UTF-8")));
////            String key1 = URLEncoder.encode(encrypt(Security.AesCBC, "index"), "UTF-8");
////            System.out.println(key1);
//            System.out.println(URLEncoder.encode(encrypt(Security.AesCBC, "http://m.fsgplus.com/home?v=1530436188891"), "UTF-8"));
//            System.out.println(decrypt(Security.AesCBC,"6xvDUkUPdlVnwQIuK33bfUDNunlluRuUKLAwSW9wV0mjVo7FQ/nEcdIBTBvJA+aH"));
//            System.out.println(URLDecoder.decode("81B6FDA9FB15F0CAE4BB52B5DEDDAAAEAD910C5980A7C16EF07656647048966B1D0159A5556ED080A436898EDE53B00E", "UTF-8"));
////            System.out.println(decrypt(Security.AesCBC, ));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(encrypt(Security.Aes, "temporary").substring(0, 16));
//        String[] params = {"外滩业务中心", "南京路业务中心", "徐家汇业务中心", "虹桥业务中心", "人民广场业务中心", "新天地业务中心", "陆家嘴业务中心"
//                , "日本事业部", "长三角区域公司", "薪酬福利事业部", "招聘及灵活用工事业部", "业务外包事业部", "国企业务部"
//                , "外服跑团", "工会跑团"};
//        StringBuilder sb = new StringBuilder();
//        for (String param : params) {
//            String name = Cn2SpellUtil.converterToSpell(param);
//            sb.append(name + ",");
//            System.out.println(name);
////            System.out.println(encrypt(Security.Aes, name).substring(0, 16));
//        }
//        System.out.println(sb);
//        System.out.println(URLEncoder.encode("06646650B61CF129F01DCE637D319B27CE35BF37B9F66A5802B4DD3EF6A4BE5DAEF728032473784AC0E8658C92D4804B","UTF-8"));;
//        System.out.println(UUIDUtil.base58Uuid());
        String msg ="{\n" +
                "    \"alert_state\": \"alerting\", \n" +
                "    \"client\": \"Grafana\", \n" +
                "    \"client_url\": \"http://localhost:3000/alerting/list\", \n" +
                "    \"description\": \"[FIRING:1, RESOLVED:1]  (asdf)\", \n" +
                "    \"details\": \"**Firing**\n" +
                "\n" +
                "Value: [ var='B0' metric='Value' labels={__name__=kube_pod_container_status_running, app=prometheus, chart=prometheus-9.1.0, component=kube-state-metrics, container=tomcat, heritage=Tiller, instance=10.42.2.84:8080, io_cattle_field_appId=prometheus, job=kubernetes-service-endpoints, kubernetes_name=prometheus-kube-state-metrics, kubernetes_namespace=monitor, kubernetes_node=pdkjrancher04, namespace=airflow, pod=tomcat-7b95b687bf-qxbts, release=prometheus} value=0 ]\n" +
                "Labels:\n" +
                " - alertname = pod_alert\n" +
                " - key_a = asdf\n" +
                " - key_b = rancher\n" +
                "Annotations:\n" +
                " - description = K8S pod {kube_pod_container_status_running} {Metric}is down\n" +
                " - summary = K8S pod {labels.container} alert\n" +
                "Source: http://localhost:3000/alerting/grafana/Z7gIdrRVk/view\n" +
                "Silence: http://localhost:3000/alerting/silence/new?alertmanager=grafana&matcher=alertname%3Dpod_alert&matcher=key_a%3Dasdf&matcher=key_b%3Drancher\n" +
                "\n" +
                "\n" +
                "**Resolved**\n" +
                "\n" +
                "Value: [no value]\n" +
                "Labels:\n" +
                " - alertname = Panel Title\n" +
                " - key_a = asdf\n" +
                "Annotations:\n" +
                " - SendTarget = {\\\"eamil\\\":\\\"group1\\\",\\\"wechat\\\":\\\"group1\\\"}\n" +
                " - Source = Prometheus\n" +
                " - WarningContent = live网站心跳监控异常，服务无响应\n" +
                " - WarningId = Panel-Title-1\n" +
                " - WarningLevel = 警告\n" +
                "Source: http://localhost:3000/alerting/grafana/OxOf5tg4k/view\n" +
                "Silence: http://localhost:3000/alerting/silence/new?alertmanager=grafana&matcher=alertname%3DPanel+Title&matcher=key_a%3Dasdf\n" +
                "Dashboard: http://localhost:3000/d/_MeudpR4k\n" +
                "Panel: http://localhost:3000/d/_MeudpR4k?viewPanel=2\n" +
                "\", \n" +
                "    \"incident_key\": \"a1421bd7b320c416792bf8884f1dff336098260e4fa9c0b3a869217fa05e98f7\"\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(msg);
        InputStream in = new ByteArrayInputStream(jsonObject.getString("").getBytes(StandardCharsets.UTF_8));
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
