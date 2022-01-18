package me.shinkhan.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Comand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public Comand(main instance)
	{
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("shopcheck")) {
				sender.sendMessage("§6현재 이 서버에는 §cDHShop " + main.var + "v §6플러그인이 적용되어 있습니다.");
				sender.sendMessage("§6제작자 :: §cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("§cDHShop §6플러그인을 사용해주셔서 감사합니다.  §f- 제작자 shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6현재 이 서버에는 §cDHShop " + main.var + "v §6플러그인이 적용되어 있습니다.");
			sender.sendMessage("§6제작자 :: §cshinkhan");
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("상점오픈")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 1) {
						if (Config.getList(args[0].replace("_", " ")) == null) {
							p.sendMessage("§c해당 상점이 존재하지 않습니다. Config에서 상점 설정을 해주시기 바랍니다.");
							return false;
						}
						
						GUI.openGUI(p, 1, args[0].replace("_", " "));
						return false;
					} else {
						sender.sendMessage("§6/상점오픈 <상점 이름> §f- Config에 설정된 상점을 오픈합니다.");
						return false;
					}
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage(GOLD + "/상점오픈 <상점 이름> " + WHITE + "- Config에 설정된 상점을 오픈합니다.");
			return false;
		} return false;
	}

}
