package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Event_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATeventdelete implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATeventdelete(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if (args.length == 1)
			{
				Data_Event_Methods events = plugin.events;
				if(!events.equalsEvents(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "��ϵ� �̺�Ʈ�� �����ϴ�.");
					return true;
				}
				events.deleteEvents(args[0]);
				sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " �̺�Ʈ�� �����Ǿ����ϴ�.");
				return true;
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "������ �����ϴ�.");
			return true;
		}
		return false;
	}
}
