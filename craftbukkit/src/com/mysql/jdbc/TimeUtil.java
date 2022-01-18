// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Map;

public class TimeUtil
{
    static final Map ABBREVIATED_TIMEZONES;
    static final TimeZone GMT_TIMEZONE;
    static final Map TIMEZONE_MAPPINGS;
    
    public static Time changeTimezone(final MySQLConnection conn, final Calendar sessionCalendar, final Calendar targetCalendar, final Time t, final TimeZone fromTz, final TimeZone toTz, final boolean rollForward) {
        if (conn != null) {
            if (conn.getUseTimezone() && !conn.getNoTimezoneConversionForTimeType()) {
                final Calendar fromCal = Calendar.getInstance(fromTz);
                fromCal.setTime(t);
                final int fromOffset = fromCal.get(15) + fromCal.get(16);
                final Calendar toCal = Calendar.getInstance(toTz);
                toCal.setTime(t);
                final int toOffset = toCal.get(15) + toCal.get(16);
                final int offsetDiff = fromOffset - toOffset;
                long toTime = toCal.getTime().getTime();
                if (rollForward || (conn.isServerTzUTC() && !conn.isClientTzUTC())) {
                    toTime += offsetDiff;
                }
                else {
                    toTime -= offsetDiff;
                }
                final Time changedTime = new Time(toTime);
                return changedTime;
            }
            if (conn.getUseJDBCCompliantTimezoneShift() && targetCalendar != null) {
                final Time adjustedTime = new Time(jdbcCompliantZoneShift(sessionCalendar, targetCalendar, t));
                return adjustedTime;
            }
        }
        return t;
    }
    
    public static Timestamp changeTimezone(final MySQLConnection conn, final Calendar sessionCalendar, final Calendar targetCalendar, final Timestamp tstamp, final TimeZone fromTz, final TimeZone toTz, final boolean rollForward) {
        if (conn != null) {
            if (conn.getUseTimezone()) {
                final Calendar fromCal = Calendar.getInstance(fromTz);
                fromCal.setTime(tstamp);
                final int fromOffset = fromCal.get(15) + fromCal.get(16);
                final Calendar toCal = Calendar.getInstance(toTz);
                toCal.setTime(tstamp);
                final int toOffset = toCal.get(15) + toCal.get(16);
                final int offsetDiff = fromOffset - toOffset;
                long toTime = toCal.getTime().getTime();
                if (rollForward || (conn.isServerTzUTC() && !conn.isClientTzUTC())) {
                    toTime += offsetDiff;
                }
                else {
                    toTime -= offsetDiff;
                }
                final Timestamp changedTimestamp = new Timestamp(toTime);
                return changedTimestamp;
            }
            if (conn.getUseJDBCCompliantTimezoneShift() && targetCalendar != null) {
                final Timestamp adjustedTimestamp = new Timestamp(jdbcCompliantZoneShift(sessionCalendar, targetCalendar, tstamp));
                adjustedTimestamp.setNanos(tstamp.getNanos());
                return adjustedTimestamp;
            }
        }
        return tstamp;
    }
    
    private static long jdbcCompliantZoneShift(Calendar sessionCalendar, final Calendar targetCalendar, final Date dt) {
        if (sessionCalendar == null) {
            sessionCalendar = new GregorianCalendar();
        }
        final Date origCalDate = targetCalendar.getTime();
        final Date origSessionDate = sessionCalendar.getTime();
        try {
            sessionCalendar.setTime(dt);
            targetCalendar.set(1, sessionCalendar.get(1));
            targetCalendar.set(2, sessionCalendar.get(2));
            targetCalendar.set(5, sessionCalendar.get(5));
            targetCalendar.set(11, sessionCalendar.get(11));
            targetCalendar.set(12, sessionCalendar.get(12));
            targetCalendar.set(13, sessionCalendar.get(13));
            targetCalendar.set(14, sessionCalendar.get(14));
            return targetCalendar.getTime().getTime();
        }
        finally {
            sessionCalendar.setTime(origSessionDate);
            targetCalendar.setTime(origCalDate);
        }
    }
    
    static final java.sql.Date fastDateCreate(final boolean useGmtConversion, Calendar gmtCalIfNeeded, final Calendar cal, final int year, final int month, final int day) {
        Calendar dateCal = cal;
        if (useGmtConversion) {
            if (gmtCalIfNeeded == null) {
                gmtCalIfNeeded = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            }
            gmtCalIfNeeded.clear();
            dateCal = gmtCalIfNeeded;
        }
        dateCal.clear();
        dateCal.set(14, 0);
        dateCal.set(year, month - 1, day, 0, 0, 0);
        long dateAsMillis = 0L;
        try {
            dateAsMillis = dateCal.getTimeInMillis();
        }
        catch (IllegalAccessError iae) {
            dateAsMillis = dateCal.getTime().getTime();
        }
        return new java.sql.Date(dateAsMillis);
    }
    
    static final java.sql.Date fastDateCreate(final int year, final int month, final int day, final Calendar targetCalendar) {
        final Calendar dateCal = (targetCalendar == null) ? new GregorianCalendar() : targetCalendar;
        dateCal.clear();
        dateCal.set(year, month - 1, day, 0, 0, 0);
        dateCal.set(14, 0);
        long dateAsMillis = 0L;
        try {
            dateAsMillis = dateCal.getTimeInMillis();
        }
        catch (IllegalAccessError iae) {
            dateAsMillis = dateCal.getTime().getTime();
        }
        return new java.sql.Date(dateAsMillis);
    }
    
