// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public class EncryptDeploy
{
    public static final EncryptDeploy NO_ENCRYPT;
    public static final EncryptDeploy ANNOTATION;
    public static final EncryptDeploy ENCRYPT_DB;
    public static final EncryptDeploy ENCRYPT_CLIENT;
    private final Mode mode;
    private final boolean dbEncrypt;
    private final int dbLength;
    
    public EncryptDeploy(final Mode mode, final boolean dbEncrypt, final int dbLength) {
        this.mode = mode;
        this.dbEncrypt = dbEncrypt;
        this.dbLength = dbLength;
    }
    
    public Mode getMode() {
        return this.mode;
    }
    
    public boolean isDbEncrypt() {
        return this.dbEncrypt;
    }
    
    public int getDbLength() {
        return this.dbLength;
    }
    
    static {
        NO_ENCRYPT = new EncryptDeploy(Mode.MODE_NO_ENCRYPT, true, 0);
        ANNOTATION = new EncryptDeploy(Mode.MODE_ANNOTATION, true, 0);
        ENCRYPT_DB = new EncryptDeploy(Mode.MODE_ENCRYPT, true, 0);
        ENCRYPT_CLIENT = new EncryptDeploy(Mode.MODE_ENCRYPT, false, 0);
    }
    
    public enum Mode
    {
        MODE_ENCRYPT, 
        MODE_NO_ENCRYPT, 
        MODE_ANNOTATION;
    }
}
