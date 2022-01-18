// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.WeatherType;
import java.util.Comparator;
import java.util.Collections;
import java.util.Map;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import org.bukkit.util.Vector;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.craftbukkit.v1_5_R3.CraftTravelAgent;
import org.bukkit.TravelAgent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.Location;
import java.util.ArrayList;
import java.net.SocketAddress;
import org.bukkit.event.player.PlayerLoginEvent;
import java.net.InetSocketAddress;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_5_R3.chunkio.ChunkIOExecutor;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.command.ColouredConsoleSender;
import java.util.LinkedHashSet;
import java.util.HashSet;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import java.util.Set;
import java.util.List;
import java.text.SimpleDateFormat;

public abstract class PlayerList
{
    private static final SimpleDateFormat d;
    private final MinecraftServer server;
    public final List players;
    private final BanList banByName;
    private final BanList banByIP;
    private Set operators;
    private Set whitelist;
    public IPlayerFileData playerFileData;
    public boolean hasWhitelist;
    protected int maxPlayers;
    protected int c;
    private EnumGamemode l;
    private boolean m;
    private int n;
    private CraftServer cserver;
    
    public PlayerList(final MinecraftServer minecraftserver) {
        this.players = new CopyOnWriteArrayList();
        this.banByName = new BanList(new File("banned-players.txt"));
        this.banByIP = new BanList(new File("banned-ips.txt"));
        this.operators = new HashSet();
        this.whitelist = new LinkedHashSet();
        this.n = 0;
        minecraftserver.server = new CraftServer(minecraftserver, this);
        minecraftserver.console = ColouredConsoleSender.getInstance();
        this.cserver = minecraftserver.server;
        this.server = minecraftserver;
        this.banByName.setEnabled(false);
        this.banByIP.setEnabled(false);
        this.maxPlayers = 8;
    }
    
