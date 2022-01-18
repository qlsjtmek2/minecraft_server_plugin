package me.shinkhan.Finance;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.shinkhan.Finance.config.GUIMessage;
import me.shinkhan.Finance.config.Message;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final Main plugin;
	Message E;
	GUIMessage G;

	public mainCommand(Main instance)
	{
		this.plugin = instance;
	}

	@SuppressWarnings("static-access")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("����") || 
				commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("BK") || commandLabel.equalsIgnoreCase("Bank")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.openGUI(p);
					return false;
				} else {
					sender.sendMessage(E.getMessage("��Ŷ ��� (���ڵ� X)"));
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				GUI.openGUI(p);
				return false;
			} else {
				sender.sendMessage(E.getMessage("��Ŷ ��� (���ڵ� X)"));
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("��")) {
				if (args.length == 2) {
					if (sender.isOp()) {
						double x = Integer.parseInt(args[0]);
						double y = Integer.parseInt(args[1]);
						double z = x * ((y + 100D) / 100D);
						sender.sendMessage(ChatColor.GOLD + "���ʽ� ���� " + ChatColor.RED + (int) z + ChatColor.GOLD + "\\ �Դϴ�.");
					}
				} return false;
			}
		} catch (NumberFormatException ex) {
			return false;
		} 
		
		try {
			if (commandLabel.equalsIgnoreCase("��")) {
				if (args.length == 2) {
					if (sender.isOp()) {
						double x = Double.parseDouble(args[0]);
						double y = Double.parseDouble(args[1]);
						double z = (y / x) * 100 - 100;
						sender.sendMessage(ChatColor.GOLD + "���� ���� " + ChatColor.RED + (int) z + ChatColor.GOLD + "% �Դϴ�.");
					}
				} return false;
			}
		} catch (NumberFormatException ex) {
			return false;
		} return false;
	}
}
