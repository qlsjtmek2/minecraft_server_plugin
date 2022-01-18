package me.shinkhan.music;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	public static void CreateConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			
			List<String> list = config.getStringList("������.����");
            List<String> list2 = config.getStringList("���� ����.����");
            List<String> list3 = config.getStringList("Stop ������.����");
            list.add("��7Ŭ���� ������ ����մϴ�.");
            list2.add("��6/���� <all/playername> <�뷡�̸�> ��f- ������ ����մϴ�.");
            list3.add("��7Ŭ���� ������ �����մϴ�.");
            config.set("������.����", list);
            config.set("������.�ڵ�", 84);
            config.set("������.������ �ڵ�", 0);
            config.set("Stop ������.����", list3);
            config.set("Stop ������.�ڵ�", 77);
            config.set("Stop ������.������ �ڵ�", 0);
            config.set("���� ����.����", list2);
            config.set("���� ����.�̸�", "��6��l���� ��ɾ�");
            config.set("���� ����.�ڵ�", 340);
            config.set("���� ����.������ �ڵ�", 0);
            config.set("Ÿ�� ������.�ڵ�", 143);
            config.set("Ÿ�� ������.������ �ڵ�", 0);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		
		if (list == null) {
			return null;
		}
		
		return list;
	}
	
	public static Boolean getBoolean(String name) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getString(String name) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setBoolean(String name, Boolean amount) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
