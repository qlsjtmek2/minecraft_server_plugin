// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Random;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;

public class Creeper extends AbilityBase
{
    public Creeper() {
    	this.InitAbility("\ud06c\ub9ac\ud37c", Type.Active_Immediately, Rank.B, "\ucca0\uad34\ub85c \uc88c\ud074\ub9ad\uc2dc \uc790\ud3ed \ud569\ub2c8\ub2e4. \ud3ed\ubc1c\ub825\uc740 \ub79c\ub364\uc785\ub2c8\ub2e4.");
    	this.InitAbility(0, 0, true);
    	this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (!Event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            final Random r = new Random();
            final int a = r.nextInt(10);
            this.GetPlayer().setHealth(0);
            if (a == 0 || a == 4 || a == 7 || a == 9) {
                Event.getPlayer().getWorld().createExplosion(this.GetPlayer().getLocation(), 7.0f, false);
            }
            if (a == 1 || a == 5 || a == 8) {
                Event.getPlayer().getWorld().createExplosion(this.GetPlayer().getLocation(), 14.0f, false);
            }
            if (a == 2 || a == 6) {
                Event.getPlayer().getWorld().createExplosion(this.GetPlayer().getLocation(), 0.1f, false);
            }
            if (a == 3) {
                Event.getPlayer().getWorld().createExplosion(this.GetPlayer().getLocation(), 21.0f, false);
            }
        }
    }
}
