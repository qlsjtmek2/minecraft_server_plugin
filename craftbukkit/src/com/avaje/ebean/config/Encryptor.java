// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public interface Encryptor
{
    byte[] encrypt(final byte[] p0, final EncryptKey p1);
    
    byte[] decrypt(final byte[] p0, final EncryptKey p1);
    
    byte[] encryptString(final String p0, final EncryptKey p1);
    
    String decryptString(final byte[] p0, final EncryptKey p1);
}
