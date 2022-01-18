// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileHitEvent;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Fireball extends AbilityBase
{
    public Fireball() {
        this.InitAbility("\ud30c\uc774\uc5b4\ubcfc", Type.Active_Immediately, Rank.A, "\ubc14\ub77c\ubcf4\ub294 \ubc29\ud5a5\uc5d0 \uc57d\ud55c \ud654\uc5fc\uad6c\ub97c \ub0a0\ub9bd\ub2c8\ub2e4.");
        this.InitAbility(30, 0, true);
        this.RegisterLeftClickEvent();
        EventManager.onProjectileHitEvent.add(new EventData(this, 1));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final PlayerInteractEvent Event = (PlayerInteractEvent)event;
                if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    return 0;
                }
                break;
            }
            case 1: {
                final ProjectileHitEvent Event2 = (ProjectileHitEvent)event;
                if (Event2.getEntity().getType() == EntityType.FIREBALL) {
                    Event2.getEntity().getWorld().createExplosion(Event2.getEntity().getLocation(), 3.0f, true);
                    Event2.getEntity().getWorld().createExplosion(Event2.getEntity().getLocation(), 2.5f, true);
                    Event2.getEntity().getWorld().createExplosion(Event2.getEntity().getLocation(), 3.0f, true);
                    break;
                }
                break;
            }
        }
        return -1;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            final World a = p.getWorld();
            final Location loc = p.getLocation();
            final Location l = p.getLocation();
            l.setY(p.getLocation().getY() + 2.0);
            final double degrees = Math.toRadians(-(l.getYaw() % 360.0f));
            final double ydeg = Math.toRadians(-(l.getPitch() % 360.0f));
            loc.setX(l.getX() + 1.2 * (Math.sin(degrees) * Math.cos(ydeg)));
            loc.setY(l.getY() + 1.2 * Math.sin(ydeg));
            loc.setZ(l.getZ() + 1.2 * (Math.cos(degrees) * Math.cos(ydeg)));
            a.spawn(loc, (Class)org.bukkit.entity.Fireball.class);
        }
    }
}
