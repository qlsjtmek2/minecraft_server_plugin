// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import org.bukkit.metadata.MetadataStoreBase;
import net.minecraft.server.v1_5_R3.ChunkProviderServer;
import org.bukkit.Sound;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.WorldType;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.plugin.Plugin;
import net.minecraft.server.v1_5_R3.WorldNBTStorage;
import java.io.File;
import org.bukkit.ChunkSnapshot;
import net.minecraft.server.v1_5_R3.EntityFireworks;
import org.bukkit.entity.Firework;
import net.minecraft.server.v1_5_R3.EntityFishingHook;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Weather;
import net.minecraft.server.v1_5_R3.EntityExperienceOrb;
import org.bukkit.entity.ExperienceOrb;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityTNTPrimed;
import org.bukkit.entity.TNTPrimed;
import net.minecraft.server.v1_5_R3.EntityHanging;
import net.minecraft.server.v1_5_R3.EntityItemFrame;
import org.bukkit.entity.ItemFrame;
import net.minecraft.server.v1_5_R3.EntityPainting;
import org.bukkit.entity.Painting;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Hanging;
import net.minecraft.server.v1_5_R3.EntityBat;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Ambient;
import net.minecraft.server.v1_5_R3.EntityEnderDragon;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.ComplexLivingEntity;
import net.minecraft.server.v1_5_R3.EntityWither;
import org.bukkit.entity.Wither;
import net.minecraft.server.v1_5_R3.EntityWitch;
import org.bukkit.entity.Witch;
import net.minecraft.server.v1_5_R3.EntityVillager;
import org.bukkit.entity.Villager;
import net.minecraft.server.v1_5_R3.EntityBlaze;
import org.bukkit.entity.Blaze;
import net.minecraft.server.v1_5_R3.EntityEnderman;
import org.bukkit.entity.Enderman;
import net.minecraft.server.v1_5_R3.EntitySilverfish;
import org.bukkit.entity.Silverfish;
import net.minecraft.server.v1_5_R3.EntityGiantZombie;
import org.bukkit.entity.Giant;
import net.minecraft.server.v1_5_R3.EntityZombie;
import org.bukkit.entity.Zombie;
import net.minecraft.server.v1_5_R3.EntityPigZombie;
import org.bukkit.entity.PigZombie;
import net.minecraft.server.v1_5_R3.EntityOcelot;
import org.bukkit.entity.Ocelot;
import net.minecraft.server.v1_5_R3.EntityWolf;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Tameable;
import net.minecraft.server.v1_5_R3.EntitySquid;
import org.bukkit.entity.Squid;
import net.minecraft.server.v1_5_R3.EntitySpider;
import net.minecraft.server.v1_5_R3.EntityCaveSpider;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Spider;
import net.minecraft.server.v1_5_R3.EntitySlime;
import net.minecraft.server.v1_5_R3.EntityMagmaCube;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Slime;
import net.minecraft.server.v1_5_R3.EntitySkeleton;
import org.bukkit.entity.Skeleton;
import net.minecraft.server.v1_5_R3.EntitySheep;
import org.bukkit.entity.Sheep;
import net.minecraft.server.v1_5_R3.EntityPig;
import org.bukkit.entity.Pig;
import net.minecraft.server.v1_5_R3.EntityGhast;
import org.bukkit.entity.Ghast;
import net.minecraft.server.v1_5_R3.EntityCreeper;
import org.bukkit.entity.Creeper;
import net.minecraft.server.v1_5_R3.EntityIronGolem;
import org.bukkit.entity.IronGolem;
import net.minecraft.server.v1_5_R3.EntitySnowman;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Golem;
import net.minecraft.server.v1_5_R3.EntityCow;
import net.minecraft.server.v1_5_R3.EntityMushroomCow;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Cow;
import net.minecraft.server.v1_5_R3.EntityChicken;
import org.bukkit.entity.Chicken;
import net.minecraft.server.v1_5_R3.EntityEnderCrystal;
import org.bukkit.entity.EnderCrystal;
import net.minecraft.server.v1_5_R3.EntityEnderSignal;
import org.bukkit.entity.EnderSignal;
import net.minecraft.server.v1_5_R3.EntityMinecartRideable;
import net.minecraft.server.v1_5_R3.EntityMinecartMobSpawner;
import org.bukkit.entity.minecart.SpawnerMinecart;
import net.minecraft.server.v1_5_R3.EntityMinecartHopper;
import org.bukkit.entity.minecart.HopperMinecart;
import net.minecraft.server.v1_5_R3.EntityMinecartTNT;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import net.minecraft.server.v1_5_R3.EntityMinecartChest;
import org.bukkit.entity.minecart.StorageMinecart;
import net.minecraft.server.v1_5_R3.EntityMinecartFurnace;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.Minecart;
import net.minecraft.server.v1_5_R3.EntityFireball;
import net.minecraft.server.v1_5_R3.EntityLargeFireball;
import net.minecraft.server.v1_5_R3.EntityWitherSkull;
import org.bukkit.entity.WitherSkull;
import net.minecraft.server.v1_5_R3.EntitySmallFireball;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Fireball;
import net.minecraft.server.v1_5_R3.EntityPotion;
import org.bukkit.entity.ThrownPotion;
import net.minecraft.server.v1_5_R3.EntityThrownExpBottle;
import org.bukkit.entity.ThrownExpBottle;
import net.minecraft.server.v1_5_R3.EntityEgg;
import org.bukkit.entity.Egg;
import net.minecraft.server.v1_5_R3.EntitySnowball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Projectile;
import net.minecraft.server.v1_5_R3.EntityBoat;
import org.bukkit.entity.Boat;
import net.minecraft.server.v1_5_R3.EntityFallingBlock;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.Difficulty;
import net.minecraft.server.v1_5_R3.ExceptionWorldConflict;
import net.minecraft.server.v1_5_R3.IProgressUpdate;
import java.util.Collection;
import net.minecraft.server.v1_5_R3.BiomeBase;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlock;
import org.bukkit.block.Biome;
import net.minecraft.server.v1_5_R3.WorldProvider;
import net.minecraft.server.v1_5_R3.Packet4UpdateTime;
import java.util.UUID;
import net.minecraft.server.v1_5_R3.TileEntity;
import net.minecraft.server.v1_5_R3.BlockSapling;
import net.minecraft.server.v1_5_R3.WorldGenSwampTree;
import net.minecraft.server.v1_5_R3.WorldGenHugeMushroom;
import net.minecraft.server.v1_5_R3.WorldGenGroundBush;
import net.minecraft.server.v1_5_R3.WorldGenTrees;
import net.minecraft.server.v1_5_R3.WorldGenMegaTree;
import net.minecraft.server.v1_5_R3.WorldGenTaiga1;
import net.minecraft.server.v1_5_R3.WorldGenTaiga2;
import net.minecraft.server.v1_5_R3.WorldGenForest;
import net.minecraft.server.v1_5_R3.WorldGenBigTree;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.TreeType;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLightningStrike;
import net.minecraft.server.v1_5_R3.EntityLightning;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.CreatureType;
import net.minecraft.server.v1_5_R3.EntityArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftItem;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityItem;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.IChunkProvider;
import org.bukkit.craftbukkit.v1_5_R3.util.LongHash;
import net.minecraft.server.v1_5_R3.EmptyChunk;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.world.SpawnChangeEvent;
import net.minecraft.server.v1_5_R3.ChunkCoordinates;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.Iterator;
import net.minecraft.server.v1_5_R3.Packet;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_5_R3.Packet63WorldParticles;
import org.bukkit.material.MaterialData;
import net.minecraft.server.v1_5_R3.Material;
import net.minecraft.server.v1_5_R3.Packet61WorldEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import java.util.Random;
import org.bukkit.craftbukkit.v1_5_R3.metadata.BlockMetadataStore;
import org.bukkit.generator.BlockPopulator;
import java.util.List;
import org.bukkit.generator.ChunkGenerator;
import net.minecraft.server.v1_5_R3.WorldServer;
import org.bukkit.World;

