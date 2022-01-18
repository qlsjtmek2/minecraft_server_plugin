package me.espoo.book;

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
			if (commandLabel.equalsIgnoreCase("����")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUImain.openGUI(p);
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				GUImain.openGUI(p);
				return false;
			} else {
				return false;
			}
		} 
		
		try {
			if (commandLabel.equalsIgnoreCase("in")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (p.isOp()) {
						if (main.shop.get(p.getName()) != null) {
							p.sendMessage("��6������ �̸� ���� ��尡 ��c������6�Ǿ����ϴ�.");
							main.shop.remove(p.getName());
							return false;
						} else {
							p.sendMessage("��6������ �̸� ���� ��尡 ��c������6�Ǿ����ϴ�.");
							main.shop.put(p.getName(), "true");
							return false;
						}
					} return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("error");
		} return false;
	}
}
