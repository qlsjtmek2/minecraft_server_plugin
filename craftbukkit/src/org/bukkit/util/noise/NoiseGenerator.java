// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util.noise;

public abstract class NoiseGenerator
{
    protected final int[] perm;
    protected double offsetX;
    protected double offsetY;
    protected double offsetZ;
    
    public NoiseGenerator() {
        this.perm = new int[512];
    }
    
    public static int floor(final double x) {
        return (x >= 0.0) ? ((int)x) : ((int)x - 1);
    }
    
    protected static double fade(final double x) {
        return x * x * x * (x * (x * 6.0 - 15.0) + 10.0);
    }
    
    protected static double lerp(final double x, final double y, final double z) {
        return y + x * (z - y);
    }
    
    protected static double grad(int hash, final double x, final double y, final double z) {
        hash &= 0xF;
        final double u = (hash < 8) ? x : y;
        final double v = (hash < 4) ? y : ((hash == 12 || hash == 14) ? x : z);
        return (((hash & 0x1) == 0x0) ? u : (-u)) + (((hash & 0x2) == 0x0) ? v : (-v));
    }
    
    public double noise(final double x) {
        return this.noise(x, 0.0, 0.0);
    }
    
    public double noise(final double x, final double y) {
        return this.noise(x, y, 0.0);
    }
    
    public abstract double noise(final double p0, final double p1, final double p2);
    
    public double noise(final double x, final int octaves, final double frequency, final double amplitude) {
        return this.noise(x, 0.0, 0.0, octaves, frequency, amplitude);
    }
    
    public double noise(final double x, final int octaves, final double frequency, final double amplitude, final boolean normalized) {
        return this.noise(x, 0.0, 0.0, octaves, frequency, amplitude, normalized);
    }
    
    public double noise(final double x, final double y, final int octaves, final double frequency, final double amplitude) {
        return this.noise(x, y, 0.0, octaves, frequency, amplitude);
    }
    
    public double noise(final double x, final double y, final int octaves, final double frequency, final double amplitude, final boolean normalized) {
        return this.noise(x, y, 0.0, octaves, frequency, amplitude, normalized);
    }
    
    public double noise(final double x, final double y, final double z, final int octaves, final double frequency, final double amplitude) {
        return this.noise(x, y, z, octaves, frequency, amplitude, false);
    }
    
    public double noise(final double x, final double y, final double z, final int octaves, final double frequency, final double amplitude, final boolean normalized) {
        double result = 0.0;
        double amp = 1.0;
        double freq = 1.0;
        double max = 0.0;
        for (int i = 0; i < octaves; ++i) {
            result += this.noise(x * freq, y * freq, z * freq) * amp;
            max += amp;
            freq *= frequency;
            amp *= amplitude;
        }
        if (normalized) {
            result /= max;
        }
        return result;
    }
}
