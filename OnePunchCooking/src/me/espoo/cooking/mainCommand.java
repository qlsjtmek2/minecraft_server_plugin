package me.espoo.cooking;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.espoo.punchstat.Method;
import me.shinkhan.fatigue.API;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	ChatColor GOLD = ChatColor.GOLD;
	ChatColor RED = ChatColor.RED;
	ChatColor WHITE = ChatColor.WHITE;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("�丮")) {
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
			if (commandLabel.equalsIgnoreCase("�丮ȿ��")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (p.isOp()) {
						if (args.length == 0 || args == null) {
							sender.sendMessage(GOLD + "/�丮ȿ�� <�����̸�> <�Ƿε�/ü��/�����/������/��罺��/�ȱٷ�/����/");
							sender.sendMessage(GOLD + "�ٸ��ٷ�/���ǵ�/�߰�����> <EAT/DRINK> <��> [<���ӽð�>]" + WHITE + " - �丮 ȿ���� �ݴϴ�.");
							return false;
						}
						
						if (args[2].equalsIgnoreCase("EAT")) {
							if (args.length == 4) {
								if (args[1].equalsIgnoreCase("�Ƿε�")) {
									if (API.getFatigue(p) == 100) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��c�Ƿε��� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									API.addFatigue(p, Integer.parseInt(args[3]));
									args[0] = args[0].replaceAll("_", " ");
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� �Ƿε��� ��c+" + args[3] + " ��6�߰��Ǿ����ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("ü��")) {
									if (p.getHealth() == p.getMaxHealth()) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��cü���� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									int i = p.getHealth() + Integer.parseInt(args[3]);
									args[0] = args[0].replaceAll("_", " ");
									if (i >= p.getMaxHealth()) {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� ü���� ��c��� ��6ä�������ϴ�.");
										p.setHealth(p.getMaxHealth());
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
										return false;
									} else {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� ü���� ��c+" + args[3] + " ��6ȸ���Ǿ����ϴ�.");
										p.setHealth(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
										return false;
									}
								}
								
								else if (args[1].equalsIgnoreCase("�����")) {
									if (p.getFoodLevel() == 20) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��c������� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									int i = p.getFoodLevel() + Integer.parseInt(args[3]);
									args[0] = args[0].replaceAll("_", " ");
									if (i >= 20) {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� ������� ��c��� ��6ä�������ϴ�.");
										p.setFoodLevel(20);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
										return false;
									} else {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� ü���� ��c+" + args[3] + " ��6ȸ���Ǿ����ϴ�.");
										p.setFoodLevel(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
										return false;
									}
								}
							}
							
							else if (args.length == 5) {
								if (args[1].equalsIgnoreCase("������")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.FAST_DIGGING);
									p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� �������� ��c" + args[4] + " ��6�� ��ŭ ��c" + args[3] + "�ܰ� ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�߰�����")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.NIGHT_VISION);
									p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� �߰����ø� ��c" + args[4] + " ��6�� ��ŭ ��c" + args[3] + "�ܰ� ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�ȱٷ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� �� �ٷ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("����")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� ���� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�ٸ��ٷ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� �ٸ� �ٷ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("���ǵ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� ���ǵ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("��罺��")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ �����Ͽ� ��� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
							}
						}
						
						else if (args[2].equalsIgnoreCase("DRINK")) {
							if (args.length == 4) {
								if (args[1].equalsIgnoreCase("�Ƿε�")) {
									if (API.getFatigue(p) == 100) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��c�Ƿε��� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									API.addFatigue(p, Integer.parseInt(args[3]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� �Ƿε��� ��c+" + args[3] + " ��6�߰��Ǿ����ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("ü��")) {
									if (p.getHealth() == p.getMaxHealth()) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��cü���� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									int i = p.getHealth() + Integer.parseInt(args[3]);
									if (i >= p.getMaxHealth()) {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� ü���� ��c��� ��6ä�������ϴ�.");
										p.setHealth(p.getMaxHealth());
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
										return false;
									} else {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� ü���� ��c+" + args[3] + " ��6ȸ���Ǿ����ϴ�.");
										p.setHealth(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
										return false;
									}
								}
								
								else if (args[1].equalsIgnoreCase("�����")) {
									if (p.getFoodLevel() == 20) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��c������� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									int i = p.getFoodLevel() + Integer.parseInt(args[3]);
									if (i >= 20) {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� ������� ��c��� ��6ä�������ϴ�.");
										p.setFoodLevel(20);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
										return false;
									} else {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� ü���� ��c+" + args[3] + " ��6ȸ���Ǿ����ϴ�.");
										p.setFoodLevel(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
										return false;
									}
								}
							}
							
							else if (args.length == 5) {
								if (args[1].equalsIgnoreCase("������")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.FAST_DIGGING);
									p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� �������� ��c" + args[4] + " ��6�� ��ŭ ��c" + args[3] + "�ܰ� ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�߰�����")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.NIGHT_VISION);
									p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� �߰����ø� ��c" + args[4] + " ��6�� ��ŭ ��c" + args[3] + "�ܰ� ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�ȱٷ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� �� �ٷ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("����")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� ���� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�ٸ��ٷ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� �ٸ� �ٷ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("���ǵ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� ���ǵ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("��罺��")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ���� ��� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
							}
						}
						
						else if (args[2].equalsIgnoreCase("ITEM")) {
							if (args.length == 4) {
								if (args[1].equalsIgnoreCase("�Ƿε�")) {
									if (API.getFatigue(p) == 100) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��c�Ƿε��� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									API.addFatigue(p, Integer.parseInt(args[3]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ������ ��ü�� �Ծ� �Ƿε��� ��c+" + args[3] + " ��6�߰��Ǿ����ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("ü��")) {
									if (p.getHealth() == p.getMaxHealth()) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��cü���� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									int i = p.getHealth() + Integer.parseInt(args[3]);
									if (i >= p.getMaxHealth()) {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� ü���� ��c��� ��6ä�������ϴ�.");
										p.setHealth(p.getMaxHealth());
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
										return false;
									} else {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ���� ü���� ��c+" + args[3] + " ��6ȸ���Ǿ����ϴ�.");
										p.setHealth(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
										return false;
									}
								}
								
								else if (args[1].equalsIgnoreCase("�����")) {
									if (p.getFoodLevel() == 20) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("��c������� �� �� �����Ƿ� ������ ���� �ʿ䰡 �����ϴ�.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									int i = p.getFoodLevel() + Integer.parseInt(args[3]);
									if (i >= 20) {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� ������� ��c��� ��6ä�������ϴ�.");
										p.setFoodLevel(20);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
										return false;
									} else {
										p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� ü���� ��c+" + args[3] + " ��6ȸ���Ǿ����ϴ�.");
										p.setFoodLevel(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
										return false;
									}
								}
							}
							
							else if (args.length == 5) {
								if (args[1].equalsIgnoreCase("������")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.FAST_DIGGING);
									p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� �������� ��c" + args[4] + " ��6�� ��ŭ ��c" + args[3] + "�ܰ� ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�߰�����")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.NIGHT_VISION);
									p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� �߰����ø� ��c" + args[4] + " ��6�� ��ŭ ��c" + args[3] + "�ܰ� ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�ȱٷ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� �� �ٷ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("����")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� ���� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("�ٸ��ٷ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� �ٸ� �ٷ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("���ǵ�")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� ���ǵ� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("��罺��")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("��6����� ��c" + args[0] + "��6 ��ü�� ������ �Ծ� ��� ������ ��c" + args[4] + " ��6�� ��ŭ ��c+" + args[3] + " ��6�����̽��ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
							}
						}
					} return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage(GOLD + "/�丮ȿ�� <�����̸�> <�Ƿε�/ü��/�����/������/��罺��/�ȱٷ�/����/");
			sender.sendMessage(GOLD + "�ٸ��ٷ�/���ǵ�/�߰�����> <EAT/DRINK> <��> [<���ӽð�>]" + WHITE + " - �丮 ȿ���� �ݴϴ�.");
		} return false;
	}

}
