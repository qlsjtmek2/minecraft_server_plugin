package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Command_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATcommandview implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATcommandview(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(args.length == 1)
			{
				Data_Command_Methods cmds = plugin.commands;
				if(!cmds.equalsCommands(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "등록된 명령어가 없습니다.");
					return true;
				}
				String scripts[] = cmds.getScripts(args[0]);
				sender.sendMessage(ChatColor.GREEN + "-- /" + args[0] + " - " + cmds.getOverride(args[0]) + " --");
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
