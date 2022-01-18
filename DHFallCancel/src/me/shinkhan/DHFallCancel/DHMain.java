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
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
		getConfig().options().copyDefaults(true);
		reloadConfig();
        saveConfig();
	}
	
	public void onDisable()
	{
		saveConfig();
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("����"))
		{
			if(args.length == 0)
			{
				if(player.isOp() == true) 
				{
					sender.sendMessage("��a���7��                                                  ��a���7��");
					sender.sendMessage("��7���c�� ��e----- ��6DHFallCancel ��e-- ��6���� ��e----- ��7���c��");
					sender.sendMessage("");
					sender.sendMessage("��6/���� ��f- DHFallCancel ��ɾ� ������ ���ϴ�.");
					sender.sendMessage("��6/���� Ȱ��ȭ ��f- ���� �������� �����մϴ�.");
					sender.sendMessage("��6/���� ��Ȱ��ȭ ��f- ���� ������ ������ ���۴ϴ�.");
					sender.sendMessage("��6/���� �޹̼� Ȱ��ȭ ��f- �޹̼��� �־�߸� ������ �����ϰ� �����մϴ�.");
					sender.sendMessage("��6/���� �޹̼� ��Ȱ��ȭ ��f- �޹̼��� ��� ������ �����ϰ� �����մϴ�.");
					sender.sendMessage("��6/���� reload ��f- config�� ���ε� �մϴ�.");
					sender.sendMessage("");
					sender.sendMessage(" ��e�⺻��: ��f���� Ȱ��ȭ, ���� �޹̼� ��Ȱ��ȭ");
					sender.sendMessage("��e----------------------------------------------------");
				}
				
				else
				{
					sender.sendMessage("��f[��cD��aH��f] ��c����� ������ �����ϴ�.");
				}
			}
				
			else if(args[0].equalsIgnoreCase("reload"))
			{
				if(player.isOp() == true) 
				{
					sender.sendMessage("��f[��cD��aH��f] ��aconfig ������ ���λ����Ǿ����ϴ�");
					getConfig().options().copyDefaults(true);
					reloadConfig();
			        saveConfig();
				}
				
				else
				{
					sender.sendMessage("��f[��cD��aH��f] ��c����� ������ �����ϴ�.");
				}
			}
			
			else if(args[0].equalsIgnoreCase("Ȱ��ȭ"))
			{
				if(player.isOp() == true) 
				{
					Bukkit.broadcastMessage("��f[��cD��aH��f] ��a�������� ��� ���絥������ ���� �ʽ��ϴ�.");
					getConfig().set("FallDamage",true);
			        saveConfig();
				}
				
				else
				{
					sender.sendMessage("��f[��cD��aH��f] ��c����� ������ �����ϴ�.");
				}
			}
			
			else if(args[0].equalsIgnoreCase("��Ȱ��ȭ"))
			{
				if(player.isOp() == true) 
				{
					Bukkit.broadcastMessage("��f[��cD��aH��f] ��c�������� ��� ���絥������ �޽��ϴ�.");
					getConfig().set("FallDamage",false);
			        saveConfig();
				}
				
				else
				{
					sender.sendMessage("��f[��cD��aH��f] ��c����� ������ �����ϴ�.");
				}
			}
			
			else if(args[0].equalsIgnoreCase("�޹̼�"))
			{
				if(args[1].equalsIgnoreCase("Ȱ��ȭ"))
				{
					if(player.isOp() == true) 
					{
						sender.sendMessage("��f[��cD��aH��f] ��a�޹̼� ��尡 �־�߸� ������ �ǰ� �����߽��ϴ�.");
						getConfig().set("UserPermission",true);
				        saveConfig();
					}
					
					else
					{
						sender.sendMessage("��f[��cD��aH��f] ��c����� ������ �����ϴ�.");
					}
				}
				
				else if(args[1].equalsIgnoreCase("��Ȱ��ȭ"))
				{
					
					if(player.isOp() == true) 
					{
						sender.sendMessage("��f[��cD��aH��f] ��a�޹̼� ��尡 ��� ������ �����ϰ� �����߽��ϴ�.");
						getConfig().set("UserPermission",false);
				        saveConfig();
					}
					
					else
					{
						sender.sendMessage("��f[��cD��aH��f] ��c����� ������ �����ϴ�.");
					}
				}
			}
			
			else
			{
				sender.sendMessage("��f[��cD��aH��f] ��c���ڰ��� ����� �Է����ּ���.");
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
