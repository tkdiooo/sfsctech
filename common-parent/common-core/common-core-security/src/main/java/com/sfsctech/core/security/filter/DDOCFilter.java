package com.sfsctech.core.security.filter;


import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.filter.BaseFilter;
import com.sfsctech.core.security.domain.Whitelist;
import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.MapUtil;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Class SecurityFilter
 *
 * @author 张麒 2017-11-6.
 * @version Description:
 */
public class DDOCFilter extends BaseFilter {

    public static final int FILTER_ORDER = 1;

    private RedisTemplate<String, ?> redisTemplate;

    private SecurityProperties.DDOS ddos;

    private Map<String, Whitelist> whitelist;

    public DDOCFilter(SecurityProperties.DDOS ddos, RedisTemplate<String, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.ddos = ddos;
        if (null != ddos.getWhitelist()) {
            this.whitelist = MapUtil.toMap(ddos.getWhitelist(), "ip");
        }
    }

    @Override
    public void doHandler(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        System.out.println(url);
        String ip = HttpUtil.getRequestIP(request);
        String key = "req_limit_".concat(url).concat(LabelConstants.UNDERLINE).concat(ip);
        long count = redisTemplate.opsForValue().increment(key, 1);
        int time = ddos.getTimeSpan();
        int limit = ddos.getLimit();
        // 白名单请求
        if (null != this.whitelist && whitelist.containsKey(ip)) {
            time = whitelist.get(ip).getTimeSpan();
            limit = whitelist.get(ip).getLimit();
        }
        if (count > limit) {
            logger.warn("用户IP[" + ip + "]访问地址[" + url + "]，在[" + time + "]秒内，超过了限定的次数[" + limit + "]");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden illegal request");
            return;
        }
        if (count == 1) {
            redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
