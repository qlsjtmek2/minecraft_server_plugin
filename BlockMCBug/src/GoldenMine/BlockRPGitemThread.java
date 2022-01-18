// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine;

import think.rpgitems.Plugin;
import org.bukkit.event.player.PlayerPickupItemEvent;

class BlockRPGitemThread implements Runnable
{
    @Override
    public void run() {
        try {
            try {
                Thread.sleep(18000L);
            }
            catch (InterruptedException ex) {}
            PlayerPickupItemEvent.getHandlerList().unregister((org.bukkit.plugin.Plugin)Plugin.plugin);
            Main.PrintBukkit("RPGitem \ud50c\ub7ec\uadf8\uc778\uc744 \ucc3e\uc558\uc73c\ubbc0\ub85c RPGitem \ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0 \uae30\ub2a5\uc744 \ud65c\uc131\ud654\uc2dc\ud0b5\ub2c8\ub2e4.");
        }
        catch (Exception e) {
            Main.PrintBukkit("RPGitem \ud50c\ub7ec\uadf8\uc778\uc744 \ucc3e\uc9c0 \ubabb\ud574 RPGitem \ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0 \uae30\ub2a5\uc774 \ud65c\uc131\ud654 \ub418\uc9c0 \uc54a\uc558\uc2b5\ub2c8\ub2e4.");
        }
    }
}
