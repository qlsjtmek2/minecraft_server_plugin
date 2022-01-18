package me.espoo.rpg.guild;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import me.espoo.rpg.Method;
import me.espoo.rpg.PlayerYml;

public class GuildYml {
	public static void CreateGuildYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> list;
            list = config.getStringList("��� �̸�");
            config.set("��� �̸�", list);
            
            List<Integer> list2;
            list2 = config.getIntegerList("��� ����Ʈ");
            config.set("��� ����Ʈ", list2);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void addGuild(String guild, String manager)
	{
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			Calendar cal = Calendar.getInstance();
			StringBuilder str = new StringBuilder();
			str.append(cal.get(Calendar.MONTH) + 1);
			str.append(",");
			str.append(cal.get(Calendar.DATE));
			
			StringBuilder st = new StringBuilder();
			st.append(cal.get(Calendar.YEAR));
			st.append(",");
			st.append(cal.get(Calendar.MONTH) + 1);
			st.append(",");
			st.append(cal.get(Calendar.DATE));
            List<String> list;
            List<String> list2;
            list = config.getStringList(guild + ".����.��� ����");
            list2 = config.getStringList(guild + ".���� ���");
			config.set(guild + ".�Ŵ���", manager);
			config.set(guild + ".�Ŵ��� ������ ���ӽð�", str.toString());
			config.set(guild + ".��� ������ ��¥", st.toString());
			config.set(guild + ".���׷��̵� �ܰ�", 0);
			config.set(guild + ".���� Ÿ��", 0);
			config.set(guild + ".����.������ �ڵ�", "323");
			config.set(guild + ".���� ���", null);
			config.set(guild + ".��� ��� �׺� ��û", false);
			config.set(guild + ".����.���� �޼���", "��f���� �޼����� �������� �ʽ��ϴ�.");
			list.add("��f���� â���� " + guild + " ��� �Դϴ�.");
			list.add("��f���� ���ɰ� ��� ��Ź�մϴ�.");
			config.set(guild + ".����.��� ����", list);
			config.set(guild + ".����.����ġ", 0);
			config.set(guild + ".����.����� ����", 0);
			config.set(guild + ".����.���", 0);
			config.set(guild + ".����.����ġ Ȱ��ȭ", true);
			config.set(guild + ".����.����� ���� Ȱ��ȭ", true);
			config.set(guild + ".����.��� Ȱ��ȭ", true);
			config.set(guild + ".�ε���.���� ����", false);
			config.set(guild + ".�ε���.ù��° ��ǥ", "999999999,999999999,999999999");
			config.set(guild + ".�ε���.�ι�° ��ǥ", "999999999,999999999,999999999");
			config.set(guild + ".�ε���.Ȩ ��ǥ", "999999999,999999999,999999999,0,0");
			config.set(guild + ".�ε���.����ȭ�α���", -1);
			config.set(guild + ".�ε���.��ġ���ű���", -1);
			config.set(guild + ".�ε���.PvP����", false);
			list2.add(manager + ",�Ŵ���");
			config.set(guild + ".���� ���", list2);
			config.set(guild + ".���� ��û ���", Arrays.asList());
			config.save(f);
			
			GuildYml.pluGuildList(guild);
		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void deleteGuild(String guild, OfflinePlayer p)
	{
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			PlayerYml.setInfoString(p, "���Ե� ���", null);
            List<String> list;
            list = config.getStringList(guild);
            list.clear();
			config.set(guild, list);
			config.save(f);
			delGuildList(guild);
		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static List<Integer> getPointList() {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}
		
		List<Integer> getList = config.getIntegerList("��� ����Ʈ");
		return getList;
	}
	
	public static List<String> getGuildList() {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}
		
		List<String> getList = config.getStringList("��� �̸�");
		return getList;
	}
	
