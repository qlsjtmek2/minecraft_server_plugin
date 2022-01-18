package me.shinkhan.warning;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerYml {
	public static void CreatePlayerInfo(File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("°æ°í", 0);
			config.set("IP", 0);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHWarning] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getString(String user, String name) {
		File f = new File("plugins/DHWarning/Player/" + user + ".yml");
		File folder = new File("plugins/DHWarning");
		File folder2 = new File("plugins/DHWarning/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString.equalsIgnoreCase("NONE")) return null;
		return getString;
	}
	
	public static Boolean getBoolean(String user, String name) {
		File f = new File("plugins/DHWarning/Player/" + user + ".yml");
		File folder = new File("plugins/DHWarning");
		File folder2 = new File("plugins/DHWarning/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String user, String name) {
		File f = new File("plugins/DHWarning/Player/" + user + ".yml");
		File folder = new File("plugins/DHWarning");
		File folder2 = new File("plugins/DHWarning/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setBoolean(String user, String name, Boolean amount) {
		File f = new File("plugins/DHWarning/Player/" + user + ".yml");
		File folder = new File("plugins/DHWarning");
		File folder2 = new File("plugins/DHWarning/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHWarning] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String user, String name, int amount) {
		File f = new File("plugins/DHWarning/Player/" + user + ".yml");
		File folder = new File("plugins/DHWarning");
		File folder2 = new File("plugins/DHWarning/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHWarning] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String user, String name, String amount) {
		File f = new File("plugins/DHWarning/Player/" + user + ".yml");
		File folder = new File("plugins/DHWarning");
		File folder2 = new File("plugins/DHWarning/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHWarning] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
