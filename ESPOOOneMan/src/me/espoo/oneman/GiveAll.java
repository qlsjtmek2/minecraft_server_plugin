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
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] 버킷에선 실행이 불가능 합니다.");
			return false;
		}
		Player player = (Player)sender;
		try
		{
			if (label.equalsIgnoreCase("지급"))
			{
				if (player.isOp())
				{
					if (player.getItemInHand().getType() == Material.AIR)
					{
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] 손에 아이템을 들고있지 않습니다");
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
									Bukkit.getServer().broadcastMessage("§f[§4알림§f] §c" + item.getType() + "§6을(를) §c" + player.getName() + "§6님이 모두에게 §c" + amount + "§6개 지급하였습니다.");
									Bukkit.getServer().broadcastMessage("");
									return false;
								}
                
								Bukkit.getServer().broadcastMessage("");
								Bukkit.getServer().broadcastMessage("§f[§4알림§f] §c" + item.getItemMeta().getDisplayName() + "§6을(를) §c" + player.getName() + "§6님이 모두에게 §c" + amount + "§6개 지급하였습니다.");
								Bukkit.getServer().broadcastMessage("");
								return false;
							}

							player.sendMessage("");
							player.sendMessage("");
							player.sendMessage(main.prefix + ChatColor.RED + "갯수는 0 이하일 수 없습니다!");
							return false;
						}
						
						player.sendMessage("");
						player.sendMessage("");
						player.sendMessage(main.prefix + ChatColor.RED + "갯수는 63를 초과할 수 없습니다!");
						return false;
					}

					player.sendMessage(main.prefix + ChatColor.RED + "아이템을 1개들고있어야합니다.");
				}
				else
				{
					player.sendMessage("");
					player.sendMessage("");
					player.sendMessage(main.prefix + ChatColor.RED + "당신은 권한이 없습니다.");
					return false;
				}
			}
		}
		catch (Exception e)
		{
			player.sendMessage("§f[§4알림§f]" + ChatColor.GREEN + "사용법: /지급 《수량》");
			return false;
		}
		return true;
	}
}