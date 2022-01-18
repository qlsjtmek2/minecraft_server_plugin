// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;
import java.util.Set;

public interface Team
{
    String getName() throws IllegalStateException;
    
    String getDisplayName() throws IllegalStateException;
    
    void setDisplayName(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    String getPrefix() throws IllegalStateException;
    
    void setPrefix(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    String getSuffix() throws IllegalStateException;
    
    void setSuffix(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    boolean allowFriendlyFire() throws IllegalStateException;
    
    void setAllowFriendlyFire(final boolean p0) throws IllegalStateException;
    
    boolean canSeeFriendlyInvisibles() throws IllegalStateException;
    
    void setCanSeeFriendlyInvisibles(final boolean p0) throws IllegalStateException;
    
    Set<OfflinePlayer> getPlayers() throws IllegalStateException;
    
    int getSize() throws IllegalStateException;
    
    Scoreboard getScoreboard();
    
    void addPlayer(final OfflinePlayer p0) throws IllegalStateException, IllegalArgumentException;
    
    boolean removePlayer(final OfflinePlayer p0) throws IllegalStateException, IllegalArgumentException;
    
    void unregister() throws IllegalStateException;
    
    boolean hasPlayer(final OfflinePlayer p0) throws IllegalArgumentException, IllegalStateException;
}
