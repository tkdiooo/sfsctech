package com.sfsctech.mybatis.domain;

import java.util.Date;

public class TSysAccount {
    private Long guid;

    private Long userguid;

    private String account;

    private String password;

    private String initpassword;

    private Short sort;

    private Short enabled;

    private Short locked;

    private Date locktime;

    private Short status;

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
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getInitpassword() {
        return initpassword;
    }

    public void setInitpassword(String initpassword) {
        this.initpassword = initpassword == null ? null : initpassword.trim();
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    public Short getLocked() {
        return locked;
    }

    public void setLocked(Short locked) {
        this.locked = locked;
    }

    public Date getLocktime() {
        return locktime;
    }

    public void setLocktime(Date locktime) {
        this.locktime = locktime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
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