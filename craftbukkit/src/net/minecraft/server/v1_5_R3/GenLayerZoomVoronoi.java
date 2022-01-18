// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class GenLayerZoomVoronoi extends GenLayer
{
    public GenLayerZoomVoronoi(final long n, final GenLayer a) {
        super(n);
        super.a = a;
    }
    
    public int[] a(int n, int n2, final int n3, final int n4) {
        n -= 2;
        n2 -= 2;
        final int n5 = 2;
        final int n6 = 1 << n5;
        final int n7 = n >> n5;
        final int n8 = n2 >> n5;
        final int n9 = (n3 >> n5) + 3;
        final int n10 = (n4 >> n5) + 3;
        final int[] a = this.a.a(n7, n8, n9, n10);
        final int n11 = n9 << n5;
        final int[] a2 = IntCache.a(n11 * (n10 << n5));
        for (int i = 0; i < n10 - 1; ++i) {
            int n12 = a[0 + (i + 0) * n9];
            int n13 = a[0 + (i + 1) * n9];
            for (int j = 0; j < n9 - 1; ++j) {
                final double n14 = n6 * 0.9;
                this.a(j + n7 << n5, i + n8 << n5);
                final double n15 = (this.a(1024) / 1024.0 - 0.5) * n14;
                final double n16 = (this.a(1024) / 1024.0 - 0.5) * n14;
                this.a(j + n7 + 1 << n5, i + n8 << n5);
                final double n17 = (this.a(1024) / 1024.0 - 0.5) * n14 + n6;
                final double n18 = (this.a(1024) / 1024.0 - 0.5) * n14;
                this.a(j + n7 << n5, i + n8 + 1 << n5);
                final double n19 = (this.a(1024) / 1024.0 - 0.5) * n14;
                final double n20 = (this.a(1024) / 1024.0 - 0.5) * n14 + n6;
                this.a(j + n7 + 1 << n5, i + n8 + 1 << n5);
                final double n21 = (this.a(1024) / 1024.0 - 0.5) * n14 + n6;
                final double n22 = (this.a(1024) / 1024.0 - 0.5) * n14 + n6;
                final int n23 = a[j + 1 + (i + 0) * n9];
                final int n24 = a[j + 1 + (i + 1) * n9];
                for (int k = 0; k < n6; ++k) {
                    int n25 = ((i << n5) + k) * n11 + (j << n5);
                    for (int l = 0; l < n6; ++l) {
                        final double n26 = (k - n16) * (k - n16) + (l - n15) * (l - n15);
                        final double n27 = (k - n18) * (k - n18) + (l - n17) * (l - n17);
                        final double n28 = (k - n20) * (k - n20) + (l - n19) * (l - n19);
                        final double n29 = (k - n22) * (k - n22) + (l - n21) * (l - n21);
                        if (n26 < n27 && n26 < n28 && n26 < n29) {
                            a2[n25++] = n12;
                        }
                        else if (n27 < n26 && n27 < n28 && n27 < n29) {
                            a2[n25++] = n23;
                        }
                        else if (n28 < n26 && n28 < n27 && n28 < n29) {
                            a2[n25++] = n13;
                        }
                        else {
                            a2[n25++] = n24;
                        }
                    }
                }
                n12 = n23;
                n13 = n24;
            }
        }
        final int[] a3 = IntCache.a(n3 * n4);
        for (int n30 = 0; n30 < n4; ++n30) {
            System.arraycopy(a2, (n30 + (n2 & n6 - 1)) * (n9 << n5) + (n & n6 - 1), a3, n30 * n3, n3);
        }
        return a3;
    }
}
