// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.metadata.MetadataStoreBase;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.PlayerConnection;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.craftbukkit.v1_5_R3.scoreboard.CraftScoreboard;
import net.minecraft.server.v1_5_R3.Container;
import org.bukkit.inventory.InventoryView;
import java.util.List;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.entity.EntityType;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import com.google.common.collect.ImmutableSet;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import net.minecraft.server.v1_5_R3.Packet250CustomPayload;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.plugin.Plugin;
import org.bukkit.conversations.ConversationCanceller;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.conversations.Conversation;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import java.util.LinkedHashMap;
import net.minecraft.server.v1_5_R3.EntityTracker;
import net.minecraft.server.v1_5_R3.EntityTrackerEntry;
import net.minecraft.server.v1_5_R3.ChunkCoordinates;
import net.minecraft.server.v1_5_R3.Block;
import net.minecraft.server.v1_5_R3.Packet70Bed;
import net.minecraft.server.v1_5_R3.EnumGamemode;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.GameMode;
import net.minecraft.server.v1_5_R3.BanEntry;
import org.bukkit.WeatherType;
import net.minecraft.server.v1_5_R3.Packet200Statistic;
import org.bukkit.Statistic;
import org.bukkit.Achievement;
import net.minecraft.server.v1_5_R3.WorldServer;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.craftbukkit.v1_5_R3.map.RenderData;
import net.minecraft.server.v1_5_R3.Packet131ItemData;
import org.bukkit.craftbukkit.v1_5_R3.map.CraftMapView;
import org.bukkit.map.MapView;
import org.apache.commons.lang.NotImplementedException;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.Packet53BlockChange;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.CraftEffect;
import net.minecraft.server.v1_5_R3.Packet62NamedSoundEffect;
import org.bukkit.craftbukkit.v1_5_R3.CraftSound;
import org.bukkit.Sound;
import org.bukkit.Note;
import org.bukkit.Instrument;
import net.minecraft.server.v1_5_R3.Packet54PlayNoteBlock;
import org.bukkit.command.CommandSender;
import net.minecraft.server.v1_5_R3.Packet6SpawnPosition;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import org.bukkit.OfflinePlayer;
import net.minecraft.server.v1_5_R3.Packet201PlayerInfo;
import net.minecraft.server.v1_5_R3.Packet3Chat;
import java.net.SocketAddress;
import java.util.Iterator;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet63WorldParticles;
import org.bukkit.material.MaterialData;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.Packet61WorldEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import java.net.InetSocketAddress;
import com.google.common.collect.MapMaker;
import java.util.HashSet;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import java.util.Map;
import java.util.Set;
import org.bukkit.craftbukkit.v1_5_R3.conversations.ConversationTracker;
import org.bukkit.craftbukkit.v1_5_R3.CraftOfflinePlayer;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.entity.Player;

@DelegateDeserialization(CraftOfflinePlayer.class)
public class CraftPlayer extends CraftHumanEntity implements Player
{
    private long firstPlayed;
    private long lastPlayed;
    private boolean hasPlayedBefore;
    private final ConversationTracker conversationTracker;
    private final Set<String> channels;
    private final Map<String, Player> hiddenPlayers;
    private int hash;
    private final Spigot spigot;
    
    public CraftPlayer(final CraftServer server, final EntityPlayer entity) {
        super(server, entity);
        this.firstPlayed = 0L;
        this.lastPlayed = 0L;
        this.hasPlayedBefore = false;
        this.conversationTracker = new ConversationTracker();
        this.channels = new HashSet<String>();
        this.hiddenPlayers = (Map<String, Player>)new MapMaker().softValues().makeMap();
        this.hash = 0;
        this.spigot = new Spigot() {
            public InetSocketAddress getRawAddress() {
                return (CraftPlayer.this.getHandle().playerConnection == null) ? null : ((InetSocketAddress)CraftPlayer.this.getHandle().playerConnection.networkManager.getSocket().getRemoteSocketAddress());
            }
            
            public void playEffect(final Location location, final Effect effect, final int id, final int data, final float offsetX, final float offsetY, final float offsetZ, final float speed, final int particleCount, final int radius) {
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
                    if (effect.getData() != null && (effect.getData().equals(Material.class) || effect.getData().equals(MaterialData.class))) {
                        particleFullName.append('_').append(id);
                    }
                    if (effect.getData() != null && effect.getData().equals(MaterialData.class)) {
                        particleFullName.append('_').append(data);
                    }
                    packet = new Packet63WorldParticles(effect.getName(), (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, particleCount, radius);
                }
                if (!location.getWorld().equals(CraftPlayer.this.getWorld())) {
                    return;
                }
                CraftPlayer.this.getHandle().playerConnection.sendPacket(packet);
            }
        };
        this.firstPlayed = System.currentTimeMillis();
    }
    
