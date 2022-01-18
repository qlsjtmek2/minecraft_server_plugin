// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ScoreboardServer extends Scoreboard
{
    private final MinecraftServer a;
    private final Set b;
    private ScoreboardSaveData c;
    
    public ScoreboardServer(final MinecraftServer minecraftserver) {
        this.b = new HashSet();
        this.a = minecraftserver;
    }
    
    public void handleScoreChanged(final ScoreboardScore scoreboardscore) {
        super.handleScoreChanged(scoreboardscore);
        if (this.b.contains(scoreboardscore.getObjective())) {
            this.sendAll(new Packet207SetScoreboardScore(scoreboardscore, 0));
        }
        this.b();
    }
    
    public void handlePlayerRemoved(final String s) {
        super.handlePlayerRemoved(s);
        this.sendAll(new Packet207SetScoreboardScore(s));
        this.b();
    }
    
    public void setDisplaySlot(final int i, final ScoreboardObjective scoreboardobjective) {
        final ScoreboardObjective scoreboardobjective2 = this.getObjectiveForSlot(i);
        super.setDisplaySlot(i, scoreboardobjective);
        if (scoreboardobjective2 != scoreboardobjective && scoreboardobjective2 != null) {
            if (this.h(scoreboardobjective2) > 0) {
                this.sendAll(new Packet208SetScoreboardDisplayObjective(i, scoreboardobjective));
            }
            else {
                this.g(scoreboardobjective2);
            }
        }
        if (scoreboardobjective != null) {
            if (this.b.contains(scoreboardobjective)) {
                this.sendAll(new Packet208SetScoreboardDisplayObjective(i, scoreboardobjective));
            }
            else {
                this.e(scoreboardobjective);
            }
        }
        this.b();
    }
    
    public void addPlayerToTeam(final String s, final ScoreboardTeam scoreboardteam) {
        super.addPlayerToTeam(s, scoreboardteam);
        this.sendAll(new Packet209SetScoreboardTeam(scoreboardteam, Arrays.asList(s), 3));
        this.b();
    }
    
    public void removePlayerFromTeam(final String s, final ScoreboardTeam scoreboardteam) {
        super.removePlayerFromTeam(s, scoreboardteam);
        this.sendAll(new Packet209SetScoreboardTeam(scoreboardteam, Arrays.asList(s), 4));
        this.b();
    }
    
    public void handleObjectiveAdded(final ScoreboardObjective scoreboardobjective) {
        super.handleObjectiveAdded(scoreboardobjective);
        this.b();
    }
    
    public void handleObjectiveChanged(final ScoreboardObjective scoreboardobjective) {
        super.handleObjectiveChanged(scoreboardobjective);
        if (this.b.contains(scoreboardobjective)) {
            this.sendAll(new Packet206SetScoreboardObjective(scoreboardobjective, 2));
        }
        this.b();
    }
    
    public void handleObjectiveRemoved(final ScoreboardObjective scoreboardobjective) {
        super.handleObjectiveRemoved(scoreboardobjective);
        if (this.b.contains(scoreboardobjective)) {
            this.g(scoreboardobjective);
        }
        this.b();
    }
    
    public void handleTeamAdded(final ScoreboardTeam scoreboardteam) {
        super.handleTeamAdded(scoreboardteam);
        this.sendAll(new Packet209SetScoreboardTeam(scoreboardteam, 0));
        this.b();
    }
    
    public void handleTeamChanged(final ScoreboardTeam scoreboardteam) {
        super.handleTeamChanged(scoreboardteam);
        this.sendAll(new Packet209SetScoreboardTeam(scoreboardteam, 2));
        this.b();
    }
    
    public void handleTeamRemoved(final ScoreboardTeam scoreboardteam) {
        super.handleTeamRemoved(scoreboardteam);
        this.sendAll(new Packet209SetScoreboardTeam(scoreboardteam, 1));
        this.b();
    }
    
    public void a(final ScoreboardSaveData scoreboardsavedata) {
        this.c = scoreboardsavedata;
    }
    
    protected void b() {
        if (this.c != null) {
            this.c.c();
        }
    }
    
    public List getScoreboardScorePacketsForObjective(final ScoreboardObjective scoreboardobjective) {
        final ArrayList arraylist = new ArrayList();
        arraylist.add(new Packet206SetScoreboardObjective(scoreboardobjective, 0));
        for (int i = 0; i < 3; ++i) {
            if (this.getObjectiveForSlot(i) == scoreboardobjective) {
                arraylist.add(new Packet208SetScoreboardDisplayObjective(i, scoreboardobjective));
            }
        }
        for (final ScoreboardScore scoreboardscore : this.getScoresForObjective(scoreboardobjective)) {
            arraylist.add(new Packet207SetScoreboardScore(scoreboardscore, 0));
        }
        return arraylist;
    }
    
    public void e(final ScoreboardObjective scoreboardobjective) {
        final List list = this.getScoreboardScorePacketsForObjective(scoreboardobjective);
        for (final EntityPlayer entityplayer : this.a.getPlayerList().players) {
            if (entityplayer.getBukkitEntity().getScoreboard().getHandle() != this) {
                continue;
            }
            for (final Packet packet : list) {
                entityplayer.playerConnection.sendPacket(packet);
            }
        }
        this.b.add(scoreboardobjective);
    }
    
    public List f(final ScoreboardObjective scoreboardobjective) {
        final ArrayList arraylist = new ArrayList();
        arraylist.add(new Packet206SetScoreboardObjective(scoreboardobjective, 1));
        for (int i = 0; i < 3; ++i) {
            if (this.getObjectiveForSlot(i) == scoreboardobjective) {
                arraylist.add(new Packet208SetScoreboardDisplayObjective(i, scoreboardobjective));
            }
        }
        return arraylist;
    }
    
    public void g(final ScoreboardObjective scoreboardobjective) {
        final List list = this.f(scoreboardobjective);
        for (final EntityPlayer entityplayer : this.a.getPlayerList().players) {
            if (entityplayer.getBukkitEntity().getScoreboard().getHandle() != this) {
                continue;
            }
            for (final Packet packet : list) {
                entityplayer.playerConnection.sendPacket(packet);
            }
        }
        this.b.remove(scoreboardobjective);
    }
    
    public int h(final ScoreboardObjective scoreboardobjective) {
        int i = 0;
        for (int j = 0; j < 3; ++j) {
            if (this.getObjectiveForSlot(j) == scoreboardobjective) {
                ++i;
            }
        }
        return i;
    }
    
    private void sendAll(final Packet packet) {
        for (final EntityPlayer entityplayer : this.a.getPlayerList().players) {
            if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this) {
                entityplayer.playerConnection.sendPacket(packet);
            }
        }
    }
}
