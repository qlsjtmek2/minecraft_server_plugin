// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;

public final class Sort
{
    private static final CompareFunc comparableCompareFunc;
    
    public static void sort(final Comparable[] array) {
        sort(array, Sort.comparableCompareFunc);
    }
    
    public static void sort(final Object[] array, final CompareFunc compareFunc) {
        sort(array, compareFunc, 0, array.length - 1);
    }
    
    private static void sort(final Object[] array, final CompareFunc compareFunc, final int n, int n2) {
        if (n >= n2) {
            return;
        }
        if (n2 - n <= 6) {
            for (int i = n + 1; i <= n2; ++i) {
                Object o;
                int n3;
                for (o = array[i], n3 = i - 1; n3 >= n && compareFunc.compare(array[n3], o) > 0; --n3) {
                    array[n3 + 1] = array[n3];
                }
                array[n3 + 1] = o;
            }
            return;
        }
        final Object o2 = array[n2];
        int n4 = n - 1;
        while (true) {
            if (n4 < n2 && compareFunc.compare(array[++n4], o2) < 0) {
                continue;
            }
            while (n2 > n4 && compareFunc.compare(array[--n2], o2) > 0) {}
            final Object o3 = array[n4];
            array[n4] = array[n2];
            array[n2] = o3;
            if (n4 >= n2) {
                break;
            }
        }
        final Object o4 = array[n4];
        array[n4] = array[n2];
        array[n2] = o4;
        sort(array, compareFunc, n, n4 - 1);
        sort(array, compareFunc, n4 + 1, n2);
    }
    
    static {
        comparableCompareFunc = new CompareFunc() {
            public int compare(final Object o, final Object o2) {
                return ((Comparable)o).compareTo(o2);
            }
        };
    }
    
    public interface CompareFunc
    {
        int compare(final Object p0, final Object p1);
    }
    
    public interface Comparable
    {
        int compareTo(final Object p0);
    }
}
