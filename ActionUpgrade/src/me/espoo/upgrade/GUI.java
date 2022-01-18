package me.espoo.upgrade;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	public static void openGUI(Player p, String display, String name, int num, int slot)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "강화 하기");
		
		Stack3(API.getPremiumItemStack(p), 11, GUI);
		Stack3(API.getUpgradeItemStack(p, display, name, num), 13, GUI);
		Stack3(API.getCloseItemStack(p), 26, GUI);
		Stack3(API.getLoreItemStack(p, display, name, num, slot), 8, GUI);
		
		Stack2("", 101, 0, 1, Arrays.asList(), 0, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 9, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 18, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 17, GUI);
		
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
