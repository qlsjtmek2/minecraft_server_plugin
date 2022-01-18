package me.shinkhan.plantpotal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
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
			if (commandLabel.equalsIgnoreCase("유저포탈")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.RankingGUI(p, 1);
					return true;
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("유저포탈추가")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (p.hasPermission("*")) {
						if (API.isPotalList(p.getName())) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마법의스크롤 give " + p.getName() + " 1");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.sendMessage("§c유저 포탈 연결은 한번만 가능합니다.");
							return false;
						}

						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.sendMessage("§6성공적으로 §c유저 포탈 §6을 생성하셨습니다. §e[ /유저포탈 ]");
						Config.addPotal(p.getName());
						API.setIslandWarp(p, p.getName());
						return true;
					} return false;
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			return false;
		} return false;
	}
}
