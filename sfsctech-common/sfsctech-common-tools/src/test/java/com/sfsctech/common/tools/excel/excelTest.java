package com.sfsctech.common.tools.excel;

import com.sfsctech.common.tools.excel.constants.ExcelConstants;
import com.sfsctech.common.tools.excel.model.ExcelModel;
import com.sfsctech.common.tools.excel.model.SheetModel;
import com.sfsctech.common.tools.excel.poi.ExcelHelper;
import com.sfsctech.common.tools.excel.poi.exports.ExcelExportHelper;
import com.sfsctech.common.tools.excel.poi.imports.ExcelImportHelper;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class excelTest
 *
 * @author 张麒 2016/5/11.
 * @version Description:
 */
public class excelTest {


    private void exportExcel(Map<String, SheetModel> sheets) throws Exception {
        ExcelModel model = new ExcelModel(ExcelConstants.ExcelVersion.xlsx, "d:\\project.xlsx");
        model.setSheets(sheets);
        ExcelExportHelper helper = new ExcelExportHelper(model);
        helper.exportExcel();
    }

    private void writeExcelByClass(List<testModel> list, List<testModel> list1) throws Exception {
        ExcelModel multi = new ExcelModel(ExcelConstants.ExcelVersion.xlsx, "d:\\multi.xlsx");
        multi.getSheets().put("数据1", ExcelHelper.getSheetModelByList(list, testModel.class));
        multi.getSheets().put("数据2", ExcelHelper.getSheetModelByList(list1, testModel.class));
        ExcelExportHelper helper = new ExcelExportHelper(multi);
        helper.exportExcel();


        ExcelModel single = new ExcelModel(ExcelConstants.ExcelVersion.xlsx, "d:\\single.xlsx");
        ExcelHelper.setSheetRows(single, list, testModel.class);
        ExcelExportHelper helper1 = new ExcelExportHelper(single);
        helper1.exportExcel();
    }

    @Test
    public void importExcel() throws Exception {
        ExcelModel excelModel = new ExcelModel(ExcelConstants.ExcelVersion.xlsx, "d:\\items20150717.xlsx");

        LinkedHashMap<String, Object> header = ExcelHelper.getHeader(testModel.class);
        ExcelHelper.setSheetHeader(excelModel, header, "项目数据", 0);

        ExcelImportHelper helper = new ExcelImportHelper(excelModel);

        helper.importExcel();
        exportExcel(excelModel.getSheets());
    }

    @Test
    public void importExcelByClass() throws Exception {
        ExcelModel excelModel = new ExcelModel(ExcelConstants.ExcelVersion.xlsx, "d:\\items20150717.xlsx");

        ExcelHelper.setSheetHeader(excelModel, testModel.class);

        ExcelImportHelper helper = new ExcelImportHelper(excelModel);

        helper.importExcel();

        for (String key : excelModel.getSheets().keySet()) {
            List<testModel> list = ExcelHelper.getListBySheetModel(excelModel.getSheets().get(key), testModel.class);
            writeExcelByClass(list, list);
        }
    }

    @Test
    public void appendExcel() throws Exception {
        ExcelModel excelModel = new ExcelModel(ExcelConstants.ExcelVersion.xlsx, "d:\\multi.xlsx");
        LinkedHashMap<String, Object> header = ExcelHelper.getHeader(testModel.class);
        ExcelHelper.setSheetHeader(excelModel, header, "数据1", 0);
        ExcelHelper.setSheetHeader(excelModel, header, "数据2", 0);
        ExcelImportHelper helper = new ExcelImportHelper(excelModel);
        helper.importExcel();

        ExcelModel model = new ExcelModel(ExcelConstants.ExcelVersion.xlsx, "d:\\project.xlsx");
        ExcelExportHelper exportHelper = new ExcelExportHelper(model);
        List<testModel> list = ExcelHelper.getListBySheetModel(excelModel.getSheets().get("数据1"), testModel.class);
        exportHelper.appendExcel(list, testModel.class);

        ExcelModel model1 = new ExcelModel(ExcelConstants.ExcelVersion.xlsx, "d:\\project1.xlsx");
        ExcelExportHelper eh = new ExcelExportHelper(model1);
        eh.appendExcel(excelModel);
    }
}
