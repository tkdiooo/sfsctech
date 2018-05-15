package com.sfsctech.common.core.rest.util;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
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

    public static RestTemplate patch(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                converterTarget = converter;
                break;
            }
        }
        if (converterTarget != null) {
            converters.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(converter);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }
}
