// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.Player;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import GoldenMine.Main;
import GoldenMine.Instance.ConfigSetting;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.Listener;

public class PlayerEvent implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void BlockAuthmeBug(final PlayerCommandPreprocessEvent e) {
        try {
            final String message = e.getMessage().toLowerCase();
            final String[] msg = message.replace("/", "").split(" ");
            if ((msg[0].contains("unregister") || msg[0].contains("\ud68c\uc6d0\ud0c8\ud1f4")) && msg.length >= 2 && ConfigSetting.ONE_COPY_BUG) {
                e.getPlayer().performCommand("unregister " + msg[1]);
                e.getPlayer().performCommand("\ud68c\uc6d0\ud0c8\ud1f4 " + msg[1]);
                e.getPlayer().kickPlayer("authme+lwc \ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0\ub97c \uc704\ud574 \ud68c\uc6d0\ud0c8\ud1f4\uc2dc \uc790\ub3d9\uc73c\ub85c \ud0a5\ub429\ub2c8\ub2e4.");
                Main.PrintBukkit(e.getPlayer().getName() + "\ub2d8\uc774 \ud68c\uc6d0\ud0c8\ud1f4\ub97c \uc785\ub825\ud558\uc5ec \uc790\ub3d9\ud0a5 \ucc98\ub9ac \ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
            }
            if (ConfigSetting.TRIGGER_BUG && !e.getPlayer().isOp() && message.contains("<") && message.contains(">")) {
                final char[] list = message.toCharArray();
                for (int i = 0; i < list.length; ++i) {
                    if (list[i] == '>') {
                        int count = 0;
                        for (int j = i; j >= 0; --j) {
                            if (list[j] == ':') {
                                ++count;
                            }
                            if (list[j] == '<' && count >= 2) {
                                final Pattern p = Pattern.compile("<give.*>");
                                final Matcher m = p.matcher(message.substring(j, i + 1));
                                if (m.matches()) {
                                    e.setCancelled(true);
                                    e.setMessage("/");
                                    e.getPlayer().kickPlayer("\uba85\ub839\uc5b4\uc5d0 <,>\ub97c \uc4f8\uc218 \uc5c6\uc2b5\ub2c8\ub2e4!");
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public void tepoimp(final int k, final long g) {
        final ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        list.stream().filter(num -> num % 2 == 0);
    }
}
