// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.EventData;

public class Fish extends AbilityBase
{
    public Fish() {
        this.InitAbility("\uac15\ud0dc\uacf5", Type.Passive_Manual, Rank.A, "\ub09a\uc2ef\ub300\ub85c \uc0c1\ub300\ub97c \ud0c0\uaca9\uc2dc \uc0c1\ub300\uc5d0\uac8c \uac15\ud55c \ub370\ubbf8\uc9c0\ub97c \uc8fc\uace0, \ub9e4\uc6b0 \ub0ae\uc740 \ud655\ub960\ub85c \ubb3c\uace0\uae30\ub97c \uc5bb\uc2b5\ub2c8\ub2e4.", "\ubb3c\uace0\uae30\ub97c \ub4e4\uace0 \uc0c1\ub300\ub97c \ud0c0\uaca9\uc2dc\uc5d0, \ub354\uc6b1\ub354 \uac15\ud55c \ub370\ubbf8\uc9c0\ub97c \uc90d\ub2c8\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this, 0));
        EventManager.onPlayerDropItem.add(new EventData(this, 1));
        EventManager.onPlayerRespawn.add(new EventData(this, 2));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
                if (this.PlayerCheck(Event.getDamager()) && this.ItemCheck(Material.FISHING_ROD.getId())) {
                    return 0;
                }
                if (this.PlayerCheck(Event.getDamager()) && this.ItemCheck(349)) {
                    return 3;
                }
                break;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                if (!this.PlayerCheck(Event2.getPlayer()) || Event2.getItemDrop().getItemStack().getType() != Material.FISHING_ROD) {
                    break;
                }
                final PlayerInventory inv = Event2.getPlayer().getInventory();
                if (!inv.contains(Material.FISHING_ROD, 1)) {
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
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
                Event0.setDamage(Event0.getDamage() + 7);
                if (Math.random() <= 0.05) {
                    Event0.getEntity().getWorld().dropItemNaturally(Event0.getEntity().getLocation(), new ItemStack(349));
                    break;
                }
                break;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                Event2.getPlayer().sendMessage(PhysicalFighters.a + "³¬½Ë´ë´Â ¹ö¸± ¼ö ¾ø½À´Ï´Ù.");
                Event2.setCancelled(true);
                break;
            }
            case 2: {
                final PlayerRespawnEvent Event3 = (PlayerRespawnEvent)event;
                Event3.getPlayer().sendMessage(PhysicalFighters.a + "³¬½Ë´ë°¡ ¡×bÁö±Þ¡×fµË´Ï´Ù.");
                Event3.getPlayer().getInventory().setItem(8, new ItemStack(Material.FISHING_ROD.getId(), 1));
                break;
            }
            case 3: {
                final EntityDamageByEntityEvent Event4 = (EntityDamageByEntityEvent)event;
                Event4.setDamage(Event4.getDamage() + 12);
                break;
            }
        }
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(Material.FISHING_ROD.getId(), 1));
    }
    
    @Override
    public void A_ResetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(Material.FISHING_ROD.getId(), 1));
    }
}
