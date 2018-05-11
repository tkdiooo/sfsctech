package com.sfsctech.common.support.excel.poi;

import com.sfsctech.common.support.excel.annotation.ExcelHeader;
import com.sfsctech.common.support.excel.annotation.ExcelSheet;
import com.sfsctech.common.support.excel.constants.ExcelConstants;
import com.sfsctech.common.support.excel.model.ExcelModel;
import com.sfsctech.common.support.excel.model.SheetModel;
import com.sfsctech.common.support.util.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Class ExcelHelper
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
public abstract class ExcelHelper {

    private Workbook workbook;

    public abstract ExcelModel getModel();

    /**
     * 读取标题
     *
     * @param row Row
     * @return List
     */
    protected List<String> readHeader(Row row) {
        AssertUtil.notNull(row, "row 对象为空");
        List<String> header = new ArrayList<>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            header.add(getCellValue(row.getCell(i)));
        }
        return header;
    }

    /**
     * 校验Sheet
     */
    protected void validSheet() {
        Map<String, SheetModel> sheets = getModel().getSheets();
        AssertUtil.notEmpty(sheets, "ExcelModel内sheets 集合为空");
        Workbook workbook = getWorkbook();
        if (workbook.getNumberOfSheets() != sheets.size()) {
            throw new RuntimeException("ExcelModel内sheets对象size与Model配置的sheet size不符，请调整后再次操作。");
        }
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (!sheets.containsKey(sheet.getSheetName().trim())) {
                throw new RuntimeException("ExcelModel内sheets对象不包含sheet name [" + sheet.getSheetName() + "]");
            }
        }
    }


    /**
     * 校验Header
     *
     * @param sheet      Sheet
     * @param sheetModel SheetModel
     */
    protected void validHeader(Sheet sheet, SheetModel sheetModel) {
        Map<Integer, Map<String, Object>> rows = sheetModel.getRows();
        // 读取标题：标题显示中文名称
        List<String> header = readHeader(sheet.getRow(sheetModel.getHeaderIndex()));
        // 获取验证标题：{key : 实体属性名称,value : 标题显示中文名称}
        Map<String, Object> verify = rows.get(sheetModel.getHeaderIndex());
        AssertUtil.notEmpty(verify, "sheetModel中rows标题行为空");
        // 验证长度是否匹配
        if (header.size() != verify.size()) {
            throw new RuntimeException("[" + sheet.getSheetName() + "] Sheet的标题数与Model配置的标题数量不符");
        }
        if (!ListUtil.equals(header, ListUtil.toList(verify))) {
            throw new RuntimeException("[" + sheet.getSheetName() + "] Sheet的标题名称与Model配置的标题名称不符");
        }
    }

    /**
     * 获取不包含文件流的Workbook对象，根据枚举ExcelVersion生成对应版本
     * <p>
     * //     * @param ev ExcelVersion
     *
     * @return Workbook
     * @throws IOException
     */
    public static Workbook createWorkbook(ExcelConstants.ExcelVersion ev) throws IOException {
        if (ExcelConstants.ExcelVersion.xls.equals(ev))
            return new HSSFWorkbook();
        else
            return new XSSFWorkbook();

    }

    /**
     * 获取Workbook对象，自动获取对应版本
     *
     * @param path 文件路径
     * @return Workbook
     * @throws IOException
     */
    public static Workbook createWorkbook(String path) throws IOException, InvalidFormatException {
        AssertUtil.isNotBlank(path, "path 对象为空");
        File file = new File(path);
        AssertUtil.isFile(file, "path文件对象不存在");
//        if (isExcel2003(new FileInputStream(file))) {
        return WorkbookFactory.create(new FileInputStream(file));
//        } else {
//            return new XSSFWorkbook(new FileInputStream(file));
//        }
    }

    /**
     * 通过Map&lt;String, Object&gt;设置Sheet的标题
     *
     * @param excelModel ExcelModel
     * @param header     标题集合&lt;英文，中文&gt;
     * @param sheetName  Excel Sheet name
     * @param rower      标题行标
     */
    public static void setSheetHeader(ExcelModel excelModel, Map<String, Object> header, String sheetName, int rower) {
        AssertUtil.notNull(excelModel, "excelModel 对象为空");
        AssertUtil.notNull(header, "header 对象为空");
        AssertUtil.notNull(sheetName, "sheetName 对象为空");
        // 创建SheetModel
        SheetModel sheetModel = new SheetModel(rower, new HashMap<>());
        sheetModel.getRows().put(sheetModel.getHeaderIndex(), header);
        excelModel.getSheets().put(sheetName, sheetModel);
    }

    /**
     * 通过Class&lt;T&gt;设置Sheet的标题
     *
     * @param excelModel ExcelModel
     * @param cls        映射Class，需要配置com.qi.common.excel.annotation.ExcelSheet
     * @param <T>        范型类
     */
    public static <T> void setSheetHeader(ExcelModel excelModel, Class<T> cls) {
        AssertUtil.notNull(excelModel, "excelModel 对象为空");
        AssertUtil.notNull(cls, "Class<T> 对象为空");
        ExcelSheet excelSheet = cls.getAnnotation(ExcelSheet.class);
        AssertUtil.notNull(excelSheet, "Class[" + cls.getSimpleName() + "]没有配置注解[ExcelSheet]");
        AssertUtil.isNotBlank(excelSheet.name(), "Class[" + cls.getSimpleName() + "]注解[ExcelSheet]中name参数为空");
        setSheetHeader(excelModel, getHeader(cls), excelSheet.name(), excelSheet.rower());
    }

    /**
     * 通过Class&lt;T&gt;设置数据行
     *
     * @param excelModel ExcelModel
     * @param dataRows   数据行
     * @param cls        映射Class，需要配置com.qi.common.excel.annotation.ExcelSheet
     * @param <T>        范型类
     */
    public static <T> void setSheetRows(ExcelModel excelModel, List<T> dataRows, Class<T> cls) {
        AssertUtil.notNull(excelModel, "excelModel 对象为空");
        ExcelSheet excelSheet = cls.getAnnotation(ExcelSheet.class);
        AssertUtil.notNull(excelSheet, "Class[" + cls.getSimpleName() + "]没有配置注解[ExcelSheet]");
        AssertUtil.isNotBlank(excelSheet.name(), "Class[" + cls.getSimpleName() + "]注解[ExcelSheet]中name参数为空");
        // 获取sheetModel
        SheetModel sheetModel = getSheetModelByList(dataRows, cls);
        excelModel.getSheets().put(excelSheet.name(), sheetModel);
    }

    /**
     * 通过Class&lt;T&gt;转换List&lt;T&gt;集合对象为SheetModel对象
     *
     * @param dataRows 数据集合
     * @param cls      映射Class，需要配置com.qi.common.excel.annotation.ExcelSheet
     * @param <T>      范型类
     */
    public static <T> SheetModel getSheetModelByList(List<T> dataRows, Class<T> cls) {
        AssertUtil.notNull(cls, "Class<T> 对象为空");
        AssertUtil.notEmpty(dataRows, "dataRows 集合为空");
        ExcelSheet excelSheet = cls.getAnnotation(ExcelSheet.class);
        AssertUtil.notNull(excelSheet, "Class[" + cls.getSimpleName() + "]没有配置注解[ExcelSheet]");
        SheetModel sheetModel = new SheetModel(excelSheet.rower(), new HashMap<>());
        Integer rower = 0;
        // 设置标题
        sheetModel.getRows().put(sheetModel.getHeaderIndex(), getHeader(cls));
        // 设置数据行
        for (T t : dataRows) {
            Map<String, Object> map = new HashMap<>();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                ExcelHeader excelHeader = field.getAnnotation(ExcelHeader.class);
                if (null != excelHeader) {
                    map.put(field.getName(), BeanUtil.getPropertyValue(t, field.getName()));
                }
            }
            // 如果标题行标等于当前行标，当前行标+1
            if (sheetModel.getHeaderIndex().equals(rower)) {
                rower++;
            }
            sheetModel.getRows().put(rower++, map);
        }
        return sheetModel;
    }

    /**
     * 通过Class&lt;T&gt;转换SheetModel对象为List&lt;T&gt;集合对象
     *
     * @param sheetModel SheetModel
     * @param cls        映射Class
     * @param <T>        范型类
     * @return List&lt;T&gt;
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getListBySheetModel(SheetModel sheetModel, Class<T> cls) {
        AssertUtil.notNull(sheetModel, "sheetModel 对象为空");
        AssertUtil.notEmpty(sheetModel.getRows(), "sheetModel内rows 集合为空");
        AssertUtil.notNull(cls, "Class<T> 对象为空");
        List<T> list = new ArrayList<>();
        sheetModel.getRows().forEach((key, value) -> {
            // 不读取标题
            if (!key.equals(sheetModel.getHeaderIndex())) {
                try {
                    Object obj = cls.newInstance();
                    for (String field : value.keySet()) {
                        BeanUtil.setProperty(obj, field, value.get(field));
                    }
                    list.add((T) obj);
                } catch (Exception e) {
                    throw new RuntimeException(("Class [" + cls.getSimpleName() + "] newInstance error :" + ThrowableUtil.getRootMessage(e)));
                }
            }
        });
        return list;
    }

    /**
     * 通过Class&lt;T&gt;获取标题集合
     *
     * @param cls 映射Class，需要配置com.qi.common.excel.annotation.ExcelHeader
     * @param <T> 范型类
     * @return LinkedHashMap&lt;String, Object&gt;
     */
    public static <T> LinkedHashMap<String, Object> getHeader(Class<T> cls) {
        LinkedHashMap<String, Object> header = new LinkedHashMap<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            ExcelHeader excelHeader = field.getAnnotation(ExcelHeader.class);
            if (null != excelHeader) {
                header.put(field.getName(), excelHeader.value());
            }
        }
        AssertUtil.notEmpty(header, "Class [" + cls.getSimpleName() + "]没有配置注解[ExcelHeader]");
        return header;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(Workbook wb) {
        this.workbook = wb;
    }

    /**
     * 写入Excel文件
     *
     * @param workbook Workbook
     * @param path     文件路径
     * @throws IOException
     */
    public void writeExcel(Workbook workbook, String path) throws IOException {
        AssertUtil.notNull(workbook, "workbook 对象为空");
        AssertUtil.isNotBlank(path, "path 对象为空");
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            workbook.write(os);
        } finally {
            FileUtil.close(os);
        }
    }

    /**
     * 设置Cell的值
     *
     * @param cell  Cell
     * @param value Value
     */
    public void setCellValue(Cell cell, Object value) {
        AssertUtil.notNull(cell, "cell 对象为空");
        if (null == value) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue(((String) value).trim());
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(value.toString());
        } else {
            throw new RuntimeException("找不到匹配的数据类型");
        }
    }

    /**
     * 获取Cell的值
     *
     * @param cell Cell
     * @return Cell Value
     */
    public String getCellValue(Cell cell) {
        AssertUtil.notNull(cell, "cell 对象为空");
        String result;
        switch (cell.getCellType()) {
            // 数字类型
            case HSSFCell.CELL_TYPE_NUMERIC:
                // 处理日期格式、时间格式
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                    可以判断得到的Date是日期时间、日期还是时间，可以通过cell.getCellStyle().getDataFormat()来判断，这个返回值没有一个常量值来对应，我本机是excel2013，测试结果是日期时间(yyyy-MM-dd HH:mm:ss) - 22，日期(yyyy-MM-dd) - 14，时间(HH:mm:ss) - 21，年月(yyyy-MM) - 17，时分(HH:mm) - 20，月日(MM-dd) - 58
//                    switch (cell.getCellStyle().getDataFormat()) {
//                        case 22:
//                            break;
//                        default:
//                    }
//                    if (cell.getCellStyle().getDataFormat() == 58) {
//                        // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
//                        double value = cell.getNumericCellValue();
//                        Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
//                        result = DateUtil.parse3String(date, "yyyy-MM-dd");
//                    } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#");
                    }
                    result = format.format(value);
//                    }
//                    Date date = cell.getDateCellValue();
//                    result = DateUtil.toDateTime(date);
                } else {
                    double doubleVal = cell.getNumericCellValue();
                    long longVal = Math.round(cell.getNumericCellValue());
                    if (Double.parseDouble(longVal + ".0") == doubleVal)
                        result = String.valueOf(longVal);
                    else
                        result = String.valueOf(doubleVal);
                }
                break;
            // String类型
            case HSSFCell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().toString();
                break;
            // 布尔类型
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            // 表达式
            case HSSFCell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            // 空类型
            case HSSFCell.CELL_TYPE_BLANK:
                result = null;
                break;
            default:
                result = "";
                break;
        }
        if (StringUtil.isNotBlank(result)) return result.trim();
        else return result;
    }

}
