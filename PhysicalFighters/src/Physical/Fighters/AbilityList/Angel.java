// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.TimerTask;
import java.util.Timer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.Bukkit;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Angel extends AbilityBase
{
    public static String pp;
    public static boolean ppon;
    
    static {
        Angel.pp = "false";
        Angel.ppon = false;
    }
    
    public Angel() {
        if (!PhysicalFighters.Bapo && PhysicalFighters.Gods && !PhysicalFighters.Moone) {
            this.InitAbility("\ucc9c\uc0ac", Type.Active_Immediately, Rank.GOD, "\ucca0\uad34\ub85c \ud0c0\uaca9\ubc1b\uc740 \ub300\uc0c1\uc5d0\uac8c 10\ucd08\uac04 \uc790\uc2e0\uc774 \ubc1b\ub294 \ub370\ubbf8\uc9c0\uc758 \ubc18\uc744 \ud761\uc218\uc2dc\ud0b5\ub2c8\ub2e4.", "\ub3c5, \uc9c8\uc2dd, \ub099\ud558 \ub370\ubbf8\uc9c0\ub97c \ubc1b\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(80, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
            EventManager.onEntityDamage.add(new EventData(this, 3));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        if (CustomData == 0) {
            final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
            if (this.PlayerCheck(Event.getDamager()) && this.ItemCheck(ACC.DefaultItem) && !EventManager.DamageGuard && Angel.pp == "false" && !Angel.ppon) {
                return 0;
            }
            if (this.PlayerCheck(Event.getEntity()) && !EventManager.DamageGuard && Angel.pp != "false" && Angel.ppon) {
                Bukkit.getPlayer(Angel.pp).damage(Event.getDamage() / 2, Event.getEntity());
                Event.setDamage(Event.getDamage() / 2);
            }
        }
        else if (CustomData == 3) {
            final EntityDamageEvent Event2 = (EntityDamageEvent)event;
            if (this.PlayerCheck(Event2.getEntity())) {
                if (Event2.getCause() == EntityDamageEvent.DamageCause.POISON || Event2.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                    Event2.setCancelled(true);
                }
                else if (Event2.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    this.GetPlayer().sendMessage(PhysicalFighters.a + "사뿐하게 떨어져 §c데미지§f를 받지 않았습니다.");
                    Event2.setCancelled(true);
                }
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        Angel.pp = ((Player)Event.getEntity()).getName();
        this.GetPlayer().sendMessage(PhysicalFighters.a + "§e" + ((Player)Event.getEntity()).getName() + "§f님은 이제 §b10초§f간 당신의 데미지의 반을 §d흡수§f합니다.");
        ((Player)Event.getEntity()).sendMessage(PhysicalFighters.a + "당신은 §b10초§f간 " + this.GetPlayer().getName() + "님이 받는 데미지의 반을 §d흡수§f합니다.");
        Angel.ppon = true;
        final Timer timer = new Timer();
        timer.schedule(new offTimer(), 10000L);
    }
    
    class offTimer extends TimerTask
    {
        @Override
        public void run() {
            Angel.ppon = false;
            Angel.pp = "false";
            Angel.this.GetPlayer().sendMessage(PhysicalFighters.a + "지속시간이 끝났습니다.");
        }
    }
}
