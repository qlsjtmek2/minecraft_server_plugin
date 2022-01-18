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
				sender.sendMessage(ChatColor.RED + "�ֿܼ����� ����� �� �����ϴ�.");
				return true;
			}
			if(args.length != 0)
				return false;
			plugin.listener.setListener(sender.getName(), "", "", 0, "areaset", true);
			sender.sendMessage(ChatColor.GREEN + "���ϴ� ������ ������ �������� " + ChatColor.YELLOW + "/ara �����̸� ��ũ��Ʈ");
		}
		else
			sender.sendMessage(ChatColor.RED + "������ �����ϴ�.");
		return true;
	}
}
