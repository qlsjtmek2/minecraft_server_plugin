package me.espoo.BRB;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	public static void CreateConfig(File f, YamlConfiguration config) {
		try
		{
			f.createNewFile();
    		List<String> list1;
            list1 = config.getStringList("BanName");
			config.set("BanName", list1);
			
    		List<String> list2;
            list2 = config.getStringList("BanIp");
			config.set("BanIp", list2);
			
    		List<String> list3;
            list3 = config.getStringList("BanRange");
			config.set("BanRange", list3);
			
			config.save(f);
		 } catch (IOException ioex) {}
	}
	
	public static List<String> getBanName() {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		List<String> list;
		list = config.getStringList("BanName");
		return list;
	}
	
	public static List<String> getBanIp() {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		List<String> list;
		list = config.getStringList("BanIp");
		return list;
	}
	
	public static List<String> getBanRange() {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		List<String> list;
		list = config.getStringList("BanRange");
		return list;
	}
	
	public static void addBanName(String str) {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		try {
			List<String> list;
			list = config.getStringList("BanName");
			list.add(str);
			
			config.set("BanName", list);
			config.save(f);
		 } catch (IOException ioex) {}
	}
	
	public static void addBanIp(String str) {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		try {
			List<String> list;
			list = config.getStringList("BanIp");
			list.add(str);
			
			config.set("BanIp", list);
			config.save(f);
		 } catch (IOException ioex) {}
	}
	
	public static void addBanRange(String str) {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		try {
			List<String> list;
			list = config.getStringList("BanRange");
			list.add(str);
			
			config.set("BanRange", list);
			config.save(f);
		 } catch (IOException ioex) {}
	}
	
	public static void subBanName(String str) {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		try {
			List<String> list;
			list = config.getStringList("BanName");
			if (list.contains(str)) list.remove(str);
			
			config.set("BanName", list);
			config.save(f);
		 } catch (IOException ioex) {}
	}
	
	public static void subBanIp(String str) {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		try {
			List<String> list;
			list = config.getStringList("BanIp");
			if (list.contains(str)) list.remove(str);
			
			config.set("BanIp", list);
			config.save(f);
		 } catch (IOException ioex) {}
	}
	
	public static void subBanRange(String str) {
		String readFilePath = main.file;
		File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		try {
			List<String> list;
			list = config.getStringList("BanRange");
			if (list.contains(str)) list.remove(str);
			
			config.set("BanRange", list);
			config.save(f);
		 } catch (IOException ioex) {}
	}
	
	
}
