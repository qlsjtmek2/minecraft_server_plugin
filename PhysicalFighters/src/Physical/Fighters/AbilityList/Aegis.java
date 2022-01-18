// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Aegis extends AbilityBase
{
    public Aegis() {
        if (!PhysicalFighters.ReverseMode) {
            this.InitAbility("\uc774\uc9c0\uc2a4", Type.Active_Continue, Rank.A, "\ub2a5\ub825 \uc0ac\uc6a9\uc2dc \uc77c\uc815\uc2dc\uac04\ub3d9\uc548 \ubb34\uc801\uc774 \ub429\ub2c8\ub2e4. \ubb34\uc801\uc740 \ub300\ubd80\ubd84\uc758", "\ub370\ubbf8\uc9c0\ub97c \ubb34\ub825\ud654\uc2dc\ud0a4\uba70 \ub2a5\ub825 \uc0ac\uc6a9\uc911\uc5d4 Mirroring \ub2a5\ub825\ub3c4 ", "\ubb34\ub825\ud654\ub429\ub2c8\ub2e4.");
            this.InitAbility(28, 6, true);
            this.RegisterLeftClickEvent();
            EventManager.onEntityDamage.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        if (!PhysicalFighters.ReverseMode) {
            final PlayerInteractEvent Event = (PlayerInteractEvent)event;
            if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                return 0;
            }
        }
        else {
            final EntityDamageEvent Event2 = (EntityDamageEvent)event;
            if (this.PlayerCheck(Event2.getEntity())) {
                return 0;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        if (!PhysicalFighters.ReverseMode) {
            final EntityDamageEvent Event = (EntityDamageEvent)event;
            if (this.PlayerCheck(Event.getEntity())) {
                final Player p = (Player)Event.getEntity();
                if (!p.getWorld().getName().equalsIgnoreCase("world")) {
                    p.setFireTicks(0);
                    Event.setCancelled(true);
                }
            }
        }
        else {
            final EntityDamageEvent Event = (EntityDamageEvent)event;
            Event.setDamage(Event.getDamage() * 1000);
        }
    }
}
