package me.espoo.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class API {
	public static String getLoreNum(List<String> lore) {
		for (String str : lore) {
			if (str.contains("����ġ ���ʽ�")) return "����ġ ���ʽ�";
			if (str.contains("����� ���� �߰�")) return "����� ���� �߰�";
			if (str.contains("�߰� ������")) return "�߰� ������";
			if (str.contains("������ ����")) return "������ ����";
			if (str.contains("ũ��Ƽ�� Ȯ��")) return "ũ��Ƽ�� Ȯ��";
			if (str.contains("ũ��Ƽ�� ������")) return "ũ��Ƽ�� ������";
			if (str.contains("ȸ�Ƿ�")) return "ȸ�Ƿ�";
			if (str.contains("ü�� ���")) return "ü�� ���";
		} return null;
	}
	
	public static void castLvup(Player p) {
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	public static int getExpBouns(List<String> lore) {
		for (String str : lore) {
			if (str.contains("����ġ ���ʽ�")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]).replaceAll("%", ""));
		} return 0;
	}
	
	public static int getHeroScore(List<String> lore) {
		for (String str : lore) {
			if (str.contains("����� ���� �߰�")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[4]).replaceAll("%", ""));
		} return 0;
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
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��8���ϱ� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(10) + 11;
			list.add("��f[��6��l+��f] ����ġ ���ʽ� " + one + "%");
			if (one <= 15) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (one > 15) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 2:
			int two = new Random().nextInt(10) + 11;
			list.add("��f[��6��l+��f] ����� ���� �߰� " + two + "%");
			if (two <= 15) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (two > 15) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 3:
			int three = new Random().nextInt(6) + 5;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 7) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (three > 7) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 4:
			int four = new Random().nextInt(3) + 1;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (four > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 5:
			int five = new Random().nextInt(10) + 11;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 15) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (five > 15) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 6:
			int six = new Random().nextInt(4) + 2;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 3) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (six > 3) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 7:
			int seven = new Random().nextInt(4) + 1;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (seven > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
			
		case 8:
			int eight = new Random().nextInt(3) + 1;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 2) list.add("��f[��6��l+��f] ��� - ��6��");
			else if (eight > 2) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			break;
		}
		
		return list;
	}
	
	public static List<String> getTwoLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��7�ϱ� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(20) + 21;
			list.add("��f[��6��l+��f] ����ġ ���ʽ� " + one + "%");
			if (one <= 30) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (one > 30) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 2:
			int two = new Random().nextInt(20) + 21;
			list.add("��f[��6��l+��f] ����� ���� �߰� " + two + "%");
			if (two <= 30) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (two > 30) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 3:
			int three = new Random().nextInt(15) + 11;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 18) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (three > 18) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 4:
			int four = new Random().nextInt(3) + 3;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 3) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (four > 3) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 5:
			int five = new Random().nextInt(10) + 21;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 25) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (five > 25) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 6:
			int six = new Random().nextInt(6) + 5;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 7) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (six > 7) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 7:
			int seven = new Random().nextInt(4) + 5;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 6) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (seven > 6) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
			
		case 8:
			int eight = new Random().nextInt(3) + 3;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 3) list.add("��f[��6��l+��f] ��� - ��6�ڡ�");
			else if (eight > 3) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			break;
		}
		
		return list;
	}
	
	public static List<String> getThreeLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��a�߱� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(30) + 51;
			list.add("��f[��6��l+��f] ����ġ ���ʽ� " + one + "%");
			if (one <= 65) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (one > 65) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 2:
			int two = new Random().nextInt(30) + 51;
			list.add("��f[��6��l+��f] ����� ���� �߰� " + two + "%");
			if (two <= 65) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (two > 65) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 3:
			int three = new Random().nextInt(14) + 29;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 35) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (three > 35) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 4:
			int four = new Random().nextInt(4) + 5;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 6) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (four > 6) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 5:
			int five = new Random().nextInt(10) + 31;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 35) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (five > 35) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 6:
			int six = new Random().nextInt(13) + 13;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (six > 19) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 7:
			int seven = new Random().nextInt(8) + 9;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 12) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (seven > 12) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
			
		case 8:
			int eight = new Random().nextInt(4) + 5;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 6) list.add("��f[��6��l+��f] ��� - ��6�ڡڡ�");
			else if (eight > 6) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			break;
		}
		
		return list;
	}
	
	public static List<String> getFourLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��b��� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(50) + 101;
			list.add("��f[��6��l+��f] ����ġ ���ʽ� " + one + "%");
			if (one <= 125) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (one > 125) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 2:
			int two = new Random().nextInt(50) + 101;
			list.add("��f[��6��l+��f] ����� ���� �߰� " + two + "%");
			if (two <= 125) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (two > 125) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 3:
			int three = new Random().nextInt(20) + 46;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 55) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (three > 55) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 4:
			int four = new Random().nextInt(6) + 7;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (four > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 5:
			int five = new Random().nextInt(10) + 41;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 45) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (five > 45) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 6:
			int six = new Random().nextInt(15) + 31;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 38) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (six > 38) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 7:
			int seven = new Random().nextInt(12) + 21;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 27) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (seven > 27) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
			
		case 8:
			int eight = new Random().nextInt(6) + 7;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡ�");
			if (eight > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			break;
		}
		
		return list;
	}
	
	public static List<String> getFiveLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("��7��l==================");
		list.add("��6�ֻ�� ���ϼ���f�� �����Ͽ�");
		list.add("��fȹ���� ���ϼ��̴�. ä��â");
		list.add("��e[ /���� ] ��f�Է��� ��������.");
		list.add("��7��l==================");
		list.add("��f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(70) + 151;
			list.add("��f[��6��l+��f] ����ġ ���ʽ� " + one + "%");
			if (one <= 125) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (one > 125) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 2:
			int two = new Random().nextInt(70) + 151;
			list.add("��f[��6��l+��f] ����� ���� �߰� " + two + "%");
			if (two <= 125) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (two > 125) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 3:
			int three = new Random().nextInt(25) + 66;
			list.add("��f[��6��l+��f] �߰� ������ +" + three);
			if (three <= 55) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (three > 55) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 4:
			int four = new Random().nextInt(10) + 11;
			list.add("��f[��6��l+��f] ������ ���� " + four + "%");
			if (four <= 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (four > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 5:
			int five = new Random().nextInt(15) + 51;
			list.add("��f[��6��l+��f] ũ��Ƽ�� Ȯ�� " + five + "%");
			if (five <= 45) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (five > 45) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 6:
			int six = new Random().nextInt(26) + 41;
			list.add("��f[��6��l+��f] ũ��Ƽ�� ������ +" + six);
			if (six <= 38) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (six > 38) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 7:
			int seven = new Random().nextInt(14) + 29;
			list.add("��f[��6��l+��f] ȸ�Ƿ� " + seven + "%");
			if (seven <= 27) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (seven > 27) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
			
		case 8:
			int eight = new Random().nextInt(10) + 11;
			list.add("��f[��6��l+��f] ü�� ��� +" + eight);
			if (eight <= 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡ�");
			if (eight > 9) list.add("��f[��6��l+��f] ��� - ��6�ڡڡڡڡڡ�");
			break;
		}
		
		return list;
	}
}
