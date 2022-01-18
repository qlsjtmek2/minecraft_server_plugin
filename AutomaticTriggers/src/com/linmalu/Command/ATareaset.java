package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.linmalu.Main.AutomaticTriggers;

public class ATareaset implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATareaset(AutomaticTriggers plugin)
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
			if(args.length != 0)
				return false;
			plugin.listener.setListener(sender.getName(), "", "", 0, "areaset", true);
			sender.sendMessage(ChatColor.GREEN + "원하는 공간를 도구로 설정한후 " + ChatColor.YELLOW + "/ara 공간이름 스크립트");
		}
		else
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
		return true;
	}
}
