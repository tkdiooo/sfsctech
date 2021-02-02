package com.sfsctech.core.base.json;

import com.sfsctech.core.base.constants.JsonConstants;
import java.lang.annotation.*;

/**
 * Class Desensitized
 *
 * @author 张麒 2021-2-2.
 * @version Description:
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Desensitized {

    //    脱敏类型(规则)
    JsonConstants.SensitiveType type();

}