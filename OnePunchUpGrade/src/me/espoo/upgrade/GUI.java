package me.espoo.upgrade;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	static GUIMessage G;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, G.getMessage(p, "도구 강화 GUI 이름"));
		
		String[] water = "↘,↓,↙,←,↖,↑,↗,→".split(",");
		int num = 0;
		
		for (int i = 0; i < 8; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 1;
			else if (i == 2) num = 2;
			else if (i == 3) num = 11;
			else if (i == 4) num = 20;
			else if (i == 5) num = 19;
			else if (i == 6) num = 18;
			else if (i == 7) num = 9;
			
			Stack("§c§l" + water[i], 8, 0, 1, G.Water, num, GUI);
		}

		for (int i = 0; i < 8; i ++) {
			if (i == 0) num = 6;
			else if (i == 1) num = 7;
			else if (i == 2) num = 8;
			else if (i == 3) num = 17;
			else if (i == 4) num = 26;
			else if (i == 5) num = 25;
			else if (i == 6) num = 24;
			else if (i == 7) num = 15;
			
			Stack("§c§l" + water[i], 10, 0, 1, G.Lava, num, GUI);
		}
		
		for (int i = 0; i < 4; i ++) {
			if (i == 0) num = 4;
			else if (i == 1) num = 14;
			else if (i == 2) num = 22;
			else if (i == 3) num = 12;
				
			Stack2("§f§l+", 101, 0, 1, Arrays.asList(), num, GUI);
		}
		
		Stack("§f[ §6강화 하기 §f]", 145, 0, 1, G.AnvilX, 13, GUI);
		Stack("§e도구 §6강화 §cTip 1.", 386, 0, 1, G.BookOne, 3, GUI);
		Stack("§e도구 §6강화 §cTip 2.", 386, 0, 1, G.BookTwo, 5, GUI);
		Stack("§e도구 §6강화 §cTip 3.", 386, 0, 1, G.BookThree, 21, GUI);
		Stack("§e도구 §6강화 §cTip 4.", 386, 0, 1, G.BookFour, 23, GUI);
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void updateGUI(Player p, InventoryView v)
	{
		ItemStack item = new MaterialData(145, (byte) 0).toItemStack(1);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("§f[ §6강화 하기 §f]");
		if (G.getLoreList(p, "모루.아이템 설명").size() != 0) item_Meta.setLore(G.getLoreList(p, "모루.아이템 설명"));
		item.setItemMeta(item_Meta);
		v.setItem(13, item);
		p.updateInventory();
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
