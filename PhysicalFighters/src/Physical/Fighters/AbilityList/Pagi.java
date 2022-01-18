// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Entity;
import Physical.Fighters.OtherModule.Vector;
import org.bukkit.GameMode;
import org.bukkit.Bukkit;
import java.util.TimerTask;
import java.util.Timer;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import Physical.Fighters.MainModule.EventManager;
import org.bukkit.event.Event;
import Physical.Fighters.MainModule.AbilityBase;

public class Pagi extends AbilityBase
{
    public Pagi() {
        this.InitAbility("\ud328\uae30", Type.Active_Immediately, Rank.SS, "\ub2a5\ub825 \uc0ac\uc6a9\uc2dc 20\ucd08\uac04 10\uce78 \uc548\uc5d0 \uc788\ub294 \uc801\uc5d0\uac8c 5\ucd08\ub9c8\ub2e4 \uac15\ud55c\ub370\ubbf8\uc9c0\ub97c \uc90d\ub2c8\ub2e4.");
        this.InitAbility(160, 0, true);
        this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        if (!EventManager.DamageGuard) {
            final PlayerInteractEvent Event = (PlayerInteractEvent)event;
            if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                return 0;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (!Event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
        	final Timer timer = new Timer();
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
                            final Location loc = p1[i].getLocation();
                            final Location l = p2.getLocation();
                            final Vector targetvec = new Vector(loc.getX(), loc.getY(), loc.getZ());
                            final Vector playervec = new Vector(l.getX(), l.getY(), l.getZ());
                            if (playervec.distance(targetvec) <= 10.0 && loc.getY() != 0.0) {
                                p1[i].damage(5, (Entity)p2);
                                p1[i].addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30, 0), true);
                            }
                        }
                    }
                }
                if (this.num > 20) {
                    this.cancel();
                }
                ++this.num;
            }
        }
    }
}
