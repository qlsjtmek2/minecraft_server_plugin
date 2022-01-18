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
				sender.sendMessage(ChatColor.RED + "�ֿܼ����� ����� �� �����ϴ�.");
				return true;
			}
			if (args.length > 0)
			{
				if(plugin.scriptCommands.EqualScriptCommand(sender, args, "walk", 0))
					return true;
				plugin.listener.setListener(sender.getName(), args[0], plugin.ScriptLine(args, 0), 0, "walk", true);
				sender.sendMessage(ChatColor.GREEN + "���ϴ� ��ġ�� ������ ��Ŭ�� �ϼ���.");
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
