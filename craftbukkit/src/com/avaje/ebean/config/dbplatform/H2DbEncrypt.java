// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class H2DbEncrypt extends AbstractDbEncrypt
{
    public H2DbEncrypt() {
        this.varcharEncryptFunction = new H2VarcharFunction();
        this.dateEncryptFunction = new H2DateFunction();
    }
    
    public boolean isBindEncryptDataFirst() {
        return false;
    }
    
    private static class H2VarcharFunction implements DbEncryptFunction
    {
        public String getDecryptSql(final String columnWithTableAlias) {
            return "TRIM(CHAR(0) FROM UTF8TOSTRING(DECRYPT('AES', STRINGTOUTF8(?), " + columnWithTableAlias + ")))";
        }
        
        public String getEncryptBindSql() {
            return "ENCRYPT('AES', STRINGTOUTF8(?), STRINGTOUTF8(?))";
        }
    }
    
    private static class H2DateFunction implements DbEncryptFunction
    {
        public String getDecryptSql(final String columnWithTableAlias) {
            return "PARSEDATETIME(TRIM(CHAR(0) FROM UTF8TOSTRING(DECRYPT('AES', STRINGTOUTF8(?), " + columnWithTableAlias + "))),'yyyyMMdd')";
        }
        
        public String getEncryptBindSql() {
            return "ENCRYPT('AES', STRINGTOUTF8(?), STRINGTOUTF8(FORMATDATETIME(?,'yyyyMMdd')))";
        }
    }
}
