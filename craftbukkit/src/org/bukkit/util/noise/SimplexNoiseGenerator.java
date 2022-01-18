// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util.noise;

import java.util.Random;
import org.bukkit.World;

public class SimplexNoiseGenerator extends PerlinNoiseGenerator
{
    protected static final double SQRT_3;
    protected static final double SQRT_5;
    protected static final double F2;
    protected static final double G2;
    protected static final double G22;
    protected static final double F3 = 0.3333333333333333;
    protected static final double G3 = 0.16666666666666666;
    protected static final double F4;
    protected static final double G4;
    protected static final double G42;
    protected static final double G43;
    protected static final double G44;
    protected static final int[][] grad4;
    protected static final int[][] simplex;
    protected static double offsetW;
    private static final SimplexNoiseGenerator instance;
    
    protected SimplexNoiseGenerator() {
    }
    
    public SimplexNoiseGenerator(final World world) {
        this(new Random(world.getSeed()));
    }
    
    public SimplexNoiseGenerator(final long seed) {
        this(new Random(seed));
    }
    
    public SimplexNoiseGenerator(final Random rand) {
        super(rand);
        SimplexNoiseGenerator.offsetW = rand.nextDouble() * 256.0;
    }
    
    protected static double dot(final int[] g, final double x, final double y) {
        return g[0] * x + g[1] * y;
    }
    
    protected static double dot(final int[] g, final double x, final double y, final double z) {
        return g[0] * x + g[1] * y + g[2] * z;
    }
    
    protected static double dot(final int[] g, final double x, final double y, final double z, final double w) {
        return g[0] * x + g[1] * y + g[2] * z + g[3] * w;
    }
    
    public static double getNoise(final double xin) {
        return SimplexNoiseGenerator.instance.noise(xin);
    }
    
    public static double getNoise(final double xin, final double yin) {
        return SimplexNoiseGenerator.instance.noise(xin, yin);
    }
    
    public static double getNoise(final double xin, final double yin, final double zin) {
        return SimplexNoiseGenerator.instance.noise(xin, yin, zin);
    }
    
    public static double getNoise(final double x, final double y, final double z, final double w) {
        return SimplexNoiseGenerator.instance.noise(x, y, z, w);
    }
    
