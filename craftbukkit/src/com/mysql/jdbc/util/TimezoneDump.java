// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.util;

import java.sql.ResultSet;
import com.mysql.jdbc.ExceptionInterceptor;
import com.mysql.jdbc.TimeUtil;
import java.sql.DriverManager;

public class TimezoneDump
{
    private static final String DEFAULT_URL = "jdbc:mysql:///test";
    
    public static void main(final String[] args) throws Exception {
        String jdbcUrl = "jdbc:mysql:///test";
        if (args.length == 1 && args[0] != null) {
            jdbcUrl = args[0];
        }
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        final ResultSet rs = DriverManager.getConnection(jdbcUrl).createStatement().executeQuery("SHOW VARIABLES LIKE 'timezone'");
        while (rs.next()) {
            final String timezoneFromServer = rs.getString(2);
            System.out.println("MySQL timezone name: " + timezoneFromServer);
            final String canonicalTimezone = TimeUtil.getCanoncialTimezone(timezoneFromServer, null);
            System.out.println("Java timezone name: " + canonicalTimezone);
        }
    }
}
