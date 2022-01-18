// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.util.Hashtable;
import java.sql.DriverPropertyInfo;
import java.util.Iterator;
import java.sql.Statement;
import java.util.HashSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;

public class SQLiteConfig
{
    private final Properties pragmaTable;
    private int openModeFlag;
    private static final String[] OnOff;
    
    public SQLiteConfig() {
        this(new Properties());
    }
    
    public SQLiteConfig(final Properties prop) {
        this.openModeFlag = 0;
        this.pragmaTable = prop;
        final String openMode = this.pragmaTable.getProperty(Pragma.OPEN_MODE.pragmaName);
        if (openMode != null) {
            this.openModeFlag = Integer.parseInt(openMode);
        }
        else {
            this.setOpenMode(SQLiteOpenMode.READWRITE);
            this.setOpenMode(SQLiteOpenMode.CREATE);
        }
    }
    
    public Connection createConnection(final String url) throws SQLException {
        return JDBC.createConnection(url, this.toProperties());
    }
    
    public void apply(final Connection conn) throws SQLException {
        final HashSet<String> pragmaParams = new HashSet<String>();
        for (final Pragma each : Pragma.values()) {
            pragmaParams.add(each.pragmaName);
        }
        pragmaParams.remove(Pragma.OPEN_MODE.pragmaName);
        pragmaParams.remove(Pragma.SHARED_CACHE.pragmaName);
        pragmaParams.remove(Pragma.LOAD_EXTENSION.pragmaName);
        final Statement stat = conn.createStatement();
        try {
            int count = 0;
            for (final Object each2 : ((Hashtable<Object, V>)this.pragmaTable).keySet()) {
                final String key = each2.toString();
                if (!pragmaParams.contains(key)) {
                    continue;
                }
                final String value = this.pragmaTable.getProperty(key);
                if (value == null) {
                    continue;
                }
                final String sql = String.format("pragma %s=%s", key, value);
                stat.addBatch(sql);
                ++count;
            }
            if (count > 0) {
                stat.executeBatch();
            }
        }
        finally {
            if (stat != null) {
                stat.close();
            }
        }
    }
    
    private void set(final Pragma pragma, final boolean flag) {
        this.setPragma(pragma, Boolean.toString(flag));
    }
    
    private void set(final Pragma pragma, final int num) {
        this.setPragma(pragma, Integer.toString(num));
    }
    
    private boolean getBoolean(final Pragma pragma, final String defaultValue) {
        return Boolean.parseBoolean(this.pragmaTable.getProperty(pragma.pragmaName, defaultValue));
    }
    
    public boolean isEnabledSharedCache() {
        return this.getBoolean(Pragma.SHARED_CACHE, "false");
    }
    
    public boolean isEnabledLoadExtension() {
        return this.getBoolean(Pragma.LOAD_EXTENSION, "false");
    }
    
    public int getOpenModeFlags() {
        return this.openModeFlag;
    }
    
    public void setPragma(final Pragma pragma, final String value) {
        ((Hashtable<String, String>)this.pragmaTable).put(pragma.pragmaName, value);
    }
    
    public Properties toProperties() {
        this.pragmaTable.setProperty(Pragma.OPEN_MODE.pragmaName, Integer.toString(this.openModeFlag));
        return this.pragmaTable;
    }
    
    static DriverPropertyInfo[] getDriverPropertyInfo() {
        final Pragma[] pragma = Pragma.values();
        final DriverPropertyInfo[] result = new DriverPropertyInfo[pragma.length];
        int index = 0;
        for (final Pragma p : Pragma.values()) {
            final DriverPropertyInfo di = new DriverPropertyInfo(p.pragmaName, null);
            di.choices = p.choices;
            di.description = p.description;
            di.required = false;
            result[index++] = di;
        }
        return result;
    }
    
    public void setOpenMode(final SQLiteOpenMode mode) {
        this.openModeFlag |= mode.flag;
    }
    
    public void resetOpenMode(final SQLiteOpenMode mode) {
        this.openModeFlag &= ~mode.flag;
    }
    
    public void setSharedCache(final boolean enable) {
        this.set(Pragma.SHARED_CACHE, enable);
    }
    
