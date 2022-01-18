// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;
import java.util.HashSet;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Fallingarrow extends AbilityBase
{
    public Fallingarrow() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ub099\ud558\ud654\uc0b4", Type.Active_Immediately, Rank.A, "\ubc14\ub77c\ubcf4\ub294\uacf3\uc5d0 \ube60\ub978\uc18d\ub3c4\ub85c \ud654\uc0b4 16\ubc1c\uc744 \ub0b4\ub9ac\uaf42\uc2b5\ub2c8\ub2e4. [\uc2e4\ub0b4\uc5d0\uc11c \uc0ac\uc6a9\uc774 \uc548\ub420 \uc218 \uc788\uc2b5\ub2c8\ub2e4.]");
            this.InitAbility(3, 0, true);
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        final Location l2 = p.getTargetBlock((HashSet)null, 0).getLocation();
        final Vector v = new Vector(0, -10, 0);
        l2.setY(256.0);
        for (int i = -1; i <= 2; ++i) {
            for (int j = -1; j <= 2; ++j) {
                final Location k = p.getTargetBlock((HashSet)null, 0).getLocation();
                k.setX(k.getX() + 0.5 * i);
                if (k.getY() + 10.0 > 250.0) {
                    k.setY(250.0);
                }
                else {
                    k.setY(k.getY() + 10.0);
                }
                k.setZ(k.getZ() + 0.5 * j);
                final Arrow a = (Arrow)p.getWorld().spawn(k, (Class)Arrow.class);
                a.setVelocity(v);
                a.setShooter((LivingEntity)p);
            }
        }
    }
}
