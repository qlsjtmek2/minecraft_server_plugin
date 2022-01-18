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
			if (commandLabel.equalsIgnoreCase("유저가이드") || commandLabel.equalsIgnoreCase("가이드") || commandLabel.equalsIgnoreCase("userguide")
			 || commandLabel.equalsIgnoreCase("guide") || commandLabel.equalsIgnoreCase("ug")) {
				if (args.length == 0) {
					HelpMessage(sender);
					return false;
				} else {
					if (args[0].equalsIgnoreCase("목록") || args[0].equalsIgnoreCase("list")) {
						if (API.getUserGuide() == null || API.getUserGuide().isEmpty()) {
							sender.sendMessage(RED + "현재 유저가이드가 존재하지 않습니다.");
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
								sender.sendMessage(GOLD + "다음 목록을 보시려면 " + RED + "/가이드 목록 " + (k + 1) + " " + GOLD + "을/를 입력해주세요.");
				            }
				        } return false;
					}
					
					else if (args[0].equalsIgnoreCase("추가") && args.length == 2 || args[0].equalsIgnoreCase("g") && args.length == 2) {
						if (!sender.isOp()) {
							sender.sendMessage(RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						String name = args[1];
						API.addUserGuide(name);
						sender.sendMessage(GOLD + "성공적으로 " + RED + name + GOLD + " 님을 유저가이드로 임명하셨습니다.");
						
						Player p = API.getOnorOffLine(name);
						if (p != null) p.sendMessage("§f[§a안내§f] §6축하합니다! 당신은 §e유저가이드 §6로 임명되셨습니다.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("제거") && args.length == 2 || args[0].equalsIgnoreCase("r") && args.length == 2) {
						if (!sender.isOp()) {
							sender.sendMessage(RED + "당신은 권한이 없습니다.");
							return false;
						}
						
						String name = args[1];
						if (!API.isUserGuide(name)) {
							sender.sendMessage(RED + "그 플레이어는 유저가이드가 아닙니다.");
							return false;
						}
						
						API.removeUserGuide(name);
						sender.sendMessage(GOLD + "성공적으로 " + RED + name + GOLD + " 님을 유저가이드에서 해임하였습니다.");
						
						Player p = API.getOnorOffLine(name);
						if (p != null) p.sendMessage("§f[§a안내§f] §c당신은 §e유저가이드 §c에서 해임되셨습니다.");
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
		sender.sendMessage(YELLOW + " ---- " + GOLD + "유저가이드 " + YELLOW + "-- " + GOLD + "페이지 " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/유저가이드, /가이드, /UserGuide, /Guide, /UG " + WHITE + "- 유저 가이드 명령어를 확인합니다.");
		sender.sendMessage(GOLD + "/가이드 <목록/List> " + WHITE + "- 내 경고 수를 확인합니다.");
		if (sender.isOp()) {
			sender.sendMessage(BLUE + "/가이드 <추가/g> <닉네임> " + WHITE + "- 플레이어에게 가이드 권한을 부여합니다.");
			sender.sendMessage(BLUE + "/가이드 <제거/r> <닉네임> " + WHITE + "- 플레이어에게 가이드 권한을 뺐습니다.");
		}

		sender.sendMessage("");
		sender.sendMessage(YELLOW + "* 가이드는 채팅 금지 상황에 채팅을 칠 수 있습니다.");
		sender.sendMessage(RED + "* 가이드는 무조건 칭호가 §f[§2유저§a가이드§f] §c로 고정됩니다.");
	}
}
