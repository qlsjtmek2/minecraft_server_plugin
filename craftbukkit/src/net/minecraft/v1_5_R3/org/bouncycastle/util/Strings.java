// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.util;

public final class Strings
{
    public static String a(final String s) {
        boolean b = false;
        final char[] charArray = s.toCharArray();
        for (int i = 0; i != charArray.length; ++i) {
            final char c = charArray[i];
            if ('A' <= c && 'Z' >= c) {
                b = true;
                charArray[i] = (char)(c - 'A' + 'a');
            }
        }
        if (b) {
            return new String(charArray);
        }
        return s;
    }
}
