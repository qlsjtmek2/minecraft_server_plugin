// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class PostgresDbEncrypt extends AbstractDbEncrypt
{
    public PostgresDbEncrypt() {
        this.varcharEncryptFunction = new PgVarcharFunction();
        this.dateEncryptFunction = new PgDateFunction();
    }
    
    private static class PgVarcharFunction implements DbEncryptFunction
    {
        public String getDecryptSql(final String columnWithTableAlias) {
            return "pgp_sym_decrypt(" + columnWithTableAlias + ",?)";
        }
        
        public String getEncryptBindSql() {
            return "pgp_sym_encrypt(?,?)";
        }
    }
    
    private static class PgDateFunction implements DbEncryptFunction
    {
        public String getDecryptSql(final String columnWithTableAlias) {
            return "to_date(pgp_sym_decrypt(" + columnWithTableAlias + ",?),'YYYYMMDD')";
        }
        
        public String getEncryptBindSql() {
            return "pgp_sym_encrypt(to_char(?::date,'YYYYMMDD'),?)";
        }
    }
}
