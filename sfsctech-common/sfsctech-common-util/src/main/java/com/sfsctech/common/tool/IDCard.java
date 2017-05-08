package com.sfsctech.common.tool;

import com.sfsctech.common.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证验证
 *
 * @author 张麒 2016/4/12.
 * @version Description:
 */
public class IDCard {


    private String _codeError;

    private static IDCard idCard = null;

    private IDCard() {

    }

    public static IDCard getInstance() {
        if (null == idCard)
            synchronized (IDCard.class) {
                if (null == idCard) idCard = new IDCard();
            }
        return idCard;
    }

    //wi =2(n-1)(mod 11)
    final int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    // verify digit
    final int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};
    private int[] ai = new int[18];
    private static String[] _areaCode = {"11", "12", "13", "14", "15", "21", "22"
            , "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44"
            , "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};
    private static Map<String, Integer> dateMap;
    private static Map<String, String> areaCodeMap;

    static {
        dateMap = new HashMap<>();
        dateMap.put("01", 31);
        dateMap.put("02", null);
        dateMap.put("03", 31);
        dateMap.put("04", 30);
        dateMap.put("05", 31);
        dateMap.put("06", 30);
        dateMap.put("07", 31);
        dateMap.put("08", 31);
        dateMap.put("09", 30);
        dateMap.put("10", 31);
        dateMap.put("11", 30);
        dateMap.put("12", 31);
        areaCodeMap = new HashMap<>();
        for (String code : _areaCode) {
            areaCodeMap.put(code, null);
        }
    }

    /**
     * 验证身份证位数,15位和18位身份证
     *
     * @param idCard 身份证
     * @return Boolean
     */
    public boolean verifyLength(String idCard) {
        int length = idCard.length();
        if (length == 15 || length == 18) {
            return true;
        } else {
            _codeError = "错误：输入的身份证号不是15位和18位的";
            return false;
        }
    }

    /**
     * 判断地区码
     *
     * @param idCard 身份证
     * @return Boolean
     */
    public boolean verifyAreaCode(String idCard) {
        String areaCode = idCard.substring(0, 2);
//            Element child=  _areaCodeElement.getChild("_"+areaCode);
        if (areaCodeMap.containsKey(areaCode)) {
            return true;
        } else {
            _codeError = "错误：输入的身份证号的地区码(1-2位)[" + areaCode + "]不符合中国行政区划分代码规定(GB/T2260-1999)";
            return false;
        }
    }

    /**
     * 判断月份和日期
     *
     * @param idCard 身份证
     * @return Boolean
     */
    public boolean verifyBirthdayCode(String idCard) {
        //验证月份
        String month = idCard.substring(10, 12);
        boolean isEighteenCode = (18 == idCard.length());
        if (!dateMap.containsKey(month)) {
            _codeError = "错误：输入的身份证号" + (isEighteenCode ? "(11-12位)" : "(9-10位)") + "不存在[" + month + "]月份,不符合要求(GB/T7408)";
            return false;
        }
        //验证日期
        String dayCode = idCard.substring(12, 14);
        Integer day = dateMap.get(month);
        String yearCode = idCard.substring(6, 10);
        Integer year = Integer.valueOf(yearCode);

        //非2月的情况
        if (day != null) {
            if (Integer.valueOf(dayCode) > day || Integer.valueOf(dayCode) < 1) {
                _codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号不符合小月1-30天大月1-31天的规定(GB/T7408)";
                return false;
            }
        }
        //2月的情况
        else {
            //闰月的情况
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                if (Integer.valueOf(dayCode) > 29 || Integer.valueOf(dayCode) < 1) {
                    _codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号在" + year + "闰年的情况下未符合1-29号的规定(GB/T7408)";
                    return false;
                }
            }
            //非闰月的情况
            else {
                if (Integer.valueOf(dayCode) > 28 || Integer.valueOf(dayCode) < 1) {
                    _codeError = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号在" + year + "平年的情况下未符合1-28号的规定(GB/T7408)";
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证身份除了最后位其他的是否包含字母
     *
     * @param idCard
     * @return Boolean
     */
    public boolean containsAllNumber(String idCard) {
        String str = "";
        if (idCard.length() == 15) {
            str = idCard.substring(0, 15);
        } else if (idCard.length() == 18) {
            str = idCard.substring(0, 17);
        }
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (!(ch[i] >= '0' && ch[i] <= '9')) {
                _codeError = "错误：输入的身份证号第" + (i + 1) + "位包含字母";
                return false;
            }
        }
        return true;
    }

    /**
     * 获取错误信息
     *
     * @return String Error Message
     */
    public String getErrorCode() {
        return _codeError;
    }

    /**
     * 验证身份证
     *
     * @param idCard 身份证
     * @return boolean
     */
    public boolean verify(String idCard) {
        _codeError = "";
        //验证身份证位数,15位和18位身份证
        if (!verifyLength(idCard)) {
            return false;
        }
        //验证身份除了最后位其他的是否包含字母
        if (!containsAllNumber(idCard)) {
            return false;
        }

        //如果是15位的就转成18位的身份证
        String eighteenCard;
        if (idCard.length() == 15) {
            eighteenCard = upToEighteen(idCard);
        } else {
            eighteenCard = idCard;
        }
        //验证身份证的地区码
        if (!verifyAreaCode(eighteenCard)) {
            return false;
        }
        //判断月份和日期
        if (!verifyBirthdayCode(eighteenCard)) {
            return false;
        }
        //验证18位校验码,校验码采用ISO 7064：1983，MOD 11-2 校验码系统
        return verifyMOD(eighteenCard);
    }

    /**
     * 验证18位校验码,校验码采用ISO 7064：1983，MOD 11-2 校验码系统
     *
     * @param idCard 身份证
     * @return Boolean
     */
    public boolean verifyMOD(String idCard) {
        String verify = idCard.substring(17, 18);
        if ("x".equals(verify)) {
            idCard = idCard.replaceAll("x", "X");
            verify = "X";
        }
        String verifyIndex = getVerify(idCard);
        if (verify.equals(verifyIndex)) {
            return true;
        }
//            int x=17;
//            if(code.length()==15){
//                  x=14;
//            }
        _codeError = "错误：输入的身份证号最末尾的数字验证码错误";
        return false;
    }

    /**
     * 获得校验位
     *
     * @param idCard 身份证
     * @return String 校验位
     */
    public String getVerify(String idCard) {
        int remaining = 0;

        if (idCard.length() == 18) {
            idCard = idCard.substring(0, 17);
        }

        if (idCard.length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                String k = idCard.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }

            for (int i = 0; i < 17; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }

        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }

    /**
     * 15位身份证转18位身份证
     *
     * @param fifteenCard 15位身份证
     * @return String 18位身份证
     */
    public String upToEighteen(String fifteenCard) {
        String eightCard = fifteenCard.substring(0, 6);
        eightCard = eightCard + "19";
        eightCard = eightCard + fifteenCard.substring(6, 15);
        eightCard = eightCard + getVerify(eightCard);
        return eightCard;
    }

    /**
     * 从身份证获取出生日期
     *
     * @param idCard 已经校验合法的身份证号
     * @return Date yyyy-MM-dd 出生日期
     */
    public Date getBirthFromCard(String idCard) {
        String birthString = getBirthDateFromCard(idCard);
        return DateUtil.parseDate(birthString);

    }

    /**
     * 从身份证获取出生日期
     *
     * @param idCard 已经校验合法的身份证号
     * @return String yyyy-MM-dd 出生日期
     */
    public String getBirthDateFromCard(String idCard) {
        String card = idCard.trim();
        String year;
        String month;
        String day;
        if (card.length() == 18) { //处理18位身份证
            year = card.substring(6, 10);
            month = card.substring(10, 12);
            day = card.substring(12, 14);
        } else { //处理非18位身份证
            year = card.substring(6, 8);
            month = card.substring(8, 10);
            day = card.substring(10, 12);
            year = "19" + year;
        }
        if (month.length() == 1) {
            month = "0" + month; //补足两位
        }
        if (day.length() == 1) {
            day = "0" + day; //补足两位
        }
        return year + "-" + month + "-" + day;
    }

}
