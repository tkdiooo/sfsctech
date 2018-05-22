package com.sfsctech.core.security.resolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sfsctech.core.security.annotation.Verify;
import com.sfsctech.core.security.tools.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Class argumentResolvers
 *
 * @author 张麒 2017/6/22.
 * @version Description:
 */
public class RequestAttributeResolver implements HandlerMethodArgumentResolver {

    private final Logger logger = LoggerFactory.getLogger(RequestAttributeResolver.class);

    /**
     * 检查参数是否需要验证
     */
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Verify.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        JSONObject jo = new JSONObject();
        request.getParameterMap().forEach((key, value) -> {
            if (value.length > 1) {
                jo.put(key.substring(0, key.length() - 2), value);
            } else {
                jo.put(key, value[0]);
            }
        });
        logger.info("请求地址：" + parameter.getMethod().getDeclaringClass() + "." + parameter.getMethod().getName() + "()，参数解析：" + jo.toJSONString());
        Object o = JSON.parseObject(jo.toJSONString(), parameter.getParameterType());
        // TODO
//        ValidatorResult result = ValidatorUtil.validate(o);
//        if (result.hasErrors()) {
//            throw new VerifyException(I18NConstants.Tips.ExceptionValidator, result);
//        }
        // 解密敏感参数
        SecurityUtil.Decrypt(o);
        return o;
    }
}