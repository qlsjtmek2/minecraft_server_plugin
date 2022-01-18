// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.EventData;

public class CP9 extends AbilityBase
{
    public CP9() {
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("CP9", Type.Active_Immediately, Rank.S, "\ucca0\uad34\ub85c \uc0c1\ub300 \ud0c0\uaca9\uc2dc\uc5d0 \uac15\ud55c\ub370\ubbf8\uc9c0\ub97c \uc90d\ub2c8\ub2e4.", "\ucca0\uad34 \uc624\ub978\ud074\ub9ad\uc2dc \ud3ed\ubc1c\uacfc\ud568\uaed8 \ubc14\ub77c\ubcf4\ub294 \ubc29\ud5a5\uc73c\ub85c \ube60\ub974\uac8c \uc804\uc9c4\ud569\ub2c8\ub2e4. [\uc810\ud504\uc640 \uc0ac\uc6a9\ud558\uba74 \ud6a8\uc728\uc801]", "*\ub099\ud558\ub370\ubbf8\uc9c0\ub97c \ubc1b\uc9c0\uc54a\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(15, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
            this.RegisterRightClickEvent();
            EventManager.onEntityDamage.add(new EventData(this, 3));
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
                if (this.PlayerCheck(Event2.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    return 2;
                }
                break;
            }
        }
        if (CustomData == 3) {
            final EntityDamageEvent Event3 = (EntityDamageEvent)event;
            if (this.PlayerCheck(Event3.getEntity()) && Event3.getCause() == EntityDamageEvent.DamageCause.FALL) {
                Event3.setCancelled(true);
                this.GetPlayer().sendMessage(PhysicalFighters.a + "사뿐하게 떨어져 §c데미지§f를 받지 않았습니다.");
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
                p1.setHealth(p1.getHealth() - 6);
                this.GetPlayer().sendMessage(String.format(PhysicalFighters.a + "§e%s §f님에게 §c지건§f을 사용했습니다.", p1.getName()));
                break;
            }
            case 2: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                final Location loca = Event2.getPlayer().getLocation();
                final Location loca2 = Event2.getPlayer().getLocation();
                final Player p2 = Event2.getPlayer();
                final double degrees = Math.toRadians(-(Event2.getPlayer().getLocation().getYaw() % 360.0f));
                final double ydeg = Math.toRadians(-(Event2.getPlayer().getLocation().getPitch() % 360.0f));
                loca.setX(Event2.getPlayer().getLocation().getX() + -1.5 * (Math.sin(degrees) * Math.cos(ydeg)));
                loca.setY(Event2.getPlayer().getLocation().getY() + -1.5 * Math.sin(ydeg));
                loca.setZ(Event2.getPlayer().getLocation().getZ() + -1.5 * (Math.cos(degrees) * Math.cos(ydeg)));
                Event2.getPlayer().getWorld().createExplosion(loca, 0.0f);
                loca2.setX(Event2.getPlayer().getLocation().getX() + 5.0 * (Math.sin(degrees) * Math.cos(ydeg)));
                loca2.setY(Event2.getPlayer().getLocation().getY() + 5.0 * Math.sin(ydeg));
                loca2.setZ(Event2.getPlayer().getLocation().getZ() + 5.0 * (Math.cos(degrees) * Math.cos(ydeg)));
                p2.setVelocity(p2.getVelocity().add(loca2.toVector().subtract(Event2.getPlayer().getLocation().toVector()).normalize().multiply(5)));
                break;
            }
        }
    }
}
