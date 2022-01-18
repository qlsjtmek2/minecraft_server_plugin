// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.TileEntity;
import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.scheduler.BukkitTask;
import java.util.HashMap;
import org.spigotmc.CustomTimingsHandler;

public class SpigotTimings
{
    public static final CustomTimingsHandler serverTickTimer;
    public static final CustomTimingsHandler playerListTimer;
    public static final CustomTimingsHandler connectionTimer;
    public static final CustomTimingsHandler tickablesTimer;
    public static final CustomTimingsHandler schedulerTimer;
    public static final CustomTimingsHandler chunkIOTickTimer;
    public static final CustomTimingsHandler syncChunkLoadTimer;
    public static final CustomTimingsHandler entityMoveTimer;
    public static final CustomTimingsHandler tickEntityTimer;
    public static final CustomTimingsHandler activatedEntityTimer;
    public static final CustomTimingsHandler tickTileEntityTimer;
    public static final CustomTimingsHandler timerEntityBaseTick;
    public static final CustomTimingsHandler timerEntityAI;
    public static final CustomTimingsHandler timerEntityAICollision;
    public static final CustomTimingsHandler timerEntityAIMove;
    public static final CustomTimingsHandler timerEntityTickRest;
    public static final CustomTimingsHandler playerCommandTimer;
    public static final CustomTimingsHandler entityActivationCheckTimer;
    public static final CustomTimingsHandler checkIfActiveTimer;
    public static final HashMap<String, CustomTimingsHandler> entityTypeTimingMap;
    public static final HashMap<String, CustomTimingsHandler> tileEntityTypeTimingMap;
    public static final HashMap<String, CustomTimingsHandler> pluginTaskTimingMap;
    
    public static CustomTimingsHandler getPluginTaskTimings(final BukkitTask task, final long period) {
        final String plugin = task.getOwner().getDescription().getFullName();
        String name = "Task: " + plugin + " Id:";
        if (period > 0L) {
            name = name + "(interval:" + period + ")";
        }
        else {
            name += "(Single)";
        }
        CustomTimingsHandler result = SpigotTimings.pluginTaskTimingMap.get(name);
        if (result == null) {
            result = new CustomTimingsHandler(name);
            SpigotTimings.pluginTaskTimingMap.put(name, result);
        }
        return result;
    }
    
    public static CustomTimingsHandler getEntityTimings(final Entity entity) {
        final String entityType = entity.getClass().getSimpleName();
        CustomTimingsHandler result = SpigotTimings.entityTypeTimingMap.get(entityType);
        if (result == null) {
            result = new CustomTimingsHandler("** tickEntity - " + entityType, SpigotTimings.activatedEntityTimer);
            SpigotTimings.entityTypeTimingMap.put(entityType, result);
        }
        return result;
    }
    
    public static CustomTimingsHandler getTileEntityTimings(final TileEntity entity) {
        final String entityType = entity.getClass().getSimpleName();
        CustomTimingsHandler result = SpigotTimings.tileEntityTypeTimingMap.get(entityType);
        if (result == null) {
            result = new CustomTimingsHandler("** tickTileEntity - " + entityType, SpigotTimings.tickTileEntityTimer);
            SpigotTimings.tileEntityTypeTimingMap.put(entityType, result);
        }
        return result;
    }
    
    static {
        serverTickTimer = new CustomTimingsHandler("** Full Server Tick");
        playerListTimer = new CustomTimingsHandler("Player List");
        connectionTimer = new CustomTimingsHandler("Player Tick");
        tickablesTimer = new CustomTimingsHandler("Tickables");
        schedulerTimer = new CustomTimingsHandler("Scheduler");
        chunkIOTickTimer = new CustomTimingsHandler("ChunkIOTick");
        syncChunkLoadTimer = new CustomTimingsHandler("syncChunkLoad");
        entityMoveTimer = new CustomTimingsHandler("** entityMove");
        tickEntityTimer = new CustomTimingsHandler("** tickEntity");
        activatedEntityTimer = new CustomTimingsHandler("** activatedTickEntity");
        tickTileEntityTimer = new CustomTimingsHandler("** tickTileEntity");
        timerEntityBaseTick = new CustomTimingsHandler("** livingEntityBaseTick");
        timerEntityAI = new CustomTimingsHandler("** livingEntityAI");
        timerEntityAICollision = new CustomTimingsHandler("** livingEntityAICollision");
        timerEntityAIMove = new CustomTimingsHandler("** livingEntityAIMove");
        timerEntityTickRest = new CustomTimingsHandler("** livingEntityTickRest");
        playerCommandTimer = new CustomTimingsHandler("** playerCommand");
        entityActivationCheckTimer = new CustomTimingsHandler("entityActivationCheck");
        checkIfActiveTimer = new CustomTimingsHandler("** checkIfActive");
        entityTypeTimingMap = new HashMap<String, CustomTimingsHandler>();
        tileEntityTypeTimingMap = new HashMap<String, CustomTimingsHandler>();
        pluginTaskTimingMap = new HashMap<String, CustomTimingsHandler>();
    }
    
    public static class WorldTimingsHandler
    {
        public final CustomTimingsHandler mobSpawn;
        public final CustomTimingsHandler doChunkUnload;
        public final CustomTimingsHandler doPortalForcer;
        public final CustomTimingsHandler doTickPending;
        public final CustomTimingsHandler doTickTiles;
        public final CustomTimingsHandler doVillages;
        public final CustomTimingsHandler doChunkMap;
        public final CustomTimingsHandler doChunkGC;
        public final CustomTimingsHandler doSounds;
        public final CustomTimingsHandler entityTick;
        public final CustomTimingsHandler tileEntityTick;
        public final CustomTimingsHandler tileEntityPending;
        public final CustomTimingsHandler tracker;
        
        public WorldTimingsHandler(final World server) {
            final String name = server.worldData.getName() + " - ";
            this.mobSpawn = new CustomTimingsHandler(name + "mobSpawn");
            this.doChunkUnload = new CustomTimingsHandler(name + "doChunkUnload");
            this.doTickPending = new CustomTimingsHandler(name + "doTickPending");
            this.doTickTiles = new CustomTimingsHandler(name + "doTickTiles");
            this.doVillages = new CustomTimingsHandler(name + "doVillages");
            this.doChunkMap = new CustomTimingsHandler(name + "doChunkMap");
            this.doSounds = new CustomTimingsHandler(name + "doSounds");
            this.doChunkGC = new CustomTimingsHandler(name + "doChunkGC");
            this.doPortalForcer = new CustomTimingsHandler(name + "doPortalForcer");
            this.entityTick = new CustomTimingsHandler(name + "entityTick");
            this.tileEntityTick = new CustomTimingsHandler(name + "tileEntityTick");
            this.tileEntityPending = new CustomTimingsHandler(name + "tileEntityPending");
            this.tracker = new CustomTimingsHandler(name + "tracker");
        }
    }
}
