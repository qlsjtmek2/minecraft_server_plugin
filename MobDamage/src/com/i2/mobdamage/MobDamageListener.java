/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Snowball
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package com.i2.mobdamage;

import com.i2.mobdamage.MobDamage;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class MobDamageListener
implements Listener {
    Map<Integer, Integer> mobHealth = new HashMap<Integer, Integer>();
    public MobDamage plugin;

    public MobDamageListener(MobDamage plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Arrow b;
        if (e.getDamager().getType().equals((Object)EntityType.PIG) && this.plugin.getConfig().getInt("MobDamage.Pig") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Pig"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.PIG) && this.plugin.getConfig().getInt("MobDamage.Cow") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Cow"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.SPIDER) && this.plugin.getConfig().getInt("MobDamage.Spider") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Spider"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.CAVE_SPIDER) && this.plugin.getConfig().getInt("MobDamage.CaveSpider") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.CaveSpider"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.ZOMBIE) && this.plugin.getConfig().getInt("MobDamage.Zombie") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Zombie"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.ARROW) && (b = (Arrow)e.getDamager()).getShooter().getType().equals((Object)EntityType.SKELETON) && this.plugin.getConfig().getInt("MobDamage.Skeleton") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Skeleton"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.CREEPER) && this.plugin.getConfig().getInt("MobDamage.Creeper") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Creeper"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.OCELOT) && this.plugin.getConfig().getInt("MobDamage.Ocelot") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Ocelot"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.BAT) && this.plugin.getConfig().getInt("MobDamage.Bat") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Bat"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.CHICKEN) && this.plugin.getConfig().getInt("MobDamage.Chicken") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Chicken"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.MUSHROOM_COW) && this.plugin.getConfig().getInt("MobDamage.Mooshroom") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Mooshroom"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.SHEEP) && this.plugin.getConfig().getInt("MobDamage.Sheep") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Sheep"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.SQUID) && this.plugin.getConfig().getInt("MobDamage.Squid") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Squid"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.VILLAGER) && this.plugin.getConfig().getInt("MobDamage.Villager") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Villager"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.ENDERMAN) && this.plugin.getConfig().getInt("MobDamage.Enderman") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Enderman"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.WOLF) && this.plugin.getConfig().getInt("MobDamage.Wolf") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Wolf"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.PIG_ZOMBIE) && this.plugin.getConfig().getInt("MobDamage.ZombiePigman") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.ZombiePigman"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.BLAZE) && this.plugin.getConfig().getInt("MobDamage.Blaze") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Blaze"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.GHAST) && this.plugin.getConfig().getInt("MobDamage.Ghast") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Ghast"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.MAGMA_CUBE) && this.plugin.getConfig().getInt("MobDamage.MagmaCube") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.MagmaCube"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.SILVERFISH) && this.plugin.getConfig().getInt("MobDamage.Silverfish") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Silverfish"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.SLIME) && this.plugin.getConfig().getInt("MobDamage.Slime") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Slime"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.WITCH) && this.plugin.getConfig().getInt("MobDamage.Witch") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.Witch"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.WITHER_SKULL) && this.plugin.getConfig().getInt("MobDamage.WitherSkeleton") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.WitherSkeleton"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.IRON_GOLEM) && this.plugin.getConfig().getInt("MobDamage.IronGolem") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.IronGolem"));
        }
        if (e.getDamager().getType().equals((Object)EntityType.ENDER_DRAGON) && this.plugin.getConfig().getInt("MobDamage.EnderDragon") != -1) {
            e.setDamage(this.plugin.getConfig().getInt("MobDamage.EnderDragon"));
        }
    }

    public void addEntity(EntityDamageEvent e) {
        if (!this.mobHealth.containsKey(e.getEntity().getEntityId())) {
            if (e.getEntityType().equals((Object)EntityType.COW) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Cow") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Cow"));
            }
            if (e.getEntityType().equals((Object)EntityType.PIG) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Pig") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Pig"));
            }
            if (e.getEntityType().equals((Object)EntityType.SPIDER) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Spider") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Spider"));
            }
            if (e.getEntityType().equals((Object)EntityType.CAVE_SPIDER) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.CaveSpider") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.CaveSpider"));
            }
            if (e.getEntityType().equals((Object)EntityType.ZOMBIE) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Zombie") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Zombie"));
            }
            if (e.getEntityType().equals((Object)EntityType.SKELETON) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Skeleton") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Skeleton"));
            }
            if (e.getEntityType().equals((Object)EntityType.CREEPER) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Creeper") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Creeper"));
            }
            if (e.getEntityType().equals((Object)EntityType.OCELOT) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Ocelot") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Ocelot"));
            }
            if (e.getEntityType().equals((Object)EntityType.BAT) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Bat") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Bat"));
            }
            if (e.getEntityType().equals((Object)EntityType.CHICKEN) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Chicken") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Chicken"));
            }
            if (e.getEntityType().equals((Object)EntityType.MUSHROOM_COW) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Mooshroom") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Mooshroom"));
            }
            if (e.getEntityType().equals((Object)EntityType.SHEEP) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Sheep") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Sheep"));
            }
            if (e.getEntityType().equals((Object)EntityType.SQUID) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Squid") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Squid"));
            }
            if (e.getEntityType().equals((Object)EntityType.VILLAGER) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Villager") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Villager"));
            }
            if (e.getEntityType().equals((Object)EntityType.ENDERMAN) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Enderman") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Enderman"));
            }
            if (e.getEntityType().equals((Object)EntityType.WOLF) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Wolf") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Wolf"));
            }
            if (e.getEntityType().equals((Object)EntityType.PIG_ZOMBIE) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.ZombiePigman") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.ZombiePigman"));
            }
            if (e.getEntityType().equals((Object)EntityType.BLAZE) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Blaze") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Blaze"));
            }
            if (e.getEntityType().equals((Object)EntityType.GHAST) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Ghast") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Ghast"));
            }
            if (e.getEntityType().equals((Object)EntityType.MAGMA_CUBE) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.MagmaCube") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.MagmaCube"));
            }
            if (e.getEntityType().equals((Object)EntityType.SILVERFISH) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Silverfish") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Silverfish"));
            }
            if (e.getEntityType().equals((Object)EntityType.SLIME) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Slime") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Slime"));
            }
            if (e.getEntityType().equals((Object)EntityType.WITCH) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.Witch") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.Witch"));
            }
            if (e.getEntityType().equals((Object)EntityType.WITHER_SKULL) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.WitherSkeleton") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.WitherSkeleton"));
            }
            if (e.getEntityType().equals((Object)EntityType.IRON_GOLEM) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.IronGolem") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.IronGolem"));
            }
            if (e.getEntityType().equals((Object)EntityType.SNOWMAN) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.SnowGolem") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.SnowGolem"));
            }
            if (e.getEntityType().equals((Object)EntityType.ENDER_DRAGON) && !e.isCancelled() && this.plugin.getConfig().getInt("MobHealth.EnderDragon") != -1) {
                this.mobHealth.put(e.getEntity().getEntityId(), this.plugin.getConfig().getInt("MobHealth.EnderDragon"));
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onDamage2(EntityDamageEvent e) {
        this.addEntity(e);
        if (e.getEntityType().equals((Object)EntityType.PIG) && this.plugin.getConfig().getInt("MobHealth.Pig") != -1 || e.getEntityType().equals((Object)EntityType.COW) && this.plugin.getConfig().getInt("MobHealth.Cow") != -1 || e.getEntityType().equals((Object)EntityType.SPIDER) && this.plugin.getConfig().getInt("MobHealth.Spider") != -1 || e.getEntityType().equals((Object)EntityType.CAVE_SPIDER) && this.plugin.getConfig().getInt("MobHealth.Cavepider") != -1 || e.getEntityType().equals((Object)EntityType.ZOMBIE) && this.plugin.getConfig().getInt("MobHealth.Zombie") != -1 || e.getEntityType().equals((Object)EntityType.SKELETON) && this.plugin.getConfig().getInt("MobHealth.Skeleton") != -1 || e.getEntityType().equals((Object)EntityType.CREEPER) && this.plugin.getConfig().getInt("MobHealth.Creeper") != -1 || e.getEntityType().equals((Object)EntityType.OCELOT) && this.plugin.getConfig().getInt("MobHealth.Ocelot") != -1 || e.getEntityType().equals((Object)EntityType.BAT) && this.plugin.getConfig().getInt("MobHealth.Bat") != -1 || e.getEntityType().equals((Object)EntityType.CHICKEN) && this.plugin.getConfig().getInt("MobHealth.Chicken") != -1 || e.getEntityType().equals((Object)EntityType.MUSHROOM_COW) && this.plugin.getConfig().getInt("MobHealth.Mooshroom") != -1 || e.getEntityType().equals((Object)EntityType.SHEEP) && this.plugin.getConfig().getInt("MobHealth.Sheep") != -1 || e.getEntityType().equals((Object)EntityType.SQUID) && this.plugin.getConfig().getInt("MobHealth.Squid") != -1 || e.getEntityType().equals((Object)EntityType.VILLAGER) && this.plugin.getConfig().getInt("MobHealth.Villager") != -1 || e.getEntityType().equals((Object)EntityType.ENDERMAN) && this.plugin.getConfig().getInt("MobHealth.Enderman") != -1 || e.getEntityType().equals((Object)EntityType.WOLF) && this.plugin.getConfig().getInt("MobHealth.Wolf") != -1 || e.getEntityType().equals((Object)EntityType.PIG_ZOMBIE) && this.plugin.getConfig().getInt("MobHealth.ZombiePigman") != -1 || e.getEntityType().equals((Object)EntityType.BLAZE) && this.plugin.getConfig().getInt("MobHealth.Blaze") != -1 || e.getEntityType().equals((Object)EntityType.GHAST) && this.plugin.getConfig().getInt("MobHealth.Ghast") != -1 || e.getEntityType().equals((Object)EntityType.MAGMA_CUBE) && this.plugin.getConfig().getInt("MobHealth.MagmaCube") != -1 || e.getEntityType().equals((Object)EntityType.SILVERFISH) && this.plugin.getConfig().getInt("MobHealth.Silverfish") != -1 || e.getEntityType().equals((Object)EntityType.SLIME) && this.plugin.getConfig().getInt("MobHealth.Slime") != -1 || e.getEntityType().equals((Object)EntityType.WITCH) && this.plugin.getConfig().getInt("MobHealth.Witch") != -1 || e.getEntityType().equals((Object)EntityType.WITHER_SKULL) && this.plugin.getConfig().getInt("MobHealth.WitherSkeleton") != -1 || e.getEntityType().equals((Object)EntityType.IRON_GOLEM) && this.plugin.getConfig().getInt("MobHealth.IronGolem") != -1 && this.plugin.getConfig().getInt("MobHealth.EnderDragon") != -1) {
            int q = this.mobHealth.get(e.getEntity().getEntityId());
            if (q - e.getDamage() > 0) {
                this.mobHealth.put(e.getEntity().getEntityId(), q - e.getDamage());
                e.setDamage(0);
            }
            if (q - e.getDamage() < 1) {
                e.setDamage(1000000);
                this.mobHealth.remove(e.getEntity().getEntityId());
            }
        }
    }
}

