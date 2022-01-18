package me.espoo.banip;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Method {
	public static void CreateConfig(File f, YamlConfiguration config)
	{
		try {
			List<String> list;
			list = config.getStringList("BanList");
			f.createNewFile();
			config.set("BanList", list);
			config.save(f);
		} catch (IOException ioex) {}
	}
	
	public static void addBanList(String name) {
	    String readFilePath = "C:/Users/Ω≈»Ò∞Ô/Desktop/BanList.yml";
	    File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, config);
			}
			
			List<String> list;
			list = config.getStringList("BanList");
			list.add(name);
			config.set("BanList", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[BungeeBanIP] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subBanList(String name) {
	    String readFilePath = "C:/Users/Ω≈»Ò∞Ô/Desktop/BanList.yml";
	    File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, config);
			}
			
			List<String> list;
			list = config.getStringList("BanList");
			if (list.contains(name)) list.remove(name);
			config.set("BanList", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[BungeeBanIP] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static List<String> getBanList() {
	    String readFilePath = "C:/Users/Ω≈»Ò∞Ô/Desktop/BanList.yml";
	    File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, config);
		}
		
		List<String> list;
		list = config.getStringList("BanList");
		return list;
	}
}
