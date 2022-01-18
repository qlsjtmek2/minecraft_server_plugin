package me.espoo.rpg.guild;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.Method;
import me.espoo.rpg.main;
import us.talabrek.ultimateskyblock.PlayerInfo;

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
					
					if (args.length == 0) {
						if (!Guild.players.containsKey(p.getName())) {
							GuildGUI.noGuildGUI(p);
							return false;
						}
						
						Guild guild = Guild.players.get(p.getName());
						if (guild.getManager().equalsIgnoreCase(p.getName())) {
							GuildGUI.MasterGuildGUI(p);
						}
						
						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("����")) {
							GuildGUI.userGuildGUI(p);
						}
						
						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("��ȸ��")) {
							GuildGUI.MemberGuildGUI(p);
						}
						
						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("�����̳�")) {
							GuildGUI.DesignerGuildGUI(p);
						}
						
						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("����")) {
							GuildGUI.StepGuildGUI(p);
						}
					}
					
					else if (args.length == 1 && p.isOp()) {
						if (args[1].equalsIgnoreCase("reload")) {
							main.createGuildMoney = Integer.parseInt(GuildConfig.getGuildString("��� â�� ���"));
							main.guildConfig = GuildConfig.getGuildList("��� ���׷��̵�");
						} else {
							PlayerInfo pi = Method.readPlayerFile(args[1]);
							Location lo = pi.getIslandLocation();
							lo.setWorld(Bukkit.getWorld("world_guild"));
							p.teleport(lo);
						}
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
				
				if (args.length == 0) {
					if (!Guild.players.containsKey(p.getName())) {
						GuildGUI.noGuildGUI(p);
						return false;
					}
					
					Guild guild = Guild.players.get(p.getName());
					if (guild.getManager().equalsIgnoreCase(p.getName())) {
						GuildGUI.MasterGuildGUI(p);
					}
					
					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("����")) {
						GuildGUI.userGuildGUI(p);
					}
					
					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("��ȸ��")) {
						GuildGUI.MemberGuildGUI(p);
					}
					
					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("�����̳�")) {
						GuildGUI.DesignerGuildGUI(p);
					}
					
					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("����")) {
						GuildGUI.StepGuildGUI(p);
					}
				}
				
				else if (args.length == 1 && p.isOp()) {
					if (args[1].equalsIgnoreCase("reload")) {
						main.createGuildMoney = Integer.parseInt(GuildConfig.getGuildString("��� â�� ���"));
						main.guildConfig = GuildConfig.getGuildList("��� ���׷��̵�");
					} else {
						PlayerInfo pi = Method.readPlayerFile(args[1]);
						Location lo = pi.getIslandLocation();
						lo.setWorld(Bukkit.getWorld("world_guild"));
						p.teleport(lo);
					}
				}
				
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		} return false;
	}
}
