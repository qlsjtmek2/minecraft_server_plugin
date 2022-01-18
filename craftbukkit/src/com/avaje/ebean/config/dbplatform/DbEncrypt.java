// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public interface DbEncrypt
{
    DbEncryptFunction getDbEncryptFunction(final int p0);
    
    int getEncryptDbType();
    
    boolean isBindEncryptDataFirst();
}
