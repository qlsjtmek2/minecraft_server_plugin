// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Trash extends AbilityBase
{
    public Trash() {
        if (PhysicalFighters.SRankUsed && !PhysicalFighters.Bapo && !PhysicalFighters.Specialability) {
            this.InitAbility("\uc4f0\ub808\uae30", Type.Active_Immediately, Rank.FF, "\ub2a5\ub825 \uc0ac\uc6a9\uc2dc \uccb4\ub825\uc744 \uc18c\ube44\ud558\uc5ec 1\ubd84\uac04 \ud5c8\uc57d\ud574\uc9d1\ub2c8\ub2e4.", "\ucca0\uad34\ub85c \uc0c1\ub300\ub97c \ud0c0\uaca9\uc2dc 1%\ud655\ub960\ub85c \ub2a5\ub825\uc744 \uc11c\ub85c \ubc14\uafc9\ub2c8\ub2e4.");
            this.InitAbility(10, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
            this.RegisterRightClickEvent();
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
                if (this.PlayerCheck(Event0.getDamager()) && Math.random() <= 0.01) {
                    final Player p1 = (Player)Event0.getDamager();
                    final Player p2 = (Player)Event0.getEntity();
                    final AbilityBase a = AbilityBase.FindAbility(p1);
                    final AbilityBase a2 = AbilityBase.FindAbility(p2);
                    a2.SetPlayer(p1, false);
                    a.SetPlayer(p2, false);
                    a2.SetRunAbility(true);
                    a.SetRunAbility(true);
                    p1.sendMessage(PhysicalFighters.a + "당신은 §c쓰레기 §f능력을 사용해 상대방과 능력을 바꿨습니다.");
                    p2.sendMessage(PhysicalFighters.a + "당신은 §c쓰레기 §f능력에 의해 쓰레기가 되었습니다.");
                    break;
                }
                break;
            }
            case 1: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                if (this.PlayerCheck(Event2.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    return 0;
                }
                break;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        p.setHealth(p.getHealth() - 4);
        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1200, 0), true);
    }
}
