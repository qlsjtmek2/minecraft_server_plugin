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
			if (commandLabel.equalsIgnoreCase("������Ż")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.RankingGUI(p, 1);
					return true;
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("������Ż�߰�")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (p.hasPermission("*")) {
						if (API.isPotalList(p.getName())) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����ǽ�ũ�� give " + p.getName() + " 1");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.sendMessage("��c���� ��Ż ������ �ѹ��� �����մϴ�.");
							return false;
						}

						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.sendMessage("��6���������� ��c���� ��Ż ��6�� �����ϼ̽��ϴ�. ��e[ /������Ż ]");
						Config.addPotal(p.getName());
						API.setIslandWarp(p, p.getName());
						return true;
					} return false;
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			return false;
		} return false;
	}
}
