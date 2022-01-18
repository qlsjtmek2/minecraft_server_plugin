package me.shinkhan.warning;

import java.io.File;
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
	
	public static boolean isFile(String name) {
		File f = new File("plugins/DHWarning/Player/" + name + ".yml");
		return f.exists();
	}
	
	public static boolean isWarning(String name) {
		return PlayerYml.getInt(name, "���") > 0;
	}
	
	public static void addWarning(String name, int num) {
		int i = PlayerYml.getInt(name, "���");
		i += num;
		PlayerYml.setInt(name, "���", i);
		BanCheck(name);
	}
	
	public static void subWarning(String name, int num) {
		int i = PlayerYml.getInt(name, "���");
		i -= num;
		if (i <= 0) PlayerYml.setInt(name, "���", 0);
		else PlayerYml.setInt(name, "���", i);
	}
	
	public static void setWarning(String name, int num) {
		if (num <= 0) PlayerYml.setInt(name, "���", 0);
		else PlayerYml.setInt(name, "���", num);
		BanCheck(name);
	}

	public static int getWarning(String name) {
		return PlayerYml.getInt(name, "���");
	}
	
	public static boolean isIP(String name) {
		return PlayerYml.getString(name, "IP") != null;
	}
	
	public static String getIP(String name) {
		if (isIP(name)) {
			return PlayerYml.getString(name, "IP");
		} return null;
	}
	
	public static void setIP(String name, String IP) {
		if (isIP(name)) PlayerYml.setString(name, "IP", IP);
	}
	
	public static String cleanIP(String IP) {
		IP = IP.replaceAll("/", "");
		return IP.split(":")[0];
	}
	
	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static void BanCheck(String name) {
		int amount = API.getWarning(name);
		
		if (amount == 4) {
			API.sendCommand("ban " + name + " ��c����� ��� 4�� �ʰ��Ǿ� ��Ǿ����ϴ�. ��f�� ��ü ���Ǵ� < http://cafe.naver.com/espooservers >");
			API.sendCommand("ban " + name + " ��c����� ��� 4�� �ʰ��Ǿ� ��Ǿ����ϴ�. ��f�� ��ü ���Ǵ� < http://cafe.naver.com/espooservers >");
			
			Player p = API.getOnorOffLine(name);
			if (p != null) {
				p.kickPlayer("��c����� ��� 4�� �ʰ��Ǿ� ��Ǿ����ϴ�.");
			} API.setWarning(name, 0);
		}
		
		else if (amount == 5) {
			API.sendCommand("ban " + name + " ��c����� ��� 5�� �ʰ��Ǿ� ������ ��Ǿ����ϴ�. ��f�� ��ü ���Ǵ� < http://cafe.naver.com/espooservers >");
			API.sendCommand("ban " + name + " ��c����� ��� 5�� �ʰ��Ǿ� ������ ��Ǿ����ϴ�. ��f�� ��ü ���Ǵ� < http://cafe.naver.com/espooservers >");
			Bukkit.banIP(API.getIP(name));
			
			Player p = API.getOnorOffLine(name);
			if (p != null) {
				p.kickPlayer("��c����� ��� 5�� �ʰ��Ǿ� ������ ��Ǿ����ϴ�.");
			} API.setWarning(name, 0);
		}
		
		else if (amount >= 6) {
			if (API.getIP(name) != null) {
				String[] IP = API.getIP(name).replace(".", "&").split("&");
				String IPstr = IP[0] + "." + IP[1] + "." + IP[2];
				API.sendCommand("rban ban " + IPstr);
				
				Player p = API.getOnorOffLine(name);
				if (p != null) {
					p.kickPlayer("��c����� ��� 6�� �ʰ��Ǿ� ������ ��Ǿ����ϴ�.");
				} API.setWarning(name, 0);
			}
		}
	}
	
	public static void sendCommand(String s) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	}
}
