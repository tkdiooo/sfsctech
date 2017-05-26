package com.sfsctech.framework.inf;

import com.sfsctech.framework.model.dto.SysAccountDto;

import java.util.List;

/**
 * Class SysAccountService
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
public interface SysAccountService<T extends SysAccountDto> {

    Long save();

    List<T> find();

    List<T> findByAccount(String account);
}
