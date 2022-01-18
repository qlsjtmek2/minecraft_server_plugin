package me.espoo.switched.sheep;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class SheepGUI {
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "¾çÅÐ ±³È¯±â");
		
		int num = 0;
		for (int i = 0; i < 37; i ++) {
			if (i == 0) num = 0;
			else if (i == 13) num = 17;
			else if (i == 15) num = 20;
			else if (i == 17) num = 26;
			else if (i == 19) num = 29;
			else if (i == 21) num = 35;
			else if (i == 26) num = 43;
			else num++;
			
			Stack("", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		SheepAPI.setFirstSheepGUI(GUI);
		p.openInventory(GUI);
	}
	
	public static void Stack(String Display, int ID, int DATA, int STACK, List<String> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}

	public static void Stack2(String Display, int ID, int DATA, int STACK, List<Object> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}

	public static void Stack3(ItemStack item, int loc, Inventory inv) {
		inv.setItem(loc, item);
	}
}
