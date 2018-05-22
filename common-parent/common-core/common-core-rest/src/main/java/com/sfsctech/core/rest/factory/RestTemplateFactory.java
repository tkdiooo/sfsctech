package com.sfsctech.core.rest.factory;

import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Class RestTemplateFactory
 *
 * @author 张麒 2018-5-15.
 * @version Description:
 */
public interface RestTemplateFactory {

    RestTemplate buildSimpleRest();

    RestTemplate buildPoolRest();

    AsyncRestTemplate buildAsyncRest();
}
