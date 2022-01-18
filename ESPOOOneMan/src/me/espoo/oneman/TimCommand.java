package me.espoo.oneman;

import me.espoo.oneman.main.EnchantmentResult;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.enchantments.Enchantment;

public class TimCommand implements CommandExecutor {
	private final main plugin;

	public TimCommand(main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		if (!(sender instanceof Player)) {
			System.out.println(ChatColor.RED + "�ֿܼ��� ����� �Ұ����մϴ�.");
			return false;
		}
		Player Player = (Player)sender;

		if(commandLabel.equalsIgnoreCase("��þƮ���"))
		{
			if ((args.length > 0) && 
					(args[0].equalsIgnoreCase("����"))) {
				Player.sendMessage(ChatColor.GOLD + "---------------------------------------------");
				Player.sendMessage(ChatColor.GOLD + "/��þƮ��� ���� ��f- ������ ���ϴ�.");
				Player.sendMessage(ChatColor.GOLD + "/��þƮ��� ��ȣ ��f- ��ȣ ��þƮ ���ϴ�.");
				Player.sendMessage(ChatColor.GOLD + "/��þƮ��� Ȱ ��f- Ȱ ��þƮ�� ���ϴ�.");
				Player.sendMessage(ChatColor.GOLD + "/��þƮ��� Į ��f- ��ȣ ��þƮ ���ϴ�.");
				Player.sendMessage(ChatColor.GOLD + "/��þƮ��� ��� ��f- Ȱ ��þƮ�� ���ϴ�.");
				Player.sendMessage(ChatColor.GOLD + "/��þƮ��� ���� ��f- ������ ���ϴ�.");
				Player.sendMessage(ChatColor.GOLD + "---------------------------------------------");
			}

			if (args[0].equalsIgnoreCase("��ȣ")) {
				Player.sendMessage(ChatColor.AQUA + "��ȣ�迭 : 0 ��ȣ 1 ȭ����ȣ 2 ������ ���� 3 ���ߺ�ȣ 4 ���Ÿ� ��ȣ 5 ȣ�� 6 ģ���� 7 ����");
			}
			if (args[0].equalsIgnoreCase("Ȱ")) {
				Player.sendMessage(ChatColor.AQUA + "Ȱ�迭 : 48 �� 49 �о�� 50 ȭ�� 51 ����");
			}
			if (args[0].equalsIgnoreCase("Į")) {
				Player.sendMessage(ChatColor.AQUA + "Į�迭 : 16 ��ī�ο� 17  ��Ÿ 18 ȭ�� 19 ��ġ�� 20 ȭ��");
			}
			if (args[0].equalsIgnoreCase("���")) {
				Player.sendMessage(ChatColor.AQUA + "��� �迭 : 32 ȿ�� 33 ������ �ձ� 34 ������  35 ��� ");
			}
			if (args[0].equalsIgnoreCase("Į")) {
				Player.sendMessage(ChatColor.AQUA + "Į�迭 : 16 ��ī�ο� 17  ��Ÿ 18 ȭ�� 51 ����");
			}
			return true;
		}
		
		if(commandLabel.equalsIgnoreCase("��þƮ"))
		{
			if (Player.hasPermission("enchanter.enchant")) {
				if (args[0].equalsIgnoreCase("���")) {
					boolean dirty = (args.length <= 1) || (!args[1].equalsIgnoreCase("����"));
					for (Enchantment enchantment : Enchantment.values()) {
						this.plugin.enchantItem(Player, enchantment, dirty ? 32767 : enchantment.getMaxLevel());
					}
					sender.sendMessage(ChatColor.GOLD + "��� ��þƮ�� ��c32767 ��6���� �����߽��ϴ�.");
					return true;
				}
				int targetLevel = 1;
				if (args.length > 1) {
					try {
						targetLevel = Integer.valueOf(args[1]).intValue();
					} catch (NumberFormatException e) {
						if (args[1].equalsIgnoreCase("�ִ�")) {
							targetLevel = -1;
						}
					}
				}
				EnchantmentResult result = main.enchantItem(Player, args[0], targetLevel);
				switch (result) {
				case CANNOT_ENCHANT:
					sender.sendMessage("��c�װ��� ��þƮ �ڵ尡 �ƴմϴ�.");
					break;
				case INVALID_ID:
					sender.sendMessage("��c�ش� �����ۿ��� ��þƮ�� �Ұ����մϴ�.");
				}

	            if (!result.equals(EnchantmentResult.VICIOUS_STREAK_A_MILE_WIDE)) {
					Player.sendMessage(ChatColor.GOLD + "/��þƮ��� ���� ��f- ������ ���ϴ�.");
	            	return true;
	            }
	            
	            sender.sendMessage(ChatColor.YELLOW + "��6���������� ��c�����ۡ�6�� ��c��þƮ��6�Ǿ����ϴ�.");
	            return true;
			}
			return true;
		}
		return true;
	}
}