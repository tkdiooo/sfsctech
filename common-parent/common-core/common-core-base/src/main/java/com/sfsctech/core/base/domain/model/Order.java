package com.sfsctech.core.base.domain.model;

import java.io.Serializable;

/**
 * Class Order
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 5187015103480060291L;
    private int column;
    private String dir;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
