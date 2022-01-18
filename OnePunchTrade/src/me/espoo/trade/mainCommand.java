package me.espoo.trade;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("거래") || commandLabel.equalsIgnoreCase("교환")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			
		} return false;
	}
}
