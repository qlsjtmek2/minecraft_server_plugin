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
            List<String> reward = config.getStringList("�̰����� NPC �̸��� ��������.1.����");
            List<String> task = config.getStringList("�̰����� NPC �̸��� ��������.1.����");
            List<String> content = config.getStringList("�̰����� NPC �̸��� ��������.1.����");
            List<String> tip = config.getStringList("�̰����� NPC �̸��� ��������.1.����");
            
            reward.add("rpgitem RPG�������̸�,����");
            reward.add("give �������ڵ�:�����۵������ڵ�,����");
            reward.add("money ��");
            reward.add("item �������ڵ�:�����۵������ڵ�,����,�������̸�,�����ۼ���");
            reward.add("command ��ɾ�");
            reward.add("commandop ��ɾ�");
            reward.add("commandcon ��ɾ�");
            reward.add("exp ����");
            reward.add("score ����");

            task.add("epicboss ���Ⱥ����̸�,ų��");
            task.add("talk NPC�̸�");
            task.add("rpgitem RPG�������̸�,���� true");
            task.add("item �������ڵ�:�����۵������ڵ�,����,�������̸�,�����ۼ��� true");
            task.add("command ��ɾ�");
            task.add("chat ä�ó���");
            task.add("playerkill ����");
            task.add("monsterkill ����");
            
            content.add("<player>���̰����� ��ȭ ������ ��������");
            content.add("<npc>���̰����� ��ȭ ������ ��������");
            
            tip.add("�̰����� ����Ʈ ���� ��������");
            
            config.set("�̰����� NPC �̸��� ��������.1.�̸�", "����ٰ� ����Ʈ �̸��� ��������");
            config.set("�̰����� NPC �̸��� ��������.1.���", "<�Ϲ�/����/����/���>");
            config.set("�̰����� NPC �̸��� ��������.1.��ǥ", "X,Y,Z,World,Yaw,Pitch");
            config.set("�̰����� NPC �̸��� ��������.1.��", tip);
            config.set("�̰����� NPC �̸��� ��������.1.����", reward);
            config.set("�̰����� NPC �̸��� ��������.1.����", task);
            config.set("�̰����� NPC �̸��� ��������.1.��ŵ", true);
            config.set("�̰����� NPC �̸��� ��������.1.����", content);
            
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
