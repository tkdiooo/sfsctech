//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.execute.factory.http;

import com.bestv.common.net.domain.CommonNetConfig;
import com.bestv.common.net.util.UTF8PatcherForHttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class CommonRestTemplateFactory implements RestTemplateFactory {
    public CommonRestTemplateFactory() {
    }

    public RestTemplate createRestTemplate(CommonNetConfig commonNetConfig) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(commonNetConfig.getConnectionRequestTimeout());
        httpRequestFactory.setConnectTimeout(commonNetConfig.getConnectionTimeout());
        httpRequestFactory.setReadTimeout(commonNetConfig.getReadTimeout());
        RestTemplate httpClient = new RestTemplate(httpRequestFactory);
        UTF8PatcherForHttpClient.patch(httpClient);
        return httpClient;
    }
}
