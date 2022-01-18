// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class GenLayerRiverInit extends GenLayer
{
    public GenLayerRiverInit(final long n, final GenLayer a) {
        super(n);
        this.a = a;
    }
    
    public int[] a(final int n, final int n2, final int n3, final int n4) {
        final int[] a = this.a.a(n, n2, n3, n4);
        final int[] a2 = IntCache.a(n3 * n4);
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n3; ++j) {
                this.a(j + n, i + n2);
                a2[j + i * n3] = ((a[j + i * n3] > 0) ? (this.a(2) + 2) : 0);
            }
        }
        return a2;
    }
}
