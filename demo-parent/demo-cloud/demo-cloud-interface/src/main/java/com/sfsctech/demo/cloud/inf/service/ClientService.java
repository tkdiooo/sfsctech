package com.sfsctech.demo.cloud.inf.service;

import com.bestv.common.net.annotation.CommonNets;
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
@RequestMapping("/binding")
@CommonNets(appName = "cloud-client")
public interface ClientService {

    /**
     * 通过buCode和buMemberId检查是否绑定
     *
     * @author: huangsheng
     * @date: 2017/11/2 上午11:26
     */
    @RequestMapping("checkBinding")
    CheckBindingRes checkBinding(@RequestBody CheckBindingReq checkBindingReq);
}
