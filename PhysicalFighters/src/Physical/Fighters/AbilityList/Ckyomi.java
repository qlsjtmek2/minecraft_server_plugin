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

public class Ckyomi extends AbilityBase
{
    public Ckyomi() {
        this.InitAbility("\uce20\ucfe0\uc694\ubbf8", Type.Passive_AutoMatic, Rank.A, "\uc0c1\ub300\ub97c \uacf5\uaca9\ud558\uba74 \uc0c1\ub300\uc5d0\uac8c 5\ucd08\uac04 \ud63c\ub780\ud6a8\uacfc\uc640 \ub514\ubc84\ud504\ub97c \uc90d\ub2c8\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (!EventManager.DamageGuard && this.PlayerCheck(Event.getDamager()) && Event.getEntity() instanceof Player) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
        	p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0), true);
        	p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0), true);
        	p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0), true);
        }
    }
}
