package com.sfsctech.common.tools.excel.model;


import com.sfsctech.common.tools.excel.constants.ExcelConstants;

import java.util.Map;
import java.util.TreeMap;

/**
 * Class ExcelModel
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
public class ExcelModel {

    private ExcelConstants.ExcelVersion version;

    private String filePath;

    private Map<String, SheetModel> sheets;

    public ExcelModel(ExcelConstants.ExcelVersion version, String filePath) {
        this.version = version;
        this.filePath = filePath;
    }

    public ExcelConstants.ExcelVersion getVersion() {
        return version;
    }

    public void setVersion(ExcelConstants.ExcelVersion version) {
        this.version = version;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, SheetModel> getSheets() {
        if (sheets == null) {
            sheets = new TreeMap<>();
        }
        return sheets;
    }

    public void setSheets(Map<String, SheetModel> sheets) {
        this.sheets = sheets;
    }

}
