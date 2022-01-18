// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.ChestShop;

import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import GoldenMine.Main;
import org.bukkit.block.Sign;
import org.bukkit.Location;
import org.bukkit.Material;
import GoldenMine.Instance.ConfigSetting;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;

public class ChestShop implements Listener
{
    @EventHandler
    public void interact(final PlayerInteractEvent e) {
        if (ConfigSetting.chestshop) {
            final Block b = e.getClickedBlock();
            if (b != null && b.getType() == Material.CHEST) {
                final Location loc = b.getLocation();
                for (int x = -2; x <= 2; ++x) {
                    for (int y = -1; y <= 1; ++y) {
                        for (int z = -2; z <= 2; ++z) {
                            final Block b2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z).getBlock();
                            if ((b2.getType() == Material.SIGN_POST || b2.getType() == Material.WALL_SIGN) && ChestShopUtil.isValid((Sign)b2.getState())) {
                                e.setCancelled(true);
                                Main.PrintMessage(e.getPlayer(), "\uc0c1\uc790 \uc8fc\uc704(\uc88c\uc6b0 2\uce78, \uc0c1\ud558 1\uce78)\uc5d0 \uccb4\uc2a4\ud2b8\uc0f5 \uc0c1\uc810\uc774 \uc788\ub294 \uacbd\uc6b0 \uc5f4 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
                            }
                        }
                    }
                }
            }
        }
    }
}
