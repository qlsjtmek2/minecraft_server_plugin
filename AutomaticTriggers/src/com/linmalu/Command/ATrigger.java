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
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
			return true;
		}
		if(args.length == 0 || (args.length == 1 && args[0].equals("1")))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.GOLD + "  --  페이지 1 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atrigger setvar <변수이름> <값>" + ChatColor.GRAY + " - 변수 값 설정");
			sender.sendMessage(ChatColor.GOLD + "/atrigger getvar <변수이름>" + ChatColor.GRAY + " - 변수 값 보기");
			sender.sendMessage(ChatColor.GOLD + "/atrigger delvar <변수이름>" + ChatColor.GRAY + " - 변수 삭제");
			sender.sendMessage(ChatColor.GOLD + "/atrigger listvar" + ChatColor.GRAY + " - 변수 목록 보기");
			sender.sendMessage(ChatColor.GOLD + "/atrigger save" + ChatColor.GRAY + " - 트리거 저장");
			sender.sendMessage(ChatColor.GOLD + "/atrigger reload" + ChatColor.GRAY + " - 트리거 불러오기");
			sender.sendMessage(ChatColor.GOLD + "/atrigger reloadconfig" + ChatColor.GRAY + " - config.yml 불러오기");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  린마루");
		}
		else if(args.length == 1 && args[0].equals("2"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "페이지 2 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atarea" + ChatColor.GRAY + " - 공간 생성");
			sender.sendMessage(ChatColor.GOLD + "/atareaset" + ChatColor.GRAY + " - 공간 지역 설정");
			sender.sendMessage(ChatColor.GOLD + "/atareaadd" + ChatColor.GRAY + " - 공간 내용 추가");
			sender.sendMessage(ChatColor.GOLD + "/atarearemove" + ChatColor.GRAY + " - 공간 내용 삭제");
			sender.sendMessage(ChatColor.GOLD + "/atareaedit" + ChatColor.GRAY + " - 공간 내용 수정");
			sender.sendMessage(ChatColor.GOLD + "/atareaview" + ChatColor.GRAY + " - 공간 내용 보기");
			sender.sendMessage(ChatColor.GOLD + "/atareadelete" + ChatColor.GRAY + " - 공간 삭제");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  린마루");
		}
		else if(args.length == 1 && args[0].equals("3"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "페이지 3 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atcommand" + ChatColor.GRAY + " - 명령어 생성");
			sender.sendMessage(ChatColor.GOLD + "/atcommandoverride" + ChatColor.GRAY + " - 명령어 덮어쓰기 설정");
			sender.sendMessage(ChatColor.GOLD + "/atcommandadd" + ChatColor.GRAY + " - 명령어 내용 추가");
			sender.sendMessage(ChatColor.GOLD + "/atcommandremove" + ChatColor.GRAY + " - 명령어 내용 삭제");
			sender.sendMessage(ChatColor.GOLD + "/atcommandedit" + ChatColor.GRAY + " - 명령어 내용 수정");
			sender.sendMessage(ChatColor.GOLD + "/atcommandview" + ChatColor.GRAY + " - 명령어 내용 보기");
			sender.sendMessage(ChatColor.GOLD + "/atcommanddelete" + ChatColor.GRAY + " - 명령어 삭제");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  린마루");
		}
		else if(args.length == 1 && args[0].equals("4"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "페이지 4 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atevent" + ChatColor.GRAY + " - 이벤트 생성");
			sender.sendMessage(ChatColor.GOLD + "/ateventadd" + ChatColor.GRAY + " - 이벤트 내용 추가");
			sender.sendMessage(ChatColor.GOLD + "/ateventremove" + ChatColor.GRAY + " - 이벤트 내용 삭제");
			sender.sendMessage(ChatColor.GOLD + "/ateventedit" + ChatColor.GRAY + " - 이벤트 내용 수정");
			sender.sendMessage(ChatColor.GOLD + "/ateventview" + ChatColor.GRAY + " - 이벤트 내용 보기");
			sender.sendMessage(ChatColor.GOLD + "/ateventdelete" + ChatColor.GRAY + " - 이벤트 삭제");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  린마루");
		}
		else if(args.length == 1 && args[0].equals("5"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "페이지 5 / 6");
			sender.sendMessage(ChatColor.GOLD + "/attouch" + ChatColor.GRAY + " - 터치 생성");
			sender.sendMessage(ChatColor.GOLD + "/attouchadd" + ChatColor.GRAY + " - 터치 내용 추가");
			sender.sendMessage(ChatColor.GOLD + "/attouchremove" + ChatColor.GRAY + " - 터치 내용 삭제");
			sender.sendMessage(ChatColor.GOLD + "/attouchedit" + ChatColor.GRAY + " - 터치 내용 수정");
			sender.sendMessage(ChatColor.GOLD + "/attouchview" + ChatColor.GRAY + " - 터치 내용 보기");
			sender.sendMessage(ChatColor.GOLD + "/attouchdelete" + ChatColor.GRAY + " - 터치 삭제");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  린마루");
		}
		else if(args.length == 1 && args[0].equals("6"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "페이지 6 / 6");
			sender.sendMessage(ChatColor.GOLD + "/atwalk" + ChatColor.GRAY + " - 발판 생성");
			sender.sendMessage(ChatColor.GOLD + "/atwalkadd" + ChatColor.GRAY + " - 발판 내용 추가");
			sender.sendMessage(ChatColor.GOLD + "/atwalkremove" + ChatColor.GRAY + " - 발판 내용 삭제");
			sender.sendMessage(ChatColor.GOLD + "/atwalkedit" + ChatColor.GRAY + " - 발판 내용 수정");
			sender.sendMessage(ChatColor.GOLD + "/atwalkview" + ChatColor.GRAY + " - 발판 내용 보기");
			sender.sendMessage(ChatColor.GOLD + "/atwalkdelete" + ChatColor.GRAY + " - 발판 삭제");
			sender.sendMessage(ChatColor.GREEN + "http://cafe.naver.com/craftproducer" + ChatColor.GOLD + "  --  린마루");
		}
		else if(args.length == 1 && args[0].equals("save"))
		{
			plugin.config.SaveTriggers();
			sender.sendMessage(ChatColor.GREEN + "트리거 저장 완료");
		}
		else if(args.length == 1 && args[0].equals("reload"))
		{
			plugin.config.ReloadTriggers();
			sender.sendMessage(ChatColor.GREEN + "트리거 불러오기 완료");
		}
		else if(args.length == 1 && args[0].equals("reloadconfig"))
		{
			plugin.config.ReloadConfig();
			sender.sendMessage(ChatColor.GREEN + "config.yml 불러오기 완료");
		}
		else if(args.length == 3 && args[0].equals("setvar"))
		{
			plugin.scriptVariables.putScriptVariables(args[1], args[2]);
			sender.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " 변수 설정 값 : " + ChatColor.WHITE + args[2]);
		}
		else if(args.length == 2 && args[0].equals("getvar"))
		{
			if(plugin.scriptVariables.equalsScriptVariables(args[1]))
				sender.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " 변수 값 : " + ChatColor.WHITE + plugin.scriptVariables.getScriptVariables(args[1]));
			else
				sender.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " 변수가 없습니다. ");
		}
		else if(args.length == 2 && args[0].equals("delvar"))
		{
			plugin.scriptVariables.removeScriptVariables(args[1]);
			sender.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " 변수를 삭제했습니다.");
		}
		else if(args.length == 1 && args[0].equals("listvar"))
		{
			sender.sendMessage(ChatColor.GREEN + "AutomaticTriggers" + ChatColor.WHITE + "  --  "  + ChatColor.GOLD + "변수 목록");
			String variables[] = plugin.scriptVariables.getKeyScriptVariables();
			for(int i = 0; i < variables.length; i++)
				sender.sendMessage(ChatColor.YELLOW + variables[i] + ChatColor.GREEN + " 변수 값 : " + ChatColor.WHITE + plugin.scriptVariables.getScriptVariables(variables[i]));
		}
		else
			return false;
		return true;
	}
}