public class CraftWorld implements World
{
    public static final int CUSTOM_DIMENSION_OFFSET = 10;
    private final WorldServer world;
    private Environment environment;
    private final CraftServer server;
    private final ChunkGenerator generator;
    private final List<BlockPopulator> populators;
    private final BlockMetadataStore blockMetadata;
    private int monsterSpawn;
    private int animalSpawn;
    private int waterAnimalSpawn;
    private int ambientSpawn;
    private int chunkLoadCount;
    private int chunkGCTickCount;
    private static final Random rand;
    public int growthPerTick;
    public boolean randomLightingUpdates;
    public int mobSpawnRange;
    public int aggregateTicks;
    public int wheatGrowthModifier;
    public int cactusGrowthModifier;
    public int melonGrowthModifier;
    public int pumpkinGrowthModifier;
    public int sugarGrowthModifier;
    public int treeGrowthModifier;
    public int mushroomGrowthModifier;
    public double itemMergeRadius;
    public double expMergeRadius;
    public int viewDistance;
    public boolean obfuscated;
    public int miscEntityActivationRange;
    public int animalEntityActivationRange;
    public int monsterEntityActivationRange;
    public int playerTrackingRange;
    public int miscTrackingRange;
    public int animalTrackingRange;
    public int monsterTrackingRange;
    public int maxTrackingRange;
    private final Spigot spigot;
    
    public CraftWorld(final WorldServer world, final ChunkGenerator gen, final Environment env) {
        this(world, gen, env, "default");
    }
    
    public CraftWorld(final WorldServer world, final ChunkGenerator gen, final Environment env, String name) {
        this.server = (CraftServer)Bukkit.getServer();
        this.populators = new ArrayList<BlockPopulator>();
        this.blockMetadata = new BlockMetadataStore(this);
        this.monsterSpawn = -1;
        this.animalSpawn = -1;
        this.waterAnimalSpawn = -1;
        this.ambientSpawn = -1;
        this.chunkLoadCount = 0;
        this.growthPerTick = 650;
        this.randomLightingUpdates = false;
        this.mobSpawnRange = 4;
        this.aggregateTicks = 4;
        this.wheatGrowthModifier = 100;
        this.cactusGrowthModifier = 100;
        this.melonGrowthModifier = 100;
        this.pumpkinGrowthModifier = 100;
        this.sugarGrowthModifier = 100;
        this.treeGrowthModifier = 100;
        this.mushroomGrowthModifier = 100;
        this.itemMergeRadius = 3.5;
        this.expMergeRadius = 3.5;
        this.obfuscated = false;
        this.miscEntityActivationRange = 16;
        this.animalEntityActivationRange = 32;
        this.monsterEntityActivationRange = 32;
        this.playerTrackingRange = 64;
        this.miscTrackingRange = 32;
        this.animalTrackingRange = 48;
        this.monsterTrackingRange = 48;
        this.maxTrackingRange = 64;
        this.spigot = new Spigot() {
            public void playEffect(final Location location, final Effect effect, final int id, final int data, final float offsetX, final float offsetY, final float offsetZ, final float speed, final int particleCount, int radius) {
                Validate.notNull(location, "Location cannot be null");
                Validate.notNull(effect, "Effect cannot be null");
                Validate.notNull(location.getWorld(), "World cannot be null");
                Packet packet;
                if (effect.getType() != Effect.Type.PARTICLE) {
                    final int packetData = effect.getId();
                    packet = new Packet61WorldEvent(packetData, location.getBlockX(), location.getBlockY(), location.getBlockZ(), id, false);
                }
                else {
                    final StringBuilder particleFullName = new StringBuilder();
                    particleFullName.append(effect.getName());
                    if ((effect.getData() != null && effect.getData().equals(Material.class)) || effect.getData().equals(MaterialData.class)) {
                        particleFullName.append('_').append(id);
                    }
                    if (effect.getData() != null && effect.getData().equals(MaterialData.class)) {
                        particleFullName.append('_').append(data);
                    }
                    packet = new Packet63WorldParticles(effect.getName(), (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, particleCount, radius);
                }
                radius *= radius;
                for (final Player player : CraftWorld.this.getPlayers()) {
                    if (((CraftPlayer)player).getHandle().playerConnection == null) {
                        continue;
                    }
                    if (!location.getWorld().equals(player.getWorld())) {
                        continue;
                    }
                    final int distance = (int)player.getLocation().distanceSquared(location);
                    if (distance > radius) {
                        continue;
                    }
                    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                }
            }
            
            public void playEffect(final Location location, final Effect effect) {
                CraftWorld.this.playEffect(location, effect, 0);
            }
        };
        this.world = world;
        this.generator = gen;
        this.environment = env;
        if (this.server.chunkGCPeriod > 0) {
            this.chunkGCTickCount = CraftWorld.rand.nextInt(this.server.chunkGCPeriod);
        }
        final YamlConfiguration configuration = this.server.configuration;
        name = name.replaceAll(" ", "_");
        boolean info = configuration.getBoolean("world-settings.default.info", true);
        this.growthPerTick = configuration.getInt("world-settings.default.growth-chunks-per-tick", this.growthPerTick);
        this.randomLightingUpdates = configuration.getBoolean("world-settings.default.random-light-updates", this.randomLightingUpdates);
        this.mobSpawnRange = configuration.getInt("world-settings.default.mob-spawn-range", this.mobSpawnRange);
        this.aggregateTicks = Math.max(1, configuration.getInt("world-settings.default.aggregate-chunkticks", this.aggregateTicks));
        this.wheatGrowthModifier = configuration.getInt("world-settings.default.wheat-growth-modifier", this.wheatGrowthModifier);
        this.cactusGrowthModifier = configuration.getInt("world-settings.default.cactus-growth-modifier", this.cactusGrowthModifier);
        this.melonGrowthModifier = configuration.getInt("world-settings.default.melon-growth-modifier", this.melonGrowthModifier);
        this.pumpkinGrowthModifier = configuration.getInt("world-settings.default.pumpkin-growth-modifier", this.pumpkinGrowthModifier);
        this.sugarGrowthModifier = configuration.getInt("world-settings.default.sugar-growth-modifier", this.sugarGrowthModifier);
        this.treeGrowthModifier = configuration.getInt("world-settings.default.tree-growth-modifier", this.treeGrowthModifier);
        this.mushroomGrowthModifier = configuration.getInt("world-settings.default.mushroom-growth-modifier", this.mushroomGrowthModifier);
        this.itemMergeRadius = configuration.getDouble("world-settings.default.item-merge-radius", this.itemMergeRadius);
        this.expMergeRadius = configuration.getDouble("world-settings.default.exp-merge-radius", this.expMergeRadius);
        this.miscEntityActivationRange = configuration.getInt("world-settings.default.entity-activation-range-misc");
        this.animalEntityActivationRange = configuration.getInt("world-settings.default.entity-activation-range-animals");
        this.monsterEntityActivationRange = configuration.getInt("world-settings.default.entity-activation-range-monsters");
        this.playerTrackingRange = configuration.getInt("world-settings.default.entity-tracking-range-players");
        this.miscTrackingRange = configuration.getInt("world-settings.default.entity-tracking-range-misc");
        this.animalTrackingRange = configuration.getInt("world-settings.default.entity-tracking-range-animals");
        this.monsterTrackingRange = configuration.getInt("world-settings.default.entity-tracking-range-monsters");
        this.maxTrackingRange = configuration.getInt("world-settings.default.entity-tracking-range-max");
        info = configuration.getBoolean("world-settings." + name + ".info", info);
        this.growthPerTick = configuration.getInt("world-settings." + name + ".growth-chunks-per-tick", this.growthPerTick);
        this.randomLightingUpdates = configuration.getBoolean("world-settings." + name + ".random-light-updates", this.randomLightingUpdates);
        this.mobSpawnRange = configuration.getInt("world-settings." + name + ".mob-spawn-range", this.mobSpawnRange);
        this.aggregateTicks = Math.max(1, configuration.getInt("world-settings." + name + ".aggregate-chunkticks", this.aggregateTicks));
        this.wheatGrowthModifier = configuration.getInt("world-settings." + name + ".wheat-growth-modifier", this.wheatGrowthModifier);
        this.cactusGrowthModifier = configuration.getInt("world-settings." + name + ".cactus-growth-modifier", this.cactusGrowthModifier);
        this.melonGrowthModifier = configuration.getInt("world-settings." + name + ".melon-growth-modifier", this.melonGrowthModifier);
        this.pumpkinGrowthModifier = configuration.getInt("world-settings." + name + ".pumpkin-growth-modifier", this.pumpkinGrowthModifier);
        this.sugarGrowthModifier = configuration.getInt("world-settings." + name + ".sugar-growth-modifier", this.sugarGrowthModifier);
        this.treeGrowthModifier = configuration.getInt("world-settings." + name + ".tree-growth-modifier", this.treeGrowthModifier);
        this.mushroomGrowthModifier = configuration.getInt("world-settings." + name + ".mushroom-growth-modifier", this.mushroomGrowthModifier);
        this.itemMergeRadius = configuration.getDouble("world-settings." + name + ".item-merge-radius", this.itemMergeRadius);
        this.expMergeRadius = configuration.getDouble("world-settings." + name + ".exp-merge-radius", this.expMergeRadius);
        this.miscEntityActivationRange = configuration.getInt("world-settings." + name + ".entity-activation-range-misc", this.miscEntityActivationRange);
        this.animalEntityActivationRange = configuration.getInt("world-settings." + name + ".entity-activation-range-animals", this.animalEntityActivationRange);
        this.monsterEntityActivationRange = configuration.getInt("world-settings." + name + ".entity-activation-range-monsters", this.monsterEntityActivationRange);
        this.maxTrackingRange = configuration.getInt("world-settings." + name + ".entity-tracking-range-max", this.maxTrackingRange);
        this.playerTrackingRange = Math.min(this.maxTrackingRange, configuration.getInt("world-settings." + name + ".entity-tracking-range-players", this.playerTrackingRange));
        this.miscTrackingRange = Math.min(this.maxTrackingRange, configuration.getInt("world-settings." + name + ".entity-tracking-range-misc", this.miscTrackingRange));
        this.animalTrackingRange = Math.min(this.maxTrackingRange, configuration.getInt("world-settings." + name + ".entity-tracking-range-animals", this.animalTrackingRange));
        this.monsterTrackingRange = Math.min(this.maxTrackingRange, configuration.getInt("world-settings." + name + ".entity-tracking-range-monsters", this.monsterTrackingRange));
        this.viewDistance = Bukkit.getServer().getViewDistance();
        this.viewDistance = configuration.getInt("world-settings." + name + ".view-distance", this.viewDistance);
        this.obfuscated = (world.getServer().orebfuscatorEnabled && !world.getServer().orebfuscatorDisabledWorlds.contains(name));
        if (this.maxTrackingRange == 0) {
            System.err.println("Error! Should not have 0 maxRange");
        }
        if (info) {
            this.server.getLogger().info("-------------- Spigot ----------------");
            this.server.getLogger().info("-------- World Settings For [" + name + "] --------");
            this.server.getLogger().info("Growth Per Tick: " + this.growthPerTick);
            this.server.getLogger().info("Random Lighting Updates: " + this.randomLightingUpdates);
            this.server.getLogger().info("Mob Spawn Range: " + this.mobSpawnRange);
            this.server.getLogger().info("Aggregate Ticks: " + this.aggregateTicks);
            this.server.getLogger().info("Wheat Growth Modifier: " + this.wheatGrowthModifier);
            this.server.getLogger().info("Cactus Growth Modifier: " + this.cactusGrowthModifier);
            this.server.getLogger().info("Melon Growth Modifier: " + this.melonGrowthModifier);
            this.server.getLogger().info("Pumpkin Growth Modifier: " + this.pumpkinGrowthModifier);
            this.server.getLogger().info("Sugar Growth Modifier: " + this.sugarGrowthModifier);
            this.server.getLogger().info("Tree Growth Modifier: " + this.treeGrowthModifier);
            this.server.getLogger().info("Mushroom Growth Modifier: " + this.mushroomGrowthModifier);
            this.server.getLogger().info("Item Merge Radius: " + this.itemMergeRadius);
            this.server.getLogger().info("Exp Merge Radius: " + this.expMergeRadius);
            this.server.getLogger().info("View distance: " + this.viewDistance);
            this.server.getLogger().info("Orebfuscator: " + this.obfuscated);
            this.server.getLogger().info("Entity Activation Range: An " + this.animalEntityActivationRange + " / Mo " + this.monsterEntityActivationRange + " / Mi " + this.miscEntityActivationRange);
            this.server.getLogger().info("Entity Tracking Range: Pl " + this.playerTrackingRange + " / An " + this.animalTrackingRange + " / Mo " + this.monsterTrackingRange + " / Mi " + this.miscTrackingRange + " / Max " + this.maxTrackingRange);
            this.server.getLogger().info("-------------------------------------------------");
        }
    }
    
    public Block getBlockAt(final int x, final int y, final int z) {
        return this.getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0xFF, z & 0xF);
    }
    
