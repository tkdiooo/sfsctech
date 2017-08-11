package com.sfsctech.security.resolver;

import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.security.csrf.CSRFToken;
import com.sfsctech.security.csrf.CSRFTokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

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
            ActionResult result = (ActionResult) returnValue;
            result.addAttach(CSRFTokenManager.CSRF_TOKEN, CSRFTokenManager.generateCSRFToken(request));
        }
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}
