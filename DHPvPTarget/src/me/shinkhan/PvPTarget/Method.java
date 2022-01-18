package me.shinkhan.PvPTarget;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Method {
	public static void Createconfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
    		List<String> listW;
            listW = config.getStringList("비활성화 월드");
    		List<String> listC;
            listC = config.getStringList("명령어 목록");
            listW.add("world_nether");
            listC.add("/heal");
            listC.add("/help");
            listC.add("/list");
			config.set("전투 메세지 여부", true);
			config.set("전투 시작 메세지", "&6당신은 &c전투 모드&6가 되었습니다. &c로그아웃&6시 아이템을 모두 잃습니다.");
			config.set("전투 종료 메세지", "&6전투모드가 &c해제&6되었습니다.");
			config.set("입장 메세지", "&6당신은 전투중 &c퇴장&6하여 &c모든 아이템&6을 잃으셨습니다.");
			config.set("전투 퇴장 방송 여부", true);
			config.set("방송 메세지", "&f[&4알림&f] &c<playername> &6님이 전투중 &c퇴장&6하여 아이템을 잃으셨습니다!");
			config.set("전투 시간 (초)", 5);
			config.set("명령어 사용 금지", true);
			config.set("명령어 목록", listC);
			config.set("명령어 메세지", "&c전투중에는 명령어를 사용하실 수 없습니다.");
			config.set("비활성화 월드", listW);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[PvPTarget] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateQuitList(File f, File folder, YamlConfiguration config)
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
			 System.out.println("[PvPTarget] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static Boolean getConfigBoolean(String name) {
		File f = new File("plugins/DHPvPTarget/config.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getConfigInt(String name) {
		File f = new File("plugins/DHPvPTarget/config.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getConfigString(String name) {
		File f = new File("plugins/DHPvPTarget/config.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setConfigBoolean(String name, Boolean amount) {
		File f = new File("plugins/DHPvPTarget/config.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				Createconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PvPTarget] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigInt(String name, int amount) {
		File f = new File("plugins/DHPvPTarget/config.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				Createconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PvPTarget] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigString(String name, String amount) {
		File f = new File("plugins/DHPvPTarget/config.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				Createconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PvPTarget] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addQuitList(String name) {
		File f = new File("plugins/DHPvPTarget/Quitlist.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateQuitList(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
			list.add(name);
            config.set("Player", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PvPTarget] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subQuitList(String name) {
		File f = new File("plugins/DHPvPTarget/Quitlist.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateQuitList(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
			if (list.contains(name)) list.remove(name);
            config.set("Player", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PvPTarget] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static boolean isQuitList(String name) {
		File f = new File("plugins/DHPvPTarget/Quitlist.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateQuitList(f, folder, config);
		}
		
		List<String> list;
        list = config.getStringList("Player");
		if (list.contains(name)) return true;
		else return false;
	}
	
	public static List<String> getWorldList() {
		File f = new File("plugins/DHPvPTarget/config.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		List<String> list;
        list = config.getStringList("비활성화 월드");
		return list;
	}
	
	public static List<String> getCommandList() {
		File f = new File("plugins/DHPvPTarget/config.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		List<String> list;
        list = config.getStringList("명령어 목록");
		return list;
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
}
