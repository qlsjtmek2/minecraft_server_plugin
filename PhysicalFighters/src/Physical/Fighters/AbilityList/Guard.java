// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.OtherModule.Vector;

public class Guard extends AbilityBase
{
    public Guard() {
        this.InitAbility("\ubaa9\ub454", Type.Active_Immediately, Rank.A, "\ub2a5\ub825\uc744 \uc0ac\uc6a9\ud558\uba74 \ub098\ubb34 \ubcbd\uc744 \uc124\uce58\ud574 \uc0c1\ub300\ub97c \uac00\ub458 \uc218 \uc788\ub2e4.");
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
        if (!EventManager.DamageGuard && this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem) && Event.getPlayer().getTargetBlock((HashSet<Byte>)null, 0).getType() != Material.BEDROCK) {
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
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
        	final Location l1 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            final Location l2 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            for (int j = 0; j <= 8; ++j) {
                l2.setY(l1.getY() + j);
                for (int i = 0; i <= 5; ++i) {
                    l2.setX(l1.getX() + i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.WOOD);
                    }
                    for (int k = 0; k <= 5; ++k) {
                        l2.setZ(l1.getZ() + k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.WOOD);
                        }
                    }
                }
                for (int i = 0; i <= 5; ++i) {
                    l2.setX(l1.getX() - i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.WOOD);
                    }
                    for (int k = 0; k <= 5; ++k) {
                        l2.setZ(l1.getZ() - k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.WOOD);
                        }
                    }
                }
                for (int i = 0; i <= 5; ++i) {
                    l2.setX(l1.getX() - i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.WOOD);
                    }
                    for (int k = 0; k <= 5; ++k) {
                        l2.setZ(l1.getZ() + k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.WOOD);
                        }
                    }
                }
                for (int i = 0; i <= 5; ++i) {
                    l2.setX(l1.getX() + i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.WOOD);
                    }
                    for (int k = 0; k <= 5; ++k) {
                        l2.setZ(l1.getZ() - k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.WOOD);
                        }
                    }
                }
            }
            for (int j = 1; j <= 6; ++j) {
                l2.setY(l1.getY() + j);
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() + i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    }
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() + k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.AIR);
                        }
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() - i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    }
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() - k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.AIR);
                        }
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() - i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    }
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() + k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.AIR);
                        }
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() + i);
                    if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                        p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    }
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() - k);
                        if (p.getWorld().getBlockAt(l2).getType() != Material.BEDROCK) {
                            p.getWorld().getBlockAt(l2).setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
