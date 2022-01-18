package me.espoo.pvp;

import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.pvp.ranking.Ranking;
import me.espoo.pvp.yml.PVPPlayer;
import me.espoo.rpg2.Method;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("pvp")) {
				if (!(sender instanceof Player)) {
					return false;
				}
				
				Player p = (Player) sender;
				
				if (Method.getInfoInt(p, "전직 카운트") == 1) {
					p.sendMessage("§e----------------------------------------------------------------");
					p.sendMessage("");
					p.sendMessage("§6아슬아슬한 §c초급 PvP장§6에 입장하셨습니다!");
					p.sendMessage("");
					p.sendMessage("§e----------------------------------------------------------------");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp PvP_world " + p.getName());
					return false;
				}

				else if (Method.getInfoInt(p, "전직 카운트") == 2) {
					int RandomAmount = new Random().nextInt(4) + 1;
					p.sendMessage("§e----------------------------------------------------------------");
					p.sendMessage("");
					p.sendMessage("§6영광스러운 §c콜로세움§6에 입장하셨습니다!");
					p.sendMessage("");
					p.sendMessage("§e----------------------------------------------------------------");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp pvp_" + RandomAmount + " " + p.getName());
					return false;
				}

				else if (Method.getInfoInt(p, "전직 카운트") == 3) {
					int RandomAmount = new Random().nextInt(2) + 1;
					p.sendMessage("§e----------------------------------------------------------------");
					p.sendMessage("");
					p.sendMessage("§6위험한 §cBlock PvP장§6에 입장하셨습니다!");
					p.sendMessage("");
					p.sendMessage("§e----------------------------------------------------------------");
					switch (RandomAmount) {
					case 1:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp PvP_blue " + p.getName());
						break;
					case 2:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp PvP_red " + p.getName());
					}
					return false;
				}
				
				else if (Method.getInfoInt(p, "전직 카운트") == 4) {
					int RandomAmount = new Random().nextInt(4) + 1;
					p.sendMessage("§e----------------------------------------------------------------");
					p.sendMessage("");
					p.sendMessage("§6화려한 §c궁전§6에 입장하셨습니다!");
					p.sendMessage("");
					p.sendMessage("§e----------------------------------------------------------------");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp " + RandomAmount + ".PVP " + p.getName());
					return false;
				}
				
				else {
					if (p.isOp()) {
						p.sendMessage("§e----------------------------------------------------------------");
						p.sendMessage("");
						p.sendMessage("§6아슬아슬한 §c초급 PvP장§6에 입장하셨습니다!");
						p.sendMessage("");
						p.sendMessage("§e----------------------------------------------------------------");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp PvP_world " + p.getName());
						return false;
					}
					
					p.sendMessage("§6당신은 §c권한이 없으므로 §6접속하실 수 없습니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6/pvp §f- pvp장으로 전직에 맞게 이동합니다.");
			return false;
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("대련") || commandLabel.equalsIgnoreCase("대전")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						Help(p, commandLabel);
						return true;
					}
					
					else if (args.length >= 1) {
						if (args[0].equalsIgnoreCase("매칭") || args[0].equalsIgnoreCase("gui")) {
							if (API.isUserVSMatch(p)) {
								GUI.stopGUI(p);
								return false;
							}
							
							GUI.openGUI(p);
							return true;
						}
						
						else if (args[0].equalsIgnoreCase("전적") || args[0].equalsIgnoreCase("view")) {
							PVPPlayer pp = API.getPVPPlayera(p.getName());
					        p.sendMessage("§6당신의 클래스: " + pp.getclassExpress());
					        p.sendMessage("§6당신의 킬: §c" + pp.getKill());
					        p.sendMessage("§6당신의 데스: §c" + pp.getDeath());
					        p.sendMessage("§6당신의 목숨당 처치: §c" + pp.getKillDivDeath());
					        return true;
						}
						
						else if (args[0].equalsIgnoreCase("랭킹") || args[0].equalsIgnoreCase("rank")) {
							int listlen = 0;
							if (args.length == 1) {
								listlen = 1;
							} else if ((args.length == 2) && (args[1].matches("^[0-9]+$"))) {
								listlen = Integer.valueOf(args[1]).intValue();
							} else {
								Help(p, commandLabel);
								return true;
							}
							
							Ranking.displayRanking(sender, listlen);
							return true;
						}
						
						else if (args[0].equalsIgnoreCase("검색") || args[0].equalsIgnoreCase("searchrank")) {
							if (args.length == 2) {
								boolean is = PlayerYml.isUser(p.getName());
								if (!is)
									p.sendMessage("§c그 플레이어는 존재하지 않습니다.");
								else
									Ranking.searchRanking(sender, p.getName());
								return false;
							} else {
								Help(p, commandLabel);
								return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("계급도") || args[0].equalsIgnoreCase("class")) 	{
							p.sendMessage("§6---------------------------------");
							p.sendMessage("§e * §7F §f[§e5킬§f]-> §eE");
							p.sendMessage("§e * §eE §f[§e30킬§f]-> §6D");
							p.sendMessage("§e * §6D §f[§e60킬§f]-> §bC");
							p.sendMessage("§e * §bC §f[§e120킬§f]-> §dB");
							p.sendMessage("§e * §dB §f[§e300킬§f]-> §cA");
							p.sendMessage("§e * §cA §f[§e500킬§f]-> §4S");
							p.sendMessage("§e * §4S §f[§e800킬§f]-> §4§lSS");
							p.sendMessage("§6---------------------------------");
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("승급") || args[0].equalsIgnoreCase("upgrade")) {
							API.Upgrade(p);
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("B") || args[0].equalsIgnoreCase("A") || args[0].equalsIgnoreCase("S") || args[0].equalsIgnoreCase("SS")) {
							PVPPlayer pp = API.getPVPPlayera(p.getName());
							if (pp.getclass().equalsIgnoreCase(args[0])) {
								p.chat("/kit " + args[0]);
								return false;
							} else {
								p.sendMessage("§c당신의 랭크가 맞지 않아 보상을 받으실 수 없습니다.");
								return false;
							}
						}
					
						else if (args[0].equalsIgnoreCase("업데이트") || args[0].equalsIgnoreCase("update")) {
							if (!p.isOp()) {
								sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
								return false;
							}
							
							Ranking.updateRanking();
					        for (PVPPlayer player : API.pvpplayer.values()) {
					            player.saveRpgPlayer();
					        }
					        main.user.saveConfig();
					        System.out.println("[ActionPvP] 유저 파일 자동 저장 완료");
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("설정") || args[0].equalsIgnoreCase("set")) {
							if (!p.isOp()) {
								sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
								return false;
							}
							
							if (!(args.length == 4)) {
								Help(p, commandLabel);
								return false;
							}
							
							if (!PlayerYml.isUser(args[2])) {
								sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							Scanner scan = new Scanner(args[3]);
							if (!scan.hasNextInt()) {
								sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
								return false;
							}

							String name = Bukkit.getPlayer(args[2]).getName();
							PVPPlayer pp = API.getPVPPlayera(name);
							if (args[1].equalsIgnoreCase("킬") || args[1].equalsIgnoreCase("Kill")) {
								pp.setKill(Integer.parseInt(args[3]));
								sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 킬 수를 §c" + args[3] + " §6만큼 설정하였습니다.");
								return false;
							}
							
							else if (args[1].equalsIgnoreCase("데스") || args[1].equalsIgnoreCase("Death")) {
								pp.setDeath(Integer.parseInt(args[3]));
								sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 데스 수를 §c" + args[3] + " §6만큼 설정하였습니다.");
								return false;
							}
							
							else {
								Help(p, commandLabel);
								return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("추가")) {
							if (!p.isOp()) {
								sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
								return false;
							}
							
							if (!(args.length == 4)) {
								Help(p, commandLabel);
								return false;
							}
							
							if (!PlayerYml.isUser(args[2])) {
								sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							Scanner scan = new Scanner(args[3]);
							if (!scan.hasNextInt()) {
								sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
								return false;
							}
							
							String name = Bukkit.getPlayer(args[2]).getName();
							PVPPlayer pp = API.getPVPPlayera(name);
							if (args[1].equalsIgnoreCase("킬") || args[1].equalsIgnoreCase("Kill")) {
								pp.addKill(Integer.parseInt(args[3]));
								sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 킬 수를 §c" + args[3] + " §6만큼 추가하였습니다.");
								return false;
							}
							
							else if (args[1].equalsIgnoreCase("데스") || args[1].equalsIgnoreCase("Death")) {
								pp.addDeath(Integer.parseInt(args[3]));
								sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 데스 수를 §c" + args[3] + " §6만큼 추가하였습니다.");
								return false;
							}
							
							else {
								Help(p, commandLabel);
								return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("빼기")) {
							if (!p.isOp()) {
								sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
								return false;
							}
							
							if (!(args.length == 4)) {
								Help(p, commandLabel);
								return false;
							}
							
							if (!PlayerYml.isUser(args[2])) {
								sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							Scanner scan = new Scanner(args[3]);
							if (!scan.hasNextInt()) {
								sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
								return false;
							}
							
							String name = Bukkit.getPlayer(args[2]).getName();
							PVPPlayer pp = API.getPVPPlayera(name);
							if (args[1].equalsIgnoreCase("킬") || args[1].equalsIgnoreCase("Kill")) {
								pp.subKill(Integer.parseInt(args[3]));
								sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 킬 수를 §c" + args[3] + " §6만큼 뻈습니다.");
								return false;
							}
							
							else if (args[1].equalsIgnoreCase("데스") || args[1].equalsIgnoreCase("Death")) {
								pp.subDeath(Integer.parseInt(args[3]));
								sender.sendMessage("§6성공적으로 §c" + args[2] + "§6님의 데스 수를 §c" + args[3] + " §6만큼 뻈습니다.");
								return false;
							}
							
							else {
								Help(p, commandLabel);
								return false;
							}
						}
					}
					
					return false;
				} else {
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("대련위치") || commandLabel.equalsIgnoreCase("대전위치") || commandLabel.equalsIgnoreCase("PVP위치")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						HelpMessage(sender);
						return false;
					}
					
					else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("1:1")) {
							switch (args[1]) {
								case "1":
									Position.setOneRed(p.getLocation());
									p.sendMessage("§6성공적으로 1:1 대련 §c첫번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "2":
									Position.setOneBlue(p.getLocation());
									p.sendMessage("§6성공적으로 1:1 대련 §c두번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								default: HelpMessage(sender); return false;
							}
						}
					} return false;
				} else {
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("경기취소") || commandLabel.equalsIgnoreCase("대련취소") || commandLabel.equalsIgnoreCase("대전취소")) {
				if (args.length == 0) {
					if (!sender.isOp()) {
						sender.sendMessage(RED + "당신은 권한이 없으므로 이 명령어를 실행할 수 없습니다.");
						return false;
					}
					
					if (!main.oneVS) {
						sender.sendMessage(RED + "현재 1:1 대련중인 사람이 없습니다.");
						return false;
					}
					
					Player o1 = Bukkit.getPlayerExact(main.oneName.split(",")[0]);
					Player o2 = Bukkit.getPlayerExact(main.oneName.split(",")[1]);
					if (o1 != null && o2 != null) {
						o1.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
						o2.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
						main.oneType = -1;
						main.oneVS = false;
						main.oneName = null;
						API.sendCommand("spawn " + o1.getName());
						API.sendCommand("spawn " + o2.getName());
						API.sendCommand("heal " + o1.getName());
						API.sendCommand("heal " + o2.getName());
						API.oneStopAPI(o1, o2);
						return false;
					} else {
						sender.sendMessage(RED + "ERROR: 대련중이긴 하나 대련중인 플레이어를 찾을 수 없음");
						return false;
					}
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Help(p, commandLabel);
				return true;
			} else {
				return false;
			}
		} return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage("§e ---- §6대련 위치 설정 §e-- §6페이지 §c1§6/§c1 §e----");
		sender.sendMessage("§6/대련위치, /대전위치, /PVP위치 §f- 대련 위치 설정 도움말을 봅니다.");
		sender.sendMessage("§6/대련위치 1:1 <1/2> §f- 1:1 대련 위치를 설정합니다.");
	}
	
	public static void Help(Player sender, String command) {
		if (command.equalsIgnoreCase("대련") || command.equalsIgnoreCase("대전")) {
			sender.sendMessage("§e ---- §6대련 §e-- §6페이지 §c1§6/§c1 §e----");
			sender.sendMessage(" §e-- §6당신의 클래스는 " + API.getPVPPlayera(sender.getName()).getclassExpress() + " §6입니다. §e--");
			sender.sendMessage("§6/대련, /대전 §f- 대련 도움말을 봅니다.");
			sender.sendMessage("§c§l/PVP §f- 대난투에 바로 입장합니다.");
			sender.sendMessage("§c§l/" + command + " 매칭 §f- PVP 매칭을 할 수 있는 창을 띄웁니다.");
			sender.sendMessage("§6/" + command + " 전적 §f- 자신의 PVP 전적을 확인합니다.");
			sender.sendMessage("§6/" + command + " 랭킹 §f- 플레이어들의 PVP 랭킹을 확인합니다.");
			sender.sendMessage("§6/" + command + " 검색 <닉네임> §f- 유저의 PVP 랭킹 순위를 검색합니다.");
			sender.sendMessage("§6/" + command + " 승급 §f- 자신의 PVP 클래스를 조건이 맞을 시 승급시킵니다.");
			sender.sendMessage("§6/" + command + " <B/A/S/SS> §f- 4시간마다 랭크 전용 보상을 받습니다");
			sender.sendMessage("§6/" + command + " 계급도 §f- 클래스의 계급도를 봅니다.");
			if (sender.isOp())
			{
				sender.sendMessage("§f");
				sender.sendMessage("§b/" + command + " <설정/추가/빼기> <킬/데스> <닉네임> <값>");
				sender.sendMessage("§b/" + command + " 업데이트 §f- 랭킹을 업데이트합니다.");
				sender.sendMessage("§b/경기취소, 대련취소, 대전취소 §f- 1:1 대련을 취소합니다.");
			}
			sender.sendMessage("§f");
			sender.sendMessage("§e* B 클래스 이상부터는 명령어로 보상을 받으실 수 있습니다.");
		}
	}
}
