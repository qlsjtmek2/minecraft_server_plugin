package me.shinkhan.BuyHouse;

import java.io.File;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin implements Listener {
    public static Economy economy = null;
    
	public void onEnable()
	{
		File f = new File("plugins/DHBuyHouse/config.yml");
		File folder = new File("plugins/DHBuyHouse");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Method.CreateConfig(f, folder, config);
		
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("housecheck")) {
				sender.sendMessage("§6현재 이 서버에는 §cDHBuyHouse §6플러그인이 적용되어 있습니다.");
				sender.sendMessage("§6제작자 :: §cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("§cDHBuyHouse §6플러그인을 사용해주셔서 감사합니다.  §f- shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6현재 이 서버에는 §cDHBuyHouse §6플러그인이 적용되어 있습니다.");
			sender.sendMessage("§6제작자 :: §cshinkhan");
		} 
		
		try {
			if (commandLabel.equalsIgnoreCase("자동구매")) {	
				if (args.length == 0) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (p.isOp()) {
							Method.HelpOpMessage(sender);
							return false;
						} else {
							Method.HelpUserMessage(sender);
							return false;
						}
					} else {
						Method.HelpConMessage(sender);
						return false;
					}
				}
				
				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("보기")) {
						if (!(sender instanceof Player)) {
							return false;
						} Player p = (Player) sender;
						
						if (args[1].equalsIgnoreCase("모두")) {
							p.sendMessage("§6당신의 §c자동 구매 §6모든 목록입니다.");
							p.sendMessage("§f - §6집 §f:: " + Method.getInfoInt(p, "집 개수"));
							p.sendMessage("§f - §6창고 §f:: " + Method.getInfoInt(p, "창고 개수"));
							p.sendMessage("§f - §6모루 §f:: " + Method.getInfoInt(p, "모루 개수"));
							p.sendMessage("§f - §6인첸트 §f:: " + Method.getInfoInt(p, "인첸트 개수"));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("집")) {
							p.sendMessage("§6당신은 §c" + Method.getInfoInt(p, "집 개수") + " §6개의 집을 소유하고 있습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("창고")) {
							p.sendMessage("§6당신은 §c" + Method.getInfoInt(p, "창고 개수") + " §6개의 창고를 소유하고 있습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("모루")) {
							p.sendMessage("§6당신은 §c" + Method.getInfoInt(p, "모루 개수") + " §6개의 모루를 소유하고 있습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("인첸트")) {
							p.sendMessage("§6당신은 §c" + Method.getInfoInt(p, "인첸트 개수") + " §6개의 인첸트를 소유하고 있습니다.");
							return false;
						}
					}
				}
				
				else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("초기화")) {
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("모두")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "집 개수", 0);
							Method.setInfoInt(p, "창고 개수", 0);
							Method.setInfoInt(p, "모루 개수", 0);
							Method.setInfoInt(p, "인첸트 개수", 0);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 모든 값을 초기화 하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("집")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "집 개수", 0);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 집 개수 값을 초기화 하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("창고")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "창고 개수", 0);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 창고 개수 값을 초기화 하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("모루")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "모루 개수", 0);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 모루 개수 값을 초기화 하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("인첸트")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "인첸트 개수", 0);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 인첸트 개수 값을 초기화 하였습니다.");
							return false;
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
						
						File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
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
						
						if (args[1].equalsIgnoreCase("집"))
						{
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "집 개수", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 집 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 으로 설정하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("창고"))
						{
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "창고 개수", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 창고 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 으로 설정하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("모루"))
						{
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "모루 개수", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 모루 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 으로 설정하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("인첸트"))
						{
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "인첸트 개수", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 인첸트 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 으로 설정하였습니다.");
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
						
						File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
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
						
						if (args[1].equalsIgnoreCase("집"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "집 개수") + Integer.parseInt(args[3]);
							Method.setInfoInt(p, "집 개수", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 집 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 정도 추가하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("창고"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "창고 개수") + Integer.parseInt(args[3]);
							Method.setInfoInt(p, "창고 개수", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 창고 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 정도 추가하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("모루"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "모루 개수") + Integer.parseInt(args[3]);
							Method.setInfoInt(p, "모루 개수", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 모루 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 정도 추가하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("인첸트"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "인첸트 개수") + Integer.parseInt(args[3]);
							Method.setInfoInt(p, "인첸트 개수", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 인첸트 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 정도 추가하였습니다.");
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
						
						File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
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
						
						if (args[1].equalsIgnoreCase("집"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "집 개수") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							Method.setInfoInt(p, "집 개수", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 집 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 만큼 뺐습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("창고"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "창고 개수") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							Method.setInfoInt(p, "창고 개수", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 창고 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 만큼 뺐습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("모루"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "모루 개수") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							Method.setInfoInt(p, "모루 개수", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 모루 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 만큼 뺐습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("인첸트"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "인첸트 개수") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							Method.setInfoInt(p, "인첸트 개수", i);
							sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + name + ChatColor.GOLD + " 님의 인첸트 개수 값을 " + ChatColor.RED + args[3] + ChatColor.GOLD + " 만큼 뺐습니다.");
							return false;
						}
					}
				}
				
				else {}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.isOp()) {
					Method.HelpOpMessage(sender);
					return false;
				} else {
					Method.HelpUserMessage(sender);
					return false;
				}
			} else {
				Method.HelpConMessage(sender);
				return false;
			}
		} return false;
	}
}
