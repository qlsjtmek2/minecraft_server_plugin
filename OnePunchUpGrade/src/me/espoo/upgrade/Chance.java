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
			
            config.set("1강 보통 확률", "100,0,0");
            config.set("2강 보통 확률", "90,0,0");
            config.set("3강 보통 확률", "80,0,0");
            config.set("4강 보통 확률", "70,0,0");
            config.set("5강 보통 확률", "50,20,0");
            config.set("6강 보통 확률", "40,30,0");
            config.set("7강 보통 확률", "30,50,5");
            config.set("8강 보통 확률", "20,60,15");
            config.set("9강 보통 확률", "10,70,20");
            config.set("10강 보통 확률", "4,60,36");
            
            config.set("1강 특별 확률", "100,0,0");
            config.set("2강 특별 확률", "90,0,0");
            config.set("3강 특별 확률", "80,0,0");
            config.set("4강 특별 확률", "70,0,0");
            config.set("5강 특별 확률", "50,20,0");
            config.set("6강 특별 확률", "40,30,0");
            config.set("7강 특별 확률", "30,50,0");
            config.set("8강 특별 확률", "20,60,0");
            config.set("9강 특별 확률", "10,70,0");
            config.set("10강 특별 확률", "4,70,0");
            
            config.set("1강 프리미엄 확률", "100,0,0");
            config.set("2강 프리미엄 확률", "95,0,0");
            config.set("3강 프리미엄 확률", "90,0,0");
            config.set("4강 프리미엄 확률", "80,0,0");
            config.set("5강 프리미엄 확률", "70,15,0");
            config.set("6강 프리미엄 확률", "55,20,0");
            config.set("7강 프리미엄 확률", "45,30,0");
            config.set("8강 프리미엄 확률", "25,45,0");
            config.set("9강 프리미엄 확률", "20,55,0");
            config.set("10강 프리미엄 확률", "10,65,0");
            
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
