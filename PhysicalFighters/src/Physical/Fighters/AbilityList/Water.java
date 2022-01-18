// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.OtherModule.Vector;

public class Water extends AbilityBase
{
    public Water() {
    	this.InitAbility("\ubb3c\ud48d\uc120", Type.Active_Immediately, Rank.C, "\ucca0\uad34 \uc88c\ud074\ub9ad\uc2dc \uc9c0\uc815\ud55c \uacf3\uc5d0 \ubb3c\ud48d\uc120\uc774 \ub5a8\uc5b4\uc9d1\ub2c8\ub2e4.");
    	this.InitAbility(20, 0, true);
    	this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        final Block b = p.getTargetBlock((HashSet<Byte>)null, 0);
        final Location loc = b.getLocation();
        final Location ploc = p.getLocation();
        final Vector playervec = new Vector(ploc.getX(), ploc.getY(), ploc.getZ());
        final Vector targetvec = new Vector(loc.getX(), loc.getY(), loc.getZ());
        if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
            if (playervec.distance(targetvec) <= 40.0 && b.getY() != 0) {
                return 0;
            }
            p.sendMessage(String.format(PhysicalFighters.w + "거리가 너무 멉니다.", new Object[0]));
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            final World w = p.getWorld();
            final Location l = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            l.setY(l.getY() + 8.0);
            w.spawnFallingBlock(l, Material.WATER, (byte)0);
        }
    }
}
