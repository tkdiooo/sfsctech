package com.sfsctech.core.auth.base.sso.jwt.extractor;

import com.sfsctech.core.base.constants.LabelConstants;

/**
 *
 */
public interface TokenExtractor {
    String HEADER_PREFIX = "Bearer" + LabelConstants.PERIOD;

    String extract(String payload);
}
