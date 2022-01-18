package me.espoo.rpg.exp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class ExpYml {
	public static void CreateExpYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> list;
            list = config.getStringList("닉네임");
            config.set("닉네임", list);
            
            List<Integer> list2;
            list2 = config.getIntegerList("경험치");
            config.set("경험치", list2);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<Integer> getExpList() {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateExpYml(f, folder, config);
		}
		
		List<Integer> getList = config.getIntegerList("경험치");
		return getList;
	}
	
	public static List<String> getNameList() {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateExpYml(f, folder, config);
		}
		
		List<String> getList = config.getStringList("닉네임");
		return getList;
	}
	
	public static void setExpList(String name, List<Integer> amount) {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpYml(f, folder, config);
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
	
	public static void setNameList(String name, List<String> amount) {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpYml(f, folder, config);
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
	
	public static void PlusPlayerList(String name) {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpYml(f, folder, config);
			}
			
            List<String> list;
            list = config.getStringList("닉네임");
            list.add(name);
			config.set("닉네임", list);
			
			List<Integer> list2;
            list2 = config.getIntegerList("경험치");
            list2.add(0);
			config.set("경험치", list2);
			
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static int getPlayerExp(String name) {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		
		if (!f.exists()) {
			CreateExpYml(f, folder, config);
		}

        List<String> pname = config.getStringList("닉네임");
		List<Integer> Exp = config.getIntegerList("경험치");
        
		int i = -1;
		for (int y = 0; pname.size() > y; y++) {
			if (pname.get(y).equalsIgnoreCase(name)) {
				i = y; break;
			}
		}
		
		if (i == -1) {
			return 0;
		} else {
			return Exp.get(i);	
		}
	}
	
	public static void setPlayerExp(String name, int amount) {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpYml(f, folder, config);
			}

            List<String> pname = config.getStringList("닉네임");
			List<Integer> Exp = config.getIntegerList("경험치");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				Exp.set(i, amount);
			} else {
				Exp.add(amount);
				pname.add(name);
			}

			config.set("경험치", Exp);
			config.set("닉네임", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addPlayerExp(String name, int amount) {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpYml(f, folder, config);
			}

            List<String> pname = config.getStringList("닉네임");
			List<Integer> Exp = config.getIntegerList("경험치");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				Exp.set(i, Exp.get(i) + amount);
			} else {
				Exp.add(amount);
				pname.add(name);
			}
			
			config.set("경험치", Exp);
			config.set("닉네임", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subPlayerExp(String name, int amount) {
		File f = new File("plugins/OnePunchRpg/Exp.yml");
		File folder = new File("plugins/OnePunchRpg");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateExpYml(f, folder, config);
			}

            List<String> pname = config.getStringList("닉네임");
			List<Integer> Exp = config.getIntegerList("경험치");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				if (Exp.get(i) - amount <= 0) {
					Exp.set(i, 0);
				} else {
					Exp.set(i, Exp.get(i) - amount);	
				}
			} else {
				Exp.add(0);
				pname.add(name);
			}
			
			config.set("경험치", Exp);
			config.set("닉네임", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchRpg] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static List<String> ArrayExp() {
		HashMap<String, Integer> hashMap  = new HashMap<String, Integer>();
		List<String> name = ExpYml.getNameList();
		List<Integer> Exp = ExpYml.getExpList();
		List<String> list = new ArrayList<>();
		for (int y = 0; name.size() > y; y++) {
			hashMap.put(name.get(y), Exp.get(y));
		}
		
        Iterator<?> it = ExpYml.sortByValue(hashMap).iterator();
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
}
