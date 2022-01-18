package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Data.Data_Event_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class ATeventadd implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATeventadd(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender.hasPermission("atriggers.admin") || sender.isOp() == true)
		{
			if (args.length > 2)
			{
				Data_Event_Methods events = plugin.events;
				if(!events.equalsEvents(args[0], 0))
				{
					sender.sendMessage(ChatColor.RED + "��ϵ� �̺�Ʈ�� �����ϴ�.");
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
				if(!events.equalsEvents(args[0], line))
				{
					sender.sendMessage(ChatColor.RED + "�ٹ�ȣ�� �����ϴ�.");
					return true;
				}
				if(plugin.scriptCommands.EqualScriptCommand(sender, args, "eventadd", line))
					return true;
				String script = plugin.ScriptLine(args, 2);
				events.addEvents(args[0], line, script);
				sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.GREEN + " �̺�Ʈ�� " + line + "��°�ٿ� �߰��Ǿ����ϴ�.");
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
