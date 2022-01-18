package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.linmalu.Main.AutomaticTriggers;

public class ATtouchedit implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATtouchedit(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "콘솔에서는 사용할 수 없습니다.");
				return true;
			}
			if (args.length > 1)
			{
				int line = 0;
				try{
					line = Integer.parseInt(args[0]);
				}catch(Exception e){
					sender.sendMessage(ChatColor.RED + "줄번호가 숫자가 아닙니다.");
					return  true;
				}
				if(line < 1)
				{
					sender.sendMessage(ChatColor.RED + "줄번호가 1보다 작습니다.");
					return true;
				}
				if(plugin.scriptCommands.EqualScriptCommand(sender, args, "touchedit", line))
					return true;
				plugin.listener.setListener(sender.getName(), args[1], plugin.ScriptLine(args, 1), line, "touchedit", true);
				sender.sendMessage(ChatColor.GREEN + "원하는 위치를 도구로 우클릭 하세요.");
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
