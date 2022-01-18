// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

public final class CharMatch
{
    private final char[] upperChars;
    private final int maxLength;
    
    public CharMatch(final String s) {
        this.upperChars = s.toUpperCase().toCharArray();
        this.maxLength = this.upperChars.length;
    }
    
    public boolean startsWith(final String other) {
        if (other == null || other.length() < this.maxLength) {
            return false;
        }
        final char[] ta = other.toCharArray();
        int pos = -1;
        while (++pos < this.maxLength) {
            final char c1 = this.upperChars[pos];
            final char c2 = Character.toUpperCase(ta[pos]);
            if (c1 != c2) {
                return false;
            }
        }
        return true;
    }
    
    public boolean endsWith(final String other) {
        if (other == null || other.length() < this.maxLength) {
            return false;
        }
        final char[] ta = other.toCharArray();
        final int offset = ta.length - this.maxLength;
        int pos = this.maxLength;
        while (--pos >= 0) {
            final char c1 = this.upperChars[pos];
            final char c2 = Character.toUpperCase(ta[offset + pos]);
            if (c1 != c2) {
                return false;
            }
        }
        return true;
    }
}
