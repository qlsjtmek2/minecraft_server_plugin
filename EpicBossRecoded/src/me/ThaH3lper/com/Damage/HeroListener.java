// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Damage;

import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.entity.Entity;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.event.Listener;

public class HeroListener implements Listener
{
    private EpicBoss eb;
    private DamageListener dl;
    
    public HeroListener(final EpicBoss boss, final DamageListener dl) {
        this.eb = boss;
        this.dl = dl;
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void HeroWeapon(final WeaponDamageEvent e) {
        if (this.dl.DamageMethod((Entity)e.getDamager().getEntity(), e.getEntity(), e.getDamage())) {
            e.setCancelled(true);
        }
        if (this.eb.bossCalculator.isBoss((Entity)e.getDamager().getEntity())) {
            final Boss boss = this.eb.bossCalculator.getBoss((Entity)e.getDamager().getEntity());
            e.setDamage(boss.getDamage());
        }
        else if (this.eb.bossCalculator.isBoss(e.getEntity())) {
            e.setDamage(1);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void HeroSkillDamage(final SkillDamageEvent e) {
        if (this.dl.DamageMethod((Entity)e.getDamager().getEntity(), e.getEntity(), e.getDamage())) {
            e.setCancelled(true);
        }
        if (this.eb.bossCalculator.isBoss((Entity)e.getDamager().getEntity())) {
            final Boss boss = this.eb.bossCalculator.getBoss((Entity)e.getDamager().getEntity());
            e.setDamage(boss.getDamage());
        }
        else if (this.eb.bossCalculator.isBoss(e.getEntity())) {
            e.setDamage(1);
        }
    }
}
