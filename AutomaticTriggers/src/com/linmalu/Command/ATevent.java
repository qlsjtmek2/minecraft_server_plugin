package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Event_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATevent implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATevent(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if (args.length > 1)
			{
				Data_Event_Methods events = plugin.events;
				if(!events.equalsEvents(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "등록된 이벤트가 없습니다.");
					return true;
				}
				if(plugin.scriptCommands.EqualScriptCommand(sender, args, "event", 0))
					return true;
				String script = plugin.ScriptLine(args, 1);
				events.setEvent(args[0], script);
				sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " 이벤트에 내용이 추가되었습니다.");
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
