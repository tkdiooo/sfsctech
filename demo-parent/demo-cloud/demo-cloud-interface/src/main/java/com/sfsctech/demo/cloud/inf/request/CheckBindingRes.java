package com.sfsctech.demo.cloud.inf.request;


/**
 * 检查是否绑定结果类
 *
 * @author: huangsheng
 * @date: 2017/11/2
 */
public class CheckBindingRes {

    /**
     * 是否绑定
     */
    private boolean binding = false;

    /**
     * groupId
     */
    private String groupId;

    private CheckBindingReq req;

    /**
     * Gets group id.
     *
     * @return the group id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets group id.
     *
     * @param groupId the group id
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Is binding boolean.
     *
     * @return the boolean
     */
    public boolean isBinding() {
        return binding;
    }

    /**
     * Sets binding.
     *
     * @param binding the binding
     */
    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    public CheckBindingReq getReq() {
        return req;
    }

    public void setReq(CheckBindingReq req) {
        this.req = req;
    }
}
