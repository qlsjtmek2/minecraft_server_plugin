// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class GenLayerSwampRivers extends GenLayer
{
    public GenLayerSwampRivers(final long n, final GenLayer a) {
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
                if ((n5 == BiomeBase.SWAMPLAND.id && this.a(6) == 0) || ((n5 == BiomeBase.JUNGLE.id || n5 == BiomeBase.JUNGLE_HILLS.id) && this.a(8) == 0)) {
                    a2[j + i * n3] = BiomeBase.RIVER.id;
                }
                else {
                    a2[j + i * n3] = n5;
                }
            }
        }
        return a2;
    }
}
