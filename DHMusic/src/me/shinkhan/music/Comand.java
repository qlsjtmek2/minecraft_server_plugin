package me.shinkhan.music;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public class Comand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static Map<String, SongPlayer> songMap = new HashMap<String, SongPlayer>();
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
			if (commandLabel.equalsIgnoreCase("musiccheck")) {
				sender.sendMessage("��6���� �� �������� ��cDHMusic " + main.var + "v ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
				sender.sendMessage("��6������ :: ��cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("��cDHMusic ��6�÷������� ������ּż� �����մϴ�.  ��f- ������ shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6���� �� �������� ��cDHMusic " + main.var + "v ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
			sender.sendMessage("��6������ :: ��cshinkhan");
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("����")) {
				if (args.length >= 2) {
					if (sender.isOp()) {
						if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("���")) {
							if (Method.getFileList().contains(Method.getFinalArg(args, 1))) {
								Method.play(Bukkit.getOnlinePlayers(), Method.getFinalArg(args, 1));
								return false;
							} else {
								sender.sendMessage(RED + "�߸��� ���� ���� �̸��Դϴ�.");
								sender.sendMessage(GOLD + "/���� (all/���/playername) <���� �̸�> " + WHITE + "- ������ ����մϴ�.");
								return false;
							}
						}
						
						String name = Method.searchOnlinePlayer(args[0]);
						if (name == null || Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "�߸��� ��ɾ� �̰ų� �߸��� �÷��̾� �̸��Դϴ�.");
							sender.sendMessage(GOLD + "/���� (all/���/playername) <���� �̸�> " + WHITE + "- ������ ����մϴ�.");
							return false;
						}
						
						if (Method.getFileList().contains(Method.getFinalArg(args, 1))) {
							Player pl = Method.getOnorOffLine(args[0]);
							Method.play(new Player[] { pl }, Method.getFinalArg(args, 1));
							return false;
						} else {
							sender.sendMessage(RED + "�߸��� ���� ���� �̸��Դϴ�.");
							sender.sendMessage(GOLD + "/���� (all/���/playername) <���� �̸�> " + WHITE + "- ������ ����մϴ�.");
							return false;
						}
					} else {
						sender.sendMessage(RED + "����� �� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
				}
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.openGUI(p, 1);
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				GUI.openGUI(p, 1);
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		} return false;
	}
}
