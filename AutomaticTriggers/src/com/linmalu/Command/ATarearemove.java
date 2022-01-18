package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.linmalu.Data.Data_Area_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATarearemove implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATarearemove(AutomaticTriggers plugin)
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
			if(args.length == 2)
			{
				Data_Area_Methods areas = plugin.areas;
				String worldName = plugin.getServer().getPlayerExact(sender.getName()).getWorld().getName();
				if(!areas.equalsAreas(worldName, args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "등록된 공간이 없습니다.");
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
				if(!areas.equalsAreas(worldName, args[0], line))
				{
					sender.sendMessage(ChatColor.RED + "줄번호가 없습니다.");
					return true;
				}
				areas.removeAreas(worldName, args[0], line);
				sender.sendMessage(ChatColor.YELLOW + args[0] + " 공간의 " + ChatColor.GREEN + line + "번째줄이 삭제되었습니다.");
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
