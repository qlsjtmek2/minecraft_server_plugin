package me.shinkhan.Finance.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerYml extends JavaPlugin {
	public static void CreatePlayerInfo(File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("대출값", 0);
			config.set("투자값", 0);
            config.set("예금값", 0);
			config.set("대출 타이머", 0);
			config.set("투자 타이머", 0);
            config.set("예금 타이머", 0);
			config.set("대출 완료", false);
			config.set("투자 완료", false);
            config.set("예금 완료", false);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/DHFinance/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHFinance");
		File folder2 = new File("plugins/DHFinance/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/DHFinance/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHFinance");
		File folder2 = new File("plugins/DHFinance/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/DHFinance/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHFinance");
		File folder2 = new File("plugins/DHFinance/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/DHFinance/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHFinance");
		File folder2 = new File("plugins/DHFinance/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/DHFinance/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHFinance");
		File folder2 = new File("plugins/DHFinance/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, Boolean amount) {
		File f = new File("plugins/DHFinance/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHFinance");
		File folder2 = new File("plugins/DHFinance/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