    public double noise(double xin, double yin, double zin) {
        xin += this.offsetX;
        yin += this.offsetY;
        zin += this.offsetZ;
        final double s = (xin + yin + zin) * 0.3333333333333333;
        final int i = NoiseGenerator.floor(xin + s);
        final int j = NoiseGenerator.floor(yin + s);
        final int k = NoiseGenerator.floor(zin + s);
        final double t = (i + j + k) * 0.16666666666666666;
        final double X0 = i - t;
        final double Y0 = j - t;
        final double Z0 = k - t;
        final double x0 = xin - X0;
        final double y0 = yin - Y0;
        final double z0 = zin - Z0;
        int i2;
        int j2;
        int k2;
        int i3;
        int j3;
        int k3;
        if (x0 >= y0) {
            if (y0 >= z0) {
                i2 = 1;
                j2 = 0;
                k2 = 0;
                i3 = 1;
                j3 = 1;
                k3 = 0;
            }
            else if (x0 >= z0) {
                i2 = 1;
                j2 = 0;
                k2 = 0;
                i3 = 1;
                j3 = 0;
                k3 = 1;
            }
            else {
                i2 = 0;
                j2 = 0;
                k2 = 1;
                i3 = 1;
                j3 = 0;
                k3 = 1;
            }
        }
        else if (y0 < z0) {
            i2 = 0;
            j2 = 0;
            k2 = 1;
            i3 = 0;
            j3 = 1;
            k3 = 1;
        }
        else if (x0 < z0) {
            i2 = 0;
            j2 = 1;
            k2 = 0;
            i3 = 0;
            j3 = 1;
            k3 = 1;
        }
        else {
            i2 = 0;
            j2 = 1;
            k2 = 0;
            i3 = 1;
            j3 = 1;
            k3 = 0;
        }
        final double x2 = x0 - i2 + 0.16666666666666666;
        final double y2 = y0 - j2 + 0.16666666666666666;
        final double z2 = z0 - k2 + 0.16666666666666666;
        final double x3 = x0 - i3 + 0.3333333333333333;
        final double y3 = y0 - j3 + 0.3333333333333333;
        final double z3 = z0 - k3 + 0.3333333333333333;
        final double x4 = x0 - 1.0 + 0.5;
        final double y4 = y0 - 1.0 + 0.5;
        final double z4 = z0 - 1.0 + 0.5;
        final int ii = i & 0xFF;
        final int jj = j & 0xFF;
        final int kk = k & 0xFF;
        final int gi0 = this.perm[ii + this.perm[jj + this.perm[kk]]] % 12;
        final int gi2 = this.perm[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2]]] % 12;
        final int gi3 = this.perm[ii + i3 + this.perm[jj + j3 + this.perm[kk + k3]]] % 12;
        final int gi4 = this.perm[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1]]] % 12;
        double t2 = 0.6 - x0 * x0 - y0 * y0 - z0 * z0;
        double n0;
        if (t2 < 0.0) {
            n0 = 0.0;
        }
        else {
            t2 *= t2;
            n0 = t2 * t2 * dot(SimplexNoiseGenerator.grad3[gi0], x0, y0, z0);
        }
        double t3 = 0.6 - x2 * x2 - y2 * y2 - z2 * z2;
        double n2;
        if (t3 < 0.0) {
            n2 = 0.0;
        }
        else {
            t3 *= t3;
            n2 = t3 * t3 * dot(SimplexNoiseGenerator.grad3[gi2], x2, y2, z2);
        }
        double t4 = 0.6 - x3 * x3 - y3 * y3 - z3 * z3;
        double n3;
        if (t4 < 0.0) {
            n3 = 0.0;
        }
        else {
            t4 *= t4;
            n3 = t4 * t4 * dot(SimplexNoiseGenerator.grad3[gi3], x3, y3, z3);
        }
        double t5 = 0.6 - x4 * x4 - y4 * y4 - z4 * z4;
        double n4;
        if (t5 < 0.0) {
            n4 = 0.0;
        }
        else {
            t5 *= t5;
            n4 = t5 * t5 * dot(SimplexNoiseGenerator.grad3[gi4], x4, y4, z4);
        }
        return 32.0 * (n0 + n2 + n3 + n4);
    }
    
    public double noise(double xin, double yin) {
        xin += this.offsetX;
        yin += this.offsetY;
        final double s = (xin + yin) * SimplexNoiseGenerator.F2;
        final int i = NoiseGenerator.floor(xin + s);
        final int j = NoiseGenerator.floor(yin + s);
        final double t = (i + j) * SimplexNoiseGenerator.G2;
        final double X0 = i - t;
        final double Y0 = j - t;
        final double x0 = xin - X0;
        final double y0 = yin - Y0;
        int i2;
        int j2;
        if (x0 > y0) {
            i2 = 1;
            j2 = 0;
        }
        else {
            i2 = 0;
            j2 = 1;
        }
        final double x2 = x0 - i2 + SimplexNoiseGenerator.G2;
        final double y2 = y0 - j2 + SimplexNoiseGenerator.G2;
        final double x3 = x0 + SimplexNoiseGenerator.G22;
        final double y3 = y0 + SimplexNoiseGenerator.G22;
        final int ii = i & 0xFF;
        final int jj = j & 0xFF;
        final int gi0 = this.perm[ii + this.perm[jj]] % 12;
        final int gi2 = this.perm[ii + i2 + this.perm[jj + j2]] % 12;
        final int gi3 = this.perm[ii + 1 + this.perm[jj + 1]] % 12;
        double t2 = 0.5 - x0 * x0 - y0 * y0;
        double n0;
        if (t2 < 0.0) {
            n0 = 0.0;
        }
        else {
            t2 *= t2;
            n0 = t2 * t2 * dot(SimplexNoiseGenerator.grad3[gi0], x0, y0);
        }
        double t3 = 0.5 - x2 * x2 - y2 * y2;
        double n2;
        if (t3 < 0.0) {
            n2 = 0.0;
        }
        else {
            t3 *= t3;
            n2 = t3 * t3 * dot(SimplexNoiseGenerator.grad3[gi2], x2, y2);
        }
        double t4 = 0.5 - x3 * x3 - y3 * y3;
        double n3;
        if (t4 < 0.0) {
            n3 = 0.0;
        }
        else {
            t4 *= t4;
            n3 = t4 * t4 * dot(SimplexNoiseGenerator.grad3[gi3], x3, y3);
        }
        return 70.0 * (n0 + n2 + n3);
    }
    
    public double noise(double x, double y, double z, double w) {
        x += this.offsetX;
        y += this.offsetY;
        z += this.offsetZ;
        w += SimplexNoiseGenerator.offsetW;
        final double s = (x + y + z + w) * SimplexNoiseGenerator.F4;
        final int i = NoiseGenerator.floor(x + s);
        final int j = NoiseGenerator.floor(y + s);
        final int k = NoiseGenerator.floor(z + s);
        final int l = NoiseGenerator.floor(w + s);
        final double t = (i + j + k + l) * SimplexNoiseGenerator.G4;
        final double X0 = i - t;
        final double Y0 = j - t;
        final double Z0 = k - t;
        final double W0 = l - t;
        final double x2 = x - X0;
        final double y2 = y - Y0;
        final double z2 = z - Z0;
        final double w2 = w - W0;
        final int c1 = (x2 > y2) ? 32 : 0;
        final int c2 = (x2 > z2) ? 16 : 0;
        final int c3 = (y2 > z2) ? 8 : 0;
        final int c4 = (x2 > w2) ? 4 : 0;
        final int c5 = (y2 > w2) ? 2 : 0;
        final int c6 = (z2 > w2) ? 1 : 0;
        final int c7 = c1 + c2 + c3 + c4 + c5 + c6;
        final int i2 = (SimplexNoiseGenerator.simplex[c7][0] >= 3) ? 1 : 0;
        final int j2 = (SimplexNoiseGenerator.simplex[c7][1] >= 3) ? 1 : 0;
        final int k2 = (SimplexNoiseGenerator.simplex[c7][2] >= 3) ? 1 : 0;
        final int l2 = (SimplexNoiseGenerator.simplex[c7][3] >= 3) ? 1 : 0;
        final int i3 = (SimplexNoiseGenerator.simplex[c7][0] >= 2) ? 1 : 0;
        final int j3 = (SimplexNoiseGenerator.simplex[c7][1] >= 2) ? 1 : 0;
        final int k3 = (SimplexNoiseGenerator.simplex[c7][2] >= 2) ? 1 : 0;
        final int l3 = (SimplexNoiseGenerator.simplex[c7][3] >= 2) ? 1 : 0;
        final int i4 = (SimplexNoiseGenerator.simplex[c7][0] >= 1) ? 1 : 0;
        final int j4 = (SimplexNoiseGenerator.simplex[c7][1] >= 1) ? 1 : 0;
        final int k4 = (SimplexNoiseGenerator.simplex[c7][2] >= 1) ? 1 : 0;
        final int l4 = (SimplexNoiseGenerator.simplex[c7][3] >= 1) ? 1 : 0;
        final double x3 = x2 - i2 + SimplexNoiseGenerator.G4;
        final double y3 = y2 - j2 + SimplexNoiseGenerator.G4;
        final double z3 = z2 - k2 + SimplexNoiseGenerator.G4;
        final double w3 = w2 - l2 + SimplexNoiseGenerator.G4;
        final double x4 = x2 - i3 + SimplexNoiseGenerator.G42;
        final double y4 = y2 - j3 + SimplexNoiseGenerator.G42;
        final double z4 = z2 - k3 + SimplexNoiseGenerator.G42;
        final double w4 = w2 - l3 + SimplexNoiseGenerator.G42;
        final double x5 = x2 - i4 + SimplexNoiseGenerator.G43;
        final double y5 = y2 - j4 + SimplexNoiseGenerator.G43;
        final double z5 = z2 - k4 + SimplexNoiseGenerator.G43;
        final double w5 = w2 - l4 + SimplexNoiseGenerator.G43;
        final double x6 = x2 + SimplexNoiseGenerator.G44;
        final double y6 = y2 + SimplexNoiseGenerator.G44;
        final double z6 = z2 + SimplexNoiseGenerator.G44;
        final double w6 = w2 + SimplexNoiseGenerator.G44;
        final int ii = i & 0xFF;
        final int jj = j & 0xFF;
        final int kk = k & 0xFF;
        final int ll = l & 0xFF;
        final int gi0 = this.perm[ii + this.perm[jj + this.perm[kk + this.perm[ll]]]] % 32;
        final int gi2 = this.perm[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2 + this.perm[ll + l2]]]] % 32;
        final int gi3 = this.perm[ii + i3 + this.perm[jj + j3 + this.perm[kk + k3 + this.perm[ll + l3]]]] % 32;
        final int gi4 = this.perm[ii + i4 + this.perm[jj + j4 + this.perm[kk + k4 + this.perm[ll + l4]]]] % 32;
        final int gi5 = this.perm[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1 + this.perm[ll + 1]]]] % 32;
        double t2 = 0.6 - x2 * x2 - y2 * y2 - z2 * z2 - w2 * w2;
        double n0;
        if (t2 < 0.0) {
            n0 = 0.0;
        }
        else {
            t2 *= t2;
            n0 = t2 * t2 * dot(SimplexNoiseGenerator.grad4[gi0], x2, y2, z2, w2);
        }
        double t3 = 0.6 - x3 * x3 - y3 * y3 - z3 * z3 - w3 * w3;
        double n2;
        if (t3 < 0.0) {
            n2 = 0.0;
        }
        else {
            t3 *= t3;
            n2 = t3 * t3 * dot(SimplexNoiseGenerator.grad4[gi2], x3, y3, z3, w3);
        }
        double t4 = 0.6 - x4 * x4 - y4 * y4 - z4 * z4 - w4 * w4;
        double n3;
        if (t4 < 0.0) {
            n3 = 0.0;
        }
        else {
            t4 *= t4;
            n3 = t4 * t4 * dot(SimplexNoiseGenerator.grad4[gi3], x4, y4, z4, w4);
        }
        double t5 = 0.6 - x5 * x5 - y5 * y5 - z5 * z5 - w5 * w5;
        double n4;
        if (t5 < 0.0) {
            n4 = 0.0;
        }
        else {
            t5 *= t5;
            n4 = t5 * t5 * dot(SimplexNoiseGenerator.grad4[gi4], x5, y5, z5, w5);
        }
        double t6 = 0.6 - x6 * x6 - y6 * y6 - z6 * z6 - w6 * w6;
        double n5;
        if (t6 < 0.0) {
            n5 = 0.0;
        }
        else {
            t6 *= t6;
            n5 = t6 * t6 * dot(SimplexNoiseGenerator.grad4[gi5], x6, y6, z6, w6);
        }
        return 27.0 * (n0 + n2 + n3 + n4 + n5);
    }
    
    public static SimplexNoiseGenerator getInstance() {
        return SimplexNoiseGenerator.instance;
    }
    
    static {
        SQRT_3 = Math.sqrt(3.0);
        SQRT_5 = Math.sqrt(5.0);
        F2 = 0.5 * (SimplexNoiseGenerator.SQRT_3 - 1.0);
        G2 = (3.0 - SimplexNoiseGenerator.SQRT_3) / 6.0;
        G22 = SimplexNoiseGenerator.G2 * 2.0 - 1.0;
        F4 = (SimplexNoiseGenerator.SQRT_5 - 1.0) / 4.0;
        G4 = (5.0 - SimplexNoiseGenerator.SQRT_5) / 20.0;
        G42 = SimplexNoiseGenerator.G4 * 2.0;
        G43 = SimplexNoiseGenerator.G4 * 3.0;
        G44 = SimplexNoiseGenerator.G4 * 4.0 - 1.0;
        grad4 = new int[][] { { 0, 1, 1, 1 }, { 0, 1, 1, -1 }, { 0, 1, -1, 1 }, { 0, 1, -1, -1 }, { 0, -1, 1, 1 }, { 0, -1, 1, -1 }, { 0, -1, -1, 1 }, { 0, -1, -1, -1 }, { 1, 0, 1, 1 }, { 1, 0, 1, -1 }, { 1, 0, -1, 1 }, { 1, 0, -1, -1 }, { -1, 0, 1, 1 }, { -1, 0, 1, -1 }, { -1, 0, -1, 1 }, { -1, 0, -1, -1 }, { 1, 1, 0, 1 }, { 1, 1, 0, -1 }, { 1, -1, 0, 1 }, { 1, -1, 0, -1 }, { -1, 1, 0, 1 }, { -1, 1, 0, -1 }, { -1, -1, 0, 1 }, { -1, -1, 0, -1 }, { 1, 1, 1, 0 }, { 1, 1, -1, 0 }, { 1, -1, 1, 0 }, { 1, -1, -1, 0 }, { -1, 1, 1, 0 }, { -1, 1, -1, 0 }, { -1, -1, 1, 0 }, { -1, -1, -1, 0 } };
        simplex = new int[][] { { 0, 1, 2, 3 }, { 0, 1, 3, 2 }, { 0, 0, 0, 0 }, { 0, 2, 3, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 2, 3, 0 }, { 0, 2, 1, 3 }, { 0, 0, 0, 0 }, { 0, 3, 1, 2 }, { 0, 3, 2, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 3, 2, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 2, 0, 3 }, { 0, 0, 0, 0 }, { 1, 3, 0, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 3, 0, 1 }, { 2, 3, 1, 0 }, { 1, 0, 2, 3 }, { 1, 0, 3, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 0, 3, 1 }, { 0, 0, 0, 0 }, { 2, 1, 3, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 0, 1, 3 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 3, 0, 1, 2 }, { 3, 0, 2, 1 }, { 0, 0, 0, 0 }, { 3, 1, 2, 0 }, { 2, 1, 0, 3 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 3, 1, 0, 2 }, { 0, 0, 0, 0 }, { 3, 2, 0, 1 }, { 3, 2, 1, 0 } };
        instance = new SimplexNoiseGenerator();
    }
}
