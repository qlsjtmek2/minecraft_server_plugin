package me.espoo.quest.yml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import me.espoo.quest.API;

public class subYml {
	public static void CreateSubYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> reward = config.getStringList("이곳에다 NPC 이름을 적으세요.1.보상");
            List<String> task = config.getStringList("이곳에다 NPC 이름을 적으세요.1.과제");
            List<String> content = config.getStringList("이곳에다 NPC 이름을 적으세요.1.내용");
            List<String> tip = config.getStringList("이곳에다 NPC 이름을 적으세요.1.내용");
            
            reward.add("rpgitem RPG아이템이름,수량");
            reward.add("give 아이템코드:아이템데이터코드,수량");
            reward.add("money 돈");
            reward.add("item 아이템코드:아이템데이터코드,수량,아이템이름,아이템설명");
            reward.add("command 명령어");
            reward.add("commandop 명령어");
            reward.add("commandcon 명령어");
            reward.add("exp 점수");
            reward.add("score 점수");

            task.add("epicboss 에픽보스이름,킬수");
            task.add("talk NPC이름");
            task.add("rpgitem RPG아이템이름,수량 true");
            task.add("item 아이템코드:아이템데이터코드,수량,아이템이름,아이템설명 true");
            task.add("command 명령어");
            task.add("chat 채팅내용");
            task.add("playerkill 수량");
            task.add("monsterkill 수량");
            
            content.add("<player>│이곳에다 대화 내용을 적으세요");
            content.add("<npc>│이곳에다 대화 내용을 적으세요");
            
            tip.add("이곳에다 퀘스트 팁을 적으세요");
            
            config.set("이곳에다 NPC 이름을 적으세요.1.이름", "여기다가 퀘스트 이름을 적으세요");
            config.set("이곳에다 NPC 이름을 적으세요.1.등급", "<일반/레어/에픽/긴급>");
            config.set("이곳에다 NPC 이름을 적으세요.1.좌표", "X,Y,Z,World,Yaw,Pitch");
            config.set("이곳에다 NPC 이름을 적으세요.1.팁", tip);
            config.set("이곳에다 NPC 이름을 적으세요.1.보상", reward);
            config.set("이곳에다 NPC 이름을 적으세요.1.과제", task);
            config.set("이곳에다 NPC 이름을 적으세요.1.스킵", true);
            config.set("이곳에다 NPC 이름을 적으세요.1.내용", content);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchQuest] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/OnePunchQuest/sub.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateSubYml(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
		
		if (list == null) {
			return null;
		}
		
        for (String str : list) {
    		str = API.replaceAllColors(str);
    		Returnlist.add(str);
        }
		return Returnlist;
	}
	
	public static Boolean getBoolean(String name) {
		File f = new File("plugins/OnePunchQuest/sub.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateSubYml(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/OnePunchQuest/sub.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateSubYml(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getString(String name) {
		File f = new File("plugins/OnePunchQuest/sub.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateSubYml(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setBoolean(String name, Boolean amount) {
		File f = new File("plugins/OnePunchQuest/sub.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateSubYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/OnePunchQuest/sub.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateSubYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/OnePunchQuest/sub.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateSubYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/OnePunchQuest/sub.yml");
		File folder = new File("plugins/OnePunchQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateSubYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
