package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Event_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATeventremove implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATeventremove(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(args.length == 2)
			{
				Data_Event_Methods events = plugin.events;
				if(!events.equalsEvents(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "등록된 이벤트가 없습니다.");
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
				if(!events.equalsEvents(args[0], line))
				{
					sender.sendMessage(ChatColor.RED + "줄번호가 없습니다.");
					return true;
				}
				events.removeEvents(args[0], line);
				sender.sendMessage(ChatColor.YELLOW + args[0] + " 이벤트의 " + ChatColor.GREEN + line + "번째줄이 삭제되었습니다.");
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
