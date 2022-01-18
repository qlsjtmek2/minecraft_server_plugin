package me.shinkhan.shop;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Method {
	public static String replaceAllColors(String message) {
		message = message.replaceAll("&1", "」1");
		message = message.replaceAll("&2", "」2");
		message = message.replaceAll("&3", "」3");
		message = message.replaceAll("&4", "」4");
		message = message.replaceAll("&5", "」5");
		message = message.replaceAll("&6", "」6");
		message = message.replaceAll("&7", "」7");
		message = message.replaceAll("&8", "」8");
		message = message.replaceAll("&9", "」9");
		message = message.replaceAll("&0", "」0");
		message = message.replaceAll("&a", "」a");
		message = message.replaceAll("&b", "」b");
		message = message.replaceAll("&c", "」c");
		message = message.replaceAll("&d", "」d");
		message = message.replaceAll("&e", "」e");
		message = message.replaceAll("&f", "」f");
		message = message.replaceAll("&k", "」k");
		message = message.replaceAll("&l", "」l");
		message = message.replaceAll("&m", "」m");
		message = message.replaceAll("&n", "」n");
		message = message.replaceAll("&o", "」o");
		message = message.replaceAll("&r", "」r");
		return message;
	}

	public static boolean equals(ItemStack one, ItemStack two) {
		return one.isSimilar(two);
	}

	public static int getAmount(ItemStack item, Inventory inventory) {
		if (!inventory.contains(item.getType())) {
			return 0;
		}

		if (inventory.getType() == null) {
			return 2147483647;
		}

		HashMap<Integer, ? extends ItemStack> items = inventory.all(item.getType());
		int itemAmount = 0;

		for (ItemStack iStack : items.values()) {
			if (!equals(iStack, item)) {
				continue;
			}

			itemAmount += iStack.getAmount();
		}

		return itemAmount;
	}
	
	public static int getInventoryAir(Player p) {
        int t = 0; ItemStack[] contents;
        for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
            ItemStack it = contents[j];
            if (it == null) {
                ++t;
            }
        }
        
        return t;
	}
}
