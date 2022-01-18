// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Iterator;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataOutput;
import java.io.DataInputStream;
import java.io.DataInput;
import java.io.File;
import java.util.LinkedHashMap;

public class ChunkRegionLoader implements IAsyncChunkSaver, IChunkLoader
{
    private LinkedHashMap<ChunkCoordIntPair, PendingChunkToSave> pendingSaves;
    private Object c;
    private final File d;
    
    public ChunkRegionLoader(final File file1) {
        this.pendingSaves = new LinkedHashMap<ChunkCoordIntPair, PendingChunkToSave>();
        this.c = new Object();
        this.d = file1;
    }
    
    public boolean chunkExists(final World world, final int i, final int j) {
        final ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
        synchronized (this.c) {
            if (this.pendingSaves.containsKey(chunkcoordintpair)) {
                return true;
            }
        }
        return RegionFileCache.a(this.d, i, j).chunkExists(i & 0x1F, j & 0x1F);
    }
    
    public Chunk a(final World world, final int i, final int j) {
        final Object[] data = this.loadChunk(world, i, j);
        if (data != null) {
            final Chunk chunk = (Chunk)data[0];
            final NBTTagCompound nbttagcompound = (NBTTagCompound)data[1];
            this.loadEntities(chunk, nbttagcompound.getCompound("Level"), world);
            return chunk;
        }
        return null;
    }
    
    public Object[] loadChunk(final World world, final int i, final int j) {
        NBTTagCompound nbttagcompound = null;
        final ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
        final Object object = this.c;
        synchronized (this.c) {
            final PendingChunkToSave pendingchunktosave = this.pendingSaves.get(chunkcoordintpair);
            if (pendingchunktosave != null) {
                nbttagcompound = pendingchunktosave.b;
            }
        }
        if (nbttagcompound == null) {
            final DataInputStream datainputstream = RegionFileCache.c(this.d, i, j);
            if (datainputstream == null) {
                return null;
            }
            nbttagcompound = NBTCompressedStreamTools.a((DataInput)datainputstream);
        }
        return this.a(world, i, j, nbttagcompound);
    }
    
    protected Object[] a(final World world, final int i, final int j, final NBTTagCompound nbttagcompound) {
        if (!nbttagcompound.hasKey("Level")) {
            world.getLogger().severe("Chunk file at " + i + "," + j + " is missing level data, skipping");
            return null;
        }
        if (!nbttagcompound.getCompound("Level").hasKey("Sections")) {
            world.getLogger().severe("Chunk file at " + i + "," + j + " is missing block data, skipping");
            return null;
        }
        Chunk chunk = this.a(world, nbttagcompound.getCompound("Level"));
        if (!chunk.a(i, j)) {
            world.getLogger().severe("Chunk file at " + i + "," + j + " is in the wrong location; relocating. (Expected " + i + ", " + j + ", got " + chunk.x + ", " + chunk.z + ")");
            nbttagcompound.getCompound("Level").setInt("xPos", i);
            nbttagcompound.getCompound("Level").setInt("zPos", j);
            final NBTTagList tileEntities = nbttagcompound.getCompound("Level").getList("TileEntities");
            if (tileEntities != null) {
                for (int te = 0; te < tileEntities.size(); ++te) {
                    final NBTTagCompound tileEntity = (NBTTagCompound)tileEntities.get(te);
                    final int x = tileEntity.getInt("x") - chunk.x * 16;
                    final int z = tileEntity.getInt("z") - chunk.z * 16;
                    tileEntity.setInt("x", i * 16 + x);
                    tileEntity.setInt("z", j * 16 + z);
                }
            }
            chunk = this.a(world, nbttagcompound.getCompound("Level"));
        }
        final Object[] data = { chunk, nbttagcompound };
        return data;
    }
    
