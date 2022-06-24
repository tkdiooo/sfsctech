package excel;

import com.sfsctech.support.tools.excel.annotation.ExcelHeader;
import com.sfsctech.support.tools.excel.annotation.ExcelSheet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class UserExcel
 *
 * @author 张麒 2019-7-18.
 * @version Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelSheet(name = "Sheet1", titleLine = 0)
public class UserExcel {

    @ExcelHeader("员工工号")
    private String number;
    @ExcelHeader("姓名")
    private String name;
    @ExcelHeader("性别")
    private String gender;
    @ExcelHeader("岗位")
    private String post;
    @ExcelHeader("部门")
    private String department;
    @ExcelHeader("手机")
    private String phone;
    @ExcelHeader("邮箱")
    private String email;

}
