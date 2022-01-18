// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.EventData;

public class Kijaru extends AbilityBase
{
    public Kijaru() {
        this.InitAbility("\ud0a4\uc790\ub8e8", Type.Active_Immediately, Rank.SS, "\ucca0\uad34\ub85c \ud0c0\uaca9\uc744 \ub2f9\ud55c \uc0c1\ub300\ub97c \ube5b\uc758 \uc18d\ub3c4\ub85c \ud0c0\uaca9\ud569\ub2c8\ub2e4.", "\uc0c1\ub300\ub294 \uc5c4\uccad\ub09c \uc18d\ub3c4\ub85c \uba40\ub9ac \ub0a0\ub77c\uac11\ub2c8\ub2e4. \ub2f9\uc2e0\ub3c4 \uc0c1\ub300\ub97c \ub530\ub77c \uadfc\uc811\ud558\uac8c \ub0a0\ub77c\uac11\ub2c8\ub2e4.", "\ub099\ud558\ub370\ubbf8\uc9c0\ub97c \ubc1b\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
        this.InitAbility(45, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
        EventManager.onEntityDamage.add(new EventData(this, 3));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        if (CustomData == 3) {
            final EntityDamageEvent Event2 = (EntityDamageEvent)event;
            if (this.PlayerCheck(Event2.getEntity()) && Event2.getCause() == EntityDamageEvent.DamageCause.FALL) {
                Event2.setCancelled(true);
                this.GetPlayer().sendMessage(PhysicalFighters.a + "사뿐하게 떨어져 §c데미지§f를 받지 않았습니다.");
            }
        }
        else {
            final EntityDamageByEntityEvent Event3 = (EntityDamageByEntityEvent)event;
            if (this.PlayerCheck(Event3.getDamager()) && this.ItemCheck(ACC.DefaultItem)) {
                return 0;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        Event.setDamage(8);
        final Location l2 = Event.getEntity().getLocation();
        l2.setY(Event.getEntity().getLocation().getY() + 1.0);
        Event.getEntity().teleport(l2);
        this.goPlayerVelocity((Player)Event.getEntity(), (Player)Event.getDamager(), -10);
        Event.getEntity().getWorld().createExplosion(Event.getEntity().getLocation(), 0.0f);
        final Timer timer = new Timer();
        timer.schedule(new Kizaru(Event.getDamager(), Event.getEntity()), 1000L);
    }
    
    class Kizaru extends TimerTask
    {
        Player player22;
        Player player;
        
        Kizaru(final Entity entity, final Entity entity2) {
            this.player22 = (Player)entity;
            this.player = (Player)entity2;
        }
        
        @Override
        public void run() {
            final Location loc2 = this.player.getLocation();
            loc2.setY(this.player.getLocation().getY() + 2.0);
            this.player22.teleport(loc2);
        }
    }
}
