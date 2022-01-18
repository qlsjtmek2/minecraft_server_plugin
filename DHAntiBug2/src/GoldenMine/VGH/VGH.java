// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.VGH;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import GoldenMine.Main;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.Listener;

public class VGH implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void preprocess(final PlayerCommandPreprocessEvent e) {
        final String s = e.getMessage().replace("/", "");
        final String[] split = s.split(" ");
        if (split[0].equalsIgnoreCase("vgh")) {
            e.setCancelled(true);
            Main.PrintMessage(e.getPlayer(), "vgh \uba85\ub839\uc5b4\ub294 \uc0ac\uc6a9 \ubd88\uac00\ud569\ub2c8\ub2e4.");
        }
    }
}
