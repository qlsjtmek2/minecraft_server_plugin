package me.espoo.sellshop;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class mainGUI {
	static GUIMessage G;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 45, "판매 상점");
		int num = 0;
		
		for (int i = 0; i < 33; i++) {
			if (i == 1) num++;
			else if (i == 2) num++;
			else if (i == 3) num++;
			else if (i == 4) num++;
			else if (i == 5) num++;
			else if (i == 6) num++;
			else if (i == 7) num++;
			else if (i == 8) num++;
			else if (i == 9) num++;
			else if (i == 10) num++;
			else if (i == 11) num++;
			else if (i == 12) num++;
			else if (i == 13) num++;
			else if (i == 14) num = 17;
			else if (i == 15) num++;
			else if (i == 16) num++;
			else if (i == 17) num = 21;
			else if (i == 18) num++;
			else if (i == 19) num = 26;
			else if (i == 20) num++;
			else if (i == 21) num++;
			else if (i == 22) num = 30;
			else if (i == 23) num++;
			else if (i == 24) num = 35;
			else if (i == 25) num = 37;
			else if (i == 26) num++;
			else if (i == 27) num++;
			else if (i == 28) num++;
			else if (i == 29) num++;
			else if (i == 30) num++;
			else if (i == 31) num++;
			else if (i == 32) num++;
				
			Stack2("", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		String[] water = "↘,↓,↙,←,↖,↑,↗,→".split(",");
		
		for (int i = 0; i < 8; i ++) {
			if (i == 0) num = 14;
			else if (i == 1) num = 15;
			else if (i == 2) num = 16;
			else if (i == 3) num = 25;
			else if (i == 4) num = 34;
			else if (i == 5) num = 33;
			else if (i == 6) num = 32;
			else if (i == 7) num = 23;
			
			Stack("§c§l" + water[i], 10, 0, 1, G.Lava, num, GUI);
		}
		
		Stack("§f[ §6판매 하기 §f]", 388, 0, 1, G.ButtonFir, 20, GUI);
		Stack("§6판매 상점 §cTip", 386, 0, 1, G.Tip, 29, GUI);
		Stack("§f[ §7§l뒤로 가기 §f]", 324, 0, 1, G.Back, 36, GUI);
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void updateGUI(Player p, InventoryView v)
	{
		ItemStack item = new MaterialData(388, (byte) 0).toItemStack(1);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("§f[ §6판매 하기 §f]");
		if (G.Button.size() != 0) item_Meta.setLore(G.getButton(p, v));
		item.setItemMeta(item_Meta);
		v.setItem(20, item);
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void deleteItemGUI(Player p, InventoryView v)
	{
		v.setItem(24, null);
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
