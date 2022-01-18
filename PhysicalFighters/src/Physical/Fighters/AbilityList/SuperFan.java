// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;

public class SuperFan extends AbilityBase
{
    public SuperFan() {
        this.InitAbility("\uc120\ud48d\uae30", Type.Active_Immediately, Rank.B, "\ucca0\uad34\ub97c \ub4e4\uace0 \uc67c\ucabd\ud074\ub9ad\ud558\uba74 \ubc14\ub77c\ubcf4\ub294 \ubc29\ud5a5\uc758 \ud50c\ub808\uc774\uc5b4\ub4e4\uc744  \ub0a0\ub824\ubc84\ub9bd\ub2c8\ub2e4.", "\uc774\ub54c \ub0a0\ub77c\uac04 \ud50c\ub808\uc774\uc5b4\ub4e4\uc740 \ubb34\ub354\uc704\uc5d0 \uc2dc\uc6d0\ud568\uc744 \ub290\uaef4 \uccb4\ub825\uc774 \ud68c\ubcf5\ub429\ub2c8\ub2e4.", "\ud558\uc9c0\ub9cc \uac15\ud55c \ubc14\ub78c\uc5d0 \uc758\ud574 \ub208\uc744 \ub728\uae30\uac00 \ud798\ub4e4\uace0, \ud5c8\uc57d\ud574\uc9d1\ub2c8\ub2e4.");
        this.InitAbility(15, 0, true);
        this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        final Location l = Event.getPlayer().getLocation();
        final Location l2 = Event.getPlayer().getLocation();
        final double degrees = Math.toRadians(-(l.getYaw() % 360.0f));
        final double ydeg = Math.toRadians(-(l.getPitch() % 360.0f));
        for (int i = 1; i < 10; ++i) {
            l2.setX(l.getX() + (1 * i + 2) * (Math.sin(degrees) * Math.cos(ydeg)));
            l2.setY(l.getY() + (1 * i + 2) * Math.sin(ydeg));
            l2.setZ(l.getZ() + (1 * i + 2) * (Math.cos(degrees) * Math.cos(ydeg)));
            final Player[] pp = Bukkit.getOnlinePlayers();
            for (int ii = 0; ii < Bukkit.getOnlinePlayers().length; ++ii) {
                if (pp[ii] != this.GetPlayer()) {
                    final Location loc = pp[ii].getLocation();
                    if (l2.distance(loc) <= 3.0 && !EventManager.DamageGuard) {
                        pp[ii].setVelocity(pp[ii].getVelocity().add(p.getLocation().toVector().subtract(pp[ii].getLocation().toVector()).normalize().multiply(-1)));
                        pp[ii].addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0), true);
                        pp[ii].addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2), true);
                        pp[ii].addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2), false);
                        pp[ii].addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 2), false);
                        pp[ii].sendMessage(PhysicalFighters.a + "§e앗! §f바람이 강하지만 §b시원해§f~§4♥");
                    }
                }
            }
        }
    }
}
