// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.util;

import java.sql.ResultSet;
import java.sql.Connection;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Properties;
import com.mysql.jdbc.NonRegisteringDriver;

public class VersionFSHierarchyMaker
{
    public static void main(final String[] args) throws Exception {
        if (args.length < 3) {
            usage();
            System.exit(1);
        }
        String jdbcUrl = null;
        final String jvmVersion = removeWhitespaceChars(System.getProperty("java.version"));
        final String jvmVendor = removeWhitespaceChars(System.getProperty("java.vendor"));
        final String osName = removeWhitespaceChars(System.getProperty("os.name"));
        final String osArch = removeWhitespaceChars(System.getProperty("os.arch"));
        final String osVersion = removeWhitespaceChars(System.getProperty("os.version"));
        jdbcUrl = System.getProperty("com.mysql.jdbc.testsuite.url");
        String mysqlVersion = "not-available";
        try {
            final Connection conn = new NonRegisteringDriver().connect(jdbcUrl, null);
            final ResultSet rs = conn.createStatement().executeQuery("SELECT VERSION()");
            rs.next();
            mysqlVersion = removeWhitespaceChars(rs.getString(1));
        }
        catch (Throwable t) {
            mysqlVersion = "no-server-running-on-" + removeWhitespaceChars(jdbcUrl);
        }
        final String jvmSubdirName = jvmVendor + "-" + jvmVersion;
        final String osSubdirName = osName + "-" + osArch + "-" + osVersion;
        final File baseDir = new File(args[1]);
        final File mysqlVersionDir = new File(baseDir, mysqlVersion);
        final File osVersionDir = new File(mysqlVersionDir, osSubdirName);
        final File jvmVersionDir = new File(osVersionDir, jvmSubdirName);
        jvmVersionDir.mkdirs();
        FileOutputStream pathOut = null;
        try {
            final String propsOutputPath = args[2];
            pathOut = new FileOutputStream(propsOutputPath);
            final String baseDirStr = baseDir.getAbsolutePath();
            String jvmVersionDirStr = jvmVersionDir.getAbsolutePath();
            if (jvmVersionDirStr.startsWith(baseDirStr)) {
                jvmVersionDirStr = jvmVersionDirStr.substring(baseDirStr.length() + 1);
            }
            pathOut.write(jvmVersionDirStr.getBytes());
        }
        finally {
            if (pathOut != null) {
                pathOut.flush();
                pathOut.close();
            }
        }
    }
    
    public static String removeWhitespaceChars(final String input) {
        if (input == null) {
            return input;
        }
        final int strLen = input.length();
        final StringBuffer output = new StringBuffer(strLen);
        for (int i = 0; i < strLen; ++i) {
            final char c = input.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                if (Character.isWhitespace(c)) {
                    output.append("_");
                }
                else {
                    output.append(".");
                }
            }
            else {
                output.append(c);
            }
        }
        return output.toString();
    }
    
    private static void usage() {
        System.err.println("Creates a fs hierarchy representing MySQL version, OS version and JVM version.");
        System.err.println("Stores the full path as 'outputDirectory' property in file 'directoryPropPath'");
        System.err.println();
        System.err.println("Usage: java VersionFSHierarchyMaker unit|compliance baseDirectory directoryPropPath");
    }
}
