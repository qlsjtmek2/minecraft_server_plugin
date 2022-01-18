package me.espoo.socket;

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
            List<String> list1 = config.getStringList("1.아이템 설명");
            List<String> list2 = config.getStringList("2.아이템 설명");
            List<String> list3 = config.getStringList("3.아이템 설명");
			config.set("1.아이템 이름", "NONE");
			config.set("1.아이템 설명", list1);
			config.set("1.아이템 코드", 0);
			config.set("2.아이템 이름", "NONE");
			config.set("2.아이템 설명", list2);
			config.set("2.아이템 코드", 0);
			config.set("3.아이템 이름", "NONE");
			config.set("3.아이템 설명", list3);
			config.set("3.아이템 코드", 0);
			config.save(f);
		 } catch (IOException ioex) {
		 }
	}
	
	public static List<String> getInfoList(Player p, String name) {
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionSocket");
		File folder2 = new File("plugins/ActionSocket/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		List<String> getList = config.getStringList(name);
		if (getList == null) return null;
		return getList;
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionSocket");
		File folder2 = new File("plugins/ActionSocket/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString.equalsIgnoreCase("NONE")) return null;
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionSocket");
		File folder2 = new File("plugins/ActionSocket/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionSocket");
		File folder2 = new File("plugins/ActionSocket/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoList(Player p, String name, List<String> amount) {
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionSocket");
		File folder2 = new File("plugins/ActionSocket/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
		}
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionSocket");
		File folder2 = new File("plugins/ActionSocket/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionSocket");
		File folder2 = new File("plugins/ActionSocket/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
		}
	}
	
	public static void setInfoString(Player p, String name, String amount) {
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionSocket");
		File folder2 = new File("plugins/ActionSocket/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
		}
	}
}
