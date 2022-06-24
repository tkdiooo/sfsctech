package com.sfsctech.support.tools.excel.poi.exports;

import com.sfsctech.support.common.util.AssertUtil;
import com.sfsctech.support.common.util.FileUtil;
import com.sfsctech.support.common.util.MapUtil;
import com.sfsctech.support.tools.excel.annotation.ExcelHeader;
import com.sfsctech.support.tools.excel.annotation.ExcelSheet;
import com.sfsctech.support.tools.excel.constants.ExcelConstants;
import com.sfsctech.support.tools.excel.model.ExcelModel;
import com.sfsctech.support.tools.excel.model.SheetModel;
import com.sfsctech.support.tools.excel.poi.ExcelHelper;
import com.sfsctech.support.tools.excel.poi.imports.ExcelImportHelper;
import com.sfsctech.support.tools.excel.poi.style.CellStyles;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Class ExcelExportHelper
 *
 * @author 张麒 2016/4/16.
 * @version Description:
 */
public class ExcelExportHelper extends ExcelHelper {

    // 设置cell编码解决中文高位字节截断
//     private static short XLS_ENCODING = HSSFWorkbook.ENCODING_UTF_16;

    private static final int MAX_ROW = 65535;

    private ExcelModel model;

    private CellStyles style;

    public ExcelExportHelper(ExcelModel model) throws IOException {
        AssertUtil.notNull(model, "model 对象为空");
        AssertUtil.notNull(model.getVersion(), "model内[version] 对象为空");

        this.setModel(model);
        Workbook workbook = createWorkbook(getModel());
        this.style = model.getStyle();
        this.style.initStyle(workbook);
        super.setWorkbook(workbook);
    }

    /**
     * 批量构建Sheet
     *
     * @param sheets
     * @param <T>
     */
    public <T> void bulidSheets(Map<Class<T>, List<T>> sheets) {
        sheets.forEach(this::bulidSheet);
    }

