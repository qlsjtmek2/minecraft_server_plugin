package me.espoo.seteffect;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class ListFile {
	public static void CreateListFile(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
    		List<String> list;
            list = config.getStringList("List");
            config.set("List", list);
            config.save(f);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchSetEffect] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList() {
		File f = new File("plugins/OnePunchSetEffect/List.yml");
		File folder = new File("plugins/OnePunchSetEffect");
		YamlConfiguration List = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateListFile(f, folder, List);
		}
		
		List<String> getList = List.getStringList("List");
		return getList;
	}
	
	public static void addList(String name) {
		File f = new File("plugins/OnePunchSetEffect/List.yml");
		File folder = new File("plugins/OnePunchSetEffect");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateListFile(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("List");
			folder.mkdir();
			f.createNewFile();
			list.add(name);
            config.set("List", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchSetEffect] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subList(String name) {
		File f = new File("plugins/OnePunchSetEffect/List.yml");
		File folder = new File("plugins/OnePunchSetEffect");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateListFile(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("List");
			folder.mkdir();
			f.createNewFile();
			if (list.contains(name)) list.remove(name);
            config.set("List", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchSetEffect] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void delList() {
		File f = new File("plugins/OnePunchSetEffect/List.yml");
		File folder = new File("plugins/OnePunchSetEffect");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateListFile(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("List");
			folder.mkdir();
			f.createNewFile();
			list.clear();
            config.set("List", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchSetEffect] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
