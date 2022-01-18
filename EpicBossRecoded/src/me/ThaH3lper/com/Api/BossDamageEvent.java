// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Api;

import org.bukkit.event.HandlerList;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.entity.Player;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.event.Event;

public class BossDamageEvent extends Event
{
    private EpicBoss eb;
    private Player player;
    private Boss boss;
    private int damage;
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public BossDamageEvent(final EpicBoss neb, final Player p, final Boss b, final int d) {
        this.eb = neb;
        this.player = p;
        this.boss = b;
        this.damage = d;
    }
    
    public Boss getBoss() {
        return this.boss;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    public String getBossName() {
        return this.boss.getName();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public HandlerList getHandlers() {
        return BossDamageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BossDamageEvent.handlers;
    }
}
