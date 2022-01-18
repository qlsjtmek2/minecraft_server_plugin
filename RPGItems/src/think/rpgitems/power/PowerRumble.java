// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import think.rpgitems.Plugin;
import org.bukkit.entity.Entity;
import org.bukkit.block.Block;
import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.power.types.PowerRightClick;

public class PowerRumble extends Power implements PowerRightClick
{
    public long cooldownTime;
    public int power;
    public int distance;
    
    public PowerRumble() {
        this.cooldownTime = 20L;
        this.power = 2;
        this.distance = 15;
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.cooldownTime = s.getLong("cooldown", 20L);
        this.power = s.getInt("power", 2);
        this.distance = s.getInt("distance", 15);
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("cooldown", (Object)this.cooldownTime);
        s.set("power", (Object)this.power);
        s.set("distance", (Object)this.distance);
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue value = RPGValue.get(player, this.item, "rumble.cooldown");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "rumble.cooldown", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
            final Location location = player.getLocation().add(0.0, -0.2, 0.0);
            final Vector direction = player.getLocation().getDirection();
            direction.setY(0);
            direction.normalize();
            final BukkitRunnable task = new BukkitRunnable() {
                private int count = 0;
                
                public void run() {
                    final Location above = location.clone().add(0.0, 1.0, 0.0);
                    if (above.getBlock().getType().isSolid() || !location.getBlock().getType().isSolid()) {
                        this.cancel();
                        return;
                    }
                    final Location temp = location.clone();
                    for (int x = -2; x <= 2; ++x) {
                        for (int z = -2; z <= 2; ++z) {
                            temp.setX((double)(x + location.getBlockX()));
                            temp.setZ((double)(z + location.getBlockZ()));
                            final Block block = temp.getBlock();
                            temp.getWorld().playEffect(temp, Effect.STEP_SOUND, block.getTypeId());
                        }
                    }
                    Entity[] near = Power.getNearbyEntities(location, 1.5);
                    boolean hit = false;
                    final Random random = new Random();
                    Entity[] array;
                    for (int length = (array = near).length, i = 0; i < length; ++i) {
                        final Entity e = array[i];
                        if (e != player) {
                            hit = true;
                            break;
                        }
                    }
                    if (hit) {
                        location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), (float)PowerRumble.this.power, false, false);
                        near = Power.getNearbyEntities(location, 2.5);
                        Entity[] array2;
                        for (int length2 = (array2 = near).length, j = 0; j < length2; ++j) {
                            final Entity e = array2[j];
                            if (e != player) {
                                e.setVelocity(new Vector(random.nextGaussian() / 4.0, 1.0 + random.nextDouble() * PowerRumble.this.power, random.nextGaussian() / 4.0));
                            }
                        }
                        this.cancel();
                        return;
                    }
                    location.add(direction);
                    if (this.count >= PowerRumble.this.distance) {
                        this.cancel();
                    }
                    ++this.count;
                }
            };
            task.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.plugin, 0L, 3L);
        }
        else {
            player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        }
    }
    
    @Override
    public String getName() {
        return "rumble";
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.rumble", locale), this.cooldownTime / 20.0);
    }
}
