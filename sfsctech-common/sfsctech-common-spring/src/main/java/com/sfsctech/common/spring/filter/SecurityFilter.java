package com.sfsctech.common.spring.filter;

import com.sfsctech.common.constants.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class SecurityFilter
 *
 * @author 张麒 2017/3/24.
 * @version Description:
 */
public class SecurityFilter implements Filter {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = transformURI(request);
        // 不过滤静态资源
        if (!containsSuffix(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        logger.info("request url " + uri);
        logger.info(request.getHeader("Referer"));
        // 用户未登录
//        if (request.getSession().getAttribute(CommonConstants.LOGIN_SESSION_KEY) == null) {
//            logger.info("user not login");
//            // 请求路径完全匹配 || 登录路径匹配
//            if (CommonConstants.GreenUrlSet.contains(uri) || containsKey(uri)) {
//                logger.info("don't check  url , " + request.getRequestURI());
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//            Cookie[] cookies = request.getCookies();
//            logger.info("Cookie check");
//            if (cookies != null) {
//                logger.info("Cookie length " + cookies.length);
//                boolean flag = true;
//                for (Cookie cookie : cookies) {
//                    if (cookie.getName().equals(CommonConstants.LOGIN_SESSION_KEY)) {
//                        if (StringUtils.isNotBlank(cookie.getValue())) {
//                            flag = false;
//                        } else {
//                            break;
//                        }
//                        String value = getUserId(cookie.getValue());
//                        Integer userId = 0;
//                        if (userRepository == null) {
//                            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//                            userRepository = (UserRepository) factory.getBean("userRepository");
//                        }
//                        if (StringUtils.isNotBlank(value)) {
//                            userId = Integer.valueOf(value);
//                        }
//                        User user = userRepository.findOne(userId);
//                        String html;
//                        if (null == user) {
//                            html = "<script type=\"text/javascript\">window.location.href=\"_BP_login\"</script>";
//                        } else {
//                            logger.info("userId :" + user.getUserSno());
//                            request.getSession().setAttribute(CommonConstants.LOGIN_SESSION_KEY, user);
//                            String referer = this.getRef(request);
//                            if (referer.contains("/collect?")) {
//                                filterChain.doFilter(servletRequest, servletResponse);
//                                return;
//                            } else {
//                                html = "<script type=\"text/javascript\">window.location.href=\"_BP_\"</script>";
//                            }
//                        }
//                        html = html.replace("_BP_", CommonConstants.BASE_PATH + "/home");
//                        servletResponse.getWriter().write(html);
//                    }
//                }
//                if (flag) {
//                    this.toLogin(request, servletResponse);
//                }
//            } else {
//                this.toLogin(request, servletResponse);
//            }
//        } else {
        filterChain.doFilter(servletRequest, servletResponse);
//        }
    }

    @Override
    public void destroy() {

    }

    private String transformURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.endsWith("/") ? uri.substring(0, uri.length() - 1) : uri;
    }


    /**
     * @param url
     * @return
     */
    private boolean containsSuffix(String url) {
        Pattern pattern = Pattern.compile(".*?\\/(?:.*?\\.(?!js$|css$|jpg$|gif$|png$|html$|eot$|svg$|ttf$|woff$|ico$|woff2$)|[^\\.]*$)");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }


//    /**
//     * @param url
//     * @return
//     */
//    private boolean containsKey(String url) {
//        return url.endsWith(CommonConstants.CONTEXT_PATH)
//                || url.contains(CommonConstants.CONTEXT_PATH + CommonConstants.INDEX_PATH)
//                || url.contains(CommonConstants.CONTEXT_PATH + CommonConstants.LOING_PATH)
//                || url.contains(CommonConstants.CONTEXT_PATH + CommonConstants.WEICHAT_PATH);
//    }

//    private String getUserId(String value) {
//        try {
//            String userId = Des3EncryptionUtil.decode(CommonConstants.DES3_KEY, value);
//            if (StringUtils.isNotBlank(userId)) {
//                userId = userId.substring(0, userId.indexOf(CommonConstants.PASSWORD_KEY));
//                return userId;
//            }
//        } catch (Exception e) {
//            logger.error("解析cookie异常：", e);
//        }
//        return null;
//    }

//    private String getRef(HttpServletRequest request) {
//        String referer = "";
//        String param = this.codeToString(request.getQueryString());
//        if (StringUtils.isNotBlank(request.getContextPath())) {
//            referer = referer + request.getContextPath();
//        }
//        if (StringUtils.isNotBlank(request.getServletPath())) {
//            referer = referer + request.getServletPath();
//        }
//        if (StringUtils.isNotBlank(param)) {
//            referer = referer + "?" + param;
//        }
//        request.getSession().setAttribute(CommonConstants.LAST_REFERER, referer);
//        return referer;
//    }

    private String codeToString(String str) {
        String strString = str;
        try {
            byte tempB[] = strString.getBytes("ISO-8859-1");
            strString = new String(tempB);
            return strString;
        } catch (Exception e) {
            return strString;
        }
    }

//    private void toLogin(HttpServletRequest request, ServletResponse servletResponse) throws IOException {
//        //跳转到登陆页面
//        String referer = this.getRef(request);
//        logger.debug("security filter, deney, " + request.getRequestURI());
//        String html;
//        if (!referer.contains("/collect?")) {
//            html = "<script type=\"text/javascript\">window.location.href=\"_BP_/index\"</script>";
//        } else {
//            html = "<script type=\"text/javascript\">window.location.href=\"_BP_/login\"</script>";
//        }
//        html = html.replace("_BP_", CommonConstants.BASE_PATH);
//        servletResponse.getWriter().write(html);
//    }
}
