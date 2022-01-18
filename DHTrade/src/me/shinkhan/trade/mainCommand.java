package me.shinkhan.trade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.shinkhan.trade.data.GameData;
import me.shinkhan.trade.data.InventoryData;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	private final main plugin;
	private final ChatColor YELLOW = ChatColor.YELLOW;
	private final ChatColor GOLD = ChatColor.GOLD;
	private final ChatColor WHITE = ChatColor.WHITE;
	private final ChatColor RED = ChatColor.RED;
	private final ChatColor GREEN = ChatColor.GREEN;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			if (label.equalsIgnoreCase("��ȯ") || label.equalsIgnoreCase("�ŷ�")) {
				if (sender instanceof Player) {
					GameData data = plugin.getGameData();
					Player player = (Player) sender;
					InventoryData id = data.getInventoryData(player);
					if ((args.length == 2) && (args[0].equals("��û"))) {
						Player target = Bukkit.getPlayerExact(args[1]);
						if (target != null) {
							if (player == target) {
								player.sendMessage(RED + "�ڽſ��Դ� �ŷ��� �� �� �����ϴ�.");
							} else if (id != null) {
								if ((id == data.getInventoryData(target)) && (id.isFirst(target))) {
									id.openInventory();
								} else if (id.isFirst(player)) {
									player.sendMessage(RED + "�̹� " + YELLOW + id.getName2() + RED + "�Կ��� �ŷ��� ��û�߽��ϴ�.");
								} else {
									player.sendMessage(RED + "�̹� " + YELLOW + id.getName1() + RED + "�Կ��� �ŷ���û�� �޾ҽ��ϴ�.");
								}
							} else if (data.getInventoryData(target) != null) {
								player.sendMessage(YELLOW + args[1] + RED + "���� �̹� �ŷ����Դϴ�.");
							} else if (!me.espoo.option.PlayerYml.getInfoBoolean(target, "��ȯ ��û")) {
								player.sendMessage(YELLOW + args[1] + RED + " ���� ��ȯ ��û�� ��Ȱ��ȭ �ϼ̽��ϴ�.");
							} else {
								player.sendMessage(GOLD + args[1] + GREEN + " �Կ��� �ŷ��� ��û�߽��ϴ�.");
								target.sendMessage(GOLD + player.getName() + GREEN + " �Կ��� �ŷ���û�� ���Խ��ϴ�.");
								target.sendMessage(GOLD + "/�ŷ� ���� " + WHITE + "- �ŷ� ��û�� �����մϴ�.");
								target.sendMessage(GOLD + "/�ŷ� ��� " + WHITE + "- �ŷ� ��û�� ��� �Ǵ� �����մϴ�.");
								data.addInventoryData(player, target);
							}
						} else {
							player.sendMessage(YELLOW + args[1] + RED + " ���� �������� �ʽ��ϴ�.");
						}
					} else if ((args.length == 1) && (args[0].equals("����"))) {
						if (id == null) {
							player.sendMessage(RED + "���� �ŷ� ��û�� �����ϴ�.");
						} else if (id.isFirst(player)) {
							player.sendMessage(RED + "�ŷ��� �������� �����ϴ�.");
						} else {
							id.openInventory();
						}

					} else if ((args.length == 1) && (args[0].equals("���"))) {
						if (id == null) {
							player.sendMessage(RED + "���� �ŷ� ��û�� �����ϴ�.");
						} else {
							player.sendMessage(GOLD + id.getName2() + GREEN + " �԰��� �ŷ��� ����߽��ϴ�.");
							Player target = id.isFirst(player) ? id.getPlayer2() : id.getPlayer1();
							if (target.isOnline()) {
								target.sendMessage(GOLD + player.getName() + GREEN + " ���� �ŷ��� ����߽��ϴ�.");
							}
							id.cancel();
						}
					} else {
						HelpMessage(sender);
						return false;
					}
				} else {
					sender.sendMessage("[���] �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			HelpMessage(sender);
			return false;
		} return false;
	}
	
	private void HelpMessage(CommandSender sender) {
		sender.sendMessage(YELLOW + " ---- " + GOLD + "�ŷ� " + YELLOW + "-- " + GOLD + "������ " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/�ŷ�, /��ȯ " + WHITE + "- �ŷ� ��ɾ� ������ Ȯ���մϴ�.");
		sender.sendMessage(GOLD + "/�ŷ� ��û <�÷��̾�> " + WHITE + "- �÷��̾�� �ŷ��� ��û�մϴ�.");
		sender.sendMessage(GOLD + "/�ŷ� ���� " + WHITE + "- �ŷ� ��û�� �����մϴ�.");
		sender.sendMessage(GOLD + "/�ŷ� ��� " + WHITE + "- �ŷ� ��û�� ��� �Ǵ� �����մϴ�.");
	}
}
