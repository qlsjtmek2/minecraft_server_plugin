package me.espoo.rpg.exp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import me.espoo.rpg.Method;

public class ExpConfig {
	public static void CreateExpConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			
            List<String> list;
            List<String> list2;
            list = config.getStringList("보너스.테스트");
            list2 = config.getStringList("타입 목록");
            list2.add("테스트");
            config.set("경험치 이벤트", 100);
            config.set("경험치 이벤트 타이머", 0);
            config.set("보너스.테스트", list);
            config.set("타입 목록", list);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getExpList(String name) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateExpConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
		
		if (list == null) {
			return null;
		}
		
        for (String str : list) {
    		str = Method.replaceAllColors(str);
    		Returnlist.add(str);
        }
		return Returnlist;
	}
	
	public static Boolean getExpBoolean(String name) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateExpConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getExpInt(String name) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateExpConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getExpString(String name) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateExpConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = Method.replaceAllColors(config.getString(name));
		return getString;
	}
	
	public static String getExpOrString(String name) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateExpConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setExpBoolean(String name, Boolean amount) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setExpInt(String name, int amount) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setExpString(String name, String amount) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setExpList(String name, List<String> amount) {
		File f = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
