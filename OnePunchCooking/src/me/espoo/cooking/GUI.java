package me.espoo.cooking;

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
	static PlayerYml P;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "요리 조합대");
		String[] one = "↘,↓,↙".split(",");
		String[] two = "↗,↑,↖".split(",");
		String[] three = "↘,↓,↙,←,↖,↑,↗,→".split(",");
		int num = 0;
		
		for (int i = 0; i < 3; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			
			Stack("§c§l" + one[i], 8, 0, 1, G.Wood, num, GUI);
		}
		
		for (int i = 0; i < 3; i ++) {
			if (i == 0) num = 46;
			else if (i == 1) num = 47;
			else if (i == 2) num = 48;
			
			Stack("§c§l" + two[i], 8, 0, 1, G.Wood, num, GUI);
		}
		
		for (int i = 0; i < 8; i ++) {
			if (i == 0) num = 32;
			else if (i == 1) num = 33;
			else if (i == 2) num = 34;
			else if (i == 3) num = 43;
			else if (i == 4) num = 52;
			else if (i == 5) num = 51;
			else if (i == 6) num = 50;
			else if (i == 7) num = 41;
			
			Stack("§c§l" + three[i], 10, 0, 1, G.Lava, num, GUI);
		}
		
		Stack("§6§l재료 1. §c§l→", 8, 0, 1, G.Wood, 10, GUI);
		Stack("§6§l재료 2. §c§l→", 8, 0, 1, G.Wood, 19, GUI);
		Stack("§6§l재료 3. §c§l→", 8, 0, 1, G.Wood, 28, GUI);
		Stack("§6§l재료 4. §c§l→", 8, 0, 1, G.Wood, 37, GUI);
		Stack("§6§l재료 1. §c§l←", 8, 0, 1, G.Wood, 12, GUI);
		Stack("§6§l재료 2. §c§l←", 8, 0, 1, G.Wood, 21, GUI);
		Stack("§6§l재료 3. §c§l←", 8, 0, 1, G.Wood, 30, GUI);
		Stack("§6§l재료 4. §c§l←", 8, 0, 1, G.Wood, 39, GUI);
		
		if (P.getInfoInt(p, "남은 시간") <= -1) {
			Stack("§f[ §e요리 하기 §f]", 58, 0, 1, G.WeekFir, 15, GUI);
			Stack2("§f만들어진 요리가 없습니다", 102, 0, 1, Arrays.asList(), 42, GUI);
		}
		
		else if (P.getInfoInt(p, "남은 시간") == 0) {
			Stack("§f[ §e요리 완성 §f]", 146, 0, 1, G.getWeekFin(p), 15, GUI);
			Stack2(" ", 102, 0, 1, Arrays.asList(), 11, GUI);
			Stack2(" ", 102, 0, 1, Arrays.asList(), 20, GUI);
			Stack2(" ", 102, 0, 1, Arrays.asList(), 29, GUI);
			Stack2(" ", 102, 0, 1, Arrays.asList(), 38, GUI);
			Stack2(FoodList.NameList.get(PlayerYml.getInfoInt(p, "요리 코드")), 102, 0, 1, Arrays.asList(), 42, GUI);
		}
		
		else {
			Stack("§f[ §e요리 만드는중 §f]", 61, 0, 1, G.getWeekMake(p), 15, GUI);
			Stack2(" ", 102, 0, 1, Arrays.asList(), 11, GUI);
			Stack2(" ", 102, 0, 1, Arrays.asList(), 20, GUI);
			Stack2(" ", 102, 0, 1, Arrays.asList(), 29, GUI);
			Stack2(" ", 102, 0, 1, Arrays.asList(), 38, GUI);
			Stack2("§f요리를 만드는 중입니다", 102, 0, 1, Arrays.asList(), 42, GUI);
		}
		
		for (int i = 0; i < 26; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 9;
			else if (i == 2) num = 18;
			else if (i == 3) num = 27;
			else if (i == 4) num = 36;
			else if (i == 5) num = 45;
			else if (i == 6) num = 4;
			else if (i == 7) num = 5;
			else if (i == 8) num = 6;
			else if (i == 9) num = 7;
			else if (i == 10) num = 8;
			else if (i == 11) num = 13;
			else if (i == 12) num = 14;
			else if (i == 13) num = 16;
			else if (i == 14) num = 17;
			else if (i == 15) num = 22;
			else if (i == 16) num = 23;
			else if (i == 17) num = 24;
			else if (i == 18) num = 25;
			else if (i == 19) num = 26;
			else if (i == 20) num = 31;
			else if (i == 21) num = 40;
			else if (i == 22) num = 49;
			else if (i == 23) num = 35;
			else if (i == 24) num = 44;
			else if (i == 25) num = 53;
			
			Stack2(" ", 102, 0, 1, Arrays.asList(), num, GUI);
		}

		p.openInventory(GUI);
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void updateGUI(Player p, InventoryView v)
	{
		ItemStack item = new MaterialData(58, (byte) 0).toItemStack(1);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("§f[ §e요리 하기 §f]");
		if (G.Week.size() != 0) item_Meta.setLore(G.getWeek(p, v));
		item.setItemMeta(item_Meta);
		v.setItem(15, item);
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
