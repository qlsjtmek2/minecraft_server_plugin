// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.Random;
import think.rpgitems.power.types.PowerProjectileHit;
import think.rpgitems.power.types.PowerHit;

public class PowerLightning extends Power implements PowerHit, PowerProjectileHit
{
    public int chance;
    private Random random;
    
    public PowerLightning() {
        this.chance = 20;
        this.random = new Random();
    }
    
    @Override
    public void hit(final Player player, final LivingEntity e) {
        if (this.random.nextInt(this.chance) == 0) {
            e.getWorld().strikeLightning(e.getLocation());
        }
    }
    
    @Override
    public void projectileHit(final Player player, final Projectile p) {
        if (this.random.nextInt(this.chance) == 0) {
            p.getWorld().strikeLightning(p.getLocation());
        }
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.lightning", locale), (int)(1.0 / this.chance * 100.0));
    }
    
    @Override
    public String getName() {
        return "lightning";
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.chance = s.getInt("chance");
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("chance", (Object)this.chance);
    }
}
