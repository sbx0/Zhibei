package cn.sbx0.zhibei.tool;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;

/**
 * @author sbx0
 */
public class DateTools {

    public static String format(Date date) {
        return DateFormat.getDateInstance().format(date);
    }

    /**
     * 判断时间间隔有多少小时
     *
     * @param begin begin
     * @param end   end
     * @return double
     */
    public static double howManyHours(Date begin, Date end) {
        return (int) ((end.getTime() - begin.getTime()) / (60.0 * 60.0 * 1000.0) * 100000) / 100000.0;
    }

    /**
     * 判断时间是周几 0 周日 1 周一 2 周二 3 周三 以此类推
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        return weekIndex;
    }

    /**
     * 时间判断 午夜 0:00-1:00 凌晨 1:00-5:00 清晨 5:00-8:00 上午 8:00-12:00 中午 12:00-13:00 下午
     * 13:00-18:00 傍晚 18:00-19:00 晚上 19:00-24:00
     *
     * @param date date
     * @return int
     */
    public static int judgingTime(Date date) {
        Date start = getStartOfDay(date);
        double result = (int) ((date.getTime() - start.getTime()) / (60.0 * 60.0 * 1000.0) * 100000) / 100000.0;
        if (result <= 1.0) {
            // 午夜 0:00-1:00
            return 0;
        } else if (result <= 5.0) {
            // 凌晨 1:00-5:00
            return 1;
        } else if (result <= 8.0) {
            // 清晨 5:00-8:00
            return 2;
        } else if (result <= 12.0) {
            // 上午 8:00-12:00
            return 3;
        } else if (result <= 13.0) {
            // 中午 12:00-13:00
            return 4;
        } else if (result <= 18.0) {
            // 下午 13:00-18:00
            return 5;
        } else if (result <= 19.0) {
            // 傍晚 18:00-19:00
            return 6;
        } else if (result <= 24.0) {
            // 晚上 19:00-24:00
            return 7;
        } else {
            return -1;
        }
    }

    /**
     * 给时间添加几天
     *
     * @param date date
     * @param day  day
     * @return Date
     */
    public static Date addDay(Date date, int day) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, day);
            date = cal.getTime();
        }
        return date;
    }

    /**
     * 给时间减去几天
     *
     * @param date date
     * @param day  day
     * @return Date
     */
    public static Date rollDay(Date date, int day) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, -day);
            date = cal.getTime();
        }
        return date;
    }

    /**
     * 给时间加上几秒
     *
     * @param date   date
     * @param second second
     * @return Date
     */
    public static Date addSecond(Date date, int second) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.SECOND, second);
            date = cal.getTime();
        }
        return date;
    }

    /**
     * 给时间减上几秒
     *
     * @param date   date
     * @param second second
     * @return Date
     */
    public static Date rollSecond(Date date, int second) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.SECOND, -second);
            date = cal.getTime();
        }
        return date;
    }

    /**
     * 获得某天最大时间 2017-10-15 23:59:59
     *
     * @param date 日期
     * @return 指定日期的最大时间
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
                ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某天最小时间 2017-10-15 00:00:00
     *
     * @param date 日期
     * @return 指定日期的最小时间
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
                ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}
