// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Api;

import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import me.ThaH3lper.com.EpicBoss;

public class Api
{
    public EpicBoss eb;
    
    public Api(final EpicBoss neweb) {
        this.eb = neweb;
    }
    
    public boolean isBoss(final LivingEntity l) {
        return this.eb.bossCalculator.isBossLiv(l);
    }
    
    public String getBossName(final LivingEntity l) {
        final Boss b = this.eb.bossCalculator.getBoss((Entity)l);
        return b.getName();
    }
    
    public int getMaxHealth(final LivingEntity l) {
        final Boss b = this.eb.bossCalculator.getBoss((Entity)l);
        return b.getMaxHealth();
    }
    
    public int getHealth(final LivingEntity l) {
        final Boss b = this.eb.bossCalculator.getBoss((Entity)l);
        return b.getHealth();
    }
    
    public boolean isShowingHp(final LivingEntity l) {
        final Boss b = this.eb.bossCalculator.getBoss((Entity)l);
        return b.getShowHp();
    }
    
    public void addNewSkill(final String name) {
        this.eb.CustomSkills.add(name);
    }
}
