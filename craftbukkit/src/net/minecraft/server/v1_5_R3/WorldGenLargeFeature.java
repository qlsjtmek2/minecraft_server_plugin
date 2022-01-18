// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Arrays;
import java.util.Random;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class WorldGenLargeFeature extends StructureGenerator
{
    private static List e;
    private List f;
    private int g;
    private int h;
    
    public WorldGenLargeFeature() {
        this.f = new ArrayList();
        this.g = 32;
        this.h = 8;
        this.f.add(new BiomeMeta(EntityWitch.class, 1, 1, 1));
    }
    
    public WorldGenLargeFeature(final Map map) {
        this();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("distance")) {
                this.g = MathHelper.a((String)entry.getValue(), this.g, this.h + 1);
            }
        }
    }
    
    protected boolean a(int n, int n2) {
        final int n3 = n;
        final int n4 = n2;
        if (n < 0) {
            n -= this.g - 1;
        }
        if (n2 < 0) {
            n2 -= this.g - 1;
        }
        final int i = n / this.g;
        final int j = n2 / this.g;
        final Random h = this.c.H(i, j, 14357617);
        final int n5 = i * this.g;
        final int n6 = j * this.g;
        final int n7 = n5 + h.nextInt(this.g - this.h);
        final int n8 = n6 + h.nextInt(this.g - this.h);
        n = n3;
        n2 = n4;
        if (n == n7 && n2 == n8) {
            final BiomeBase biome = this.c.getWorldChunkManager().getBiome(n * 16 + 8, n2 * 16 + 8);
            final Iterator<BiomeBase> iterator = (Iterator<BiomeBase>)WorldGenLargeFeature.e.iterator();
            while (iterator.hasNext()) {
                if (biome == iterator.next()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected StructureStart b(final int n, final int n2) {
        return new WorldGenLargeFeatureStart(this.c, this.b, n, n2);
    }
    
    public List a() {
        return this.f;
    }
    
    static {
        WorldGenLargeFeature.e = Arrays.asList(BiomeBase.DESERT, BiomeBase.DESERT_HILLS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS, BiomeBase.SWAMPLAND);
    }
}
