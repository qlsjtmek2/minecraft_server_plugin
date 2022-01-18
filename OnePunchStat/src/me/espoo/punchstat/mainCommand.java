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
			if (commandLabel.equalsIgnoreCase("스텟")) {
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
			if (commandLabel.equalsIgnoreCase("스텟a")) {	
				if (args.length == 0) {
					if (sender.isOp()) {
						Method.HelpMessage(sender);
					} else {
						sender.sendMessage("당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
				}
				
				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("보기")) {
						if (!(sender instanceof Player)) {
							return false;
						} 
						
						Player p = (Player) sender;
						String tn = Method.searchOnlinePlayer(args[1]);
						Player t = Method.getOnorOffLine(tn);
						
						if (!p.isOp()) {
							p.sendMessage("당신은 명령어를 실행할 권한이 없습니다.");
							return false;
						}
						
						if (t == null) {
							p.sendMessage("§c그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						GUI.viewGUI(t, p);
						return false;
					}
				}
				
				else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("활성화"))
					{
						String name = Method.searchOnlinePlayer(args[2]);
						File f = new File("plugins/OnePunchStat/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("1"))
						{
							Player p = Method.getOnorOffLine(name);
							boolean is = PlayerYml.getInfoBoolean(p, "팔 근력 스텟 적용");
							if (is == true) {
								PlayerYml.setInfoBoolean(p, "팔 근력 스텟 적용", false);
							} else {
								PlayerYml.setInfoBoolean(p, "팔 근력 스텟 적용", true);
							} return false;
						}
						
						else if (args[1].equalsIgnoreCase("2"))
						{
							Player p = Method.getOnorOffLine(name);
							boolean is = PlayerYml.getInfoBoolean(p, "복근 스텟 적용");
							if (is == true) {
								PlayerYml.setInfoBoolean(p, "복근 스텟 적용", false);
								StatsRunAPI.PlayerHHealth(p);
							} else {
								PlayerYml.setInfoBoolean(p, "복근 스텟 적용", true);
								StatsRunAPI.PlayerHealth(p);
							} return false;
						}
						
						else if (args[1].equalsIgnoreCase("3"))
						{
							Player p = Method.getOnorOffLine(name);
							boolean is = PlayerYml.getInfoBoolean(p, "다리 근력 스텟 적용");
							if (is == true) {
								PlayerYml.setInfoBoolean(p, "다리 근력 스텟 적용", false);
								p.removePotionEffect(PotionEffectType.JUMP);
							} else {
								PlayerYml.setInfoBoolean(p, "다리 근력 스텟 적용", true);
								int i = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
								if (i > 0) {
									p.removePotionEffect(PotionEffectType.JUMP);
									p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
								}
							} return false;
						}
						
						else if (args[1].equalsIgnoreCase("4"))
						{
							Player p = Method.getOnorOffLine(name);
							boolean is = PlayerYml.getInfoBoolean(p, "스피드 스텟 적용");
							if (is == true) {
								PlayerYml.setInfoBoolean(p, "스피드 스텟 적용", false);
								p.setWalkSpeed(0.2F);
							} else {
								PlayerYml.setInfoBoolean(p, "스피드 스텟 적용", true);
								float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
					if (args[0].equalsIgnoreCase("설정"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/OnePunchStat/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("1"))
						{
							Player p = Method.getOnorOffLine(name);
							PlayerYml.setInfoInt(p, "팔 근력", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 팔 근력 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 으로 설정하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("2"))
						{
							Player p = Method.getOnorOffLine(name);
							PlayerYml.setInfoInt(p, "복근", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 복근 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 으로 설정하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("3"))
						{
							Player p = Method.getOnorOffLine(name);
							PlayerYml.setInfoInt(p, "다리 근력", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 다리 근력 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 으로 설정하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("4"))
						{
							Player p = Method.getOnorOffLine(name);
							PlayerYml.setInfoInt(p, "스피드", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 스피드 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 으로 설정하였습니다.");
							
							if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
								float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
					
					else if (args[0].equalsIgnoreCase("추가"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/OnePunchStat/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("1"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "팔 근력") + Integer.parseInt(args[3]);
							PlayerYml.setInfoInt(p, "팔 근력", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 팔 근력 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 정도 추가하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("2"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "복근") + Integer.parseInt(args[3]);
							PlayerYml.setInfoInt(p, "복근", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 복근 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 정도 추가하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("3"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "다리 근력") + Integer.parseInt(args[3]);
							PlayerYml.setInfoInt(p, "다리 근력", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 다리 근력 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 정도 추가하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("4"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "스피드") + Integer.parseInt(args[3]);
							PlayerYml.setInfoInt(p, "스피드", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 스피드 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 정도 추가하였습니다.");
							
							if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
								float y = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
					
					else if (args[0].equalsIgnoreCase("빼기"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/OnePunchStat/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("1"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "팔 근력") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							PlayerYml.setInfoInt(p, "팔 근력", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 팔 근력 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 만큼 뺐습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("2"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "복근") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							PlayerYml.setInfoInt(p, "복근", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 복근 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 만큼 뺐습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("3"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "다리 근력") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							PlayerYml.setInfoInt(p, "다리 근력", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 다리 근력 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 만큼 뺐습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("4"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = PlayerYml.getInfoInt(p, "스피드") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							PlayerYml.setInfoInt(p, "스피드", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 스피드 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 만큼 뺐습니다.");
							
							if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
								float y = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
				sender.sendMessage("당신은 명령어를 실행할 권한이 없습니다.");
				return false;
			}
		} return false;
	}
}
