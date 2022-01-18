// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class WorldProviderTheEnd extends WorldProvider
{
    public void b() {
        this.d = new WorldChunkManagerHell(BiomeBase.SKY, 0.5f, 0.0f);
        this.dimension = 1;
        this.f = true;
    }
    
    public IChunkProvider getChunkProvider() {
        return new ChunkProviderTheEnd(this.a, this.a.getSeed());
    }
    
    public float a(final long n, final float n2) {
        return 0.0f;
    }
    
    public boolean e() {
        return false;
    }
    
    public boolean d() {
        return false;
    }
    
    public boolean canSpawn(final int i, final int j) {
        final int b = this.a.b(i, j);
        return b != 0 && Block.byId[b].material.isSolid();
    }
    
    public ChunkCoordinates h() {
        return new ChunkCoordinates(100, 50, 0);
    }
    
    public int getSeaLevel() {
        return 50;
    }
    
    public String getName() {
        return "The End";
    }
}
