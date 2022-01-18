package me.espoo.rpg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.espoo.rpg.guild.Guild;
import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.Party;

public class GUIMessage {
	public static List<String> YesEXPBouns = new ArrayList<String>();
	public static List<String> NoEXPBouns = new ArrayList<String>();
	public static List<String> GuildCreate = new ArrayList<String>();
	public static List<String> GuildRanking = new ArrayList<String>();
	public static List<String> CloseGUI = new ArrayList<String>();
	public static List<String> BackRanking = new ArrayList<String>();
	public static List<String> MessageRanking = new ArrayList<String>();
	public static List<String> MessageUserList = new ArrayList<String>();
	public static List<String> MessageStat = new ArrayList<String>();
	public static List<String> LeaveGuild = new ArrayList<String>();
	public static List<String> InvitePlayer = new ArrayList<String>();
	public static List<String> JoinList = new ArrayList<String>();
	public static List<String> AcceptList = new ArrayList<String>();
	public static List<String> Option = new ArrayList<String>();
	public static List<String> OptionAnd = new ArrayList<String>();
	public static List<String> GuildGive = new ArrayList<String>();
	public static List<String> GuildDelete = new ArrayList<String>();
	public static List<String> LandGuild = new ArrayList<String>();
	public static List<String> LandCreate = new ArrayList<String>();
	public static List<String> LandMenu = new ArrayList<String>();
	public static List<String> LandRemove = new ArrayList<String>();
	public static List<String> LandHome = new ArrayList<String>();
	public static List<String> Shop = new ArrayList<String>();
	public static List<String> o1 = new ArrayList<String>();
	public static List<String> o2 = new ArrayList<String>();
	public static List<String> o3 = new ArrayList<String>();
	public static List<String> WarSubmit = new ArrayList<String>();
	public static List<String> WarAccept = new ArrayList<String>();
	public static List<String> PartyCreate = new ArrayList<String>();
	public static List<String> PartyList = new ArrayList<String>();
	public static List<String> PartyLeave = new ArrayList<String>();
	public static List<String> PartyM = new ArrayList<String>();
	public static List<String> PartyU = new ArrayList<String>();
	public static List<String> PartyDelete = new ArrayList<String>();
	public static List<String> PartyInvite = new ArrayList<String>();
	public static List<String> PartyOption = new ArrayList<String>();
	public static List<String> PartyInfo = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		PartyCreate.add("��7������ Ŭ���� ��Ƽ�� �����մϴ�.");
		PartyList.add("��7������ Ŭ���� ��Ƽ ����� ���ϴ�.");
		PartyLeave.add("��7������ Ŭ���� ��Ƽ�� Ż���մϴ�.");
		PartyM.add("��7���: ��f��Ƽ��");
		PartyU.add("��7���: ��f��Ƽ��");
		PartyDelete.add("��7������ Ŭ���� ��Ƽ�� �����մϴ�.");
		PartyInvite.add("��7������ Ŭ���� ������ ��Ƽ�� �ʴ��մϴ�.");
		PartyOption.add("��7������ Ŭ���� ��Ƽ �ɼ��� Ȯ���մϴ�.");
		PartyInfo.add("��7������ Ŭ���� ��Ƽ ������ �����մϴ�.");

		WarSubmit.add("��7������ Ŭ���� �������� ��忡 �׺���û�� �մϴ�.");
		WarAccept.add("��7������ ��Ŭ���� ������");
		WarAccept.add("��7�׺��� �����ϰ�, ��Ŭ����");
		WarAccept.add("��7������ �׺��� �����մϴ�.");
		WarAccept.add("��f");
		WarAccept.add("��8��l<HELP> ��7��� �׺��� �����Ͻ�");
		WarAccept.add("��7�� ���� ��忡 ����ǰ�� ��");
		WarAccept.add("��7�� �䱸�Ͻð� �����ϼ���!");
		
		YesEXPBouns.add("��f�������� �Ҹ��ϰ� ��6���ʽ�");
		YesEXPBouns.add("��6����ġ��f�� �����ϰ� �����ôٸ�");
		YesEXPBouns.add("��f�������� ��a��lŬ����f�� �ּ���.");
		
		NoEXPBouns.add("��fâ�� �ݰ� �����ôٸ�");
		NoEXPBouns.add("��f�������� ��a��lŬ����f�� �ּ���.");
		
