package com.sfsctech.core.security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

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
        // TODO
//        if (returnValue.getClass().equals(ActionResult.class)) {
//            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
//            HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
//            ActionResult result = (ActionResult) returnValue;
//            result.addAttach(CSRFTokenManager.CSRF_TOKEN, CSRFTokenManager.generateCSRFToken(request, response));
//        }
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}
