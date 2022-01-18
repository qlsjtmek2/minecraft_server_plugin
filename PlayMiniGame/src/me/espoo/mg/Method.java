package me.espoo.mg;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.mg.Data.GameData;

public class Method extends JavaPlugin {
	public static void CreateConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("��1 ���̾ƺ�", "world_map, 1661, 35, 345, 0, 0");
			config.set("��2 ���̾ƺ�", "world_map, 1647, 44, -49, 0, 0");
			config.set("��3 ���̾ƺ�", "world_map, 1979, 47, -87, 0, 0");
			config.set("���� ���� ����", "world, 1061, 19, 614, 1, 31");
			config.set("���� ���� �̵�", "1061 19 614");
			config.set("���� ���� ����", "world, 1061, 29, 614, 1, 31");
			config.set("������ϱ� ���� ����", "world, 1015, 28, 979, 2, 63");
			config.set("������ϱ� ���� �̵�", "1015 28 979");
			config.set("������ϱ� ���� ����", "world, 1015, 36, 979, 2, 63");
			config.set("��� ���� ����", "world, 554, 41, 941, 2, 79");
			config.set("��� ���� �̵�", "554 41 941");
			config.set("��� ���� ����", "world, 554, 49, 941, 2, 79");
			config.set("���ø��� ���� ����", "world, 1212, 59, 841, 0, 91");
			config.set("���ø��� ���� �̵�", "1212 59 841");
			config.set("���ø��� ���� ����", "world, 1212, 64, 841, 0, 91");
			config.set("�ѹ�Į�� ���� ����", "world, 350, 40, 1147, 0, 63");
			config.set("�ѹ�Į�� ���� �̵�", "350 43 1147");
			config.set("�ѹ�Į�� ���� ����", "world, 350, 43, 1147, 0, 63");
			config.set("���ǵ弦�� ���� ����", "world, 1335, 30, 635, 1, 47");
			config.set("���ǵ弦�� ���� �̵�", "1335 30 635");
			config.set("���ǵ弦�� ���� ����", "world, 1335, 40, 635, 1, 47");
			config.set("�պպ� ���� ����", "world, 1156, 37, 1215, 0, 79");
			config.set("�պպ� ���� �̵�", "1156 37 1215");
			config.set("�պպ� ���� ����", "world, 1156, 37, 1215, 0, 79");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateSchemFolder(File folder, File folder2)
	{
		folder.mkdir();
		folder2.mkdir();
	}
	
	public static Location getLocation(String name) {
		File f = new File("plugins/PlayMiniGame/config.yml");
		File folder = new File("plugins/PlayMiniGame");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		String[] Data = config.getString(name).split(", ");
		World world = Bukkit.getWorld(Data[0]);
		double x = Double.parseDouble(Data[1]);
		double y = Double.parseDouble(Data[2]);
		double z = Double.parseDouble(Data[3]);
		float yaw = Float.parseFloat(Data[4]);
		float pitch = Float.parseFloat(Data[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	public static String getWarp(String name) {
		File f = new File("plugins/PlayMiniGame/config.yml");
		File folder = new File("plugins/PlayMiniGame");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateConfig(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void ChatClean()
	{
		for (int i = 0; i < 100; i++) Bukkit.broadcastMessage("");
	}
	
	public static void SetMap()
	{
		int RandomInt = new Random().nextInt(3) + 1;
		GameData.mapNum = RandomInt;
	}
	
	public static void GotoMap(Player p)
	{
		int RandomInt = new Random().nextInt(4) + 1;
        switch (GameData.mapNum)
        {
        	case 1:
        		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp OneMap" + RandomInt + " " + p.getName());
        		break;
        		
        	case 2:
        		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp TwoMap" + RandomInt + " " + p.getName());
        		break;
        		
        	case 3:
        		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ThreeMap" + RandomInt + " " + p.getName());
        		break;
        }
	}
	
	public static void CreatePlayerInfo(Player p, File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("����", 0);
			config.set("Class", "��e�Թ���");
			config.set("�ʱ���", false);
			config.set("�߱���", false);
			config.set("�����", false);
			config.set("�ֻ����", false);
			config.set("������", false);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateRanking(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("one", 0);
			config.set("two", 0);
			config.set("three", 0);
			config.set("four", 0);
			config.set("five", 0);
			config.set("oneName", "NONE");
			config.set("twoName", "NONE");
			config.set("threeName", "NONE");
			config.set("fourName", "NONE");
			config.set("fiveName", "NONE");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static int getRankingI(String name) {
		File f = new File("plugins/PlayMiniGame/Ranking.yml");
		File folder = new File("plugins/PlayMiniGame");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateRanking(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getRankingS(String name) {
		File f = new File("plugins/PlayMiniGame/Ranking.yml");
		File folder = new File("plugins/PlayMiniGame");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateRanking(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setRankingI(String name, int amount) {
		File f = new File("plugins/PlayMiniGame/Ranking.yml");
		File folder = new File("plugins/PlayMiniGame");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateRanking(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setRankingS(String name, String amount) {
		File f = new File("plugins/PlayMiniGame/Ranking.yml");
		File folder = new File("plugins/PlayMiniGame");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateRanking(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void truePlayerRank(Player p, String rank, File f, File folder, File folder2, YamlConfiguration config) {
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			if (rank.equals("�ʱ���")) config.set("�ʱ���", true);
			else if (rank.equals("�߱���")) config.set("�߱���", true);
			else if (rank.equals("�����")) config.set("�����", true);
			else if (rank.equals("�ֻ����")) config.set("�ֻ����", true);
			else if (rank.equals("������")) config.set("������", true);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static boolean getTrueRank(Player p, String rank) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		Boolean Rankamount = config.getBoolean(rank);
		return Rankamount;
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, Boolean amount) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addScoreYes(Player p, int score, String Message) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 100.0f, 100.0f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int i = getInfoInt(p, "����") + score;
			config.set("����", i);
			config.save(f);
			Ranking.RankingStat(p);
			p.sendMessage(main.a + Message + " ��c" + score + " ��6������ ȹ���ϼ̽��ϴ�.");
			

		} catch (IOException ioex) {
			System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addScoreNo(Player p, int score) {
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 100.0f, 100.0f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int i = getInfoInt(p, "����") + score;
			config.set("����", i);
			config.save(f);
			Ranking.RankingStat(p);

		} catch (IOException ioex) {
			System.out.println("[PlayMiniGame] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addMoneyYes(Player p, int money) {
		p.sendMessage(main.a + "��6�� ��c" + money + " ��6���� ȹ���ϼ̽��ϴ�.");
		main.economy.depositPlayer(p.getName(), money);
	}
	
	public static void addMoneyNo(Player p, int money) {
		main.economy.depositPlayer(p.getName(), money);
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
}
