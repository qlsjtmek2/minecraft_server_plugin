// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Explosion extends AbilityBase
{
    public Explosion() {
        this.InitAbility("\uc775\uc2a4\ud50c\ub85c\uc83c", Type.Passive_Manual, Rank.B, "\uc0ac\ub9dd\uc2dc\uc5d0 \uc5c4\uccad\ub09c \uc5f0\uc1c4\ud3ed\ubc1c\uc744 \uc77c\uc73c\ucf1c \uc8fc\ubcc0\uc758 \uc720\uc800\ub4e4\uc744 \uc8fd\uc785\ub2c8\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDeath.add(new EventData(this, 0));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDeathEvent Event0 = (EntityDeathEvent)event;
                if (this.PlayerCheck((Entity)Event0.getEntity())) {
                    return 0;
                }
                break;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final PlayerDeathEvent Event0 = (PlayerDeathEvent)event;
                final Player killed = Event0.getEntity();
                if (!killed.getWorld().getName().equalsIgnoreCase("world")) {
                	killed.getWorld().createExplosion(killed.getLocation(), 14.0f, false);
                	break;
                }
            }
        }
    }
}
