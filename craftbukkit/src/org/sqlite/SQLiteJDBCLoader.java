// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.net.URL;
import java.util.Properties;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.io.ByteArrayOutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SQLiteJDBCLoader
{
    private static boolean extracted;
    
    public static boolean initialize() {
        loadSQLiteNativeLibrary();
        return SQLiteJDBCLoader.extracted;
    }
    
    static boolean getPureJavaFlag() {
        return Boolean.parseBoolean(System.getProperty("sqlite.purejava", "false"));
    }
    
    public static boolean isPureJavaMode() {
        return !isNativeMode();
    }
    
    public static boolean isNativeMode() {
        if (getPureJavaFlag()) {
            return false;
        }
        initialize();
        return SQLiteJDBCLoader.extracted;
    }
    
    static String md5sum(final InputStream input) throws IOException {
        final BufferedInputStream in = new BufferedInputStream(input);
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final DigestInputStream digestInputStream = new DigestInputStream(in, digest);
            while (digestInputStream.read() >= 0) {}
            final ByteArrayOutputStream md5out = new ByteArrayOutputStream();
            md5out.write(digest.digest());
            return md5out.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm is not available: " + e);
        }
        finally {
            in.close();
        }
    }
    
    private static boolean extractAndLoadLibraryFile(final String libFolderForCurrentOS, final String libraryFileName, final String targetFolder) {
        final String nativeLibraryFilePath = libFolderForCurrentOS + "/" + libraryFileName;
        final String prefix = "sqlite-" + getVersion() + "-";
        final String extractedLibFileName = prefix + libraryFileName;
        final File extractedLibFile = new File(targetFolder, extractedLibFileName);
        try {
            if (extractedLibFile.exists()) {
                final String md5sum1 = md5sum(SQLiteJDBCLoader.class.getResourceAsStream(nativeLibraryFilePath));
                final String md5sum2 = md5sum(new FileInputStream(extractedLibFile));
                if (md5sum1.equals(md5sum2)) {
                    return loadNativeLibrary(targetFolder, extractedLibFileName);
                }
                final boolean deletionSucceeded = extractedLibFile.delete();
                if (!deletionSucceeded) {
                    throw new IOException("failed to remove existing native library file: " + extractedLibFile.getAbsolutePath());
                }
            }
            final InputStream reader = SQLiteJDBCLoader.class.getResourceAsStream(nativeLibraryFilePath);
            final FileOutputStream writer = new FileOutputStream(extractedLibFile);
            final byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, bytesRead);
            }
            writer.close();
            reader.close();
            if (!System.getProperty("os.name").contains("Windows")) {
                try {
                    Runtime.getRuntime().exec(new String[] { "chmod", "755", extractedLibFile.getAbsolutePath() }).waitFor();
                }
                catch (Throwable t) {}
            }
            return loadNativeLibrary(targetFolder, extractedLibFileName);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    
    private static synchronized boolean loadNativeLibrary(final String path, final String name) {
        final File libPath = new File(path, name);
        if (libPath.exists()) {
            try {
                System.load(new File(path, name).getAbsolutePath());
                return true;
            }
            catch (UnsatisfiedLinkError e) {
                System.err.println(e);
                return false;
            }
        }
        return false;
    }
    
    private static void loadSQLiteNativeLibrary() {
        if (SQLiteJDBCLoader.extracted) {
            return;
        }
        final boolean runInPureJavaMode = getPureJavaFlag();
        if (runInPureJavaMode) {
            SQLiteJDBCLoader.extracted = false;
            return;
        }
        String sqliteNativeLibraryPath = System.getProperty("org.sqlite.lib.path");
        String sqliteNativeLibraryName = System.getProperty("org.sqlite.lib.name");
        if (sqliteNativeLibraryName == null) {
            sqliteNativeLibraryName = System.mapLibraryName("sqlitejdbc");
        }
        if (sqliteNativeLibraryPath != null && loadNativeLibrary(sqliteNativeLibraryPath, sqliteNativeLibraryName)) {
            SQLiteJDBCLoader.extracted = true;
            return;
        }
        sqliteNativeLibraryPath = "/native/" + OSInfo.getNativeLibFolderPathForCurrentOS();
        if (SQLiteJDBCLoader.class.getResource(sqliteNativeLibraryPath + "/" + sqliteNativeLibraryName) == null) {
            return;
        }
        final String tempFolder = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        if (extractAndLoadLibraryFile(sqliteNativeLibraryPath, sqliteNativeLibraryName, tempFolder)) {
            SQLiteJDBCLoader.extracted = true;
            return;
        }
        SQLiteJDBCLoader.extracted = false;
    }
    
    private static void getNativeLibraryFolderForTheCurrentOS() {
        final String osName = OSInfo.getOSName();
        final String archName = OSInfo.getArchName();
    }
    
    public static int getMajorVersion() {
        final String[] c = getVersion().split("\\.");
        return (c.length > 0) ? Integer.parseInt(c[0]) : 1;
    }
    
    public static int getMinorVersion() {
        final String[] c = getVersion().split("\\.");
        return (c.length > 1) ? Integer.parseInt(c[1]) : 0;
    }
    
    public static String getVersion() {
        URL versionFile = SQLiteJDBCLoader.class.getResource("/META-INF/maven/org.xerial/sqlite-jdbc/pom.properties");
        if (versionFile == null) {
            versionFile = SQLiteJDBCLoader.class.getResource("/META-INF/maven/org.xerial/sqlite-jdbc/VERSION");
        }
        String version = "unknown";
        try {
            if (versionFile != null) {
                final Properties versionData = new Properties();
                versionData.load(versionFile.openStream());
                version = versionData.getProperty("version", version);
                version = version.trim().replaceAll("[^0-9\\.]", "");
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
        return version;
    }
    
    static {
        SQLiteJDBCLoader.extracted = false;
    }
}
