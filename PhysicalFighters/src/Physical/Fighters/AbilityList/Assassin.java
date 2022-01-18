// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.EventData;

public class Assassin extends AbilityBase
{
    public Assassin() {
        if (!PhysicalFighters.Bapo) {
            this.InitAbility("\uc5b4\uc314\uc2e0", Type.Passive_AutoMatic, Rank.C, "\ub4a4\uc5d0\uc11c \uacf5\uaca9\ud560\uc2dc\uc5d0 \ub370\ubbf8\uc9c0\ub97c \ub450\ubc30\ub85c \uc785\ud788\uace0 \ub208\uc744 \uac00\ub9bd\ub2c8\ub2e4.");
            this.InitAbility(0, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (this.PlayerCheck(Event.getDamager()) && Event.getEntity() instanceof Player) {
            final Player p = (Player)Event.getEntity();
            final Player p2 = (Player)Event.getDamager();
            if (!p.getWorld().getName().equalsIgnoreCase("world")) {
                if (AbilityBase.Direction(p) == AbilityBase.Direction(p2)) {
                    return 0;
                }
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0), true);
            Event.setDamage(Event.getDamage() * 2);
            ((Player)Event.getDamager()).sendMessage(PhysicalFighters.a + "백스텝을 §b성공§f하였습니다.");
            p.sendMessage(PhysicalFighters.a + "§c백스텝을 당하셨습니다.");
        }
    }
}
