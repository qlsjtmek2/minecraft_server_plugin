package me.shinkhan.userguide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class API {
	public static String searchOnlinePlayer(String target) {
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < 16; i++) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
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
	
	public static boolean isUserGuide(String name) {
		List<String> list = getUserGuide();
		if (list == null || list.isEmpty()) return false;
		if (list.contains(name)) return true;
		return false;
	}
	
	public static List<String> getUserGuide() {
		List<String> list = Config.getList("목록");
		return list;
	}
	
	public static void addUserGuide(String name) {
		List<String> list = getUserGuide();
		if (list == null || list.isEmpty()) list = new ArrayList<String>();
		list.add(name);
		Config.setList("목록", list);
	}
	
	public static void removeUserGuide(String name) {
		List<String> list = getUserGuide();
		if (list == null || list.isEmpty()) return;
		list.remove(name);
		Config.setList("목록", list);
	}
}
