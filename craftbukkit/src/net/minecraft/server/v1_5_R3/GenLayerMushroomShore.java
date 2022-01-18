// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class GenLayerMushroomShore extends GenLayer
{
    public GenLayerMushroomShore(final long n, final GenLayer a) {
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
                if (n5 == BiomeBase.MUSHROOM_ISLAND.id) {
                    final int n6 = a[j + 1 + (i + 1 - 1) * (n3 + 2)];
                    final int n7 = a[j + 1 + 1 + (i + 1) * (n3 + 2)];
                    final int n8 = a[j + 1 - 1 + (i + 1) * (n3 + 2)];
                    final int n9 = a[j + 1 + (i + 1 + 1) * (n3 + 2)];
                    if (n6 == BiomeBase.OCEAN.id || n7 == BiomeBase.OCEAN.id || n8 == BiomeBase.OCEAN.id || n9 == BiomeBase.OCEAN.id) {
                        a2[j + i * n3] = BiomeBase.MUSHROOM_SHORE.id;
                    }
                    else {
                        a2[j + i * n3] = n5;
                    }
                }
                else if (n5 != BiomeBase.OCEAN.id && n5 != BiomeBase.RIVER.id && n5 != BiomeBase.SWAMPLAND.id && n5 != BiomeBase.EXTREME_HILLS.id) {
                    final int n10 = a[j + 1 + (i + 1 - 1) * (n3 + 2)];
                    final int n11 = a[j + 1 + 1 + (i + 1) * (n3 + 2)];
                    final int n12 = a[j + 1 - 1 + (i + 1) * (n3 + 2)];
                    final int n13 = a[j + 1 + (i + 1 + 1) * (n3 + 2)];
                    if (n10 == BiomeBase.OCEAN.id || n11 == BiomeBase.OCEAN.id || n12 == BiomeBase.OCEAN.id || n13 == BiomeBase.OCEAN.id) {
                        a2[j + i * n3] = BiomeBase.BEACH.id;
                    }
                    else {
                        a2[j + i * n3] = n5;
                    }
                }
                else if (n5 == BiomeBase.EXTREME_HILLS.id) {
                    final int n14 = a[j + 1 + (i + 1 - 1) * (n3 + 2)];
                    final int n15 = a[j + 1 + 1 + (i + 1) * (n3 + 2)];
                    final int n16 = a[j + 1 - 1 + (i + 1) * (n3 + 2)];
                    final int n17 = a[j + 1 + (i + 1 + 1) * (n3 + 2)];
                    if (n14 != BiomeBase.EXTREME_HILLS.id || n15 != BiomeBase.EXTREME_HILLS.id || n16 != BiomeBase.EXTREME_HILLS.id || n17 != BiomeBase.EXTREME_HILLS.id) {
                        a2[j + i * n3] = BiomeBase.SMALL_MOUNTAINS.id;
                    }
                    else {
                        a2[j + i * n3] = n5;
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
