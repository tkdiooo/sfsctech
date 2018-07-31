package com.sfsctech.demo.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * Class ApplyClientDetailService
 *
 * @author 张麒 2018-7-13.
 * @version Description:
 */
@Service
public class ApplyClientDetailService implements ClientDetailsService, ClientRegistrationService {

//    @Autowired
//    private ApplyService applyService;

    @Autowired
    private DataSource dataSource;

    @Override
    public ClientDetails loadClientByClientId(String applyName) throws ClientRegistrationException {

        // 通过redis获取ClientDetails，如果不存在则从数据库获取

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

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {

    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

    }

    @Override
    public void updateClientSecret(String s, String s1) throws NoSuchClientException {

    }

    @Override
    public void removeClientDetails(String s) throws NoSuchClientException {

    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return null;
    }
}
