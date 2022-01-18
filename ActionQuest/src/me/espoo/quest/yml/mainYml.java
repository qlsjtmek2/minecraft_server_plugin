package me.espoo.quest.yml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import me.espoo.quest.API;

public class mainYml {
	public static void CreateMainYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> reward = config.getStringList("1.보상");
            List<String> task = config.getStringList("1.과제");
            List<String> content = config.getStringList("1.내용");
            List<String> tip = config.getStringList("1.팁");
            
            reward.add("rpgitem RPG아이템이름,수량");
            reward.add("give 아이템코드:아이템데이터코드,수량");
            reward.add("money 돈");
            reward.add("item 아이템코드:아이템데이터코드,수량,아이템이름,아이템설명");
            reward.add("command 명령어");
            reward.add("commandop 명령어");
            reward.add("commandcon 명령어");
            reward.add("exp 점수");

            task.add("epicboss 에픽보스이름,킬수");
            task.add("talk NPC이름 <player>│이곳에다_대화_내용을_적으세요,<npc>│이곳에다_대화_내용을_적으세요");
            task.add("rpgitem RPG아이템이름,수량 true(완료시 아이템을 뺐을건지 아닌지)");
            task.add("item 아이템코드:아이템데이터코드,수량,아이템이름 true(완료시 아이템을 뺐을건지 아닌지)");
            task.add("command 명령어");
            task.add("chat 채팅내용");
            task.add("playerkill 수량");
            task.add("monsterkill 수량");
            
            content.add("<player>│이곳에다 대화 내용을 적으세요");
            content.add("<npc>│이곳에다 대화 내용을 적으세요");
            
            tip.add("이곳에다 퀘스트 팁을 적으세요");
            
            config.set("1.NPC", "여기다가 NPC 이름을 적으세요");
            config.set("1.이름", "여기다가 퀘스트 이름을 적으세요");
            config.set("1.좌표", "X,Y,Z,World,Yaw,Pitch");
            config.set("1.팁", tip);
            config.set("1.보상", reward);
            config.set("1.과제", task);
            config.set("1.스킵", false);
            config.set("1.내용", content);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getList(String name) {
		File f = new File("plugins/ActionQuest/main.yml");
		File folder = new File("plugins/ActionQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainYml(f, folder, config);
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
		File f = new File("plugins/ActionQuest/main.yml");
		File folder = new File("plugins/ActionQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainYml(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/ActionQuest/main.yml");
		File folder = new File("plugins/ActionQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainYml(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getString(String name) {
		File f = new File("plugins/ActionQuest/main.yml");
		File folder = new File("plugins/ActionQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainYml(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setBoolean(String name, Boolean amount) {
		File f = new File("plugins/ActionQuest/main.yml");
		File folder = new File("plugins/ActionQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/ActionQuest/main.yml");
		File folder = new File("plugins/ActionQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/ActionQuest/main.yml");
		File folder = new File("plugins/ActionQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/ActionQuest/main.yml");
		File folder = new File("plugins/ActionQuest");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionQuest] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
