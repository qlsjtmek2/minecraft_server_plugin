// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import org.bukkit.block.Biome;

public interface ChunkSnapshot
{
    int getX();
    
    int getZ();
    
    String getWorldName();
    
    int getBlockTypeId(final int p0, final int p1, final int p2);
    
    int getBlockData(final int p0, final int p1, final int p2);
    
    int getBlockSkyLight(final int p0, final int p1, final int p2);
    
    int getBlockEmittedLight(final int p0, final int p1, final int p2);
    
    int getHighestBlockYAt(final int p0, final int p1);
    
    Biome getBiome(final int p0, final int p1);
    
    double getRawBiomeTemperature(final int p0, final int p1);
    
    double getRawBiomeRainfall(final int p0, final int p1);
    
    long getCaptureFullTime();
    
    boolean isSectionEmpty(final int p0);
}
