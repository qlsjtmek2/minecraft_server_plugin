package me.shinkhan.ender;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Method {
	public static void Createconfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("스폰 타이머", 0);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHEnderSet] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static int getConfigInt() {
		File f = new File("plugins/DHEnderSet/config.yml");
		File folder = new File("plugins/DHEnderSet");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		int getInt = config.getInt("스폰 타이머");
		return getInt;
	}
	
	public static void setConfigInt(int amount) {
		File f = new File("plugins/DHEnderSet/config.yml");
		File folder = new File("plugins/DHEnderSet");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				Createconfig(f, folder, config);
			}
			
			config.set("스폰 타이머", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHEnderSet] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
