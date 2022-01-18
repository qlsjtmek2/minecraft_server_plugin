package me.shinkhan.antibug.yml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class OnlyKRYml {
	public static void CreateOnlyKRYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> list = config.getStringList("허용 해외 아이피");
            config.set("허용 해외 아이피", list);
            List<String> list2 = config.getStringList("허용 오피계정 아이피");
            config.set("허용 오피계정 아이피", list2);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHAntiBug] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/DHAntiBug/OnlyKR.yml");
		File folder = new File("plugins/DHAntiBug");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateOnlyKRYml(f, folder, config);
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
		File f = new File("plugins/DHAntiBug/OnlyKR.yml");
		File folder = new File("plugins/DHAntiBug");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateOnlyKRYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHAntiBug] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
