package me.shinkhan.userguide;

import java.util.List;

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
			if (commandLabel.equalsIgnoreCase("�������̵�") || commandLabel.equalsIgnoreCase("���̵�") || commandLabel.equalsIgnoreCase("userguide")
			 || commandLabel.equalsIgnoreCase("guide") || commandLabel.equalsIgnoreCase("ug")) {
				if (args.length == 0) {
					HelpMessage(sender);
					return false;
				} else {
					if (args[0].equalsIgnoreCase("���") || args[0].equalsIgnoreCase("list")) {
						if (API.getUserGuide() == null || API.getUserGuide().isEmpty()) {
							sender.sendMessage(RED + "���� �������̵尡 �������� �ʽ��ϴ�.");
							return false;
						}

						List<String> list = API.getUserGuide();
						int k = 1;
						if (args.length == 2) k = Integer.parseInt(args[1]);
						if (args.length > 2 || args.length == 0) {
							HelpMessage(sender);
							return false;
						}
						
				        if (k * 10 - 9 > list.size() || k == 0) {
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
								sender.sendMessage(GOLD + "���� ����� ���÷��� " + RED + "/���̵� ��� " + (k + 1) + " " + GOLD + "��/�� �Է����ּ���.");
				            }
				        } return false;
					}
					
					else if (args[0].equalsIgnoreCase("�߰�") && args.length == 2 || args[0].equalsIgnoreCase("g") && args.length == 2) {
						if (!sender.isOp()) {
							sender.sendMessage(RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = args[1];
						API.addUserGuide(name);
						sender.sendMessage(GOLD + "���������� " + RED + name + GOLD + " ���� �������̵�� �Ӹ��ϼ̽��ϴ�.");
						
						Player p = API.getOnorOffLine(name);
						if (p != null) p.sendMessage("��f[��a�ȳ���f] ��6�����մϴ�! ����� ��e�������̵� ��6�� �Ӹ�Ǽ̽��ϴ�.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("����") && args.length == 2 || args[0].equalsIgnoreCase("r") && args.length == 2) {
						if (!sender.isOp()) {
							sender.sendMessage(RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = args[1];
						if (!API.isUserGuide(name)) {
							sender.sendMessage(RED + "�� �÷��̾�� �������̵尡 �ƴմϴ�.");
							return false;
						}
						
						API.removeUserGuide(name);
						sender.sendMessage(GOLD + "���������� " + RED + name + GOLD + " ���� �������̵忡�� �����Ͽ����ϴ�.");
						
						Player p = API.getOnorOffLine(name);
						if (p != null) p.sendMessage("��f[��a�ȳ���f] ��c����� ��e�������̵� ��c���� ���ӵǼ̽��ϴ�.");
						return false;
					}
				}
			}
		} catch (NumberFormatException ex) {
			HelpMessage(sender);
			return false;
		} return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage(YELLOW + " ---- " + GOLD + "�������̵� " + YELLOW + "-- " + GOLD + "������ " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/�������̵�, /���̵�, /UserGuide, /Guide, /UG " + WHITE + "- ���� ���̵� ��ɾ Ȯ���մϴ�.");
		sender.sendMessage(GOLD + "/���̵� <���/List> " + WHITE + "- �� ��� ���� Ȯ���մϴ�.");
		if (sender.isOp()) {
			sender.sendMessage(BLUE + "/���̵� <�߰�/g> <�г���> " + WHITE + "- �÷��̾�� ���̵� ������ �ο��մϴ�.");
			sender.sendMessage(BLUE + "/���̵� <����/r> <�г���> " + WHITE + "- �÷��̾�� ���̵� ������ �����ϴ�.");
		}

		sender.sendMessage("");
		sender.sendMessage(YELLOW + "* ���̵�� ä�� ���� ��Ȳ�� ä���� ĥ �� �ֽ��ϴ�.");
		sender.sendMessage(RED + "* ���̵�� ������ Īȣ�� ��f[��2������a���̵��f] ��c�� �����˴ϴ�.");
	}
}
