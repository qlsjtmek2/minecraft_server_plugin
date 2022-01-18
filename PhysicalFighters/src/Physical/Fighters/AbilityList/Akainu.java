// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.TimerTask;
import java.util.Timer;
import org.bukkit.event.entity.EntityDamageEvent;
import java.util.HashSet;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import org.bukkit.Material;
import org.bukkit.block.Block;
import Physical.Fighters.MainModule.AbilityBase;

public class Akainu extends AbilityBase
{
    public static Block[][][] B;
    public static Material[][][] M;
    
    static {
        Akainu.B = new Block[5][5][3];
        Akainu.M = new Material[5][5][3];
    }
    
    public Akainu() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\uc544\uce74\uc774\ub204", Type.Active_Immediately, Rank.SS, "\ubc14\ub77c\ubcf4\ub294 \uacf3\uc758 \ub545\uc744 \uc6a9\uc554\uc73c\ub85c \ubc14\uafd4\ubc84\ub9bd\ub2c8\ub2e4.", "\uc6a9\uc554\uc18d\uc5d0\uc11c \ub370\ubbf8\uc9c0\ub97c \ubc1b\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(80, 0, true);
            this.RegisterLeftClickEvent();
            EventManager.onEntityDamage.add(new EventData(this, 3));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        if (CustomData == 0) {
            final PlayerInteractEvent Event = (PlayerInteractEvent)event;
            if (!EventManager.DamageGuard && this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem) && Event.getPlayer().getTargetBlock((HashSet<Byte>)null, 0).getType() != Material.BEDROCK) {
                return 0;
            }
        }
        if (CustomData == 3) {
            final EntityDamageEvent Event2 = (EntityDamageEvent)event;
            if (this.PlayerCheck(Event2.getEntity()) && (Event2.getCause() == EntityDamageEvent.DamageCause.LAVA || Event2.getCause() == EntityDamageEvent.DamageCause.FIRE || Event2.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)) {
                Event2.setCancelled(true);
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            final World w = Event.getPlayer().getWorld();
            final Location loc = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            final Timer timer = new Timer();
            for (int i = -2; i < 3; ++i) {
                for (int j = -2; j < 3; ++j) {
                    for (int k = -2; k < 1; ++k) {
                        Akainu.B[i + 2][j + 2][k + 2] = w.getBlockAt((int)loc.getX() + i, (int)loc.getY() + k, (int)loc.getZ() + j);
                        Akainu.M[i + 2][j + 2][k + 2] = w.getBlockAt((int)loc.getX() + i, (int)loc.getY() + k, (int)loc.getZ() + j).getType();
                        w.getBlockAt((int)loc.getX() + i, (int)loc.getY() + k, (int)loc.getZ() + j).setType(Material.GRASS);
                    }
                }
            }
            for (int i = -1; i < 2; ++i) {
                for (int j = -1; j < 2; ++j) {
                    for (int k = -1; k < 1; ++k) {
                        w.getBlockAt((int)loc.getX() + i, (int)loc.getY() + k, (int)loc.getZ() + j).setType(Material.LAVA);
                    }
                }
            }
            timer.schedule(new ExplosionTimer(w), 3000L);
        }
    }
    
    class ExplosionTimer extends TimerTask
    {
        private World w;
        
        public ExplosionTimer(final World w1) {
            this.w = w1;
        }
        
        @Override
        public void run() {
            for (int i = -2; i < 3; ++i) {
                for (int j = -2; j < 3; ++j) {
                    for (int k = -2; k < 1; ++k) {
                        final Location loc = Akainu.B[i + 2][j + 2][k + 2].getLocation();
                        this.w.getBlockAt(loc).setType(Akainu.M[i + 2][j + 2][k + 2]);
                    }
                }
            }
        }
    }
}
