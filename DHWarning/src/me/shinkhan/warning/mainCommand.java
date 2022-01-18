package me.shinkhan.warning;

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
			if (commandLabel.equalsIgnoreCase("���") || commandLabel.equalsIgnoreCase("wa")) {
					if (args.length == 0) {
						HelpMessage(sender);
						return false;
					} else {
						if (args[0].equalsIgnoreCase("Ȯ��") || args[0].equalsIgnoreCase("v")) {
							if (args.length == 1) {
								if (sender instanceof Player) {
									Player p = (Player) sender;
									p.sendMessage("��6����� ��� ���� ��c" + API.getWarning(p.getName()) + " ��6�Դϴ�.");
								} else {
									sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
								} return false;
							}
							
							else if (args.length == 2) {
								String name = args[1];
								if (!API.isFile(name)) {
									sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
									return false;
								}
								
								sender.sendMessage(RED + name + GOLD + " ���� ��� ���� " + RED + API.getWarning(name) + GOLD + " �Դϴ�.");
								return false;
							}
							
							else {
								HelpMessage(sender);
								return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("�ֱ�") && args.length == 3 || args[0].equalsIgnoreCase("g") && args.length == 3) {
							if (!sender.isOp()) {
								sender.sendMessage(RED + "����� ������ �����ϴ�.");
								return false;
							}
							
							String name = args[1];
							if (!API.isFile(name)) {
								sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (!API.isNumeric(args[2])) {
								sender.sendMessage(RED + "<��> �κп� ���ڸ� �Է��� �ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							int num = Integer.parseInt(args[2]);
							API.addWarning(name, num);
							sender.sendMessage(GOLD + "���������� " + RED + name + GOLD + " ���� ��� ���� " + RED + num + GOLD + " ��ŭ �߰��Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("����") && args.length == 3 || args[0].equalsIgnoreCase("t") && args.length == 3) {
							if (!sender.isOp()) {
								sender.sendMessage(RED + "����� ������ �����ϴ�.");
								return false;
							}
							
							String name = args[1];
							if (!API.isFile(name)) {
								sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (!API.isNumeric(args[2])) {
								sender.sendMessage(RED + "<��> �κп� ���ڸ� �Է��� �ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							int num = Integer.parseInt(args[2]);
							API.subWarning(name, num);
							sender.sendMessage(GOLD + "���������� " + RED + name + GOLD + " ���� ��� ���� " + RED + num + GOLD + " ��ŭ �����ϴ�.");
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("����") && args.length == 3 || args[0].equalsIgnoreCase("s") && args.length == 3) {
							if (!sender.isOp()) {
								sender.sendMessage(RED + "����� ������ �����ϴ�.");
								return false;
							}
							
							String name = args[1];
							if (!API.isFile(name)) {
								sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (!API.isNumeric(args[2])) {
								sender.sendMessage(RED + "<��> �κп� ���ڸ� �Է��� �ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							int num = Integer.parseInt(args[2]);
							API.setWarning(name, num);
							sender.sendMessage(GOLD + "���������� " + RED + name + GOLD + " ���� ��� ���� " + RED + num + GOLD + " ��ŭ �����Ͽ����ϴ�.");
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
		sender.sendMessage(YELLOW + " ---- " + GOLD + "��� " + YELLOW + "-- " + GOLD + "������ " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/���, /wa " + WHITE + "- ��� ��ɾ� ������ Ȯ���մϴ�.");
		sender.sendMessage(GOLD + "/��� <Ȯ��/v> " + WHITE + "- �� ��� ���� Ȯ���մϴ�.");
		sender.sendMessage(GOLD + "/��� <Ȯ��/v> <�г���> " + WHITE + "- �÷��̾��� ��� ���� Ȯ���մϴ�.");
		if (sender.isOp()) {
			sender.sendMessage(BLUE + "/��� <�ֱ�/g> <�г���> <��> " + WHITE + "- �÷��̾�� ��� �ݴϴ�.");
			sender.sendMessage(BLUE + "/��� <����/t> <�г���> <��> " + WHITE + "- �÷��̾�� ��� �����ϴ�.");
			sender.sendMessage(BLUE + "/��� <����/s> <�г���> <��> " + WHITE + "- �÷��̾��� ��� �����մϴ�.");
		}

		sender.sendMessage("");
		sender.sendMessage(YELLOW + "* ��� ���� 4 �̻��� �ڵ����� " + RED + "�� " + YELLOW + "�� �˴ϴ�.");
		sender.sendMessage(YELLOW + "* ��� ���� 5 �̻��� �ڵ����� " + RED + "������ �� " + YELLOW + "�� �˴ϴ�.");
		sender.sendMessage(YELLOW + "* ��� ���� 6 �̻��� �ڵ����� " + RED + "������ ���� �� " + YELLOW + "�� �˴ϴ�.");
	}
}
