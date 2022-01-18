package me.espoo.pvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("대련") || commandLabel.equalsIgnoreCase("대전") || commandLabel.equalsIgnoreCase("PVP")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 6) {
						if (args[0].equalsIgnoreCase("쿨") && args[1].equalsIgnoreCase("타") && args[2].equalsIgnoreCase("임") &&
							args[3].equalsIgnoreCase("초") && args[4].equalsIgnoreCase("기") && args[5].equalsIgnoreCase("화")) {
							main.coloCooltimeList.clear();
							return false;
						}
					}
					
					if (API.isUserVSMatch(p)) {
						GUI.stopGUI(p);
						return false;
					}
					
					String party = PartyAPI.getJoinParty(p);
					if (party != null) {
						if (API.isPartyVSMatch(party)) {
							GUI.stopGUI(p);
							return false;
						}
					}
					
					GUI.openGUI(p);
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
						
						else if (args[0].equalsIgnoreCase("콜로세움")) {
							switch (args[1]) {
							case "1":
								Position.setColoRed(p.getLocation());
								p.sendMessage("§6성공적으로 콜로세움 대련 §c첫번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
									
							case "2":
								Position.setColoBlue(p.getLocation());
								p.sendMessage("§6성공적으로 콜로세움 대련 §c두번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
								
							case "관전추가":
								Position.addColoWatchLocation(p.getLocation());
								p.sendMessage("§6성공적으로 콜로세움 대련 §c관전 장소 §6를 추가하셨습니다.");
								return false;
									
							default: HelpMessage(sender); return false;
							}
						}
					}
					
					else if (args.length == 3) {
						if (args[0].equalsIgnoreCase("2:2")) {
							if (args[1].equalsIgnoreCase("RED")) {
								switch (args[2]) {
								case "1":
									Position.setTwoRed1(p.getLocation());
									p.sendMessage("§6성공적으로 2:2 대련 RED §c첫번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "2":
									Position.setTwoRed2(p.getLocation());
									p.sendMessage("§6성공적으로 2:2 대련 RED §c두번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
							
							else if (args[1].equalsIgnoreCase("BLUE")) {
								switch (args[2]) {
								case "1":
									Position.setTwoBlue1(p.getLocation());
									p.sendMessage("§6성공적으로 2:2 대련 BLUE §c첫번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "2":
									Position.setTwoBlue2(p.getLocation());
									p.sendMessage("§6성공적으로 2:2 대련 BLUE §c두번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
						}

						else if (args[0].equalsIgnoreCase("3:3")) {
							if (args[1].equalsIgnoreCase("RED")) {
								switch (args[2]) {
								case "1":
									Position.setThreeRed1(p.getLocation());
									p.sendMessage("§6성공적으로 3:3 대련 RED §c첫번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "2":
									Position.setThreeRed2(p.getLocation());
									p.sendMessage("§6성공적으로 3:3 대련 RED §c두번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "3":
									Position.setThreeRed3(p.getLocation());
									p.sendMessage("§6성공적으로 3:3 대련 RED §c세번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
							
							else if (args[1].equalsIgnoreCase("BLUE")) {
								switch (args[2]) {
								case "1":
									Position.setThreeBlue1(p.getLocation());
									p.sendMessage("§6성공적으로 3:3 대련 BLUE §c첫번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "2":
									Position.setThreeBlue2(p.getLocation());
									p.sendMessage("§6성공적으로 3:3 대련 BLUE §c두번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "3":
									Position.setThreeBlue3(p.getLocation());
									p.sendMessage("§6성공적으로 3:3 대련 BLUE §c세번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
						}
						
						else if (args[0].equalsIgnoreCase("4:4")) {
							if (args[1].equalsIgnoreCase("RED")) {
								switch (args[2]) {
								case "1":
									Position.setFourRed1(p.getLocation());
									p.sendMessage("§6성공적으로 4:4 대련 RED §c첫번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "2":
									Position.setFourRed2(p.getLocation());
									p.sendMessage("§6성공적으로 4:4 대련 RED §c두번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "3":
									Position.setFourRed3(p.getLocation());
									p.sendMessage("§6성공적으로 4:4 대련 RED §c세번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "4":
									Position.setFourRed4(p.getLocation());
									p.sendMessage("§6성공적으로 4:4 대련 RED §c네번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
							
							else if (args[1].equalsIgnoreCase("BLUE")) {
								switch (args[2]) {
								case "1":
									Position.setFourBlue1(p.getLocation());
									p.sendMessage("§6성공적으로 4:4 대련 BLUE §c첫번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "2":
									Position.setFourBlue2(p.getLocation());
									p.sendMessage("§6성공적으로 4:4 대련 BLUE §c두번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "3":
									Position.setFourBlue3(p.getLocation());
									p.sendMessage("§6성공적으로 4:4 대련 BLUE §c세번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								case "4":
									Position.setFourBlue4(p.getLocation());
									p.sendMessage("§6성공적으로 4:4 대련 BLUE §c네번째 스폰 위치 §6를 설정하셨습니다.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
						}
					} return false;
				} else {
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("배팅")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (main.coloType != 1) {
						p.sendMessage("§c현재 콜로세움 대련중이지 않거나 배팅할 때가 아닙니다.");
						return false;
					}
					
					if (!p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
						p.sendMessage("§c[ /관전 ] 입력해서 관전 중일 때만 배팅이 가능합니다.");
						return false;
					}
					
					if (API.isColoVSPlaying(p.getName())) {
						p.sendMessage("§c콜로세움 대련중일때는 배팅이 불가능합니다.");
						return false;
					}
					
					if (main.coloMoney.get(p) != null) {
						p.sendMessage("§c당신은 이미 배팅을 진행한 상태입니다.");
						return false;
					}
					
					if (args.length != 1) {
						p.sendMessage("§6/배팅 <1/2> §f- 1 플레이어나 2 플레이어에게 " + main.coloBetting + " 원 만큼 배팅을 합니다.");
						p.sendMessage("§61. §c" + API.getColoPlayer(1));
						p.sendMessage("§62. §c" + API.getColoPlayer(2));
						return false;
					}
					
					if (!args[0].equalsIgnoreCase("1") && !args[0].equalsIgnoreCase("2")) {
						p.sendMessage("§6/배팅 <1/2> §f- 1 플레이어나 2 플레이어에게 " + main.coloBetting + " 원 만큼 배팅을 합니다.");
						p.sendMessage("§61. §c" + API.getColoPlayer(1));
						p.sendMessage("§62. §c" + API.getColoPlayer(2));
						return false;
					}
					
					if (main.economy.getBalance(p.getName()) < main.coloBetting) {
						p.sendMessage("§c당신은 돈이 부족하여 배팅을 하실 수 없습니다.");
						return false;
					}
					
					String name = API.getColoPlayer(Integer.parseInt(args[0]));
					main.coloMoney.put(p, name + "," + main.coloBetting);
					main.economy.withdrawPlayer(p.getName(), main.coloBetting);
					p.sendMessage("§6성공적으로 §c" + name + " §6님에게 §c" + main.coloBetting + " §6원을 걸었습니다.");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("관전")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					p.teleport(Position.getRandomColoWatchLocation());
					p.getLocation().getChunk().load();
					p.sendMessage("§6콜로세움 관전장으로 §c이동 §6하였습니다.");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("경기취소") || commandLabel.equalsIgnoreCase("대련취소") || commandLabel.equalsIgnoreCase("대전취소")) {
				if (args.length == 1) {
					if (!sender.isOp()) {
						sender.sendMessage(RED + "당신은 권한이 없으므로 이 명령어를 실행할 수 없습니다.");
						return false;
					}
					
					String arg = args[0];
					
					if (arg.equalsIgnoreCase("1") || arg.equalsIgnoreCase("2") || arg.equalsIgnoreCase("3") || arg.equalsIgnoreCase("4") || arg.equalsIgnoreCase("5")) {
						int num = Integer.parseInt(arg);
						
						switch (num) {
						case 1:
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
							
						case 2:
							if (!main.twoVS) {
								sender.sendMessage(RED + "현재 2:2 대련중인 사람이 없습니다.");
								return false;
							}
							
							String pO = main.twoName.split(",")[0];
							String pT = main.twoName.split(",")[1];
							
							if (PartyAPI.isParty(pO) && PartyAPI.isParty(pT)) {
								for (String user : PartyAPI.getUser(pO)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.twoStopAPI(pO, pT);
									}
								}
								
								for (String user : PartyAPI.getUser(pT)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.twoStopAPI(pO, pT);
									}
								} return false;
							} else {
								sender.sendMessage(RED + "ERROR: 대련중이긴 하나 대련중인 파티를 찾을 수 없음");
								return false;
							}
							
						case 3:
							if (!main.threeVS) {
								sender.sendMessage(RED + "현재 3:3 대련중인 사람이 없습니다.");
								return false;
							}
							
							String tO = main.threeName.split(",")[0];
							String tT = main.threeName.split(",")[1];
							
							if (PartyAPI.isParty(tO) && PartyAPI.isParty(tT)) {
								for (String user : PartyAPI.getUser(tO)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.threeStopAPI(tO, tT);
									}
								}
								
								for (String user : PartyAPI.getUser(tT)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.threeStopAPI(tO, tT);
									}
								} return false;
							} else {
								sender.sendMessage(RED + "ERROR: 대련중이긴 하나 대련중인 파티를 찾을 수 없음");
								return false;
							}
							
						case 4:
							if (!main.fourVS) {
								sender.sendMessage(RED + "현재 3:3 대련중인 사람이 없습니다.");
								return false;
							}
							
							String fO = main.fourName.split(",")[0];
							String fT = main.fourName.split(",")[1];
							
							if (PartyAPI.isParty(fO) && PartyAPI.isParty(fT)) {
								for (String user : PartyAPI.getUser(fO)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.fourStopAPI(fO, fT);
									}
								}
								
								for (String user : PartyAPI.getUser(fT)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.fourStopAPI(fO, fT);
									}
								} return false;
							} else {
								sender.sendMessage(RED + "ERROR: 대련중이긴 하나 대련중인 파티를 찾을 수 없음");
								return false;
							}
							
						case 5:
							if (!main.oneVS) {
								sender.sendMessage(RED + "현재 콜로세움 대련중인 사람이 없습니다.");
								return false;
							}
							
							Player c1 = Bukkit.getPlayerExact(main.oneName.split(",")[0]);
							Player c2 = Bukkit.getPlayerExact(main.oneName.split(",")[1]);
							if (c1 != null && c2 != null) {
								c1.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
								c2.sendMessage("§6관리자가 대련을 강제로 종료하여 스폰으로 이동되었습니다.");
								main.coloType = -1;
								main.coloVS = false;
								main.coloName = null;
								main.coloBetting = 0;
		    					main.coloWinnerName = null;
								c1.chat("/spawn");
								c2.chat("/spawn");
								API.sendCommand("heal " + c1.getName());
								API.sendCommand("heal " + c2.getName());
								API.coloStopAPI(c1);
								API.coloStopAPI(c2);
								c1.getInventory().setHelmet(null);
								c1.getInventory().setChestplate(null);
								c1.getInventory().setLeggings(null);
								c1.getInventory().setBoots(null);
								c1.setItemInHand(null);
								c2.getInventory().setHelmet(null);
								c2.getInventory().setChestplate(null);
								c2.getInventory().setLeggings(null);
								c2.getInventory().setBoots(null);
								c2.setItemInHand(null);
								return false;
							} else {
								sender.sendMessage(RED + "ERROR: 대련중이긴 하나 대련중인 플레이어를 찾을 수 없음");
								return false;
							}
							
							
							
							
							
							
							
							
							
						}
					} else {
						if (!sender.isOp()) {
							sender.sendMessage(RED + "당신은 권한이 없으므로 이 명령어를 실행할 수 없습니다.");
							return false;
						}
						
						sender.sendMessage(GOLD + "/경기취소 <1/2/3/4/5> " + WHITE + "- 해당 숫자의 경기를 취소합니다.");
						return false;
					}
				}
				
				else {
					HelpStopMessage(sender);
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (API.isUserVSMatch(p)) {
					GUI.stopGUI(p);
					return false;
				}
				
				String party = PartyAPI.getJoinParty(p);
				if (party != null) {
					if (API.isPartyVSMatch(party)) {
						GUI.stopGUI(p);
						return false;
					}
				}
				
				GUI.openGUI(p);
				return false;
			} else {
				return false;
			}
		} return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage("§e ---- §6대련 위치 설정 §e-- §6페이지 §c1§6/§c1 §e----");
		sender.sendMessage("§6/대련위치, /대전위치, /PVP위치 §f- 대련 위치 설정 도움말을 봅니다.");
		sender.sendMessage("§6/대련위치 콜로세움 <1/2> §f- 콜로세움 대련 위치를 설정합니다.");
		sender.sendMessage("§6/대련위치 콜로세움 관전추가 §f- 콜로세움 관전 포지션을 추가합니다.");
		sender.sendMessage("§6/대련위치 1:1 <1/2> §f- 1:1 대련 위치를 설정합니다.");
		sender.sendMessage("§6/대련위치 2:2 <RED/BLUE> <1/2> §f- 2:2 대련 위치를 설정합니다.");
		sender.sendMessage("§6/대련위치 3:3 <RED/BLUE> <1/2/3> §f- 3:3 대련 위치를 설정합니다.");
		sender.sendMessage("§6/대련위치 4:4 <RED/BLUE> <1/2/3/4> §f- 4:4 대련 위치를 설정합니다.");
	}
	
	public static void HelpStopMessage(CommandSender sender) {
		sender.sendMessage(YELLOW + " ---- " + GOLD + "경기 취소 도움말 " + YELLOW + "-- " + GOLD + "페이지 " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/경기취소 " + WHITE + "- 경기 취소 도움말을 봅니다.");
		sender.sendMessage(GOLD + "/경기취소 <1/2/3/4/5> " + WHITE + "- 해당 숫자의 경기를 취소합니다.");
		sender.sendMessage(WHITE + "1. <" + YELLOW + "1:1" + WHITE + ">, 2. <" + YELLOW + "2:2" + WHITE + ">, 3. <" + YELLOW + "3:3" + WHITE + ">, 4. <" + YELLOW + "4:4" + WHITE + ">, 5.<" + YELLOW + "콜로세움" + WHITE + ">");
	}
}
