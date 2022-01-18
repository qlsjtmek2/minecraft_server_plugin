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

public class Flower extends AbilityBase
{
    public Flower() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ud761\ud608\ucd08", Type.Active_Immediately, Rank.SS, "\ucca0\uad34 \uc67c\ud074\ub9ad\uc2dc \ub9de\uc740 \uc0ac\ub78c\uc758 \uccb4\ub825\uc744 \ud761\uc218\ud569\ub2c8\ub2e4.", "\ucca0\uad34 \uc624\ub978\ud074\ub9ad\uc2dc \uc790\uc2e0\uc758 \uccb4\ub825\uc744 \uc18c\ube44\ud574 \ub808\ubca8\uc744 \uc5bb\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(5, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
            this.RegisterRightClickEvent();
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
                if (Event1.getEntity() instanceof Player && this.PlayerCheck(Event1.getDamager()) && this.ItemCheck(ACC.DefaultItem) && !EventManager.DamageGuard) {
                    return 1;
                }
                break;
            }
            case 1: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                if (this.PlayerCheck(Event2.getPlayer()) && this.GetPlayer().getHealth() >= 16 && this.ItemCheck(ACC.DefaultItem)) {
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
            case 1: {
                final EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
                final Player p1 = (Player)Event1.getEntity();
                if (this.GetPlayer().getHealth() <= 14) {
                    this.GetPlayer().setHealth(this.GetPlayer().getHealth() + 6);
                    p1.setHealth(p1.getHealth() - 6);
                }
                else {
                    this.GetPlayer().setHealth(20);
                    p1.setHealth(p1.getHealth() - 6);
                }
                p1.sendMessage(String.format(PhysicalFighters.a + "§e%s§f 님이 당신의 §d체력§f을 흡수했습니다.", this.GetPlayer().getName()));
                this.GetPlayer().sendMessage(String.format(PhysicalFighters.a + "§e%s §f님의 §d체력§f을 흡수했습니다.", p1.getName()));
                break;
            }
            case 2: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                final Player p2 = Event2.getPlayer();
                p2.setLevel(p2.getLevel() + 10);
                p2.sendMessage(PhysicalFighters.a + "§b레벨§f을 얻었습니다. §e+2");
                p2.setHealth(p2.getHealth() - 15);
                break;
            }
        }
    }
}
