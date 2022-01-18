package me.shinkhan.antibug;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;

import org.bukkit.event.inventory.InventoryOpenEvent;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import java.util.HashMap;
import org.bukkit.event.Listener;

public class SkriptGUI implements Listener
{
    static HashMap<String, TempPlayerData> list;
    
    public SkriptGUI() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, new Runnable() {
            @Override
            public void run() {
                final List<String> list2 = new ArrayList<String>();
                for (final String s : SkriptGUI.list.keySet()) {
                    TempPlayerData tempPlayerData;
                    TempPlayerData tpd = tempPlayerData = SkriptGUI.list.get(s);
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
    public void open(InventoryOpenEvent e) {
    	if (SkriptGUI.list.containsKey(e.getPlayer().getName())) {
            e.setCancelled(true);
            Player p = (Player) e.getPlayer();
            p.sendMessage("§e[BlockMCBug] §f한번 인벤토리를 연 후에는 1초후 다시 열 수 있습니다.");
        }
        else {
            SkriptGUI.list.put(e.getPlayer().getName(), new TempPlayerData());
        }
    }
    
    static {
        SkriptGUI.list = new HashMap<String, TempPlayerData>();
    }
}
