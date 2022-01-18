// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

public class OSInfo
{
    public static void main(final String[] args) {
        if (args.length >= 1) {
            if ("--os".equals(args[0])) {
                System.out.print(getOSName());
                return;
            }
            if ("--arch".equals(args[0])) {
                System.out.print(getArchName());
                return;
            }
        }
        System.out.print(getNativeLibFolderPathForCurrentOS());
    }
    
    public static String getNativeLibFolderPathForCurrentOS() {
        return getOSName() + "/" + getArchName();
    }
    
    public static String getOSName() {
        return translateOSNameToFolderName(System.getProperty("os.name"));
    }
    
    public static String getArchName() {
        return translateArchNameToFolderName(System.getProperty("os.arch"));
    }
    
    public static String translateOSNameToFolderName(final String osName) {
        if (osName.contains("Windows")) {
            return "Windows";
        }
        if (osName.contains("Mac")) {
            return "Mac";
        }
        if (osName.contains("Linux")) {
            return "Linux";
        }
        return osName.replaceAll("\\W", "");
    }
    
    public static String translateArchNameToFolderName(final String archName) {
        return archName.replaceAll("\\W", "");
    }
}
