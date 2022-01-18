// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.List;
import java.util.Arrays;

public class WorldChunkManagerHell extends WorldChunkManager
{
    private BiomeBase d;
    private float e;
    private float f;
    
    public WorldChunkManagerHell(final BiomeBase d, final float e, final float f) {
        this.d = d;
        this.e = e;
        this.f = f;
    }
    
    public BiomeBase getBiome(final int n, final int n2) {
        return this.d;
    }
    
    public BiomeBase[] getBiomes(BiomeBase[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new BiomeBase[n3 * n4];
        }
        Arrays.fill(array, 0, n3 * n4, this.d);
        return array;
    }
    
    public float[] getWetness(float[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new float[n3 * n4];
        }
        Arrays.fill(array, 0, n3 * n4, this.e);
        return array;
    }
    
    public float[] getTemperatures(float[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new float[n3 * n4];
        }
        Arrays.fill(array, 0, n3 * n4, this.f);
        return array;
    }
    
    public BiomeBase[] getBiomeBlock(BiomeBase[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new BiomeBase[n3 * n4];
        }
        Arrays.fill(array, 0, n3 * n4, this.d);
        return array;
    }
    
    public BiomeBase[] a(final BiomeBase[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        return this.getBiomeBlock(array, n, n2, n3, n4);
    }
    
    public ChunkPosition a(final int n, final int n2, final int n3, final List list, final Random random) {
        if (list.contains(this.d)) {
            return new ChunkPosition(n - n3 + random.nextInt(n3 * 2 + 1), 0, n2 - n3 + random.nextInt(n3 * 2 + 1));
        }
        return null;
    }
    
    public boolean a(final int n, final int n2, final int n3, final List list) {
        return list.contains(this.d);
    }
}
