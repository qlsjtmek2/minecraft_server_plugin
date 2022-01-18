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

public class Wall extends AbilityBase
{
    public static Location Aloc;
    
    public Wall() {
    	this.InitAbility("\ubcbd", Type.Active_Immediately, Rank.S, "\ucca0\uad34 \uc67c\ucabd\ud074\ub9ad\uc2dc \ud50c\ub808\uc774\uc5b4\uac00 \ubc14\ub77c\ubcf4\ub294 \ubc29\ud5a5\uc5d0 \ubcbd\uc744 \uc124\uce58 \ud569\ub2c8\ub2e4.", "\ucca0\uad34 64\uac1c \ubbf8\ub9cc\uc77c\uc2dc \ud759, \ucca0\uad34 64\uac1c \uc77c\uc2dc \ub3cc \ubcbd\uc774 \uc124\uce58\ub429\ub2c8\ub2e4..");
    	this.InitAbility(10, 0, true);
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
        final World w = p.getWorld();
        final Location loc = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
        final Vector targetvec = new Vector(loc.getX(), loc.getY(), loc.getZ());
        for (int y = 0; y < 5; ++y) {
            for (int z = -1; z <= 1; ++z) {
                for (int i = -1; i <= 1; ++i) {
                    loc.setX(targetvec.getX() + i);
                    loc.setZ(targetvec.getZ() + z);
                    loc.setY(targetvec.getY() + y);
                    if (p.getItemInHand().getAmount() == 64 && w.getBlockAt((int)loc.getX(), (int)loc.getY(), (int)loc.getZ()).getType() != Material.BEDROCK) {
                        w.getBlockAt(loc).setType(Material.STONE);
                    }
                    else if (p.getItemInHand().getAmount() < 64 && w.getBlockAt((int)loc.getX(), (int)loc.getY(), (int)loc.getZ()).getType() != Material.BEDROCK) {
                        w.getBlockAt(loc).setType(Material.DIRT);
                    }
                }
            }
        }
    }
}
