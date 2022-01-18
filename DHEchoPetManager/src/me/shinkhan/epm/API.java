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
		return message.replaceAll("&", "��");
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
		return PlayerYml.getBoolean(p, "�� Ȱ��ȭ ����");
	}
	
	public static void setShowPet(Player p, boolean is) {
		PlayerYml.setBoolean(p, "�� Ȱ��ȭ ����", is);
	}
	
	public static String getSetPet(Player p) {
		return PlayerYml.getString(p, "�������� ��");
	}
	
	public static void setSetPet(Player p, String pet) {
		PlayerYml.setString(p, "�������� ��", pet);
	}
	
	public static List<String> getPetList(Player p) {
		return PlayerYml.getList(p, "�� ���");
	}
	
	public static void addPet(Player p, String name) {
		List<String> list = API.getPetList(p);
		if (!list.contains(name)) list.add(name);
		PlayerYml.setList(p, "�� ���", list);
	}
	
	public static void subPet(Player p, String name) {
		List<String> list = API.getPetList(p);
		if (list.contains(name)) list.remove(name);
		PlayerYml.setList(p, "�� ���", list);
	}
	
	public static boolean isPet(Player p, String name) {
		return API.getPetList(p).contains(name);
	}
	
	public static List<String> getNormalList() {
		return Config.getList("�Ϲ� ��");
	}
	
	public static List<String> getRareList() {
		return Config.getList("���� ��");
	}
	
	public static List<String> getColorList() {
		return Config.getList("�� ���� ��");
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
		return PlayerYml.getString(p, "�� �̸�");
	}
	
	public static void setPetName(Player p, String name) {
		PlayerYml.setString(p, "�� �̸�", name);
	}
	
	public static void removeOP(Player p, boolean is) {
		if (!is) if (p.isOnline()) p.setOp(false);
	}
	
	public static String PetToString(String pet) {
		switch (pet) {
		case "bat": return "����";
		case "blaze": return "������";
		case "cavespider": return "���� �Ź�";
		case "chicken": return "��";
		case "chicken:baby": return "�Ʊ� ��";
		case "cow": return "��";
		case "cow:baby": return "�Ʊ� ��";
		case "creeper": return "ũ����";
		case "creeper:powered": return "�������� ũ����";
		case "enderdragon": return "���� �巡��";
		case "enderman": return "���� ��";
		case "ghast": return "����Ʈ";
		case "irongolem": return "���̾� ��";
		case "magmacube": return "���׸� ť��";
		case "magmacube:small": return "���� ���׸� ť��";
		case "magmacube:medium": return "���׸� ť��";
		case "magmacube:large": return "ū ���׸� ť��";
		case "mushroomcow": return "���� ��";
		case "mushroomcow:baby": return "�Ʊ� ���� ��";
		case "ocelot": return "������";
		case "ocelot:baby": return "�Ʊ� ������";
		case "ocelot:black": return "�� ������";
		case "ocelot:red": return "���� ������";
		case "ocelot:siamese": return "������ ������";
		case "ocelot:wild": return "�߻� ������";
		case "ocelot:baby:black": return "�Ʊ� �� ������";
		case "ocelot:baby:red": return "�Ʊ� ���� ������";
		case "ocelot:baby:siamese": return "�Ʊ� ������ ������";
		case "ocelot:baby:wild": return "�Ʊ� �߻� ������";
		case "pig": return "����";
		case "pig:baby": return "�Ʊ� ����";
		case "pigzombie": return "���� ����";
		case "pigzombie:baby": return "�Ʊ� ���� ����";
		case "silverfish": return "������";
		case "skeleton": return "���̷���";
		case "slime": return "������";
		case "slime:small": return "���� ������";
		case "slime:medium": return "������";
		case "slime:large": return "ū ������";
		case "snowman": return "�����";
		case "spider": return "�Ź�";
		case "squid": return "��¡��";
		case "witch": return "����";
		case "wither": return "����";
		case "wolf": return "����";
		case "wolf:baby": return "�Ʊ� ����";
		case "zombie": return "����";
		case "zombie:baby": return "�Ʊ� ����";
		case "villager": return "�ֹ�";
		case "villager:blacksmith": return "�������� �ֹ�";
		case "villager:butcher": return "������ �ֹ�";
		case "villager:farmer": return "��� �ֹ�";
		case "villager:librarian": return "�缭 �ֹ�";
		case "villager:priest": return "������ �ֹ�";
		case "villager:baby": return "�Ʊ� �ֹ�";
		case "villager:baby:blacksmith": return "�Ʊ� �������� �ֹ�";
		case "villager:baby:butcher": return "�Ʊ� ������ �ֹ�";
		case "villager:baby:farmer": return "�Ʊ� ��� �ֹ�";
		case "villager:baby:librarian": return "�Ʊ� �缭 �ֹ�";
		case "villager:baby:priest": return "�Ʊ� ������ �ֹ�";
		case "sheep": return "��";
		case "sheep:white": return "��";
		case "sheep:orange": return "��Ȳ�� ��";
		case "sheep:magenta": return "��ȫ�� ��";
		case "sheep:lightblue": return "�ϴû� ��";
		case "sheep:yellow": return "����� ��";
		case "sheep:lime": return "���λ� ��";
		case "sheep:pink": return "��ȫ�� ��";
		case "sheep:gray": return "ȸ�� ��";
		case "sheep:silver": return "���� ��";
		case "sheep:cyan": return "û�ϻ� ��";
		case "sheep:purple": return "����� ��";
		case "sheep:blue": return "�Ķ��� ��";
		case "sheep:brown": return "���� ��";
		case "sheep:green": return "�ʷϻ� ��";
		case "sheep:red": return "������ ��";
		case "sheep:black": return "������ ��";
		case "sheep:baby": return "�Ʊ� ��";
		case "sheep:baby:white": return "�Ʊ� ��";
		case "sheep:baby:orange": return "�Ʊ� ��Ȳ�� ��";
		case "sheep:baby:magenta": return "�Ʊ� ��ȫ�� ��";
		case "sheep:baby:lightblue": return "�Ʊ� �ϴû� ��";
		case "sheep:baby:yellow": return "�Ʊ� ����� ��";
		case "sheep:baby:lime": return "�Ʊ� ���λ� ��";
		case "sheep:baby:pink": return "�Ʊ� ��ȫ�� ��";
		case "sheep:baby:gray": return "�Ʊ� ȸ�� ��";
		case "sheep:baby:silver": return "�Ʊ� ���� ��";
		case "sheep:baby:cyan": return "�Ʊ� û�ϻ� ��";
		case "sheep:baby:purple": return "�Ʊ� ����� ��";
		case "sheep:baby:blue": return "�Ʊ� �Ķ��� ��";
		case "sheep:baby:brown": return "�Ʊ� ���� ��";
		case "sheep:baby:green": return "�Ʊ� �ʷϻ� ��";
		case "sheep:baby:red": return "�Ʊ� ������ ��";
		case "sheep:baby:black": return "�Ʊ� ������ ��";
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
