// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class MathHelper
{
    private static float[] a;
    
    public static final float sin(final float n) {
        return MathHelper.a[(int)(n * 10430.378f) & 0xFFFF];
    }
    
    public static final float cos(final float n) {
        return MathHelper.a[(int)(n * 10430.378f + 16384.0f) & 0xFFFF];
    }
    
    public static final float c(final float n) {
        return (float)Math.sqrt(n);
    }
    
    public static final float sqrt(final double n) {
        return (float)Math.sqrt(n);
    }
    
    public static int d(final float n) {
        final int n2 = (int)n;
        return (n < n2) ? (n2 - 1) : n2;
    }
    
    public static int floor(final double n) {
        final int n2 = (int)n;
        return (n < n2) ? (n2 - 1) : n2;
    }
    
    public static long d(final double n) {
        final long n2 = (long)n;
        return (n < n2) ? (n2 - 1L) : n2;
    }
    
    public static float abs(final float n) {
        return (n >= 0.0f) ? n : (-n);
    }
    
    public static int a(final int n) {
        return (n >= 0) ? n : (-n);
    }
    
    public static int f(final float n) {
        final int n2 = (int)n;
        return (n > n2) ? (n2 + 1) : n2;
    }
    
    public static int f(final double n) {
        final int n2 = (int)n;
        return (n > n2) ? (n2 + 1) : n2;
    }
    
    public static int a(final int n, final int n2, final int n3) {
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }
    
    public static double a(double n, double n2) {
        if (n < 0.0) {
            n = -n;
        }
        if (n2 < 0.0) {
            n2 = -n2;
        }
        return (n > n2) ? n : n2;
    }
    
    public static int nextInt(final Random random, final int n, final int n2) {
        if (n >= n2) {
            return n;
        }
        return random.nextInt(n2 - n + 1) + n;
    }
    
    public static double a(final Random random, final double n, final double n2) {
        if (n >= n2) {
            return n;
        }
        return random.nextDouble() * (n2 - n) + n;
    }
    
    public static double a(final long[] array) {
        long n = 0L;
        for (int length = array.length, i = 0; i < length; ++i) {
            n += array[i];
        }
        return n / array.length;
    }
    
    public static float g(float n) {
        n %= 360.0f;
        if (n >= 180.0f) {
            n -= 360.0f;
        }
        if (n < -180.0f) {
            n += 360.0f;
        }
        return n;
    }
    
    public static double g(double n) {
        n %= 360.0;
        if (n >= 180.0) {
            n -= 360.0;
        }
        if (n < -180.0) {
            n += 360.0;
        }
        return n;
    }
    
    public static int a(final String s, final int n) {
        int int1 = n;
        try {
            int1 = Integer.parseInt(s);
        }
        catch (Throwable t) {}
        return int1;
    }
    
    public static int a(final String s, final int n, final int n2) {
        int int1 = n;
        try {
            int1 = Integer.parseInt(s);
        }
        catch (Throwable t) {}
        if (int1 < n2) {
            int1 = n2;
        }
        return int1;
    }
    
    public static double a(final String s, final double n) {
        double double1 = n;
        try {
            double1 = Double.parseDouble(s);
        }
        catch (Throwable t) {}
        return double1;
    }
    
    public static double a(final String s, final double n, final double n2) {
        double double1 = n;
        try {
            double1 = Double.parseDouble(s);
        }
        catch (Throwable t) {}
        if (double1 < n2) {
            double1 = n2;
        }
        return double1;
    }
    
    static {
        MathHelper.a = new float[65536];
        for (int i = 0; i < 65536; ++i) {
            MathHelper.a[i] = (float)Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
    }
}
