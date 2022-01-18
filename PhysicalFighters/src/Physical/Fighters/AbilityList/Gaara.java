// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.OtherModule.Vector;

public class Gaara extends AbilityBase
{
    public Gaara() {
        this.InitAbility("\uac00\uc544\ub77c", Type.Active_Immediately, Rank.S, "\ub2a5\ub825\uc744 \uc0ac\uc6a9\ud558\uba74 \ubaa8\ub798\ub97c \ub5a8\uc5b4\ub728\ub9ac\uace0 \ud3ed\ubc1c\uc2dc\ud0a8\ub2e4.");
        this.InitAbility(30, 0, true);
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
                if (!EventManager.DamageGuard) {
                    return 0;
                }
            }
            else {
                p.sendMessage(String.format(PhysicalFighters.a + "§c거리가 너무 멉니다.", new Object[0]));
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
        	final Location l1 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            final Location l2 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            final Timer timer = new Timer();
            final Block block = Event.getPlayer().getTargetBlock((HashSet<Byte>)null, 0);
            timer.schedule(new ExplosionTimer(block), 4000L);
            for (int j = 4; j <= 8; ++j) {
                l2.setY(l1.getY() + j);
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() + i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.SAND);
                    }
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() + k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.SAND);
                        }
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() - i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.SAND);
                    }
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() - k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.SAND);
                        }
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() - i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.SAND);
                    }
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() + k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.SAND);
                        }
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() + i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.SAND);
                    }
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() - k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.SAND);
                        }
                    }
                }
            }
        }
    }
    
    class ExplosionTimer extends TimerTask
    {
        World world;
        Location location;
        
        ExplosionTimer(final Block block) {
            this.world = block.getWorld();
            this.location = block.getLocation();
        }
        
        @Override
        public void run() {
            this.location.setY(this.location.getY() + 2.0);
            this.world.createExplosion(this.location, 5.0f);
        }
    }
}
