// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class ChunkProviderHell implements IChunkProvider
{
    private Random i;
    private NoiseGeneratorOctaves j;
    private NoiseGeneratorOctaves k;
    private NoiseGeneratorOctaves l;
    private NoiseGeneratorOctaves m;
    private NoiseGeneratorOctaves n;
    public NoiseGeneratorOctaves a;
    public NoiseGeneratorOctaves b;
    private World o;
    private double[] p;
    public WorldGenNether c;
    private double[] q;
    private double[] r;
    private double[] s;
    private WorldGenBase t;
    double[] d;
    double[] e;
    double[] f;
    double[] g;
    double[] h;
    
    public ChunkProviderHell(final World o, final long n) {
        this.c = new WorldGenNether();
        this.q = new double[256];
        this.r = new double[256];
        this.s = new double[256];
        this.t = new WorldGenCavesHell();
        this.o = o;
        this.i = new Random(n);
        this.j = new NoiseGeneratorOctaves(this.i, 16);
        this.k = new NoiseGeneratorOctaves(this.i, 16);
        this.l = new NoiseGeneratorOctaves(this.i, 8);
        this.m = new NoiseGeneratorOctaves(this.i, 4);
        this.n = new NoiseGeneratorOctaves(this.i, 4);
        this.a = new NoiseGeneratorOctaves(this.i, 10);
        this.b = new NoiseGeneratorOctaves(this.i, 16);
    }
    
    public void a(final int n, final int n2, final byte[] array) {
        final int n3 = 4;
        final int n4 = 32;
        final int n5 = n3 + 1;
        final int n6 = 17;
        final int n7 = n3 + 1;
        this.p = this.a(this.p, n * n3, 0, n2 * n3, n5, n6, n7);
        for (int i = 0; i < n3; ++i) {
            for (int j = 0; j < n3; ++j) {
                for (int k = 0; k < 16; ++k) {
                    final double n8 = 0.125;
                    double n9 = this.p[((i + 0) * n7 + (j + 0)) * n6 + (k + 0)];
                    double n10 = this.p[((i + 0) * n7 + (j + 1)) * n6 + (k + 0)];
                    double n11 = this.p[((i + 1) * n7 + (j + 0)) * n6 + (k + 0)];
                    double n12 = this.p[((i + 1) * n7 + (j + 1)) * n6 + (k + 0)];
                    final double n13 = (this.p[((i + 0) * n7 + (j + 0)) * n6 + (k + 1)] - n9) * n8;
                    final double n14 = (this.p[((i + 0) * n7 + (j + 1)) * n6 + (k + 1)] - n10) * n8;
                    final double n15 = (this.p[((i + 1) * n7 + (j + 0)) * n6 + (k + 1)] - n11) * n8;
                    final double n16 = (this.p[((i + 1) * n7 + (j + 1)) * n6 + (k + 1)] - n12) * n8;
                    for (int l = 0; l < 8; ++l) {
                        final double n17 = 0.25;
                        double n18 = n9;
                        double n19 = n10;
                        final double n20 = (n11 - n9) * n17;
                        final double n21 = (n12 - n10) * n17;
                        for (int n22 = 0; n22 < 4; ++n22) {
                            int n23 = n22 + i * 4 << 11 | 0 + j * 4 << 7 | k * 8 + l;
                            final int n24 = 128;
                            final double n25 = 0.25;
                            double n26 = n18;
                            final double n27 = (n19 - n18) * n25;
                            for (int n28 = 0; n28 < 4; ++n28) {
                                int n29 = 0;
                                if (k * 8 + l < n4) {
                                    n29 = Block.STATIONARY_LAVA.id;
                                }
                                if (n26 > 0.0) {
                                    n29 = Block.NETHERRACK.id;
                                }
                                array[n23] = (byte)n29;
                                n23 += n24;
                                n26 += n27;
                            }
                            n18 += n20;
                            n19 += n21;
                        }
                        n9 += n13;
                        n10 += n14;
                        n11 += n15;
                        n12 += n16;
                    }
                }
            }
        }
    }
    
    public void b(final int n, final int n2, final byte[] array) {
        final int n3 = 64;
        final double n4 = 0.03125;
        this.q = this.m.a(this.q, n * 16, n2 * 16, 0, 16, 16, 1, n4, n4, 1.0);
        this.r = this.m.a(this.r, n * 16, 109, n2 * 16, 16, 1, 16, n4, 1.0, n4);
        this.s = this.n.a(this.s, n * 16, n2 * 16, 0, 16, 16, 1, n4 * 2.0, n4 * 2.0, n4 * 2.0);
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                final boolean b = this.q[i + j * 16] + this.i.nextDouble() * 0.2 > 0.0;
                final boolean b2 = this.r[i + j * 16] + this.i.nextDouble() * 0.2 > 0.0;
                final int n5 = (int)(this.s[i + j * 16] / 3.0 + 3.0 + this.i.nextDouble() * 0.25);
                int n6 = -1;
                byte b3 = (byte)Block.NETHERRACK.id;
                byte b4 = (byte)Block.NETHERRACK.id;
                for (int k = 127; k >= 0; --k) {
                    final int n7 = (j * 16 + i) * 128 + k;
                    if (k >= 127 - this.i.nextInt(5) || k <= 0 + this.i.nextInt(5)) {
                        array[n7] = (byte)Block.BEDROCK.id;
                    }
                    else {
                        final byte b5 = array[n7];
                        if (b5 == 0) {
                            n6 = -1;
                        }
                        else if (b5 == Block.NETHERRACK.id) {
                            if (n6 == -1) {
                                if (n5 <= 0) {
                                    b3 = 0;
                                    b4 = (byte)Block.NETHERRACK.id;
                                }
                                else if (k >= n3 - 4 && k <= n3 + 1) {
                                    b3 = (byte)Block.NETHERRACK.id;
                                    b4 = (byte)Block.NETHERRACK.id;
                                    if (b2) {
                                        b3 = (byte)Block.GRAVEL.id;
                                    }
                                    if (b2) {
                                        b4 = (byte)Block.NETHERRACK.id;
                                    }
                                    if (b) {
                                        b3 = (byte)Block.SOUL_SAND.id;
                                    }
                                    if (b) {
                                        b4 = (byte)Block.SOUL_SAND.id;
                                    }
                                }
                                if (k < n3 && b3 == 0) {
                                    b3 = (byte)Block.STATIONARY_LAVA.id;
                                }
                                n6 = n5;
                                if (k >= n3 - 1) {
                                    array[n7] = b3;
                                }
                                else {
                                    array[n7] = b4;
                                }
                            }
                            else if (n6 > 0) {
                                --n6;
                                array[n7] = b4;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public Chunk getChunkAt(final int n, final int n2) {
        return this.getOrCreateChunk(n, n2);
    }
    
    public Chunk getOrCreateChunk(final int i, final int j) {
        this.i.setSeed(i * 341873128712L + j * 132897987541L);
        final byte[] abyte = new byte[32768];
        this.a(i, j, abyte);
        this.b(i, j, abyte);
        this.t.a(this, this.o, i, j, abyte);
        this.c.a(this, this.o, i, j, abyte);
        final Chunk chunk = new Chunk(this.o, abyte, i, j);
        final BiomeBase[] biomeBlock = this.o.getWorldChunkManager().getBiomeBlock(null, i * 16, j * 16, 16, 16);
        final byte[] m = chunk.m();
        for (int k = 0; k < m.length; ++k) {
            m[k] = (byte)biomeBlock[k].id;
        }
        chunk.n();
        return chunk;
    }
    
    private double[] a(double[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (array == null) {
            array = new double[n4 * n5 * n6];
        }
        final double n7 = 684.412;
        final double n8 = 2053.236;
        this.g = this.a.a(this.g, n, n2, n3, n4, 1, n6, 1.0, 0.0, 1.0);
        this.h = this.b.a(this.h, n, n2, n3, n4, 1, n6, 100.0, 0.0, 100.0);
        this.d = this.l.a(this.d, n, n2, n3, n4, n5, n6, n7 / 80.0, n8 / 60.0, n7 / 80.0);
        this.e = this.j.a(this.e, n, n2, n3, n4, n5, n6, n7, n8, n7);
        this.f = this.k.a(this.f, n, n2, n3, n4, n5, n6, n7, n8, n7);
        int n9 = 0;
        int n10 = 0;
        final double[] array2 = new double[n5];
        for (int i = 0; i < n5; ++i) {
            array2[i] = Math.cos(i * 3.141592653589793 * 6.0 / n5) * 2.0;
            double n11 = i;
            if (i > n5 / 2) {
                n11 = n5 - 1 - i;
            }
            if (n11 < 4.0) {
                final double n12 = 4.0 - n11;
                final double[] array3 = array2;
                final int n13 = i;
                array3[n13] -= n12 * n12 * n12 * 10.0;
            }
        }
        for (int j = 0; j < n4; ++j) {
            for (int k = 0; k < n6; ++k) {
                double n14 = (this.g[n10] + 256.0) / 512.0;
                if (n14 > 1.0) {
                    n14 = 1.0;
                }
                final double n15 = 0.0;
                double n16 = this.h[n10] / 8000.0;
                if (n16 < 0.0) {
                    n16 = -n16;
                }
                double n17 = n16 * 3.0 - 3.0;
                double n19;
                if (n17 < 0.0) {
                    double n18 = n17 / 2.0;
                    if (n18 < -1.0) {
                        n18 = -1.0;
                    }
                    n19 = n18 / 1.4 / 2.0;
                    n14 = 0.0;
                }
                else {
                    if (n17 > 1.0) {
                        n17 = 1.0;
                    }
                    n19 = n17 / 6.0;
                }
                final double n20 = n19 * n5 / 16.0;
                ++n10;
                for (int l = 0; l < n5; ++l) {
                    final double n21 = array2[l];
                    final double n22 = this.e[n9] / 512.0;
                    final double n23 = this.f[n9] / 512.0;
                    final double n24 = (this.d[n9] / 10.0 + 1.0) / 2.0;
                    double n25;
                    if (n24 < 0.0) {
                        n25 = n22;
                    }
                    else if (n24 > 1.0) {
                        n25 = n23;
                    }
                    else {
                        n25 = n22 + (n23 - n22) * n24;
                    }
                    double n26 = n25 - n21;
                    if (l > n5 - 4) {
                        final double n27 = (l - (n5 - 4)) / 3.0f;
                        n26 = n26 * (1.0 - n27) + -10.0 * n27;
                    }
                    if (l < n15) {
                        double n28 = (n15 - l) / 4.0;
                        if (n28 < 0.0) {
                            n28 = 0.0;
                        }
                        if (n28 > 1.0) {
                            n28 = 1.0;
                        }
                        n26 = n26 * (1.0 - n28) + -10.0 * n28;
                    }
                    array[n9] = n26;
                    ++n9;
                }
            }
        }
        return array;
    }
    
    public boolean isChunkLoaded(final int n, final int n2) {
        return true;
    }
    
    public void getChunkAt(final IChunkProvider chunkProvider, final int n, final int n2) {
        BlockSand.instaFall = true;
        final int n3 = n * 16;
        final int n4 = n2 * 16;
        this.c.a(this.o, this.i, n, n2);
        for (int i = 0; i < 8; ++i) {
            new WorldGenHellLava(Block.LAVA.id, false).a(this.o, this.i, n3 + this.i.nextInt(16) + 8, this.i.nextInt(120) + 4, n4 + this.i.nextInt(16) + 8);
        }
        for (int n5 = this.i.nextInt(this.i.nextInt(10) + 1) + 1, j = 0; j < n5; ++j) {
            new WorldGenFire().a(this.o, this.i, n3 + this.i.nextInt(16) + 8, this.i.nextInt(120) + 4, n4 + this.i.nextInt(16) + 8);
        }
        for (int nextInt = this.i.nextInt(this.i.nextInt(10) + 1), k = 0; k < nextInt; ++k) {
            new WorldGenLightStone1().a(this.o, this.i, n3 + this.i.nextInt(16) + 8, this.i.nextInt(120) + 4, n4 + this.i.nextInt(16) + 8);
        }
        for (int l = 0; l < 10; ++l) {
            new WorldGenLightStone2().a(this.o, this.i, n3 + this.i.nextInt(16) + 8, this.i.nextInt(128), n4 + this.i.nextInt(16) + 8);
        }
        if (this.i.nextInt(1) == 0) {
            new WorldGenFlowers(Block.BROWN_MUSHROOM.id).a(this.o, this.i, n3 + this.i.nextInt(16) + 8, this.i.nextInt(128), n4 + this.i.nextInt(16) + 8);
        }
        if (this.i.nextInt(1) == 0) {
            new WorldGenFlowers(Block.RED_MUSHROOM.id).a(this.o, this.i, n3 + this.i.nextInt(16) + 8, this.i.nextInt(128), n4 + this.i.nextInt(16) + 8);
        }
        final WorldGenMinable worldGenMinable = new WorldGenMinable(Block.QUARTZ_ORE.id, 13, Block.NETHERRACK.id);
        for (int n6 = 0; n6 < 16; ++n6) {
            worldGenMinable.a(this.o, this.i, n3 + this.i.nextInt(16), this.i.nextInt(108) + 10, n4 + this.i.nextInt(16));
        }
        for (int n7 = 0; n7 < 16; ++n7) {
            new WorldGenHellLava(Block.LAVA.id, true).a(this.o, this.i, n3 + this.i.nextInt(16), this.i.nextInt(108) + 10, n4 + this.i.nextInt(16));
        }
        BlockSand.instaFall = false;
    }
    
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return true;
    }
    
    public void b() {
    }
    
    public boolean unloadChunks() {
        return false;
    }
    
    public boolean canSave() {
        return true;
    }
    
    public String getName() {
        return "HellRandomLevelSource";
    }
    
    public List getMobsFor(final EnumCreatureType enumCreatureType, final int i, final int n, final int j) {
        if (enumCreatureType == EnumCreatureType.MONSTER && this.c.a(i, n, j)) {
            return this.c.a();
        }
        final BiomeBase biome = this.o.getBiome(i, j);
        if (biome == null) {
            return null;
        }
        return biome.getMobs(enumCreatureType);
    }
    
    public ChunkPosition findNearestMapFeature(final World world, final String s, final int n, final int n2, final int n3) {
        return null;
    }
    
    public int getLoadedChunks() {
        return 0;
    }
    
    public void recreateStructures(final int n, final int n2) {
        this.c.a(this, this.o, n, n2, null);
    }
}
