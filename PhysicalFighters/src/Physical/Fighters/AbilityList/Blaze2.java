// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.EventData;

public class Blaze2 extends AbilityBase
{
    public Blaze2() {
    	this.InitAbility("\ube14\ub808\uc774\uc9882", Type.Passive_AutoMatic, Rank.B, "\uc6a9\uc554\uacfc \ubd88 \ub370\ubbf8\uc9c0\ub97c \uc785\uc744 \uc2dc \uccb4\ub825\uc774 \ud68c\ubcf5 \ub429\ub2c8\ub2e4.");
    	this.InitAbility(0, 0, true);
    	EventManager.onEntityDamage.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        if (this.PlayerCheck(Event.getEntity()) && (Event.getCause() == EntityDamageEvent.DamageCause.LAVA || Event.getCause() == EntityDamageEvent.DamageCause.FIRE || Event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        final Player p = (Player)Event.getEntity();
        final Random r = new Random();
        final int a = r.nextInt(5);
        final int h = p.getHealth();
        switch (a) {
            case 0: {
                if (h < 20) {
                    p.setHealth(h + 1);
                    break;
                }
                p.setHealth(20);
                break;
            }
            case 2: {
                if (h < 19) {
                    p.setHealth(h + 2);
                    break;
                }
                p.setHealth(20);
                break;
            }
            case 3: {
                if (h < 20) {
                    p.setHealth(h + 1);
                    break;
                }
                p.setHealth(20);
                break;
            }
        }
    }
}
