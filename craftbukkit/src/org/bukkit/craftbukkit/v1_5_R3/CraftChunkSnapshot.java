// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlock;
import org.bukkit.block.Biome;
import net.minecraft.server.v1_5_R3.BiomeBase;
import org.bukkit.ChunkSnapshot;

public class CraftChunkSnapshot implements ChunkSnapshot
{
    private final int x;
    private final int z;
    private final String worldname;
    private final short[][] blockids;
    private final byte[][] blockdata;
    private final byte[][] skylight;
    private final byte[][] emitlight;
    private final boolean[] empty;
    private final int[] hmap;
    private final long captureFulltime;
    private final BiomeBase[] biome;
    private final double[] biomeTemp;
    private final double[] biomeRain;
    
    CraftChunkSnapshot(final int x, final int z, final String wname, final long wtime, final short[][] sectionBlockIDs, final byte[][] sectionBlockData, final byte[][] sectionSkyLights, final byte[][] sectionEmitLights, final boolean[] sectionEmpty, final int[] hmap, final BiomeBase[] biome, final double[] biomeTemp, final double[] biomeRain) {
        this.x = x;
        this.z = z;
        this.worldname = wname;
        this.captureFulltime = wtime;
        this.blockids = sectionBlockIDs;
        this.blockdata = sectionBlockData;
        this.skylight = sectionSkyLights;
        this.emitlight = sectionEmitLights;
        this.empty = sectionEmpty;
        this.hmap = hmap;
        this.biome = biome;
        this.biomeTemp = biomeTemp;
        this.biomeRain = biomeRain;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public String getWorldName() {
        return this.worldname;
    }
    
    public final int getBlockTypeId(final int x, final int y, final int z) {
        return this.blockids[y >> 4][(y & 0xF) << 8 | z << 4 | x];
    }
    
    public final int getBlockData(final int x, final int y, final int z) {
        final int off = (y & 0xF) << 7 | z << 3 | x >> 1;
        return this.blockdata[y >> 4][off] >> ((x & 0x1) << 2) & 0xF;
    }
    
    public final int getBlockSkyLight(final int x, final int y, final int z) {
        final int off = (y & 0xF) << 7 | z << 3 | x >> 1;
        return this.skylight[y >> 4][off] >> ((x & 0x1) << 2) & 0xF;
    }
    
    public final int getBlockEmittedLight(final int x, final int y, final int z) {
        final int off = (y & 0xF) << 7 | z << 3 | x >> 1;
        return this.emitlight[y >> 4][off] >> ((x & 0x1) << 2) & 0xF;
    }
    
    public final int getHighestBlockYAt(final int x, final int z) {
        return this.hmap[z << 4 | x];
    }
    
    public final Biome getBiome(final int x, final int z) {
        return CraftBlock.biomeBaseToBiome(this.biome[z << 4 | x]);
    }
    
    public final double getRawBiomeTemperature(final int x, final int z) {
        return this.biomeTemp[z << 4 | x];
    }
    
    public final double getRawBiomeRainfall(final int x, final int z) {
        return this.biomeRain[z << 4 | x];
    }
    
    public final long getCaptureFullTime() {
        return this.captureFulltime;
    }
    
    public final boolean isSectionEmpty(final int sy) {
        return this.empty[sy];
    }
}
