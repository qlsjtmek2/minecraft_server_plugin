// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Material;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Zombie extends AbilityBase
{
    public Zombie() {
        this.InitAbility("\uc880\ube44", Type.Passive_AutoMatic, Rank.B, "\ubaa8\ub4e0 \ub370\ubbf8\uc9c0\uc758 \ubc18\uc744 \ud761\uc218\ud569\ub2c8\ub2e4.", "\ubd88\uacf5\uaca9\uc758 \ub370\ubbf8\uc9c0\ub97c 8\ubc30\ub85c \ubc1b\uc2b5\ub2c8\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDamage.add(new EventData(this));
        EventManager.onEntityDamageByEntity.add(new EventData(this, 1));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        if (CustomData == 0) {
            final EntityDamageEvent Event = (EntityDamageEvent)event;
            if (this.PlayerCheck(Event.getEntity())) {
                if (Event.getCause() == EntityDamageEvent.DamageCause.LAVA || Event.getCause() == EntityDamageEvent.DamageCause.FIRE || Event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                    return 0;
                }
                if (Event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || Event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    return 1;
                }
                if (Event.getCause() == EntityDamageEvent.DamageCause.FALL || Event.getCause() == EntityDamageEvent.DamageCause.POISON || Event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    return 2;
                }
            }
        }
        else if (CustomData == 1) {
            final EntityDamageByEntityEvent Event2 = (EntityDamageByEntityEvent)event;
            if (this.PlayerCheck(Event2.getEntity()) && ((HumanEntity)Event2.getDamager()).getItemInHand().getType().getId() != ACC.DefaultItem && ((HumanEntity)Event2.getDamager()).getItemInHand().getType().getId() != Material.GOLD_INGOT.getId()) {
                return 2;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageEvent Event = (EntityDamageEvent)event;
                if (!Event.getEntity().getWorld().getName().equalsIgnoreCase("world"))
                	Event.setDamage(Event.getDamage() * 8);
                break;
            }
            case 1: {
                final EntityDamageEvent Event2 = (EntityDamageEvent)event;
                if (!Event2.getEntity().getWorld().getName().equalsIgnoreCase("world"))
                	Event2.setDamage(Event2.getDamage() * 4);
                break;
            }
            case 2: {
                final EntityDamageByEntityEvent Event3 = (EntityDamageByEntityEvent)event;
                if (!Event3.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
                    this.GetPlayer().damage(1);
                    Event3.setDamage(Event3.getDamage() / 2);
                }
                break;
            }
        }
    }
}
