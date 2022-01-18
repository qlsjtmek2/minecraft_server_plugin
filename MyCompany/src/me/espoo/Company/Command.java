package me.espoo.Company;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Command extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final Main plugin;

	public Command(Main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			
		} catch (NumberFormatException ex) {
			if (sender.isOp()) {
				displayHelp(sender);
				return false;
			}
		}
		if(commandLabel.equalsIgnoreCase("회사")) {
			
		}
		if(!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] 버킷에선 실행이 불가능 합니다.");
			return false;
		}
		
		
		return false;
	}
	
	private void displayHelp(CommandSender sender)
	{
		sender.sendMessage(" §e---- §6회사 §e-- §6페이지 §c1§6/§c1 §e----");
		sender.sendMessage("§6/인기도 <보기/상승/감소> <닉네임> §f- 인기도를 보거나 상승시키거나 감소시킵니다.");
		sender.sendMessage("§6/도움말, /명령어 §f- 도움말을 봅니다.");
		sender.sendMessage("§6/확성기 §f- 확성기를 실행합니다. §b( rpgitem )");
		sender.sendMessage("§6/이벤터 §f- 이벤터 확성기를 실행합니다. §b( rpgitem )");
		sender.sendMessage("§6/pvp §f- pvp장으로 전직에 맞게 이동합니다.");
		sender.sendMessage("§6/튜토리얼마침 §f- 튜토리얼을 마치는 명령어입니다. §b( rpgitem )");
		sender.sendMessage("§6/전직실행GUI §f- 전직 GUI를 오픈합니다.");
		sender.sendMessage("§6/포션알고리즘<1~5> §f- 포션 효과를 줍니다. §b( rpgitem )");
		sender.sendMessage("§6/색깔을변경하자 §f- 닉네임 색깔을 변경합니다. §b( rpgitem )");
		sender.sendMessage("§6/색깔을복구하자 §f- 닉네임 색깔을 복구합니다. §b( rpgitem )");
		sender.sendMessage("§6/강화-<칼/완드/석궁/글러브/단검/삽/곡괭이/도끼/장비/강화석> §f- 강화 도움말을 봅니다.");
		sender.sendMessage("§6/스텟<1/10/40/60> §f- 스텟을 줍니다. §b( rpgitem )");
		sender.sendMessage("");
		sender.sendMessage("§6명령어 §cAR 2 §6를 쳐서 다음페이지로 넘어가세요.");
	}
	
	private void displayHelp_1(CommandSender sender)
	{
		sender.sendMessage(" §e---- §6회사 §e-- §6페이지 §c1§6/§c §e----");
		sender.sendMessage("§6/인기도 <보기/상승/감소> <닉네임> §f- 인기도를 보거나 상승시키거나 감소시킵니다.");
		sender.sendMessage("§6/도움말, /명령어 §f- 도움말을 봅니다.");
		sender.sendMessage("§6/확성기 §f- 확성기를 실행합니다. §b( rpgitem )");
		sender.sendMessage("§6/이벤터 §f- 이벤터 확성기를 실행합니다. §b( rpgitem )");
		sender.sendMessage("§6/pvp §f- pvp장으로 전직에 맞게 이동합니다.");
		sender.sendMessage("§6/튜토리얼마침 §f- 튜토리얼을 마치는 명령어입니다. §b( rpgitem )");
		sender.sendMessage("§6/전직실행GUI §f- 전직 GUI를 오픈합니다.");
		sender.sendMessage("§6/포션알고리즘<1~5> §f- 포션 효과를 줍니다. §b( rpgitem )");
		sender.sendMessage("§6/색깔을변경하자 §f- 닉네임 색깔을 변경합니다. §b( rpgitem )");
		sender.sendMessage("§6/색깔을복구하자 §f- 닉네임 색깔을 복구합니다. §b( rpgitem )");
		sender.sendMessage("§6/강화-<칼/완드/석궁/글러브/단검/삽/곡괭이/도끼/장비/강화석> §f- 강화 도움말을 봅니다.");
		sender.sendMessage("§6/스텟<1/10/40/60> §f- 스텟을 줍니다. §b( rpgitem )");
		sender.sendMessage("");
		sender.sendMessage("§6명령어 §cAR 2 §6를 쳐서 다음페이지로 넘어가세요.");
	}
	
	private void displayHelp_2(CommandSender sender)
	{
		sender.sendMessage(" §e---- §6회사 §e-- §6페이지 §c1§6/§c §e----");
		sender.sendMessage("§6/인기도 <보기/상승/감소> <닉네임> §f- 인기도를 보거나 상승시키거나 감소시킵니다.");
		sender.sendMessage("§6/도움말, /명령어 §f- 도움말을 봅니다.");
		sender.sendMessage("§6/확성기 §f- 확성기를 실행합니다. §b( rpgitem )");
		sender.sendMessage("§6/이벤터 §f- 이벤터 확성기를 실행합니다. §b( rpgitem )");
		sender.sendMessage("§6/pvp §f- pvp장으로 전직에 맞게 이동합니다.");
		sender.sendMessage("§6/튜토리얼마침 §f- 튜토리얼을 마치는 명령어입니다. §b( rpgitem )");
		sender.sendMessage("§6/전직실행GUI §f- 전직 GUI를 오픈합니다.");
		sender.sendMessage("§6/포션알고리즘<1~5> §f- 포션 효과를 줍니다. §b( rpgitem )");
		sender.sendMessage("§6/색깔을변경하자 §f- 닉네임 색깔을 변경합니다. §b( rpgitem )");
		sender.sendMessage("§6/색깔을복구하자 §f- 닉네임 색깔을 복구합니다. §b( rpgitem )");
		sender.sendMessage("§6/강화-<칼/완드/석궁/글러브/단검/삽/곡괭이/도끼/장비/강화석> §f- 강화 도움말을 봅니다.");
		sender.sendMessage("§6/스텟<1/10/40/60> §f- 스텟을 줍니다. §b( rpgitem )");
		sender.sendMessage("");
		sender.sendMessage("§6명령어 §cAR 2 §6를 쳐서 다음페이지로 넘어가세요.");
	}
}
