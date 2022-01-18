package me.shinkhan.plantpotal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	public static void CreateConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("��Ż ���", new ArrayList<String>());
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHPlantPotal] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void addPotal(String name)
	{
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
            List<String> list = config.getStringList(name + ".����");
			list.add("&f���� ������� ����ֽ��ϴ�.");
			list.add("&f�������� ��Ŭ�� �ϼż� �ٸ纸����.");
			config.set(name + ".�̸�", "&6" + name + "���� ��");
			config.set(name + ".�ڵ�", 2);
			config.set(name + ".������ �ڵ�", 0);
			config.set(name + ".�̵�", true);
			config.set(name + ".����", list);
			config.save(f);
			
			API.addPotalList(name);
		} catch (IOException ioex) {
			System.out.println("[DHPlantPotal] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void deletePotal(String name)
	{
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
            List<String> list = config.getStringList(name);
            list.clear();
			config.set(name, list);
			config.save(f);
			
			API.removePotalList(name);
		} catch (IOException ioex) {
			System.out.println("[DHPlantPotal] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
		
		if (list == null) {
			return null;
		}
		
        for (String str : list) {
    		str = API.replaceAllColors(str);
    		Returnlist.add(str);
        }
		return Returnlist;
	}
	
	public static Boolean getBoolean(String name) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getString(String name) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = API.replaceAllColors(config.getString(name));
		return getString;
	}
	
	public static String getOrString(String name) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setBoolean(String name, Boolean amount) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHPlantPotal] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHPlantPotal] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHPlantPotal] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/DHPlantPotal/Config.yml");
		File folder = new File("plugins/DHPlantPotal");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHPlantPotal] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
