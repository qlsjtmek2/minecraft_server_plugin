package me.shinkhan.boat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{	
	public void onEnable()
	{
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("boatcheck")) {
				sender.sendMessage("§6현재 이 서버에는 §cDHBoatPrevent §6플러그인이 적용되어 있습니다.");
				sender.sendMessage("§6제작자 :: §cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("§cDHBoatPrevent §6플러그인을 사용해주셔서 감사합니다.  §f- shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6현재 이 서버에는 §cDHBoatPrevent §6플러그인이 적용되어 있습니다.");
			sender.sendMessage("§6제작자 :: §cshinkhan");
		} return false;
	}

}
