// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class ChunkProviderGenerate implements IChunkProvider
{
    private Random k;
    private NoiseGeneratorOctaves l;
    private NoiseGeneratorOctaves m;
    private NoiseGeneratorOctaves n;
    private NoiseGeneratorOctaves o;
    public NoiseGeneratorOctaves a;
    public NoiseGeneratorOctaves b;
    public NoiseGeneratorOctaves c;
    private World p;
    private final boolean q;
    private double[] r;
    private double[] s;
    private WorldGenBase t;
    private WorldGenStronghold u;
    private WorldGenVillage v;
    private WorldGenMineshaft w;
    private WorldGenLargeFeature x;
    private WorldGenBase y;
    private BiomeBase[] z;
    double[] d;
    double[] e;
    double[] f;
    double[] g;
    double[] h;
    float[] i;
    int[][] j;
    
    public ChunkProviderGenerate(final World p3, final long n, final boolean q) {
        this.s = new double[256];
        this.t = new WorldGenCaves();
        this.u = new WorldGenStronghold();
        this.v = new WorldGenVillage();
        this.w = new WorldGenMineshaft();
        this.x = new WorldGenLargeFeature();
        this.y = new WorldGenCanyon();
        this.j = new int[32][32];
        this.p = p3;
        this.q = q;
        this.k = new Random(n);
        this.l = new NoiseGeneratorOctaves(this.k, 16);
        this.m = new NoiseGeneratorOctaves(this.k, 16);
        this.n = new NoiseGeneratorOctaves(this.k, 8);
        this.o = new NoiseGeneratorOctaves(this.k, 4);
        this.a = new NoiseGeneratorOctaves(this.k, 10);
        this.b = new NoiseGeneratorOctaves(this.k, 16);
        this.c = new NoiseGeneratorOctaves(this.k, 8);
    }
    
    public void a(final int n, final int n2, final byte[] array) {
        final int n3 = 4;
        final int n4 = 16;
        final int n5 = 63;
        final int n6 = n3 + 1;
        final int n7 = 17;
        final int n8 = n3 + 1;
        this.z = this.p.getWorldChunkManager().getBiomes(this.z, n * 4 - 2, n2 * 4 - 2, n6 + 5, n8 + 5);
        this.r = this.a(this.r, n * n3, 0, n2 * n3, n6, n7, n8);
        for (int i = 0; i < n3; ++i) {
            for (int j = 0; j < n3; ++j) {
                for (int k = 0; k < n4; ++k) {
                    final double n9 = 0.125;
                    double n10 = this.r[((i + 0) * n8 + (j + 0)) * n7 + (k + 0)];
                    double n11 = this.r[((i + 0) * n8 + (j + 1)) * n7 + (k + 0)];
                    double n12 = this.r[((i + 1) * n8 + (j + 0)) * n7 + (k + 0)];
                    double n13 = this.r[((i + 1) * n8 + (j + 1)) * n7 + (k + 0)];
                    final double n14 = (this.r[((i + 0) * n8 + (j + 0)) * n7 + (k + 1)] - n10) * n9;
                    final double n15 = (this.r[((i + 0) * n8 + (j + 1)) * n7 + (k + 1)] - n11) * n9;
                    final double n16 = (this.r[((i + 1) * n8 + (j + 0)) * n7 + (k + 1)] - n12) * n9;
                    final double n17 = (this.r[((i + 1) * n8 + (j + 1)) * n7 + (k + 1)] - n13) * n9;
                    for (int l = 0; l < 8; ++l) {
                        final double n18 = 0.25;
                        double n19 = n10;
                        double n20 = n11;
                        final double n21 = (n12 - n10) * n18;
                        final double n22 = (n13 - n11) * n18;
                        for (int n23 = 0; n23 < 4; ++n23) {
                            final int n24 = n23 + i * 4 << 11 | 0 + j * 4 << 7 | k * 8 + l;
                            final int n25 = 128;
                            int n26 = n24 - n25;
                            final double n27 = 0.25;
                            final double n28 = n19;
                            final double n29 = (n20 - n19) * n27;
                            double n30 = n28 - n29;
                            for (int n31 = 0; n31 < 4; ++n31) {
                                if ((n30 += n29) > 0.0) {
                                    array[n26 += n25] = (byte)Block.STONE.id;
                                }
                                else if (k * 8 + l < n5) {
                                    array[n26 += n25] = (byte)Block.STATIONARY_WATER.id;
                                }
                                else {
                                    array[n26 += n25] = 0;
                                }
                            }
                            n19 += n21;
                            n20 += n22;
                        }
                        n10 += n14;
                        n11 += n15;
                        n12 += n16;
                        n13 += n17;
                    }
                }
            }
        }
    }
    
    public void a(final int n, final int n2, final byte[] array, final BiomeBase[] array2) {
        final int n3 = 63;
        final double n4 = 0.03125;
        this.s = this.o.a(this.s, n * 16, n2 * 16, 0, 16, 16, 1, n4 * 2.0, n4 * 2.0, n4 * 2.0);
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                final BiomeBase biomeBase = array2[j + i * 16];
                final float k = biomeBase.j();
                final int n5 = (int)(this.s[i + j * 16] / 3.0 + 3.0 + this.k.nextDouble() * 0.25);
                int nextInt = -1;
                byte b = biomeBase.A;
                byte b2 = biomeBase.B;
                for (int l = 127; l >= 0; --l) {
                    final int n6 = (j * 16 + i) * 128 + l;
                    if (l <= 0 + this.k.nextInt(5)) {
                        array[n6] = (byte)Block.BEDROCK.id;
                    }
                    else {
                        final byte b3 = array[n6];
                        if (b3 == 0) {
                            nextInt = -1;
                        }
                        else if (b3 == Block.STONE.id) {
                            if (nextInt == -1) {
                                if (n5 <= 0) {
                                    b = 0;
                                    b2 = (byte)Block.STONE.id;
                                }
                                else if (l >= n3 - 4 && l <= n3 + 1) {
                                    b = biomeBase.A;
                                    b2 = biomeBase.B;
                                }
                                if (l < n3 && b == 0) {
                                    if (k < 0.15f) {
                                        b = (byte)Block.ICE.id;
                                    }
                                    else {
                                        b = (byte)Block.STATIONARY_WATER.id;
                                    }
                                }
                                nextInt = n5;
                                if (l >= n3 - 1) {
                                    array[n6] = b;
                                }
                                else {
                                    array[n6] = b2;
                                }
                            }
                            else if (nextInt > 0) {
                                --nextInt;
                                array[n6] = b2;
                                if (nextInt == 0 && b2 == Block.SAND.id) {
                                    nextInt = this.k.nextInt(4);
                                    b2 = (byte)Block.SANDSTONE.id;
                                }
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
        this.k.setSeed(i * 341873128712L + j * 132897987541L);
        final byte[] abyte = new byte[32768];
        this.a(i, j, abyte);
        this.a(i, j, abyte, this.z = this.p.getWorldChunkManager().getBiomeBlock(this.z, i * 16, j * 16, 16, 16));
        this.t.a(this, this.p, i, j, abyte);
        this.y.a(this, this.p, i, j, abyte);
        if (this.q) {
            this.w.a(this, this.p, i, j, abyte);
            this.v.a(this, this.p, i, j, abyte);
            this.u.a(this, this.p, i, j, abyte);
            this.x.a(this, this.p, i, j, abyte);
        }
        final Chunk chunk = new Chunk(this.p, abyte, i, j);
        final byte[] m = chunk.m();
        for (int k = 0; k < m.length; ++k) {
            m[k] = (byte)this.z[k].id;
        }
        chunk.initLighting();
        return chunk;
    }
    
    private double[] a(double[] array, int n, final int n2, int n3, final int n4, final int n5, final int n6) {
        if (array == null) {
            array = new double[n4 * n5 * n6];
        }
        if (this.i == null) {
            this.i = new float[25];
            for (int i = -2; i <= 2; ++i) {
                for (int j = -2; j <= 2; ++j) {
                    this.i[i + 2 + (j + 2) * 5] = 10.0f / MathHelper.c(i * i + j * j + 0.2f);
                }
            }
        }
        final double n7 = 684.412;
        final double n8 = 684.412;
        this.g = this.a.a(this.g, n, n3, n4, n6, 1.121, 1.121, 0.5);
        this.h = this.b.a(this.h, n, n3, n4, n6, 200.0, 200.0, 0.5);
        this.d = this.n.a(this.d, n, n2, n3, n4, n5, n6, n7 / 80.0, n8 / 160.0, n7 / 80.0);
        this.e = this.l.a(this.e, n, n2, n3, n4, n5, n6, n7, n8, n7);
        this.f = this.m.a(this.f, n, n2, n3, n4, n5, n6, n7, n8, n7);
        n3 = (n = 0);
        int n9 = 0;
        int n10 = 0;
        for (int k = 0; k < n4; ++k) {
            for (int l = 0; l < n6; ++l) {
                float n11 = 0.0f;
                float n12 = 0.0f;
                float n13 = 0.0f;
                final int n14 = 2;
                final BiomeBase biomeBase = this.z[k + 2 + (l + 2) * (n4 + 5)];
                for (int n15 = -n14; n15 <= n14; ++n15) {
                    for (int n16 = -n14; n16 <= n14; ++n16) {
                        final BiomeBase biomeBase2 = this.z[k + n15 + 2 + (l + n16 + 2) * (n4 + 5)];
                        float n17 = this.i[n15 + 2 + (n16 + 2) * 5] / (biomeBase2.D + 2.0f);
                        if (biomeBase2.D > biomeBase.D) {
                            n17 /= 2.0f;
                        }
                        n11 += biomeBase2.E * n17;
                        n12 += biomeBase2.D * n17;
                        n13 += n17;
                    }
                }
                final float n18 = n11 / n13;
                final float n19 = n12 / n13;
                final float n20 = n18 * 0.9f + 0.1f;
                final float n21 = (n19 * 4.0f - 1.0f) / 8.0f;
                double n22 = this.h[n10] / 8000.0;
                if (n22 < 0.0) {
                    n22 = -n22 * 0.3;
                }
                double n23 = n22 * 3.0 - 2.0;
                double n25;
                if (n23 < 0.0) {
                    double n24 = n23 / 2.0;
                    if (n24 < -1.0) {
                        n24 = -1.0;
                    }
                    n25 = n24 / 1.4 / 2.0;
                }
                else {
                    if (n23 > 1.0) {
                        n23 = 1.0;
                    }
                    n25 = n23 / 8.0;
                }
                ++n10;
                for (int n26 = 0; n26 < n5; ++n26) {
                    double n27 = (n26 - (n5 / 2.0 + (n21 + n25 * 0.2) * n5 / 16.0 * 4.0)) * 12.0 * 128.0 / 128.0 / n20;
                    if (n27 < 0.0) {
                        n27 *= 4.0;
                    }
                    final double n28 = this.e[n9] / 512.0;
                    final double n29 = this.f[n9] / 512.0;
                    final double n30 = (this.d[n9] / 10.0 + 1.0) / 2.0;
                    double n31;
                    if (n30 < 0.0) {
                        n31 = n28;
                    }
                    else if (n30 > 1.0) {
                        n31 = n29;
                    }
                    else {
                        n31 = n28 + (n29 - n28) * n30;
                    }
                    double n32 = n31 - n27;
                    if (n26 > n5 - 4) {
                        final double n33 = (n26 - (n5 - 4)) / 3.0f;
                        n32 = n32 * (1.0 - n33) + -10.0 * n33;
                    }
                    array[n9] = n32;
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
        int n3 = n * 16;
        int n4 = n2 * 16;
        final BiomeBase biome = this.p.getBiome(n3 + 16, n4 + 16);
        this.k.setSeed(this.p.getSeed());
        this.k.setSeed(n * (this.k.nextLong() / 2L * 2L + 1L) + n2 * (this.k.nextLong() / 2L * 2L + 1L) ^ this.p.getSeed());
        boolean a = false;
        if (this.q) {
            this.w.a(this.p, this.k, n, n2);
            a = this.v.a(this.p, this.k, n, n2);
            this.u.a(this.p, this.k, n, n2);
            this.x.a(this.p, this.k, n, n2);
        }
        if (!a && this.k.nextInt(4) == 0) {
            new WorldGenLakes(Block.STATIONARY_WATER.id).a(this.p, this.k, n3 + this.k.nextInt(16) + 8, this.k.nextInt(128), n4 + this.k.nextInt(16) + 8);
        }
        if (!a && this.k.nextInt(8) == 0) {
            final int n5 = n3 + this.k.nextInt(16) + 8;
            final int nextInt = this.k.nextInt(this.k.nextInt(120) + 8);
            final int n6 = n4 + this.k.nextInt(16) + 8;
            if (nextInt < 63 || this.k.nextInt(10) == 0) {
                new WorldGenLakes(Block.STATIONARY_LAVA.id).a(this.p, this.k, n5, nextInt, n6);
            }
        }
        for (int i = 0; i < 8; ++i) {
            if (new WorldGenDungeons().a(this.p, this.k, n3 + this.k.nextInt(16) + 8, this.k.nextInt(128), n4 + this.k.nextInt(16) + 8)) {}
        }
        biome.a(this.p, this.k, n3, n4);
        SpawnerCreature.a(this.p, biome, n3 + 8, n4 + 8, 16, 16, this.k);
        n3 += 8;
        n4 += 8;
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                final int h = this.p.h(n3 + j, n4 + k);
                if (this.p.x(j + n3, h - 1, k + n4)) {
                    this.p.setTypeIdAndData(j + n3, h - 1, k + n4, Block.ICE.id, 0, 2);
                }
                if (this.p.z(j + n3, h, k + n4)) {
                    this.p.setTypeIdAndData(j + n3, h, k + n4, Block.SNOW.id, 0, 2);
                }
            }
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
        return "RandomLevelSource";
    }
    
    public List getMobsFor(final EnumCreatureType enumCreatureType, final int i, final int n, final int j) {
        final BiomeBase biome = this.p.getBiome(i, j);
        if (biome == null) {
            return null;
        }
        if (biome == BiomeBase.SWAMPLAND && enumCreatureType == EnumCreatureType.MONSTER && this.x.a(i, n, j)) {
            return this.x.a();
        }
        return biome.getMobs(enumCreatureType);
    }
    
    public ChunkPosition findNearestMapFeature(final World world, final String s, final int n, final int n2, final int n3) {
        if ("Stronghold".equals(s) && this.u != null) {
            return this.u.getNearestGeneratedFeature(world, n, n2, n3);
        }
        return null;
    }
    
    public int getLoadedChunks() {
        return 0;
    }
    
    public void recreateStructures(final int n, final int n2) {
        if (this.q) {
            this.w.a(this, this.p, n, n2, null);
            this.v.a(this, this.p, n, n2, null);
            this.u.a(this, this.p, n, n2, null);
            this.x.a(this, this.p, n, n2, null);
        }
    }
}
