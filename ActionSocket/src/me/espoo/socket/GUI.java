package me.espoo.socket;

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
	static PlayerYml P;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "���� ����");
		int num = 0;
		
		for (int i = 0; i < 8; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 9;
			else if (i == 2) num = 18;
			else if (i == 3) num = 27;
			else if (i == 4) num = 8;
			else if (i == 5) num = 17;
			else if (i == 6) num = 26;
			else if (i == 7) num = 35;
				
			Stack2("", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		for (int i = 0; i < 22; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 10;
			else if (i == 8) num = 19;
			else if (i == 9) num = 12;
			else if (i == 10) num = 21;
			else if (i == 11) num = 14;
			else if (i == 12) num = 23;
			else if (i == 13) num = 16;
			else if (i == 14) num = 25;
			else if (i == 15) num = 28;
			else if (i == 16) num = 29;
			else if (i == 17) num = 30;
			else if (i == 18) num = 31;
			else if (i == 19) num = 32;
			else if (i == 20) num = 33;
			else if (i == 21) num = 34;
			
			Stack2("", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		Stack("��6���� ��e���� ��fù��°", 323, 0, 1, getLore("ù"), 11, GUI);
		Stack("��6���� ��e���� ��f�ι�°", 323, 0, 1, getLore("��"), 13, GUI);
		Stack("��6���� ��e���� ��f����°", 323, 0, 1, getLore("��"), 15, GUI);
		
		if (P.getInfoInt(p, "1.������ �ڵ�") != 0 && P.getInfoString(p, "1.������ �̸�") != null && !P.getInfoList(p, "1.������ ����").equals(Arrays.asList())) {
			Stack(P.getInfoString(p, "1.������ �̸�"), P.getInfoInt(p, "1.������ �ڵ�"), 0, 1, P.getInfoList(p, "1.������ ����"), 20, GUI);
		}
		
		if (P.getInfoInt(p, "2.������ �ڵ�") != 0 && P.getInfoString(p, "2.������ �̸�") != null && !P.getInfoList(p, "2.������ ����").equals(Arrays.asList())) {
			Stack(P.getInfoString(p, "2.������ �̸�"), P.getInfoInt(p, "2.������ �ڵ�"), 0, 1, P.getInfoList(p, "2.������ ����"), 22, GUI);
		}
		
		if (P.getInfoInt(p, "3.������ �ڵ�") != 0 && P.getInfoString(p, "3.������ �̸�") != null && !P.getInfoList(p, "3.������ ����").equals(Arrays.asList())) {
			Stack(P.getInfoString(p, "3.������ �̸�"), P.getInfoInt(p, "3.������ �ڵ�"), 0, 1, P.getInfoList(p, "3.������ ����"), 24, GUI);
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
	
	public static List<String> getLore(String str) {
		List<String> lore = new ArrayList<String>();
		lore.add("��c" + str + "��° ��f���� ���� �����Դϴ�.");
		lore.add("��f�ؿ� �� ������ ���ϼ��� �÷��ּ���.");
		return lore;
	}
}
