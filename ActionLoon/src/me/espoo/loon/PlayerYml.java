package me.espoo.loon;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
			config.set("룬.아이템 이름", "NONE");
			config.set("룬.아이템 설명", Arrays.asList());
			config.set("룬.아이템 코드", 0);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionLoon] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getInfoList(Player p, String name) {
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionLoon");
		File folder2 = new File("plugins/ActionLoon/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		List<String> getList = config.getStringList(name);
		if (getList == null) return null;
		return getList;
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionLoon");
		File folder2 = new File("plugins/ActionLoon/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString.equalsIgnoreCase("NONE")) return null;
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionLoon");
		File folder2 = new File("plugins/ActionLoon/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionLoon");
		File folder2 = new File("plugins/ActionLoon/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoList(Player p, String name, List<String> amount) {
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionLoon");
		File folder2 = new File("plugins/ActionLoon/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionLoon] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionLoon");
		File folder2 = new File("plugins/ActionLoon/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionLoon] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionLoon");
		File folder2 = new File("plugins/ActionLoon/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionLoon] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, String amount) {
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionLoon");
		File folder2 = new File("plugins/ActionLoon/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionLoon] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
