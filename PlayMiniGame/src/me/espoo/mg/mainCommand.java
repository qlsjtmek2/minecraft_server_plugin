package me.espoo.mg;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.mg.Data.GameData;
import me.espoo.mg.Game.Anvil_Game;
import me.espoo.mg.Game.Bomb_Game;
import me.espoo.mg.Game.Climb_Game;
import me.espoo.mg.Game.OneKnife_Game;
import me.espoo.mg.Game.Running_Game;
import me.espoo.mg.Game.SpeedShot_Game;
import me.espoo.mg.Game.Spleef_Game;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;

	public mainCommand(main instance)
	{
		this.plugin = instance;
	}
	
	@SuppressWarnings({ "resource", "static-access" })
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if(commandLabel.equalsIgnoreCase("점수") || commandLabel.equalsIgnoreCase("스코어") 
			|| commandLabel.equalsIgnoreCase("score") || commandLabel.equalsIgnoreCase("wjatn") || commandLabel.equalsIgnoreCase("tmzhdj")) {
				if(args.length == 0) {
					displayHelp2(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("정보") && args.length == 2)
				{
					File f = new File("plugins/PlayMiniGame/Player/" + args[1] + ".yml");
					if (!f.exists()) {
						sender.sendMessage("해당 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Player argp = Bukkit.getPlayer(args[1]);
					sender.sendMessage("§e * " + args[1] + " §6님의 §c스코어 §6정보");
					sender.sendMessage("§e ┗━━ §c점수 §7:: §f" + Method.getInfoInt(argp, "점수"));
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
					if (!sender.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage("§6/점수 <설정/추가/빼기> <닉네임> <값> §f- 플레이어의 점수를 설정합니다.");
						return false;
					}
					
					File f = new File("plugins/PlayMiniGame/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					

					Player argsP = Bukkit.getPlayer(args[1]);
					Method.setInfoInt(argsP, "점수", Integer.parseInt(args[2]));
					sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 점수를 §c" + args[2] + " §6만큼 설정하였습니다.");
					Ranking.RankingStat(argsP);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("추가"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage("§6/점수 <설정/추가/빼기> <닉네임> <값> §f- 플레이어의 점수를 설정합니다.");
						return false;
					}
					
					File f = new File("plugins/PlayMiniGame/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					Player argsP = Bukkit.getPlayer(args[1]);
					int Score = Method.getInfoInt(argsP, "점수") + Integer.parseInt(args[2]);
					Method.setInfoInt(argsP, "점수", Score);
					sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 점수를 §c" + args[2] + " §6만큼 추가하였습니다.");
					Ranking.RankingStat(argsP);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("빼기"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage("§6/점수 <설정/추가/빼기> <닉네임> <값> §f- 플레이어의 점수를 설정합니다.");
						return false;
					}
					
					File f = new File("plugins/PlayMiniGame/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					Player argsP = Bukkit.getPlayer(args[1]);
					int Score = Method.getInfoInt(argsP, "점수") - Integer.parseInt(args[2]);
					if (Score < 0) Score = 0;
					Method.setInfoInt(argsP, "점수", Score);
					sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 점수를 §c" + args[2] + " §6만큼 뺐습니다.");
					Ranking.RankingStat(argsP);
					return false;
				}
				
				if ((sender instanceof Player))
				{
					Player p = (Player) sender;
					
					if (args[0].equalsIgnoreCase("정보") && args.length == 1)
					{
						sender.sendMessage("§e * §6당신의 §c스코어 §6정보");
						sender.sendMessage("§e ┗━━ §c점수 §7:: §f" + Method.getInfoInt(p, "점수"));
						return false;
					}
				}
			}
		} catch (NumberFormatException ex) {
			displayHelp2(sender);
			return false;
		}
		
		try {
			if(commandLabel.equalsIgnoreCase("미니게임") || commandLabel.equalsIgnoreCase("mg") || commandLabel.equalsIgnoreCase("MiniGame")) {
				if (!sender.isOp()) {
					sender.sendMessage(main.w + "§c당신은 권한이 없습니다.");
					return false;
				}
				
				if (!(sender instanceof Player))
				{
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + 
							   		   ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능 합니다.");
					return false;
				}
				
				Player p = (Player) sender;
				
				if(args.length == 0) {
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("런닝"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "§c플레이어§6가 §c2명 미만§6이므로 게임을 시작할 수 없습니다.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(1);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("모루피하기"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "§c플레이어§6가 §c2명 미만§6이므로 게임을 시작할 수 없습니다.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(2);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("등반"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "§c플레이어§6가 §c2명 미만§6이므로 게임을 시작할 수 없습니다.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(3);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("스플리프"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "§c플레이어§6가 §c2명 미만§6이므로 게임을 시작할 수 없습니다.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(4);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("한방칼전"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "§c플레이어§6가 §c2명 미만§6이므로 게임을 시작할 수 없습니다.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(5);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("스피드샷건"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "§c플레이어§6가 §c2명 미만§6이므로 게임을 시작할 수 없습니다.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(6);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("붐붐붐"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "§c플레이어§6가 §c2명 미만§6이므로 게임을 시작할 수 없습니다.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(7);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("랜덤"))
				{
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "§c플레이어§6가 §c2명 미만§6이므로 게임을 시작할 수 없습니다.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					int RandomNum = new Random().nextInt(7) + 1;
					GameData.GameStart(RandomNum);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("강제종료"))
				{
					if (GameData.gameState) {
						GameData.GameStop();
				    	Bukkit.broadcastMessage("");
				    	Bukkit.broadcastMessage(main.p + "§c관리자§6가 진행중인 §c미니게임 §6을 강제로 종료시켰습니다.");
				    	Bukkit.broadcastMessage("");
						return false;
					} else {
						p.sendMessage(main.w + "§c지급은 게임을 종료하실 수 없습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("맵선택이동"))
				{
					if (GameData.mapWark == false) {
						GameData.mapWark = true;
						GameData.mapPlayer = p.getName();
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp MAP " + p.getName());
						p.sendMessage(main.a + "§6당신은 §c미니게임 선택존§6에 입장하셨습니다.");
						p.sendMessage(main.a + "§6하고싶은 §c미니게임§6을 골라 §c물포탈§6에 입장하세요!");
			         	Block block1 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵1 다이아블럭"));
			         	Block block2 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵2 다이아블럭"));
			         	Block block3 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵3 다이아블럭"));
			         	block1.setType(Material.AIR);
			           	block2.setType(Material.AIR);
			           	block3.setType(Material.AIR);

			           	for (Player player : Bukkit.getOnlinePlayers()) {
			           		player.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 120.0f, 200.0f);
			           	} return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("로그인"))
				{
					if (GameData.gameStart) {
						switch (GameData.gameNum)
						{
							case 1:
								new Running_Game().OdiePlayer(p); break;
							case 2:
								new Anvil_Game().OdiePlayer(p); break;
							case 3:
								new Climb_Game().OdiePlayer(p); break;
							case 4:
								new Spleef_Game().OdiePlayer(p); break;
							case 5:
								new OneKnife_Game().OdiePlayer(p); break;
							case 6:
								new SpeedShot_Game().OdiePlayer(p); break;
							case 7:
								new Bomb_Game().OdiePlayer(p); break;
						}
					}
				}
				
				else if (args[0].equalsIgnoreCase("스폰블럭밟기"))
				{
					if (GameData.gameStart && GameData.gameState) {
				        switch (GameData.gameNum) {
				        	case 1:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("런닝 게임 관전"));
				        		else Running_Game.OdiePlayer(p);
				        		break;
				        	case 2:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("모루피하기 게임 관전"));
				        		else Anvil_Game.OdiePlayer(p);
				        		break;
				        	case 3:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("등반 게임 관전"));
				        		else Climb_Game.OdiePlayer(p);
				        		break;
				         	case 4:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("스플리프 게임 관전"));
				        		else Spleef_Game.OdiePlayer(p);
				        		break;
				        	case 5:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("한방칼전 게임 관전"));
				        		else OneKnife_Game.OdiePlayer(p);
				        		break;
				        	case 6:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("스피드샷건 게임 관전"));
				        		else SpeedShot_Game.OdiePlayer(p);
				        		break;
				        	case 7:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("붐붐붐 게임 관전"));
				        		else Bomb_Game.OdiePlayer(p);
				        		break;
				        } return false;
					}
					
					else if (GameData.gameStart && GameData.gameState == false) {
						Method.GotoMap(p);
						p.sendMessage(main.a + "§c신호기§6를 밟아 맵을 고르는 곳으로 이동해보세요!");
						return false;
					}
					
					else if (GameData.gameStart == false && GameData.gameState == false) {
						p.sendMessage(main.a + "§c신호기§6를 밟아 맵을 고르는 곳으로 이동해보세요!");
						
						Method.GotoMap(p);
						return false;
					}
					
					else return false;
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
		sender.sendMessage(" §e---- §6미니게임 §e-- §6페이지 §c1§6/§c1 §e----");
		sender.sendMessage("§6/미니게임, /MiniGmae, /MG §f- 미니게임 명령어를 봅니다.");
		sender.sendMessage("§6/미니게임 런닝 §f- 런닝을 시작합니다.");
		sender.sendMessage("§6/미니게임 모루피하기 §f- 모루피하기를 시작합니다.");
		sender.sendMessage("§6/미니게임 등반 §f- 등반을 시작합니다.");
		sender.sendMessage("§6/미니게임 스플리프 §f- 스플리프를 시작합니다.");
		sender.sendMessage("§6/미니게임 한방칼전 §f- 한방칼전을 시작합니다.");
		sender.sendMessage("§6/미니게임 스피드샷건 §f- 스피드샷건을 시작합니다.");
		sender.sendMessage("§6/미니게임 붐붐붐 §f- 붐붐붐을 시작합니다.");
		sender.sendMessage("§6/미니게임 랜덤 §f- 랜덤으로 맵을 선택해서 시작합니다.");
		sender.sendMessage("§6/미니게임 강제종료 §f- 게임을 강제종료한다.");
		sender.sendMessage("§6/미니게임 맵선택이동 §f- 맵을 선택하는 곳으로 이동한다.");
	}
	
	private void displayHelp2(CommandSender sender)
	{
		Player p = (Player) sender;
		sender.sendMessage(" §e----- §6미니게임 스코어 §e--- §6도움말 §e-----");
		sender.sendMessage("§6/점수, /스코어, /score §f- 미니게임 스코어 도움말을 봅니다.");
		sender.sendMessage("§6/점수 정보 §f- 자신의 정보를 확인합니다.");
		sender.sendMessage("§6/점수 정보 <닉네임> §f- 다른 사람의 정보를 확인합니다.");
		sender.sendMessage("§6/점수 순위 §f- 스코어 순위권 TOP 5명을 봅니다.");
		if (p.isOp())
		{
			sender.sendMessage("§f");
			sender.sendMessage("§b/점수 <설정/추가/빼기> <닉네임> <값> §f- 플레이어의 점수를 설정합니다.");
		}
	}
}
