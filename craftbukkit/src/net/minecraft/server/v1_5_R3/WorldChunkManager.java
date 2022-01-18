// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class WorldChunkManager
{
    private GenLayer d;
    private GenLayer e;
    private BiomeCache f;
    private List g;
    
    protected WorldChunkManager() {
        this.f = new BiomeCache(this);
        (this.g = new ArrayList()).add(BiomeBase.FOREST);
        this.g.add(BiomeBase.PLAINS);
        this.g.add(BiomeBase.TAIGA);
        this.g.add(BiomeBase.TAIGA_HILLS);
        this.g.add(BiomeBase.FOREST_HILLS);
        this.g.add(BiomeBase.JUNGLE);
        this.g.add(BiomeBase.JUNGLE_HILLS);
    }
    
    public WorldChunkManager(final long n, final WorldType worldType) {
        this();
        final GenLayer[] a = GenLayer.a(n, worldType);
        this.d = a[0];
        this.e = a[1];
    }
    
    public WorldChunkManager(final World world) {
        this(world.getSeed(), world.getWorldData().getType());
    }
    
    public List a() {
        return this.g;
    }
    
    public BiomeBase getBiome(final int n, final int n2) {
        return this.f.b(n, n2);
    }
    
    public float[] getWetness(float[] array, final int n, final int n2, final int n3, final int n4) {
        IntCache.a();
        if (array == null || array.length < n3 * n4) {
            array = new float[n3 * n4];
        }
        final int[] a = this.e.a(n, n2, n3, n4);
        for (int i = 0; i < n3 * n4; ++i) {
            float n5 = BiomeBase.biomes[a[i]].g() / 65536.0f;
            if (n5 > 1.0f) {
                n5 = 1.0f;
            }
            array[i] = n5;
        }
        return array;
    }
    
    public float[] getTemperatures(float[] array, final int n, final int n2, final int n3, final int n4) {
        IntCache.a();
        if (array == null || array.length < n3 * n4) {
            array = new float[n3 * n4];
        }
        final int[] a = this.e.a(n, n2, n3, n4);
        for (int i = 0; i < n3 * n4; ++i) {
            float n5 = BiomeBase.biomes[a[i]].h() / 65536.0f;
            if (n5 > 1.0f) {
                n5 = 1.0f;
            }
            array[i] = n5;
        }
        return array;
    }
    
    public BiomeBase[] getBiomes(BiomeBase[] array, final int n, final int n2, final int n3, final int n4) {
        IntCache.a();
        if (array == null || array.length < n3 * n4) {
            array = new BiomeBase[n3 * n4];
        }
        final int[] a = this.d.a(n, n2, n3, n4);
        for (int i = 0; i < n3 * n4; ++i) {
            array[i] = BiomeBase.biomes[a[i]];
        }
        return array;
    }
    
    public BiomeBase[] getBiomeBlock(final BiomeBase[] array, final int n, final int n2, final int n3, final int n4) {
        return this.a(array, n, n2, n3, n4, true);
    }
    
    public BiomeBase[] a(BiomeBase[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        IntCache.a();
        if (array == null || array.length < n3 * n4) {
            array = new BiomeBase[n3 * n4];
        }
        if (b && n3 == 16 && n4 == 16 && (n & 0xF) == 0x0 && (n2 & 0xF) == 0x0) {
            System.arraycopy(this.f.e(n, n2), 0, array, 0, n3 * n4);
            return array;
        }
        final int[] a = this.e.a(n, n2, n3, n4);
        for (int i = 0; i < n3 * n4; ++i) {
            array[i] = BiomeBase.biomes[a[i]];
        }
        return array;
    }
    
    public boolean a(final int n, final int n2, final int n3, final List list) {
        IntCache.a();
        final int n4 = n - n3 >> 2;
        final int n5 = n2 - n3 >> 2;
        final int n6 = n + n3 >> 2;
        final int n7 = n2 + n3 >> 2;
        final int n8 = n6 - n4 + 1;
        final int n9 = n7 - n5 + 1;
        final int[] a = this.d.a(n4, n5, n8, n9);
        for (int i = 0; i < n8 * n9; ++i) {
            if (!list.contains(BiomeBase.biomes[a[i]])) {
                return false;
            }
        }
        return true;
    }
    
    public ChunkPosition a(final int n, final int n2, final int n3, final List list, final Random random) {
        IntCache.a();
        final int n4 = n - n3 >> 2;
        final int n5 = n2 - n3 >> 2;
        final int n6 = n + n3 >> 2;
        final int n7 = n2 + n3 >> 2;
        final int n8 = n6 - n4 + 1;
        final int n9 = n7 - n5 + 1;
        final int[] a = this.d.a(n4, n5, n8, n9);
        ChunkPosition chunkPosition = null;
        int n10 = 0;
        for (int i = 0; i < n8 * n9; ++i) {
            final int n11 = n4 + i % n8 << 2;
            final int n12 = n5 + i / n8 << 2;
            if (list.contains(BiomeBase.biomes[a[i]]) && (chunkPosition == null || random.nextInt(n10 + 1) == 0)) {
                chunkPosition = new ChunkPosition(n11, 0, n12);
                ++n10;
            }
        }
        return chunkPosition;
    }
    
    public void b() {
        this.f.a();
    }
}
