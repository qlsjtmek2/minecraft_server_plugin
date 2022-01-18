// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class NuclearPunch extends AbilityBase
{
    public NuclearPunch() {
        this.InitAbility("\ud575 \ud380\uce58", Type.Active_Immediately, Rank.A, "\ucca0\uad34\ub85c \ud0c0\uaca9\uc744 \ub2f9\ud55c \uc0c1\ub300\uac00 \ub9e4\uc6b0 \uba40\ub9ac \ub109\ubc31\ub2f9\ud569\ub2c8\ub2e4.", "\ub3d9\uc2dc\uc5d0 \ub370\ubbf8\uc9c0 20\uc744 \ubc1b\uc2b5\ub2c8\ub2e4.");
        this.InitAbility(45, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (this.PlayerCheck(Event.getDamager()) && this.ItemCheck(ACC.DefaultItem)) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        Event.setDamage(8);
        Event.getEntity().getWorld().createExplosion(Event.getEntity().getLocation(), 0.0f);
        int knockback = -24;
        if (Event.getEntity() instanceof Player) {
            final Player p = (Player)Event.getEntity();
            if (p.isBlocking()) {
                knockback = -12;
            }
        }
        Event.getEntity().setVelocity(Event.getEntity().getVelocity().add(Event.getDamager().getLocation().toVector().subtract(Event.getEntity().getLocation().toVector()).normalize().multiply(knockback)));
    }
}
