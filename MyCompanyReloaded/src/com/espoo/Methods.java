package com.espoo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Methods {
	public static void addComList(Player p, String name)
	{
		File f = new File("plugins/MyCompany/CompanyList");
		File folder = new File("plugins/MyCompany");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Company.CreateComList(p, f, folder, config);
		}

        try {
    		List<String> list;
            list = config.getStringList("CompanyList");
            list.add(name);
            config.set("CompanyList", list);
			config.save(f);
		} catch (IOException e) {}
	}
	
	public static void remComList(Player p, String name)
	{
		File f = new File("plugins/MyCompany/CompanyList");
		File folder = new File("plugins/MyCompany");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Company.CreateComList(p, f, folder, config);
		}

        try {
    		List<String> list;
            list = config.getStringList("CompanyList");
    		for (int i = list.size(); i==0; i--) {
    			if(list.get(i) == name) {
    				list.remove(i);
    	            config.set("CompanyList", list);
    				config.save(f);
    			}
    		}
		} catch (IOException e) {}
	}
	
	public static boolean getComBoolean(Player p, String Name)
	{
		File f = new File("plugins/MyCompany/CompanyList.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<String> list;
        list = config.getStringList("CompanyList");
		for (int i = list.size(); i==0; i--) {
			if(list.get(i) == Name) {
				return true;
			}
		}
		return false;
	}
	
	public static Player getOnorOffLine(String s)
	{
		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all.getName().equals(s))
			{
				return all;
			}
		}
		return null;
	}
	
	public static void InvitePlayer(Player p, Player args, String cn)
	{
		UserData.SetStringData(args, "초대", cn);
		args.sendMessage("§c" + p.getName() + "§6 님이 §c" + cn + " §6회사 입사를 권유하셨습니다.");
		args.sendMessage("§6/회사 수락 §f- 가입 권유를 수락합니다.");
		args.sendMessage("§6/회사 거절 §f- 가입 권유를 거절합니다.");
	}
}
