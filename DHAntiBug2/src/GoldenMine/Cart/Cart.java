// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.Cart;

import org.bukkit.event.EventHandler;
import GoldenMine.Main;
import GoldenMine.Instance.ConfigSetting;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;

public class Cart implements Listener
{
    @EventHandler
    public void interact(final PlayerInteractEvent e) {
        if (ConfigSetting.CartBug && e.getPlayer().isInsideVehicle()) {
            e.setCancelled(true);
            Main.PrintMessage(e.getPlayer(), "\ud0c8 \uac83\uc5d0 \ud0c0\uace0 \uc788\ub294 \uacbd\uc6b0 \uc0c1\ud638\uc791\uc6a9\uc744 \ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
        }
    }
}
