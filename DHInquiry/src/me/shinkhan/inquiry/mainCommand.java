package me.shinkhan.inquiry;

import java.util.List;

import org.bukkit.Bukkit;
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
			if (commandLabel.equalsIgnoreCase("문의") || commandLabel.equalsIgnoreCase("q")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						HelpMessage(sender);
						return false;
					}
					
					else {
						if (args[0].equalsIgnoreCase("여부") || args[0].equalsIgnoreCase("is")) {
							String message = API.getInquiry(p.getName());
							if (message == null) {
								p.sendMessage("§a현재 대기중인 문의 신청이 없습니다.");
							} else {
								p.sendMessage("§b처리 예정인 문의: §f" + message);
							} return false;
						}
						
						else if (args[0].equalsIgnoreCase("확인") || args[0].equalsIgnoreCase("c")) {
							if (!p.isOp()) {
								p.sendMessage("§c당신은 권한이 없습니다.");
								return false;
							}
							
							int k = 1;
							if (args.length == 2) k = Integer.parseInt(args[1]);
							if (args.length > 2 || args.length == 0) {
								HelpMessage(sender);
								return false;
							}

							List<String> list = Config.getList("문의");
					        if (list == null || list.isEmpty() || k * 10 - 9 > list.size() || k == 0) {
					            sender.sendMessage(RED + "해당 목록은 존재하지 않습니다.");
					            return false;
					        }
					        
					        if (list.size() % 10 == 0) {
					            sender.sendMessage(RED + "" + list.size() + GOLD + " 개의 문의를 찾았습니다. " + RED  + k + GOLD + "/" + RED + list.size() / 10);
					        } else {
					            sender.sendMessage(RED + "" + list.size() + GOLD + " 개의 문의를 찾았습니다. " + RED  + k + GOLD + "/" + RED + (list.size() / 10 + 1));
					        }
					        
					        for (int j = (k - 1) * 10; j < k * 10; ++j) {
								String[] str = list.get(j).split(",");
								p.sendMessage("§6" + str[0] + ": §f" + str[1]);
								
					            if (list.size() == j + 1) {
					                break;
					            }
					            
					            if (k * 10 - 1 == j && list.size() > j + 1) {
									sender.sendMessage(GOLD + "다음 목록을 보시려면 " + RED + "/문의 확인 " + (k + 1) + " " + GOLD + "을/를 입력해주세요.");
					            }
					        } return false;
						}
						
						else if (args[0].equalsIgnoreCase("처리") && args.length == 2 || args[0].equalsIgnoreCase("h") && args.length == 2) {
							if (!p.isOp()) {
								p.sendMessage("§c당신은 권한이 없습니다.");
								return false;
							}
							
							String name = args[1];
							if (API.isInquiry(name)) {
								p.sendMessage("§6성공적으로 §c" + name + " §6님의 문의가 처리되었습니다.");
								p.sendMessage("§6내용: §c" + API.getInquiry(name));
								API.removeInquiry(name);
							} else {
								p.sendMessage("§c그 플레이어의 문의는 존재하지 않습니다.");
							} return false;
						}

						else if (args[0].equalsIgnoreCase("모두처리") || args[0].equalsIgnoreCase("ah")) {
							if (!p.isOp()) {
								p.sendMessage("§c당신은 권한이 없습니다.");
								return false;
							}
							
							if (Config.getList("문의") == null) {
								p.sendMessage("§c처리할 문의가 존재하지 않습니다.");
								return false;
							}
							
							API.clearInquiry();
							p.sendMessage("§6모든 문의가 정상적으로 §c처리§6되었습니다.");
							return false;
						}
						
						else {
							if (API.isInquiry(p.getName())) {
								p.sendMessage("§c당신은 이미 제출한 문의가 존재합니다.");
								return false;
							}
							
							String message = API.getFinalArg(args, 0);
							API.addInquiry(p.getName(), message);
							p.sendMessage("§6성공적으로 문의를 §c제출§6하셨습니다.");
							p.sendMessage("§6제출한 문의: §f" + message);
							
							for (Player pl : Bukkit.getOnlinePlayers()) {
								if (pl.isOp()) {
									pl.sendMessage("§6새로운 문의가 들어왔습니다. §e[ /문의 확인 ][ /q c ]");
								}
							} return false;
						}
					}
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				HelpMessage(sender);
				return false;
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		} return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage("§e ---- §6문의 §e-- §6페이지 §c1§6/§c1 §e----");
		sender.sendMessage("§6/문의, /q §f- 문의 명령어 도움말을 확인합니다.");
		sender.sendMessage("§6/문의 <문의 내용> §f- 문의를 접수합니다. (중복 불가능)");
		sender.sendMessage("§6/문의 <여부/is> §f- 문의가 처리 되었는지 아닌지 여부를 확인합니다.");
		if (sender.isOp()) {
			sender.sendMessage("§b/문의 <확인/c> §f- 문의 내용을 채팅으로 확인합니다.");
			sender.sendMessage("§b/문의 <처리/h> <닉네임> §f- 플레이어의 문의를 처리합니다.");
			sender.sendMessage("§b/문의 <모두처리/ah> §f- 모든 문의를 처리합니다.");
		}
		
		sender.sendMessage("");
		sender.sendMessage("§e * 몬의 내용에 비속어, 성적인 요소가 들어갈 시 처벌을 받으실 수 있습니다.");
	}
}