    public void enableLoadExtension(final boolean enable) {
        this.set(Pragma.LOAD_EXTENSION, enable);
    }
    
    public void setReadOnly(final boolean readOnly) {
        if (readOnly) {
            this.setOpenMode(SQLiteOpenMode.READONLY);
            this.resetOpenMode(SQLiteOpenMode.READWRITE);
        }
        else {
            this.setOpenMode(SQLiteOpenMode.READWRITE);
            this.resetOpenMode(SQLiteOpenMode.READONLY);
        }
    }
    
    public void setCacheSize(final int numberOfPages) {
        this.set(Pragma.CACHE_SIZE, numberOfPages);
    }
    
    public void enableCaseSensitiveLike(final boolean enable) {
        this.set(Pragma.CASE_SENSITIVE_LIKE, enable);
    }
    
    public void enableCountChanges(final boolean enable) {
        this.set(Pragma.COUNT_CHANGES, enable);
    }
    
    public void setDefaultCacheSize(final int numberOfPages) {
        this.set(Pragma.DEFAULT_CACHE_SIZE, numberOfPages);
    }
    
    public void enableEmptyResultCallBacks(final boolean enable) {
        this.set(Pragma.EMPTY_RESULT_CALLBACKS, enable);
    }
    
    private static String[] toStringArray(final PragmaValue[] list) {
        final String[] result = new String[list.length];
        for (int i = 0; i < list.length; ++i) {
            result[i] = list[i].getValue();
        }
        return result;
    }
    
    public void setEncoding(final Encoding encoding) {
        this.setPragma(Pragma.ENCODING, encoding.typeName);
    }
    
    public void enforceForeignKeys(final boolean enforce) {
        this.set(Pragma.FOREIGN_KEYS, enforce);
    }
    
    public void enableFullColumnNames(final boolean enable) {
        this.set(Pragma.FULL_COLUMN_NAMES, enable);
    }
    
    public void enableFullSync(final boolean enable) {
        this.set(Pragma.FULL_SYNC, enable);
    }
    
    public void incrementalVacuum(final int numberOfPagesToBeRemoved) {
        this.set(Pragma.INCREMENTAL_VACUUM, numberOfPagesToBeRemoved);
    }
    
    public void setJournalMode(final JournalMode mode) {
        this.setPragma(Pragma.JOURNAL_MODE, mode.name());
    }
    
    public void setJounalSizeLimit(final int limit) {
        this.set(Pragma.JOURNAL_SIZE_LIMIT, limit);
    }
    
    public void useLegacyFileFormat(final boolean use) {
        this.set(Pragma.LEGACY_FILE_FORMAT, use);
    }
    
    public void setLockingMode(final LockingMode mode) {
        this.setPragma(Pragma.LOCKING_MODE, mode.name());
    }
    
    public void setPageSize(final int numBytes) {
        this.set(Pragma.PAGE_SIZE, numBytes);
    }
    
    public void setMaxPageCount(final int numPages) {
        this.set(Pragma.MAX_PAGE_COUNT, numPages);
    }
    
    public void setReadUncommited(final boolean useReadUncommitedIsolationMode) {
        this.set(Pragma.READ_UNCOMMITED, useReadUncommitedIsolationMode);
    }
    
    public void enableRecursiveTriggers(final boolean enable) {
        this.set(Pragma.RECURSIVE_TRIGGERS, enable);
    }
    
    public void enableReverseUnorderedSelects(final boolean enable) {
        this.set(Pragma.REVERSE_UNORDERED_SELECTS, enable);
    }
    
    public void enableShortColumnNames(final boolean enable) {
        this.set(Pragma.SHORT_COLUMN_NAMES, enable);
    }
    
    public void setSynchronous(final SynchronousMode mode) {
        this.setPragma(Pragma.SYNCHRONOUS, mode.name());
    }
    
    public void setTempStore(final TempStore storeType) {
        this.setPragma(Pragma.TEMP_STORE, storeType.name());
    }
    
    public void setTempStoreDirectory(final String directoryName) {
        this.setPragma(Pragma.TEMP_STORE_DIRECTORY, String.format("'%s'", directoryName));
    }
    
    public void setUserVersion(final int version) {
        this.set(Pragma.USER_VERSION, version);
    }
    
