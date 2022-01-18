package me.espoo.rpg.guild;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.configuration.file.YamlConfiguration;

import me.espoo.rpg.Method;

public class GuildYml {
	public static void CreateGuildYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> list;
            list = config.getStringList("길드 이름");
            config.set("길드 이름", list);
            
            List<Integer> list2;
            list2 = config.getIntegerList("길드 포인트");
            config.set("길드 포인트", list2);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<Integer> getPointList() {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}
		
		List<Integer> getList = config.getIntegerList("길드 포인트");
		return getList;
	}
	
	public static List<String> getGuildList() {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}
		
		List<String> getList = config.getStringList("길드 이름");
		return getList;
	}
	
	public static void setPointList(List<Integer> amount) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			config.set("길드 포인트", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setGuildList(List<String> amount) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
			config.set("길드 이름", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void pluGuildList(String name) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
            List<String> list = config.getStringList("길드 이름");
            list.add(name);
			config.set("길드 이름", list);
			
			List<Integer> list2 = config.getIntegerList("길드 포인트");
            list2.add(0);
			config.set("길드 포인트", list2);
			
			config.save(f);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void delGuildList(String name) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}
			
            List<String> list = config.getStringList("길드 이름");
			List<Integer> list2 = config.getIntegerList("길드 포인트");
            int num = 0; boolean is = false;
            
            for (String str : list) {
                if (str.equalsIgnoreCase(name)) {
                	is = true;
                } else num++;
            }
            
            if (is) {
            	list.remove(num);
    			list2.remove(num);
    			
    			config.set("길드 이름", list);
    			config.set("길드 포인트", list2);
            }
			
			config.save(f);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static int getPlayerGuild(String name) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}

        List<String> pname = config.getStringList("길드 이름");
		List<Integer> Guild = config.getIntegerList("길드 포인트");
        
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
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}

            List<String> pname = config.getStringList("길드 이름");
			List<Integer> Guild = config.getIntegerList("길드 포인트");
            
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

			config.set("길드 포인트", Guild);
			config.set("길드 이름", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addPlayerGuild(String name, int amount) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}

            List<String> pname = config.getStringList("길드 이름");
			List<Integer> Guild = config.getIntegerList("길드 포인트");
            
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
			
			config.set("길드 포인트", Guild);
			config.set("길드 이름", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subPlayerGuild(String name, int amount) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
			}

            List<String> pname = config.getStringList("길드 이름");
			List<Integer> Guild = config.getIntegerList("길드 포인트");
            
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
			
			config.set("길드 포인트", Guild);
			config.set("길드 이름", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
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
    
	public static void sortGuildPoint() {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		ValueComparator bvc = new ValueComparator(map);
		TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
		map.putAll(Guild.guildpoint);
		sorted_map.putAll(map);
		Guild.guildpoint.clear();
		
        for (Map.Entry<String,Integer> entry : sorted_map.entrySet()) {
        	Guild.guildpoint.put(entry.getKey(), map.get(entry.getKey()));
        }
	}
    /*
	public static List<String> ArrayGuild() {
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		List<String> name = new ArrayList<String>();
		List<Integer> point = new ArrayList<Integer>();
		name.addAll(Guild.guildpoint.keySet());
		point.addAll(Guild.guildpoint.values());
		
		List<String> list = new ArrayList<>();
		for (int y = 0; name.size() > y; y++) {
			hashMap.put(name.get(y), point.get(y));
		}
		
        Iterator<String> it = GuildYml.sortByValue(hashMap).iterator();
        while (it.hasNext()) {
            String temp = (String) it.next();
            list.add(temp + "," + hashMap.get(temp));
        }

		return list;
	}
	
    public static List<String> sortByValue(final HashMap<String, Integer> map) {
        List<String> list = new ArrayList<String>();
        list.addAll(map.keySet());

        Collections.sort(list, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			public int compare(Object o1, Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                 
                return ((Comparable<Object>) v1).compareTo(v2);
            }
        });
        
        Collections.reverse(list); // 주석시 오름차순
        return list;
    }
    */
	public static List<String> getList(String name) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
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
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGuildYml(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInt(String name) {
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
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
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
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
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
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
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
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
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
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
		File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateGuildYml(f, folder, config);
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
