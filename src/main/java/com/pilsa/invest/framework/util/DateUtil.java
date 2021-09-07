package com.pilsa.invest.framework.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * The type Date util.
 * 날짜관련 유틸 정의
 *
 * @author pilsa_home1
 */
public class DateUtil {
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddhhmm = "yyyyMMddhhmm";
    public static final String yyyyMMddhhmmss = "yyyyMMddhhmmss";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

    /**
     * Is date boolean.
     * 입력받은 문자열이 유효한 날짜형식인지 검증한다. (Default:yyyyMMdd)
     *
     * @param date the date
     * @return the boolean
     */
    public static boolean isDate(String date){
        return isDate(date, yyyyMMdd);
    }

    /**
     * Is date boolean.
     * 입력받은 문자열이 유효한 날짜형식인지 검증한다.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the boolean
     */
    public static boolean isDate(String date, @Nullable String pattern){
        if (StringUtils.isEmpty(date)) return false;
        if (StringUtils.isEmpty(pattern)) pattern = yyyyMMdd;
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeParseException e){
            return false;
        }
        return true;
    }

    public static String getDateString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(yyyyMMdd));
    }

    /**
     * Get date local date.
     * 입력받은 문자열을 LocalDateTime Type 으로 변환하여 반환한다. (Default:yyyyMMddhhmm)
     *
     * @param date the date
     * @return the local date
     */
    public static LocalDateTime getDate(String date){
        return getDate(date,null);
    }

    /**
     * Get date local date.
     * 입력받은 문자열을 LocalDate Type으로 변환하여 반환한다.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the local date
     */
    public static LocalDateTime getDate(String date, @Nullable String pattern){
        if (StringUtils.isEmpty(date)) return null;
        if (StringUtils.isEmpty(pattern)) pattern = yyyyMMddhhmm;
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * Date to string string.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String dateToString(LocalDateTime localDateTime){
        return dateToString(localDateTime,null);
    }

    /**
     * Date to string string.
     *
     * @param localDateTime the local date time
     * @param pattern       the pattern
     * @return the string
     */
    public static String dateToString(LocalDateTime localDateTime, @Nullable String pattern){
        if (StringUtils.isEmpty(localDateTime)) return null;
        if (StringUtils.isEmpty(pattern)) pattern = yyyyMMddhhmmss;
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }


}

