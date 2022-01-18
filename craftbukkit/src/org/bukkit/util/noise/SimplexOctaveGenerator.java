// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util.noise;

import java.util.Random;
import org.bukkit.World;

public class SimplexOctaveGenerator extends OctaveGenerator
{
    private double wScale;
    
    public SimplexOctaveGenerator(final World world, final int octaves) {
        this(new Random(world.getSeed()), octaves);
    }
    
    public SimplexOctaveGenerator(final long seed, final int octaves) {
        this(new Random(seed), octaves);
    }
    
    public SimplexOctaveGenerator(final Random rand, final int octaves) {
        super(createOctaves(rand, octaves));
        this.wScale = 1.0;
    }
    
    public void setScale(final double scale) {
        super.setScale(scale);
        this.setWScale(scale);
    }
    
    public double getWScale() {
        return this.wScale;
    }
    
    public void setWScale(final double scale) {
        this.wScale = scale;
    }
    
    public double noise(final double x, final double y, final double z, final double w, final double frequency, final double amplitude) {
        return this.noise(x, y, z, w, frequency, amplitude, false);
    }
    
    public double noise(double x, double y, double z, double w, final double frequency, final double amplitude, final boolean normalized) {
        double result = 0.0;
        double amp = 1.0;
        double freq = 1.0;
        double max = 0.0;
        x *= this.xScale;
        y *= this.yScale;
        z *= this.zScale;
        w *= this.wScale;
        for (final NoiseGenerator octave : this.octaves) {
            result += ((SimplexNoiseGenerator)octave).noise(x * freq, y * freq, z * freq, w * freq) * amp;
            max += amp;
            freq *= frequency;
            amp *= amplitude;
        }
        if (normalized) {
            result /= max;
        }
        return result;
    }
    
    private static NoiseGenerator[] createOctaves(final Random rand, final int octaves) {
        final NoiseGenerator[] result = new NoiseGenerator[octaves];
        for (int i = 0; i < octaves; ++i) {
            result[i] = new SimplexNoiseGenerator(rand);
        }
        return result;
    }
}
