// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class LayerIsland extends GenLayer
{
    public LayerIsland(final long n) {
        super(n);
    }
    
    public int[] a(final int n, final int n2, final int n3, final int n4) {
        final int[] a = IntCache.a(n3 * n4);
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n3; ++j) {
                this.a(n + j, n2 + i);
                a[j + i * n3] = ((this.a(10) == 0) ? 1 : 0);
            }
        }
        if (n > -n3 && n <= 0 && n2 > -n4 && n2 <= 0) {
            a[-n + -n2 * n3] = 1;
        }
        return a;
    }
}
