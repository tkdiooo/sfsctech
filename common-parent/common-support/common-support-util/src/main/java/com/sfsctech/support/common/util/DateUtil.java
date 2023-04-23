/**
 *
 */
package com.sfsctech.support.common.util;

import com.sfsctech.core.base.constants.DateConstants;
import com.sfsctech.core.base.constants.DateConstants.Weeks;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class DateUtil
 *
 * @author 张麒 2016年3月28日
 * @version Description：日期工具类
 */
public class DateUtil extends DateUtils {


    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static Date getTimeMillisDate(Long timeMillis) {
        return new Date(timeMillis);
    }

    /**
     * 获取时区时间
     *
     * @return Date
     */
    public static Date getTimeZoneDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取系统时间
     *
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取年份
     *
     * @param date Date
     * @return Year
     */
    public static Integer getYears(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取季度
     *
     * @param date Date
     * @return Quarter
     */
    public static Integer getQuarter(Date date) {
        int month = getMonth(date);
        // 第一季度
        if (month <= Calendar.APRIL) {
            return 1;
        }
        // 第二季度
        else if (month <= Calendar.JULY) {
            return 2;
        }
        // 第三季度
        else if (month <= Calendar.OCTOBER) {
            return 3;
        }
        // 第四季度
        else {
            return 4;
        }
    }

    /**
     * 获取月份
     *
     * @param date Date
     * @return Month
     */
    public static Integer getMonth(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static Integer getDayOfYear(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        return c.get(Calendar.DAY_OF_YEAR);
    }

    public static Integer getDayOfMonth(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getDayOfWeek(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static Integer getHours(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static Integer getMinute(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        return c.get(Calendar.MINUTE);
    }

    public static Integer getSeconds(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 返回日期的星期中文字符
     *
     * @param date Date
     * @return Week
     */
    public static String getWeekChineseName(Date date) {
        AssertUtil.notNull(date, "date is null");

        int w = getDayOfWeek(date) + 1;
        return Weeks.getValueByKey(w);
    }

    /**
     * 将时间转化为特定格式的字符串
     *
     * @param date    Date
     * @param pattern Pattern
     * @return Date Pattern
     */
    public static String format(Date date, String pattern) {
        AssertUtil.notNull(date, "date is null");

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyyMMddHHmmss
     */
    public static String toDateTime(Date date) {
        return format(date, DateConstants.PATTERN_DEFAULT);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toDateTimeDash(Date date) {
        return format(date, DateConstants.PATTERN_DEFAULT_DASH);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy/MM/dd HH:mm:ss
     */
    public static String toDateTimeSlash(Date date) {
        return format(date, DateConstants.PATTERN_DEFAULT_SLASH);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy年MM月dd日 HH时mm分ss秒
     */
    public static String toDateTimeCN(Date date) {
        return format(date, DateConstants.PATTERN_DEFAULT_CN);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyyMMdd
     */
    public static String toDate(Date date) {
        return format(date, DateConstants.PATTERN_YMD);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy-MM-dd
     */
    public static String toDateDash(Date date) {
        return format(date, DateConstants.PATTERN_YMD_DASH);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy/MM/dd
     */
    public static String toDateSlash(Date date) {
        return format(date, DateConstants.PATTERN_YMD_SLASH);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy年MM月dd日
     */
    public static String toDateCN(Date date) {
        return format(date, DateConstants.PATTERN_YMD_CN);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return HH:mm:ss
     */
    public static String toTime(Date date) {
        return format(date, DateConstants.PATTERN_HMSM);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return HH时mm分ss秒
     */
    public static String toTimeCN(Date date) {
        return format(date, DateConstants.PATTERN_HMSM_CN);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return HH:mm
     */
    public static String toHours(Date date) {
        return format(date, DateConstants.PATTERN_HM);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return HH时mm分
     */
    public static String toHoursCN(Date date) {
        return format(date, DateConstants.PATTERN_HM_CN);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyyMMddHHmmssSSS
     */
    public static String toMSDateTime(Date date) {
        return format(date, DateConstants.PATTERN_FULL);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String toMSDateTimeDash(Date date) {
        return format(date, DateConstants.PATTERN_FULL_DASH);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy/MM/dd HH:mm:ss.SSS
     */
    public static String toMSDateTimeSlash(Date date) {
        return format(date, DateConstants.PATTERN_FULL_SLASH);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return yyyy年MM月dd日 HH时mm分ss秒SSS毫秒
     */
    public static String toMSDateTimeCN(Date date) {
        return format(date, DateConstants.PATTERN_FULL_CN);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return HH:mm:ss.SSS
     */
    public static String toMSTime(Date date) {
        return format(date, DateConstants.PATTERN_HMSSM);
    }

    /**
     * 格式化日期
     *
     * @param date Date
     * @return HH时mm分ss秒SSS毫秒
     */
    public static String toMSTimeCN(Date date) {
        return format(date, DateConstants.PATTERN_HMSSM_CN);
    }

    /**
     * 设置日期，时间位全部为0
     *
     * @param date 2000-01-01 12:30:30.999
     * @return 2000-01-01 00:00:00.000
     */
    public static Date setDate(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c = toCalendar(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 解析日期
     *
     * @param date    Date
     * @param pattern Pattern
     * @return java.tool.Date：pattern
     */
    public static Date parse(String date, String pattern) {
        try {
            return parseDate(date, pattern);
        } catch (Exception e) {
            throw ThrowableUtil.convertExceptionToUnchecked(e);
        }
    }

    /**
     * 解析日期
     *
     * @param date Date
     * @return java.tool.Date：yyyy-MM-dd
     */
    public static Date parseDate(String date) {
        return parse(date, DateConstants.PATTERN_YMD_DASH);
    }

    /**
     * 解析日期
     *
     * @param date Date
     * @return java.tool.Date：yyyy-MM-dd HH:mm:ss
     */
    public static Date parseDateTime(String date) {
        return parse(date, DateConstants.PATTERN_DEFAULT_DASH);
    }

    /**
     * 获取月份的第一天
     *
     * @param date Date
     * @return java.tool.Date
     */
    public static Date firstDayOfMonth(Date date) {
        AssertUtil.notNull(date, "date is null");
        Calendar c = toCalendar(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取月份的最后一天
     *
     * @param date Date
     * @return java.tool.Date
     */
    public static Date lastDayOfMonth(Date date) {
        AssertUtil.notNull(date, "date is null");
        Calendar c = toCalendar(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获取周的第一天
     *
     * @param date Date
     * @return java.tool.Date
     */
    public static Date firstDayOfWeek(Date date) {
        AssertUtil.notNull(date, "date is null");
        Calendar c = toCalendar(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取周的最后一天
     *
     * @param date Date
     * @return java.tool.Date
     */
    public static Date lastDayOfWeek(Date date) {
        AssertUtil.notNull(date, "date is null");
        Calendar c = toCalendar(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.add(Calendar.WEEK_OF_YEAR, 1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取季度的第一天
     *
     * @param date Date
     * @return java.tool.Date
     */
    public static Date firstDayOfQuarter(Date date) {
        AssertUtil.notNull(date, "date is null");
        Calendar c = toCalendar(date);
        int q = getQuarter(c.getTime());
        switch (q) {
            case 1:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case 2:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case 3:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case 4:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
        }
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取季度的最后一天
     *
     * @param date Date
     * @return java.tool.Date
     */
    public static Date lastDayOfQuarter(Date date) {
        AssertUtil.notNull(date, "date is null");
        Calendar c = toCalendar(date);
        int q = getQuarter(c.getTime());
        switch (q) {
            case 1:
                c.set(Calendar.MONTH, Calendar.MARCH);
                break;
            case 2:
                c.set(Calendar.MONTH, Calendar.JUNE);
                break;
            case 3:
                c.set(Calendar.MONTH, Calendar.SEPTEMBER);
                break;
            case 4:
                c.set(Calendar.MONTH, Calendar.DECEMBER);
                break;
        }
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 日期比较大小<br>
     * 0相等<br>
     * &gt;0 date1 &gt; date2<br>
     * &lt;0 date1 &lt; date2
     *
     * @param date1 Date
     * @param date2 Date
     * @return int
     */
    public static int compareDate(Date date1, Date date2) {
        AssertUtil.notNull(date1, "date1 is null");
        AssertUtil.notNull(date2, "date2 is null");
        Calendar c1 = toCalendar(date1);
        Calendar c2 = toCalendar(date2);
        return c1.compareTo(c2);
    }

    /**
     * 获取日期区间月数
     *
     * @param early Date
     * @param late  Date
     * @return Month Count
     */
    public static int intervalForMonth(Date early, Date late) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        Calendar c1 = toCalendar(setDate(early));
        Calendar c2 = toCalendar(setDate(late));

        c1.add(Calendar.MONTH, 1);

        int count = 0;
        while (compareDate(c1.getTime(), c2.getTime()) <= 0) {
            count++;
            c1.add(Calendar.MONTH, 1);
        }

        return count;
    }

    /**
     * 获取日期区间周数
     *
     * @param early Date
     * @param late  Date
     * @return Week Count
     */
    public static int intervalForWeeks(Date early, Date late) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        Calendar c1 = toCalendar(setDate(early));
        Calendar c2 = toCalendar(setDate(late));

        Integer weekend;
        int days = 0;
        while (compareDate(c1.getTime(), c2.getTime()) <= 0) {
            weekend = getDayOfWeek(c1.getTime()) + 1;
            // 如果是周末
            if (weekend == Calendar.SUNDAY) {
                days++;
            }
            c1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    /**
     * 获取日期区间天数，排除周末
     *
     * @param early Date
     * @param late  Date
     * @return Day Count
     */
    public static int intervalForDaysExclWeekend(Date early, Date late) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        Calendar c1 = toCalendar(setDate(early));
        Calendar c2 = toCalendar(setDate(late));

        Integer weekend;
        int days = 0;
        while (compareDate(c1.getTime(), c2.getTime()) <= 0) {
            weekend = getDayOfWeek(c1.getTime()) + 1;
            // 如果不是周六或周末
            if (weekend != Calendar.SUNDAY && weekend != Calendar.SATURDAY) {
                days++;
            }
            c1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    /**
     * 获取日期区间天数
     *
     * @param early Date
     * @param late  Date
     * @return Day Count
     */
    public static int intervalForDays(Date early, Date late) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        Calendar c1 = toCalendar(setDate(early));
        Calendar c2 = toCalendar(setDate(late));

        int days = ((int) (c2.getTime().getTime() / 1000) - (int) (c1.getTime().getTime() / 1000)) / 3600 / 24;
        return days;
    }

    /**
     * 获取日期区间小时
     *
     * @param early Date
     * @param late  Date
     * @return Hour Count
     */
    public static int intervalForHours(Date early, Date late) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        Calendar c1 = toCalendar(early);
        Calendar c2 = toCalendar(late);

        int days = ((int) (c2.getTime().getTime() / 1000) - (int) (c1.getTime().getTime() / 1000)) / 3600;
        return days;
    }

    /**
     * 获取日期区间分钟
     *
     * @param early Date
     * @param late  Date
     * @return Minutes Count
     */
    public static int intervalForMinutes(Date early, Date late) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        Calendar c1 = toCalendar(early);
        Calendar c2 = toCalendar(late);

        int days = ((int) (c2.getTime().getTime() / 1000) - (int) (c1.getTime().getTime() / 1000)) / 60;
        return days;
    }

    /**
     * 获取日期区间秒数
     *
     * @param early Date
     * @param late  Date
     * @return Seconds Count
     */
    public static int intervalForSeconds(Date early, Date late) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        Calendar c1 = toCalendar(early);
        Calendar c2 = toCalendar(late);

        int days = ((int) (c2.getTime().getTime() / 1000) - (int) (c1.getTime().getTime() / 1000));
        return days;
    }

    /**
     * 判断日期是否为工作日
     *
     * @param date Date
     * @return Boolean
     */
    public static boolean isWorkDay(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c1 = toCalendar(date);
        int day = getDayOfWeek(c1.getTime()) + 1;
        // 如果不是周六或周末
        if (day != Calendar.SUNDAY && day != Calendar.SATURDAY) {
            return true;
        }
        return false;
    }

    /**
     * 判断日期是否为周末
     *
     * @param date Date
     * @return Boolean
     */
    public static boolean isWeekend(Date date) {
        AssertUtil.notNull(date, "date is null");

        Calendar c1 = toCalendar(date);
        int day = getDayOfWeek(c1.getTime()) + 1;
        // 如果不是周六或周末
        if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
            return true;
        }
        return false;
    }

    /**
     * 返回日期之间的月份集合
     *
     * @param early Date
     * @param late  Date
     * @return List
     */
    public static List<Date> splitForMonth(Date early, Date late) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        List<Date> result = new ArrayList<>();

        Calendar c1 = toCalendar(setDate(early));
        Calendar c2 = toCalendar(setDate(late));

        c1.add(Calendar.MONTH, 1);

        while (compareDate(c1.getTime(), c2.getTime()) <= 0) {
            result.add(firstDayOfMonth(c1.getTime()));
            c1.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 返回日期之间的季度集合
     *
     * @param early    Date
     * @param late     Date
     * @param isBefore true：季度的第一天、false：季度的最后一天
     * @return List
     */
    public static List<Date> splitForQuarter(Date early, Date late, boolean isBefore) {
        AssertUtil.notNull(early, "early is null");
        AssertUtil.notNull(late, "late is null");

        List<Date> result = new ArrayList<>();

        Calendar c1 = toCalendar(setDate(early));
        Calendar c2 = toCalendar(setDate(late));

        while (compareDate(c1.getTime(), c2.getTime()) <= 0) {
            if (isBefore) {
                result.add(firstDayOfQuarter(c1.getTime()));
            } else {
                result.add(lastDayOfQuarter(c1.getTime()));
            }
            c1.add(Calendar.MONTH, 3);
        }

        return result;
    }

    /**
     * 获取一天的起始时间
     * @param date
     * @return
     */
    public static String getDayByStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //一天的开始时间 yyyy:MM:dd 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return toDateTimeDash(calendar.getTime());
    }

    /**
     * 获取一天的起始时间
     * @param date
     * @return
     */
    public static String getDayByStart(String date) {
        return getDayByStart(parseDate(date));
    }

    /**
     * 获取一天最后时间
     * @param date
     * @return
     */
    public static String getDayByEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //一天的结束时间 yyyy:MM:dd 23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return toDateTimeDash(calendar.getTime());
    }

    /**
     * 获取一天最后时间
     * @param date
     * @return
     */
    public static String getDayByEnd(String date) {
        return getDayByEnd(parseDate(date));
    }

    public static Date getDateSubCondition(Date date, DateConstants.DateType dateType, int condition) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (dateType) {
            case Year:
                //当前时间减去年
                calendar.add(Calendar.YEAR, -condition);
                break;
            case Month:
                //当前时间减去月
                calendar.add(Calendar.MONTH, -condition);
                break;
            case Day:
                //当前时间减去天，即一天前的时间
                calendar.add(Calendar.DAY_OF_MONTH, -condition);
                break;
            case Hour:
                //当前时间减去小时
                calendar.add(Calendar.HOUR_OF_DAY, -condition);
                break;
            case Minute:
                // 当前时间减去分钟
                calendar.add(Calendar.MINUTE, -condition);
                break;
            default:
        }
        return calendar.getTime();
    }
}
