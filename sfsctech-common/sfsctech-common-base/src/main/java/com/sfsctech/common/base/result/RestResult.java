package com.sfsctech.common.base.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Restful 通信接口响应对象
 * success：成功 or 失败<br/>
 * ReplyCode：响应状态码<br/>
 * messages：响应消息列表<br/>
 * dataSet：数据集合<br/>
 * result：单一数据对象
 *
 * @author 张麒 2017/3/21.
 * @version Description:
 */
public class RestResult<T> extends BaseResult {

    private static final long serialVersionUID = -5771508025833426474L;

    private List<T> dataSet;

    private T result;

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

}
