package me.shinkhan.Streng;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Method {
	public static void CreatePlayerInfo(Player p, File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("강화 기회", 0);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHStrengthen] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static int getIntYml(Player p, String name) {
		File f = new File("plugins/DHStrengthen/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHStrengthen");
		File folder2 = new File("plugins/DHStrengthen/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int Ymlname = config.getInt(name);
		return Ymlname;
	}
	
	public static void setIntYml(Player p, String name, int amount) {
		File f = new File("plugins/DHStrengthen/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHStrengthen");
		File folder2 = new File("plugins/DHStrengthen/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		 } catch (IOException ioex) {
			 System.out.println("[DHStrengthen] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
}
