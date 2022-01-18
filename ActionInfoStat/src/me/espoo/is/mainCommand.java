package me.espoo.is;

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
		try {
			if (commandLabel.equalsIgnoreCase("정보")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					
					if (args.length == 0) {
						GUI.InfoGUI(p, p);
						return false;
					}
					
					else if (args.length == 1) {
						String str = Method.searchOnlinePlayer(args[0]);
						
						if (str == null) {
							return false;
						}
						
						Player pl = Method.getOnorOffLine(str);
						
						if (pl == null) {
							p.sendMessage("§c그 플레이어는 온라인 상태가 아닙니다.");
							return false;
						}
						
						if (!p.isOp() && !me.espoo.option.PlayerYml.getInfoBoolean(pl, "정보 공개 여부")) {
							p.sendMessage("§c그 플레이어는 정보 공개를 거부해두었습니다.");
							return false;
	 					}
						
						GUI.InfoGUI(p, pl);
						return false;
					}
					
					else {
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				sender.sendMessage("§6/정보 <닉네임> §f- 플레이어의 정보를 봅니다.");
				return false;
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("상태")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						GUI.StatGUI(p, p);
						return false;
					}
					
					else if (args.length == 1) {
						String str = Method.searchOnlinePlayer(args[0]);
						
						if (str == null) {
							p.sendMessage("§c그 플레이어는 존재하지 않습니다.");
							return false;
						}
						
						Player pl = Method.getOnorOffLine(str);
						
						if (pl == null) {
							p.sendMessage("§c그 플레이어는 온라인 상태가 아닙니다.");
							return false;
						}
						
						if (!p.isOp() && !me.espoo.option.PlayerYml.getInfoBoolean(pl, "상태 공개 여부")) {
							p.sendMessage("§c그 플레이어는 상태 공개를 거부해두었습니다.");
							return false;
	 					}
						
						GUI.StatGUI(p, pl);
						return false;
					}
					
					else {
						p.sendMessage("§6/상태 <닉네임> §f- 플레이어의 상태를 봅니다.");
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				sender.sendMessage("§6/상태 <닉네임> §f- 플레이어의 상태를 봅니다.");
				return false;
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		} return false;
	}
}
