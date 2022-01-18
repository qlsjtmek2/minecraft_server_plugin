package me.espoo.rpg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.guild.GuildConfig;
import me.espoo.rpg.party.PartyAPI;

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
		PartyCreate.add("§7아이템 클릭시 파티를 생성합니다.");
		PartyList.add("§7아이템 클릭시 파티 목록을 봅니다.");
		PartyLeave.add("§7아이템 클릭시 파티를 탈퇴합니다.");
		PartyM.add("§7계급: §f파티장");
		PartyU.add("§7계급: §f파티원");
		PartyDelete.add("§7아이템 클릭시 파티를 해제합니다.");
		PartyInvite.add("§7아이템 클릭시 유저를 파티에 초대합니다.");
		PartyOption.add("§7아이템 클릭시 파티 옵션을 확인합니다.");
		PartyInfo.add("§7아이템 클릭시 파티 설명을 수정합니다.");

		WarSubmit.add("§7아이템 클릭시 전쟁중인 길드에 항복신청을 합니다.");
		WarAccept.add("§7아이템 왼클릭시 상대방의");
		WarAccept.add("§7항복을 거절하고, 우클릭시");
		WarAccept.add("§7상대방의 항복을 수락합니다.");
		WarAccept.add("§f");
		WarAccept.add("§8§l<HELP> §7길드 항복을 수락하실");
		WarAccept.add("§7때 상대방 길드에 전리품을 직");
		WarAccept.add("§7접 요구하시고 결정하세요!");
		
		YesEXPBouns.add("§f아이템을 소모하고 §6보너스");
		YesEXPBouns.add("§6경험치§f를 적용하고 싶으시다면");
		YesEXPBouns.add("§f아이템을 §a§l클릭§f해 주세요.");
		
		NoEXPBouns.add("§f창을 닫고 싶으시다면");
		NoEXPBouns.add("§f아이템을 §a§l클릭§f해 주세요.");
		
		GuildCreate.add("§f길드를 §c" + GuildAPI.getCreateMoney() + " §f원 지불");
		GuildCreate.add("§f하고 직접 창설합니다.");
		
		GuildRanking.add("§f클릭하시면 모든 길드");
		GuildRanking.add("§f의 §e랭킹§f을 확인합니다.");
		GuildRanking.add("§f");
		GuildRanking.add("§8§l<HELP> §7랭킹에서 길드");
		GuildRanking.add("§7가입 신청, 길드 정보 확인");
		GuildRanking.add("§7등을 진행하실 수 있습니다.");
		
		AcceptList.add("§7유저 - §f길드 탈퇴, 목록, 채팅, 스텟 확인 가능");
		AcceptList.add("§7정회원 - §f유저 기능 + 플레이어 초대 가능");
		AcceptList.add("§7디자이너 - §f정화원 기능 + 상태 메세지, 설명, 가입 조건 수정");
		AcceptList.add("§7스탭 - §f디자이너 기능 + 강퇴, 가입 신청서 관리, 권한 지급 가능");
		AcceptList.add("§7매니저 - §f스탭 기능 + 길드 폐쇄, 양도, 업그레이드, 스텟 설정 가능");

		LandMenu.add("§7아이템 클릭시 길드 땅 메뉴 창을 엽니다.");
		LandGuild.add("§7아이템 클릭시 길드 홈으로 이동합니다.");
		Shop.add("§7아이템 클릭시 길드 상점을 오픈합니다.");
		CloseGUI.add("§7아이템 클릭시 창을 닫습니다.");
		BackRanking.add("§7아이템 클릭시 이전 창으로 돌아갑니다.");
		MessageRanking.add("§7아이템 클릭시 길드 랭킹을 봅니다.");
		MessageUserList.add("§7아이템 클릭시 길드 유저 목록을 봅니다.");
		MessageStat.add("§7아이템 클릭시 길드의 스텟을 봅니다.");
		LeaveGuild.add("§7가입한 길드에서 탈퇴합니다.");
		InvitePlayer.add("§7원하는 플레이어를 초대시킵니다.");
		JoinList.add("§7클릭시 가입 신청한 유저 목록을 봅니다.");
		Option.add("§7길드 옵션 창을 오픈합니다.");
		OptionAnd.add("§7길드 옵션 창과 가입 신청서 목록을 오픈합니다.");
		GuildGive.add("§7클릭시 길드를 양도하는 절차를 진행합니다.");
		GuildDelete.add("§7클릭시 길드를 폐쇄합니다.");

		o1.add("§7클릭시 길드 상태 메세지를 수정합니다.");
		o2.add("§7클릭시 길드 설명을 수정합니다.");
		o3.add("§7클릭시 길드 아이템 코드를 수정합니다.");

		LandCreate.add("§7아이템 클릭시 길드 땅을 생성합니다.");
		LandCreate.add("");
		LandCreate.add("§8§l<HELP> §7생성하기 전에");
		LandCreate.add("§7'/포탈 -> 길드 월드'로 이동");
		LandCreate.add("§7후 나무 곡괭이로 지점을 찍어서");
		LandCreate.add("§7땅을 생성해 주시기 바랍니다.");
		LandRemove.add("§7아이템 클릭시 길드 영토를 제거합니다.");
		LandHome.add("§7클릭시 길드 영토 홈을 서있는 위치로 변경합니다.");
	}
	
	public static List<String> getPlayerClassMessage(Player p)
	{
		List<String> list = new ArrayList<String>();
		list.add("§7당신의 길드 계급: §f" + GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()));
		return list;
	}
	
	public static List<String> getStat(Player p, int num)
	{
		List<String> list = new ArrayList<String>();
		String name = GuildAPI.getJoinGuild(p);
		
		switch (num) {
		case 1:
			list.add("§6스텟 1당 §c경험치 보너스 +10%");
			list.add("§6현재 길드 스텟 §c" + Integer.toString(GuildAPI.getExpStat(name)) + "§6/§c" + GuildAPI.getMaxExpStat(name));
			break;
			
		case 2:
			list.add("§6스텟 1당 §c히어로 점수 보너스 +10%");
			list.add("§6현재 길드 스텟 §c" + Integer.toString(GuildAPI.getScoreStat(name)) + "§6/§c" + GuildAPI.getMaxScoreStat(name));
			break;
			
		case 3:
			list.add("§6스텟 2당 §c광물 드랍 +1개§6, 스텟 1당 §c강화 확률 +0.5%");
			list.add("§6현재 길드 스텟 §c" + Integer.toString(GuildAPI.getLuckStat(name)) + "§6/§c" + GuildAPI.getMaxLuckStat(name));
			break;
		} return list;
	}
	
	public static List<String> getStatSet(Player p, int num)
	{
		List<String> list = new ArrayList<String>();
		String name = GuildAPI.getJoinGuild(p);
		
		switch (num) {
		case 1:
			list.add("§6스텟 1당 §c경험치 보너스 +10%");
			list.add("§6현재 길드 스텟 §c" + Integer.toString(GuildAPI.getExpStat(name)) + "§6/§c" + GuildAPI.getMaxExpStat(name));
			list.add("");
			list.add("§8§l<HELP> §7클릭시 길드 포인트를 '12000'");
			list.add("§7만큼 소비하고 스텟을 1만큼 추가합니다.");
			break;
			
		case 2:
			list.add("§6스텟 1당 §c히어로 점수 보너스 +10%");
			list.add("§6현재 길드 스텟 §c" + Integer.toString(GuildAPI.getScoreStat(name)) + "§6/§c" + GuildAPI.getMaxScoreStat(name));
			list.add("");
			list.add("§8§l<HELP> §7클릭시 길드 포인트를 '12000'");
			list.add("§7만큼 소비하고 스텟을 1만큼 추가합니다.");
			break;
			
		case 3:
			list.add("§6스텟 2당 §c광물 드랍 +1개§6, 스텟 1당 §c강화 확률 +0.5%");
			list.add("§6현재 길드 스텟 §c" + Integer.toString(GuildAPI.getLuckStat(name)) + "§6/§c" + GuildAPI.getMaxLuckStat(name));
			list.add("");
			list.add("§8§l<HELP> §7클릭시 길드 포인트를 '16000'");
			list.add("§7만큼 소비하고 스텟을 1만큼 추가합니다.");
			break;
		} return list;
	}
	
	public static List<String> getPlayerChatMessage(Player p)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("§7채팅 모드: §f" + GuildAPI.getPlayerChat(p));
		list.add("");
		list.add("§8§l<HELP> §7클릭시 채팅 모드를 변경합니다");
		list.add("§7채팅창에 '/cm', '/chatmode',");
		list.add("§7'/채팅모드' 입력하셔도 변경됩니다.");
		return list;
	}
	
	public static List<String> getGuildPoint(Player p)
	{
		List<String> list = new ArrayList<String>();
		String guild = GuildAPI.getJoinGuild(p);
		
		if (GuildAPI.isGuild(guild)) {
			list.add("§7현재 길드 포인트: §f" + GuildAPI.getPoint(guild));
		}
		
		return list;
	}
	
	public static List<String> getJoinType(Player p)
	{
		List<String> list = new ArrayList<String>();
		String guild = GuildAPI.getJoinGuild(p);
		
		if (GuildAPI.isGuild(guild)) {
			list.add("§7현재 가입 조건: §f" + GuildAPI.getJoinType(guild));
			list.add("§f");
			list.add("§8§l<HELP> §7클릭할때마다 가입 조건이 바뀝니다.");
		}
		
		return list;
	}
	
	public static List<String> getJoinTypeParty(Player p)
	{
		List<String> list = new ArrayList<String>();
		String party = PartyAPI.getJoinParty(p);
		
		if (PartyAPI.isParty(party)) {
			list.add("§7현재 가입 조건: §f" + PartyAPI.getJoinType(party));
			list.add("§f");
			list.add("§8§l<HELP> §7클릭할때마다 가입 조건이 바뀝니다.");
		}
		
		return list;
	}
	
	public static List<String> getAcceptType(Player p, String str)
	{
		List<String> list = new ArrayList<String>();
		
		if (main.ClickAccept.containsKey(p)) {
			list.add("§7클릭시 " + main.ClickAccept.get(p) + " 유저에게 " + str + " 권한을 지급합니다.");
		} return list;
	}
	
	public static List<String> getUpGradeStart(Player p)
	{
		List<String> list = new ArrayList<String>();
		String guild = GuildAPI.getJoinGuild(p);
		int i = GuildAPI.getUpGrade(guild);
		i += 1;
		
		if (GuildAPI.isGuild(guild)) {
			if (GuildConfig.getGuildList("길드 업그레이드").size() < (i + 2)) {
				list.add("§7당신은 모든 길드 업그레이드를 완료하셨습니다.");
			} else {
				list.add("§7클릭시 §f" + i + " §7단계로 업그레이드 합니다.");
				list.add("§7업그레이드시 §f" + GuildConfig.getGuildList("길드 업그레이드").get(i).split(" ")[1] + " §7원이 소요됩니다.");
			}
		} return list;
	}
	
	public static List<String> getUpGrade()
	{
		List<String> list = new ArrayList<String>();
		list.add("§f비용, 최대 유저, 정회원 수, 디자이너 수, 스탭 수,");
		list.add("§f경험치 스텟, 히어로 점수 스텟, 행운 스텟");
		list.add("");
		int z = -2;
		for (String str : GuildConfig.getGuildList("길드 업그레이드")) {
			z++;
			if (z == -1) {
				continue;
			}
			
			list.add("§7" + z + "단계: §f" + Method.getFinalArgs(str.split(" "), 1));
		} return list;
	}
	
	public static List<String> getStatMessage(Player p, String str)
	{
		List<String> list = new ArrayList<String>();
		String name = GuildAPI.getJoinGuild(p);
		
		if (GuildAPI.isGuild(name)) {
			if (str.equalsIgnoreCase("경험치 보너스")) {
				if (GuildAPI.isExpStat(name))
					list.add("§7현재  " + str + " 스텟은 §a활성화 §7상태입니다.");
				else
					list.add("§7현재  " + str + " 스텟은 §c비활성화 §7상태입니다.");
			}
			
			else if (str.equalsIgnoreCase("히어로 점수 보너스")) {
				if (GuildAPI.isExpStat(name))
					list.add("§7현재  " + str + " 스텟은 §a활성화 §7상태입니다.");
				else
					list.add("§7현재  " + str + " 스텟은 §c비활성화 §7상태입니다.");
			}
			
			else if (str.equalsIgnoreCase("행운")) {
				if (GuildAPI.isExpStat(name))
					list.add("§7현재  " + str + " 스텟은 §a활성화 §7상태입니다.");
				else
					list.add("§7현재  " + str + " 스텟은 §c비활성화 §7상태입니다.");
			}
			
			list.add("§f");
			list.add("§8§l<HELP> §7클릭하시면 활성화/비활성화가 가능합니다.");
		}
		
		return list;
	}
	
	public static List<String> getLandTypeUse(Player p)
	{
		List<String> list = new ArrayList<String>();
		String guild = GuildAPI.getJoinGuild(p);
		String str = null;
		
		switch (GuildAPI.getLandAcceptUse(guild)) {
			case 1: str = "길드원"; break;
			case 2: str = "정회원 이상"; break;
			case 3: str = "디자이너 이상"; break;
			case 4: str = "스탭 이상"; break;
			case 5: str = "매니저"; break;
			default: str = "ERROR- 한번 클릭해 주세요."; break;
		}
		
		list.add("§7권한 여부: §f" + str);
		return list;
	}
	
	public static List<String> getLandTypeBlock(Player p)
	{
		List<String> list = new ArrayList<String>();
		String guild = GuildAPI.getJoinGuild(p);
		String str = null;
		
		switch (GuildAPI.getLandAcceptBlock(guild)) {
			case 1: str = "길드원"; break;
			case 2: str = "정회원 이상"; break;
			case 3: str = "디자이너 이상"; break;
			case 4: str = "스탭 이상"; break;
			case 5: str = "매니저"; break;
			default: str = "ERROR: 한번 클릭해 주세요."; break;
		}
		
		list.add("§7권한 여부: §f" + str);
		return list;
	}
	
	public static List<String> getLandTypePvP(Player p)
	{
		List<String> list = new ArrayList<String>();
		String guild = GuildAPI.getJoinGuild(p);
		String str = GuildAPI.getLandPvPType(guild) ? "허용" : "비허용";
		
		list.add("§7권한 여부: §f" + str);
		return list;
	}
	
	public static List<String> getPartyShare(Player p)
	{
		List<String> list = new ArrayList<String>();
		String party = PartyAPI.getJoinParty(p);
		String str = PartyAPI.getShare(party) ? "분배" : "비분배";
		
		list.add("§7분배 여부: §f" + str);
		return list;
	}
}
