/**
 *
 */
package com.sfsctech.support.common.util;

import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class StringEscapeUtil
 *
 * @author 张麒 2016年3月28日
 * @version Description：
 */
public class HtmlEscapeUtil extends StringEscapeUtils {

    /**
     * Html字符串编码，以适应页面显示
     *
     * @param html html
     * @return String
     */
    public static String htmlEncode(String html) {
        if (StringUtil.isNotBlank(html))
            return html.replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("'", "‘").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
                    .replaceAll(" ", "&nbsp;").replaceAll("\\n", "<br>");
        else
            return "";
    }

    /**
     * html字符解码
     *
     * @param html html
     * @return String
     */
    public static String htmlDecode(String html) {
        if (StringUtil.isNotBlank(html))
            return html.replaceAll("&amp;", "&").replaceAll("&quot;", "\"").replaceAll("‘", "'").replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                    .replaceAll("&nbsp;", " ").replaceAll("<br>", "\\n");
        else
            return "";
    }

    /**
     * 清除html代码
     *
     * @param html Html
     * @return "取消"
     */
    public static String htmlClear(String html) {
        if (StringUtil.isBlank(html)) {
            return "";
        }
        String regEx_script = "<script[^>]*?>[\\s\\S]*?</script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?</style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(html);
        html = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(html);
        html = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(html);
        html = m_html.replaceAll(""); // 过滤html标签

        return html;
    }

    public static String xssEncode(String param) {
        if (StringUtil.isBlank(param)) {
            return param;
        } else {
            param = stripXSSAndSql(param);
        }
        StringBuilder sb = new StringBuilder(param.length() + 16);
        for (int i = 0; i < param.length(); i++) {
            char c = param.charAt(i);
            switch (c) {
                case '\'':
                    sb.append("＇");// 转义单引号
                    break;
                case '#':
                    sb.append("＃");// 转义#
                    break;
                case '(':
                    sb.append("（");// 转义大于号
                    break;
                case ')':
                    sb.append("）");// 转义大于号
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return escapeHtml4(sb.toString());
    }

    private static String stripXSSAndSql(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and
            // uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
            /** value = value.replaceAll("", ""); ***/
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile(
                    "<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid anything in a
            // src="http://www.yihaomen.com/article/java/..." type of
            // e-xpression
            scriptPattern = Pattern.compile("src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<[\r\n| | ]*script(.*?)>",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid e-xpression(...) expressions
            scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return value;
    }


    public static boolean checkXSSAndSql(String value) {
        boolean flag = false;
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and
            // uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
            /** value = value.replaceAll("", ""); ***/
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile(
                    "<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
            // Avoid anything in a
            // src="http://www.yihaomen.com/article/java/..." type of
            // e-xpression
            scriptPattern = Pattern.compile("src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<[\r\n| | ]*script(.*?)>",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
            // Avoid e-xpression(...) expressions
            scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            flag = scriptPattern.matcher(value).find();
            if (flag) {
                return flag;
            }
        }
        return flag;
    }
}