		GuildCreate.add("��f��带 ��c" + main.createGuildMoney + " ��f�� ����");
		GuildCreate.add("��f�ϰ� ���� â���մϴ�.");
		
		GuildRanking.add("��fŬ���Ͻø� ��� ���");
		GuildRanking.add("��f�� ��e��ŷ��f�� Ȯ���մϴ�.");
		GuildRanking.add("��f");
		GuildRanking.add("��8��l<HELP> ��7��ŷ���� ���");
		GuildRanking.add("��7���� ��û, ��� ���� Ȯ��");
		GuildRanking.add("��7���� �����Ͻ� �� �ֽ��ϴ�.");
		
		AcceptList.add("��7���� - ��f��� Ż��, ���, ä��, ���� Ȯ�� ����");
		AcceptList.add("��7��ȸ�� - ��f���� ��� + �÷��̾� �ʴ� ����");
		AcceptList.add("��7�����̳� - ��f��ȭ�� ��� + ���� �޼���, ����, ���� ���� ����");
		AcceptList.add("��7���� - ��f�����̳� ��� + ����, ���� ��û�� ����, ���� ���� ����");
		AcceptList.add("��7�Ŵ��� - ��f���� ��� + ��� ���, �絵, ���׷��̵�, ���� ���� ����");

		LandMenu.add("��7������ Ŭ���� ��� �� �޴� â�� ���ϴ�.");
		LandGuild.add("��7������ Ŭ���� ��� Ȩ���� �̵��մϴ�.");
		Shop.add("��7������ Ŭ���� ��� ������ �����մϴ�.");
		CloseGUI.add("��7������ Ŭ���� â�� �ݽ��ϴ�.");
		BackRanking.add("��7������ Ŭ���� ���� â���� ���ư��ϴ�.");
		MessageRanking.add("��7������ Ŭ���� ��� ��ŷ�� ���ϴ�.");
		MessageUserList.add("��7������ Ŭ���� ��� ���� ����� ���ϴ�.");
		MessageStat.add("��7������ Ŭ���� ����� ������ ���ϴ�.");
		LeaveGuild.add("��7������ ��忡�� Ż���մϴ�.");
		InvitePlayer.add("��7���ϴ� �÷��̾ �ʴ��ŵ�ϴ�.");
		JoinList.add("��7Ŭ���� ���� ��û�� ���� ����� ���ϴ�.");
		Option.add("��7��� �ɼ� â�� �����մϴ�.");
		OptionAnd.add("��7��� �ɼ� â�� ���� ��û�� ����� �����մϴ�.");
		GuildGive.add("��7Ŭ���� ��带 �絵�ϴ� ������ �����մϴ�.");
		GuildDelete.add("��7Ŭ���� ��带 ����մϴ�.");

		o1.add("��7Ŭ���� ��� ���� �޼����� �����մϴ�.");
		o2.add("��7Ŭ���� ��� ������ �����մϴ�.");
		o3.add("��7Ŭ���� ��� ������ �ڵ带 �����մϴ�.");

