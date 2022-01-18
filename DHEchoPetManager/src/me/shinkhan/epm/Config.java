package me.shinkhan.epm;

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

            List<String> list1 = config.getStringList("ÀÏ¹Ý Æê");
            List<String> list2 = config.getStringList("·¹¾î Æê");
            List<String> list3 = config.getStringList("¾ç »ö±ò Æê");
            
    		list1.add("chicken:baby");
    		list1.add("cow:baby");
    		list1.add("creeper");
    		list1.add("pig:baby");
    		list1.add("skeleton");
    		list1.add("slime:small");
    		list1.add("wolf:baby");
    		list1.add("zombie:baby");
    		list1.add("sheep:baby");
    		
    		list2.add("bat");
    		list2.add("blaze");
    		list2.add("irongolem");
    		list2.add("magmacube:small");
    		list2.add("ocelot:baby");
    		list2.add("pigzombie:baby");
    		list2.add("mushroomcow:baby");
    		list2.add("ocelot:baby:black");
    		list2.add("ocelot:baby:siamese");
    		list2.add("villager:baby");
    		
    		list3.add("sheep:baby:orange");
    		list3.add("sheep:baby:magenta");
    		list3.add("sheep:baby:lightblue");
    		list3.add("sheep:baby:yellow");
    		list3.add("sheep:baby:lime");
    		list3.add("sheep:baby:pink");
    		list3.add("sheep:baby:gray");
    		list3.add("sheep:baby:silver");
    		list3.add("sheep:baby:cyan");
    		list3.add("sheep:baby:purple");
    		list3.add("sheep:baby:blue");
    		list3.add("sheep:baby:brown");
    		list3.add("sheep:baby:green");
    		list3.add("sheep:baby:red");
    		list3.add("sheep:baby:black");
            
            config.set("ÀÏ¹Ý Æê", list1);
            config.set("·¹¾î Æê", list2);
            config.set("¾ç »ö±ò Æê", list3);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHEchoPetManager] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/DHEchoPetManager/Config.yml");
		File folder = new File("plugins/DHEchoPetManager");
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
		File f = new File("plugins/DHEchoPetManager/Config.yml");
		File folder = new File("plugins/DHEchoPetManager");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/DHEchoPetManager/Config.yml");
		File folder = new File("plugins/DHEchoPetManager");
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
		File f = new File("plugins/DHEchoPetManager/Config.yml");
		File folder = new File("plugins/DHEchoPetManager");
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
		File f = new File("plugins/DHEchoPetManager/Config.yml");
		File folder = new File("plugins/DHEchoPetManager");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHEchoPetManager] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/DHEchoPetManager/Config.yml");
		File folder = new File("plugins/DHEchoPetManager");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHEchoPetManager] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/DHEchoPetManager/Config.yml");
		File folder = new File("plugins/DHEchoPetManager");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHEchoPetManager] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/DHEchoPetManager/Config.yml");
		File folder = new File("plugins/DHEchoPetManager");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHEchoPetManager] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
