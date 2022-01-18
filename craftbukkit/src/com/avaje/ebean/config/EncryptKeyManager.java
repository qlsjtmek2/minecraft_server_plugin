// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public interface EncryptKeyManager
{
    void initialise();
    
    EncryptKey getEncryptKey(final String p0, final String p1);
}
