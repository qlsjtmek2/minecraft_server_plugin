// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.ace;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import GoldenMine.Main;
import GoldenMine.Instance.ConfigSetting;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.Listener;

public class ace implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void preprocess(final PlayerCommandPreprocessEvent e) {
        if (ConfigSetting.ace) {
            final String s = e.getMessage().replace("/", "");
            if (s.equalsIgnoreCase("ace") && !e.getPlayer().isOp()) {
                e.setCancelled(true);
                Main.PrintMessage(e.getPlayer(), "\uc624\ud53c\uac00 \uc544\ub2cc \uc720\uc800\ub294 ace \uba85\ub839\uc5b4\ub97c \uce60 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
            }
        }
    }
}
