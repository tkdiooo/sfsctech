package com.sfsctech.demo.cloud.client.service.impl;

import com.sfsctech.common.core.base.constants.RpcConstants;
import com.sfsctech.common.core.rpc.result.ActionResult;
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
    public ActionResult<CheckBindingRes> checkBinding(@RequestBody CheckBindingReq checkBindingReq) {
        CheckBindingRes res = new CheckBindingRes();
        res.setGroupId(checkBindingReq.getBuCode() + ":ClientService");
        res.setReq(checkBindingReq);
        ActionResult<CheckBindingRes> result = new ActionResult<>(res);
        result.setStatus(RpcConstants.Status.Failure);
        System.out.println(result.toString());
        return result;
    }
}
