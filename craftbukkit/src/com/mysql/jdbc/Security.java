// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Security
{
    private static final char PVERSION41_CHAR = '*';
    private static final int SHA1_HASH_SIZE = 20;
    
    private static int charVal(final char c) {
        return (c >= '0' && c <= '9') ? (c - '0') : ((c >= 'A' && c <= 'Z') ? (c - 'A' + '\n') : (c - 'a' + '\n'));
    }
    
    static byte[] createKeyFromOldPassword(String passwd) throws NoSuchAlgorithmException {
        passwd = makeScrambledPassword(passwd);
        final int[] salt = getSaltFromPassword(passwd);
        return getBinaryPassword(salt, false);
    }
    
    static byte[] getBinaryPassword(final int[] salt, final boolean usingNewPasswords) throws NoSuchAlgorithmException {
        int val = 0;
        final byte[] binaryPassword = new byte[20];
        if (usingNewPasswords) {
            int pos = 0;
            for (int i = 0; i < 4; ++i) {
                val = salt[i];
                for (int t = 3; t >= 0; --t) {
                    binaryPassword[pos++] = (byte)(val & 0xFF);
                    val >>= 8;
                }
            }
            return binaryPassword;
        }
        int offset = 0;
        for (int i = 0; i < 2; ++i) {
            val = salt[i];
            for (int t = 3; t >= 0; --t) {
                binaryPassword[t + offset] = (byte)(val % 256);
                val >>= 8;
            }
            offset += 4;
        }
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(binaryPassword, 0, 8);
        return md.digest();
    }
    
    private static int[] getSaltFromPassword(final String password) {
        final int[] result = new int[6];
        if (password == null || password.length() == 0) {
            return result;
        }
        if (password.charAt(0) == '*') {
            final String saltInHex = password.substring(1, 5);
            int val = 0;
            for (int i = 0; i < 4; ++i) {
                val = (val << 4) + charVal(saltInHex.charAt(i));
            }
            return result;
        }
        int resultPos = 0;
        int pos = 0;
        final int length = password.length();
        while (pos < length) {
            int val2 = 0;
            for (int j = 0; j < 8; ++j) {
                val2 = (val2 << 4) + charVal(password.charAt(pos++));
            }
            result[resultPos++] = val2;
        }
        return result;
    }
    
    private static String longToHex(final long val) {
        final String longHex = Long.toHexString(val);
        final int length = longHex.length();
        if (length < 8) {
            final int padding = 8 - length;
            final StringBuffer buf = new StringBuffer();
            for (int i = 0; i < padding; ++i) {
                buf.append("0");
            }
            buf.append(longHex);
            return buf.toString();
        }
        return longHex.substring(0, 8);
    }
    
    static String makeScrambledPassword(final String password) throws NoSuchAlgorithmException {
        final long[] passwordHash = Util.newHash(password);
        final StringBuffer scramble = new StringBuffer();
        scramble.append(longToHex(passwordHash[0]));
        scramble.append(longToHex(passwordHash[1]));
        return scramble.toString();
    }
    
    static void passwordCrypt(final byte[] from, final byte[] to, final byte[] password, final int length) {
        for (int pos = 0; pos < from.length && pos < length; ++pos) {
            to[pos] = (byte)(from[pos] ^ password[pos]);
        }
    }
    
    static byte[] passwordHashStage1(final String password) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        final StringBuffer cleansedPassword = new StringBuffer();
        for (int passwordLength = password.length(), i = 0; i < passwordLength; ++i) {
            final char c = password.charAt(i);
            if (c != ' ') {
                if (c != '\t') {
                    cleansedPassword.append(c);
                }
            }
        }
        return md.digest(cleansedPassword.toString().getBytes());
    }
    
    static byte[] passwordHashStage2(final byte[] hashedPassword, final byte[] salt) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(salt, 0, 4);
        md.update(hashedPassword, 0, 20);
        return md.digest();
    }
    
    static byte[] scramble411(final String password, final String seed, final Connection conn) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        final String passwordEncoding = conn.getPasswordCharacterEncoding();
        final byte[] passwordHashStage1 = md.digest((passwordEncoding == null || passwordEncoding.length() == 0) ? password.getBytes() : password.getBytes(passwordEncoding));
        md.reset();
        final byte[] passwordHashStage2 = md.digest(passwordHashStage1);
        md.reset();
        final byte[] seedAsBytes = seed.getBytes("ASCII");
        md.update(seedAsBytes);
        md.update(passwordHashStage2);
        final byte[] toBeXord = md.digest();
        for (int numToXor = toBeXord.length, i = 0; i < numToXor; ++i) {
            toBeXord[i] ^= passwordHashStage1[i];
        }
        return toBeXord;
    }
}
