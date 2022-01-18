// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.util.Random;
import java.util.logging.Logger;

public class Prefix
{
    private static final Logger logger;
    private static final int[] oa;
    
    public static String getProp(final String prop) {
        final String v = dec(prop);
        final int p = v.indexOf(":");
        final String r = v.substring(1, p);
        return r;
    }
    
    public static void main(final String[] args) {
        final String m = e(args[0]);
        Prefix.logger.info("[" + m + "]");
        final String o = getProp(m);
        Prefix.logger.info("[" + o + "]");
    }
    
    public static String e(String msg) {
        msg = elen(msg, 40);
        return enc(msg);
    }
    
    public static byte az(final byte c, final int offset) {
        int z = c + offset;
        if (z > 122) {
            z = z - 122 + 48 - 1;
        }
        return (byte)z;
    }
    
    public static byte bz(final byte c, final int offset) {
        int z = c - offset;
        if (z < 48) {
            z = z + 122 - 48 + 1;
        }
        return (byte)z;
    }
    
    public static String enc(final String msg) {
        final byte[] msgbytes = msg.getBytes();
        final byte[] encbytes = new byte[msgbytes.length + 1];
        final Random r = new Random();
        final int key = r.nextInt(70);
        final char k = (char)(key + 48);
        encbytes[0] = az((byte)k, Prefix.oa[0]);
        final int ios = key;
        for (int i = 1; i < msgbytes.length + 1; ++i) {
            encbytes[i] = az(msgbytes[i - 1], Prefix.oa[(i + ios) % Prefix.oa.length]);
        }
        return new String(encbytes);
    }
    
    public static String dec(final String msg) {
        final byte[] msgbytes = msg.getBytes();
        final byte[] encbytes = new byte[msgbytes.length];
        encbytes[0] = bz(msgbytes[0], Prefix.oa[0]);
        final byte key = encbytes[0];
        final int ios = key - 48;
        for (int i = 1; i < msgbytes.length; ++i) {
            encbytes[i] = bz(msgbytes[i], Prefix.oa[(i + ios) % Prefix.oa.length]);
        }
        return new String(encbytes);
    }
    
    public static String elen(final String msg, final int len) {
        final Random r = new Random();
        if (msg.length() < len) {
            final int max = len - msg.length();
            final StringBuilder sb = new StringBuilder();
            sb.append(msg).append(":");
            for (int i = 1; i < max; ++i) {
                final int bc = r.nextInt(74);
                sb.append(Character.toString((char)(bc + 48)));
            }
            return sb.toString();
        }
        return msg;
    }
    
    static {
        logger = Logger.getLogger(Prefix.class.getName());
        oa = new int[] { 50, 12, 4, 6, 8, 10, 7, 23, 45, 23, 6, 9, 12, 2, 8, 34 };
    }
}
