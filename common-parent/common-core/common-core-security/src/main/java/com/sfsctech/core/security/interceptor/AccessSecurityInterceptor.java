package com.sfsctech.core.security.interceptor;

import com.alibaba.fastjson.JSON;
import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.exception.enums.VerifyExceptionTipsEnum;
import com.sfsctech.core.security.csrf.CSRFTokenManager;
import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.core.security.tools.SecurityUtil;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.ResponseUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Class SecurityInterceptor
 *
 * @author 张麒 2017/7/19.
 * @version Description:
 */
public class AccessSecurityInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(AccessSecurityInterceptor.class);

    private SecurityProperties.CSRF csrf;

    private Set<String> verifyExcludePath;

    private Set<String> requiredVerifyPath;

    public AccessSecurityInterceptor(SecurityProperties.CSRF csrf) {
        if (null != csrf) {
            this.csrf = csrf;
            this.verifyExcludePath = csrf.getVerifyExcludePath();
            this.requiredVerifyPath = csrf.getRequiredVerifyPath();
        }
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        final String requestURI = request.getRequestURI();
        final String domain = HttpUtil.getDomain(request);
        final String ret_url = request.getHeader("Referer");
        final String method = request.getMethod();
        // 请求方法验证
        if (!"GET".equals(method) && !"POST".equals(method) && !"HEAD".equals(method) && !DispatcherType.ERROR.equals(request.getDispatcherType())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Request only permit GET POST or HEAD");
            return false;
        }
        // 判断路径是否无需校验
        boolean verify = FilterHandler.isExclusion(requestURI, verifyExcludePath);
        logger.info("requestURI:" + requestURI + ",isExclusion:" + verify);
        logger.info("domain:" + domain);
        logger.info("Referer:" + ret_url);
        logger.info(String.valueOf((StringUtil.isNotBlank(ret_url) && !ret_url.startsWith(domain))));
        // 访问劫持验证：路径无需校验，并且上次请求不是当前服务域名
        if (!verify && (StringUtil.isNotBlank(ret_url) && !ret_url.startsWith(domain))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden illegal request");
            return false;
        }
        if (null != csrf && csrf.isEnabled()) {
            boolean required = FilterHandler.isExclusion(requestURI, requiredVerifyPath);
            logger.info("exclusion：[" + verify + "] request uri：[" + requestURI + "] ");
            // 当前请求路径是否需要验证 && Csrf防御验证
            if ((!verify || required) && CSRFTokenManager.isValidCSRFToken(request, response)) {
                logger.error(VerifyExceptionTipsEnum.CsrfWrong.toString());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden illegal request");
                return false;
            }
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle：[" + request.getRequestURI() + "] ");
        if (null != csrf && csrf.isEnabled()) {
            Method method = null;
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                method = handlerMethod.getMethod();
            }
            // 如果是Ajax请求，返回类型是String，csrf参数设置至Header
            if (HttpUtil.isAjaxRequest(request) && null != method && method.getReturnType() == String.class) {
                response.setHeader(CSRFTokenManager.CSRF_TOKEN, JSON.toJSONString(CSRFTokenManager.generateCSRFToken(request, response)));
            }
            // 如果不是Ajax请求，返回类型是String，csrf参数设置至ModelAndView
            else if (!HttpUtil.isAjaxRequest(request) && null != modelAndView && null != modelAndView.getModel()) {
                // 加密敏感参数
                Map<String, Object> model = modelAndView.getModel();
                for (String key : model.keySet()) {
                    Object obj = model.get(key);
                    if (BaseDto.class.equals(obj.getClass().getSuperclass())) {
                        SecurityUtil.Encrypt(obj);
                    }
                }
                // 设置Csrf Token
                model.put(CSRFTokenManager.CSRF_TOKEN, CSRFTokenManager.generateCSRFToken(request, response));
            }
        }
        // 设置无缓存，防止浏览器回退操作
        ResponseUtil.setNoCacheHeaders(response);
    }
}
