// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.EventData;

public class Hulk extends AbilityBase
{
    boolean playerhulk;
    int playerhealth;
    
    public Hulk() {
        this.playerhulk = false;
        this.playerhealth = 20;
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ud5d0\ud06c", Type.Active_Immediately, Rank.SSS, "\ucca0\uad34 \uc624\ub978\ucabd\ud074\ub9ad\uc2dc\uc5d0 30\ucd08\uac04 \ub9e4\uc6b0 \uac15\ud574\uc9d1\ub2c8\ub2e4.", "\ubc84\ud504\ub97c \ubc1b\uc73c\uba70, \ubaa8\ub4e0 \ub370\ubbf8\uc9c0\ub97c \ubc18\uc73c\ub85c \uc904\uc5ec\ubc1b\uc73c\uba70, \uc77c\ubd80 \uc561\ud2f0\ube0c\ub2a5\ub825\uc744 \ubb34\uc2dc\ud569\ub2c8\ub2e4.", "\ub2f9\uc2e0\uc758 \ub370\ubbf8\uc9c0\ub294 1.5\ubc30\uac00 \ub418\uba70, \ub2f9\uc2e0\uc758 \uacf5\uaca9 \ubc94\uc704\uac00 \ub113\uc5b4\uc9d1\ub2c8\ub2e4.");
            this.InitAbility(180, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
            this.RegisterRightClickEvent();
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
                if (!(Event1.getEntity() instanceof Player)) {
                    break;
                }
                if (this.PlayerCheck(Event1.getDamager())) {
                    if (this.playerhulk) {
                        Event1.setDamage((int)(Event1.getDamage() * 1.5));
                        ((Player)Event1.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0), true);
                    }
                    else {
                        Event1.setDamage(Event1.getDamage());
                    }
                }
                if (!this.PlayerCheck(Event1.getEntity())) {
                    break;
                }
                if (this.playerhulk) {
                    Event1.setDamage(Event1.getDamage() / 2);
                    break;
                }
                Event1.setDamage(Event1.getDamage());
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
        final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
        final Player p = Event2.getPlayer();
        this.playerhealth = p.getHealth();
        p.getWorld().createExplosion(p.getLocation(), 0.0f);
        p.setHealth(20);
        p.sendMessage(PhysicalFighters.a + "당신은 §c헐크§f로 변신했으며, §e30초§f간 무척 강해집니다. §e30초§f가 지나면 당신은 원래대로 돌아옵니다.");
        this.playerhulk = true;
        this.GetPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0), true);
        this.GetPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0), true);
        this.GetPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 0), true);
        this.GetPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0), true);
        this.GetPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 0), true);
        this.GetPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 0), true);
        final Timer timer = new Timer();
        timer.schedule(new Pauck(this.GetPlayer()), 30000L);
    }
    
    class Pauck extends TimerTask
    {
        Player p1;
        
        Pauck(final Player pp1) {
            this.p1 = pp1;
        }
        
        @Override
        public void run() {
            this.p1.setHealth(Hulk.this.playerhealth);
            Hulk.this.playerhulk = false;
            this.p1.sendMessage(PhysicalFighters.a + "원래대로 돌아왔습니다.");
        }
    }
}
