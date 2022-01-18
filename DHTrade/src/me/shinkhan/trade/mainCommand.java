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
			if (label.equalsIgnoreCase("교환") || label.equalsIgnoreCase("거래")) {
				if (sender instanceof Player) {
					GameData data = plugin.getGameData();
					Player player = (Player) sender;
					InventoryData id = data.getInventoryData(player);
					if ((args.length == 2) && (args[0].equals("신청"))) {
						Player target = Bukkit.getPlayerExact(args[1]);
						if (target != null) {
							if (player == target) {
								player.sendMessage(RED + "자신에게는 거래를 할 수 없습니다.");
							} else if (id != null) {
								if ((id == data.getInventoryData(target)) && (id.isFirst(target))) {
									id.openInventory();
								} else if (id.isFirst(player)) {
									player.sendMessage(RED + "이미 " + YELLOW + id.getName2() + RED + "님에게 거래를 신청했습니다.");
								} else {
									player.sendMessage(RED + "이미 " + YELLOW + id.getName1() + RED + "님에게 거래신청을 받았습니다.");
								}
							} else if (data.getInventoryData(target) != null) {
								player.sendMessage(YELLOW + args[1] + RED + "님은 이미 거래중입니다.");
							} else if (!me.espoo.option.PlayerYml.getInfoBoolean(target, "교환 신청")) {
								player.sendMessage(YELLOW + args[1] + RED + " 님은 교환 신청을 비활성화 하셨습니다.");
							} else {
								player.sendMessage(GOLD + args[1] + GREEN + " 님에게 거래를 신청했습니다.");
								target.sendMessage(GOLD + player.getName() + GREEN + " 님에게 거래신청이 들어왔습니다.");
								target.sendMessage(GOLD + "/거래 수락 " + WHITE + "- 거래 신청을 수락합니다.");
								target.sendMessage(GOLD + "/거래 취소 " + WHITE + "- 거래 신청을 취소 또는 거절합니다.");
								data.addInventoryData(player, target);
							}
						} else {
							player.sendMessage(YELLOW + args[1] + RED + " 님은 존재하지 않습니다.");
						}
					} else if ((args.length == 1) && (args[0].equals("수락"))) {
						if (id == null) {
							player.sendMessage(RED + "받은 거래 요청이 없습니다.");
						} else if (id.isFirst(player)) {
							player.sendMessage(RED + "거래를 받은적이 없습니다.");
						} else {
							id.openInventory();
						}

					} else if ((args.length == 1) && (args[0].equals("취소"))) {
						if (id == null) {
							player.sendMessage(RED + "받은 거래 요청이 없습니다.");
						} else {
							player.sendMessage(GOLD + id.getName2() + GREEN + " 님과의 거래를 취소했습니다.");
							Player target = id.isFirst(player) ? id.getPlayer2() : id.getPlayer1();
							if (target.isOnline()) {
								target.sendMessage(GOLD + player.getName() + GREEN + " 님이 거래를 취소했습니다.");
							}
							id.cancel();
						}
					} else {
						HelpMessage(sender);
						return false;
					}
				} else {
					sender.sendMessage("[경고] 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			HelpMessage(sender);
			return false;
		} return false;
	}
	
	private void HelpMessage(CommandSender sender) {
		sender.sendMessage(YELLOW + " ---- " + GOLD + "거래 " + YELLOW + "-- " + GOLD + "페이지 " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/거래, /교환 " + WHITE + "- 거래 명령어 도움말을 확인합니다.");
		sender.sendMessage(GOLD + "/거래 신청 <플레이어> " + WHITE + "- 플레이어에게 거래를 신청합니다.");
		sender.sendMessage(GOLD + "/거래 수락 " + WHITE + "- 거래 신청을 수락합니다.");
		sender.sendMessage(GOLD + "/거래 취소 " + WHITE + "- 거래 신청을 취소 또는 거절합니다.");
	}
}
