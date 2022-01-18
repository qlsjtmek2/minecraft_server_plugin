// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Api;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import me.ThaH3lper.com.Boss.Boss;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.event.Event;

public class BossSkillEvent extends Event
{
    private EpicBoss eb;
    private String name;
    private Boolean custom;
    private Boss boss;
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public BossSkillEvent(final EpicBoss neb, final Boss b, final String nname, final Boolean ncustom) {
        this.eb = neb;
        this.boss = b;
        this.name = nname;
        this.custom = ncustom;
    }
    
    public String getBossName() {
        return this.boss.getName();
    }
    
    public String getSkillName() {
        return this.name;
    }
    
    public boolean isCustomSkill() {
        return this.custom;
    }
    
    public LivingEntity getBoss() {
        return this.boss.getLivingEntity();
    }
    
    public HandlerList getHandlers() {
        return BossSkillEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BossSkillEvent.handlers;
    }
}
