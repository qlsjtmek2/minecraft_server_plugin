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
			if (commandLabel.equalsIgnoreCase("���") || commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("PVP")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 6) {
						if (args[0].equalsIgnoreCase("��") && args[1].equalsIgnoreCase("Ÿ") && args[2].equalsIgnoreCase("��") &&
							args[3].equalsIgnoreCase("��") && args[4].equalsIgnoreCase("��") && args[5].equalsIgnoreCase("ȭ")) {
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
			
			else if (commandLabel.equalsIgnoreCase("�����ġ") || commandLabel.equalsIgnoreCase("������ġ") || commandLabel.equalsIgnoreCase("PVP��ġ")) {
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
									p.sendMessage("��6���������� 1:1 ��� ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "2":
									Position.setOneBlue(p.getLocation());
									p.sendMessage("��6���������� 1:1 ��� ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								default: HelpMessage(sender); return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("�ݷμ���")) {
							switch (args[1]) {
							case "1":
								Position.setColoRed(p.getLocation());
								p.sendMessage("��6���������� �ݷμ��� ��� ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
									
							case "2":
								Position.setColoBlue(p.getLocation());
								p.sendMessage("��6���������� �ݷμ��� ��� ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
								
							case "�����߰�":
								Position.addColoWatchLocation(p.getLocation());
								p.sendMessage("��6���������� �ݷμ��� ��� ��c���� ��� ��6�� �߰��ϼ̽��ϴ�.");
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
									p.sendMessage("��6���������� 2:2 ��� RED ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "2":
									Position.setTwoRed2(p.getLocation());
									p.sendMessage("��6���������� 2:2 ��� RED ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
							
							else if (args[1].equalsIgnoreCase("BLUE")) {
								switch (args[2]) {
								case "1":
									Position.setTwoBlue1(p.getLocation());
									p.sendMessage("��6���������� 2:2 ��� BLUE ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "2":
									Position.setTwoBlue2(p.getLocation());
									p.sendMessage("��6���������� 2:2 ��� BLUE ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
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
									p.sendMessage("��6���������� 3:3 ��� RED ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "2":
									Position.setThreeRed2(p.getLocation());
									p.sendMessage("��6���������� 3:3 ��� RED ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "3":
									Position.setThreeRed3(p.getLocation());
									p.sendMessage("��6���������� 3:3 ��� RED ��c����° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
							
							else if (args[1].equalsIgnoreCase("BLUE")) {
								switch (args[2]) {
								case "1":
									Position.setThreeBlue1(p.getLocation());
									p.sendMessage("��6���������� 3:3 ��� BLUE ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "2":
									Position.setThreeBlue2(p.getLocation());
									p.sendMessage("��6���������� 3:3 ��� BLUE ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "3":
									Position.setThreeBlue3(p.getLocation());
									p.sendMessage("��6���������� 3:3 ��� BLUE ��c����° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
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
									p.sendMessage("��6���������� 4:4 ��� RED ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "2":
									Position.setFourRed2(p.getLocation());
									p.sendMessage("��6���������� 4:4 ��� RED ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "3":
									Position.setFourRed3(p.getLocation());
									p.sendMessage("��6���������� 4:4 ��� RED ��c����° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "4":
									Position.setFourRed4(p.getLocation());
									p.sendMessage("��6���������� 4:4 ��� RED ��c�׹�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								default: HelpMessage(sender); return false;
								}
							}
							
							else if (args[1].equalsIgnoreCase("BLUE")) {
								switch (args[2]) {
								case "1":
									Position.setFourBlue1(p.getLocation());
									p.sendMessage("��6���������� 4:4 ��� BLUE ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "2":
									Position.setFourBlue2(p.getLocation());
									p.sendMessage("��6���������� 4:4 ��� BLUE ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "3":
									Position.setFourBlue3(p.getLocation());
									p.sendMessage("��6���������� 4:4 ��� BLUE ��c����° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
									return false;
									
								case "4":
									Position.setFourBlue4(p.getLocation());
									p.sendMessage("��6���������� 4:4 ��� BLUE ��c�׹�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
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
			
			else if (commandLabel.equalsIgnoreCase("����")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (main.coloType != 1) {
						p.sendMessage("��c���� �ݷμ��� ��������� �ʰų� ������ ���� �ƴմϴ�.");
						return false;
					}
					
					if (!p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
						p.sendMessage("��c[ /���� ] �Է��ؼ� ���� ���� ���� ������ �����մϴ�.");
						return false;
					}
					
					if (API.isColoVSPlaying(p.getName())) {
						p.sendMessage("��c�ݷμ��� ������϶��� ������ �Ұ����մϴ�.");
						return false;
					}
					
					if (main.coloMoney.get(p) != null) {
						p.sendMessage("��c����� �̹� ������ ������ �����Դϴ�.");
						return false;
					}
					
					if (args.length != 1) {
						p.sendMessage("��6/���� <1/2> ��f- 1 �÷��̾ 2 �÷��̾�� " + main.coloBetting + " �� ��ŭ ������ �մϴ�.");
						p.sendMessage("��61. ��c" + API.getColoPlayer(1));
						p.sendMessage("��62. ��c" + API.getColoPlayer(2));
						return false;
					}
					
					if (!args[0].equalsIgnoreCase("1") && !args[0].equalsIgnoreCase("2")) {
						p.sendMessage("��6/���� <1/2> ��f- 1 �÷��̾ 2 �÷��̾�� " + main.coloBetting + " �� ��ŭ ������ �մϴ�.");
						p.sendMessage("��61. ��c" + API.getColoPlayer(1));
						p.sendMessage("��62. ��c" + API.getColoPlayer(2));
						return false;
					}
					
					if (main.economy.getBalance(p.getName()) < main.coloBetting) {
						p.sendMessage("��c����� ���� �����Ͽ� ������ �Ͻ� �� �����ϴ�.");
						return false;
					}
					
					String name = API.getColoPlayer(Integer.parseInt(args[0]));
					main.coloMoney.put(p, name + "," + main.coloBetting);
					main.economy.withdrawPlayer(p.getName(), main.coloBetting);
					p.sendMessage("��6���������� ��c" + name + " ��6�Կ��� ��c" + main.coloBetting + " ��6���� �ɾ����ϴ�.");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("����")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					p.teleport(Position.getRandomColoWatchLocation());
					p.getLocation().getChunk().load();
					p.sendMessage("��6�ݷμ��� ���������� ��c�̵� ��6�Ͽ����ϴ�.");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("������") || commandLabel.equalsIgnoreCase("������") || commandLabel.equalsIgnoreCase("�������")) {
				if (args.length == 1) {
					if (!sender.isOp()) {
						sender.sendMessage(RED + "����� ������ �����Ƿ� �� ��ɾ ������ �� �����ϴ�.");
						return false;
					}
					
					String arg = args[0];
					
					if (arg.equalsIgnoreCase("1") || arg.equalsIgnoreCase("2") || arg.equalsIgnoreCase("3") || arg.equalsIgnoreCase("4") || arg.equalsIgnoreCase("5")) {
						int num = Integer.parseInt(arg);
						
						switch (num) {
						case 1:
							if (!main.oneVS) {
								sender.sendMessage(RED + "���� 1:1 ������� ����� �����ϴ�.");
								return false;
							}
							
							Player o1 = Bukkit.getPlayerExact(main.oneName.split(",")[0]);
							Player o2 = Bukkit.getPlayerExact(main.oneName.split(",")[1]);
							if (o1 != null && o2 != null) {
								o1.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
								o2.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
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
								sender.sendMessage(RED + "ERROR: ������̱� �ϳ� ������� �÷��̾ ã�� �� ����");
								return false;
							}
							
						case 2:
							if (!main.twoVS) {
								sender.sendMessage(RED + "���� 2:2 ������� ����� �����ϴ�.");
								return false;
							}
							
							String pO = main.twoName.split(",")[0];
							String pT = main.twoName.split(",")[1];
							
							if (PartyAPI.isParty(pO) && PartyAPI.isParty(pT)) {
								for (String user : PartyAPI.getUser(pO)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.twoStopAPI(pO, pT);
									}
								}
								
								for (String user : PartyAPI.getUser(pT)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.twoStopAPI(pO, pT);
									}
								} return false;
							} else {
								sender.sendMessage(RED + "ERROR: ������̱� �ϳ� ������� ��Ƽ�� ã�� �� ����");
								return false;
							}
							
						case 3:
							if (!main.threeVS) {
								sender.sendMessage(RED + "���� 3:3 ������� ����� �����ϴ�.");
								return false;
							}
							
							String tO = main.threeName.split(",")[0];
							String tT = main.threeName.split(",")[1];
							
							if (PartyAPI.isParty(tO) && PartyAPI.isParty(tT)) {
								for (String user : PartyAPI.getUser(tO)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.threeStopAPI(tO, tT);
									}
								}
								
								for (String user : PartyAPI.getUser(tT)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.threeStopAPI(tO, tT);
									}
								} return false;
							} else {
								sender.sendMessage(RED + "ERROR: ������̱� �ϳ� ������� ��Ƽ�� ã�� �� ����");
								return false;
							}
							
						case 4:
							if (!main.fourVS) {
								sender.sendMessage(RED + "���� 3:3 ������� ����� �����ϴ�.");
								return false;
							}
							
							String fO = main.fourName.split(",")[0];
							String fT = main.fourName.split(",")[1];
							
							if (PartyAPI.isParty(fO) && PartyAPI.isParty(fT)) {
								for (String user : PartyAPI.getUser(fO)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.fourStopAPI(fO, fT);
									}
								}
								
								for (String user : PartyAPI.getUser(fT)) {
									Player p = Bukkit.getPlayerExact(user);
									
									if (p != null) {
										p.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.fourStopAPI(fO, fT);
									}
								} return false;
							} else {
								sender.sendMessage(RED + "ERROR: ������̱� �ϳ� ������� ��Ƽ�� ã�� �� ����");
								return false;
							}
							
						case 5:
							if (!main.oneVS) {
								sender.sendMessage(RED + "���� �ݷμ��� ������� ����� �����ϴ�.");
								return false;
							}
							
							Player c1 = Bukkit.getPlayerExact(main.oneName.split(",")[0]);
							Player c2 = Bukkit.getPlayerExact(main.oneName.split(",")[1]);
							if (c1 != null && c2 != null) {
								c1.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
								c2.sendMessage("��6�����ڰ� ����� ������ �����Ͽ� �������� �̵��Ǿ����ϴ�.");
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
								sender.sendMessage(RED + "ERROR: ������̱� �ϳ� ������� �÷��̾ ã�� �� ����");
								return false;
							}
							
							
							
							
							
							
							
							
							
						}
					} else {
						if (!sender.isOp()) {
							sender.sendMessage(RED + "����� ������ �����Ƿ� �� ��ɾ ������ �� �����ϴ�.");
							return false;
						}
						
						sender.sendMessage(GOLD + "/������ <1/2/3/4/5> " + WHITE + "- �ش� ������ ��⸦ ����մϴ�.");
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
		sender.sendMessage("��e ---- ��6��� ��ġ ���� ��e-- ��6������ ��c1��6/��c1 ��e----");
		sender.sendMessage("��6/�����ġ, /������ġ, /PVP��ġ ��f- ��� ��ġ ���� ������ ���ϴ�.");
		sender.sendMessage("��6/�����ġ �ݷμ��� <1/2> ��f- �ݷμ��� ��� ��ġ�� �����մϴ�.");
		sender.sendMessage("��6/�����ġ �ݷμ��� �����߰� ��f- �ݷμ��� ���� �������� �߰��մϴ�.");
		sender.sendMessage("��6/�����ġ 1:1 <1/2> ��f- 1:1 ��� ��ġ�� �����մϴ�.");
		sender.sendMessage("��6/�����ġ 2:2 <RED/BLUE> <1/2> ��f- 2:2 ��� ��ġ�� �����մϴ�.");
		sender.sendMessage("��6/�����ġ 3:3 <RED/BLUE> <1/2/3> ��f- 3:3 ��� ��ġ�� �����մϴ�.");
		sender.sendMessage("��6/�����ġ 4:4 <RED/BLUE> <1/2/3/4> ��f- 4:4 ��� ��ġ�� �����մϴ�.");
	}
	
	public static void HelpStopMessage(CommandSender sender) {
		sender.sendMessage(YELLOW + " ---- " + GOLD + "��� ��� ���� " + YELLOW + "-- " + GOLD + "������ " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/������ " + WHITE + "- ��� ��� ������ ���ϴ�.");
		sender.sendMessage(GOLD + "/������ <1/2/3/4/5> " + WHITE + "- �ش� ������ ��⸦ ����մϴ�.");
		sender.sendMessage(WHITE + "1. <" + YELLOW + "1:1" + WHITE + ">, 2. <" + YELLOW + "2:2" + WHITE + ">, 3. <" + YELLOW + "3:3" + WHITE + ">, 4. <" + YELLOW + "4:4" + WHITE + ">, 5.<" + YELLOW + "�ݷμ���" + WHITE + ">");
	}
}
