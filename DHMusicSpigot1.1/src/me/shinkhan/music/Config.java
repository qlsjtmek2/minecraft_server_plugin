package me.shinkhan.music;

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
			
			List<String> list = config.getStringList("아이템.설명");
            List<String> list2 = config.getStringList("오피 도움말.설명");
            List<String> list3 = config.getStringList("Stop 아이템.설명");
            list.add("§7클릭시 음악을 재생합니다.");
            list2.add("§6/음악 <all/playername> <노래이름> §f- 음악을 재생합니다.");
            list3.add("§7클릭시 음악을 정지합니다.");
            config.set("아이템.설명", list);
            config.set("아이템.코드", 84);
            config.set("아이템.데이터 코드", 0);
            config.set("Stop 아이템.설명", list3);
            config.set("Stop 아이템.코드", 77);
            config.set("Stop 아이템.데이터 코드", 0);
            config.set("오피 도움말.설명", list2);
            config.set("오피 도움말.이름", "§6§l오피 명령어");
            config.set("오피 도움말.코드", 340);
            config.set("오피 도움말.데이터 코드", 0);
            config.set("타입 아이템.코드", 143);
            config.set("타입 아이템.데이터 코드", 0);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		
		if (list == null) {
			return null;
		}
		
		return list;
	}
	
	public static Boolean getBoolean(String name) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
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
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
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
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/DHMusic/config.yml");
		File folder = new File("plugins/DHMusic");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHMusic] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
