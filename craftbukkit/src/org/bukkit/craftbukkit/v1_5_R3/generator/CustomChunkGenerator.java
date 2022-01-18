// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.generator;

import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlock;
import org.bukkit.block.Biome;
import net.minecraft.server.v1_5_R3.ChunkPosition;
import net.minecraft.server.v1_5_R3.EnumCreatureType;
import org.bukkit.generator.BlockPopulator;
import java.util.List;
import net.minecraft.server.v1_5_R3.IProgressUpdate;
import net.minecraft.server.v1_5_R3.IChunkProvider;
import net.minecraft.server.v1_5_R3.ChunkSection;
import net.minecraft.server.v1_5_R3.BiomeBase;
import net.minecraft.server.v1_5_R3.Chunk;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.WorldGenStronghold;
import java.util.Random;
import net.minecraft.server.v1_5_R3.WorldServer;
import org.bukkit.generator.ChunkGenerator;

public class CustomChunkGenerator extends InternalChunkGenerator
{
    private final ChunkGenerator generator;
    private final WorldServer world;
    private final Random random;
    private final WorldGenStronghold strongholdGen;
    
    public CustomChunkGenerator(final World world, final long seed, final ChunkGenerator generator) {
        this.strongholdGen = new WorldGenStronghold();
        this.world = (WorldServer)world;
        this.generator = generator;
        this.random = new Random(seed);
    }
    
    public boolean isChunkLoaded(final int x, final int z) {
        return true;
    }
    
    public Chunk getOrCreateChunk(final int x, final int z) {
        this.random.setSeed(x * 341873128712L + z * 132897987541L);
        final CustomBiomeGrid biomegrid = new CustomBiomeGrid();
        biomegrid.biome = new BiomeBase[256];
        this.world.getWorldChunkManager().getBiomeBlock(biomegrid.biome, x << 4, z << 4, 16, 16);
        final short[][] xbtypes = this.generator.generateExtBlockSections(this.world.getWorld(), this.random, x, z, biomegrid);
        Chunk chunk;
        if (xbtypes != null) {
            chunk = new Chunk(this.world, x, z);
            final ChunkSection[] csect = chunk.i();
            for (int scnt = Math.min(csect.length, xbtypes.length), sec = 0; sec < scnt; ++sec) {
                if (xbtypes[sec] != null) {
                    final byte[] secBlkID = new byte[4096];
                    byte[] secExtBlkID = null;
                    final short[] bdata = xbtypes[sec];
                    for (int i = 0, j = 0; i < bdata.length; i += 2, ++j) {
                        final short b1 = bdata[i];
                        final short b2 = bdata[i + 1];
                        final byte extb = (byte)(b1 >> 8 | (b2 >> 4 & 0xF0));
                        secBlkID[i] = (byte)b1;
                        secBlkID[i + 1] = (byte)b2;
                        if (extb != 0) {
                            if (secExtBlkID == null) {
                                secExtBlkID = new byte[2048];
                            }
                            secExtBlkID[j] = extb;
                        }
                    }
                    csect[sec] = new ChunkSection(sec << 4, true, secBlkID, secExtBlkID);
                }
            }
        }
        else {
            final byte[][] btypes = this.generator.generateBlockSections(this.world.getWorld(), this.random, x, z, biomegrid);
            if (btypes != null) {
                chunk = new Chunk(this.world, x, z);
                final ChunkSection[] csect2 = chunk.i();
                for (int scnt2 = Math.min(csect2.length, btypes.length), sec2 = 0; sec2 < scnt2; ++sec2) {
                    if (btypes[sec2] != null) {
                        csect2[sec2] = new ChunkSection(sec2 << 4, true, btypes[sec2], null);
                    }
                }
            }
            else {
                final byte[] types = this.generator.generate(this.world.getWorld(), this.random, x, z);
                final int ydim = types.length / 256;
                int scnt3 = ydim / 16;
                chunk = new Chunk(this.world, x, z);
                final ChunkSection[] csect3 = chunk.i();
                scnt3 = Math.min(scnt3, csect3.length);
                for (int sec3 = 0; sec3 < scnt3; ++sec3) {
                    ChunkSection cs = null;
                    byte[] csbytes = null;
                    for (int cy = 0; cy < 16; ++cy) {
                        final int cyoff = cy | sec3 << 4;
                        for (int cx = 0; cx < 16; ++cx) {
                            final int cxyoff = cx * ydim * 16 + cyoff;
                            for (int cz = 0; cz < 16; ++cz) {
                                final byte blk = types[cxyoff + cz * ydim];
                                if (blk != 0) {
                                    if (cs == null) {
                                        final ChunkSection[] array = csect3;
                                        final int n = sec3;
                                        final ChunkSection chunkSection = new ChunkSection(sec3 << 4, true);
                                        array[n] = chunkSection;
                                        cs = chunkSection;
                                        csbytes = cs.getIdArray();
                                    }
                                    csbytes[cy << 8 | cz << 4 | cx] = blk;
                                }
                            }
                        }
                    }
                    if (cs != null) {
                        cs.recalcBlockCounts();
                    }
                }
            }
        }
        final byte[] biomeIndex = chunk.m();
        for (int k = 0; k < biomeIndex.length; ++k) {
            biomeIndex[k] = (byte)(biomegrid.biome[k].id & 0xFF);
        }
        chunk.initLighting();
        return chunk;
    }
    
