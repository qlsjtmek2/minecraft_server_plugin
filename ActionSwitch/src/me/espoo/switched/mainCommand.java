package me.espoo.switched;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.switched.sheep.SheepGUI;
import me.espoo.switched.trade.TradeAPI;
import me.espoo.switched.trade.TradeGUI;

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
			if (commandLabel.equalsIgnoreCase("양털") || commandLabel.equalsIgnoreCase("양털교환") || commandLabel.equalsIgnoreCase("양털교환기")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					SheepGUI.openGUI(p);
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				SheepGUI.openGUI(p);
				return false;
			} else {
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("교환기오픈")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length != 1) {
						return false;
					}
					
					if (!TradeAPI.isTradeName(args[0])) {
						p.sendMessage("§c해당 목록은 존재하지 않습니다.");
						return false;
					}
					
					TradeGUI.openGUI(p, args[0], 1);
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) { return false; }
		
		try {
			if (commandLabel.equalsIgnoreCase("교환기추가")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length != 1 || !p.isOp()) {
						return false;
					}
					
					TradeGUI.OPopenGUI(p, args[0]);
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) { return false; }
		return false;
	}
}
