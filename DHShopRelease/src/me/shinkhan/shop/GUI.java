package me.shinkhan.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	public static void openGUI(Player p, int num, String name)
	{
		Inventory G = Bukkit.createInventory(null, 9, "���� ��� �ҷ�������..");
		p.openInventory(G);
		
		List<String> list = Config.getList(name);
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// � ������ŭ ������ ������
		int y = 1;	// for�� ����
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "��l�� ��0" + name + " " + num + "/" + num2);
		
		for (String st : list)
		{
			if (y < i && num != 1) {
				y++;
				continue;
			}
			
			if (i >= ((num - 1) * 45) + 46) break;
			String[] s = st.split(",");
			List<String> lore = new ArrayList<String>();
			
			lore.add("��f- ��7������ ����: ��f" + s[2]);
			if (s.length == 5) {
				if (s[3].contains("S") || s[3].contains("s")) {
					lore.add("��f- ��7��Ŭ���� ��f" + s[3].replace("S", "").replace("s", "") + " ��7���� ��f�Ǹš�7�մϴ�.");
				}
				
				else if (s[3].contains("B") || s[3].contains("b")) {
					lore.add("��f- ��7��Ŭ���� ��f" + s[3].replace("B", "").replace("b", "") + " ��7���� ��f���š�7�մϴ�.");
				}
				
				if (s[4].contains("S") || s[4].contains("s")) {
					lore.add("��f- ��7��Ŭ���� ��f" + s[4].replace("S", "").replace("s", "") + " ��7���� ��f�Ǹš�7�մϴ�.");
				}
				
				else if (s[4].contains("B") || s[4].contains("b")) {
					lore.add("��f- ��7��Ŭ���� ��f" + s[4].replace("B", "").replace("b", "") + " ��7���� ��f���š�7�մϴ�.");
				}
			}
			
			else if (s.length == 4) {
				if (s[3].contains("S") || s[3].contains("s")) {
					lore.add("��f- ��7��Ŭ���� ��f" + s[3].replace("S", "").replace("s", "") + " ��7���� ��f�Ǹš�7�մϴ�.");
				}
				
				else if (s[3].contains("B") || s[3].contains("b")) {
					lore.add("��f- ��7��Ŭ���� ��f" + s[3].replace("B", "").replace("b", "") + " ��7���� ��f���š�7�մϴ�.");
				}
			}
			
			else {
				continue;
			}
			
			if (Integer.parseInt(s[2]) > 64) {
				Stack(null, Integer.parseInt(s[0]), Integer.parseInt(s[1]), 1, lore, z, GUI);
			} else {
				Stack(null, Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), lore, z, GUI);
			}
			
			i++; z++;
		}
		
		if (list.size() > i) {
			Stack2("��6���� ��� Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.BackRanking, 45, GUI);
		} else {
			Stack("��6���� ��� Ȯ��", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			List<String> list2 = new ArrayList<String>();
			list2.add("��7������: ��fshinkhan");
			Stack("", 119, 0, 1, list2, x, GUI);
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
