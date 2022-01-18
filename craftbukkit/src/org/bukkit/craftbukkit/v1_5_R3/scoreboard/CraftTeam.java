// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scoreboard;

import org.bukkit.scoreboard.Scoreboard;
import java.util.Iterator;
import org.bukkit.Bukkit;
import com.google.common.collect.ImmutableSet;
import org.bukkit.OfflinePlayer;
import java.util.Set;
import org.apache.commons.lang.Validate;
import net.minecraft.server.v1_5_R3.ScoreboardTeam;
import org.bukkit.scoreboard.Team;

final class CraftTeam extends CraftScoreboardComponent implements Team
{
    private final ScoreboardTeam team;
    
    CraftTeam(final CraftScoreboard scoreboard, final ScoreboardTeam team) {
        super(scoreboard);
        this.team = team;
        scoreboard.teams.put(team.getName(), this);
    }
    
    public String getName() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.team.getName();
    }
    
    public String getDisplayName() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.team.getDisplayName();
    }
    
    public void setDisplayName(final String displayName) throws IllegalStateException {
        Validate.notNull(displayName, "Display name cannot be null");
        Validate.isTrue(displayName.length() <= 32, "Display name '" + displayName + "' is longer than the limit of 32 characters");
        final CraftScoreboard scoreboard = this.checkState();
        this.team.setDisplayName(displayName);
    }
    
    public String getPrefix() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.team.getPrefix();
    }
    
    public void setPrefix(final String prefix) throws IllegalStateException, IllegalArgumentException {
        Validate.notNull(prefix, "Prefix cannot be null");
        Validate.isTrue(prefix.length() <= 32, "Prefix '" + prefix + "' is longer than the limit of 32 characters");
        final CraftScoreboard scoreboard = this.checkState();
        this.team.setPrefix(prefix);
    }
    
    public String getSuffix() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.team.getSuffix();
    }
    
    public void setSuffix(final String suffix) throws IllegalStateException, IllegalArgumentException {
        Validate.notNull(suffix, "Suffix cannot be null");
        Validate.isTrue(suffix.length() <= 32, "Suffix '" + suffix + "' is longer than the limit of 32 characters");
        final CraftScoreboard scoreboard = this.checkState();
        this.team.setSuffix(suffix);
    }
    
    public boolean allowFriendlyFire() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.team.allowFriendlyFire();
    }
    
    public void setAllowFriendlyFire(final boolean enabled) throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        this.team.setAllowFriendlyFire(enabled);
    }
    
    public boolean canSeeFriendlyInvisibles() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.team.canSeeFriendlyInvisibles();
    }
    
    public void setCanSeeFriendlyInvisibles(final boolean enabled) throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        this.team.setCanSeeFriendlyInvisibles(enabled);
    }
    
    public Set<OfflinePlayer> getPlayers() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        final ImmutableSet.Builder<OfflinePlayer> players = ImmutableSet.builder();
        for (final Object o : this.team.getPlayerNameSet()) {
            players.add(Bukkit.getOfflinePlayer(o.toString()));
        }
        return players.build();
    }
    
    public int getSize() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        return this.team.getPlayerNameSet().size();
    }
    
    public void addPlayer(final OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
        Validate.notNull(player, "OfflinePlayer cannot be null");
        final CraftScoreboard scoreboard = this.checkState();
        scoreboard.board.addPlayerToTeam(player.getName(), this.team);
    }
    
    public boolean removePlayer(final OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
        Validate.notNull(player, "OfflinePlayer cannot be null");
        final CraftScoreboard scoreboard = this.checkState();
        if (!this.team.getPlayerNameSet().contains(player.getName())) {
            return false;
        }
        scoreboard.board.removePlayerFromTeam(player.getName(), this.team);
        return true;
    }
    
    public boolean hasPlayer(final OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
        Validate.notNull(player, "OfflinePlayer cannot be null");
        final CraftScoreboard scoreboard = this.checkState();
        return this.team.getPlayerNameSet().contains(player.getName());
    }
    
    public void unregister() throws IllegalStateException {
        final CraftScoreboard scoreboard = this.checkState();
        scoreboard.board.removeTeam(this.team);
        scoreboard.teams.remove(this.team.getName());
        this.setUnregistered();
    }
}
