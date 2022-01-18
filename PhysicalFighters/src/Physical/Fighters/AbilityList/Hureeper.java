// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.EventData;

public class Hureeper extends AbilityBase
{
    public Hureeper() {
    	this.InitAbility("\ud734\ub9ac\ud37c", Type.Passive_AutoMatic, Rank.S, "\uc778\uac04\uacfc \ud06c\ub9ac\ud37c\uc758 \ud63c\ud608\uc778\uc785\ub2c8\ub2e4.", "\ud3ed\ubc1c \ub370\ubbf8\uc9c0\ub97c \uc785\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
    	this.InitAbility(0, 0, true);
    	EventManager.onEntityDamage.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        if (this.PlayerCheck(Event.getEntity()) && (Event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING || Event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || Event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        Event.setCancelled(true);
    }
}
