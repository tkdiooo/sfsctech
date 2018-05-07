//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.execute.factory.http;

import com.bestv.common.net.domain.CommonNetConfig;
import org.springframework.web.client.RestTemplate;

public interface RestTemplateFactory {
    RestTemplate createRestTemplate(CommonNetConfig var1);
}
