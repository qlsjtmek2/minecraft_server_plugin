package me.shinkhan.epm;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class API {
	public static void sendCommand(String s) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	}
	
	public static String repixColor(String message) {
		return message.replaceAll("&", "§");
	}
	
	public static String getFinalArg(String[] args, int start) {
		StringBuilder bldr = new StringBuilder();
	    for (int i = start; i < args.length; i++) {
	    	if (i != start) {
	    		bldr.append(" ");
	    	} bldr.append(args[i]);
	    } return bldr.toString();
	}
	
	public static void reCreateFile(Player p) {
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHEchoPetManager");
		File folder2 = new File("plugins/DHEchoPetManager/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			PlayerYml.CreateInfo(f, folder, folder2, config);
			return;
		}
		
		f.delete();
		PlayerYml.CreateInfo(f, folder, folder2, config);
	}
	
	public static boolean isShowPet(Player p) {
		return PlayerYml.getBoolean(p, "펫 활성화 여부");
	}
	
	public static void setShowPet(Player p, boolean is) {
		PlayerYml.setBoolean(p, "펫 활성화 여부", is);
	}
	
	public static String getSetPet(Player p) {
		return PlayerYml.getString(p, "적용중인 펫");
	}
	
	public static void setSetPet(Player p, String pet) {
		PlayerYml.setString(p, "적용중인 펫", pet);
	}
	
	public static List<String> getPetList(Player p) {
		return PlayerYml.getList(p, "펫 목록");
	}
	
	public static void addPet(Player p, String name) {
		List<String> list = API.getPetList(p);
		if (!list.contains(name)) list.add(name);
		PlayerYml.setList(p, "펫 목록", list);
	}
	
	public static void subPet(Player p, String name) {
		List<String> list = API.getPetList(p);
		if (list.contains(name)) list.remove(name);
		PlayerYml.setList(p, "펫 목록", list);
	}
	
	public static boolean isPet(Player p, String name) {
		return API.getPetList(p).contains(name);
	}
	
	public static List<String> getNormalList() {
		return Config.getList("일반 펫");
	}
	
	public static List<String> getRareList() {
		return Config.getList("레어 펫");
	}
	
	public static List<String> getColorList() {
		return Config.getList("양 색깔 펫");
	}
	
	public static String rdomNormalPet() {
		List<String> list = API.getNormalList();
		return list.get(new Random().nextInt(list.size()));
	}
	
	public static String rdomRarePet() {
		List<String> list = API.getRareList();
		return list.get(new Random().nextInt(list.size()));
	}

	public static String rdomColorPet() {
		List<String> list = API.getColorList();
		return list.get(new Random().nextInt(list.size()));
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cast forcecast " + p.getName() + " lvup");
	}
	
	public static void removeCommandPet(Player p) {
		boolean is = p.isOp();
		p.setOp(true);
		Bukkit.getServer().dispatchCommand(p, "pet remove");
		API.removeOP(p, is);
	}
	
	public static void appCommandPet(Player p, String pet) {
		if (pet != null) {
			boolean is = p.isOp();
			p.setOp(true);
			Bukkit.getServer().dispatchCommand(p, "pet remove");
			Bukkit.getServer().dispatchCommand(p, "pet " + pet);
			if (API.getPetName(p) != null)
				Bukkit.getServer().dispatchCommand(p, "pet name " + API.getPetName(p));
			API.removeOP(p, is);
			API.setSetPet(p, pet);
		}
	}
	
	public static String getPetName(Player p) {
		return PlayerYml.getString(p, "펫 이름");
	}
	
	public static void setPetName(Player p, String name) {
		PlayerYml.setString(p, "펫 이름", name);
	}
	
	public static void removeOP(Player p, boolean is) {
		if (!is) if (p.isOnline()) p.setOp(false);
	}
	
	public static String PetToString(String pet) {
		switch (pet) {
		case "bat": return "박쥐";
		case "blaze": return "블레이즈";
		case "cavespider": return "동굴 거미";
		case "chicken": return "닭";
		case "chicken:baby": return "아기 닭";
		case "cow": return "소";
		case "cow:baby": return "아기 소";
		case "creeper": return "크리퍼";
		case "creeper:powered": return "번개맞은 크리퍼";
		case "enderdragon": return "엔더 드래곤";
		case "enderman": return "엔더 맨";
		case "ghast": return "가스트";
		case "irongolem": return "아이언 골렘";
		case "magmacube": return "마그마 큐브";
		case "magmacube:small": return "작은 마그마 큐브";
		case "magmacube:medium": return "마그마 큐브";
		case "magmacube:large": return "큰 마그마 큐브";
		case "mushroomcow": return "버섯 소";
		case "mushroomcow:baby": return "아기 버섯 소";
		case "ocelot": return "오셀롯";
		case "ocelot:baby": return "아기 오셀롯";
		case "ocelot:black": return "블랙 오셀롯";
		case "ocelot:red": return "레드 오셀롯";
		case "ocelot:siamese": return "점박이 오셀롯";
		case "ocelot:wild": return "야생 오셀롯";
		case "ocelot:baby:black": return "아기 블랙 오셀롯";
		case "ocelot:baby:red": return "아기 레드 오셀롯";
		case "ocelot:baby:siamese": return "아기 점박이 오셀롯";
		case "ocelot:baby:wild": return "아기 야생 오셀롯";
		case "pig": return "돼지";
		case "pig:baby": return "아기 돼지";
		case "pigzombie": return "돼지 좀비";
		case "pigzombie:baby": return "아기 돼지 좀비";
		case "silverfish": return "좀벌레";
		case "skeleton": return "스켈레톤";
		case "slime": return "슬라임";
		case "slime:small": return "작은 슬라임";
		case "slime:medium": return "슬라임";
		case "slime:large": return "큰 슬라임";
		case "snowman": return "눈사람";
		case "spider": return "거미";
		case "squid": return "오징어";
		case "witch": return "마녀";
		case "wither": return "위더";
		case "wolf": return "늑대";
		case "wolf:baby": return "아기 늑대";
		case "zombie": return "좀비";
		case "zombie:baby": return "아기 좀비";
		case "villager": return "주민";
		case "villager:blacksmith": return "대장장이 주민";
		case "villager:butcher": return "정육점 주민";
		case "villager:farmer": return "농부 주민";
		case "villager:librarian": return "사서 주민";
		case "villager:priest": return "성직자 주민";
		case "villager:baby": return "아기 주민";
		case "villager:baby:blacksmith": return "아기 대장장이 주민";
		case "villager:baby:butcher": return "아기 정육점 주민";
		case "villager:baby:farmer": return "아기 농부 주민";
		case "villager:baby:librarian": return "아기 사서 주민";
		case "villager:baby:priest": return "아기 성직자 주민";
		case "sheep": return "양";
		case "sheep:white": return "양";
		case "sheep:orange": return "주황색 양";
		case "sheep:magenta": return "자홍색 양";
		case "sheep:lightblue": return "하늘색 양";
		case "sheep:yellow": return "노랑색 양";
		case "sheep:lime": return "연두색 양";
		case "sheep:pink": return "분홍색 양";
		case "sheep:gray": return "회색 양";
		case "sheep:silver": return "은색 양";
		case "sheep:cyan": return "청록색 양";
		case "sheep:purple": return "보라색 양";
		case "sheep:blue": return "파랑색 양";
		case "sheep:brown": return "갈색 양";
		case "sheep:green": return "초록색 양";
		case "sheep:red": return "빨강색 양";
		case "sheep:black": return "검은색 양";
		case "sheep:baby": return "아기 양";
		case "sheep:baby:white": return "아기 양";
		case "sheep:baby:orange": return "아기 주황색 양";
		case "sheep:baby:magenta": return "아기 자홍색 양";
		case "sheep:baby:lightblue": return "아기 하늘색 양";
		case "sheep:baby:yellow": return "아기 노랑색 양";
		case "sheep:baby:lime": return "아기 연두색 양";
		case "sheep:baby:pink": return "아기 분홍색 양";
		case "sheep:baby:gray": return "아기 회색 양";
		case "sheep:baby:silver": return "아기 은색 양";
		case "sheep:baby:cyan": return "아기 청록색 양";
		case "sheep:baby:purple": return "아기 보라색 양";
		case "sheep:baby:blue": return "아기 파랑색 양";
		case "sheep:baby:brown": return "아기 갈색 양";
		case "sheep:baby:green": return "아기 초록색 양";
		case "sheep:baby:red": return "아기 빨강색 양";
		case "sheep:baby:black": return "아기 검은색 양";
		} return null;
	}
	
	public static String PetToPetCode(String pet) {
		switch (pet) {
		case "bat": return "383:65";
		case "blaze": return "383:61";
		case "cavespider": return "383:59";
		case "chicken": return "383:93";
		case "chicken:baby": return "383:93";
		case "cow": return "383:92";
		case "cow:baby": return "383:92";
		case "creeper": return "383:50";
		case "creeper:powered": return "383:50";
		case "enderdragon": return "122:0";
		case "enderman": return "383:58";
		case "ghast": return "383:56";
		case "irongolem": return "42:0";
		case "magmacube": return "383:62";
		case "magmacube:small": return "383:62";
		case "magmacube:medium": return "383:62";
		case "magmacube:large": return "383:62";
		case "mushroomcow": return "383:96";
		case "mushroomcow:baby": return "383:96";
		case "ocelot": return "383:98";
		case "ocelot:baby": return "383:98";
		case "ocelot:black": return "383:98";
		case "ocelot:red": return "383:98";
		case "ocelot:siamese": return "383:98";
		case "ocelot:wild": return "383:98";
		case "ocelot:baby:black": return "383:98";
		case "ocelot:baby:red": return "383:98";
		case "ocelot:baby:siamese": return "383:98";
		case "ocelot:baby:wild": return "383:98";
		case "pig": return "383:90";
		case "pig:baby": return "383:90";
		case "pigzombie": return "383:57";
		case "pigzombie:baby": return "383:57";
		case "silverfish": return "383:60";
		case "skeleton": return "383:51";
		case "slime": return "383:55";
		case "slime:small": return "383:55";
		case "slime:medium": return "383:55";
		case "slime:large": return "383:55";
		case "snowman": return "80:0";
		case "spider": return "383:52";
		case "squid": return "383:94";
		case "witch": return "373:0";
		case "wither": return "391:1";
		case "wolf": return "383:95";
		case "wolf:baby": return "383:95";
		case "zombie": return "383:54";
		case "zombie:baby": return "383:54";
		case "villager": return "383:120";
		case "villager:blacksmith": return "383:120";
		case "villager:butcher": return "383:120";
		case "villager:farmer": return "383:120";
		case "villager:librarian": return "383:120";
		case "villager:priest": return "383:120";
		case "villager:baby": return "383:120";
		case "villager:baby:blacksmith": return "383:120";
		case "villager:baby:butcher": return "383:120";
		case "villager:baby:farmer": return "383:120";
		case "villager:baby:librarian": return "383:120";
		case "villager:baby:priest": return "383:120";
		case "sheep": return "383:91";
		case "sheep:white": return "383:91";
		case "sheep:orange": return "383:91";
		case "sheep:magenta": return "383:91";
		case "sheep:lightblue": return "383:91";
		case "sheep:yellow": return "383:91";
		case "sheep:lime": return "383:91";
		case "sheep:pink": return "383:91";
		case "sheep:gray": return "383:91";
		case "sheep:silver": return "383:91";
		case "sheep:cyan": return "383:91";
		case "sheep:purple": return "383:91";
		case "sheep:blue": return "383:91";
		case "sheep:brown": return "383:91";
		case "sheep:green": return "383:91";
		case "sheep:red": return "383:91";
		case "sheep:black": return "383:91";
		case "sheep:baby": return "383:91";
		case "sheep:baby:white": return "383:91";
		case "sheep:baby:orange": return "383:91";
		case "sheep:baby:magenta": return "383:91";
		case "sheep:baby:lightblue": return "383:91";
		case "sheep:baby:yellow": return "383:91";
		case "sheep:baby:lime": return "383:91";
		case "sheep:baby:pink": return "383:91";
		case "sheep:baby:gray": return "383:91";
		case "sheep:baby:silver": return "383:91";
		case "sheep:baby:cyan": return "383:91";
		case "sheep:baby:purple": return "383:91";
		case "sheep:baby:blue": return "383:91";
		case "sheep:baby:brown": return "383:91";
		case "sheep:baby:green": return "383:91";
		case "sheep:baby:red": return "383:91";
		case "sheep:baby:black": return "383:91";
		} return null;
	}
}
