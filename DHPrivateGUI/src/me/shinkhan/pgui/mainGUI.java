package me.shinkhan.pgui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class mainGUI {
	public static void ManagerGUI(Player p, Sign s)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "잠금 관리자");
		
		Stack("§b플레이어 추가", 399, 0, 1, GUIMessage.Leave, 15, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 16, GUI);
		
		int num = 0;
		for (int i = 0; i < 12; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 1;
			else if (i == 2) num = 2;
			else if (i == 3) num = 3;
			else if (i == 4) num = 4;
			else if (i == 5) num = 22;
			else if (i == 6) num = 9;
			else if (i == 7) num = 13;
			else if (i == 8) num = 18;
			else if (i == 9) num = 19;
			else if (i == 10) num = 20;
			else if (i == 11) num = 21;
			
			Stack2("", 10, 0, 1, Arrays.asList(), num, GUI);
		}
		
		String one = s.getLine(1);
		String two = s.getLine(2);
		String three = s.getLine(3);
		
		if (!one.equalsIgnoreCase("")) {
			if (Method.getOnorOffLine(one) != null) Stack("§f" + one, 397, 3, 1, GUIMessage.getUserMan(one), 10, GUI);
			else Stack("§f" + one, 397, 0, 1, GUIMessage.getUserMan(one), 10, GUI);
		}
		
		if (!two.equalsIgnoreCase("")) {
			if (Method.getOnorOffLine(two) != null) Stack("§f" + two, 397, 3, 1, GUIMessage.getManagerMan(two), 11, GUI);
			else Stack("§f" + two, 397, 0, 1, GUIMessage.getManagerMan(two), 11, GUI);
		}
		
		if (!three.equalsIgnoreCase("")) {
			if (Method.getOnorOffLine(three) != null) Stack("§f" + three, 397, 3, 1, GUIMessage.getManagerMan(three), 12, GUI);
			else Stack("§f" + three, 397, 0, 1, GUIMessage.getManagerMan(three), 12, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void UserGUI(Player p, Sign s)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "잠금 유저");

		Stack("§c표지판 탈퇴", 331, 0, 1, GUIMessage.Leave, 15, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 16, GUI);
		
		int num = 0;
		for (int i = 0; i < 12; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 1;
			else if (i == 2) num = 2;
			else if (i == 3) num = 3;
			else if (i == 4) num = 4;
			else if (i == 5) num = 22;
			else if (i == 6) num = 9;
			else if (i == 7) num = 13;
			else if (i == 8) num = 18;
			else if (i == 9) num = 19;
			else if (i == 10) num = 20;
			else if (i == 11) num = 21;
			
			Stack2("", 8, 0, 1, Arrays.asList(), num, GUI);
		}
		
		String one = s.getLine(1);
		String two = s.getLine(2);
		String three = s.getLine(3);
		
		if (!one.equalsIgnoreCase("")) {
			if (Method.getOnorOffLine(one) != null) Stack("§f" + one, 397, 3, 1, GUIMessage.getUserMan(one), 10, GUI);
			else Stack("§f" + one, 397, 0, 1, GUIMessage.getUserMan(one), 10, GUI);
		}
		
		if (!two.equalsIgnoreCase("")) {
			if (Method.getOnorOffLine(two) != null) Stack("§f" + two, 397, 3, 1, GUIMessage.getUserMan(two), 11, GUI);
			else Stack("§f" + two, 397, 0, 1, GUIMessage.getUserMan(two), 11, GUI);
		}
		
		if (!three.equalsIgnoreCase("")) {
			if (Method.getOnorOffLine(three) != null) Stack("§f" + three, 397, 3, 1, GUIMessage.getUserMan(three), 12, GUI);
			else Stack("§f" + three, 397, 0, 1, GUIMessage.getUserMan(three), 12, GUI);
		}
		
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
