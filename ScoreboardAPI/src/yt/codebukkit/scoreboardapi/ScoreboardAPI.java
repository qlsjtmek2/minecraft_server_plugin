// 
// Decompiled by Procyon v0.5.30
// 

package yt.codebukkit.scoreboardapi;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class ScoreboardAPI extends JavaPlugin
{
    public static String NAME;
    public static String VERSION;
    public static String AUTHORS;
    public static String CMD_PREFIX;
    public static String PREFIX;
    public static String NO_PERMISSIONS;
    public static String FOLDER;
    public static String CONFIG_FILE;
    public static String PREFIX_COLOR;
    public static String CONFIG_NAME;
    public static boolean ENABLE_METRICS;
    private List<Scoreboard> scoreboards;
    Format format;
    Helper helper;
    
    public ScoreboardAPI() {
        this.scoreboards = new LinkedList<Scoreboard>();
        this.format = new Format(this);
        this.helper = new Helper(this);
    }
    
    public List<Scoreboard> getScoreboards() {
        return this.scoreboards;
    }
    
    public void removeScoreboard(final Scoreboard s) {
        s.stopShowingAllPlayers();
        this.scoreboards.remove(s);
    }
    
    public static ScoreboardAPI getInstance() {
        final Plugin pl = Bukkit.getPluginManager().getPlugin("ScoreboardAPI");
        if (pl == null) {
            return null;
        }
        if (!(pl instanceof ScoreboardAPI)) {
            return null;
        }
        return (ScoreboardAPI)pl;
    }
    
    public Scoreboard createScoreboard(final String name, final int priority) {
        for (final Scoreboard s : this.scoreboards) {
            if (s.getName().equals(name)) {
                return null;
            }
        }
        final Scoreboard s2 = new Scoreboard(name, priority, this);
        this.scoreboards.add(s2);
        return s2;
    }
    
    public Scoreboard getScoreboard(final String name) {
        for (final Scoreboard s : this.scoreboards) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }
    
    public void updateForPlayer(final Player p) {
        for (final Scoreboard s : this.scoreboards) {
            s.checkIfNeedsToBeDisabledForPlayer(p);
            s.checkIfNeedsToBeEnabledForPlayer(p);
        }
    }
    
    public boolean isPlayerReceivingScoreboard(final Player p) {
        for (final Scoreboard s : this.scoreboards) {
            if (s.hasPlayerAdded(p)) {
                return true;
            }
        }
        return false;
    }
    
    public void updateForAllPlayers() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            this.updateForPlayer(p);
        }
    }
    
    private void initialize() {
        ScoreboardAPI.NAME = this.getDescription().getName();
        ScoreboardAPI.VERSION = this.getDescription().getVersion();
        ScoreboardAPI.AUTHORS = "";
        for (final String s : this.getDescription().getAuthors()) {
            ScoreboardAPI.AUTHORS = ScoreboardAPI.AUTHORS + s + ", ";
        }
        ScoreboardAPI.CMD_PREFIX = "[" + ScoreboardAPI.NAME + "] ";
        ScoreboardAPI.PREFIX = "[" + ScoreboardAPI.PREFIX_COLOR + ScoreboardAPI.NAME + ChatColor.RESET + "] ";
        ScoreboardAPI.NO_PERMISSIONS = ScoreboardAPI.PREFIX + ChatColor.DARK_RED + "You don't have permissions!";
        ScoreboardAPI.FOLDER = "plugins/" + ScoreboardAPI.NAME;
        ScoreboardAPI.CONFIG_FILE = ScoreboardAPI.FOLDER + "/" + ScoreboardAPI.CONFIG_NAME;
    }
    
    public void onEnable() {
        this.initialize();
    }
    
    public void onDisable() {
    }
    
    static {
        ScoreboardAPI.PREFIX_COLOR = ChatColor.AQUA + "";
        ScoreboardAPI.CONFIG_NAME = "config.yml";
        ScoreboardAPI.ENABLE_METRICS = false;
    }
}
