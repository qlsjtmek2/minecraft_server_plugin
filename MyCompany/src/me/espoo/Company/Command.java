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
		if(commandLabel.equalsIgnoreCase("ȸ��")) {
			
		}
		if(!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
			return false;
		}
		
		
		return false;
	}
	
	private void displayHelp(CommandSender sender)
	{
		sender.sendMessage(" ��e---- ��6ȸ�� ��e-- ��6������ ��c1��6/��c1 ��e----");
		sender.sendMessage("��6/�α⵵ <����/���/����> <�г���> ��f- �α⵵�� ���ų� ��½�Ű�ų� ���ҽ�ŵ�ϴ�.");
		sender.sendMessage("��6/����, /��ɾ� ��f- ������ ���ϴ�.");
		sender.sendMessage("��6/Ȯ���� ��f- Ȯ���⸦ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�̺��� ��f- �̺��� Ȯ���⸦ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/pvp ��f- pvp������ ������ �°� �̵��մϴ�.");
		sender.sendMessage("��6/Ʃ�丮��ħ ��f- Ʃ�丮���� ��ġ�� ��ɾ��Դϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/��������GUI ��f- ���� GUI�� �����մϴ�.");
		sender.sendMessage("��6/���Ǿ˰���<1~5> ��f- ���� ȿ���� �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������������� ��f- �г��� ������ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������������� ��f- �г��� ������ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/��ȭ-<Į/�ϵ�/����/�۷���/�ܰ�/��/���/����/���/��ȭ��> ��f- ��ȭ ������ ���ϴ�.");
		sender.sendMessage("��6/����<1/10/40/60> ��f- ������ �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("");
		sender.sendMessage("��6��ɾ� ��cAR 2 ��6�� �ļ� ������������ �Ѿ����.");
	}
	
	private void displayHelp_1(CommandSender sender)
	{
		sender.sendMessage(" ��e---- ��6ȸ�� ��e-- ��6������ ��c1��6/��c ��e----");
		sender.sendMessage("��6/�α⵵ <����/���/����> <�г���> ��f- �α⵵�� ���ų� ��½�Ű�ų� ���ҽ�ŵ�ϴ�.");
		sender.sendMessage("��6/����, /��ɾ� ��f- ������ ���ϴ�.");
		sender.sendMessage("��6/Ȯ���� ��f- Ȯ���⸦ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�̺��� ��f- �̺��� Ȯ���⸦ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/pvp ��f- pvp������ ������ �°� �̵��մϴ�.");
		sender.sendMessage("��6/Ʃ�丮��ħ ��f- Ʃ�丮���� ��ġ�� ��ɾ��Դϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/��������GUI ��f- ���� GUI�� �����մϴ�.");
		sender.sendMessage("��6/���Ǿ˰���<1~5> ��f- ���� ȿ���� �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������������� ��f- �г��� ������ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������������� ��f- �г��� ������ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/��ȭ-<Į/�ϵ�/����/�۷���/�ܰ�/��/���/����/���/��ȭ��> ��f- ��ȭ ������ ���ϴ�.");
		sender.sendMessage("��6/����<1/10/40/60> ��f- ������ �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("");
		sender.sendMessage("��6��ɾ� ��cAR 2 ��6�� �ļ� ������������ �Ѿ����.");
	}
	
	private void displayHelp_2(CommandSender sender)
	{
		sender.sendMessage(" ��e---- ��6ȸ�� ��e-- ��6������ ��c1��6/��c ��e----");
		sender.sendMessage("��6/�α⵵ <����/���/����> <�г���> ��f- �α⵵�� ���ų� ��½�Ű�ų� ���ҽ�ŵ�ϴ�.");
		sender.sendMessage("��6/����, /��ɾ� ��f- ������ ���ϴ�.");
		sender.sendMessage("��6/Ȯ���� ��f- Ȯ���⸦ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�̺��� ��f- �̺��� Ȯ���⸦ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/pvp ��f- pvp������ ������ �°� �̵��մϴ�.");
		sender.sendMessage("��6/Ʃ�丮��ħ ��f- Ʃ�丮���� ��ġ�� ��ɾ��Դϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/��������GUI ��f- ���� GUI�� �����մϴ�.");
		sender.sendMessage("��6/���Ǿ˰���<1~5> ��f- ���� ȿ���� �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������������� ��f- �г��� ������ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������������� ��f- �г��� ������ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/��ȭ-<Į/�ϵ�/����/�۷���/�ܰ�/��/���/����/���/��ȭ��> ��f- ��ȭ ������ ���ϴ�.");
		sender.sendMessage("��6/����<1/10/40/60> ��f- ������ �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("");
		sender.sendMessage("��6��ɾ� ��cAR 2 ��6�� �ļ� ������������ �Ѿ����.");
	}
}
