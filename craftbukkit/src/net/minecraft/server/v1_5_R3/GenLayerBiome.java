// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class GenLayerBiome extends GenLayer
{
    private BiomeBase[] b;
    
    public GenLayerBiome(final long n, final GenLayer a, final WorldType worldType) {
        super(n);
        this.b = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA, BiomeBase.JUNGLE };
        this.a = a;
        if (worldType == WorldType.NORMAL_1_1) {
            this.b = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA };
        }
    }
    
    public int[] a(final int n, final int n2, final int n3, final int n4) {
        final int[] a = this.a.a(n, n2, n3, n4);
        final int[] a2 = IntCache.a(n3 * n4);
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n3; ++j) {
                this.a(j + n, i + n2);
                final int n5 = a[j + i * n3];
                if (n5 == 0) {
                    a2[j + i * n3] = 0;
                }
                else if (n5 == BiomeBase.MUSHROOM_ISLAND.id) {
                    a2[j + i * n3] = n5;
                }
                else if (n5 == 1) {
                    a2[j + i * n3] = this.b[this.a(this.b.length)].id;
                }
                else {
                    final int id = this.b[this.a(this.b.length)].id;
                    if (id == BiomeBase.TAIGA.id) {
                        a2[j + i * n3] = id;
                    }
                    else {
                        a2[j + i * n3] = BiomeBase.ICE_PLAINS.id;
                    }
                }
            }
        }
        return a2;
    }
}
