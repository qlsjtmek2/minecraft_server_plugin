package me.espoo.oneman;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAll implements CommandExecutor {
	private final main plugin;

	public GiveAll(main main)
	{
		this.plugin = main;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
			return false;
		}
		Player player = (Player)sender;
		try
		{
			if (label.equalsIgnoreCase("����"))
			{
				if (player.isOp())
				{
					if (player.getItemInHand().getType() == Material.AIR)
					{
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] �տ� �������� ������� �ʽ��ϴ�");
						return false;
					}

					ItemStack item = player.getItemInHand().clone();
					int amount = Integer.parseInt(args[0]);
					if (player.getItemInHand().getAmount() == 1)
					{
						if (amount <= 63)
						{
							if (amount > 0)
							{
								item.setAmount(amount);

								for (Player all : Bukkit.getServer().getOnlinePlayers())
								{
									all.getInventory().addItem(new ItemStack[] { item });
								}
								if (!item.getItemMeta().hasDisplayName())
								{
									Bukkit.getServer().broadcastMessage("");
									Bukkit.getServer().broadcastMessage("��f[��4�˸���f] ��c" + item.getType() + "��6��(��) ��c" + player.getName() + "��6���� ��ο��� ��c" + amount + "��6�� �����Ͽ����ϴ�.");
									Bukkit.getServer().broadcastMessage("");
									return false;
								}
                
								Bukkit.getServer().broadcastMessage("");
								Bukkit.getServer().broadcastMessage("��f[��4�˸���f] ��c" + item.getItemMeta().getDisplayName() + "��6��(��) ��c" + player.getName() + "��6���� ��ο��� ��c" + amount + "��6�� �����Ͽ����ϴ�.");
								Bukkit.getServer().broadcastMessage("");
								return false;
							}

							player.sendMessage("");
							player.sendMessage("");
							player.sendMessage(main.prefix + ChatColor.RED + "������ 0 ������ �� �����ϴ�!");
							return false;
						}
						
						player.sendMessage("");
						player.sendMessage("");
						player.sendMessage(main.prefix + ChatColor.RED + "������ 63�� �ʰ��� �� �����ϴ�!");
						return false;
					}

					player.sendMessage(main.prefix + ChatColor.RED + "�������� 1������־���մϴ�.");
				}
				else
				{
					player.sendMessage("");
					player.sendMessage("");
					player.sendMessage(main.prefix + ChatColor.RED + "����� ������ �����ϴ�.");
					return false;
				}
			}
		}
		catch (Exception e)
		{
			player.sendMessage("��f[��4�˸���f]" + ChatColor.GREEN + "����: /���� ��������");
			return false;
		}
		return true;
	}
}