package me.shinkhan.BarHealth;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	public static void CreateConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			
            config.set("보스바 표시", "§e[ <playername> ] §c<health>§6/§c<maxhealth>");
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHBarHealth] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getName(String name, String disname, int health, int maxhealth) {
		String str = main.barName;
		str = str.replaceAll("<playername>", name);
		str = str.replaceAll("<displayname>", disname);
		str = str.replaceAll("<health>", Integer.toString(health));
		str = str.replaceAll("<maxhealth>", Integer.toString(maxhealth));
		return str;
	}
	
	public static String getString(String name) {
		File f = new File("plugins/DHBarHealth/config.yml");
		File folder = new File("plugins/DHBarHealth");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/DHBarHealth/config.yml");
		File folder = new File("plugins/DHBarHealth");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHBarHealth] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
