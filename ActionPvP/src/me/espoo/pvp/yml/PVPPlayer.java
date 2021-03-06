package me.espoo.pvp.yml;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.espoo.pvp.API;
import me.espoo.pvp.main;

public class PVPPlayer
{
    private String name;
    private Player player;
    private int kill;
    private int death;
    private String express;
    
    public PVPPlayer(String name) {
        this.name = null;
        this.player = null;
        this.kill = 0;
        this.death = 0;
        this.express = null;
        
        this.name = name;
        this.player = Bukkit.getPlayerExact(name);
        this.kill = main.user.getInt(name + ".킬");
        this.death = main.user.getInt(name + ".데스");
        this.express = main.user.getString(name + ".클래스");
    }
    
    public void saveRpgPlayer() {
        if (this.kill > 0) main.user.set(this.name + ".킬", this.kill);
        if (this.death > 0) main.user.set(this.name + ".데스", this.death);
        if (this.express != null) main.user.set(this.name + ".클래스", this.express);
    }
    
    public void updateCraftPlayer() {
        this.player = Bukkit.getPlayerExact(this.name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public int getKill() {
        return this.kill;
    }
    
    public int getDeath() {
        return this.death;
    }
    
    public void setKill(int kill) {
        this.kill = kill;
    }
    
    public void setDeath(int death) {
        this.death = death;
    }
    
    public void addKill(int kill) {
        this.kill += kill;
    }
    
    public void addDeath(int death) {
        this.death += death;
    }
    
    public void subKill(int kill) {
        this.kill -= kill;
        if (this.kill < 0) this.kill = 0;
    }
    
    public void subDeath(int death) {
        this.death -= death;
        if (this.death < 0) this.death = 0;
    }
    
    public double getKillDivDeath() {
		if (this.death == 0) return (double) this.kill;
		return API.getDotsecond((double) this.kill / this.death);
    }
    
    public String getclassExpress() {
        return this.express == null ? "§7F" : this.express;
    }
    
    public String getclass() {
        return this.express == null ? "F" : ChatColor.stripColor(this.express);
    }
    
    public void setclass(String express) {
        this.express = express;
    }
}
