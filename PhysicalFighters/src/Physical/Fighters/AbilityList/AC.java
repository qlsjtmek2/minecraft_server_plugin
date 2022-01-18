// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.EventData;

public class AC extends AbilityBase
{
    public AC() {
    	this.InitAbility("\uc0b4\uc778\uc758 \ud76c\uc5f4", Type.Passive_AutoMatic, Rank.S, "\uc0c1\ub300\ubc29\uc744 \uc8fd\uc77c \uc2dc \uccb4\ub825\uc774 \ubaa8\ub450 \ud68c\ubcf5\ub429\ub2c8\ub2e4.");
    	this.InitAbility(0, 0, true);
    	EventManager.onEntityDeath.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDeathEvent Event1 = (EntityDeathEvent)event;
        if (Event1.getEntity() instanceof Player) {
            final Player p = (Player)Event1.getEntity();
            if (this.PlayerCheck(p.getKiller())) {
                return 0;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDeathEvent Event1 = (EntityDeathEvent)event;
        final Player killed = (Player)Event1.getEntity();
        final Player killerP = killed.getKiller();
        
        if (!killerP.getWorld().getName().equalsIgnoreCase("world")) {
            killerP.setHealth(killerP.getMaxHealth());
            killerP.sendMessage(PhysicalFighters.a + "체력이 모두 §d회복§f되었습니다.");
        }
    }
}
