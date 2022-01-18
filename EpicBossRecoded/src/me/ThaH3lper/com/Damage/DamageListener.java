// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Damage;

import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Arrow;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.ThaH3lper.com.Api.BossDamageEvent;
import me.ThaH3lper.com.Api.BossDeathEvent;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.event.Listener;

public class DamageListener implements Listener
{
    private EpicBoss eb;
    BossDeathEvent event;
    BossDamageEvent event2;
    
    public DamageListener(final EpicBoss boss) {
        this.eb = boss;
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void Damage(final EntityDamageByEntityEvent e) {
        if (!this.eb.HeroesEnabled) {
            if (this.DamageMethod(e.getDamager(), e.getEntity(), e.getDamage())) {
                e.setCancelled(true);
            }
            if (this.eb.bossCalculator.isBoss(e.getDamager())) {
                final Boss boss = this.eb.bossCalculator.getBoss(e.getDamager());
                e.setDamage(boss.getDamage());
            }
            else if (this.eb.bossCalculator.isBoss(e.getEntity())) {
                e.setDamage(1);
                this.event2 = new BossDamageEvent(this.eb, (Player)e.getDamager(), this.eb.bossCalculator.getBoss(e.getEntity()), e.getDamage());
                Bukkit.getServer().getPluginManager().callEvent((Event)this.event2);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void BossNoLose(final EntityDamageEvent event) {
        if (event.getEntity() != null && this.eb.bossCalculator.isBoss(event.getEntity()) && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE && event.getCause() != EntityDamageEvent.DamageCause.MAGIC && event.getCause() != EntityDamageEvent.DamageCause.CUSTOM) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void BossNoFire(final EntityCombustEvent e) {
        if (this.eb.bossCalculator.isBoss(e.getEntity())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void NoTaming(final EntityTameEvent e) {
        if (this.eb.bossCalculator.isBoss((Entity)e.getEntity())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void NoBlowCreeper(final EntityExplodeEvent e) {
        this.eb.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.eb, (Runnable)new Runnable() {
            @Override
            public void run() {
                DamageListener.this.eb.timer.despawn.DeSpawnEvent(DamageListener.this.eb);
            }
        }, 1L);
    }
    
    public boolean DamageMethod(Entity Damager, final Entity Hited, final int damage) {
        if (Damager instanceof Arrow) {
            final Arrow a = (Arrow)Damager;
            Damager = (Entity)a.getShooter();
            if (Damager == null && this.eb.bossCalculator.isBoss(Hited)) {
                return true;
            }
            if (Damager == null) {
                return false;
            }
        }
        if (Damager instanceof Fireball) {
            final Fireball a2 = (Fireball)Damager;
            Damager = (Entity)a2.getShooter();
        }
        if (Damager instanceof SmallFireball) {
            final SmallFireball a3 = (SmallFireball)Damager;
            Damager = (Entity)a3.getShooter();
        }
        if (Damager instanceof Snowball) {
            final Snowball a4 = (Snowball)Damager;
            Damager = (Entity)a4.getShooter();
        }
        if (Damager instanceof Egg) {
            final Egg a5 = (Egg)Damager;
            Damager = (Entity)a5.getShooter();
        }
        if (Damager instanceof ThrownPotion) {
            final ThrownPotion a6 = (ThrownPotion)Damager;
            Damager = (Entity)a6.getShooter();
        }
        if (Damager instanceof LivingEntity && Hited instanceof LivingEntity) {
            if (!this.eb.bossCalculator.isBoss(Damager) && !this.eb.bossCalculator.isBoss(Hited)) {
                return false;
            }
            final LivingEntity hited = (LivingEntity)Hited;
            if (this.eb.bossCalculator.isBoss(Hited) && !this.eb.bossCalculator.BossHited(hited)) {
                final Boss boss = this.eb.bossCalculator.getBoss(Hited);
                boss.sethealth(boss.getHealth() - damage);
                hited.setHealth(hited.getMaxHealth());
                if (Damager instanceof Player) {
                    this.eb.skillhandler.skills(boss, (Player)Damager);
                }
                if (boss.getHealth() <= 0) {
                    final List<ItemStack> dropItems = this.eb.dropitems.getDrops(boss);
                    final int exp = this.eb.dropitems.getExp(boss);
                    final int hexp = this.eb.dropitems.getHeroesExp(boss);
                    if (this.eb.HeroesEnabled && Damager instanceof Player) {
                        this.eb.heroes.getCharacterManager().getHero((Player)Damager).addExp((double)hexp, this.eb.heroes.getCharacterManager().getHero((Player)Damager).getHeroClass(), boss.getLocation());
                    }
                    if (Damager instanceof Player) {
                        this.event = new BossDeathEvent(this.eb, (Player)Damager, boss, dropItems, exp);
                        Bukkit.getServer().getPluginManager().callEvent((Event)this.event);
                    }
                    this.eb.damagemethods.deathBoss(boss, dropItems, this.event.getExp());
                }
                else if (boss.getShowHp() && Damager instanceof Player) {
                    final Player p = (Player)Damager;
                    if (!this.eb.percentage) {
                        String bossName = boss.getName();
                        bossName = bossName.replace("_", " ");
                        final String s = String.valueOf(String.valueOf(this.eb.name)) + ChatColor.RED + bossName + ChatColor.GRAY + " [" + ChatColor.DARK_RED + boss.getHealth() + ChatColor.GRAY + "/" + ChatColor.DARK_RED + boss.getMaxHealth() + ChatColor.GRAY + "]";
                        p.sendMessage(s);
                    }
                    else {
                        final double per = boss.getHealth() / boss.getMaxHealth() * 10.0 + 1.0;
                        if (!boss.hasPercent((int)per)) {
                            String bossName2 = boss.getName();
                            bossName2 = bossName2.replace("_", " ");
                            final String s2 = String.valueOf(String.valueOf(this.eb.name)) + ChatColor.RED + bossName2 + ChatColor.GRAY + " [" + ChatColor.DARK_RED + (int)per * 10 + "%" + ChatColor.GRAY + "]";
                            for (final Player player : this.eb.skillhandler.getPlayersRadious(20, boss)) {
                                player.sendMessage(s2);
                            }
                            boss.addPercent((int)per);
                        }
                    }
                }
            }
        }
        return false;
    }
}
