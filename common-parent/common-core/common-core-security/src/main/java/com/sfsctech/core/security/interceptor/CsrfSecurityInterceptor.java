package com.sfsctech.core.security.interceptor;

import com.alibaba.fastjson.JSON;
import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.exception.enums.VerifyExceptionTipsEnum;
import com.sfsctech.core.exception.ex.VerifyException;
import com.sfsctech.core.security.csrf.CSRFTokenManager;
import com.sfsctech.core.security.tools.SecurityUtil;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.ResponseUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
public class CsrfSecurityInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(CsrfSecurityInterceptor.class);

    private Set<String> verifyExcludePath;

    private Set<String> requiredVerifyPath;

    public void setVerifyExcludePath(Set<String> verifyExcludePath) {
        this.verifyExcludePath = verifyExcludePath;
    }

    public void setRequiredVerifyPath(Set<String> requiredVerifyPath) {
        this.requiredVerifyPath = requiredVerifyPath;
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        final String requestURI = request.getRequestURI();
        final String domain = HttpUtil.getDomain(request);
        final String ret_url = request.getHeader("Referer");
        boolean verify = FilterHandler.isExclusion(requestURI, verifyExcludePath);
        boolean required = FilterHandler.isExclusion(requestURI, requiredVerifyPath);
        logger.info("exclusion：[" + verify + "] request uri：[" + requestURI + "] ");
        // 当前请求路径是否需要验证 && Csrf防御验证
        if ((!verify || required) && CSRFTokenManager.isValidCSRFToken(request, response)) {
            // TODO 待完善
            logger.error(VerifyExceptionTipsEnum.CsrfWrong.toString());
            throw new VerifyException(VerifyExceptionTipsEnum.CsrfWrong);
        }
        // 访问劫持验证：上次请求不是当前服务域名，并且不在允许跨域访问之内
        if ((!verify || required) && (StringUtil.isNotBlank(ret_url) && !ret_url.startsWith(domain))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden illegal request");
            return false;
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle：[" + request.getRequestURI() + "] ");
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
        // 设置无缓存，防止浏览器回退操作
        ResponseUtil.setNoCacheHeaders(response);
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 会在Controller方法异步执行时开始执行
     *
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    }
}
