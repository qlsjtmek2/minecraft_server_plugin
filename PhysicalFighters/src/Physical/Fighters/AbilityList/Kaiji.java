// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.List;
import org.bukkit.inventory.ItemStack;
import Physical.Fighters.PhysicalFighters;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.Script.MainScripter;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Kaiji extends AbilityBase
{
    public Kaiji() {
        this.InitAbility("\uce74\uc774\uc9c0", Type.Passive_Manual, Rank.S, "\ub2e4\uc774\uc544\ubaac\ub4dc\ub85c \uc0c1\ub300\ub97c \ud0c0\uaca9\ud560\uc2dc\uc5d0 30%\ud655\ub960\ub85c \uc0c1\ub300\ub97c \uc989\uc0ac\uc2dc\ud0a4\uace0,", "70%\ud655\ub960\ub85c \uc0ac\ub9dd\ud569\ub2c8\ub2e4.");
        this.InitAbility(20, 0, true);
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
                if (!EventManager.DamageGuard && this.PlayerCheck(Event.getDamager()) && this.ItemCheck(Material.DIAMOND.getId())) {
                    return 0;
                }
                break;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                if (!this.PlayerCheck(Event2.getPlayer()) || Event2.getItemDrop().getItemStack().getType() != Material.DIAMOND) {
                    break;
                }
                final PlayerInventory inv = Event2.getPlayer().getInventory();
                if (!inv.contains(Material.DIAMOND, 1)) {
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
                final Player p = (Player)Event.getEntity();
                if (!p.getWorld().getName().equalsIgnoreCase("world")) {
                    if (Math.random() <= 0.3) {
                        p.damage(5000);
                        for (Player player : MainScripter.PlayerList)
                        	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이  카'의지'에 능력에 의지가 꺾였습니다.", p.getName()));
                        if (!PhysicalFighters.AutoKick) {
                            break;
                        }
                        p.sendMessage(PhysicalFighters.a + "카이지에 의해 §c사망§f했습니다.");
                        if (PhysicalFighters.AutoBan) {
                            p.setBanned(true);
                            break;
                        }
                        break;
                    } else {
                        this.GetPlayer().damage(5000);
                        for (Player player : MainScripter.PlayerList)
                        	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이  도박하다가 손목이 날라갔습니다.", this.GetPlayer().getName()));
                        if (!PhysicalFighters.AutoKick) {
                            break;
                        }
                        this.GetPlayer().sendMessage(PhysicalFighters.a + "카이지에 의해 §c사망§f했습니다.");
                        if (PhysicalFighters.AutoBan) {
                            this.GetPlayer().setBanned(true);
                            break;
                        }
                        break;
                    }
                }
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                Event2.getPlayer().sendMessage(PhysicalFighters.a + "§c다이아는 버릴 수 없습니다.");
                Event2.setCancelled(true);
                break;
            }
            case 2: {
                final PlayerRespawnEvent Event3 = (PlayerRespawnEvent)event;
                final PlayerInventory inv = Event3.getPlayer().getInventory();
                inv.setItem(8, new ItemStack(Material.DIAMOND.getId(), 1));
                break;
            }
            case 3: {
                final EntityDeathEvent Event4 = (EntityDeathEvent)event;
                final List<ItemStack> itemlist = (List<ItemStack>)Event4.getDrops();
                for (int l = 0; l < itemlist.size(); ++l) {
                    if (itemlist.get(l).getType() == Material.DIAMOND) {
                        itemlist.remove(l);
                    }
                }
                break;
            }
        }
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(Material.DIAMOND.getId(), 1));
    }
    
    @Override
    public void A_ResetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(Material.DIAMOND.getId(), 1));
    }
}
