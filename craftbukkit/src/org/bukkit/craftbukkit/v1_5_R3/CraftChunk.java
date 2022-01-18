// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import java.util.Arrays;
import net.minecraft.server.v1_5_R3.WorldChunkManager;
import net.minecraft.server.v1_5_R3.ChunkSection;
import net.minecraft.server.v1_5_R3.BiomeBase;
import org.bukkit.ChunkSnapshot;
import net.minecraft.server.v1_5_R3.ChunkPosition;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlock;
import org.bukkit.block.Block;
import org.bukkit.World;
import net.minecraft.server.v1_5_R3.EmptyChunk;
import net.minecraft.server.v1_5_R3.WorldServer;
import java.lang.ref.WeakReference;
import org.bukkit.Chunk;

public class CraftChunk implements Chunk
{
    private WeakReference<net.minecraft.server.v1_5_R3.Chunk> weakChunk;
    private final WorldServer worldServer;
    private final int x;
    private final int z;
    private static final byte[] emptyData;
    private static final short[] emptyBlockIDs;
    private static final byte[] emptySkyLight;
    
    public CraftChunk(final net.minecraft.server.v1_5_R3.Chunk chunk) {
        if (!(chunk instanceof EmptyChunk)) {
            this.weakChunk = new WeakReference<net.minecraft.server.v1_5_R3.Chunk>(chunk);
        }
        this.worldServer = (WorldServer)this.getHandle().world;
        this.x = this.getHandle().x;
        this.z = this.getHandle().z;
    }
    
    public World getWorld() {
        return this.worldServer.getWorld();
    }
    
    public CraftWorld getCraftWorld() {
        return (CraftWorld)this.getWorld();
    }
    
    public net.minecraft.server.v1_5_R3.Chunk getHandle() {
        net.minecraft.server.v1_5_R3.Chunk c = this.weakChunk.get();
        if (c == null) {
            c = this.worldServer.getChunkAt(this.x, this.z);
            if (!(c instanceof EmptyChunk)) {
                this.weakChunk = new WeakReference<net.minecraft.server.v1_5_R3.Chunk>(c);
            }
        }
        return c;
    }
    
