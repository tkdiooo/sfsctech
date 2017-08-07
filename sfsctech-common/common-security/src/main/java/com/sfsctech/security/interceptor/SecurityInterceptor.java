package com.sfsctech.security.interceptor;

import com.sfsctech.base.exception.BizException;
import com.sfsctech.base.model.BaseDto;
import com.sfsctech.common.util.ResponseUtil;
import com.sfsctech.constants.I18NConstants;
import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.security.csrf.CSRFTokenManager;
import com.sfsctech.security.tools.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//        }
        String requestURI = request.getRequestURI();
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
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//        }
        logger.info("requestURI：[" + request.getRequestURI() + "] " + getClass());
        if (null != modelAndView && null != modelAndView.getModel()) {
            // 加密敏感参数
            Map<String, Object> model = modelAndView.getModel();
            for (String key : model.keySet()) {
                Object obj = model.get(key);
                if (BaseDto.class.equals(obj.getClass().getSuperclass())) {
                    SecurityUtil.Encrypt(obj);
                }
            }
            // 设置Csrf Token
            model.put(CSRFTokenManager.CSRF_TOKEN, CSRFTokenManager.generateCSRFToken(request));
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
