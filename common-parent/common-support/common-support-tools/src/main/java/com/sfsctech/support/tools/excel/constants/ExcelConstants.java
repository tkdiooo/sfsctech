package com.sfsctech.support.tools.excel.constants;


import com.sfsctech.core.base.constants.DateConstants;

/**
 * Class ExcelConstants
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
public class ExcelConstants {

    public enum Verify {
        Strong,
        Weak
    }

    public enum CellStyle {
        HeaderCell,
        DateCell,
        FormulaCell,
        StringCell,
        StringCellNL,
        NumericCell,
        NumericCellNL,
        BooleanCell,
        BooleanCellNL,
    }

    public enum ExcelFormat {
        DATE(DateConstants.PATTERN_YMD_DASH),
        DATETIME(DateConstants.PATTERN_DEFAULT_DASH),
        NUMBER("0.00"),
        CURRENCY("#,##0.00"),
        PERCENT("0.00%");
        private final String pattern;

        ExcelFormat(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return this.pattern;
        }
    }

    public enum ExcelVersion {
        xls, xlsx
    }
}
