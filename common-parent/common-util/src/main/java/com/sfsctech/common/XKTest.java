package com.sfsctech.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sfsctech.common.http.ResponseContent;
import com.sfsctech.common.http.synch.HttpHelper;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class XKTest
 *
 * @author 张麒 2018-4-24.
 * @version Description:
 */
public class XKTest {

    private static String client_id = "hmboss_shhwf";
    private static String client_secret = "HMZ9T@fi@5MT@OsB(0)";
    private static String store_id = "000001";
    private static String project_id = "thirdparty011901";
    private static String packageCode = "HE-TC-000001-63010";
    private static String version = "0.1";
    private static String reportType = "0";
    private static String proofNum = "210723195006090225";

    public String tokenCode() throws UnsupportedEncodingException {
//        HttpClientUtil httpClientUtil = new HttpClientUtil();
//        HttpHelper.postJsonEntity()
        String url = "http://dlpassport.xikang.com/oauth/token";
        Map<String, String> param = new HashMap<>();
        param.put("client_id", client_id);
        param.put("client_secret", client_secret);
        param.put("grant_type", "client_credentials");
        param.put("scope", "trust");
//        String post = httpClientUtil.sendJsonDataByPost(url, param);
        ResponseContent rc = HttpHelper.postEntity(url, param);
//        JSONObject returnJson = JSONObject.fromObject(post);
//        String access_token = returnJson.getString("access_token");
//        String token_type = returnJson.getString("token_type");
//        String expires_in = returnJson.getString("expires_in");
//        String scope = returnJson.getString("scope");
//        return access_token;
        return JSON.parseObject(rc.getUTFContent(), Map.class).get("access_token").toString();
    }

    public String reportType() throws Exception {
//        HttpClientUtil httpClientUtil = new HttpClientUtil();
        XKTest test = new XKTest();
        String token = test.tokenCode();
        String url = "http://xk.xikang.com/hcservice/open/thirdparty/findReportByProofNum/000001/thirdparty011901/210723195006090225/2?version=0.1";
        long time = System.currentTimeMillis();
        String secret = client_secret;

        //这里是对参数进行加加密，需要按参数首字母顺序进行，如 ：a,b,c... clientId
        String text = "access_token=" + token + "time=" + time + "version=0.1" + secret;
        String sign = DigestUtils.md5Hex(text.getBytes("utf-8"));
        url = url + "&access_token=" + token + "&time=" + time + "&sign=" + sign;

        System.out.println(url);
        ResponseContent rc = HttpHelper.getUrlRespContent(url);
        Map result = JSON.parseObject(new String(rc.getContentBytes()), Map.class);
        List report = JSON.parseObject(result.get("report").toString(), List.class);
        String data = ((JSONObject) report.get(0)).get("reportData").toString();

        System.out.println(data);
        getFile(data.getBytes(), "D:", "a.pdf");
        data = new String(data.getBytes(StandardCharsets.UTF_16));
        System.out.println(data);
        getFile(data.getBytes(), "D:", "b.pdf");

//        String post = httpClientUtil.sendJsonDataByGetToXK(url);
//        JSONObject returnJson = JSONObject.fromObject(post);
//        JSONArray jsonList = returnJson.getJSONArray("report");
//        JSONObject j = jsonList.getJSONObject(0);
//        String reportData = JSONObject.fromObject(j).getString("reportData");
//        String s = new String(reportData.getBytes("ISO-8859-1"));
//        //System.out.println(s);
//        //System.out.println(getEncoding(reportData));
        return "";
    }

    /**
     * 获得指定文件的byte数组
     */
    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
//            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
            dir.mkdirs();
//            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {

        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {

        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {

        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {

        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        XKTest test = new XKTest();
//		String token = test.tokenCode();
        System.out.println(test.reportType());
    }
}
