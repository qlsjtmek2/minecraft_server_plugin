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
			if (commandLabel.equalsIgnoreCase("경고") || commandLabel.equalsIgnoreCase("wa")) {
					if (args.length == 0) {
						HelpMessage(sender);
						return false;
					} else {
						if (args[0].equalsIgnoreCase("확인") || args[0].equalsIgnoreCase("v")) {
							if (args.length == 1) {
								if (sender instanceof Player) {
									Player p = (Player) sender;
									p.sendMessage("§6당신의 경고 수는 §c" + API.getWarning(p.getName()) + " §6입니다.");
								} else {
									sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
								} return false;
							}
							
							else if (args.length == 2) {
								String name = args[1];
								if (!API.isFile(name)) {
									sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
									return false;
								}
								
								sender.sendMessage(RED + name + GOLD + " 님의 경고 수는 " + RED + API.getWarning(name) + GOLD + " 입니다.");
								return false;
							}
							
							else {
								HelpMessage(sender);
								return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("주기") && args.length == 3 || args[0].equalsIgnoreCase("g") && args.length == 3) {
							if (!sender.isOp()) {
								sender.sendMessage(RED + "당신은 권한이 없습니다.");
								return false;
							}
							
							String name = args[1];
							if (!API.isFile(name)) {
								sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							if (!API.isNumeric(args[2])) {
								sender.sendMessage(RED + "<수> 부분엔 숫자만 입력해 주시기 바랍니다.");
								return false;
							}
							
							int num = Integer.parseInt(args[2]);
							API.addWarning(name, num);
							sender.sendMessage(GOLD + "성공적으로 " + RED + name + GOLD + " 님의 경고 수를 " + RED + num + GOLD + " 만큼 추가하였습니다.");
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("뺏기") && args.length == 3 || args[0].equalsIgnoreCase("t") && args.length == 3) {
							if (!sender.isOp()) {
								sender.sendMessage(RED + "당신은 권한이 없습니다.");
								return false;
							}
							
							String name = args[1];
							if (!API.isFile(name)) {
								sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							if (!API.isNumeric(args[2])) {
								sender.sendMessage(RED + "<수> 부분엔 숫자만 입력해 주시기 바랍니다.");
								return false;
							}
							
							int num = Integer.parseInt(args[2]);
							API.subWarning(name, num);
							sender.sendMessage(GOLD + "성공적으로 " + RED + name + GOLD + " 님의 경고 수를 " + RED + num + GOLD + " 만큼 뺐습니다.");
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("설정") && args.length == 3 || args[0].equalsIgnoreCase("s") && args.length == 3) {
							if (!sender.isOp()) {
								sender.sendMessage(RED + "당신은 권한이 없습니다.");
								return false;
							}
							
							String name = args[1];
							if (!API.isFile(name)) {
								sender.sendMessage(RED + "그 플레이어는 존재하지 않습니다.");
								return false;
							}
							
							if (!API.isNumeric(args[2])) {
								sender.sendMessage(RED + "<수> 부분엔 숫자만 입력해 주시기 바랍니다.");
								return false;
							}
							
							int num = Integer.parseInt(args[2]);
							API.setWarning(name, num);
							sender.sendMessage(GOLD + "성공적으로 " + RED + name + GOLD + " 님의 경고 수를 " + RED + num + GOLD + " 만큼 설정하였습니다.");
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
		sender.sendMessage(YELLOW + " ---- " + GOLD + "경고 " + YELLOW + "-- " + GOLD + "페이지 " + RED + "1" + GOLD + "/" + RED + "1 " + YELLOW + "----");
		sender.sendMessage(GOLD + "/경고, /wa " + WHITE + "- 경고 명령어 도움말을 확인합니다.");
		sender.sendMessage(GOLD + "/경고 <확인/v> " + WHITE + "- 내 경고 수를 확인합니다.");
		sender.sendMessage(GOLD + "/경고 <확인/v> <닉네임> " + WHITE + "- 플레이어의 경고 수를 확인합니다.");
		if (sender.isOp()) {
			sender.sendMessage(BLUE + "/경고 <주기/g> <닉네임> <수> " + WHITE + "- 플레이어에게 경고를 줍니다.");
			sender.sendMessage(BLUE + "/경고 <뺏기/t> <닉네임> <수> " + WHITE + "- 플레이어에게 경고를 뺏습니다.");
			sender.sendMessage(BLUE + "/경고 <설정/s> <닉네임> <수> " + WHITE + "- 플레이어의 경고를 설정합니다.");
		}

		sender.sendMessage("");
		sender.sendMessage(YELLOW + "* 경고 수가 4 이상이 자동으로 " + RED + "밴 " + YELLOW + "이 됩니다.");
		sender.sendMessage(YELLOW + "* 경고 수가 5 이상이 자동으로 " + RED + "아이피 밴 " + YELLOW + "이 됩니다.");
		sender.sendMessage(YELLOW + "* 경고 수가 6 이상이 자동으로 " + RED + "아이피 광역 밴 " + YELLOW + "이 됩니다.");
	}
}