    public int getBlockTypeIdAt(final int x, final int y, final int z) {
        return this.world.getTypeId(x, y, z);
    }
    
    public int getHighestBlockYAt(final int x, final int z) {
        if (!this.isChunkLoaded(x >> 4, z >> 4)) {
            this.loadChunk(x >> 4, z >> 4);
        }
        return this.world.getHighestBlockYAt(x, z);
    }
    
    public Location getSpawnLocation() {
        final ChunkCoordinates spawn = this.world.getSpawn();
        return new Location(this, spawn.x, spawn.y, spawn.z);
    }
    
    public boolean setSpawnLocation(final int x, final int y, final int z) {
        try {
            final Location previousLocation = this.getSpawnLocation();
            this.world.worldData.setSpawn(x, y, z);
            final SpawnChangeEvent event = new SpawnChangeEvent(this, previousLocation);
            this.server.getPluginManager().callEvent(event);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public Chunk getChunkAt(final int x, final int z) {
        return this.world.chunkProviderServer.getChunkAt(x, z).bukkitChunk;
    }
    
    public Chunk getChunkAt(final Block block) {
        return this.getChunkAt(block.getX() >> 4, block.getZ() >> 4);
    }
    
    public boolean isChunkLoaded(final int x, final int z) {
        return this.world.chunkProviderServer.isChunkLoaded(x, z);
    }
    
    public Chunk[] getLoadedChunks() {
        final Object[] chunks = this.world.chunkProviderServer.chunks.values().toArray();
        final Chunk[] craftChunks = new CraftChunk[chunks.length];
        for (int i = 0; i < chunks.length; ++i) {
            final net.minecraft.server.v1_5_R3.Chunk chunk = (net.minecraft.server.v1_5_R3.Chunk)chunks[i];
            craftChunks[i] = chunk.bukkitChunk;
        }
        return craftChunks;
    }
    
    public void loadChunk(final int x, final int z) {
        this.loadChunk(x, z, true);
    }
    
    public boolean unloadChunk(final Chunk chunk) {
        return this.unloadChunk(chunk.getX(), chunk.getZ());
    }
    
    public boolean unloadChunk(final int x, final int z) {
        return this.unloadChunk(x, z, true);
    }
    
    public boolean unloadChunk(final int x, final int z, final boolean save) {
        return this.unloadChunk(x, z, save, false);
    }
    
    public boolean unloadChunkRequest(final int x, final int z) {
        return this.unloadChunkRequest(x, z, true);
    }
    
    public boolean unloadChunkRequest(final int x, final int z, final boolean safe) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous chunk unload!");
        }
        if (safe && this.isChunkInUse(x, z)) {
            return false;
        }
        this.world.chunkProviderServer.queueUnload(x, z);
        return true;
    }
    