    static {
        OnOff = new String[] { "true", "false" };
    }
    
    private enum Pragma
    {
        OPEN_MODE("open_mode", "Database open-mode flag", (String[])null), 
        SHARED_CACHE("shared_cache", "Enablse SQLite Shared-Cache mode, native driver only", SQLiteConfig.OnOff), 
        LOAD_EXTENSION("enable_load_extension", "Enable SQLite load_extention() function, native driver only", SQLiteConfig.OnOff), 
        CACHE_SIZE("cache_size"), 
        CASE_SENSITIVE_LIKE("case_sensitive_like", SQLiteConfig.OnOff), 
        COUNT_CHANGES("count_changes", SQLiteConfig.OnOff), 
        DEFAULT_CACHE_SIZE("default_cache_size"), 
        EMPTY_RESULT_CALLBACKS("empty_result_callback", SQLiteConfig.OnOff), 
        ENCODING("encoding", toStringArray(Encoding.values())), 
        FOREIGN_KEYS("foreign_keys", SQLiteConfig.OnOff), 
        FULL_COLUMN_NAMES("full_column_names", SQLiteConfig.OnOff), 
        FULL_SYNC("fullsync", SQLiteConfig.OnOff), 
        INCREMENTAL_VACUUM("incremental_vacuum"), 
        JOURNAL_MODE("journal_mode", toStringArray(JournalMode.values())), 
        JOURNAL_SIZE_LIMIT("journal_size_limit"), 
        LEGACY_FILE_FORMAT("legacy_file_format", SQLiteConfig.OnOff), 
        LOCKING_MODE("locking_mode", toStringArray(LockingMode.values())), 
        PAGE_SIZE("page_size"), 
        MAX_PAGE_COUNT("max_page_count"), 
        READ_UNCOMMITED("read_uncommited", SQLiteConfig.OnOff), 
        RECURSIVE_TRIGGERS("recursive_triggers", SQLiteConfig.OnOff), 
        REVERSE_UNORDERED_SELECTS("reverse_unordered_selects", SQLiteConfig.OnOff), 
        SHORT_COLUMN_NAMES("short_column_names", SQLiteConfig.OnOff), 
        SYNCHRONOUS("synchronous", toStringArray(SynchronousMode.values())), 
        TEMP_STORE("temp_store", toStringArray(TempStore.values())), 
        TEMP_STORE_DIRECTORY("temp_store_directory"), 
        USER_VERSION("user_version");
        
        public final String pragmaName;
        public final String[] choices;
        public final String description;
        
        private Pragma(final String pragmaName) {
            this(pragmaName, null);
        }
        
        private Pragma(final String pragmaName, final String[] choices) {
            this(pragmaName, null, null);
        }
        
        private Pragma(final String pragmaName, final String description, final String[] choices) {
            this.pragmaName = pragmaName;
            this.description = description;
            this.choices = choices;
        }
    }
    
    public enum Encoding implements PragmaValue
    {
        UTF8("UTF-8"), 
        UTF16("UTF-16"), 
        UTF16_LITTLE_ENDIAN("UTF-16le"), 
        UTF16_BIG_ENDIAN("UTF-16be");
        
        public final String typeName;
        
        private Encoding(final String typeName) {
            this.typeName = typeName;
        }
        
        public String getValue() {
            return this.typeName;
        }
    }
    
    public enum JournalMode implements PragmaValue
    {
        DELETE, 
        TRUNCATE, 
        PERSIST, 
        MEMORY, 
        OFF;
        
        public String getValue() {
            return this.name();
        }
    }
    
    public enum LockingMode implements PragmaValue
    {
        NORMAL, 
        EXCLUSIVE;
        
        public String getValue() {
            return this.name();
        }
    }
    
    public enum SynchronousMode implements PragmaValue
    {
        OFF, 
        NORMAL, 
        FULL;
        
        public String getValue() {
            return this.name();
        }
    }
    
    public enum TempStore implements PragmaValue
    {
        DEFAULT, 
        FILE, 
        MEMORY;
        
        public String getValue() {
            return this.name();
        }
    }
    
    private interface PragmaValue
    {
        String getValue();
    }
}
