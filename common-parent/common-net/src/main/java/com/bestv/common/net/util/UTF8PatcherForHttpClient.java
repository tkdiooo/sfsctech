//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.util;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class UTF8PatcherForHttpClient {
    public UTF8PatcherForHttpClient() {
    }

    public static void patch(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
        StringHttpMessageConverter stringHttpMessageConverter = null;
        Iterator var3 = httpMessageConverters.iterator();

        while(var3.hasNext()) {
            HttpMessageConverter httpMessageConverter = (HttpMessageConverter)var3.next();
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                stringHttpMessageConverter = (StringHttpMessageConverter)httpMessageConverter;
                break;
            }
        }

        httpMessageConverters.remove(stringHttpMessageConverter);
        stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
        httpMessageConverters.add(stringHttpMessageConverter);
        restTemplate.setMessageConverters(httpMessageConverters);
    }
}
