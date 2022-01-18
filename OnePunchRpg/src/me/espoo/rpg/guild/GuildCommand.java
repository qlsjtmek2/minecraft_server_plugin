package me.espoo.rpg.guild;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.main;

public class GuildCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public GuildCommand(main instance)
	{
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("���") || commandLabel.equalsIgnoreCase("expg") || commandLabel.equalsIgnoreCase("guild")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (GuildAPI.getJoinGuild(p) == null) {
						GuildGUI.noGuildGUI(p);
					}
					
					else if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
						GuildGUI.MasterGuildGUI(p);
					}
					
					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("����")) {
						GuildGUI.userGuildGUI(p);
					}
					
					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("��ȸ��")) {
						GuildGUI.MemberGuildGUI(p);
					}
					
					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("�����̳�")) {
						GuildGUI.DesignerGuildGUI(p);
					}
					
					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("����")) {
						GuildGUI.StepGuildGUI(p);
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
				
				if (GuildAPI.getJoinGuild(p) == null) {
					GuildGUI.noGuildGUI(p);
				}
				
				else if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
					GuildGUI.MasterGuildGUI(p);
				}
				
				else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("����")) {
					GuildGUI.userGuildGUI(p);
				}
				
				else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("��ȸ��")) {
					GuildGUI.MemberGuildGUI(p);
				}
				
				else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("�����̳�")) {
					GuildGUI.DesignerGuildGUI(p);
				}
				
				else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("����")) {
					GuildGUI.StepGuildGUI(p);
				}
				
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		} return false;
	}
}
