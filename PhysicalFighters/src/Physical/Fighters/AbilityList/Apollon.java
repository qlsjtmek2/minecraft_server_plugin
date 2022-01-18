// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import java.util.HashSet;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MainModule.EventManager;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Apollon extends AbilityBase
{
    public Apollon() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\uc544\ud3f4\ub860", Type.Active_Immediately, Rank.SS, "\ubc14\ub77c\ubcf4\ub294 \ubc29\ud5a5\uc5d0 \ubd88\uad6c\ub369\uc774\ub97c \ub9cc\ub4ed\ub2c8\ub2e4.");
            this.InitAbility(40, 0, true);
            this.RegisterLeftClickEvent();
        }
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
        final Player p = Event.getPlayer();
        final Location l1 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
        final Location l2 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            for (int j = 0; j <= 7; ++j) {
                l2.setY(l1.getY() - j);
                for (int i = 0; i <= 4; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.NETHERRACK);
                    for (int k = 0; k <= 4; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.NETHERRACK);
                    }
                }
                for (int i = 0; i <= 4; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.NETHERRACK);
                    for (int k = 0; k <= 4; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.NETHERRACK);
                    }
                }
                for (int i = 0; i <= 4; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.NETHERRACK);
                    for (int k = 0; k <= 4; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.NETHERRACK);
                    }
                }
                for (int i = 0; i <= 4; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.NETHERRACK);
                    for (int k = 0; k <= 4; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.NETHERRACK);
                    }
                }
            }
            for (int j = 0; j <= 6; ++j) {
                l2.setY(l1.getY() - j);
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.AIR);
                    }
                }
            }
            for (int j = 6; j <= 6; ++j) {
                l2.setY(l1.getY() - j);
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.FIRE);
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.FIRE);
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.FIRE);
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.FIRE);
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.FIRE);
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.FIRE);
                    }
                }
                for (int i = 0; i <= 3; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.FIRE);
                    for (int k = 0; k <= 3; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.FIRE);
                    }
                }
            }
        }
	}
}
