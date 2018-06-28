package com.sfsctech.demo.test.inf.service;

import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.demo.cloud.inf.request.CheckBindingReq;
import com.sfsctech.demo.cloud.inf.request.CheckBindingRes;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class ClientService
 *
 * @author 张麒 2018-5-7.
 * @version Description:
 */
@RestController
@RequestMapping("/test")
@CloudService("cloud-client")
public interface TestService {

    /**
     * 通过buCode和buMemberId检查是否绑定
     *
     * @author: huangsheng
     * @date: 2017/11/2 上午11:26
     */
    @RequestMapping("checkBinding")
    RpcResult<CheckBindingRes> checkBinding(@RequestBody CheckBindingReq checkBindingReq);
}
