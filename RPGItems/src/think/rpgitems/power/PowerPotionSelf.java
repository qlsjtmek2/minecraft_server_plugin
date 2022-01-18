// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import think.rpgitems.power.types.PowerRightClick;

public class PowerPotionSelf extends Power implements PowerRightClick
{
    public long cooldownTime;
    public int amplifier;
    public int time;
    public PotionEffectType type;
    
    public PowerPotionSelf() {
        this.cooldownTime = 20L;
        this.amplifier = 3;
        this.time = 20;
        this.type = PotionEffectType.HEAL;
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue value = RPGValue.get(player, this.item, "potionself.cooldown");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "potionself.cooldown", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
            player.addPotionEffect(new PotionEffect(this.type, this.time, this.amplifier));
        }
        else {
            player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        }
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.cooldownTime = s.getLong("cooldown");
        this.amplifier = s.getInt("amp");
        this.time = s.getInt("time");
        this.type = PotionEffectType.getByName(s.getString("type", "heal"));
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("cooldown", (Object)this.cooldownTime);
        s.set("amp", (Object)this.amplifier);
        s.set("time", (Object)this.time);
        s.set("type", (Object)this.type.getName());
    }
    
    @Override
    public String getName() {
        return "potionself";
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.potionself", locale), this.type.getName().toLowerCase().replaceAll("_", " "), this.amplifier + 1, this.time / 20.0);
    }
}
