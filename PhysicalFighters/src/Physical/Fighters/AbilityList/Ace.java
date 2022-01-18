// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import org.bukkit.Bukkit;
import java.util.TimerTask;
import java.util.Timer;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MainModule.EventManager;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MainModule.AbilityBase;

public class Ace extends AbilityBase
{
    public Ace() {
        this.InitAbility("\uc5d0\uc774\uc2a4", Type.Active_Immediately, Rank.S, "\ucca0\uad34 \uc67c\ucabd\ud074\ub9ad\uc2dc 20\ucd08\uac04 \uc790\uc2e0\uc758 \uc8fc\ubcc0\uc5d0 \uc788\ub294 \uc801\ub4e4\uc744 \ubd88\ud0dc\uc6c1\ub2c8\ub2e4.");
        this.InitAbility(40, 0, true);
        this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (!EventManager.DamageGuard && this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Timer timer = new Timer();
        if (!Event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
        	timer.schedule(new Pauck(Event.getPlayer().getName()), 500L, 1500L);
        }
    }
    
    class Pauck extends TimerTask
    {
        private int num;
        private String name;
        
        public Pauck(final String name1) {
            this.num = 0;
            this.name = null;
            this.name = name1;
        }
        
        @Override
        public void run() {
            final Player[] p1 = Bukkit.getOnlinePlayers();
            final Player p2 = Bukkit.getPlayer(this.name);
            if (p2 != null) {
                for (int i = 0; i < Bukkit.getOnlinePlayers().length; ++i) {
                    if (p1[i] != p2) {
                        if (p1[i].getGameMode() != GameMode.CREATIVE) {
                            final Location lo = p1[i].getLocation();
                            if (p2.getLocation().distance(p1[i].getLocation()) <= 10.0 && lo.getY() != 0.0) {
                                p1[i].setFireTicks(80);
                            }
                        }
                    }
                }
            }
            if (this.num > 16) {
                this.cancel();
            }
            ++this.num;
        }
    }
}
