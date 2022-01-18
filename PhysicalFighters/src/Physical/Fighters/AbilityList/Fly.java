// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.EventData;

public class Fly extends AbilityBase
{
    public Fly() {
        if (!PhysicalFighters.Bapo && !PhysicalFighters.Moone) {
            this.InitAbility("\ud50c\ub77c\uc774", Type.Active_Immediately, Rank.GOD, "\ucca0\uad34\ub97c \ud718\ub450\ub97c\uc2dc\uc5d0 10\ucd08\uac04 \ud558\ub298\uc744 \ub0a0\ub77c\ub2e4\ub2d0 \uc218 \uc788\uc2b5\ub2c8\ub2e4.", "\ub099\ud558 \ub370\ubbf8\uc9c0\ub97c \ubc1b\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(60, 0, true);
            this.RegisterLeftClickEvent();
            EventManager.onEntityDamage.add(new EventData(this, 3));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        if (CustomData == 0) {
            final PlayerInteractEvent Event = (PlayerInteractEvent)event;
            if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                return 0;
            }
        }
        else if (CustomData == 3) {
            final EntityDamageEvent Event2 = (EntityDamageEvent)event;
            if (this.PlayerCheck(Event2.getEntity()) && Event2.getCause() == EntityDamageEvent.DamageCause.FALL) {
                this.GetPlayer().sendMessage(PhysicalFighters.a + "사뿐하게 떨어져 §c데미지§f를 받지 않았습니다.");
                Event2.setCancelled(true);
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        this.GetPlayer().setAllowFlight(true);
        this.GetPlayer().setFlying(true);
        final Timer timer = new Timer();
        timer.schedule(new offTimer(), 10000L);
    }
    
    class offTimer extends TimerTask
    {
        @Override
        public void run() {
            Fly.this.GetPlayer().setFlying(false);
            Fly.this.GetPlayer().setAllowFlight(false);
            Fly.this.GetPlayer().sendMessage(PhysicalFighters.a + "§b지속시간§f이 끝났습니다.");
        }
    }
}
