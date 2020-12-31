package com.sfsctech.support.common.http.synch;

import com.sfsctech.support.common.util.AssertUtil;
import com.sfsctech.support.common.http.ResponseContent;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class HttpHelper
 *
 * @author 张麒 2016/4/8.
 * @version Description:
 */
public class HttpHelper {

    private static Integer socketTimeout = 3000;
    private static Integer connectTimeout = 6000;
    private static Integer connectionRequestTimeout = 3000;


    private static void setParams(String url, HttpClientWrapper hw) {
        AssertUtil.isNotBlank(url, "请求url为空");
        String[] paramStr = url.split("[?]", 2);
        if (paramStr.length != 2) {
            return;
        }
        String[] paramArray = paramStr[1].split("[&]");
        for (String param : paramArray) {
            if (param == null || "".equals(param.trim())) {
                continue;
            }
            String[] keyValue = param.split("[=]", 2);
            if (keyValue.length != 2) {
                continue;
            }
            hw.addNV(keyValue[0], keyValue[1]);
        }
    }

    private static ResponseContent postRequestBody(String url, String body, String contentType) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent ret = null;
        try {
            ret = hw.postRequestBody(url, "UTF-8", contentType, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     *
     * @param url 完整的URL地址
     * @return ResponseContent 如果发生异常则返回null，否则返回ResponseContent对象
     */
    public static ResponseContent getUrlRespContent(String url) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent response = null;
        try {
            response = hw.getResponse(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     *
     * @param url         完整的URL地址
     * @param urlEncoding 编码，可以为null
     * @return ResponseContent 如果发生异常则返回null，否则返回ResponseContent对象
     */
    public static ResponseContent getUrlRespContent(String url, String urlEncoding) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent response = null;
        try {
            response = hw.getResponse(url, urlEncoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 将参数拼装在url中，进行post请求。http:www.demo.com?a=1&amp;b=2
     *
     * @param url URL地址
     * @return ResponseContent
     */
    public static ResponseContent postUrl(String url) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent ret = null;
        try {
            setParams(url, hw);
            ret = hw.postNV(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 使用post方式，发布对象转成的json给Rest服务。
     *
     * @param url      URL地址
     * @param jsonBody JsonBody
     * @return ResponseContent
     */
    public static ResponseContent postJsonEntity(String url, String jsonBody) {
        return postRequestBody(url, jsonBody, ContentType.APPLICATION_JSON.getMimeType());
    }

    /**
     * 使用post方式，发布对象转成的xml给Rest服务
     *
     * @param url     URL地址
     * @param xmlBody xml文本字符串
     * @return ResponseContent
     */
    public static ResponseContent postXmlEntity(String url, String xmlBody) {
        return postRequestBody(url, xmlBody, ContentType.APPLICATION_XML.getMimeType());
    }

    /**
     * @param url        请求URL
     * @param paramName  参数名称
     * @param paramValue 参数值
     * @return ResponseContent
     */
    public static ResponseContent postDefEntity(String url, String paramName, String paramValue) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent ret = null;
        try {
            hw.addNV(paramName, paramValue);
            ret = hw.postNV(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @param url         请求URL
     * @param body        Body
     * @param contentType ContentType
     * @return ResponseContent
     */
    public static ResponseContent postEntity(String url, String body, String contentType) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent ret = null;
        try {
            hw.addNV("body", body);
            ret = hw.postNV(url, contentType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 使用post方式，请求Rest服务
     *
     * @param url       请求URL
     * @param paramsMap 参数和值
     * @return ResponseContent
     */
    public static ResponseContent postEntity(String url, Map<String, String> paramsMap) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent ret = null;
        try {
            setParams(url, hw);
            for (String key : paramsMap.keySet()) {
                String value = paramsMap.get(key);
                if (value != null && !"".equals(value)) hw.addNV(key, value);
                else hw.addNV(key, "");
            }
            ret = hw.postNV(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 上传文件（包括图片）
     *
     * @param url       请求URL
     * @param paramsMap 参数和值
     * @return ResponseContent
     */
    public static ResponseContent uploadFiles(String url, Map<String, Object> paramsMap) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent ret = null;
        try {
            setParams(url, hw);
            for (String key : paramsMap.keySet()) {
                Object value = paramsMap.get(key);
                if (value instanceof File) {
                    FileBody fileBody = new FileBody((File) value);
                    hw.getContentBodies().add(fileBody);
                } else if (value instanceof byte[]) {
                    byte[] byteValue = (byte[]) value;
                    ByteArrayBody byteArrayBody = new ByteArrayBody(byteValue, key);
                    hw.getContentBodies().add(byteArrayBody);
                } else if (value instanceof InputStream) {
                    InputStream is = (InputStream) value;
                    InputStreamBody isb = new InputStreamBody(is, key);
                    hw.getContentBodies().add(isb);
                } else {
                    if (value != null && !"".equals(value)) {
                        hw.addNV(key, String.valueOf(value));
                    } else {
                        hw.addNV(key, "");
                    }
                }
            }
            ret = hw.postEntity(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&amp;name2=value2 的形式。
     * @return result 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
//            long startTime = System.currentTimeMillis();
//            System.out.println("@@ startTime is " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(startTime)));
            String urlNameString = url + "?" + param;
//            System.out.println("## url is " + urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
//            long endTime = System.currentTimeMillis();
//            System.out.println("## response is " + result);
//            System.out.println("@@ endTime is " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(endTime)));
//            System.out.println("@@ total-time:" + (endTime - startTime));
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&amp;name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            /*
             * conn.setRequestProperty("deviceId", "1220524");
			 * conn.setRequestProperty("appCode", "10000");
			 */
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {

//        String PARAMS_GENKEY = "genKey";

//        String PARAMS_SUBKEY = "subKey";
        Map<String, String> params = new HashMap<>();

//        params.put(PARAMS_GENKEY, "hcm");
//        params.put(PARAMS_SUBKEY, "TmRulesHoliday");
        String SequenceUrl = "http://localhost:8081/data/msg/test";// 序列ID
        ResponseContent ret = HttpHelper.postUrl(SequenceUrl);
        try {
            System.out.println(ret.getContent());
//            System.out.println(HttpHelper.sendGet(SequenceUrl, "genKey=hcm&subKey=TmRulesHoliday"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}