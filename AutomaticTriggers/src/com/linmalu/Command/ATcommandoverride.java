package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Command_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATcommandoverride implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATcommandoverride(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(args.length == 2)
			{
				Data_Command_Methods cmds = plugin.commands;
				if(!cmds.equalsCommands(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "등록된 명령어가 없습니다.");
					return true;
				}
				if(!(args[1].equals("true") || args[1].equals("false")))
				{
					sender.sendMessage(ChatColor.RED + "true // false가 올바르지 않습니다.");
					return true;
				}
				cmds.setOverride(args[0], Boolean.parseBoolean(args[1]));
				sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " 명령어의 덮어쓰기가 " + ChatColor.YELLOW + args[1] +  ChatColor.GREEN + "로 설정되었습니다.");
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
