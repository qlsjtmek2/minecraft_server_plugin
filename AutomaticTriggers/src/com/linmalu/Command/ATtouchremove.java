package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.linmalu.Main.AutomaticTriggers;

public class ATtouchremove implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATtouchremove(AutomaticTriggers plugin)
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
			if (args.length == 1)
			{
				int line = 0;
				try{
					line = Integer.parseInt(args[0]);
				}catch(Exception e){
					sender.sendMessage(ChatColor.RED + "�ٹ�ȣ�� ���ڰ� �ƴմϴ�.");
					return  true;
				}
				if(line < 1)
				{
					sender.sendMessage(ChatColor.RED + "�ٹ�ȣ�� 1���� �۽��ϴ�.");
					return true;
				}
				plugin.listener.setListener(sender.getName(), "", "", line, "touchremove", true);
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