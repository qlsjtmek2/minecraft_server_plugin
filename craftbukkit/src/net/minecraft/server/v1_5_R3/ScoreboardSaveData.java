// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;

public class ScoreboardSaveData extends WorldMapBase
{
    private Scoreboard a;
    private NBTTagCompound b;
    
    public ScoreboardSaveData() {
        this("scoreboard");
    }
    
    public ScoreboardSaveData(final String s) {
        super(s);
    }
    
    public void a(final Scoreboard a) {
        this.a = a;
        if (this.b != null) {
            this.a(this.b);
        }
    }
    
    public void a(final NBTTagCompound b) {
        if (this.a == null) {
            this.b = b;
            return;
        }
        this.b(b.getList("Objectives"));
        this.c(b.getList("PlayerScores"));
        if (b.hasKey("DisplaySlots")) {
            this.c(b.getCompound("DisplaySlots"));
        }
        if (b.hasKey("Teams")) {
            this.a(b.getList("Teams"));
        }
    }
    
    protected void a(final NBTTagList list) {
        for (int i = 0; i < list.size(); ++i) {
            final NBTTagCompound nbtTagCompound = (NBTTagCompound)list.get(i);
            final ScoreboardTeam team = this.a.createTeam(nbtTagCompound.getString("Name"));
            team.setDisplayName(nbtTagCompound.getString("DisplayName"));
            team.setPrefix(nbtTagCompound.getString("Prefix"));
            team.setSuffix(nbtTagCompound.getString("Suffix"));
            if (nbtTagCompound.hasKey("AllowFriendlyFire")) {
                team.setAllowFriendlyFire(nbtTagCompound.getBoolean("AllowFriendlyFire"));
            }
            if (nbtTagCompound.hasKey("SeeFriendlyInvisibles")) {
                team.setCanSeeFriendlyInvisibles(nbtTagCompound.getBoolean("SeeFriendlyInvisibles"));
            }
            this.a(team, nbtTagCompound.getList("Players"));
        }
    }
    
    protected void a(final ScoreboardTeam scoreboardTeam, final NBTTagList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.a.addPlayerToTeam(((NBTTagString)list.get(i)).data, scoreboardTeam);
        }
    }
    
    protected void c(final NBTTagCompound nbtTagCompound) {
        for (int i = 0; i < 3; ++i) {
            if (nbtTagCompound.hasKey("slot_" + i)) {
                this.a.setDisplaySlot(i, this.a.getObjective(nbtTagCompound.getString("slot_" + i)));
            }
        }
    }
    
    protected void b(final NBTTagList list) {
        for (int i = 0; i < list.size(); ++i) {
            final NBTTagCompound nbtTagCompound = (NBTTagCompound)list.get(i);
            this.a.registerObjective(nbtTagCompound.getString("Name"), (IScoreboardCriteria)IScoreboardCriteria.a.get(nbtTagCompound.getString("CriteriaName"))).setDisplayName(nbtTagCompound.getString("DisplayName"));
        }
    }
    
    protected void c(final NBTTagList list) {
        for (int i = 0; i < list.size(); ++i) {
            final NBTTagCompound nbtTagCompound = (NBTTagCompound)list.get(i);
            this.a.getPlayerScoreForObjective(nbtTagCompound.getString("Name"), this.a.getObjective(nbtTagCompound.getString("Objective"))).setScore(nbtTagCompound.getInt("Score"));
        }
    }
    
    public void b(final NBTTagCompound nbtTagCompound) {
        if (this.a == null) {
            MinecraftServer.getServer().getLogger().warning("Tried to save scoreboard without having a scoreboard...");
            return;
        }
        nbtTagCompound.set("Objectives", this.b());
        nbtTagCompound.set("PlayerScores", this.e());
        nbtTagCompound.set("Teams", this.a());
        this.d(nbtTagCompound);
    }
    
    protected NBTTagList a() {
        final NBTTagList list = new NBTTagList();
        for (final ScoreboardTeam scoreboardTeam : this.a.getTeams()) {
            final NBTTagCompound paramNBTBase = new NBTTagCompound();
            paramNBTBase.setString("Name", scoreboardTeam.getName());
            paramNBTBase.setString("DisplayName", scoreboardTeam.getDisplayName());
            paramNBTBase.setString("Prefix", scoreboardTeam.getPrefix());
            paramNBTBase.setString("Suffix", scoreboardTeam.getSuffix());
            paramNBTBase.setBoolean("AllowFriendlyFire", scoreboardTeam.allowFriendlyFire());
            paramNBTBase.setBoolean("SeeFriendlyInvisibles", scoreboardTeam.canSeeFriendlyInvisibles());
            final NBTTagList list2 = new NBTTagList();
            final Iterator iterator2 = scoreboardTeam.getPlayerNameSet().iterator();
            while (iterator2.hasNext()) {
                list2.add(new NBTTagString("", iterator2.next()));
            }
            paramNBTBase.set("Players", list2);
            list.add(paramNBTBase);
        }
        return list;
    }
    
    protected void d(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        boolean b = false;
        for (int i = 0; i < 3; ++i) {
            final ScoreboardObjective objectiveForSlot = this.a.getObjectiveForSlot(i);
            if (objectiveForSlot != null) {
                nbtTagCompound2.setString("slot_" + i, objectiveForSlot.getName());
                b = true;
            }
        }
        if (b) {
            nbtTagCompound.setCompound("DisplaySlots", nbtTagCompound2);
        }
    }
    
    protected NBTTagList b() {
        final NBTTagList list = new NBTTagList();
        for (final ScoreboardObjective scoreboardObjective : this.a.getObjectives()) {
            final NBTTagCompound paramNBTBase = new NBTTagCompound();
            paramNBTBase.setString("Name", scoreboardObjective.getName());
            paramNBTBase.setString("CriteriaName", scoreboardObjective.getCriteria().getName());
            paramNBTBase.setString("DisplayName", scoreboardObjective.getDisplayName());
            list.add(paramNBTBase);
        }
        return list;
    }
    
    protected NBTTagList e() {
        final NBTTagList list = new NBTTagList();
        for (final ScoreboardScore scoreboardScore : this.a.getScores()) {
            final NBTTagCompound paramNBTBase = new NBTTagCompound();
            paramNBTBase.setString("Name", scoreboardScore.getPlayerName());
            paramNBTBase.setString("Objective", scoreboardScore.getObjective().getName());
            paramNBTBase.setInt("Score", scoreboardScore.getScore());
            list.add(paramNBTBase);
        }
        return list;
    }
}
