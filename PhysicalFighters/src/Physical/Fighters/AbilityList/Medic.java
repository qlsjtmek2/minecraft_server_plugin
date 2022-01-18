// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.EventData;

public class Medic extends AbilityBase
{
    public Medic() {
        this.InitAbility("\uba54\ub515", Type.Active_Immediately, Rank.B, "\ucca0\uad34 \uc67c\ud074\ub9ad\uc2dc \ub9de\uc740 \uc0ac\ub78c\uc758 \uccb4\ub825\uc774 6 \ud68c\ubcf5\ub429\ub2c8\ub2e4.", "\ucca0\uad34 \uc624\ub978\ud074\ub9ad\uc2dc \uc790\uc2e0\uc758 \uccb4\ub825\uc744 6 \ud68c\ubcf5\ud569\ub2c8\ub2e4.", "\ub450 \uae30\ub2a5\uc740 \ucfe8\ud0c0\uc784\uc744 \uacf5\uc720\ud569\ub2c8\ub2e4.");
        this.InitAbility(5, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
        this.RegisterRightClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
                if (Event1.getEntity() instanceof Player && this.PlayerCheck(Event1.getDamager()) && this.ItemCheck(ACC.DefaultItem)) {
                    return 0;
                }
                break;
            }
            case 1: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                if (this.PlayerCheck(Event2.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    return 1;
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
                final EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
                final Player p1 = (Player)Event1.getEntity();
                if (p1.getHealth() <= 14) {
                    p1.setHealth(p1.getHealth() + 6);
                }
                else {
                    p1.setHealth(20);
                }
                p1.sendMessage(String.format(PhysicalFighters.a + "§e%s§f님의 §d메딕 §f능력으로 체력을 6 §c회복§f했습니다.", this.GetPlayer().getName()));
                this.GetPlayer().sendMessage(String.format(PhysicalFighters.a + "§e%s§f님의 체력을 6 §c회복※f시켰습니다.", p1.getName()));
                Event1.setCancelled(true);
                break;
            }
            case 1: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                final Player p2 = Event2.getPlayer();
                if (p2.getHealth() <= 14) {
                    p2.setHealth(p2.getHealth() + 6);
                } else {
                    p2.setHealth(p2.getMaxHealth());
                }
                p2.sendMessage(PhysicalFighters.a + "§e%s§f님의 §d메딕 §f능력으로 체력을 6 §c회복§f했습니다.");
                break;
            }
        }
    }
}
