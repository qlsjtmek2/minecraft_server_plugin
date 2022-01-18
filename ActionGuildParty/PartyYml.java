package me.espoo.rpg.party;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import me.espoo.rpg.Method;
import me.espoo.rpg.PlayerYml;

public class PartyYml {
	public static void CreatePartyYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> list;
            list = config.getStringList("��Ƽ �̸�");
            config.set("��Ƽ �̸�", list);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void addParty(String manager)
	{
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
            List<String> list;
            List<String> list2;
            list = config.getStringList(manager + ".���� ���");
            list2 = config.getStringList(manager + ".��Ƽ ����");
			config.set(manager + ".��Ƽ��", manager);
			config.set(manager + ".���� Ÿ��", 0);
			list2.add("��f/��Ƽ -> ��Ƽ �ɼ� -> ��Ƽ ���� ���� ��7����");
			list2.add("��7��� Ŭ�� �� ������ ������ �ֽñ� �ٶ��ϴ�.");
			list.add(manager);
			config.set(manager + ".��Ƽ ����", list2);
			config.set(manager + ".��Ƽ�� ���", list);
			config.set(manager + ".����ġ �й�", true);
			config.save(f);
			
			PartyYml.pluPartyList(manager);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setParty(String manager, List<String> user, List<String> info, int type, boolean exp)
	{
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
			config.set(manager + ".��Ƽ��", manager);
			config.set(manager + ".���� Ÿ��", type);
			config.set(manager + ".��Ƽ ����", info);
			config.set(manager + ".��Ƽ�� ���", user);
			config.set(manager + ".����ġ �й�", exp);
			config.save(f);
			
			PartyYml.pluPartyList(manager);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void deleteParty(String Party, OfflinePlayer p)
	{
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
			PlayerYml.setInfoString(p, "���Ե� ��Ƽ", null);
            List<String> list;
            list = config.getStringList(Party);
            list.clear();
			config.set(Party, list);
			config.save(f);
			delPartyList(Party);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static List<String> getPartyList() {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePartyYml(f, folder, config);
		}
		
		List<String> getList = config.getStringList("��Ƽ �̸�");
		return getList;
	}
	
	public static void setPartyList(List<String> amount) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
			config.set("��Ƽ �̸�", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void pluPartyList(String name) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
            List<String> list = config.getStringList("��Ƽ �̸�");
            if (!list.contains(name)) list.add(name);
			config.set("��Ƽ �̸�", list);
			
			config.save(f);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void delPartyList(String name) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
            List<String> list = config.getStringList("��Ƽ �̸�");
            if (list.contains(name)) list.remove(name);
            config.set("��Ƽ �̸�", list);
			
			config.save(f);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setPlayerParty(String name, int amount) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}

            List<String> pname = config.getStringList("��Ƽ �̸�");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i == -1) {
				pname.add(name);
			}

			config.set("��Ƽ �̸�", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addPlayerParty(String name, int amount) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}

            List<String> pname = config.getStringList("��Ƽ �̸�");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i == -1) {
				pname.add(name);
			}
			
			config.set("��Ƽ �̸�", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subPlayerParty(String name, int amount) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}

            List<String> pname = config.getStringList("��Ƽ �̸�");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i == -1) {
				pname.add(name);
			}
			
			config.set("��Ƽ �̸�", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
    
	public static List<String> getList(String name) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePartyYml(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
		
		if (list == null) {
			return null;
		}
		
        for (String str : list) {
    		str = Method.replaceAllColors(str);
    		Returnlist.add(str);
        }
		return Returnlist;
	}
	
	public static Boolean getBoolean(String name) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePartyYml(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePartyYml(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getString(String name) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePartyYml(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setBoolean(String name, Boolean amount) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/ActionGuildParty/Party.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePartyYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
