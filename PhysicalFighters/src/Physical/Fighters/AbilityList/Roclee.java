// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Roclee extends AbilityBase
{
    public Roclee() {
        this.InitAbility("\ub85d\ub9ac", Type.Active_Immediately, Rank.S, "\ucca0\uad34\ub85c \uc0c1\ub300\ub97c \ud0c0\uaca9\ud560\uc2dc \ub9de\uc740\uc0ac\ub78c\uc744 \ub9e4\uc6b0\ube60\ub978\uc18d\ub3c4\ub85c \ub192\uc774 \ub744\uc6c1\ub2c8\ub2e4.");
        this.InitAbility(20, 0, true);
        EventManager.onEntityDamageByEntity.add(new EventData(this));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (this.PlayerCheck(Event.getDamager()) && this.ItemCheck(ACC.DefaultItem)) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        final Location l1 = Event.getEntity().getLocation();
        final Location l2 = Event.getEntity().getLocation();
        final Location i = this.GetPlayer().getLocation();
        final Location ll = this.GetPlayer().getLocation();
        Event.getEntity().getWorld().createExplosion(Event.getEntity().getLocation(), 0.0f);
        l2.setY(l1.getY() + 8.0);
        p.teleport(l2);
        Event.getEntity().getWorld().createExplosion(l2, 1.0f);
        ll.setY(i.getY() + 8.0);
        p.damage(12);
        this.GetPlayer().teleport(ll);
    }
}
