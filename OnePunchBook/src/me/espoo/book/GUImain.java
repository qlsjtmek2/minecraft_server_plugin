package me.espoo.book;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUImain {
	static GUIMessage G;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "도감");

		Stack("§f[ §c무기 §6도감 §f]", 276, 0, 1, G.ML, 11, GUI);
		Stack("§f[ §f일반 §b장비 §6도감 §f]", 307, 0, 1, G.ML, 12, GUI);
		Stack("§f[ §6유니크 §b장비 §6도감 §f]", 311, 0, 1, G.ML, 13, GUI);
		Stack("§f[ §a도구 §6도감 §f]", 278, 0, 1, G.ML, 14, GUI);
		Stack("§f[ §d룬 §6도감 §f]", 2257, 0, 1, G.ML, 15, GUI);
		
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
}
