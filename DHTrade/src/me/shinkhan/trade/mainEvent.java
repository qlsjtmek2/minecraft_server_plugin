package me.shinkhan.trade;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.shinkhan.trade.data.InventoryData;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		HumanEntity entity = event.getWhoClicked();
		Player p = Bukkit.getPlayerExact(entity.getName());
		if ((entity.getType() == EntityType.PLAYER) && (checkInventory(event.getInventory()))) {
			InventoryData id = M.getGameData().getInventoryData(entity);
			if (id != null) {
				int slot = event.getRawSlot();
				if (((slot >= 0) && (slot < 9)) || (event.getClick() == ClickType.SHIFT_LEFT) || (event.getClick() == ClickType.DOUBLE_CLICK)) {
					id.changeItem();
				}
				if ((9 <= slot) && (slot < 45)) {
					event.setCancelled(true);
				} else if ((45 <= slot) && (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
					ItemStack[] items = event.getInventory().getContents();
					boolean item = false;
					for (int i = 0; i < 9; i++) {
						if (items[i] != null)
							continue;
						item = true;
					}

					if (!item) {
						event.setCancelled(true);
					}
				}
				if ((37 == slot)) {
					id.changeReady(entity);
    				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		HumanEntity entity = event.getWhoClicked();
		if ((entity.getType() == EntityType.PLAYER) && (checkInventory(event.getInventory()))) {
			InventoryData id = M.getGameData().getInventoryData(entity);
			if (id != null) {
				for (Iterator<Integer> localIterator = event.getRawSlots().iterator(); localIterator.hasNext();) {
					int slot = ((Integer) localIterator.next()).intValue();

					if (slot < 9) {
						id.changeItem();
					} else {
						if ((9 > slot) || (slot >= 45))
							continue;
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		HumanEntity entity = event.getPlayer();
		if ((entity.getType() == EntityType.PLAYER) && (checkInventory(event.getInventory()))) {
			InventoryData id = M.getGameData().getInventoryData(entity);
			if (id != null) {
				id.cancel();
			}
		}
	}

	private boolean checkInventory(Inventory inventory) {
		return inventory.getName().equals(main.INVENTORY_NAME);
	}
}
