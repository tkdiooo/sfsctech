package com.sfsctech.common.excel.poi.style;

import com.sfsctech.common.excel.constants.ExcelConstants;
import org.apache.poi.ss.usermodel.CellStyle;

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
}
