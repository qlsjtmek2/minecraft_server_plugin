// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BiomeDecorator
{
    protected World a;
    protected Random b;
    protected int c;
    protected int d;
    protected BiomeBase e;
    protected WorldGenerator f;
    protected WorldGenerator g;
    protected WorldGenerator h;
    protected WorldGenerator i;
    protected WorldGenerator j;
    protected WorldGenerator k;
    protected WorldGenerator l;
    protected WorldGenerator m;
    protected WorldGenerator n;
    protected WorldGenerator o;
    protected WorldGenerator p;
    protected WorldGenerator q;
    protected WorldGenerator r;
    protected WorldGenerator s;
    protected WorldGenerator t;
    protected WorldGenerator u;
    protected WorldGenerator v;
    protected WorldGenerator w;
    protected WorldGenerator x;
    protected int y;
    protected int z;
    protected int A;
    protected int B;
    protected int C;
    protected int D;
    protected int E;
    protected int F;
    protected int G;
    protected int H;
    protected int I;
    protected int J;
    public boolean K;
    
    public BiomeDecorator(final BiomeBase e) {
        this.f = new WorldGenClay(4);
        this.g = new WorldGenSand(7, Block.SAND.id);
        this.h = new WorldGenSand(6, Block.GRAVEL.id);
        this.i = new WorldGenMinable(Block.DIRT.id, 32);
        this.j = new WorldGenMinable(Block.GRAVEL.id, 32);
        this.k = new WorldGenMinable(Block.COAL_ORE.id, 16);
        this.l = new WorldGenMinable(Block.IRON_ORE.id, 8);
        this.m = new WorldGenMinable(Block.GOLD_ORE.id, 8);
        this.n = new WorldGenMinable(Block.REDSTONE_ORE.id, 7);
        this.o = new WorldGenMinable(Block.DIAMOND_ORE.id, 7);
        this.p = new WorldGenMinable(Block.LAPIS_ORE.id, 6);
        this.q = new WorldGenFlowers(Block.YELLOW_FLOWER.id);
        this.r = new WorldGenFlowers(Block.RED_ROSE.id);
        this.s = new WorldGenFlowers(Block.BROWN_MUSHROOM.id);
        this.t = new WorldGenFlowers(Block.RED_MUSHROOM.id);
        this.u = new WorldGenHugeMushroom();
        this.v = new WorldGenReed();
        this.w = new WorldGenCactus();
        this.x = new WorldGenWaterLily();
        this.y = 0;
        this.z = 0;
        this.A = 2;
        this.B = 1;
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.G = 1;
        this.H = 3;
        this.I = 1;
        this.J = 0;
        this.K = true;
        this.e = e;
    }
    
    public void a(final World a, final Random b, final int c, final int d) {
        if (this.a != null) {
            throw new RuntimeException("Already decorating!!");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.a();
        this.a = null;
        this.b = null;
    }
    
    protected void a() {
        this.b();
        for (int i = 0; i < this.H; ++i) {
            final int j = this.c + this.b.nextInt(16) + 8;
            final int k = this.d + this.b.nextInt(16) + 8;
            this.g.a(this.a, this.b, j, this.a.i(j, k), k);
        }
        for (int l = 0; l < this.I; ++l) {
            final int m = this.c + this.b.nextInt(16) + 8;
            final int j2 = this.d + this.b.nextInt(16) + 8;
            this.f.a(this.a, this.b, m, this.a.i(m, j2), j2);
        }
        for (int n = 0; n < this.G; ++n) {
            final int i2 = this.c + this.b.nextInt(16) + 8;
            final int j3 = this.d + this.b.nextInt(16) + 8;
            this.g.a(this.a, this.b, i2, this.a.i(i2, j3), j3);
        }
        int z = this.z;
        if (this.b.nextInt(10) == 0) {
            ++z;
        }
        for (int n2 = 0; n2 < z; ++n2) {
            final int i3 = this.c + this.b.nextInt(16) + 8;
            final int j4 = this.d + this.b.nextInt(16) + 8;
            final WorldGenerator a = this.e.a(this.b);
            a.a(1.0, 1.0, 1.0);
            a.a(this.a, this.b, i3, this.a.getHighestBlockYAt(i3, j4), j4);
        }
        for (int n3 = 0; n3 < this.J; ++n3) {
            final int i4 = this.c + this.b.nextInt(16) + 8;
            final int j5 = this.d + this.b.nextInt(16) + 8;
            this.u.a(this.a, this.b, i4, this.a.getHighestBlockYAt(i4, j5), j5);
        }
        for (int n4 = 0; n4 < this.A; ++n4) {
            this.q.a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
            if (this.b.nextInt(4) == 0) {
                this.r.a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
            }
        }
        for (int n5 = 0; n5 < this.B; ++n5) {
            this.e.b(this.b).a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
        }
        for (int n6 = 0; n6 < this.C; ++n6) {
            new WorldGenDeadBush(Block.DEAD_BUSH.id).a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
        }
        for (int n7 = 0; n7 < this.y; ++n7) {
            int i5;
            int k2;
            int nextInt;
            for (i5 = this.c + this.b.nextInt(16) + 8, k2 = this.d + this.b.nextInt(16) + 8, nextInt = this.b.nextInt(128); nextInt > 0 && this.a.getTypeId(i5, nextInt - 1, k2) == 0; --nextInt) {}
            this.x.a(this.a, this.b, i5, nextInt, k2);
        }
        for (int n8 = 0; n8 < this.D; ++n8) {
            if (this.b.nextInt(4) == 0) {
                final int i6 = this.c + this.b.nextInt(16) + 8;
                final int j6 = this.d + this.b.nextInt(16) + 8;
                this.s.a(this.a, this.b, i6, this.a.getHighestBlockYAt(i6, j6), j6);
            }
            if (this.b.nextInt(8) == 0) {
                this.t.a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
            }
        }
        if (this.b.nextInt(4) == 0) {
            this.s.a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
        }
        if (this.b.nextInt(8) == 0) {
            this.t.a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
        }
        for (int n9 = 0; n9 < this.E; ++n9) {
            this.v.a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
        }
        for (int n10 = 0; n10 < 10; ++n10) {
            this.v.a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
        }
        if (this.b.nextInt(32) == 0) {
            new WorldGenPumpkin().a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
        }
        for (int n11 = 0; n11 < this.F; ++n11) {
            this.w.a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(128), this.d + this.b.nextInt(16) + 8);
        }
        if (this.K) {
            for (int n12 = 0; n12 < 50; ++n12) {
                new WorldGenLiquids(Block.WATER.id).a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(this.b.nextInt(120) + 8), this.d + this.b.nextInt(16) + 8);
            }
            for (int n13 = 0; n13 < 20; ++n13) {
                new WorldGenLiquids(Block.LAVA.id).a(this.a, this.b, this.c + this.b.nextInt(16) + 8, this.b.nextInt(this.b.nextInt(this.b.nextInt(112) + 8) + 8), this.d + this.b.nextInt(16) + 8);
            }
        }
    }
    
    protected void a(final int n, final WorldGenerator worldGenerator, final int n2, final int n3) {
        for (int i = 0; i < n; ++i) {
            worldGenerator.a(this.a, this.b, this.c + this.b.nextInt(16), this.b.nextInt(n3 - n2) + n2, this.d + this.b.nextInt(16));
        }
    }
    
    protected void b(final int n, final WorldGenerator worldGenerator, final int n2, final int n3) {
        for (int i = 0; i < n; ++i) {
            worldGenerator.a(this.a, this.b, this.c + this.b.nextInt(16), this.b.nextInt(n3) + this.b.nextInt(n3) + (n2 - n3), this.d + this.b.nextInt(16));
        }
    }
    
    protected void b() {
        this.a(20, this.i, 0, 128);
        this.a(10, this.j, 0, 128);
        this.a(20, this.k, 0, 128);
        this.a(20, this.l, 0, 64);
        this.a(2, this.m, 0, 32);
        this.a(8, this.n, 0, 16);
        this.a(1, this.o, 0, 16);
        this.b(1, this.p, 16, 16);
    }
}
