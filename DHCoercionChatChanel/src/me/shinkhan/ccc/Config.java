package me.shinkhan.ccc;

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
			
			List<String> list = config.getStringList("채팅소외모음");
            config.set("채팅소외모음", list);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHCoercionChatChanel] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/DHCoercionChatChanel/Config.yml");
		File folder = new File("plugins/DHCoercionChatChanel");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		
		if (list.isEmpty()) {
			return null;
		}
		
		return list;
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/DHCoercionChatChanel/Config.yml");
		File folder = new File("plugins/DHCoercionChatChanel");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHCoercionChatChanel] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
