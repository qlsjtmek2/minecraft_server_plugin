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
				
				if (Method.getInfoInt(p, "���� ī��Ʈ") == 1) {
					p.sendMessage("��e----------------------------------------------------------------");
					p.sendMessage("");
					p.sendMessage("��6�ƽ��ƽ��� ��c�ʱ� PvP���6�� �����ϼ̽��ϴ�!");
					p.sendMessage("");
					p.sendMessage("��e----------------------------------------------------------------");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp PvP_world " + p.getName());
					return false;
				}

				else if (Method.getInfoInt(p, "���� ī��Ʈ") == 2) {
					int RandomAmount = new Random().nextInt(4) + 1;
					p.sendMessage("��e----------------------------------------------------------------");
					p.sendMessage("");
					p.sendMessage("��6���������� ��c�ݷμ����6�� �����ϼ̽��ϴ�!");
					p.sendMessage("");
					p.sendMessage("��e----------------------------------------------------------------");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp pvp_" + RandomAmount + " " + p.getName());
					return false;
				}

				else if (Method.getInfoInt(p, "���� ī��Ʈ") == 3) {
					int RandomAmount = new Random().nextInt(2) + 1;
					p.sendMessage("��e----------------------------------------------------------------");
					p.sendMessage("");
					p.sendMessage("��6������ ��cBlock PvP���6�� �����ϼ̽��ϴ�!");
					p.sendMessage("");
					p.sendMessage("��e----------------------------------------------------------------");
					switch (RandomAmount) {
					case 1:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp PvP_blue " + p.getName());
						break;
					case 2:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp PvP_red " + p.getName());
					}
					return false;
				}
				
				else if (Method.getInfoInt(p, "���� ī��Ʈ") == 4) {
					int RandomAmount = new Random().nextInt(4) + 1;
					p.sendMessage("��e----------------------------------------------------------------");
					p.sendMessage("");
					p.sendMessage("��6ȭ���� ��c������6�� �����ϼ̽��ϴ�!");
					p.sendMessage("");
					p.sendMessage("��e----------------------------------------------------------------");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp " + RandomAmount + ".PVP " + p.getName());
					return false;
				}
				
				else {
					if (p.isOp()) {
						p.sendMessage("��e----------------------------------------------------------------");
						p.sendMessage("");
						p.sendMessage("��6�ƽ��ƽ��� ��c�ʱ� PvP���6�� �����ϼ̽��ϴ�!");
						p.sendMessage("");
						p.sendMessage("��e----------------------------------------------------------------");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp PvP_world " + p.getName());
						return false;
					}
					
					p.sendMessage("��6����� ��c������ �����Ƿ� ��6�����Ͻ� �� �����ϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6/pvp ��f- pvp������ ������ �°� �̵��մϴ�.");
			return false;
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("���") || commandLabel.equalsIgnoreCase("����")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						Help(p, commandLabel);
						return true;
					}
					
					else if (args.length >= 1) {
						if (args[0].equalsIgnoreCase("��Ī") || args[0].equalsIgnoreCase("gui")) {
							if (API.isUserVSMatch(p)) {
								GUI.stopGUI(p);
								return false;
							}
							
							GUI.openGUI(p);
							return true;
						}
						
						else if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("view")) {
							PVPPlayer pp = API.getPVPPlayera(p.getName());
					        p.sendMessage("��6����� Ŭ����: " + pp.getclassExpress());
					        p.sendMessage("��6����� ų: ��c" + pp.getKill());
					        p.sendMessage("��6����� ����: ��c" + pp.getDeath());
					        p.sendMessage("��6����� ����� óġ: ��c" + pp.getKillDivDeath());
					        return true;
						}
						
						else if (args[0].equalsIgnoreCase("��ŷ") || args[0].equalsIgnoreCase("rank")) {
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
						
						else if (args[0].equalsIgnoreCase("�˻�") || args[0].equalsIgnoreCase("searchrank")) {
							if (args.length == 2) {
								boolean is = PlayerYml.isUser(p.getName());
								if (!is)
									p.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
								else
									Ranking.searchRanking(sender, p.getName());
								return false;
							} else {
								Help(p, commandLabel);
								return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("��޵�") || args[0].equalsIgnoreCase("class")) 	{
							p.sendMessage("��6---------------------------------");
							p.sendMessage("��e * ��7F ��f[��e5ų��f]-> ��eE");
							p.sendMessage("��e * ��eE ��f[��e30ų��f]-> ��6D");
							p.sendMessage("��e * ��6D ��f[��e60ų��f]-> ��bC");
							p.sendMessage("��e * ��bC ��f[��e120ų��f]-> ��dB");
							p.sendMessage("��e * ��dB ��f[��e300ų��f]-> ��cA");
							p.sendMessage("��e * ��cA ��f[��e500ų��f]-> ��4S");
							p.sendMessage("��e * ��4S ��f[��e800ų��f]-> ��4��lSS");
							p.sendMessage("��6---------------------------------");
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("�±�") || args[0].equalsIgnoreCase("upgrade")) {
							API.Upgrade(p);
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("B") || args[0].equalsIgnoreCase("A") || args[0].equalsIgnoreCase("S") || args[0].equalsIgnoreCase("SS")) {
							PVPPlayer pp = API.getPVPPlayera(p.getName());
							if (pp.getclass().equalsIgnoreCase(args[0])) {
								p.chat("/kit " + args[0]);
								return false;
							} else {
								p.sendMessage("��c����� ��ũ�� ���� �ʾ� ������ ������ �� �����ϴ�.");
								return false;
							}
						}
					
						else if (args[0].equalsIgnoreCase("������Ʈ") || args[0].equalsIgnoreCase("update")) {
							if (!p.isOp()) {
								sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
								return false;
							}
							
							Ranking.updateRanking();
					        for (PVPPlayer player : API.pvpplayer.values()) {
					            player.saveRpgPlayer();
					        }
					        main.user.saveConfig();
					        System.out.println("[ActionPvP] ���� ���� �ڵ� ���� �Ϸ�");
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("set")) {
							if (!p.isOp()) {
								sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
								return false;
							}
							
							if (!(args.length == 4)) {
								Help(p, commandLabel);
								return false;
							}
							
							if (!PlayerYml.isUser(args[2])) {
								sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							Scanner scan = new Scanner(args[3]);
							if (!scan.hasNextInt()) {
								sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
								return false;
							}

							String name = Bukkit.getPlayer(args[2]).getName();
							PVPPlayer pp = API.getPVPPlayera(name);
							if (args[1].equalsIgnoreCase("ų") || args[1].equalsIgnoreCase("Kill")) {
								pp.setKill(Integer.parseInt(args[3]));
								sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ų ���� ��c" + args[3] + " ��6��ŭ �����Ͽ����ϴ�.");
								return false;
							}
							
							else if (args[1].equalsIgnoreCase("����") || args[1].equalsIgnoreCase("Death")) {
								pp.setDeath(Integer.parseInt(args[3]));
								sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ���� ���� ��c" + args[3] + " ��6��ŭ �����Ͽ����ϴ�.");
								return false;
							}
							
							else {
								Help(p, commandLabel);
								return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("�߰�")) {
							if (!p.isOp()) {
								sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
								return false;
							}
							
							if (!(args.length == 4)) {
								Help(p, commandLabel);
								return false;
							}
							
							if (!PlayerYml.isUser(args[2])) {
								sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							Scanner scan = new Scanner(args[3]);
							if (!scan.hasNextInt()) {
								sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
								return false;
							}
							
							String name = Bukkit.getPlayer(args[2]).getName();
							PVPPlayer pp = API.getPVPPlayera(name);
							if (args[1].equalsIgnoreCase("ų") || args[1].equalsIgnoreCase("Kill")) {
								pp.addKill(Integer.parseInt(args[3]));
								sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ų ���� ��c" + args[3] + " ��6��ŭ �߰��Ͽ����ϴ�.");
								return false;
							}
							
							else if (args[1].equalsIgnoreCase("����") || args[1].equalsIgnoreCase("Death")) {
								pp.addDeath(Integer.parseInt(args[3]));
								sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ���� ���� ��c" + args[3] + " ��6��ŭ �߰��Ͽ����ϴ�.");
								return false;
							}
							
							else {
								Help(p, commandLabel);
								return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("����")) {
							if (!p.isOp()) {
								sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
								return false;
							}
							
							if (!(args.length == 4)) {
								Help(p, commandLabel);
								return false;
							}
							
							if (!PlayerYml.isUser(args[2])) {
								sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							Scanner scan = new Scanner(args[3]);
							if (!scan.hasNextInt()) {
								sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
								return false;
							}
							
							String name = Bukkit.getPlayer(args[2]).getName();
							PVPPlayer pp = API.getPVPPlayera(name);
							if (args[1].equalsIgnoreCase("ų") || args[1].equalsIgnoreCase("Kill")) {
								pp.subKill(Integer.parseInt(args[3]));
								sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ų ���� ��c" + args[3] + " ��6��ŭ �Q���ϴ�.");
								return false;
							}
							
							else if (args[1].equalsIgnoreCase("����") || args[1].equalsIgnoreCase("Death")) {
								pp.subDeath(Integer.parseInt(args[3]));
								sender.sendMessage("��6���������� ��c" + args[2] + "��6���� ���� ���� ��c" + args[3] + " ��6��ŭ �Q���ϴ�.");
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
					} return false;
				} else {
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("������") || commandLabel.equalsIgnoreCase("������") || commandLabel.equalsIgnoreCase("�������")) {
				if (args.length == 0) {
					if (!sender.isOp()) {
						sender.sendMessage(RED + "����� ������ �����Ƿ� �� ��ɾ ������ �� �����ϴ�.");
						return false;
					}
					
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
		sender.sendMessage("��e ---- ��6��� ��ġ ���� ��e-- ��6������ ��c1��6/��c1 ��e----");
		sender.sendMessage("��6/�����ġ, /������ġ, /PVP��ġ ��f- ��� ��ġ ���� ������ ���ϴ�.");
		sender.sendMessage("��6/�����ġ 1:1 <1/2> ��f- 1:1 ��� ��ġ�� �����մϴ�.");
	}
	
	public static void Help(Player sender, String command) {
		if (command.equalsIgnoreCase("���") || command.equalsIgnoreCase("����")) {
			sender.sendMessage("��e ---- ��6��� ��e-- ��6������ ��c1��6/��c1 ��e----");
			sender.sendMessage(" ��e-- ��6����� Ŭ������ " + API.getPVPPlayera(sender.getName()).getclassExpress() + " ��6�Դϴ�. ��e--");
			sender.sendMessage("��6/���, /���� ��f- ��� ������ ���ϴ�.");
			sender.sendMessage("��c��l/PVP ��f- �볭���� �ٷ� �����մϴ�.");
			sender.sendMessage("��c��l/" + command + " ��Ī ��f- PVP ��Ī�� �� �� �ִ� â�� ���ϴ�.");
			sender.sendMessage("��6/" + command + " ���� ��f- �ڽ��� PVP ������ Ȯ���մϴ�.");
			sender.sendMessage("��6/" + command + " ��ŷ ��f- �÷��̾���� PVP ��ŷ�� Ȯ���մϴ�.");
			sender.sendMessage("��6/" + command + " �˻� <�г���> ��f- ������ PVP ��ŷ ������ �˻��մϴ�.");
			sender.sendMessage("��6/" + command + " �±� ��f- �ڽ��� PVP Ŭ������ ������ ���� �� �±޽�ŵ�ϴ�.");
			sender.sendMessage("��6/" + command + " <B/A/S/SS> ��f- 4�ð����� ��ũ ���� ������ �޽��ϴ�");
			sender.sendMessage("��6/" + command + " ��޵� ��f- Ŭ������ ��޵��� ���ϴ�.");
			if (sender.isOp())
			{
				sender.sendMessage("��f");
				sender.sendMessage("��b/" + command + " <����/�߰�/����> <ų/����> <�г���> <��>");
				sender.sendMessage("��b/" + command + " ������Ʈ ��f- ��ŷ�� ������Ʈ�մϴ�.");
				sender.sendMessage("��b/������, ������, ������� ��f- 1:1 ����� ����մϴ�.");
			}
			sender.sendMessage("��f");
			sender.sendMessage("��e* B Ŭ���� �̻���ʹ� ��ɾ�� ������ ������ �� �ֽ��ϴ�.");
		}
	}
}
