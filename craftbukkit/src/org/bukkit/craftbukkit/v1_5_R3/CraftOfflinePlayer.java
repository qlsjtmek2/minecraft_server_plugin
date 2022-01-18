// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import org.bukkit.metadata.MetadataStoreBase;
import org.bukkit.plugin.Plugin;
import java.util.List;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.World;
import org.bukkit.Location;
import java.io.File;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import java.util.Iterator;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.server.v1_5_R3.BanEntry;
import org.bukkit.Server;
import net.minecraft.server.v1_5_R3.WorldServer;
import net.minecraft.server.v1_5_R3.WorldNBTStorage;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.OfflinePlayer;

@SerializableAs("Player")
public class CraftOfflinePlayer implements OfflinePlayer, ConfigurationSerializable
{
    private final String name;
    private final CraftServer server;
    private final WorldNBTStorage storage;
    
    protected CraftOfflinePlayer(final CraftServer server, final String name) {
        this.server = server;
        this.name = name;
        this.storage = (WorldNBTStorage)server.console.worlds.get(0).getDataManager();
    }
    
    public boolean isOnline() {
        return this.getPlayer() != null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Server getServer() {
        return this.server;
    }
    
    public boolean isOp() {
        return this.server.getHandle().isOp(this.getName().toLowerCase());
    }
    
    public void setOp(final boolean value) {
        if (value == this.isOp()) {
            return;
        }
        if (value) {
            this.server.getHandle().addOp(this.getName().toLowerCase());
        }
        else {
            this.server.getHandle().removeOp(this.getName().toLowerCase());
        }
    }
    
    public boolean isBanned() {
        return this.server.getHandle().getNameBans().isBanned(this.name.toLowerCase());
    }
    
    public void setBanned(final boolean value) {
        if (value) {
            final BanEntry entry = new BanEntry(this.name.toLowerCase());
            this.server.getHandle().getNameBans().add(entry);
        }
        else {
            this.server.getHandle().getNameBans().remove(this.name.toLowerCase());
        }
        this.server.getHandle().getNameBans().save();
    }
    
    public boolean isWhitelisted() {
        return this.server.getHandle().getWhitelisted().contains(this.name.toLowerCase());
    }
    
    public void setWhitelisted(final boolean value) {
        if (value) {
            this.server.getHandle().addWhitelist(this.name.toLowerCase());
        }
        else {
            this.server.getHandle().removeWhitelist(this.name.toLowerCase());
        }
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("name", this.name);
        return result;
    }
    
    public static OfflinePlayer deserialize(final Map<String, Object> args) {
        return Bukkit.getServer().getOfflinePlayer(args.get("name"));
    }
    
    public String toString() {
        return this.getClass().getSimpleName() + "[name=" + this.name + "]";
    }
    
    public Player getPlayer() {
        for (final Object obj : this.server.getHandle().players) {
            final EntityPlayer player = (EntityPlayer)obj;
            if (player.name.equalsIgnoreCase(this.getName())) {
                return (player.playerConnection != null) ? player.playerConnection.getPlayer() : null;
            }
        }
        return null;
    }
    
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OfflinePlayer)) {
            return false;
        }
        final OfflinePlayer other = (OfflinePlayer)obj;
        return this.getName() != null && other.getName() != null && this.getName().equalsIgnoreCase(other.getName());
    }
    
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + ((this.getName() != null) ? this.getName().toLowerCase().hashCode() : 0);
        return hash;
    }
    
    private NBTTagCompound getData() {
        return this.storage.getPlayerData(this.getName());
    }
    
    private NBTTagCompound getBukkitData() {
        NBTTagCompound result = this.getData();
        if (result != null) {
            if (!result.hasKey("bukkit")) {
                result.setCompound("bukkit", new NBTTagCompound());
            }
            result = result.getCompound("bukkit");
        }
        return result;
    }
    
    private File getDataFile() {
        return new File(this.storage.getPlayerDir(), this.name + ".dat");
    }
    
    public long getFirstPlayed() {
        final Player player = this.getPlayer();
        if (player != null) {
            return player.getFirstPlayed();
        }
        final NBTTagCompound data = this.getBukkitData();
        if (data == null) {
            return 0L;
        }
        if (data.hasKey("firstPlayed")) {
            return data.getLong("firstPlayed");
        }
        final File file = this.getDataFile();
        return file.lastModified();
    }
    
    public long getLastPlayed() {
        final Player player = this.getPlayer();
        if (player != null) {
            return player.getLastPlayed();
        }
        final NBTTagCompound data = this.getBukkitData();
        if (data == null) {
            return 0L;
        }
        if (data.hasKey("lastPlayed")) {
            return data.getLong("lastPlayed");
        }
        final File file = this.getDataFile();
        return file.lastModified();
    }
    
    public boolean hasPlayedBefore() {
        return this.getData() != null;
    }
    
    public Location getBedSpawnLocation() {
        final NBTTagCompound data = this.getData();
        if (data == null) {
            return null;
        }
        if (data.hasKey("SpawnX") && data.hasKey("SpawnY") && data.hasKey("SpawnZ")) {
            String spawnWorld = data.getString("SpawnWorld");
            if (spawnWorld.equals("")) {
                spawnWorld = this.server.getWorlds().get(0).getName();
            }
            return new Location(this.server.getWorld(spawnWorld), data.getInt("SpawnX"), data.getInt("SpawnY"), data.getInt("SpawnZ"));
        }
        return null;
    }
    
    public void setMetadata(final String metadataKey, final MetadataValue metadataValue) {
        ((MetadataStoreBase<CraftOfflinePlayer>)this.server.getPlayerMetadata()).setMetadata(this, metadataKey, metadataValue);
    }
    
    public List<MetadataValue> getMetadata(final String metadataKey) {
        return ((MetadataStoreBase<CraftOfflinePlayer>)this.server.getPlayerMetadata()).getMetadata(this, metadataKey);
    }
    
    public boolean hasMetadata(final String metadataKey) {
        return ((MetadataStoreBase<CraftOfflinePlayer>)this.server.getPlayerMetadata()).hasMetadata(this, metadataKey);
    }
    
    public void removeMetadata(final String metadataKey, final Plugin plugin) {
        ((MetadataStoreBase<CraftOfflinePlayer>)this.server.getPlayerMetadata()).removeMetadata(this, metadataKey, plugin);
    }
}
