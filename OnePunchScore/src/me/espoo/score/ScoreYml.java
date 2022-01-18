package me.espoo.score;

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

public class ScoreYml {
	public static void CreateScoreYml(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            List<String> list;
            list = config.getStringList("닉네임");
            config.set("닉네임", list);
            
            List<Integer> list2;
            list2 = config.getIntegerList("점수");
            config.set("점수", list2);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchScore] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<Integer> getScoreList() {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateScoreYml(f, folder, config);
		}
		
		List<Integer> getList = config.getIntegerList("점수");
		return getList;
	}
	
	public static List<String> getNameList() {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateScoreYml(f, folder, config);
		}
		
		List<String> getList = config.getStringList("닉네임");
		return getList;
	}
	
	public static void setScoreList(String name, List<Integer> amount) {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateScoreYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setNameList(String name, List<String> amount) {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateScoreYml(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void PlusPlayerList(String name) {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateScoreYml(f, folder, config);
			}
			
            List<String> list;
            list = config.getStringList("닉네임");
            list.add(name);
			config.set("닉네임", list);
			
			List<Integer> list2;
            list2 = config.getIntegerList("점수");
            list2.add(0);
			config.set("점수", list2);
			
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setPlayerScore(String name, int amount) {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateScoreYml(f, folder, config);
			}

            List<String> pname = config.getStringList("닉네임");
			List<Integer> score = config.getIntegerList("점수");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				score.set(i, amount);
			} else {
				score.add(amount);
				pname.add(name);
			}

			config.set("점수", score);
			config.set("닉네임", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addPlayerScore(String name, int amount) {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateScoreYml(f, folder, config);
			}

            List<String> pname = config.getStringList("닉네임");
			List<Integer> score = config.getIntegerList("점수");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				score.set(i, score.get(i) + amount);
			} else {
				score.add(amount);
				pname.add(name);
			}
			
			config.set("점수", score);
			config.set("닉네임", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subPlayerScore(String name, int amount) {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateScoreYml(f, folder, config);
			}

            List<String> pname = config.getStringList("닉네임");
			List<Integer> score = config.getIntegerList("점수");
            
			int i = -1;
			for (int y = 0; pname.size() > y; y++) {
				if (pname.get(y).equalsIgnoreCase(name)) {
					i = y; break;
				}
			}
			
			if (i != -1) {
				if (score.get(i) - amount <= 0) {
					score.set(i, 0);
				} else {
					score.set(i, score.get(i) - amount);	
				}
			} else {
				score.add(0);
				pname.add(name);
			}
			
			config.set("점수", score);
			config.set("닉네임", pname);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static List<String> ArrayScore() {
		HashMap<String, Integer> hashMap  = new HashMap<String, Integer>();
		List<String> name = ScoreYml.getNameList();
		List<Integer> score = ScoreYml.getScoreList();
		List<String> list = new ArrayList<>();
		for (int y = 0; name.size() > y; y++) {
			hashMap.put(name.get(y), score.get(y));
		}
		
        Iterator<?> it = ScoreYml.sortByValue(hashMap).iterator();
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
