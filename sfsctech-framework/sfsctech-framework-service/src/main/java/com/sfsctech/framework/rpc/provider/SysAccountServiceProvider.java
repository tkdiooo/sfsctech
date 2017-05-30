package com.sfsctech.framework.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.sfsctech.common.base.result.RpcResult;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.framework.service.read.AccountReadService;
import com.sfsctech.framework.service.write.AccountWriteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Class SysAccountServiceProvider
 *
 * @author 张麒 2017/5/27.
 * @version Description:
 */
@Service(retries = -1)
public class SysAccountServiceProvider implements SysAccountService {

    @Autowired
    private AccountWriteService accountWriteService;

    @Autowired
    private AccountReadService accountReadService;

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
    public RpcResult<SysAccountDto> findByPage(int pageNum, int pageSize) {
        List<TSysAccount> list = accountReadService.findByPage(pageNum, pageSize);
        RpcResult<SysAccountDto> result = new RpcResult<>();
        list.forEach(account -> result.getDataSet().add(BeanUtil.copyPropertiesNotEmpty(SysAccountDto.class, account)));
        return result;
    }
}
