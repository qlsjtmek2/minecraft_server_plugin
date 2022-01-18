package me.espoo.rpg.guild;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import me.espoo.rpg.Method;

public class GuildConfig {
	public static void CreateGuildConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			
			List<String> list;
            list = config.getStringList("길드 업그레이드");
            config.set("길드 창설 비용", 2000000);
            list.add("업그레이드단계 비용 최대수용유저 최대정회원수 최대디자이너수 최대스탭수 경험치스텟 농사스텟 채광스텟");
            list.add("0 0 5 2 1 1 2 2 2");
            list.add("1 100000 10 4 2 1 3 3 3");
            list.add("2 200000 20 10 4 2 4 4 4");
            list.add("3 400000 30 15 6 3 5 5 5");
            list.add("4 800000 45 25 8 4 6 6 6");
            list.add("5 1200000 60 35 10 5 8 8 8");
            list.add("6 2000000 80 50 10 6 10 10 10");
            list.add("7 3500000 100 75 10 7 12 12 12");
            list.add("8 5000000 120 90 10 8 15 15 15");
            list.add("9 8000000 150 120 10 9 18 18 18");
            list.add("10 10000000 200 150 10 10 20 20 20");
            config.set("길드 업그레이드", list);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getGuildList(String name) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildConfig(f, folder, config);
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
	
	public static Boolean getGuildBoolean(String name) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getGuildInt(String name) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getGuildString(String name) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = Method.replaceAllColors(config.getString(name));
		return getString;
	}
	
	public static String getGuildOrString(String name) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setGuildBoolean(String name, Boolean amount) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setGuildInt(String name, int amount) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setGuildString(String name, String amount) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setGuildList(String name, List<String> amount) {
		File f = new File("plugins/ActionGuildParty/GuildConfig.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
