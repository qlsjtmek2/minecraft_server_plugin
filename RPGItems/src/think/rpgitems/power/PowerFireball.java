// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.entity.SmallFireball;
import org.bukkit.Sound;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerRightClick;

public class PowerFireball extends Power implements PowerRightClick
{
    public long cooldownTime;
    
    public PowerFireball() {
        this.cooldownTime = 20L;
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue value = RPGValue.get(player, this.item, "tnt.fireball");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "tnt.fireball", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
            player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
            player.launchProjectile((Class)SmallFireball.class);
        }
        else {
            player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        }
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.fireball", locale), this.cooldownTime / 20.0);
    }
    
    @Override
    public String getName() {
        return "fireball";
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.cooldownTime = s.getLong("cooldown", 20L);
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("cooldown", (Object)this.cooldownTime);
    }
}
