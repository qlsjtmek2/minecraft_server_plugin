package me.shinkhan.treasurechest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("�������ڻ���")) {
				if (sender instanceof Player) {
					Config.setString("��ǥ", args[0]);
					sender.sendMessage(GOLD + "���������� ��ǥ�� �����߽��ϴ�.");
					main.chest = API.StringToLocation(args[0]);
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage(GOLD + "/�������ڻ��� <X,Y,Z,World,Yaw,Pitch> " + WHITE + "- �������� ��ǥ�� �����մϴ�.");
			return false;
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("��������GUI")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					Inventory GUI = Bukkit.createInventory(null, 27, "���� ���� ����");
					p.openInventory(GUI);
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage(GOLD + "/�������� <���� �̸�> " + WHITE + "- Config�� ������ ������ �����մϴ�.");
			return false;
		} return false;
	}
}
