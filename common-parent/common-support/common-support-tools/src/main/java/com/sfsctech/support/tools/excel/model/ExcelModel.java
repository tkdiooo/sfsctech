package com.sfsctech.support.tools.excel.model;

import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.support.tools.excel.annotation.ExcelSheet;
import com.sfsctech.support.tools.excel.constants.ExcelConstants;
import com.sfsctech.support.tools.excel.poi.style.CellStyles;
import com.sfsctech.support.tools.excel.poi.style.DefaultCellStyle;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Class ExcelModel
 *
 * @author 张麒 2016/4/17.
 * @version Description:
 */
public class ExcelModel extends BaseDto {

    private static final long serialVersionUID = -8402771858776062521L;

    private ExcelConstants.ExcelVersion version = ExcelConstants.ExcelVersion.xlsx;

    private ExcelConstants.Verify verify = ExcelConstants.Verify.Strong;

    private String filePath;

    private InputStream inputStream;

    private OutputStream outputStream;

    /**
     * Sheet对象
     * String sheet名称
     */
    private Map<String, SheetModel> sheets = new LinkedHashMap<>();

    /**
     * 数据原型
     */
    private List<Class<?>> prototype;

    /**
     * 数据原型和ExcelSheet注解映射（Class：数据原型）
     */
    private Map<Class<?>, ExcelSheet> prototypeSheet = new HashMap<>();

    private CellStyles style = new DefaultCellStyle();

    public ExcelModel(String filePath) {
        this.filePath = filePath;
        this.prototype = null;
    }

    public ExcelModel(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ExcelModel(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ExcelModel(InputStream inputStream, List<Class<?>> prototype) {
        this.prototype = prototype;
    }

    public SheetModel getSheetModel(Class<?> cls) {
        return sheets.get(prototypeSheet.get(cls).name());
    }

    public ExcelConstants.ExcelVersion getVersion() {
        return version;
    }

    public void setVersion(ExcelConstants.ExcelVersion version) {
        this.version = version;
    }


    public String getFilePath() {
        return filePath;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public List<Class<?>> getPrototype() {
        return prototype;
    }

    public Map<Class<?>, ExcelSheet> getPrototypeSheet() {
        return prototypeSheet;
    }

    public CellStyles getStyle() {
        return style;
    }

    public void setStyle(CellStyles style) {
        this.style = style;
    }

    public Map<String, SheetModel> getSheets() {
        return sheets;
    }

    public ExcelConstants.Verify getVerify() {
        return verify;
    }

    public void setVerify(ExcelConstants.Verify verify) {
        this.verify = verify;
    }

    public void setSheets(Map<String, SheetModel> sheets) {
        this.sheets = sheets;
    }

}
