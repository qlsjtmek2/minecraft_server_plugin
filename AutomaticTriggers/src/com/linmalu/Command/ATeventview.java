package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Event_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATeventview implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATeventview(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(args.length == 1)
			{
				Data_Event_Methods events = plugin.events;
				if(!events.equalsEvents(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "등록된 이벤트가 없습니다.");
					return true;
				}
				String scripts[] = events.getScripts(args[0]);
				sender.sendMessage(ChatColor.GREEN + "-- " + args[0] + " --");
				for(int i = 0; i < scripts.length; i++)
					sender.sendMessage(ChatColor.GREEN + (i + 1 + ". ") + ChatColor.YELLOW + scripts[i]);
				return true;
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
			return true;
		}
		return false;
	}
}
