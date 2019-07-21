package excel;

import com.sfsctech.support.tools.excel.annotation.ExcelHeader;
import com.sfsctech.support.tools.excel.annotation.ExcelSheet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Class testModel
 *
 * @author 张麒 2016/5/6.
 * @version Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelSheet(name = "Sheet2")
public class testModel {

    @ExcelHeader(value = "是否离职", complexMapping = YesOrNo.class)
    private Boolean projectCode;
    @ExcelHeader(value = "是否派驻", complexMapping = YesOrNo.class)
    private Boolean projectName;
    @ExcelHeader("入职时间")
    private Date projectTime;
    @ExcelHeader("创建时间")
    private Date contractNumber;
    @ExcelHeader("创建人")
    private String contractCode;
    @ExcelHeader("更新时间")
    private Date contract;
    @ExcelHeader("更新人")
    private String signTime;
    @ExcelHeader("编制")
    private String newCode;

}