    public void a(final INetworkManager inetworkmanager, final EntityPlayer entityplayer) {
        final NBTTagCompound nbttagcompound = this.a(entityplayer);
        entityplayer.spawnIn(this.server.getWorldServer(entityplayer.dimension));
        entityplayer.playerInteractManager.a((WorldServer)entityplayer.world);
        String s = "local";
        if (inetworkmanager.getSocketAddress() != null) {
            s = inetworkmanager.getSocketAddress().toString();
        }
        this.server.getLogger().info(entityplayer.name + "[" + s + "] logged in with entity id " + entityplayer.id + " at ([" + entityplayer.world.worldData.getName() + "] " + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
        final WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
        final ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
        this.a(entityplayer, null, worldserver);
        final PlayerConnection playerconnection = new PlayerConnection(this.server, inetworkmanager, entityplayer);
        int maxPlayers = this.getMaxPlayers();
        if (maxPlayers > 60) {
            maxPlayers = 60;
        }
        playerconnection.sendPacket(new Packet1Login(entityplayer.id, worldserver.getWorldData().getType(), entityplayer.playerInteractManager.getGameMode(), worldserver.getWorldData().isHardcore(), worldserver.worldProvider.dimension, worldserver.difficulty, worldserver.getHeight(), maxPlayers));
        entityplayer.getBukkitEntity().sendSupportedChannels();
        playerconnection.sendPacket(new Packet6SpawnPosition(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z));
        playerconnection.sendPacket(new Packet202Abilities(entityplayer.abilities));
        playerconnection.sendPacket(new Packet16BlockItemSwitch(entityplayer.inventory.itemInHandIndex));
        this.a((ScoreboardServer)worldserver.getScoreboard(), entityplayer);
        this.b(entityplayer, worldserver);
        this.c(entityplayer);
        playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        this.server.ae().a(playerconnection);
        playerconnection.sendPacket(new Packet4UpdateTime(worldserver.getTime(), worldserver.getDayTime()));
        if (this.server.getTexturePack().length() > 0) {
            entityplayer.a(this.server.getTexturePack(), this.server.S());
        }
        for (final MobEffect mobeffect : entityplayer.getEffects()) {
            playerconnection.sendPacket(new Packet41MobEffect(entityplayer.id, mobeffect));
        }
        entityplayer.syncInventory();
        if (nbttagcompound != null && nbttagcompound.hasKey("Riding")) {
            final Entity entity = EntityTypes.a(nbttagcompound.getCompound("Riding"), worldserver);
            if (entity != null) {
                entity.p = true;
                worldserver.addEntity(entity);
                entityplayer.mount(entity);
                entity.p = false;
            }
        }
    }
    
    public void a(final ScoreboardServer scoreboardserver, final EntityPlayer entityplayer) {
        final HashSet hashset = new HashSet();
        for (final ScoreboardTeam scoreboardteam : scoreboardserver.getTeams()) {
            entityplayer.playerConnection.sendPacket(new Packet209SetScoreboardTeam(scoreboardteam, 0));
        }
        for (int i = 0; i < 3; ++i) {
            final ScoreboardObjective scoreboardobjective = scoreboardserver.getObjectiveForSlot(i);
            if (scoreboardobjective != null && !hashset.contains(scoreboardobjective)) {
                final List list = scoreboardserver.getScoreboardScorePacketsForObjective(scoreboardobjective);
                for (final Packet packet : list) {
                    entityplayer.playerConnection.sendPacket(packet);
                }
                hashset.add(scoreboardobjective);
            }
        }
    }
    
    public void setPlayerFileData(final WorldServer[] aworldserver) {
        if (this.playerFileData != null) {
            return;
        }
        this.playerFileData = aworldserver[0].getDataManager().getPlayerFileData();
    }
    
    public void a(final EntityPlayer entityplayer, final WorldServer worldserver) {
        final WorldServer worldserver2 = entityplayer.o();
        if (worldserver != null) {
            worldserver.getPlayerChunkMap().removePlayer(entityplayer);
        }
        worldserver2.getPlayerChunkMap().addPlayer(entityplayer);
        worldserver2.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
    }
    
    public int a() {
        return PlayerChunkMap.getFurthestViewableBlock(this.o());
    }
    
    public NBTTagCompound a(final EntityPlayer entityplayer) {
        final NBTTagCompound nbttagcompound = this.server.worlds.get(0).getWorldData().i();
        NBTTagCompound nbttagcompound2;
        if (entityplayer.getName().equals(this.server.H()) && nbttagcompound != null) {
            entityplayer.f(nbttagcompound);
            nbttagcompound2 = nbttagcompound;
            System.out.println("loading single player");
        }
        else {
            nbttagcompound2 = this.playerFileData.load(entityplayer);
        }
        return nbttagcompound2;
    }
    
    protected void b(final EntityPlayer entityplayer) {
        this.playerFileData.save(entityplayer);
    }
    
    public void c(final EntityPlayer entityplayer) {
        this.cserver.detectListNameConflict(entityplayer);
        this.players.add(entityplayer);
        final WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
        final PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.name + " joined the game.");
        this.cserver.getPluginManager().callEvent(playerJoinEvent);
        final String joinMessage = playerJoinEvent.getJoinMessage();
        if (joinMessage != null && joinMessage.length() > 0) {
            this.server.getPlayerList().sendAll(new Packet3Chat(joinMessage));
        }
        this.cserver.onPlayerJoin(playerJoinEvent.getPlayer());
        ChunkIOExecutor.adjustPoolSize(this.getPlayerCount());
        if (entityplayer.world == worldserver && !worldserver.players.contains(entityplayer)) {
            worldserver.addEntity(entityplayer);
            this.a(entityplayer, null);
        }
        final Packet201PlayerInfo packet = new Packet201PlayerInfo(entityplayer.listName, true, 1000);
        for (int i = 0; i < this.players.size(); ++i) {
            final EntityPlayer entityplayer2 = this.players.get(i);
            if (entityplayer2.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
                entityplayer2.playerConnection.sendPacket(packet);
            }
        }
        for (int i = 0; i < this.players.size(); ++i) {
            final EntityPlayer entityplayer2 = this.players.get(i);
            if (entityplayer.getBukkitEntity().canSee(entityplayer2.getBukkitEntity())) {
                entityplayer.playerConnection.sendPacket(new Packet201PlayerInfo(entityplayer2.listName, true, entityplayer2.ping));
            }
        }
    }
    
