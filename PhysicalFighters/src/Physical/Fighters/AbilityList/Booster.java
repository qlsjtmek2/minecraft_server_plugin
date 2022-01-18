// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Random;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Booster extends AbilityBase
{
    public Booster() {
        this.InitAbility("\ubd80\uc2a4\ud130", Type.Passive_AutoMatic, Rank.SSS, "\uacf5\uaca9\uc2dc\uc5d0 \ub51c\ub808\uc774\uac00 \ub9e4\uc6b0 \ub0ae\uc2b5\ub2c8\ub2e4. \ub2e8 \ub2f9\uc2e0\uc758 \ub370\ubbf8\uc9c0\ub294 1~4\ub85c \uc77c\uc815\ud574\uc9d1\ub2c8\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (this.PlayerCheck(Event.getDamager()) && Event.getEntity() instanceof Player) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0), true);
            p.setNoDamageTicks(8);
            final Random rand = new Random();
            final int r = rand.nextInt(6);
            switch (r) {
                case 0: {
                    Event.setDamage(3);
                }
                case 1: {
                    Event.setDamage(2);
                }
                case 2: {
                    Event.setDamage(3);
                }
                case 5: {
                    Event.setDamage(4);
                }
                case 3: {
                    Event.setDamage(3);
                }
                case 4: {
                    Event.setDamage(1);
                    break;
                }
            }
        }
    }
}
