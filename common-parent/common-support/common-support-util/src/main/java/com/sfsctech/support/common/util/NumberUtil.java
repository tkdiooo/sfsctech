package com.sfsctech.support.common.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * Class NumberUtil
 *
 * @author 张麒 2016/4/3.
 * @version Description:
 */
public class NumberUtil extends NumberUtils {

    private final static String[] CN_Digits = {"零", "壹", "貳", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    /**
     * 将一组数字（不多于四个）转化成中文表示 <br/>
     *
     * @param partValue   字符串形式的数字
     * @param bInsertZero 是否在前面添加零
     * @return String
     */
    private static String Part2CN(String partValue, boolean bInsertZero) {
        // 使用正则表达式，去除前面的0
        partValue = partValue.replaceFirst("^0+", "");
        int len = partValue.length();
        if (len == 0)
            return "零";
        StringBuilder sbResult = new StringBuilder();
        int digit;
        String[] CN_Carry = new String[]{"", "拾", "佰", "仟"};
        for (int i = 0; i < len; i++) {
            digit = Integer.parseInt(partValue.substring(i, i + 1));
            if (digit != 0) {
                sbResult.append(CN_Digits[digit]);
                sbResult.append(CN_Carry[len - 1 - i]);
            } else {
                // 若不是最后一位，且下不位不为零，追加零
                if (i != len - 1 && Integer.parseInt(partValue.substring(i + 1, i + 2)) != 0)
                    sbResult.append("零");
            }
        }
        if (bInsertZero && len != 4)
            sbResult.insert(0, "零");
        return sbResult.toString();
    }

    /**
     * <pre>
     * 把中文数字转换为阿拉伯数字
     * NumberUtil.cnNumToInt("一万二千三百四十五") = 12345
     * </pre>
     *
     * @param number 中文数字
     * @return 阿拉伯数字
     */
    public static int cnNumToInt(String number) {
        int result = 0;
        AssertUtil.isNotBlank(number, "中文数字字符串为空");
        int yi = 1;// 记录高级单位
        int wan = 1;// 记录高级单位
        int ge = 1;// 记录单位

        for (int i = number.length() - 1; i >= 0; i--) {
            char c = number.charAt(i);
            int temp = 0;// 记录数值
            switch (c) {
                // 数值
                case '〇':
                case '零':
                    temp = 0;
                    break;
                case '一':
                    temp = ge * wan * yi;
                    ge = 1;
                    break;
                case '二':
                    temp = 2 * ge * wan * yi;
                    ge = 1;
                    break;
                case '三':
                    temp = 3 * ge * wan * yi;
                    ge = 1;
                    break;
                case '四':
                    temp = 4 * ge * wan * yi;
                    ge = 1;
                    break;
                case '五':
                    temp = 5 * ge * wan * yi;
                    ge = 1;
                    break;
                case '六':
                    temp = 6 * ge * wan * yi;
                    ge = 1;
                    break;
                case '七':
                    temp = 7 * ge * wan * yi;
                    ge = 1;
                    break;
                case '八':
                    temp = 8 * ge * wan * yi;
                    ge = 1;
                    break;
                case '九':
                    temp = 9 * ge * wan * yi;
                    ge = 1;
                    break;
                // 单位，前缀是单数字
                case '十':
                    ge = 10;
                    break;
                case '百':
                    ge = 100;
                    break;
                case '千':
                    ge = 1000;
                    break;
                // 高级单位，前缀可以是多个数字
                case '万':
                    wan = 10000;
                    ge = 1;
                    break;
                case '亿':
                    yi = 100000000;
                    wan = 1;
                    ge = 1;
                    break;
                default:
                    return -1;
            }
            result += temp;
        }
        if (ge > 1) {
            result += ge * wan * yi;
        }
        return result;
    }

    /**
     * <pre>
     * 把阿拉伯数字转为中文数字，返回值不会为null，也不会为空。
     * NumberUtil.intToCnNum(12345) = "一万二千三百四十五"
     * </pre>
     *
     * @param number 暂不支持负值
     * @return 中文数字
     */
    public static String intToCnNum(final int number) {
        StringBuilder sb = new StringBuilder();
        final String CN_NUM = "〇一二三四五六七八九";
        final String CN_W = "十百千";
        int n = number;
        for (int i = 0; n > 0; i++) {
            int g = n % 10;
            if ((i >= 1) && (i <= 3)) {
                if (g != 0) {
                    sb.insert(0, CN_W.charAt(i - 1));
                }
            } else if (i == 4) {
                sb.insert(0, '万');
            } else if ((i >= 5) && (i <= 7)) {
                if (g != 0) {
                    sb.insert(0, CN_W.charAt(i - 4 - 1));
                }
            } else if (i == 8) {
                sb.insert(0, '亿');
            } else if ((i >= 9) && (i <= 10)) {
                if (g != 0) {
                    sb.insert(0, CN_W.charAt(i - 8 - 1));
                }
            }
            if ((g != 0) || ((i > 0) && (sb.charAt(0) != '〇'))) {
                sb.insert(0, CN_NUM.charAt(g));
            }
            n = n / 10;
        }
        if ((sb.length() > 1) && (sb.charAt(sb.length() - 1) == '〇')) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }


    /**
     * <pre>
     * 将数字型货币转换为中文型货币
     * NumberUtil.convertCurrency("123456789") = "壹亿貳仟叁佰肆拾伍万陆仟柒佰捌拾玖元整"
     * </pre>
     *
     * @param money 数字金额，小数部分，将多于3位部分舍去，不做四舍五入
     * @return 中文货币
     */
    public static String convertCurrency(String money) {
        AssertUtil.isNotBlank(money, "数字金额字符串为空");
        // 使用正则表达式，去除前面的零及数字中的逗号
        String value = money.replaceFirst("^0+", "");
        value = value.replaceAll(",", "");
        // 分割小数部分与整数部分
        int dot_pos = value.indexOf('.');
        String int_value;
        String fraction_value;
        if (dot_pos == -1) {
            int_value = value;
            fraction_value = "00";
        } else {
            int_value = value.substring(0, dot_pos);
            fraction_value = value.substring(dot_pos + 1, value.length()) + "00".substring(0, 2);// 也加两个0，便于后面统一处理
        }

        int len = int_value.length();
        if (len > 16)
            return "值过大";
        StringBuilder cn_currency = new StringBuilder();
        String[] CN_Carry = new String[]{"", "万", "亿", "万"};
        // 数字分组处理，计数组数
        int cnt = len / 4 + (len % 4 == 0 ? 0 : 1);
        // 左边第一组的长度
        int partLen = len - (cnt - 1) * 4;
        String partValue;
        boolean bZero = false;// 有过零
        String curCN = null;
        for (int i = 0; i < cnt; i++) {
            partValue = int_value.substring(0, partLen);
            int_value = int_value.substring(partLen);
            curCN = Part2CN(partValue, i != 0 && !"零".equals(curCN));
            // 若上次为零，这次不为零，则加入零
            if (bZero && !"零".equals(curCN)) {
                cn_currency.append("零");
                bZero = false;
            }
            if ("零".equals(curCN))
                bZero = true;
            // 若数字不是零，加入中文数字及单位
            if (!"零".equals(curCN)) {
                cn_currency.append(curCN);
                cn_currency.append(CN_Carry[cnt - 1 - i]);
            }
            // 除最左边一组长度不定外，其它长度都为4
            partLen = 4;
        }
        cn_currency.append("元");
        // 处理小数部分
        int fv1 = Integer.parseInt(fraction_value.substring(0, 1));
        int fv2 = Integer.parseInt(fraction_value.substring(1, 2));
        if (fv1 + fv2 == 0) {
            cn_currency.append("整");
        } else {
            cn_currency.append(CN_Digits[fv1]).append("角");
            cn_currency.append(CN_Digits[fv2]).append("分");
        }
        return cn_currency.toString();
    }

    /**
     * <pre>
     * 二进制转十六进制字符串
     * </pre>
     *
     * @param b 二进制
     * @return 十六进制
     */
    public static String toHex(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            sb.append(Character.forDigit((aB & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(aB & 0x0f, 16));
        }
        return sb.toString();
    }

    /**
     * <pre>
     * 十六进制转二进制
     * </pre>
     *
     * @param b 十六进制
     * @return 二进制
     */
    public static byte[] toByte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 隐藏数值
     *
     * @param numeric 数值
     * @param start   起始位
     * @param scope   替换位数
     * @return numeric
     */
    public static String hiddenNumber(final String numeric, int start, int scope) {
        AssertUtil.isNotBlank(numeric, "数值为空");
        if ((start + scope) >= numeric.length()) {
            return numeric;
        }
        String replace = String.format("%0" + scope + "d", 0).replace("0", "*");
        return numeric.substring(0, start) + replace + numeric.substring((start + scope), numeric.length());
    }

    /**
     * 数字类型转换
     *
     * @param number
     * @param pattern
     * @param <Pattern>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <Pattern> Pattern convertNumber(Number number, Pattern pattern) {
        if (null == number) {
            return null;
        } else if (pattern instanceof String) {
            return (Pattern) String.valueOf(number);
        } else if (pattern instanceof Integer) {
            return (Pattern) Integer.valueOf(number.toString());
        } else if (pattern instanceof Double) {
            return (Pattern) Double.valueOf(number.toString());
        } else if (pattern instanceof Float) {
            return (Pattern) Float.valueOf(number.toString());
        } else if (pattern instanceof Long) {
            return (Pattern) Long.valueOf(number.toString());
        } else if (pattern instanceof BigDecimal) {
            return (Pattern) new BigDecimal(number.toString());
        } else {
            throw new RuntimeException("找不到匹配的数据类型");
        }
    }
}
