package me.espoo.quest;

import java.util.ArrayList;
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
	public static void NPCQuestListGUI(Player p, String npc)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, npc);
		int is1 = 0, is2 = 0;
		if (!API.isMainNextFinish(p)) {
			if (!API.isMainQuestIng(p)) {
				is1 = 1;
				p.closeInventory();
				p.sendMessage("§6퀘스트 목록을 불러오는 중입니다..");
			} else {
				if (API.isMainQuestComplete(p)) {
					is1 = 3;
					p.closeInventory();
					p.sendMessage("§6퀘스트 목록을 불러오는 중입니다..");
				} else {
					is1 = 2;
					p.closeInventory();
					p.sendMessage("§6퀘스트 목록을 불러오는 중입니다..");
				}
			}
		}

		if (is1 != 0 && is2 != 0) {
			if (is1 == 1) {
				List<String> lore = new ArrayList<String>();
				lore.add("§7퀘스트 등급: §6메인");
				lore.add("§7진행도: §f수락 가능");

				List<String> list1 = API.getMainTaskColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("§7과제: §f없음");
				} else {
					lore.add("§7 과제:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("§7보상: §f없음");
				} else {
					lore.add("§7 보상:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("§8§lㅡ> §7클릭시 퀘스트를 진행합니다.");
				
				Stack("§6§l◇ " + API.getMainQuestName(p), 339, 0, 1, lore, 11, GUI);
			}
			
			else if (is1 == 2) {
				List<String> lore = new ArrayList<String>();
				lore.add("§7퀘스트 등급: §6메인");
				lore.add("§7진행도: §f퀘스트 진행중");

				List<String> list1 = API.getMainPlayerTaskingColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("§7과제: §f없음");
				} else {
					lore.add("§7 과제:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("§7보상: §f없음");
				} else {
					lore.add("§7 보상:");
					for (String str : list1) lore.add(str);
				}
				
				Stack("§6§l▷ " + API.getMainQuestName(p), 340, 0, 1, lore, 11, GUI);
			}
			
			else if (is1 == 3) {
				List<String> lore = new ArrayList<String>();
				lore.add("§7퀘스트 등급: §6메인");
				lore.add("§7진행도: §f보상 획득 가능");

				List<String> list1 = API.getMainPlayerTaskingColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("§7과제: §f없음");
				} else {
					lore.add("§7 과제:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("§7보상: §f없음");
				} else {
					lore.add("§7 보상:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("§8§lㅡ> §7클릭시 보상을 획득합니다.");
				
				Stack("§6§l▶ " + API.getMainQuestName(p), 403, 0, 1, lore, 11, GUI);
			} p.openInventory(GUI);
		} else {
			if (is1 != 0) {
				if (is1 == 1) {
					List<String> lore = new ArrayList<String>();
					lore.add("§7퀘스트 등급: §6메인");
					lore.add("§7진행도: §f수락 가능");

					List<String> list1 = API.getMainTaskColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("§7과제: §f없음");
					} else {
						lore.add("§7 과제:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("§7보상: §f없음");
					} else {
						lore.add("§7 보상:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("§8§lㅡ> §7클릭시 퀘스트를 진행합니다.");
					
					Stack("§6§l◇ " + API.getMainQuestName(p), 339, 0, 1, lore, 13, GUI);
				}
				
				else if (is1 == 2) {
					List<String> lore = new ArrayList<String>();
					lore.add("§7퀘스트 등급: §6메인");
					lore.add("§7진행도: §f퀘스트 진행중");

					List<String> list1 = API.getMainPlayerTaskingColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("§7과제: §f없음");
					} else {
						lore.add("§7 과제:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("§7보상: §f없음");
					} else {
						lore.add("§7 보상:");
						for (String str : list1) lore.add(str);
					}
					
					Stack("§6§l▷ " + API.getMainQuestName(p), 340, 0, 1, lore, 13, GUI);
				}
				
				else if (is1 == 3) {
					List<String> lore = new ArrayList<String>();
					lore.add("§7퀘스트 등급: §6메인");
					lore.add("§7진행도: §f보상 획득 가능");

					List<String> list1 = API.getMainPlayerTaskingColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("§7과제: §f없음");
					} else {
						lore.add("§7 과제:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("§7보상: §f없음");
					} else {
						lore.add("§7 보상:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("§8§lㅡ> §7클릭시 보상을 획득합니다.");
					
					Stack("§6§l▶ " + API.getMainQuestName(p), 403, 0, 1, lore, 13, GUI);
				}
				
				p.openInventory(GUI);
			}
			
			else return;
		}
	}
	
	public static void QuestMainADGUI(Player p, String questname)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "§6§l퀘스트: §0" + questname);
		List<String> lore1 = new ArrayList<String>();
		lore1.add("§7퀘스트 요청: §f" + API.getMainNPCName());
		List<String> list = API.getMainTaskColor(p);
		if (list != null && !list.isEmpty())
			for (String str : list) lore1.add(str);
		Stack("§a§o" + questname, 339, 0, 1, lore1, 13, GUI);
		
		Stack2("§a클릭시 퀘스트를 수락합니다.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("§c클릭시 퀘스트를 거절합니다.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void QuestIngGUI(Player p, String questname)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "§6§l퀘스트: §0" + questname);
		Stack2("§f§l§n클릭시 퀘스트를 진행합니다.", 143, 0, 1, Arrays.asList(), 13, GUI);
		
		p.openInventory(GUI);
	}

	@SuppressWarnings("deprecation")
	public static void updateQuestIngGUI(Player p, InventoryView v, String questname, String itemname, String lore, int code, int date)
	{
		List<String> listlore = new ArrayList<String>();
		
		if (lore.contains(";")) {
			String[] str = lore.split(";");
			for (String strr : str) {
				listlore.add("§f" + API.replaceAllColors(strr));
			}
		} else {
			listlore.add("§f" + API.replaceAllColors(lore));
		}
		
		ItemStack item = new MaterialData(code, (byte) date).toItemStack(1);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("§6§l▶ §f§l" + itemname);
		item_Meta.setLore(listlore);
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
	
	public static void Stack3(ItemStack item, int loc, Inventory inv) {
		inv.setItem(loc, item);
	}
}
