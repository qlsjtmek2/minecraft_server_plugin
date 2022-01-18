package me.espoo.dunzeon;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	public static void CreateConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			
            config.set("괴인 협회 대기실 1", "0,0,0,world,0,0");
            config.set("괴인 협회 대기실 2", "0,0,0,world,0,0");
            config.set("1라운드.첫번째 좌표", "0,0,0,world,0,0");
            config.set("1라운드.두번째 좌표", "0,0,0,world,0,0");
            config.set("1라운드.세번째 좌표", "0,0,0,world,0,0");
            config.set("1라운드.네번째 좌표", "0,0,0,world,0,0");
            config.set("2라운드.첫번째 좌표", "0,0,0,world,0,0");
            config.set("2라운드.두번째 좌표", "0,0,0,world,0,0");
            config.set("2라운드.세번째 좌표", "0,0,0,world,0,0");
            config.set("2라운드.네번째 좌표", "0,0,0,world,0,0");
            config.set("3라운드.첫번째 좌표", "0,0,0,world,0,0");
            config.set("3라운드.두번째 좌표", "0,0,0,world,0,0");
            config.set("3라운드.세번째 좌표", "0,0,0,world,0,0");
            config.set("3라운드.네번째 좌표", "0,0,0,world,0,0");
            config.set("4라운드.첫번째 좌표", "0,0,0,world,0,0");
            config.set("4라운드.두번째 좌표", "0,0,0,world,0,0");
            config.set("4라운드.세번째 좌표", "0,0,0,world,0,0");
            config.set("4라운드.네번째 좌표", "0,0,0,world,0,0");
            config.set("1라운트.타이머", 50);
            config.set("2라운트.타이머", 80);
            config.set("3라운트.타이머", 120);
            config.set("4라운트.타이머", 200);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchDunzeon] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/OnePunchDunzeon/Config.yml");
		File folder = new File("plugins/OnePunchDunzeon");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		Returnlist.add(str);
        } return Returnlist;
	}
	
	public static Boolean getBoolean(String name) {
		File f = new File("plugins/OnePunchDunzeon/Config.yml");
		File folder = new File("plugins/OnePunchDunzeon");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/OnePunchDunzeon/Config.yml");
		File folder = new File("plugins/OnePunchDunzeon");
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
		File f = new File("plugins/OnePunchDunzeon/Config.yml");
		File folder = new File("plugins/OnePunchDunzeon");
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
		File f = new File("plugins/OnePunchDunzeon/Config.yml");
		File folder = new File("plugins/OnePunchDunzeon");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchDunzeon] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/OnePunchDunzeon/Config.yml");
		File folder = new File("plugins/OnePunchDunzeon");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchDunzeon] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/OnePunchDunzeon/Config.yml");
		File folder = new File("plugins/OnePunchDunzeon");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchDunzeon] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
