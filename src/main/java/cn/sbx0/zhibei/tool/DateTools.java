package cn.sbx0.zhibei.tool;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTools {

    /**
     * 获得某天最大时间 2017-10-15 23:59:59
     *
     * @param date 日期
     * @return 指定日期的最大时间
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
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
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}
