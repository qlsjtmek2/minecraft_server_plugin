// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import java.net.InetAddress;
import net.minecraft.server.v1_5_R3.PendingConnection;
import net.minecraft.server.v1_5_R3.EntityExperienceOrb;
import net.minecraft.server.v1_5_R3.EntityItem;
import net.minecraft.server.v1_5_R3.EntityPainting;
import net.minecraft.server.v1_5_R3.EntityItemFrame;
import net.minecraft.server.v1_5_R3.EntityGhast;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet255KickDisconnect;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import java.io.File;
import net.minecraft.server.v1_5_R3.EntitySheep;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import net.minecraft.server.v1_5_R3.EntityVillager;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityArrow;
import net.minecraft.server.v1_5_R3.Chunk;
import java.util.Iterator;
import net.minecraft.server.v1_5_R3.MathHelper;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.EntityFireworks;
import net.minecraft.server.v1_5_R3.EntityEnderCrystal;
import net.minecraft.server.v1_5_R3.EntityTNTPrimed;
import net.minecraft.server.v1_5_R3.EntityWeather;
import net.minecraft.server.v1_5_R3.EntityFireball;
import net.minecraft.server.v1_5_R3.EntityWither;
import net.minecraft.server.v1_5_R3.EntityComplexPart;
import net.minecraft.server.v1_5_R3.EntityEnderDragon;
import net.minecraft.server.v1_5_R3.EntityProjectile;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityAmbient;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntitySlime;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.Entity;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.spigotmc.WatchdogThread;
import org.spigotmc.RestartCommand;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_5_R3.command.TicksPerSecondCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.command.SimpleCommandMap;
import java.util.regex.Pattern;
import java.util.List;
import org.spigotmc.Metrics;
import net.minecraft.server.v1_5_R3.AxisAlignedBB;

public class Spigot
{
    static AxisAlignedBB maxBB;
    static AxisAlignedBB miscBB;
    static AxisAlignedBB animalBB;
    static AxisAlignedBB monsterBB;
    private static boolean filterIps;
    public static boolean tabPing;
    private static Metrics metrics;
    public static List<String> bungeeIPs;
    public static int textureResolution;
    public static final Pattern validName;
    public static int hopperTransferCooldown;
    public static int hopperCheckCooldown;
    
