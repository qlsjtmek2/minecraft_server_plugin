package me.espoo.switched.sheep;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.switched.main;

public class SheepEvent extends JavaPlugin implements Listener {
	main M;
	
	public SheepEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		InventoryView i = e.getView();
		
		if (i.getTitle().equalsIgnoreCase("양털 교환기")) {
            int t = 0;
            ItemStack[] contents;
            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
                ItemStack it = contents[j];
                if (it == null) {
                    ++t;
                }
            }
            
            if (t <= 0) {
    			if (i.getItem(28) != null && i.getItem(28).getType() != Material.AIR) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(28));
    			}
            } else {
            	if (i.getItem(28) != null && i.getItem(28).getType() != Material.AIR) {
                	p.getInventory().addItem(i.getItem(28));
    			}
            }
		}
	}
	
	@EventHandler
    public void onClickItem(InventoryClickEvent e) {
    	if (e.getInventory().getName().equalsIgnoreCase("양털 교환기")) {
    		if (e.getCurrentItem() == null) return;
    		InventoryAction action = e.getAction();
    		ItemStack cursor = e.getCursor();
    		ItemStack current = e.getCurrentItem();
    		Player p = (Player) e.getWhoClicked();
    		
    		if (current.getType() != Material.WOOL && current.getType() != Material.AIR || current.hasItemMeta() || action != InventoryAction.PICKUP_ALL && action != InventoryAction.MOVE_TO_OTHER_INVENTORY && 
    			action != InventoryAction.PICKUP_HALF && action != InventoryAction.PICKUP_ONE && action != InventoryAction.PICKUP_SOME && current.getType() != Material.AIR) {
    			e.setCancelled(true);
    			
    			if (cursor != null && cursor.getType() == current.getType()) {
    				e.setCancelled(false);
    			} else return;
    		}
    		
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 25);
			timer.schedule(new TimerTask() {
				public void run() {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
					SheepAPI.setSheepGUI(e.getInventory());
					return;
				}
			}, timeToRun);
    	}
	}
}
