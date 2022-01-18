// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.chunkio;

import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.ChunkProviderServer;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.ChunkRegionLoader;

class QueuedChunk
{
    final long coords;
    final ChunkRegionLoader loader;
    final World world;
    final ChunkProviderServer provider;
    NBTTagCompound compound;
    
    public QueuedChunk(final long coords, final ChunkRegionLoader loader, final World world, final ChunkProviderServer provider) {
        this.coords = coords;
        this.loader = loader;
        this.world = world;
        this.provider = provider;
    }
    
    public int hashCode() {
        return (int)this.coords ^ this.world.hashCode();
    }
    
    public boolean equals(final Object object) {
        if (object instanceof QueuedChunk) {
            final QueuedChunk other = (QueuedChunk)object;
            return this.coords == other.coords && this.world == other.world;
        }
        return false;
    }
}
