// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.permissions.ServerOperator;

public interface OfflinePlayer extends ServerOperator, AnimalTamer, ConfigurationSerializable
{
    boolean isOnline();
    
    String getName();
    
    boolean isBanned();
    
    void setBanned(final boolean p0);
    
    boolean isWhitelisted();
    
    void setWhitelisted(final boolean p0);
    
    Player getPlayer();
    
    long getFirstPlayed();
    
    long getLastPlayed();
    
    boolean hasPlayedBefore();
    
    Location getBedSpawnLocation();
}
