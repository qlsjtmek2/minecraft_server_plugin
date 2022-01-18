// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import think.rpgitems.power.types.PowerTick;

public class PowerPotionTick extends Power implements PowerTick
{
    public int amplifier;
    public PotionEffectType effect;
    
    public PowerPotionTick() {
        this.amplifier = 2;
        this.effect = PotionEffectType.SPEED;
    }
    
    @Override
    public void tick(final Player player) {
        boolean hasEffect = false;
        for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().equals((Object)this.effect) && potionEffect.getDuration() == 25) {
                player.addPotionEffect(new PotionEffect(this.effect, 30, this.amplifier, true), true);
                hasEffect = true;
                break;
            }
        }
        if (!hasEffect) {
            player.addPotionEffect(new PotionEffect(this.effect, 30, this.amplifier, true), true);
        }
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.amplifier = s.getInt("amplifier");
        this.effect = PotionEffectType.getByName(s.getString("effect", "heal"));
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("amplifier", (Object)this.amplifier);
        s.set("effect", (Object)this.effect.getName());
    }
    
    @Override
    public String getName() {
        return "potiontick";
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.potiontick", locale), this.effect.getName().toLowerCase().replaceAll("_", " "), this.amplifier + 1);
    }
}
