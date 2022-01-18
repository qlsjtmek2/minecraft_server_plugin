// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.api.bukkit;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;

public class DataAPI
{
    public static List<String> insertList(final List<String> list, final int index, final String str) {
        final List<String> list2 = new LinkedList<String>();
        for (int i = 0; i < index; ++i) {
            list2.add(list.get(i));
        }
        list2.add(str);
        for (int i = index; i < list.size(); ++i) {
            list2.add(list.get(i));
        }
        return list2;
    }
    
    public static boolean isListhasIndex(final List list, final int index) {
        return index >= 0 && list.size() > index;
    }
    
    public static List<String> toSet(final Set<String> set) {
        final List<String> list = new LinkedList<String>();
        for (final String entry : set) {
            list.add(entry);
        }
        return list;
    }
    
    public static int toInteger(final BigDecimal big) {
        try {
            return big.intValueExact();
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    public static int toInteger(final String str) {
        return Integer.valueOf(str);
    }
    
    public static long toLong(final String str) {
        return Long.valueOf(str);
    }
    
    public static double toDouble(final String str) {
        return Double.valueOf(str);
    }
    
    public static float toFloat(final String str) {
        return Float.valueOf(str);
    }
    
    public static boolean toBoolean(final String str) {
        return Boolean.valueOf(str);
    }
    
    public static boolean toBoolean(final int i) {
        return i == 1;
    }
}
