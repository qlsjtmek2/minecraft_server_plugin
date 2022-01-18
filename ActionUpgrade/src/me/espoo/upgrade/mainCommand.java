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
		if (commandLabel.equalsIgnoreCase("축복기회추가")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("*")) {
				return false;
			}
			
			p.sendMessage("§c강화 축복 주문서 §6를 사용하여 축복되는 강화 기회가 §c1 §6만큼 추가되었습니다. §e[ /축복 ]");
			API.addPlayerPremiumAmount(p);
		}
		
		else if (commandLabel.equalsIgnoreCase("축복")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}

			Player p = (Player) sender;
			int i = API.getPlayerPremiumAmount(p);
			
			p.sendMessage("§6당신의 강화 축복 기회는 §c" + i + " §6번 입니다.");
			return false;
		} return false;
	}
}
