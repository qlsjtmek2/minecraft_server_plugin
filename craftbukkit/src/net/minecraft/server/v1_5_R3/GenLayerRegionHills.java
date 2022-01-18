// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class GenLayerRegionHills extends GenLayer
{
    public GenLayerRegionHills(final long n, final GenLayer a) {
        super(n);
        this.a = a;
    }
    
    public int[] a(final int n, final int n2, final int n3, final int n4) {
        final int[] a = this.a.a(n - 1, n2 - 1, n3 + 2, n4 + 2);
        final int[] a2 = IntCache.a(n3 * n4);
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n3; ++j) {
                this.a(j + n, i + n2);
                final int n5 = a[j + 1 + (i + 1) * (n3 + 2)];
                if (this.a(3) == 0) {
                    int n6;
                    if ((n6 = n5) == BiomeBase.DESERT.id) {
                        n6 = BiomeBase.DESERT_HILLS.id;
                    }
                    else if (n5 == BiomeBase.FOREST.id) {
                        n6 = BiomeBase.FOREST_HILLS.id;
                    }
                    else if (n5 == BiomeBase.TAIGA.id) {
                        n6 = BiomeBase.TAIGA_HILLS.id;
                    }
                    else if (n5 == BiomeBase.PLAINS.id) {
                        n6 = BiomeBase.FOREST.id;
                    }
                    else if (n5 == BiomeBase.ICE_PLAINS.id) {
                        n6 = BiomeBase.ICE_MOUNTAINS.id;
                    }
                    else if (n5 == BiomeBase.JUNGLE.id) {
                        n6 = BiomeBase.JUNGLE_HILLS.id;
                    }
                    if (n6 == n5) {
                        a2[j + i * n3] = n5;
                    }
                    else {
                        final int n7 = a[j + 1 + (i + 1 - 1) * (n3 + 2)];
                        final int n8 = a[j + 1 + 1 + (i + 1) * (n3 + 2)];
                        final int n9 = a[j + 1 - 1 + (i + 1) * (n3 + 2)];
                        final int n10 = a[j + 1 + (i + 1 + 1) * (n3 + 2)];
                        if (n7 == n5 && n8 == n5 && n9 == n5 && n10 == n5) {
                            a2[j + i * n3] = n6;
                        }
                        else {
                            a2[j + i * n3] = n5;
                        }
                    }
                }
                else {
                    a2[j + i * n3] = n5;
                }
            }
        }
        return a2;
    }
}
