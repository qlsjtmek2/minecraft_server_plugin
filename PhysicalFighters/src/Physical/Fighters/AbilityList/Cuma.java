// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Cuma extends AbilityBase
{
    public Cuma() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ubc14\uc194\ub85c\ubba4 \ucfe0\ub9c8", Type.Passive_AutoMatic, Rank.S, "\uc77c\uc815 \ud655\ub960\ub974 \ubc1b\uc740 \uacf5\uaca9\uc744 \uc0c1\ub300\uc5d0\uac8c \ub418\ub3cc\ub824\uc8fc\uba70, \uacf5\uaca9\ubc1b\uc744\uc2dc \uc0c1\ub300\ub97c \ub4a4\ub85c \ub109\ubc31\uc2dc\ud0b5\ub2c8\ub2e4.");
            this.InitAbility(0, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (this.PlayerCheck(Event.getEntity())) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        final Player pn = (Player)Event.getDamager();
        if (Math.random() <= 0.15) {
            pn.damage(Event.getDamage());
            Event.setCancelled(true);
        }
        pn.getWorld().createExplosion(pn.getLocation(), 0.0f);
        pn.setVelocity(pn.getVelocity().add(p.getLocation().toVector().subtract(pn.getLocation().toVector()).normalize().multiply(-1)));
    }
}
