package me.espoo.file;

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
			config.set("팔 근력", 0);
			config.set("복근", 0);
			config.set("다리 근력", 0);
            config.set("스피드", 0);
			config.set("팔 근력 스텟 적용", true);
			config.set("복근 스텟 적용", true);
			config.set("다리 근력 스텟 적용", true);
			config.set("스피드 스텟 적용", true);
			config.set("팔 근력 효과", 0);
			config.set("복근 효과", 0);
			config.set("다리 근력 효과", 0);
            config.set("스피드 효과", 0);
			config.set("팔 근력 지속시간", 0);
			config.set("복근 지속시간", 0);
			config.set("다리 근력 지속시간", 0);
            config.set("스피드 지속시간", 0);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchStat] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchStat] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchStat] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, Boolean amount) {
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchStat] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
