// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class GenLayerZoom extends GenLayer
{
    public GenLayerZoom(final long n, final GenLayer a) {
        super(n);
        super.a = a;
    }
    
    public int[] a(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n >> 1;
        final int n6 = n2 >> 1;
        final int n7 = (n3 >> 1) + 3;
        final int n8 = (n4 >> 1) + 3;
        final int[] a = this.a.a(n5, n6, n7, n8);
        final int[] a2 = IntCache.a(n7 * 2 * (n8 * 2));
        final int n9 = n7 << 1;
        for (int i = 0; i < n8 - 1; ++i) {
            int n10 = (i << 1) * n9;
            int n11 = a[0 + (i + 0) * n7];
            int n12 = a[0 + (i + 1) * n7];
            for (int j = 0; j < n7 - 1; ++j) {
                this.a(j + n5 << 1, i + n6 << 1);
                final int n13 = a[j + 1 + (i + 0) * n7];
                final int n14 = a[j + 1 + (i + 1) * n7];
                a2[n10] = n11;
                a2[n10++ + n9] = this.a(n11, n12);
                a2[n10] = this.a(n11, n13);
                a2[n10++ + n9] = this.b(n11, n13, n12, n14);
                n11 = n13;
                n12 = n14;
            }
        }
        final int[] a3 = IntCache.a(n3 * n4);
        for (int k = 0; k < n4; ++k) {
            System.arraycopy(a2, (k + (n2 & 0x1)) * (n7 << 1) + (n & 0x1), a3, k * n3, n3);
        }
        return a3;
    }
    
    protected int a(final int n, final int n2) {
        return (this.a(2) == 0) ? n : n2;
    }
    
    protected int b(final int n, final int n2, final int n3, final int n4) {
        if (n2 == n3 && n3 == n4) {
            return n2;
        }
        if (n == n2 && n == n3) {
            return n;
        }
        if (n == n2 && n == n4) {
            return n;
        }
        if (n == n3 && n == n4) {
            return n;
        }
        if (n == n2 && n3 != n4) {
            return n;
        }
        if (n == n3 && n2 != n4) {
            return n;
        }
        if (n == n4 && n2 != n3) {
            return n;
        }
        if (n2 == n && n3 != n4) {
            return n2;
        }
        if (n2 == n3 && n != n4) {
            return n2;
        }
        if (n2 == n4 && n != n3) {
            return n2;
        }
        if (n3 == n && n2 != n4) {
            return n3;
        }
        if (n3 == n2 && n != n4) {
            return n3;
        }
        if (n3 == n4 && n != n2) {
            return n3;
        }
        if (n4 == n && n2 != n3) {
            return n3;
        }
        if (n4 == n2 && n != n3) {
            return n3;
        }
        if (n4 == n3 && n != n2) {
            return n3;
        }
        final int a = this.a(4);
        if (a == 0) {
            return n;
        }
        if (a == 1) {
            return n2;
        }
        if (a == 2) {
            return n3;
        }
        return n4;
    }
    
    public static GenLayer a(final long n, final GenLayer genLayer, final int n2) {
        GenLayer genLayer2 = genLayer;
        for (int i = 0; i < n2; ++i) {
            genLayer2 = new GenLayerZoom(n + i, genLayer2);
        }
        return genLayer2;
    }
}
