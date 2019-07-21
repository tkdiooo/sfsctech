package excel;

import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.support.tools.excel.inf.ComplexDataMapping;

/**
 * Class YesOrNo
 *
 * @author 张麒 2019/7/21.
 * @version Description:
 */
public class YesOrNo implements ComplexDataMapping<Boolean, String> {

    @Override
    public Boolean mapping(String value) {
        return StatusConstants.YesNo.getKeyByValue(value);
    }
}
