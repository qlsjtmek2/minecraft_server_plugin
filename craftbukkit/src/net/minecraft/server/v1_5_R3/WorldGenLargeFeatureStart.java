// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenLargeFeatureStart extends StructureStart
{
    public WorldGenLargeFeatureStart(final World world, final Random random, final int n, final int n2) {
        final BiomeBase biome = world.getBiome(n * 16 + 8, n2 * 16 + 8);
        if (biome == BiomeBase.JUNGLE || biome == BiomeBase.JUNGLE_HILLS) {
            this.a.add(new WorldGenJungleTemple(random, n * 16, n2 * 16));
        }
        else if (biome == BiomeBase.SWAMPLAND) {
            this.a.add(new WorldGenWitchHut(random, n * 16, n2 * 16));
        }
        else {
            this.a.add(new WorldGenPyramidPiece(random, n * 16, n2 * 16));
        }
        this.c();
    }
}
