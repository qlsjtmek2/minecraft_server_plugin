// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerHit;

public class PowerFlame extends Power implements PowerHit
{
    public int burnTime;
    
    public PowerFlame() {
        this.burnTime = 20;
    }
    
    @Override
    public void hit(final Player player, final LivingEntity e) {
        e.setFireTicks(this.burnTime);
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.flame", locale), this.burnTime / 20.0);
    }
    
    @Override
    public String getName() {
        return "flame";
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.burnTime = s.getInt("burntime");
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("burntime", (Object)this.burnTime);
    }
}
