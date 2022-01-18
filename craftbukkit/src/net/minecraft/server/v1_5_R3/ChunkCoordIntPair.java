// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ChunkCoordIntPair
{
    public final int x;
    public final int z;
    
    public ChunkCoordIntPair(final int x, final int z) {
        this.x = x;
        this.z = z;
    }
    
    public static long a(final int n, final int n2) {
        return (n & 0xFFFFFFFFL) | (n2 & 0xFFFFFFFFL) << 32;
    }
    
    public int hashCode() {
        final long a = a(this.x, this.z);
        return (int)a ^ (int)(a >> 32);
    }
    
    public boolean equals(final Object o) {
        final ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair)o;
        return chunkCoordIntPair.x == this.x && chunkCoordIntPair.z == this.z;
    }
    
    public int a() {
        return (this.x << 4) + 8;
    }
    
    public int b() {
        return (this.z << 4) + 8;
    }
    
    public ChunkPosition a(final int n) {
        return new ChunkPosition(this.a(), n, this.b());
    }
    
    public String toString() {
        return "[" + this.x + ", " + this.z + "]";
    }
}
