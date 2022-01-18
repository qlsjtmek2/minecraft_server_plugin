package me.espoo.rpg.exp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.Method;
import me.espoo.rpg.PlayerYml;
import me.espoo.rpg.main;
import me.espoo.rpg.guild.GuildAPI;

public class ExpCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public ExpCommand(main instance)
	{
		this.plugin = instance;
	}
	
	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("경험치") || commandLabel.equalsIgnoreCase("exp")) {
				if (args.length == 0) {
					if (sender instanceof Player) {
						displayHelp(sender);
						return false;
					} else {
						sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
						return false;
					}
				}
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (args[0].equalsIgnoreCase("확인") && args.length == 1 || args[0].equalsIgnoreCase("전적") && args.length == 1)
					{
						sender.sendMessage("§e * " + p.getName() + " §7:: §f" + ExpAPI.getDayMessage(p));
						sender.sendMessage("§e ┗━━ §c경험치 §7:: §f" + ExpAPI.getExp(p.getName()));
						sender.sendMessage("§e ┗━━ §c플레이어 순위 §7:: §f" + ExpAPI.getRank(p.getName()));
						int num = 0;
						for (String type : ExpConfig.getExpList("타입 목록")) {
							if (ExpAPI.getBouns(p.getName(), type) != 0) {
								num += ExpAPI.getBouns(p.getName(), type);
								sender.sendMessage("§e ┗━ §6" + type + " §7:: §f" + ExpAPI.getBouns(p.getName(), type) + "%");
							} else sender.sendMessage("§e ┗━ §6" + type + " §7:: §f100%");
						}
						
						if (GuildAPI.getJoinGuild(p) != null) {
							int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
							num += i;
							sender.sendMessage("§e ┗━ §6길드 보너스 §7:: §f" + i + "%");
						}
						
						if (ExpAPI.getServerBouns() != 100) num += ExpAPI.getServerBouns();
						sender.sendMessage("§e ┗━ §6서버 이벤트 §7:: §f" + ExpAPI.getServerBouns() + "%");
						
						if (num != 0) sender.sendMessage("§e ┗━ §6도합 §7:: §f" + num + "%");
						else sender.sendMessage("§e ┗━ §6도합 §7:: §f100%");
						return false;
					}
					
					if (args[0].equalsIgnoreCase("확인") && args.length == 2 || args[0].equalsIgnoreCase("전적") && args.length == 2) {
						Player pl = Method.getOnorOffLine(Method.searchOnlinePlayer(args[1]));
						
						if (pl == null) {
							sender.sendMessage(RED + "이 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (sender.isOp())
						{
							sender.sendMessage("§e * " + pl.getName() + " §7:: §f" + ExpAPI.getDayMessage(pl));
							sender.sendMessage("§e ┗━━ §c경험치 §7:: §f" + ExpAPI.getExp(pl.getName()));
							sender.sendMessage("§e ┗━━ §c플레이어 순위 §7:: §f" + ExpAPI.getRank(pl.getName()));
							int num = 0;
							for (String type : ExpConfig.getExpList("타입 목록")) {
								if (ExpAPI.getBouns(pl.getName(), type) != 0) {
									num += ExpAPI.getBouns(pl.getName(), type);
									sender.sendMessage("§e ┗━ §6" + type + " §7:: §f" + ExpAPI.getBouns(pl.getName(), type) + "%");
								} else sender.sendMessage("§e ┗━ §6" + type + " §7:: §f100%");
							}
							
							if (GuildAPI.getJoinGuild(p) != null) {
								int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
								num += i;
								sender.sendMessage("§e ┗━ §6길드 보너스 §7:: §f" + i + "%");
							}
							
							if (ExpAPI.getServerBouns() != 100) num += ExpAPI.getServerBouns();
							sender.sendMessage("§e ┗━ §6서버 이벤트 §7:: §f" + ExpAPI.getServerBouns() + "%");
							
							if (num != 0) sender.sendMessage("§e ┗━ §6도합 §7:: §f" + num + "%");
							else sender.sendMessage("§e ┗━ §6도합 §7:: §f100%");
							return false;
						}
						
						if (ExpAPI.getSeeInfo(pl))
						{
							sender.sendMessage("§e * " + pl.getName() + " §7:: §f" + ExpAPI.getDayMessage(pl));
							sender.sendMessage("§e ┗━━ §c경험치 §7:: §f" + ExpAPI.getExp(pl.getName()));
							sender.sendMessage("§e ┗━━ §c플레이어 순위 §7:: §f" + ExpAPI.getRank(pl.getName()));
							int num = 0;
							for (String type : ExpConfig.getExpList("타입 목록")) {
								if (ExpAPI.getBouns(pl.getName(), type) != 0) {
									num += ExpAPI.getBouns(pl.getName(), type);
									sender.sendMessage("§e ┗━ §6" + type + " §7:: §f" + ExpAPI.getBouns(pl.getName(), type) + "%");
								} else sender.sendMessage("§e ┗━ §6" + type + " §7:: §f100%");
							}
							
							if (GuildAPI.getJoinGuild(p) != null) {
								int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
								num += i;
								sender.sendMessage("§e ┗━ §6길드 보너스 §7:: §f" + i + "%");
							}
							
							if (ExpAPI.getServerBouns() != 100) num += ExpAPI.getServerBouns();
							sender.sendMessage("§e ┗━ §6서버 이벤트 §7:: §f" + ExpAPI.getServerBouns() + "%");
							
							if (num != 0) sender.sendMessage("§e ┗━ §6도합 §7:: §f" + num + "%");
							else sender.sendMessage("§e ┗━ §6도합 §7:: §f100%");
							return false;
						}
						
						else
						{
							sender.sendMessage(RED + "상대방이 전적 보기를 허가하지 않았습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("순위") && args.length == 1 || args[0].equalsIgnoreCase("랭킹") && args.length == 1)
					{
						List<String> list = ExpYml.ArrayExp();
						int i = 1;
						int num = (list.size() / 10) + 1;
						sender.sendMessage("§c" + list.size() + " §6명의 유저를 찾았습니다. §c1§6/§c" + num);
						for (String str : list)
						{
							if (i >= 11) break;
							sender.sendMessage("§c" + i + ": §6" + str.split(",")[0] + " §eEXP." + str.split(",")[1]);
							i++;
						}
						
						if (list.size() > 10) {
							sender.sendMessage("§6다음 랭킹을 보시려면 §c/경험치 순위 2 §6를 입력해주세요.");
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("순위") && args.length == 2 || args[0].equalsIgnoreCase("랭킹") && args.length == 2)
					{
						List<String> list = ExpYml.ArrayExp();
						int num = Integer.parseInt(args[1]);
						int num2 = (list.size() / 10) + 1;
						
						if (num > list.size() / 10) {
							sender.sendMessage("§c해당 순위는 존재하지 않습니다.");
							return false;
						}
						
						int i = (num * 10) + 1;
						int y = 1;
						
						sender.sendMessage("§c" + list.size() + " §6명의 유저를 찾았습니다. §c" + num + "§6/§c" + num2);
						for (String str : list)
						{
							if (y < i) {
								y++;
								continue;
							}
							
							if (i >= (num * 10) + 11) break;
							sender.sendMessage("§c" + i + ": §6" + str.split(",")[0] + " §eEXP." + str.split(",")[1]);
							i++;
						}
						
						if (list.size() > i) {
							sender.sendMessage("§6다음 랭킹을 보시려면 §c/경험치 순위 " + num+1 + " §6를 입력해주세요.");
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("보상"))
					{
						sender.sendMessage("§6---------------------------------");
						sender.sendMessage("§e * §6경험치 랭킹 1위 - §f돈 " + me.espoo.oneman.Method.getConfigInt("1등 보상") + "원");
						sender.sendMessage("§e * §6경험치 랭킹 2위 - §f돈 " + me.espoo.oneman.Method.getConfigInt("2등 보상") + "원");
						sender.sendMessage("§e * §6경험치 랭킹 3위 - §f돈 " + me.espoo.oneman.Method.getConfigInt("3등 보상") + "원");
						sender.sendMessage("§e * §6경험치 랭킹 4위 - §f돈 " + me.espoo.oneman.Method.getConfigInt("4등 보상") + "원");
						sender.sendMessage("§6---------------------------------");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("상메") && args.length >= 2 || args[0].equalsIgnoreCase("상태메세지") && args.length >= 2 || args[0].equalsIgnoreCase("상태메시지") && args.length >= 2)
					{
						String message = Method.replaceAllColors(Method.getFinalArg(args, 1));
						PlayerYml.setInfoString(p, "상태 메세지", message);
						p.sendMessage("§6성공적을 상태 메세지를 변경하셨습니다! §e[ /경험치 확인 ]");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("아이템"))
					{
						if (args.length == 4) {
							if (!sender.isOp())
							{
								sender.sendMessage(RED + "당신은 명령어를 실행할 권한이 없습니다.");
								return false;
							}
							
							String type = args[1];
							int bouns = Integer.parseInt(args[2]);
							int time = 0;
							
							if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
								sender.sendMessage(RED + "손에 아이템이 들려있지 않아 경험치 보너스를 제작할 수 없습니다.");
								return false;
							}
							
							if (!ExpAPI.isBounsType(type)) {
								sender.sendMessage(RED + "해당 경험치 보너스 타입은 존재하지 않습니다.");
								return false;
							}
							
							if (p.getItemInHand().hasItemMeta()) {
								if (p.getItemInHand().getItemMeta().hasLore()) {
									ItemStack item = p.getItemInHand();
									ItemMeta meta = item.getItemMeta();
									List<String> list = meta.getLore();
									
									if (args[3].equalsIgnoreCase("infinite")) {
										meta.setDisplayName("§6경험치 보너스 아이템");
										list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 영구§6히 지속");
									}
									
									else if (args[3].contains("h")) {
										time = Integer.parseInt(args[3].replaceAll("h", ""));
										meta.setDisplayName("§6경험치 보너스 아이템");
										list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "H §6지속");
									}
									
									else if (args[3].contains("m")) {
										time = Integer.parseInt(args[3].replaceAll("m", ""));
										meta.setDisplayName("§6경험치 보너스 아이템");
										list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "M §6지속");
									}
									
									else {
										time = Integer.parseInt(args[3]);
										meta.setDisplayName("§6경험치 보너스 아이템");
										list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "M §6지속");
									}
									
									meta.setLore(list);
									item.setItemMeta(meta);
									p.setItemInHand(item);
									p.sendMessage("§6성공적으로 경험치 보너스 아이템을 제작하였습니다.");
									return false;
								} else {
									ItemStack item = p.getItemInHand();
									ItemMeta meta = item.getItemMeta();
									List<String> list = new ArrayList<String>();
									
									if (args[3].equalsIgnoreCase("infinite")) {
										meta.setDisplayName("§6경험치 보너스 아이템");
										list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 영구§6히 지속");
									}
									
									else if (args[3].contains("h")) {
										time = Integer.parseInt(args[3].replaceAll("h", ""));
										meta.setDisplayName("§6경험치 보너스 아이템");
										list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "H §6지속");
									}
									
									else if (args[3].contains("m")) {
										time = Integer.parseInt(args[3].replaceAll("m", ""));
										meta.setDisplayName("§6경험치 보너스 아이템");
										list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "M §6지속");
									}
									
									else {
										time = Integer.parseInt(args[3]);
										meta.setDisplayName("§6경험치 보너스 아이템");
										list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "M §6지속");
									}
									
									meta.setLore(list);
									item.setItemMeta(meta);
									p.setItemInHand(item);
									p.sendMessage("§6성공적으로 경험치 보너스 아이템을 제작하였습니다.");
									return false;
								}
							} else {
								ItemStack item = p.getItemInHand();
								ItemMeta meta = item.getItemMeta();
								List<String> list = new ArrayList<String>();
								
								if (args[3].equalsIgnoreCase("infinite")) {
									meta.setDisplayName("§6경험치 보너스 아이템");
									list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 영구§6히 지속");
								}
								
								else if (args[3].contains("h")) {
									time = Integer.parseInt(args[3].replaceAll("h", ""));
									meta.setDisplayName("§6경험치 보너스 아이템");
									list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "H §6지속");
								}
								
								else if (args[3].contains("m")) {
									time = Integer.parseInt(args[3].replaceAll("m", ""));
									meta.setDisplayName("§6경험치 보너스 아이템");
									list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "M §6지속");
								}
								
								else {
									time = Integer.parseInt(args[3]);
									meta.setDisplayName("§6경험치 보너스 아이템");
									list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "M §6지속");
								}
								
								meta.setLore(list);
								item.setItemMeta(meta);
								p.setItemInHand(item);
								p.sendMessage("§6성공적으로 경험치 보너스 아이템을 제작하였습니다.");
								return false;
							}
						}
						
						else if (args.length == 5) {
							if (!sender.isOp())
							{
								sender.sendMessage(RED + "당신은 명령어를 실행할 권한이 없습니다.");
								return false;
							}
							
							String type = args[1];
							int bouns = Integer.parseInt(args[2]);
							int time = 0;
							
							if (p.getItemInHand() == null) {
								sender.sendMessage(RED + "손에 아이템이 들려있지 않아 경험치 보너스를 제작할 수 없습니다.");
								return false;
							}
							
							if (!ExpAPI.isBounsType(type)) {
								sender.sendMessage(RED + "해당 경험치 보너스 타입은 존재하지 않습니다.");
								return false;
							}

				            int t = 0;
				            ItemStack[] contents;
				            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
				                ItemStack it = contents[j];
				                if (it == null) {
				                    ++t;
				                }
				            }
				            
				            if (t <= 1) {
				    			p.sendMessage("§c인벤토리 공간이 부족하여 아이템을 생성할 수 없습니다.");
				    			return false;
				            } else {
								ItemStack item = null;
								if (args[3].contains(":")) {
									item = new MaterialData(Integer.parseInt(args[4].split(":")[0]), Byte.parseByte(args[4].split(":")[1])).toItemStack(1);
								} else {
									item = new MaterialData(Integer.parseInt(args[4]), (byte) 0).toItemStack(1);
								}
								
								ItemMeta meta = item.getItemMeta();
								List<String> list = new ArrayList<String>();
								
								if (args[3].equalsIgnoreCase("infinite")) {
									meta.setDisplayName("§6경험치 보너스 아이템");
									list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 영구§6히 지속");
								}
								
								else if (args[3].contains("h")) {
									time = Integer.parseInt(args[3].replaceAll("h", ""));
									meta.setDisplayName("§6경험치 보너스 아이템");
									list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "H §6지속");
								}
								
								else if (args[3].contains("m")) {
									time = Integer.parseInt(args[3].replaceAll("m", ""));
									meta.setDisplayName("§6경험치 보너스 아이템");
									list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "M §6지속");
								}
								
								else {
									time = Integer.parseInt(args[3]);
									meta.setDisplayName("§6경험치 보너스 아이템");
									list.add("§6경험치 보너스 §c" + type + "§6에 §c" + bouns + "% 만큼 " + time + "M §6지속");
								}
								
								meta.setLore(list);
								item.setItemMeta(meta);
								p.getInventory().addItem(item);
								p.sendMessage("§6성공적으로 경험치 보너스 아이템을 제작하였습니다.");
								return false;
				            }
						}
					}
					
					else if (args[0].equalsIgnoreCase("이벤트"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage(RED + "당신은 명령어를 실행할 권한이 없습니다.");
							return false;
						}
						
						if (!(args.length == 3))
						{
							sender.sendMessage("§b/경험치 이벤트 <값> <시간>(h,m,infinite) §f- 경험치 보너스 이벤트를 진행합니다.");
							return false;
						}
						
						int bouns = Integer.parseInt(args[1]);
						int time = 0;
						
						if (ExpAPI.getServerBouns() > bouns) {
							if (args[2].equalsIgnoreCase("infinite")) {
								time = -1;
								Bukkit.broadcastMessage("§f[§4알림§f] §6경험치 이벤트가 §c" + bouns + "% §6만큼 §c영구적§6으로 줄어들었습니다.");
							}
							
							if (args[2].contains("h")) {
								time = Integer.parseInt(args[2].replaceAll("h", ""));
								Bukkit.broadcastMessage("§f[§4알림§f] §6경험치 이벤트가 §c" + bouns + "% §6만큼 §c" + time + "§6 시간동안 줄어듭니다.");
								time = time * 60;
							}
							
							else if (args[2].contains("m")) {
								time = Integer.parseInt(args[2].replaceAll("m", ""));
								Bukkit.broadcastMessage("§f[§4알림§f] §6경험치 이벤트가 §c" + bouns + "% §6만큼 §c" + time + "§6 분동안 줄어듭니다.");
							}
							
							else {
								time = Integer.parseInt(args[2]);
								Bukkit.broadcastMessage("§f[§4알림§f] §6경험치 이벤트가 §c" + bouns + "% §6만큼 §c" + time + "§6 분동안 줄어듭니다.");
							}
						} else {
							if (args[2].equalsIgnoreCase("infinite")) {
								time = -1;
								Bukkit.broadcastMessage("§f[§4알림§f] §6지금부터 경험치 §c" + bouns + "% §6이벤트를 §c영구적§6으로 진행합니다.");
							}
							
							if (args[2].contains("h")) {
								time = Integer.parseInt(args[2].replaceAll("h", ""));
								Bukkit.broadcastMessage("§f[§4알림§f] §6지금부터 경험치 §c" + bouns + "% §6이벤트를 §c" + time + "§6 시간동안 진행합니다.");
								time = time * 60;
							}
							
							else if (args[2].contains("m")) {
								time = Integer.parseInt(args[2].replaceAll("m", ""));
								Bukkit.broadcastMessage("§f[§4알림§f] §6지금부터 경험치 §c" + bouns + "% §6이벤트를 §c" + time + "§6 분동안 진행합니다.");
							}
							
							else {
								time = Integer.parseInt(args[2]);
								Bukkit.broadcastMessage("§f[§4알림§f] §6지금부터 경험치 §c" + bouns + "% §6이벤트를 §c" + time + "§6 분동안 진행합니다.");
							}
						}
						
						ExpAPI.setServerBouns(bouns);
						ExpAPI.setServerBounsTimer(time);
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
							sender.sendMessage(BLUE + "/경험치 <설정/추가/빼기> <닉네임> <값> " + WHITE + "- 플레이어의 경험치를 변경합니다.");
							return false;
						}
						
						String str = Method.searchOnlinePlayer(args[1]);
						File f = new File("plugins/OnePunchRpg/Player/" + str + ".yml");
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
						ExpYml.setPlayerExp(argsP.getName(), Integer.parseInt(args[2]));
						sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 경험치를 §c" + args[2] + " §6만큼 설정하였습니다.");
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
							sender.sendMessage(BLUE + "/경험치 <설정/추가/빼기> <닉네임> <값> " + WHITE + "- 플레이어의 경험치를 변경합니다.");
							return false;
						}
						
						String str = Method.searchOnlinePlayer(args[1]);
						File f = new File("plugins/OnePunchRpg/Player/" + str + ".yml");
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
						ExpYml.addPlayerExp(argsP.getName(), Integer.parseInt(args[2]));
						sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 경험치 수를 §c" + args[2] + " §6만큼 추가했습니다.");
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
							sender.sendMessage(BLUE + "/경험치 <설정/추가/빼기> <닉네임> <값> " + WHITE + "- 플레이어의 경험치를 변경합니다.");
							return false;
						}
						
						String str = Method.searchOnlinePlayer(args[1]);
						File f = new File("plugins/OnePunchRpg/Player/" + str + ".yml");
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
						ExpYml.subPlayerExp(argsP.getName(), Integer.parseInt(args[2]));
						sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 경험치 수를 §c" + args[2] + " §6만큼 뺐습니다.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("보기") && args.length == 2)
					{
						if (args[1].equalsIgnoreCase("허용"))
						{
							if (ExpAPI.getSeeInfo(p) == true)
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
							if (ExpAPI.getSeeInfo(p) == false)
							{
								sender.sendMessage("§c이미 전적 보기를 불허하셨습니다.");
								return false;
							}

							PlayerYml.setInfoBoolean(p, "보기", false);
							sender.sendMessage("§6당신은 상대방이 전적을 보는 것을 §c거부§6했습니다.");
							return false;
						}
					}
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
				sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		} return false;
	}
	
	private void displayHelp(CommandSender sender)
	{
		sender.sendMessage(" §e------ §6경험치 §e--- §6도움말 §e------");
		sender.sendMessage("§6/경험치 §f- 경험치 명령어를 확인합니다.");
		sender.sendMessage("§6/경험치 확인 §f- 자신의 경험치와 관련된 값을 확인합니다.");
		sender.sendMessage("§6/경험치 확인 <닉네임> §f- 플레이어의 경험치와 관련된 값을 확인합니다.");
		sender.sendMessage("§6/경험치 순위 §f- 경험치 관련 플레이어의 모든 순위를 봅니다.");
		sender.sendMessage("§6/경험치 보상 §f- 경험치로 획득 가능한 보상을 봅니다.");
		sender.sendMessage("§6/경험치 상메 <메세지> §f- 내 상태 메세지를 변경합니다.");
		sender.sendMessage("§6/경험치 보기 <허용/거부> §f- 프로필 공개 여부를 허용하거나 거부합니다. §b( 기본값 비허용 )");
		
		if (sender.isOp())
		{
			sender.sendMessage("§f");
			sender.sendMessage("§b/경험치 <설정/추가/빼기> <닉네임> <값> §f- 플레이어의 경험치를 변경합니다.");
			sender.sendMessage("§b/경험치 이벤트 <값> <시간>(h,m,infinite) §f- 경험치 보너스 이벤트를 진행합니다.");
			sender.sendMessage("§b/경험치 아이템 <타입> <값> <시간>(h,m,infinite) <아이템코드> §f- 경험치 추가 아이템을 생성합니다.");
		}
	}
}
