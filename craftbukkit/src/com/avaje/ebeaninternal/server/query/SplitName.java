// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

public class SplitName
{
    public static String add(final String prefix, final String name) {
        if (prefix != null) {
            return prefix + "." + name;
        }
        return name;
    }
    
    public static int count(final char c, final String name) {
        int count = 0;
        for (int i = 0; i < name.length(); ++i) {
            if (c == name.charAt(i)) {
                ++count;
            }
        }
        return count;
    }
    
    public static String parent(final String name) {
        if (name == null) {
            return null;
        }
        final String[] s = split(name, true);
        return s[0];
    }
    
    public static String[] split(final String name) {
        return split(name, true);
    }
    
    public static String[] splitBegin(final String name) {
        return split(name, false);
    }
    
    private static String[] split(final String name, final boolean last) {
        final int pos = last ? name.lastIndexOf(46) : name.indexOf(46);
        if (pos != -1) {
            final String s0 = name.substring(0, pos);
            final String s2 = name.substring(pos + 1);
            return new String[] { s0, s2 };
        }
        if (last) {
            return new String[] { null, name };
        }
        return new String[] { name, null };
    }
}