    public void getChunkAt(final IChunkProvider icp, final int i, final int i1) {
    }
    
    public boolean saveChunks(final boolean bln, final IProgressUpdate ipu) {
        return true;
    }
    
    public boolean unloadChunks() {
        return false;
    }
    
    public boolean canSave() {
        return true;
    }
    
    public byte[] generate(final org.bukkit.World world, final Random random, final int x, final int z) {
        return this.generator.generate(world, random, x, z);
    }
    
    public byte[][] generateBlockSections(final org.bukkit.World world, final Random random, final int x, final int z, final BiomeGrid biomes) {
        return this.generator.generateBlockSections(world, random, x, z, biomes);
    }
    
    public short[][] generateExtBlockSections(final org.bukkit.World world, final Random random, final int x, final int z, final BiomeGrid biomes) {
        return this.generator.generateExtBlockSections(world, random, x, z, biomes);
    }
    
    public Chunk getChunkAt(final int x, final int z) {
        return this.getOrCreateChunk(x, z);
    }
    
    public boolean canSpawn(final org.bukkit.World world, final int x, final int z) {
        return this.generator.canSpawn(world, x, z);
    }
    
    public List<BlockPopulator> getDefaultPopulators(final org.bukkit.World world) {
        return this.generator.getDefaultPopulators(world);
    }
    
    public List<?> getMobsFor(final EnumCreatureType type, final int x, final int y, final int z) {
        final BiomeBase biomebase = this.world.getBiome(x, z);
        return (List<?>)((biomebase == null) ? null : biomebase.getMobs(type));
    }
    
    public ChunkPosition findNearestMapFeature(final World world, final String type, final int x, final int y, final int z) {
        return ("Stronghold".equals(type) && this.strongholdGen != null) ? this.strongholdGen.getNearestGeneratedFeature(world, x, y, z) : null;
    }
    
    public void recreateStructures(final int i, final int j) {
    }
    
    public int getLoadedChunks() {
        return 0;
    }
    
    public String getName() {
        return "CustomChunkGenerator";
    }
    
    public void b() {
    }
    
    private static class CustomBiomeGrid implements BiomeGrid
    {
        BiomeBase[] biome;
        
        public Biome getBiome(final int x, final int z) {
            return CraftBlock.biomeBaseToBiome(this.biome[z << 4 | x]);
        }
        
        public void setBiome(final int x, final int z, final Biome bio) {
            this.biome[z << 4 | x] = CraftBlock.biomeToBiomeBase(bio);
        }
    }
}
