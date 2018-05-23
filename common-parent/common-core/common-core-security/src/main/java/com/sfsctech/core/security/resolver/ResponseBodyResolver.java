package com.sfsctech.core.security.resolver;

import com.sfsctech.core.rpc.result.ActionResult;
import com.sfsctech.core.security.csrf.CSRFTokenManager;
import com.sfsctech.core.spring.util.ResourceUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class ResponseRe
 *
 * @author 张麒 2017/8/11.
 * @version Description:
 */
public class ResponseBodyResolver implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ResponseBodyResolver(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return delegate.supportsReturnType(methodParameter);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 处理Ajax请求的CSRF
        if (returnValue.getClass().equals(ActionResult.class)) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
            ActionResult result = (ActionResult) returnValue;
            result.addAttach(CSRFTokenManager.CSRF_TOKEN, CSRFTokenManager.generateCSRFToken(request, response));
            // 国际化提示
            if (null != result.getTips() && result.getMessages().size() == 0) {
                result.setMessage(ResourceUtil.getMessage(result.getTips().getCode(), request.getLocale(), result.getParams()));
            }
        }
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}
