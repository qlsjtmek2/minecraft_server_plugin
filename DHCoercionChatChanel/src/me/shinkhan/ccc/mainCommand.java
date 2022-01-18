package me.shinkhan.ccc;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
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
			if (commandLabel.equalsIgnoreCase("ä�üҿ�") || commandLabel.equalsIgnoreCase("ccc")) {
				if (sender.isOp()) {
					if (args.length == 0) {
						HelpMessage(sender);
						return false;
					}
					
					if (args[0].equalsIgnoreCase("���") || args[0].equalsIgnoreCase("list")) {
						List<String> list = API.getList();
						int k = 1;
						if (args.length == 2) k = Integer.parseInt(args[1]);
						if (args.length > 2 || args.length == 0) {
							HelpMessage(sender);
							return false;
						}
						
				        if (list == null || list.isEmpty() || k * 10 - 9 > list.size() || k == 0) {
				            sender.sendMessage(RED + "�ش� ����� �������� �ʽ��ϴ�.");
				            return false;
				        }
				        
				        if (list.size() % 10 == 0) {
				            sender.sendMessage(RED + "" + list.size() + GOLD + " ���� ������ ã�ҽ��ϴ�. " + RED  + k + GOLD + "/" + RED + list.size() / 10);
				        } else {
				            sender.sendMessage(RED + "" + list.size() + GOLD + " ���� ������ ã�ҽ��ϴ�. " + RED  + k + GOLD + "/" + RED + (list.size() / 10 + 1));
				        }
				        
				        for (int j = (k - 1) * 10; j < k * 10; ++j) {
							sender.sendMessage(RED + "" + (j + 1) + ": " + GOLD + list.get(j));
				            if (list.size() == j + 1) {
				                break;
				            }
				            
				            if (k * 10 - 1 == j && list.size() > j + 1) {
								sender.sendMessage(GOLD + "���� ����� ���÷��� " + RED + "/ä�üҿ� ��� " + (k + 1) + " " + GOLD + "��/�� �Է����ּ���.");
				            }
				        } return false;
					}
					
					if (args.length == 2) {
						if (args[0].equalsIgnoreCase("�߰�") || args[0].equalsIgnoreCase("add")) {
							API.addPlayer(args[1]);
							sender.sendMessage(GOLD + "���������� " + RED + args[1] + GOLD + " �÷��̾ �߰��߽��ϴ�.");
							API.reloadConfig();
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("remove")) {
							API.removePlayer(args[1]);
							sender.sendMessage(GOLD + "���������� " + RED + args[1] + GOLD + " �÷��̾ �����߽��ϴ�.");
							API.reloadConfig();
							return false;
						}
					}
					
					else {
						HelpMessage(sender);
						return false;
					}
				} else {
					sender.sendMessage(RED + "����� ������ �����ϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender.isOp()) {
				HelpMessage(sender);
				return false;
			} else {
				sender.sendMessage(RED + "����� ������ �����ϴ�.");
				return false;
			}
		} return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage(YELLOW + " ---- " + GOLD + "ä�� �ҿ� " + YELLOW + "-- " + GOLD + "������ " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/ä�üҿ�, /ccc " + WHITE + "- ä�üҿ� ��ɾ� ������ Ȯ���մϴ�.");
		sender.sendMessage(GOLD + "/ä�üҿ� <���/list> " + WHITE + "- ä���� �ҿܵ� �÷��̾���� Ȯ���մϴ�.");
		sender.sendMessage(GOLD + "/ä�üҿ� <�߰�/add> <�÷��̾�> " + WHITE + "- �ҿ��� �÷��̾ �߰��մϴ�.");
		sender.sendMessage(GOLD + "/ä�üҿ� <����/remove> <�÷��̾�> " + WHITE + "- �ҿ��� �÷��̾ �����մϴ�.");
		sender.sendMessage("");
		sender.sendMessage(YELLOW + " * ä�üҿܴ� Ư�� �÷��̾�鳢���� ä���ϵ��� �ҿܽ�Ű�� ���Դϴ�.");
	}

}
