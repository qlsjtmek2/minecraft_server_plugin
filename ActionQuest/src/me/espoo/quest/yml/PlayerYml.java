package me.espoo.quest.yml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerYml {
	public static void CreatePlayerInfo(File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
            List<String> main = config.getStringList("메인 퀘스트.과제");
			config.set("메인 퀘스트.단계", 1);
			config.set("메인 퀘스트.진행", false);
			config.set("메인 퀘스트.과제", main);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getString(Player p, String name) {
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionQuest");
		File folder2 = new File("plugins/ActionQuest/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString == null) return null;
		return getString;
	}
	
	public static Boolean getBoolean(Player p, String name) {
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionQuest");
		File folder2 = new File("plugins/ActionQuest/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(Player p, String name) {
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionQuest");
		File folder2 = new File("plugins/ActionQuest/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionQuest");
		File folder2 = new File("plugins/ActionQuest/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(Player p, String name, int amount) {
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionQuest");
		File folder2 = new File("plugins/ActionQuest/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(Player p, String name, String amount) {
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionQuest");
		File folder2 = new File("plugins/ActionQuest/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(Player p, String name, List<String> amount) {
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionQuest");
		File folder2 = new File("plugins/ActionQuest/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
    
	public static List<String> getList(Player p, String name) {
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionQuest");
		File folder2 = new File("plugins/ActionQuest/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
		
		if (list == null) {
			return null;
		}
		
        for (String str : list) {
    		Returnlist.add(str);
        }
		return Returnlist;
	}
}
