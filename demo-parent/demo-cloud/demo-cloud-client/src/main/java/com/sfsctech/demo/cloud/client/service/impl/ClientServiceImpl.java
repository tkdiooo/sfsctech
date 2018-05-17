package com.sfsctech.demo.cloud.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.sfsctech.common.core.base.constants.RpcConstants;
import com.sfsctech.common.core.rpc.result.ActionResult;
import com.sfsctech.demo.cloud.inf.request.CheckBindingReq;
import com.sfsctech.demo.cloud.inf.request.CheckBindingRes;
import com.sfsctech.demo.cloud.inf.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger loggers = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public ActionResult<CheckBindingRes> checkBinding(@RequestBody CheckBindingReq checkBindingReq) {
        CheckBindingRes res = new CheckBindingRes();
        res.setGroupId(checkBindingReq.getBuCode() + ":ClientService");
        res.setReq(checkBindingReq);
        ActionResult<CheckBindingRes> result = new ActionResult<>(res);
        result.setStatus(RpcConstants.Status.Failure);
        loggers.info(JSON.toJSONString(result));
        return result;
    }
}
