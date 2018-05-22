package com.sfsctech.core.web.initialize;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.WebsiteConstants;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.web.properties.WebsiteProperties;
import com.sfsctech.support.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.stereotype.Component;

/**
 * Class PropertiesInitialize
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Component
public class PropertiesInitialize {

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private WebMvcProperties webMvcProperties;
    
    @Autowired
    private WebsiteProperties websiteProperties;


    private String getViewTemplate() {
        if (null != webMvcProperties && StringUtils.isNotBlank(webMvcProperties.getView().getSuffix())) {
            return LabelConstants.STAR + webMvcProperties.getView().getSuffix() + LabelConstants.COMMA;
        }
        return "";
    }

    @Autowired
    public void attribute(
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + "spring.mvc.static-path-pattern" + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String staticPath) {
        // 项目ContextPath
        WebsiteConstants.CONTEXT_PATH = serverProperties.getContextPath();
        // 过滤器排除 - 项目视图模板
        FilterHandler.addFilterExcludes(getViewTemplate());
        // 项目静态资源路径
        if (StringUtil.isNotBlank(staticPath)) {
            // CSRF防御拦截排除 - 项目静态资源
            FilterHandler.addCSRFExcludes(staticPath);
            // 过滤器排除 - 项目静态资源
            FilterHandler.addFilterExcludes(staticPath);
        }
    }
}
