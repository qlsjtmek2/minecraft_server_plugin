// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public interface DbEncryptFunction
{
    String getDecryptSql(final String p0);
    
    String getEncryptBindSql();
}
