package me.espoo.score;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.PartyAPI;

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
			if (commandLabel.equalsIgnoreCase("등록")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (PlayerYml.getInfoBoolean(p, "등록")) {
						p.sendMessage("§c당신은 이미 프로 히어로 협회에 등록되어 있습니다.");
						return false;
					}
					
					if (PlayerYml.getInfoBoolean(p, "등록여부")) {
						Method.castLvup(p);
						p.sendMessage("§6성공적으로 프로 히어로 협회에 §c등록§6하셨습니다!");
						PlayerYml.setInfoBoolean(p, "등록", true);
						return false;
					}

					p.sendMessage("");
					p.sendMessage("§c당신은 아직 프로 히어로 협회에 등록할 수 없습니다.");
					p.sendMessage("§f- §c일정 스토리 퀘스트를 달성해야만 등록이 가능합니다.");
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				sender.sendMessage("§6/등록 §f- 프로 히어로 협회에 등록합니다.");
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("점수")) {
				if (args.length == 0) {
					if (sender instanceof Player) {
						displayHelp(sender);
						return false;
					} else {
						displayConsol(sender);
						return false;
					}
				}
				
				if (args[0].equalsIgnoreCase("확인") && args.length == 2 || args[0].equalsIgnoreCase("전적") && args.length == 2) {
					Player p = Method.getOnorOffLine(Method.searchOnlinePlayer(args[1]));
					
					if (p == null) {
						sender.sendMessage(RED + "이 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					if (sender.isOp())
					{
						String str;
						if (Method.getPlayerRanking(p.getName()) <= 15) {
							str = "§4S";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "§cA";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "§bB";
						}
						
						else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "등록") && Method.getPlayerRanking(p.getName()) > 15)
						{
							str = "§eC";
						}
						
						else {
							str = "없음";
						}
						
						sender.sendMessage(YELLOW + " * " + args[1] + " " + GOLD + "님의 " + RED + "전적");
						sender.sendMessage(YELLOW + " ┗━━ " + RED + "히어로 점수 " + GRAY + ":: " + WHITE + Method.getPlayerScore(p.getName()));
						sender.sendMessage(YELLOW + " ┗━━ " + RED + "플레이어 순위 " + GRAY + ":: " + WHITE + Method.getPlayerRanking(p.getName()));
						sender.sendMessage(YELLOW + " ┗━━ " + RED + "플레이어 랭크 " + GRAY + ":: " + WHITE + str);
						sender.sendMessage(YELLOW + " ┗━━ " + RED + "소켓 보너스 " + GRAY + ":: " + WHITE + PlayerYml.getInfoInt(p, "보너스") + "%");
						
						if (GuildAPI.getJoinGuild(p) != null) {
							int i = GuildAPI.getScoreStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
							sender.sendMessage(YELLOW + " ┗━━ " + RED + "길드 보너스 " + GRAY + ":: " + WHITE + i + "%");
						} return false;
					}
					
					if (Method.getPlayerSeeInfo(p))
					{
						if (PlayerYml.getInfoBoolean(p, "등록여부") == false)
						{
							sender.sendMessage(RED + "이 플레이어는 히어로에 가입되어있지 않습니다.");
							return false;
						}
						
						String str;
						if (Method.getPlayerRanking(p.getName()) <= 15) {
							str = "§4S";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "§cA";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "§bB";
						}
						
						else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "등록") && Method.getPlayerRanking(p.getName()) > 15)
						{
							str = "§eC";
						}
						
						else {
							str = "없음";
						}
						
						sender.sendMessage(YELLOW + " * " + args[1] + " " + GOLD + "님의 " + RED + "전적");
						sender.sendMessage(YELLOW + " ┗━━ " + RED + "히어로 점수 " + GRAY + ":: " + WHITE + Method.getPlayerScore(p.getName()));
						sender.sendMessage(YELLOW + " ┗━━ " + RED + "플레이어 순위 " + GRAY + ":: " + WHITE + Method.getPlayerRanking(p.getName()));
						sender.sendMessage(YELLOW + " ┗━━ " + RED + "플레이어 랭크 " + GRAY + ":: " + WHITE + str);
						sender.sendMessage(YELLOW + " ┗━━ " + RED + "소켓 보너스 " + GRAY + ":: " + WHITE + PlayerYml.getInfoInt(p, "보너스") + "%");
						
						if (GuildAPI.getJoinGuild(p) != null) {
							int i = GuildAPI.getScoreStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
							sender.sendMessage(YELLOW + " ┗━━ " + RED + "길드 보너스 " + GRAY + ":: " + WHITE + i + "%");
						} return false;
					}
					
					else
					{
						sender.sendMessage(RED + "상대방이 전적 보기를 허가하지 않았습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("순위") || args[0].equalsIgnoreCase("랭킹"))
				{
					List<String> list = ScoreYml.ArrayScore();
					int i = 1;
					
					sender.sendMessage(YELLOW + "---------------------------------------------------");
					for (String str : list)
					{
						if (i >= 16) break;
						sender.sendMessage(GOLD + " ┗━ " + RED + i + "위 " + GRAY + ":: " + WHITE + "[" + DARK_RED + "S" + WHITE + "] " + str.split(",")[0] + " / " + str.split(",")[1]);
						i++;
					}
					sender.sendMessage(YELLOW + "---------------------------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("계급도"))
				{
					sender.sendMessage("§6---------------------------------");
					sender.sendMessage("§e * §f뉴비 §f[§e프로 히어로 등록§f]-> §eC");
					sender.sendMessage("§e * §eC §f[§e4000점§f]-> §dB");
					sender.sendMessage("§e * §dB §f[§e30000점§f]-> §cA");
					sender.sendMessage("§e * §cA §f[§e랭킹 15위 내§f]-> §4S");
					sender.sendMessage("§6---------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("보너스"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage(BLUE + "/점수 보너스 <닉네임> <퍼센트> " + WHITE + "- 플레이어의 점수 보너스를 설정합니다.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "그 플레이어는 온라인 상태가 아닙니다.");
						return false;
					}
					
					Player argsP = Method.getOnorOffLine(str);
					Method.setPlayerBouns(argsP, Integer.parseInt(args[2]));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("등록해주기"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 2))
					{
						sender.sendMessage(GOLD + "/점수 등록해주기 <닉네임> " + WHITE + "- 프로 히어로 협회에 등록할 권한을 줍니다.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "그 플레이어는 온라인 상태가 아닙니다.");
						return false;
					}
					
					Player argsP = Method.getOnorOffLine(str);
					PlayerYml.setInfoBoolean(argsP, "등록여부", true);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("설정"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage(BLUE + "/점수 <설정/추가/빼기> <닉네임> <값> " + WHITE + "- 플레이어의 점수를 변경합니다.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "그 플레이어는 온라인 상태가 아닙니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage(RED + "<값>에 숫자값만 입력해 주십시오.");
						return false;
					}

					Player argsP = Method.getOnorOffLine(str);
					ScoreYml.setPlayerScore(argsP.getName(), Integer.parseInt(args[2]));
					sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 점수를 §c" + args[2] + " §6만큼 설정하였습니다.");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("추가"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage(BLUE + "/점수 <설정/추가/빼기> <닉네임> <값> " + WHITE + "- 플레이어의 점수를 변경합니다.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "그 플레이어는 온라인 상태가 아닙니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage(RED + "<값>에 숫자값만 입력해 주십시오.");
						return false;
					}

					Player argsP = Method.getOnorOffLine(str);
					if (PlayerYml.getInfoBoolean(argsP, "등록여부") == false)
					{
						return false;
					}
					
					if (PartyAPI.getJoinParty(argsP) == null) {
						int i = Integer.parseInt(args[2]);
						double val = PlayerYml.getInfoInt(argsP, "보너스");
						if (GuildAPI.getJoinGuild(argsP) != null) val += GuildAPI.getScoreStat(GuildAPI.getJoinGuild(argsP));
						val = val / 100;
						val = Double.parseDouble(Method.roundDot1Off(Double.toString(val)));
						double y = (double) i * val;
						ScoreYml.addPlayerScore(argsP.getName(), (int) y);
						argsP.sendMessage("§6당신은 괴인을 §c사냥§6하여 §c" + args[2] + "(x" + val + ") §6만큼의 점수를 §c획득§6하셨습니다.");
						
						if (GuildAPI.getJoinGuild(argsP) != null) {
							int num = (int) y; num /= 10;
							GuildAPI.addPoint(GuildAPI.getJoinGuild(argsP), num);
						}
					} else {
						if (PartyAPI.getShare(PartyAPI.getJoinParty(argsP))) {
							String name = PartyAPI.getJoinParty(argsP);
							int i = Integer.parseInt(args[2]);
							double val = PlayerYml.getInfoInt(argsP, "보너스");
							if (GuildAPI.getJoinGuild(argsP) != null) val += GuildAPI.getScoreStat(GuildAPI.getJoinGuild(argsP));
							val = val / 100;
							val = Double.parseDouble(Method.roundDot1Off(Double.toString(val)));
							double y = (double) i * val;
							double total = 0.8;
							total += PartyAPI.getUserInteger(name) * 0.05;
							total *= y;
							ScoreYml.addPlayerScore(argsP.getName(), (int) total);
							argsP.sendMessage("§6당신은 괴인을 §c사냥§6하여 §c" + args[2] + "(x" + val + ") §6만큼의 점수를 §c획득§6하셨습니다.");
							
							if (GuildAPI.getJoinGuild(argsP) != null) {
								int num = (int) y; num /= 10;
								GuildAPI.addPoint(GuildAPI.getJoinGuild(argsP), num);
							}
							
							for (String st : PartyAPI.getUser(name)) {
								if (Method.getOnorOffLine(st) != null) {
									if (st.equalsIgnoreCase(argsP.getName())) continue;
									Player pl = Method.getOnorOffLine(st);
									if (!pl.getWorld().getName().equalsIgnoreCase(argsP.getWorld().getName())) continue;
									
									double dtc = argsP.getLocation().distance(pl.getLocation());
									
									if (dtc <= 25) {
										total = 0.25;
										total += PartyAPI.getUserInteger(name) * 0.1;
										total *= i;
										ScoreYml.addPlayerScore(pl.getName(), (int) total);
										String mess = "§6당신은 §c" + argsP.getName() + "§6님에게 파티 보너스로 §c<score> §6만큼 히어로 점수를 받았습니다.";
										mess = mess.replaceAll("<score>", Integer.toString((int) total));
										pl.sendMessage(mess);
									}
								}
							}
						} else {
							int i = Integer.parseInt(args[2]);
							double val = PlayerYml.getInfoInt(argsP, "보너스");
							if (GuildAPI.getJoinGuild(argsP) != null) val += GuildAPI.getScoreStat(GuildAPI.getJoinGuild(argsP));
							val = val / 100;
							val = Double.parseDouble(Method.roundDot1Off(Double.toString(val)));
							double y = (double) i * val;
							ScoreYml.addPlayerScore(argsP.getName(), (int) y);
							argsP.sendMessage("§6당신은 괴인을 §c사냥§6하여 §c" + args[2] + "(x" + val + ") §6만큼의 점수를 §c획득§6하셨습니다.");
							
							if (GuildAPI.getJoinGuild(argsP) != null) {
								int num = (int) y; num /= 10;
								GuildAPI.addPoint(GuildAPI.getJoinGuild(argsP), num);
							}
						}
					}
					
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("빼기"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage(BLUE + "/점수 <설정/추가/빼기> <닉네임> <값> " + WHITE + "- 플레이어의 점수를 변경합니다.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "그 플레이어는 온라인 상태가 아닙니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage(RED + "<값>에 숫자값만 입력해 주십시오.");
						return false;
					}

					Player argsP = Method.getOnorOffLine(str);
					ScoreYml.subPlayerScore(argsP.getName(), Integer.parseInt(args[2]));
					sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 킬 수를 §c" + args[2] + " §6만큼 뻈습니다.");
					return false;
				}
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args[0].equalsIgnoreCase("확인") && args.length == 1 || args[0].equalsIgnoreCase("전적") && args.length == 1)
					{
						if (PlayerYml.getInfoBoolean(p, "등록여부") == false)
						{
							sender.sendMessage("§c당신은 프로 히어로가 아니므로 전적을 보실 수 없습니다.");
							return false;
						}
						
						String str;
						if (Method.getPlayerRanking(p.getName()) <= 15) {
							str = "§4S";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "§cA";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "§bB";
						}
						
						else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "등록") && Method.getPlayerRanking(p.getName()) > 15)
						{
							str = "§eC";
						}
						
						else {
							str = "없음";
						}
						
						sender.sendMessage("§e * " + p.getName() + " §6님의 §c전적");
						sender.sendMessage("§e ┗━━ §c히어로 점수 §7:: §f" + Method.getPlayerScore(p.getName()));
						sender.sendMessage("§e ┗━━ §c플레이어 순위 §7:: §f" + Method.getPlayerRanking(p.getName()));
						sender.sendMessage("§e ┗━━ §c플레이어 랭크 §7:: §f" +  str);
						sender.sendMessage("§e ┗━━ §c소켓 보너스 §7:: §f" + PlayerYml.getInfoInt(p, "보너스") + "%");
						
						if (GuildAPI.getJoinGuild(p) != null) {
							int i = GuildAPI.getScoreStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
							sender.sendMessage(YELLOW + " ┗━━ " + RED + "길드 보너스 " + GRAY + ":: " + WHITE + i + "%");
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("보기") && args.length == 2)
					{
						if (args[1].equalsIgnoreCase("허용"))
						{
							if (Method.getPlayerSeeInfo(p) == true)
							{
								sender.sendMessage("§c이미 전적 보기를 허용하셨습니다.");
								return false;
							}
							  
							PlayerYml.setInfoBoolean(p, "보기", true);
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

							PlayerYml.setInfoBoolean(p, "보기", false);
							sender.sendMessage("§6당신은 상대방이 전적을 보는 것을 §c거부§6했습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("등록"))
					{
						if (PlayerYml.getInfoBoolean(p, "등록여부") == false)
						{
							sender.sendMessage("§c당신은 아직 프로 히어로 협회에 등록하실 수 없습니다.");
							return false;
						}
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("C"))
					{
						if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "등록") && Method.getPlayerRanking(p.getName()) > 15)
						{
							p.performCommand("kit C");
							return false;
						}	
						  
						else
						{
							sender.sendMessage("§c당신은 §eC §c랭크가 아닙니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("B"))
					{
						if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15)
						{
							p.performCommand("kit B");
							return false;
						}	
						  
						else
						{
							sender.sendMessage("§c당신은 §dB §c랭크가 아닙니다.");
							return false;
						}
					}
					  
					else if (args[0].equalsIgnoreCase("A"))
					{
						if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15)
						{
							p.performCommand("kit A");
							return false;
						}
						
						else
						{
							sender.sendMessage("§c당신은 A 랭크가 아닙니다.");
							return false;
						}
					}
					  
					else if (args[0].equalsIgnoreCase("S"))
					{
						if (Method.getPlayerRanking(p.getName()) <= 15)
						{
							p.performCommand("kit S");
							return false;
						}
						  
						else
						{
							sender.sendMessage("§c당신은 §4S §c랭크가 아닙니다.");
							return false;
						}
					}
					
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				displayHelp(sender);
				return false;
			} else {
				displayConsol(sender);
				return false;
			}
		} return false;
	}
	
	private void displayHelp(CommandSender sender)
	{
		Player p = (Player) sender;
		String str;
		if (Method.getPlayerRanking(p.getName()) <= 15) {
			str = "§4S §6입";
		}
		
		else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
			str = "§cA §6입";
		}
		
		else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
			str = "§bB §6입";
		}
		
		else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "등록") && Method.getPlayerRanking(p.getName()) > 15)
		{
			str = "§eC §6입";
		}
		
		else {
			str = "존재하지 않습";
		}
		
		sender.sendMessage(" §e----- §6히어로 점수 §e--- §6도움말 §e-----");
		sender.sendMessage("  §e-- §6당신의 계급은 " + str + "니다. §e--");
		sender.sendMessage("§6/점수 §f- 히어로 점수 도움말을 봅니다.");
		sender.sendMessage("§6/점수 확인 §f- 자신의 히어로 점수를 봅니다.");
		sender.sendMessage("§6/점수 확인 <닉네임> §f- 다른 사람의 킬 뎃 전적을 봅니다.");
		sender.sendMessage("§6/점수 순위 §f- S랭크 15명을 봅니다.");
		sender.sendMessage("§6/점수 <C/B/A/S> §f- 4시간마다 랭크 전용 보상을 받습니다");
		sender.sendMessage("§6/점수 보기 <허용/거부> §f- 남이 자신의 전적을 보는 것을 설정합니다. §b( 기본값 비허용 )");
		sender.sendMessage("§6/점수 계급도 §f- 랭크의 계급도를 봅니다.");
		sender.sendMessage("§6/등록 §f- 프로 히어로 협회에 등록합니다.");
		
		if (p.isOp())
		{
			sender.sendMessage("§f");
			sender.sendMessage("§b/점수 <설정/추가/빼기> <닉네임> <값> §f- 플레이어의 점수를 변경합니다.");
			sender.sendMessage("§b/점수 등록해주기 <닉네임> §f- 프로 히어로 협회에 등록할 권한을 줍니다.");
			sender.sendMessage("§b/점수 보너스 <닉네임> <퍼센트> §f- 플레이어의 점수 보너스를 설정합니다.");
		}
	}
	
	private void displayConsol(CommandSender sender)
	{
		sender.sendMessage(" " + YELLOW + "----- " + GOLD + "히어로 점수 " + YELLOW + "--- " + GOLD + "도움말 " + YELLOW + "-----");
		sender.sendMessage(GOLD + "/점수 " + WHITE + "- 히어로 점수 도움말을 봅니다.");
		sender.sendMessage(GOLD + "/점수 확인 <닉네임> " + WHITE + "- 다른 사람의 킬 뎃 전적을 봅니다.");
		sender.sendMessage(GOLD + "/점수 순위 " + WHITE + "- S랭크 15명을 봅니다.");
		sender.sendMessage(GOLD + "/점수 계급도 " + WHITE + "- 랭크의 계급도를 봅니다.");
		
		if (sender.isOp())
		{
			sender.sendMessage("");
			sender.sendMessage(BLUE + "/점수 <설정/추가/빼기> <닉네임> <값> " + WHITE + "- 플레이어의 점수를 변경합니다.");
			sender.sendMessage(BLUE + "/점수 등록해주기 <닉네임> " + WHITE + "- 프로 히어로 협회에 등록할 권한을 줍니다.");
			sender.sendMessage(BLUE + "/점수 보너스 <닉네임> <퍼센트> " + WHITE + "- 플레이어의 점수 보너스를 설정합니다.");
		}
	}
}
