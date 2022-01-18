// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scoreboard;

import org.bukkit.scoreboard.Score;
import org.bukkit.OfflinePlayer;
import net.minecraft.server.v1_5_R3.Scoreboard;
import org.bukkit.scoreboard.DisplaySlot;
import org.apache.commons.lang.Validate;
import net.minecraft.server.v1_5_R3.ScoreboardObjective;
import org.bukkit.scoreboard.Objective;

final class CraftObjective extends CraftScoreboardComponent implements Objective
{
    private final ScoreboardObjective objective;
    private final CraftCriteria criteria;
    
    CraftObjective(final CraftScoreboard scoreboard, final ScoreboardObjective objective) {
        super(scoreboard);
        this.objective = objective;
        this.criteria = CraftCriteria.getFromNMS(objective);
        scoreboard.objectives.put(objective.getName(), this);
    }
    
    ScoreboardObjective getHandle() {
        return this.objective;
    }
    
    public String getName() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.objective.getName();
    }
    
    public String getDisplayName() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.objective.getDisplayName();
    }
    
    public void setDisplayName(final String displayName) throws IllegalStateException, IllegalArgumentException {
        Validate.notNull(displayName, "Display name cannot be null");
        Validate.isTrue(displayName.length() <= 32, "Display name '" + displayName + "' is longer than the limit of 32 characters");
        final CraftScoreboard scoreboard = this.checkState();
        this.objective.setDisplayName(displayName);
    }
    
    public String getCriteria() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.criteria.bukkitName;
    }
    
    public boolean isModifiable() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return !this.criteria.criteria.isReadOnly();
    }
    
    public void setDisplaySlot(final DisplaySlot slot) throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        final Scoreboard board = scoreboard.board;
        final ScoreboardObjective objective = this.objective;
        for (int i = 0; i < 3; ++i) {
            if (board.getObjectiveForSlot(i) == objective) {
                board.setDisplaySlot(i, null);
            }
        }
        if (slot != null) {
            final int slotNumber = CraftScoreboardTranslations.fromBukkitSlot(slot);
            board.setDisplaySlot(slotNumber, this.getHandle());
        }
    }
    
    public DisplaySlot getDisplaySlot() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        final Scoreboard board = scoreboard.board;
        final ScoreboardObjective objective = this.objective;
        for (int i = 0; i < 3; ++i) {
            if (board.getObjectiveForSlot(i) == objective) {
                return CraftScoreboardTranslations.toBukkitSlot(i);
            }
        }
        return null;
    }
    
    public Score getScore(final OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
        Validate.notNull(player, "Player cannot be null");
        final CraftScoreboard scoreboard = this.checkState();
        return new CraftScore(this, player.getName());
    }
    
    public void unregister() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        scoreboard.objectives.remove(this.getName());
        scoreboard.board.unregisterObjective(this.objective);
        this.setUnregistered();
    }
}
