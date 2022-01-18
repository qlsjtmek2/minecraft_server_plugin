package me.espoo.pet;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Methods extends JavaPlugin {
	public static void CreatePlayerInfo(Player p, File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("havepet", false);
			config.set("defaultpet", false);
			config.set("petsheep", false);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[EchoPetManager] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static boolean getBooleanYml(Player p, String name) {
		File f = new File("plugins/EchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/EchoPetManager");
		File folder2 = new File("plugins/EchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		boolean Ymlname = config.getBoolean(name);
		return Ymlname;
	}
	
	public static void setBooleanYml(Player p, String name, Boolean amount) {
		File f = new File("plugins/EchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/EchoPetManager");
		File folder2 = new File("plugins/EchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		 } catch (IOException ioex) {
			 System.out.println("[EchoPetManager] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
}
