// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ChunkPosition
{
    public final int x;
    public final int y;
    public final int z;
    
    public ChunkPosition(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public ChunkPosition(final Vec3D vec3D) {
        this(MathHelper.floor(vec3D.c), MathHelper.floor(vec3D.d), MathHelper.floor(vec3D.e));
    }
    
    public boolean equals(final Object o) {
        if (o instanceof ChunkPosition) {
            final ChunkPosition chunkPosition = (ChunkPosition)o;
            return chunkPosition.x == this.x && chunkPosition.y == this.y && chunkPosition.z == this.z;
        }
        return false;
    }
    
    public int hashCode() {
        return this.x * 8976890 + this.y * 981131 + this.z;
    }
}
