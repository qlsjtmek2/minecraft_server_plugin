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
			System.out.println(ChatColor.RED + "콘솔에서 사용은 불가능합니다.");
			return false;
		}
		Player Player = (Player)sender;

		if(commandLabel.equalsIgnoreCase("인첸트목록"))
		{
			if ((args.length > 0) && 
					(args[0].equalsIgnoreCase("도움말"))) {
				Player.sendMessage(ChatColor.GOLD + "---------------------------------------------");
				Player.sendMessage(ChatColor.GOLD + "/인첸트목록 도움말 §f- 도움말을 봅니다.");
				Player.sendMessage(ChatColor.GOLD + "/인첸트목록 보호 §f- 보호 인첸트 봅니다.");
				Player.sendMessage(ChatColor.GOLD + "/인첸트목록 활 §f- 활 인첸트를 봅니다.");
				Player.sendMessage(ChatColor.GOLD + "/인첸트목록 칼 §f- 보호 인첸트 봅니다.");
				Player.sendMessage(ChatColor.GOLD + "/인첸트목록 곡괭이 §f- 활 인첸트를 봅니다.");
				Player.sendMessage(ChatColor.GOLD + "/인첸트목록 버전 §f- 버전을 봅니다.");
				Player.sendMessage(ChatColor.GOLD + "---------------------------------------------");
			}

			if (args[0].equalsIgnoreCase("보호")) {
				Player.sendMessage(ChatColor.AQUA + "보호계열 : 0 보호 1 화염보호 2 가벼운 착지 3 폭발보호 4 원거리 보호 5 호흡 6 친수성 7 가시");
			}
			if (args[0].equalsIgnoreCase("활")) {
				Player.sendMessage(ChatColor.AQUA + "활계열 : 48 힘 49 밀어내기 50 화염 51 무한");
			}
			if (args[0].equalsIgnoreCase("칼")) {
				Player.sendMessage(ChatColor.AQUA + "칼계열 : 16 날카로움 17  강타 18 화염 19 밀치기 20 화염");
			}
			if (args[0].equalsIgnoreCase("곡괭이")) {
				Player.sendMessage(ChatColor.AQUA + "곡괭이 계열 : 32 효율 33 섬세한 손길 34 내구도  35 행운 ");
			}
			if (args[0].equalsIgnoreCase("칼")) {
				Player.sendMessage(ChatColor.AQUA + "칼계열 : 16 날카로움 17  강타 18 화염 51 무한");
			}
			return true;
		}
		
		if(commandLabel.equalsIgnoreCase("인첸트"))
		{
			if (Player.hasPermission("enchanter.enchant")) {
				if (args[0].equalsIgnoreCase("모두")) {
					boolean dirty = (args.length <= 1) || (!args[1].equalsIgnoreCase("정상"));
					for (Enchantment enchantment : Enchantment.values()) {
						this.plugin.enchantItem(Player, enchantment, dirty ? 32767 : enchantment.getMaxLevel());
					}
					sender.sendMessage(ChatColor.GOLD + "모든 인첸트를 §c32767 §6으로 설정했습니다.");
					return true;
				}
				int targetLevel = 1;
				if (args.length > 1) {
					try {
						targetLevel = Integer.valueOf(args[1]).intValue();
					} catch (NumberFormatException e) {
						if (args[1].equalsIgnoreCase("최대")) {
							targetLevel = -1;
						}
					}
				}
				EnchantmentResult result = main.enchantItem(Player, args[0], targetLevel);
				switch (result) {
				case CANNOT_ENCHANT:
					sender.sendMessage("§c그것은 인첸트 코드가 아닙니다.");
					break;
				case INVALID_ID:
					sender.sendMessage("§c해당 아이템에는 인첸트가 불가능합니다.");
				}

	            if (!result.equals(EnchantmentResult.VICIOUS_STREAK_A_MILE_WIDE)) {
					Player.sendMessage(ChatColor.GOLD + "/인첸트목록 도움말 §f- 도움말을 봅니다.");
	            	return true;
	            }
	            
	            sender.sendMessage(ChatColor.YELLOW + "§6성공적으로 §c아이템§6이 §c인첸트§6되었습니다.");
	            return true;
			}
			return true;
		}
		return true;
	}
}