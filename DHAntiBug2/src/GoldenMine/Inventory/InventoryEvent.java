// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.Inventory;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.event.EventHandler;
import GoldenMine.Main;
import org.bukkit.event.inventory.InventoryAction;
import GoldenMine.Instance.ConfigSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class InventoryEvent implements Listener
{
    @EventHandler
    public void BlockCopyBug(final InventoryClickEvent e) {
        try {
            if (!e.getInventory().getType().equals((Object)InventoryType.PLAYER)) {
                final Player p = (Player)e.getWhoClicked();
                if (e.getInventory().getType().equals((Object)InventoryType.MERCHANT)) {
                    if (ConfigSetting.KEEP_COERCIVE_EXCHANGE && e.getAction().equals((Object)InventoryAction.COLLECT_TO_CURSOR)) {
                        e.setCancelled(true);
                        if (ConfigSetting.LOOK_MESSAGE_SHOPKEEPERS) {
                            Main.PrintMessage(p, "§f\ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0\ub85c \uc778\ud574 \ucc28\ub2e8\ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
                        }
                    }
                    if (ConfigSetting.SHIFT_EXCHANGE && e.isShiftClick()) {
                        e.setCancelled(true);
                        if (ConfigSetting.LOOK_MESSAGE_SHOPKEEPERS) {
                            Main.PrintMessage(p, "§f\ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0\ub85c \uc778\ud574 \ucc28\ub2e8\ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
            Main.PrintBukkit("\uac15\uc81c\uad50\ud658 \ubc84\uadf8\ubc29\uc9c0\uc5d0\uc11c \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.");
            Main.PrintBukkit("1.5.2 spigot \ub610\ub294 craftbukkit R1.1 \uc774\uc0c1\uc744 \uc0ac\uc6a9\ud558\uace0 \uc788\ub294\uc9c0 \uc810\uac80\ud574\uc8fc\uc2dc\uae30 \ubc14\ub78d\ub2c8\ub2e4.");
            Main.PrintBukkit("\ub9cc\uc57d \uadf8 \uacbd\uc6b0\uac00 \uc544\ub2c8\ub77c\uba74 \ud574\ub2f9 \ubc84\ud0b7 \ub85c\uadf8\ub97c \uce74\uce74\uc624\ud1a1 joyfulskys\ub85c \ubcf4\ub0b4\uc8fc\uc2dc\uba74 \uac10\uc0ac\ud558\uaca0\uc2b5\ub2c8\ub2e4.");
        }
    }
    
    public void tepoimp(final int k, final long g) {
        final ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        list.stream().filter(num -> num % 2 == 0);
    }
}
