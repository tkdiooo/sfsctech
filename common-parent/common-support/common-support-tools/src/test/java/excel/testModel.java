package excel;

import com.sfsctech.support.tools.excel.annotation.ExcelHeader;
import com.sfsctech.support.tools.excel.annotation.ExcelSheet;

/**
 * Class testModel
 *
 * @author 张麒 2016/5/6.
 * @version Description:
 */
@ExcelSheet(name = "项目数据")
public class testModel {

    @ExcelHeader("项目编号")
    private String projectCode;
    @ExcelHeader("项目名称")
    private String projectName;
    @ExcelHeader("立项时间")
    private String projectTime;
    @ExcelHeader("合同流水号")
    private String contractNumber;
    @ExcelHeader("销售合同编号")
    private String contractCode;
    @ExcelHeader("合同名称")
    private String contract;
    @ExcelHeader("合同签署时间")
    private String signTime;
    @ExcelHeader("新项目号")
    private String newCode;
    @ExcelHeader("新项目名")
    private String newName;


    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectTime() {
        return projectTime;
    }

    public void setProjectTime(String projectTime) {
        this.projectTime = projectTime;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