		LandCreate.add("��7������ Ŭ���� ��� ���� �����մϴ�.");
		LandRemove.add("��7������ Ŭ���� ��� ���� �ʱ�ȭ �մϴ�.");
		LandHome.add("��7Ŭ���� ��� ���� Ȩ�� ���ִ� ��ġ�� �����մϴ�.");
	}
	
	public static List<String> getPlayerClassMessage(Player p)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("��7����� ��� ���: ��f" + Guild.players.get(p.getName()).getUserClass(p.getName()));
		return list;
	}
	
	public static List<String> getStat(Player p, int num)
	{
		List<String> list = new ArrayList<String>();
		Guild guild = Guild.players.get(p.getName());
		
		switch (num) {
		case 1:
			list.add("��6���� 1�� ��c����ġ ���ʽ� +10%");
			list.add("��6���� ��� ���� ��c" + Integer.toString(guild.getStat_Exp()) + "��6/��c" + guild.getMaxExpStat());
			break;
			
		case 2:
			list.add("��6���� 2�� ��c��깰 2�� ����� +10%");
			list.add("��6���� ��� ���� ��c" + Integer.toString(guild.getStat_Plant()) + "��6/��c" + guild.getMaxPlantStat());
			break;
			
		case 3:
			list.add("��6���� 2�� ��c��, ���� 2�� ����� +10%");
			list.add("��6���� ��� ���� ��c" + Integer.toString(guild.getStat_Mine()) + "��6/��c" + guild.getMaxMineStat());
			break;
		} return list;
	}
	
	public static List<String> getStatSet(Player p, int num)
	{
		List<String> list = new ArrayList<String>();
		Guild guild = Guild.players.get(p.getName());
		
		switch (num) {
		case 1:
			list.add("��6���� 1�� ��c����ġ ���ʽ� +10%");
			list.add("��6���� ��� ���� ��c" + Integer.toString(guild.getStat_Exp()) + "��6/��c" + guild.getMaxExpStat());
			list.add("");
			list.add("��8��l<HELP> ��7Ŭ���� ��� ����Ʈ�� '12000'");
			list.add("��7��ŭ �Һ��ϰ� ������ 1��ŭ �߰��մϴ�.");
			break;
			
		case 2:
			list.add("��6���� 2�� ��c��깰 2�� ����� +10%");
			list.add("��6���� ��� ���� ��c" + Integer.toString(guild.getStat_Plant()) + "��6/��c" + guild.getMaxPlantStat());
			list.add("");
			list.add("��8��l<HELP> ��7Ŭ���� ��� ����Ʈ�� '12000'");
			list.add("��7��ŭ �Һ��ϰ� ������ 1��ŭ �߰��մϴ�.");
			break;
			
		case 3:
			list.add("��6���� 2�� ��c��, ���� 2�� ����� +10%");
			list.add("��6���� ��� ���� ��c" + Integer.toString(guild.getStat_Mine()) + "��6/��c" + guild.getMaxMineStat());
			list.add("");
			list.add("��8��l<HELP> ��7Ŭ���� ��� ����Ʈ�� '12000'");
			list.add("��7��ŭ �Һ��ϰ� ������ 1��ŭ �߰��մϴ�.");
			break;
		} return list;
	}
	
	public static List<String> getPlayerChatMessage(Player p)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("��7ä�� ���: ��f" + GuildAPI.getPlayerChat(p));
		list.add("");
		list.add("��8��l<HELP> ��7Ŭ���� ä�� ��带 �����մϴ�");
		list.add("��7ä��â�� '/cm', '/chatmode',");
		list.add("��7'/ä�ø��' �Է��ϼŵ� ����˴ϴ�.");
		return list;
	}
	
	public static List<String> getGuildPoint(Player p)
	{
		List<String> list = new ArrayList<String>();
		
		if (Guild.players.containsKey(p.getName())) {
			Guild guild = Guild.players.get(p.getName());
			list.add("��7���� ��� ����Ʈ: ��f" + guild.getPoint());
			list.add("��f");
			list.add("��8��l<HELP> ��7��� ����Ʈ�� ȹ���ϴ� ");
			list.add("��7����ġ�� ��150 �� �ؼ� ����ϴ�.");
		}
		
		return list;
	}
	
	public static List<String> getJoinType(Player p)
	{
		List<String> list = new ArrayList<String>();
		
		if (Guild.players.containsKey(p.getName())) {
			Guild guild = Guild.players.get(p.getName());
			list.add("��7���� ���� ����: ��f" + guild.getJoinType());
			list.add("��f");
			list.add("��8��l<HELP> ��7Ŭ���Ҷ����� ���� ������ �ٲ�ϴ�.");
		}
		
		return list;
	}
	
	public static List<String> getJoinTypeParty(Player p)
	{
		List<String> list = new ArrayList<String>();
		if (Party.players.containsKey(p.getName())) {
			Party party = Party.players.get(p.getName());
			list.add("��7���� ���� ����: ��f" + party.getJoinType());
			list.add("��f");
			list.add("��8��l<HELP> ��7Ŭ���Ҷ����� ���� ������ �ٲ�ϴ�.");
		}
		
		return list;
	}
	
	public static List<String> getAcceptType(Player p, String str)
	{
		List<String> list = new ArrayList<String>();
		
		if (main.ClickAccept.containsKey(p)) {
			list.add("��7Ŭ���� " + main.ClickAccept.get(p) + " �������� " + str + " ������ �����մϴ�.");
		} return list;
	}
	
	public static List<String> getUpGradeStart(Player p)
	{
		List<String> list = new ArrayList<String>();
		
		if (Guild.players.containsKey(p.getName())) {
			Guild guild = Guild.players.get(p.getName());
			int i = guild.getUpgrade() + 1;
			if (main.guildConfig.size() < (i + 2)) {
				list.add("��7����� ��� ��� ���׷��̵带 �Ϸ��ϼ̽��ϴ�.");
			} else {
				list.add("��7Ŭ���� ��f" + i + " ��7�ܰ�� ���׷��̵� �մϴ�.");
				list.add("��7���׷��̵�� ��f" + main.guildConfig.get(i).split(" ")[1] + " ��7���� �ҿ�˴ϴ�.");
			}
		} return list;
	}
	
	public static List<String> getUpGrade()
	{
		List<String> list = new ArrayList<String>();
		list.add("��f���, �ִ� ����, ��ȸ�� ��, �����̳� ��, ���� ��,");
		list.add("��f����ġ ����, ��� ����, ä�� ����");
		list.add("");
		int z = -2;
		for (String str : main.guildConfig) {
			z++;
			if (z == -1) {
				continue;
			}
			
			list.add("��7" + z + "�ܰ�: ��f" + Method.getFinalArgs(str.split(" "), 1));
		} return list;
	}
	
	public static List<String> getStatMessage(Player p, String str)
	{
		List<String> list = new ArrayList<String>();
		
		if (Guild.players.containsKey(p.getName())) {
			Guild guild = Guild.players.get(p.getName());
			
			if (str.equalsIgnoreCase("����ġ ���ʽ�")) {
				if (guild.getStat_IsExp())
					list.add("��7����  " + str + " ������ ��aȰ��ȭ ��7�����Դϴ�.");
				else
					list.add("��7����  " + str + " ������ ��c��Ȱ��ȭ ��7�����Դϴ�.");
			}
			
			else if (str.equalsIgnoreCase("��� ���ʽ�")) {
				if (guild.getStat_IsPlant())
					list.add("��7����  " + str + " ������ ��aȰ��ȭ ��7�����Դϴ�.");
				else
					list.add("��7����  " + str + " ������ ��c��Ȱ��ȭ ��7�����Դϴ�.");
			}
			
			else if (str.equalsIgnoreCase("ä�� ���ʽ�")) {
				if (guild.getStat_IsMine())
					list.add("��7����  " + str + " ������ ��aȰ��ȭ ��7�����Դϴ�.");
				else
					list.add("��7����  " + str + " ������ ��c��Ȱ��ȭ ��7�����Դϴ�.");
			}
			
			list.add("��f");
			list.add("��8��l<HELP> ��7Ŭ���Ͻø� Ȱ��ȭ/��Ȱ��ȭ�� �����մϴ�.");
		}
		
		return list;
	}
	
	public static List<String> getLandTypeUse(Player p)
	{
		List<String> list = new ArrayList<String>();
		Guild guild = Guild.players.get(p.getName());
		String str = null;
		
		switch (guild.getIsland_Chest()) {
			case 1: str = "����"; break;
			case 2: str = "��ȸ�� �̻�"; break;
			case 3: str = "�����̳� �̻�"; break;
			case 4: str = "���� �̻�"; break;
			case 5: str = "�Ŵ���"; break;
			default: str = "ERROR- �ѹ� Ŭ���� �ּ���."; break;
		}
		
		list.add("��7���� ����: ��f" + str);
		return list;
	}
	
	public static List<String> getLandTypeBlock(Player p)
	{
		List<String> list = new ArrayList<String>();
		Guild guild = Guild.players.get(p.getName());
		String str = null;
		
		switch (guild.getIsland_Build()) {
			case 1: str = "����"; break;
			case 2: str = "��ȸ�� �̻�"; break;
			case 3: str = "�����̳� �̻�"; break;
			case 4: str = "���� �̻�"; break;
			case 5: str = "�Ŵ���"; break;
			default: str = "ERROR: �ѹ� Ŭ���� �ּ���."; break;
		}
		
		list.add("��7���� ����: ��f" + str);
		return list;
	}
	
	public static List<String> getLandTypePvP(Player p)
	{
		List<String> list = new ArrayList<String>();
		Guild guild = Guild.players.get(p.getName());
		String str = guild.getIsland_PVP() ? "���" : "�����";
		
		list.add("��7���� ����: ��f" + str);
		return list;
	}
	
	public static List<String> getPartyShare(Player p)
	{
		List<String> list = new ArrayList<String>();
		Party party = Party.players.get(p.getName());
		String str = party.getIsExp() ? "�й�" : "��й�";
		
		list.add("��7�й� ����: ��f" + str);
		return list;
	}
}
