package com.sfsctech.common.support.excel.poi.exports;

import com.sfsctech.common.support.excel.annotation.ExcelSheet;
import com.sfsctech.common.support.excel.constants.ExcelConstants;
import com.sfsctech.common.support.excel.model.ExcelModel;
import com.sfsctech.common.support.excel.model.SheetModel;
import com.sfsctech.common.support.excel.poi.ExcelHelper;
import com.sfsctech.common.support.excel.poi.imports.ExcelImportHelper;
import com.sfsctech.common.support.excel.poi.style.CellStyles;
import com.sfsctech.common.support.util.AssertUtil;
import com.sfsctech.common.support.util.FileUtil;
import com.sfsctech.common.support.util.MapUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.util.*;

/**
 * Class ExcelExportHelper
 *
 * @author 张麒 2016/4/16.
 * @version Description:
 */
public class ExcelExportHelper extends ExcelHelper {

    // 设置cell编码解决中文高位字节截断
    // private static short XLS_ENCODING = HSSFWorkbook.ENCODING_UTF_16;

    private static final int MAX_ROW = 65535;

    private ExcelModel model;

    private CellStyles style;

    public ExcelExportHelper(ExcelModel model) throws IOException, InvalidFormatException {
        AssertUtil.notNull(model, "model 对象为空");
        AssertUtil.notNull(model.getVersion(), "model内[version] 对象为空");

        this.setModel(model);
        Workbook workbook;
        // 如果文件存在
        if (FileUtil.isExists(getModel().getFilePath())) {
            workbook = createWorkbook(getModel().getFilePath());
        } else {
            workbook = createWorkbook(getModel().getVersion());
        }
        this.style = model.getStyle();
        this.style.initStyle(workbook);
        super.setWorkbook(workbook);
    }

    /**
     * 设置Cell样式对象，对象必须实现CellStyles接口
     *
     * @param style CellStyles
     */
    public void setCellStyles(CellStyles style) {
        this.style = style;
    }

    /**
     * 导出Excel文件至filePath路径
     *
     * @throws IOException
     */
    public void exportExcel() throws IOException {
        AssertUtil.notNull(getModel().getSheets(), "model内[sheets] 对象为空");
        AssertUtil.isNotBlank(getModel().getFilePath(), "model内[filePath] 对象为空");
        String suffix = FileUtil.getFileSuffixName(getModel().getFilePath());
        if (!suffix.equals(getModel().getVersion().name())) {
            throw new RuntimeException("文件后缀与声明的Excel版本不匹配");
        }

        for (String sheetName : getModel().getSheets().keySet()) {
            this.createSheet(getWorkbook(), sheetName, getModel().getSheets().get(sheetName));
        }
        super.writeExcel(getWorkbook(), getModel().getFilePath());
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
        SheetModel sheetModel = ExcelHelper.getSheetModelByList(dataRows, cls);
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
                        Map<Integer, Map<String, Object>> map = new HashMap<>();
                        for (int rower : sheetModel.getRows().keySet()) {
                            if (rower == sheetModel.getHeaderIndex()) {
                                rowIndex -= 1;
                                map.put(rower, sheetModel.getRows().get(rower));
                            } else {
                                map.put((rower + rowIndex), sheetModel.getRows().get(rower));
                            }
                        }
                        getModel().getSheets().put(sheetName, new SheetModel(sheetModel.getHeaderIndex(), map));
                    }
                }
            });
            exportExcel();
        }
    }

    /**
     * 创建Sheet
     *
     * @param wb        Workbook
     * @param sheetName Sheet Name
     * @param model     SheetModel
     */
    public void createSheet(Workbook wb, String sheetName, SheetModel model) {
        AssertUtil.notNull(wb, "wb 对象为空");
        AssertUtil.isNotBlank(sheetName, "sheetName 对象为空");
        AssertUtil.notNull(model, "model 对象为空");
        super.setWorkbook(wb);
        Sheet sheet = super.getWorkbook().getSheet(sheetName);
        if (null == sheet) {
            sheet = super.getWorkbook().createSheet(sheetName);
        }
        createRow(sheet, model.getHeaderIndex(), model.getRows());
        // 设置列宽自适应
        int columnCount = sheet.getRow(sheet.getLastRowNum()).getLastCellNum();
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }

    /**
     * 创建标题
     *
     * @param sheet    Sheet
     * @param rower    标题行标
     * @param dataRows 数据集
     */
    public void createHeader(Sheet sheet, int rower, Map<Integer, Map<String, Object>> dataRows) {
        AssertUtil.notNull(sheet, "sheet 对象为空");
        AssertUtil.notEmpty(dataRows, "dataRows 集合为空");
        Row row = sheet.createRow(rower);
        if (MapUtil.isNotEmpty(dataRows)) {
            // 获取标题集合
            Map<String, Object> header = dataRows.get(rower);
            if (null != header) {
                int cellIndex = 0;
                for (String key : header.keySet()) {
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
     * 创建指定标题行的row
     *
     * @param sheet    Sheet
     * @param rower    标题行标
     * @param dataRows 数据集
     */
    public void createRow(Sheet sheet, Integer rower, Map<Integer, Map<String, Object>> dataRows) {
        AssertUtil.notNull(sheet, "sheet 对象为空");
        AssertUtil.notEmpty(dataRows, "dataRows 集合为空");

        Set<String> keys = null;
        if (null != rower) {
            // 数据集合量越界
            if (dataRows.size() > MAX_ROW)
                throw new RuntimeException("数据行数超过[" + MAX_ROW + "]行，请调整后再次操作。");
            // 获取标题集合
            Map<String, Object> header = dataRows.get(rower);
            // 标题不为空，以标题序列为准
            if (null != header) keys = header.keySet();
        }
        boolean bool = false;
        // 如果sheet的行数大于0，表示是追加操作
        if (sheet.getLastRowNum() > 0) {
            bool = true;
        }
        //遍历数据
        for (Integer rowIndex : dataRows.keySet()) {
            // 如果是追加操作，不添加标题行
            if (bool && null != rower && rower.equals(rowIndex)) {
                continue;
            }
            Map<String, Object> data = dataRows.get(rowIndex);
            int cellIndex = 0;
            Row row = sheet.createRow(rowIndex);
            // 如果标题为空，以数据序列为准
            if (keys == null) keys = data.keySet();
            for (String key : keys) {
                Cell cell = row.createCell(cellIndex++);
                // 设置内容
                setCellValue(cell, data.get(key));
                // 设置样式
                if (null != rower && rower.equals(rowIndex)) {
                    cell.setCellStyle(style.getCellStyle(ExcelConstants.CellStyle.HeaderCell));
                } else {
                    setCellStyle(cell, data.get(key));
                }
            }
        }
    }

    /**
     * 创建row
     *
     * @param sheet    Sheet
     * @param dataRows 数据集合
     */
    public void createRow(Sheet sheet, Map<Integer, Map<String, Object>> dataRows) {
        this.createRow(sheet, null, dataRows);
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

    public void setModel(ExcelModel model) {
        this.model = model;
    }
}
