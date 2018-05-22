package com.sfsctech.core.rest.util;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class UTF8PatcherForHttpClient
 *
 * @author 张麒 2018-5-15.
 * @version Description:
 */
public class UTF8PatcherForHttpClient {

    public static RestTemplate patch(RestTemplate restTemplate, FastJsonHttpMessageConverter jsonConverter) {
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (int i = converters.size() - 1; i >= 0; i--) {
            if (converters.get(i) instanceof StringHttpMessageConverter || converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.remove(i);
                break;
            }
        }
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(jsonConverter);
        restTemplate.setMessageConverters(converters);

        return restTemplate;
    }
}
