// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerRightClick;

@Deprecated
public class PowerRush extends Power implements PowerRightClick
{
    private long cd;
    private int speed;
    private int time;
    
    public PowerRush() {
        this.cd = 20L;
        this.speed = 3;
        this.time = 20;
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue value = RPGValue.get(player, this.item, "rush.cooldown");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "rush.cooldown", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cd);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.time, this.speed));
        }
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.cd = s.getLong("cooldown");
        this.speed = s.getInt("speed");
        this.time = s.getInt("time");
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("cooldown", (Object)this.cd);
        s.set("speed", (Object)this.speed);
        s.set("time", (Object)this.time);
    }
    
    @Override
    public String getName() {
        return "rush";
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + "Gives temporary speed boost";
    }
}
