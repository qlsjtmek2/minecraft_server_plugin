package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.linmalu.Main.AutomaticTriggers;

public class ATwalk implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATwalk(AutomaticTriggers plugin)
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
			if (args.length > 0)
			{
				if(plugin.scriptCommands.EqualScriptCommand(sender, args, "walk", 0))
					return true;
				plugin.listener.setListener(sender.getName(), args[0], plugin.ScriptLine(args, 0), 0, "walk", true);
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
