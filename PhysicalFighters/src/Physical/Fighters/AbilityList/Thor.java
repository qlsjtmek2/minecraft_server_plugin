// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.HashSet;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Thor extends AbilityBase
{
    public Thor() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ud1a0\ub974", Type.Active_Immediately, Rank.S, "\uc9c0\uc815\ud55c \uc9c0\uc810\uc5d0 \ubc88\uac1c\ub97c \ub5a8\uc5b4\ub728\ub9bd\ub2c8\ub2e4. \ubc88\uac1c\uac00", "\ub5a8\uc5b4\uc9c4\uacf3\uc5d0 \ub370\ubbf8\uc9c0\uc640 \ud3ed\ubc1c\uc774 \uc77c\uc5b4\ub0a9\ub2c8\ub2e4.");
            this.InitAbility(27, 0, true);
            this.RegisterLeftClickEvent();
        }
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
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            final World w = p.getWorld();
            final Location loc = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            w.strikeLightning(loc);
            w.strikeLightning(loc);
            w.createExplosion(loc, 4.2f, true);
        }
    }
}
