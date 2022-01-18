// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class PendingChunkToSave
{
    public final ChunkCoordIntPair a;
    public final NBTTagCompound b;
    
    public PendingChunkToSave(final ChunkCoordIntPair a, final NBTTagCompound b) {
        this.a = a;
        this.b = b;
    }
}
