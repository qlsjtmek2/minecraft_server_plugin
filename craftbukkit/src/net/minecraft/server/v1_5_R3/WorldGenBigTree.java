// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenBigTree extends WorldGenerator implements BlockSapling.TreeGenerator
{
    static final byte[] a;
    Random b;
    BlockChangeDelegate world;
    int[] d;
    int e;
    int f;
    double g;
    double h;
    double i;
    double j;
    double k;
    int l;
    int m;
    int n;
    int[][] o;
    
    public WorldGenBigTree(final boolean flag) {
        super(flag);
        this.b = new Random();
        this.d = new int[] { 0, 0, 0 };
        this.e = 0;
        this.g = 0.618;
        this.h = 1.0;
        this.i = 0.381;
        this.j = 1.0;
        this.k = 1.0;
        this.l = 1;
        this.m = 12;
        this.n = 4;
    }
    
    void a() {
        this.f = (int)(this.e * this.g);
        if (this.f >= this.e) {
            this.f = this.e - 1;
        }
        int i = (int)(1.382 + Math.pow(this.k * this.e / 13.0, 2.0));
        if (i < 1) {
            i = 1;
        }
        final int[][] aint = new int[i * this.e][4];
        int j = this.d[1] + this.e - this.n;
        int k = 1;
        final int l = this.d[1] + this.f;
        int i2 = j - this.d[1];
        aint[0][0] = this.d[0];
        aint[0][1] = j;
        aint[0][2] = this.d[2];
        aint[0][3] = l;
        --j;
        while (i2 >= 0) {
            int j2 = 0;
            final float f = this.a(i2);
            if (f < 0.0f) {
                --j;
                --i2;
            }
            else {
                final double d0 = 0.5;
                while (j2 < i) {
                    final double d2 = this.j * f * (this.b.nextFloat() + 0.328);
                    final double d3 = this.b.nextFloat() * 2.0 * 3.14159;
                    final int k2 = MathHelper.floor(d2 * Math.sin(d3) + this.d[0] + d0);
                    final int l2 = MathHelper.floor(d2 * Math.cos(d3) + this.d[2] + d0);
                    final int[] aint2 = { k2, j, l2 };
                    final int[] aint3 = { k2, j + this.n, l2 };
                    if (this.a(aint2, aint3) == -1) {
                        final int[] aint4 = { this.d[0], this.d[1], this.d[2] };
                        final double d4 = Math.sqrt(Math.pow(Math.abs(this.d[0] - aint2[0]), 2.0) + Math.pow(Math.abs(this.d[2] - aint2[2]), 2.0));
                        final double d5 = d4 * this.i;
                        if (aint2[1] - d5 > l) {
                            aint4[1] = l;
                        }
                        else {
                            aint4[1] = (int)(aint2[1] - d5);
                        }
                        if (this.a(aint4, aint2) == -1) {
                            aint[k][0] = k2;
                            aint[k][1] = j;
                            aint[k][2] = l2;
                            aint[k][3] = aint4[1];
                            ++k;
                        }
                    }
                    ++j2;
                }
                --j;
                --i2;
            }
        }
        System.arraycopy(aint, 0, this.o = new int[k][4], 0, k);
    }
    
    void a(final int i, final int j, final int k, final float f, final byte b0, final int l) {
        final int i2 = (int)(f + 0.618);
        final byte b = WorldGenBigTree.a[b0];
        final byte b2 = WorldGenBigTree.a[b0 + 3];
        final int[] aint = { i, j, k };
        final int[] aint2 = { 0, 0, 0 };
        int j2 = -i2;
        int k2 = -i2;
        aint2[b0] = aint[b0];
        while (j2 <= i2) {
            aint2[b] = aint[b] + j2;
            for (k2 = -i2; k2 <= i2; ++k2) {
                final double d0 = Math.pow(Math.abs(j2) + 0.5, 2.0) + Math.pow(Math.abs(k2) + 0.5, 2.0);
                if (d0 <= f * f) {
                    aint2[b2] = aint[b2] + k2;
                    final int l2 = this.world.getTypeId(aint2[0], aint2[1], aint2[2]);
                    if (l2 == 0 || l2 == Block.LEAVES.id) {
                        this.setTypeAndData(this.world, aint2[0], aint2[1], aint2[2], l, 0);
                    }
                }
            }
            ++j2;
        }
    }
    
    float a(final int i) {
        if (i < this.e * 0.3) {
            return -1.618f;
        }
        final float f = this.e / 2.0f;
        final float f2 = this.e / 2.0f - i;
        float f3;
        if (f2 == 0.0f) {
            f3 = f;
        }
        else if (Math.abs(f2) >= f) {
            f3 = 0.0f;
        }
        else {
            f3 = (float)Math.sqrt(Math.pow(Math.abs(f), 2.0) - Math.pow(Math.abs(f2), 2.0));
        }
        f3 *= 0.5f;
        return f3;
    }
    
    float b(final int i) {
        return (i >= 0 && i < this.n) ? ((i != 0 && i != this.n - 1) ? 3.0f : 2.0f) : -1.0f;
    }
    
    void a(final int i, final int j, final int k) {
        for (int l = j, i2 = j + this.n; l < i2; ++l) {
            final float f = this.b(l - j);
            this.a(i, l, k, f, (byte)1, Block.LEAVES.id);
        }
    }
    
    void a(final int[] aint, final int[] aint1, final int i) {
        final int[] aint2 = { 0, 0, 0 };
        byte b0 = 0;
        byte b2 = 0;
        while (b0 < 3) {
            aint2[b0] = aint1[b0] - aint[b0];
            if (Math.abs(aint2[b0]) > Math.abs(aint2[b2])) {
                b2 = b0;
            }
            ++b0;
        }
        if (aint2[b2] != 0) {
            final byte b3 = WorldGenBigTree.a[b2];
            final byte b4 = WorldGenBigTree.a[b2 + 3];
            byte b5;
            if (aint2[b2] > 0) {
                b5 = 1;
            }
            else {
                b5 = -1;
            }
            final double d0 = aint2[b3] / aint2[b2];
            final double d2 = aint2[b4] / aint2[b2];
            final int[] aint3 = { 0, 0, 0 };
            for (int j = 0, k = aint2[b2] + b5; j != k; j += b5) {
                aint3[b2] = MathHelper.floor(aint[b2] + j + 0.5);
                aint3[b3] = MathHelper.floor(aint[b3] + j * d0 + 0.5);
                aint3[b4] = MathHelper.floor(aint[b4] + j * d2 + 0.5);
                byte b6 = 0;
                final int l = Math.abs(aint3[0] - aint[0]);
                final int i2 = Math.abs(aint3[2] - aint[2]);
                final int j2 = Math.max(l, i2);
                if (j2 > 0) {
                    if (l == j2) {
                        b6 = 4;
                    }
                    else if (i2 == j2) {
                        b6 = 8;
                    }
                }
                this.setTypeAndData(this.world, aint3[0], aint3[1], aint3[2], i, b6);
            }
        }
    }
    
    void b() {
        for (int i = 0, j = this.o.length; i < j; ++i) {
            final int k = this.o[i][0];
            final int l = this.o[i][1];
            final int i2 = this.o[i][2];
            this.a(k, l, i2);
        }
    }
    
    boolean c(final int i) {
        return i >= this.e * 0.2;
    }
    
    void c() {
        final int i = this.d[0];
        final int j = this.d[1];
        final int k = this.d[1] + this.f;
        final int l = this.d[2];
        final int[] aint = { i, j, l };
        final int[] aint2 = { i, k, l };
        this.a(aint, aint2, Block.LOG.id);
        if (this.l == 2) {
            final int[] array = aint;
            final int n = 0;
            ++array[n];
            final int[] array2 = aint2;
            final int n2 = 0;
            ++array2[n2];
            this.a(aint, aint2, Block.LOG.id);
            final int[] array3 = aint;
            final int n3 = 2;
            ++array3[n3];
            final int[] array4 = aint2;
            final int n4 = 2;
            ++array4[n4];
            this.a(aint, aint2, Block.LOG.id);
            final int[] array5 = aint;
            final int n5 = 0;
            --array5[n5];
            final int[] array6 = aint2;
            final int n6 = 0;
            --array6[n6];
            this.a(aint, aint2, Block.LOG.id);
        }
    }
    
    void d() {
        int i = 0;
        final int j = this.o.length;
        final int[] aint = { this.d[0], this.d[1], this.d[2] };
        while (i < j) {
            final int[] aint2 = this.o[i];
            final int[] aint3 = { aint2[0], aint2[1], aint2[2] };
            aint[1] = aint2[3];
            final int k = aint[1] - this.d[1];
            if (this.c(k)) {
                this.a(aint, aint3, (byte)Block.LOG.id);
            }
            ++i;
        }
    }
    
    int a(final int[] aint, final int[] aint1) {
        final int[] aint2 = { 0, 0, 0 };
        byte b0 = 0;
        byte b2 = 0;
        while (b0 < 3) {
            aint2[b0] = aint1[b0] - aint[b0];
            if (Math.abs(aint2[b0]) > Math.abs(aint2[b2])) {
                b2 = b0;
            }
            ++b0;
        }
        if (aint2[b2] == 0) {
            return -1;
        }
        final byte b3 = WorldGenBigTree.a[b2];
        final byte b4 = WorldGenBigTree.a[b2 + 3];
        byte b5;
        if (aint2[b2] > 0) {
            b5 = 1;
        }
        else {
            b5 = -1;
        }
        final double d0 = aint2[b3] / aint2[b2];
        final double d2 = aint2[b4] / aint2[b2];
        final int[] aint3 = { 0, 0, 0 };
        int i;
        int j;
        for (i = 0, j = aint2[b2] + b5; i != j; i += b5) {
            aint3[b2] = aint[b2] + i;
            aint3[b3] = MathHelper.floor(aint[b3] + i * d0);
            aint3[b4] = MathHelper.floor(aint[b4] + i * d2);
            final int k = this.world.getTypeId(aint3[0], aint3[1], aint3[2]);
            if (k != 0 && k != Block.LEAVES.id) {
                break;
            }
            if (aint3[1] >= 256) {
                break;
            }
        }
        return (i == j) ? -1 : Math.abs(i);
    }
    
    boolean e() {
        final int[] aint = { this.d[0], this.d[1], this.d[2] };
        final int[] aint2 = { this.d[0], this.d[1] + this.e - 1, this.d[2] };
        final int i = this.world.getTypeId(this.d[0], this.d[1] - 1, this.d[2]);
        if (i != 2 && i != 3) {
            return false;
        }
        final int j = this.a(aint, aint2);
        if (j == -1) {
            return true;
        }
        if (j < 6) {
            return false;
        }
        this.e = j;
        return true;
    }
    
    public void a(final double d0, final double d1, final double d2) {
        this.m = (int)(d0 * 12.0);
        if (d0 > 0.5) {
            this.n = 5;
        }
        this.j = d1;
        this.k = d2;
    }
    
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        return this.generate((BlockChangeDelegate)world, random, i, j, k);
    }
    
    public boolean generate(final BlockChangeDelegate world, final Random random, final int i, final int j, final int k) {
        this.world = world;
        final long l = random.nextLong();
        this.b.setSeed(l);
        this.d[0] = i;
        this.d[1] = j;
        this.d[2] = k;
        if (this.e == 0) {
            this.e = 5 + this.b.nextInt(this.m);
        }
        if (!this.e()) {
            return false;
        }
        this.a();
        this.b();
        this.c();
        this.d();
        return true;
    }
    
    static {
        a = new byte[] { 2, 0, 0, 1, 2, 1 };
    }
}
