package me.shinkhan.DHFallCancel;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class DHMain extends JavaPlugin
{
	public DHMain plugin;
	{
		this.plugin = plugin;
	}
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	
	public void onEnable()
	{
		initEvents();
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
		getConfig().options().copyDefaults(true);
		reloadConfig();
        saveConfig();
	}
	
	public void onDisable()
	{
		saveConfig();
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("낙뎀"))
		{
			if(args.length == 0)
			{
				if(player.isOp() == true) 
				{
					sender.sendMessage("§a■§7■                                                  §a■§7■");
					sender.sendMessage("§7■§c■ §e----- §6DHFallCancel §e-- §6도움말 §e----- §7■§c■");
					sender.sendMessage("");
					sender.sendMessage("§6/낙뎀 §f- DHFallCancel 명령어 도움말을 봅니다.");
					sender.sendMessage("§6/낙뎀 활성화 §f- 낙사 데미지를 방지합니다.");
					sender.sendMessage("§6/낙뎀 비활성화 §f- 낙사 데미지 방지를 없앱니다.");
					sender.sendMessage("§6/낙뎀 펄미션 활성화 §f- 펄미션이 있어야만 방지가 가능하게 설정합니다.");
					sender.sendMessage("§6/낙뎀 펄미션 비활성화 §f- 펄미션이 없어도 방지가 가능하게 설정합니다.");
					sender.sendMessage("§6/낙뎀 reload §f- config를 리로딩 합니다.");
					sender.sendMessage("");
					sender.sendMessage(" §e기본값: §f낙뎀 활성화, 낙뎀 펄미션 비활성화");
					sender.sendMessage("§e----------------------------------------------------");
				}
				
				else
				{
					sender.sendMessage("§f[§cD§aH§f] §c당신은 권한이 없습니다.");
				}
			}
				
			else if(args[0].equalsIgnoreCase("reload"))
			{
				if(player.isOp() == true) 
				{
					sender.sendMessage("§f[§cD§aH§f] §aconfig 파일이 새로생성되었습니다");
					getConfig().options().copyDefaults(true);
					reloadConfig();
			        saveConfig();
				}
				
				else
				{
					sender.sendMessage("§f[§cD§aH§f] §c당신은 권한이 없습니다.");
				}
			}
			
			else if(args[0].equalsIgnoreCase("활성화"))
			{
				if(player.isOp() == true) 
				{
					Bukkit.broadcastMessage("§f[§cD§aH§f] §a이제부터 모든 낙사데미지를 받지 않습니다.");
					getConfig().set("FallDamage",true);
			        saveConfig();
				}
				
				else
				{
					sender.sendMessage("§f[§cD§aH§f] §c당신은 권한이 없습니다.");
				}
			}
			
			else if(args[0].equalsIgnoreCase("비활성화"))
			{
				if(player.isOp() == true) 
				{
					Bukkit.broadcastMessage("§f[§cD§aH§f] §c이제부터 모든 낙사데미지를 받습니다.");
					getConfig().set("FallDamage",false);
			        saveConfig();
				}
				
				else
				{
					sender.sendMessage("§f[§cD§aH§f] §c당신은 권한이 없습니다.");
				}
			}
			
			else if(args[0].equalsIgnoreCase("펄미션"))
			{
				if(args[1].equalsIgnoreCase("활성화"))
				{
					if(player.isOp() == true) 
					{
						sender.sendMessage("§f[§cD§aH§f] §a펄미션 노드가 있어야만 방지가 되게 설정했습니다.");
						getConfig().set("UserPermission",true);
				        saveConfig();
					}
					
					else
					{
						sender.sendMessage("§f[§cD§aH§f] §c당신은 권한이 없습니다.");
					}
				}
				
				else if(args[1].equalsIgnoreCase("비활성화"))
				{
					
					if(player.isOp() == true) 
					{
						sender.sendMessage("§f[§cD§aH§f] §a펄미션 노드가 없어도 방지가 가능하게 설정했습니다.");
						getConfig().set("UserPermission",false);
				        saveConfig();
					}
					
					else
					{
						sender.sendMessage("§f[§cD§aH§f] §c당신은 권한이 없습니다.");
					}
				}
			}
			
			else
			{
				sender.sendMessage("§f[§cD§aH§f] §c인자값을 제대로 입력해주세요.");
			}
		}
		return false;
		
	}
	
	public void initEvents()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new DHEvent(this), this);
	}
	
	
}
