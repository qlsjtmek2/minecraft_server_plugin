// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebean.config.Encryptor;
import com.avaje.ebean.config.EncryptKeyManager;

public class DataEncryptSupport
{
    private final EncryptKeyManager encryptKeyManager;
    private final Encryptor encryptor;
    private final String table;
    private final String column;
    
    public DataEncryptSupport(final EncryptKeyManager encryptKeyManager, final Encryptor encryptor, final String table, final String column) {
        this.encryptKeyManager = encryptKeyManager;
        this.encryptor = encryptor;
        this.table = table;
        this.column = column;
    }
    
    public byte[] encrypt(final byte[] data) {
        final EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
        return this.encryptor.encrypt(data, key);
    }
    
    public byte[] decrypt(final byte[] data) {
        final EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
        return this.encryptor.decrypt(data, key);
    }
    
    public String decryptObject(final byte[] data) {
        final EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
        return this.encryptor.decryptString(data, key);
    }
    
    public <T> byte[] encryptObject(final String formattedValue) {
        final EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
        return this.encryptor.encryptString(formattedValue, key);
    }
}
