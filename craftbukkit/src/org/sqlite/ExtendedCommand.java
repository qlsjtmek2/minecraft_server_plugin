// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.SQLException;

public class ExtendedCommand
{
    public static SQLExtension parse(final String sql) throws SQLException {
        if (sql == null) {
            return null;
        }
        if (sql.startsWith("backup")) {
            return BackupCommand.parse(sql);
        }
        if (sql.startsWith("restore")) {
            return RestoreCommand.parse(sql);
        }
        return null;
    }
    
    public static String removeQuotation(final String s) {
        if (s == null) {
            return s;
        }
        if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
    
    public static class BackupCommand implements SQLExtension
    {
        public final String srcDB;
        public final String destFile;
        private static Pattern backupCmd;
        
        public BackupCommand(final String srcDB, final String destFile) {
            this.srcDB = srcDB;
            this.destFile = destFile;
        }
        
        public static BackupCommand parse(final String sql) throws SQLException {
            if (sql != null) {
                final Matcher m = BackupCommand.backupCmd.matcher(sql);
                if (m.matches()) {
                    String dbName = ExtendedCommand.removeQuotation(m.group(2));
                    final String dest = ExtendedCommand.removeQuotation(m.group(3));
                    if (dbName == null || dbName.length() == 0) {
                        dbName = "main";
                    }
                    return new BackupCommand(dbName, dest);
                }
            }
            throw new SQLException("syntax error: " + sql);
        }
        
        public void execute(final DB db) throws SQLException {
            db.backup(this.srcDB, this.destFile, null);
        }
        
        static {
            BackupCommand.backupCmd = Pattern.compile("backup(\\s+(\"[^\"]*\"|'[^']*'|\\S+))?\\s+to\\s+(\"[^\"]*\"|'[^']*'|\\S+)");
        }
    }
    
    public static class RestoreCommand implements SQLExtension
    {
        public final String targetDB;
        public final String srcFile;
        private static Pattern restoreCmd;
        
        public RestoreCommand(final String targetDB, final String srcFile) {
            this.targetDB = targetDB;
            this.srcFile = srcFile;
        }
        
        public static RestoreCommand parse(final String sql) throws SQLException {
            if (sql != null) {
                final Matcher m = RestoreCommand.restoreCmd.matcher(sql);
                if (m.matches()) {
                    String dbName = ExtendedCommand.removeQuotation(m.group(2));
                    final String dest = ExtendedCommand.removeQuotation(m.group(3));
                    if (dbName == null || dbName.length() == 0) {
                        dbName = "main";
                    }
                    return new RestoreCommand(dbName, dest);
                }
            }
            throw new SQLException("syntax error: " + sql);
        }
        
        public void execute(final DB db) throws SQLException {
            db.restore(this.targetDB, this.srcFile, null);
        }
        
        static {
            RestoreCommand.restoreCmd = Pattern.compile("restore(\\s+(\"[^\"]*\"|'[^']*'|\\S+))?\\s+from\\s+(\"[^\"]*\"|'[^']*'|\\S+)");
        }
    }
    
    public interface SQLExtension
    {
        void execute(final DB p0) throws SQLException;
    }
}
