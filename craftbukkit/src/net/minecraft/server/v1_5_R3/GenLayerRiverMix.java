// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class GenLayerRiverMix extends GenLayer
{
    private GenLayer b;
    private GenLayer c;
    
    public GenLayerRiverMix(final long n, final GenLayer b, final GenLayer c) {
        super(n);
        this.b = b;
        this.c = c;
    }
    
    public void a(final long n) {
        this.b.a(n);
        this.c.a(n);
        super.a(n);
    }
    
    public int[] a(final int n, final int n2, final int n3, final int n4) {
        final int[] a = this.b.a(n, n2, n3, n4);
        final int[] a2 = this.c.a(n, n2, n3, n4);
        final int[] a3 = IntCache.a(n3 * n4);
        for (int i = 0; i < n3 * n4; ++i) {
            if (a[i] == BiomeBase.OCEAN.id) {
                a3[i] = a[i];
            }
            else if (a2[i] >= 0) {
                if (a[i] == BiomeBase.ICE_PLAINS.id) {
                    a3[i] = BiomeBase.FROZEN_RIVER.id;
                }
                else if (a[i] == BiomeBase.MUSHROOM_ISLAND.id || a[i] == BiomeBase.MUSHROOM_SHORE.id) {
                    a3[i] = BiomeBase.MUSHROOM_SHORE.id;
                }
                else {
                    a3[i] = a2[i];
                }
            }
            else {
                a3[i] = a[i];
            }
        }
        return a3;
    }
}
