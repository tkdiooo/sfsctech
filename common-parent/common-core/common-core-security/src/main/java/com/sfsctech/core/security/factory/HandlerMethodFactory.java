package com.sfsctech.core.security.factory;

import com.google.common.collect.Lists;
import com.sfsctech.core.security.resolver.ResponseBodyResolver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

/**
 * Class ResponseBodyWrapFactoryBean
 *
 * @author 张麒 2017/8/11.
 * @version Description:
 */
public class HandlerMethodFactory implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = Lists.newArrayList(returnValueHandlers);
        decorateHandlers(handlers);
        adapter.setReturnValueHandlers(handlers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                //用自己的ResponseBody包装类替换掉框架的，达到返回Result的效果
                ResponseBodyResolver decorator = new ResponseBodyResolver(handler);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
                break;
            }
        }
    }
}
