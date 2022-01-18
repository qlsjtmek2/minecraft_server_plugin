package me.espoo.statrestore.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.tpsw.RpgStatsSystem.api.StatsAPI;
import me.tpsw.RpgStatsSystem.api.StatsPlayer;
import to.oa.tpsw.rpgexpsystem.api.Rpg;
import to.oa.tpsw.rpgexpsystem.api.RpgPlayer;

public class main extends JavaPlugin implements Listener {
	public void onEnable()
	{
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
			if (commandLabel.equalsIgnoreCase("sr")) {
				if (!(sender instanceof Player)) {
					return false;
				} Player p = (Player) sender;
				
				if (args.length != 2) {
					p.sendMessage("§6/sr <닉네임> §f- 유저의 스텟을 자동으로 복구합니다.");
					return false;
				}
				
				String name = Method.searchOnlinePlayer(args[0]);
				
				if (Method.getOnorOffLine(name) == null) {
					sender.sendMessage(ChatColor.RED + "그 플레이어는 온라인이 아닙니다.");
					return false;
				}
				
				Player player = Method.getOnorOffLine(name);
				RpgPlayer rpgplayer = Rpg.getRpgPlayera(player.getName());
				StatsPlayer sp = StatsAPI.getStatsPlayer(player.getName());
				int level = rpgplayer.getRpgLevel();
				if (level <= 0) return false;
				
				if (player.hasPermission("전직.전사") || player.hasPermission("전직.마법사") ||player.hasPermission("전직.궁수") 
				 || player.hasPermission("전직.파이터") || player.hasPermission("전직.도적")) {
					int i = (level * 2) + 5;
				}
				
				sp.getStatPoint(name);
				
				return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6현재 이 서버에는 §cDHBoatPrevent §6플러그인이 적용되어 있습니다.");
			sender.sendMessage("§6제작자 :: §cshinkhan");
		} return false;
	}
}
