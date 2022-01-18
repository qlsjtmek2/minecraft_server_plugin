package me.espoo.score;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Method extends JavaPlugin {
	public static void CreatePlayerInfo(Player p, File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("MaxScore", 0);
			config.set("TotalScore", 0);
			config.set("Class", "��e�Թ���");
			config.set("�ʱ���", false);
			config.set("�߱���", false);
			config.set("�����", false);
			config.set("�ֻ����", false);
			config.set("������", false);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[FlyScore] Exception Occured: " + ioex.getMessage());
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
			 System.out.println("[FlyScore] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static int getRankingI(String name) {
		File f = new File("plugins/FlyScore/Ranking.yml");
		File folder = new File("plugins/FlyScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateRanking(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getRankingS(String name) {
		File f = new File("plugins/FlyScore/Ranking.yml");
		File folder = new File("plugins/FlyScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateRanking(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setRankingI(String name, int amount) {
		File f = new File("plugins/FlyScore/Ranking.yml");
		File folder = new File("plugins/FlyScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateRanking(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[FlyScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setRankingS(String name, String amount) {
		File f = new File("plugins/FlyScore/Ranking.yml");
		File folder = new File("plugins/FlyScore");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateRanking(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[FlyScore] Exception Occured: " + ioex.getMessage());
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
			System.out.println("[FlyScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addPlayerRank(Player p) {
		File f = new File("plugins/FlyScore/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FlyScore");
		File folder2 = new File("plugins/FlyScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			if ((getTotalScore(p) >= 80000) && (!(getTrueRank(p, "������"))))
			{
				config.set("Class", "��2������");
				truePlayerRank(p, "������", f, folder, folder2, config);
				truePlayerRank(p, "�ֻ����", f, folder, folder2, config);
				truePlayerRank(p, "�����", f, folder, folder2, config);
				truePlayerRank(p, "�߱���", f, folder, folder2, config);
				truePlayerRank(p, "�ʱ���", f, folder, folder2, config);
				Bukkit.broadcastMessage("��e---------------------------------------------------------");
				Bukkit.broadcastMessage("��f");
				Bukkit.broadcastMessage("��6�������ּ���! ��e[ " + p.getName() + " ] ��6����");
				Bukkit.broadcastMessage("��c80000 ���ھ��6�� �޼��� ��4������ ��6�� �Ǿ����ϴ�!");
				Bukkit.broadcastMessage("��f");
				Bukkit.broadcastMessage("��e---------------------------------------------------------");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " ������ world");
					p.chat("/manuadd " + p.getName() + " ������ world_fly");
					p.chat("/manuadd " + p.getName() + " ������ world_cookfly");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getTotalScore(p) >= 54000) && (!(getTrueRank(p, "�ֻ����"))))
			{
				config.set("Class", "��9�ֻ����");
				truePlayerRank(p, "�ֻ����", f, folder, folder2, config);
				truePlayerRank(p, "�����", f, folder, folder2, config);
				truePlayerRank(p, "�߱���", f, folder, folder2, config);
				truePlayerRank(p, "�ʱ���", f, folder, folder2, config);
				p.sendMessage("��6����� ��c54000 ���ھ��6�� �޼��� ��e�ֻ���� ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " �ֻ���� world");
					p.chat("/manuadd " + p.getName() + " �ֻ���� world_fly");
					p.chat("/manuadd " + p.getName() + " �ֻ���� world_cookfly");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getTotalScore(p) >= 26000) && (!(getTrueRank(p, "�����"))))
			{
				config.set("Class", "��5�����");
				truePlayerRank(p, "�����", f, folder, folder2, config);
				truePlayerRank(p, "�߱���", f, folder, folder2, config);
				truePlayerRank(p, "�ʱ���", f, folder, folder2, config);
				p.sendMessage("��6����� ��c26000 ���ھ��6�� �޼��� ��e����� ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " ����� world");
					p.chat("/manuadd " + p.getName() + " ����� world_fly");
					p.chat("/manuadd " + p.getName() + " ����� world_cookfly");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getTotalScore(p) >= 8000) && (!(getTrueRank(p, "�߱���"))))
			{
				config.set("Class", "��c�߱���");
				truePlayerRank(p, "�߱���", f, folder, folder2, config);
				truePlayerRank(p, "�ʱ���", f, folder, folder2, config);
				p.sendMessage("��6����� ��c8000 ���ھ��6�� �޼��� ��e�߱��� ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " �߱��� world");
					p.chat("/manuadd " + p.getName() + " �߱��� world_fly");
					p.chat("/manuadd " + p.getName() + " �߱��� world_cookfly");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getTotalScore(p) >= 2000) && (!(getTrueRank(p, "�ʱ���"))))
			{
				config.set("Class", "��d�ʱ���");
				truePlayerRank(p, "�ʱ���", f, folder, folder2, config);
				p.sendMessage("��6����� ��c2000 ���ھ��6�� �޼��� ��e�ʱ��� ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " �ʱ��� world");
					p.chat("/manuadd " + p.getName() + " �ʱ��� world_fly");
					p.chat("/manuadd " + p.getName() + " �ʱ��� world_cookfly");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else p.sendMessage("��c����� ��ũ�� �ø��� ���� �䱸������ �������� �ʾҽ��ϴ�.");
			

		} catch (IOException ioex) {
			System.out.println("[FlyScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static int getMaxScore(Player p) {
		File f = new File("plugins/FlyScore/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FlyScore");
		File folder2 = new File("plugins/FlyScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int MaxScore = config.getInt("MaxScore");
		return MaxScore;
	}
	
	public static boolean getTrueRank(Player p, String rank) {
		File f = new File("plugins/FlyScore/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FlyScore");
		File folder2 = new File("plugins/FlyScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		Boolean Rankamount = config.getBoolean(rank);
		return Rankamount;
	}
	
	public static int getTotalScore(Player p) {
		File f = new File("plugins/FlyScore/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FlyScore");
		File folder2 = new File("plugins/FlyScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int TotalScore = config.getInt("TotalScore");
		return TotalScore;
	}
	
	public static String getClass(Player p) {
		File f = new File("plugins/FlyScore/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FlyScore");
		File folder2 = new File("plugins/FlyScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		String Class = config.getString("Class");
		return Class;
	}
	
	public static void addTotalScore(Player p, int amount) {
		File f = new File("plugins/FlyScore/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FlyScore");
		File folder2 = new File("plugins/FlyScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int TotalScore = config.getInt("TotalScore");
			config.set("TotalScore", TotalScore + amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[FlyScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setMaxScore(Player p, int amount) {
		File f = new File("plugins/FlyScore/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/FlyScore");
		File folder2 = new File("plugins/FlyScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set("MaxScore", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[FlyScore] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