    void breakLink() {
        this.weakChunk.clear();
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public String toString() {
        return "CraftChunk{x=" + this.getX() + "z=" + this.getZ() + '}';
    }
    
    public Block getBlock(final int x, final int y, final int z) {
        return new CraftBlock(this, this.getX() << 4 | (x & 0xF), y & 0xFF, this.getZ() << 4 | (z & 0xF));
    }
    
    public Entity[] getEntities() {
        int count = 0;
        int index = 0;
        final net.minecraft.server.v1_5_R3.Chunk chunk = this.getHandle();
        for (int i = 0; i < 16; ++i) {
            count += chunk.entitySlices[i].size();
        }
        final Entity[] entities = new Entity[count];
        for (int j = 0; j < 16; ++j) {
            for (final Object obj : chunk.entitySlices[j].toArray()) {
                if (obj instanceof net.minecraft.server.v1_5_R3.Entity) {
                    entities[index++] = ((net.minecraft.server.v1_5_R3.Entity)obj).getBukkitEntity();
                }
            }
        }
        return entities;
    }
    
    public BlockState[] getTileEntities() {
        int index = 0;
        final net.minecraft.server.v1_5_R3.Chunk chunk = this.getHandle();
        final BlockState[] entities = new BlockState[chunk.tileEntities.size()];
        for (final Object obj : chunk.tileEntities.keySet().toArray()) {
            if (obj instanceof ChunkPosition) {
                final ChunkPosition position = (ChunkPosition)obj;
                entities[index++] = this.worldServer.getWorld().getBlockAt(position.x + (chunk.x << 4), position.y, position.z + (chunk.z << 4)).getState();
            }
        }
        return entities;
    }
    
    public boolean isLoaded() {
        return this.getWorld().isChunkLoaded(this);
    }
    
    public boolean load() {
        return this.getWorld().loadChunk(this.getX(), this.getZ(), true);
    }
    
    public boolean load(final boolean generate) {
        return this.getWorld().loadChunk(this.getX(), this.getZ(), generate);
    }
    
    public boolean unload() {
        return this.getWorld().unloadChunk(this.getX(), this.getZ());
    }
    
    public boolean unload(final boolean save) {
        return this.getWorld().unloadChunk(this.getX(), this.getZ(), save);
    }
    
    public boolean unload(final boolean save, final boolean safe) {
        return this.getWorld().unloadChunk(this.getX(), this.getZ(), save, safe);
    }
    
    public ChunkSnapshot getChunkSnapshot() {
        return this.getChunkSnapshot(true, false, false);
    }
    
    public ChunkSnapshot getChunkSnapshot(final boolean includeMaxBlockY, final boolean includeBiome, final boolean includeBiomeTempRain) {
        final net.minecraft.server.v1_5_R3.Chunk chunk = this.getHandle();
        final ChunkSection[] cs = chunk.i();
        final short[][] sectionBlockIDs = new short[cs.length][];
        final byte[][] sectionBlockData = new byte[cs.length][];
        final byte[][] sectionSkyLights = new byte[cs.length][];
        final byte[][] sectionEmitLights = new byte[cs.length][];
        final boolean[] sectionEmpty = new boolean[cs.length];
        for (int i = 0; i < cs.length; ++i) {
            if (cs[i] == null) {
                sectionBlockIDs[i] = CraftChunk.emptyBlockIDs;
                sectionBlockData[i] = CraftChunk.emptyData;
                sectionSkyLights[i] = CraftChunk.emptySkyLight;
                sectionEmitLights[i] = CraftChunk.emptyData;
                sectionEmpty[i] = true;
            }
            else {
                final short[] blockids = new short[4096];
                final byte[] baseids = cs[i].getIdArray();
                for (int j = 0; j < 4096; ++j) {
                    blockids[j] = (short)(baseids[j] & 0xFF);
                }
                if (cs[i].getExtendedIdArray() != null) {
                    if (cs[i].getExtendedIdArray().isTrivialArray()) {
                        int tval = cs[i].getExtendedIdArray().getTrivialArrayValue();
                        if (tval != 0) {
                            tval <<= 8;
                            for (int k = 0; k < 4096; ++k) {
                                final short[] array = blockids;
                                final int n = k;
                                array[n] |= (short)tval;
                            }
                        }
                    }
                    else {
                        final byte[] extids = cs[i].getExtendedIdArray().getValueArray();
                        for (int k = 0; k < 2048; ++k) {
                            final short b = (short)(extids[k] & 0xFF);
                            if (b != 0) {
                                final short[] array2 = blockids;
                                final int n2 = k << 1;
                                array2[n2] |= (short)((b & 0xF) << 8);
                                final short[] array3 = blockids;
                                final int n3 = (k << 1) + 1;
                                array3[n3] |= (short)((b & 0xF0) << 4);
                            }
                        }
                    }
                }
                sectionBlockIDs[i] = blockids;
                if (cs[i].getDataArray().isTrivialArray() && cs[i].getDataArray().getTrivialArrayValue() == 0) {
                    sectionBlockData[i] = CraftChunk.emptyData;
                }
                else {
                    sectionBlockData[i] = new byte[2048];
                    cs[i].getDataArray().copyToByteArray(sectionBlockData[i], 0);
                }
                if (cs[i].getSkyLightArray() == null) {
                    sectionSkyLights[i] = CraftChunk.emptyData;
                }
                else if (cs[i].getSkyLightArray().isTrivialArray()) {
                    if (cs[i].getSkyLightArray().getTrivialArrayValue() == 0) {
                        sectionSkyLights[i] = CraftChunk.emptyData;
                    }
                    else if (cs[i].getSkyLightArray().getTrivialArrayValue() == 15) {
                        sectionSkyLights[i] = CraftChunk.emptySkyLight;
                    }
                    else {
                        sectionSkyLights[i] = new byte[2048];
                        cs[i].getSkyLightArray().copyToByteArray(sectionSkyLights[i], 0);
                    }
                }
                else {
                    sectionSkyLights[i] = new byte[2048];
                    cs[i].getSkyLightArray().copyToByteArray(sectionSkyLights[i], 0);
                }
                if (cs[i].getEmittedLightArray().isTrivialArray() && cs[i].getEmittedLightArray().getTrivialArrayValue() == 0) {
                    sectionEmitLights[i] = CraftChunk.emptyData;
                }
                else {
                    sectionEmitLights[i] = new byte[2048];
                    cs[i].getEmittedLightArray().copyToByteArray(sectionEmitLights[i], 0);
                }
            }
        }
        int[] hmap = null;
        if (includeMaxBlockY) {
            hmap = new int[256];
            System.arraycopy(chunk.heightMap, 0, hmap, 0, 256);
        }
        BiomeBase[] biome = null;
        double[] biomeTemp = null;
        double[] biomeRain = null;
        if (includeBiome || includeBiomeTempRain) {
            final WorldChunkManager wcm = chunk.world.getWorldChunkManager();
            if (includeBiome) {
                biome = new BiomeBase[256];
                for (int l = 0; l < 256; ++l) {
                    biome[l] = chunk.a(l & 0xF, l >> 4, wcm);
                }
            }
            if (includeBiomeTempRain) {
                biomeTemp = new double[256];
                biomeRain = new double[256];
                float[] dat = wcm.getTemperatures(null, this.getX() << 4, this.getZ() << 4, 16, 16);
                for (int m = 0; m < 256; ++m) {
                    biomeTemp[m] = dat[m];
                }
                dat = wcm.getWetness(null, this.getX() << 4, this.getZ() << 4, 16, 16);
                for (int m = 0; m < 256; ++m) {
                    biomeRain[m] = dat[m];
                }
            }
        }
        final World world = this.getWorld();
        return new CraftChunkSnapshot(this.getX(), this.getZ(), world.getName(), world.getFullTime(), sectionBlockIDs, sectionBlockData, sectionSkyLights, sectionEmitLights, sectionEmpty, hmap, biome, biomeTemp, biomeRain);
    }
    
    public static ChunkSnapshot getEmptyChunkSnapshot(final int x, final int z, final CraftWorld world, final boolean includeBiome, final boolean includeBiomeTempRain) {
        BiomeBase[] biome = null;
        double[] biomeTemp = null;
        double[] biomeRain = null;
        if (includeBiome || includeBiomeTempRain) {
            final WorldChunkManager wcm = world.getHandle().getWorldChunkManager();
            if (includeBiome) {
                biome = new BiomeBase[256];
                for (int i = 0; i < 256; ++i) {
                    biome[i] = world.getHandle().getBiome((x << 4) + (i & 0xF), (z << 4) + (i >> 4));
                }
            }
            if (includeBiomeTempRain) {
                biomeTemp = new double[256];
                biomeRain = new double[256];
                float[] dat = wcm.getTemperatures(null, x << 4, z << 4, 16, 16);
                for (int j = 0; j < 256; ++j) {
                    biomeTemp[j] = dat[j];
                }
                dat = wcm.getWetness(null, x << 4, z << 4, 16, 16);
                for (int j = 0; j < 256; ++j) {
                    biomeRain[j] = dat[j];
                }
            }
        }
        final int hSection = world.getMaxHeight() >> 4;
        final short[][] blockIDs = new short[hSection][];
        final byte[][] skyLight = new byte[hSection][];
        final byte[][] emitLight = new byte[hSection][];
        final byte[][] blockData = new byte[hSection][];
        final boolean[] empty = new boolean[hSection];
        for (int k = 0; k < hSection; ++k) {
            blockIDs[k] = CraftChunk.emptyBlockIDs;
            skyLight[k] = CraftChunk.emptySkyLight;
            emitLight[k] = CraftChunk.emptyData;
            blockData[k] = CraftChunk.emptyData;
            empty[k] = true;
        }
        return new CraftChunkSnapshot(x, z, world.getName(), world.getFullTime(), blockIDs, blockData, skyLight, emitLight, empty, new int[256], biome, biomeTemp, biomeRain);
    }
    
    static {
        emptyData = new byte[2048];
        emptyBlockIDs = new short[4096];
        Arrays.fill(emptySkyLight = new byte[2048], (byte)(-1));
    }
}
