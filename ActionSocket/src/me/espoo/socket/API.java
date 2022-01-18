package me.espoo.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class API {
	public static void removeMapData(Player p) {
		if (main.damage.containsKey(p)) main.damage.remove(p);
		if (main.cretprobe.containsKey(p)) main.cretprobe.remove(p);
		if (main.cretdamage.containsKey(p)) main.cretdamage.remove(p);
		if (main.heart.containsKey(p)) main.heart.remove(p);
		if (main.defense.containsKey(p)) main.defense.remove(p);
		if (main.dodge.containsKey(p)) main.dodge.remove(p);
	}
	
	public static boolean isDataAttack(Player p) {
		return main.damage.containsKey(p) || main.cretprobe.containsKey(p) || main.cretdamage.containsKey(p) || main.heart.containsKey(p);
	}
	
	public static boolean isDataBeShot(Player p) {
		return main.defense.containsKey(p) || main.dodge.containsKey(p);
	}
	
	public static void setData(Player p) {
		int damage = 0;
		int cretprobe = 0;
		int cretdamage = 0;
		int heart = 0;
		int defense = 0;
		int dodge = 0;
		
		if (PlayerYml.getInfoString(p, "1.������ �̸�") != null) {
			String str = API.getLoreNum(PlayerYml.getInfoList(p, "1.������ ����"));

			if (str.equalsIgnoreCase("�߰� ������")) {
				damage += API.getPlusDamage(PlayerYml.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
				cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
				cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ü�� ���")) {
				heart += API.getHeartGet(PlayerYml.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("������ ����")) {
				defense += API.getDamageDefense(PlayerYml.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
				dodge += API.getDodge(PlayerYml.getInfoList(p, "1.������ ����"));
			}
		}
		
		if (PlayerYml.getInfoString(p, "2.������ �̸�") != null) {
			String str = API.getLoreNum(PlayerYml.getInfoList(p, "2.������ ����"));
			if (str.equalsIgnoreCase("�߰� ������")) {
				damage += API.getPlusDamage(PlayerYml.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
				cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
				cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ü�� ���")) {
				heart += API.getHeartGet(PlayerYml.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("������ ����")) {
				defense += API.getDamageDefense(PlayerYml.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
				dodge += API.getDodge(PlayerYml.getInfoList(p, "2.������ ����"));
			}
		}
		
		if (PlayerYml.getInfoString(p, "3.������ �̸�") != null) {
			String str = API.getLoreNum(PlayerYml.getInfoList(p, "3.������ ����"));
			if (str.equalsIgnoreCase("�߰� ������")) {
				damage += API.getPlusDamage(PlayerYml.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
				cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
				cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ü�� ���")) {
				heart += API.getHeartGet(PlayerYml.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("������ ����")) {
				defense += API.getDamageDefense(PlayerYml.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
				dodge += API.getDodge(PlayerYml.getInfoList(p, "3.������ ����"));
			}
		}
		
		API.setMapData(p, damage, cretprobe, cretdamage, heart, defense, dodge);
	}
	
	public static void setMapData(Player p, int damage, int cretprobe, int cretdamage, int heart, int defense, int dodge) {
		if (main.damage.containsKey(p)) main.damage.remove(p);
		if (main.cretprobe.containsKey(p)) main.cretprobe.remove(p);
		if (main.cretdamage.containsKey(p)) main.cretdamage.remove(p);
		if (main.heart.containsKey(p)) main.heart.remove(p);
		if (main.defense.containsKey(p)) main.defense.remove(p);
		if (main.dodge.containsKey(p)) main.dodge.remove(p);
		
		main.damage.put(p, damage);
		main.cretprobe.put(p, cretprobe);
		main.cretdamage.put(p, cretdamage);
		main.heart.put(p, heart);
		main.defense.put(p, defense);
		main.dodge.put(p, dodge);
	}
	
	public static String getLoreNum(List<String> lore) {
		for (String str : lore) {
			if (str.contains("�߰� ������")) return "�߰� ������";
			if (str.contains("������ ����")) return "������ ����";
			if (str.contains("ũ��Ƽ�� Ȯ��")) return "ũ��Ƽ�� Ȯ��";
			if (str.contains("ũ��Ƽ�� ������")) return "ũ��Ƽ�� ������";
			if (str.contains("ȸ�Ƿ�")) return "ȸ�Ƿ�";
			if (str.contains("ü�� ���")) return "ü�� ���";
		} return null;
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cast forcecast " + p.getName() + " lvup");
	}
	
	public static int getPlusDamage(List<String> lore) {
		for (String str : lore) {
			if (str.contains("�߰� ������")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]));
		} return 0;
	}
	
	public static int getCretProbe(List<String> lore) {
		for (String str : lore) {
			if (str.contains("ũ��Ƽ�� Ȯ��")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]).replaceAll("%", ""));
		} return 0;
	}
	
	public static int getCretDamage(List<String> lore) {
		for (String str : lore) {
			if (str.contains("ũ��Ƽ�� ������")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]));
		} return 0;
	}
	
	public static int getHeartGet(List<String> lore) {
		for (String str : lore) {
			if (str.contains("ü�� ���")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]));
		} return 0;
	}
	
	public static int getDamageDefense(List<String> lore) {
		for (String str : lore) {
			if (str.contains("������ ����")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]).replaceAll("%", ""));
		} return 0;
	}
	
	public static int getDodge(List<String> lore) {
		for (String str : lore) {
			if (str.contains("ȸ�Ƿ�")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[2]).replaceAll("%", ""));
		} return 0;
	}
	
	public static List<String> getOneLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��8���ϱ� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(6) + 5;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 9) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (three > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 2:
			int four = new Random().nextInt(3) + 1;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (four > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 6;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 14) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (five > 14) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 4:
			int six = new Random().nextInt(3) + 3;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 4) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (six > 4) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 5:
			int seven = new Random().nextInt(3) + 1;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (seven > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 6:
			int eight = new Random().nextInt(3) + 1;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (eight > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
		}
		
		return list;
	}
	
	public static List<String> getTwoLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��7�ϱ� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(12) + 9;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 19) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (three > 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 2:
			int four = new Random().nextInt(2) + 3;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 3) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (four > 3) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 16;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 24) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (five > 24) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 4:
			int six = new Random().nextInt(10) + 5;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 9) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (six > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 5:
			int seven = new Random().nextInt(4) + 3;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 5) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (seven > 5) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 6:
			int eight = new Random().nextInt(3) + 3;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 4) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (eight > 4) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
		}
		
		return list;
	}
	
	public static List<String> getThreeLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��a�߱� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(12) + 21;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 31) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (three > 31) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 2:
			int four = new Random().nextInt(3) + 4;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 5) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (four > 5) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 26;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 34) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (five > 34) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 4:
			int six = new Random().nextInt(13) + 13;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 24) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (six > 24) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 5:
			int seven = new Random().nextInt(6) + 7;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 11) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (seven > 11) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 6:
			int eight = new Random().nextInt(4) + 5;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 7) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (eight > 7) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
		}
		
		return list;
	}
	
	public static List<String> getFourLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��b��� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(13) + 33;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (three > 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 2:
			int four = new Random().nextInt(5) + 6;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (four > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 36;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (five > 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 4:
			int six = new Random().nextInt(15) + 31;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (six > 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 5:
			int seven = new Random().nextInt(8) + 13;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (seven > 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 6:
			int eight = new Random().nextInt(6) + 7;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 11) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (eight > 11) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
		}
		
		return list;
	}
	
	public static List<String> getFiveLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��6�ֻ�� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(15) + 46;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 59) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (three > 59) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 2:
			int four = new Random().nextInt(7) + 9;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 14) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (four > 14) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 46;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 54) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (five > 54) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 4:
			int six = new Random().nextInt(26) + 41;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 65) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (six > 65) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 5:
			int seven = new Random().nextInt(8) + 21;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 27) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (seven > 27) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 6:
			int eight = new Random().nextInt(10) + 11;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (eight > 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
		}
		
		return list;
	}
	
	public static ItemStack getItem(String type, String option, int integer, int amount) {
		ItemStack item = null;
		List<String> list = new ArrayList<String>();
		int intoption = 0;
		switch (option) {
		case "�ߵ�":
			intoption = 1;
			break;
			
		case "����":
			intoption = 2;
			break;
			
		case "ũȮ":
			intoption = 3;
			break;
			
		case "ũ��":
			intoption = 4;
			break;
			
		case "ȸ��":
			intoption = 5;
			break;
			
		case "ü��":
			intoption = 6;
			break;
			
		default:
			return null;
		}
		
		
		switch (type) {
		case "����":
			item = new MaterialData(263, (byte) 0).toItemStack(amount);
			list.add("��7��l==================");
			list.add("��8���ϱ� ���ϼ���f�� �����Ͽ�");
			list.add("��fȹ���� ���ϼ��̴�. ä��â");
			list.add("��e[ /���� ] ��f�Է��� ��������.");
			list.add("��7��l==================");
			list.add("��f");
			break;
		case "��":
			item = new MaterialData(318, (byte) 0).toItemStack(amount);
			list.add("��7��l==================");
			list.add("��7�ϱ� ���ϼ���f�� �����Ͽ�");
			list.add("��fȹ���� ���ϼ��̴�. ä��â");
			list.add("��e[ /���� ] ��f�Է��� ��������.");
			list.add("��7��l==================");
			list.add("��f");
			break;
		case "��":
			item = new MaterialData(265, (byte) 0).toItemStack(amount);
			list.add("��7��l==================");
			list.add("��a�߱� ���ϼ���f�� �����Ͽ�");
			list.add("��fȹ���� ���ϼ��̴�. ä��â");
			list.add("��e[ /���� ] ��f�Է��� ��������.");
			list.add("��7��l==================");
			list.add("��f");
			break;
		case "��":
			item = new MaterialData(264, (byte) 0).toItemStack(amount);
			list.add("��7��l==================");
			list.add("��b��� ���ϼ���f�� �����Ͽ�");
			list.add("��fȹ���� ���ϼ��̴�. ä��â");
			list.add("��e[ /���� ] ��f�Է��� ��������.");
			list.add("��7��l==================");
			list.add("��f");
			break;
		case "�ֻ�":
			item = new MaterialData(388, (byte) 0).toItemStack(amount);
			list.add("��7��l==================");
			list.add("��6�ֻ�� ���ϼ���f�� �����Ͽ�");
			list.add("��fȹ���� ���ϼ��̴�. ä��â");
			list.add("��e[ /���� ] ��f�Է��� ��������.");
			list.add("��7��l==================");
			list.add("��f");
			break;
			
		default:
			return null;
		}
		
		switch (intoption) {
		case 1:
			list.add("��f[��6��l+��f] �߰� ������ +" + integer);
			break;
			
		case 2:
			list.add("��f[��6��l+��f] ������ ���� " + integer + "%");
			break;
			
		case 3:
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + integer + "%");
			break;
			
		case 4:
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + integer);
			break;
			
		case 5:
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + integer + "%");
			break;
			
		case 6:
			list.add("��f[��6��l+��f] ü�� ��� +" + integer);
			break;
			
		default:
			return null;
		}
		
		switch (type) {
		case "����":
			switch (intoption) {
			case 1:
				if (integer <= 9) list.add("��f[��6��l+��f] ��� - ��6��");
				else if (integer > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				break;
				
			case 2:
				if (integer <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
				else if (integer > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				break;
				
			case 3:
				if (integer <= 14) list.add("��f[��6��l+��f] ��� - ��6��");
				else if (integer > 14) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				break;
				
			case 4:
				if (integer <= 4) list.add("��f[��6��l+��f] ��� - ��6��");
				else if (integer > 4) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				break;
				
			case 5:
				if (integer <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
				else if (integer > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				break;
				
			case 6:
				if (integer <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
				else if (integer > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				break;
			} break;
			
		case "��":
			switch (intoption) {
			case 1:
				if (integer <= 19) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				else if (integer > 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				break;
				
			case 2:
				if (integer <= 3) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				else if (integer > 3) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				break;
				
			case 3:
				if (integer <= 24) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				else if (integer > 24) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				break;
				
			case 4:
				if (integer <= 9) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				else if (integer > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				break;
				
			case 5:
				if (integer <= 5) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				else if (integer > 5) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				break;
				
			case 6:
				if (integer <= 4) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
				else if (integer > 4) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				break;
			} break;
			
		case "��":
			switch (intoption) {
			case 1:
				if (integer <= 31) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				else if (integer > 31) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				break;
				
			case 2:
				if (integer <= 5) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				else if (integer > 5) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				break;
				
			case 3:
				if (integer <= 34) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				else if (integer > 34) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				break;
				
			case 4:
				if (integer <= 24) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				else if (integer > 24) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				break;
				
			case 5:
				if (integer <= 11) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				else if (integer > 11) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				break;
				
			case 6:
				if (integer <= 7) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
				else if (integer > 7) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				break;
			} break;
			
		case "��":
			switch (intoption) {
			case 1:
				if (integer <= 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				if (integer > 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				break;
				
			case 2:
				if (integer <= 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				if (integer > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				break;
				
			case 3:
				if (integer <= 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				if (integer > 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				break;
				
			case 4:
				if (integer <= 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				if (integer > 44) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				break;
				
			case 5:
				if (integer <= 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				if (integer > 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				break;
				
			case 6:
				if (integer <= 11) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
				if (integer > 11) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				break;
			} break;
			
		case "�ֻ�":
			switch (intoption) {
			case 1:
				if (integer <= 59) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				if (integer > 59) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
				break;
				
			case 2:
				if (integer <= 14) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				if (integer > 14) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
				break;
				
			case 3:
				if (integer <= 54) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				if (integer > 54) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
				break;
				
			case 4:
				if (integer <= 65) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				if (integer > 65) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
				break;
				
			case 5:
				if (integer <= 27) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				if (integer > 27) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
				break;
				
			case 6:
				if (integer <= 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
				if (integer > 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
				break;
			} break;
			
		default:
			return null;
		}
		
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("��6������ ���ϼ�");
		item_Meta.setLore(list);
		item.setItemMeta(item_Meta);
		
		return item;
	}
}
