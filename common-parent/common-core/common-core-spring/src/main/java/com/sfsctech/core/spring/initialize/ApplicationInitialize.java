package com.sfsctech.core.spring.initialize;

import com.sfsctech.core.base.constants.LabelConstants;
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
    private String appName;

    @Autowired
    public void attribute(
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + "spring.profiles.active" + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String active,
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + "spring.application.name" + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String appName) {
        this.active = active;
        this.appName = appName;
    }

    public String getActive() {
        return active;
    }

    public String getAppName() {
        return appName;
    }
}
