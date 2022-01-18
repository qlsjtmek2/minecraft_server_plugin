// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import java.util.logging.Level;
import javax.sql.DataSource;
import com.avaje.ebean.BackgroundExecutor;
import java.util.logging.Logger;

public class DatabasePlatform
{
    private static final Logger logger;
    protected String openQuote;
    protected String closeQuote;
    protected SqlLimiter sqlLimiter;
    protected DbTypeMap dbTypeMap;
    protected DbDdlSyntax dbDdlSyntax;
    protected DbIdentity dbIdentity;
    protected int booleanDbType;
    protected int blobDbType;
    protected int clobDbType;
    protected boolean treatEmptyStringsAsNull;
    protected String name;
    private static final char BACK_TICK = '`';
    protected DbEncrypt dbEncrypt;
    protected boolean idInExpandedForm;
    protected boolean selectCountWithAlias;
    
    public DatabasePlatform() {
        this.openQuote = "\"";
        this.closeQuote = "\"";
        this.sqlLimiter = new LimitOffsetSqlLimiter();
        this.dbTypeMap = new DbTypeMap();
        this.dbDdlSyntax = new DbDdlSyntax();
        this.dbIdentity = new DbIdentity();
        this.booleanDbType = 16;
        this.blobDbType = 2004;
        this.clobDbType = 2005;
        this.name = "generic";
    }
    
    public String getName() {
        return this.name;
    }
    
    public IdGenerator createSequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        return null;
    }
    
    public DbEncrypt getDbEncrypt() {
        return this.dbEncrypt;
    }
    
    public void setDbEncrypt(final DbEncrypt dbEncrypt) {
        this.dbEncrypt = dbEncrypt;
    }
    
    public DbTypeMap getDbTypeMap() {
        return this.dbTypeMap;
    }
    
    public DbDdlSyntax getDbDdlSyntax() {
        return this.dbDdlSyntax;
    }
    
    public String getCloseQuote() {
        return this.closeQuote;
    }
    
    public String getOpenQuote() {
        return this.openQuote;
    }
    
    public int getBooleanDbType() {
        return this.booleanDbType;
    }
    
    public int getBlobDbType() {
        return this.blobDbType;
    }
    
    public int getClobDbType() {
        return this.clobDbType;
    }
    
    public boolean isTreatEmptyStringsAsNull() {
        return this.treatEmptyStringsAsNull;
    }
    
    public boolean isIdInExpandedForm() {
        return this.idInExpandedForm;
    }
    
    public DbIdentity getDbIdentity() {
        return this.dbIdentity;
    }
    
    public SqlLimiter getSqlLimiter() {
        return this.sqlLimiter;
    }
    
    public String convertQuotedIdentifiers(final String dbName) {
        if (dbName != null && dbName.length() > 0 && dbName.charAt(0) == '`') {
            if (dbName.charAt(dbName.length() - 1) == '`') {
                String quotedName = this.getOpenQuote();
                quotedName += dbName.substring(1, dbName.length() - 1);
                quotedName += this.getCloseQuote();
                return quotedName;
            }
            DatabasePlatform.logger.log(Level.SEVERE, "Missing backquote on [" + dbName + "]");
        }
        return dbName;
    }
    
    public boolean isSelectCountWithAlias() {
        return this.selectCountWithAlias;
    }
    
    static {
        logger = Logger.getLogger(DatabasePlatform.class.getName());
    }
}
