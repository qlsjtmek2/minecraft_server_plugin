package me.espoo.pvp;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	static GUIMessage G;

	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "대련 하기");
		
		Stack("§c1:1 §6대련 하기", 272, 0, 1, G.one, 11, GUI);
		Stack("§c대난투 §6입장 하기", 280, 0, 1, G.eosksxn, 15, GUI);
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void stopGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "매칭 취소");
		Stack("§c매칭 취소", 372, 0, 1, G.stop, 13, GUI);
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
