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
			if (commandLabel.equalsIgnoreCase("����")) {
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
							p.sendMessage("��c�� �÷��̾�� �¶��� ���°� �ƴմϴ�.");
							return false;
						}
						
						if (!p.isOp() && !me.espoo.option.PlayerYml.getInfoBoolean(pl, "���� ���� ����")) {
							p.sendMessage("��c�� �÷��̾�� ���� ������ �ź��صξ����ϴ�.");
							return false;
	 					}
						
						GUI.InfoGUI(p, pl);
						return false;
					}
					
					else {
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				sender.sendMessage("��6/���� <�г���> ��f- �÷��̾��� ������ ���ϴ�.");
				return false;
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("����")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						GUI.StatGUI(p, p);
						return false;
					}
					
					else if (args.length == 1) {
						String str = Method.searchOnlinePlayer(args[0]);
						
						if (str == null) {
							p.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						Player pl = Method.getOnorOffLine(str);
						
						if (pl == null) {
							p.sendMessage("��c�� �÷��̾�� �¶��� ���°� �ƴմϴ�.");
							return false;
						}
						
						if (!p.isOp() && !me.espoo.option.PlayerYml.getInfoBoolean(pl, "���� ���� ����")) {
							p.sendMessage("��c�� �÷��̾�� ���� ������ �ź��صξ����ϴ�.");
							return false;
	 					}
						
						GUI.StatGUI(p, pl);
						return false;
					}
					
					else {
						p.sendMessage("��6/���� <�г���> ��f- �÷��̾��� ���¸� ���ϴ�.");
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				sender.sendMessage("��6/���� <�г���> ��f- �÷��̾��� ���¸� ���ϴ�.");
				return false;
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		} return false;
	}
}
