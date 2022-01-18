package me.espoo.upgrade;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class API {
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "��1");
        message = message.replaceAll("&2", "��2");
        message = message.replaceAll("&3", "��3");
        message = message.replaceAll("&4", "��4");
        message = message.replaceAll("&5", "��5");
        message = message.replaceAll("&6", "��6");
        message = message.replaceAll("&7", "��7");
        message = message.replaceAll("&8", "��8");
        message = message.replaceAll("&9", "��9");
        message = message.replaceAll("&0", "��0");
        message = message.replaceAll("&a", "��a");
        message = message.replaceAll("&b", "��b");
        message = message.replaceAll("&c", "��c");
        message = message.replaceAll("&d", "��d");
        message = message.replaceAll("&e", "��e");
        message = message.replaceAll("&f", "��f");
        message = message.replaceAll("&k", "��k");
        message = message.replaceAll("&l", "��l");
        message = message.replaceAll("&m", "��m");
        message = message.replaceAll("&n", "��n");
        message = message.replaceAll("&o", "��o");
        message = message.replaceAll("&r", "��r");
        return message;
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cast forcecast " + p.getName() + " lvup");
	}
	
	public static List<String> getUpgradePaperList() {
		return Config.getList("��ȭ�ֹ��� ���");
	}
	
	public static List<String> getUpgradeNList() {
		return Config.getList("��ȭ ��� (name)");
	}
	
	public static List<String> getUpgradeLList() {
		return Config.getList("��ȭ ��� (lore)");
	}
	
	public static List<String> getList(String name) {
		return Config.getList(name);
	}
	
	public static boolean isUpgradePaper(String name) {
		List<String> list = getUpgradePaperList();
		if (list == null || list.isEmpty()) return false;
		if (list.contains(name)) return true;
		return false;
	}
	
	public static String getUpgradeNameN(String name) {
		List<String> list = getUpgradeNList();
		if (list == null || list.isEmpty()) return null;
		for (String str : list) 
			if (name.contains(str)) return str;
		return null;
	}
	
	public static String getUpgradeNameL(List<String> lore) {
		List<String> list = getUpgradeLList();
		if (list == null || list.isEmpty()) return null;
		for (String str : list) 
			for (String str2 : lore)
				if (str.equalsIgnoreCase(str2)) return str;
		return null;
	}
	
	public static String getUpgradeItemName(String name, int num) {
		List<String> list = getList(name);
		if (list == null || list.isEmpty()) return null;
		if (num < 0) num = 0;
		return list.get(num);
	}
	
	public static boolean isUpgradeMax(String name, String display) {
		List<String> list = getList(name);
		if (list == null || list.isEmpty()) return false;
		if (list.get(list.size() - 1).equalsIgnoreCase(display)) return true;
		return false;
	}
	
	public static int getUpgradeNum(String name, String display) {
		List<String> list = getList(name);
		if (list == null || list.isEmpty()) return -1;
		int num = 0;
		for (String str : list) {
			if (display.equalsIgnoreCase(str)) return num;
			else num++;
		} return -1;
	}
	
	public static String[] getUpgradeStr(String name, int num) {
		List<String> list = getList(name + " Ȯ��");
		if (list == null || list.isEmpty()) return null;
		return list.get(num).split(" ");
	}
	
	public static int getPlayerPremiumAmount(Player p) {
		return PlayerYml.getInfoInt(p, "�ູ ��ȸ");
	}
	
	public static void addPlayerPremiumAmount(Player p) {
		int n = PlayerYml.getInfoInt(p, "�ູ ��ȸ") + 1;
		PlayerYml.setInfoInt(p, "�ູ ��ȸ", n);
	}
	
	public static void subPlayerPremiumAmount(Player p) {
		int n = PlayerYml.getInfoInt(p, "�ູ ��ȸ") - 1;
		if (n < 0) n = 0;
		PlayerYml.setInfoInt(p, "�ູ ��ȸ", n);
	}
	
	public static boolean isPremium(Player p) {
		return PlayerYml.getInfoBoolean(p, "�ູ ����");
	}
	
	public static void changePremium(Player p) {
		PlayerYml.setInfoBoolean(p, "�ູ ����", !isPremium(p));
	}
	
	public static ItemStack getPremiumItemStack(Player p) {
		List<String> lore = new ArrayList<String>();
		int premium = getPlayerPremiumAmount(p);
		ItemStack item = null;
		
		if (premium <= 0 || !isPremium(p)) {
			item = new MaterialData(35, (byte) 14).toItemStack(1);
			lore.add("��7�ູ ����: ��c��Ȱ��ȭ");
		} else {
			item = new MaterialData(35, (byte) 5).toItemStack(1);
			lore.add("��7�ູ ����: ��aȰ��ȭ");
		}

		lore.add("��7�ູ ����: ��f" + premium);
		lore.add("��f");
		lore.add("��7* ��fŬ���� �ູ ��� ���θ�");
		lore.add("��fȰ��ȭ/��Ȱ��ȭ �մϴ�.");
		
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("��6�ູ");
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		return item;
	}
	
	public static ItemStack getCloseItemStack(Player p) {
		ItemStack item = new MaterialData(324, (byte) 0).toItemStack(1);
		List<String> lore = new ArrayList<String>();
		lore.add("��7Ŭ���� ��ȭ�� ����մϴ�.");
		
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("��f��ȭ ���");
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		return item;
	}
	
	public static ItemStack getLoreItemStack(Player p, String display, String name, int num, int slot) {
		ItemStack item = new MaterialData(101, (byte) 0).toItemStack(1);
		List<String> lore = new ArrayList<String>();
		lore.add(display);
		lore.add(name);
		lore.add(Integer.toString(num));
		lore.add(Integer.toString(slot));
		
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("��f");
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		return item;
	}
	
	public static ItemStack getUpgradeItemStack(Player p, String display, String name, int num) {
		ItemStack item = new MaterialData(145, (byte) 0).toItemStack(1);
		List<String> lore = new ArrayList<String>();
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("��a��ȭ �ϱ�");
		
		String[] str1 = API.getUpgradeStr(name, num)[0].split(",");
		String[] str2 = API.getUpgradeStr(name, num)[1].split(",");
		
		int success1 = Integer.parseInt(str1[0]);
		int minus1 = Integer.parseInt(str1[1]);
		int broken1 = Integer.parseInt(str1[2]);
		int fail1 = 100 - success1 - minus1 - broken1;
		int success2 = Integer.parseInt(str2[0]);
		int minus2 = Integer.parseInt(str2[1]);
		int broken2 = Integer.parseInt(str2[2]);
		int fail2 = 100 - success2 - minus2 - broken2;
		
		if (!isPremium(p) || API.getPlayerPremiumAmount(p) <= 0) {
			lore.add("��6==== �Ϲ� ====");
			lore.add("��6�� ����: ��c" + success1);
			lore.add("��6�� ����: ��c" + fail1);
			lore.add("��6�� �ٿ�: ��c" + minus1);
			lore.add("��6�� �ı�: ��c" + broken1);
			lore.add("��6============");
			lore.add("��f");
			lore.add("��7��m==== �ູ ====");
			lore.add("��7��m�� ����: " + success2);
			lore.add("��7��m�� ����: " + fail2);
			lore.add("��7��m�� �ٿ�: " + minus2);
			lore.add("��7��m�� �ı�: " + broken2);
			lore.add("��7��m============");
			lore.add("��f");
			lore.add("��7* ��fŬ���� ��ȭ�մϴ�.");
		} else {
			lore.add("��7��m==== �Ϲ� ====");
			lore.add("��7��m�� ����: " + success1);
			lore.add("��7��m�� ����: " + fail1);
			lore.add("��7��m�� �ٿ�: " + minus1);
			lore.add("��7��m�� �ı�: " + broken1);
			lore.add("��7��m============");
			lore.add("��f");
			lore.add("��6==== �ູ ====");
			lore.add("��6�� ����: ��c" + success2);
			lore.add("��6�� ����: ��c" + fail2);
			lore.add("��6�� �ٿ�: ��c" + minus2);
			lore.add("��6�� �ı�: ��c" + broken2);
			lore.add("��6============");
			lore.add("��f");
			lore.add("��7* ��fŬ���� ��ȭ�մϴ�.");
		}
		
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		return item;
	}
	
	public static ItemStack getPaperItemStack(int num, int amount) {
		ItemStack item = new MaterialData(339, (byte) 0).toItemStack(amount);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(API.getUpgradePaperList().get(num));
		item.setItemMeta(item_Meta);
		return item;
	}
	
	public static boolean isInventoryClearSpace(Player p) {
		int t = 0;
	    ItemStack[] contents;
	    for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
	        ItemStack it = contents[j];
	        if (it == null) {
	            ++t;
	        }
	    }
	    
	    return t > 0;
	}
}
