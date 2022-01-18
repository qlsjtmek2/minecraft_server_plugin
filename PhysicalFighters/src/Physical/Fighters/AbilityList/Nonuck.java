// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Nonuck extends AbilityBase
{
    public Nonuck() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ubb34\ud1b5\uc99d", Type.Passive_AutoMatic, Rank.S, "\ud50c\ub808\uc774\uc5b4\uc5d0\uac8c \ud0c0\uaca9\ub2f9\ud560\uc2dc\uc5d0 80%\ud655\ub960\ub85c \ub109\ubc31\uc744 \ubb34\uc2dc\ud569\ub2c8\ub2e4.");
            this.InitAbility(0, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (this.PlayerCheck(Event.getEntity()) && (Event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || Event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) && Math.random() <= 0.8 && !EventManager.DamageGuard) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        final int damage = Event.getDamage();
        p.damage(damage);
        Event.setCancelled(true);
    }
}
