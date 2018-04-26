package com.sfsctech.common.util;

import java.util.Locale;

/**
 * Class ByteSizeValue
 *
 * @author 张麒 2017/3/30.
 * @version Description:
 */
public class ByteSizeUtil {

    public enum ByteSize {
        BYTES {
            @Override
            public long toBytes(long size) {
                return size;
            }

            @Override
            public long toKB(long size) {
                return size / (C1 / C0);
            }

            @Override
            public double toKBFrac(long size) {
                return ((double) toBytes(size)) / C1;
            }

            @Override
            public long toMB(long size) {
                return size / (C2 / C0);
            }

            @Override
            public double toMBFrac(long size) {
                return ((double) toBytes(size)) / C2;
            }

            @Override
            public long toGB(long size) {
                return size / (C3 / C0);
            }

            @Override
            public double toGBFrac(long size) {
                return ((double) toBytes(size)) / C3;
            }

            @Override
            public long toTB(long size) {
                return size / (C4 / C0);
            }

            @Override
            public double toTBFrac(long size) {
                return ((double) toBytes(size)) / C4;
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C0);
            }

            @Override
            public double toPBFrac(long size) {
                return ((double) toBytes(size)) / C5;
            }
        },

        KB {
            @Override
            public long toBytes(long size) {
                return x(size, C1 / C0, MAX / (C1 / C0));
            }

            @Override
            public long toKB(long size) {
                return size;
            }

            @Override
            public double toKBFrac(long size) {
                return ((double) toBytes(size)) / C1;
            }

            @Override
            public long toMB(long size) {
                return size / (C2 / C1);
            }

            @Override
            public double toMBFrac(long size) {
                return ((double) toBytes(size)) / C2;
            }

            @Override
            public long toGB(long size) {
                return size / (C3 / C1);
            }

            @Override
            public double toGBFrac(long size) {
                return ((double) toBytes(size)) / C3;
            }

            @Override
            public long toTB(long size) {
                return size / (C4 / C1);
            }

            @Override
            public double toTBFrac(long size) {
                return ((double) toBytes(size)) / C4;
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C1);
            }

