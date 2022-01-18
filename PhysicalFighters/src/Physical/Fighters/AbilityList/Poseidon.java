// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.Material;
import java.util.HashSet;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Poseidon extends AbilityBase
{
    public Poseidon() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ud3ec\uc138\uc774\ub3c8", Type.Active_Immediately, Rank.SS, "\ubc14\ub77c\ubcf4\ub294\uacf3\uc5d0 \uac70\ub300\ud55c \uc5b4\ud56d\uc744 \ub9cc\ub4e4\uc5b4 \uac00\ub461\ub2c8\ub2e4.", "\ubb3c\uc5d0\uc11c \uc228\uc744 \uc274 \uc218 \uc788\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(60, 0, true);
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
            if (this.PlayerCheck(Event2.getEntity()) && Event2.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
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
            final Location l1 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            final Location l2 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
            for (int j = 0; j <= 8; ++j) {
                l2.setY(l1.getY() + j);
                for (int i = 0; i <= 5; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.GLASS);
                    for (int k = 0; k <= 5; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.GLASS);
                    }
                }
                for (int i = 0; i <= 5; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.GLASS);
                    for (int k = 0; k <= 5; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.GLASS);
                    }
                }
                for (int i = 0; i <= 5; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.GLASS);
                    for (int k = 0; k <= 5; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.GLASS);
                    }
                }
                for (int i = 0; i <= 5; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.GLASS);
                    for (int k = 0; k <= 5; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.GLASS);
                    }
                }
            }
            for (int j = 1; j <= 6; ++j) {
                l2.setY(l1.getY() + j);
                for (int i = 0; i <= 2; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.WATER);
                    for (int k = 0; k <= 2; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.WATER);
                    }
                }
                for (int i = 0; i <= 2; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.WATER);
                    for (int k = 0; k <= 2; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.WATER);
                    }
                }
                for (int i = 0; i <= 2; ++i) {
                    l2.setX(l1.getX() - i);
                    p.getWorld().getBlockAt(l2).setType(Material.WATER);
                    for (int k = 0; k <= 2; ++k) {
                        l2.setZ(l1.getZ() + k);
                        p.getWorld().getBlockAt(l2).setType(Material.WATER);
                    }
                }
                for (int i = 0; i <= 2; ++i) {
                    l2.setX(l1.getX() + i);
                    p.getWorld().getBlockAt(l2).setType(Material.WATER);
                    for (int k = 0; k <= 2; ++k) {
                        l2.setZ(l1.getZ() - k);
                        p.getWorld().getBlockAt(l2).setType(Material.WATER);
                    }
                }
            }
        }
    }
}