    public boolean isOp() {
        return this.server.getHandle().isOp(this.getName());
    }
    
    public void setOp(final boolean value) {
        if (value == this.isOp()) {
            return;
        }
        if (value) {
            this.server.getHandle().addOp(this.getName());
        }
        else {
            this.server.getHandle().removeOp(this.getName());
        }
        this.perm.recalculatePermissions();
    }
    
    public boolean isOnline() {
        for (final Object obj : this.server.getHandle().players) {
            final EntityPlayer player = (EntityPlayer)obj;
            if (player.name.equalsIgnoreCase(this.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public InetSocketAddress getAddress() {
        if (this.getHandle().playerConnection == null) {
            return null;
        }
        final SocketAddress addr = this.getHandle().playerConnection.networkManager.getSocketAddress();
        if (addr instanceof InetSocketAddress) {
            return (InetSocketAddress)addr;
        }
        return null;
    }
    
    public double getEyeHeight() {
        return this.getEyeHeight(false);
    }
    
    public double getEyeHeight(final boolean ignoreSneaking) {
        if (ignoreSneaking) {
            return 1.62;
        }
        if (this.isSneaking()) {
            return 1.54;
        }
        return 1.62;
    }
    
    public void sendRawMessage(final String message) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        this.getHandle().playerConnection.sendPacket(new Packet3Chat(message));
    }
    
    public void sendMessage(final String message) {
        if (!this.conversationTracker.isConversingModaly()) {
            this.sendRawMessage(message);
        }
    }
    
    public void sendMessage(final String[] messages) {
        for (final String message : messages) {
            this.sendMessage(message);
        }
    }
    
    public String getDisplayName() {
        return this.getHandle().displayName;
    }
    
    public void setDisplayName(final String name) {
        this.getHandle().displayName = name;
    }
    
    public String getPlayerListName() {
        return this.getHandle().listName;
    }
    
    public void setPlayerListName(String name) {
        final String oldName = this.getHandle().listName;
        if (name == null) {
            name = this.getName();
        }
        if (oldName.equals(name)) {
            return;
        }
        if (name.length() > 16) {
            throw new IllegalArgumentException("Player list names can only be a maximum of 16 characters long");
        }
        for (int i = 0; i < this.server.getHandle().players.size(); ++i) {
            if (this.server.getHandle().players.get(i).listName.equals(name)) {
                throw new IllegalArgumentException(name + " is already assigned as a player list name for someone");
            }
        }
        this.getHandle().listName = name;
        final Packet201PlayerInfo oldpacket = new Packet201PlayerInfo(oldName, false, 9999);
        final Packet201PlayerInfo packet = new Packet201PlayerInfo(name, true, this.getHandle().ping);
        for (int j = 0; j < this.server.getHandle().players.size(); ++j) {
            final EntityPlayer entityplayer = this.server.getHandle().players.get(j);
            if (entityplayer.playerConnection != null) {
                if (entityplayer.getBukkitEntity().canSee(this)) {
                    entityplayer.playerConnection.sendPacket(oldpacket);
                    entityplayer.playerConnection.sendPacket(packet);
                }
            }
        }
    }
    
    public boolean equals(final Object obj) {
        if (!(obj instanceof OfflinePlayer)) {
            return false;
        }
        final OfflinePlayer other = (OfflinePlayer)obj;
        if (this.getName() == null || other.getName() == null) {
            return false;
        }
        final boolean nameEquals = this.getName().equalsIgnoreCase(other.getName());
        boolean idEquals = true;
        if (other instanceof CraftPlayer) {
            idEquals = (this.getEntityId() == ((CraftPlayer)other).getEntityId());
        }
        return nameEquals && idEquals;
    }
    
    public void kickPlayer(final String message) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous player kick!");
        }
        if (this.getHandle().playerConnection == null) {
            return;
        }
        this.getHandle().playerConnection.disconnect((message == null) ? "" : message);
    }
    
    public void setCompassTarget(final Location loc) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        this.getHandle().playerConnection.sendPacket(new Packet6SpawnPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
    }
    
    public Location getCompassTarget() {
        return this.getHandle().compassTarget;
    }
    
    public void chat(final String msg) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        this.getHandle().playerConnection.chat(msg, false);
    }
    
