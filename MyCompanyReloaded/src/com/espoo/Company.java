package com.espoo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Company {
	public static void CreateComList(Player p, File f, File folder, YamlConfiguration config)
	{
		try
		{
    		List<String> ComList;
    		ComList = config.getStringList("CompanyList");
			folder.mkdir();
			f.createNewFile();
	        config.set("CompanyList", ComList);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[MyCompany] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateComFile(String name, String Cname) throws IOException {
		File cf = new File("plugins/MyCompany/Company/" + Cname + ".yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		cof.set("이름", name);
		cof.set("월급", "500");
		cof.set("가입조건", "0");
		cof.set("설명", "NONE");
		cof.set("Motd", "NONE");
		cof.set("Level", "0");
		
		@SuppressWarnings("rawtypes")
		List ComList = new ArrayList();
        cof.set("User", ComList);
		cof.save(cf);
	}

	public static void addUserList(String name, String Cname) throws IOException {
		File f = new File("plugins/MyCompany/Company/" + Cname + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<String> list;
        list = config.getStringList("User");
		for (int i = list.size(); i==0; i--) {
			if(list.get(i) == name) {
				list.add("name");
				config.set("User", list);
				config.save(f);
			}
		}
	}
}
