// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;

public class BBom extends AbilityBase
{
    public BBom() {
    	this.InitAbility("\ube14\ub7ec\ub4dc\ubd90", Type.Active_Immediately, Rank.C, "\ucca0\uad34\ub85c \uc88c \ud074\ub9ad \uc2dc \ub2a5\ub825\uc774 \ubc1c\ub3d9\ub429\ub2c8\ub2e4.", "\uc790\uc2e0\uc758 \uccb4\ub825\uc744 \uc18c\ubaa8\ud558\uc5ec \uc9c0\uc815\ud55c \uacf3\uc5d0 \ud3ed\ubc1c\uc744 \uc77c\uc73c\ud0b5\ub2c8\ub2e4.");
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
        final Player p = Event.getPlayer();
        final World w = p.getWorld();
        final Location loc = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
        p.damage(5);
        w.createExplosion(loc, 2.4f, true);
    }
}
