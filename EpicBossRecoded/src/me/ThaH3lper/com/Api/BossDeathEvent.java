// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Api;

import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.entity.Player;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.event.Event;

public class BossDeathEvent extends Event
{
    private EpicBoss eb;
    private Player player;
    private Boss boss;
    private List<ItemStack> Drops;
    private int exp;
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public BossDeathEvent(final EpicBoss neb, final Player p, final Boss b, final List<ItemStack> nlist, final int nexp) {
        this.eb = neb;
        this.exp = nexp;
        this.player = p;
        this.boss = b;
        this.Drops = nlist;
    }
    
    public Boss getBoss() {
        return this.boss;
    }
    
    public String getBossName() {
        return this.boss.getName();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public List<ItemStack> getDrops() {
        return this.Drops;
    }
    
    public int getExp() {
        return this.exp;
    }
    
    public void setExp(final int i) {
        this.exp = i;
    }
    
    public HandlerList getHandlers() {
        return BossDeathEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BossDeathEvent.handlers;
    }
}
