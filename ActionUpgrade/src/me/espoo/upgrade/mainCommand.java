package me.espoo.upgrade;

import org.bukkit.ChatColor;
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
		if (commandLabel.equalsIgnoreCase("�ູ��ȸ�߰�")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "�ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("*")) {
				return false;
			}
			
			p.sendMessage("��c��ȭ �ູ �ֹ��� ��6�� ����Ͽ� �ູ�Ǵ� ��ȭ ��ȸ�� ��c1 ��6��ŭ �߰��Ǿ����ϴ�. ��e[ /�ູ ]");
			API.addPlayerPremiumAmount(p);
		}
		
		else if (commandLabel.equalsIgnoreCase("�ູ")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "�ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}

			Player p = (Player) sender;
			int i = API.getPlayerPremiumAmount(p);
			
			p.sendMessage("��6����� ��ȭ �ູ ��ȸ�� ��c" + i + " ��6�� �Դϴ�.");
			return false;
		} return false;
	}
}
