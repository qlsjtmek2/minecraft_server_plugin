package me.shinkhan.fatigue;

import java.io.File;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	public static ChatColor GOLD = ChatColor.GOLD;
	public static ChatColor RED = ChatColor.RED;
	
	public void onEnable()
	{
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 public void run() {
				 for (Player p : Bukkit.getOnlinePlayers()) {
					 API.addFatigue(p, 1D);
				 }
			 }
		 }, 12000L, 12000L);
		 
		getServer().getPluginManager().registerEvents(new Event(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + RED + "■ " + RED + "비활성화가 완료되었습니다.");
	}
	
	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("fa") || commandLabel.equalsIgnoreCase("피로도")) {	
				if (args.length == 0) {
					if (sender.isOp()) {
						Method.HelpMessage(sender);
						return false;
					} else {
						Player p = (Player) sender;
						p.sendMessage(GOLD + "당신의 피로도는 " + RED + API.getFatigue(p) + GOLD + "% 입니다.");
					}
				}
				
				else if (args.length == 1) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						
						if (args[0].equalsIgnoreCase("보기")) {
							p.sendMessage(GOLD + "당신의 피로도는 " + RED + API.getFatigue(p) + GOLD + "% 입니다.");
							return false;
						}
					} else {
						sender.sendMessage(RED + "당신은 이 명령어를 실행시킬 수 없습니다.");
						return false;
					}
				}
				
				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("보기")) {
						String name = Method.searchOnlinePlayer(args[1]);
						
						File f = new File("plugins/FatigueAPI/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						Player p = Method.getOnorOffLine(name);
						sender.sendMessage(RED + p.getName() + GOLD + "님의 피로도는 " + RED + API.getFatigue(p) + GOLD + "% 입니다.");
						return false;
					}
				}

				else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("설정"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[1]);
						
						File f = new File("plugins/FatigueAPI/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						Scanner scan = new Scanner(args[2]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
							return false;
						}

						Player p = Method.getOnorOffLine(name);
						API.setFatigue(p, (double) Integer.parseInt(args[2]));
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("추가"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[1]);
						
						File f = new File("plugins/FatigueAPI/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						Scanner scan = new Scanner(args[2]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
							return false;
						}

						Player p = Method.getOnorOffLine(name);
						API.addFatigue(p, (double) Integer.parseInt(args[2]));
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("빼기"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[1]);
						
						File f = new File("plugins/FatigueAPI/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "그 플레이어는 온라인이 아닙니다.");
							return false;
						}
						
						Scanner scan = new Scanner(args[2]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
							return false;
						}

						Player p = Method.getOnorOffLine(name);
						API.subFatigue(p, (double) Integer.parseInt(args[2]));
						return false;
					}
				}
				
				else {}
			}
		} catch (NumberFormatException ex) {
			if (sender.isOp()) {
				Method.HelpMessage(sender);
				return false;
			} else {
				Player p = (Player) sender;
				p.sendMessage("당신의 피로도는 " + RED + API.getFatigue(p) + GOLD + "% 입니다.");
			}
		} return false;
	}
}
