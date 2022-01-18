// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.OtherModule.Vector;

public class Jumper extends AbilityBase
{
    public Jumper() {
        this.InitAbility("\uc810\ud37c", Type.Active_Immediately, Rank.B, "\ucd5c\ub300 40\uce78 \uac70\ub9ac\ub97c \uc21c\uac04\uc774\ub3d9 \ud560\uc218 \uc788\uc2b5\ub2c8\ub2e4.", "\ub2e8, \ubcbd\uc740 \ud1b5\uacfc\ud560\uc218 \uc5c6\uace0 \ub099\uc0ac \ub370\ubbf8\uc9c0\ub3c4 \ubc1b\uc2b5\ub2c8\ub2e4.", "\uc790\uc2e0\uc774 \uac08 \uc7a5\uc18c\uc758 \ubc14\ub2e5 \ube14\ub7ed\uc744 \ud074\ub9ad\ud574\uc57c \ud154\ud3ec\uac00 \ub429\ub2c8\ub2e4.", "\uc0ac\uc6a9\uc2dc \uc720\uc758\ud558\uc138\uc694. \ud5c8\uacf5\uc5d4 \uc0ac\uc6a9\uc774 \uc548\ub429\ub2c8\ub2e4!");
        this.InitAbility(20, 0, true);
        this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
            final Player p = Event.getPlayer();
            final Block b = p.getTargetBlock((HashSet<Byte>)null, 0);
            final Location loc = b.getLocation();
            final Location ploc = p.getLocation();
            Vector targetvec;
            if (b.getRelative(0, 1, 0).getTypeId() == 0 && b.getRelative(0, 2, 0).getTypeId() == 0) {
                targetvec = new Vector(loc.getX(), loc.getY(), loc.getZ());
            }
            else {
                targetvec = new Vector(loc.getX(), loc.getY(), loc.getZ());
            }
            final Vector playervec = new Vector(ploc.getX(), ploc.getY(), ploc.getZ());
            if (playervec.distance(targetvec) <= 40.0 && b.getY() != 0) {
                return 0;
            }
            p.sendMessage(String.format(PhysicalFighters.a + "§c거리가 너무 멉니다.", new Object[0]));
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        final Block b = p.getTargetBlock((HashSet<Byte>)null, 0);
        final Location loc = b.getLocation();
        Vector targetvec;
        if (b.getRelative(0, 1, 0).getTypeId() == 0 && b.getRelative(0, 2, 0).getTypeId() == 0) {
            targetvec = new Vector(loc.getX(), loc.getY(), loc.getZ());
        }
        else {
            targetvec = new Vector(loc.getX(), b.getWorld().getHighestBlockYAt(b.getX(), b.getZ()), loc.getZ());
        }
        loc.setX(targetvec.getX() + 0.5);
        loc.setY(targetvec.getY() + 1.0);
        loc.setZ(targetvec.getZ() + 0.5);
        loc.setPitch(p.getLocation().getPitch());
        loc.setYaw(p.getLocation().getYaw());
        p.teleport(loc);
    }
}