    public void a(final World world, final Chunk chunk) {
        try {
            world.F();
        }
        catch (ExceptionWorldConflict ex) {
            ex.printStackTrace();
        }
        try {
            final NBTTagCompound nbttagcompound = new NBTTagCompound();
            final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            nbttagcompound.set("Level", nbttagcompound2);
            this.a(chunk, world, nbttagcompound2);
            this.a(chunk.l(), nbttagcompound);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    protected void a(final ChunkCoordIntPair chunkcoordintpair, final NBTTagCompound nbttagcompound) {
        final Object object = this.c;
        synchronized (this.c) {
            if (this.pendingSaves.put(chunkcoordintpair, new PendingChunkToSave(chunkcoordintpair, nbttagcompound)) != null) {
                return;
            }
            FileIOThread.a.a(this);
        }
    }
    
    public boolean c() {
        PendingChunkToSave pendingchunktosave = null;
        final Object object = this.c;
        synchronized (this.c) {
            if (this.pendingSaves.isEmpty()) {
                return false;
            }
            pendingchunktosave = this.pendingSaves.values().iterator().next();
            this.pendingSaves.remove(pendingchunktosave.a);
        }
        if (pendingchunktosave != null) {
            try {
                this.a(pendingchunktosave);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return true;
    }
    
    public void a(final PendingChunkToSave pendingchunktosave) throws IOException {
        final DataOutputStream dataoutputstream = RegionFileCache.d(this.d, pendingchunktosave.a.x, pendingchunktosave.a.z);
        NBTCompressedStreamTools.a(pendingchunktosave.b, (DataOutput)dataoutputstream);
        dataoutputstream.close();
    }
    
    public void b(final World world, final Chunk chunk) {
    }
    
    public void a() {
    }
    
    public void b() {
        while (this.c()) {}
    }
    
    private void a(final Chunk chunk, final World world, final NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("xPos", chunk.x);
        nbttagcompound.setInt("zPos", chunk.z);
        nbttagcompound.setLong("LastUpdate", world.getTime());
        nbttagcompound.setIntArray("HeightMap", chunk.heightMap);
        nbttagcompound.setBoolean("TerrainPopulated", chunk.done);
        final ChunkSection[] achunksection = chunk.i();
        final NBTTagList nbttaglist = new NBTTagList("Sections");
        final boolean flag = !world.worldProvider.f;
        final ChunkSection[] achunksection2 = achunksection;
        for (int i = achunksection.length, j = 0; j < i; ++j) {
            final ChunkSection chunksection = achunksection2[j];
            if (chunksection != null) {
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Y", (byte)(chunksection.getYPosition() >> 4 & 0xFF));
                nbttagcompound2.setByteArray("Blocks", chunksection.getIdArray());
                if (chunksection.getExtendedIdArray() != null) {
                    nbttagcompound2.setByteArray("Add", chunksection.getExtendedIdArray().getValueArray());
                }
                nbttagcompound2.setByteArray("Data", chunksection.getDataArray().getValueArray());
                nbttagcompound2.setByteArray("BlockLight", chunksection.getEmittedLightArray().getValueArray());
                if (flag) {
                    nbttagcompound2.setByteArray("SkyLight", chunksection.getSkyLightArray().getValueArray());
                }
                else {
                    nbttagcompound2.setByteArray("SkyLight", new byte[chunksection.getEmittedLightArray().getValueArray().length]);
                }
                nbttaglist.add(nbttagcompound2);
            }
        }
        nbttagcompound.set("Sections", nbttaglist);
        nbttagcompound.setByteArray("Biomes", chunk.m());
        chunk.m = false;
        final NBTTagList nbttaglist2 = new NBTTagList();
        for (int i = 0; i < chunk.entitySlices.length; ++i) {
            for (final Entity entity : chunk.entitySlices[i]) {
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                if (entity.d(nbttagcompound2)) {
                    chunk.m = true;
                    nbttaglist2.add(nbttagcompound2);
                }
            }
        }
        nbttagcompound.set("Entities", nbttaglist2);
        final NBTTagList nbttaglist3 = new NBTTagList();
        for (final TileEntity tileentity : chunk.tileEntities.values()) {
            final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            tileentity.b(nbttagcompound2);
            nbttaglist3.add(nbttagcompound2);
        }
        nbttagcompound.set("TileEntities", nbttaglist3);
        final List list = world.a(chunk, false);
        if (list != null) {
            final long k = world.getTime();
            final NBTTagList nbttaglist4 = new NBTTagList();
            for (final NextTickListEntry nextticklistentry : list) {
                final NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                nbttagcompound3.setInt("i", nextticklistentry.d);
                nbttagcompound3.setInt("x", nextticklistentry.a);
                nbttagcompound3.setInt("y", nextticklistentry.b);
                nbttagcompound3.setInt("z", nextticklistentry.c);
                nbttagcompound3.setInt("t", (int)(nextticklistentry.e - k));
                nbttagcompound3.setInt("p", nextticklistentry.f);
                nbttaglist4.add(nbttagcompound3);
            }
            nbttagcompound.set("TileTicks", nbttaglist4);
        }
    }
    
    private Chunk a(final World world, final NBTTagCompound nbttagcompound) {
        final int i = nbttagcompound.getInt("xPos");
        final int j = nbttagcompound.getInt("zPos");
        final Chunk chunk = new Chunk(world, i, j);
        chunk.heightMap = nbttagcompound.getIntArray("HeightMap");
        chunk.done = nbttagcompound.getBoolean("TerrainPopulated");
        final NBTTagList nbttaglist = nbttagcompound.getList("Sections");
        final byte b0 = 16;
        final ChunkSection[] achunksection = new ChunkSection[b0];
        final boolean flag = !world.worldProvider.f;
        for (int k = 0; k < nbttaglist.size(); ++k) {
            final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.get(k);
            final byte b2 = nbttagcompound2.getByte("Y");
            final ChunkSection chunksection = new ChunkSection(b2 << 4, flag);
            chunksection.setIdArray(nbttagcompound2.getByteArray("Blocks"));
            if (nbttagcompound2.hasKey("Add")) {
                chunksection.setExtendedIdArray(new NibbleArray(nbttagcompound2.getByteArray("Add"), 4));
            }
            chunksection.setDataArray(new NibbleArray(nbttagcompound2.getByteArray("Data"), 4));
            chunksection.setEmittedLightArray(new NibbleArray(nbttagcompound2.getByteArray("BlockLight"), 4));
            if (flag) {
                chunksection.setSkyLightArray(new NibbleArray(nbttagcompound2.getByteArray("SkyLight"), 4));
            }
            chunksection.recalcBlockCounts();
            achunksection[b2] = chunksection;
        }
        chunk.a(achunksection);
        if (nbttagcompound.hasKey("Biomes")) {
            chunk.a(nbttagcompound.getByteArray("Biomes"));
        }
        return chunk;
    }
    
    public void loadEntities(final Chunk chunk, final NBTTagCompound nbttagcompound, final World world) {
        final NBTTagList nbttaglist1 = nbttagcompound.getList("Entities");
        if (nbttaglist1 != null) {
            for (int l = 0; l < nbttaglist1.size(); ++l) {
                final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist1.get(l);
                final Entity entity = EntityTypes.a(nbttagcompound2, world);
                chunk.m = true;
                if (entity != null) {
                    chunk.a(entity);
                    Entity entity2 = entity;
                    for (NBTTagCompound nbttagcompound3 = nbttagcompound2; nbttagcompound3.hasKey("Riding"); nbttagcompound3 = nbttagcompound3.getCompound("Riding")) {
                        final Entity entity3 = EntityTypes.a(nbttagcompound3.getCompound("Riding"), world);
                        if (entity3 != null) {
                            chunk.a(entity3);
                            entity2.mount(entity3);
                        }
                        entity2 = entity3;
                    }
                }
            }
        }
        final NBTTagList nbttaglist2 = nbttagcompound.getList("TileEntities");
        if (nbttaglist2 != null) {
            for (int i1 = 0; i1 < nbttaglist2.size(); ++i1) {
                final NBTTagCompound nbttagcompound4 = (NBTTagCompound)nbttaglist2.get(i1);
                final TileEntity tileentity = TileEntity.c(nbttagcompound4);
                if (tileentity != null) {
                    chunk.a(tileentity);
                }
            }
        }
        if (nbttagcompound.hasKey("TileTicks")) {
            final NBTTagList nbttaglist3 = nbttagcompound.getList("TileTicks");
            if (nbttaglist3 != null) {
                for (int j1 = 0; j1 < nbttaglist3.size(); ++j1) {
                    final NBTTagCompound nbttagcompound5 = (NBTTagCompound)nbttaglist3.get(j1);
                    world.b(nbttagcompound5.getInt("x"), nbttagcompound5.getInt("y"), nbttagcompound5.getInt("z"), nbttagcompound5.getInt("i"), nbttagcompound5.getInt("t"), nbttagcompound5.getInt("p"));
                }
            }
        }
    }
}
