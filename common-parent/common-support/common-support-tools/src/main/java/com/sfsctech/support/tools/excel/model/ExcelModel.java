package com.sfsctech.support.tools.excel.model;

import com.sfsctech.support.tools.excel.constants.ExcelConstants;
import com.sfsctech.support.tools.excel.poi.style.CellStyles;
import com.sfsctech.support.tools.excel.poi.style.DefaultCellStyle;
import lombok.Data;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class ExcelModel
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
@Data
public class ExcelModel {

    private ExcelConstants.ExcelVersion version = ExcelConstants.ExcelVersion.xlsx;

    private ExcelConstants.SheetVerify sheetVerify = ExcelConstants.SheetVerify.Full;

    private String filePath;

    private InputStream inputStream;

    /**
     * Sheet原型
     * String sheet名称
     */
    private Map<String, SheetModel<?>> sheets = new LinkedHashMap<>();

    /**
     * 数据原型
     */
    private final List<Class> prototype;

    private CellStyles style = new DefaultCellStyle();

    public ExcelModel(String filePath, List<Class> prototype) {
        this.filePath = filePath;
        this.prototype = prototype;
    }

    public ExcelModel(InputStream inputStream, List<Class> prototype) {
        this.inputStream = inputStream;
        this.prototype = prototype;
    }

}
