package com.sfsctech.support.tools.excel.poi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sfsctech.support.common.util.DateUtil;
import com.sfsctech.support.common.util.*;
import com.sfsctech.support.tools.excel.annotation.ExcelHeader;
import com.sfsctech.support.tools.excel.annotation.ExcelSheet;
import com.sfsctech.support.tools.excel.constants.ExcelConstants;
import com.sfsctech.support.tools.excel.model.ExcelModel;
import com.sfsctech.support.tools.excel.model.SheetModel;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class ExcelHelper
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
public abstract class ExcelHelper {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Workbook workbook;

    public abstract ExcelModel getModel();

    protected abstract <T, S> T complexData(ExcelHeader excelHeader, S value);

    /**
     * 数据原型匹配sheet
     */
    protected void matchSheet() {
        ExcelModel excel = getModel();
        List<Class<?>> prototype = excel.getPrototype();
        AssertUtil.notEmpty(prototype, "ExcelModel内prototype数据原型集合为空");
        // 初始化
        for (Class<?> cls : prototype) {
            if (!cls.isAnnotationPresent(ExcelSheet.class)) {
                throw new IllegalArgumentException("Class [" + cls.getSimpleName() + "]没有配置注解[ExcelSheet]");
            }
            ExcelSheet excelSheet = cls.getAnnotation(ExcelSheet.class);
            // 遍历sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                // 根据sheet名称匹配数据原型
                if (sheet.getSheetName().equals(excelSheet.name())) {
                    excel.getPrototypeSheet().put(cls, excelSheet);
                }
            }
        }
    }


    /**
     * 读取标题
     *
     * @param row Row
     * @return List
     */
    protected Map<String, Integer> readHeader(Row row) {
        AssertUtil.notNull(row, "row 对象为空");
        Map<String, Integer> header = Maps.newLinkedHashMap();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            header.put(row.getCell(i).getRichStringCellValue().toString(), i);
        }
        return header;
    }

    /**
     * 校验Sheet
     */
    protected void validSheet() {
        ExcelModel excel = getModel();
        Workbook workbook = getWorkbook();
        if (workbook.getNumberOfSheets() != excel.getPrototype().size()) {
            throw new RuntimeException("ExcelModel内prototype数据原型集合size与Excel的sheet size不符，请调整后再次操作。");
        }
        matchSheet();
        // 遍历sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            boolean bool = true;
            Sheet sheet = workbook.getSheetAt(i);
            for (Class<?> cls : excel.getPrototypeSheet().keySet()) {
                // 根据sheet名称匹配数据原型
                if (sheet.getSheetName().equals(excel.getPrototypeSheet().get(cls).name())) {
                    bool = false;
                    break;
                }
            }
            if (bool) {
                throw new RuntimeException("在数据原型集合" + excel.getPrototype() + "中找不到sheet name [" + sheet.getSheetName() + "]");
            }
        }
        logger.info("Excel Sheet校验通过,sheet:[{}]", getModel().getPrototypeSheet().values());
    }

    /**
     * 校验Header
     *
     * @param sheet      Sheet
     * @param excelTitle Sheet的标题
     * @param dataTitle  数据原型的标题
     */
    protected void validHeader(Sheet sheet, Map<String, Integer> excelTitle, Map<Field, String> dataTitle) {
        AssertUtil.notEmpty(dataTitle, "sheetModel中rows标题行为空");
        // 验证长度是否匹配
        if (excelTitle.size() != dataTitle.size()) {
            throw new RuntimeException("[" + sheet.getSheetName() + "] Sheet的标题数与Model配置的标题数量不符");
        }
        List title = dataTitle.keySet().stream().map(Field::getName).collect(Collectors.toList());
        // 验证标题名称是否匹配
        if (!ListUtil.equals(Lists.newArrayList(excelTitle.keySet()), title)) {
            throw new RuntimeException("[" + sheet.getSheetName() + "] Sheet的标题名称与Model配置的标题名称不符");
        }
    }

    /**
     * 获取Workbook对象，自动获取对应版本
     *
     * @param model ExcelModel
     * @return Workbook
     * @throws IOException
     */
    protected Workbook createWorkbook(ExcelModel model) throws IOException {
        if (null != model.getInputStream()) {
            return WorkbookFactory.create(model.getInputStream());
        } else if (StringUtil.isNotBlank(model.getFilePath())) {
            File file = new File(model.getFilePath());
            if (file.isFile()) {
                return WorkbookFactory.create(new FileInputStream(file));
            }
        }
        if (ExcelConstants.ExcelVersion.xls.equals(model.getVersion()))
            return new HSSFWorkbook();
        else
            return new XSSFWorkbook();
    }

    /**
     * 通过Map&lt;String, Object&gt;设置Sheet的标题
     *
     * @param excelModel ExcelModel
     * @param header     标题集合&lt;Field，中文&gt;
     * @param sheetName  Excel Sheet name
     * @param rower      标题行标
     */
    protected void setSheetHeader(ExcelModel excelModel, LinkedHashMap<Field, String> header, String sheetName, int rower) {
        AssertUtil.notNull(excelModel, "excelModel 对象为空");
        AssertUtil.notNull(header, "header 对象为空");
        AssertUtil.notNull(sheetName, "sheetName 对象为空");
        // 创建SheetModel
        excelModel.getSheets().put(sheetName, SheetModel.builder().titleLine(rower).header(header).build());
    }

    /**
     * 通过Class&lt;T&gt;设置Sheet的标题
     *
     * @param excelModel ExcelModel
     * @param cls        映射Class，需要配置com.qi.common.excel.annotation.ExcelSheet
     * @param <T>        范型类
     */
    protected <T> void setSheetHeader(ExcelModel excelModel, Class<T> cls) {
        AssertUtil.notNull(excelModel, "excelModel 对象为空");
        AssertUtil.notNull(cls, "Class<T> 对象为空");
        ExcelSheet excelSheet = cls.getAnnotation(ExcelSheet.class);
        AssertUtil.notNull(excelSheet, "Class[" + cls.getSimpleName() + "]没有配置注解[ExcelSheet]");
        AssertUtil.isNotBlank(excelSheet.name(), "Class[" + cls.getSimpleName() + "]注解[ExcelSheet]中name参数为空");
        setSheetHeader(excelModel, getHeader(cls), excelSheet.name(), excelSheet.titleLine());
    }

    /**
     * 通过Class&lt;T&gt;设置数据行
     *
     * @param excelModel ExcelModel
     * @param dataRows   数据行
     * @param cls        映射Class，需要配置com.qi.common.excel.annotation.ExcelSheet
     * @param <T>        范型类
     */
    protected <T> void setSheetRows(ExcelModel excelModel, List<T> dataRows, Class<T> cls) {
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
    protected <T> SheetModel getSheetModelByList(List<T> dataRows, Class<T> cls) {
        AssertUtil.notNull(cls, "Class<T> 对象为空");
        AssertUtil.notEmpty(dataRows, "dataRows 集合为空");
        ExcelSheet excelSheet = cls.getAnnotation(ExcelSheet.class);
        AssertUtil.notNull(excelSheet, "Class[" + cls.getSimpleName() + "]没有配置注解[ExcelSheet]");
        SheetModel sheetModel = new SheetModel();
        sheetModel.setTitleLine(excelSheet.titleLine());
        Integer rower = 0;
        // 设置标题
        sheetModel.setHeader(getHeader(cls));
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
            if (sheetModel.getTitleLine().equals(rower)) {
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
    protected <T> List<T> getListBySheetModel(SheetModel sheetModel, Class<T> cls) {
        AssertUtil.notNull(sheetModel, "sheetModel 对象为空");
        AssertUtil.notEmpty(sheetModel.getRows(), "sheetModel内rows 集合为空");
        AssertUtil.notNull(cls, "Class<T> 对象为空");
        List<T> list = new ArrayList<>();
        sheetModel.getRows().forEach((index, value) -> {
            // 不读取标题
            if (!index.equals(sheetModel.getTitleLine())) {
                try {
                    T obj = cls.newInstance();
                    for (String field : value.keySet()) {
                        BeanUtil.setFieldValue(obj, field, value.get(field));
                    }
                    list.add(obj);
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
    protected <T> LinkedHashMap<Field, String> getHeader(Class<T> cls) {
        LinkedHashMap<Field, String> header = new LinkedHashMap<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            ExcelHeader excelHeader = field.getAnnotation(ExcelHeader.class);
            if (null != excelHeader) {
                header.put(field, excelHeader.value());
            }
        }
        AssertUtil.notEmpty(header, "Class [" + cls.getSimpleName() + "]没有配置注解[ExcelHeader]");
        logger.info("标题:" + header.values());
        return header;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    protected void setWorkbook(Workbook wb) {
        this.workbook = wb;
    }

    /**
     * 写入Excel文件
     *
     * @param workbook Workbook
     * @param path     文件路径
     * @throws IOException
     */
    public static void writeExcel(Workbook workbook, String path) throws IOException {
        AssertUtil.notNull(workbook, "workbook 对象为空");
        AssertUtil.isNotBlank(path, "path 对象为空");
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            workbook.write(os);
        } finally {
            FileUtil.close(os);
            workbook.close();
        }
    }


    /**
     * 写入Excel文件
     *
     * @param model ExcelModel
     * @throws IOException
     */
    protected void writeExcel(ExcelModel model) throws IOException {
        AssertUtil.notNull(workbook, "workbook 对象为空");
        AssertUtil.notNull(model, "model 对象为空");
        OutputStream os = null;
        if (StringUtil.isNotBlank(model.getFilePath())) {
            os = new FileOutputStream(model.getFilePath());
        } else if (null != model.getOutputStream()) {
            os = model.getOutputStream();
        }
        AssertUtil.notNull(os, "输出流对象为空");
        try {
            workbook.write(os);
        } finally {
            FileUtil.close(os);
            workbook.close();
        }
    }

    /**
     * 设置Cell的值
     *
     * @param cell  Cell
     * @param value Value
     */
    protected void setCellValue(Cell cell, Object value) {
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
    protected <T> T getCellValue(Field field, Cell cell) {
        AssertUtil.notNull(cell, "cell 对象为空");
        ExcelHeader excelHeader = field.getAnnotation(ExcelHeader.class);
        // 数字类型
        if (cell.getCellType().equals(CellType.NUMERIC)) {
            // 处理日期格式、时间格式
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                System.out.println(cell.getCellStyle().getDataFormat());
//                // 可以判断得到的Date是日期时间、日期还是时间，可以通过cell.getCellStyle().getDataFormat()来判断，这个返回值没有一个常量值来对应，我本机是excel2013，测试结果是日期时间(yyyy-MM-dd HH:mm:ss) - 22，日期(yyyy-MM-dd) - 14，时间(HH:mm:ss) - 21，年月(yyyy-MM) - 17，时分(HH:mm) - 20，月日(MM-dd) - 58
//                switch (cell.getCellStyle().getDataFormat()) {
//                    case 22:
//                        break;
//                    default:
//                }
//                if (cell.getCellStyle().getDataFormat() == 58) {
//                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
//                    double value = cell.getNumericCellValue();
//                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
//                    result = DateUtil.toDateDash(date);
//                } else {
//                    double value = cell.getNumericCellValue();
//                    CellStyle style = cell.getCellStyle();
//                    DecimalFormat format = new DecimalFormat();
//                    String temp = style.getDataFormatString();
//                    // 单元格设置成常规
//                    if (temp.equals("General")) {
//                        format.applyPattern("#");
//                    }
//                    result = format.format(value);
//                }
                if (!ExcelHeader.None.class.equals(excelHeader.complexMapping())) {
                    return (T) complexData(excelHeader, cell.getDateCellValue());
                } else if (Date.class.equals(field.getType())) {
                    return (T) cell.getDateCellValue();
                } else if (String.class.equals(field.getType())) {
                    return (T) DateUtil.toMSDateTimeDash(cell.getDateCellValue());
                }
            } else {
                double doubleVal = cell.getNumericCellValue();
                throw new IllegalArgumentException(String.valueOf(doubleVal));
//                long longVal = Math.round(cell.getNumericCellValue());
//                if (Double.parseDouble(longVal + ".0") == doubleVal)
//                    result = String.valueOf(longVal);
//                else
//                    result = String.valueOf(doubleVal);
            }
        }
        // String类型
        else if (cell.getCellType().equals(CellType.STRING)) {
            if (!ExcelHeader.None.class.equals(excelHeader.complexMapping())) {
                return (T) complexData(excelHeader, cell.getRichStringCellValue().toString());
            } else if (String.class.equals(field.getType())) {
                return (T) cell.getRichStringCellValue().toString();
            }
        }
        // 布尔类型
        else if (cell.getCellType().equals(CellType.BOOLEAN)) {
            if (Boolean.class.equals(field.getType())) {
                return (T) Boolean.valueOf(cell.getBooleanCellValue());
            } else if (String.class.equals(field.getType())) {
                return (T) cell.getRichStringCellValue().toString();
            }
        }
        // 表达式
        else if (cell.getCellType().equals(CellType.FORMULA) && String.class.equals(field.getType())) {
            return (T) cell.getCellFormula();
        }
        // 空类型
        else if (cell.getCellType().equals(CellType.BLANK)) {
            if (String.class.equals(field.getType())) {
                return (T) "";
            } else {
                return null;
            }
        }
        throw new IllegalArgumentException("Cell:" + cell.getRichStringCellValue() + ",与实体Field:" + field.getName() + "类型:" + field.getType() + "不匹配");
    }
}
