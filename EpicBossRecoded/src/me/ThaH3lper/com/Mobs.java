// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com;

import org.bukkit.entity.Cow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Slime;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.Location;

public class Mobs
{
    public Entity SpawnMob(final String s, final Location l) {
        final String[] part = s.split(":");
        final String mobname = part[0];
        if (part.length == 2) {
            if (mobname.equals("sheep")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.SHEEP);
                final Sheep sheep = (Sheep)liver;
                sheep.setColor(DyeColor.getByData(Byte.parseByte(part[1])));
                return liver;
            }
        }
        else {
            if (mobname.equals("creeper")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.CREEPER);
                return liver;
            }
            if (mobname.equals("wither")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.WITHER);
                return liver;
            }
            if (mobname.equals("enderdragon")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.ENDER_DRAGON);
                return liver;
            }
            if (mobname.equals("witherskeleton")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.SKELETON);
                final Skeleton ske = (Skeleton)liver;
                ske.setSkeletonType(Skeleton.SkeletonType.WITHER);
                return liver;
            }
            if (mobname.equals("zombiebaby")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
                final Zombie zm = (Zombie)liver;
                zm.setBaby(true);
                return liver;
            }
            if (mobname.equals("zombievillager")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
                final Zombie zm = (Zombie)liver;
                zm.setVillager(true);
                return liver;
            }
            if (mobname.equals("bat")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.BAT);
                return liver;
            }
            if (mobname.equals("witch")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.WITCH);
                return liver;
            }
            if (mobname.equals("mushroomcow")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.MUSHROOM_COW);
                return liver;
            }
            if (mobname.equals("mushroomcowbaby")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.MUSHROOM_COW);
                final MushroomCow mu = (MushroomCow)liver;
                mu.setBaby();
                return liver;
            }
            if (mobname.equals("squid")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.SQUID);
                return liver;
            }
            if (mobname.equals("skeleton")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.SKELETON);
                return liver;
            }
            if (mobname.equals("ghast")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.GHAST);
                return liver;
            }
            if (mobname.equals("blaze")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.BLAZE);
                return liver;
            }
            if (mobname.equals("zombie")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
                return liver;
            }
            if (mobname.equals("slime")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.SLIME);
                final Slime slime = (Slime)liver;
                slime.setSize(5);
                return liver;
            }
            if (mobname.equals("wolf")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.WOLF);
                return liver;
            }
            if (mobname.equals("angrywolf")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.WOLF);
                final Wolf wolf = (Wolf)liver;
                wolf.setAngry(true);
                return liver;
            }
            if (mobname.equals("irongolem")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.IRON_GOLEM);
                return liver;
            }
            if (mobname.equals("pig")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.PIG);
                return liver;
            }
            if (mobname.equals("villager")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.VILLAGER);
                return liver;
            }
            if (mobname.equals("ocelot")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.OCELOT);
                return liver;
            }
            if (mobname.equals("chicken")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.CHICKEN);
                return liver;
            }
            if (mobname.equals("chickenbaby")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.CHICKEN);
                final Chicken ch = (Chicken)liver;
                ch.setBaby();
                return liver;
            }
            if (mobname.equals("cow")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.COW);
                return liver;
            }
            if (mobname.equals("cowbaby")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.COW);
                final Cow ch2 = (Cow)liver;
                ch2.setBaby();
                return liver;
            }
            if (mobname.equals("spider")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.SPIDER);
                return liver;
            }
            if (mobname.equals("enderman")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.ENDERMAN);
                return liver;
            }
            if (mobname.equals("cavespider")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.CAVE_SPIDER);
                return liver;
            }
            if (mobname.equals("giant")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.GIANT);
                return liver;
            }
            if (mobname.equals("silverfish")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.SILVERFISH);
                return liver;
            }
            if (mobname.equals("magmacube")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.MAGMA_CUBE);
                return liver;
            }
            if (mobname.equals("pigzombie")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
                return liver;
            }
            if (mobname.equals("sheep")) {
                final Entity liver = l.getWorld().spawnEntity(l, EntityType.SHEEP);
                return liver;
            }
        }
        return null;
    }
}
