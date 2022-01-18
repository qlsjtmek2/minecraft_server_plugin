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
			if (commandLabel.equalsIgnoreCase("길드") || commandLabel.equalsIgnoreCase("expg") || commandLabel.equalsIgnoreCase("guild")) {
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
						
						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저")) {
							GuildGUI.userGuildGUI(p);
						}
						
						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원")) {
							GuildGUI.MemberGuildGUI(p);
						}
						
						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너")) {
							GuildGUI.DesignerGuildGUI(p);
						}
						
						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭")) {
							GuildGUI.StepGuildGUI(p);
						}
					}
					
					else if (args.length == 1 && p.isOp()) {
						if (args[1].equalsIgnoreCase("reload")) {
							main.createGuildMoney = Integer.parseInt(GuildConfig.getGuildString("길드 창설 비용"));
							main.guildConfig = GuildConfig.getGuildList("길드 업그레이드");
						} else {
							PlayerInfo pi = Method.readPlayerFile(args[1]);
							Location lo = pi.getIslandLocation();
							lo.setWorld(Bukkit.getWorld("world_guild"));
							p.teleport(lo);
						}
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
				
				if (args.length == 0) {
					if (!Guild.players.containsKey(p.getName())) {
						GuildGUI.noGuildGUI(p);
						return false;
					}
					
					Guild guild = Guild.players.get(p.getName());
					if (guild.getManager().equalsIgnoreCase(p.getName())) {
						GuildGUI.MasterGuildGUI(p);
					}
					
					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저")) {
						GuildGUI.userGuildGUI(p);
					}
					
					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원")) {
						GuildGUI.MemberGuildGUI(p);
					}
					
					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너")) {
						GuildGUI.DesignerGuildGUI(p);
					}
					
					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭")) {
						GuildGUI.StepGuildGUI(p);
					}
				}
				
				else if (args.length == 1 && p.isOp()) {
					if (args[1].equalsIgnoreCase("reload")) {
						main.createGuildMoney = Integer.parseInt(GuildConfig.getGuildString("길드 창설 비용"));
						main.guildConfig = GuildConfig.getGuildList("길드 업그레이드");
					} else {
						PlayerInfo pi = Method.readPlayerFile(args[1]);
						Location lo = pi.getIslandLocation();
						lo.setWorld(Bukkit.getWorld("world_guild"));
						p.teleport(lo);
					}
				}
				
				return false;
			} else {
				sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
		} return false;
	}
}
