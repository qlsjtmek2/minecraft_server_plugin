package me.shinkhan.shop;

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
			
			List<String> list;
            list = config.getStringList("상점 이름");
            list.add("1,0,10,B10,S1");
            list.add("35,2,5,S5");
            config.set("상점 이름", list);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHShop] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/DHShop/Shop.yml");
		File folder = new File("plugins/DHShop");
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
		File f = new File("plugins/DHShop/Shop.yml");
		File folder = new File("plugins/DHShop");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/DHShop/Shop.yml");
		File folder = new File("plugins/DHShop");
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
		File f = new File("plugins/DHShop/Shop.yml");
		File folder = new File("plugins/DHShop");
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
		File f = new File("plugins/DHShop/Shop.yml");
		File folder = new File("plugins/DHShop");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHShop] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/DHShop/Shop.yml");
		File folder = new File("plugins/DHShop");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHShop] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/DHShop/Shop.yml");
		File folder = new File("plugins/DHShop");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHShop] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/DHShop/Shop.yml");
		File folder = new File("plugins/DHShop");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHShop] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
