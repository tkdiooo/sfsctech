package com.sfsctech.base.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RPC服务通信接口响应对象<br/>
 * success：成功 or 失败<br/>
 * ReplyCode：响应状态码<br/>
 * messages：响应消息列表<br/>
 * dataSet：数据集合<br/>
 * result：单一数据对象<br/>
 * attachs：附件集合
 *
 * @author 张麒 2016/4/11.
 * @version Description:
 */
public class ActionResult<T> extends BaseResult {

    private static final long serialVersionUID = -2581960680481338269L;

    private List<T> dataSet;

    private T result;

    public ActionResult() {

    }

    public ActionResult(T result) {
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

    /**
     * Attachment message
     */
    private Map<String, Object> attachs;

    public void addAttach(String key, Object value) {
        this.attachs.put(key, value);
    }

    public void setAttachs(Map<String, Object> map) {
        this.attachs = map;
    }

    public Map<String, Object> getAttachs() {
        return this.attachs;
    }
}
