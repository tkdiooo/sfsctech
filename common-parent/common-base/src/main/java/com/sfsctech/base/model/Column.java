package com.sfsctech.base.model;

import java.io.Serializable;

/**
 * Class Column
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public class Column implements Serializable {

    private static final long serialVersionUID = 932304663891096692L;

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
