package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Command_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATcommandadd implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATcommandadd(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if(args.length > 2)
			{
				Data_Command_Methods cmds = plugin.commands;
				if(!cmds.equalsCommands(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "��ϵ� ��ɾ �����ϴ�.");
					return true;
				}
				int line = 0;
				try{
					line = Integer.parseInt(args[1]);
				}catch(Exception e){
					sender.sendMessage(ChatColor.RED + "�ٹ�ȣ�� ���ڰ� �ƴմϴ�.");
					return  true;
				}
				if(line < 1)
				{
					sender.sendMessage(ChatColor.RED + "�ٹ�ȣ�� 1���� �۽��ϴ�.");
					return true;
				}
				if(!plugin.commands.equalsCommands(args[0], line))
				{
					sender.sendMessage(ChatColor.RED + "�ٹ�ȣ�� �����ϴ�.");
					return true;
				}
				if(plugin.scriptCommands.EqualScriptCommand(sender, args, "commandadd", line))
					return true;
				String script = plugin.ScriptLine(args, 2);
				cmds.addScripts(args[0], script, line);
				sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " ��ɾ��� " + line + "��°�ٿ� �߰��Ǿ����ϴ�.");
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
