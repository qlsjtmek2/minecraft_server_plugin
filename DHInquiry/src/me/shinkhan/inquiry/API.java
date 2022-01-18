package me.shinkhan.inquiry;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class API {
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
	
	public static boolean isInquiry(String name) {
		List<String> list = Config.getList("문의");
		if (list == null || list.isEmpty()) return false;
		for (String str : list) {
			if (str.split(",")[0].equalsIgnoreCase(name)) return true;
		} return false;
	}
	
	public static void addInquiry(String name, String message) {
		List<String> list = Config.getList("문의");
		if (list == null || list.isEmpty()) {
			List<String> list2 = new ArrayList<String>();
			list2.add(name + "," + message);
			Config.setList("문의", list2);
		} else {
			list.add(name + "," + message);
			Config.setList("문의", list);
		}
	}
	
	public static void removeInquiry(String name) {
		List<String> list = Config.getList("문의");
		if (list == null || list.isEmpty()) return;
		List<String> list2 = Config.getList("문의");
		int num = 0;
		for (String str : list2) {
			if (str.split(",")[0].equalsIgnoreCase(name)) list.remove(num);
			else num++;
		} Config.setList("문의", list);
	}

	public static String getInquiry(String name) {
		List<String> list = Config.getList("문의");
		if (list == null || list.isEmpty()) return null;
		for (String str : list) {
			if (str.split(",")[0].equalsIgnoreCase(name)) return str.split(",")[1];
		} return null;
	}

	public static void clearInquiry() {
		List<String> list = Config.getList("문의");
		if (list == null || list.isEmpty()) return;
		list.clear();
		Config.setList("문의", list);
	}
	
	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static String getFinalArg(String[] args, int start) {
		StringBuilder bldr = new StringBuilder();
	    for (int i = start; i < args.length; i++) {
	    	if (i != start) {
	    		bldr.append(" ");
	    	} bldr.append(args[i]);
	    } return bldr.toString();
	}
}
