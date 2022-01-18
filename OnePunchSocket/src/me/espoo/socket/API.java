package me.espoo.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class API {
	public static String getLoreNum(List<String> lore) {
		for (String str : lore) {
			if (str.contains("경험치 보너스")) return "경험치 보너스";
			if (str.contains("히어로 점수 추가")) return "히어로 점수 추가";
			if (str.contains("추가 데미지")) return "추가 데미지";
			if (str.contains("데미지 감소")) return "데미지 감소";
			if (str.contains("크리티컬 확률")) return "크리티컬 확률";
			if (str.contains("크리티컬 데미지")) return "크리티컬 데미지";
			if (str.contains("회피력")) return "회피력";
			if (str.contains("체력 흡수")) return "체력 흡수";
		} return null;
	}
	
	public static void castLvup(Player p) {
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	public static int getExpBouns(List<String> lore) {
		for (String str : lore) {
			if (str.contains("경험치 보너스")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]).replaceAll("%", ""));
		} return 0;
	}
	
	public static int getHeroScore(List<String> lore) {
		for (String str : lore) {
			if (str.contains("히어로 점수 추가")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[4]).replaceAll("%", ""));
		} return 0;
	}
	
	public static int getPlusDamage(List<String> lore) {
		for (String str : lore) {
			if (str.contains("추가 데미지")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]));
		} return 0;
	}
	
	public static int getCretProbe(List<String> lore) {
		for (String str : lore) {
			if (str.contains("크리티컬 확률")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]).replaceAll("%", ""));
		} return 0;
	}
	
	public static int getCretDamage(List<String> lore) {
		for (String str : lore) {
			if (str.contains("크리티컬 데미지")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]));
		} return 0;
	}
	
	public static int getHeartGet(List<String> lore) {
		for (String str : lore) {
			if (str.contains("체력 흡수")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]));
		} return 0;
	}
	
	public static int getDamageDefense(List<String> lore) {
		for (String str : lore) {
			if (str.contains("데미지 감소")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[3]).replaceAll("%", ""));
		} return 0;
	}
	
	public static int getDodge(List<String> lore) {
		for (String str : lore) {
			if (str.contains("회피력")) return Integer.parseInt(ChatColor.stripColor(str.split(" ")[2]).replaceAll("%", ""));
		} return 0;
	}
	
	public static List<String> getOneLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§8최하급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(10) + 11;
			list.add("§f[§6§l+§f] 경험치 보너스 " + one + "%");
			if (one <= 15) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (one > 15) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 2:
			int two = new Random().nextInt(10) + 11;
			list.add("§f[§6§l+§f] 히어로 점수 추가 " + two + "%");
			if (two <= 15) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (two > 15) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 3:
			int three = new Random().nextInt(6) + 5;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 7) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (three > 7) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 4:
			int four = new Random().nextInt(3) + 1;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (four > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 5:
			int five = new Random().nextInt(10) + 11;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 15) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (five > 15) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 6:
			int six = new Random().nextInt(4) + 2;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 3) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (six > 3) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 7:
			int seven = new Random().nextInt(4) + 1;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (seven > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 8:
			int eight = new Random().nextInt(3) + 1;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (eight > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
		}
		
		return list;
	}
	
	public static List<String> getTwoLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§7하급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(20) + 21;
			list.add("§f[§6§l+§f] 경험치 보너스 " + one + "%");
			if (one <= 30) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (one > 30) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 2:
			int two = new Random().nextInt(20) + 21;
			list.add("§f[§6§l+§f] 히어로 점수 추가 " + two + "%");
			if (two <= 30) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (two > 30) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 3:
			int three = new Random().nextInt(15) + 11;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 18) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (three > 18) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 4:
			int four = new Random().nextInt(3) + 3;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 3) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (four > 3) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 5:
			int five = new Random().nextInt(10) + 21;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 25) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (five > 25) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 6:
			int six = new Random().nextInt(6) + 5;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 7) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (six > 7) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 7:
			int seven = new Random().nextInt(4) + 5;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 6) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (seven > 6) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 8:
			int eight = new Random().nextInt(3) + 3;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 3) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (eight > 3) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
		}
		
		return list;
	}
	
	public static List<String> getThreeLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§a중급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(30) + 51;
			list.add("§f[§6§l+§f] 경험치 보너스 " + one + "%");
			if (one <= 65) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (one > 65) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 2:
			int two = new Random().nextInt(30) + 51;
			list.add("§f[§6§l+§f] 히어로 점수 추가 " + two + "%");
			if (two <= 65) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (two > 65) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 3:
			int three = new Random().nextInt(14) + 29;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 35) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (three > 35) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 4:
			int four = new Random().nextInt(4) + 5;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 6) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (four > 6) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 5:
			int five = new Random().nextInt(10) + 31;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 35) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (five > 35) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 6:
			int six = new Random().nextInt(13) + 13;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 19) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (six > 19) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 7:
			int seven = new Random().nextInt(8) + 9;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 12) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (seven > 12) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 8:
			int eight = new Random().nextInt(4) + 5;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 6) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (eight > 6) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
		}
		
		return list;
	}
	
	public static List<String> getFourLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§b상급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(50) + 101;
			list.add("§f[§6§l+§f] 경험치 보너스 " + one + "%");
			if (one <= 125) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (one > 125) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 2:
			int two = new Random().nextInt(50) + 101;
			list.add("§f[§6§l+§f] 히어로 점수 추가 " + two + "%");
			if (two <= 125) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (two > 125) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 3:
			int three = new Random().nextInt(20) + 46;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 55) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (three > 55) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 4:
			int four = new Random().nextInt(6) + 7;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 9) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (four > 9) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 5:
			int five = new Random().nextInt(10) + 41;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 45) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (five > 45) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 6:
			int six = new Random().nextInt(15) + 31;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 38) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (six > 38) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 7:
			int seven = new Random().nextInt(12) + 21;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 27) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (seven > 27) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 8:
			int eight = new Random().nextInt(6) + 7;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 9) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (eight > 9) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
		}
		
		return list;
	}
	
	public static List<String> getFiveLore() {
		int num = new Random().nextInt(8) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§6최상급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int one = new Random().nextInt(70) + 151;
			list.add("§f[§6§l+§f] 경험치 보너스 " + one + "%");
			if (one <= 125) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (one > 125) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 2:
			int two = new Random().nextInt(70) + 151;
			list.add("§f[§6§l+§f] 히어로 점수 추가 " + two + "%");
			if (two <= 125) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (two > 125) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 3:
			int three = new Random().nextInt(25) + 66;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 55) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (three > 55) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 4:
			int four = new Random().nextInt(10) + 11;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 9) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (four > 9) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 5:
			int five = new Random().nextInt(15) + 51;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 45) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (five > 45) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 6:
			int six = new Random().nextInt(26) + 41;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 38) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (six > 38) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 7:
			int seven = new Random().nextInt(14) + 29;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 27) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (seven > 27) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 8:
			int eight = new Random().nextInt(10) + 11;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 9) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (eight > 9) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
		}
		
		return list;
	}
}
