package me.shinkhan.ccc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class API {
	public static void reloadConfig() {
		main.list = API.getList();
	}
	
	public static List<String> getList() {
		return Config.getList("채팅소외모음");
	}
	
	public static void setList(List<String> list) {
		Config.setList("채팅소외모음", list);
	}
	
	public static void addPlayer(String name) {
		List<String> list = getList();
		if (list == null || list.isEmpty()) {
			list = new ArrayList<String>();
			list.add(name);
			setList(list);
			return;
		}
		
		if (!list.contains(name)) list.add(name);
		setList(list);
	}
	
	public static void removePlayer(String name) {
		List<String> list = getList();
		if (list == null || list.isEmpty()) return;
		if (list.contains(name)) list.remove(name);
		setList(list);
	}
	
	public static Player getOnorOffLine(String s) {
		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all.getName().equals(s))
			{
				return all;
			}
		}
		return null;
	}
}
