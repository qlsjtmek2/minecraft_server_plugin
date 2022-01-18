package me.shinkhan.BuyHouse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Method {
	public static void CreateConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("집 구매 입력", "[집구매]");
			config.set("창고 구매 입력", "[창고구매]");
			config.set("모루 구매 입력", "[모루구매]");
			config.set("인첸트 구매 입력", "[인첸트구매]");
			config.set("집 구매 설정", "[&a집구매&r]");
			config.set("창고 구매 설정", "[§6창고구매§r]");
			config.set("모루 구매 설정", "[§9모루구매§r]");
			config.set("인첸트 구매 설정", "[§3인첸트구매§r]");
			config.set("개인당 최대 집 구매 개수", -1);
			config.set("개인당 최대 창고 구매 개수", -1);
			config.set("개인당 최대 모루 구매 개수", -1);
			config.set("개인당 최대 인첸트 구매 개수", -1);
			config.set("Lockette 표지판", "[잠금]");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHBuyHouse] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreatePlayerInfo(Player p, File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("집 개수", 0);
			config.set("창고 개수", 0);
			config.set("모루 개수", 0);
			config.set("인첸트 개수", 0);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHBuyHouse] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static Boolean getConfigBoolean(String name) {
		File f = new File("plugins/DHBuyHouse/config.yml");
		File folder = new File("plugins/DHBuyHouse");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getConfigInt(String name) {
		File f = new File("plugins/DHBuyHouse/config.yml");
		File folder = new File("plugins/DHBuyHouse");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getConfigString(String name) {
		File f = new File("plugins/DHBuyHouse/config.yml");
		File folder = new File("plugins/DHBuyHouse");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		String getString = replaceAllColors(config.getString(name));
		return getString;
	}
	
	public static void setConfigBoolean(String name, Boolean amount) {
		File f = new File("plugins/DHBuyHouse/config.yml");
		File folder = new File("plugins/DHBuyHouse");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHBuyHouse] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigInt(String name, int amount) {
		File f = new File("plugins/DHBuyHouse/config.yml");
		File folder = new File("plugins/DHBuyHouse");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHBuyHouse] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigString(String name, String amount) {
		File f = new File("plugins/DHBuyHouse/config.yml");
		File folder = new File("plugins/DHBuyHouse");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHBuyHouse] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/DHBuyHouse/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHBuyHouse");
		File folder2 = new File("plugins/DHBuyHouse/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}

	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/DHBuyHouse/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHBuyHouse");
		File folder2 = new File("plugins/DHBuyHouse/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHBuyHouse] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "§1");
        message = message.replaceAll("&2", "§2");
        message = message.replaceAll("&3", "§3");
        message = message.replaceAll("&4", "§4");
        message = message.replaceAll("&5", "§5");
        message = message.replaceAll("&6", "§6");
        message = message.replaceAll("&7", "§7");
        message = message.replaceAll("&8", "§8");
        message = message.replaceAll("&9", "§9");
        message = message.replaceAll("&0", "§0");
        message = message.replaceAll("&a", "§a");
        message = message.replaceAll("&b", "§b");
        message = message.replaceAll("&c", "§c");
        message = message.replaceAll("&d", "§d");
        message = message.replaceAll("&e", "§e");
        message = message.replaceAll("&f", "§f");
        message = message.replaceAll("&k", "§k");
        message = message.replaceAll("&l", "§l");
        message = message.replaceAll("&m", "§m");
        message = message.replaceAll("&n", "§n");
        message = message.replaceAll("&o", "§o");
        message = message.replaceAll("&r", "§r");
        return message;
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
	
	public static void HelpOpMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + " ---- " + ChatColor.GOLD + "자동구매 " + ChatColor.YELLOW + "-- " + ChatColor.GOLD + "페이지 " + ChatColor.RED + "1" + ChatColor.GOLD + "/" + ChatColor.RED + "1 " + ChatColor.YELLOW + "----");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 " + ChatColor.WHITE + "- 자동구매 도움말을 봅니다.");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 보기  <모두|집|창고|모루|인첸트>" + ChatColor.WHITE + "- 내 집 개수를 확인합니다.");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 초기화 모두 <닉네임> " + ChatColor.WHITE + "- 플레이어의 모든 구매 개수를 초기화 시킵니다.");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 초기화 <집|창고|모루|인첸트> <닉네임>" + ChatColor.WHITE + "- 플레이어의 설정을 초기화 시킵니다.");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 <설정|추가|빼기> <집|창고|모루|인첸트> <닉네임> <값>" + ChatColor.WHITE + "- 플레이어의 집, 창고, 모루, 인첸트 값을 설정합니다.");
	}
	
	public static void HelpConMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + " ---- " + ChatColor.GOLD + "자동구매 " + ChatColor.YELLOW + "-- " + ChatColor.GOLD + "페이지 " + ChatColor.RED + "1" + ChatColor.GOLD + "/" + ChatColor.RED + "1 " + ChatColor.YELLOW + "----");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 " + ChatColor.WHITE + "- 자동구매 도움말을 봅니다.");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 초기화 모두 <닉네임> " + ChatColor.WHITE + "- 플레이어의 모든 구매 개수를 초기화 시킵니다.");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 초기화 <집|창고|모루|인첸트> <닉네임>" + ChatColor.WHITE + "- 플레이어의 설정을 초기화 시킵니다.");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 <설정|추가|빼기> <집|창고|모루|인첸트> <닉네임> <값>" + ChatColor.WHITE + "- 플레이어의 집, 창고, 모루, 인첸트 값을 설정합니다.");
	}
	
	public static void HelpUserMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + " ---- " + ChatColor.GOLD + "자동구매 " + ChatColor.YELLOW + "-- " + ChatColor.GOLD + "페이지 " + ChatColor.RED + "1" + ChatColor.GOLD + "/" + ChatColor.RED + "1 " + ChatColor.YELLOW + "----");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 " + ChatColor.WHITE + "- 자동구매 도움말을 봅니다.");
		sender.sendMessage(ChatColor.GOLD + "/자동구매 보기  <모두|집|창고|모루|인첸트>" + ChatColor.WHITE + "- 내 집 개수를 확인합니다.");
	}
}
