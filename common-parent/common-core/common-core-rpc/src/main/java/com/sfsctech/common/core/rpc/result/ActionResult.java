package com.sfsctech.common.core.rpc.result;


import com.sfsctech.common.core.base.domain.result.BaseResult;
import com.sfsctech.common.core.spring.constants.I18NConstants.Tips;
import com.sfsctech.common.core.spring.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * RPC服务通信接口响应对象
 *
 * @author 张麒 2016/4/11.
 * @version Description:
 */
public class ActionResult<T> extends BaseResult {

    private static final long serialVersionUID = -2581960680481338269L;

    private List<T> dataSet;

    private T result;

    public ActionResult() {
        super();
        super.addMessage(ResourceUtil.getMessage(Tips.OperateSuccess));
    }

    public ActionResult(Tips tips, String... params) {
        super();
        super.addMessage(ResourceUtil.getMessage(tips, params));
    }

    public ActionResult(T result, Tips tips, String... params) {
        super();
        super.addMessage(ResourceUtil.getMessage(tips, params));
        this.result = result;
    }

    public ActionResult(List<T> dataSet, Tips tips, String... params) {
        super();
        super.addMessage(ResourceUtil.getMessage(tips, params));
        this.dataSet = dataSet;
    }

    public ActionResult(T result) {
        super();
        super.addMessage(ResourceUtil.getMessage(Tips.OperateSuccess));
        this.result = result;
    }

    public ActionResult(List<T> dataSet) {
        super.addMessage(ResourceUtil.getMessage(Tips.OperateSuccess));
        this.dataSet = dataSet;
    }

    public List<T> getDataSet() {
        if (null == dataSet) {
            dataSet = new ArrayList<>();
        }
        return dataSet;
    }

    public void setDataSet(List<T> dataSet) {
        this.dataSet = dataSet;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void addMessage(Tips tips, String... params) {
        super.addMessage(ResourceUtil.getMessage(tips, params));
    }

    public void setMessage(Tips tips, String... params) {
        super.setMessage(ResourceUtil.getMessage(tips, params));
    }
}
