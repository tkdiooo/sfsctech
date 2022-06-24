package excel;

import com.google.common.collect.Lists;
import com.sfsctech.support.common.util.FileUtil;
import com.sfsctech.support.common.util.UUIDUtil;
import com.sfsctech.support.tools.excel.model.ExcelModel;
import com.sfsctech.support.tools.excel.poi.exports.ExcelExportHelper;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Class excelTest
 *
 * @author 张麒 2016/5/11.
 * @version Description:
 */
public class excelTest {


    @Test
    public void exportExcel() throws Exception {
        List<UserExcel> data = Lists.newArrayList();
        data.add(UserExcel.builder().name("abc").number("1").build());
        data.add(UserExcel.builder().name("abcd").number("2").build());
        data.add(UserExcel.builder().name("abcde").number("3").build());
        ExcelModel model = new ExcelModel("d:\\project.xlsx");
//        model.setSheets(sheets);
        ExcelExportHelper helper = new ExcelExportHelper(model);
        helper.bulidSheet(UserExcel.class, data);
        helper.exportExcel();
    }

    private void writeExcelByClass(List<testModel> list, List<testModel> list1) throws Exception {
        ExcelModel multi = new ExcelModel("d:\\multi.xlsx");
//        multi.getSheets().put("数据1", ExcelHelper.getSheetModelByList(list, testModel.class));
//        multi.getSheets().put("数据2", ExcelHelper.getSheetModelByList(list1, testModel.class));
        ExcelExportHelper helper = new ExcelExportHelper(multi);
        helper.exportExcel();


        ExcelModel single = new ExcelModel("d:\\single.xlsx");
//        ExcelHelper.setSheetRows(single, list, testModel.class);
        ExcelExportHelper helper1 = new ExcelExportHelper(single);
        helper1.exportExcel();
    }

    @Test
    public void importExcel() throws Exception {
        System.out.println(UUIDUtil.base58Uuid());
//        ExcelModel excelModel = new ExcelModel("D:\\user20190618.xls", Lists.newArrayList(UserExcel.class, testModel.class));
//        excelModel.setVerify(ExcelConstants.Verify.Weak);

//        LinkedHashMap<String, Object> header = ExcelHelper.getHeader(UserExcel.class);
//        ExcelHelper.setSheetHeader(excelModel, header, "项目数据", 0);

//        ExcelImportHelper helper = new ExcelImportHelper(excelModel);


//        helper.importExcel();
//        SheetModel<UserExcel> s = excelModel.getSheetModel(UserExcel.class);
//        System.out.println(s);
    }

    @Test
    public void importExcelByClass() throws Exception {
        ExcelModel excelModel = new ExcelModel("E:\\项目\\efesco\\SFSCTECH\\外服抽奖程序\\人事经理问卷抽奖白名单.xlsx");
//
//        ExcelHelper.setSheetHeader(excelModel, testModel.class);

//        Workbook wk = ExcelHelper.createWorkbook(excelModel);

//        Sheet sheet = wk.getSheetAt(0);

        StringBuilder sb = new StringBuilder("TRUNCATE wechat.wx_csr_real_user\r\n");
        sb.append("insert into wechat.wx_csr_real_user values");

//        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);
//            if (null != row && null != row.getCell(1)) {
//                sb.append(" ('" + row.getCell(1).toString().trim().replaceAll(" ", "") + "',''),\r\n");
//            }
//        }
        FileUtil.writeStringToFile(new File("E:\\项目\\efesco\\SFSCTECH\\外服抽奖程序\\人事经理问卷抽奖白名单.sql"), sb.toString(), "UTF-8");
        System.out.println(sb.toString());
    }
//
//        ExcelImportHelper helper = new ExcelImportHelper(excelModel);
//
//        helper.importExcel();
//
//        for (String key : excelModel.getSheets().keySet()) {
//            List<testModel> list = ExcelHelper.getListBySheetModel(excelModel.getSheets().get(key), testModel.class);
//            writeExcelByClass(list, list);
//        }
//}

    @Test
    public void appendExcel() throws Exception {
//        ExcelModel excelModel = new ExcelModel("d:\\multi.xlsx");
//        LinkedHashMap<String, Object> header = ExcelHelper.getHeader(testModel.class);
//        ExcelHelper.setSheetHeader(excelModel, header, "数据1", 0);
//        ExcelHelper.setSheetHeader(excelModel, header, "数据2", 0);
//        ExcelImportHelper helper = new ExcelImportHelper(excelModel);
//        helper.importExcel();
//
//        ExcelModel model = new ExcelModel("d:\\project.xlsx");
//        ExcelExportHelper exportHelper = new ExcelExportHelper(model);
//        List<testModel> list = ExcelHelper.getListBySheetModel(excelModel.getSheets().get("数据1"), testModel.class);
//        exportHelper.appendExcel(list, testModel.class);
//
//        ExcelModel model1 = new ExcelModel("d:\\project1.xlsx");
//        ExcelExportHelper eh = new ExcelExportHelper(model1);
//        eh.appendExcel(excelModel);
    }

    public static void main(String[] args) throws Exception {
        excelTest test = new excelTest();
        test.exportExcel();
    }
}
