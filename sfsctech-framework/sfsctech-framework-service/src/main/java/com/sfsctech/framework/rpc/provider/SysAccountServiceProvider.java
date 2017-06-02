package com.sfsctech.framework.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.sfsctech.common.base.model.PagingInfo;
import com.sfsctech.common.base.result.RpcResult;
import com.sfsctech.common.constants.I18NConstants;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.common.util.ThrowableUtil;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.framework.service.read.AccountReadService;
import com.sfsctech.framework.service.write.AccountWriteService;
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
    public RpcResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo) {
        ThrowableUtil.throwBizException(I18NConstants.Tips.ExceptionNetwork);
        PageInfo<TSysAccount> page = accountReadService.findByPage(pagingInfo);
        pagingInfo.setRecordsTotal(page.getTotal());
        page.getList().forEach(account -> pagingInfo.getData().add(BeanUtil.copyPropertiesNotEmpty(SysAccountDto.class, account)));
        return new RpcResult<>(pagingInfo);
    }
}
