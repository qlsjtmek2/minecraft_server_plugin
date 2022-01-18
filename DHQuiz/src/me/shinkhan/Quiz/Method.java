package me.shinkhan.Quiz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Method extends JavaPlugin {
	public static void Createconfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("유저 퀴즈 최소 비용", 50000);
			config.set("유저 퀴즈 최대 비용", 2000000);
			config.set("유저 퀴즈 차단", false);
			config.set("유저 퀴즈 시간 (초)", 180);
			config.set("유저 퀴즈 딜레이 (초)", 30);
			config.set("유저 퀴즈 쿨타임 (초)", 20);
			config.set("유저 퀴즈 출력 쿨타임 (초)", 20);
			config.set("수학 퀴즈 시간 (분)", 5);
			config.set("수학 퀴즈 랜덤 숫자", 50);
			config.set("수학 퀴즈 보상", 50000);
			config.set("수학 퀴즈 딜레이 (초)", 20);
			config.set("수학 퀴즈 차단", false);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHQuiz] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateBlackList(File f, File folder, YamlConfiguration config)
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
			 System.out.println("[DHQuiz] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static Boolean getConfigBoolean(String name) {
		File f = new File("plugins/DHQuiz/config.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getConfigInt(String name) {
		File f = new File("plugins/DHQuiz/config.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getConfigString(String name) {
		File f = new File("plugins/DHQuiz/config.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Createconfig(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setConfigBoolean(String name, Boolean amount) {
		File f = new File("plugins/DHQuiz/config.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				Createconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHQuiz] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigInt(String name, int amount) {
		File f = new File("plugins/DHQuiz/config.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				Createconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHQuiz] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigString(String name, String amount) {
		File f = new File("plugins/DHQuiz/config.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				Createconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHQuiz] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addBlackList(String name) {
		File f = new File("plugins/DHQuiz/BlackList.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateBlackList(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
			list.add(name);
            config.set("Player", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHQuiz] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subBlackList(String name) {
		File f = new File("plugins/DHQuiz/BlackList.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateBlackList(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
			if (list.contains(name)) list.remove(name);
            config.set("Player", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHQuiz] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void delBlackList() {
		File f = new File("plugins/DHQuiz/BlackList.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateBlackList(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
			list.clear();
            config.set("Player", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHQuiz] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static boolean isBlackList(String name) {
		File f = new File("plugins/DHQuiz/BlackList.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateBlackList(f, folder, config);
		}
			
		List<String> list;
		list = config.getStringList("Player");
		if (list.contains(name)) return true;
		else return false;
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
	
	public static boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
