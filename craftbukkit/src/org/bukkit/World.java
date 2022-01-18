// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import org.bukkit.block.Biome;
import org.bukkit.entity.FallingBlock;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import java.util.UUID;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.List;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

public interface World extends PluginMessageRecipient, Metadatable
{
    Block getBlockAt(final int p0, final int p1, final int p2);
    
    Block getBlockAt(final Location p0);
    
    int getBlockTypeIdAt(final int p0, final int p1, final int p2);
    
    int getBlockTypeIdAt(final Location p0);
    
    int getHighestBlockYAt(final int p0, final int p1);
    
    int getHighestBlockYAt(final Location p0);
    
    Block getHighestBlockAt(final int p0, final int p1);
    
    Block getHighestBlockAt(final Location p0);
    
    Chunk getChunkAt(final int p0, final int p1);
    
    Chunk getChunkAt(final Location p0);
    
    Chunk getChunkAt(final Block p0);
    
    boolean isChunkLoaded(final Chunk p0);
    
    Chunk[] getLoadedChunks();
    
    void loadChunk(final Chunk p0);
    
    boolean isChunkLoaded(final int p0, final int p1);
    
    boolean isChunkInUse(final int p0, final int p1);
    
    void loadChunk(final int p0, final int p1);
    
    boolean loadChunk(final int p0, final int p1, final boolean p2);
    
    boolean unloadChunk(final Chunk p0);
    
    boolean unloadChunk(final int p0, final int p1);
    
    boolean unloadChunk(final int p0, final int p1, final boolean p2);
    
    boolean unloadChunk(final int p0, final int p1, final boolean p2, final boolean p3);
    
    boolean unloadChunkRequest(final int p0, final int p1);
    
    boolean unloadChunkRequest(final int p0, final int p1, final boolean p2);
    
    boolean regenerateChunk(final int p0, final int p1);
    
    boolean refreshChunk(final int p0, final int p1);
    
    Item dropItem(final Location p0, final ItemStack p1);
    
    Item dropItemNaturally(final Location p0, final ItemStack p1);
    
    Arrow spawnArrow(final Location p0, final Vector p1, final float p2, final float p3);
    
    boolean generateTree(final Location p0, final TreeType p1);
    
    boolean generateTree(final Location p0, final TreeType p1, final BlockChangeDelegate p2);
    
    Entity spawnEntity(final Location p0, final EntityType p1);
    
    @Deprecated
    LivingEntity spawnCreature(final Location p0, final EntityType p1);
    
    @Deprecated
    LivingEntity spawnCreature(final Location p0, final CreatureType p1);
    
    LightningStrike strikeLightning(final Location p0);
    
    LightningStrike strikeLightningEffect(final Location p0);
    
    List<Entity> getEntities();
    
    List<LivingEntity> getLivingEntities();
    
    @Deprecated
     <T extends Entity> Collection<T> getEntitiesByClass(final Class<T>... p0);
    
     <T extends Entity> Collection<T> getEntitiesByClass(final Class<T> p0);
    
    Collection<Entity> getEntitiesByClasses(final Class<?>... p0);
    
    List<Player> getPlayers();
    
    String getName();
    
    UUID getUID();
    
    Location getSpawnLocation();
    
    boolean setSpawnLocation(final int p0, final int p1, final int p2);
    
    long getTime();
    
    void setTime(final long p0);
    
    long getFullTime();
    
    void setFullTime(final long p0);
    
    boolean hasStorm();
    
    void setStorm(final boolean p0);
    
    int getWeatherDuration();
    
    void setWeatherDuration(final int p0);
    
    boolean isThundering();
    
    void setThundering(final boolean p0);
    
    int getThunderDuration();
    
    void setThunderDuration(final int p0);
    
    boolean createExplosion(final double p0, final double p1, final double p2, final float p3);
    
    boolean createExplosion(final double p0, final double p1, final double p2, final float p3, final boolean p4);
    
