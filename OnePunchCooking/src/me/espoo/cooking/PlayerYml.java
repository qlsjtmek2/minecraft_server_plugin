package me.espoo.cooking;

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
			config.set("만들고 있는 요리", "NONE");
			config.set("요리 코드", -1);
			config.set("남은 시간", -1);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchCooking] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/OnePunchCooking/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchCooking");
		File folder2 = new File("plugins/OnePunchCooking/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString.equalsIgnoreCase("NONE")) return null;
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/OnePunchCooking/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchCooking");
		File folder2 = new File("plugins/OnePunchCooking/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/OnePunchCooking/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchCooking");
		File folder2 = new File("plugins/OnePunchCooking/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/OnePunchCooking/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchCooking");
		File folder2 = new File("plugins/OnePunchCooking/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchCooking] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/OnePunchCooking/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchCooking");
		File folder2 = new File("plugins/OnePunchCooking/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchCooking] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, String amount) {
		File f = new File("plugins/OnePunchCooking/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchCooking");
		File folder2 = new File("plugins/OnePunchCooking/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchCooking] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
