// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.OtherModule.Vector;

public class Spider extends AbilityBase
{
    public Spider() {
    	this.InitAbility("\uc2a4\ud30c\uc774\ub354", Type.Active_Immediately, Rank.B, "\ucca0\uad34 \uc67c\ud074\ub9ad\uc2dc \uc790\uc2e0\uc774 \ubc14\ub77c\ubcf4\ub294 \uacf3\uc5d0 \uac70\ubbf8\uc904\uc744 \uc124\uce58 \ud569\ub2c8\ub2e4.");
    	this.InitAbility(35, 0, true);
    	this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
        if (this.PlayerCheck(Event2.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
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
            final Vector targetvec = new Vector(loc.getX(), loc.getY(), loc.getZ());
            for (int z = -1; z < 2; ++z) {
                for (int i = -1; i < 2; ++i) {
                    loc.setX(targetvec.getX() + i);
                    loc.setY(targetvec.getY() + 1.0);
                    loc.setZ(targetvec.getZ() + z);
                    w.getBlockAt(loc).setType(Material.WEB);
                }
            }
        }
    }
}
