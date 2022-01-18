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
			if (commandLabel.equalsIgnoreCase("요리")) {
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
			if (commandLabel.equalsIgnoreCase("요리효과")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (p.isOp()) {
						if (args.length == 0 || args == null) {
							sender.sendMessage(GOLD + "/요리효과 <음식이름> <피로도/체력/배고픔/성급함/모든스텟/팔근력/복근/");
							sender.sendMessage(GOLD + "다리근력/스피드/야간투시> <EAT/DRINK> <값> [<지속시간>]" + WHITE + " - 요리 효과를 줍니다.");
							return false;
						}
						
						if (args[2].equalsIgnoreCase("EAT")) {
							if (args.length == 4) {
								if (args[1].equalsIgnoreCase("피로도")) {
									if (API.getFatigue(p) == 100) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c피로도가 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									API.addFatigue(p, Integer.parseInt(args[3]));
									args[0] = args[0].replaceAll("_", " ");
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 피로도가 §c+" + args[3] + " §6추가되었습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("체력")) {
									if (p.getHealth() == p.getMaxHealth()) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c체력이 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									int i = p.getHealth() + Integer.parseInt(args[3]);
									args[0] = args[0].replaceAll("_", " ");
									if (i >= p.getMaxHealth()) {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 체력이 §c모두 §6채워졌습니다.");
										p.setHealth(p.getMaxHealth());
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
										return false;
									} else {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 체력이 §c+" + args[3] + " §6회복되었습니다.");
										p.setHealth(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
										return false;
									}
								}
								
								else if (args[1].equalsIgnoreCase("배고픔")) {
									if (p.getFoodLevel() == 20) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c배고픔이 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									int i = p.getFoodLevel() + Integer.parseInt(args[3]);
									args[0] = args[0].replaceAll("_", " ");
									if (i >= 20) {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 배고픔이 §c모두 §6채워졌습니다.");
										p.setFoodLevel(20);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
										return false;
									} else {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 체력이 §c+" + args[3] + " §6회복되었습니다.");
										p.setFoodLevel(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
										return false;
									}
								}
							}
							
							else if (args.length == 5) {
								if (args[1].equalsIgnoreCase("성급함")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.FAST_DIGGING);
									p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 성급함을 §c" + args[4] + " §6초 만큼 §c" + args[3] + "단계 §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("야간투시")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.NIGHT_VISION);
									p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 야간투시를 §c" + args[4] + " §6초 만큼 §c" + args[3] + "단계 §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("팔근력")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 팔 근력 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("복근")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 복근 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("다리근력")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 다리 근력 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("스피드")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 스피드 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("모든스텟")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 섭취하여 모든 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
									return false;
								}
							}
						}
						
						else if (args[2].equalsIgnoreCase("DRINK")) {
							if (args.length == 4) {
								if (args[1].equalsIgnoreCase("피로도")) {
									if (API.getFatigue(p) == 100) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c피로도가 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									API.addFatigue(p, Integer.parseInt(args[3]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 피로도가 §c+" + args[3] + " §6추가되었습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("체력")) {
									if (p.getHealth() == p.getMaxHealth()) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c체력이 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									int i = p.getHealth() + Integer.parseInt(args[3]);
									if (i >= p.getMaxHealth()) {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 체력이 §c모두 §6채워졌습니다.");
										p.setHealth(p.getMaxHealth());
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
										return false;
									} else {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 체력이 §c+" + args[3] + " §6회복되었습니다.");
										p.setHealth(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
										return false;
									}
								}
								
								else if (args[1].equalsIgnoreCase("배고픔")) {
									if (p.getFoodLevel() == 20) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c배고픔이 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									int i = p.getFoodLevel() + Integer.parseInt(args[3]);
									if (i >= 20) {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 배고픔이 §c모두 §6채워졌습니다.");
										p.setFoodLevel(20);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
										return false;
									} else {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 체력이 §c+" + args[3] + " §6회복되었습니다.");
										p.setFoodLevel(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
										return false;
									}
								}
							}
							
							else if (args.length == 5) {
								if (args[1].equalsIgnoreCase("성급함")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.FAST_DIGGING);
									p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 성급함을 §c" + args[4] + " §6초 만큼 §c" + args[3] + "단계 §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("야간투시")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.NIGHT_VISION);
									p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 야간투시를 §c" + args[4] + " §6초 만큼 §c" + args[3] + "단계 §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("팔근력")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 팔 근력 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("복근")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 복근 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("다리근력")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 다리 근력 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("스피드")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 스피드 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("모든스텟")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 마셔 모든 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
									return false;
								}
							}
						}
						
						else if (args[2].equalsIgnoreCase("ITEM")) {
							if (args.length == 4) {
								if (args[1].equalsIgnoreCase("피로도")) {
									if (API.getFatigue(p) == 100) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c피로도가 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									API.addFatigue(p, Integer.parseInt(args[3]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 음식을 물체를 먹어 피로도가 §c+" + args[3] + " §6추가되었습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("체력")) {
									if (p.getHealth() == p.getMaxHealth()) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c체력이 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									int i = p.getHealth() + Integer.parseInt(args[3]);
									if (i >= p.getMaxHealth()) {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 체력이 §c모두 §6채워졌습니다.");
										p.setHealth(p.getMaxHealth());
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
										return false;
									} else {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 마셔 체력이 §c+" + args[3] + " §6회복되었습니다.");
										p.setHealth(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
										return false;
									}
								}
								
								else if (args[1].equalsIgnoreCase("배고픔")) {
									if (p.getFoodLevel() == 20) {
										Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + p.getName() + " 1");
										p.sendMessage("§c배고픔이 꽉 차 있으므로 음식을 먹을 필요가 없습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
										return false;
									}
									
									args[0] = args[0].replaceAll("_", " ");
									int i = p.getFoodLevel() + Integer.parseInt(args[3]);
									if (i >= 20) {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 배고픔이 §c모두 §6채워졌습니다.");
										p.setFoodLevel(20);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
										return false;
									} else {
										p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 체력이 §c+" + args[3] + " §6회복되었습니다.");
										p.setFoodLevel(i);
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
										return false;
									}
								}
							}
							
							else if (args.length == 5) {
								if (args[1].equalsIgnoreCase("성급함")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.FAST_DIGGING);
									p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 성급함을 §c" + args[4] + " §6초 만큼 §c" + args[3] + "단계 §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("야간투시")) {
									args[0] = args[0].replaceAll("_", " ");
									p.removePotionEffect(PotionEffectType.NIGHT_VISION);
									p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.parseInt(args[4]) * 20, Integer.parseInt(args[3]) + 1, true));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 야간투시를 §c" + args[4] + " §6초 만큼 §c" + args[3] + "단계 §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("팔근력")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 팔 근력 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("복근")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 복근 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("다리근력")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 다리 근력 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("스피드")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 스피드 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
									return false;
								}
								
								else if (args[1].equalsIgnoreCase("모든스텟")) {
									args[0] = args[0].replaceAll("_", " ");
									Method.add1StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add2StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add3StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									Method.add4StatEffect(p, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
									p.sendMessage("§6당신은 §c" + args[0] + "§6 물체를 억지로 먹어 모든 스텟을 §c" + args[4] + " §6초 만큼 §c+" + args[3] + " §6얻으셨습니다.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 0.6F);
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
			sender.sendMessage(GOLD + "/요리효과 <음식이름> <피로도/체력/배고픔/성급함/모든스텟/팔근력/복근/");
			sender.sendMessage(GOLD + "다리근력/스피드/야간투시> <EAT/DRINK> <값> [<지속시간>]" + WHITE + " - 요리 효과를 줍니다.");
		} return false;
	}

}
