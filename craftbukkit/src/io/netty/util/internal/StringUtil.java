// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.internal;

import java.util.Formatter;
import java.util.List;
import java.util.ArrayList;

public final class StringUtil
{
    public static final String NEWLINE;
    private static final String EMPTY_STRING = "";
    
    public static String[] split(final String value, final char delim) {
        final int end = value.length();
        final List<String> res = new ArrayList<String>();
        int start = 0;
        for (int i = 0; i < end; ++i) {
            if (value.charAt(i) == delim) {
                if (start == i) {
                    res.add("");
                }
                else {
                    res.add(value.substring(start, i));
                }
                start = i + 1;
            }
        }
        if (start == 0) {
            res.add(value);
        }
        else if (start != end) {
            res.add(value.substring(start, end));
        }
        else {
            for (int i = res.size() - 1; i >= 0 && res.get(i).isEmpty(); --i) {
                res.remove(i);
            }
        }
        return res.toArray(new String[res.size()]);
    }
    
    static {
        String newLine;
        try {
            newLine = new Formatter().format("%n", new Object[0]).toString();
        }
        catch (Exception e) {
            newLine = "\n";
        }
        NEWLINE = newLine;
    }
}
