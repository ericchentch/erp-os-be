package com.chilleric.franchise_sys.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.exception.InvalidRequestException;

public class DateFormat {
    public static Date getCurrentTime() {
        Date date = java.util.Calendar.getInstance().getTime();
        return date;
    }

    public static String toDateString(Date date, String format) {
        if (date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = null;
        try {
            result = sdf.format(date);
        } catch (Exception e) {
            throw new InvalidRequestException(new HashMap<>(),
                    LanguageMessageKey.INVALID_DATE_FORMAT);
        }
        return result;
    }
}
