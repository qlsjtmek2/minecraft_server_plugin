package me.shinkhan.fatigue;

import java.io.File;
import java.io.IOException;

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
			config.set("ÇÇ·Îµµ", 100);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[FatigueAPI] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString.equalsIgnoreCase("NONE")) return null;
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static double getInfoDouble(Player p, String name) {
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		double getDouble = config.getDouble(name);
		return getDouble;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[FatigueAPI] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[FatigueAPI] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoDouble(Player p, String name, double amount) {
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[FatigueAPI] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, String amount) {
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[FatigueAPI] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
