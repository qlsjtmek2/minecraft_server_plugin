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
		UserData.SetStringData(args, "�ʴ�", cn);
		args.sendMessage("��c" + p.getName() + "��6 ���� ��c" + cn + " ��6ȸ�� �Ի縦 �����ϼ̽��ϴ�.");
		args.sendMessage("��6/ȸ�� ���� ��f- ���� ������ �����մϴ�.");
		args.sendMessage("��6/ȸ�� ���� ��f- ���� ������ �����մϴ�.");
	}
}
