package me.espoo.option;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerYml {
	public static void CreatePlayerInfo(File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("아이템 버리기", false);
			config.set("채팅 보기", true);
			config.set("플레이어 숨기기", false);
			config.set("정보 공개 여부", true);
			config.set("상태 공개 여부", true);
			config.set("효과음", true);
			config.set("귓속말 거부", false);
			config.set("파티 초대", true);
			config.set("길드 초대", true);
			config.set("대련 신청", true);
			config.set("교환 신청", true);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionOption] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/ActionOption/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionOption");
		File folder2 = new File("plugins/ActionOption/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString.equalsIgnoreCase("NONE")) return null;
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/ActionOption/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionOption");
		File folder2 = new File("plugins/ActionOption/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/ActionOption/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionOption");
		File folder2 = new File("plugins/ActionOption/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/ActionOption/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionOption");
		File folder2 = new File("plugins/ActionOption/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionOption] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/ActionOption/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionOption");
		File folder2 = new File("plugins/ActionOption/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionOption] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, String amount) {
		File f = new File("plugins/ActionOption/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionOption");
		File folder2 = new File("plugins/ActionOption/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionOption] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
