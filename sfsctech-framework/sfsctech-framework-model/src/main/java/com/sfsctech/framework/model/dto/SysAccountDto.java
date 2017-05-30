package com.sfsctech.framework.model.dto;

import com.sfsctech.common.base.model.BaseDto;

import java.util.Date;

/**
 * Class SysAccountDto
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
public class SysAccountDto extends BaseDto {

    private Long guid;

    private Long userguid;

    private String account;

    private String password;

    private String initpassword;

    private Integer sort;

    private Integer enabled;

    private Integer locked;

    private Date locktime;

    private Integer status;

    private Long creator;

    private Date createtime;

    private Long updater;

    private Date updatetime;

    public Long getGuid() {
        return guid;
    }

    public void setGuid(Long guid) {
        this.guid = guid;
    }

    public Long getUserguid() {
        return userguid;
    }

    public void setUserguid(Long userguid) {
        this.userguid = userguid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInitpassword() {
        return initpassword;
    }

    public void setInitpassword(String initpassword) {
        this.initpassword = initpassword;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Date getLocktime() {
        return locktime;
    }

    public void setLocktime(Date locktime) {
        this.locktime = locktime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
