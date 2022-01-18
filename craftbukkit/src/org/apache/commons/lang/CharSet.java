// 
// Decompiled by Procyon v0.5.30
// 

package org.apache.commons.lang;

import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.io.Serializable;

public class CharSet implements Serializable
{
    private static final long serialVersionUID = 5947847346149275958L;
    public static final CharSet EMPTY;
    public static final CharSet ASCII_ALPHA;
    public static final CharSet ASCII_ALPHA_LOWER;
    public static final CharSet ASCII_ALPHA_UPPER;
    public static final CharSet ASCII_NUMERIC;
    protected static final Map COMMON;
    private Set set;
    
    public static CharSet getInstance(final String setStr) {
        final Object set = CharSet.COMMON.get(setStr);
        if (set != null) {
            return (CharSet)set;
        }
        return new CharSet(setStr);
    }
    
    protected CharSet(final String setStr) {
        this.set = new HashSet();
        this.add(setStr);
    }
    
    protected CharSet(final String[] set) {
        this.set = new HashSet();
        for (int sz = set.length, i = 0; i < sz; ++i) {
            this.add(set[i]);
        }
    }
    
    protected void add(final String str) {
        if (str == null) {
            return;
        }
        final int len = str.length();
        int pos = 0;
        while (pos < len) {
            final int remainder = len - pos;
            if (remainder >= 4 && str.charAt(pos) == '^' && str.charAt(pos + 2) == '-') {
                this.set.add(new CharRange(str.charAt(pos + 1), str.charAt(pos + 3), true));
                pos += 4;
            }
            else if (remainder >= 3 && str.charAt(pos + 1) == '-') {
                this.set.add(new CharRange(str.charAt(pos), str.charAt(pos + 2)));
                pos += 3;
            }
            else if (remainder >= 2 && str.charAt(pos) == '^') {
                this.set.add(new CharRange(str.charAt(pos + 1), true));
                pos += 2;
            }
            else {
                this.set.add(new CharRange(str.charAt(pos)));
                ++pos;
            }
        }
    }
    
    public CharRange[] getCharRanges() {
        return this.set.toArray(new CharRange[this.set.size()]);
    }
    
    public boolean contains(final char ch) {
        for (final CharRange range : this.set) {
            if (range.contains(ch)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CharSet)) {
            return false;
        }
        final CharSet other = (CharSet)obj;
        return this.set.equals(other.set);
    }
    
    public int hashCode() {
        return 89 + this.set.hashCode();
    }
    
    public String toString() {
        return this.set.toString();
    }
    
    static {
        EMPTY = new CharSet((String)null);
        ASCII_ALPHA = new CharSet("a-zA-Z");
        ASCII_ALPHA_LOWER = new CharSet("a-z");
        ASCII_ALPHA_UPPER = new CharSet("A-Z");
        ASCII_NUMERIC = new CharSet("0-9");
        (COMMON = new HashMap()).put(null, CharSet.EMPTY);
        CharSet.COMMON.put("", CharSet.EMPTY);
        CharSet.COMMON.put("a-zA-Z", CharSet.ASCII_ALPHA);
        CharSet.COMMON.put("A-Za-z", CharSet.ASCII_ALPHA);
        CharSet.COMMON.put("a-z", CharSet.ASCII_ALPHA_LOWER);
        CharSet.COMMON.put("A-Z", CharSet.ASCII_ALPHA_UPPER);
        CharSet.COMMON.put("0-9", CharSet.ASCII_NUMERIC);
    }
}
