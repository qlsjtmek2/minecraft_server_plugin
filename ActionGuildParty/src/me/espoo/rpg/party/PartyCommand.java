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
			if (commandLabel.equalsIgnoreCase("��Ƽ") || commandLabel.equalsIgnoreCase("expp") || commandLabel.equalsIgnoreCase("party")) {
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
					sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
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
				sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("cm") || commandLabel.equalsIgnoreCase("chatmode") || commandLabel.equalsIgnoreCase("ä�ø��")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						String mode = GuildAPI.getPlayerChat(p);
						String str = Method.NextChatMode(p, mode);
						
						if (str == null) {
							p.sendMessage("��c���, ��Ƽ �� �� �ƹ� ���� �������� �ʾƼ� ä�� ��带 �����Ͻ� �� �����ϴ�.");
							return false;
						} else {
							GuildAPI.setPlayerChat(p, str);
							p.sendMessage("��6ä�� ��带 ��c" + str + " ��6Ÿ������ �����Ͽ����ϴ�.");
							return false;
						}
					} else {
						sender.sendMessage("��6/ä�ø��, /chatmode, /cm ��f- ä�� ��带 �����մϴ�. (��ü, ���, ��Ƽ)");
						return false;
					}
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				sender.sendMessage("��6/ä�ø��, /chatmode, /cm ��f- ä�� ��带 �����մϴ�. (��ü, ���, ��Ƽ)");
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		} return false;
	}

}
