// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.chunkio;

import org.bukkit.Server;
import net.minecraft.server.v1_5_R3.IChunkProvider;
import org.bukkit.event.Event;
import org.bukkit.event.world.ChunkLoadEvent;
import net.minecraft.server.v1_5_R3.ChunkRegionLoader;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_5_R3.util.LongHash;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.server.v1_5_R3.Chunk;
import org.bukkit.craftbukkit.v1_5_R3.util.AsynchronousExecutor;

class ChunkIOProvider implements AsynchronousExecutor.CallBackProvider<QueuedChunk, Chunk, Runnable, RuntimeException>
{
    private final AtomicInteger threadNumber;
    
    ChunkIOProvider() {
        this.threadNumber = new AtomicInteger(1);
    }
    
    public Chunk callStage1(final QueuedChunk queuedChunk) throws RuntimeException {
        final ChunkRegionLoader loader = queuedChunk.loader;
        final Object[] data = loader.loadChunk(queuedChunk.world, LongHash.msw(queuedChunk.coords), LongHash.lsw(queuedChunk.coords));
        if (data != null) {
            queuedChunk.compound = (NBTTagCompound)data[1];
            return (Chunk)data[0];
        }
        return null;
    }
    
    public void callStage2(final QueuedChunk queuedChunk, final Chunk chunk) throws RuntimeException {
        if (chunk == null) {
            queuedChunk.provider.getChunkAt(LongHash.msw(queuedChunk.coords), LongHash.lsw(queuedChunk.coords));
            return;
        }
        final int x = LongHash.msw(queuedChunk.coords);
        final int z = LongHash.lsw(queuedChunk.coords);
        if (queuedChunk.provider.chunks.containsKey(queuedChunk.coords)) {
            queuedChunk.provider.unloadQueue.remove(x, z);
            return;
        }
        queuedChunk.loader.loadEntities(chunk, queuedChunk.compound.getCompound("Level"), queuedChunk.world);
        chunk.n = queuedChunk.provider.world.getTime();
        queuedChunk.provider.chunks.put(queuedChunk.coords, chunk);
        chunk.addEntities();
        if (queuedChunk.provider.chunkProvider != null) {
            queuedChunk.provider.chunkProvider.recreateStructures(x, z);
        }
        final Server server = queuedChunk.provider.world.getServer();
        if (server != null) {
            server.getPluginManager().callEvent(new ChunkLoadEvent(chunk.bukkitChunk, false));
        }
        chunk.a(queuedChunk.provider, queuedChunk.provider, x, z);
    }
    
    public void callStage3(final QueuedChunk queuedChunk, final Chunk chunk, final Runnable runnable) throws RuntimeException {
        runnable.run();
    }
    
    public Thread newThread(final Runnable runnable) {
        final Thread thread = new Thread(runnable, "Chunk I/O Executor Thread-" + this.threadNumber.getAndIncrement());
        thread.setDaemon(true);
        return thread;
    }
}
