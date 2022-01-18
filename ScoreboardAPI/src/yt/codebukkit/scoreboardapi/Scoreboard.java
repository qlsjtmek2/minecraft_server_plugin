// 
// Decompiled by Procyon v0.5.30
// 

package yt.codebukkit.scoreboardapi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet206SetScoreboardObjective;
import net.minecraft.server.v1_5_R3.Packet207SetScoreboardScore;
import net.minecraft.server.v1_5_R3.Packet208SetScoreboardDisplayObjective;
import net.minecraft.server.v1_5_R3.PlayerConnection;

public class Scoreboard
{
    private List<Player> players;
    private HashMap<String, Integer> items;
    private Type type;
    private String name;
    private String displayName;
    private int priority;
    private ScoreboardAPI plugin;
    
    Scoreboard(final String name, final int priority, final ScoreboardAPI plugin) {
        this.players = new LinkedList<Player>();
        this.items = new HashMap<String, Integer>();
        this.type = Type.SIDEBAR;
        this.displayName = "¡×4Not initialized";
        this.priority = 10;
        this.name = name;
        this.priority = priority;
        this.plugin = plugin;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public void setType(final Type type) {
        this.type = type;
        for (final Player p : this.players) {
            this.updatePosition(p);
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public String getScoreboardName() {
        return this.displayName;
    }
    
    public void setScoreboardName(final String displayName) {
        this.displayName = displayName;
        final Packet206SetScoreboardObjective pack = new Packet206SetScoreboardObjective();
        pack.a = this.name;
        pack.b = displayName;
        pack.c = 2;
        for (final Player p : this.players) {
            if (!this.isUnique(p)) {
                continue;
            }
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)pack);
        }
    }
    
    public void setItem(final String name2, final int value) {
        this.items.put(name2, value);
        final Packet207SetScoreboardScore pack = new Packet207SetScoreboardScore();
        pack.a = name2;
        pack.c = value;
        pack.d = 0;
        pack.b = this.name;
        for (final Player p : this.players) {
            if (!this.isUnique(p)) {
                continue;
            }
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)pack);
        }
    }
    
    public void removeItem(final String name2) {
        if (this.items.remove(name2) != null) {
            final Packet207SetScoreboardScore pack = new Packet207SetScoreboardScore();
            pack.a = name2;
            pack.c = 0;
            pack.d = 1;
            pack.b = this.name;
            for (final Player p : this.players) {
                if (!this.isUnique(p)) {
                    continue;
                }
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)pack);
            }
        }
    }
    
    public boolean isVacant() {
        return this.items.size() == 0 ? true : false;
    }
    
    public boolean hasPlayerAdded(final Player p) {
        return this.players.contains(p);
    }
    
    public List<Player> getAddedPlayers() {
        return this.players;
    }
    
    public void showToPlayer(final Player p, final boolean show) {
        if (show) {
            if (!this.players.contains(p)) {
                this.players.add(p);
                this.plugin.updateForPlayer(p);
            }
        }
        else if (this.players.remove(p)) {
            final Packet206SetScoreboardObjective pack = new Packet206SetScoreboardObjective();
            pack.a = this.name;
            pack.b = "";
            pack.c = 1;
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)pack);
            this.plugin.updateForPlayer(p);
        }
    }
    
    public void showToPlayer(final Player p) {
        this.showToPlayer(p, true);
    }
    
    public void stopShowingAllPlayers() {
        for (final Player p : this.players) {
            this.showToPlayer(p, false);
        }
    }
    
    private void updatePosition(final Player p) {
        if (!this.isUnique(p)) {
            return;
        }
        final Packet208SetScoreboardDisplayObjective pack2 = new Packet208SetScoreboardDisplayObjective();
        pack2.a = this.type.ordinal();
        pack2.b = this.name;
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)pack2);
    }
    
    public void checkIfNeedsToBeDisabledForPlayer(final Player p) {
        if (!this.players.contains(p)) {
            return;
        }
        final PlayerConnection conn = ((CraftPlayer)p).getHandle().playerConnection;
        if (!this.isUnique(p)) {
            final Packet206SetScoreboardObjective pack = new Packet206SetScoreboardObjective();
            pack.a = this.name;
            pack.b = this.displayName;
            pack.c = 1;
            conn.sendPacket((Packet)pack);
        }
    }
    
    public void clearItems() {
        for (final String name2 : this.items.keySet()) {
            this.removeItem(name2);
        }
    }
    
    public void checkIfNeedsToBeEnabledForPlayer(final Player p) {
        if (!this.players.contains(p)) {
            return;
        }
        final PlayerConnection conn = ((CraftPlayer)p).getHandle().playerConnection;
        if (this.isUnique(p)) {
            final Packet206SetScoreboardObjective pack = new Packet206SetScoreboardObjective();
            pack.a = this.name;
            pack.b = this.displayName;
            pack.c = 0;
            conn.sendPacket((Packet)pack);
            for (final String name2 : this.items.keySet()) {
                final Integer valObj = this.items.get(name2);
                if (valObj == null) {
                    continue;
                }
                final int val = valObj;
                final Packet207SetScoreboardScore pack2 = new Packet207SetScoreboardScore();
                pack2.a = name2;
                pack2.c = val;
                pack2.d = 0;
                pack2.b = this.name;
                conn.sendPacket((Packet)pack2);
            }
            this.updatePosition(p);
        }
    }
    
    private boolean isUnique(final Player p) {
        int myPos = 0;
        for (int i = 0; i < this.plugin.getScoreboards().size(); ++i) {
            if (this.plugin.getScoreboards().get(i) == this) {
                myPos = i;
                break;
            }
            final Scoreboard s = this.plugin.getScoreboards().get(i);
            if (s != this && s.hasPlayerAdded(p) && s.getType() == this.type && (s.getPriority() > this.priority || (i > myPos && s.getPriority() == this.priority))) {
                return false;
            }
        }
        return true;
    }
    
    public enum Type
    {
        PLAYER_LIST, 
        SIDEBAR;
    }
}
