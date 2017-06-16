package com.sfsctech.framework.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
//import com.alibaba.dubbo.rpc.proxy.TraceIdUtil;
import com.github.pagehelper.PageInfo;
import com.sfsctech.base.exception.BizException;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.base.result.RpcResult;
import com.sfsctech.cache.redis.RedisProxy;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.common.util.ThrowableUtil;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.framework.service.read.AccountReadService;
import com.sfsctech.framework.service.write.AccountWriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Class SysAccountServiceProvider
 *
 * @author 张麒 2017/5/27.
 * @version Description:
 */
@Service(retries = -1)
public class SysAccountServiceProvider implements SysAccountService {

    private final Logger logger = LoggerFactory.getLogger(SysAccountServiceProvider.class);

    @Autowired
    private AccountWriteService accountWriteService;

    @Autowired
    private AccountReadService accountReadService;

    @Autowired
    private RedisProxy redis;

    @Override
    public RpcResult<Long> save(List<SysAccountDto> dataSet) {
        RpcResult<Long> result = new RpcResult<>();
        dataSet.forEach(dto -> {
            TSysAccount model = BeanUtil.copyPropertiesNotEmpty(TSysAccount.class, dto);
            accountWriteService.save(model);
            result.getDataSet().add(model.getGuid());
        });
        return result;
    }

    @Override
    public RpcResult<SysAccountDto> find() {
        return null;
    }

    @Override
    public RpcResult<SysAccountDto> findByAccount(String account) {
        return null;
    }

    @Override
    public RpcResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo) {
        System.out.println(redis.get("test_key"));
        logger.info("日志消息");
        PageInfo<TSysAccount> page = accountReadService.findByPage(pagingInfo);
        pagingInfo.setRecordsTotal(page.getTotal());
        page.getList().forEach(account -> pagingInfo.getData().add(BeanUtil.copyPropertiesNotEmpty(SysAccountDto.class, account)));
        return new RpcResult<>(pagingInfo);
    }
}