    static final Time fastTimeCreate(final Calendar cal, final int hour, final int minute, final int second, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        if (hour < 0 || hour > 24) {
            throw SQLError.createSQLException("Illegal hour value '" + hour + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
        }
        if (minute < 0 || minute > 59) {
            throw SQLError.createSQLException("Illegal minute value '" + minute + "'" + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
        }
        if (second < 0 || second > 59) {
            throw SQLError.createSQLException("Illegal minute value '" + second + "'" + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
        }
        cal.clear();
        cal.set(1970, 0, 1, hour, minute, second);
        long timeAsMillis = 0L;
        try {
            timeAsMillis = cal.getTimeInMillis();
        }
        catch (IllegalAccessError iae) {
            timeAsMillis = cal.getTime().getTime();
        }
        return new Time(timeAsMillis);
    }
    
    static final Time fastTimeCreate(final int hour, final int minute, final int second, final Calendar targetCalendar, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        if (hour < 0 || hour > 23) {
            throw SQLError.createSQLException("Illegal hour value '" + hour + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
        }
        if (minute < 0 || minute > 59) {
            throw SQLError.createSQLException("Illegal minute value '" + minute + "'" + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
        }
        if (second < 0 || second > 59) {
            throw SQLError.createSQLException("Illegal minute value '" + second + "'" + "' for java.sql.Time type in value '" + timeFormattedString(hour, minute, second) + ".", "S1009", exceptionInterceptor);
        }
        final Calendar cal = (targetCalendar == null) ? new GregorianCalendar() : targetCalendar;
        cal.clear();
        cal.set(1970, 0, 1, hour, minute, second);
        long timeAsMillis = 0L;
        try {
            timeAsMillis = cal.getTimeInMillis();
        }
        catch (IllegalAccessError iae) {
            timeAsMillis = cal.getTime().getTime();
        }
        return new Time(timeAsMillis);
    }
    
    static final Timestamp fastTimestampCreate(final boolean useGmtConversion, Calendar gmtCalIfNeeded, final Calendar cal, final int year, final int month, final int day, final int hour, final int minute, final int seconds, final int secondsPart) {
        cal.clear();
        cal.set(year, month - 1, day, hour, minute, seconds);
        int offsetDiff = 0;
        if (useGmtConversion) {
            final int fromOffset = cal.get(15) + cal.get(16);
            if (gmtCalIfNeeded == null) {
                gmtCalIfNeeded = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            }
            gmtCalIfNeeded.clear();
            gmtCalIfNeeded.setTimeInMillis(cal.getTimeInMillis());
            final int toOffset = gmtCalIfNeeded.get(15) + gmtCalIfNeeded.get(16);
            offsetDiff = fromOffset - toOffset;
        }
        if (secondsPart != 0) {
            cal.set(14, secondsPart / 1000000);
        }
        long tsAsMillis = 0L;
        try {
            tsAsMillis = cal.getTimeInMillis();
        }
        catch (IllegalAccessError iae) {
            tsAsMillis = cal.getTime().getTime();
        }
        final Timestamp ts = new Timestamp(tsAsMillis + offsetDiff);
        ts.setNanos(secondsPart);
        return ts;
    }
    
    static final Timestamp fastTimestampCreate(final TimeZone tz, final int year, final int month, final int day, final int hour, final int minute, final int seconds, final int secondsPart) {
        final Calendar cal = (tz == null) ? new GregorianCalendar() : new GregorianCalendar(tz);
        cal.clear();
        cal.set(year, month - 1, day, hour, minute, seconds);
        long tsAsMillis = 0L;
        try {
            tsAsMillis = cal.getTimeInMillis();
        }
        catch (IllegalAccessError iae) {
            tsAsMillis = cal.getTime().getTime();
        }
        final Timestamp ts = new Timestamp(tsAsMillis);
        ts.setNanos(secondsPart);
        return ts;
    }
    
    public static String getCanoncialTimezone(String timezoneStr, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        if (timezoneStr == null) {
            return null;
        }
        timezoneStr = timezoneStr.trim();
        if (timezoneStr.length() > 2 && (timezoneStr.charAt(0) == '+' || timezoneStr.charAt(0) == '-') && Character.isDigit(timezoneStr.charAt(1))) {
            return "GMT" + timezoneStr;
        }
        final int daylightIndex = StringUtils.indexOfIgnoreCase(timezoneStr, "DAYLIGHT");
        if (daylightIndex != -1) {
            final StringBuffer timezoneBuf = new StringBuffer();
            timezoneBuf.append(timezoneStr.substring(0, daylightIndex));
            timezoneBuf.append("Standard");
            timezoneBuf.append(timezoneStr.substring(daylightIndex + "DAYLIGHT".length(), timezoneStr.length()));
            timezoneStr = timezoneBuf.toString();
        }
        String canonicalTz = TimeUtil.TIMEZONE_MAPPINGS.get(timezoneStr);
        if (canonicalTz == null) {
            final String[] abbreviatedTimezone = TimeUtil.ABBREVIATED_TIMEZONES.get(timezoneStr);
            if (abbreviatedTimezone != null) {
                if (abbreviatedTimezone.length != 1) {
                    final StringBuffer possibleTimezones = new StringBuffer(128);
                    possibleTimezones.append(abbreviatedTimezone[0]);
                    for (int i = 1; i < abbreviatedTimezone.length; ++i) {
                        possibleTimezones.append(", ");
                        possibleTimezones.append(abbreviatedTimezone[i]);
                    }
                    throw SQLError.createSQLException(Messages.getString("TimeUtil.TooGenericTimezoneId", new Object[] { timezoneStr, possibleTimezones }), "01S00", exceptionInterceptor);
                }
                canonicalTz = abbreviatedTimezone[0];
            }
        }
        return canonicalTz;
    }
    
    private static String timeFormattedString(final int hours, final int minutes, final int seconds) {
        final StringBuffer buf = new StringBuffer(8);
        if (hours < 10) {
            buf.append("0");
        }
        buf.append(hours);
        buf.append(":");
        if (minutes < 10) {
            buf.append("0");
        }
        buf.append(minutes);
        buf.append(":");
        if (seconds < 10) {
            buf.append("0");
        }
        buf.append(seconds);
        return buf.toString();
    }
    
    static {
        GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
        HashMap tempMap = new HashMap();
        tempMap.put("Romance", "Europe/Paris");
        tempMap.put("Romance Standard Time", "Europe/Paris");
        tempMap.put("Warsaw", "Europe/Warsaw");
        tempMap.put("Central Europe", "Europe/Prague");
        tempMap.put("Central Europe Standard Time", "Europe/Prague");
        tempMap.put("Prague Bratislava", "Europe/Prague");
        tempMap.put("W. Central Africa Standard Time", "Africa/Luanda");
        tempMap.put("FLE", "Europe/Helsinki");
        tempMap.put("FLE Standard Time", "Europe/Helsinki");
        tempMap.put("GFT", "Europe/Athens");
        tempMap.put("GFT Standard Time", "Europe/Athens");
        tempMap.put("GTB", "Europe/Athens");
        tempMap.put("GTB Standard Time", "Europe/Athens");
        tempMap.put("Israel", "Asia/Jerusalem");
        tempMap.put("Israel Standard Time", "Asia/Jerusalem");
        tempMap.put("Arab", "Asia/Riyadh");
        tempMap.put("Arab Standard Time", "Asia/Riyadh");
        tempMap.put("Arabic Standard Time", "Asia/Baghdad");
        tempMap.put("E. Africa", "Africa/Nairobi");
        tempMap.put("E. Africa Standard Time", "Africa/Nairobi");
        tempMap.put("Saudi Arabia", "Asia/Riyadh");
        tempMap.put("Saudi Arabia Standard Time", "Asia/Riyadh");
        tempMap.put("Iran", "Asia/Tehran");
        tempMap.put("Iran Standard Time", "Asia/Tehran");
        tempMap.put("Afghanistan", "Asia/Kabul");
        tempMap.put("Afghanistan Standard Time", "Asia/Kabul");
        tempMap.put("India", "Asia/Calcutta");
        tempMap.put("India Standard Time", "Asia/Calcutta");
        tempMap.put("Myanmar Standard Time", "Asia/Rangoon");
        tempMap.put("Nepal Standard Time", "Asia/Katmandu");
        tempMap.put("Sri Lanka", "Asia/Colombo");
        tempMap.put("Sri Lanka Standard Time", "Asia/Colombo");
        tempMap.put("Beijing", "Asia/Shanghai");
        tempMap.put("China", "Asia/Shanghai");
        tempMap.put("China Standard Time", "Asia/Shanghai");
        tempMap.put("AUS Central", "Australia/Darwin");
        tempMap.put("AUS Central Standard Time", "Australia/Darwin");
        tempMap.put("Cen. Australia", "Australia/Adelaide");
        tempMap.put("Cen. Australia Standard Time", "Australia/Adelaide");
        tempMap.put("Vladivostok", "Asia/Vladivostok");
        tempMap.put("Vladivostok Standard Time", "Asia/Vladivostok");
        tempMap.put("West Pacific", "Pacific/Guam");
        tempMap.put("West Pacific Standard Time", "Pacific/Guam");
        tempMap.put("E. South America", "America/Sao_Paulo");
        tempMap.put("E. South America Standard Time", "America/Sao_Paulo");
        tempMap.put("Greenland Standard Time", "America/Godthab");
        tempMap.put("Newfoundland", "America/St_Johns");
        tempMap.put("Newfoundland Standard Time", "America/St_Johns");
        tempMap.put("Pacific SA", "America/Caracas");
        tempMap.put("Pacific SA Standard Time", "America/Caracas");
        tempMap.put("SA Western", "America/Caracas");
        tempMap.put("SA Western Standard Time", "America/Caracas");
        tempMap.put("SA Pacific", "America/Bogota");
        tempMap.put("SA Pacific Standard Time", "America/Bogota");
        tempMap.put("US Eastern", "America/Indianapolis");
        tempMap.put("US Eastern Standard Time", "America/Indianapolis");
        tempMap.put("Central America Standard Time", "America/Regina");
        tempMap.put("Mexico", "America/Mexico_City");
        tempMap.put("Mexico Standard Time", "America/Mexico_City");
        tempMap.put("Canada Central", "America/Regina");
        tempMap.put("Canada Central Standard Time", "America/Regina");
        tempMap.put("US Mountain", "America/Phoenix");
        tempMap.put("US Mountain Standard Time", "America/Phoenix");
        tempMap.put("GMT", "GMT");
        tempMap.put("Ekaterinburg", "Asia/Yekaterinburg");
        tempMap.put("Ekaterinburg Standard Time", "Asia/Yekaterinburg");
        tempMap.put("West Asia", "Asia/Karachi");
        tempMap.put("West Asia Standard Time", "Asia/Karachi");
        tempMap.put("Central Asia", "Asia/Dhaka");
        tempMap.put("Central Asia Standard Time", "Asia/Dhaka");
        tempMap.put("N. Central Asia Standard Time", "Asia/Novosibirsk");
        tempMap.put("Bangkok", "Asia/Bangkok");
        tempMap.put("Bangkok Standard Time", "Asia/Bangkok");
        tempMap.put("North Asia Standard Time", "Asia/Krasnoyarsk");
        tempMap.put("SE Asia", "Asia/Bangkok");
        tempMap.put("SE Asia Standard Time", "Asia/Bangkok");
        tempMap.put("North Asia East Standard Time", "Asia/Ulaanbaatar");
        tempMap.put("Singapore", "Asia/Singapore");
        tempMap.put("Singapore Standard Time", "Asia/Singapore");
        tempMap.put("Taipei", "Asia/Taipei");
        tempMap.put("Taipei Standard Time", "Asia/Taipei");
        tempMap.put("W. Australia", "Australia/Perth");
        tempMap.put("W. Australia Standard Time", "Australia/Perth");
        tempMap.put("Korea", "Asia/Seoul");
        tempMap.put("Korea Standard Time", "Asia/Seoul");
        tempMap.put("Tokyo", "Asia/Tokyo");
        tempMap.put("Tokyo Standard Time", "Asia/Tokyo");
        tempMap.put("Yakutsk", "Asia/Yakutsk");
        tempMap.put("Yakutsk Standard Time", "Asia/Yakutsk");
        tempMap.put("Central European", "Europe/Belgrade");
        tempMap.put("Central European Standard Time", "Europe/Belgrade");
        tempMap.put("W. Europe", "Europe/Berlin");
        tempMap.put("W. Europe Standard Time", "Europe/Berlin");
        tempMap.put("Tasmania", "Australia/Hobart");
        tempMap.put("Tasmania Standard Time", "Australia/Hobart");
        tempMap.put("AUS Eastern", "Australia/Sydney");
        tempMap.put("AUS Eastern Standard Time", "Australia/Sydney");
        tempMap.put("E. Australia", "Australia/Brisbane");
        tempMap.put("E. Australia Standard Time", "Australia/Brisbane");
        tempMap.put("Sydney Standard Time", "Australia/Sydney");
        tempMap.put("Central Pacific", "Pacific/Guadalcanal");
        tempMap.put("Central Pacific Standard Time", "Pacific/Guadalcanal");
        tempMap.put("Dateline", "Pacific/Majuro");
        tempMap.put("Dateline Standard Time", "Pacific/Majuro");
        tempMap.put("Fiji", "Pacific/Fiji");
        tempMap.put("Fiji Standard Time", "Pacific/Fiji");
        tempMap.put("Samoa", "Pacific/Apia");
        tempMap.put("Samoa Standard Time", "Pacific/Apia");
        tempMap.put("Hawaiian", "Pacific/Honolulu");
        tempMap.put("Hawaiian Standard Time", "Pacific/Honolulu");
        tempMap.put("Alaskan", "America/Anchorage");
        tempMap.put("Alaskan Standard Time", "America/Anchorage");
        tempMap.put("Pacific", "America/Los_Angeles");
        tempMap.put("Pacific Standard Time", "America/Los_Angeles");
        tempMap.put("Mexico Standard Time 2", "America/Chihuahua");
        tempMap.put("Mountain", "America/Denver");
        tempMap.put("Mountain Standard Time", "America/Denver");
        tempMap.put("Central", "America/Chicago");
        tempMap.put("Central Standard Time", "America/Chicago");
        tempMap.put("Eastern", "America/New_York");
        tempMap.put("Eastern Standard Time", "America/New_York");
        tempMap.put("E. Europe", "Europe/Bucharest");
        tempMap.put("E. Europe Standard Time", "Europe/Bucharest");
        tempMap.put("Egypt", "Africa/Cairo");
        tempMap.put("Egypt Standard Time", "Africa/Cairo");
        tempMap.put("South Africa", "Africa/Harare");
        tempMap.put("South Africa Standard Time", "Africa/Harare");
        tempMap.put("Atlantic", "America/Halifax");
        tempMap.put("Atlantic Standard Time", "America/Halifax");
        tempMap.put("SA Eastern", "America/Buenos_Aires");
        tempMap.put("SA Eastern Standard Time", "America/Buenos_Aires");
        tempMap.put("Mid-Atlantic", "Atlantic/South_Georgia");
        tempMap.put("Mid-Atlantic Standard Time", "Atlantic/South_Georgia");
        tempMap.put("Azores", "Atlantic/Azores");
        tempMap.put("Azores Standard Time", "Atlantic/Azores");
        tempMap.put("Cape Verde Standard Time", "Atlantic/Cape_Verde");
        tempMap.put("Russian", "Europe/Moscow");
        tempMap.put("Russian Standard Time", "Europe/Moscow");
        tempMap.put("New Zealand", "Pacific/Auckland");
        tempMap.put("New Zealand Standard Time", "Pacific/Auckland");
        tempMap.put("Tonga Standard Time", "Pacific/Tongatapu");
        tempMap.put("Arabian", "Asia/Muscat");
        tempMap.put("Arabian Standard Time", "Asia/Muscat");
        tempMap.put("Caucasus", "Asia/Tbilisi");
        tempMap.put("Caucasus Standard Time", "Asia/Tbilisi");
        tempMap.put("GMT Standard Time", "GMT");
        tempMap.put("Greenwich", "GMT");
        tempMap.put("Greenwich Standard Time", "GMT");
        tempMap.put("UTC", "GMT");
        final Iterator entries = tempMap.entrySet().iterator();
        final Map entryMap = new HashMap(tempMap.size());
        while (entries.hasNext()) {
            final String name = entries.next().getValue().toString();
            entryMap.put(name, name);
        }
        tempMap.putAll(entryMap);
        TIMEZONE_MAPPINGS = Collections.unmodifiableMap((Map<?, ?>)tempMap);
        tempMap = new HashMap();
        tempMap.put("ACST", new String[] { "America/Porto_Acre" });
        tempMap.put("ACT", new String[] { "America/Porto_Acre" });
        tempMap.put("ADDT", new String[] { "America/Pangnirtung" });
        tempMap.put("ADMT", new String[] { "Africa/Asmera", "Africa/Addis_Ababa" });
        tempMap.put("ADT", new String[] { "Atlantic/Bermuda", "Asia/Baghdad", "America/Thule", "America/Goose_Bay", "America/Halifax", "America/Glace_Bay", "America/Pangnirtung", "America/Barbados", "America/Martinique" });
        tempMap.put("AFT", new String[] { "Asia/Kabul" });
        tempMap.put("AHDT", new String[] { "America/Anchorage" });
        tempMap.put("AHST", new String[] { "America/Anchorage" });
        tempMap.put("AHWT", new String[] { "America/Anchorage" });
        tempMap.put("AKDT", new String[] { "America/Juneau", "America/Yakutat", "America/Anchorage", "America/Nome" });
        tempMap.put("AKST", new String[] { "Asia/Aqtobe", "America/Juneau", "America/Yakutat", "America/Anchorage", "America/Nome" });
        tempMap.put("AKT", new String[] { "Asia/Aqtobe" });
        tempMap.put("AKTST", new String[] { "Asia/Aqtobe" });
        tempMap.put("AKWT", new String[] { "America/Juneau", "America/Yakutat", "America/Anchorage", "America/Nome" });
        tempMap.put("ALMST", new String[] { "Asia/Almaty" });
        tempMap.put("ALMT", new String[] { "Asia/Almaty" });
        tempMap.put("AMST", new String[] { "Asia/Yerevan", "America/Cuiaba", "America/Porto_Velho", "America/Boa_Vista", "America/Manaus" });
        tempMap.put("AMT", new String[] { "Europe/Athens", "Europe/Amsterdam", "Asia/Yerevan", "Africa/Asmera", "America/Cuiaba", "America/Porto_Velho", "America/Boa_Vista", "America/Manaus", "America/Asuncion" });
        tempMap.put("ANAMT", new String[] { "Asia/Anadyr" });
        tempMap.put("ANAST", new String[] { "Asia/Anadyr" });
        tempMap.put("ANAT", new String[] { "Asia/Anadyr" });
        tempMap.put("ANT", new String[] { "America/Aruba", "America/Curacao" });
        tempMap.put("AQTST", new String[] { "Asia/Aqtobe", "Asia/Aqtau" });
        tempMap.put("AQTT", new String[] { "Asia/Aqtobe", "Asia/Aqtau" });
        tempMap.put("ARST", new String[] { "Antarctica/Palmer", "America/Buenos_Aires", "America/Rosario", "America/Cordoba", "America/Jujuy", "America/Catamarca", "America/Mendoza" });
        tempMap.put("ART", new String[] { "Antarctica/Palmer", "America/Buenos_Aires", "America/Rosario", "America/Cordoba", "America/Jujuy", "America/Catamarca", "America/Mendoza" });
        tempMap.put("ASHST", new String[] { "Asia/Ashkhabad" });
        tempMap.put("ASHT", new String[] { "Asia/Ashkhabad" });
        tempMap.put("AST", new String[] { "Atlantic/Bermuda", "Asia/Bahrain", "Asia/Baghdad", "Asia/Kuwait", "Asia/Qatar", "Asia/Riyadh", "Asia/Aden", "America/Thule", "America/Goose_Bay", "America/Halifax", "America/Glace_Bay", "America/Pangnirtung", "America/Anguilla", "America/Antigua", "America/Barbados", "America/Dominica", "America/Santo_Domingo", "America/Grenada", "America/Guadeloupe", "America/Martinique", "America/Montserrat", "America/Puerto_Rico", "America/St_Kitts", "America/St_Lucia", "America/Miquelon", "America/St_Vincent", "America/Tortola", "America/St_Thomas", "America/Aruba", "America/Curacao", "America/Port_of_Spain" });
        tempMap.put("AWT", new String[] { "America/Puerto_Rico" });
        tempMap.put("AZOST", new String[] { "Atlantic/Azores" });
        tempMap.put("AZOT", new String[] { "Atlantic/Azores" });
        tempMap.put("AZST", new String[] { "Asia/Baku" });
        tempMap.put("AZT", new String[] { "Asia/Baku" });
        tempMap.put("BAKST", new String[] { "Asia/Baku" });
        tempMap.put("BAKT", new String[] { "Asia/Baku" });
        tempMap.put("BDT", new String[] { "Asia/Dacca", "America/Nome", "America/Adak" });
        tempMap.put("BEAT", new String[] { "Africa/Nairobi", "Africa/Mogadishu", "Africa/Kampala" });
        tempMap.put("BEAUT", new String[] { "Africa/Nairobi", "Africa/Dar_es_Salaam", "Africa/Kampala" });
        tempMap.put("BMT", new String[] { "Europe/Brussels", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Bucharest", "Europe/Zurich", "Asia/Baghdad", "Asia/Bangkok", "Africa/Banjul", "America/Barbados", "America/Bogota" });
        tempMap.put("BNT", new String[] { "Asia/Brunei" });
        tempMap.put("BORT", new String[] { "Asia/Ujung_Pandang", "Asia/Kuching" });
        tempMap.put("BOST", new String[] { "America/La_Paz" });
        tempMap.put("BOT", new String[] { "America/La_Paz" });
        tempMap.put("BRST", new String[] { "America/Belem", "America/Fortaleza", "America/Araguaina", "America/Maceio", "America/Sao_Paulo" });
        tempMap.put("BRT", new String[] { "America/Belem", "America/Fortaleza", "America/Araguaina", "America/Maceio", "America/Sao_Paulo" });
        tempMap.put("BST", new String[] { "Europe/London", "Europe/Belfast", "Europe/Dublin", "Europe/Gibraltar", "Pacific/Pago_Pago", "Pacific/Midway", "America/Nome", "America/Adak" });
        tempMap.put("BTT", new String[] { "Asia/Thimbu" });
        tempMap.put("BURT", new String[] { "Asia/Dacca", "Asia/Rangoon", "Asia/Calcutta" });
        tempMap.put("BWT", new String[] { "America/Nome", "America/Adak" });
        tempMap.put("CANT", new String[] { "Atlantic/Canary" });
        tempMap.put("CAST", new String[] { "Africa/Gaborone", "Africa/Khartoum" });
        tempMap.put("CAT", new String[] { "Africa/Gaborone", "Africa/Bujumbura", "Africa/Lubumbashi", "Africa/Blantyre", "Africa/Maputo", "Africa/Windhoek", "Africa/Kigali", "Africa/Khartoum", "Africa/Lusaka", "Africa/Harare", "America/Anchorage" });
        tempMap.put("CCT", new String[] { "Indian/Cocos" });
        tempMap.put("CDDT", new String[] { "America/Rankin_Inlet" });
        tempMap.put("CDT", new String[] { "Asia/Harbin", "Asia/Shanghai", "Asia/Chungking", "Asia/Urumqi", "Asia/Kashgar", "Asia/Taipei", "Asia/Macao", "America/Chicago", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Knox", "America/Indiana/Vevay", "America/Louisville", "America/Menominee", "America/Rainy_River", "America/Winnipeg", "America/Pangnirtung", "America/Iqaluit", "America/Rankin_Inlet", "America/Cambridge_Bay", "America/Cancun", "America/Mexico_City", "America/Chihuahua", "America/Belize", "America/Costa_Rica", "America/Havana", "America/El_Salvador", "America/Guatemala", "America/Tegucigalpa", "America/Managua" });
        tempMap.put("CEST", new String[] { "Europe/Tirane", "Europe/Andorra", "Europe/Vienna", "Europe/Minsk", "Europe/Brussels", "Europe/Sofia", "Europe/Prague", "Europe/Copenhagen", "Europe/Tallinn", "Europe/Berlin", "Europe/Gibraltar", "Europe/Athens", "Europe/Budapest", "Europe/Rome", "Europe/Riga", "Europe/Vaduz", "Europe/Vilnius", "Europe/Luxembourg", "Europe/Malta", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Monaco", "Europe/Amsterdam", "Europe/Oslo", "Europe/Warsaw", "Europe/Lisbon", "Europe/Kaliningrad", "Europe/Madrid", "Europe/Stockholm", "Europe/Zurich", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol", "Europe/Belgrade", "Africa/Algiers", "Africa/Tripoli", "Africa/Tunis", "Africa/Ceuta" });
        tempMap.put("CET", new String[] { "Europe/Tirane", "Europe/Andorra", "Europe/Vienna", "Europe/Minsk", "Europe/Brussels", "Europe/Sofia", "Europe/Prague", "Europe/Copenhagen", "Europe/Tallinn", "Europe/Berlin", "Europe/Gibraltar", "Europe/Athens", "Europe/Budapest", "Europe/Rome", "Europe/Riga", "Europe/Vaduz", "Europe/Vilnius", "Europe/Luxembourg", "Europe/Malta", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Monaco", "Europe/Amsterdam", "Europe/Oslo", "Europe/Warsaw", "Europe/Lisbon", "Europe/Kaliningrad", "Europe/Madrid", "Europe/Stockholm", "Europe/Zurich", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol", "Europe/Belgrade", "Africa/Algiers", "Africa/Tripoli", "Africa/Casablanca", "Africa/Tunis", "Africa/Ceuta" });
        tempMap.put("CGST", new String[] { "America/Scoresbysund" });
        tempMap.put("CGT", new String[] { "America/Scoresbysund" });
        tempMap.put("CHDT", new String[] { "America/Belize" });
        tempMap.put("CHUT", new String[] { "Asia/Chungking" });
        tempMap.put("CJT", new String[] { "Asia/Tokyo" });
        tempMap.put("CKHST", new String[] { "Pacific/Rarotonga" });
        tempMap.put("CKT", new String[] { "Pacific/Rarotonga" });
        tempMap.put("CLST", new String[] { "Antarctica/Palmer", "America/Santiago" });
        tempMap.put("CLT", new String[] { "Antarctica/Palmer", "America/Santiago" });
        tempMap.put("CMT", new String[] { "Europe/Copenhagen", "Europe/Chisinau", "Europe/Tiraspol", "America/St_Lucia", "America/Buenos_Aires", "America/Rosario", "America/Cordoba", "America/Jujuy", "America/Catamarca", "America/Mendoza", "America/Caracas" });
        tempMap.put("COST", new String[] { "America/Bogota" });
        tempMap.put("COT", new String[] { "America/Bogota" });
        tempMap.put("CST", new String[] { "Asia/Harbin", "Asia/Shanghai", "Asia/Chungking", "Asia/Urumqi", "Asia/Kashgar", "Asia/Taipei", "Asia/Macao", "Asia/Jayapura", "Australia/Darwin", "Australia/Adelaide", "Australia/Broken_Hill", "America/Chicago", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Knox", "America/Indiana/Vevay", "America/Louisville", "America/Detroit", "America/Menominee", "America/Rainy_River", "America/Winnipeg", "America/Regina", "America/Swift_Current", "America/Pangnirtung", "America/Iqaluit", "America/Rankin_Inlet", "America/Cambridge_Bay", "America/Cancun", "America/Mexico_City", "America/Chihuahua", "America/Hermosillo", "America/Mazatlan", "America/Belize", "America/Costa_Rica", "America/Havana", "America/El_Salvador", "America/Guatemala", "America/Tegucigalpa", "America/Managua" });
        tempMap.put("CUT", new String[] { "Europe/Zaporozhye" });
        tempMap.put("CVST", new String[] { "Atlantic/Cape_Verde" });
        tempMap.put("CVT", new String[] { "Atlantic/Cape_Verde" });
        tempMap.put("CWT", new String[] { "America/Chicago", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Knox", "America/Indiana/Vevay", "America/Louisville", "America/Menominee" });
        tempMap.put("CXT", new String[] { "Indian/Christmas" });
        tempMap.put("DACT", new String[] { "Asia/Dacca" });
        tempMap.put("DAVT", new String[] { "Antarctica/Davis" });
        tempMap.put("DDUT", new String[] { "Antarctica/DumontDUrville" });
        tempMap.put("DFT", new String[] { "Europe/Oslo", "Europe/Paris" });
        tempMap.put("DMT", new String[] { "Europe/Belfast", "Europe/Dublin" });
        tempMap.put("DUSST", new String[] { "Asia/Dushanbe" });
        tempMap.put("DUST", new String[] { "Asia/Dushanbe" });
        tempMap.put("EASST", new String[] { "Pacific/Easter" });
        tempMap.put("EAST", new String[] { "Indian/Antananarivo", "Pacific/Easter" });
        tempMap.put("EAT", new String[] { "Indian/Comoro", "Indian/Antananarivo", "Indian/Mayotte", "Africa/Djibouti", "Africa/Asmera", "Africa/Addis_Ababa", "Africa/Nairobi", "Africa/Mogadishu", "Africa/Khartoum", "Africa/Dar_es_Salaam", "Africa/Kampala" });
        tempMap.put("ECT", new String[] { "Pacific/Galapagos", "America/Guayaquil" });
        tempMap.put("EDDT", new String[] { "America/Iqaluit" });
        tempMap.put("EDT", new String[] { "America/New_York", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Vevay", "America/Louisville", "America/Detroit", "America/Montreal", "America/Thunder_Bay", "America/Nipigon", "America/Pangnirtung", "America/Iqaluit", "America/Cancun", "America/Nassau", "America/Santo_Domingo", "America/Port-au-Prince", "America/Jamaica", "America/Grand_Turk" });
        tempMap.put("EEMT", new String[] { "Europe/Minsk", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Kaliningrad", "Europe/Moscow" });
        tempMap.put("EEST", new String[] { "Europe/Minsk", "Europe/Sofia", "Europe/Tallinn", "Europe/Helsinki", "Europe/Athens", "Europe/Riga", "Europe/Vilnius", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Warsaw", "Europe/Bucharest", "Europe/Kaliningrad", "Europe/Moscow", "Europe/Istanbul", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Asia/Nicosia", "Asia/Amman", "Asia/Beirut", "Asia/Gaza", "Asia/Damascus", "Africa/Cairo" });
        tempMap.put("EET", new String[] { "Europe/Minsk", "Europe/Sofia", "Europe/Tallinn", "Europe/Helsinki", "Europe/Athens", "Europe/Riga", "Europe/Vilnius", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Warsaw", "Europe/Bucharest", "Europe/Kaliningrad", "Europe/Moscow", "Europe/Istanbul", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol", "Asia/Nicosia", "Asia/Amman", "Asia/Beirut", "Asia/Gaza", "Asia/Damascus", "Africa/Cairo", "Africa/Tripoli" });
        tempMap.put("EGST", new String[] { "America/Scoresbysund" });
        tempMap.put("EGT", new String[] { "Atlantic/Jan_Mayen", "America/Scoresbysund" });
        tempMap.put("EHDT", new String[] { "America/Santo_Domingo" });
        tempMap.put("EST", new String[] { "Australia/Brisbane", "Australia/Lindeman", "Australia/Hobart", "Australia/Melbourne", "Australia/Sydney", "Australia/Broken_Hill", "Australia/Lord_Howe", "America/New_York", "America/Chicago", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Knox", "America/Indiana/Vevay", "America/Louisville", "America/Detroit", "America/Menominee", "America/Montreal", "America/Thunder_Bay", "America/Nipigon", "America/Pangnirtung", "America/Iqaluit", "America/Cancun", "America/Antigua", "America/Nassau", "America/Cayman", "America/Santo_Domingo", "America/Port-au-Prince", "America/Jamaica", "America/Managua", "America/Panama", "America/Grand_Turk" });
        tempMap.put("EWT", new String[] { "America/New_York", "America/Indianapolis", "America/Indiana/Marengo", "America/Indiana/Vevay", "America/Louisville", "America/Detroit", "America/Jamaica" });
        tempMap.put("FFMT", new String[] { "America/Martinique" });
        tempMap.put("FJST", new String[] { "Pacific/Fiji" });
        tempMap.put("FJT", new String[] { "Pacific/Fiji" });
        tempMap.put("FKST", new String[] { "Atlantic/Stanley" });
        tempMap.put("FKT", new String[] { "Atlantic/Stanley" });
        tempMap.put("FMT", new String[] { "Atlantic/Madeira", "Africa/Freetown" });
        tempMap.put("FNST", new String[] { "America/Noronha" });
        tempMap.put("FNT", new String[] { "America/Noronha" });
        tempMap.put("FRUST", new String[] { "Asia/Bishkek" });
        tempMap.put("FRUT", new String[] { "Asia/Bishkek" });
        tempMap.put("GALT", new String[] { "Pacific/Galapagos" });
        tempMap.put("GAMT", new String[] { "Pacific/Gambier" });
        tempMap.put("GBGT", new String[] { "America/Guyana" });
        tempMap.put("GEST", new String[] { "Asia/Tbilisi" });
        tempMap.put("GET", new String[] { "Asia/Tbilisi" });
        tempMap.put("GFT", new String[] { "America/Cayenne" });
        tempMap.put("GHST", new String[] { "Africa/Accra" });
        tempMap.put("GILT", new String[] { "Pacific/Tarawa" });
        tempMap.put("GMT", new String[] { "Atlantic/St_Helena", "Atlantic/Reykjavik", "Europe/London", "Europe/Belfast", "Europe/Dublin", "Europe/Gibraltar", "Africa/Porto-Novo", "Africa/Ouagadougou", "Africa/Abidjan", "Africa/Malabo", "Africa/Banjul", "Africa/Accra", "Africa/Conakry", "Africa/Bissau", "Africa/Monrovia", "Africa/Bamako", "Africa/Timbuktu", "Africa/Nouakchott", "Africa/Niamey", "Africa/Sao_Tome", "Africa/Dakar", "Africa/Freetown", "Africa/Lome" });
        tempMap.put("GST", new String[] { "Atlantic/South_Georgia", "Asia/Bahrain", "Asia/Muscat", "Asia/Qatar", "Asia/Dubai", "Pacific/Guam" });
        tempMap.put("GYT", new String[] { "America/Guyana" });
        tempMap.put("HADT", new String[] { "America/Adak" });
        tempMap.put("HART", new String[] { "Asia/Harbin" });
        tempMap.put("HAST", new String[] { "America/Adak" });
        tempMap.put("HAWT", new String[] { "America/Adak" });
        tempMap.put("HDT", new String[] { "Pacific/Honolulu" });
        tempMap.put("HKST", new String[] { "Asia/Hong_Kong" });
        tempMap.put("HKT", new String[] { "Asia/Hong_Kong" });
        tempMap.put("HMT", new String[] { "Atlantic/Azores", "Europe/Helsinki", "Asia/Dacca", "Asia/Calcutta", "America/Havana" });
        tempMap.put("HOVST", new String[] { "Asia/Hovd" });
        tempMap.put("HOVT", new String[] { "Asia/Hovd" });
        tempMap.put("HST", new String[] { "Pacific/Johnston", "Pacific/Honolulu" });
        tempMap.put("HWT", new String[] { "Pacific/Honolulu" });
        tempMap.put("ICT", new String[] { "Asia/Phnom_Penh", "Asia/Vientiane", "Asia/Bangkok", "Asia/Saigon" });
        tempMap.put("IDDT", new String[] { "Asia/Jerusalem", "Asia/Gaza" });
        tempMap.put("IDT", new String[] { "Asia/Jerusalem", "Asia/Gaza" });
        tempMap.put("IHST", new String[] { "Asia/Colombo" });
        tempMap.put("IMT", new String[] { "Europe/Sofia", "Europe/Istanbul", "Asia/Irkutsk" });
        tempMap.put("IOT", new String[] { "Indian/Chagos" });
        tempMap.put("IRKMT", new String[] { "Asia/Irkutsk" });
        tempMap.put("IRKST", new String[] { "Asia/Irkutsk" });
        tempMap.put("IRKT", new String[] { "Asia/Irkutsk" });
        tempMap.put("IRST", new String[] { "Asia/Tehran" });
        tempMap.put("IRT", new String[] { "Asia/Tehran" });
        tempMap.put("ISST", new String[] { "Atlantic/Reykjavik" });
        tempMap.put("IST", new String[] { "Atlantic/Reykjavik", "Europe/Belfast", "Europe/Dublin", "Asia/Dacca", "Asia/Thimbu", "Asia/Calcutta", "Asia/Jerusalem", "Asia/Katmandu", "Asia/Karachi", "Asia/Gaza", "Asia/Colombo" });
        tempMap.put("JAYT", new String[] { "Asia/Jayapura" });
        tempMap.put("JMT", new String[] { "Atlantic/St_Helena", "Asia/Jerusalem" });
        tempMap.put("JST", new String[] { "Asia/Rangoon", "Asia/Dili", "Asia/Ujung_Pandang", "Asia/Tokyo", "Asia/Kuala_Lumpur", "Asia/Kuching", "Asia/Manila", "Asia/Singapore", "Pacific/Nauru" });
        tempMap.put("KART", new String[] { "Asia/Karachi" });
        tempMap.put("KAST", new String[] { "Asia/Kashgar" });
        tempMap.put("KDT", new String[] { "Asia/Seoul" });
        tempMap.put("KGST", new String[] { "Asia/Bishkek" });
        tempMap.put("KGT", new String[] { "Asia/Bishkek" });
        tempMap.put("KMT", new String[] { "Europe/Vilnius", "Europe/Kiev", "America/Cayman", "America/Jamaica", "America/St_Vincent", "America/Grand_Turk" });
        tempMap.put("KOST", new String[] { "Pacific/Kosrae" });
        tempMap.put("KRAMT", new String[] { "Asia/Krasnoyarsk" });
        tempMap.put("KRAST", new String[] { "Asia/Krasnoyarsk" });
        tempMap.put("KRAT", new String[] { "Asia/Krasnoyarsk" });
        tempMap.put("KST", new String[] { "Asia/Seoul", "Asia/Pyongyang" });
        tempMap.put("KUYMT", new String[] { "Europe/Samara" });
        tempMap.put("KUYST", new String[] { "Europe/Samara" });
        tempMap.put("KUYT", new String[] { "Europe/Samara" });
        tempMap.put("KWAT", new String[] { "Pacific/Kwajalein" });
        tempMap.put("LHST", new String[] { "Australia/Lord_Howe" });
        tempMap.put("LINT", new String[] { "Pacific/Kiritimati" });
        tempMap.put("LKT", new String[] { "Asia/Colombo" });
        tempMap.put("LPMT", new String[] { "America/La_Paz" });
        tempMap.put("LRT", new String[] { "Africa/Monrovia" });
        tempMap.put("LST", new String[] { "Europe/Riga" });
        tempMap.put("M", new String[] { "Europe/Moscow" });
        tempMap.put("MADST", new String[] { "Atlantic/Madeira" });
        tempMap.put("MAGMT", new String[] { "Asia/Magadan" });
        tempMap.put("MAGST", new String[] { "Asia/Magadan" });
        tempMap.put("MAGT", new String[] { "Asia/Magadan" });
        tempMap.put("MALT", new String[] { "Asia/Kuala_Lumpur", "Asia/Singapore" });
        tempMap.put("MART", new String[] { "Pacific/Marquesas" });
        tempMap.put("MAWT", new String[] { "Antarctica/Mawson" });
        tempMap.put("MDDT", new String[] { "America/Cambridge_Bay", "America/Yellowknife", "America/Inuvik" });
        tempMap.put("MDST", new String[] { "Europe/Moscow" });
        tempMap.put("MDT", new String[] { "America/Denver", "America/Phoenix", "America/Boise", "America/Regina", "America/Swift_Current", "America/Edmonton", "America/Cambridge_Bay", "America/Yellowknife", "America/Inuvik", "America/Chihuahua", "America/Hermosillo", "America/Mazatlan" });
        tempMap.put("MET", new String[] { "Europe/Tirane", "Europe/Andorra", "Europe/Vienna", "Europe/Minsk", "Europe/Brussels", "Europe/Sofia", "Europe/Prague", "Europe/Copenhagen", "Europe/Tallinn", "Europe/Berlin", "Europe/Gibraltar", "Europe/Athens", "Europe/Budapest", "Europe/Rome", "Europe/Riga", "Europe/Vaduz", "Europe/Vilnius", "Europe/Luxembourg", "Europe/Malta", "Europe/Chisinau", "Europe/Tiraspol", "Europe/Monaco", "Europe/Amsterdam", "Europe/Oslo", "Europe/Warsaw", "Europe/Lisbon", "Europe/Kaliningrad", "Europe/Madrid", "Europe/Stockholm", "Europe/Zurich", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol", "Europe/Belgrade", "Africa/Algiers", "Africa/Tripoli", "Africa/Casablanca", "Africa/Tunis", "Africa/Ceuta" });
        tempMap.put("MHT", new String[] { "Pacific/Majuro", "Pacific/Kwajalein" });
        tempMap.put("MMT", new String[] { "Indian/Maldives", "Europe/Minsk", "Europe/Moscow", "Asia/Rangoon", "Asia/Ujung_Pandang", "Asia/Colombo", "Pacific/Easter", "Africa/Monrovia", "America/Managua", "America/Montevideo" });
        tempMap.put("MOST", new String[] { "Asia/Macao" });
        tempMap.put("MOT", new String[] { "Asia/Macao" });
        tempMap.put("MPT", new String[] { "Pacific/Saipan" });
        tempMap.put("MSK", new String[] { "Europe/Minsk", "Europe/Tallinn", "Europe/Riga", "Europe/Vilnius", "Europe/Chisinau", "Europe/Kiev", "Europe/Uzhgorod", "Europe/Zaporozhye", "Europe/Simferopol" });
        tempMap.put("MST", new String[] { "Europe/Moscow", "America/Denver", "America/Phoenix", "America/Boise", "America/Regina", "America/Swift_Current", "America/Edmonton", "America/Dawson_Creek", "America/Cambridge_Bay", "America/Yellowknife", "America/Inuvik", "America/Mexico_City", "America/Chihuahua", "America/Hermosillo", "America/Mazatlan", "America/Tijuana" });
        tempMap.put("MUT", new String[] { "Indian/Mauritius" });
        tempMap.put("MVT", new String[] { "Indian/Maldives" });
        tempMap.put("MWT", new String[] { "America/Denver", "America/Phoenix", "America/Boise" });
        tempMap.put("MYT", new String[] { "Asia/Kuala_Lumpur", "Asia/Kuching" });
        tempMap.put("NCST", new String[] { "Pacific/Noumea" });
        tempMap.put("NCT", new String[] { "Pacific/Noumea" });
        tempMap.put("NDT", new String[] { "America/Nome", "America/Adak", "America/St_Johns", "America/Goose_Bay" });
        tempMap.put("NEGT", new String[] { "America/Paramaribo" });
        tempMap.put("NFT", new String[] { "Europe/Paris", "Europe/Oslo", "Pacific/Norfolk" });
        tempMap.put("NMT", new String[] { "Pacific/Norfolk" });
        tempMap.put("NOVMT", new String[] { "Asia/Novosibirsk" });
        tempMap.put("NOVST", new String[] { "Asia/Novosibirsk" });
        tempMap.put("NOVT", new String[] { "Asia/Novosibirsk" });
        tempMap.put("NPT", new String[] { "Asia/Katmandu" });
        tempMap.put("NRT", new String[] { "Pacific/Nauru" });
        tempMap.put("NST", new String[] { "Europe/Amsterdam", "Pacific/Pago_Pago", "Pacific/Midway", "America/Nome", "America/Adak", "America/St_Johns", "America/Goose_Bay" });
        tempMap.put("NUT", new String[] { "Pacific/Niue" });
        tempMap.put("NWT", new String[] { "America/Nome", "America/Adak" });
        tempMap.put("NZDT", new String[] { "Antarctica/McMurdo" });
        tempMap.put("NZHDT", new String[] { "Pacific/Auckland" });
        tempMap.put("NZST", new String[] { "Antarctica/McMurdo", "Pacific/Auckland" });
        tempMap.put("OMSMT", new String[] { "Asia/Omsk" });
        tempMap.put("OMSST", new String[] { "Asia/Omsk" });
        tempMap.put("OMST", new String[] { "Asia/Omsk" });
        tempMap.put("PDDT", new String[] { "America/Inuvik", "America/Whitehorse", "America/Dawson" });
        tempMap.put("PDT", new String[] { "America/Los_Angeles", "America/Juneau", "America/Boise", "America/Vancouver", "America/Dawson_Creek", "America/Inuvik", "America/Whitehorse", "America/Dawson", "America/Tijuana" });
        tempMap.put("PEST", new String[] { "America/Lima" });
        tempMap.put("PET", new String[] { "America/Lima" });
        tempMap.put("PETMT", new String[] { "Asia/Kamchatka" });
        tempMap.put("PETST", new String[] { "Asia/Kamchatka" });
        tempMap.put("PETT", new String[] { "Asia/Kamchatka" });
        tempMap.put("PGT", new String[] { "Pacific/Port_Moresby" });
        tempMap.put("PHOT", new String[] { "Pacific/Enderbury" });
        tempMap.put("PHST", new String[] { "Asia/Manila" });
        tempMap.put("PHT", new String[] { "Asia/Manila" });
        tempMap.put("PKT", new String[] { "Asia/Karachi" });
        tempMap.put("PMDT", new String[] { "America/Miquelon" });
        tempMap.put("PMMT", new String[] { "Pacific/Port_Moresby" });
        tempMap.put("PMST", new String[] { "America/Miquelon" });
        tempMap.put("PMT", new String[] { "Antarctica/DumontDUrville", "Europe/Prague", "Europe/Paris", "Europe/Monaco", "Africa/Algiers", "Africa/Tunis", "America/Panama", "America/Paramaribo" });
        tempMap.put("PNT", new String[] { "Pacific/Pitcairn" });
        tempMap.put("PONT", new String[] { "Pacific/Ponape" });
        tempMap.put("PPMT", new String[] { "America/Port-au-Prince" });
        tempMap.put("PST", new String[] { "Pacific/Pitcairn", "America/Los_Angeles", "America/Juneau", "America/Boise", "America/Vancouver", "America/Dawson_Creek", "America/Inuvik", "America/Whitehorse", "America/Dawson", "America/Hermosillo", "America/Mazatlan", "America/Tijuana" });
        tempMap.put("PWT", new String[] { "Pacific/Palau", "America/Los_Angeles", "America/Juneau", "America/Boise", "America/Tijuana" });
        tempMap.put("PYST", new String[] { "America/Asuncion" });
        tempMap.put("PYT", new String[] { "America/Asuncion" });
        tempMap.put("QMT", new String[] { "America/Guayaquil" });
        tempMap.put("RET", new String[] { "Indian/Reunion" });
        tempMap.put("RMT", new String[] { "Atlantic/Reykjavik", "Europe/Rome", "Europe/Riga", "Asia/Rangoon" });
        tempMap.put("S", new String[] { "Europe/Moscow" });
        tempMap.put("SAMMT", new String[] { "Europe/Samara" });
        tempMap.put("SAMST", new String[] { "Europe/Samara", "Asia/Samarkand" });
        tempMap.put("SAMT", new String[] { "Europe/Samara", "Asia/Samarkand", "Pacific/Pago_Pago", "Pacific/Apia" });
        tempMap.put("SAST", new String[] { "Africa/Maseru", "Africa/Windhoek", "Africa/Johannesburg", "Africa/Mbabane" });
        tempMap.put("SBT", new String[] { "Pacific/Guadalcanal" });
        tempMap.put("SCT", new String[] { "Indian/Mahe" });
        tempMap.put("SDMT", new String[] { "America/Santo_Domingo" });
        tempMap.put("SGT", new String[] { "Asia/Singapore" });
        tempMap.put("SHEST", new String[] { "Asia/Aqtau" });
        tempMap.put("SHET", new String[] { "Asia/Aqtau" });
        tempMap.put("SJMT", new String[] { "America/Costa_Rica" });
        tempMap.put("SLST", new String[] { "Africa/Freetown" });
        tempMap.put("SMT", new String[] { "Atlantic/Stanley", "Europe/Stockholm", "Europe/Simferopol", "Asia/Phnom_Penh", "Asia/Vientiane", "Asia/Kuala_Lumpur", "Asia/Singapore", "Asia/Saigon", "America/Santiago" });
        tempMap.put("SRT", new String[] { "America/Paramaribo" });
        tempMap.put("SST", new String[] { "Pacific/Pago_Pago", "Pacific/Midway" });
        tempMap.put("SVEMT", new String[] { "Asia/Yekaterinburg" });
        tempMap.put("SVEST", new String[] { "Asia/Yekaterinburg" });
        tempMap.put("SVET", new String[] { "Asia/Yekaterinburg" });
        tempMap.put("SWAT", new String[] { "Africa/Windhoek" });
        tempMap.put("SYOT", new String[] { "Antarctica/Syowa" });
        tempMap.put("TAHT", new String[] { "Pacific/Tahiti" });
        tempMap.put("TASST", new String[] { "Asia/Samarkand", "Asia/Tashkent" });
        tempMap.put("TAST", new String[] { "Asia/Samarkand", "Asia/Tashkent" });
        tempMap.put("TBIST", new String[] { "Asia/Tbilisi" });
        tempMap.put("TBIT", new String[] { "Asia/Tbilisi" });
        tempMap.put("TBMT", new String[] { "Asia/Tbilisi" });
        tempMap.put("TFT", new String[] { "Indian/Kerguelen" });
        tempMap.put("TJT", new String[] { "Asia/Dushanbe" });
        tempMap.put("TKT", new String[] { "Pacific/Fakaofo" });
        tempMap.put("TMST", new String[] { "Asia/Ashkhabad" });
        tempMap.put("TMT", new String[] { "Europe/Tallinn", "Asia/Tehran", "Asia/Ashkhabad" });
        tempMap.put("TOST", new String[] { "Pacific/Tongatapu" });
        tempMap.put("TOT", new String[] { "Pacific/Tongatapu" });
        tempMap.put("TPT", new String[] { "Asia/Dili" });
        tempMap.put("TRST", new String[] { "Europe/Istanbul" });
        tempMap.put("TRT", new String[] { "Europe/Istanbul" });
        tempMap.put("TRUT", new String[] { "Pacific/Truk" });
        tempMap.put("TVT", new String[] { "Pacific/Funafuti" });
        tempMap.put("ULAST", new String[] { "Asia/Ulaanbaatar" });
        tempMap.put("ULAT", new String[] { "Asia/Ulaanbaatar" });
        tempMap.put("URUT", new String[] { "Asia/Urumqi" });
        tempMap.put("UYHST", new String[] { "America/Montevideo" });
        tempMap.put("UYT", new String[] { "America/Montevideo" });
        tempMap.put("UZST", new String[] { "Asia/Samarkand", "Asia/Tashkent" });
        tempMap.put("UZT", new String[] { "Asia/Samarkand", "Asia/Tashkent" });
        tempMap.put("VET", new String[] { "America/Caracas" });
        tempMap.put("VLAMT", new String[] { "Asia/Vladivostok" });
        tempMap.put("VLAST", new String[] { "Asia/Vladivostok" });
        tempMap.put("VLAT", new String[] { "Asia/Vladivostok" });
        tempMap.put("VUST", new String[] { "Pacific/Efate" });
        tempMap.put("VUT", new String[] { "Pacific/Efate" });
        tempMap.put("WAKT", new String[] { "Pacific/Wake" });
        tempMap.put("WARST", new String[] { "America/Jujuy", "America/Mendoza" });
        tempMap.put("WART", new String[] { "America/Jujuy", "America/Mendoza" });
        tempMap.put("WAST", new String[] { "Africa/Ndjamena", "Africa/Windhoek" });
        tempMap.put("WAT", new String[] { "Africa/Luanda", "Africa/Porto-Novo", "Africa/Douala", "Africa/Bangui", "Africa/Ndjamena", "Africa/Kinshasa", "Africa/Brazzaville", "Africa/Malabo", "Africa/Libreville", "Africa/Banjul", "Africa/Conakry", "Africa/Bissau", "Africa/Bamako", "Africa/Nouakchott", "Africa/El_Aaiun", "Africa/Windhoek", "Africa/Niamey", "Africa/Lagos", "Africa/Dakar", "Africa/Freetown" });
        tempMap.put("WEST", new String[] { "Atlantic/Faeroe", "Atlantic/Azores", "Atlantic/Madeira", "Atlantic/Canary", "Europe/Brussels", "Europe/Luxembourg", "Europe/Monaco", "Europe/Lisbon", "Europe/Madrid", "Africa/Algiers", "Africa/Casablanca", "Africa/Ceuta" });
        tempMap.put("WET", new String[] { "Atlantic/Faeroe", "Atlantic/Azores", "Atlantic/Madeira", "Atlantic/Canary", "Europe/Andorra", "Europe/Brussels", "Europe/Luxembourg", "Europe/Monaco", "Europe/Lisbon", "Europe/Madrid", "Africa/Algiers", "Africa/Casablanca", "Africa/El_Aaiun", "Africa/Ceuta" });
        tempMap.put("WFT", new String[] { "Pacific/Wallis" });
        tempMap.put("WGST", new String[] { "America/Godthab" });
        tempMap.put("WGT", new String[] { "America/Godthab" });
        tempMap.put("WMT", new String[] { "Europe/Vilnius", "Europe/Warsaw" });
        tempMap.put("WST", new String[] { "Antarctica/Casey", "Pacific/Apia", "Australia/Perth" });
        tempMap.put("YAKMT", new String[] { "Asia/Yakutsk" });
        tempMap.put("YAKST", new String[] { "Asia/Yakutsk" });
        tempMap.put("YAKT", new String[] { "Asia/Yakutsk" });
        tempMap.put("YAPT", new String[] { "Pacific/Yap" });
        tempMap.put("YDDT", new String[] { "America/Whitehorse", "America/Dawson" });
        tempMap.put("YDT", new String[] { "America/Yakutat", "America/Whitehorse", "America/Dawson" });
        tempMap.put("YEKMT", new String[] { "Asia/Yekaterinburg" });
        tempMap.put("YEKST", new String[] { "Asia/Yekaterinburg" });
        tempMap.put("YEKT", new String[] { "Asia/Yekaterinburg" });
        tempMap.put("YERST", new String[] { "Asia/Yerevan" });
        tempMap.put("YERT", new String[] { "Asia/Yerevan" });
        tempMap.put("YST", new String[] { "America/Yakutat", "America/Whitehorse", "America/Dawson" });
        tempMap.put("YWT", new String[] { "America/Yakutat" });
        ABBREVIATED_TIMEZONES = Collections.unmodifiableMap((Map<?, ?>)tempMap);
    }
}
