// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;

public interface Score
{
    OfflinePlayer getPlayer();
    
    Objective getObjective();
    
    int getScore() throws IllegalStateException;
    
    void setScore(final int p0) throws IllegalStateException;
    
    Scoreboard getScoreboard();
}
