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
				p.sendMessage("��6����Ʈ ����� �ҷ����� ���Դϴ�..");
			} else {
				if (API.isMainQuestComplete(p)) {
					is1 = 3;
					p.closeInventory();
					p.sendMessage("��6����Ʈ ����� �ҷ����� ���Դϴ�..");
				} else {
					is1 = 2;
					p.closeInventory();
					p.sendMessage("��6����Ʈ ����� �ҷ����� ���Դϴ�..");
				}
			}
		}

		if (is1 != 0 && is2 != 0) {
			if (is1 == 1) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: ��6����");
				lore.add("��7���൵: ��f���� ����");

				List<String> list1 = API.getMainTaskColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("��8��l��> ��7Ŭ���� ����Ʈ�� �����մϴ�.");
				
				Stack("��6��l�� " + API.getMainQuestName(p), 339, 0, 1, lore, 11, GUI);
			}
			
			else if (is1 == 2) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: ��6����");
				lore.add("��7���൵: ��f����Ʈ ������");

				List<String> list1 = API.getMainPlayerTaskingColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				Stack("��6��l�� " + API.getMainQuestName(p), 340, 0, 1, lore, 11, GUI);
			}
			
			else if (is1 == 3) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: ��6����");
				lore.add("��7���൵: ��f���� ȹ�� ����");

				List<String> list1 = API.getMainPlayerTaskingColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("��8��l��> ��7Ŭ���� ������ ȹ���մϴ�.");
				
				Stack("��6��l�� " + API.getMainQuestName(p), 403, 0, 1, lore, 11, GUI);
			} p.openInventory(GUI);
		} else {
			if (is1 != 0) {
				if (is1 == 1) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: ��6����");
					lore.add("��7���൵: ��f���� ����");

					List<String> list1 = API.getMainTaskColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("��8��l��> ��7Ŭ���� ����Ʈ�� �����մϴ�.");
					
					Stack("��6��l�� " + API.getMainQuestName(p), 339, 0, 1, lore, 13, GUI);
				}
				
				else if (is1 == 2) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: ��6����");
					lore.add("��7���൵: ��f����Ʈ ������");

					List<String> list1 = API.getMainPlayerTaskingColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					Stack("��6��l�� " + API.getMainQuestName(p), 340, 0, 1, lore, 13, GUI);
				}
				
				else if (is1 == 3) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: ��6����");
					lore.add("��7���൵: ��f���� ȹ�� ����");

					List<String> list1 = API.getMainPlayerTaskingColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("��8��l��> ��7Ŭ���� ������ ȹ���մϴ�.");
					
					Stack("��6��l�� " + API.getMainQuestName(p), 403, 0, 1, lore, 13, GUI);
				}
				
				p.openInventory(GUI);
			}
			
			else return;
		}
	}
	
	public static void QuestMainADGUI(Player p, String questname)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "��6��l����Ʈ: ��0" + questname);
		List<String> lore1 = new ArrayList<String>();
		lore1.add("��7����Ʈ ��û: ��f" + API.getMainNPCName());
		List<String> list = API.getMainTaskColor(p);
		if (list != null && !list.isEmpty())
			for (String str : list) lore1.add(str);
		Stack("��a��o" + questname, 339, 0, 1, lore1, 13, GUI);
		
		Stack2("��aŬ���� ����Ʈ�� �����մϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��cŬ���� ����Ʈ�� �����մϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void QuestIngGUI(Player p, String questname)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��6��l����Ʈ: ��0" + questname);
		Stack2("��f��l��nŬ���� ����Ʈ�� �����մϴ�.", 143, 0, 1, Arrays.asList(), 13, GUI);
		
		p.openInventory(GUI);
	}

	@SuppressWarnings("deprecation")
	public static void updateQuestIngGUI(Player p, InventoryView v, String questname, String itemname, String lore, int code, int date)
	{
		List<String> listlore = new ArrayList<String>();
		
		if (lore.contains(";")) {
			String[] str = lore.split(";");
			for (String strr : str) {
				listlore.add("��f" + API.replaceAllColors(strr));
			}
		} else {
			listlore.add("��f" + API.replaceAllColors(lore));
		}
		
		ItemStack item = new MaterialData(code, (byte) date).toItemStack(1);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("��6��l�� ��f��l" + itemname);
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
