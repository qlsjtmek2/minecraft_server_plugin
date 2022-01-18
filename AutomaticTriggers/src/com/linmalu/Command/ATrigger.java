package com.linmalu.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.linmalu.Main.AutomaticTriggers;

public class ATrigger implements CommandExecutor
{
	private AutomaticTriggers plugin;

	public ATrigger(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(!(sender.hasPermission("atriggers.admin") || sender.isOp() == true))
		{
			sender.sendMessage(ChatColor.RED + "������ �����ϴ�.");
			return true;
		}
		if(args.length == 0 || (args.length == 1 && args[0].equals("1")))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.GOLD + "  --  ������ 1 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atrigger setvar <�����̸�> <��>" + ChatColor.GRAY + " - ���� �� ����");
			sender.sendMessage(ChatColor.GOLD + "/atrigger getvar <�����̸�>" + ChatColor.GRAY + " - ���� �� ����");
			sender.sendMessage(ChatColor.GOLD + "/atrigger delvar <�����̸�>" + ChatColor.GRAY + " - ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atrigger listvar" + ChatColor.GRAY + " - ���� ��� ����");
			sender.sendMessage(ChatColor.GOLD + "/atrigger save" + ChatColor.GRAY + " - Ʈ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atrigger reload" + ChatColor.GRAY + " - Ʈ���� �ҷ�����");
			sender.sendMessage(ChatColor.GOLD + "/atrigger reloadconfig" + ChatColor.GRAY + " - config.yml �ҷ�����");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  ������");
		}
		else if(args.length == 1 && args[0].equals("2"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "������ 2 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atarea" + ChatColor.GRAY + " - ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atareaset" + ChatColor.GRAY + " - ���� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atareaadd" + ChatColor.GRAY + " - ���� ���� �߰�");
			sender.sendMessage(ChatColor.GOLD + "/atarearemove" + ChatColor.GRAY + " - ���� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atareaedit" + ChatColor.GRAY + " - ���� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atareaview" + ChatColor.GRAY + " - ���� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atareadelete" + ChatColor.GRAY + " - ���� ����");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  ������");
		}
		else if(args.length == 1 && args[0].equals("3"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "������ 3 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atcommand" + ChatColor.GRAY + " - ��ɾ� ����");
			sender.sendMessage(ChatColor.GOLD + "/atcommandoverride" + ChatColor.GRAY + " - ��ɾ� ����� ����");
			sender.sendMessage(ChatColor.GOLD + "/atcommandadd" + ChatColor.GRAY + " - ��ɾ� ���� �߰�");
			sender.sendMessage(ChatColor.GOLD + "/atcommandremove" + ChatColor.GRAY + " - ��ɾ� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atcommandedit" + ChatColor.GRAY + " - ��ɾ� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atcommandview" + ChatColor.GRAY + " - ��ɾ� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atcommanddelete" + ChatColor.GRAY + " - ��ɾ� ����");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  ������");
		}
		else if(args.length == 1 && args[0].equals("4"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "������ 4 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atevent" + ChatColor.GRAY + " - �̺�Ʈ ����");
			sender.sendMessage(ChatColor.GOLD + "/ateventadd" + ChatColor.GRAY + " - �̺�Ʈ ���� �߰�");
			sender.sendMessage(ChatColor.GOLD + "/ateventremove" + ChatColor.GRAY + " - �̺�Ʈ ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/ateventedit" + ChatColor.GRAY + " - �̺�Ʈ ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/ateventview" + ChatColor.GRAY + " - �̺�Ʈ ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/ateventdelete" + ChatColor.GRAY + " - �̺�Ʈ ����");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  ������");
		}
		else if(args.length == 1 && args[0].equals("5"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "������ 5 / 6");
			sender.sendMessage(ChatColor.GOLD + "/attouch" + ChatColor.GRAY + " - ��ġ ����");
			sender.sendMessage(ChatColor.GOLD + "/attouchadd" + ChatColor.GRAY + " - ��ġ ���� �߰�");
			sender.sendMessage(ChatColor.GOLD + "/attouchremove" + ChatColor.GRAY + " - ��ġ ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/attouchedit" + ChatColor.GRAY + " - ��ġ ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/attouchview" + ChatColor.GRAY + " - ��ġ ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/attouchdelete" + ChatColor.GRAY + " - ��ġ ����");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  ������");
		}
		else if(args.length == 1 && args[0].equals("6"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "������ 6 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atwalk" + ChatColor.GRAY + " - ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atwalkadd" + ChatColor.GRAY + " - ���� ���� �߰�");
			sender.sendMessage(ChatColor.GOLD + "/atwalkremove" + ChatColor.GRAY + " - ���� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atwalkedit" + ChatColor.GRAY + " - ���� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atwalkview" + ChatColor.GRAY + " - ���� ���� ����");
			sender.sendMessage(ChatColor.GOLD + "/atwalkdelete" + ChatColor.GRAY + " - ���� ����");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  ������");
		}
		else if(args.length == 1 && args[0].equals("save"))
		{
			plugin.config.SaveTriggers();
			sender.sendMessage(ChatColor.GREEN + "Ʈ���� ���� �Ϸ�");
		}
		else if(args.length == 1 && args[0].equals("reload"))
		{
			plugin.config.ReloadTriggers();
			sender.sendMessage(ChatColor.GREEN + "Ʈ���� �ҷ����� �Ϸ�");
		}
		else if(args.length == 1 && args[0].equals("reloadconfig"))
		{
			plugin.config.ReloadConfig();
			sender.sendMessage(ChatColor.GREEN + "config.yml �ҷ����� �Ϸ�");
		}
		else if(args.length == 3 && args[0].equals("setvar"))
		{
			plugin.scriptVariables.putScriptVariables(args[1], args[2]);
			sender.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " ���� ���� �� : " + ChatColor.WHITE + args[2]);
		}
		else if(args.length == 2 && args[0].equals("getvar"))
		{
			if(plugin.scriptVariables.equalsScriptVariables(args[1]))
				sender.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " ���� �� : " + ChatColor.WHITE + plugin.scriptVariables.getScriptVariables(args[1]));
			else
				sender.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " ������ �����ϴ�. ");
		}
		else if(args.length == 2 && args[0].equals("delvar"))
		{
			plugin.scriptVariables.removeScriptVariables(args[1]);
			sender.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " ������ �����߽��ϴ�.");
		}
		else if(args.length == 1 && args[0].equals("listvar"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "���� ���");
			String variables[] = plugin.scriptVariables.getKeyScriptVariables();
			for(int i = 0; i < variables.length; i++)
				sender.sendMessage(ChatColor.YELLOW + variables[i] + ChatColor.GREEN + " ���� �� : " + ChatColor.WHITE + plugin.scriptVariables.getScriptVariables(variables[i]));
		}
		else
			return false;
		return true;
	}
}
