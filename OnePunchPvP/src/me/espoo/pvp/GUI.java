package me.espoo.pvp;

import java.util.ArrayList;
import java.util.Arrays;
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
		
		Stack("§c1:1 §6대련 하기", 272, 0, 1, G.one, 10, GUI);
		Stack("§c2:2 §6대련 하기", 283, 0, 1, G.two, 11, GUI);
		Stack("§c3:3 §6대련 하기", 267, 0, 1, G.three, 12, GUI);
		Stack("§c4:4 §6대련 하기", 276, 0, 1, G.four, 13, GUI);
		Stack("§b콜로세움 §6대련 하기", 268, 0, 1, G.colosseum, 16, GUI);
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void stopGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "매칭 취소");
		Stack("§c매칭 취소", 372, 0, 1, G.stop, 13, GUI);
		p.openInventory(GUI);
	}
	
	public static void CheckPvPGUI(Player p, int num)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "파티 장이 " + num + ":" + num + " 대련을 진행하자고 합니다.");
		Stack2("§f예, 대련에 참가하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f아니요, 대련에 참가하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckContinueGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "대련이 종료되었습니다. 이어서 참가하시겠습니까?");
		List<String> lore = new ArrayList<String>();
		lore.add("§7이어서 참가할 시 배팅 금액이 500원 더 늘어납니다.");
		lore.add("§7현재 배팅 금액: §f" + main.coloBetting + "원");
		
		Stack("§f예, 콜로세움을 이어서 하겠습니다.", 35, 5, 1, lore, 11, GUI);
		Stack2("§f아니요, 콜로세움을 이어서 하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
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
