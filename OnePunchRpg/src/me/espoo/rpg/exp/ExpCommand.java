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
			if (commandLabel.equalsIgnoreCase("����ġ") || commandLabel.equalsIgnoreCase("exp")) {
				if (args.length == 0) {
					if (sender instanceof Player) {
						displayHelp(sender);
						return false;
					} else {
						sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
						return false;
					}
				}
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (args[0].equalsIgnoreCase("Ȯ��") && args.length == 1 || args[0].equalsIgnoreCase("����") && args.length == 1)
					{
						sender.sendMessage("��e * " + p.getName() + " ��7:: ��f" + ExpAPI.getDayMessage(p));
						sender.sendMessage("��e ������ ��c����ġ ��7:: ��f" + ExpAPI.getExp(p.getName()));
						sender.sendMessage("��e ������ ��c�÷��̾� ���� ��7:: ��f" + ExpAPI.getRank(p.getName()));
						int num = 0;
						for (String type : ExpConfig.getExpList("Ÿ�� ���")) {
							if (ExpAPI.getBouns(p.getName(), type) != 0) {
								num += ExpAPI.getBouns(p.getName(), type);
								sender.sendMessage("��e ���� ��6" + type + " ��7:: ��f" + ExpAPI.getBouns(p.getName(), type) + "%");
							} else sender.sendMessage("��e ���� ��6" + type + " ��7:: ��f100%");
						}
						
						if (GuildAPI.getJoinGuild(p) != null) {
							int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
							num += i;
							sender.sendMessage("��e ���� ��6��� ���ʽ� ��7:: ��f" + i + "%");
						}
						
						if (ExpAPI.getServerBouns() != 100) num += ExpAPI.getServerBouns();
						sender.sendMessage("��e ���� ��6���� �̺�Ʈ ��7:: ��f" + ExpAPI.getServerBouns() + "%");
						
						if (num != 0) sender.sendMessage("��e ���� ��6���� ��7:: ��f" + num + "%");
						else sender.sendMessage("��e ���� ��6���� ��7:: ��f100%");
						return false;
					}
					
					if (args[0].equalsIgnoreCase("Ȯ��") && args.length == 2 || args[0].equalsIgnoreCase("����") && args.length == 2) {
						Player pl = Method.getOnorOffLine(Method.searchOnlinePlayer(args[1]));
						
						if (pl == null) {
							sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (sender.isOp())
						{
							sender.sendMessage("��e * " + pl.getName() + " ��7:: ��f" + ExpAPI.getDayMessage(pl));
							sender.sendMessage("��e ������ ��c����ġ ��7:: ��f" + ExpAPI.getExp(pl.getName()));
							sender.sendMessage("��e ������ ��c�÷��̾� ���� ��7:: ��f" + ExpAPI.getRank(pl.getName()));
							int num = 0;
							for (String type : ExpConfig.getExpList("Ÿ�� ���")) {
								if (ExpAPI.getBouns(pl.getName(), type) != 0) {
									num += ExpAPI.getBouns(pl.getName(), type);
									sender.sendMessage("��e ���� ��6" + type + " ��7:: ��f" + ExpAPI.getBouns(pl.getName(), type) + "%");
								} else sender.sendMessage("��e ���� ��6" + type + " ��7:: ��f100%");
							}
							
							if (GuildAPI.getJoinGuild(p) != null) {
								int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
								num += i;
								sender.sendMessage("��e ���� ��6��� ���ʽ� ��7:: ��f" + i + "%");
							}
							
							if (ExpAPI.getServerBouns() != 100) num += ExpAPI.getServerBouns();
							sender.sendMessage("��e ���� ��6���� �̺�Ʈ ��7:: ��f" + ExpAPI.getServerBouns() + "%");
							
							if (num != 0) sender.sendMessage("��e ���� ��6���� ��7:: ��f" + num + "%");
							else sender.sendMessage("��e ���� ��6���� ��7:: ��f100%");
							return false;
						}
						
						if (ExpAPI.getSeeInfo(pl))
						{
							sender.sendMessage("��e * " + pl.getName() + " ��7:: ��f" + ExpAPI.getDayMessage(pl));
							sender.sendMessage("��e ������ ��c����ġ ��7:: ��f" + ExpAPI.getExp(pl.getName()));
							sender.sendMessage("��e ������ ��c�÷��̾� ���� ��7:: ��f" + ExpAPI.getRank(pl.getName()));
							int num = 0;
							for (String type : ExpConfig.getExpList("Ÿ�� ���")) {
								if (ExpAPI.getBouns(pl.getName(), type) != 0) {
									num += ExpAPI.getBouns(pl.getName(), type);
									sender.sendMessage("��e ���� ��6" + type + " ��7:: ��f" + ExpAPI.getBouns(pl.getName(), type) + "%");
								} else sender.sendMessage("��e ���� ��6" + type + " ��7:: ��f100%");
							}
							
							if (GuildAPI.getJoinGuild(p) != null) {
								int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
								num += i;
								sender.sendMessage("��e ���� ��6��� ���ʽ� ��7:: ��f" + i + "%");
							}
							
							if (ExpAPI.getServerBouns() != 100) num += ExpAPI.getServerBouns();
							sender.sendMessage("��e ���� ��6���� �̺�Ʈ ��7:: ��f" + ExpAPI.getServerBouns() + "%");
							
							if (num != 0) sender.sendMessage("��e ���� ��6���� ��7:: ��f" + num + "%");
							else sender.sendMessage("��e ���� ��6���� ��7:: ��f100%");
							return false;
						}
						
						else
						{
							sender.sendMessage(RED + "������ ���� ���⸦ �㰡���� �ʾҽ��ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("����") && args.length == 1 || args[0].equalsIgnoreCase("��ŷ") && args.length == 1)
					{
						List<String> list = ExpYml.ArrayExp();
						int i = 1;
						int num = (list.size() / 10) + 1;
						sender.sendMessage("��c" + list.size() + " ��6���� ������ ã�ҽ��ϴ�. ��c1��6/��c" + num);
						for (String str : list)
						{
							if (i >= 11) break;
							sender.sendMessage("��c" + i + ": ��6" + str.split(",")[0] + " ��eEXP." + str.split(",")[1]);
							i++;
						}
						
						if (list.size() > 10) {
							sender.sendMessage("��6���� ��ŷ�� ���÷��� ��c/����ġ ���� 2 ��6�� �Է����ּ���.");
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("����") && args.length == 2 || args[0].equalsIgnoreCase("��ŷ") && args.length == 2)
					{
						List<String> list = ExpYml.ArrayExp();
						int num = Integer.parseInt(args[1]);
						int num2 = (list.size() / 10) + 1;
						
						if (num > list.size() / 10) {
							sender.sendMessage("��c�ش� ������ �������� �ʽ��ϴ�.");
							return false;
						}
						
						int i = (num * 10) + 1;
						int y = 1;
						
						sender.sendMessage("��c" + list.size() + " ��6���� ������ ã�ҽ��ϴ�. ��c" + num + "��6/��c" + num2);
						for (String str : list)
						{
							if (y < i) {
								y++;
								continue;
							}
							
							if (i >= (num * 10) + 11) break;
							sender.sendMessage("��c" + i + ": ��6" + str.split(",")[0] + " ��eEXP." + str.split(",")[1]);
							i++;
						}
						
						if (list.size() > i) {
							sender.sendMessage("��6���� ��ŷ�� ���÷��� ��c/����ġ ���� " + num+1 + " ��6�� �Է����ּ���.");
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
						sender.sendMessage("��6---------------------------------");
						sender.sendMessage("��e * ��6����ġ ��ŷ 1�� - ��f�� " + me.espoo.oneman.Method.getConfigInt("1�� ����") + "��");
						sender.sendMessage("��e * ��6����ġ ��ŷ 2�� - ��f�� " + me.espoo.oneman.Method.getConfigInt("2�� ����") + "��");
						sender.sendMessage("��e * ��6����ġ ��ŷ 3�� - ��f�� " + me.espoo.oneman.Method.getConfigInt("3�� ����") + "��");
						sender.sendMessage("��e * ��6����ġ ��ŷ 4�� - ��f�� " + me.espoo.oneman.Method.getConfigInt("4�� ����") + "��");
						sender.sendMessage("��6---------------------------------");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("���") && args.length >= 2 || args[0].equalsIgnoreCase("���¸޼���") && args.length >= 2 || args[0].equalsIgnoreCase("���¸޽���") && args.length >= 2)
					{
						String message = Method.replaceAllColors(Method.getFinalArg(args, 1));
						PlayerYml.setInfoString(p, "���� �޼���", message);
						p.sendMessage("��6�������� ���� �޼����� �����ϼ̽��ϴ�! ��e[ /����ġ Ȯ�� ]");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("������"))
					{
						if (args.length == 4) {
							if (!sender.isOp())
							{
								sender.sendMessage(RED + "����� ��ɾ ������ ������ �����ϴ�.");
								return false;
							}
							
							String type = args[1];
							int bouns = Integer.parseInt(args[2]);
							int time = 0;
							
							if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
								sender.sendMessage(RED + "�տ� �������� ������� �ʾ� ����ġ ���ʽ��� ������ �� �����ϴ�.");
								return false;
							}
							
							if (!ExpAPI.isBounsType(type)) {
								sender.sendMessage(RED + "�ش� ����ġ ���ʽ� Ÿ���� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (p.getItemInHand().hasItemMeta()) {
								if (p.getItemInHand().getItemMeta().hasLore()) {
									ItemStack item = p.getItemInHand();
									ItemMeta meta = item.getItemMeta();
									List<String> list = meta.getLore();
									
									if (args[3].equalsIgnoreCase("infinite")) {
										meta.setDisplayName("��6����ġ ���ʽ� ������");
										list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ������6�� ����");
									}
									
									else if (args[3].contains("h")) {
										time = Integer.parseInt(args[3].replaceAll("h", ""));
										meta.setDisplayName("��6����ġ ���ʽ� ������");
										list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "H ��6����");
									}
									
									else if (args[3].contains("m")) {
										time = Integer.parseInt(args[3].replaceAll("m", ""));
										meta.setDisplayName("��6����ġ ���ʽ� ������");
										list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "M ��6����");
									}
									
									else {
										time = Integer.parseInt(args[3]);
										meta.setDisplayName("��6����ġ ���ʽ� ������");
										list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "M ��6����");
									}
									
									meta.setLore(list);
									item.setItemMeta(meta);
									p.setItemInHand(item);
									p.sendMessage("��6���������� ����ġ ���ʽ� �������� �����Ͽ����ϴ�.");
									return false;
								} else {
									ItemStack item = p.getItemInHand();
									ItemMeta meta = item.getItemMeta();
									List<String> list = new ArrayList<String>();
									
									if (args[3].equalsIgnoreCase("infinite")) {
										meta.setDisplayName("��6����ġ ���ʽ� ������");
										list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ������6�� ����");
									}
									
									else if (args[3].contains("h")) {
										time = Integer.parseInt(args[3].replaceAll("h", ""));
										meta.setDisplayName("��6����ġ ���ʽ� ������");
										list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "H ��6����");
									}
									
									else if (args[3].contains("m")) {
										time = Integer.parseInt(args[3].replaceAll("m", ""));
										meta.setDisplayName("��6����ġ ���ʽ� ������");
										list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "M ��6����");
									}
									
									else {
										time = Integer.parseInt(args[3]);
										meta.setDisplayName("��6����ġ ���ʽ� ������");
										list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "M ��6����");
									}
									
									meta.setLore(list);
									item.setItemMeta(meta);
									p.setItemInHand(item);
									p.sendMessage("��6���������� ����ġ ���ʽ� �������� �����Ͽ����ϴ�.");
									return false;
								}
							} else {
								ItemStack item = p.getItemInHand();
								ItemMeta meta = item.getItemMeta();
								List<String> list = new ArrayList<String>();
								
								if (args[3].equalsIgnoreCase("infinite")) {
									meta.setDisplayName("��6����ġ ���ʽ� ������");
									list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ������6�� ����");
								}
								
								else if (args[3].contains("h")) {
									time = Integer.parseInt(args[3].replaceAll("h", ""));
									meta.setDisplayName("��6����ġ ���ʽ� ������");
									list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "H ��6����");
								}
								
								else if (args[3].contains("m")) {
									time = Integer.parseInt(args[3].replaceAll("m", ""));
									meta.setDisplayName("��6����ġ ���ʽ� ������");
									list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "M ��6����");
								}
								
								else {
									time = Integer.parseInt(args[3]);
									meta.setDisplayName("��6����ġ ���ʽ� ������");
									list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "M ��6����");
								}
								
								meta.setLore(list);
								item.setItemMeta(meta);
								p.setItemInHand(item);
								p.sendMessage("��6���������� ����ġ ���ʽ� �������� �����Ͽ����ϴ�.");
								return false;
							}
						}
						
						else if (args.length == 5) {
							if (!sender.isOp())
							{
								sender.sendMessage(RED + "����� ��ɾ ������ ������ �����ϴ�.");
								return false;
							}
							
							String type = args[1];
							int bouns = Integer.parseInt(args[2]);
							int time = 0;
							
							if (p.getItemInHand() == null) {
								sender.sendMessage(RED + "�տ� �������� ������� �ʾ� ����ġ ���ʽ��� ������ �� �����ϴ�.");
								return false;
							}
							
							if (!ExpAPI.isBounsType(type)) {
								sender.sendMessage(RED + "�ش� ����ġ ���ʽ� Ÿ���� �������� �ʽ��ϴ�.");
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
				    			p.sendMessage("��c�κ��丮 ������ �����Ͽ� �������� ������ �� �����ϴ�.");
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
									meta.setDisplayName("��6����ġ ���ʽ� ������");
									list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ������6�� ����");
								}
								
								else if (args[3].contains("h")) {
									time = Integer.parseInt(args[3].replaceAll("h", ""));
									meta.setDisplayName("��6����ġ ���ʽ� ������");
									list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "H ��6����");
								}
								
								else if (args[3].contains("m")) {
									time = Integer.parseInt(args[3].replaceAll("m", ""));
									meta.setDisplayName("��6����ġ ���ʽ� ������");
									list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "M ��6����");
								}
								
								else {
									time = Integer.parseInt(args[3]);
									meta.setDisplayName("��6����ġ ���ʽ� ������");
									list.add("��6����ġ ���ʽ� ��c" + type + "��6�� ��c" + bouns + "% ��ŭ " + time + "M ��6����");
								}
								
								meta.setLore(list);
								item.setItemMeta(meta);
								p.getInventory().addItem(item);
								p.sendMessage("��6���������� ����ġ ���ʽ� �������� �����Ͽ����ϴ�.");
								return false;
				            }
						}
					}
					
					else if (args[0].equalsIgnoreCase("�̺�Ʈ"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage(RED + "����� ��ɾ ������ ������ �����ϴ�.");
							return false;
						}
						
						if (!(args.length == 3))
						{
							sender.sendMessage("��b/����ġ �̺�Ʈ <��> <�ð�>(h,m,infinite) ��f- ����ġ ���ʽ� �̺�Ʈ�� �����մϴ�.");
							return false;
						}
						
						int bouns = Integer.parseInt(args[1]);
						int time = 0;
						
						if (ExpAPI.getServerBouns() > bouns) {
							if (args[2].equalsIgnoreCase("infinite")) {
								time = -1;
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6����ġ �̺�Ʈ�� ��c" + bouns + "% ��6��ŭ ��c��������6���� �پ������ϴ�.");
							}
							
							if (args[2].contains("h")) {
								time = Integer.parseInt(args[2].replaceAll("h", ""));
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6����ġ �̺�Ʈ�� ��c" + bouns + "% ��6��ŭ ��c" + time + "��6 �ð����� �پ��ϴ�.");
								time = time * 60;
							}
							
							else if (args[2].contains("m")) {
								time = Integer.parseInt(args[2].replaceAll("m", ""));
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6����ġ �̺�Ʈ�� ��c" + bouns + "% ��6��ŭ ��c" + time + "��6 �е��� �پ��ϴ�.");
							}
							
							else {
								time = Integer.parseInt(args[2]);
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6����ġ �̺�Ʈ�� ��c" + bouns + "% ��6��ŭ ��c" + time + "��6 �е��� �پ��ϴ�.");
							}
						} else {
							if (args[2].equalsIgnoreCase("infinite")) {
								time = -1;
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6���ݺ��� ����ġ ��c" + bouns + "% ��6�̺�Ʈ�� ��c��������6���� �����մϴ�.");
							}
							
							if (args[2].contains("h")) {
								time = Integer.parseInt(args[2].replaceAll("h", ""));
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6���ݺ��� ����ġ ��c" + bouns + "% ��6�̺�Ʈ�� ��c" + time + "��6 �ð����� �����մϴ�.");
								time = time * 60;
							}
							
							else if (args[2].contains("m")) {
								time = Integer.parseInt(args[2].replaceAll("m", ""));
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6���ݺ��� ����ġ ��c" + bouns + "% ��6�̺�Ʈ�� ��c" + time + "��6 �е��� �����մϴ�.");
							}
							
							else {
								time = Integer.parseInt(args[2]);
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6���ݺ��� ����ġ ��c" + bouns + "% ��6�̺�Ʈ�� ��c" + time + "��6 �е��� �����մϴ�.");
							}
						}
						
						ExpAPI.setServerBouns(bouns);
						ExpAPI.setServerBounsTimer(time);
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
							sender.sendMessage(BLUE + "/����ġ <����/�߰�/����> <�г���> <��> " + WHITE + "- �÷��̾��� ����ġ�� �����մϴ�.");
							return false;
						}
						
						String str = Method.searchOnlinePlayer(args[1]);
						File f = new File("plugins/OnePunchRpg/Player/" + str + ".yml");
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
						ExpYml.setPlayerExp(argsP.getName(), Integer.parseInt(args[2]));
						sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ����ġ�� ��c" + args[2] + " ��6��ŭ �����Ͽ����ϴ�.");
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
							sender.sendMessage(BLUE + "/����ġ <����/�߰�/����> <�г���> <��> " + WHITE + "- �÷��̾��� ����ġ�� �����մϴ�.");
							return false;
						}
						
						String str = Method.searchOnlinePlayer(args[1]);
						File f = new File("plugins/OnePunchRpg/Player/" + str + ".yml");
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
						ExpYml.addPlayerExp(argsP.getName(), Integer.parseInt(args[2]));
						sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ����ġ ���� ��c" + args[2] + " ��6��ŭ �߰��߽��ϴ�.");
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
							sender.sendMessage(BLUE + "/����ġ <����/�߰�/����> <�г���> <��> " + WHITE + "- �÷��̾��� ����ġ�� �����մϴ�.");
							return false;
						}
						
						String str = Method.searchOnlinePlayer(args[1]);
						File f = new File("plugins/OnePunchRpg/Player/" + str + ".yml");
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
						ExpYml.subPlayerExp(argsP.getName(), Integer.parseInt(args[2]));
						sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ����ġ ���� ��c" + args[2] + " ��6��ŭ �����ϴ�.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("����") && args.length == 2)
					{
						if (args[1].equalsIgnoreCase("���"))
						{
							if (ExpAPI.getSeeInfo(p) == true)
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
							if (ExpAPI.getSeeInfo(p) == false)
							{
								sender.sendMessage("��c�̹� ���� ���⸦ �����ϼ̽��ϴ�.");
								return false;
							}

							PlayerYml.setInfoBoolean(p, "����", false);
							sender.sendMessage("��6����� ������ ������ ���� ���� ��c�źΡ�6�߽��ϴ�.");
							return false;
						}
					}
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
				sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		} return false;
	}
	
	private void displayHelp(CommandSender sender)
	{
		sender.sendMessage(" ��e------ ��6����ġ ��e--- ��6���� ��e------");
		sender.sendMessage("��6/����ġ ��f- ����ġ ��ɾ Ȯ���մϴ�.");
		sender.sendMessage("��6/����ġ Ȯ�� ��f- �ڽ��� ����ġ�� ���õ� ���� Ȯ���մϴ�.");
		sender.sendMessage("��6/����ġ Ȯ�� <�г���> ��f- �÷��̾��� ����ġ�� ���õ� ���� Ȯ���մϴ�.");
		sender.sendMessage("��6/����ġ ���� ��f- ����ġ ���� �÷��̾��� ��� ������ ���ϴ�.");
		sender.sendMessage("��6/����ġ ���� ��f- ����ġ�� ȹ�� ������ ������ ���ϴ�.");
		sender.sendMessage("��6/����ġ ��� <�޼���> ��f- �� ���� �޼����� �����մϴ�.");
		sender.sendMessage("��6/����ġ ���� <���/�ź�> ��f- ������ ���� ���θ� ����ϰų� �ź��մϴ�. ��b( �⺻�� ����� )");
		
		if (sender.isOp())
		{
			sender.sendMessage("��f");
			sender.sendMessage("��b/����ġ <����/�߰�/����> <�г���> <��> ��f- �÷��̾��� ����ġ�� �����մϴ�.");
			sender.sendMessage("��b/����ġ �̺�Ʈ <��> <�ð�>(h,m,infinite) ��f- ����ġ ���ʽ� �̺�Ʈ�� �����մϴ�.");
			sender.sendMessage("��b/����ġ ������ <Ÿ��> <��> <�ð�>(h,m,infinite) <�������ڵ�> ��f- ����ġ �߰� �������� �����մϴ�.");
		}
	}
}
