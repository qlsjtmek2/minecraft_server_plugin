// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.generator.BlockPopulator;
import java.util.Random;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.craftbukkit.v1_5_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_5_R3.chunkio.ChunkIOExecutor;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.util.LongHash;
import org.bukkit.craftbukkit.v1_5_R3.util.LongObjectHashMap;
import org.bukkit.craftbukkit.v1_5_R3.util.LongHashSet;

public class ChunkProviderServer implements IChunkProvider
{
    public LongHashSet unloadQueue;
    public Chunk emptyChunk;
    public IChunkProvider chunkProvider;
    private IChunkLoader e;
    public boolean forceChunkLoad;
    public LongObjectHashMap<Chunk> chunks;
    public WorldServer world;
    
    public ChunkProviderServer(final WorldServer worldserver, final IChunkLoader ichunkloader, final IChunkProvider ichunkprovider) {
        this.unloadQueue = new LongHashSet();
        this.forceChunkLoad = false;
        this.chunks = new LongObjectHashMap<Chunk>();
        this.emptyChunk = new EmptyChunk(worldserver, 0, 0);
        this.world = worldserver;
        this.e = ichunkloader;
        this.chunkProvider = ichunkprovider;
    }
    
    public boolean isChunkLoaded(final int i, final int j) {
        return this.chunks.containsKey(LongHash.toLong(i, j));
    }
    
    public void queueUnload(final int i, final int j) {
        if (this.world.worldProvider.e()) {
            final ChunkCoordinates chunkcoordinates = this.world.getSpawn();
            final int k = i * 16 + 8 - chunkcoordinates.x;
            final int l = j * 16 + 8 - chunkcoordinates.z;
            final short short1 = 128;
            if (k < -short1 || k > short1 || l < -short1 || l > short1 || !this.world.keepSpawnInMemory) {
                this.unloadQueue.add(i, j);
                final Chunk c = this.chunks.get(LongHash.toLong(i, j));
                if (c != null) {
                    c.mustSave = true;
                }
            }
        }
        else {
            this.unloadQueue.add(i, j);
            final Chunk c2 = this.chunks.get(LongHash.toLong(i, j));
            if (c2 != null) {
                c2.mustSave = true;
            }
        }
    }
    
    public void a() {
        for (final Chunk chunk : this.chunks.values()) {
            this.queueUnload(chunk.x, chunk.z);
        }
    }
    
    public Chunk getChunkAt(final int i, final int j) {
        return this.getChunkAt(i, j, null);
    }
    
    public Chunk getChunkAt(final int i, final int j, final Runnable runnable) {
        this.unloadQueue.remove(i, j);
        Chunk chunk = this.chunks.get(LongHash.toLong(i, j));
        boolean newChunk = false;
        ChunkRegionLoader loader = null;
        if (this.e instanceof ChunkRegionLoader) {
            loader = (ChunkRegionLoader)this.e;
        }
        if (chunk == null && runnable != null && loader != null && loader.chunkExists(this.world, i, j)) {
            ChunkIOExecutor.queueChunkLoad(this.world, loader, this, i, j, runnable);
            return null;
        }
        if (chunk == null) {
            SpigotTimings.syncChunkLoadTimer.startTiming();
            chunk = this.loadChunk(i, j);
            if (chunk == null) {
                if (this.chunkProvider == null) {
                    chunk = this.emptyChunk;
                }
                else {
                    try {
                        chunk = this.chunkProvider.getOrCreateChunk(i, j);
                    }
                    catch (Throwable throwable) {
                        final CrashReport crashreport = CrashReport.a(throwable, "Exception generating new chunk");
                        final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");
                        crashreportsystemdetails.a("Location", String.format("%d,%d", i, j));
                        crashreportsystemdetails.a("Position hash", LongHash.toLong(i, j));
                        crashreportsystemdetails.a("Generator", this.chunkProvider.getName());
                        throw new ReportedException(crashreport);
                    }
                }
                newChunk = true;
            }
            this.chunks.put(LongHash.toLong(i, j), chunk);
            if (chunk != null) {
                chunk.addEntities();
            }
            final Server server = this.world.getServer();
            if (server != null) {
                server.getPluginManager().callEvent(new ChunkLoadEvent(chunk.bukkitChunk, newChunk));
            }
            chunk.a(this, this, i, j);
            SpigotTimings.syncChunkLoadTimer.stopTiming();
        }
        if (runnable != null) {
            runnable.run();
        }
        return chunk;
    }
    
