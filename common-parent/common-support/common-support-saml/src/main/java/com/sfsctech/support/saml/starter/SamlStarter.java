package com.sfsctech.support.saml.starter;

import com.sfsctech.support.saml.config.SamlConfig;
import com.sfsctech.support.saml.properties.SpPropertyies;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Class SamlStarter
 *
 * @author 张麒 2020-7-17.
 * @version Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SamlConfig.class, SpPropertyies.class})
public @interface SamlStarter {
}
