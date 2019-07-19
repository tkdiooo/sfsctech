package com.sfsctech.support.tools.excel.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Class SheetModel
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
@Data
public class SheetModel<T> {

    /**
     * sheet名称
     */
    private String sheetName;
    /**
     * 当前sheet标题行号，如果是-1表示没有标题
     */
    private Integer titleLine = -1;
    /**
     * 每行数据
     */
    private Map<Integer, T> rowData = new HashMap<>();

    public SheetModel(String sheetName) {
        this.sheetName = sheetName;
    }

}
