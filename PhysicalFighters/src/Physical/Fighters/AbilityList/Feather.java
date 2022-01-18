// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Feather extends AbilityBase
{
    public Feather() {
        if (!PhysicalFighters.Specialability) {
            this.InitAbility("\uae43\ud138", Type.Passive_AutoMatic, Rank.C, "\ub099\ud558 \ub370\ubbf8\uc9c0\uc640 \ubb3c\uc18d\uc5d0\uc11c\uc758 \uc9c8\uc2dd \ub370\ubbf8\uc9c0\ub97c \ubc1b\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.", "90% \ud655\ub960\ub85c \ud3ed\ubc1c,\ubc88\uac1c \ub370\ubbf8\uc9c0\ub97c 1\ub85c \uc904\uc5ec\ubc1b\uc73c\uba70 \ubbf8\ub7ec\ub9c1 \ub2a5\ub825\uc744", "\ud68c\ud53c\ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4, \ub099\ud558\uc2dc 1\ubd84\uac04 \uc18d\ub3c4, \uc810\ud504\ub825\uc774 \ube68\ub77c\uc9c0\ub294 \ubc84\ud504\ub97c \ubc1b\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(0, 0, true);
            EventManager.onEntityDamage.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        if (this.PlayerCheck(Event.getEntity())) {
            if (Event.getCause() == EntityDamageEvent.DamageCause.FALL || Event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                return 0;
            }
            if (Math.random() <= 0.9 && (Event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING || Event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
                return 1;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageEvent Event = (EntityDamageEvent)event;
        ((Player)Event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 0), true);
        ((Player)Event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 0), true);
        Event.setCancelled(true);
    }
}
