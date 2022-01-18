// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.EventData;

public class Kimimaro extends AbilityBase
{
    public Kimimaro() {
        this.InitAbility("\ud0a4\ubbf8\ub9c8\ub85c", Type.Passive_Manual, Rank.SS, "\ubf08\ub2e4\uadc0\ub85c \uc0c1\ub300\ub97c \uacf5\uaca9\ud560\uc2dc\uc5d0 \uac15\ud55c \ub370\ubbf8\uc9c0\ub97c \uc8fc\uace0,", "40% \ud655\ub960\ub85c \uc0c1\ub300\uc5d0\uac8c 10\ucd08\uac04 \ub3c5\ud6a8\uacfc\ub97c \uc900\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this, 0));
        EventManager.onPlayerDropItem.add(new EventData(this, 1));
        EventManager.onPlayerRespawn.add(new EventData(this, 2));
        EventManager.onEntityDeath.add(new EventData(this, 3));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
                if (this.PlayerCheck(Event.getDamager()) && this.ItemCheck(Material.BONE.getId())) {
                    return 0;
                }
                break;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                if (!this.PlayerCheck(Event2.getPlayer()) || Event2.getItemDrop().getItemStack().getType() != Material.BONE) {
                    break;
                }
                final PlayerInventory inv = Event2.getPlayer().getInventory();
                if (!inv.contains(Material.BONE, 1)) {
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
                final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
                if (!Event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
                    Event.setDamage(Event.getDamage() + 10);
                    final Player p = (Player)Event.getEntity();
                    if (Math.random() <= 0.4) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0), true);
                        break;
                    }
                }
                break;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                Event2.getPlayer().sendMessage(PhysicalFighters.a + "뼈는 버릴 수 없습니다.");
                Event2.setCancelled(true);
                break;
            }
            case 2: {
                final PlayerRespawnEvent Event3 = (PlayerRespawnEvent)event;
                final PlayerInventory inv = Event3.getPlayer().getInventory();
                inv.setItem(8, new ItemStack(Material.BONE.getId(), 1));
                break;
            }
            case 3: {
                final EntityDeathEvent Event4 = (EntityDeathEvent)event;
                final List<ItemStack> itemlist = (List<ItemStack>)Event4.getDrops();
                for (int l = 0; l < itemlist.size(); ++l) {
                    if (itemlist.get(l).getType() == Material.BONE) {
                        itemlist.remove(l);
                    }
                }
                break;
            }
        }
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(Material.BONE.getId(), 1));
    }
    
    @Override
    public void A_ResetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(Material.BONE.getId(), 1));
    }
}
