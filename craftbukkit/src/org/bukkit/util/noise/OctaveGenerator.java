// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util.noise;

public abstract class OctaveGenerator
{
    protected final NoiseGenerator[] octaves;
    protected double xScale;
    protected double yScale;
    protected double zScale;
    
    protected OctaveGenerator(final NoiseGenerator[] octaves) {
        this.xScale = 1.0;
        this.yScale = 1.0;
        this.zScale = 1.0;
        this.octaves = octaves;
    }
    
    public void setScale(final double scale) {
        this.setXScale(scale);
        this.setYScale(scale);
        this.setZScale(scale);
    }
    
    public double getXScale() {
        return this.xScale;
    }
    
    public void setXScale(final double scale) {
        this.xScale = scale;
    }
    
    public double getYScale() {
        return this.yScale;
    }
    
    public void setYScale(final double scale) {
        this.yScale = scale;
    }
    
    public double getZScale() {
        return this.zScale;
    }
    
    public void setZScale(final double scale) {
        this.zScale = scale;
    }
    
    public NoiseGenerator[] getOctaves() {
        return this.octaves.clone();
    }
    
    public double noise(final double x, final double frequency, final double amplitude) {
        return this.noise(x, 0.0, 0.0, frequency, amplitude);
    }
    
    public double noise(final double x, final double frequency, final double amplitude, final boolean normalized) {
        return this.noise(x, 0.0, 0.0, frequency, amplitude, normalized);
    }
    
    public double noise(final double x, final double y, final double frequency, final double amplitude) {
        return this.noise(x, y, 0.0, frequency, amplitude);
    }
    
    public double noise(final double x, final double y, final double frequency, final double amplitude, final boolean normalized) {
        return this.noise(x, y, 0.0, frequency, amplitude, normalized);
    }
    
    public double noise(final double x, final double y, final double z, final double frequency, final double amplitude) {
        return this.noise(x, y, z, frequency, amplitude, false);
    }
    
    public double noise(double x, double y, double z, final double frequency, final double amplitude, final boolean normalized) {
        double result = 0.0;
        double amp = 1.0;
        double freq = 1.0;
        double max = 0.0;
        x *= this.xScale;
        y *= this.yScale;
        z *= this.zScale;
        for (final NoiseGenerator octave : this.octaves) {
            result += octave.noise(x * freq, y * freq, z * freq) * amp;
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