    public Chunk getOrCreateChunk(final int i, final int j) {
        Chunk chunk = this.chunks.get(LongHash.toLong(i, j));
        chunk = ((chunk == null) ? ((!this.world.isLoading && !this.forceChunkLoad) ? this.emptyChunk : this.getChunkAt(i, j)) : chunk);
        if (chunk == this.emptyChunk) {
            return chunk;
        }
        if (i != chunk.x || j != chunk.z) {
            this.world.getLogger().severe("Chunk (" + chunk.x + ", " + chunk.z + ") stored at  (" + i + ", " + j + ") in world '" + this.world.getWorld().getName() + "'");
            this.world.getLogger().severe(chunk.getClass().getName());
            final Throwable ex = new Throwable();
            ex.fillInStackTrace();
            ex.printStackTrace();
        }
        return chunk;
    }
    
    public Chunk loadChunk(final int i, final int j) {
        if (this.e == null) {
            return null;
        }
        try {
            final Chunk chunk = this.e.a(this.world, i, j);
            if (chunk != null) {
                chunk.n = this.world.getTime();
                if (this.chunkProvider != null) {
                    this.chunkProvider.recreateStructures(i, j);
                }
            }
            return chunk;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public void saveChunkNOP(final Chunk chunk) {
        if (this.e != null) {
            try {
                this.e.b(this.world, chunk);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    
    public void saveChunk(final Chunk chunk) {
        if (this.e != null) {
            try {
                chunk.n = this.world.getTime();
                this.e.a(this.world, chunk);
            }
            catch (Exception ioexception) {
                ioexception.printStackTrace();
            }
        }
    }
    
    public void getChunkAt(final IChunkProvider ichunkprovider, final int i, final int j) {
        final Chunk chunk = this.getOrCreateChunk(i, j);
        if (!chunk.done) {
            chunk.done = true;
            if (this.chunkProvider != null) {
                this.chunkProvider.getChunkAt(ichunkprovider, i, j);
                BlockSand.instaFall = true;
                final Random random = new Random();
                random.setSeed(this.world.getSeed());
                final long xRand = random.nextLong() / 2L * 2L + 1L;
                final long zRand = random.nextLong() / 2L * 2L + 1L;
                random.setSeed(i * xRand + j * zRand ^ this.world.getSeed());
                final org.bukkit.World world = this.world.getWorld();
                if (world != null) {
                    for (final BlockPopulator populator : world.getPopulators()) {
                        populator.populate(world, random, chunk.bukkitChunk);
                    }
                }
                BlockSand.instaFall = false;
                this.world.getServer().getPluginManager().callEvent(new ChunkPopulateEvent(chunk.bukkitChunk));
                chunk.e();
            }
        }
    }
    
    public boolean saveChunks(final boolean flag, final IProgressUpdate iprogressupdate) {
        int i = 0;
        for (final Chunk chunk : this.chunks.values()) {
            if (flag) {
                this.saveChunkNOP(chunk);
            }
            if (chunk.a(flag)) {
                this.saveChunk(chunk);
                chunk.l = false;
                if (++i == 24 && !flag) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    public void b() {
        if (this.e != null) {
            this.e.b();
        }
    }
    
    public boolean unloadChunks() {
        if (!this.world.savingDisabled) {
            final Server server = this.world.getServer();
            for (int i = 0; i < 100 && !this.unloadQueue.isEmpty(); ++i) {
                final long chunkcoordinates = this.unloadQueue.popFirst();
                final Chunk chunk = this.chunks.get(chunkcoordinates);
                if (chunk != null) {
                    final ChunkUnloadEvent event = new ChunkUnloadEvent(chunk.bukkitChunk);
                    server.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        chunk.removeEntities();
                        this.saveChunk(chunk);
                        this.saveChunkNOP(chunk);
                        this.chunks.remove(chunkcoordinates);
                    }
                }
            }
            if (this.e != null) {
                this.e.a();
            }
        }
        return this.chunkProvider.unloadChunks();
    }
    
    public boolean canSave() {
        return !this.world.savingDisabled;
    }
    
    public String getName() {
        return "ServerChunkCache: " + this.chunks.values().size() + " Drop: " + this.unloadQueue.size();
    }
    
    public List getMobsFor(final EnumCreatureType enumcreaturetype, final int i, final int j, final int k) {
        return this.chunkProvider.getMobsFor(enumcreaturetype, i, j, k);
    }
    
    public ChunkPosition findNearestMapFeature(final World world, final String s, final int i, final int j, final int k) {
        return this.chunkProvider.findNearestMapFeature(world, s, i, j, k);
    }
    
    public int getLoadedChunks() {
        return this.chunks.values().size();
    }
    
    public void recreateStructures(final int i, final int j) {
    }
}
