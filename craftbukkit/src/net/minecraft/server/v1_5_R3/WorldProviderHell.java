// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class WorldProviderHell extends WorldProvider
{
    public void b() {
        this.d = new WorldChunkManagerHell(BiomeBase.HELL, 1.0f, 0.0f);
        this.e = true;
        this.f = true;
        this.dimension = -1;
    }
    
    protected void a() {
        final float n = 0.1f;
        for (int i = 0; i <= 15; ++i) {
            final float n2 = 1.0f - i / 15.0f;
            this.g[i] = (1.0f - n2) / (n2 * 3.0f + 1.0f) * (1.0f - n) + n;
        }
    }
    
    public IChunkProvider getChunkProvider() {
        return new ChunkProviderHell(this.a, this.a.getSeed());
    }
    
    public boolean d() {
        return false;
    }
    
    public boolean canSpawn(final int n, final int n2) {
        return false;
    }
    
    public float a(final long n, final float n2) {
        return 0.5f;
    }
    
    public boolean e() {
        return false;
    }
    
    public String getName() {
        return "Nether";
    }
}