    /**
     * 构建Sheet
     *
     * @param cls
     * @param dataRows
     * @param <T>
     */
    public <T> void bulidSheet(Class<T> cls, List<T> dataRows) {
        // 构建sheet标题数据
        super.setSheetHeader(getModel(), cls);
        // 构建sheet行数据
        super.setSheetRows(getModel(), dataRows, cls);
        getModel().getSheets().forEach((sheetName, sheetModel) -> {
            // 创建sheet
            Sheet sheet = createSheet(sheetName);
            // 创建标题
            createHeader(sheet, sheetModel.getTitleLine(), sheetModel.getHeader());
            // 创建行
            createRow(sheet, sheetModel.getHeader(), sheetModel.getRows());
            // 设置列宽自适应
            int columnCount = sheet.getRow(sheet.getLastRowNum()).getLastCellNum();
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i, true);
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
            }
        });
    }

    /**
     * 创建Sheet
     *
     * @param sheetName Sheet Name
     */
    public Sheet createSheet(String sheetName) {
        AssertUtil.isNotBlank(sheetName, "sheetName 对象为空");
        Sheet sheet = super.getWorkbook().getSheet(sheetName);
        if (null == sheet) {
            sheet = super.getWorkbook().createSheet(sheetName);
        }
        return sheet;
    }

    /**
     * 创建标题信息
     *
     * @param sheet     Sheet
     * @param titleLine 标题行标
     * @param header    标题数据集
     */
    public void createHeader(Sheet sheet, int titleLine, LinkedHashMap<Field, String> header) {
        AssertUtil.notNull(sheet, "sheet 对象为空");
        AssertUtil.notEmpty(header, "dataRows 集合为空");
        if (titleLine != -1) {
            Row row = sheet.createRow(titleLine);
            if (MapUtil.isNotEmpty(header)) {
                int cellIndex = 0;
                for (Field key : header.keySet()) {
                    // 生成标题
                    Cell title = row.createCell(cellIndex++);
                    // 设置标题内容
                    setCellValue(title, header.get(key));
                    // 设置标题样式
                    if (null != style) title.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.HeaderCell));
                }
            }
        }
    }

    /**
     * 导出Excel文件至model内filePath路径
     *
     * @throws IOException
     */
    public void exportExcel() throws IOException {
        AssertUtil.notNull(getModel().getSheets(), "model内[sheets] 对象为空");
        super.writeExcel(getModel());
    }


    /**
     * 创建指定行的row数据
     *
     * @param sheet    Sheet
     * @param header   标题
     * @param dataRows 数据集
     */
    public void createRow(Sheet sheet, LinkedHashMap<Field, String> header, Map<Integer, Map<String, Object>> dataRows) {
        AssertUtil.notNull(sheet, "sheet 对象为空");
        AssertUtil.notEmpty(header, "header 集合为空");
        AssertUtil.notEmpty(dataRows, "dataRows 集合为空");

        // 数据集合量越界
        if (dataRows.size() > MAX_ROW || sheet.getLastRowNum() + dataRows.size() > MAX_ROW)
            throw new RuntimeException("数据行数超过[" + MAX_ROW + "]行，请调整后再次操作。");

        //遍历数据
        for (Integer rowIndex : dataRows.keySet()) {
            Map<String, Object> data = dataRows.get(rowIndex);
            int cellIndex = 0;
            Row row = sheet.createRow(rowIndex);
            // 如果标题为空，以数据序列为准
            for (Field field : header.keySet()) {
                Cell cell = row.createCell(cellIndex++);
                // 设置内容
                setCellValue(cell, data.get(field.getName()));
                // 设置样式
                setCellStyle(cell, data.get(field.getName()));
            }
        }
    }

    /**
     * 创建row数据
     *
     * @param sheet    Sheet
     * @param dataRows 数据集合
     */
    public void createRow(Sheet sheet, Map<Integer, Map<String, Object>> dataRows) {
        this.createRow(sheet, null, dataRows);
    }

    /**
     * 根据ExcelModel追加至Excel
     *
     * @param source ExcelModel
     * @throws IOException
     */
    public void appendExcel(ExcelModel source) throws IOException, InvalidFormatException {
        AssertUtil.notNull(source, "source 对象为空");
        // 读取需要追加的Excel信息
        ExcelImportHelper helper = new ExcelImportHelper(source);
        helper.importExcel();
        getModel().setSheets(source.getSheets());
        appendReady();
    }

    /**
     * 根据数据集合追加至Excel
     *
     * @param dataRows 数据集合
     * @param cls      映射Class
     * @param <T>      泛型类
     * @throws IOException
     */
    public <T> void appendExcel(List<T> dataRows, Class<T> cls) throws IOException {
        AssertUtil.notNull(cls, "cls 对象为空");
        AssertUtil.notEmpty(dataRows, "dataRows 集合为空");
        SheetModel sheetModel = super.getSheetModelByList(dataRows, cls);
        ExcelSheet excelSheet = cls.getAnnotation(ExcelSheet.class);
        getModel().getSheets().put(excelSheet.name(), sheetModel);
        appendReady();
    }

    private void appendReady() throws IOException {
        // 获取所有SheetModel集合
        Map<String, SheetModel> sheets = getModel().getSheets();
        if (sheets.size() > 0) {
            // 遍历需要追加的数据集合
            sheets.forEach((sheetName, sheetModel) -> {
                // 数据集合不为空
                if (sheetModel.getRows().size() > 0) {
                    // 获取需要写入的sheet对象
                    Sheet sheet = getWorkbook().getSheet(sheetName);
                    // 如果sheet不存在，属于新增sheet操作，直接写入Excel。否则换算追加行标
                    if (sheet != null) {
                        int rowIndex = sheet.getLastRowNum() + 1;
                        Map<Integer, Map<String, ?>> map = new HashMap<>();
                        for (int rower : sheetModel.getRows().keySet()) {
                            if (rower == sheetModel.getTitleLine()) {
                                rowIndex -= 1;
                                map.put(rower, sheetModel.getRows().get(rower));
                            } else {
                                map.put((rower + rowIndex), sheetModel.getRows().get(rower));
                            }
                        }
//                        getModel().getSheets().put(sheetName, new SheetModel(sheetName, sheetModel.getTitleLine(), map));
                    }
                }
            });
//            exportExcel();
        }
    }

    /**
     * 根据数据类型设置Cell样式
     *
     * @param cell  Cell
     * @param value 数据
     */
    public void setCellStyle(Cell cell, Object value) {
        if (style == null) {
            return;
        }
        if (null == value) {
            cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.StringCell));
        } else if (value instanceof String) {
            if (value.toString().contains("\n"))
                cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.StringCellNL));
            else
                cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.StringCell));
        } else if (value instanceof Date) {
            cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.DateCell));
        } else if (value instanceof Number) {
            if (value.toString().contains("\n"))
                cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.NumericCellNL));
            else
                cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.NumericCell));
        } else if (value instanceof Calendar) {
            cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.DateCell));
        } else if (value instanceof Boolean) {
            if (value.toString().contains("\n"))
                cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.BooleanCellNL));
            else
                cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.BooleanCell));
        } else if (value instanceof RichTextString) {
            cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.StringCellNL));
        } else {
            throw new RuntimeException("找不到匹配的数据类型");
        }
    }

    @Override
    public ExcelModel getModel() {
        return model;
    }

    @Override
    protected <T, S> T complexData(ExcelHeader excelHeader, S value) {
        return null;
    }


    public void setModel(ExcelModel model) {
        this.model = model;
    }

    /**
     * 设置Cell样式对象，对象必须实现CellStyles接口
     *
     * @param style CellStyles
     */
    public void setCellStyles(CellStyles style) {
        this.style = style;
    }
}
