package com.sfsctech.common.support.excel.poi.style;

import com.sfsctech.common.support.excel.constants.ExcelConstants;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Class CellStyles
 *
 * @author 张麒 2016/4/16.
 * @version Description:
 */
public interface CellStyles {

    /**
     * 设置边框
     *
     * @param style CellStyle
     */
    void setBorder(CellStyle style);

    /**
     * 根据枚举获取Cell样式
     *
     * @param cellStyle 枚举类[ExcelConstants.CellStyle]
     * @return CellStyle
     */
    CellStyle getCellStyle(ExcelConstants.CellStyle cellStyle);

    /**
     * 初始化样式
     *
     * @param wb Workbook
     */
    void initStyle(Workbook wb);
}
