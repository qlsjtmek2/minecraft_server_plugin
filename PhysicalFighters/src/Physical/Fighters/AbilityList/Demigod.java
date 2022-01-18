// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Demigod extends AbilityBase
{
    public Demigod() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ub370\ubbf8\uac13", Type.Passive_AutoMatic, Rank.S, "\ubc18\uc740 \uc778\uac04, \ubc18\uc740 \uc2e0\uc778 \ub2a5\ub825\uc790\uc785\ub2c8\ub2e4.", "\ub370\ubbf8\uc9c0\ub97c \ubc1b\uc73c\uba74 \uc77c\uc815 \ud655\ub960\ub85c 10\ucd08\uac04 \ub79c\ub364 \ubc84\ud504\uac00 \ubc1c\ub3d9\ub429\ub2c8\ub2e4.");
            this.InitAbility(0, 0, true);
            EventManager.onEntityDamage.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        if (!EventManager.DamageGuard && this.PlayerCheck(Event.getEntity())) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        final Player p1 = (Player)Event.getEntity();
        if (!p1.getWorld().getName().equalsIgnoreCase("world")) {
            if (Math.random() <= 0.05) {
                p1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0), true);
            }
            if (Math.random() <= 0.05) {
                p1.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0), true);
            }
            if (Math.random() <= 0.1) {
                p1.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 0), true);
            }
            if (Math.random() <= 0.1) {
                p1.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 0), true);
            }
            if (Math.random() <= 0.1) {
                p1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0), true);
            }
            if (Math.random() <= 0.05) {
                p1.setHealth(p1.getHealth() + 1);
            }
        }
    }
}
