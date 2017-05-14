package com.sfsctech.common.tools.excel.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class SheetModel
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
public class SheetModel {


    private Integer headerIndex;

    private Map<Integer, Map<String, Object>> rows;

    public SheetModel(Integer headerIndex) {
        this.headerIndex = headerIndex;
    }

    public SheetModel(Integer headerIndex, Map<Integer, Map<String, Object>> rows) {
        this.headerIndex = headerIndex;
        this.rows = rows;
    }

    public Integer getHeaderIndex() {
        return headerIndex;
    }

    public void setHeaderIndex(Integer headerIndex) {
        this.headerIndex = headerIndex;
    }

    public Map<Integer, Map<String, Object>> getRows() {
        if (rows == null) {
            rows = new HashMap<>();
        }
        return rows;
    }

    public void setRows(Map<Integer, Map<String, Object>> rows) {
        this.rows = rows;
    }

}
