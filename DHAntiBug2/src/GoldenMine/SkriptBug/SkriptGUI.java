// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.SkriptBug;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import GoldenMine.Instance.ConfigSetting;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import GoldenMine.Main;
import org.bukkit.Bukkit;
import java.util.HashMap;
import org.bukkit.event.Listener;

public class SkriptGUI implements Listener
{
    static HashMap<String, TempPlayerData> list;
    
    public SkriptGUI() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Main.instance, (Runnable)new Runnable() {
            @Override
            public void run() {
                final List<String> list2 = new ArrayList<String>();
                for (final String s : SkriptGUI.list.keySet()) {
                    final TempPlayerData tempPlayerData;
                    final TempPlayerData tpd = tempPlayerData = SkriptGUI.list.get(s);
                    --tempPlayerData.time;
                    if (tpd.time <= 0) {
                        list2.add(s);
                    }
                }
                for (final String s : list2) {
                    SkriptGUI.list.remove(s);
                }
            }
        }, 2L, 2L);
    }
    
    @EventHandler
    public void open(final InventoryOpenEvent e) {
        if (ConfigSetting.SkriptGUI) {
            if (SkriptGUI.list.containsKey(e.getPlayer().getName())) {
                e.setCancelled(true);
                Main.PrintMessage((Entity)e.getPlayer(), "\ud55c\ubc88 \uc778\ubca4\ud1a0\ub9ac\ub97c \uc5f0 \ud6c4\uc5d0\ub294 1\ucd08\ud6c4 \ub2e4\uc2dc \uc5f4 \uc218 \uc788\uc2b5\ub2c8\ub2e4.");
            }
            else {
                SkriptGUI.list.put(e.getPlayer().getName(), new TempPlayerData());
            }
        }
    }
    
    static {
        SkriptGUI.list = new HashMap<String, TempPlayerData>();
    }
}
