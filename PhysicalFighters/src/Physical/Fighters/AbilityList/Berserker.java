// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Berserker extends AbilityBase
{
    public Berserker() {
        this.InitAbility("\uad11\uc804\uc0ac", Type.Passive_Manual, Rank.A, "\uccb4\ub825\uc774 70% \uc774\ud558\ub85c \ub5a8\uc5b4\uc9c0\uba74 \ub370\ubbf8\uc9c0\uac00 1.5\ubc30\ub85c \uc99d\ud3ed\ub418\uba70", "\uccb4\ub825\uc774 40% \uc774\ud558\ub85c \ub5a8\uc5b4\uc9c0\uba74 \ub370\ubbf8\uc9c0\uac00 2\ubc30\ub85c \uc99d\ud3ed\ub429\ub2c8\ub2e4.", "\uccb4\ub825\uc774 20% \uc774\ud558\ub85c \ub5a8\uc5b4\uc9c0\uba74 \ub370\ubbf8\uc9c0\uac00 3\ubc30\ub85c \uc99d\ud3ed\ub429\ub2c8\ub2e4.", "\uccb4\ub825\uc774 5% \uc774\ud558\ub85c \ub5a8\uc5b4\uc9c0\uba74 \ub370\ubbf8\uc9c0\uac00 5\ubc30\ub85c \uc99d\ud3ed\ub429\ub2c8\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (!Event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            if (this.PlayerCheck(Event.getDamager())) {
                final Player p = (Player)Event.getDamager();
                if (p.getHealth() <= 14 && p.getHealth() > 8) {
                    return 0;
                }
                if (p.getHealth() <= 8) {
                    return 1;
                }
                if (p.getHealth() <= 4) {
                    return 2;
                }
                if (p.getHealth() <= 1) {
                    return 3;
                }
            }
        } return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (!Event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            switch (CustomData) {
            case 0: {
                Event.setDamage((int)(Event.getDamage() * 1.5));
                break;
            }
            case 1: {
                Event.setDamage(Event.getDamage() * 2);
                break;
            }
            case 2: {
                Event.setDamage(Event.getDamage() * 3);
                break;
            }
            case 3: {
                Event.setDamage(Event.getDamage() * 5);
                break;
            }
        }
        }
    }
}
