package me.espoo.rpg.party;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.Method;
import me.espoo.rpg.main;
import me.espoo.rpg.guild.GuildAPI;

public class PartyCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public PartyCommand(main instance)
	{
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("파티") || commandLabel.equalsIgnoreCase("expp") || commandLabel.equalsIgnoreCase("party")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!Party.players.containsKey(p.getName())) {
						PartyGUI.noPartyGUI(p);
						return false;
					}
					
					Party party = Party.players.get(p.getName());
					if (party.getName().equalsIgnoreCase(p.getName())) {
						PartyGUI.MasterPartyGUI(p);
					} else {
						PartyGUI.userPartyGUI(p);
					}
					
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				
				if (!Party.players.containsKey(p.getName())) {
					PartyGUI.noPartyGUI(p);
					return false;
				}
				
				Party party = Party.players.get(p.getName());
				if (party.getName().equalsIgnoreCase(p.getName())) {
					PartyGUI.MasterPartyGUI(p);
				} else {
					PartyGUI.userPartyGUI(p);
				}
				
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("cm") || commandLabel.equalsIgnoreCase("chatmode") || commandLabel.equalsIgnoreCase("채팅모드")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						String mode = GuildAPI.getPlayerChat(p);
						String str = Method.NextChatMode(p, mode);
						
						if (str == null) {
							p.sendMessage("§c길드, 파티 둘 중 아무 곳도 속해있지 않아서 채팅 모드를 변경하실 수 없습니다.");
							return false;
						} else {
							GuildAPI.setPlayerChat(p, str);
							p.sendMessage("§6채팅 모드를 §c" + str + " §6타입으로 변경하였습니다.");
							return false;
						}
					} else {
						sender.sendMessage("§6/채팅모드, /chatmode, /cm §f- 채팅 모드를 변경합니다. (전체, 길드, 파티)");
						return false;
					}
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				sender.sendMessage("§6/채팅모드, /chatmode, /cm §f- 채팅 모드를 변경합니다. (전체, 길드, 파티)");
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		} return false;
	}

}
