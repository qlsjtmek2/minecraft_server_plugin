package me.espoo.pvp;

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
			
            config.set("�ݷμ���.Red.��ǥ", "0,0,0,0,0,0");
            config.set("�ݷμ���.Blue.��ǥ", "0,0,0,0,0,0");
			
            config.set("1:1.Red.��ǥ", "0,0,0,0,0,0");
            config.set("1:1.Blue.��ǥ", "0,0,0,0,0,0");

            config.set("2:2.Red.ù��° ��ǥ", "0,0,0,0,0,0");
            config.set("2:2.Red.�ι�° ��ǥ", "0,0,0,0,0,0");
            config.set("2:2.Blue.ù��° ��ǥ", "0,0,0,0,0,0");
            config.set("2:2.Blue.�ι�° ��ǥ", "0,0,0,0,0,0");

            config.set("3:3.Red.ù��° ��ǥ", "0,0,0,0,0,0");
            config.set("3:3.Red.�ι�° ��ǥ", "0,0,0,0,0,0");
            config.set("3:3.Red.����° ��ǥ", "0,0,0,0,0,0");
            config.set("3:3.Blue.ù��° ��ǥ", "0,0,0,0,0,0");
            config.set("3:3.Blue.�ι�° ��ǥ", "0,0,0,0,0,0");
            config.set("3:3.Blue.����° ��ǥ", "0,0,0,0,0,0");

            config.set("4:4.Red.ù��° ��ǥ", "0,0,0,0,0,0");
            config.set("4:4.Red.�ι�° ��ǥ", "0,0,0,0,0,0");
            config.set("4:4.Red.����° ��ǥ", "0,0,0,0,0,0");
            config.set("4:4.Red.�׹�° ��ǥ", "0,0,0,0,0,0");
            config.set("4:4.Blue.ù��° ��ǥ", "0,0,0,0,0,0");
            config.set("4:4.Blue.�ι�° ��ǥ", "0,0,0,0,0,0");
            config.set("4:4.Blue.����° ��ǥ", "0,0,0,0,0,0");
            config.set("4:4.Blue.�׹�° ��ǥ", "0,0,0,0,0,0");
            
            config.set("��� ����.���� �ð� (��)", 5);
            config.set("��� ����.��� �ð� (��)", 60);
            config.set("��� ����.������ �ð� (��)", 3);
            config.set("�ݷμ��� ����.���� �ð� (��)", 20);
            config.set("�ݷμ��� ����.��� �ð� (��)", 120);
            config.set("�ݷμ��� ����.������ �ð� (��)", 6);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchPvP] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
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
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
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
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
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
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchPvP] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchPvP] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchPvP] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchPvP] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
