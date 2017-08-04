package aburakc.yemekmenubot.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long getFormattedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return Long.parseLong(sdf.format(date));
    }

    public static boolean isWeekEnd(Date date) {
        if (date == null) {
            return false;
        }
        Calendar cTemp = Calendar.getInstance();
        cTemp.setTime(date);
        if (cTemp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cTemp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }
}