    public boolean unloadChunk(final int x, final int z, boolean save, final boolean safe) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous chunk unload!");
        }
        if (safe && this.isChunkInUse(x, z)) {
            return false;
        }
        final net.minecraft.server.v1_5_R3.Chunk chunk = this.world.chunkProviderServer.getOrCreateChunk(x, z);
        if (chunk.mustSave) {
            save = true;
        }
        chunk.removeEntities();
        if (save && !(chunk instanceof EmptyChunk)) {
            this.world.chunkProviderServer.saveChunk(chunk);
            this.world.chunkProviderServer.saveChunkNOP(chunk);
        }
        this.world.chunkProviderServer.unloadQueue.remove(x, z);
        this.world.chunkProviderServer.chunks.remove(LongHash.toLong(x, z));
        return true;
    }
    
    public boolean regenerateChunk(final int x, final int z) {
        this.unloadChunk(x, z, false, false);
        this.world.chunkProviderServer.unloadQueue.remove(x, z);
        net.minecraft.server.v1_5_R3.Chunk chunk = null;
        if (this.world.chunkProviderServer.chunkProvider == null) {
            chunk = this.world.chunkProviderServer.emptyChunk;
        }
        else {
            chunk = this.world.chunkProviderServer.chunkProvider.getOrCreateChunk(x, z);
        }
        this.chunkLoadPostProcess(chunk, x, z);
        this.refreshChunk(x, z);
        return chunk != null;
    }
    
    public boolean refreshChunk(final int x, final int z) {
        if (!this.isChunkLoaded(x, z)) {
            return false;
        }
        final int px = x << 4;
        final int pz = z << 4;
        final int height = this.getMaxHeight() / 16;
        for (int idx = 0; idx < 64; ++idx) {
            this.world.notify(px + idx / height, idx % height * 16, pz);
        }
        this.world.notify(px + 15, height * 16 - 1, pz + 15);
        return true;
    }
    
    public boolean isChunkInUse(final int x, final int z) {
        return this.world.getPlayerChunkMap().isChunkInUse(x, z);
    }
    
    public boolean loadChunk(final int x, final int z, final boolean generate) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous chunk load!");
        }
        ++this.chunkLoadCount;
        if (generate) {
            return this.world.chunkProviderServer.getChunkAt(x, z) != null;
        }
        this.world.chunkProviderServer.unloadQueue.remove(x, z);
        net.minecraft.server.v1_5_R3.Chunk chunk = this.world.chunkProviderServer.chunks.get(LongHash.toLong(x, z));
        if (chunk == null) {
            chunk = this.world.chunkProviderServer.loadChunk(x, z);
            this.chunkLoadPostProcess(chunk, x, z);
        }
        return chunk != null;
    }
    
    private void chunkLoadPostProcess(final net.minecraft.server.v1_5_R3.Chunk chunk, final int x, final int z) {
        if (chunk != null) {
            this.world.chunkProviderServer.chunks.put(LongHash.toLong(x, z), chunk);
            chunk.addEntities();
            if (!chunk.done && this.world.chunkProviderServer.isChunkLoaded(x + 1, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x + 1, z)) {
                this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x, z);
            }
            if (this.world.chunkProviderServer.isChunkLoaded(x - 1, z) && !this.world.chunkProviderServer.getOrCreateChunk(x - 1, z).done && this.world.chunkProviderServer.isChunkLoaded(x - 1, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x, z + 1) && this.world.chunkProviderServer.isChunkLoaded(x - 1, z)) {
                this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x - 1, z);
            }
            if (this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && !this.world.chunkProviderServer.getOrCreateChunk(x, z - 1).done && this.world.chunkProviderServer.isChunkLoaded(x + 1, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x + 1, z)) {
                this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x, z - 1);
            }
            if (this.world.chunkProviderServer.isChunkLoaded(x - 1, z - 1) && !this.world.chunkProviderServer.getOrCreateChunk(x - 1, z - 1).done && this.world.chunkProviderServer.isChunkLoaded(x - 1, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x, z - 1) && this.world.chunkProviderServer.isChunkLoaded(x - 1, z)) {
                this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x - 1, z - 1);
            }
        }
    }
    
    public boolean isChunkLoaded(final Chunk chunk) {
        return this.isChunkLoaded(chunk.getX(), chunk.getZ());
    }
    
    public void loadChunk(final Chunk chunk) {
        this.loadChunk(chunk.getX(), chunk.getZ());
        ((CraftChunk)this.getChunkAt(chunk.getX(), chunk.getZ())).getHandle().bukkitChunk = chunk;
    }
    
    public WorldServer getHandle() {
        return this.world;
    }
    
    public Item dropItem(final Location loc, final ItemStack item) {
        Validate.notNull(item, "Cannot drop a Null item.");
        Validate.isTrue(item.getTypeId() != 0, "Cannot drop AIR.");
        final EntityItem entity = new EntityItem(this.world, loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        entity.pickupDelay = 10;
        this.world.addEntity(entity);
        return new CraftItem(this.world.getServer(), entity);
    }
    
    public Item dropItemNaturally(Location loc, final ItemStack item) {
        final double xs = this.world.random.nextFloat() * 0.7f + 0.15000000596046448;
        final double ys = this.world.random.nextFloat() * 0.7f + 0.15000000596046448;
        final double zs = this.world.random.nextFloat() * 0.7f + 0.15000000596046448;
        loc = loc.clone();
        loc.setX(loc.getX() + xs);
        loc.setY(loc.getY() + ys);
        loc.setZ(loc.getZ() + zs);
        return this.dropItem(loc, item);
    }
    
    public Arrow spawnArrow(final Location loc, final Vector velocity, final float speed, final float spread) {
        Validate.notNull(loc, "Can not spawn arrow with a null location");
        Validate.notNull(velocity, "Can not spawn arrow with a null velocity");
        final EntityArrow arrow = new EntityArrow(this.world);
        arrow.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
        arrow.shoot(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
        this.world.addEntity(arrow);
        return (Arrow)arrow.getBukkitEntity();
    }
    
    @Deprecated
    public LivingEntity spawnCreature(final Location loc, final CreatureType creatureType) {
        return this.spawnCreature(loc, creatureType.toEntityType());
    }
    
    @Deprecated
    public LivingEntity spawnCreature(final Location loc, final EntityType creatureType) {
        Validate.isTrue(creatureType.isAlive(), "EntityType not instance of LivingEntity");
        return (LivingEntity)this.spawnEntity(loc, creatureType);
    }
    
    public org.bukkit.entity.Entity spawnEntity(final Location loc, final EntityType entityType) {
        return this.spawn(loc, entityType.getEntityClass());
    }
    
    public LightningStrike strikeLightning(final Location loc) {
        final EntityLightning lightning = new EntityLightning(this.world, loc.getX(), loc.getY(), loc.getZ());
        this.world.strikeLightning(lightning);
        return new CraftLightningStrike(this.server, lightning);
    }
    
    public LightningStrike strikeLightningEffect(final Location loc) {
        final EntityLightning lightning = new EntityLightning(this.world, loc.getX(), loc.getY(), loc.getZ(), true);
        this.world.strikeLightning(lightning);
        return new CraftLightningStrike(this.server, lightning);
    }
    
    public boolean generateTree(final Location loc, final TreeType type) {
        return this.generateTree(loc, type, this.world);
    }
    
    public boolean generateTree(final Location loc, final TreeType type, final BlockChangeDelegate delegate) {
        BlockSapling.TreeGenerator gen = null;
        switch (type) {
            case BIG_TREE: {
                gen = new WorldGenBigTree(true);
                break;
            }
            case BIRCH: {
                gen = new WorldGenForest(true);
                break;
            }
            case REDWOOD: {
                gen = new WorldGenTaiga2(true);
                break;
            }
            case TALL_REDWOOD: {
                gen = new WorldGenTaiga1();
                break;
            }
            case JUNGLE: {
                gen = new WorldGenMegaTree(true, 10 + CraftWorld.rand.nextInt(20), 3, 3);
                break;
            }
            case SMALL_JUNGLE: {
                gen = new WorldGenTrees(true, 4 + CraftWorld.rand.nextInt(7), 3, 3, false);
                break;
            }
            case JUNGLE_BUSH: {
                gen = new WorldGenGroundBush(3, 0);
                break;
            }
            case RED_MUSHROOM: {
                gen = new WorldGenHugeMushroom(1);
                break;
            }
            case BROWN_MUSHROOM: {
                gen = new WorldGenHugeMushroom(0);
                break;
            }
            case SWAMP: {
                gen = new WorldGenSwampTree();
                break;
            }
            default: {
                gen = new WorldGenTrees(true);
                break;
            }
        }
        return gen.generate(delegate, CraftWorld.rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
    
    public TileEntity getTileEntityAt(final int x, final int y, final int z) {
        return this.world.getTileEntity(x, y, z);
    }
    
    public String getName() {
        return this.world.worldData.getName();
    }
    
    @Deprecated
    public long getId() {
        return this.world.worldData.getSeed();
    }
    
    public UUID getUID() {
        return this.world.getDataManager().getUUID();
    }
    
    public String toString() {
        return "CraftWorld{name=" + this.getName() + '}';
    }
    
    public long getTime() {
        long time = this.getFullTime() % 24000L;
        if (time < 0L) {
            time += 24000L;
        }
        return time;
    }
    
    public void setTime(final long time) {
        long margin = (time - this.getFullTime()) % 24000L;
        if (margin < 0L) {
            margin += 24000L;
        }
        this.setFullTime(this.getFullTime() + margin);
    }
    
    public long getFullTime() {
        return this.world.getDayTime();
    }
    
    public void setFullTime(final long time) {
        this.world.setDayTime(time);
        for (final Player p : this.getPlayers()) {
            final CraftPlayer cp = (CraftPlayer)p;
            if (cp.getHandle().playerConnection == null) {
                continue;
            }
            cp.getHandle().playerConnection.sendPacket(new Packet4UpdateTime(cp.getHandle().world.getTime(), cp.getHandle().getPlayerTime()));
        }
    }
    
    public boolean createExplosion(final double x, final double y, final double z, final float power) {
        return this.createExplosion(x, y, z, power, false, true);
    }
    
    public boolean createExplosion(final double x, final double y, final double z, final float power, final boolean setFire) {
        return this.createExplosion(x, y, z, power, setFire, true);
    }
    
    public boolean createExplosion(final double x, final double y, final double z, final float power, final boolean setFire, final boolean breakBlocks) {
        return !this.world.createExplosion(null, x, y, z, power, setFire, breakBlocks).wasCanceled;
    }
    
    public boolean createExplosion(final Location loc, final float power) {
        return this.createExplosion(loc, power, false);
    }
    
    public boolean createExplosion(final Location loc, final float power, final boolean setFire) {
        return this.createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire);
    }
    
    public Environment getEnvironment() {
        return this.environment;
    }
    
    public void setEnvironment(final Environment env) {
        if (this.environment != env) {
            this.environment = env;
            this.world.worldProvider = WorldProvider.byDimension(this.environment.getId());
        }
    }
    
    public Block getBlockAt(final Location location) {
        return this.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
    
    public int getBlockTypeIdAt(final Location location) {
        return this.getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
    
    public int getHighestBlockYAt(final Location location) {
        return this.getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }
    
    public Chunk getChunkAt(final Location location) {
        return this.getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }
    
    public ChunkGenerator getGenerator() {
        return this.generator;
    }
    
    public List<BlockPopulator> getPopulators() {
        return this.populators;
    }
    
    public Block getHighestBlockAt(final int x, final int z) {
        return this.getBlockAt(x, this.getHighestBlockYAt(x, z), z);
    }
    
    public Block getHighestBlockAt(final Location location) {
        return this.getHighestBlockAt(location.getBlockX(), location.getBlockZ());
    }
    
    public Biome getBiome(final int x, final int z) {
        return CraftBlock.biomeBaseToBiome(this.world.getBiome(x, z));
    }
    
    public void setBiome(final int x, final int z, final Biome bio) {
        final BiomeBase bb = CraftBlock.biomeToBiomeBase(bio);
        if (this.world.isLoaded(x, 0, z)) {
            final net.minecraft.server.v1_5_R3.Chunk chunk = this.world.getChunkAtWorldCoords(x, z);
            if (chunk != null) {
                final byte[] biomevals = chunk.m();
                biomevals[(z & 0xF) << 4 | (x & 0xF)] = (byte)bb.id;
            }
        }
    }
    
    public double getTemperature(final int x, final int z) {
        return this.world.getBiome(x, z).temperature;
    }
    
    public double getHumidity(final int x, final int z) {
        return this.world.getBiome(x, z).humidity;
    }
    
    public List<org.bukkit.entity.Entity> getEntities() {
        final List<org.bukkit.entity.Entity> list = new ArrayList<org.bukkit.entity.Entity>();
        for (final Object o : this.world.entityList) {
            if (o instanceof Entity) {
                final Entity mcEnt = (Entity)o;
                final org.bukkit.entity.Entity bukkitEntity = mcEnt.getBukkitEntity();
                if (bukkitEntity == null) {
                    continue;
                }
                list.add(bukkitEntity);
            }
        }
        return list;
    }
    
    public List<LivingEntity> getLivingEntities() {
        final List<LivingEntity> list = new ArrayList<LivingEntity>();
        for (final Object o : this.world.entityList) {
            if (o instanceof Entity) {
                final Entity mcEnt = (Entity)o;
                final org.bukkit.entity.Entity bukkitEntity = mcEnt.getBukkitEntity();
                if (bukkitEntity == null || !(bukkitEntity instanceof LivingEntity)) {
                    continue;
                }
                list.add((LivingEntity)bukkitEntity);
            }
        }
        return list;
    }
    
    @Deprecated
    public <T extends org.bukkit.entity.Entity> Collection<T> getEntitiesByClass(final Class<T>... classes) {
        return (Collection<T>)this.getEntitiesByClasses((Class<?>[])classes);
    }
    
    public <T extends org.bukkit.entity.Entity> Collection<T> getEntitiesByClass(final Class<T> clazz) {
        final Collection<T> list = new ArrayList<T>();
        for (final Object entity : this.world.entityList) {
            if (entity instanceof Entity) {
                final org.bukkit.entity.Entity bukkitEntity = ((Entity)entity).getBukkitEntity();
                if (bukkitEntity == null) {
                    continue;
                }
                final Class<?> bukkitClass = bukkitEntity.getClass();
                if (!clazz.isAssignableFrom(bukkitClass)) {
                    continue;
                }
                list.add((T)bukkitEntity);
            }
        }
        return list;
    }
    
    public Collection<org.bukkit.entity.Entity> getEntitiesByClasses(final Class<?>... classes) {
        final Collection<org.bukkit.entity.Entity> list = new ArrayList<org.bukkit.entity.Entity>();
        for (final Object entity : this.world.entityList) {
            if (entity instanceof Entity) {
                final org.bukkit.entity.Entity bukkitEntity = ((Entity)entity).getBukkitEntity();
                if (bukkitEntity == null) {
                    continue;
                }
                final Class<?> bukkitClass = bukkitEntity.getClass();
                for (final Class<?> clazz : classes) {
                    if (clazz.isAssignableFrom(bukkitClass)) {
                        list.add(bukkitEntity);
                        break;
                    }
                }
            }
        }
        return list;
    }
    
    public List<Player> getPlayers() {
        final List<Player> list = new ArrayList<Player>();
        for (final Object o : this.world.entityList) {
            if (o instanceof Entity) {
                final Entity mcEnt = (Entity)o;
                final org.bukkit.entity.Entity bukkitEntity = mcEnt.getBukkitEntity();
                if (bukkitEntity == null || !(bukkitEntity instanceof Player)) {
                    continue;
                }
                list.add((Player)bukkitEntity);
            }
        }
        return list;
    }
    
    public void save() {
        try {
            final boolean oldSave = this.world.savingDisabled;
            this.world.savingDisabled = false;
            this.world.save(true, null);
            this.world.savingDisabled = oldSave;
        }
        catch (ExceptionWorldConflict ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean isAutoSave() {
        return !this.world.savingDisabled;
    }
    
    public void setAutoSave(final boolean value) {
        this.world.savingDisabled = !value;
    }
    
    public void setDifficulty(final Difficulty difficulty) {
        this.getHandle().difficulty = difficulty.getValue();
    }
    
    public Difficulty getDifficulty() {
        return Difficulty.getByValue(this.getHandle().difficulty);
    }
    
    public BlockMetadataStore getBlockMetadata() {
        return this.blockMetadata;
    }
    
    public boolean hasStorm() {
        return this.world.worldData.hasStorm();
    }
    
    public void setStorm(final boolean hasStorm) {
        final CraftServer server = this.world.getServer();
        final WeatherChangeEvent weather = new WeatherChangeEvent(this, hasStorm);
        server.getPluginManager().callEvent(weather);
        if (!weather.isCancelled()) {
            this.world.worldData.setStorm(hasStorm);
            if (hasStorm) {
                this.setWeatherDuration(CraftWorld.rand.nextInt(12000) + 12000);
            }
            else {
                this.setWeatherDuration(CraftWorld.rand.nextInt(168000) + 12000);
            }
        }
    }
    
    public int getWeatherDuration() {
        return this.world.worldData.getWeatherDuration();
    }
    
    public void setWeatherDuration(final int duration) {
        this.world.worldData.setWeatherDuration(duration);
    }
    
    public boolean isThundering() {
        return this.hasStorm() && this.world.worldData.isThundering();
    }
    
    public void setThundering(final boolean thundering) {
        if (thundering && !this.hasStorm()) {
            this.setStorm(true);
        }
        final CraftServer server = this.world.getServer();
        final ThunderChangeEvent thunder = new ThunderChangeEvent(this, thundering);
        server.getPluginManager().callEvent(thunder);
        if (!thunder.isCancelled()) {
            this.world.worldData.setThundering(thundering);
            if (thundering) {
                this.setThunderDuration(CraftWorld.rand.nextInt(12000) + 3600);
            }
            else {
                this.setThunderDuration(CraftWorld.rand.nextInt(168000) + 12000);
            }
        }
    }
    
    public int getThunderDuration() {
        return this.world.worldData.getThunderDuration();
    }
    
    public void setThunderDuration(final int duration) {
        this.world.worldData.setThunderDuration(duration);
    }
    
    public long getSeed() {
        return this.world.worldData.getSeed();
    }
    
    public boolean getPVP() {
        return this.world.pvpMode;
    }
    
    public void setPVP(final boolean pvp) {
        this.world.pvpMode = pvp;
    }
    
    public void playEffect(final Player player, final Effect effect, final int data) {
        this.playEffect(player.getLocation(), effect, data, 0);
    }
    
    public void playEffect(final Location location, final Effect effect, final int data) {
        this.playEffect(location, effect, data, 64);
    }
    
    public <T> void playEffect(final Location loc, final Effect effect, final T data) {
        this.playEffect(loc, effect, data, 64);
    }
    
    public <T> void playEffect(final Location loc, final Effect effect, final T data, final int radius) {
        if (data != null) {
            Validate.isTrue(data.getClass().equals(effect.getData()), "Wrong kind of data for this effect!");
        }
        else {
            Validate.isTrue(effect.getData() == null, "Wrong kind of data for this effect!");
        }
        if (data != null && data.getClass().equals(MaterialData.class)) {
            final MaterialData materialData = (MaterialData)data;
            Validate.isTrue(!materialData.getItemType().isBlock(), "Material must be block");
            this.spigot().playEffect(loc, effect, materialData.getItemType().getId(), materialData.getData(), 0.0f, 0.0f, 0.0f, 1.0f, 1, radius);
        }
        else {
            final int datavalue = (data == null) ? 0 : CraftEffect.getDataValue(effect, data);
            this.playEffect(loc, effect, datavalue, radius);
        }
    }
    
    public void playEffect(final Location location, final Effect effect, final int data, final int radius) {
        this.spigot().playEffect(location, effect, data, 0, 0.0f, 0.0f, 0.0f, 1.0f, 1, radius);
    }
    
    public <T extends org.bukkit.entity.Entity> T spawn(final Location location, final Class<T> clazz) throws IllegalArgumentException {
        return this.spawn(location, clazz, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }
    
    public FallingBlock spawnFallingBlock(final Location location, final org.bukkit.Material material, final byte data) throws IllegalArgumentException {
        Validate.notNull(location, "Location cannot be null");
        Validate.notNull(material, "Material cannot be null");
        Validate.isTrue(material.isBlock(), "Material must be a block");
        final double x = location.getBlockX() + 0.5;
        final double y = location.getBlockY() + 0.5;
        final double z = location.getBlockZ() + 0.5;
        final EntityFallingBlock entity = new EntityFallingBlock(this.world, x, y, z, material.getId(), data);
        entity.c = 1;
        this.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (FallingBlock)entity.getBukkitEntity();
    }
    
    public FallingBlock spawnFallingBlock(final Location location, final int blockId, final byte blockData) throws IllegalArgumentException {
        return this.spawnFallingBlock(location, org.bukkit.Material.getMaterial(blockId), blockData);
    }
    
    public <T extends org.bukkit.entity.Entity> T spawn(final Location location, final Class<T> clazz, final CreatureSpawnEvent.SpawnReason reason) throws IllegalArgumentException {
        if (location == null || clazz == null) {
            throw new IllegalArgumentException("Location or entity class cannot be null");
        }
        Entity entity = null;
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        final float pitch = location.getPitch();
        final float yaw = location.getYaw();
        if (Boat.class.isAssignableFrom(clazz)) {
            entity = new EntityBoat(this.world, x, y, z);
        }
        else if (FallingBlock.class.isAssignableFrom(clazz)) {
            x = location.getBlockX();
            y = location.getBlockY();
            z = location.getBlockZ();
            final int type = this.world.getTypeId((int)x, (int)y, (int)z);
            final int data = this.world.getData((int)x, (int)y, (int)z);
            entity = new EntityFallingBlock(this.world, x + 0.5, y + 0.5, z + 0.5, type, data);
        }
        else if (Projectile.class.isAssignableFrom(clazz)) {
            if (Snowball.class.isAssignableFrom(clazz)) {
                entity = new EntitySnowball(this.world, x, y, z);
            }
            else if (Egg.class.isAssignableFrom(clazz)) {
                entity = new EntityEgg(this.world, x, y, z);
            }
            else if (Arrow.class.isAssignableFrom(clazz)) {
                entity = new EntityArrow(this.world);
                entity.setPositionRotation(x, y, z, 0.0f, 0.0f);
            }
            else if (ThrownExpBottle.class.isAssignableFrom(clazz)) {
                entity = new EntityThrownExpBottle(this.world);
                entity.setPositionRotation(x, y, z, 0.0f, 0.0f);
            }
            else if (ThrownPotion.class.isAssignableFrom(clazz)) {
                entity = new EntityPotion(this.world, x, y, z, CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.POTION, 1)));
            }
            else if (Fireball.class.isAssignableFrom(clazz)) {
                if (SmallFireball.class.isAssignableFrom(clazz)) {
                    entity = new EntitySmallFireball(this.world);
                }
                else if (WitherSkull.class.isAssignableFrom(clazz)) {
                    entity = new EntityWitherSkull(this.world);
                }
                else {
                    entity = new EntityLargeFireball(this.world);
                }
                entity.setPositionRotation(x, y, z, yaw, pitch);
                final Vector direction = location.getDirection().multiply(10);
                ((EntityFireball)entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
            }
        }
        else if (Minecart.class.isAssignableFrom(clazz)) {
            if (PoweredMinecart.class.isAssignableFrom(clazz)) {
                entity = new EntityMinecartFurnace(this.world, x, y, z);
            }
            else if (StorageMinecart.class.isAssignableFrom(clazz)) {
                entity = new EntityMinecartChest(this.world, x, y, z);
            }
            else if (ExplosiveMinecart.class.isAssignableFrom(clazz)) {
                entity = new EntityMinecartTNT(this.world, x, y, z);
            }
            else if (HopperMinecart.class.isAssignableFrom(clazz)) {
                entity = new EntityMinecartHopper(this.world, x, y, z);
            }
            else if (SpawnerMinecart.class.isAssignableFrom(clazz)) {
                entity = new EntityMinecartMobSpawner(this.world, x, y, z);
            }
            else {
                entity = new EntityMinecartRideable(this.world, x, y, z);
            }
        }
        else if (EnderSignal.class.isAssignableFrom(clazz)) {
            entity = new EntityEnderSignal(this.world, x, y, z);
        }
        else if (EnderCrystal.class.isAssignableFrom(clazz)) {
            entity = new EntityEnderCrystal(this.world);
            entity.setPositionRotation(x, y, z, 0.0f, 0.0f);
        }
        else if (LivingEntity.class.isAssignableFrom(clazz)) {
            if (Chicken.class.isAssignableFrom(clazz)) {
                entity = new EntityChicken(this.world);
            }
            else if (Cow.class.isAssignableFrom(clazz)) {
                if (MushroomCow.class.isAssignableFrom(clazz)) {
                    entity = new EntityMushroomCow(this.world);
                }
                else {
                    entity = new EntityCow(this.world);
                }
            }
            else if (Golem.class.isAssignableFrom(clazz)) {
                if (Snowman.class.isAssignableFrom(clazz)) {
                    entity = new EntitySnowman(this.world);
                }
                else if (IronGolem.class.isAssignableFrom(clazz)) {
                    entity = new EntityIronGolem(this.world);
                }
            }
            else if (Creeper.class.isAssignableFrom(clazz)) {
                entity = new EntityCreeper(this.world);
            }
            else if (Ghast.class.isAssignableFrom(clazz)) {
                entity = new EntityGhast(this.world);
            }
            else if (Pig.class.isAssignableFrom(clazz)) {
                entity = new EntityPig(this.world);
            }
            else if (!Player.class.isAssignableFrom(clazz)) {
                if (Sheep.class.isAssignableFrom(clazz)) {
                    entity = new EntitySheep(this.world);
                }
                else if (Skeleton.class.isAssignableFrom(clazz)) {
                    entity = new EntitySkeleton(this.world);
                }
                else if (Slime.class.isAssignableFrom(clazz)) {
                    if (MagmaCube.class.isAssignableFrom(clazz)) {
                        entity = new EntityMagmaCube(this.world);
                    }
                    else {
                        entity = new EntitySlime(this.world);
                    }
                }
                else if (Spider.class.isAssignableFrom(clazz)) {
                    if (CaveSpider.class.isAssignableFrom(clazz)) {
                        entity = new EntityCaveSpider(this.world);
                    }
                    else {
                        entity = new EntitySpider(this.world);
                    }
                }
                else if (Squid.class.isAssignableFrom(clazz)) {
                    entity = new EntitySquid(this.world);
                }
                else if (Tameable.class.isAssignableFrom(clazz)) {
                    if (Wolf.class.isAssignableFrom(clazz)) {
                        entity = new EntityWolf(this.world);
                    }
                    else if (Ocelot.class.isAssignableFrom(clazz)) {
                        entity = new EntityOcelot(this.world);
                    }
                }
                else if (PigZombie.class.isAssignableFrom(clazz)) {
                    entity = new EntityPigZombie(this.world);
                }
                else if (Zombie.class.isAssignableFrom(clazz)) {
                    entity = new EntityZombie(this.world);
                }
                else if (Giant.class.isAssignableFrom(clazz)) {
                    entity = new EntityGiantZombie(this.world);
                }
                else if (Silverfish.class.isAssignableFrom(clazz)) {
                    entity = new EntitySilverfish(this.world);
                }
                else if (Enderman.class.isAssignableFrom(clazz)) {
                    entity = new EntityEnderman(this.world);
                }
                else if (Blaze.class.isAssignableFrom(clazz)) {
                    entity = new EntityBlaze(this.world);
                }
                else if (Villager.class.isAssignableFrom(clazz)) {
                    entity = new EntityVillager(this.world);
                }
                else if (Witch.class.isAssignableFrom(clazz)) {
                    entity = new EntityWitch(this.world);
                }
                else if (Wither.class.isAssignableFrom(clazz)) {
                    entity = new EntityWither(this.world);
                }
                else if (ComplexLivingEntity.class.isAssignableFrom(clazz)) {
                    if (EnderDragon.class.isAssignableFrom(clazz)) {
                        entity = new EntityEnderDragon(this.world);
                    }
                }
                else if (Ambient.class.isAssignableFrom(clazz) && Bat.class.isAssignableFrom(clazz)) {
                    entity = new EntityBat(this.world);
                }
            }
            if (entity != null) {
                entity.setLocation(x, y, z, pitch, yaw);
            }
        }
        else if (Hanging.class.isAssignableFrom(clazz)) {
            final Block block = this.getBlockAt(location);
            BlockFace face = BlockFace.SELF;
            if (block.getRelative(BlockFace.EAST).getTypeId() == 0) {
                face = BlockFace.EAST;
            }
            else if (block.getRelative(BlockFace.NORTH).getTypeId() == 0) {
                face = BlockFace.NORTH;
            }
            else if (block.getRelative(BlockFace.WEST).getTypeId() == 0) {
                face = BlockFace.WEST;
            }
            else if (block.getRelative(BlockFace.SOUTH).getTypeId() == 0) {
                face = BlockFace.SOUTH;
            }
            int dir = 0;
            switch (face) {
                default: {
                    dir = 0;
                    break;
                }
                case WEST: {
                    dir = 1;
                    break;
                }
                case NORTH: {
                    dir = 2;
                    break;
                }
                case EAST: {
                    dir = 3;
                    break;
                }
            }
            if (Painting.class.isAssignableFrom(clazz)) {
                entity = new EntityPainting(this.world, (int)x, (int)y, (int)z, dir);
            }
            else if (ItemFrame.class.isAssignableFrom(clazz)) {
                entity = new EntityItemFrame(this.world, (int)x, (int)y, (int)z, dir);
            }
            if (entity != null && !((EntityHanging)entity).survives()) {
                entity = null;
            }
        }
        else if (TNTPrimed.class.isAssignableFrom(clazz)) {
            entity = new EntityTNTPrimed(this.world, x, y, z, null);
        }
        else if (ExperienceOrb.class.isAssignableFrom(clazz)) {
            entity = new EntityExperienceOrb(this.world, x, y, z, 0);
        }
        else if (Weather.class.isAssignableFrom(clazz)) {
            entity = new EntityLightning(this.world, x, y, z);
        }
        else if (!LightningStrike.class.isAssignableFrom(clazz)) {
            if (Fish.class.isAssignableFrom(clazz)) {
                entity = new EntityFishingHook(this.world);
                entity.setLocation(x, y, z, pitch, yaw);
            }
            else if (Firework.class.isAssignableFrom(clazz)) {
                entity = new EntityFireworks(this.world, x, y, z, null);
            }
        }
        if (entity != null) {
            this.world.addEntity(entity, reason);
            return (T)entity.getBukkitEntity();
        }
        throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
    }
    
    public ChunkSnapshot getEmptyChunkSnapshot(final int x, final int z, final boolean includeBiome, final boolean includeBiomeTempRain) {
        return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);
    }
    
    public void setSpawnFlags(final boolean allowMonsters, final boolean allowAnimals) {
        this.world.setSpawnFlags(allowMonsters, allowAnimals);
    }
    
    public boolean getAllowAnimals() {
        return this.world.allowAnimals;
    }
    
    public boolean getAllowMonsters() {
        return this.world.allowMonsters;
    }
    
    public int getMaxHeight() {
        return this.world.getHeight();
    }
    
    public int getSeaLevel() {
        return 64;
    }
    
    public boolean getKeepSpawnInMemory() {
        return this.world.keepSpawnInMemory;
    }
    
    public void setKeepSpawnInMemory(final boolean keepLoaded) {
        this.world.keepSpawnInMemory = keepLoaded;
        final ChunkCoordinates chunkcoordinates = this.world.getSpawn();
        final int chunkCoordX = chunkcoordinates.x >> 4;
        final int chunkCoordZ = chunkcoordinates.z >> 4;
        for (int x = -12; x <= 12; ++x) {
            for (int z = -12; z <= 12; ++z) {
                if (keepLoaded) {
                    this.loadChunk(chunkCoordX + x, chunkCoordZ + z);
                }
                else if (this.isChunkLoaded(chunkCoordX + x, chunkCoordZ + z)) {
                    if (this.getHandle().getChunkAt(chunkCoordX + x, chunkCoordZ + z) instanceof EmptyChunk) {
                        this.unloadChunk(chunkCoordX + x, chunkCoordZ + z, false);
                    }
                    else {
                        this.unloadChunk(chunkCoordX + x, chunkCoordZ + z);
                    }
                }
            }
        }
    }
    
    public int hashCode() {
        return this.getUID().hashCode();
    }
    
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final CraftWorld other = (CraftWorld)obj;
        return this.getUID() == other.getUID();
    }
    
    public File getWorldFolder() {
        return ((WorldNBTStorage)this.world.getDataManager()).getDirectory();
    }
    
    public void sendPluginMessage(final Plugin source, final String channel, final byte[] message) {
        StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
        for (final Player player : this.getPlayers()) {
            player.sendPluginMessage(source, channel, message);
        }
    }
    
    public Set<String> getListeningPluginChannels() {
        final Set<String> result = new HashSet<String>();
        for (final Player player : this.getPlayers()) {
            result.addAll(player.getListeningPluginChannels());
        }
        return result;
    }
    
    public WorldType getWorldType() {
        return WorldType.getByName(this.world.getWorldData().getType().name());
    }
    
    public boolean canGenerateStructures() {
        return this.world.getWorldData().shouldGenerateMapFeatures();
    }
    
    public long getTicksPerAnimalSpawns() {
        return this.world.ticksPerAnimalSpawns;
    }
    
    public void setTicksPerAnimalSpawns(final int ticksPerAnimalSpawns) {
        this.world.ticksPerAnimalSpawns = ticksPerAnimalSpawns;
    }
    
    public long getTicksPerMonsterSpawns() {
        return this.world.ticksPerMonsterSpawns;
    }
    
    public void setTicksPerMonsterSpawns(final int ticksPerMonsterSpawns) {
        this.world.ticksPerMonsterSpawns = ticksPerMonsterSpawns;
    }
    
    public void setMetadata(final String metadataKey, final MetadataValue newMetadataValue) {
        ((MetadataStoreBase<CraftWorld>)this.server.getWorldMetadata()).setMetadata(this, metadataKey, newMetadataValue);
    }
    
    public List<MetadataValue> getMetadata(final String metadataKey) {
        return ((MetadataStoreBase<CraftWorld>)this.server.getWorldMetadata()).getMetadata(this, metadataKey);
    }
    
    public boolean hasMetadata(final String metadataKey) {
        return ((MetadataStoreBase<CraftWorld>)this.server.getWorldMetadata()).hasMetadata(this, metadataKey);
    }
    
    public void removeMetadata(final String metadataKey, final Plugin owningPlugin) {
        ((MetadataStoreBase<CraftWorld>)this.server.getWorldMetadata()).removeMetadata(this, metadataKey, owningPlugin);
    }
    
    public int getMonsterSpawnLimit() {
        if (this.monsterSpawn < 0) {
            return this.server.getMonsterSpawnLimit();
        }
        return this.monsterSpawn;
    }
    
    public void setMonsterSpawnLimit(final int limit) {
        this.monsterSpawn = limit;
    }
    
    public int getAnimalSpawnLimit() {
        if (this.animalSpawn < 0) {
            return this.server.getAnimalSpawnLimit();
        }
        return this.animalSpawn;
    }
    
    public void setAnimalSpawnLimit(final int limit) {
        this.animalSpawn = limit;
    }
    
    public int getWaterAnimalSpawnLimit() {
        if (this.waterAnimalSpawn < 0) {
            return this.server.getWaterAnimalSpawnLimit();
        }
        return this.waterAnimalSpawn;
    }
    
    public void setWaterAnimalSpawnLimit(final int limit) {
        this.waterAnimalSpawn = limit;
    }
    
    public int getAmbientSpawnLimit() {
        if (this.ambientSpawn < 0) {
            return this.server.getAmbientSpawnLimit();
        }
        return this.ambientSpawn;
    }
    
    public void setAmbientSpawnLimit(final int limit) {
        this.ambientSpawn = limit;
    }
    
    public void playSound(final Location loc, final Sound sound, final float volume, final float pitch) {
        if (loc == null || sound == null) {
            return;
        }
        final double x = loc.getX();
        final double y = loc.getY();
        final double z = loc.getZ();
        this.getHandle().makeSound(x, y, z, CraftSound.getSound(sound), volume, pitch);
    }
    
    public String getGameRuleValue(final String rule) {
        return this.getHandle().getGameRules().get(rule);
    }
    
    public boolean setGameRuleValue(final String rule, final String value) {
        if (rule == null || value == null) {
            return false;
        }
        if (!this.isGameRule(rule)) {
            return false;
        }
        this.getHandle().getGameRules().set(rule, value);
        return true;
    }
    
    public String[] getGameRules() {
        return this.getHandle().getGameRules().b();
    }
    
    public boolean isGameRule(final String rule) {
        return this.getHandle().getGameRules().e(rule);
    }
    
    public void processChunkGC() {
        ++this.chunkGCTickCount;
        if (this.chunkLoadCount >= this.server.chunkGCLoadThresh && this.server.chunkGCLoadThresh > 0) {
            this.chunkLoadCount = 0;
        }
        else {
            if (this.chunkGCTickCount < this.server.chunkGCPeriod || this.server.chunkGCPeriod <= 0) {
                return;
            }
            this.chunkGCTickCount = 0;
        }
        final ChunkProviderServer cps = this.world.chunkProviderServer;
        for (final net.minecraft.server.v1_5_R3.Chunk chunk : cps.chunks.values()) {
            if (this.isChunkInUse(chunk.x, chunk.z)) {
                continue;
            }
            if (cps.unloadQueue.contains(chunk.x, chunk.z)) {
                continue;
            }
            cps.queueUnload(chunk.x, chunk.z);
        }
    }
    
    public Spigot spigot() {
        return this.spigot;
    }
    
    static {
        rand = new Random();
    }
}
