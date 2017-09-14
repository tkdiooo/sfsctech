/**
 *
 */
package com.sfsctech.common.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isBlank(html)) {
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
}
