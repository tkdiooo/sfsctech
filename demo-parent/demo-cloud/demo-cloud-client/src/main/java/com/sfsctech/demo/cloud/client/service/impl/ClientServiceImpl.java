package com.sfsctech.demo.cloud.client.service.impl;

import com.sfsctech.demo.cloud.inf.request.CheckBindingReq;
import com.sfsctech.demo.cloud.inf.request.CheckBindingRes;
import com.sfsctech.demo.cloud.inf.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Class ClientServiceImpl
 *
 * @author 张麒 2018-5-7.
 * @version Description:
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public CheckBindingRes checkBinding(@RequestBody CheckBindingReq checkBindingReq) {
        System.out.println(checkBindingReq.getBuCode());
        CheckBindingRes res = new CheckBindingRes();
        res.setGroupId(checkBindingReq.getBuCode() + ":ClientService");
        return res;
    }
}
