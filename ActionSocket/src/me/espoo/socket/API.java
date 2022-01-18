package me.espoo.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class API {
	public static void removeMapData(Player p) {
		if (main.damage.containsKey(p)) main.damage.remove(p);
		if (main.cretprobe.containsKey(p)) main.cretprobe.remove(p);
		if (main.cretdamage.containsKey(p)) main.cretdamage.remove(p);
		if (main.heart.containsKey(p)) main.heart.remove(p);
		if (main.defense.containsKey(p)) main.defense.remove(p);
		if (main.dodge.containsKey(p)) main.dodge.remove(p);
	}
	
	public static boolean isDataAttack(Player p) {
		return main.damage.containsKey(p) || main.cretprobe.containsKey(p) || main.cretdamage.containsKey(p) || main.heart.containsKey(p);
	}
	
	public static boolean isDataBeShot(Player p) {
		return main.defense.containsKey(p) || main.dodge.containsKey(p);
	}
	
	public static void setData(Player p) {
		int damage = 0;
		int cretprobe = 0;
		int cretdamage = 0;
		int heart = 0;
		int defense = 0;
		int dodge = 0;
		
		if (PlayerYml.getInfoString(p, "1.아이템 이름") != null) {
			String str = API.getLoreNum(PlayerYml.getInfoList(p, "1.아이템 설명"));

			if (str.equalsIgnoreCase("추가 데미지")) {
				damage += API.getPlusDamage(PlayerYml.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 확률")) {
				cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 데미지")) {
				cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("체력 흡수")) {
				heart += API.getHeartGet(PlayerYml.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("데미지 감소")) {
				defense += API.getDamageDefense(PlayerYml.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("회피력")) {
				dodge += API.getDodge(PlayerYml.getInfoList(p, "1.아이템 설명"));
			}
		}
		
		if (PlayerYml.getInfoString(p, "2.아이템 이름") != null) {
			String str = API.getLoreNum(PlayerYml.getInfoList(p, "2.아이템 설명"));
			if (str.equalsIgnoreCase("추가 데미지")) {
				damage += API.getPlusDamage(PlayerYml.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 확률")) {
				cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 데미지")) {
				cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("체력 흡수")) {
				heart += API.getHeartGet(PlayerYml.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("데미지 감소")) {
				defense += API.getDamageDefense(PlayerYml.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("회피력")) {
				dodge += API.getDodge(PlayerYml.getInfoList(p, "2.아이템 설명"));
			}
		}
		
		if (PlayerYml.getInfoString(p, "3.아이템 이름") != null) {
			String str = API.getLoreNum(PlayerYml.getInfoList(p, "3.아이템 설명"));
			if (str.equalsIgnoreCase("추가 데미지")) {
				damage += API.getPlusDamage(PlayerYml.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 확률")) {
				cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 데미지")) {
				cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("체력 흡수")) {
				heart += API.getHeartGet(PlayerYml.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("데미지 감소")) {
				defense += API.getDamageDefense(PlayerYml.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("회피력")) {
				dodge += API.getDodge(PlayerYml.getInfoList(p, "3.아이템 설명"));
			}
		}
		
		API.setMapData(p, damage, cretprobe, cretdamage, heart, defense, dodge);
	}
	
	public static void setMapData(Player p, int damage, int cretprobe, int cretdamage, int heart, int defense, int dodge) {
		if (main.damage.containsKey(p)) main.damage.remove(p);
		if (main.cretprobe.containsKey(p)) main.cretprobe.remove(p);
		if (main.cretdamage.containsKey(p)) main.cretdamage.remove(p);
		if (main.heart.containsKey(p)) main.heart.remove(p);
		if (main.defense.containsKey(p)) main.defense.remove(p);
		if (main.dodge.containsKey(p)) main.dodge.remove(p);
		
		main.damage.put(p, damage);
		main.cretprobe.put(p, cretprobe);
		main.cretdamage.put(p, cretdamage);
		main.heart.put(p, heart);
		main.defense.put(p, defense);
		main.dodge.put(p, dodge);
	}
	
	public static String getLoreNum(List<String> lore) {
		for (String str : lore) {
			if (str.contains("추가 데미지")) return "추가 데미지";
			if (str.contains("데미지 감소")) return "데미지 감소";
			if (str.contains("크리티컬 확률")) return "크리티컬 확률";
			if (str.contains("크리티컬 데미지")) return "크리티컬 데미지";
			if (str.contains("회피력")) return "회피력";
			if (str.contains("체력 흡수")) return "체력 흡수";
		} return null;
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cast forcecast " + p.getName() + " lvup");
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
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§8최하급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(6) + 5;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 9) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (three > 9) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 2:
			int four = new Random().nextInt(3) + 1;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (four > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 6;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 14) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (five > 14) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 4:
			int six = new Random().nextInt(3) + 3;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 4) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (six > 4) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 5:
			int seven = new Random().nextInt(3) + 1;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (seven > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
			
		case 6:
			int eight = new Random().nextInt(3) + 1;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
			else if (eight > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
			break;
		}
		
		return list;
	}
	
	public static List<String> getTwoLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§7하급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(12) + 9;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 19) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (three > 19) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 2:
			int four = new Random().nextInt(2) + 3;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 3) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (four > 3) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 16;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 24) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (five > 24) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 4:
			int six = new Random().nextInt(10) + 5;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 9) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (six > 9) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 5:
			int seven = new Random().nextInt(4) + 3;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 5) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (seven > 5) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
			
		case 6:
			int eight = new Random().nextInt(3) + 3;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 4) list.add("§f[§6§l+§f] 등급 - §6★★");
			else if (eight > 4) list.add("§f[§6§l+§f] 등급 - §6★★★");
			break;
		}
		
		return list;
	}
	
	public static List<String> getThreeLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§a중급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(12) + 21;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 31) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (three > 31) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 2:
			int four = new Random().nextInt(3) + 4;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 5) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (four > 5) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 26;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 34) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (five > 34) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 4:
			int six = new Random().nextInt(13) + 13;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 24) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (six > 24) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 5:
			int seven = new Random().nextInt(6) + 7;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 11) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (seven > 11) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
			
		case 6:
			int eight = new Random().nextInt(4) + 5;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 7) list.add("§f[§6§l+§f] 등급 - §6★★★");
			else if (eight > 7) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			break;
		}
		
		return list;
	}
	
	public static List<String> getFourLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§b상급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(13) + 33;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 44) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (three > 44) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 2:
			int four = new Random().nextInt(5) + 6;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 9) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (four > 9) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 36;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 44) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (five > 44) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 4:
			int six = new Random().nextInt(15) + 31;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 44) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (six > 44) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 5:
			int seven = new Random().nextInt(8) + 13;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 19) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (seven > 19) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
			
		case 6:
			int eight = new Random().nextInt(6) + 7;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 11) list.add("§f[§6§l+§f] 등급 - §6★★★★");
			if (eight > 11) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			break;
		}
		
		return list;
	}
	
	public static List<String> getFiveLore() {
		int num = new Random().nextInt(6) + 1;
		List<String> list = new ArrayList<>();
		list.add("§7§l==================");
		list.add("§6최상급 소켓석§f을 감정하여");
		list.add("§f획득한 소켓석이다. 채팅창");
		list.add("§e[ /소켓 ] §f입력후 장착하자.");
		list.add("§7§l==================");
		list.add("§f");
		
		switch (num) {
		case 1:
			int three = new Random().nextInt(15) + 46;
			list.add("§f[§6§l+§f] 추가 데미지 +" + three);
			if (three <= 59) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (three > 59) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 2:
			int four = new Random().nextInt(7) + 9;
			list.add("§f[§6§l+§f] 데미지 감소 " + four + "%");
			if (four <= 14) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (four > 14) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 3:
			int five = new Random().nextInt(10) + 46;
			list.add("§f[§6§l+§f] 크리티컬 확률 " + five + "%");
			if (five <= 54) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (five > 54) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 4:
			int six = new Random().nextInt(26) + 41;
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + six);
			if (six <= 65) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (six > 65) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 5:
			int seven = new Random().nextInt(8) + 21;
			list.add("§f[§6§l+§f] 회피력 " + seven + "%");
			if (seven <= 27) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (seven > 27) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
			
		case 6:
			int eight = new Random().nextInt(10) + 11;
			list.add("§f[§6§l+§f] 체력 흡수 +" + eight);
			if (eight <= 19) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
			if (eight > 19) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
			break;
		}
		
		return list;
	}
	
	public static ItemStack getItem(String type, String option, int integer, int amount) {
		ItemStack item = null;
		List<String> list = new ArrayList<String>();
		int intoption = 0;
		switch (option) {
		case "추뎀":
			intoption = 1;
			break;
			
		case "뎀감":
			intoption = 2;
			break;
			
		case "크확":
			intoption = 3;
			break;
			
		case "크뎀":
			intoption = 4;
			break;
			
		case "회피":
			intoption = 5;
			break;
			
		case "체흡":
			intoption = 6;
			break;
			
		default:
			return null;
		}
		
		
		switch (type) {
		case "최하":
			item = new MaterialData(263, (byte) 0).toItemStack(amount);
			list.add("§7§l==================");
			list.add("§8최하급 소켓석§f을 감정하여");
			list.add("§f획득한 소켓석이다. 채팅창");
			list.add("§e[ /소켓 ] §f입력후 장착하자.");
			list.add("§7§l==================");
			list.add("§f");
			break;
		case "하":
			item = new MaterialData(318, (byte) 0).toItemStack(amount);
			list.add("§7§l==================");
			list.add("§7하급 소켓석§f을 감정하여");
			list.add("§f획득한 소켓석이다. 채팅창");
			list.add("§e[ /소켓 ] §f입력후 장착하자.");
			list.add("§7§l==================");
			list.add("§f");
			break;
		case "중":
			item = new MaterialData(265, (byte) 0).toItemStack(amount);
			list.add("§7§l==================");
			list.add("§a중급 소켓석§f을 감정하여");
			list.add("§f획득한 소켓석이다. 채팅창");
			list.add("§e[ /소켓 ] §f입력후 장착하자.");
			list.add("§7§l==================");
			list.add("§f");
			break;
		case "상":
			item = new MaterialData(264, (byte) 0).toItemStack(amount);
			list.add("§7§l==================");
			list.add("§b상급 소켓석§f을 감정하여");
			list.add("§f획득한 소켓석이다. 채팅창");
			list.add("§e[ /소켓 ] §f입력후 장착하자.");
			list.add("§7§l==================");
			list.add("§f");
			break;
		case "최상":
			item = new MaterialData(388, (byte) 0).toItemStack(amount);
			list.add("§7§l==================");
			list.add("§6최상급 소켓석§f을 감정하여");
			list.add("§f획득한 소켓석이다. 채팅창");
			list.add("§e[ /소켓 ] §f입력후 장착하자.");
			list.add("§7§l==================");
			list.add("§f");
			break;
			
		default:
			return null;
		}
		
		switch (intoption) {
		case 1:
			list.add("§f[§6§l+§f] 추가 데미지 +" + integer);
			break;
			
		case 2:
			list.add("§f[§6§l+§f] 데미지 감소 " + integer + "%");
			break;
			
		case 3:
			list.add("§f[§6§l+§f] 크리티컬 확률 " + integer + "%");
			break;
			
		case 4:
			list.add("§f[§6§l+§f] 크리티컬 데미지 +" + integer);
			break;
			
		case 5:
			list.add("§f[§6§l+§f] 회피력 " + integer + "%");
			break;
			
		case 6:
			list.add("§f[§6§l+§f] 체력 흡수 +" + integer);
			break;
			
		default:
			return null;
		}
		
		switch (type) {
		case "최하":
			switch (intoption) {
			case 1:
				if (integer <= 9) list.add("§f[§6§l+§f] 등급 - §6★");
				else if (integer > 9) list.add("§f[§6§l+§f] 등급 - §6★★");
				break;
				
			case 2:
				if (integer <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
				else if (integer > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
				break;
				
			case 3:
				if (integer <= 14) list.add("§f[§6§l+§f] 등급 - §6★");
				else if (integer > 14) list.add("§f[§6§l+§f] 등급 - §6★★");
				break;
				
			case 4:
				if (integer <= 4) list.add("§f[§6§l+§f] 등급 - §6★");
				else if (integer > 4) list.add("§f[§6§l+§f] 등급 - §6★★");
				break;
				
			case 5:
				if (integer <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
				else if (integer > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
				break;
				
			case 6:
				if (integer <= 2) list.add("§f[§6§l+§f] 등급 - §6★");
				else if (integer > 2) list.add("§f[§6§l+§f] 등급 - §6★★");
				break;
			} break;
			
		case "하":
			switch (intoption) {
			case 1:
				if (integer <= 19) list.add("§f[§6§l+§f] 등급 - §6★★");
				else if (integer > 19) list.add("§f[§6§l+§f] 등급 - §6★★★");
				break;
				
			case 2:
				if (integer <= 3) list.add("§f[§6§l+§f] 등급 - §6★★");
				else if (integer > 3) list.add("§f[§6§l+§f] 등급 - §6★★★");
				break;
				
			case 3:
				if (integer <= 24) list.add("§f[§6§l+§f] 등급 - §6★★");
				else if (integer > 24) list.add("§f[§6§l+§f] 등급 - §6★★★");
				break;
				
			case 4:
				if (integer <= 9) list.add("§f[§6§l+§f] 등급 - §6★★");
				else if (integer > 9) list.add("§f[§6§l+§f] 등급 - §6★★★");
				break;
				
			case 5:
				if (integer <= 5) list.add("§f[§6§l+§f] 등급 - §6★★");
				else if (integer > 5) list.add("§f[§6§l+§f] 등급 - §6★★★");
				break;
				
			case 6:
				if (integer <= 4) list.add("§f[§6§l+§f] 등급 - §6★★");
				else if (integer > 4) list.add("§f[§6§l+§f] 등급 - §6★★★");
				break;
			} break;
			
		case "중":
			switch (intoption) {
			case 1:
				if (integer <= 31) list.add("§f[§6§l+§f] 등급 - §6★★★");
				else if (integer > 31) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				break;
				
			case 2:
				if (integer <= 5) list.add("§f[§6§l+§f] 등급 - §6★★★");
				else if (integer > 5) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				break;
				
			case 3:
				if (integer <= 34) list.add("§f[§6§l+§f] 등급 - §6★★★");
				else if (integer > 34) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				break;
				
			case 4:
				if (integer <= 24) list.add("§f[§6§l+§f] 등급 - §6★★★");
				else if (integer > 24) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				break;
				
			case 5:
				if (integer <= 11) list.add("§f[§6§l+§f] 등급 - §6★★★");
				else if (integer > 11) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				break;
				
			case 6:
				if (integer <= 7) list.add("§f[§6§l+§f] 등급 - §6★★★");
				else if (integer > 7) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				break;
			} break;
			
		case "상":
			switch (intoption) {
			case 1:
				if (integer <= 44) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				if (integer > 44) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				break;
				
			case 2:
				if (integer <= 9) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				if (integer > 9) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				break;
				
			case 3:
				if (integer <= 44) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				if (integer > 44) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				break;
				
			case 4:
				if (integer <= 44) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				if (integer > 44) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				break;
				
			case 5:
				if (integer <= 19) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				if (integer > 19) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				break;
				
			case 6:
				if (integer <= 11) list.add("§f[§6§l+§f] 등급 - §6★★★★");
				if (integer > 11) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				break;
			} break;
			
		case "최상":
			switch (intoption) {
			case 1:
				if (integer <= 59) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				if (integer > 59) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
				break;
				
			case 2:
				if (integer <= 14) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				if (integer > 14) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
				break;
				
			case 3:
				if (integer <= 54) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				if (integer > 54) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
				break;
				
			case 4:
				if (integer <= 65) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				if (integer > 65) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
				break;
				
			case 5:
				if (integer <= 27) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				if (integer > 27) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
				break;
				
			case 6:
				if (integer <= 19) list.add("§f[§6§l+§f] 등급 - §6★★★★★");
				if (integer > 19) list.add("§f[§6§l+§f] 등급 - §6★★★★★★");
				break;
			} break;
			
		default:
			return null;
		}
		
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("§6감정된 소켓석");
		item_Meta.setLore(list);
		item.setItemMeta(item_Meta);
		
		return item;
	}
}
