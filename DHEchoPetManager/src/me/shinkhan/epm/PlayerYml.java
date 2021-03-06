package me.shinkhan.epm;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerYml {
	public static void CreateInfo(File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("?? Ȱ??ȭ ????", true);
			config.set("?? ?̸?", null);
			config.set("???????? ??", null);
			config.set("?? ????", Arrays.asList());
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHEchoPetManager] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(Player p, String name) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateInfo(f, folder, folder2, config);
		}
		
		List<String> getList = config.getStringList(name);
		if (getList == null) return null;
		return getList;
	}
	
	public static String getString(Player p, String name) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString == null) return null;
		return getString;
	}
	
	public static Boolean getBoolean(Player p, String name) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(Player p, String name) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setList(Player p, String name, List<String> amount) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateInfo(f, folder, folder2, config);
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
	
	public static void setBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateInfo(f, folder, folder2, config);
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
	
	public static void setInt(Player p, String name, int amount) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateInfo(f, folder, folder2, config);
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
	
	public static void setString(Player p, String name, String amount) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateInfo(f, folder, folder2, config);
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
