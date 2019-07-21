package com.sfsctech.support.tools.excel.poi.imports;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sfsctech.support.common.util.AssertUtil;
import com.sfsctech.support.tools.excel.annotation.ExcelHeader;
import com.sfsctech.support.tools.excel.constants.ExcelConstants;
import com.sfsctech.support.tools.excel.inf.ComplexDataMapping;
import com.sfsctech.support.tools.excel.model.ExcelModel;
import com.sfsctech.support.tools.excel.model.SheetModel;
import com.sfsctech.support.tools.excel.poi.ExcelHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Class ExcelImportHelper
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
public class ExcelImportHelper extends ExcelHelper {

    private ExcelModel model;

    private Map<Class<?>, ComplexDataMapping> complex = new HashMap<>();

    public ExcelImportHelper(ExcelModel model) throws IOException {
        AssertUtil.notNull(model, "model 对象为空");
        super.setWorkbook(createWorkbook(model));
        logger.info("创建Workbook");
        this.model = model;
        logger.info("初始化ExcelModel对象:{}", model);
    }


    /**
     * 导入Excel
     */
    public void importExcel() {
        logger.info("准备导入Excel,校验规则:{}", model.getVerify());
        // 强校验
        if (model.getVerify().equals(ExcelConstants.Verify.Strong)) {
            super.validSheet();
        }
        // 弱校验
        else {
            super.matchSheet();
        }
        // 加载sheet数据
        model.getPrototypeSheet().forEach((key, value) -> model.getSheets().put(value.name(), readSheet(getWorkbook().getSheet(value.name()), value.titleLine(), key)));
    }


    /**
     * 读取Sheet信息
     *
     * @param sheet Sheet
     */
    private <T> SheetModel<T> readSheet(Sheet sheet, int titleLine, Class<T> cls) {
        AssertUtil.notNull(sheet, "sheet 对象为空");
        logger.info("Sheet[{}]标题行号:{}", sheet.getSheetName(), titleLine);
        // 获取数据原型的标题信息（key : sheet标题名称、value : 数据原型属性名称）
        Map<Field, String> dataTitle = getHeader(cls);
        // 创建sheet对象
        SheetModel<T> sheetModel = new SheetModel<>(sheet.getSheetName());
        // 当前sheet标题行号
        sheetModel.setTitleLine(titleLine);
        // excel的sheet标题（key : sheet标题名称、value : 标题的列号）
        Map<String, Integer> excelTitle;
        // 有标题读取
        if (sheetModel.getTitleLine() >= 0) {
            // 读取标题
            excelTitle = readHeader(sheet.getRow(sheetModel.getTitleLine()));
            // 验证标题
            if (this.getModel().getVerify().equals(ExcelConstants.Verify.Strong)) {
                validHeader(sheet, excelTitle, dataTitle);
            }
        }
        // 无标题读取
        else {
            final Integer[] count = {0};
            excelTitle = Maps.newLinkedHashMap();
            dataTitle.forEach((key, value) -> excelTitle.put(value, count[0]++));
        }
        // sheet数据集合
        Map<Integer, T> rows = sheetModel.getRowData();
        // 读取数据
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            if (sheetModel.getTitleLine() != i && null != sheet.getRow(i)) {
                JSONObject jo = readRow(sheet.getRow(i), dataTitle, excelTitle);
                rows.put(i, jo.toJavaObject(cls));
                logger.info(jo.toString());
            }
        }
        return sheetModel;
    }

    private JSONObject readRow(Row row, Map<Field, String> dataTitle, Map<String, Integer> excelTitle) {
        AssertUtil.notNull(row, "row 对象为空");
        JSONObject jo = new JSONObject();
        dataTitle.forEach((key, value) -> jo.put(key.getName(), getCellValue(key, row.getCell(excelTitle.get(value)))));
        return jo;
    }

    @Override
    public ExcelModel getModel() {
        return model;
    }

    @Override
    protected <T, S> T complexData(ExcelHeader excelHeader, S value) {
        if (!complex.containsKey(excelHeader.complexMapping())) {
            try {
                complex.put(excelHeader.complexMapping(), excelHeader.complexMapping().newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) complex.get(excelHeader.complexMapping()).mapping(value);
    }
}