            @Override
            public double toPBFrac(long size) {
                return ((double) toBytes(size)) / C5;
            }
        },

        MB {
            @Override
            public long toBytes(long size) {
                return x(size, C2 / C0, MAX / (C2 / C0));
            }

            @Override
            public long toKB(long size) {
                return x(size, C2 / C1, MAX / (C2 / C1));
            }

            @Override
            public double toKBFrac(long size) {
                return ((double) toBytes(size)) / C1;
            }

            @Override
            public long toMB(long size) {
                return size;
            }

            @Override
            public double toMBFrac(long size) {
                return ((double) toBytes(size)) / C2;
            }

            @Override
            public long toGB(long size) {
                return size / (C3 / C2);
            }

            @Override
            public double toGBFrac(long size) {
                return ((double) toBytes(size)) / C3;
            }

            @Override
            public long toTB(long size) {
                return size / (C4 / C2);
            }

            @Override
            public double toTBFrac(long size) {
                return ((double) toBytes(size)) / C4;
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C2);
            }

            @Override
            public double toPBFrac(long size) {
                return ((double) toBytes(size)) / C5;
            }
        },

        GB {
            @Override
            public long toBytes(long size) {
                return x(size, C3 / C0, MAX / (C3 / C0));
            }

            @Override
            public long toKB(long size) {
                return x(size, C3 / C1, MAX / (C3 / C1));
            }

            @Override
            public double toKBFrac(long size) {
                return ((double) toBytes(size)) / C1;
            }

            @Override
            public long toMB(long size) {
                return x(size, C3 / C2, MAX / (C3 / C2));
            }

            @Override
            public double toMBFrac(long size) {
                return ((double) toBytes(size)) / C2;
            }

            @Override
            public long toGB(long size) {
                return size;
            }

            @Override
            public double toGBFrac(long size) {
                return ((double) toBytes(size)) / C3;
            }

            @Override
            public long toTB(long size) {
                return size / (C4 / C3);
            }

            @Override
            public double toTBFrac(long size) {
                return ((double) toBytes(size)) / C4;
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C3);
            }

            @Override
            public double toPBFrac(long size) {
                return ((double) toBytes(size)) / C5;
            }
        },

        TB {
            @Override
            public long toBytes(long size) {
                return x(size, C4 / C0, MAX / (C4 / C0));
            }

            @Override
            public long toKB(long size) {
                return x(size, C4 / C1, MAX / (C4 / C1));
            }

            @Override
            public double toKBFrac(long size) {
                return ((double) toBytes(size)) / C1;
            }

            @Override
            public long toMB(long size) {
                return x(size, C4 / C2, MAX / (C4 / C2));
            }

            @Override
            public double toMBFrac(long size) {
                return ((double) toBytes(size)) / C2;
            }

            @Override
            public long toGB(long size) {
                return x(size, C4 / C3, MAX / (C4 / C3));
            }

            @Override
            public double toGBFrac(long size) {
                return ((double) toBytes(size)) / C3;
            }

            @Override
            public long toTB(long size) {
                return size;
            }

            @Override
            public double toTBFrac(long size) {
                return ((double) toBytes(size)) / C4;
            }

            @Override
            public long toPB(long size) {
                return size / (C5 / C4);
            }

            @Override
            public double toPBFrac(long size) {
                return ((double) toBytes(size)) / C5;
            }
        },

        PB {
            @Override
            public long toBytes(long size) {
                return x(size, C5 / C0, MAX / (C5 / C0));
            }

            @Override
            public long toKB(long size) {
                return x(size, C5 / C1, MAX / (C5 / C1));
            }

            @Override
            public double toKBFrac(long size) {
                return ((double) toBytes(size)) / C1;
            }

            @Override
            public long toMB(long size) {
                return x(size, C5 / C2, MAX / (C5 / C2));
            }

            @Override
            public double toMBFrac(long size) {
                return ((double) toBytes(size)) / C2;
            }

            @Override
            public long toGB(long size) {
                return x(size, C5 / C3, MAX / (C5 / C3));
            }

            @Override
            public double toGBFrac(long size) {
                return ((double) toBytes(size)) / C3;
            }

            @Override
            public long toTB(long size) {
                return x(size, C5 / C4, MAX / (C5 / C4));
            }

            @Override
            public double toTBFrac(long size) {
                return ((double) toBytes(size)) / C4;
            }

            @Override
            public long toPB(long size) {
                return size;
            }

            @Override
            public double toPBFrac(long size) {
                return ((double) toBytes(size)) / C5;
            }
        };

        static final long C0 = 1L;
        static final long C1 = C0 * 1024L;
        static final long C2 = C1 * 1024L;
        static final long C3 = C2 * 1024L;
        static final long C4 = C3 * 1024L;
        static final long C5 = C4 * 1024L;

        static final long MAX = Long.MAX_VALUE;

        /**
         * Scale d by m, checking for overflow.
         * This has a short name to make above code more readable.
         */
        static long x(long d, long m, long over) {
            if (d > over) return Long.MAX_VALUE;
            if (d < -over) return Long.MIN_VALUE;
            return d * m;
        }


        public abstract long toBytes(long size);

        public abstract long toKB(long size);

        public abstract double toKBFrac(long size);

        public abstract long toMB(long size);

        public abstract double toMBFrac(long size);

        public abstract long toGB(long size);

        public abstract double toGBFrac(long size);

        public abstract long toTB(long size);

        public abstract double toTBFrac(long size);

        public abstract long toPB(long size);

        public abstract double toPBFrac(long size);
    }

    public static ByteSize BYTES() {
        return ByteSize.BYTES;
    }

    public static ByteSize KB() {
        return ByteSize.KB;
    }

    public static ByteSize MB() {
        return ByteSize.MB;
    }

    public static ByteSize GB() {
        return ByteSize.GB;
    }

    public static ByteSize TB() {
        return ByteSize.TB;
    }

    public static ByteSize PB() {
        return ByteSize.PB;
    }

    public static Long parseBytesSize(String input) {
        return parseBytesSize(input, null);
    }

    public static Long parseBytesSize(String input, Long defaultValue) {
        if (StringUtil.isBlank(input)) {
            return defaultValue;
        }
        long bytes;
        String lastTwoChars = input.substring(input.length() - Math.min(2, input.length())).toLowerCase(Locale.ROOT);
        if (lastTwoChars.endsWith("k")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 1)) * ByteSize.C1);
        } else if (lastTwoChars.endsWith("kb")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 2)) * ByteSize.C1);
        } else if (lastTwoChars.endsWith("m")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 1)) * ByteSize.C2);
        } else if (lastTwoChars.endsWith("mb")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 2)) * ByteSize.C2);
        } else if (lastTwoChars.endsWith("g")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 1)) * ByteSize.C3);
        } else if (lastTwoChars.endsWith("gb")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 2)) * ByteSize.C3);
        } else if (lastTwoChars.endsWith("t")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 1)) * ByteSize.C4);
        } else if (lastTwoChars.endsWith("tb")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 2)) * ByteSize.C4);
        } else if (lastTwoChars.endsWith("p")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 1)) * ByteSize.C5);
        } else if (lastTwoChars.endsWith("pb")) {
            bytes = (long) (Double.parseDouble(input.substring(0, input.length() - 2)) * ByteSize.C5);
        } else if (lastTwoChars.endsWith("b")) {
            bytes = Long.parseLong(input.substring(0, input.length() - 1));
        } else {
            bytes = Long.parseLong(input);
        }
        return bytes;
    }
}
