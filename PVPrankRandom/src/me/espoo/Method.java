package me.espoo;

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
			config.set("Kill", 0);
			config.set("Death", 0);
			config.set("Seehandr", false);
			config.set("Rank", "§7F");
			config.set("F", true);
			config.set("E", false);
			config.set("D", false);
			config.set("C", false);
			config.set("B", false);
			config.set("A", false);
			config.set("S", false);
			config.set("SS", false);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
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
			 System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static int getRankingI(String name) {
		File f = new File("plugins/PVPrank/Ranking.yml");
		File folder = new File("plugins/PVPrank");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateRanking(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getRankingS(String name) {
		File f = new File("plugins/PVPrank/Ranking.yml");
		File folder = new File("plugins/PVPrank");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateRanking(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setRankingI(String name, int amount) {
		File f = new File("plugins/PVPrank/Ranking.yml");
		File folder = new File("plugins/PVPrank");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateRanking(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setRankingS(String name, String amount) {
		File f = new File("plugins/PVPrank/Ranking.yml");
		File folder = new File("plugins/PVPrank");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateRanking(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addPlayerKill(Player p) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int Killamount = config.getInt("Kill");
			config.set("Kill", ++Killamount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setPlayerKill(Player p, int amount) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set("Kill", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setPlayerDeath(Player p, int amount) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set("Death", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setAddPlayerKill(Player p, int amount) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int KillAmount = config.getInt("Kill");
			config.set("Kill", KillAmount + amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setAddPlayerDeath(Player p, int amount) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int DeathAmount = config.getInt("Death");
			config.set("Death", DeathAmount + amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setSubPlayerKill(Player p, int amount) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int KillAmount = config.getInt("Kill");
			KillAmount = KillAmount - amount;
			if (KillAmount < 0) KillAmount = 0;
			config.set("Kill", KillAmount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setSubPlayerDeath(Player p, int amount) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int DeathAmount = config.getInt("Death");
			DeathAmount = DeathAmount - amount;
			if (DeathAmount < 0) DeathAmount = 0;
			config.set("Death", DeathAmount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addPlayerDeath(Player p) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			int Deathamount = config.getInt("Death");
			config.set("Death", ++Deathamount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setPlayerSeeInfo(Player p, Boolean amount) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set("Seehandr", amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setPlayerRank(Player p, String Rank) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			String Rankamount = Cutter(Rank);
			truePlayerRank(p, Rankamount, f, folder, folder2, config);
			config.set("Rank", Rank);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static String Cutter(String Rank)
	{
		String[] Cut;
		Cut = Rank.split("7");
		Cut = Rank.split("e");
		Cut = Rank.split("6");
		Cut = Rank.split("b");
		Cut = Rank.split("d");
		Cut = Rank.split("c");
		Cut = Rank.split("4");
		return Cut[1];
	}
	
	public static void truePlayerRank(Player p, String rank, File f, File folder, File folder2, YamlConfiguration config) {
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			if (rank.equals("E")) config.set("E", true);
			else if (rank.equals("D")) config.set("D", true);
			else if (rank.equals("C")) config.set("C", true);
			else if (rank.equals("B")) config.set("B", true);
			else if (rank.equals("A")) config.set("A", true);
			else if (rank.equals("S")) config.set("S", true);
			else if (rank.equals("SS")) config.set("SS", true);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static boolean getTrueRank(Player p, String rank) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		Boolean Rankamount = config.getBoolean(rank);
		return Rankamount;
	}
	
	public static void addPlayerRank(Player p) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			if ((getPlayerKill(p) >= 800) && (!(getTrueRank(p, "SS"))))
			{
				config.set("Rank", "§4SS");
				truePlayerRank(p, "SS", f, folder, folder2, config);
				truePlayerRank(p, "S", f, folder, folder2, config);
				truePlayerRank(p, "A", f, folder, folder2, config);
				truePlayerRank(p, "B", f, folder, folder2, config);
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				Bukkit.broadcastMessage("§e---------------------------------------------------------");
				Bukkit.broadcastMessage("§f");
				Bukkit.broadcastMessage("§6축하해주세요! §e[ " + p.getName() + " ] §6님이");
				Bukkit.broadcastMessage("§c800킬§6을 달성해 §4SS RANK §6가 되었습니다!");
				Bukkit.broadcastMessage("§f");
				Bukkit.broadcastMessage("§e---------------------------------------------------------");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " SS world");
					p.chat("/manuadd " + p.getName() + " SS world_pvp");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getPlayerKill(p) >= 500) && (!(getTrueRank(p, "S"))))
			{
				config.set("Rank", "§4S");
				truePlayerRank(p, "S", f, folder, folder2, config);
				truePlayerRank(p, "A", f, folder, folder2, config);
				truePlayerRank(p, "B", f, folder, folder2, config);
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("§6당신은 §c500킬§6을 달성해 §eS RANK §6가 되었습니다.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " S world");
					p.chat("/manuadd " + p.getName() + " S world_pvp");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getPlayerKill(p) >= 300) && (!(getTrueRank(p, "A"))))
			{
				config.set("Rank", "§cA");
				truePlayerRank(p, "A", f, folder, folder2, config);
				truePlayerRank(p, "B", f, folder, folder2, config);
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("§6당신은 §c300킬§6을 달성해 §cA RANK §6가 되었습니다.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " A world");
					p.chat("/manuadd " + p.getName() + " A world_pvp");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getPlayerKill(p) >= 120) && (!(getTrueRank(p, "B"))))
			{
				config.set("Rank", "§dB");
				truePlayerRank(p, "B", f, folder, folder2, config);
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("§6당신은 §c120킬§6을 달성해 §dB RANK §6가 되었습니다.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " B world");
					p.chat("/manuadd " + p.getName() + " B world_pvp");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getPlayerKill(p) >= 60) && (!(getTrueRank(p, "C"))))
			{
				config.set("Rank", "§bC");
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("§6당신은 §c60킬§6을 달성해 §bC RANK §6가 되었습니다.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " C world");
					p.chat("/manuadd " + p.getName() + " C world_pvp");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getPlayerKill(p) >= 30) && (!(getTrueRank(p, "D"))))
			{
				config.set("Rank", "§6D");
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("§6당신은 §c30킬§6을 달성해 §6D RANK §6가 되었습니다.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " D world");
					p.chat("/manuadd " + p.getName() + " D world_pvp");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else if ((getPlayerKill(p) >= 5) && (!(getTrueRank(p, "E"))))
			{
				config.set("Rank", "§eE");
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("§6당신은 §c5킬§6을 달성해 §eE RANK §6가 되었습니다.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " E world");
					p.chat("/manuadd " + p.getName() + " E world_pvp");
					p.setOp(false);
				}
				config.save(f);
				return;
			}
			
			else p.sendMessage("§c당신은 랭크를 올리기 위한 요구조건이 만족되지 않았습니다.");
			

		} catch (IOException ioex) {
			System.out.println("[PVPrank] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static int getPlayerKill(Player p) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int Killamount = config.getInt("Kill");
		return Killamount;
	}
	
	public static int getPlayerDeath(Player p) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int Deathamount = config.getInt("Death");
		return Deathamount;
	}
	
	public static boolean getPlayerSeeInfo(Player p) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		boolean Seehandr = config.getBoolean("Seehandr");
		return Seehandr;
	}
	
	public static String getPlayerRank(Player p) {
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		String RankString = config.getString("Rank");
		return RankString;
	}
}
