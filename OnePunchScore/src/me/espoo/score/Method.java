package me.espoo.score;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Method {
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	public static String searchOnlinePlayer(String target) {
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < 16; i++) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}
	
	public static Player getOnorOffLine(String s) {
		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all.getName().equals(s))
			{
				return all;
			}
		}
		return null;
	}
	
	public static boolean getPlayerSeeInfo(Player p) {
		File f = new File("plugins/OnePunchScore/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchScore");
		File folder2 = new File("plugins/OnePunchScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			PlayerYml.CreatePlayerInfo(f, folder, folder2, config);
		}
		
		boolean Seehandr = config.getBoolean("보기");
		return Seehandr;
	}
	
	public static int getPlayerScore(String name) {
		File f = new File("plugins/OnePunchScore/Score.yml");
		File folder = new File("plugins/OnePunchScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			ScoreYml.CreateScoreYml(f, folder, config);
		}
		
        List<String> pname = config.getStringList("닉네임");
		List<Integer> score = config.getIntegerList("점수");
        
		int i = 0;
		for (int y = 0; pname.size() > y; y++) {
			if (pname.get(y).equalsIgnoreCase(name)) {
				i = score.get(y); break;
			}
		}
		
		return i;
	}
	
	public static void setPlayerBouns(Player p, int i) {
		PlayerYml.setInfoInt(p, "보너스", i);
	}
	
	public static int getPlayerRanking(String name) {
		int num = 1;
		for (String str : ScoreYml.ArrayScore()) {
			if (str.split(",")[0].equalsIgnoreCase(name)) {
				break;
			} else num++;
		}
		
		return num;
	}
	
	public static String roundDot1Off(String str) {
		float fTemp = (Float.parseFloat(str));
		DecimalFormat format = new DecimalFormat("#.#");
		String rtnStr = format.format(fTemp);
		return rtnStr;
	}
}
