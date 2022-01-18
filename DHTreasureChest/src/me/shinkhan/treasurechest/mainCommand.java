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
			if (commandLabel.equalsIgnoreCase("보물상자생성")) {
				if (sender instanceof Player) {
					Config.setString("좌표", args[0]);
					sender.sendMessage(GOLD + "성공적으로 좌표를 저장했습니다.");
					main.chest = API.StringToLocation(args[0]);
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage(GOLD + "/보물상자생성 <X,Y,Z,World,Yaw,Pitch> " + WHITE + "- 보물상자 좌표를 저장합니다.");
			return false;
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("보물상자GUI")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					Inventory GUI = Bukkit.createInventory(null, 27, "보급 상자 설정");
					p.openInventory(GUI);
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage(GOLD + "/상점오픈 <상점 이름> " + WHITE + "- Config에 설정된 상점을 오픈합니다.");
			return false;
		} return false;
	}
}
