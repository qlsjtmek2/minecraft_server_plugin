// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scoreboard;

import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Score;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import java.util.Collection;
import com.google.common.collect.ImmutableSet;
import org.bukkit.scoreboard.Objective;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import net.minecraft.server.v1_5_R3.ScoreboardTeam;
import net.minecraft.server.v1_5_R3.ScoreboardObjective;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.scoreboard.Scoreboard;

public final class CraftScoreboard implements Scoreboard
{
    final net.minecraft.server.v1_5_R3.Scoreboard board;
    final Map<String, CraftObjective> objectives;
    final Map<String, CraftTeam> teams;
    
    CraftScoreboard(final net.minecraft.server.v1_5_R3.Scoreboard board) {
        this.objectives = new HashMap<String, CraftObjective>();
        this.teams = new HashMap<String, CraftTeam>();
        this.board = board;
        for (final ScoreboardObjective objective : board.getObjectives()) {
            new CraftObjective(this, objective);
        }
        for (final ScoreboardTeam team : board.getTeams()) {
            new CraftTeam(this, team);
        }
    }
    
    public CraftObjective registerNewObjective(final String name, final String criteria) throws IllegalArgumentException {
        Validate.notNull(name, "Objective name cannot be null");
        Validate.notNull(criteria, "Criteria cannot be null");
        Validate.isTrue(name.length() <= 16, "The name '" + name + "' is longer than the limit of 16 characters");
        Validate.isTrue(this.board.getObjective(name) == null, "An objective of name '" + name + "' already exists");
        final CraftCriteria craftCriteria = CraftCriteria.getFromBukkit(criteria);
        final ScoreboardObjective objective = this.board.registerObjective(name, craftCriteria.criteria);
        return new CraftObjective(this, objective);
    }
    
    public Objective getObjective(final String name) throws IllegalArgumentException {
        Validate.notNull(name, "Name cannot be null");
        return this.objectives.get(name);
    }
    
    public ImmutableSet<Objective> getObjectivesByCriteria(final String criteria) throws IllegalArgumentException {
        Validate.notNull(criteria, "Criteria cannot be null");
        final ImmutableSet.Builder<Objective> objectives = ImmutableSet.builder();
        for (final CraftObjective objective : this.objectives.values()) {
            if (objective.getCriteria().equals(criteria)) {
                objectives.add(objective);
            }
        }
        return objectives.build();
    }
    
    public ImmutableSet<Objective> getObjectives() {
        return ImmutableSet.copyOf((Collection<? extends Objective>)this.objectives.values());
    }
    
    public Objective getObjective(final DisplaySlot slot) throws IllegalArgumentException {
        Validate.notNull(slot, "Display slot cannot be null");
        final ScoreboardObjective objective = this.board.getObjectiveForSlot(CraftScoreboardTranslations.fromBukkitSlot(slot));
        if (objective == null) {
            return null;
        }
        return this.objectives.get(objective.getName());
    }
    
    public ImmutableSet<Score> getScores(final OfflinePlayer player) throws IllegalArgumentException {
        Validate.notNull(player, "OfflinePlayer cannot be null");
        final ImmutableSet.Builder<Score> scores = ImmutableSet.builder();
        for (final CraftObjective objective : this.objectives.values()) {
            scores.add(objective.getScore(player));
        }
        return scores.build();
    }
    
    public void resetScores(final OfflinePlayer player) throws IllegalArgumentException {
        Validate.notNull(player, "OfflinePlayer cannot be null");
        this.board.resetPlayerScores(player.getName());
    }
    
    public Team getPlayerTeam(final OfflinePlayer player) throws IllegalArgumentException {
        Validate.notNull(player, "OfflinePlayer cannot be null");
        final ScoreboardTeam team = this.board.getPlayerTeam(player.getName());
        return (team == null) ? null : this.teams.get(team.getName());
    }
    
    public Team getTeam(final String teamName) throws IllegalArgumentException {
        Validate.notNull(teamName, "Team name cannot be null");
        return this.teams.get(teamName);
    }
    
    public ImmutableSet<Team> getTeams() {
        return ImmutableSet.copyOf((Collection<? extends Team>)this.teams.values());
    }
    
    public Team registerNewTeam(final String name) throws IllegalArgumentException {
        Validate.notNull(name, "Team name cannot be null");
        Validate.isTrue(name.length() <= 16, "Team name '" + name + "' is longer than the limit of 16 characters");
        Validate.isTrue(this.board.getTeam(name) == null, "Team name '" + name + "' is already in use");
        return new CraftTeam(this, this.board.createTeam(name));
    }
    
    public ImmutableSet<OfflinePlayer> getPlayers() {
        final ImmutableSet.Builder<OfflinePlayer> players = ImmutableSet.builder();
        for (final Object playerName : this.board.getPlayers()) {
            players.add(Bukkit.getOfflinePlayer(playerName.toString()));
        }
        return players.build();
    }
    
    public void clearSlot(final DisplaySlot slot) throws IllegalArgumentException {
        Validate.notNull(slot, "Slot cannot be null");
        this.board.setDisplaySlot(CraftScoreboardTranslations.fromBukkitSlot(slot), null);
    }
    
    public net.minecraft.server.v1_5_R3.Scoreboard getHandle() {
        return this.board;
    }
}
