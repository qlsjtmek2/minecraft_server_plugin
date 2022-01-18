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

public class Fire extends AbilityBase
{
    public Fire() {
    	this.InitAbility("\ubd88\ub625", Type.Active_Immediately, Rank.B, "\ucca0\uad34 \uc88c\ud074\ub9ad\uc2dc \uc9c0\uc815\ud55c \uacf3\uc5d0 \ubd88\ub625\uc774 \ub5a8\uc5b4\uc9d1\ub2c8\ub2e4.", "\ucca0\uad34 64\uac1c \uc88c\ud074\ub9ad\uc2dc \ud654\ub825 up!");
    	this.InitAbility(5, 0, true);
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
        if (!Event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            if (Event.getPlayer().getItemInHand().getAmount() == 64) {
                final Player p = Event.getPlayer();
                final World w = p.getWorld();
                final Location l = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
                final Vector targetvec = new Vector(l.getX(), l.getY(), l.getZ());
                for (int z = -1; z < 2; ++z) {
                    for (int i = -1; i < 2; ++i) {
                        l.setX(targetvec.getX() + i);
                        l.setY(targetvec.getY() + 8.0);
                        l.setZ(targetvec.getZ() + z);
                        w.spawnFallingBlock(l, Material.FIRE, (byte)0);
                    }
                }
            } else {
                final Player p = Event.getPlayer();
                final World w = p.getWorld();
                final Location l = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
                l.setY(l.getY() + 8.0);
                w.spawnFallingBlock(l, Material.FIRE, (byte)0);
            }
        }
    }
}
