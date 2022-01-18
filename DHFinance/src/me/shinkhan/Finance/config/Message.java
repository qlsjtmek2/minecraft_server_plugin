package me.shinkhan.Finance.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Message {
	public static void CreateMessageConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("버킷 경고 (색코드 X)", "버킷에선 실행이 불가능한 명령어 입니다.");
			config.set("대출 경고", "&c당신은 대출을 상환할 돈이 부족합니다.");
			config.set("예금 경고", "&c당신은 예금을 할 돈이 부족합니다.");
			config.set("투자 경고", "&c당신은 투자를 할 돈이 부족합니다.");
			config.set("대출 시작", "&c대출&6을 받으셨습니다. 꼭 제때 &c상환&6해주세요!");
			config.set("예금 시작", "&c예금&6을 하셨습니다. &c만기 시간&6까지 기다려 주세요.");
			config.set("투자 시작", "&c투자&6를 하셨습니다. &c만기 시간&6까지 기다려 주세요.");
			config.set("대출 종료", "&6대출 비용을 &c상환&6하셨습니다.");
			config.set("예금 종료", "&c예금&6한 비용에 &c이자&6를 붙혀 돌려받으셨습니다.");
			config.set("투자 종료", "&c투자&6한 비용에 &c이자&6를 붙혀 돌려받으셨습니다.");
			config.set("대출 상환 초과", "&6당신의 대출 상환 시간이 &c초과 &6되었습니다. &c추가 이자&6가 붙습니다.");
			config.set("예금 만기 완료", "&6당신의 예금 시간이 &c만기&6되었습니다. &e[ /금융 ] &6명령어로 찾아가주세요!");
			config.set("투자 만기 완료", "&6당신의 투자 시간이 &c만기&6되었습니다. &e[ /금융 ] &6명령어로 찾아가주세요!");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getMessage(String name) {
		File f = new File("plugins/DHFinance/Message.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMessageConfig(f, folder, config);
		}
		
		String Message = replaceAllColors(config.getString(name));
		return Message;
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
