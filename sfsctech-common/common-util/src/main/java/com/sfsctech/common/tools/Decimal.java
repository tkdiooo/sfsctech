package com.sfsctech.common.tools;

import java.math.BigDecimal;

/**
 * Class Decimal
 *
 * @author 张麒 2016/4/14.
 * @version Description:
 */
public class Decimal {

    public BigDecimal add(BigDecimal a, BigDecimal b) {
        if (null == a) a = BigDecimal.ZERO;
        if (null == b) b = BigDecimal.ZERO;
        return a.add(b);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (null == a) a = BigDecimal.ZERO;
        if (null == b) b = BigDecimal.ZERO;
        return a.subtract(b);
    }

    public BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        if (null == v1 || null == v2) return new BigDecimal(0);
        return v1.multiply(v2);
    }


    public BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }
}
