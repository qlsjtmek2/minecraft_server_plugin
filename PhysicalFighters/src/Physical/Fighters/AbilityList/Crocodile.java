// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;

public class Crocodile extends AbilityBase
{
    public Crocodile() {
        if (!PhysicalFighters.Bapo && PhysicalFighters.SRankUsed) {
            this.InitAbility("\ud06c\ub85c\ucee4\ub2e4\uc77c", Type.Active_Immediately, Rank.S, "\ucca0\uad34 \uc67c\ucabd\ud074\ub9ad\uc2dc \uc790\uc2e0\uc758 \uc8fc\ubcc0\uc758 \uc788\ub294 \ube14\ub825\uc744 \ubaa8\ub798\ub85c \ubc14\uafd4\ubc84\ub9bd\ub2c8\ub2e4.", "\ucca0\uad34 \uc624\ub978\ucabd\ud074\ub9ad\uc2dc \ubaa8\ub798 \uc8fc\ubcc0\uc5d0 \uc788\ub294 \uc801\uc5d0\uac8c \uc77c\uc815\uc2dc\uac04\ub3d9\uc548 \ubaa8\ub798\ubc14\ub78c\uc744 \uc77c\uc73c\ud0b5\ub2c8\ub2e4.");
            this.InitAbility(30, 0, true);
            this.RegisterLeftClickEvent();
            this.RegisterRightClickEvent();
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final PlayerInteractEvent Event = (PlayerInteractEvent)event;
                if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    return 0;
                }
            }
            case 1: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                if (this.PlayerCheck(Event2.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    return 1;
                }
                break;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final PlayerInteractEvent Event = (PlayerInteractEvent)event;
                final Player p = Event.getPlayer();
                if (!p.getWorld().getName().equalsIgnoreCase("world")) {
                    final World w = Event.getPlayer().getWorld();
                    final Location loc = p.getLocation();
                    for (int i = -5; i < 6; ++i) {
                        for (int j = -5; j < 6; ++j) {
                            for (int k = -1; k < 6; ++k) {
                                if (w.getBlockAt((int)loc.getX() + i, (int)loc.getY() + k, (int)loc.getZ() + j).getType() != Material.AIR) {
                                    w.getBlockAt((int)loc.getX() + i, (int)loc.getY() + k, (int)loc.getZ() + j).setType(Material.SAND);
                                }
                            }
                        }
                    }
                }
                break;
            }
            case 1: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                if (!Event2.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
                	final Timer timer = new Timer();
                	timer.schedule(new Pauck(Event2.getPlayer().getName()), 500L, 1500L);
                	break;
                }
            }
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
                            final World w = p2.getWorld();
                            for (int i2 = 0; i2 < 2; ++i2) {
                                for (int j = -1; j < 2; ++j) {
                                    for (int k = -1; k < 2; ++k) {
                                        if (w.getBlockAt((int)loc.getX() + i2, (int)loc.getY() + k, (int)loc.getZ() + j).getType() == Material.SAND) {
                                            p1[i].damage(2, (Entity)p2);
                                            p1[i].setVelocity(p1[i].getVelocity().add(p2.getLocation().toVector().subtract(p1[i].getLocation().toVector()).normalize().multiply(-1)));
                                            p1[i].addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0), true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.num > 10) {
                    this.cancel();
                    p2.sendMessage(PhysicalFighters.a + "지속시간이 끝났습니다.");
                }
                ++this.num;
            }
        }
    }
}
