// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.block.Block;
import think.rpgitems.Plugin;
import org.bukkit.util.Vector;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import java.util.HashSet;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import think.rpgitems.power.types.PowerRightClick;

public class PowerSkyHook extends Power implements PowerRightClick
{
    public Material railMaterial;
    public int hookDistance;
    
    public PowerSkyHook() {
        this.railMaterial = Material.GLASS;
        this.hookDistance = 10;
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue isHooking = RPGValue.get(player, this.item, "skyhook.isHooking");
        if (isHooking == null) {
            isHooking = new RPGValue(player, this.item, "skyhook.isHooking", false);
        }
        if (isHooking.asBoolean()) {
            player.setVelocity(player.getLocation().getDirection());
            isHooking.set(false);
            return;
        }
        final Block block = player.getTargetBlock((HashSet)null, this.hookDistance);
        if (block.getType() != this.railMaterial) {
            player.sendMessage(ChatColor.AQUA + Locale.get("message.skyhook.fail", Locale.getPlayerLocale(player)));
            return;
        }
        isHooking.set(true);
        final Location location = player.getLocation();
        player.setAllowFlight(true);
        player.setVelocity(location.getDirection().multiply(block.getLocation().distance(location) / 2.0));
        player.setFlying(true);
        new BukkitRunnable() {
            private int delay = 0;
            
            public void run() {
                if (!player.getAllowFlight()) {
                    this.cancel();
                    RPGValue.get(player, PowerSkyHook.this.item, "skyhook.isHooking").set(false);
                    return;
                }
                if (!RPGValue.get(player, PowerSkyHook.this.item, "skyhook.isHooking").asBoolean()) {
                    player.setFlying(false);
                    if (player.getGameMode() != GameMode.CREATIVE) {
                        player.setAllowFlight(false);
                    }
                    this.cancel();
                    return;
                }
                player.setFlying(true);
                player.getLocation(location);
                location.add(0.0, 2.4, 0.0);
                if (this.delay < 20) {
                    ++this.delay;
                    if (location.getBlock().getType() == PowerSkyHook.this.railMaterial) {
                        this.delay = 20;
                    }
                    return;
                }
                final Vector dir = location.getDirection().setY(0).normalize();
                location.add(dir);
                if (location.getBlock().getType() != PowerSkyHook.this.railMaterial) {
                    player.setFlying(false);
                    if (player.getGameMode() != GameMode.CREATIVE) {
                        player.setAllowFlight(false);
                    }
                    this.cancel();
                    RPGValue.get(player, PowerSkyHook.this.item, "skyhook.isHooking").set(false);
                    return;
                }
                player.setVelocity(dir.multiply(0.5));
            }
        }.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.plugin, 0L, 0L);
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.railMaterial = Material.valueOf(s.getString("railMaterial", "GLASS"));
        this.hookDistance = s.getInt("hookDistance", 10);
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("railMaterial", (Object)this.railMaterial.toString());
        s.set("hookDistance", (Object)this.hookDistance);
    }
    
    @Override
    public String getName() {
        return "skyhook";
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + Locale.get("power.skyhook", locale);
    }
}
