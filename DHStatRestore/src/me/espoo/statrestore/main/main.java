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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("sr")) {
				if (!(sender instanceof Player)) {
					return false;
				} Player p = (Player) sender;
				
				if (args.length != 2) {
					p.sendMessage("��6/sr <�г���> ��f- ������ ������ �ڵ����� �����մϴ�.");
					return false;
				}
				
				String name = Method.searchOnlinePlayer(args[0]);
				
				if (Method.getOnorOffLine(name) == null) {
					sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
					return false;
				}
				
				Player player = Method.getOnorOffLine(name);
				RpgPlayer rpgplayer = Rpg.getRpgPlayera(player.getName());
				StatsPlayer sp = StatsAPI.getStatsPlayer(player.getName());
				int level = rpgplayer.getRpgLevel();
				if (level <= 0) return false;
				
				if (player.hasPermission("����.����") || player.hasPermission("����.������") ||player.hasPermission("����.�ü�") 
				 || player.hasPermission("����.������") || player.hasPermission("����.����")) {
					int i = (level * 2) + 5;
				}
				
				sp.getStatPoint(name);
				
				return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6���� �� �������� ��cDHBoatPrevent ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
			sender.sendMessage("��6������ :: ��cshinkhan");
		} return false;
	}
}
