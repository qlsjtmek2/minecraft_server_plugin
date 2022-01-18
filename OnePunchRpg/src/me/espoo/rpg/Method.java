package me.espoo.rpg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.PartyAPI;

public class Method {
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "��1");
        message = message.replaceAll("&2", "��2");
        message = message.replaceAll("&3", "��3");
        message = message.replaceAll("&4", "��4");
        message = message.replaceAll("&5", "��5");
        message = message.replaceAll("&6", "��6");
        message = message.replaceAll("&7", "��7");
        message = message.replaceAll("&8", "��8");
        message = message.replaceAll("&9", "��9");
        message = message.replaceAll("&0", "��0");
        message = message.replaceAll("&a", "��a");
        message = message.replaceAll("&b", "��b");
        message = message.replaceAll("&c", "��c");
        message = message.replaceAll("&d", "��d");
        message = message.replaceAll("&e", "��e");
        message = message.replaceAll("&f", "��f");
        message = message.replaceAll("&k", "��k");
        message = message.replaceAll("&l", "��l");
        message = message.replaceAll("&m", "��m");
        message = message.replaceAll("&n", "��n");
        message = message.replaceAll("&o", "��o");
        message = message.replaceAll("&r", "��r");
        return message;
	}
	
	public static boolean GuildNameAgree(String m) {
        if (m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || 
        	m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || 
        	m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || 
        	m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || 
        	m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || 
        	m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��") || m.contains("��")) {
        	return false;
        }
        
        else if (m.length() < 2 || m.length() > 6) {
        	return false;
        }
        
        else if (m.equalsIgnoreCase("���") || m.equalsIgnoreCase("����") || m.equalsIgnoreCase("����") || m.equalsIgnoreCase("�ù�") || 
        		 m.equalsIgnoreCase("����") || m.equalsIgnoreCase("������")) {
        	return false;
        }
        return true;
	}
	
	public static String getFinalArg(String[] args, int start)
	{
		StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			if (i != start) {
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}
	
	public static String getFinalArgs(String[] args, int start)
	{
		StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			if (i != start) {
				bldr.append(", ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
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
	
	public static String roundDot1Off(String str) {
		float fTemp = (Float.parseFloat(str));
		DecimalFormat format = new DecimalFormat("#.#");
		String rtnStr = format.format(fTemp);
		return rtnStr;
	}
	
	public static String NextChatMode(Player p, String mode)
	{
		String str = null;
		
		switch (mode) {
			case "��ü":
				if (PartyAPI.getJoinParty(p) != null) str = "��Ƽ";
				else if (GuildAPI.getJoinGuild(p) != null) str = "���";
				break;
				
			case "��Ƽ":
				if (GuildAPI.getJoinGuild(p) != null) str = "���";
				else str = "��ü";
				break;
				
			case "���":
				str = "��ü";
				break;
		}
		
		if (str == null) {
			return null;
		} else {
			return str;
		}
	}
}
