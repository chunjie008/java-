package com.ycnote.bazi.jw;

import static com.ycnote.bazi.jw.经纬度类.经纬度字典;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

// 经纬度转真太阳时
public class 经纬度转真太阳时 {

    public static String 获取真太阳时(String date, String time, String city) {
        double[] 经纬度 = 经纬度字典.get(city);
        return 转真太阳时(经纬度[0], 经纬度[1], date + " " + time);
    }

    public static String 转真太阳时(double longitude, double latitude, String time) {
        // 将传入的时间字符串解析为ZonedDateTime
        time = formatDate(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));

        // 获取对应的UTC时间
        ZonedDateTime utcTime = localZonedDateTime.withZoneSameInstant(ZoneOffset.UTC);

        // 获取当前当地标准时间（LST）
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");  // 替换为对应时区
        ZonedDateTime localTime = utcTime.withZoneSameInstant(zoneId);

        // 计算时区差异（与标准时区的小时数差异）
        int timeZoneOffset = TimeZone.getTimeZone(zoneId).getRawOffset() / 3600000;

        // 计算经度时间差
        double longitudeTimeDifference = (longitude - (timeZoneOffset * 15)) * 4;  // 分钟数

        // 计算均时差（EOT）
        double eot = 计算均时差(localTime);

        // 计算真太阳时
        LocalTime localSolarTime = localTime.toLocalTime()
                .plusMinutes((long) longitudeTimeDifference)
                .plusMinutes((long) eot);

        // 输出结果
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime solarDateTime = localDateTime.with(LocalTime.of(localSolarTime.getHour(), localSolarTime.getMinute()));

        // 如果计算出的真太阳时比当地标准时间晚,且longitudeTimeDifference<0 减去一天
        if (solarDateTime.isAfter(localDateTime)&&longitudeTimeDifference<0) {
            return solarDateTime.plusDays(-1).format(dateTimeFormatter);
        }
        // 如果计算出的真太阳时比当地标准时间早,且longitudeTimeDifference<0 增加一天
        if (solarDateTime.isBefore(localDateTime)&&longitudeTimeDifference>0) {
            return solarDateTime.plusDays(1).format(dateTimeFormatter);
        }
        return solarDateTime.format(dateTimeFormatter);
    }

    /**
     * 计算均时差（EOT）
     *
     * @param dateTime
     * @return 均时差（分钟）
     */
    private static double 计算均时差(ZonedDateTime dateTime) {
        // 计算EOT所需的近似公式
        int dayOfYear = dateTime.getDayOfYear();
        double b = 2 * Math.PI * (dayOfYear - 81) / 364.0;
        return 9.87 * Math.sin(2 * b) - 7.53 * Math.cos(b) - 1.5 * Math.sin(b);
    }

    public static String formatDate(String dateTime) {
        // 假设输入格式为 "yyyy-M-d H:m" 或 "yyyy-M-d H:m:s"
        // 需要将其格式化为 "yyyy-MM-dd HH:mm:ss"
        try {
            // 解析日期时间
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTimeParsed = LocalDateTime.parse(dateTime, inputFormatter);

            // 格式化为目标格式
            return dateTimeParsed.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // 捕捉并处理解析异常
            System.err.println("时间格式无效: " + e.getMessage());
            return dateTime;
        }
    }

    public static void main(String[] args) {
        // 示例经纬度
        double longitude = 130.966667;
        double latitude = 39.9041999;

        String time = "2024-07-01 23:55";
        System.out.println(longitude);
        System.out.println(time);
        System.out.println(转真太阳时(longitude, latitude, time));
    }
}
