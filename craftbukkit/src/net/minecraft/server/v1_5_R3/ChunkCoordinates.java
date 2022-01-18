// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ChunkCoordinates implements Comparable
{
    public int x;
    public int y;
    public int z;
    
    public ChunkCoordinates() {
    }
    
    public ChunkCoordinates(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public ChunkCoordinates(final ChunkCoordinates chunkCoordinates) {
        this.x = chunkCoordinates.x;
        this.y = chunkCoordinates.y;
        this.z = chunkCoordinates.z;
    }
    
    public boolean equals(final Object o) {
        if (!(o instanceof ChunkCoordinates)) {
            return false;
        }
        final ChunkCoordinates chunkCoordinates = (ChunkCoordinates)o;
        return this.x == chunkCoordinates.x && this.y == chunkCoordinates.y && this.z == chunkCoordinates.z;
    }
    
    public int hashCode() {
        return this.x + this.z << 8 + this.y << 16;
    }
    
    public int compareTo(final ChunkCoordinates chunkCoordinates) {
        if (this.y != chunkCoordinates.y) {
            return this.y - chunkCoordinates.y;
        }
        if (this.z == chunkCoordinates.z) {
            return this.x - chunkCoordinates.x;
        }
        return this.z - chunkCoordinates.z;
    }
    
    public void b(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public float e(final int n, final int n2, final int n3) {
        final int n4 = this.x - n;
        final int n5 = this.y - n2;
        final int n6 = this.z - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public float e(final ChunkCoordinates chunkCoordinates) {
        return this.e(chunkCoordinates.x, chunkCoordinates.y, chunkCoordinates.z);
    }
}
