// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.WeatherType;
import org.bukkit.Statistic;
import org.bukkit.Achievement;
import org.bukkit.map.MapView;
import org.bukkit.Material;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.Note;
import org.bukkit.Instrument;
import java.net.InetSocketAddress;
import org.bukkit.Location;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;

public interface Player extends HumanEntity, Conversable, CommandSender, OfflinePlayer, PluginMessageRecipient
{
    String getDisplayName();
    
    void setDisplayName(final String p0);
    
    String getPlayerListName();
    
    void setPlayerListName(final String p0);
    
    void setCompassTarget(final Location p0);
    
    Location getCompassTarget();
    
    InetSocketAddress getAddress();
    
    void sendRawMessage(final String p0);
    
    void kickPlayer(final String p0);
    
    void chat(final String p0);
    
    boolean performCommand(final String p0);
    
    boolean isSneaking();
    
    void setSneaking(final boolean p0);
    
    boolean isSprinting();
    
    void setSprinting(final boolean p0);
    
    void saveData();
    
    void loadData();
    
    void setSleepingIgnored(final boolean p0);
    
    boolean isSleepingIgnored();
    
    void playNote(final Location p0, final byte p1, final byte p2);
    
    void playNote(final Location p0, final Instrument p1, final Note p2);
    
    void playSound(final Location p0, final Sound p1, final float p2, final float p3);
    
    void playEffect(final Location p0, final Effect p1, final int p2);
    
     <T> void playEffect(final Location p0, final Effect p1, final T p2);
    
    void sendBlockChange(final Location p0, final Material p1, final byte p2);
    
    boolean sendChunkChange(final Location p0, final int p1, final int p2, final int p3, final byte[] p4);
    
    void sendBlockChange(final Location p0, final int p1, final byte p2);
    
    void sendMap(final MapView p0);
    
    @Deprecated
    void updateInventory();
    
    void awardAchievement(final Achievement p0);
    
    void incrementStatistic(final Statistic p0);
    
    void incrementStatistic(final Statistic p0, final int p1);
    
    void incrementStatistic(final Statistic p0, final Material p1);
    
    void incrementStatistic(final Statistic p0, final Material p1, final int p2);
    
    void setPlayerTime(final long p0, final boolean p1);
    
    long getPlayerTime();
    
    long getPlayerTimeOffset();
    
    boolean isPlayerTimeRelative();
    
    void resetPlayerTime();
    
    void setPlayerWeather(final WeatherType p0);
    
    WeatherType getPlayerWeather();
    
    void resetPlayerWeather();
    
    void giveExp(final int p0);
    
    void giveExpLevels(final int p0);
    
    float getExp();
    
    void setExp(final float p0);
    
    int getLevel();
    
    void setLevel(final int p0);
    
    int getTotalExperience();
    
    void setTotalExperience(final int p0);
    
    float getExhaustion();
    
    void setExhaustion(final float p0);
    
    float getSaturation();
    
    void setSaturation(final float p0);
    
    int getFoodLevel();
    
    void setFoodLevel(final int p0);
    
    Location getBedSpawnLocation();
    
    void setBedSpawnLocation(final Location p0);
    
    void setBedSpawnLocation(final Location p0, final boolean p1);
    
    boolean getAllowFlight();
    
    void setAllowFlight(final boolean p0);
    
    void hidePlayer(final Player p0);
    
    void showPlayer(final Player p0);
    
    boolean canSee(final Player p0);
    
    @Deprecated
    boolean isOnGround();
    
    boolean isFlying();
    
    void setFlying(final boolean p0);
    
    void setFlySpeed(final float p0) throws IllegalArgumentException;
    
    void setWalkSpeed(final float p0) throws IllegalArgumentException;
    
    float getFlySpeed();
    
    float getWalkSpeed();
    
    void setTexturePack(final String p0);
    
    Scoreboard getScoreboard();
    
    void setScoreboard(final Scoreboard p0) throws IllegalArgumentException, IllegalStateException;
    
    Spigot spigot();
    
    public static class Spigot
    {
        public InetSocketAddress getRawAddress() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void playEffect(final Location location, final Effect effect, final int id, final int data, final float offsetX, final float offsetY, final float offsetZ, final float speed, final int particleCount, final int radius) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
