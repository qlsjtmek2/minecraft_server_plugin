// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class WorldProvider
{
    public World a;
    public WorldType type;
    public String c;
    public WorldChunkManager d;
    public boolean e;
    public boolean f;
    public float[] g;
    public int dimension;
    private float[] i;
    
    public WorldProvider() {
        this.e = false;
        this.f = false;
        this.g = new float[16];
        this.dimension = 0;
        this.i = new float[4];
    }
    
    public final void a(final World a) {
        this.a = a;
        this.type = a.getWorldData().getType();
        this.c = a.getWorldData().getGeneratorOptions();
        this.b();
        this.a();
    }
    
    protected void a() {
        final float n = 0.0f;
        for (int i = 0; i <= 15; ++i) {
            final float n2 = 1.0f - i / 15.0f;
            this.g[i] = (1.0f - n2) / (n2 * 3.0f + 1.0f) * (1.0f - n) + n;
        }
    }
    
    protected void b() {
        if (this.a.getWorldData().getType() == WorldType.FLAT) {
            this.d = new WorldChunkManagerHell(BiomeBase.biomes[WorldGenFlatInfo.a(this.a.getWorldData().getGeneratorOptions()).a()], 0.5f, 0.5f);
        }
        else {
            this.d = new WorldChunkManager(this.a);
        }
    }
    
    public IChunkProvider getChunkProvider() {
        if (this.type == WorldType.FLAT) {
            return new ChunkProviderFlat(this.a, this.a.getSeed(), this.a.getWorldData().shouldGenerateMapFeatures(), this.c);
        }
        return new ChunkProviderGenerate(this.a, this.a.getSeed(), this.a.getWorldData().shouldGenerateMapFeatures());
    }
    
    public boolean canSpawn(final int i, final int j) {
        return this.a.b(i, j) == Block.GRASS.id;
    }
    
    public float a(final long n, final float n2) {
        float n3 = ((int)(n % 24000L) + n2) / 24000.0f - 0.25f;
        if (n3 < 0.0f) {
            ++n3;
        }
        if (n3 > 1.0f) {
            --n3;
        }
        final float n4 = n3;
        return n4 + (1.0f - (float)((Math.cos(n3 * 3.141592653589793) + 1.0) / 2.0) - n4) / 3.0f;
    }
    
    public int a(final long n) {
        return (int)(n / 24000L) % 8;
    }
    
    public boolean d() {
        return true;
    }
    
    public boolean e() {
        return true;
    }
    
    public static WorldProvider byDimension(final int n) {
        if (n == -1) {
            return new WorldProviderHell();
        }
        if (n == 0) {
            return new WorldProviderNormal();
        }
        if (n == 1) {
            return new WorldProviderTheEnd();
        }
        return null;
    }
    
    public ChunkCoordinates h() {
        return null;
    }
    
    public int getSeaLevel() {
        if (this.type == WorldType.FLAT) {
            return 4;
        }
        return 64;
    }
    
    public abstract String getName();
}
