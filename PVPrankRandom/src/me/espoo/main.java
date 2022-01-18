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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	  
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	  
	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{	
		try {
			if (!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + 
								   ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능 합니다.");
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
				  
				else if (args[0].equalsIgnoreCase("전적") && args.length == 1)
				{
					sender.sendMessage("§e * " + p.getName() + " §6님의 §c킬 §6or §c데스 §6전적");
					sender.sendMessage("§e ┗━━ §cKILL §7:: §f" + Method.getPlayerKill(p));
					sender.sendMessage("§e ┗━━ §cDEATH §7:: §f" + Method.getPlayerDeath(p));
					return false;
				}
				  
				else if (args[0].equalsIgnoreCase("전적") && args.length == 2)
				{
					if (p.isOp())
					{
						Player argp = Bukkit.getPlayer(args[1]);
						sender.sendMessage("§e * " + args[1] + " §6님의 §c킬 §6or §c데스 §6전적");
						sender.sendMessage("§e ┗━━ §cKILL §7:: §f" + Method.getPlayerKill(argp));
						sender.sendMessage("§e ┗━━ §cDEATH §7:: §f" + Method.getPlayerDeath(argp));
						return false;
					}
					
					if (Method.getPlayerSeeInfo(p))
					{
						Player argp = Bukkit.getPlayer(args[1]);
						sender.sendMessage("§e * " + args[1] + " §6님의 §c킬 §6or §c데스 §6전적");
						sender.sendMessage("§e ┗━━ §cKILL §7:: §f" + Method.getPlayerKill(argp));
						sender.sendMessage("§e ┗━━ §cDEATH §7:: §f" + Method.getPlayerDeath(argp));
						return false;
					}
					
					else
					{
						sender.sendMessage("§c상대방이 전적 보기를 허가하지 않았습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("보기") || args.length == 2)
				{
					if (args[1].equalsIgnoreCase("허용"))
					{
						if (Method.getPlayerSeeInfo(p) == true)
						{
							sender.sendMessage("§c이미 전적 보기를 허용하셨습니다.");
							return false;
						}
						  
						Method.setPlayerSeeInfo(p, true);
						sender.sendMessage("§6당신은 상대방이 전적을 보는 것을 §c허용§6했습니다.");
						return false;
					}
					  
					else if (args[1].equalsIgnoreCase("거부"))
					{
						if (Method.getPlayerSeeInfo(p) == false)
						{
							sender.sendMessage("§c이미 전적 보기를 불허하셨습니다.");
							return false;
						}
						  
						Method.setPlayerSeeInfo(p, false);
						sender.sendMessage("§6당신은 상대방이 전적을 보는 것을 §c거부§6했습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("계급도"))
				{
					sender.sendMessage("§6---------------------------------");
					sender.sendMessage("§e * §7F §f[§e5킬§f]-> §eE");
					sender.sendMessage("§e * §eE §f[§e30킬§f]-> §6D");
					sender.sendMessage("§e * §6D §f[§e60킬§f]-> §bC");
					sender.sendMessage("§e * §bC §f[§e120킬§f]-> §dB");
					sender.sendMessage("§e * §dB §f[§e300킬§f]-> §cA");
					sender.sendMessage("§e * §cA §f[§e500킬§f]-> §4S");
					sender.sendMessage("§e * §4S §f[§e800킬§f]-> §4SS");
					sender.sendMessage("§6---------------------------------");
					return false;
				}
				 
				else if (args[0].equalsIgnoreCase("승급"))
				{
					Method.addPlayerRank(p);
					return false;
				}
				  
				else if (args[0].equalsIgnoreCase("순위"))
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
						sender.sendMessage("§e---------------------------------------------------");
						sender.sendMessage("§f 현재 랭크에 오른 사람이 존재하지 않습니다.");
						sender.sendMessage("§e---------------------------------------------------");
						return false;
					}
					
					sender.sendMessage("§e---------------------------------------------------");
					if (oneName.equals("NONE") == false) sender.sendMessage("§6 ┗ §cRANK 1위 §7::§f " + oneName + " / " + one);
					if (twoName.equals("NONE") == false) sender.sendMessage("§6 ┗━ §cRANK 2위 §7::§f " + twoName + " / " + two);
					if (threeName.equals("NONE") == false) sender.sendMessage("§6 ┗━━ §cRANK 3위 §7::§f " + threeName + " / " + three);
					if (fourName.equals("NONE") == false) sender.sendMessage("§6 ┗━━━ §cRANK 4위 §7::§f " + fourName + " / " + four);
					if (fiveName.equals("NONE") == false) sender.sendMessage("§6 ┗━━━━ §cRANK 5위 §7::§f " + fiveName + " / " + five);
					sender.sendMessage("§e---------------------------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("설정"))
				{
					if (!p.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 4))
					{
						sender.sendMessage("§6/RANK <설정/추가/빼기> <Kill/Death> <닉네임> <값>");
						return false;
					}
					
					File f = new File("plugins/PVPrank/Player/" + args[2] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[3]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					if (args[1].equalsIgnoreCase("Kill"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setPlayerKill(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 킬 수를 §c" + args[3] + " §6만큼 설정하였습니다.");
						return false;
					}
					
					else if (args[1].equalsIgnoreCase("Death"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setPlayerDeath(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 데스 수를 §c" + args[3] + " §6만큼 설정하였습니다.");
						return false;
					}
					
					else
					{
						sender.sendMessage("§6/RANK <설정/추가/빼기> <Kill/Death> <닉네임> <값>");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("추가"))
				{
					if (!p.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 4))
					{
						sender.sendMessage("§6/RANK <설정/추가/빼기> <Kill/Death> <닉네임> <값>");
						return false;
					}
					
					File f = new File("plugins/PVPrank/Player/" + args[2] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[3]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					if (args[1].equalsIgnoreCase("Kill"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setAddPlayerKill(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 킬 수를 §c" + args[3] + " §6만큼 추가하였습니다.");
						return false;
					}
					
					else if (args[1].equalsIgnoreCase("Death"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setAddPlayerDeath(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 데스 수를 §c" + args[3] + " §6만큼 추가하였습니다.");
						return false;
					}
					
					else
					{
						sender.sendMessage("§6/RANK <설정/추가/빼기> <Kill/Death> <닉네임> <값>");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("빼기"))
				{
					if (!p.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 4))
					{
						sender.sendMessage("§6/RANK <설정/추가/빼기> <Kill/Death> <닉네임> <값>");
						return false;
					}
					
					File f = new File("plugins/PVPrank/Player/" + args[2] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[3]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					if (args[1].equalsIgnoreCase("Kill"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setSubPlayerKill(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 킬 수를 §c" + args[3] + " §6만큼 뻈습니다.");
						return false;
					}
					
					else if (args[1].equalsIgnoreCase("Death"))
					{
						Player argsP = Bukkit.getPlayer(args[2]);
						Method.setSubPlayerDeath(argsP, Integer.parseInt(args[3]));
						sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 데스 수를 §c" + args[3] + " §6만큼 뻈습니다.");
						return false;
					}
					
					else
					{
						sender.sendMessage("§6/RANK <설정/추가/빼기> <Kill/Death> <닉네임> <값>");
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
		sender.sendMessage(" §e----- §6PVP RANK §e--- §6도움말 §e-----");
		sender.sendMessage("  §e-- §6당신의 계급은 " + Method.getPlayerRank(p) + " §6입니다. §e--");
		sender.sendMessage("§6/RANK §f- PVP RANK 도움말을 봅니다.");
		sender.sendMessage("§6/RANK 승급 §f- 자신보다 한단계 높은 단계로 승급합니다.");
		sender.sendMessage("§6/RANK 전적 §f- 자신의 킬 뎃 전적을 봅니다.");
		sender.sendMessage("§6/RANK 전적 <닉네임> §f- 다른 사람의 킬 뎃 전적을 봅니다.");
		sender.sendMessage("§6/RANK 순위 §f- 킬 순위권 TOP 3명을 봅니다.");
		sender.sendMessage("§6/RANK 보기 <허용/거부> §f- 남이 자신의 전적을 보는 것을 설정합니다. §b( 기본값 비허용 )");
		sender.sendMessage("§6/RANK 계급도 §f- 랭크의 계급도를 봅니다.");
		if (p.isOp())
		{
			sender.sendMessage("§f");
			sender.sendMessage("§b/RANK <설정/추가/빼기> <Kill/Death> <닉네임> <값>");
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
				Bukkit.broadcastMessage("§f[§4알림§f] §bPVP 랭킹 1위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
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
				Bukkit.broadcastMessage("§f[§4알림§f] §bPVP 랭킹 2위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
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
				Bukkit.broadcastMessage("§f[§4알림§f] §bPVP 랭킹 3위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
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
				Bukkit.broadcastMessage("§f[§4알림§f] §bPVP 랭킹 4위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
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
				Bukkit.broadcastMessage("§f[§4알림§f] §bPVP 랭킹 5위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
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
				p.chat("/칭호 추가 " + p.getName() + " &f[&7-&8F&7-&f]");
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