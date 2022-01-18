package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Command;
import com.linmalu.Data.Data_Command_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATcommand implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATcommand(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(args.length > 1)
			{
				if(plugin.scriptCommands.EqualScriptCommand(sender, args, "command", 0))
					return true;
				Data_Command_Methods cmds = plugin.commands;
				String script = plugin.ScriptLine(args, 1);
				if(!cmds.equalsCommands(args[0], 0))
				{
					cmds.putCommands(args[0], new Data_Command(script));
					sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " ��ɾ �����Ǿ����ϴ�.");
				}else{
					cmds.addScripts(args[0], script);
					sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " ��ɾ ������ �߰��Ǿ����ϴ�.");
				}
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
