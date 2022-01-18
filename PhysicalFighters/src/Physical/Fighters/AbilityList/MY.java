// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.EventData;

public class MY extends AbilityBase
{
    Player p;
    
    public MY() {
        this.p = null;
        this.InitAbility("\ub9c8\uc2a4\ud130 \uc774", Type.Active_Immediately, Rank.GOD, "\ucca0\uad34 \ud074\ub9ad\uc2dc \uc774\uc18d, \uacf5\uc18d\uc774 \uc99d\uac00\ud569\ub2c8\ub2e4.", "\ud0ac \ud560 \uc2dc \ucfe8\ud0c0\uc784\uc774 \ucd08\uae30\ud654 \ub429\ub2c8\ub2e4.");
        this.InitAbility(80, 0, true);
        this.RegisterLeftClickEvent();
        EventManager.onEntityDeath.add(new EventData(this, 1));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final PlayerInteractEvent Event = (PlayerInteractEvent)event;
                if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    this.p = Event.getPlayer();
                    return 0;
                }
                break;
            }
            case 1: {
                final EntityDeathEvent Event2 = (EntityDeathEvent)event;
                final Player killed = (Player)Event2.getEntity();
                final Player killerP = killed.getKiller();
                if (killerP == this.p) {
                    this.AbilityCTimerCancel();
                    killerP.sendMessage(PhysicalFighters.a + "쿨타임이 §e초기화 §f되었습니다.");
                    break;
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
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1), true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2), true);
    }
}
