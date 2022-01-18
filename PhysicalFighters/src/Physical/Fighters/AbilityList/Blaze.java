// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Blaze extends AbilityBase
{
    public Blaze() {
        if (!PhysicalFighters.Specialability) {
            this.InitAbility("\ube14\ub808\uc774\uc988", Type.Passive_AutoMatic, Rank.C, "\uc6a9\uc554\uacfc \ubd88 \ub370\ubbf8\uc9c0\ub97c \uc785\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.", "\ub2a5\ub825\uc5d0\uc11c \ud30c\uc0dd\ub418\ub294 \ud654\uc5fc \ub370\ubbf8\uc9c0\ub3c4 \ub9c9\uc2b5\ub2c8\ub2e4.", "\ubaa8\ub4e0 \uc885\ub958\uc758 \ud3ed\ubc1c \ub370\ubbf8\uc9c0\ub97c 50%\ub85c \uc904\uc5ec \ubc1b\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(0, 0, true);
            EventManager.onEntityDamage.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        if (this.PlayerCheck(Event.getEntity())) {
            if (Event.getCause() == EntityDamageEvent.DamageCause.LAVA || Event.getCause() == EntityDamageEvent.DamageCause.FIRE || Event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                return 0;
            }
            if (Event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || Event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                return 1;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        switch (CustomData) {
            case 0: {
                Event.setCancelled(true);
                Event.getEntity().setFireTicks(0);
                break;
            }
            case 1: {
                Event.setDamage(Event.getDamage() / 2);
                break;
            }
        }
    }
}
