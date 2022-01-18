package me.shinkhan.Finance.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Borrow {
	public static void CreateBorrowConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("�˸���", "&bŬ���� ������ ��ȯ�˴ϴ�.");
            config.set("default.�̸�", "&f���� ������ ������ �����ϴ�.");
    		List<String> list = config.getStringList("default.����");
            list.add("&a����� ��� ������ �Ϸ��߽��ϴ�.");
            config.set("default.����", list);
            config.set("default.������ �ڵ�", 339);
            config.set("default.������ �������ڵ�", 0);
            config.set("default.������ ����", 1);
            config.set("1.�̸�", "�츮 ����");
            config.set("1.���Ⱑ�� �ݾ�", 10);
            config.set("1.��ȯ��", 15);
            config.set("1.��ȯ����", 18);
            config.set("1.��ȯ�ð� (��)", 60);
            config.set("1.����", "50%");
            config.set("2.�̸�", "���� ����");
            config.set("2.���Ⱑ�� �ݾ�", 20);
            config.set("2.��ȯ��", 24);
            config.set("2.��ȯ����", 30);
            config.set("2.��ȯ�ð� (��)", 120);
            config.set("2.����", "20%");
            config.set("3.�̸�", "���� ����");
            config.set("3.���Ⱑ�� �ݾ�", 30);
            config.set("3.��ȯ��", 36);
            config.set("3.��ȯ����", 45);
            config.set("3.��ȯ�ð� (��)", 180);
            config.set("3.����", "20%");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getLoreList(Player p, String name) {
		File f = new File("plugins/DHFinance/Borrow.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateBorrowConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = replaceAllColors(str);
    		Returnlist.add(str);
        } return Returnlist;
	}
	
	public static Boolean getBorrowBoolean(String name) {
		File f = new File("plugins/DHFinance/Borrow.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateBorrowConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getBorrowInt(String name) {
		File f = new File("plugins/DHFinance/Borrow.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateBorrowConfig(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getBorrowString(String name) {
		File f = new File("plugins/DHFinance/Borrow.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateBorrowConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		else {
			String getString = replaceAllColors(config.getString(name));
			return getString;
		}
	}
	
	public static void setBorrowBoolean(String name, Boolean amount) {
		File f = new File("plugins/DHFinance/Borrow.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateBorrowConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setBorrowInt(String name, int amount) {
		File f = new File("plugins/DHFinance/Borrow.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateBorrowConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setBorrowString(String name, String amount) {
		File f = new File("plugins/DHFinance/Borrow.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateBorrowConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
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
}
