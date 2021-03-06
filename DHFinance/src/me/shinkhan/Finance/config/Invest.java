package me.shinkhan.Finance.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Invest {
	public static void CreateInvestConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("알림문", "&a클릭시 예금 자금을 획득합니다.");
            config.set("default.이름", "&f현재 가능한 예금이 없습니다.");
    		List<String> list = config.getStringList("default.설명");
            list.add("&a당신은 모든 예금을 완료했습니다.");
            config.set("default.설명", list);
            config.set("default.아이템 코드", 339);
            config.set("default.아이템 데이터코드", 0);
            config.set("default.아이템 수량", 1);
            config.set("1.이름", "코리아증권예금");
            config.set("1.예금가능 금액", 100);
            config.set("1.만기 예금", 120);
            config.set("1.만기시간 (분)", 20);
            config.set("1.이율", "20%");
            config.set("2.이름", "동부파워증권예금");
            config.set("2.예금가능 금액", 150);
            config.set("2.만기 예금", 180);
            config.set("2.만기시간 (분)", 25);
            config.set("2.이율", "20%");
            config.set("3.이름", "BNK골드스코프증권예금");
            config.set("3.예금가능 금액", 200);
            config.set("3.만기 예금", 250);
            config.set("3.만기시간 (분)", 30);
            config.set("3.이율", "25%");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getLoreList(Player p, String name) {
		File f = new File("plugins/DHFinance/Invest.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateInvestConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = replaceAllColors(str);
    		Returnlist.add(str);
        } return Returnlist;
	}
	
	public static Boolean getInvestBoolean(String name) {
		File f = new File("plugins/DHFinance/Invest.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateInvestConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInvestInt(String name) {
		File f = new File("plugins/DHFinance/Invest.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateInvestConfig(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getInvestString(String name) {
		File f = new File("plugins/DHFinance/Invest.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateInvestConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = replaceAllColors(config.getString(name));
		return getString;
	}
	
	public static void setInvestBoolean(String name, Boolean amount) {
		File f = new File("plugins/DHFinance/Invest.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateInvestConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInvestInt(String name, int amount) {
		File f = new File("plugins/DHFinance/Invest.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateInvestConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInvestString(String name, String amount) {
		File f = new File("plugins/DHFinance/Invest.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateInvestConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
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
}
