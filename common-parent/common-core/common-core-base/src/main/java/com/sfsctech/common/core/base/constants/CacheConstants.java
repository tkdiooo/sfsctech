package com.sfsctech.common.core.base.constants;

/**
 * Class CacheConstants
 *
 * @author 张麒 2018-1-16.
 * @version Description:
 */
public class CacheConstants {

    public enum Second {

        Minutes5(60 * 5),
        Minutes10(60 * 10),
        Minutes30(60 * 30),
        Hour1(60 * 60),
        Hour6(60 * 60 * 6),
        Hour12(60 * 60 * 12),
        Day1(60 * 60 * 24),
        Day7(60 * 60 * 24 * 7);

        Second(Integer value) {
            this.value = value;
        }

        private Integer value;

        public Integer getContent() {
            return value;
        }

    }

    public enum MilliSecond {

        Minutes5(60 * 5 * 1000),
        Minutes10(60 * 10 * 1000),
        Minutes30(60 * 30 * 1000),
        Hour1(60 * 60 * 1000),
        Hour6(60 * 60 * 6 * 1000),
        Hour12(60 * 60 * 12 * 1000),
        Day1(60 * 60 * 24 * 1000),
        Day7(60 * 60 * 24 * 7 * 1000);

        MilliSecond(Integer value) {
            this.value = value;
        }

        private Integer value;

        public Integer getContent() {
            return value;
        }

    }
}
