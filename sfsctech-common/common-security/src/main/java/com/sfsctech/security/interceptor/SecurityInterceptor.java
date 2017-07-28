package com.sfsctech.security.interceptor;

import com.sfsctech.base.exception.BizException;
import com.sfsctech.constants.I18NConstants;
import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.security.csrf.CSRFTokenManager;
import netscape.security.ForbiddenTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Class SecurityInterceptor
 *
 * @author 张麒 2017/7/19.
 * @version Description:
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
        }
        boolean bool = SecurityConstants.isExclusion(requestURI);
        logger.info("exclusion：[" + bool + "] request uri：[" + requestURI + "] " + getClass());
        // 当前请求路径是否需要验证
        if (!bool) {
            // Csrf防御验证
            if (CSRFTokenManager.isValidCSRFToken(request)) {
                logger.warn("CSRF attack intercept");
                throw new BizException(I18NConstants.Tips.Exception403);
            }
        }
        // 解密敏感参数

        // 只有返回true才会继续向下执行，返回false取消当前请求
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
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
        }
        logger.info("requestURI：[" + request.getRequestURI() + "] " + getClass());
        // 加密敏感参数
        // 设置Csrf Token
        modelAndView.getModel().put(CSRFTokenManager.CSRF_TOKEN, CSRFTokenManager.generateCSRFToken(request));
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
