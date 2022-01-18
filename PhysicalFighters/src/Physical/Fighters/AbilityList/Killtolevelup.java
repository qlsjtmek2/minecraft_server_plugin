// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
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
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Killtolevelup extends AbilityBase
{
    int dama;
    
    public Killtolevelup() {
        this.dama = 5;
        this.InitAbility("\ud3ed\uc8fc", Type.Passive_Manual, Rank.SS, "\uae43\ud138\uc758 \ucc98\uc74c \ub370\ubbf8\uc9c0\ub294 5\uc785\ub2c8\ub2e4.", "\uae43\ud138\ub85c 1\ud0ac\uc744 \ud560\ub54c\ub9c8\ub2e4 \ub370\ubbf8\uc9c0\uac00 2\uc529 \ub298\uc5b4\ub0a9\ub2c8\ub2e4.", "(\ub370\ubbf8\uc9c0\ub294 12\ub97c \ub118\uc744 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.)");
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
                if (this.PlayerCheck(Event.getDamager()) && this.ItemCheck(Material.FEATHER.getId())) {
                    return 0;
                }
                break;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                if (!this.PlayerCheck(Event2.getPlayer()) || Event2.getItemDrop().getItemStack().getType() != Material.FEATHER) {
                    break;
                }
                final PlayerInventory inv = Event2.getPlayer().getInventory();
                if (!inv.contains(Material.FEATHER, 1)) {
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
                if (Event4.getEntity().getKiller() != null && this.PlayerCheck(Event4.getEntity().getKiller()) && this.ItemCheck(Material.FEATHER.getId()) && Event4.getEntity() instanceof Player) {
                    return 4;
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
                Event.setDamage(Event.getDamage() + this.dama);
                break;
            }
            case 1: {
                final PlayerDropItemEvent Event2 = (PlayerDropItemEvent)event;
                Event2.getPlayer().sendMessage(PhysicalFighters.a + "깃털은 버릴 수 없습니다.");
                Event2.setCancelled(true);
                break;
            }
            case 2: {
                final PlayerRespawnEvent Event3 = (PlayerRespawnEvent)event;
                final PlayerInventory inv = Event3.getPlayer().getInventory();
                inv.setItem(8, new ItemStack(Material.FEATHER.getId(), 1));
                break;
            }
            case 3: {
                final EntityDeathEvent Event4 = (EntityDeathEvent)event;
                final List<ItemStack> itemlist = (List<ItemStack>)Event4.getDrops();
                for (int l = 0; l < itemlist.size(); ++l) {
                    if (itemlist.get(l).getType() == Material.FEATHER) {
                        itemlist.remove(l);
                    }
                }
                break;
            }
            case 4: {
                final EntityDeathEvent Event5 = (EntityDeathEvent)event;
                if (this.dama < 12) {
                    this.dama += 2;
                    for (Player player : MainScripter.PlayerList)
                    	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님을 죽이고 §c%s §6님이 §c폭주§6했습니다.", ((Player)Event5.getEntity()).getName(), Event5.getEntity().getKiller().getName()));
                    break;
                }
                this.dama = 12;
                Event5.getEntity().getKiller().sendMessage(PhysicalFighters.a + "§c당신은 이미 충분히 성장했습니다.");
                break;
            }
        }
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(Material.FEATHER.getId(), 1));
    }
    
    @Override
    public void A_ResetEvent(final Player p) {
        p.getInventory().setItem(8, new ItemStack(Material.FEATHER.getId(), 1));
    }
}
