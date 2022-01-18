// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Projectile;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.World;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerProjectileHit;
import think.rpgitems.power.types.PowerRightClick;

public class PowerTeleport extends Power implements PowerRightClick, PowerProjectileHit
{
    public int distance;
    public long cooldownTime;
    
    public PowerTeleport() {
        this.distance = 5;
        this.cooldownTime = 20L;
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue value = RPGValue.get(player, this.item, "teleport.cooldown");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "teleport.cooldown", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
            final World world = player.getWorld();
            final Location start = player.getLocation();
            start.setY(start.getY() + 1.6);
            Block lastSafe = world.getBlockAt(start);
            final BlockIterator bi = new BlockIterator((LivingEntity)player, this.distance);
            while (bi.hasNext()) {
                final Block block = bi.next();
                if (block.getType().isSolid() && block.getType() != Material.AIR) {
                    break;
                }
                lastSafe = block;
            }
            final Location newLoc = lastSafe.getLocation();
            newLoc.setPitch(start.getPitch());
            newLoc.setYaw(start.getYaw());
            player.teleport(newLoc);
            world.playEffect(newLoc, Effect.ENDER_SIGNAL, 0);
            world.playSound(newLoc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
        }
        else {
            player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        }
    }
    
    @Override
    public void projectileHit(final Player player, final Projectile p) {
        RPGValue value = RPGValue.get(player, this.item, "teleport.cooldown");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "teleport.cooldown", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
            final World world = player.getWorld();
            final Location start = player.getLocation();
            final Location newLoc = p.getLocation();
            if (start.distanceSquared(newLoc) >= this.distance * this.distance) {
                player.sendMessage(ChatColor.AQUA + Locale.get("message.too.far", Locale.getPlayerLocale(player)));
                return;
            }
            newLoc.setPitch(start.getPitch());
            newLoc.setYaw(start.getYaw());
            player.teleport(newLoc);
            world.playEffect(newLoc, Effect.ENDER_SIGNAL, 0);
            world.playSound(newLoc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
        }
        else {
            player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        }
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.cooldownTime = s.getLong("cooldown");
        this.distance = s.getInt("distance");
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("cooldown", (Object)this.cooldownTime);
        s.set("distance", (Object)this.distance);
    }
    
    @Override
    public String getName() {
        return "teleport";
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.teleport", locale), this.distance, this.cooldownTime / 20.0);
    }
}
