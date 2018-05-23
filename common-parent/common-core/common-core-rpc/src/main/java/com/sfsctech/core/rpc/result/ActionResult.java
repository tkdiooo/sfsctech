package com.sfsctech.core.rpc.result;


import com.sfsctech.core.base.domain.result.BaseResult;
import com.sfsctech.core.spring.constants.I18NConstants.Tips;

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

    private Tips tips = Tips.OperateSuccess;

    private String[] params;

    public ActionResult() {
        super();
    }

    public ActionResult(Tips tips, String... params) {
        super();
        this.tips = tips;
        this.params = params;
    }

    public ActionResult(T result, Tips tips, String... params) {
        super();
        this.result = result;
        this.tips = tips;
        this.params = params;
    }

    public ActionResult(List<T> dataSet, Tips tips, String... params) {
        super();
        this.dataSet = dataSet;
        this.tips = tips;
        this.params = params;
    }

    public ActionResult(T result) {
        super();
        this.result = result;
    }

    public ActionResult(List<T> dataSet) {
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

    public Tips getTips() {
        return tips;
    }

    public void setTips(Tips tips) {
        this.tips = tips;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
