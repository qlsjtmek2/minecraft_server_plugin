package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.linmalu.Data.Data_Area_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATareaview implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATareaview(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "콘솔에서는 사용할 수 없습니다.");
				return true;
			}
			if(args.length == 1)
			{
				Data_Area_Methods areas = plugin.areas;
				String worldName = plugin.getServer().getPlayerExact(sender.getName()).getWorld().getName();
				if(!areas.equalsAreas(worldName, args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "등록된 공간이 없습니다.");
					return true;
				}
				String scripts[] = areas.getScripts(worldName, args[0]);
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
