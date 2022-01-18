package me.espoo.punchstat;

import java.io.File;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.espoo.file.PlayerYml;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	static PlayerYml P;

	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	@SuppressWarnings({ "resource", "static-access" })
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("����")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.openGUI(p);
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				GUI.openGUI(p);
				return false;
			} else {
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("����a")) {	
				if (args.length == 0) {
					if (sender.isOp()) {
						Method.HelpMessage(sender);
					} else {
						sender.sendMessage("����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
				}
				
				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("����")) {
						if (!(sender instanceof Player)) {
							return false;
						} 
						
						Player p = (Player) sender;
						String tn = Method.searchOnlinePlayer(args[1]);
						Player t = Method.getOnorOffLine(tn);
						
						if (!p.isOp()) {
							p.sendMessage("����� ��ɾ ������ ������ �����ϴ�.");
							return false;
						}
						
						if (t == null) {
							p.sendMessage("��c�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						GUI.viewGUI(t, p);
						return false;
					}
				}
				
				else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("Ȱ��ȭ"))
					{
						String name = Method.searchOnlinePlayer(args[2]);
						File f = new File("plugins/OnePunchStat/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("1"))
						{
							Player p = Method.getOnorOffLine(name);
							boolean is = PlayerYml.getInfoBoolean(p, "�� �ٷ� ���� ����");
							if (is == true) {
								PlayerYml.setInfoBoolean(p, "�� �ٷ� ���� ����", false);
							} else {
								PlayerYml.setInfoBoolean(p, "�� �ٷ� ���� ����", true);
							} return false;
						}
						
						else if (args[1].equalsIgnoreCase("2"))
						{
							Player p = Method.getOnorOffLine(name);
							boolean is = PlayerYml.getInfoBoolean(p, "���� ���� ����");
							if (is == true) {
								PlayerYml.setInfoBoolean(p, "���� ���� ����", false);
								StatsRunAPI.PlayerHHealth(p);
							} else {
								PlayerYml.setInfoBoolean(p, "���� ���� ����", true);
								StatsRunAPI.PlayerHealth(p);
							} return false;
						}
						
						else if (args[1].equalsIgnoreCase("3"))
						{
							Player p = Method.getOnorOffLine(name);
							boolean is = PlayerYml.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����");
							if (is == true) {
								PlayerYml.setInfoBoolean(p, "�ٸ� �ٷ� ���� ����", false);
								p.removePotionEffect(PotionEffectType.JUMP);
							} else {
								PlayerYml.setInfoBoolean(p, "�ٸ� �ٷ� ���� ����", true);
								int i = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
								if (i > 0) {
									p.removePotionEffect(PotionEffectType.JUMP);
									p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
								}
							} return false;
						}
						
						else if (args[1].equalsIgnoreCase("4"))
						{
							Player p = Method.getOnorOffLine(name);
							boolean is = PlayerYml.getInfoBoolean(p, "���ǵ� ���� ����");
							if (is == true) {
								PlayerYml.setInfoBoolean(p, "���ǵ� ���� ����", false);
								p.setWalkSpeed(0.2F);
							} else {
								PlayerYml.setInfoBoolean(p, "���ǵ� ���� ����", true);
								float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
								if (i <= 1F) {
									if (i > 0.2F) {
										p.setWalkSpeed(i);
									} else {
										p.setWalkSpeed(0.2F);
									}
								} else {
									p.setWalkSpeed(1F);
								}
							} return false;
						}
					}
				}
				
				else if (args.length == 4) {
					if (args[0].equalsIgnoreCase("����"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/OnePunchStat/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("1"))
						{
							Player p = Method.getOnorOffLine(name);
							PlayerYml.setInfoInt(p, "�� �ٷ�", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �� �ٷ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �����Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("2"))
						{
							Player p = Method.getOnorOffLine(name);
							PlayerYml.setInfoInt(p, "����", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �����Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("3"))
						{
							Player p = Method.getOnorOffLine(name);
							PlayerYml.setInfoInt(p, "�ٸ� �ٷ�", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �ٸ� �ٷ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �����Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("4"))
						{
							Player p = Method.getOnorOffLine(name);
							PlayerYml.setInfoInt(p, "���ǵ�", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ���ǵ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �����Ͽ����ϴ�.");
							
							if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
								float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
								if (i <= 1F) {
									if (i > 0.2F) {
										p.setWalkSpeed(i);
									} else {
										p.setWalkSpeed(0.2F);
									}
								} else {
									p.setWalkSpeed(1F);
								}
							} else {
								p.setWalkSpeed(0.2F);
							}
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("�߰�"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/OnePunchStat/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("1"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "�� �ٷ�") + Integer.parseInt(args[3]);
							PlayerYml.setInfoInt(p, "�� �ٷ�", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �� �ٷ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �߰��Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("2"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "����") + Integer.parseInt(args[3]);
							PlayerYml.setInfoInt(p, "����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �߰��Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("3"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "�ٸ� �ٷ�") + Integer.parseInt(args[3]);
							PlayerYml.setInfoInt(p, "�ٸ� �ٷ�", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �ٸ� �ٷ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �߰��Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("4"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "���ǵ�") + Integer.parseInt(args[3]);
							PlayerYml.setInfoInt(p, "���ǵ�", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ���ǵ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �߰��Ͽ����ϴ�.");
							
							if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
								float y = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
								if (y <= 1F) {
									if (y > 0.2F) {
										p.setWalkSpeed(y);
									} else {
										p.setWalkSpeed(0.2F);
									}
								} else {
									p.setWalkSpeed(1F);
								}
							} else {
								p.setWalkSpeed(0.2F);
							}
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/OnePunchStat/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("1"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "�� �ٷ�") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							PlayerYml.setInfoInt(p, "�� �ٷ�", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �� �ٷ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ��ŭ �����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("2"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "����") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							PlayerYml.setInfoInt(p, "����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ��ŭ �����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("3"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "�ٸ� �ٷ�") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							PlayerYml.setInfoInt(p, "�ٸ� �ٷ�", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �ٸ� �ٷ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ��ŭ �����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("4"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "���ǵ�") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							PlayerYml.setInfoInt(p, "���ǵ�", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ���ǵ� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ��ŭ �����ϴ�.");
							
							if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
								float y = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
								if (y <= 1F) {
									if (y > 0.2F) {
										p.setWalkSpeed(y);
									} else {
										p.setWalkSpeed(0.2F);
									}
								} else {
									p.setWalkSpeed(1F);
								}
							} else {
								p.setWalkSpeed(0.2F);
							}
							return false;
						}
					}
				}
			}
		} catch (NumberFormatException ex) {
			if (sender.isOp()) {
				Method.HelpMessage(sender);
			} else {
				sender.sendMessage("����� ��ɾ ������ ������ �����ϴ�.");
				return false;
			}
		} return false;
	}
}
