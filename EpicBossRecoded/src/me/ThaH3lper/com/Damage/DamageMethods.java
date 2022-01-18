// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Damage;

import org.bukkit.inventory.ItemStack;
import java.util.List;
import me.ThaH3lper.com.Boss.Boss;
import me.ThaH3lper.com.EpicBoss;

public class DamageMethods
{
    private EpicBoss eb;
    
    public DamageMethods(final EpicBoss boss) {
        this.eb = boss;
    }
    
    public void deathBoss(final Boss b, final List<ItemStack> stack, final int exp) {
        this.eb.timerstuff.Death(b);
        this.eb.dropitems.dropItems(stack, b, exp);
        if (b.getLivingEntity() != null) {
            b.getLivingEntity().remove();
        }
        this.eb.BossList.remove(b);
    }
}
