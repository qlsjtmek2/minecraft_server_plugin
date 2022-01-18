// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scoreboard;

abstract class CraftScoreboardComponent
{
    private CraftScoreboard scoreboard;
    
    CraftScoreboardComponent(final CraftScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }
    
    CraftScoreboard checkState() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.scoreboard;
        if (scoreboard == null) {
            throw new IllegalStateException("Unregistered scoreboard component");
        }
        return scoreboard;
    }
    
    public CraftScoreboard getScoreboard() {
        return this.scoreboard;
    }
    
    abstract void unregister() throws IllegalStateException;
    
    final void setUnregistered() {
        this.scoreboard = null;
    }
}
