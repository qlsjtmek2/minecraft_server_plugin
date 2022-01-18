// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Material;
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

public class Bishop extends AbilityBase
{
    public Bishop() {
        if (!PhysicalFighters.Specialability) {
            this.InitAbility("\ube44\uc20d", Type.Active_Immediately, Rank.B, "\ucca0\uad34 \uc67c\ud074\ub9ad\uc2dc \ub9de\uc740 \uc0ac\ub78c\uc5d0\uac8c \uac01\uc885 \ucd95\ubcf5\uc744 \uac81\ub2c8\ub2e4.", "\ucca0\uad34 \uc624\ub978\ud074\ub9ad\uc2dc \uc790\uc2e0\uc5d0\uac8c \uac01\uc885 \ucd95\ubcf5\uc744 \uac81\ub2c8\ub2e4.", "\uae08\uad34\ub97c \uc801\uc5d0\uac8c \uc67c\ud074\ub9ad\uc2dc \uac01\uc885 \uc800\uc8fc\ub97c \uac81\ub2c8\ub2e4.", "\uc138 \uae30\ub2a5\uc740 \ucfe8\ud0c0\uc784\uc744 \uacf5\uc720\ud558\uba70 \ubaa8\ub4e0 \ud6a8\uacfc \uc9c0\uc18d\uc2dc\uac04\uc740", "15\ucd08\uc785\ub2c8\ub2e4.");
            this.InitAbility(30, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
            this.RegisterRightClickEvent();
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
                if (!(Event1.getEntity() instanceof Player) || !this.PlayerCheck(Event1.getDamager())) {
                    break;
                }
                if (this.ItemCheck(ACC.DefaultItem)) {
                    return 0;
                }
                if (this.ItemCheck(Material.GOLD_INGOT.getId())) {
                    return 2;
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
                final EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
                final Player p0 = (Player)Event0.getEntity();
                p0.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 0), true);
                p0.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 0), true);
                p0.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300, 0), true);
                p0.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 300, 0), true);
                p0.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 300, 0), true);
                p0.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 0), true);
                p0.sendMessage(PhysicalFighters.a + "비숍이 당신에게 §d축복§f을 걸었습니다. §e15초 지속.");
                Event0.setCancelled(true);
                break;
            }
            case 1: {
                final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
                final Player p2 = Event2.getPlayer();
                p2.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 0), true);
                p2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 0), true);
                p2.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300, 0), true);
                p2.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 300, 0), true);
                p2.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 300, 0), true);
                p2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 0), true);
                p2.sendMessage(PhysicalFighters.a + "자신에게 §d축복§f을 걸었습니다. §e15초 지속.");
                break;
            }
            case 2: {
                final EntityDamageByEntityEvent Event3 = (EntityDamageByEntityEvent)event;
                final Player p3 = (Player)Event3.getEntity();
                p3.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 0), true);
                p3.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 0), true);
                p3.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 0), true);
                p3.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 0), true);
                p3.sendMessage(PhysicalFighters.a + "비숍이 당신에게 §c저주§f를 걸었습니다. §e15초 지속.");
                break;
            }
        }
    }
}
