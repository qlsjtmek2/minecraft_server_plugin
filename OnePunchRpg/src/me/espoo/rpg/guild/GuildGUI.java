package me.espoo.rpg.guild;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.rpg.GUIMessage;
import me.espoo.rpg.Method;
import me.espoo.rpg.exp.ExpAPI;

public class GuildGUI {
	public static void noGuildGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "길드가 없으시군요!");
		
		Stack("§6길드 창설", 264, 0, 1, GUIMessage.GuildCreate, 12, GUI);
		Stack("§6길드 랭킹", 66, 0, 1, GUIMessage.GuildRanking, 14, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckCreateGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "정말로 길드를 창설하시겠습니까?");
		
		Stack2("§f예, 창설하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f아니요, 창설하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckQuitGuildGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "정말로 길드를 탈퇴하시겠습니까?");
		
		Stack2("§f예, 탈퇴하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f아니요, 탈퇴하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckDeleteGuildGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "정말로 길드를 폐쇄하시겠습니까?");
		
		Stack2("§f예, 폐쇄하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f아니요, 폐쇄하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}

	public static void WarSubmitGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "정말로 전쟁에서 항복하시겠습니까?");
		
		Stack2("§f예, 항복하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f아니요, 항복하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void InviteGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "길드에 초대받으셨습니다. 수락하시겠습니까?");
		
		Stack3(GuildAPI.getInfoItem(name), 13, GUI);
		Stack2("§f예, 수락하겠습니다.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("§f아니요, 수락하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void WarCheckGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "정말로 이 길드와 전쟁하시겠습니까?");
		
		Stack3(GuildAPI.getInfoItem(name), 13, GUI);
		Stack2("§f예, 전쟁하겠습니다.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("§f아니요, 전쟁하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void GiveMasterGUI(Player p, String name, String player)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "정말로 " + player + " 님에게 길드장을 양도하시겠습니까?");
		
		Stack2("§f예, 양도하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f아니요, 양도하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void LandCreateGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "정말로 길드 영토를 생성하시겠습니까?");
		
		Stack2("§f예, 생성하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f아니요, 생성하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void LandRemoveGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "정말로 길드 영토를 제거하시겠습니까?");
		
		Stack2("§f예, 제거하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f아니요, 제거하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void SetItemCodeGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "설정할 아이템 코드를 클릭해 주시기 바랍니다.");
		
		Stack2(null, 35, 0, 1, Arrays.asList(), 10, GUI);
		Stack2(null, 35, 14, 1, Arrays.asList(), 11, GUI);
		Stack2(null, 35, 5, 1, Arrays.asList(), 12, GUI);
		Stack2(null, 35, 1, 1, Arrays.asList(), 13, GUI);
		Stack2(null, 35, 6, 1, Arrays.asList(), 14, GUI);
		Stack2(null, 35, 7, 1, Arrays.asList(), 15, GUI);
		Stack2(null, 35, 9, 1, Arrays.asList(), 16, GUI);
		Stack2(null, 29, 0, 1, Arrays.asList(), 19, GUI);
		Stack2(null, 42, 0, 1, Arrays.asList(), 20, GUI);
		Stack2(null, 41, 0, 1, Arrays.asList(), 21, GUI);
		Stack2(null, 57, 0, 1, Arrays.asList(), 22, GUI);
		Stack2(null, 79, 0, 1, Arrays.asList(), 23, GUI);
		Stack2(null, 116, 0, 1, Arrays.asList(), 24, GUI);
		Stack2(null, 120, 0, 1, Arrays.asList(), 25, GUI);
		Stack2(null, 264, 0, 1, Arrays.asList(), 28, GUI);
		Stack2(null, 263, 0, 1, Arrays.asList(), 29, GUI);
		Stack2(null, 265, 0, 1, Arrays.asList(), 30, GUI);
		Stack2(null, 266, 0, 1, Arrays.asList(), 31, GUI);
		Stack2(null, 388, 0, 1, Arrays.asList(), 32, GUI);
		Stack2(null, 397, 3, 1, Arrays.asList(), 33, GUI);
		Stack2(null, 339, 0, 1, Arrays.asList(), 34, GUI);
		Stack2(null, 323, 0, 1, Arrays.asList(), 37, GUI);
		Stack2(null, 368, 0, 1, Arrays.asList(), 38, GUI);
		Stack2(null, 381, 0, 1, Arrays.asList(), 39, GUI);
		Stack2(null, 378, 0, 1, Arrays.asList(), 40, GUI);
		Stack2(null, 276, 0, 1, Arrays.asList(), 41, GUI);
		Stack2(null, 278, 0, 1, Arrays.asList(), 42, GUI);
		Stack2(null, 293, 0, 1, Arrays.asList(), 43, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void userStatGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "길드 스텟");
		
		Stack("§e경험치 보너스 §6§l스텟", 384, 0, 1, GUIMessage.getStat(p, 1), 11, GUI);
		Stack("§a히어로 점수 보너스 §6§l스텟", 399, 0, 1, GUIMessage.getStat(p, 2), 13, GUI);
		Stack("§b행운 §6§l스텟", 368, 0, 1, GUIMessage.getStat(p, 3), 15, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void setStatGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "길드 스텟 설정");
		
		Stack("§e경험치 보너스 §6§l스텟", 384, 0, 1, GUIMessage.getStatSet(p, 1), 11, GUI);
		Stack("§a히어로 점수 보너스 §6§l스텟", 399, 0, 1, GUIMessage.getStatSet(p, 2), 13, GUI);
		Stack("§b행운 §6§l스텟", 368, 0, 1, GUIMessage.getStatSet(p, 3), 15, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		Stack("§d길드 포인트", 341, 0, 1, GUIMessage.getGuildPoint(p), 26, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void StepAcceptGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "스탭 권한 설정 창");
		
		Stack("§e유저 §f권한 지급", 289, 0, 1, GUIMessage.getAcceptType(p, "유저"), 11, GUI);
		Stack("§a정회원 §f권한 지급", 337, 0, 1, GUIMessage.getAcceptType(p, "정회원"), 13, GUI);
		Stack("§d디자이너 §f권한 지급", 336, 0, 1, GUIMessage.getAcceptType(p, "디자이너"), 15, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void DoptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "디자이너 옵션");
		
		Stack("§6길드 상태 메세지 §e§l수정", 323, 0, 1, GUIMessage.o1, 11, GUI);
		Stack("§6길드 설명 §e§l수정", 339, 0, 1, GUIMessage.o2, 13, GUI);
		Stack("§6길드 아이템 코드 §e§l수정", 58, 0, 1, GUIMessage.o3, 15, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void SoptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "스탭 옵션");

		Stack("§3길드 가입조건", 399, 0, 1, GUIMessage.getJoinType(p), 23, GUI);
		Stack("§9가입 신청서 목록", 54, 0, 1, GUIMessage.JoinList, 21, GUI);
		Stack("§6길드 상태 메세지 §e§l수정", 323, 0, 1, GUIMessage.o1, 11, GUI);
		Stack("§6길드 설명 §e§l수정", 339, 0, 1, GUIMessage.o2, 13, GUI);
		Stack("§6길드 아이템 코드 §e§l수정", 58, 0, 1, GUIMessage.o3, 15, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 27, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void MoptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 45, "매니저 옵션");
		
		Stack("§6길드 상태 메세지 §e§l수정", 323, 0, 1, GUIMessage.o1, 10, GUI);
		Stack("§3길드 가입조건", 399, 0, 1, GUIMessage.getJoinType(p), 16, GUI);
		Stack("§6길드 설명 §e§l수정", 339, 0, 1, GUIMessage.o2, 12, GUI);
		Stack("§6길드 아이템 코드 §e§l수정", 58, 0, 1, GUIMessage.o3, 14, GUI);
		Stack("§9가입 신청서 목록", 54, 0, 1, GUIMessage.JoinList, 22, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
		
		Stack("§e경험치 보너스 §6§l설정", 384, 0, 1, GUIMessage.getStatMessage(p, "경험치 보너스"), 32, GUI);
		Stack("§a히어로 점수 보너스 §6§l설정", 399, 0, 1, GUIMessage.getStatMessage(p, "히어로 점수 보너스"), 33, GUI);
		Stack("§b행운 §6§l설정", 368, 0, 1, GUIMessage.getStatMessage(p, "행운"), 34, GUI);

		Stack("§a길드 업그레이드 진행", 266, 0, 1, GUIMessage.getUpGradeStart(p), 20, GUI);
		Stack("§b길드 업그레이드 목록", 265, 0, 1, GUIMessage.getUpGrade(), 29, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void GuildMenuGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "길드 영토 메뉴");
		
		Stack("§c§l길드 영토 삭제", 331, 0, 1, GUIMessage.LandRemove, 25, GUI);
		Stack("§6§l길드 상점", 388, 0, 1, GUIMessage.Shop, 16, GUI);
		Stack("§e§l영토 홈 지정", 337, 0, 1, GUIMessage.LandHome, 15, GUI);
		Stack("§a§l길드 홈 이동", 2, 0, 1, GUIMessage.LandGuild, 24, GUI);

		Stack("§a길드 상자, 화로 권한 여부", 265, 0, 1, GUIMessage.getLandTypeUse(p), 10, GUI);
		Stack("§b길드 블럭 설치, 제거 여부", 266, 0, 1, GUIMessage.getLandTypeBlock(p), 11, GUI);
		Stack("§c길드 영토 내에서 PvP 여부", 336, 0, 1, GUIMessage.getLandTypePvP(p), 12, GUI);
		Stack("§f뒤로 가기", 324, 0, 1, GUIMessage.BackRanking, 27, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void ManegerAcceptGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "매니저 권한 설정 창");
		
		Stack("§e유저 §f권한 지급", 289, 0, 1, GUIMessage.getAcceptType(p, "유저"), 10, GUI);
		Stack("§a정회원 §f권한 지급", 337, 0, 1, GUIMessage.getAcceptType(p, "정회원"), 12, GUI);
		Stack("§d디자이너 §f권한 지급", 336, 0, 1, GUIMessage.getAcceptType(p, "디자이너"), 14, GUI);
		Stack("§c스탭 §f권한 지급", 264, 0, 1, GUIMessage.getAcceptType(p, "스탭"), 16, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void userGuildGUI(Player p)
	{
		if (GuildAPI.getJoinGuild(p) != null) {
			String guild = GuildAPI.getJoinGuild(p);
			Inventory GUI = Bukkit.createInventory(null, 45, "§6§lG §0§l" + guild);

			Stack("§6길드 권한 목록", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("§c길드 탈퇴", 35, 14, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("§f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("§6길드 랭킹", 66, 0, 1, GUIMessage.MessageRanking, 15, GUI);
			Stack("§e길드 유저 목록", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("§b길드 채팅", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 31, GUI);
			Stack("§a길드 스텟", 264, 0, 1, GUIMessage.MessageStat, 33, GUI);
			Stack("§d길드 포인트", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("§6§l길드 상점", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("§a§l길드 홈 이동", 2, 0, 1, GUIMessage.LandGuild, 17, GUI);
			
			ItemStack item = GuildAPI.getInfoItem(guild);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			lore.remove(1);
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, 13, GUI);
			
			p.openInventory(GUI);
		}
	}
	
	public static void MemberGuildGUI(Player p)
	{
		if (GuildAPI.getJoinGuild(p) != null) {
			String guild = GuildAPI.getJoinGuild(p);
			Inventory GUI = Bukkit.createInventory(null, 45, "§6§lG §0§l" + guild);

			Stack("§6길드 권한 목록", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("§c길드 탈퇴", 35, 14, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("§f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("§6길드 랭킹", 66, 0, 1, GUIMessage.MessageRanking, 15, GUI);
			Stack("§e길드 유저 목록", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("§b길드 채팅", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 30, GUI);
			Stack("§a길드 스텟", 264, 0, 1, GUIMessage.MessageStat, 32, GUI);
			Stack("§2플레이어 초대", 138, 0, 1, GUIMessage.InvitePlayer, 33, GUI);
			Stack("§d길드 포인트", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("§6§l길드 상점", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("§a§l길드 홈 이동", 2, 0, 1, GUIMessage.LandGuild, 17, GUI);
			
			ItemStack item = GuildAPI.getInfoItem(guild);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			lore.remove(1);
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, 13, GUI);
			
			p.openInventory(GUI);
		}
	}
	
	public static void DesignerGuildGUI(Player p)
	{
		if (GuildAPI.getJoinGuild(p) != null) {
			String guild = GuildAPI.getJoinGuild(p);
			Inventory GUI = Bukkit.createInventory(null, 45, "§6§lG §0§l" + guild);

			Stack("§f길드 옵션", 368, 0, 1, GUIMessage.Option, 15, GUI);
			Stack("§6길드 권한 목록", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("§c길드 탈퇴", 35, 14, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("§f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("§e길드 유저 목록", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("§b길드 채팅", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 30, GUI);
			Stack("§a길드 스텟", 264, 0, 1, GUIMessage.MessageStat, 32, GUI);
			Stack("§2플레이어 초대", 138, 0, 1, GUIMessage.InvitePlayer, 33, GUI);
			Stack("§6길드 랭킹", 66, 0, 1, GUIMessage.MessageRanking, 31, GUI);
			Stack("§d길드 포인트", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("§6§l길드 상점", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("§a§l길드 홈 이동", 2, 0, 1, GUIMessage.LandGuild, 17, GUI);
			
			ItemStack item = GuildAPI.getInfoItem(guild);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			lore.remove(1);
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, 13, GUI);
			
			p.openInventory(GUI);
		}
	}
	
	public static void StepGuildGUI(Player p)
	{
		if (GuildAPI.getJoinGuild(p) != null) {
			String guild = GuildAPI.getJoinGuild(p);
			Inventory GUI = Bukkit.createInventory(null, 45, "§6§lG §0§l" + guild);

			Stack("§f길드 옵션 §7/ §f가입신청서 목록", 368, 0, 1, GUIMessage.Option, 15, GUI);
			Stack("§6길드 권한 목록", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("§d길드 포인트", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("§c길드 탈퇴", 35, 14, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("§f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("§e길드 유저 목록", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("§b길드 채팅", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 30, GUI);
			Stack("§a길드 스텟", 264, 0, 1, GUIMessage.MessageStat, 32, GUI);
			Stack("§6길드 랭킹", 66, 0, 1, GUIMessage.MessageRanking, 31, GUI);
			Stack("§2플레이어 초대", 138, 0, 1, GUIMessage.InvitePlayer, 33, GUI);
			Stack("§6§l길드 상점", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("§a§l길드 홈 이동", 2, 0, 1, GUIMessage.LandGuild, 17, GUI);
			
			ItemStack item = GuildAPI.getInfoItem(guild);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			lore.remove(1);
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, 13, GUI);
			
			p.openInventory(GUI);
		}
	}
	
	public static void MasterGuildGUI(Player p)
	{
		if (GuildAPI.getJoinGuild(p) != null) {
			String guild = GuildAPI.getJoinGuild(p);
			Inventory GUI = Bukkit.createInventory(null, 54, "§6§lG §0§l" + guild);

			Stack("§f길드 옵션 §7/ §f가입신청서 목록", 368, 0, 1, GUIMessage.Option, 15, GUI);
			Stack("§6길드 권한 목록", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("§d길드 포인트", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
			Stack("§c길드 탈퇴", 35, 14, 1, GUIMessage.LeaveGuild, 53, GUI);
			Stack("§f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("§e길드 유저 목록", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("§b길드 채팅", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 30, GUI);
			Stack("§a길드 스텟", 264, 0, 1, GUIMessage.MessageStat, 32, GUI);
			Stack("§6길드 랭킹", 66, 0, 1, GUIMessage.MessageRanking, 31, GUI);
			Stack("§2플레이어 초대", 138, 0, 1, GUIMessage.InvitePlayer, 33, GUI);
			Stack("§e길드 양도", 133, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("§f[§4!§f] §4길드 폐쇄", 152, 0, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("§6§l길드 상점", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("§a§l길드 땅 메뉴", 2, 0, 1, GUIMessage.LandMenu, 17, GUI);
			else
				Stack("§a길드 땅 생성", 3, 0, 1, GUIMessage.LandCreate, 17, GUI);
			
			if (GuildAPI.isWar(guild)) {
				if (GuildAPI.isSubmit(guild)) {
					Stack("§f전쟁 항복 수락/거절", 339, 0, 1, GUIMessage.WarAccept, 49, GUI);
				} else {
					if (!GuildAPI.isSubmit(GuildAPI.getWarGuild(guild)))
						Stack("§f전쟁 항복하기", 283, 0, 1, GUIMessage.WarSubmit, 49, GUI);
				}
			}
			
			ItemStack item = GuildAPI.getInfoItem(guild);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			lore.remove(1);
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, 13, GUI);
			
			p.openInventory(GUI);
		}
	}
	
	public static void joinListGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "가입 신청서 목록 불러오는중..");
		p.openInventory(G);
		
		String name = GuildAPI.getJoinGuild(p);
		List<String> list = GuildAPI.getJoinList(name);
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("§c해당 목록은 존재하지 않습니다.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// 어떤 순위만큼 보여줄 것인지
		int y = 1;	// for문 순위
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "가입신청서 목록 " + num + "/" + num2);
		
		for (String st : list)
		{
			if (y < i && num != 1) {
				y++;
				continue;
			}
			
			if (i >= ((num - 1) * 45) + 46) break;
			ItemStack item = null;
			boolean online = false;
			if (Method.getOnorOffLine(st) != null) {
				online = true;
				item = new MaterialData(397, (byte) 3).toItemStack(1);
			} else {
				item = new MaterialData(397, (byte) 0).toItemStack(1);
			}
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f" + st);
			List<String> lore = new ArrayList<String>();
			if (online) lore.add("§7접속 유무: §a온라인");
			else lore.add("§7접속 유무: §c오프라인");
			lore.add("§7경험치: §f" + ExpAPI.getExp(st));
			lore.add("§7히어로 점수: §f" + me.espoo.score.Method.getPlayerScore(st));
			
			if (GuildAPI.getJoinGuild(p) != null) {
				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()) 
				 || GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("스탭")) {
					lore.add("§f");
					lore.add("§8§l<HELP> §7왼클릭시 유저를 받음");
					lore.add("§7우클릭시 유저를 받지 않음");
				}
			}
			
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, z, GUI);
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("§6다음 목록 확인", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("§6이전 목록 확인", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			Stack2("", 119, 0, 1, Arrays.asList(), x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void giveMasterListGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "유저 목록 불러오는중..");
		p.openInventory(G);
		
		String name = GuildAPI.getJoinGuild(p);
		List<String> list = GuildAPI.getUserOrg(name);
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("§c해당 목록은 존재하지 않습니다.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// 어떤 순위만큼 보여줄 것인지
		int y = 1;	// for문 순위
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "양도할 유저 목록 " + num + "/" + num2);
		
		for (String st : list)
		{
			if (y < i && num != 1) {
				y++;
				continue;
			}
			
			if (i >= ((num - 1) * 45) + 46) break;
			ItemStack item = null;
			if (Method.getOnorOffLine(st) != null) {
				item = new MaterialData(397, (byte) 3).toItemStack(1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§f" + st);
				List<String> lore = new ArrayList<String>();
				lore.add("§7접속 유무: §a온라인");
				lore.add("§7길드 계급: §f" + GuildAPI.getUserClass(name, st));
				
				if (GuildAPI.getJoinGuild(p) != null) {
					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()) 
					 || GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("스탭")) {
						lore.add("§f");
						lore.add("§8§l<HELP> §7클릭시 유저에게");
						lore.add("§7길드 마스터를 양도합니다.");
					}
				}
				
				meta.setLore(lore);
				item.setItemMeta(meta);
				Stack3(item, z, GUI);
				i++; z++;
			}
		}
		
		int size = list.size();
		for (String st : list) {
			if (Method.getOnorOffLine(st) == null) {
				size--;
			}
		}
		
		if (size > (i - 1)) {
			Stack2("§6다음 목록 확인", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("§6이전 목록 확인", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			Stack2("", 119, 0, 1, Arrays.asList(), x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void userListGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "유저 목록 불러오는중..");
		p.openInventory(G);
		
		String name = GuildAPI.getJoinGuild(p);
		List<String> list = GuildAPI.getUserOrg(name);
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("§c해당 목록은 존재하지 않습니다.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// 어떤 순위만큼 보여줄 것인지
		int y = 1;	// for문 순위
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "유저 목록 " + num + "/" + num2);
		
		for (String st : list)
		{
			if (y < i && num != 1) {
				y++;
				continue;
			}
			
			if (i >= ((num - 1) * 45) + 46) break;
			ItemStack item = null;
			boolean online = false;
			if (Method.getOnorOffLine(st) != null) {
				online = true;
				item = new MaterialData(397, (byte) 3).toItemStack(1);
			} else {
				item = new MaterialData(397, (byte) 0).toItemStack(1);
			}
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f" + st);
			List<String> lore = new ArrayList<String>();
			if (online) lore.add("§7접속 유무: §a온라인");
			else lore.add("§7접속 유무: §c오프라인");
			lore.add("§7길드 계급: §f" + GuildAPI.getUserClass(name, st));
			
			if (GuildAPI.getJoinGuild(p) != null) {
				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()) 
				 || GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("스탭")) {
					lore.add("§f");
					lore.add("§8§l<HELP> §7쉬프트 클릭시 유저를 강퇴함.");
					lore.add("§7우클릭시 유저에게 다양한 권한을 부여함");
				}
			}
			
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, z, GUI);
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("§6다음 목록 확인", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("§6이전 목록 확인", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			Stack2("", 119, 0, 1, Arrays.asList(), x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void RankingGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "길드 랭킹 불러오는중..");
		p.openInventory(G);
		
		List<String> list = GuildYml.ArrayGuild();
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("§c해당 순위는 존재하지 않습니다.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// 어떤 순위만큼 보여줄 것인지
		int y = 1;	// for문 순위
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "길드 랭킹 " + num + "/" + num2);
		
		for (String str : list)
		{
			if (y < i && num != 1) {
				y++;
				continue;
			}
			
			if (i >= ((num - 1) * 45) + 46) break;
			String name = str.split(",")[0];
			ItemStack item = GuildAPI.getInfoItem(name);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§c" + i + ". " + meta.getDisplayName());
			List<String> lore = new ArrayList<String>();
			lore.add("§7생성 날짜: §f" + GuildAPI.getYear(name) + "년 " + GuildAPI.getMonth(name) + "월 " + GuildAPI.getDate(name) + "일");
			for (String string : meta.getLore()) {
				lore.add(string);
			}
			
			if (GuildAPI.getJoinGuild(p) != null) {
				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
					lore.add("§f");
					lore.add("§8§l<HELP> §7우클릭시 길드 전쟁 선포가 가능함.");
				}
			} else {
				lore.add("§f");
				lore.add("§8§l<HELP> §7왼클릭시 가입 신청, 즉시 가입 가능");
			}
			
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, z, GUI);
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("§6다음 랭킹 확인", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("§6이전 랭킹 확인", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			Stack2("", 119, 0, 1, Arrays.asList(), x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void Stack(String Display, int ID, int DATA, int STACK, List<String> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}

	public static void Stack2(String Display, int ID, int DATA, int STACK, List<Object> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}
	
	public static void Stack3(ItemStack item, int loc, Inventory inv) {
		inv.setItem(loc, item);
	}
}
