package me.espoo.crafting;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Whitelist {
	public static void CreateWhitelistConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> list;
            list = config.getStringList("���� ������ ������ ���");
            list.add("42:0:null");
            list.add("41:0:null");
            list.add("57:0:null");
            list.add("133:0:null");
            list.add("35:15:&f��ź ���");
            list.add("155:0:&f�״� ���� ���");
            list.add("152:0:null");
            list.add("22:0:null");
            list.add("265:0:null");
            list.add("266:0:null");
            list.add("264:0:null");
            list.add("263:0:null");
            list.add("388:0:null");
            list.add("406:0:null");
            list.add("331:0:null");
            list.add("351:4:null");
            list.add("35:0:&eö ���� ���");
            list.add("35:4:&e�� ���� ���");
            list.add("35:9:&e���̾Ƹ�� ���� ���");
            list.add("35:5:&e���޶��� ���� ���");
            list.add("49:0:&e��ź ���� ���");
            list.add("35:8:&e�״� ���� ���� ���");
            list.add("35:14:&e���彺�� ���� ���");
            list.add("35:11:&eû�ݼ� ���� ���");
            list.add("292:0:null");
            list.add("293:0:null");
            config.set("���� ������ ������ ���", list);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchCrafting] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getWhitelistList(String name) {
		File f = new File("plugins/OnePunchCrafting/Whitelist.yml");
		File folder = new File("plugins/OnePunchCrafting");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateWhitelistConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = replaceAllColors(str);
    		Returnlist.add(str);
        }
		return Returnlist;
	}
	
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "��1");
        message = message.replaceAll("&2", "��2");
        message = message.replaceAll("&3", "��3");
        message = message.replaceAll("&4", "��4");
        message = message.replaceAll("&5", "��5");
        message = message.replaceAll("&6", "��6");
        message = message.replaceAll("&7", "��7");
        message = message.replaceAll("&8", "��8");
        message = message.replaceAll("&9", "��9");
        message = message.replaceAll("&0", "��0");
        message = message.replaceAll("&a", "��a");
        message = message.replaceAll("&b", "��b");
        message = message.replaceAll("&c", "��c");
        message = message.replaceAll("&d", "��d");
        message = message.replaceAll("&e", "��e");
        message = message.replaceAll("&f", "��f");
        message = message.replaceAll("&k", "��k");
        message = message.replaceAll("&l", "��l");
        message = message.replaceAll("&m", "��m");
        message = message.replaceAll("&n", "��n");
        message = message.replaceAll("&o", "��o");
        message = message.replaceAll("&r", "��r");
        return message;
	}
}
