package com.sfsctech.core.spring.initialize;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.WebsiteConstants;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class ApplicationInitialize
 *
 * @author 张麒 2018-7-26.
 * @version Description:
 */
@Component
public class ApplicationInitialize {

    private String active;

    @Autowired
    public void attribute(
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + "spring.profiles.active" + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }
}
