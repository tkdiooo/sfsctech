package com.sfsctech.support.tools.excel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class SheetModel
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SheetModel {

    /**
     * sheet名称
     */
    private String sheetName;
    /**
     * 当前sheet标题行号，如果是-1表示没有标题
     */
    private Integer titleLine = -1;

    /**
     * sheet标题
     */
    private LinkedHashMap<Field, String> header;

    /**
     * 每行数据
     * Integer:行号
     * String:标题Field
     * Object:数据
     */
    private Map<Integer, Map<String, Object>> rows = new HashMap<>();

}