    boolean createExplosion(final double p0, final double p1, final double p2, final float p3, final boolean p4, final boolean p5);
    
    boolean createExplosion(final Location p0, final float p1);
    
    boolean createExplosion(final Location p0, final float p1, final boolean p2);
    
    Environment getEnvironment();
    
    long getSeed();
    
    boolean getPVP();
    
    void setPVP(final boolean p0);
    
    ChunkGenerator getGenerator();
    
    void save();
    
    List<BlockPopulator> getPopulators();
    
     <T extends Entity> T spawn(final Location p0, final Class<T> p1) throws IllegalArgumentException;
    
    FallingBlock spawnFallingBlock(final Location p0, final Material p1, final byte p2) throws IllegalArgumentException;
    
    FallingBlock spawnFallingBlock(final Location p0, final int p1, final byte p2) throws IllegalArgumentException;
    
    void playEffect(final Location p0, final Effect p1, final int p2);
    
    void playEffect(final Location p0, final Effect p1, final int p2, final int p3);
    
     <T> void playEffect(final Location p0, final Effect p1, final T p2);
    
     <T> void playEffect(final Location p0, final Effect p1, final T p2, final int p3);
    
    ChunkSnapshot getEmptyChunkSnapshot(final int p0, final int p1, final boolean p2, final boolean p3);
    
    void setSpawnFlags(final boolean p0, final boolean p1);
    
    boolean getAllowAnimals();
    
    boolean getAllowMonsters();
    
    Biome getBiome(final int p0, final int p1);
    
    void setBiome(final int p0, final int p1, final Biome p2);
    
    double getTemperature(final int p0, final int p1);
    
    double getHumidity(final int p0, final int p1);
    
    int getMaxHeight();
    
    int getSeaLevel();
    
    boolean getKeepSpawnInMemory();
    
    void setKeepSpawnInMemory(final boolean p0);
    
    boolean isAutoSave();
    
    void setAutoSave(final boolean p0);
    
    void setDifficulty(final Difficulty p0);
    
    Difficulty getDifficulty();
    
    File getWorldFolder();
    
    WorldType getWorldType();
    
    boolean canGenerateStructures();
    
    long getTicksPerAnimalSpawns();
    
    void setTicksPerAnimalSpawns(final int p0);
    
    long getTicksPerMonsterSpawns();
    
    void setTicksPerMonsterSpawns(final int p0);
    
    int getMonsterSpawnLimit();
    
    void setMonsterSpawnLimit(final int p0);
    
    int getAnimalSpawnLimit();
    
    void setAnimalSpawnLimit(final int p0);
    
    int getWaterAnimalSpawnLimit();
    
    void setWaterAnimalSpawnLimit(final int p0);
    
    int getAmbientSpawnLimit();
    
    void setAmbientSpawnLimit(final int p0);
    
    void playSound(final Location p0, final Sound p1, final float p2, final float p3);
    
    String[] getGameRules();
    
    String getGameRuleValue(final String p0);
    
    boolean setGameRuleValue(final String p0, final String p1);
    
    boolean isGameRule(final String p0);
    
    Spigot spigot();
    
    public static class Spigot
    {
        public void playEffect(final Location location, final Effect effect) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void playEffect(final Location location, final Effect effect, final int id, final int data, final float offsetX, final float offsetY, final float offsetZ, final float speed, final int particleCount, final int radius) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    public enum Environment
    {
        NORMAL(0), 
        NETHER(-1), 
        THE_END(1);
        
        private final int id;
        private static final Map<Integer, Environment> lookup;
        
        private Environment(final int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
        
        public static Environment getEnvironment(final int id) {
            return Environment.lookup.get(id);
        }
        
        static {
            lookup = new HashMap<Integer, Environment>();
            for (final Environment env : values()) {
                Environment.lookup.put(env.getId(), env);
            }
        }
    }

	net.minecraft.server.v1_5_R3.World getHandle();
}
