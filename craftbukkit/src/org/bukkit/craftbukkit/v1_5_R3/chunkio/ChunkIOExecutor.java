// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.chunkio;

import net.minecraft.server.v1_5_R3.ChunkProviderServer;
import net.minecraft.server.v1_5_R3.ChunkRegionLoader;
import org.bukkit.craftbukkit.v1_5_R3.util.LongHash;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.Chunk;
import org.bukkit.craftbukkit.v1_5_R3.util.AsynchronousExecutor;

public class ChunkIOExecutor
{
    static final int BASE_THREADS = 1;
    static final int PLAYERS_PER_THREAD = 50;
    private static final AsynchronousExecutor<QueuedChunk, Chunk, Runnable, RuntimeException> instance;
    
    public static void waitForChunkLoad(final World world, final int x, final int z) {
        ChunkIOExecutor.instance.get(new QueuedChunk(LongHash.toLong(x, z), null, world, null));
    }
    
    public static void queueChunkLoad(final World world, final ChunkRegionLoader loader, final ChunkProviderServer provider, final int x, final int z, final Runnable runnable) {
        ChunkIOExecutor.instance.add(new QueuedChunk(LongHash.toLong(x, z), loader, world, provider), runnable);
    }
    
    public static void adjustPoolSize(final int players) {
        final int size = Math.max(1, (int)Math.ceil(players / 50));
        ChunkIOExecutor.instance.setActiveThreads(size);
    }
    
    public static void tick() {
        ChunkIOExecutor.instance.finishActive();
    }
    
    static {
        instance = new AsynchronousExecutor<QueuedChunk, Chunk, Runnable, RuntimeException>(new ChunkIOProvider(), 1);
    }
}