    public boolean performCommand(final String command) {
        return this.server.dispatchCommand(this, command);
    }
    
    public void playNote(final Location loc, final byte instrument, final byte note) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        final int id = this.getHandle().world.getTypeId(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        this.getHandle().playerConnection.sendPacket(new Packet54PlayNoteBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), id, instrument, note));
    }
    
    public void playNote(final Location loc, final Instrument instrument, final Note note) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        final int id = this.getHandle().world.getTypeId(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        this.getHandle().playerConnection.sendPacket(new Packet54PlayNoteBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), id, instrument.getType(), note.getId()));
    }
    
    public void playSound(final Location loc, final Sound sound, final float volume, final float pitch) {
        if (loc == null || sound == null || this.getHandle().playerConnection == null) {
            return;
        }
        final double x = loc.getBlockX() + 0.5;
        final double y = loc.getBlockY() + 0.5;
        final double z = loc.getBlockZ() + 0.5;
        final Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect(CraftSound.getSound(sound), x, y, z, volume, pitch);
        this.getHandle().playerConnection.sendPacket(packet);
    }
    
    public void playEffect(final Location location, final Effect effect, final int data) {
        this.spigot().playEffect(location, effect, data, 0, 0.0f, 0.0f, 0.0f, 1.0f, 1, 64);
    }
    
    public <T> void playEffect(final Location loc, final Effect effect, final T data) {
        if (data != null) {
            Validate.isTrue(data.getClass().equals(effect.getData()), "Wrong kind of data for this effect!");
        }
        else {
            Validate.isTrue(effect.getData() == null, "Wrong kind of data for this effect!");
        }
        if (data != null && data.getClass().equals(MaterialData.class)) {
            final MaterialData materialData = (MaterialData)data;
            Validate.isTrue(!materialData.getItemType().isBlock(), "Material must be block");
            this.spigot().playEffect(loc, effect, materialData.getItemType().getId(), materialData.getData(), 0.0f, 0.0f, 0.0f, 1.0f, 1, 64);
        }
        else {
            final int datavalue = (data == null) ? 0 : CraftEffect.getDataValue(effect, data);
            this.playEffect(loc, effect, datavalue);
        }
    }
    
    public void sendBlockChange(final Location loc, final Material material, final byte data) {
        this.sendBlockChange(loc, material.getId(), data);
    }
    
    public void sendBlockChange(final Location loc, final int material, final byte data) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        final Packet53BlockChange packet = new Packet53BlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), ((CraftWorld)loc.getWorld()).getHandle());
        packet.material = material;
        packet.data = data;
        this.getHandle().playerConnection.sendPacket(packet);
    }
    
    public boolean sendChunkChange(final Location loc, final int sx, final int sy, final int sz, final byte[] data) {
        if (this.getHandle().playerConnection == null) {
            return false;
        }
        throw new NotImplementedException("Chunk changes do not yet work");
    }
    
    public void sendMap(final MapView map) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        final RenderData data = ((CraftMapView)map).render(this);
        for (int x = 0; x < 128; ++x) {
            final byte[] bytes = new byte[131];
            bytes[1] = (byte)x;
            for (int y = 0; y < 128; ++y) {
                bytes[y + 3] = data.buffer[y * 128 + x];
            }
            final Packet131ItemData packet = new Packet131ItemData((short)Material.MAP.getId(), map.getId(), bytes);
            this.getHandle().playerConnection.sendPacket(packet);
        }
    }
    
    public boolean teleport(final Location location, final PlayerTeleportEvent.TeleportCause cause) {
        final EntityPlayer entity = this.getHandle();
        if (this.getHealth() == 0 || entity.dead) {
            return false;
        }
        if (entity.playerConnection == null || entity.playerConnection.disconnected) {
            return false;
        }
        if (entity.vehicle != null || entity.passenger != null) {
            return false;
        }
        Location from = this.getLocation();
        final PlayerTeleportEvent event = new PlayerTeleportEvent(this, from, location, cause);
        this.server.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        from = event.getFrom();
        final Location to = event.getTo();
        final WorldServer fromWorld = ((CraftWorld)from.getWorld()).getHandle();
        final WorldServer toWorld = ((CraftWorld)to.getWorld()).getHandle();
        if (this.getHandle().activeContainer != this.getHandle().defaultContainer) {
            this.getHandle().closeInventory();
        }
        if (fromWorld == toWorld) {
            entity.playerConnection.teleport(to);
        }
        else {
            this.server.getHandle().moveToWorld(entity, toWorld.dimension, true, to, true);
        }
        return true;
    }
    
    public void setSneaking(final boolean sneak) {
        this.getHandle().setSneaking(sneak);
    }
    
    public boolean isSneaking() {
        return this.getHandle().isSneaking();
    }
    
    public boolean isSprinting() {
        return this.getHandle().isSprinting();
    }
    
    public void setSprinting(final boolean sprinting) {
        this.getHandle().setSprinting(sprinting);
    }
    
    public void loadData() {
        this.server.getHandle().playerFileData.load(this.getHandle());
    }
    
    public void saveData() {
        this.server.getHandle().playerFileData.save(this.getHandle());
    }
    
    @Deprecated
    public void updateInventory() {
        this.getHandle().updateInventory(this.getHandle().activeContainer);
    }
    
    public void setSleepingIgnored(final boolean isSleeping) {
        this.getHandle().fauxSleeping = isSleeping;
        ((CraftWorld)this.getWorld()).getHandle().checkSleepStatus();
    }
    
    public boolean isSleepingIgnored() {
        return this.getHandle().fauxSleeping;
    }
    
    public void awardAchievement(final Achievement achievement) {
        this.sendStatistic(achievement.getId(), 1);
    }
    
    public void incrementStatistic(final Statistic statistic) {
        this.incrementStatistic(statistic, 1);
    }
    
    public void incrementStatistic(final Statistic statistic, final int amount) {
        this.sendStatistic(statistic.getId(), amount);
    }
    
    public void incrementStatistic(final Statistic statistic, final Material material) {
        this.incrementStatistic(statistic, material, 1);
    }
    
    public void incrementStatistic(final Statistic statistic, final Material material, final int amount) {
        if (!statistic.isSubstatistic()) {
            throw new IllegalArgumentException("Given statistic is not a substatistic");
        }
        if (statistic.isBlock() != material.isBlock()) {
            throw new IllegalArgumentException("Given material is not valid for this substatistic");
        }
        int mat = material.getId();
        if (!material.isBlock()) {
            mat -= 255;
        }
        this.sendStatistic(statistic.getId() + mat, amount);
    }
    
    private void sendStatistic(final int id, int amount) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        while (amount > 127) {
            this.sendStatistic(id, 127);
            amount -= 127;
        }
        this.getHandle().playerConnection.sendPacket(new Packet200Statistic(id, amount));
    }
    
    public void setPlayerTime(final long time, final boolean relative) {
        this.getHandle().timeOffset = time;
        this.getHandle().relativeTime = relative;
    }
    
    public long getPlayerTimeOffset() {
        return this.getHandle().timeOffset;
    }
    
    public long getPlayerTime() {
        return this.getHandle().getPlayerTime();
    }
    
    public boolean isPlayerTimeRelative() {
        return this.getHandle().relativeTime;
    }
    
    public void resetPlayerTime() {
        this.setPlayerTime(0L, true);
    }
    
    public void setPlayerWeather(final WeatherType type) {
        this.getHandle().setPlayerWeather(type, true);
    }
    
    public WeatherType getPlayerWeather() {
        return this.getHandle().getPlayerWeather();
    }
    
    public void resetPlayerWeather() {
        this.getHandle().resetPlayerWeather();
    }
    
    public boolean isBanned() {
        return this.server.getHandle().getNameBans().isBanned(this.getName().toLowerCase());
    }
    
    public void setBanned(final boolean value) {
        if (value) {
            final BanEntry entry = new BanEntry(this.getName().toLowerCase());
            this.server.getHandle().getNameBans().add(entry);
        }
        else {
            this.server.getHandle().getNameBans().remove(this.getName().toLowerCase());
        }
        this.server.getHandle().getNameBans().save();
    }
    
    public boolean isWhitelisted() {
        return this.server.getHandle().getWhitelisted().contains(this.getName().toLowerCase());
    }
    
    public void setWhitelisted(final boolean value) {
        if (value) {
            this.server.getHandle().addWhitelist(this.getName().toLowerCase());
        }
        else {
            this.server.getHandle().removeWhitelist(this.getName().toLowerCase());
        }
    }
    
    public void setGameMode(final GameMode mode) {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        if (mode == null) {
            throw new IllegalArgumentException("Mode cannot be null");
        }
        if (mode != this.getGameMode()) {
            final PlayerGameModeChangeEvent event = new PlayerGameModeChangeEvent(this, mode);
            this.server.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            this.getHandle().playerInteractManager.setGameMode(EnumGamemode.a(mode.getValue()));
            this.getHandle().playerConnection.sendPacket(new Packet70Bed(3, mode.getValue()));
        }
    }
    
    public GameMode getGameMode() {
        return GameMode.getByValue(this.getHandle().playerInteractManager.getGameMode().a());
    }
    
    public void giveExp(final int exp) {
        this.getHandle().giveExp(exp);
    }
    
    public void giveExpLevels(final int levels) {
        this.getHandle().levelDown(levels);
    }
    
    public float getExp() {
        return this.getHandle().exp;
    }
    
    public void setExp(final float exp) {
        this.getHandle().exp = exp;
        this.getHandle().lastSentExp = -1;
    }
    
    public int getLevel() {
        return this.getHandle().expLevel;
    }
    
    public void setLevel(final int level) {
        this.getHandle().expLevel = level;
        this.getHandle().lastSentExp = -1;
    }
    
    public int getTotalExperience() {
        return this.getHandle().expTotal;
    }
    
    public void setTotalExperience(final int exp) {
        this.getHandle().expTotal = exp;
    }
    
    public float getExhaustion() {
        return this.getHandle().getFoodData().exhaustionLevel;
    }
    
    public void setExhaustion(final float value) {
        this.getHandle().getFoodData().exhaustionLevel = value;
    }
    
    public float getSaturation() {
        return this.getHandle().getFoodData().saturationLevel;
    }
    
    public void setSaturation(final float value) {
        this.getHandle().getFoodData().saturationLevel = value;
    }
    
    public int getFoodLevel() {
        return this.getHandle().getFoodData().foodLevel;
    }
    
    public void setFoodLevel(final int value) {
        this.getHandle().getFoodData().foodLevel = value;
    }
    
    public Location getBedSpawnLocation() {
        final org.bukkit.World world = this.getServer().getWorld(this.getHandle().spawnWorld);
        final ChunkCoordinates bed = this.getHandle().getBed();
        if (world == null || bed == null) {
            return null;
        }
        if (this.getHandle().isRespawnForced()) {
            return new Location(world, bed.x, bed.y, bed.z);
        }
        final int cx = bed.x >> 4;
        final int cz = bed.z >> 4;
        final boolean before = world.isChunkLoaded(cx, cz);
        if (!before) {
            world.loadChunk(cx, cz);
        }
        Location location = null;
        if (world.getBlockTypeIdAt(bed.x, bed.y, bed.z) == Block.BED.id) {
            location = new Location(world, bed.x, bed.y, bed.z);
        }
        if (!before) {
            world.unloadChunk(cx, cz);
        }
        return location;
    }
    
    public void setBedSpawnLocation(final Location location) {
        this.setBedSpawnLocation(location, false);
    }
    
    public void setBedSpawnLocation(final Location location, final boolean override) {
        if (location == null) {
            this.getHandle().setRespawnPosition(null, override);
        }
        else {
            this.getHandle().setRespawnPosition(new ChunkCoordinates(location.getBlockX(), location.getBlockY(), location.getBlockZ()), override);
            this.getHandle().spawnWorld = location.getWorld().getName();
        }
    }
    
    public void hidePlayer(final Player player) {
        Validate.notNull(player, "hidden player cannot be null");
        if (this.getHandle().playerConnection == null) {
            return;
        }
        if (this.equals(player)) {
            return;
        }
        if (this.hiddenPlayers.containsKey(player.getName())) {
            return;
        }
        this.hiddenPlayers.put(player.getName(), player);
        final EntityTracker tracker = ((WorldServer)this.entity.world).tracker;
        final EntityPlayer other = ((CraftPlayer)player).getHandle();
        final EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(other.id);
        if (entry != null) {
            entry.clear(this.getHandle());
        }
        this.getHandle().playerConnection.sendPacket(new Packet201PlayerInfo(player.getPlayerListName(), false, 9999));
    }
    
    public void showPlayer(final Player player) {
        Validate.notNull(player, "shown player cannot be null");
        if (this.getHandle().playerConnection == null) {
            return;
        }
        if (this.equals(player)) {
            return;
        }
        if (!this.hiddenPlayers.containsKey(player.getName())) {
            return;
        }
        this.hiddenPlayers.remove(player.getName());
        final EntityTracker tracker = ((WorldServer)this.entity.world).tracker;
        final EntityPlayer other = ((CraftPlayer)player).getHandle();
        final EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(other.id);
        if (entry != null && !entry.trackedPlayers.contains(this.getHandle())) {
            entry.updatePlayer(this.getHandle());
        }
        this.getHandle().playerConnection.sendPacket(new Packet201PlayerInfo(player.getPlayerListName(), true, this.getHandle().ping));
    }
    
    public boolean canSee(final Player player) {
        return !this.hiddenPlayers.containsKey(player.getName());
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("name", this.getName());
        return result;
    }
    
    public Player getPlayer() {
        return this;
    }
    
    public EntityPlayer getHandle() {
        return (EntityPlayer)this.entity;
    }
    
    public void setHandle(final EntityPlayer entity) {
        super.setHandle(entity);
    }
    
    public String toString() {
        return "CraftPlayer{name=" + this.getName() + '}';
    }
    
    public int hashCode() {
        if (this.hash == 0 || this.hash == 485) {
            this.hash = 485 + ((this.getName() != null) ? this.getName().toLowerCase().hashCode() : 0);
        }
        return this.hash;
    }
    
    public long getFirstPlayed() {
        return this.firstPlayed;
    }
    
    public long getLastPlayed() {
        return this.lastPlayed;
    }
    
    public boolean hasPlayedBefore() {
        return this.hasPlayedBefore;
    }
    
    public void setFirstPlayed(final long firstPlayed) {
        this.firstPlayed = firstPlayed;
    }
    
    public void readExtraData(final NBTTagCompound nbttagcompound) {
        this.hasPlayedBefore = true;
        if (nbttagcompound.hasKey("bukkit")) {
            final NBTTagCompound data = nbttagcompound.getCompound("bukkit");
            if (data.hasKey("firstPlayed")) {
                this.firstPlayed = data.getLong("firstPlayed");
                this.lastPlayed = data.getLong("lastPlayed");
            }
            if (data.hasKey("newExp")) {
                final EntityPlayer handle = this.getHandle();
                handle.newExp = data.getInt("newExp");
                handle.newTotalExp = data.getInt("newTotalExp");
                handle.newLevel = data.getInt("newLevel");
                handle.expToDrop = data.getInt("expToDrop");
                handle.keepLevel = data.getBoolean("keepLevel");
            }
        }
    }
    
    public void setExtraData(final NBTTagCompound nbttagcompound) {
        if (!nbttagcompound.hasKey("bukkit")) {
            nbttagcompound.setCompound("bukkit", new NBTTagCompound());
        }
        final NBTTagCompound data = nbttagcompound.getCompound("bukkit");
        final EntityPlayer handle = this.getHandle();
        data.setInt("newExp", handle.newExp);
        data.setInt("newTotalExp", handle.newTotalExp);
        data.setInt("newLevel", handle.newLevel);
        data.setInt("expToDrop", handle.expToDrop);
        data.setBoolean("keepLevel", handle.keepLevel);
        data.setLong("firstPlayed", this.getFirstPlayed());
        data.setLong("lastPlayed", System.currentTimeMillis());
    }
    
    public boolean beginConversation(final Conversation conversation) {
        return this.conversationTracker.beginConversation(conversation);
    }
    
    public void abandonConversation(final Conversation conversation) {
        this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
    }
    
    public void abandonConversation(final Conversation conversation, final ConversationAbandonedEvent details) {
        this.conversationTracker.abandonConversation(conversation, details);
    }
    
    public void acceptConversationInput(final String input) {
        this.conversationTracker.acceptConversationInput(input);
    }
    
    public boolean isConversing() {
        return this.conversationTracker.isConversing();
    }
    
    public void sendPluginMessage(final Plugin source, final String channel, final byte[] message) {
        StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
        if (this.getHandle().playerConnection == null) {
            return;
        }
        if (this.channels.contains(channel)) {
            final Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.tag = channel;
            packet.length = message.length;
            packet.data = message;
            this.getHandle().playerConnection.sendPacket(packet);
        }
    }
    
    public void setTexturePack(final String url) {
        Validate.notNull(url, "Texture pack URL cannot be null");
        final byte[] message = (url + "\u0000" + org.bukkit.craftbukkit.v1_5_R3.Spigot.textureResolution).getBytes();
        Validate.isTrue(message.length <= 32766, "Texture pack URL is too long");
        this.getHandle().playerConnection.sendPacket(new Packet250CustomPayload("MC|TPack", message));
    }
    
    public void addChannel(final String channel) {
        if (this.channels.add(channel)) {
            this.server.getPluginManager().callEvent(new PlayerRegisterChannelEvent(this, channel));
        }
    }
    
    public void removeChannel(final String channel) {
        if (this.channels.remove(channel)) {
            this.server.getPluginManager().callEvent(new PlayerUnregisterChannelEvent(this, channel));
        }
    }
    
    public Set<String> getListeningPluginChannels() {
        return (Set<String>)ImmutableSet.copyOf((Collection<?>)this.channels);
    }
    
    public void sendSupportedChannels() {
        if (this.getHandle().playerConnection == null) {
            return;
        }
        final Set<String> listening = this.server.getMessenger().getIncomingChannels();
        if (!listening.isEmpty()) {
            final Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.tag = "REGISTER";
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            for (final String channel : listening) {
                try {
                    stream.write(channel.getBytes("UTF8"));
                    stream.write(0);
                }
                catch (IOException ex) {
                    Logger.getLogger(CraftPlayer.class.getName()).log(Level.SEVERE, "Could not send Plugin Channel REGISTER to " + this.getName(), ex);
                }
            }
            packet.data = stream.toByteArray();
            packet.length = packet.data.length;
            this.getHandle().playerConnection.sendPacket(packet);
        }
    }
    
    public EntityType getType() {
        return EntityType.PLAYER;
    }
    
    public void setMetadata(final String metadataKey, final MetadataValue newMetadataValue) {
        ((MetadataStoreBase<CraftPlayer>)this.server.getPlayerMetadata()).setMetadata(this, metadataKey, newMetadataValue);
    }
    
    public List<MetadataValue> getMetadata(final String metadataKey) {
        return ((MetadataStoreBase<CraftPlayer>)this.server.getPlayerMetadata()).getMetadata(this, metadataKey);
    }
    
    public boolean hasMetadata(final String metadataKey) {
        return ((MetadataStoreBase<CraftPlayer>)this.server.getPlayerMetadata()).hasMetadata(this, metadataKey);
    }
    
    public void removeMetadata(final String metadataKey, final Plugin owningPlugin) {
        ((MetadataStoreBase<CraftPlayer>)this.server.getPlayerMetadata()).removeMetadata(this, metadataKey, owningPlugin);
    }
    
    public boolean setWindowProperty(final InventoryView.Property prop, final int value) {
        final Container container = this.getHandle().activeContainer;
        if (container.getBukkitView().getType() != prop.getType()) {
            return false;
        }
        this.getHandle().setContainerData(container, prop.getId(), value);
        return true;
    }
    
    public void disconnect(final String reason) {
        this.conversationTracker.abandonAllConversations();
        this.perm.clearPermissions();
    }
    
    public boolean isFlying() {
        return this.getHandle().abilities.isFlying;
    }
    
    public void setFlying(final boolean value) {
        if (!this.getAllowFlight() && value) {
            throw new IllegalArgumentException("Cannot make player fly if getAllowFlight() is false");
        }
        this.getHandle().abilities.isFlying = value;
        this.getHandle().updateAbilities();
    }
    
    public boolean getAllowFlight() {
        return this.getHandle().abilities.canFly;
    }
    
    public void setAllowFlight(final boolean value) {
        if (this.isFlying() && !value) {
            this.getHandle().abilities.isFlying = false;
        }
        this.getHandle().abilities.canFly = value;
        this.getHandle().updateAbilities();
    }
    
    public int getNoDamageTicks() {
        if (this.getHandle().invulnerableTicks > 0) {
            return Math.max(this.getHandle().invulnerableTicks, this.getHandle().noDamageTicks);
        }
        return this.getHandle().noDamageTicks;
    }
    
    public void setFlySpeed(final float value) {
        this.validateSpeed(value);
        final EntityPlayer player = this.getHandle();
        player.abilities.flySpeed = value / 2.0f;
        player.updateAbilities();
    }
    
    public void setWalkSpeed(final float value) {
        this.validateSpeed(value);
        final EntityPlayer player = this.getHandle();
        player.abilities.walkSpeed = value / 2.0f;
        player.updateAbilities();
    }
    
    public float getFlySpeed() {
        return this.getHandle().abilities.flySpeed * 2.0f;
    }
    
    public float getWalkSpeed() {
        return this.getHandle().abilities.walkSpeed * 2.0f;
    }
    
    private void validateSpeed(final float value) {
        if (value < 0.0f) {
            if (value < -1.0f) {
                throw new IllegalArgumentException(value + " is too low");
            }
        }
        else if (value > 1.0f) {
            throw new IllegalArgumentException(value + " is too high");
        }
    }
    
    public void setMaxHealth(final int amount) {
        super.setMaxHealth(amount);
        this.getHandle().triggerHealthUpdate();
    }
    
    public void resetMaxHealth() {
        super.resetMaxHealth();
        this.getHandle().triggerHealthUpdate();
    }
    
    public CraftScoreboard getScoreboard() {
        return this.server.getScoreboardManager().getPlayerBoard(this);
    }
    
    public void setScoreboard(final Scoreboard scoreboard) {
        Validate.notNull(scoreboard, "Scoreboard cannot be null");
        final PlayerConnection playerConnection = this.getHandle().playerConnection;
        if (playerConnection == null) {
            throw new IllegalStateException("Cannot set scoreboard yet");
        }
        if (playerConnection.disconnected) {
            throw new IllegalStateException("Cannot set scoreboard for invalid CraftPlayer");
        }
        this.server.getScoreboardManager().setPlayerBoard(this, scoreboard);
    }
    
    public Spigot spigot() {
        return this.spigot;
    }
}
