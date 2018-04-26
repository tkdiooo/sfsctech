package com.sfsctech.framework.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.framework.service.read.AccountReadService;
import com.sfsctech.framework.service.transactional.AccountTransactionalService;
import com.sfsctech.framework.service.write.AccountWriteService;
import com.sfsctech.rpc.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


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
    private AccountWriteService writeService;

    @Autowired
    private AccountReadService readService;

    @Autowired
    private AccountTransactionalService transactionalService;

    @Autowired
    private CacheFactory factory;

    @Override
    public ActionResult<SysAccountDto> save(SysAccountDto model) {
        System.out.println(readService.find());
//        model.setEnabled(0);
//        model.setLocked(0);
//        model.setStatus(StatusConstants.Status.VALID.getKey());
//        writeService.save(model);
        return new ActionResult<>(model);
    }

    @Override
    public ActionResult<SysAccountDto> find() {
        return null;
    }

    @Override
    public ActionResult<SysAccountDto> findByAccount(String account) {
        return null;
    }

    @Override
    public ActionResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo) {
        System.out.println(factory.getCacheClient().get("test_key"));
        logger.info("日志消息");
        PageInfo<TSysAccount> page = readService.findByPage(pagingInfo);
        pagingInfo.setRecordsTotal(page.getTotal());
        page.getList().forEach(account -> pagingInfo.getData().add(BeanUtil.copyPropertiesNotEmpty(SysAccountDto.class, account)));
        return new ActionResult<>(pagingInfo);
    }
}