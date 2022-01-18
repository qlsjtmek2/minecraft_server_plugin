// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.security.spec.AlgorithmParameterSpec;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebean.config.Encryptor;

public class SimpleAesEncryptor implements Encryptor
{
    private static final String AES_CIPHER = "AES/CBC/PKCS5Padding";
    private static final String padding = "asldkalsdkadsdfkjsldfjl";
    
    private String paddKey(final EncryptKey encryptKey) {
        final String key = encryptKey.getStringValue();
        final int addChars = 16 - key.length();
        if (addChars < 0) {
            return key.substring(0, 16);
        }
        if (addChars > 0) {
            return key + "asldkalsdkadsdfkjsldfjl".substring(0, addChars);
        }
        return key;
    }
    
    private byte[] getKeyBytes(final String skey) {
        try {
            return skey.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    private IvParameterSpec getIvParameterSpec(final String initialVector) {
        return new IvParameterSpec(initialVector.getBytes());
    }
    
    public byte[] decrypt(final byte[] data, final EncryptKey encryptKey) {
        if (data == null) {
            return null;
        }
        final String key = this.paddKey(encryptKey);
        try {
            final byte[] keyBytes = this.getKeyBytes(key);
            final IvParameterSpec iv = this.getIvParameterSpec(key);
            final SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
            final Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(2, sks, iv);
            return c.doFinal(data);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] encrypt(final byte[] data, final EncryptKey encryptKey) {
        if (data == null) {
            return null;
        }
        final String key = this.paddKey(encryptKey);
        try {
            final byte[] keyBytes = this.getKeyBytes(key);
            final IvParameterSpec iv = this.getIvParameterSpec(key);
            final SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
            final Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(1, sks, iv);
            return c.doFinal(data);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String decryptString(final byte[] data, final EncryptKey key) {
        if (data == null) {
            return null;
        }
        final byte[] bytes = this.decrypt(data, key);
        try {
            return new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] encryptString(final String valueFormatValue, final EncryptKey key) {
        if (valueFormatValue == null) {
            return null;
        }
        try {
            final byte[] d = valueFormatValue.getBytes("UTF-8");
            return this.encrypt(d, key);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
