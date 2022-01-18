// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.Random;
import think.rpgitems.power.types.PowerHit;

public class PowerUnbreaking extends Power implements PowerHit
{
    public int level;
    private Random random;
    
    public PowerUnbreaking() {
        this.level = 1;
        this.random = new Random();
    }
    
    @Override
    public void hit(final Player player, final LivingEntity e) {
        if (this.random.nextDouble() < this.level / 100.0) {
            System.out.println(player.getItemInHand().getDurability());
            player.getItemInHand().setDurability((short)(player.getItemInHand().getDurability() - 1));
            System.out.println(player.getItemInHand().getDurability());
            player.updateInventory();
        }
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.level = s.getInt("level", 1);
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("level", (Object)this.level);
    }
    
    @Override
    public String getName() {
        return "unbreaking";
    }
    
    @Override
    public String displayText(final String locale) {
        return String.format(ChatColor.GREEN + Locale.get("power.unbreaking", locale), this.level);
    }
}
