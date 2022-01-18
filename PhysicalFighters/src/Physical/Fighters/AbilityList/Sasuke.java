// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Sasuke extends AbilityBase
{
    public Sasuke() {
        this.InitAbility("\uc0ac\uc2a4\ucf00", Type.Active_Immediately, Rank.S, "\ucca0\uad34\ub85c \uc0c1\ub300\ub97c \ud0c0\uaca9\uc2dc\uc5d0 \uce58\ub3c4\ub9ac\ub97c \uc0ac\uc6a9\ud574 \uc0c1\ub300\ub97c \uc5c4\uccad\ub09c \ub370\ubbf8\uc9c0\ub85c \uac10\uc804\uc2dc\ud0b5\ub2c8\ub2e4.");
        this.InitAbility(30, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (this.PlayerCheck(Event.getDamager()) && this.ItemCheck(ACC.DefaultItem) && !EventManager.DamageGuard) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        p.damage(15);
        Event.getEntity().getWorld().strikeLightning(Event.getEntity().getLocation());
    }
}
