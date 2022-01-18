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
					sender.sendMessage(ChatColor.RED + "��ϵ� ��ɾ �����ϴ�.");
					return true;
				}
				if(!(args[1].equals("true") || args[1].equals("false")))
				{
					sender.sendMessage(ChatColor.RED + "true // false�� �ùٸ��� �ʽ��ϴ�.");
					return true;
				}
				cmds.setOverride(args[0], Boolean.parseBoolean(args[1]));
				sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " ��ɾ��� ����Ⱑ " + ChatColor.YELLOW + args[1] +  ChatColor.GREEN + "�� �����Ǿ����ϴ�.");
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
