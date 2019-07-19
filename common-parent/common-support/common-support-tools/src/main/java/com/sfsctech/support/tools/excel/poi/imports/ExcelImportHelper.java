package com.sfsctech.support.tools.excel.poi.imports;

import com.sfsctech.support.tools.excel.model.ExcelModel;
import com.sfsctech.support.tools.excel.model.SheetModel;
import com.sfsctech.support.tools.excel.poi.ExcelHelper;
import com.sfsctech.support.common.util.AssertUtil;
import com.sfsctech.support.common.util.MapUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class ExcelImportHelper
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
public class ExcelImportHelper extends ExcelHelper {

    private ExcelModel model;

    public ExcelImportHelper(ExcelModel model) throws IOException, InvalidFormatException {
        AssertUtil.notNull(model, "model 对象为空");
        Workbook workbook = createWorkbook(model);
        super.setWorkbook(workbook);
        this.setModel(model);
    }

    /**
     * 导入Excel
     */
    public void importExcel() {
        super.validSheet();
        Map<String, SheetModel> sheets = getModel().getSheets();
        Workbook workbook = super.getWorkbook();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            SheetModel sheetModel = sheets.get(sheet.getSheetName());
            readSheet(sheet, sheetModel);
        }
        model.setSheets(sheets);
    }


    /**
     * 读取标题
     *
     * @param row Row
     * @return List
     */
    @Override
    public List<String> readHeader(Row row) {
        return super.readHeader(row);
    }

    /**
     * 读取行数据
     *
     * @param row    Row
     * @param header 标题
     * @return Map
     */
    public Map<String, Object> readRow(Row row, List<String> header) {
        AssertUtil.notNull(row, "row 对象为空");
        AssertUtil.notEmpty(header, "header 集合为空");
        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            data.put(header.get(i), getCellValue(row.getCell(i)));
        }
        return data;
    }

    /**
     * 读取行数据
     *
     * @param row Row
     * @return List
     */
    public List<Object> readRow(Row row) {
        AssertUtil.notNull(row, "row 对象为空");
        List<Object> data = new ArrayList<>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            data.add(getCellValue(row.getCell(i)));
        }
        return data;
    }

    @Override
    public ExcelModel getModel() {
        return model;
    }

    public void setModel(ExcelModel model) {
        this.model = model;
    }
}
