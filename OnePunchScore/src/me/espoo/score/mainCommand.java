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
			if (commandLabel.equalsIgnoreCase("���")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (PlayerYml.getInfoBoolean(p, "���")) {
						p.sendMessage("��c����� �̹� ���� ����� ��ȸ�� ��ϵǾ� �ֽ��ϴ�.");
						return false;
					}
					
					if (PlayerYml.getInfoBoolean(p, "��Ͽ���")) {
						Method.castLvup(p);
						p.sendMessage("��6���������� ���� ����� ��ȸ�� ��c��ϡ�6�ϼ̽��ϴ�!");
						PlayerYml.setInfoBoolean(p, "���", true);
						return false;
					}

					p.sendMessage("");
					p.sendMessage("��c����� ���� ���� ����� ��ȸ�� ����� �� �����ϴ�.");
					p.sendMessage("��f- ��c���� ���丮 ����Ʈ�� �޼��ؾ߸� ����� �����մϴ�.");
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				sender.sendMessage("��6/��� ��f- ���� ����� ��ȸ�� ����մϴ�.");
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("����")) {
				if (args.length == 0) {
					if (sender instanceof Player) {
						displayHelp(sender);
						return false;
					} else {
						displayConsol(sender);
						return false;
					}
				}
				
				if (args[0].equalsIgnoreCase("Ȯ��") && args.length == 2 || args[0].equalsIgnoreCase("����") && args.length == 2) {
					Player p = Method.getOnorOffLine(Method.searchOnlinePlayer(args[1]));
					
					if (p == null) {
						sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					if (sender.isOp())
					{
						String str;
						if (Method.getPlayerRanking(p.getName()) <= 15) {
							str = "��4S";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "��cA";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "��bB";
						}
						
						else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "���") && Method.getPlayerRanking(p.getName()) > 15)
						{
							str = "��eC";
						}
						
						else {
							str = "����";
						}
						
						sender.sendMessage(YELLOW + " * " + args[1] + " " + GOLD + "���� " + RED + "����");
						sender.sendMessage(YELLOW + " ������ " + RED + "����� ���� " + GRAY + ":: " + WHITE + Method.getPlayerScore(p.getName()));
						sender.sendMessage(YELLOW + " ������ " + RED + "�÷��̾� ���� " + GRAY + ":: " + WHITE + Method.getPlayerRanking(p.getName()));
						sender.sendMessage(YELLOW + " ������ " + RED + "�÷��̾� ��ũ " + GRAY + ":: " + WHITE + str);
						sender.sendMessage(YELLOW + " ������ " + RED + "���� ���ʽ� " + GRAY + ":: " + WHITE + PlayerYml.getInfoInt(p, "���ʽ�") + "%");
						
						if (GuildAPI.getJoinGuild(p) != null) {
							int i = GuildAPI.getScoreStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
							sender.sendMessage(YELLOW + " ������ " + RED + "��� ���ʽ� " + GRAY + ":: " + WHITE + i + "%");
						} return false;
					}
					
					if (Method.getPlayerSeeInfo(p))
					{
						if (PlayerYml.getInfoBoolean(p, "��Ͽ���") == false)
						{
							sender.sendMessage(RED + "�� �÷��̾�� ����ο� ���ԵǾ����� �ʽ��ϴ�.");
							return false;
						}
						
						String str;
						if (Method.getPlayerRanking(p.getName()) <= 15) {
							str = "��4S";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "��cA";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "��bB";
						}
						
						else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "���") && Method.getPlayerRanking(p.getName()) > 15)
						{
							str = "��eC";
						}
						
						else {
							str = "����";
						}
						
						sender.sendMessage(YELLOW + " * " + args[1] + " " + GOLD + "���� " + RED + "����");
						sender.sendMessage(YELLOW + " ������ " + RED + "����� ���� " + GRAY + ":: " + WHITE + Method.getPlayerScore(p.getName()));
						sender.sendMessage(YELLOW + " ������ " + RED + "�÷��̾� ���� " + GRAY + ":: " + WHITE + Method.getPlayerRanking(p.getName()));
						sender.sendMessage(YELLOW + " ������ " + RED + "�÷��̾� ��ũ " + GRAY + ":: " + WHITE + str);
						sender.sendMessage(YELLOW + " ������ " + RED + "���� ���ʽ� " + GRAY + ":: " + WHITE + PlayerYml.getInfoInt(p, "���ʽ�") + "%");
						
						if (GuildAPI.getJoinGuild(p) != null) {
							int i = GuildAPI.getScoreStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
							sender.sendMessage(YELLOW + " ������ " + RED + "��� ���ʽ� " + GRAY + ":: " + WHITE + i + "%");
						} return false;
					}
					
					else
					{
						sender.sendMessage(RED + "������ ���� ���⸦ �㰡���� �ʾҽ��ϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("��ŷ"))
				{
					List<String> list = ScoreYml.ArrayScore();
					int i = 1;
					
					sender.sendMessage(YELLOW + "---------------------------------------------------");
					for (String str : list)
					{
						if (i >= 16) break;
						sender.sendMessage(GOLD + " ���� " + RED + i + "�� " + GRAY + ":: " + WHITE + "[" + DARK_RED + "S" + WHITE + "] " + str.split(",")[0] + " / " + str.split(",")[1]);
						i++;
					}
					sender.sendMessage(YELLOW + "---------------------------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("��޵�"))
				{
					sender.sendMessage("��6---------------------------------");
					sender.sendMessage("��e * ��f���� ��f[��e���� ����� ��ϡ�f]-> ��eC");
					sender.sendMessage("��e * ��eC ��f[��e4000����f]-> ��dB");
					sender.sendMessage("��e * ��dB ��f[��e30000����f]-> ��cA");
					sender.sendMessage("��e * ��cA ��f[��e��ŷ 15�� ����f]-> ��4S");
					sender.sendMessage("��6---------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("���ʽ�"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage(BLUE + "/���� ���ʽ� <�г���> <�ۼ�Ʈ> " + WHITE + "- �÷��̾��� ���� ���ʽ��� �����մϴ�.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "�� �÷��̾�� �¶��� ���°� �ƴմϴ�.");
						return false;
					}
					
					Player argsP = Method.getOnorOffLine(str);
					Method.setPlayerBouns(argsP, Integer.parseInt(args[2]));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("������ֱ�"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 2))
					{
						sender.sendMessage(GOLD + "/���� ������ֱ� <�г���> " + WHITE + "- ���� ����� ��ȸ�� ����� ������ �ݴϴ�.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "�� �÷��̾�� �¶��� ���°� �ƴմϴ�.");
						return false;
					}
					
					Player argsP = Method.getOnorOffLine(str);
					PlayerYml.setInfoBoolean(argsP, "��Ͽ���", true);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage(BLUE + "/���� <����/�߰�/����> <�г���> <��> " + WHITE + "- �÷��̾��� ������ �����մϴ�.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "�� �÷��̾�� �¶��� ���°� �ƴմϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage(RED + "<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}

					Player argsP = Method.getOnorOffLine(str);
					ScoreYml.setPlayerScore(argsP.getName(), Integer.parseInt(args[2]));
					sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��c" + args[2] + " ��6��ŭ �����Ͽ����ϴ�.");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�߰�"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage(BLUE + "/���� <����/�߰�/����> <�г���> <��> " + WHITE + "- �÷��̾��� ������ �����մϴ�.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "�� �÷��̾�� �¶��� ���°� �ƴմϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage(RED + "<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}

					Player argsP = Method.getOnorOffLine(str);
					if (PlayerYml.getInfoBoolean(argsP, "��Ͽ���") == false)
					{
						return false;
					}
					
					if (PartyAPI.getJoinParty(argsP) == null) {
						int i = Integer.parseInt(args[2]);
						double val = PlayerYml.getInfoInt(argsP, "���ʽ�");
						if (GuildAPI.getJoinGuild(argsP) != null) val += GuildAPI.getScoreStat(GuildAPI.getJoinGuild(argsP));
						val = val / 100;
						val = Double.parseDouble(Method.roundDot1Off(Double.toString(val)));
						double y = (double) i * val;
						ScoreYml.addPlayerScore(argsP.getName(), (int) y);
						argsP.sendMessage("��6����� ������ ��c��ɡ�6�Ͽ� ��c" + args[2] + "(x" + val + ") ��6��ŭ�� ������ ��cȹ���6�ϼ̽��ϴ�.");
						
						if (GuildAPI.getJoinGuild(argsP) != null) {
							int num = (int) y; num /= 10;
							GuildAPI.addPoint(GuildAPI.getJoinGuild(argsP), num);
						}
					} else {
						if (PartyAPI.getShare(PartyAPI.getJoinParty(argsP))) {
							String name = PartyAPI.getJoinParty(argsP);
							int i = Integer.parseInt(args[2]);
							double val = PlayerYml.getInfoInt(argsP, "���ʽ�");
							if (GuildAPI.getJoinGuild(argsP) != null) val += GuildAPI.getScoreStat(GuildAPI.getJoinGuild(argsP));
							val = val / 100;
							val = Double.parseDouble(Method.roundDot1Off(Double.toString(val)));
							double y = (double) i * val;
							double total = 0.8;
							total += PartyAPI.getUserInteger(name) * 0.05;
							total *= y;
							ScoreYml.addPlayerScore(argsP.getName(), (int) total);
							argsP.sendMessage("��6����� ������ ��c��ɡ�6�Ͽ� ��c" + args[2] + "(x" + val + ") ��6��ŭ�� ������ ��cȹ���6�ϼ̽��ϴ�.");
							
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
										String mess = "��6����� ��c" + argsP.getName() + "��6�Կ��� ��Ƽ ���ʽ��� ��c<score> ��6��ŭ ����� ������ �޾ҽ��ϴ�.";
										mess = mess.replaceAll("<score>", Integer.toString((int) total));
										pl.sendMessage(mess);
									}
								}
							}
						} else {
							int i = Integer.parseInt(args[2]);
							double val = PlayerYml.getInfoInt(argsP, "���ʽ�");
							if (GuildAPI.getJoinGuild(argsP) != null) val += GuildAPI.getScoreStat(GuildAPI.getJoinGuild(argsP));
							val = val / 100;
							val = Double.parseDouble(Method.roundDot1Off(Double.toString(val)));
							double y = (double) i * val;
							ScoreYml.addPlayerScore(argsP.getName(), (int) y);
							argsP.sendMessage("��6����� ������ ��c��ɡ�6�Ͽ� ��c" + args[2] + "(x" + val + ") ��6��ŭ�� ������ ��cȹ���6�ϼ̽��ϴ�.");
							
							if (GuildAPI.getJoinGuild(argsP) != null) {
								int num = (int) y; num /= 10;
								GuildAPI.addPoint(GuildAPI.getJoinGuild(argsP), num);
							}
						}
					}
					
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage(RED + "����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage(BLUE + "/���� <����/�߰�/����> <�г���> <��> " + WHITE + "- �÷��̾��� ������ �����մϴ�.");
						return false;
					}
					
					String str = Method.searchOnlinePlayer(args[1]);
					File f = new File("plugins/OnePunchScore/Player/" + str + ".yml");
					if (!f.exists())
					{
						sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					if (Method.getOnorOffLine(str) == null) {
						sender.sendMessage(RED + "�� �÷��̾�� �¶��� ���°� �ƴմϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage(RED + "<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}

					Player argsP = Method.getOnorOffLine(str);
					ScoreYml.subPlayerScore(argsP.getName(), Integer.parseInt(args[2]));
					sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ų ���� ��c" + args[2] + " ��6��ŭ �Q���ϴ�.");
					return false;
				}
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args[0].equalsIgnoreCase("Ȯ��") && args.length == 1 || args[0].equalsIgnoreCase("����") && args.length == 1)
					{
						if (PlayerYml.getInfoBoolean(p, "��Ͽ���") == false)
						{
							sender.sendMessage("��c����� ���� ����ΰ� �ƴϹǷ� ������ ���� �� �����ϴ�.");
							return false;
						}
						
						String str;
						if (Method.getPlayerRanking(p.getName()) <= 15) {
							str = "��4S";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "��cA";
						}
						
						else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
							str = "��bB";
						}
						
						else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "���") && Method.getPlayerRanking(p.getName()) > 15)
						{
							str = "��eC";
						}
						
						else {
							str = "����";
						}
						
						sender.sendMessage("��e * " + p.getName() + " ��6���� ��c����");
						sender.sendMessage("��e ������ ��c����� ���� ��7:: ��f" + Method.getPlayerScore(p.getName()));
						sender.sendMessage("��e ������ ��c�÷��̾� ���� ��7:: ��f" + Method.getPlayerRanking(p.getName()));
						sender.sendMessage("��e ������ ��c�÷��̾� ��ũ ��7:: ��f" +  str);
						sender.sendMessage("��e ������ ��c���� ���ʽ� ��7:: ��f" + PlayerYml.getInfoInt(p, "���ʽ�") + "%");
						
						if (GuildAPI.getJoinGuild(p) != null) {
							int i = GuildAPI.getScoreStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
							sender.sendMessage(YELLOW + " ������ " + RED + "��� ���ʽ� " + GRAY + ":: " + WHITE + i + "%");
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("����") && args.length == 2)
					{
						if (args[1].equalsIgnoreCase("���"))
						{
							if (Method.getPlayerSeeInfo(p) == true)
							{
								sender.sendMessage("��c�̹� ���� ���⸦ ����ϼ̽��ϴ�.");
								return false;
							}
							  
							PlayerYml.setInfoBoolean(p, "����", true);
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

							PlayerYml.setInfoBoolean(p, "����", false);
							sender.sendMessage("��6����� ������ ������ ���� ���� ��c�źΡ�6�߽��ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("���"))
					{
						if (PlayerYml.getInfoBoolean(p, "��Ͽ���") == false)
						{
							sender.sendMessage("��c����� ���� ���� ����� ��ȸ�� ����Ͻ� �� �����ϴ�.");
							return false;
						}
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("C"))
					{
						if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "���") && Method.getPlayerRanking(p.getName()) > 15)
						{
							p.performCommand("kit C");
							return false;
						}	
						  
						else
						{
							sender.sendMessage("��c����� ��eC ��c��ũ�� �ƴմϴ�.");
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
							sender.sendMessage("��c����� ��dB ��c��ũ�� �ƴմϴ�.");
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
							sender.sendMessage("��c����� A ��ũ�� �ƴմϴ�.");
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
							sender.sendMessage("��c����� ��4S ��c��ũ�� �ƴմϴ�.");
							return false;
						}
					}
					
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
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
			str = "��4S ��6��";
		}
		
		else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
			str = "��cA ��6��";
		}
		
		else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
			str = "��bB ��6��";
		}
		
		else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "���") && Method.getPlayerRanking(p.getName()) > 15)
		{
			str = "��eC ��6��";
		}
		
		else {
			str = "�������� �ʽ�";
		}
		
		sender.sendMessage(" ��e----- ��6����� ���� ��e--- ��6���� ��e-----");
		sender.sendMessage("  ��e-- ��6����� ����� " + str + "�ϴ�. ��e--");
		sender.sendMessage("��6/���� ��f- ����� ���� ������ ���ϴ�.");
		sender.sendMessage("��6/���� Ȯ�� ��f- �ڽ��� ����� ������ ���ϴ�.");
		sender.sendMessage("��6/���� Ȯ�� <�г���> ��f- �ٸ� ����� ų �� ������ ���ϴ�.");
		sender.sendMessage("��6/���� ���� ��f- S��ũ 15���� ���ϴ�.");
		sender.sendMessage("��6/���� <C/B/A/S> ��f- 4�ð����� ��ũ ���� ������ �޽��ϴ�");
		sender.sendMessage("��6/���� ���� <���/�ź�> ��f- ���� �ڽ��� ������ ���� ���� �����մϴ�. ��b( �⺻�� ����� )");
		sender.sendMessage("��6/���� ��޵� ��f- ��ũ�� ��޵��� ���ϴ�.");
		sender.sendMessage("��6/��� ��f- ���� ����� ��ȸ�� ����մϴ�.");
		
		if (p.isOp())
		{
			sender.sendMessage("��f");
			sender.sendMessage("��b/���� <����/�߰�/����> <�г���> <��> ��f- �÷��̾��� ������ �����մϴ�.");
			sender.sendMessage("��b/���� ������ֱ� <�г���> ��f- ���� ����� ��ȸ�� ����� ������ �ݴϴ�.");
			sender.sendMessage("��b/���� ���ʽ� <�г���> <�ۼ�Ʈ> ��f- �÷��̾��� ���� ���ʽ��� �����մϴ�.");
		}
	}
	
	private void displayConsol(CommandSender sender)
	{
		sender.sendMessage(" " + YELLOW + "----- " + GOLD + "����� ���� " + YELLOW + "--- " + GOLD + "���� " + YELLOW + "-----");
		sender.sendMessage(GOLD + "/���� " + WHITE + "- ����� ���� ������ ���ϴ�.");
		sender.sendMessage(GOLD + "/���� Ȯ�� <�г���> " + WHITE + "- �ٸ� ����� ų �� ������ ���ϴ�.");
		sender.sendMessage(GOLD + "/���� ���� " + WHITE + "- S��ũ 15���� ���ϴ�.");
		sender.sendMessage(GOLD + "/���� ��޵� " + WHITE + "- ��ũ�� ��޵��� ���ϴ�.");
		
		if (sender.isOp())
		{
			sender.sendMessage("");
			sender.sendMessage(BLUE + "/���� <����/�߰�/����> <�г���> <��> " + WHITE + "- �÷��̾��� ������ �����մϴ�.");
			sender.sendMessage(BLUE + "/���� ������ֱ� <�г���> " + WHITE + "- ���� ����� ��ȸ�� ����� ������ �ݴϴ�.");
			sender.sendMessage(BLUE + "/���� ���ʽ� <�г���> <�ۼ�Ʈ> " + WHITE + "- �÷��̾��� ���� ���ʽ��� �����մϴ�.");
		}
	}
}
