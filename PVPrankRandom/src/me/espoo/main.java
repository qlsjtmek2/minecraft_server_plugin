package me.espoo;

import java.io.File;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	  
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	  
	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{	
		try {
			if (!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + 
								   ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ��� �մϴ�.");
				return false;
			}
			  
			Player p = (Player) sender;
			if (commandLabel.equalsIgnoreCase("rank"))
			{
				if ((args.length == 0))
				{
					displayHelp(sender);
					return false;
				}
				  
				else if (args[0].equalsIgnoreCase("����") && args.length == 1)
				{
					sender.sendMessage("��e * " + p.getName() + " ��6���� ��cų ��6or ��c���� ��6����");
					sender.sendMessage("��e ������ ��cKILL ��7:: ��f" + Method.getPlayerKill(p));
					sender.sendMessage("��e ������ ��cDEATH ��7:: ��f" + Method.getPlayerDeath(p));
					return false;
				}
				  
				else if (args[0].equalsIgnoreCase("����") && args.length == 2)
				{
					if (p.isOp())
					{
						Player argp = Bukkit.getPlayer(args[1]);
						sender.sendMessage("��e * " + args[1] + " ��6���� ��cų ��6or ��c���� ��6����");
						sender.sendMessage("��e ������ ��cKILL ��7:: ��f" + Method.getPlayerKill(argp));
						sender.sendMessage("��e ������ ��cDEATH ��7:: ��f" + Method.getPlayerDeath(argp));
						return false;
					}
					
					if (Method.getPlayerSeeInfo(p))
					{
						Player argp = Bukkit.getPlayer(args[1]);
						sender.sendMessage("��e * " + args[1] + " ��6���� ��cų ��6or ��c���� ��6����");
						sender.sendMessage("��e ������ ��cKILL ��7:: ��f" + Method.getPlayerKill(argp));
						sender.sendMessage("��e ������ ��cDEATH ��7:: ��f" + Method.getPlayerDeath(argp));
						return false;
					}
					
					else
					{
						sender.sendMessage("��c������ ���� ���⸦ �㰡���� �ʾҽ��ϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("����") || args.length == 2)
				{
					if (args[1].equalsIgnoreCase("���"))
					{
						if (Method.getPlayerSeeInfo(p) == true)
						{
							sender.sendMessage("��c�̹� ���� ���⸦ ����ϼ̽��ϴ�.");
							return false;
						}
						  
						Method.setPlayerSeeInfo(p, true);
						sender.sendMessage("��6����� ������ ������ ���� ���� ��c����6�߽��ϴ�.");
						return false;
					}
					  
					else if (args[1].equalsIgnoreCase("�ź�"))
					{
						if (Method.getPlayerSeeInfo(p) == false)
						{
							sender.sendMessage("��c�̹� ���� ���⸦ �����ϼ̽��ϴ�.");
							return false;
						}
						  
						Method.setPlayerSeeInfo(p, false);
						sender.sendMessage("��6����� ������ ������ ���� ���� ��c�źΡ�6�߽��ϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("��޵�"))
				{
					sender.sendMessage("��6---------------------------------");
					sender.sendMessage("��e * ��7F ��f[��e5ų��f]-> ��eE");
					sender.sendMessage("��e * ��eE ��f[��e30ų��f]-> ��6D");
					sender.sendMessage("��e * ��6D ��f[��e60ų��f]-> ��bC");
					sender.sendMessage("��e * ��bC ��f[��e120ų��f]-> ��dB");
					sender.sendMessage("��e * ��dB ��f[��e300ų��f]-> ��cA");
					sender.sendMessage("��e * ��cA ��f[��e500ų��f]-> ��4S");
					sender.sendMessage("��e * ��4S ��f[��e800ų��f]-> ��4SS");
					sender.sendMessage("��6---------------------------------");
					return false;
				}
				 
				else if (args[0].equalsIgnoreCase("�±�"))
				{
					Method.addPlayerRank(p);
					return false;
				}
				  
				else if (args[0].equalsIgnoreCase("����"))
				{
					int one = Method.getRankingI("one");
					int two = Method.getRankingI("two");
					int three = Method.getRankingI("three");
					int four = Method.getRankingI("four");
					int five = Method.getRankingI("five");
					String oneName = Method.getRankingS("oneName");
					String twoName = Method.getRankingS("twoName");
					String threeName = Method.getRankingS("threeName");
					String fourName = Method.getRankingS("fourName");
					String fiveName = Method.getRankingS("fiveName");
					
					if (one == 0 && two == 0 && three == 0 && four == 0 && five == 0)
					{
						sender.sendMessage("��e---------------------------------------------------");
						sender.sendMessage("��f ���� ��ũ�� ���� ����� �������� �ʽ��ϴ�.");
						sender.sendMessage("��e---------------------------------------------------");
						return false;
					}
					
					sender.sendMessage("��e---------------------------------------------------");
					if (oneName.equals("NONE") == false) sender.sendMessage("��6 �� ��cRANK 1�� ��7::��f " + oneName + " / " + one);
					if (twoName.equals("NONE") == false) sender.sendMessage("��6 ���� ��cRANK 2�� ��7::��f " + twoName + " / " + two);
					if (threeName.equals("NONE") == false) sender.sendMessage("��6 ������ ��cRANK 3�� ��7::��f " + threeName + " / " + three);
					if (fourName.equals("NONE") == false) sender.sendMessage("��6 �������� ��cRANK 4�� ��7::��f " + fourName + " / " + four);
					if (fiveName.equals("NONE") == false) sender.sendMessage("��6 ���������� ��cRANK 5�� ��7::��f " + fiveName + " / " + five);
					sender.sendMessage("��e---------------------------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (!p.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 4))
					{
						sender.sendMessage("��6/RANK <����/�߰�/����> <Kill/Death> <�г���> <��>");
						return false;
					}
					
					File f = new File("plugins/PVPrank/Player/" + args[2] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[3]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					
					if (args[1].equalsIgnoreCase("Kill"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setPlayerKill(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ų ���� ��c" + args[3] + " ��6��ŭ �����Ͽ����ϴ�.");
						return false;
					}
					
					else if (args[1].equalsIgnoreCase("Death"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setPlayerDeath(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ���� ���� ��c" + args[3] + " ��6��ŭ �����Ͽ����ϴ�.");
						return false;
					}
					
					else
					{
						sender.sendMessage("��6/RANK <����/�߰�/����> <Kill/Death> <�г���> <��>");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("�߰�"))
				{
					if (!p.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 4))
					{
						sender.sendMessage("��6/RANK <����/�߰�/����> <Kill/Death> <�г���> <��>");
						return false;
					}
					
					File f = new File("plugins/PVPrank/Player/" + args[2] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[3]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					
					if (args[1].equalsIgnoreCase("Kill"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setAddPlayerKill(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ų ���� ��c" + args[3] + " ��6��ŭ �߰��Ͽ����ϴ�.");
						return false;
					}
					
					else if (args[1].equalsIgnoreCase("Death"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setAddPlayerDeath(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ���� ���� ��c" + args[3] + " ��6��ŭ �߰��Ͽ����ϴ�.");
						return false;
					}
					
					else
					{
						sender.sendMessage("��6/RANK <����/�߰�/����> <Kill/Death> <�г���> <��>");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (!p.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 4))
					{
						sender.sendMessage("��6/RANK <����/�߰�/����> <Kill/Death> <�г���> <��>");
						return false;
					}
					
					File f = new File("plugins/PVPrank/Player/" + args[2] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[3]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					
					if (args[1].equalsIgnoreCase("Kill"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setSubPlayerKill(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ų ���� ��c" + args[3] + " ��6��ŭ �Q���ϴ�.");
						return false;
					}
					
					else if (args[1].equalsIgnoreCase("Death"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setSubPlayerDeath(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ���� ���� ��c" + args[3] + " ��6��ŭ �Q���ϴ�.");
						return false;
					}
					
					else
					{
						sender.sendMessage("��6/RANK <����/�߰�/����> <Kill/Death> <�г���> <��>");
						return false;
					}
				}
			}
		} catch (NumberFormatException ex) {
			displayHelp(sender);
			return false;
		}
		
		return false;
	}
	  
	private void displayHelp(CommandSender sender)
	{
		Player p = (Player) sender;
		sender.sendMessage(" ��e----- ��6PVP RANK ��e--- ��6���� ��e-----");
		sender.sendMessage("  ��e-- ��6����� ����� " + Method.getPlayerRank(p) + " ��6�Դϴ�. ��e--");
		sender.sendMessage("��6/RANK ��f- PVP RANK ������ ���ϴ�.");
		sender.sendMessage("��6/RANK �±� ��f- �ڽź��� �Ѵܰ� ���� �ܰ�� �±��մϴ�.");
		sender.sendMessage("��6/RANK ���� ��f- �ڽ��� ų �� ������ ���ϴ�.");
		sender.sendMessage("��6/RANK ���� <�г���> ��f- �ٸ� ����� ų �� ������ ���ϴ�.");
		sender.sendMessage("��6/RANK ���� ��f- ų ������ TOP 3���� ���ϴ�.");
		sender.sendMessage("��6/RANK ���� <���/�ź�> ��f- ���� �ڽ��� ������ ���� ���� �����մϴ�. ��b( �⺻�� ����� )");
		sender.sendMessage("��6/RANK ��޵� ��f- ��ũ�� ��޵��� ���ϴ�.");
		if (p.isOp())
		{
			sender.sendMessage("��f");
			sender.sendMessage("��b/RANK <����/�߰�/����> <Kill/Death> <�г���> <��>");
		}
	}
	
	public void RankingStat(Player p) {
		int killd = Method.getPlayerKill(p);
		int one = Method.getRankingI("one");
		int two = Method.getRankingI("two");
		int three = Method.getRankingI("three");
		int four = Method.getRankingI("four");
		int five = Method.getRankingI("five");
		String oneName = Method.getRankingS("oneName");
		String twoName = Method.getRankingS("twoName");
		String threeName = Method.getRankingS("threeName");
		String fourName = Method.getRankingS("fourName");
		String fiveName = Method.getRankingS("fiveName");
		
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!threeName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (killd > one)
			{
				int oneJunkI = Method.getRankingI("one");
				String oneJunkS = Method.getRankingS("oneName");
				int twoJunkI = Method.getRankingI("two");
				String twoJunkS = Method.getRankingS("twoName");
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (oneJunkS.equalsIgnoreCase(p.getName())) oneJunkS = "NONE";
				else if (twoJunkS.equalsIgnoreCase(p.getName())) twoJunkS = "NONE";
				else if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("one", killd);
				Method.setRankingS("oneName", p.getName());
				Method.setRankingI("two", oneJunkI);
				Method.setRankingS("twoName", oneJunkS);
				Method.setRankingI("three", twoJunkI);
				Method.setRankingS("threeName", twoJunkS);
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("��f[��4�˸���f] ��bPVP ��ŷ 1���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
			
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (killd > two && killd <= one)
			{
				int twoJunkI = Method.getRankingI("two");
				String twoJunkS = Method.getRankingS("twoName");
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (twoJunkS.equalsIgnoreCase(p.getName())) twoJunkS = "NONE";
				else if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("two", killd);
				Method.setRankingS("twoName", p.getName());
				Method.setRankingI("three", twoJunkI);
				Method.setRankingS("threeName", twoJunkS);
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("��f[��4�˸���f] ��bPVP ��ŷ 2���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
			
		if (!fiveName.equalsIgnoreCase(p.getName()) && !threeName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (killd > three && killd <= two)
			{
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("three", killd);
				Method.setRankingS("threeName", p.getName());
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("��f[��4�˸���f] ��bPVP ��ŷ 3���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
			
		if (!fourName.equalsIgnoreCase(p.getName()) && !threeName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (killd > four && killd <= three)
			{
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("four", killd);
				Method.setRankingS("fourName", p.getName());
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("��f[��4�˸���f] ��bPVP ��ŷ 4���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
		
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!threeName.equalsIgnoreCase(p.getName()) && !twoName.equalsIgnoreCase(p.getName()) && 
			!oneName.equalsIgnoreCase(p.getName())) {
			if (killd > five && killd <= four)
			{
				Method.setRankingI("five", killd);
				Method.setRankingS("fiveName", p.getName());
				Bukkit.broadcastMessage("��f[��4�˸���f] ��bPVP ��ŷ 5���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/PVPrank/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PVPrank");
		File folder2 = new File("plugins/PVPrank/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Method.CreatePlayerInfo(p, f, folder, folder2, config);
			if (!p.isOp()) {
				p.setOp(true);
				p.chat("/Īȣ �߰� " + p.getName() + " &f[&7-&8F&7-&f]");
				p.setOp(false);
			}
		}
	}
	
	@EventHandler
	public void PlayerDeath(PlayerDeathEvent e) {
		PlayerDeathEvent Event0 = (PlayerDeathEvent) e;
		Player killed = Event0.getEntity();
		Player killer = (Player) killed.getKiller();
		if (killer == null) return;
		if (killed.getWorld().getName().equalsIgnoreCase("world_pvp"))
		{
			Method.addPlayerDeath(killed);
			Method.addPlayerKill(killer);
			RankingStat(killer);
		}
		
		if (Method.getRankingS("oneName").equalsIgnoreCase(killer.getName())) Method.setRankingI("one", Method.getPlayerKill(killer));
		else if (Method.getRankingS("twoName").equalsIgnoreCase(killer.getName())) Method.setRankingI("two", Method.getPlayerKill(killer));
		else if (Method.getRankingS("threeName").equalsIgnoreCase(killer.getName())) Method.setRankingI("three", Method.getPlayerKill(killer));
		else if (Method.getRankingS("fourName").equalsIgnoreCase(killer.getName())) Method.setRankingI("four", Method.getPlayerKill(killer));
		else if (Method.getRankingS("fiveName").equalsIgnoreCase(killer.getName())) Method.setRankingI("five", Method.getPlayerKill(killer));
	}
}