    public static void initialize(final CraftServer server, final SimpleCommandMap commandMap, final YamlConfiguration configuration) {
        commandMap.register("bukkit", new TicksPerSecondCommand("tps"));
        commandMap.register("restart", new RestartCommand("restart"));
        server.whitelistMessage = configuration.getString("settings.whitelist-message", server.whitelistMessage);
        server.stopMessage = configuration.getString("settings.stop-message", server.stopMessage);
        server.logCommands = configuration.getBoolean("settings.log-commands", true);
        server.commandComplete = configuration.getBoolean("settings.command-complete", true);
        server.spamGuardExclusions = configuration.getStringList("settings.spam-exclusions");
        Spigot.filterIps = configuration.getBoolean("settings.filter-unsafe-ips", false);
        final int configVersion = configuration.getInt("config-version");
        switch (configVersion) {
            case 0: {
                configuration.set("settings.timeout-time", 30);
            }
            case 1: {
                configuration.set("settings.timeout-time", 60);
                break;
            }
        }
        configuration.set("config-version", 2);
        WatchdogThread.doStart(configuration.getInt("settings.timeout-time", 60), configuration.getBoolean("settings.restart-on-crash", false));
        server.orebfuscatorEnabled = configuration.getBoolean("orebfuscator.enable", false);
        server.orebfuscatorEngineMode = configuration.getInt("orebfuscator.engine-mode", 1);
        server.orebfuscatorDisabledWorlds = configuration.getStringList("orebfuscator.disabled-worlds");
        server.orebfuscatorBlocks = configuration.getShortList("orebfuscator.blocks");
        if (server.orebfuscatorEngineMode != 1 && server.orebfuscatorEngineMode != 2) {
            server.orebfuscatorEngineMode = 1;
        }
        if (server.chunkGCPeriod == 0) {
            server.getLogger().severe("[Spigot] You should not disable chunk-gc, unexpected behaviour may occur!");
        }
        Spigot.tabPing = configuration.getBoolean("settings.tab-ping", Spigot.tabPing);
        Spigot.bungeeIPs = configuration.getStringList("settings.bungee-proxies");
        Spigot.textureResolution = configuration.getInt("settings.texture-resolution", Spigot.textureResolution);
        Spigot.hopperTransferCooldown = configuration.getInt("ticks-per.hopper-transfer", Spigot.hopperTransferCooldown);
        Spigot.hopperCheckCooldown = configuration.getInt("ticks-per.hopper-check", Spigot.hopperCheckCooldown);
        if (Spigot.metrics == null) {
            try {
                (Spigot.metrics = new Metrics()).start();
            }
            catch (IOException ex) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not start metrics service", ex);
            }
        }
    }
    
    public static byte initializeEntityActivationType(final Entity entity) {
        if (entity instanceof EntityMonster || entity instanceof EntitySlime) {
            return 1;
        }
        if (entity instanceof EntityCreature || entity instanceof EntityAmbient) {
            return 2;
        }
        return 3;
    }
    
    public static boolean initializeEntityActivationState(final Entity entity, final CraftWorld world) {
        return (entity.activationType == 3 && world.miscEntityActivationRange == 0) || (entity.activationType == 2 && world.animalEntityActivationRange == 0) || (entity.activationType == 1 && world.monsterEntityActivationRange == 0) || entity instanceof EntityHuman || entity instanceof EntityProjectile || entity instanceof EntityEnderDragon || entity instanceof EntityComplexPart || entity instanceof EntityWither || entity instanceof EntityFireball || entity instanceof EntityWeather || entity instanceof EntityTNTPrimed || entity instanceof EntityEnderCrystal || entity instanceof EntityFireworks;
    }
    
    public static void growBB(final AxisAlignedBB target, final AxisAlignedBB source, final int x, final int y, final int z) {
        target.a = source.a - x;
        target.b = source.b - y;
        target.c = source.c - z;
        target.d = source.d + x;
        target.e = source.e + y;
        target.f = source.f + z;
    }
    
    public static void activateEntities(final World world) {
        SpigotTimings.entityActivationCheckTimer.startTiming();
        final int miscActivationRange = world.getWorld().miscEntityActivationRange;
        final int animalActivationRange = world.getWorld().animalEntityActivationRange;
        final int monsterActivationRange = world.getWorld().monsterEntityActivationRange;
        int maxRange = Math.max(monsterActivationRange, animalActivationRange);
        maxRange = Math.max(maxRange, miscActivationRange);
        maxRange = Math.min((world.getWorld().viewDistance << 4) - 8, maxRange);
        for (final Entity player : new ArrayList(world.players)) {
            player.activatedTick = MinecraftServer.currentTick;
            growBB(Spigot.maxBB, player.boundingBox, maxRange, 256, maxRange);
            growBB(Spigot.miscBB, player.boundingBox, miscActivationRange, 256, miscActivationRange);
            growBB(Spigot.animalBB, player.boundingBox, animalActivationRange, 256, animalActivationRange);
            growBB(Spigot.monsterBB, player.boundingBox, monsterActivationRange, 256, monsterActivationRange);
            final int i = MathHelper.floor(Spigot.maxBB.a / 16.0);
            final int j = MathHelper.floor(Spigot.maxBB.d / 16.0);
            final int k = MathHelper.floor(Spigot.maxBB.c / 16.0);
            final int l = MathHelper.floor(Spigot.maxBB.f / 16.0);
            for (int i2 = i; i2 <= j; ++i2) {
                for (int j2 = k; j2 <= l; ++j2) {
                    if (world.getWorld().isChunkLoaded(i2, j2)) {
                        activateChunkEntities(world.getChunkAt(i2, j2));
                    }
                }
            }
        }
        SpigotTimings.entityActivationCheckTimer.stopTiming();
    }
    
    private static void activateChunkEntities(final Chunk chunk) {
        for (final List<Entity> slice : chunk.entitySlices) {
            for (final Entity entity : slice) {
                if (MinecraftServer.currentTick > entity.activatedTick) {
                    if (entity.defaultActivationState) {
                        entity.activatedTick = MinecraftServer.currentTick;
                    }
                    else {
                        switch (entity.activationType) {
                            case 1: {
                                if (Spigot.monsterBB.a(entity.boundingBox)) {
                                    entity.activatedTick = MinecraftServer.currentTick;
                                    continue;
                                }
                                continue;
                            }
                            case 2: {
                                if (Spigot.animalBB.a(entity.boundingBox)) {
                                    entity.activatedTick = MinecraftServer.currentTick;
                                    continue;
                                }
                                continue;
                            }
                            default: {
                                if (Spigot.miscBB.a(entity.boundingBox)) {
                                    entity.activatedTick = MinecraftServer.currentTick;
                                    continue;
                                }
                                continue;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static boolean checkEntityImmunities(final Entity entity) {
        if (entity.inWater || entity.fireTicks > 0) {
            return true;
        }
        if (!(entity instanceof EntityArrow)) {
            if (!entity.onGround || entity.passenger != null || entity.vehicle != null) {
                return true;
            }
        }
        else if (!((EntityArrow)entity).inGround) {
            return true;
        }
        if (entity instanceof EntityLiving) {
            final EntityLiving living = (EntityLiving)entity;
            if (living.attackTicks > 0 || living.hurtTicks > 0 || living.effects.size() > 0) {
                return true;
            }
            if (entity instanceof EntityCreature && ((EntityCreature)entity).target != null) {
                return true;
            }
            if (entity instanceof EntityVillager && ((EntityVillager)entity).n()) {
                return true;
            }
            if (entity instanceof EntityAnimal) {
                final EntityAnimal animal = (EntityAnimal)entity;
                if (animal.isBaby() || animal.r()) {
                    return true;
                }
                if (entity instanceof EntitySheep && ((EntitySheep)entity).isSheared()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean checkIfActive(final Entity entity) {
        SpigotTimings.checkIfActiveTimer.startTiming();
        boolean isActive = entity.activatedTick >= MinecraftServer.currentTick || entity.defaultActivationState;
        if (!isActive) {
            if ((MinecraftServer.currentTick - entity.activatedTick - 1L) % 20L == 0L) {
                if (checkEntityImmunities(entity)) {
                    entity.activatedTick = MinecraftServer.currentTick + 20;
                }
                isActive = true;
            }
        }
        else if (!entity.defaultActivationState && entity.ticksLived % 4 == 0 && !checkEntityImmunities(entity)) {
            isActive = false;
        }
        final int x = MathHelper.floor(entity.locX);
        final int z = MathHelper.floor(entity.locZ);
        if (isActive && !entity.world.areChunksLoaded(x, 0, z, 16)) {
            isActive = false;
        }
        SpigotTimings.checkIfActiveTimer.stopTiming();
        return isActive;
    }
    
    public static void restart() {
        try {
            final String startupScript = MinecraftServer.getServer().server.configuration.getString("settings.restart-script-location", "");
            final File file = new File(startupScript);
            if (file.isFile()) {
                System.out.println("Attempting to restart with " + startupScript);
                for (final EntityPlayer p : MinecraftServer.getServer().getPlayerList().players) {
                    p.playerConnection.networkManager.queue(new Packet255KickDisconnect("Server is restarting"));
                    p.playerConnection.networkManager.d();
                }
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException ex2) {}
                MinecraftServer.getServer().ae().a();
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException ex3) {}
                try {
                    MinecraftServer.getServer().stop();
                }
                catch (Throwable t) {}
                final Thread shutdownHook = new Thread() {
                    public void run() {
                        try {
                            final String os = System.getProperty("os.name").toLowerCase();
                            if (os.contains("win")) {
                                Runtime.getRuntime().exec("cmd /c start " + file.getPath());
                            }
                            else {
                                Runtime.getRuntime().exec(new String[] { "sh", file.getPath() });
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                shutdownHook.setDaemon(true);
                Runtime.getRuntime().addShutdownHook(shutdownHook);
            }
            else {
                System.out.println("Startup script '" + startupScript + "' does not exist! Stopping server.");
            }
            System.exit(0);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getEntityTrackingRange(final Entity entity, final int defaultRange) {
        final CraftWorld world = entity.world.getWorld();
        int range = defaultRange;
        if (entity instanceof EntityPlayer) {
            range = world.playerTrackingRange;
        }
        else if (entity.defaultActivationState || entity instanceof EntityGhast) {
            range = defaultRange;
        }
        else if (entity.activationType == 1) {
            range = world.monsterTrackingRange;
        }
        else if (entity.activationType == 2) {
            range = world.animalTrackingRange;
        }
        else if (entity instanceof EntityItemFrame || entity instanceof EntityPainting || entity instanceof EntityItem || entity instanceof EntityExperienceOrb) {
            range = world.miscTrackingRange;
        }
        return Math.min(world.maxTrackingRange, range);
    }
    
    public static boolean filterIp(final PendingConnection con) {
        if (Spigot.filterIps) {
            try {
                final InetAddress address = con.getSocket().getInetAddress();
                final String ip = address.getHostAddress();
                if (!address.isLoopbackAddress()) {
                    final String[] split = ip.split("\\.");
                    final StringBuilder lookup = new StringBuilder();
                    for (int i = split.length - 1; i >= 0; --i) {
                        lookup.append(split[i]);
                        lookup.append(".");
                    }
                    lookup.append("xbl.spamhaus.org.");
                    if (InetAddress.getByName(lookup.toString()) != null) {
                        con.disconnect("Your IP address (" + ip + ") is flagged as unsafe by spamhaus.org/xbl");
                        return true;
                    }
                }
            }
            catch (Exception ex) {}
        }
        return false;
    }
    
    static {
        Spigot.maxBB = AxisAlignedBB.a(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        Spigot.miscBB = AxisAlignedBB.a(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        Spigot.animalBB = AxisAlignedBB.a(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        Spigot.monsterBB = AxisAlignedBB.a(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        Spigot.tabPing = false;
        Spigot.textureResolution = 16;
        validName = Pattern.compile("^[a-zA-Z0-9_-]{2,16}$");
        Spigot.hopperTransferCooldown = 8;
        Spigot.hopperCheckCooldown = 8;
    }
}
