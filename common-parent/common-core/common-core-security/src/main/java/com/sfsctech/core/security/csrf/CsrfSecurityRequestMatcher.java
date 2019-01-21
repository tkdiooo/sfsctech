package com.sfsctech.core.security.csrf;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Class CsrfSecurityRequestMatcher
 *
 * @author 张麒 2019-1-21.
 * @version Description:
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    private RegexRequestMatcher unprotectedMatcher = new RegexRequestMatcher("/unprotected", null);

    @Override
    public boolean matches(HttpServletRequest request) {
        if (allowedMethods.matcher(request.getMethod()).matches()) {
            return false;
        }

        return !unprotectedMatcher.matches(request);
    }
}
