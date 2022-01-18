package me.espoo.switched.trade;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.switched.GUIMessage;

public class TradeGUI {
	static GUIMessage G;
	
	public static void openGUI(Player p, String name, int amount)
	{
		int max = TradeAPI.getRecipeAmount(name);
		Inventory GUI = Bukkit.createInventory(null, 45, TradeAPI.replaceAllColors(name) + "§2§0 교환기 §3§0" + amount + "/" + max);
		
		int num = 0;
		for (int i = 0; i < 29; i ++) {
			if (i == 0) num = 1;
			else if (i == 7) num = 10;
			else if (i == 8) num = 13;
			else if (i == 10) num = 16;
			else if (i == 11) num = 19;
			else if (i == 18) num = 28;
			else if (i == 19) num = 31;
			else if (i == 21) num = 34;
			else if (i == 22) num = 37;
			else num++;
			
			Stack("", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		Stack2("", 101, 0, 1, Arrays.asList(), 0, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 9, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 18, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 27, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 8, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 17, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 26, GUI);
		Stack2("", 101, 0, 1, Arrays.asList(), 35, GUI);
		Stack2("§f이곳에서 조합 결과가 나옵니다.", 102, 0, 1, Arrays.asList(), 33, GUI);
		
		if (max > amount) {
			Stack("§6다음 목록 확인", 10, 0, 1, GUIMessage.NextRanking, 44, GUI);
		} else {
			Stack2("", 101, 0, 1, Arrays.asList(), 44, GUI);
		}
		
		if (amount == 1) {
			Stack("§f뒤로 가기", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
		} else {
			Stack("§6이전 목록 확인", 8, 0, 1, GUIMessage.BackRanking, 36, GUI);
		}
		
		ItemStack item1 = TradeAPI.getOne(name, amount);
		ItemStack item2 = TradeAPI.getTwo(name, amount);
		if (item1 != null) {
			Stack3(item1, 11, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 11, GUI);
			Stack2("", 119, 0, 1, Arrays.asList(), 29, GUI);
		}
		
		if (item2 != null) {
			Stack3(item2, 12, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 12, GUI);
			Stack2("", 119, 0, 1, Arrays.asList(), 30, GUI);
		}
		
		Stack3(TradeAPI.getResult(name, amount), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void OPopenGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, name + "§2§0 조합 추가");
		
		int num = 0;
		for (int i = 0; i < 23; i ++) {
			if (i == 0) num = 0;
			else if (i == 10) num = 12;
			else if (i == 11) num = 14;
			else if (i == 13) num = 17;
			else num++;
			
			Stack("", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		Stack("§f[ §6조합 추가 하기 §f]", 58, 0, 1, GUIMessage.OPTrade, 13, GUI);
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
