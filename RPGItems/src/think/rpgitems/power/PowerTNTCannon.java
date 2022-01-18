// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.Sound;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerRightClick;

public class PowerTNTCannon extends Power implements PowerRightClick
{
    public long cooldownTime;
    
    public PowerTNTCannon() {
        this.cooldownTime = 20L;
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue value = RPGValue.get(player, this.item, "tnt.cooldown");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "tnt.cooldown", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            final TNTPrimed tnt = (TNTPrimed)player.getWorld().spawn(player.getLocation().add(0.0, 1.8, 0.0), (Class)TNTPrimed.class);
            tnt.setVelocity(player.getLocation().getDirection().multiply(2.0));
        }
        else {
            player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        }
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.tntcannon", locale), this.cooldownTime / 20.0);
    }
    
    @Override
    public String getName() {
        return "tntcannon";
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
