package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.linmalu.Data.Data_Area_Methods;
import com.linmalu.Listener.Listener_Data;
import com.linmalu.Main.AutomaticTriggers;

public class ATarea implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATarea(AutomaticTriggers plugin)
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
			if(args.length > 1)
			{
				if(plugin.listener.equalsListener(sender.getName()) && plugin.listener.containsLocation(sender.getName()))
				{
					Data_Area_Methods areas = plugin.areas;
					Listener_Data data = plugin.listener.getListener(sender.getName());
					plugin.listener.removeListener(sender.getName());
					String script = plugin.ScriptLine(args, 1);
					areas.setAreas(data.worldName, args[0] + "," + data.leftLocation + "," + data.rightLocation, script);
					sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " 구역이 생성되었습니다.");
				}
				else
				{
					Data_Area_Methods areas = plugin.areas;
					String worldName = plugin.getServer().getPlayerExact(sender.getName()).getWorld().getName();
					if(!areas.equalsAreas(worldName, args[0], 0))
					{
						sender.sendMessage(ChatColor.RED + "등록된 공간이 없습니다.");
						return true;
					}
					if(plugin.scriptCommands.EqualScriptCommand(sender, args, "area", 0))
						return true;
					String script = plugin.ScriptLine(args, 1);
					String location = areas.getAreaName(worldName, args[0]);
					areas.setAreas(worldName, location, script);
					sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " 공간에 내용이 추가되었습니다.");
				}
				return true;
			}
			return false;
		}else{
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
			return true;
		}
	}
}
