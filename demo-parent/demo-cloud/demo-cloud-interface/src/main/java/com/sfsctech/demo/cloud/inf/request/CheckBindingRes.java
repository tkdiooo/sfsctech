package com.sfsctech.demo.cloud.inf.request;

import com.bestv.common.lang.result.BaseResult;

/**
 * 检查是否绑定结果类
 *
 * @author: huangsheng
 * @date: 2017/11/2
 */
public class CheckBindingRes extends BaseResult {

    /**
     * 是否绑定
     */
    private boolean binding = false;

    /**
     * groupId
     */
    private String groupId;

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
}
