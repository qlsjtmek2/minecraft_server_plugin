package me.espoo.switched.trade;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class TradeConfig {
	public static void CreateConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("교환기 이름.조합개수", 0);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionSwitch] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
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
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return 0;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getString(String name) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static ItemStack getItemStack(String name) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getItemStack(name) == null)
			return null;
		ItemStack getItemStack = config.getItemStack(name);
		return getItemStack;
	}
	
	public static void setBoolean(String name, Boolean amount) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionSwitch] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionSwitch] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionSwitch] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setItemStack(String name, ItemStack amount) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionSwitch] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/ActionSwitch/Config.yml");
		File folder = new File("plugins/ActionSwitch");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionSwitch] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
