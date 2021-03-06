package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Command_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATcommandedit implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATcommandedit(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(args.length > 2)
			{
				Data_Command_Methods cmds = plugin.commands;
				if(!cmds.equalsCommands(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "등록된 명령어가 없습니다.");
					return true;
				}
				int line = 0;
				try{
					line = Integer.parseInt(args[1]);
				}catch(Exception e){
					sender.sendMessage(ChatColor.RED + "줄번호가 올바르지 않습니다.");
					return  true;
				}
				if(line < 1)
				{
					sender.sendMessage(ChatColor.RED + "줄번호가 1보다 작습니다.");
					return true;
				}
				if(!plugin.commands.equalsCommands(args[0], line))
				{
					sender.sendMessage(ChatColor.RED + "줄번호가 없습니다.");
					return true;
				}
				if(plugin.scriptCommands.EqualScriptCommand(sender, args, "commandedit", line))
					return true;
				String script = plugin.ScriptLine(args, 2);
				cmds.editScripts(args[0], script, line);
				sender.sendMessage(ChatColor.YELLOW + args[0] + " 명령어의 " + ChatColor.GREEN + line + "번째줄이 변경되었습니다.");
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
