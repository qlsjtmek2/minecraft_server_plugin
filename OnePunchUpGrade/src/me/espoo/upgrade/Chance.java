package me.espoo.upgrade;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Chance {
	public static void CreateChanceConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			
            config.set("1�� ���� Ȯ��", "100,0,0");
            config.set("2�� ���� Ȯ��", "90,0,0");
            config.set("3�� ���� Ȯ��", "80,0,0");
            config.set("4�� ���� Ȯ��", "70,0,0");
            config.set("5�� ���� Ȯ��", "50,20,0");
            config.set("6�� ���� Ȯ��", "40,30,0");
            config.set("7�� ���� Ȯ��", "30,50,5");
            config.set("8�� ���� Ȯ��", "20,60,15");
            config.set("9�� ���� Ȯ��", "10,70,20");
            config.set("10�� ���� Ȯ��", "4,60,36");
            
            config.set("1�� Ư�� Ȯ��", "100,0,0");
            config.set("2�� Ư�� Ȯ��", "90,0,0");
            config.set("3�� Ư�� Ȯ��", "80,0,0");
            config.set("4�� Ư�� Ȯ��", "70,0,0");
            config.set("5�� Ư�� Ȯ��", "50,20,0");
            config.set("6�� Ư�� Ȯ��", "40,30,0");
            config.set("7�� Ư�� Ȯ��", "30,50,0");
            config.set("8�� Ư�� Ȯ��", "20,60,0");
            config.set("9�� Ư�� Ȯ��", "10,70,0");
            config.set("10�� Ư�� Ȯ��", "4,70,0");
            
            config.set("1�� �����̾� Ȯ��", "100,0,0");
            config.set("2�� �����̾� Ȯ��", "95,0,0");
            config.set("3�� �����̾� Ȯ��", "90,0,0");
            config.set("4�� �����̾� Ȯ��", "80,0,0");
            config.set("5�� �����̾� Ȯ��", "70,15,0");
            config.set("6�� �����̾� Ȯ��", "55,20,0");
            config.set("7�� �����̾� Ȯ��", "45,30,0");
            config.set("8�� �����̾� Ȯ��", "25,45,0");
            config.set("9�� �����̾� Ȯ��", "20,55,0");
            config.set("10�� �����̾� Ȯ��", "10,65,0");
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static Boolean getChanceBoolean(String name) {
		File f = new File("plugins/OnePunchUpGrade/Chance.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateChanceConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getChanceInt(String name) {
		File f = new File("plugins/OnePunchUpGrade/Chance.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateChanceConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getChanceString(String name) {
		File f = new File("plugins/OnePunchUpGrade/Chance.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateChanceConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = Method.replaceAllColors(config.getString(name));
		return getString;
	}
	
	public static String getChanceOrString(String name) {
		File f = new File("plugins/OnePunchUpGrade/Chance.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateChanceConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setChanceBoolean(String name, Boolean amount) {
		File f = new File("plugins/OnePunchUpGrade/Chance.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateChanceConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setChanceInt(String name, int amount) {
		File f = new File("plugins/OnePunchUpGrade/Chance.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateChanceConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setChanceString(String name, String amount) {
		File f = new File("plugins/OnePunchUpGrade/Chance.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateChanceConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
