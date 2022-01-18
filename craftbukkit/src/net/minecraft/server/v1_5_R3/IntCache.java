// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IntCache
{
    private static int a;
    private static List b;
    private static List c;
    private static List d;
    private static List e;
    
    public static synchronized int[] a(final int a) {
        if (a <= 256) {
            if (IntCache.b.isEmpty()) {
                final int[] array = new int[256];
                IntCache.c.add(array);
                return array;
            }
            final int[] array2 = IntCache.b.remove(IntCache.b.size() - 1);
            IntCache.c.add(array2);
            return array2;
        }
        else {
            if (a > IntCache.a) {
                IntCache.a = a;
                IntCache.d.clear();
                IntCache.e.clear();
                final int[] array3 = new int[IntCache.a];
                IntCache.e.add(array3);
                return array3;
            }
            if (IntCache.d.isEmpty()) {
                final int[] array4 = new int[IntCache.a];
                IntCache.e.add(array4);
                return array4;
            }
            final int[] array5 = IntCache.d.remove(IntCache.d.size() - 1);
            IntCache.e.add(array5);
            return array5;
        }
    }
    
    public static synchronized void a() {
        if (!IntCache.d.isEmpty()) {
            IntCache.d.remove(IntCache.d.size() - 1);
        }
        if (!IntCache.b.isEmpty()) {
            IntCache.b.remove(IntCache.b.size() - 1);
        }
        IntCache.d.addAll(IntCache.e);
        IntCache.b.addAll(IntCache.c);
        IntCache.e.clear();
        IntCache.c.clear();
    }
    
    public static synchronized String b() {
        return "cache: " + IntCache.d.size() + ", tcache: " + IntCache.b.size() + ", allocated: " + IntCache.e.size() + ", tallocated: " + IntCache.c.size();
    }
    
    static {
        IntCache.a = 256;
        IntCache.b = new ArrayList();
        IntCache.c = new ArrayList();
        IntCache.d = new ArrayList();
        IntCache.e = new ArrayList();
    }
}
