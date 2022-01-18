package me.espoo.BRB;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static ChatColor RED = ChatColor.RED;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("k")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("");
			return false;
		}
		
		
		
		
		
		return false;
	}
}
