// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scoreboard;

import java.util.Map;
import net.minecraft.server.v1_5_R3.Scoreboard;
import net.minecraft.server.v1_5_R3.ScoreboardScore;
import org.bukkit.scoreboard.Objective;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Score;

final class CraftScore implements Score
{
    private final String playerName;
    private final CraftObjective objective;
    
    CraftScore(final CraftObjective objective, final String playerName) {
        this.objective = objective;
        this.playerName = playerName;
    }
    
    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(this.playerName);
    }
    
    public Objective getObjective() {
        return this.objective;
    }
    
    public int getScore() throws IllegalStateException {
        final Scoreboard board = this.objective.checkState().board;
        if (board.getPlayers().contains(this.playerName)) {
            final Map<String, ScoreboardScore> scores = (Map<String, ScoreboardScore>)board.getPlayerObjectives(this.playerName);
            final ScoreboardScore score = scores.get(this.objective.getHandle());
            if (score != null) {
                return score.getScore();
            }
        }
        return 0;
    }
    
    public void setScore(final int score) throws IllegalStateException {
        this.objective.checkState().board.getPlayerScoreForObjective(this.playerName, this.objective.getHandle()).setScore(score);
    }
    
    public CraftScoreboard getScoreboard() {
        return this.objective.getScoreboard();
    }
}
