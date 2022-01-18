package me.shinkhan.music;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public class Comand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static Map<String, SongPlayer> songMap = new HashMap<String, SongPlayer>();
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public Comand(main instance)
	{
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("musiccheck")) {
				sender.sendMessage("§6현재 이 서버에는 §cDHMusic " + main.var + "v §6플러그인이 적용되어 있습니다.");
				sender.sendMessage("§6제작자 :: §cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("§cDHMusic §6플러그인을 사용해주셔서 감사합니다.  §f- 제작자 shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6현재 이 서버에는 §cDHMusic " + main.var + "v §6플러그인이 적용되어 있습니다.");
			sender.sendMessage("§6제작자 :: §cshinkhan");
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("음악")) {
				if (args.length == 2) {
					if (sender.isOp()) {
						if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("모두")) {
							if (Method.getFileList().contains(args[1])) {
								Method.play(Bukkit.getOnlinePlayers(), args[1]);
								return false;
							} else {
								sender.sendMessage(RED + "잘못된 음악 파일 이름입니다.");
								sender.sendMessage(GOLD + "/음악 (all/모두/playername) <파일 이름> " + WHITE + "- 음악을 재생합니다.");
								return false;
							}
						}
						
						if (Method.getOnorOffLine(args[0]) == null) {
							sender.sendMessage(RED + "잘못된 명령어 이거나 잘못된 플레이어 이름입니다.");
							sender.sendMessage(GOLD + "/음악 (all/모두/playername) <파일 이름> " + WHITE + "- 음악을 재생합니다.");
							return false;
						}
						
						if (Method.getFileList().contains(args[1])) {
							Player pl = Method.getOnorOffLine(args[0]);
							Method.play(new Player[] { pl }, args[1]);
							return false;
						} else {
							sender.sendMessage(RED + "잘못된 음악 파일 이름입니다.");
							sender.sendMessage(GOLD + "/음악 (all/모두/playername) <파일 이름> " + WHITE + "- 음악을 재생합니다.");
							return false;
						}
					} else {
						sender.sendMessage(RED + "당신은 이 명령어를 실행할 권한이 없습니다.");
						return false;
					}
				}
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.openGUI(p, 1);
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				GUI.openGUI(p, 1);
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		} return false;
	}
}
