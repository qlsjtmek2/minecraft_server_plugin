package me.espoo.rpg2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Method extends JavaPlugin {
	public static void removeOP(Player p, boolean is) {
		if (!is) p.getPlayer().setOp(false);
	}
	
	public static void sendCommand(String s) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	}
	
	public static void PlayerManuadd(String p, String name) {
		for (World world : Bukkit.getWorlds()) {
			String n = world.getName();
			if (n.equalsIgnoreCase("world_the_end") || n.equalsIgnoreCase("world_nether")) continue;
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p + " " + name + " " + n);
		}
	}
	
	public static void CreatePlayerInfo(Player p, File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("튜토리얼", false);
			config.set("만렙", false);
			config.set("초기화", false);
			config.set("전직", "NONE");
			config.set("전직 카운트", 0);
			config.save(f);
		} catch (IOException ioex) {
			System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================"); 
		}
	}
	
	public static void CreateConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("리로드", false);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateOpList(File f, File folder, YamlConfiguration config)
	{
		try
		{
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
            config.set("Player", list);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getOpList() {
		File f = new File("plugins/ActionRPG/OpList.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		return config.getStringList("Player");
	}
	
	public static void addOpList(String str) {
		File f = new File("plugins/ActionRPG/OpList.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList("강제 해제 당한 오피 목록");
		if (list == null) list = new ArrayList<String>();
		list.add(str);
		config.set("강제 해제 당한 오피 목록", list);
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/ActionRPG/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionRPG");
		File folder2 = new File("plugins/ActionRPG/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/ActionRPG/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionRPG");
		File folder2 = new File("plugins/ActionRPG/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/ActionRPG/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionRPG");
		File folder2 = new File("plugins/ActionRPG/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/ActionRPG/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionRPG");
		File folder2 = new File("plugins/ActionRPG/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/ActionRPG/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionRPG");
		File folder2 = new File("plugins/ActionRPG/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, String string) {
		File f = new File("plugins/ActionRPG/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionRPG");
		File folder2 = new File("plugins/ActionRPG/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, string);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static Boolean getConfigBoolean(String name) {
		File f = new File("plugins/ActionRPG/config.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getConfigInt(String name) {
		File f = new File("plugins/ActionRPG/config.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getConfigString(String name) {
		File f = new File("plugins/ActionRPG/config.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setConfigBoolean(String name, Boolean amount) {
		File f = new File("plugins/ActionRPG/config.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigInt(String name, int amount) {
		File f = new File("plugins/ActionRPG/config.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigString(String name, String amount) {
		File f = new File("plugins/ActionRPG/config.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionRPG] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
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
	
	public static void addPotion(Player p, PotionEffectType pe, int delay, int amount) {
		 p.addPotionEffect(new PotionEffect(pe, delay * 20, amount, true));
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cast forcecast " + p.getName() + " lvup");
	}
	
	public static String Cutter(String Rank)
	{
		String[] Cut;
		Cut = Rank.split("7");
		Cut = Rank.split("e");
		Cut = Rank.split("6");
		Cut = Rank.split("b");
		Cut = Rank.split("d");
		Cut = Rank.split("c");
		Cut = Rank.split("4");
		return Cut[1];
	}
}
