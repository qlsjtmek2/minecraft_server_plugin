// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.Random;
import think.rpgitems.power.types.PowerHit;

public class PowerKnockup extends Power implements PowerHit
{
    public int chance;
    public double power;
    private Random rand;
    
    public PowerKnockup() {
        this.chance = 20;
        this.power = 2.0;
        this.rand = new Random();
    }
    
    @Override
    public void hit(final Player player, final LivingEntity e) {
        if (this.rand.nextInt(this.chance) == 0) {
            e.setVelocity(player.getLocation().getDirection().setY(this.power));
        }
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.knockup", locale), (int)(1.0 / this.chance * 100.0));
    }
    
    @Override
    public String getName() {
        return "knockup";
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.chance = s.getInt("chance");
        this.power = s.getDouble("power", 2.0);
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("chance", (Object)this.chance);
        s.set("power", (Object)this.power);
    }
}
