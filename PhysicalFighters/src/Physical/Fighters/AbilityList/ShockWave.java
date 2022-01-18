// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class ShockWave extends AbilityBase
{
    public ShockWave() {
        this.InitAbility("\uc1fc\ud06c\uc6e8\uc774\ube0c", Type.Active_Immediately, Rank.A, "\uc790\uc2e0\uc774 \ubcf4\uace0\uc788\ub294 \ubc29\ud5a5\uc73c\ub85c \ub9c9\uac15\ud55c \uc9c1\uc120 \ucda9\uaca9\ud3ec\ub97c \uc3e9\ub2c8\ub2e4.", "\uc5b4\ub5a4 \ubc29\ud5a5\uc73c\ub85c\ub3c4 \ubc1c\uc0ac\ud560\uc218 \uc788\uc73c\uba70 \ubc1c\uc0ac \uc81c\uc57d\uc774 \uc5c6\uc2b5\ub2c8\ub2e4.", "\uacf5\uaca9 \ubc94\uc704\ub294 30\uce78\uc815\ub3c4\uc774\uba70 \uc790\uc2e0\uc758 \uc8fc\ubcc0 4\uce78\uc740 \ubcf4\ud638\ub429\ub2c8\ub2e4.", "\uae08\uad34 \uc67c\ud074\ub9ad\uc73c\ub85c \ub2a5\ub825\uc744 \ubc1c\ub3d9\uc2dc\ud0b5\ub2c8\ub2e4. \ucca0\uad34 1\uac1c \uc18c\ubaa8.");
        this.InitAbility(60, 0, true);
        this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (!this.PlayerCheck(Event.getPlayer()) || !this.ItemCheck(Material.GOLD_INGOT.getId())) {
            return -1;
        }
        final PlayerInventory inv = Event.getPlayer().getInventory();
        if (inv.contains(Material.IRON_INGOT, 1)) {
            return 0;
        }
        Event.getPlayer().sendMessage(PhysicalFighters.w + "Ã¶±«°¡ ºÎÁ·ÇÕ´Ï´Ù.");
        return -2;
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (!Event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            final PlayerInventory inv = Event.getPlayer().getInventory();
            final Location l = Event.getPlayer().getLocation();
            final Location l2 = Event.getPlayer().getLocation();
            Event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 0), true);
            Event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 0), true);
            Event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 0), true);
            final int sell = inv.first(Material.IRON_INGOT);
            if (inv.getItem(sell).getAmount() == 1) {
                inv.clear(sell);
            } else {
                inv.getItem(sell).setAmount(inv.getItem(sell).getAmount() - 1);
            }
            Event.getPlayer().updateInventory();
            final double degrees = Math.toRadians(-(l.getYaw() % 360.0f));
            final double ydeg = Math.toRadians(-(l.getPitch() % 360.0f));
            for (int i = 1; i < 6; ++i) {
                l2.setX(l.getX() + (3 * i + 3) * (Math.sin(degrees) * Math.cos(ydeg)));
                l2.setY(l.getY() + (3 * i + 3) * Math.sin(ydeg));
                l2.setZ(l.getZ() + (3 * i + 3) * (Math.cos(degrees) * Math.cos(ydeg)));
                l2.getWorld().createExplosion(l2, 3.0f);
            }
        }
    }
}
