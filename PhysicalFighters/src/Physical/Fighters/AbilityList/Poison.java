// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Poison extends AbilityBase
{
    public Poison() {
        this.InitAbility("\ud3ec\uc774\uc98c", Type.Passive_AutoMatic, Rank.B, "\uc790\uc2e0\uc5d0\uac8c \uacf5\uaca9\ubc1b\uc740 \uc0ac\ub78c\uc740 3\ucd08\uac04 \ub3c5\uc5d0 \uac10\uc5fc\ub429\ub2c8\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (this.PlayerCheck(Event.getDamager()) && Event.getEntity() instanceof Player) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10, 2), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10, 2), false);
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10, 2), false);
        }
    }
}
