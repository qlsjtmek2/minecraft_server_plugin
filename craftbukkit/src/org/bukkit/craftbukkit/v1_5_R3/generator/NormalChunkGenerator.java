// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.generator;

import net.minecraft.server.v1_5_R3.ChunkPosition;
import net.minecraft.server.v1_5_R3.EnumCreatureType;
import net.minecraft.server.v1_5_R3.IProgressUpdate;
import net.minecraft.server.v1_5_R3.Chunk;
import java.util.ArrayList;
import org.bukkit.generator.BlockPopulator;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import java.util.Random;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.IChunkProvider;

public class NormalChunkGenerator extends InternalChunkGenerator
{
    private final IChunkProvider provider;
    
    public NormalChunkGenerator(final World world, final long seed) {
        this.provider = world.worldProvider.getChunkProvider();
    }
    
    public byte[] generate(final org.bukkit.World world, final Random random, final int x, final int z) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    public boolean canSpawn(final org.bukkit.World world, final int x, final int z) {
        return ((CraftWorld)world).getHandle().worldProvider.canSpawn(x, z);
    }
    
    public List<BlockPopulator> getDefaultPopulators(final org.bukkit.World world) {
        return new ArrayList<BlockPopulator>();
    }
    
    public boolean isChunkLoaded(final int i, final int i1) {
        return this.provider.isChunkLoaded(i, i1);
    }
    
    public Chunk getOrCreateChunk(final int i, final int i1) {
        return this.provider.getOrCreateChunk(i, i1);
    }
    
    public Chunk getChunkAt(final int i, final int i1) {
        return this.provider.getChunkAt(i, i1);
    }
    
    public void getChunkAt(final IChunkProvider icp, final int i, final int i1) {
        this.provider.getChunkAt(icp, i, i1);
    }
    
    public boolean saveChunks(final boolean bln, final IProgressUpdate ipu) {
        return this.provider.saveChunks(bln, ipu);
    }
    
    public boolean unloadChunks() {
        return this.provider.unloadChunks();
    }
    
    public boolean canSave() {
        return this.provider.canSave();
    }
    
    public List<?> getMobsFor(final EnumCreatureType ect, final int i, final int i1, final int i2) {
        return (List<?>)this.provider.getMobsFor(ect, i, i1, i2);
    }
    
    public ChunkPosition findNearestMapFeature(final World world, final String string, final int i, final int i1, final int i2) {
        return this.provider.findNearestMapFeature(world, string, i, i1, i2);
    }
    
    public void recreateStructures(final int i, final int j) {
        this.provider.recreateStructures(i, j);
    }
    
    public int getLoadedChunks() {
        return 0;
    }
    
    public String getName() {
        return "NormalWorldGenerator";
    }
    
    public void b() {
    }
}
