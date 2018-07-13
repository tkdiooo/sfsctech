package com.sfsctech.demo.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * Class ApplyClientDetailService
 *
 * @author 张麒 2018-7-13.
 * @version Description:
 */
public class ApplyClientDetailService implements ClientDetailsService {

//    @Autowired
//    private ApplyService applyService;

    @Autowired
    private DataSource dataSource;

    @Override
    public ClientDetails loadClientByClientId(String applyName) throws ClientRegistrationException {

        /*
        // 使用mybatic验证client是否存在 ，根据需求写sql
        Map clientMap = applyService.findApplyById(applyName);

        if(clientMap == null) {
            throw new ClientRegistrationException("应用" + applyName + "不存在!");
        }*/

//        MyJdbcClientDetailsService jdbcClientDetailsService= new MyJdbcClientDetailsService(dataSource, "authentication");
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);

        return jdbcClientDetailsService.loadClientByClientId(applyName);
    }

}
