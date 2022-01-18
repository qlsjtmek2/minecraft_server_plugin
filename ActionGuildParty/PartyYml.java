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
            list = config.getStringList("파티 이름");
            config.set("파티 이름", list);
            
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
            list = config.getStringList(manager + ".유저 목록");
            list2 = config.getStringList(manager + ".파티 설명");
			config.set(manager + ".파티장", manager);
			config.set(manager + ".가입 타입", 0);
			list2.add("§f/파티 -> 파티 옵션 -> 파티 설명 수정 §7순서");
			list2.add("§7대로 클릭 후 설명을 변경해 주시기 바랍니다.");
			list.add(manager);
			config.set(manager + ".파티 설명", list2);
			config.set(manager + ".파티원 목록", list);
			config.set(manager + ".경험치 분배", true);
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
			
			config.set(manager + ".파티장", manager);
			config.set(manager + ".가입 타입", type);
			config.set(manager + ".파티 설명", info);
			config.set(manager + ".파티원 목록", user);
			config.set(manager + ".경험치 분배", exp);
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
			
			PlayerYml.setInfoString(p, "가입된 파티", null);
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
		
		List<String> getList = config.getStringList("파티 이름");
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
			
			config.set("파티 이름", amount);
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
			
            List<String> list = config.getStringList("파티 이름");
            if (!list.contains(name)) list.add(name);
			config.set("파티 이름", list);
			
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
			
            List<String> list = config.getStringList("파티 이름");
            if (list.contains(name)) list.remove(name);
            config.set("파티 이름", list);
			
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

            List<String> pname = config.getStringList("파티 이름");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i == -1) {
				pname.add(name);
			}

			config.set("파티 이름", pname);
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

            List<String> pname = config.getStringList("파티 이름");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i == -1) {
				pname.add(name);
			}
			
			config.set("파티 이름", pname);
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

            List<String> pname = config.getStringList("파티 이름");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i == -1) {
				pname.add(name);
			}
			
			config.set("파티 이름", pname);
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
