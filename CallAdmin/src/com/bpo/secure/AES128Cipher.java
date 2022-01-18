// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.secure;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES128Cipher
{
    private static String key;
    
    static {
        AES128Cipher.key = "";
    }
    
    public static void setKey(String str) {
        if (str.length() < 16) {
            while (str.length() < 16) {
                str = String.valueOf(str) + str;
            }
        }
        AES128Cipher.key = str.substring(0, 16);
    }
    
    public static byte[] hexToByteArray(final String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        final byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; ++i) {
            ba[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }
    
    public static String byteArrayToHex(final byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }
        final StringBuffer sb = new StringBuffer(ba.length * 2);
        for (int x = 0; x < ba.length; ++x) {
            final String hexNumber = "0" + Integer.toHexString(0xFF & ba[x]);
            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }
    
    public static String encrypt(final String message) {
        try {
            final SecretKeySpec skeySpec = new SecretKeySpec(AES128Cipher.key.getBytes(), "AES");
            final Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, skeySpec);
            final byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));
            return byteArrayToHex(encrypted);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String decrypt(final String encrypted) throws Exception {
        final SecretKeySpec skeySpec = new SecretKeySpec(AES128Cipher.key.getBytes(), "AES");
        final Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, skeySpec);
        final byte[] original = cipher.doFinal(hexToByteArray(encrypted));
        final String originalString = new String(original, "UTF-8");
        return originalString;
    }
}
