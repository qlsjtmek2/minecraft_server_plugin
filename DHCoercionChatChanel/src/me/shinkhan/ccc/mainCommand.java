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
			if (commandLabel.equalsIgnoreCase("채팅소외") || commandLabel.equalsIgnoreCase("ccc")) {
				if (sender.isOp()) {
					if (args.length == 0) {
						HelpMessage(sender);
						return false;
					}
					
					if (args[0].equalsIgnoreCase("목록") || args[0].equalsIgnoreCase("list")) {
						List<String> list = API.getList();
						int k = 1;
						if (args.length == 2) k = Integer.parseInt(args[1]);
						if (args.length > 2 || args.length == 0) {
							HelpMessage(sender);
							return false;
						}
						
				        if (list == null || list.isEmpty() || k * 10 - 9 > list.size() || k == 0) {
				            sender.sendMessage(RED + "해당 목록은 존재하지 않습니다.");
				            return false;
				        }
				        
				        if (list.size() % 10 == 0) {
				            sender.sendMessage(RED + "" + list.size() + GOLD + " 명의 유저를 찾았습니다. " + RED  + k + GOLD + "/" + RED + list.size() / 10);
				        } else {
				            sender.sendMessage(RED + "" + list.size() + GOLD + " 명의 유저를 찾았습니다. " + RED  + k + GOLD + "/" + RED + (list.size() / 10 + 1));
				        }
				        
				        for (int j = (k - 1) * 10; j < k * 10; ++j) {
							sender.sendMessage(RED + "" + (j + 1) + ": " + GOLD + list.get(j));
				            if (list.size() == j + 1) {
				                break;
				            }
				            
				            if (k * 10 - 1 == j && list.size() > j + 1) {
								sender.sendMessage(GOLD + "다음 목록을 보시려면 " + RED + "/채팅소외 목록 " + (k + 1) + " " + GOLD + "을/를 입력해주세요.");
				            }
				        } return false;
					}
					
					if (args.length == 2) {
						if (args[0].equalsIgnoreCase("추가") || args[0].equalsIgnoreCase("add")) {
							API.addPlayer(args[1]);
							sender.sendMessage(GOLD + "성공적으로 " + RED + args[1] + GOLD + " 플레이어를 추가했습니다.");
							API.reloadConfig();
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("제거") || args[0].equalsIgnoreCase("remove")) {
							API.removePlayer(args[1]);
							sender.sendMessage(GOLD + "성공적으로 " + RED + args[1] + GOLD + " 플레이어를 제거했습니다.");
							API.reloadConfig();
							return false;
						}
					}
					
					else {
						HelpMessage(sender);
						return false;
					}
				} else {
					sender.sendMessage(RED + "당신은 권한이 없습니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender.isOp()) {
				HelpMessage(sender);
				return false;
			} else {
				sender.sendMessage(RED + "당신은 권한이 없습니다.");
				return false;
			}
		} return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage(YELLOW + " ---- " + GOLD + "채팅 소외 " + YELLOW + "-- " + GOLD + "페이지 " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/채팅소외, /ccc " + WHITE + "- 채팅소외 명령어 도움말을 확인합니다.");
		sender.sendMessage(GOLD + "/채팅소외 <목록/list> " + WHITE + "- 채팅이 소외된 플레이어들을 확인합니다.");
		sender.sendMessage(GOLD + "/채팅소외 <추가/add> <플레이어> " + WHITE + "- 소외할 플레이어를 추가합니다.");
		sender.sendMessage(GOLD + "/채팅소외 <제거/remove> <플레이어> " + WHITE + "- 소외할 플레이어를 제거합니다.");
		sender.sendMessage("");
		sender.sendMessage(YELLOW + " * 채팅소외는 특정 플레이어들끼리만 채팅하도록 소외시키는 것입니다.");
	}

}
