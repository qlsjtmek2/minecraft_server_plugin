// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import java.util.Random;
import think.rpgitems.power.types.PowerHit;

public class PowerPotionHit extends Power implements PowerHit
{
    public int chance;
    private Random random;
    public PotionEffectType type;
    public int duration;
    public int amplifier;
    
    public PowerPotionHit() {
        this.chance = 20;
        this.random = new Random();
        this.type = PotionEffectType.HARM;
        this.duration = 20;
        this.amplifier = 1;
    }
    
    @Override
    public void hit(final Player player, final LivingEntity e) {
        if (this.random.nextInt(this.chance) == 0) {
            e.addPotionEffect(new PotionEffect(this.type, this.duration, this.amplifier));
        }
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.potionhit", locale), (int)(1.0 / this.chance * 100.0), this.type.getName().toLowerCase().replace('_', ' '));
    }
    
    @Override
    public String getName() {
        return "potionhit";
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.chance = s.getInt("chance", 20);
        this.duration = s.getInt("duration", 20);
        this.amplifier = s.getInt("amplifier", 1);
        this.type = PotionEffectType.getByName(s.getString("type", PotionEffectType.HARM.getName()));
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("chance", (Object)this.chance);
        s.set("duration", (Object)this.duration);
        s.set("amplifier", (Object)this.amplifier);
        s.set("type", (Object)this.type.getName());
    }
}
