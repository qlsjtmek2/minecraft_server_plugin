// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;

public class Magnet extends AbilityBase
{
    public Magnet() {
    	this.InitAbility("\uc790\uc11d", Type.Active_Immediately, Rank.S, "\ucca0\uad34\ub97c \ub4e4\uace0 \uc67c\ucabd\ud074\ub9ad\uc2dc, 20\uce78\uc548\uc5d0 \uc788\ub294 \ubaa8\ub4e0 \uc801\uc744 \uc790\uc2e0\uc758 \ubc29\ud5a5\uc73c\ub85c,", "\ucca0\uad34\ub97c \ub4e4\uace0 \uc624\ub978\ucabd\ud074\ub9ad\uc2dc, 20\uce78\uc548\uc5d0 \uc788\ub294 \ubaa8\ub4e0 \uc801\uc744 \uc790\uc2e0\uc758 \ubc18\ub300\ubc29\ud5a5\uc73c\ub85c \ub0a0\ub824\ubc84\ub9bd\ub2c8\ub2e4.");
    	this.InitAbility(40, 0, true);
    	this.RegisterLeftClickEvent();
    	this.RegisterRightClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        switch (CustomData) {
            case 0: {
                if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    return 0;
                }
                break;
            }
            case 1: {
                if (!this.PlayerCheck(Event.getPlayer()) || !this.ItemCheck(ACC.DefaultItem)) {
                    break;
                }
                if (!EventManager.DamageGuard) {
                    return 1;
                }
                if (EventManager.DamageGuard) {
                    return -1;
                }
                break;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        final LinkedList<Player> ts = new LinkedList<Player>();
        for (int i = 0; i < Bukkit.getOnlinePlayers().length; ++i) {
            if (p.getLocation().distance(Bukkit.getOnlinePlayers()[i].getLocation()) < 20.0 && Bukkit.getOnlinePlayers()[i] != p && Bukkit.getOnlinePlayers()[i].getGameMode() != GameMode.CREATIVE) {
                ts.add(Bukkit.getOnlinePlayers()[i]);
            }
        }
        if (!ts.isEmpty()) {
            final Location l = p.getLocation();
            l.setY(l.getY() + 2.0);
            final Location l2 = p.getLocation();
            l2.setY(l.getY() - 3.0);
            switch (CustomData) {
                case 0: {
                    for (int j = 0; j < ts.size(); ++j) {
                        final Player t = ts.get(j);
                        t.setVelocity(t.getVelocity().add(t.getLocation().toVector().subtract(l.toVector()).normalize().multiply(-3)));
                    }
                    ts.clear();
                    break;
                }
                case 1: {
                    for (int j = 0; j < ts.size(); ++j) {
                        final Player t = ts.get(j);
                        t.setVelocity(t.getVelocity().add(t.getLocation().toVector().subtract(l2.toVector()).normalize().multiply(3)));
                    }
                    ts.clear();
                    break;
                }
            }
        }
    }
}
