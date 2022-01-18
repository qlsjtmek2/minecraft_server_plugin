package me.espoo.quest.yml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class positionYml {
	public static void CreatePositionYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> list = config.getStringList("ÁÂÇ¥ ¸ñ·Ï");
            config.set("ÁÂÇ¥ ¸ñ·Ï", list);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchQuest] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/OnePunchQuest/position.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePositionYml(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
		
		if (list == null) {
			return null;
		}
		
        for (String str : list) {
    		Returnlist.add(str);
        }
		return Returnlist;
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/OnePunchQuest/position.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePositionYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
