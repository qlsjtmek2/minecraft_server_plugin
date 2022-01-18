// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import com.avaje.ebean.config.GlobalProperties;

public class Oracle10DbEncrypt extends AbstractDbEncrypt
{
    public Oracle10DbEncrypt() {
        final String encryptfunction = GlobalProperties.get("ebean.oracle.encryptfunction", "eb_encrypt");
        final String decryptfunction = GlobalProperties.get("ebean.oracle.decryptfunction", "eb_decrypt");
        this.varcharEncryptFunction = new OraVarcharFunction(encryptfunction, decryptfunction);
        this.dateEncryptFunction = new OraDateFunction(encryptfunction, decryptfunction);
    }
    
    private static class OraVarcharFunction implements DbEncryptFunction
    {
        private final String encryptfunction;
        private final String decryptfunction;
        
        public OraVarcharFunction(final String encryptfunction, final String decryptfunction) {
            this.encryptfunction = encryptfunction;
            this.decryptfunction = decryptfunction;
        }
        
        public String getDecryptSql(final String columnWithTableAlias) {
            return this.decryptfunction + "(" + columnWithTableAlias + ",?)";
        }
        
        public String getEncryptBindSql() {
            return this.encryptfunction + "(?,?)";
        }
    }
    
    private static class OraDateFunction implements DbEncryptFunction
    {
        private final String encryptfunction;
        private final String decryptfunction;
        
        public OraDateFunction(final String encryptfunction, final String decryptfunction) {
            this.encryptfunction = encryptfunction;
            this.decryptfunction = decryptfunction;
        }
        
        public String getDecryptSql(final String columnWithTableAlias) {
            return "to_date(" + this.decryptfunction + "(" + columnWithTableAlias + ",?),'YYYYMMDD')";
        }
        
        public String getEncryptBindSql() {
            return this.encryptfunction + "(to_char(?,'YYYYMMDD'),?)";
        }
    }
}
