// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.generator;

import org.bukkit.block.Biome;
import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.Material;
import java.util.Random;
import org.bukkit.World;

public abstract class ChunkGenerator
{
    @Deprecated
    public byte[] generate(final World world, final Random random, final int x, final int z) {
        throw new UnsupportedOperationException("Custom generator is missing required methods: generate(), generateBlockSections() and generateExtBlockSections()");
    }
    
    public short[][] generateExtBlockSections(final World world, final Random random, final int x, final int z, final BiomeGrid biomes) {
        return null;
    }
    
    public byte[][] generateBlockSections(final World world, final Random random, final int x, final int z, final BiomeGrid biomes) {
        return null;
    }
    
    public boolean canSpawn(final World world, final int x, final int z) {
        final Block highest = world.getBlockAt(x, world.getHighestBlockYAt(x, z), z);
        switch (world.getEnvironment()) {
            case NETHER: {
                return true;
            }
            case THE_END: {
                return highest.getType() != Material.AIR && highest.getType() != Material.WATER && highest.getType() != Material.LAVA;
            }
            default: {
                return highest.getType() == Material.SAND || highest.getType() == Material.GRAVEL;
            }
        }
    }
    
    public List<BlockPopulator> getDefaultPopulators(final World world) {
        return new ArrayList<BlockPopulator>();
    }
    
    public Location getFixedSpawnLocation(final World world, final Random random) {
        return null;
    }
    
    public interface BiomeGrid
    {
        Biome getBiome(final int p0, final int p1);
        
        void setBiome(final int p0, final int p1, final Biome p2);
    }
}
