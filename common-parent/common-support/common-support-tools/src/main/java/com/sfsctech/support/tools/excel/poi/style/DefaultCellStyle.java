package com.sfsctech.support.tools.excel.poi.style;

import com.sfsctech.support.tools.excel.constants.ExcelConstants;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class DefaultCellStyle
 *
 * @author 张麒 2016/4/16.
 * @version Description:
 */
public class DefaultCellStyle implements CellStyles {

    private Map<ExcelConstants.CellStyle, CellStyle> styles;

    public DefaultCellStyle() {
        styles = new HashMap<>();
    }

    @Override
    public void initStyle(Workbook wb) {
        DataFormat df = wb.createDataFormat();

        // --字体设定 --//

        //普通字体
        Font normalFont = wb.createFont();
        normalFont.setFontHeightInPoints((short) 10);

        //标题字体
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        //蓝色加粗字体
        Font blueBoldFont = wb.createFont();
        blueBoldFont.setFontHeightInPoints((short) 10);
        blueBoldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        blueBoldFont.setColor(IndexedColors.BLUE.getIndex());

        // --Cell Style设定-- //

        //标题格式
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        setBorder(headerStyle);
        styles.put(ExcelConstants.CellStyle.HeaderCell, headerStyle);

        //String格式
        CellStyle stringStyle = wb.createCellStyle();
        stringStyle.setFont(normalFont);
        setBorder(stringStyle);
        styles.put(ExcelConstants.CellStyle.StringCell, stringStyle);

        CellStyle stringStyleNL = wb.createCellStyle();
        stringStyleNL.setWrapText(true);
        stringStyleNL.setFont(normalFont);
        setBorder(stringStyleNL);
        styles.put(ExcelConstants.CellStyle.StringCellNL, stringStyleNL);

        //日期格式
        CellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setFont(normalFont);
        dateCellStyle.setDataFormat(df.getFormat(ExcelConstants.ExcelFormat.DATE.getPattern()));
        setBorder(dateCellStyle);
        styles.put(ExcelConstants.CellStyle.DateCell, dateCellStyle);

        //数字格式
        CellStyle numberStyle = wb.createCellStyle();
        numberStyle.setFont(normalFont);
        numberStyle.setDataFormat(df.getFormat(ExcelConstants.ExcelFormat.NUMBER.getPattern()));
        setBorder(numberStyle);
        styles.put(ExcelConstants.CellStyle.NumericCell, numberStyle);

        CellStyle numberStyleNL = wb.createCellStyle();
        numberStyleNL.setWrapText(true);
        numberStyleNL.setFont(normalFont);
        numberStyleNL.setDataFormat(df.getFormat(ExcelConstants.ExcelFormat.NUMBER.getPattern()));
        setBorder(numberStyleNL);
        styles.put(ExcelConstants.CellStyle.NumericCellNL, numberStyleNL);

        //布尔格式
        CellStyle boolStyle = wb.createCellStyle();
        boolStyle.setFont(blueBoldFont);
        boolStyle.setAlignment(CellStyle.ALIGN_CENTER);
        setBorder(boolStyle);
        styles.put(ExcelConstants.CellStyle.BooleanCell, boolStyle);

        CellStyle boolStyleNL = wb.createCellStyle();
        boolStyleNL.setWrapText(true);
        boolStyleNL.setFont(blueBoldFont);
        boolStyleNL.setAlignment(CellStyle.ALIGN_CENTER);
        setBorder(boolStyleNL);
        styles.put(ExcelConstants.CellStyle.BooleanCellNL, boolStyleNL);

        // 函数格式
        CellStyle formulaStyle = wb.createCellStyle();
        formulaStyle.setFont(blueBoldFont);
        formulaStyle.setAlignment(CellStyle.ALIGN_CENTER);
        setBorder(formulaStyle);
        styles.put(ExcelConstants.CellStyle.FormulaCell, boolStyle);
    }

    @Override
    public void setBorder(CellStyle style) {
        //设置边框
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    }

    @Override
    public CellStyle getCellStyle(ExcelConstants.CellStyle cellStyle) {
        return styles.get(cellStyle);
    }

}