    public void d(final EntityPlayer entityplayer) {
        entityplayer.o().getPlayerChunkMap().movePlayer(entityplayer);
    }
    
    public String disconnect(final EntityPlayer entityplayer) {
        if (entityplayer.playerConnection.disconnected) {
            return null;
        }
        CraftEventFactory.handleInventoryCloseEvent(entityplayer);
        final PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.name + " left the game.");
        this.cserver.getPluginManager().callEvent(playerQuitEvent);
        entityplayer.getBukkitEntity().disconnect(playerQuitEvent.getQuitMessage());
        this.b(entityplayer);
        final WorldServer worldserver = entityplayer.o();
        if (entityplayer.vehicle != null) {
            worldserver.kill(entityplayer.vehicle);
            System.out.println("removing player mount");
        }
        worldserver.kill(entityplayer);
        worldserver.getPlayerChunkMap().removePlayer(entityplayer);
        this.players.remove(entityplayer);
        ChunkIOExecutor.adjustPoolSize(this.getPlayerCount());
        final Packet201PlayerInfo packet = new Packet201PlayerInfo(entityplayer.listName, false, 9999);
        for (int i = 0; i < this.players.size(); ++i) {
            final EntityPlayer entityplayer2 = this.players.get(i);
            if (entityplayer2.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
                entityplayer2.playerConnection.sendPacket(packet);
            }
        }
        this.cserver.getScoreboardManager().removePlayer(entityplayer.getBukkitEntity());
        return playerQuitEvent.getQuitMessage();
    }
    
    public EntityPlayer attemptLogin(final PendingConnection pendingconnection, final String s, final String hostname) {
        final EntityPlayer entity = new EntityPlayer(this.server, this.server.getWorldServer(0), s, this.server.M() ? new DemoPlayerInteractManager(this.server.getWorldServer(0)) : new PlayerInteractManager(this.server.getWorldServer(0)));
        final Player player = entity.getBukkitEntity();
        final PlayerLoginEvent event = new PlayerLoginEvent(player, hostname, ((InetSocketAddress)pendingconnection.networkManager.getSocketAddress()).getAddress(), pendingconnection.getSocket().getInetAddress());
        final SocketAddress socketaddress = pendingconnection.networkManager.getSocketAddress();
        if (this.banByName.isBanned(s)) {
            final BanEntry banentry = this.banByName.getEntries().get(s);
            String s2 = "You are banned from this server!\nReason: " + banentry.getReason();
            if (banentry.getExpires() != null) {
                s2 = s2 + "\nYour ban will be removed on " + PlayerList.d.format(banentry.getExpires());
            }
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s2);
        }
        else if (!this.isWhitelisted(s)) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, this.cserver.whitelistMessage);
        }
        else {
            String s3 = socketaddress.toString();
            s3 = s3.substring(s3.indexOf("/") + 1);
            s3 = s3.substring(0, s3.indexOf(":"));
            if (this.banByIP.isBanned(s3)) {
                final BanEntry banentry2 = this.banByIP.getEntries().get(s3);
                String s4 = "Your IP address is banned from this server!\nReason: " + banentry2.getReason();
                if (banentry2.getExpires() != null) {
                    s4 = s4 + "\nYour ban will be removed on " + PlayerList.d.format(banentry2.getExpires());
                }
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s4);
            }
            else if (this.players.size() >= this.maxPlayers) {
                event.disallow(PlayerLoginEvent.Result.KICK_FULL, "The server is full!");
            }
            else {
                event.disallow(PlayerLoginEvent.Result.ALLOWED, s3);
            }
        }
        this.cserver.getPluginManager().callEvent(event);
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            pendingconnection.disconnect(event.getKickMessage());
            return null;
        }
        return entity;
    }
    
    public EntityPlayer processLogin(final EntityPlayer player) {
        final String s = player.name;
        final ArrayList arraylist = new ArrayList();
        for (int i = 0; i < this.players.size(); ++i) {
            final EntityPlayer entityplayer = this.players.get(i);
            if (entityplayer.name.equalsIgnoreCase(s)) {
                arraylist.add(entityplayer);
            }
        }
        final Iterator iterator = arraylist.iterator();
        while (iterator.hasNext()) {
            final EntityPlayer entityplayer = iterator.next();
            entityplayer.playerConnection.disconnect("You logged in from another location");
        }
        return player;
    }
    
    public EntityPlayer moveToWorld(final EntityPlayer entityplayer, final int i, final boolean flag) {
        return this.moveToWorld(entityplayer, i, flag, null, true);
    }
    
    public EntityPlayer moveToWorld(final EntityPlayer entityplayer, final int i, final boolean flag, Location location, final boolean avoidSuffocation) {
        entityplayer.o().getTracker().untrackPlayer(entityplayer);
        entityplayer.o().getPlayerChunkMap().removePlayer(entityplayer);
        this.players.remove(entityplayer);
        this.server.getWorldServer(entityplayer.dimension).removeEntity(entityplayer);
        ChunkCoordinates chunkcoordinates = entityplayer.getBed();
        final boolean flag2 = entityplayer.isRespawnForced();
        final org.bukkit.World fromWorld = entityplayer.getBukkitEntity().getWorld();
        entityplayer.viewingCredits = false;
        entityplayer.copyTo(entityplayer, flag);
        if (location == null) {
            boolean isBedSpawn = false;
            CraftWorld cworld = (CraftWorld)this.server.server.getWorld(entityplayer.spawnWorld);
            if (cworld != null && chunkcoordinates != null) {
                final ChunkCoordinates chunkcoordinates2 = EntityHuman.getBed(cworld.getHandle(), chunkcoordinates, flag2);
                if (chunkcoordinates2 != null) {
                    isBedSpawn = true;
                    location = new Location(cworld, chunkcoordinates2.x + 0.5, chunkcoordinates2.y, chunkcoordinates2.z + 0.5);
                }
                else {
                    entityplayer.setRespawnPosition(null, true);
                    entityplayer.playerConnection.sendPacket(new Packet70Bed(0, 0));
                }
            }
            if (location == null) {
                cworld = this.server.server.getWorlds().get(0);
                chunkcoordinates = cworld.getHandle().getSpawn();
                location = new Location(cworld, chunkcoordinates.x + 0.5, chunkcoordinates.y, chunkcoordinates.z + 0.5);
            }
            final Player respawnPlayer = this.cserver.getPlayer(entityplayer);
            final PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn);
            this.cserver.getPluginManager().callEvent(respawnEvent);
            location = respawnEvent.getRespawnLocation();
            entityplayer.reset();
        }
        else {
            location.setWorld(this.server.getWorldServer(i).getWorld());
        }
        final WorldServer worldserver = ((CraftWorld)location.getWorld()).getHandle();
        entityplayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        worldserver.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
        while (avoidSuffocation && !worldserver.getCubes(entityplayer, entityplayer.boundingBox).isEmpty()) {
            entityplayer.setPosition(entityplayer.locX, entityplayer.locY + 1.0, entityplayer.locZ);
        }
        final byte actualDimension = (byte)worldserver.getWorld().getEnvironment().getId();
        entityplayer.playerConnection.sendPacket(new Packet9Respawn((byte)((actualDimension >= 0) ? -1 : 0), (byte)worldserver.difficulty, worldserver.getWorldData().getType(), worldserver.getHeight(), entityplayer.playerInteractManager.getGameMode()));
        entityplayer.playerConnection.sendPacket(new Packet9Respawn(actualDimension, (byte)worldserver.difficulty, worldserver.getWorldData().getType(), worldserver.getHeight(), entityplayer.playerInteractManager.getGameMode()));
        entityplayer.spawnIn(worldserver);
        entityplayer.dead = false;
        entityplayer.playerConnection.teleport(new Location(worldserver.getWorld(), entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch));
        entityplayer.setSneaking(false);
        final ChunkCoordinates chunkcoordinates2 = worldserver.getSpawn();
        entityplayer.playerConnection.sendPacket(new Packet6SpawnPosition(chunkcoordinates2.x, chunkcoordinates2.y, chunkcoordinates2.z));
        entityplayer.playerConnection.sendPacket(new Packet43SetExperience(entityplayer.exp, entityplayer.expTotal, entityplayer.expLevel));
        this.b(entityplayer, worldserver);
        worldserver.getPlayerChunkMap().addPlayer(entityplayer);
        worldserver.addEntity(entityplayer);
        this.players.add(entityplayer);
        this.updateClient(entityplayer);
        entityplayer.updateAbilities();
        for (final MobEffect mobeffect : entityplayer.getEffects()) {
            entityplayer.playerConnection.sendPacket(new Packet41MobEffect(entityplayer.id, mobeffect));
        }
        entityplayer.setHealth(entityplayer.getHealth());
        if (fromWorld != location.getWorld()) {
            final PlayerChangedWorldEvent event = new PlayerChangedWorldEvent(entityplayer.getBukkitEntity(), fromWorld);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        return entityplayer;
    }
    
    public void changeDimension(final EntityPlayer entityplayer, final int i, final PlayerTeleportEvent.TeleportCause cause) {
        WorldServer exitWorld = null;
        if (entityplayer.dimension < 10) {
            for (final WorldServer world : this.server.worlds) {
                if (world.dimension == i) {
                    exitWorld = world;
                }
            }
        }
        final Location enter = entityplayer.getBukkitEntity().getLocation();
        Location exit = null;
        boolean useTravelAgent = false;
        if (exitWorld != null) {
            if (cause == PlayerTeleportEvent.TeleportCause.END_PORTAL && i == 0) {
                final ChunkCoordinates chunkcoordinates = entityplayer.getBed();
                final CraftWorld spawnWorld = (CraftWorld)this.server.server.getWorld(entityplayer.spawnWorld);
                if (spawnWorld != null && chunkcoordinates != null) {
                    final ChunkCoordinates chunkcoordinates2 = EntityHuman.getBed(spawnWorld.getHandle(), chunkcoordinates, entityplayer.isRespawnForced());
                    if (chunkcoordinates2 != null) {
                        exit = new Location(spawnWorld, chunkcoordinates2.x + 0.5, chunkcoordinates2.y, chunkcoordinates2.z + 0.5);
                    }
                }
                if (exit == null || ((CraftWorld)exit.getWorld()).getHandle().dimension != 0) {
                    exit = exitWorld.getWorld().getSpawnLocation();
                }
            }
            else {
                exit = this.calculateTarget(enter, exitWorld);
                useTravelAgent = true;
            }
        }
        final TravelAgent agent = (TravelAgent)((exit != null) ? ((CraftWorld)exit.getWorld()).getHandle().t() : CraftTravelAgent.DEFAULT);
        final PlayerPortalEvent event = new PlayerPortalEvent(entityplayer.getBukkitEntity(), enter, exit, agent, cause);
        event.useTravelAgent(useTravelAgent);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled() || event.getTo() == null) {
            return;
        }
        exit = (event.useTravelAgent() ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo());
        if (exit == null) {
            return;
        }
        exitWorld = ((CraftWorld)exit.getWorld()).getHandle();
        final Vector velocity = entityplayer.getBukkitEntity().getVelocity();
        final boolean before = exitWorld.chunkProviderServer.forceChunkLoad;
        exitWorld.chunkProviderServer.forceChunkLoad = true;
        exitWorld.t().adjustExit(entityplayer, exit, velocity);
        exitWorld.chunkProviderServer.forceChunkLoad = before;
        this.moveToWorld(entityplayer, exitWorld.dimension, true, exit, false);
        if (entityplayer.motX != velocity.getX() || entityplayer.motY != velocity.getY() || entityplayer.motZ != velocity.getZ()) {
            entityplayer.getBukkitEntity().setVelocity(velocity);
        }
    }
    
    public void a(final Entity entity, final int i, final WorldServer worldserver, final WorldServer worldserver1) {
        final Location exit = this.calculateTarget(entity.getBukkitEntity().getLocation(), worldserver1);
        this.repositionEntity(entity, exit, true);
    }
    
    public Location calculateTarget(final Location enter, final World target) {
        final WorldServer worldserver = ((CraftWorld)enter.getWorld()).getHandle();
        WorldServer worldserver2 = target.getWorld().getHandle();
        final int i = worldserver.dimension;
        double y = enter.getY();
        float yaw = enter.getYaw();
        float pitch = enter.getPitch();
        double d0 = enter.getX();
        double d2 = enter.getZ();
        final double d3 = 8.0;
        if (worldserver2.dimension == -1) {
            d0 /= d3;
            d2 /= d3;
        }
        else if (worldserver2.dimension == 0) {
            d0 *= d3;
            d2 *= d3;
        }
        else {
            ChunkCoordinates chunkcoordinates;
            if (i == 1) {
                worldserver2 = this.server.worlds.get(0);
                chunkcoordinates = worldserver2.getSpawn();
            }
            else {
                chunkcoordinates = worldserver2.getDimensionSpawn();
            }
            d0 = chunkcoordinates.x;
            y = chunkcoordinates.y;
            d2 = chunkcoordinates.z;
            yaw = 90.0f;
            pitch = 0.0f;
        }
        if (i != 1) {
            d0 = MathHelper.a((int)d0, -29999872, 29999872);
            d2 = MathHelper.a((int)d2, -29999872, 29999872);
        }
        return new Location(worldserver2.getWorld(), d0, y, d2, yaw, pitch);
    }
    
    public void repositionEntity(final Entity entity, final Location exit, final boolean portal) {
        final int i = entity.dimension;
        final WorldServer worldserver = (WorldServer)entity.world;
        final WorldServer worldserver2 = ((CraftWorld)exit.getWorld()).getHandle();
        worldserver.methodProfiler.a("moving");
        entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
        if (entity.isAlive()) {
            worldserver.entityJoinedWorld(entity, false);
        }
        worldserver.methodProfiler.b();
        if (i != 1) {
            worldserver.methodProfiler.a("placing");
            if (entity.isAlive()) {
                worldserver2.addEntity(entity);
                worldserver2.entityJoinedWorld(entity, false);
                if (portal) {
                    final Vector velocity = entity.getBukkitEntity().getVelocity();
                    worldserver2.t().adjustExit(entity, exit, velocity);
                    entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
                    if (entity.motX != velocity.getX() || entity.motY != velocity.getY() || entity.motZ != velocity.getZ()) {
                        entity.getBukkitEntity().setVelocity(velocity);
                    }
                }
            }
            worldserver.methodProfiler.b();
        }
        entity.spawnIn(worldserver2);
    }
    
    public void tick() {
        if (++this.n > 600) {
            this.n = 0;
        }
        if (this.players.size() == 0 || !Spigot.tabPing) {
            return;
        }
        final int index = MinecraftServer.currentTick % this.players.size();
        final EntityPlayer player = this.players.get(index);
        if (player.lastPing == -1 || Math.abs(player.ping - player.lastPing) > 20) {
            final Packet packet = new Packet201PlayerInfo(player.listName, true, player.ping);
            for (final EntityPlayer splayer : this.players) {
                if (splayer.getBukkitEntity().canSee(player.getBukkitEntity())) {
                    splayer.playerConnection.sendPacket(packet);
                }
            }
            player.lastPing = player.ping;
        }
    }
    
    public void sendAll(final Packet packet) {
        for (int i = 0; i < this.players.size(); ++i) {
            this.players.get(i).playerConnection.sendPacket(packet);
        }
    }
    
    public void a(final Packet packet, final int i) {
        for (int j = 0; j < this.players.size(); ++j) {
            final EntityPlayer entityplayer = this.players.get(j);
            if (entityplayer.dimension == i) {
                entityplayer.playerConnection.sendPacket(packet);
            }
        }
    }
    
    public String c() {
        String s = "";
        for (int i = 0; i < this.players.size(); ++i) {
            if (i > 0) {
                s += ", ";
            }
            s += this.players.get(i).name;
        }
        return s;
    }
    
    public String[] d() {
        final String[] astring = new String[this.players.size()];
        for (int i = 0; i < this.players.size(); ++i) {
            astring[i] = this.players.get(i).name;
        }
        return astring;
    }
    
    public BanList getNameBans() {
        return this.banByName;
    }
    
    public BanList getIPBans() {
        return this.banByIP;
    }
    
    public void addOp(final String s) {
        this.operators.add(s.toLowerCase());
        final Player player = this.server.server.getPlayer(s);
        if (player != null) {
            player.recalculatePermissions();
        }
    }
    
    public void removeOp(final String s) {
        this.operators.remove(s.toLowerCase());
        final Player player = this.server.server.getPlayer(s);
        if (player != null) {
            player.recalculatePermissions();
        }
    }
    
    public boolean isWhitelisted(String s) {
        s = s.trim().toLowerCase();
        return !this.hasWhitelist || this.operators.contains(s) || this.whitelist.contains(s);
    }
    
    public boolean isOp(final String s) {
        return this.operators.contains(s.trim().toLowerCase()) || (this.server.I() && this.server.worlds.get(0).getWorldData().allowCommands() && this.server.H().equalsIgnoreCase(s)) || this.m;
    }
    
    public EntityPlayer getPlayer(final String s) {
        for (final EntityPlayer entityplayer : this.players) {
            if (entityplayer.name.equalsIgnoreCase(s)) {
                return entityplayer;
            }
        }
        return null;
    }
    
    public List a(final ChunkCoordinates chunkcoordinates, final int i, final int j, int k, final int l, final int i1, final int j1, final Map map, String s, String s1) {
        if (this.players.isEmpty()) {
            return null;
        }
        Object object = new ArrayList();
        final boolean flag = k < 0;
        final int k2 = i * i;
        final int l2 = j * j;
        k = MathHelper.a(k);
        for (int i2 = 0; i2 < this.players.size(); ++i2) {
            final EntityPlayer entityplayer = this.players.get(i2);
            if (s != null) {
                final boolean flag2 = s.startsWith("!");
                if (flag2) {
                    s = s.substring(1);
                }
                if (flag2 == s.equalsIgnoreCase(entityplayer.getLocalizedName())) {
                    continue;
                }
            }
            if (s1 != null) {
                final boolean flag2 = s1.startsWith("!");
                if (flag2) {
                    s1 = s1.substring(1);
                }
                final ScoreboardTeam scoreboardteam = entityplayer.getScoreboardTeam();
                final String s2 = (scoreboardteam == null) ? "" : scoreboardteam.getName();
                if (flag2 == s1.equalsIgnoreCase(s2)) {
                    continue;
                }
            }
            if (chunkcoordinates != null && (i > 0 || j > 0)) {
                final float f = chunkcoordinates.e(entityplayer.b());
                if (i > 0 && f < k2) {
                    continue;
                }
                if (j > 0 && f > l2) {
                    continue;
                }
            }
            if (this.a(entityplayer, map) && (l == EnumGamemode.NONE.a() || l == entityplayer.playerInteractManager.getGameMode().a()) && (i1 <= 0 || entityplayer.expLevel >= i1) && entityplayer.expLevel <= j1) {
                ((List)object).add(entityplayer);
            }
        }
        if (chunkcoordinates != null) {
            Collections.sort((List<Object>)object, new PlayerDistanceComparator(chunkcoordinates));
        }
        if (flag) {
            Collections.reverse((List<?>)object);
        }
        if (k > 0) {
            object = ((List)object).subList(0, Math.min(k, ((List)object).size()));
        }
        return (List)object;
    }
    
    private boolean a(final EntityHuman entityhuman, final Map map) {
        if (map != null && map.size() != 0) {
            for (final Map.Entry entry : map.entrySet()) {
                String s = entry.getKey();
                boolean flag = false;
                if (s.endsWith("_min") && s.length() > 4) {
                    flag = true;
                    s = s.substring(0, s.length() - 4);
                }
                final Scoreboard scoreboard = entityhuman.getScoreboard();
                final ScoreboardObjective scoreboardobjective = scoreboard.getObjective(s);
                if (scoreboardobjective == null) {
                    return false;
                }
                final ScoreboardScore scoreboardscore = entityhuman.getScoreboard().getPlayerScoreForObjective(entityhuman.getLocalizedName(), scoreboardobjective);
                final int i = scoreboardscore.getScore();
                if (i < entry.getValue() && flag) {
                    return false;
                }
                if (i > entry.getValue() && !flag) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    
    public void sendPacketNearby(final double d0, final double d1, final double d2, final double d3, final int i, final Packet packet) {
        this.sendPacketNearby(null, d0, d1, d2, d3, i, packet);
    }
    
    public void sendPacketNearby(final EntityHuman entityhuman, final double d0, final double d1, final double d2, final double d3, final int i, final Packet packet) {
        for (int j = 0; j < this.players.size(); ++j) {
            final EntityPlayer entityplayer = this.players.get(j);
            if (entityhuman == null || !(entityhuman instanceof EntityPlayer) || entityplayer.getBukkitEntity().canSee(((EntityPlayer)entityhuman).getBukkitEntity())) {
                if (entityplayer != entityhuman && entityplayer.dimension == i) {
                    final double d4 = d0 - entityplayer.locX;
                    final double d5 = d1 - entityplayer.locY;
                    final double d6 = d2 - entityplayer.locZ;
                    if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
                        entityplayer.playerConnection.sendPacket(packet);
                    }
                }
            }
        }
    }
    
    public void savePlayers() {
        for (int i = 0; i < this.players.size(); ++i) {
            this.b(this.players.get(i));
        }
    }
    
    public void addWhitelist(final String s) {
        this.whitelist.add(s);
    }
    
    public void removeWhitelist(final String s) {
        this.whitelist.remove(s);
    }
    
    public Set getWhitelisted() {
        return this.whitelist;
    }
    
    public Set getOPs() {
        return this.operators;
    }
    
    public void reloadWhitelist() {
    }
    
    public void b(final EntityPlayer entityplayer, final WorldServer worldserver) {
        entityplayer.playerConnection.sendPacket(new Packet4UpdateTime(worldserver.getTime(), worldserver.getDayTime()));
        if (worldserver.P()) {
            entityplayer.setPlayerWeather(WeatherType.DOWNFALL, false);
        }
    }
    
    public void updateClient(final EntityPlayer entityplayer) {
        entityplayer.updateInventory(entityplayer.defaultContainer);
        entityplayer.triggerHealthUpdate();
        entityplayer.playerConnection.sendPacket(new Packet16BlockItemSwitch(entityplayer.inventory.itemInHandIndex));
    }
    
    public int getPlayerCount() {
        return this.players.size();
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public String[] getSeenPlayers() {
        return this.server.worlds.get(0).getDataManager().getPlayerFileData().getSeenPlayers();
    }
    
    public boolean getHasWhitelist() {
        return this.hasWhitelist;
    }
    
    public void setHasWhitelist(final boolean flag) {
        this.hasWhitelist = flag;
    }
    
    public List j(final String s) {
        final ArrayList arraylist = new ArrayList();
        for (final EntityPlayer entityplayer : this.players) {
            if (entityplayer.p().equals(s)) {
                arraylist.add(entityplayer);
            }
        }
        return arraylist;
    }
    
    public int o() {
        return this.c;
    }
    
    public MinecraftServer getServer() {
        return this.server;
    }
    
    public NBTTagCompound q() {
        return null;
    }
    
    private void a(final EntityPlayer entityplayer, final EntityPlayer entityplayer1, final World world) {
        if (entityplayer1 != null) {
            entityplayer.playerInteractManager.setGameMode(entityplayer1.playerInteractManager.getGameMode());
        }
        else if (this.l != null) {
            entityplayer.playerInteractManager.setGameMode(this.l);
        }
        entityplayer.playerInteractManager.b(world.getWorldData().getGameType());
    }
    
    public void r() {
        while (!this.players.isEmpty()) {
            final EntityPlayer p = this.players.get(0);
            p.playerConnection.disconnect(this.server.server.getShutdownMessage());
            if (!this.players.isEmpty() && this.players.get(0) == p) {
                this.players.remove(0);
            }
        }
    }
    
    public void k(final String s) {
        this.server.info(s);
        this.sendAll(new Packet3Chat(s));
    }
    
    static {
        d = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }
}