	public static void setPointList(List<Integer> amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			config.set("��� ����Ʈ", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setGuildList(List<String> amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			config.set("��� �̸�", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void pluGuildList(String name) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
            List<String> list = config.getStringList("��� �̸�");
            list.add(name);
			config.set("��� �̸�", list);
			
			List<Integer> list2 = config.getIntegerList("��� ����Ʈ");
            list2.add(0);
			config.set("��� ����Ʈ", list2);
			
			config.save(f);
		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void delGuildList(String name) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
            List<String> list = config.getStringList("��� �̸�");
			List<Integer> list2 = config.getIntegerList("��� ����Ʈ");
            int num = 0; boolean is = false;
            
            for (String str : list) {
                if (str.equalsIgnoreCase(name)) {
                	is = true;
                } else num++;
            }
            
            if (is) {
            	list.remove(num);
    			list2.remove(num);
    			
    			config.set("��� �̸�", list);
    			config.set("��� ����Ʈ", list2);
            }
			
			config.save(f);
		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static int getPlayerGuild(String name) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}

        List<String> pname = config.getStringList("��� �̸�");
		List<Integer> Guild = config.getIntegerList("��� ����Ʈ");
        
		int i = -1;
		for (int y = 0; pname.size() > y; y++) {
			if (pname.get(y).equalsIgnoreCase(name)) {
				i = y; break;
			}
		}
		
		if (i == -1) {
			return 0;
		} else {
			return Guild.get(i);	
		}
	}
	
	public static void setPlayerGuild(String name, int amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}

            List<String> pname = config.getStringList("��� �̸�");
			List<Integer> Guild = config.getIntegerList("��� ����Ʈ");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				Guild.set(i, amount);
			} else {
				Guild.add(amount);
				pname.add(name);
			}

			config.set("��� ����Ʈ", Guild);
			config.set("��� �̸�", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addPlayerGuild(String name, int amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}

            List<String> pname = config.getStringList("��� �̸�");
			List<Integer> Guild = config.getIntegerList("��� ����Ʈ");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				Guild.set(i, Guild.get(i) + amount);
			} else {
				Guild.add(amount);
				pname.add(name);
			}
			
			config.set("��� ����Ʈ", Guild);
			config.set("��� �̸�", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subPlayerGuild(String name, int amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}

            List<String> pname = config.getStringList("��� �̸�");
			List<Integer> Guild = config.getIntegerList("��� ����Ʈ");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				if (Guild.get(i) - amount <= 0) {
					Guild.set(i, 0);
				} else {
					Guild.set(i, Guild.get(i) - amount);	
				}
			} else {
				Guild.add(0);
				pname.add(name);
			}
			
			config.set("��� ����Ʈ", Guild);
			config.set("��� �̸�", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static List<String> ArrayGuild() {
		HashMap<String, Integer> hashMap  = new HashMap<String, Integer>();
		List<String> name = GuildYml.getGuildList();
		List<Integer> Guild = GuildYml.getPointList();
		List<String> list = new ArrayList<>();
		for (int y = 0; name.size() > y; y++) {
			hashMap.put(name.get(y), Guild.get(y));
		}
		
        Iterator<?> it = GuildYml.sortByValue(hashMap).iterator();
        while (it.hasNext()) {
            String temp = (String) it.next();
            list.add(temp + "," + hashMap.get(temp));
        }

		return list;
	}
	
    public static List<String> sortByValue(final Map<String, ?> map) {
        List<String> list = new ArrayList<String>();
        list.addAll(map.keySet());
         
        Collections.sort(list,new Comparator<Object>() {
            @SuppressWarnings("unchecked")
			public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                 
                return ((Comparable<Object>) v1).compareTo(v2);
            }
        });
        
        Collections.reverse(list);
        return list;
    }
    
	public static List<String> getList(String name) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
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
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getString(String name) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setBoolean(String name, Boolean amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInt(String name, int amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setString(String name, String amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setList(String name, List<String> amount) {
		File f = new File("plugins/OnePunchRpg/Guild.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
