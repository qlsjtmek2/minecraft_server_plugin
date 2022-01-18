// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scoreboard;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_5_R3.ScoreboardScore;
import net.minecraft.server.v1_5_R3.IScoreboardCriteria;
import org.bukkit.entity.Player;
import java.util.Iterator;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.Packet209SetScoreboardTeam;
import net.minecraft.server.v1_5_R3.ScoreboardTeam;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet206SetScoreboardObjective;
import net.minecraft.server.v1_5_R3.ScoreboardObjective;
import java.util.HashSet;
import org.apache.commons.lang.Validate;
import net.minecraft.server.v1_5_R3.ScoreboardServer;
import java.util.HashMap;
import org.bukkit.craftbukkit.v1_5_R3.util.WeakCollection;
import net.minecraft.server.v1_5_R3.Scoreboard;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import java.util.Map;
import java.util.Collection;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import org.bukkit.scoreboard.ScoreboardManager;

public final class CraftScoreboardManager implements ScoreboardManager
{
    private final CraftScoreboard mainScoreboard;
    private final MinecraftServer server;
    private final Collection<CraftScoreboard> scoreboards;
    private final Map<CraftPlayer, CraftScoreboard> playerBoards;
    
    public CraftScoreboardManager(final MinecraftServer minecraftserver, final Scoreboard scoreboardServer) {
        this.scoreboards = new WeakCollection<CraftScoreboard>();
        this.playerBoards = new HashMap<CraftPlayer, CraftScoreboard>();
        this.mainScoreboard = new CraftScoreboard(scoreboardServer);
        this.server = minecraftserver;
        this.scoreboards.add(this.mainScoreboard);
    }
    
    public CraftScoreboard getMainScoreboard() {
        return this.mainScoreboard;
    }
    
    public CraftScoreboard getNewScoreboard() {
        final CraftScoreboard scoreboard = new CraftScoreboard(new ScoreboardServer(this.server));
        this.scoreboards.add(scoreboard);
        return scoreboard;
    }
    
    public CraftScoreboard getPlayerBoard(final CraftPlayer player) {
        final CraftScoreboard board = this.playerBoards.get(player);
        return (board == null) ? this.getMainScoreboard() : board;
    }
    
    public void setPlayerBoard(final CraftPlayer player, final org.bukkit.scoreboard.Scoreboard bukkitScoreboard) throws IllegalArgumentException {
        Validate.isTrue(bukkitScoreboard instanceof CraftScoreboard, "Cannot set player scoreboard to an unregistered Scoreboard");
        final CraftScoreboard scoreboard = (CraftScoreboard)bukkitScoreboard;
        final Scoreboard oldboard = this.getPlayerBoard(player).getHandle();
        final Scoreboard newboard = scoreboard.getHandle();
        final EntityPlayer entityplayer = player.getHandle();
        if (oldboard == newboard) {
            return;
        }
        if (scoreboard == this.mainScoreboard) {
            this.playerBoards.remove(player);
        }
        else {
            this.playerBoards.put(player, scoreboard);
        }
        final HashSet<ScoreboardObjective> removed = new HashSet<ScoreboardObjective>();
        for (int i = 0; i < 3; ++i) {
            final ScoreboardObjective scoreboardobjective = oldboard.getObjectiveForSlot(i);
            if (scoreboardobjective != null && !removed.contains(scoreboardobjective)) {
                entityplayer.playerConnection.sendPacket(new Packet206SetScoreboardObjective(scoreboardobjective, 1));
                removed.add(scoreboardobjective);
            }
        }
        for (final ScoreboardTeam scoreboardteam : oldboard.getTeams()) {
            entityplayer.playerConnection.sendPacket(new Packet209SetScoreboardTeam(scoreboardteam, 1));
        }
        this.server.getPlayerList().a((ScoreboardServer)newboard, player.getHandle());
    }
    
    public void removePlayer(final Player player) {
        this.playerBoards.remove(player);
    }
    
    public Collection<ScoreboardScore> getScoreboardScores(final IScoreboardCriteria criteria, final String name, final Collection<ScoreboardScore> collection) {
        for (final CraftScoreboard scoreboard : this.scoreboards) {
            final Scoreboard board = scoreboard.board;
            for (final ScoreboardObjective objective : board.getObjectivesForCriteria(criteria)) {
                collection.add(board.getPlayerScoreForObjective(name, objective));
            }
        }
        return collection;
    }
    
    public void updateAllScoresForList(final IScoreboardCriteria criteria, final String name, final List<EntityPlayer> of) {
        for (final ScoreboardScore score : this.getScoreboardScores(criteria, name, new ArrayList<ScoreboardScore>())) {
            score.updateForList(of);
        }
    }
}
