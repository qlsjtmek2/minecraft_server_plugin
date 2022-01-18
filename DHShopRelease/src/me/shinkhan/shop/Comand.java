package me.shinkhan.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Comand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public Comand(main instance)
	{
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("shopcheck")) {
				sender.sendMessage("��6���� �� �������� ��cDHShop " + main.var + "v ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
				sender.sendMessage("��6������ :: ��cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("��cDHShop ��6�÷������� ������ּż� �����մϴ�.  ��f- ������ shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6���� �� �������� ��cDHShop " + main.var + "v ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
			sender.sendMessage("��6������ :: ��cshinkhan");
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("��������")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 1) {
						if (Config.getList(args[0].replace("_", " ")) == null) {
							p.sendMessage("��c�ش� ������ �������� �ʽ��ϴ�. Config���� ���� ������ ���ֽñ� �ٶ��ϴ�.");
							return false;
						}
						
						GUI.openGUI(p, 1, args[0].replace("_", " "));
						return false;
					} else {
						sender.sendMessage("��6/�������� <���� �̸�> ��f- Config�� ������ ������ �����մϴ�.");
						return false;
					}
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage(GOLD + "/�������� <���� �̸�> " + WHITE + "- Config�� ������ ������ �����մϴ�.");
			return false;
		} return false;
	}

}
