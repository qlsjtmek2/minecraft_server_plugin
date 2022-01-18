// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util.noise;

import java.util.Random;
import org.bukkit.World;

public class PerlinOctaveGenerator extends OctaveGenerator
{
    public PerlinOctaveGenerator(final World world, final int octaves) {
        this(new Random(world.getSeed()), octaves);
    }
    
    public PerlinOctaveGenerator(final long seed, final int octaves) {
        this(new Random(seed), octaves);
    }
    
    public PerlinOctaveGenerator(final Random rand, final int octaves) {
        super(createOctaves(rand, octaves));
    }
    
    private static NoiseGenerator[] createOctaves(final Random rand, final int octaves) {
        final NoiseGenerator[] result = new NoiseGenerator[octaves];
        for (int i = 0; i < octaves; ++i) {
            result[i] = new PerlinNoiseGenerator(rand);
        }
        return result;
    }
}
