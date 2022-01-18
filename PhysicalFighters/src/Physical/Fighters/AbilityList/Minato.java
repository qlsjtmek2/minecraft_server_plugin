// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.EventData;

public class Minato extends AbilityBase
{
    public Minato() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ubbf8\ub098\ud1a0", Type.Passive_Manual, Rank.S, "\ub208\ub369\uc774\ub97c \ub358\uc838, \ub9de\uc740 \uc801\uc5d0\uac8c \ud154\ub808\ud3ec\ud2b8\ud569\ub2c8\ub2e4.");
            this.InitAbility(1, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this, 0));
            EventManager.onPlayerDropItem.add(new EventData(this, 1));
            EventManager.onPlayerRespawn.add(new EventData(this, 2));
            EventManager.onEntityDeath.add(new EventData(this, 3));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
                if (!(Event0.getDamager() instanceof Snowball)) {
                    break;
                }
                final Snowball a = (Snowball)Event0.getDamager();
                if (!this.PlayerCheck((Entity)a.getShooter())) {
                    break;
                }
                if (Event0.getEntity() instanceof Player && (Player)a.getShooter() == (Player)Event0.getEntity()) {
                    return 9999;
                }
                return 0;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                if (!this.PlayerCheck(Event2.getPlayer()) || Event2.getItemDrop().getItemStack().getType() != Material.SNOW_BALL) {
                    break;
                }
                final PlayerInventory inv = Event2.getPlayer().getInventory();
                if (!inv.contains(Material.SNOW_BALL, 16)) {
                    return 1;
                }
                break;
            }
            case 2: {
                final PlayerRespawnEvent Event3 = (PlayerRespawnEvent)event;
                if (this.PlayerCheck(Event3.getPlayer())) {
                    return 2;
                }
                break;
            }
            case 3: {
                final EntityDeathEvent Event4 = (EntityDeathEvent)event;
                if (this.PlayerCheck((Entity)Event4.getEntity())) {
                    return 3;
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
                final EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
                final Player pe = (Player)Event0.getEntity();
                if (!pe.getWorld().getName().equalsIgnoreCase("world")) {
                	final Location l1 = pe.getLocation();
                	this.GetPlayer().teleport(l1);
                }
                break;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                Event2.getPlayer().sendMessage(PhysicalFighters.a + "소유한 눈덩이가 §e16개 §f이하일시 못버립니다.");
                Event2.setCancelled(true);
                break;
            }
            case 2: {
                final PlayerRespawnEvent Event3 = (PlayerRespawnEvent)event;
                Event3.getPlayer().sendMessage(PhysicalFighters.a + "§l눈덩이§r가 지급됩니다.");
                final PlayerInventory inv = Event3.getPlayer().getInventory();
                inv.setItem(8, new ItemStack(332, 64));
                inv.setItem(7, new ItemStack(332, 64));
                break;
            }
            case 3: {
                final EntityDeathEvent Event4 = (EntityDeathEvent)event;
                final List<ItemStack> itemlist = (List<ItemStack>)Event4.getDrops();
                for (int i = 0; i < itemlist.size(); ++i) {
                    if (itemlist.get(i).getType() == Material.SNOW_BALL) {
                        itemlist.remove(i);
                    }
                }
                break;
            }
        }
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(332, 64));
        p.getInventory().setItem(7, new ItemStack(332, 64));
    }
    
    @Override
    public void A_ResetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(332, 64));
        p.getInventory().setItem(7, new ItemStack(332, 64));
    }
}
