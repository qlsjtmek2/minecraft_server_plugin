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
		Inventory GUI = Bukkit.createInventory(null, 27, "��尡 �����ñ���!");
		
		Stack("��6��� â��", 264, 0, 1, GUIMessage.GuildCreate, 12, GUI);
		Stack("��6��� ��ŷ", 66, 0, 1, GUIMessage.GuildRanking, 14, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckCreateGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "������ ��带 â���Ͻðڽ��ϱ�?");
		
		Stack2("��f��, â���ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, â������ �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckQuitGuildGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "������ ��带 Ż���Ͻðڽ��ϱ�?");
		
		Stack2("��f��, Ż���ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, Ż������ �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckDeleteGuildGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "������ ��带 ����Ͻðڽ��ϱ�?");
		
		Stack2("��f��, ����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, ������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}

	public static void WarSubmitGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "������ ���￡�� �׺��Ͻðڽ��ϱ�?");
		
		Stack2("��f��, �׺��ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, �׺����� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void InviteGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "��忡 �ʴ�����̽��ϴ�. �����Ͻðڽ��ϱ�?");
		
		Stack3(GuildAPI.getInfoItem(name), 13, GUI);
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void WarCheckGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "������ �� ���� �����Ͻðڽ��ϱ�?");
		
		Stack3(GuildAPI.getInfoItem(name), 13, GUI);
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void GiveMasterGUI(Player p, String name, String player)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "������ " + player + " �Կ��� ������� �絵�Ͻðڽ��ϱ�?");
		
		Stack2("��f��, �絵�ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, �絵���� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void LandCreateGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "������ ��� ���並 �����Ͻðڽ��ϱ�?");
		
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void LandRemoveGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "������ ��� ���並 �����Ͻðڽ��ϱ�?");
		
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void SetItemCodeGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "������ ������ �ڵ带 Ŭ���� �ֽñ� �ٶ��ϴ�.");
		
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
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void userStatGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��� ����");
		
		Stack("��e����ġ ���ʽ� ��6��l����", 384, 0, 1, GUIMessage.getStat(p, 1), 11, GUI);
		Stack("��a����� ���� ���ʽ� ��6��l����", 399, 0, 1, GUIMessage.getStat(p, 2), 13, GUI);
		Stack("��b��� ��6��l����", 368, 0, 1, GUIMessage.getStat(p, 3), 15, GUI);
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void setStatGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��� ���� ����");
		
		Stack("��e����ġ ���ʽ� ��6��l����", 384, 0, 1, GUIMessage.getStatSet(p, 1), 11, GUI);
		Stack("��a����� ���� ���ʽ� ��6��l����", 399, 0, 1, GUIMessage.getStatSet(p, 2), 13, GUI);
		Stack("��b��� ��6��l����", 368, 0, 1, GUIMessage.getStatSet(p, 3), 15, GUI);
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		Stack("��d��� ����Ʈ", 341, 0, 1, GUIMessage.getGuildPoint(p), 26, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void StepAcceptGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "���� ���� ���� â");
		
		Stack("��e���� ��f���� ����", 289, 0, 1, GUIMessage.getAcceptType(p, "����"), 11, GUI);
		Stack("��a��ȸ�� ��f���� ����", 337, 0, 1, GUIMessage.getAcceptType(p, "��ȸ��"), 13, GUI);
		Stack("��d�����̳� ��f���� ����", 336, 0, 1, GUIMessage.getAcceptType(p, "�����̳�"), 15, GUI);
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void DoptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "�����̳� �ɼ�");
		
		Stack("��6��� ���� �޼��� ��e��l����", 323, 0, 1, GUIMessage.o1, 11, GUI);
		Stack("��6��� ���� ��e��l����", 339, 0, 1, GUIMessage.o2, 13, GUI);
		Stack("��6��� ������ �ڵ� ��e��l����", 58, 0, 1, GUIMessage.o3, 15, GUI);
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void SoptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "���� �ɼ�");

		Stack("��3��� ��������", 399, 0, 1, GUIMessage.getJoinType(p), 23, GUI);
		Stack("��9���� ��û�� ���", 54, 0, 1, GUIMessage.JoinList, 21, GUI);
		Stack("��6��� ���� �޼��� ��e��l����", 323, 0, 1, GUIMessage.o1, 11, GUI);
		Stack("��6��� ���� ��e��l����", 339, 0, 1, GUIMessage.o2, 13, GUI);
		Stack("��6��� ������ �ڵ� ��e��l����", 58, 0, 1, GUIMessage.o3, 15, GUI);
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 27, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void MoptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 45, "�Ŵ��� �ɼ�");
		
		Stack("��6��� ���� �޼��� ��e��l����", 323, 0, 1, GUIMessage.o1, 10, GUI);
		Stack("��3��� ��������", 399, 0, 1, GUIMessage.getJoinType(p), 16, GUI);
		Stack("��6��� ���� ��e��l����", 339, 0, 1, GUIMessage.o2, 12, GUI);
		Stack("��6��� ������ �ڵ� ��e��l����", 58, 0, 1, GUIMessage.o3, 14, GUI);
		Stack("��9���� ��û�� ���", 54, 0, 1, GUIMessage.JoinList, 22, GUI);
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
		
		Stack("��e����ġ ���ʽ� ��6��l����", 384, 0, 1, GUIMessage.getStatMessage(p, "����ġ ���ʽ�"), 32, GUI);
		Stack("��a����� ���� ���ʽ� ��6��l����", 399, 0, 1, GUIMessage.getStatMessage(p, "����� ���� ���ʽ�"), 33, GUI);
		Stack("��b��� ��6��l����", 368, 0, 1, GUIMessage.getStatMessage(p, "���"), 34, GUI);

		Stack("��a��� ���׷��̵� ����", 266, 0, 1, GUIMessage.getUpGradeStart(p), 20, GUI);
		Stack("��b��� ���׷��̵� ���", 265, 0, 1, GUIMessage.getUpGrade(), 29, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void GuildMenuGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "��� ���� �޴�");
		
		Stack("��c��l��� ���� ����", 331, 0, 1, GUIMessage.LandRemove, 25, GUI);
		Stack("��6��l��� ����", 388, 0, 1, GUIMessage.Shop, 16, GUI);
		Stack("��e��l���� Ȩ ����", 337, 0, 1, GUIMessage.LandHome, 15, GUI);
		Stack("��a��l��� Ȩ �̵�", 2, 0, 1, GUIMessage.LandGuild, 24, GUI);

		Stack("��a��� ����, ȭ�� ���� ����", 265, 0, 1, GUIMessage.getLandTypeUse(p), 10, GUI);
		Stack("��b��� �� ��ġ, ���� ����", 266, 0, 1, GUIMessage.getLandTypeBlock(p), 11, GUI);
		Stack("��c��� ���� ������ PvP ����", 336, 0, 1, GUIMessage.getLandTypePvP(p), 12, GUI);
		Stack("��f�ڷ� ����", 324, 0, 1, GUIMessage.BackRanking, 27, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void ManegerAcceptGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "�Ŵ��� ���� ���� â");
		
		Stack("��e���� ��f���� ����", 289, 0, 1, GUIMessage.getAcceptType(p, "����"), 10, GUI);
		Stack("��a��ȸ�� ��f���� ����", 337, 0, 1, GUIMessage.getAcceptType(p, "��ȸ��"), 12, GUI);
		Stack("��d�����̳� ��f���� ����", 336, 0, 1, GUIMessage.getAcceptType(p, "�����̳�"), 14, GUI);
		Stack("��c���� ��f���� ����", 264, 0, 1, GUIMessage.getAcceptType(p, "����"), 16, GUI);
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void userGuildGUI(Player p)
	{
		if (GuildAPI.getJoinGuild(p) != null) {
			String guild = GuildAPI.getJoinGuild(p);
			Inventory GUI = Bukkit.createInventory(null, 45, "��6��lG ��0��l" + guild);

			Stack("��6��� ���� ���", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("��c��� Ż��", 35, 14, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��6��� ��ŷ", 66, 0, 1, GUIMessage.MessageRanking, 15, GUI);
			Stack("��e��� ���� ���", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("��b��� ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 31, GUI);
			Stack("��a��� ����", 264, 0, 1, GUIMessage.MessageStat, 33, GUI);
			Stack("��d��� ����Ʈ", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("��6��l��� ����", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("��a��l��� Ȩ �̵�", 2, 0, 1, GUIMessage.LandGuild, 17, GUI);
			
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
			Inventory GUI = Bukkit.createInventory(null, 45, "��6��lG ��0��l" + guild);

			Stack("��6��� ���� ���", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("��c��� Ż��", 35, 14, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��6��� ��ŷ", 66, 0, 1, GUIMessage.MessageRanking, 15, GUI);
			Stack("��e��� ���� ���", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("��b��� ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 30, GUI);
			Stack("��a��� ����", 264, 0, 1, GUIMessage.MessageStat, 32, GUI);
			Stack("��2�÷��̾� �ʴ�", 138, 0, 1, GUIMessage.InvitePlayer, 33, GUI);
			Stack("��d��� ����Ʈ", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("��6��l��� ����", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("��a��l��� Ȩ �̵�", 2, 0, 1, GUIMessage.LandGuild, 17, GUI);
			
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
			Inventory GUI = Bukkit.createInventory(null, 45, "��6��lG ��0��l" + guild);

			Stack("��f��� �ɼ�", 368, 0, 1, GUIMessage.Option, 15, GUI);
			Stack("��6��� ���� ���", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("��c��� Ż��", 35, 14, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��e��� ���� ���", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("��b��� ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 30, GUI);
			Stack("��a��� ����", 264, 0, 1, GUIMessage.MessageStat, 32, GUI);
			Stack("��2�÷��̾� �ʴ�", 138, 0, 1, GUIMessage.InvitePlayer, 33, GUI);
			Stack("��6��� ��ŷ", 66, 0, 1, GUIMessage.MessageRanking, 31, GUI);
			Stack("��d��� ����Ʈ", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("��6��l��� ����", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("��a��l��� Ȩ �̵�", 2, 0, 1, GUIMessage.LandGuild, 17, GUI);
			
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
			Inventory GUI = Bukkit.createInventory(null, 45, "��6��lG ��0��l" + guild);

			Stack("��f��� �ɼ� ��7/ ��f���Խ�û�� ���", 368, 0, 1, GUIMessage.Option, 15, GUI);
			Stack("��6��� ���� ���", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("��d��� ����Ʈ", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("��c��� Ż��", 35, 14, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��e��� ���� ���", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("��b��� ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 30, GUI);
			Stack("��a��� ����", 264, 0, 1, GUIMessage.MessageStat, 32, GUI);
			Stack("��6��� ��ŷ", 66, 0, 1, GUIMessage.MessageRanking, 31, GUI);
			Stack("��2�÷��̾� �ʴ�", 138, 0, 1, GUIMessage.InvitePlayer, 33, GUI);
			Stack("��6��l��� ����", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("��a��l��� Ȩ �̵�", 2, 0, 1, GUIMessage.LandGuild, 17, GUI);
			
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
			Inventory GUI = Bukkit.createInventory(null, 54, "��6��lG ��0��l" + guild);

			Stack("��f��� �ɼ� ��7/ ��f���Խ�û�� ���", 368, 0, 1, GUIMessage.Option, 15, GUI);
			Stack("��6��� ���� ���", 369, 0, 1, GUIMessage.AcceptList, 8, GUI);
			Stack("��d��� ����Ʈ", 341, 0, 1, GUIMessage.getGuildPoint(p), 0, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
			Stack("��c��� Ż��", 35, 14, 1, GUIMessage.LeaveGuild, 53, GUI);
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��e��� ���� ���", 340, 0, 1, GUIMessage.MessageUserList, 29, GUI);
			Stack("��b��� ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 30, GUI);
			Stack("��a��� ����", 264, 0, 1, GUIMessage.MessageStat, 32, GUI);
			Stack("��6��� ��ŷ", 66, 0, 1, GUIMessage.MessageRanking, 31, GUI);
			Stack("��2�÷��̾� �ʴ�", 138, 0, 1, GUIMessage.InvitePlayer, 33, GUI);
			Stack("��e��� �絵", 133, 0, 1, GUIMessage.CloseGUI, 36, GUI);
			Stack("��f[��4!��f] ��4��� ���", 152, 0, 1, GUIMessage.LeaveGuild, 44, GUI);
			Stack("��6��l��� ����", 388, 0, 1, GUIMessage.Shop, 9, GUI);
			if (GuildAPI.getGuildLand(guild))
				Stack("��a��l��� �� �޴�", 2, 0, 1, GUIMessage.LandMenu, 17, GUI);
			else
				Stack("��a��� �� ����", 3, 0, 1, GUIMessage.LandCreate, 17, GUI);
			
			if (GuildAPI.isWar(guild)) {
				if (GuildAPI.isSubmit(guild)) {
					Stack("��f���� �׺� ����/����", 339, 0, 1, GUIMessage.WarAccept, 49, GUI);
				} else {
					if (!GuildAPI.isSubmit(GuildAPI.getWarGuild(guild)))
						Stack("��f���� �׺��ϱ�", 283, 0, 1, GUIMessage.WarSubmit, 49, GUI);
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
		Inventory G = Bukkit.createInventory(null, 9, "���� ��û�� ��� �ҷ�������..");
		p.openInventory(G);
		
		String name = GuildAPI.getJoinGuild(p);
		List<String> list = GuildAPI.getJoinList(name);
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// � ������ŭ ������ ������
		int y = 1;	// for�� ����
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "���Խ�û�� ��� " + num + "/" + num2);
		
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
			meta.setDisplayName("��f" + st);
			List<String> lore = new ArrayList<String>();
			if (online) lore.add("��7���� ����: ��a�¶���");
			else lore.add("��7���� ����: ��c��������");
			lore.add("��7����ġ: ��f" + ExpAPI.getExp(st));
			lore.add("��7����� ����: ��f" + me.espoo.score.Method.getPlayerScore(st));
			
			if (GuildAPI.getJoinGuild(p) != null) {
				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()) 
				 || GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("����")) {
					lore.add("��f");
					lore.add("��8��l<HELP> ��7��Ŭ���� ������ ����");
					lore.add("��7��Ŭ���� ������ ���� ����");
				}
			}
			
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, z, GUI);
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("��6���� ��� Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("��6���� ��� Ȯ��", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			Stack2("", 119, 0, 1, Arrays.asList(), x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void giveMasterListGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "���� ��� �ҷ�������..");
		p.openInventory(G);
		
		String name = GuildAPI.getJoinGuild(p);
		List<String> list = GuildAPI.getUserOrg(name);
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// � ������ŭ ������ ������
		int y = 1;	// for�� ����
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "�絵�� ���� ��� " + num + "/" + num2);
		
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
				meta.setDisplayName("��f" + st);
				List<String> lore = new ArrayList<String>();
				lore.add("��7���� ����: ��a�¶���");
				lore.add("��7��� ���: ��f" + GuildAPI.getUserClass(name, st));
				
				if (GuildAPI.getJoinGuild(p) != null) {
					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()) 
					 || GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("����")) {
						lore.add("��f");
						lore.add("��8��l<HELP> ��7Ŭ���� ��������");
						lore.add("��7��� �����͸� �絵�մϴ�.");
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
			Stack2("��6���� ��� Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("��6���� ��� Ȯ��", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			Stack2("", 119, 0, 1, Arrays.asList(), x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void userListGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "���� ��� �ҷ�������..");
		p.openInventory(G);
		
		String name = GuildAPI.getJoinGuild(p);
		List<String> list = GuildAPI.getUserOrg(name);
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// � ������ŭ ������ ������
		int y = 1;	// for�� ����
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "���� ��� " + num + "/" + num2);
		
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
			meta.setDisplayName("��f" + st);
			List<String> lore = new ArrayList<String>();
			if (online) lore.add("��7���� ����: ��a�¶���");
			else lore.add("��7���� ����: ��c��������");
			lore.add("��7��� ���: ��f" + GuildAPI.getUserClass(name, st));
			
			if (GuildAPI.getJoinGuild(p) != null) {
				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()) 
				 || GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("����")) {
					lore.add("��f");
					lore.add("��8��l<HELP> ��7����Ʈ Ŭ���� ������ ������.");
					lore.add("��7��Ŭ���� �������� �پ��� ������ �ο���");
				}
			}
			
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, z, GUI);
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("��6���� ��� Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("��6���� ��� Ȯ��", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			Stack2("", 119, 0, 1, Arrays.asList(), x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void RankingGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "��� ��ŷ �ҷ�������..");
		p.openInventory(G);
		
		List<String> list = GuildYml.ArrayGuild();
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("��c�ش� ������ �������� �ʽ��ϴ�.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// � ������ŭ ������ ������
		int y = 1;	// for�� ����
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "��� ��ŷ " + num + "/" + num2);
		
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
			meta.setDisplayName("��c" + i + ". " + meta.getDisplayName());
			List<String> lore = new ArrayList<String>();
			lore.add("��7���� ��¥: ��f" + GuildAPI.getYear(name) + "�� " + GuildAPI.getMonth(name) + "�� " + GuildAPI.getDate(name) + "��");
			for (String string : meta.getLore()) {
				lore.add(string);
			}
			
			if (GuildAPI.getJoinGuild(p) != null) {
				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
					lore.add("��f");
					lore.add("��8��l<HELP> ��7��Ŭ���� ��� ���� ������ ������.");
				}
			} else {
				lore.add("��f");
				lore.add("��8��l<HELP> ��7��Ŭ���� ���� ��û, ��� ���� ����");
			}
			
			meta.setLore(lore);
			item.setItemMeta(meta);
			Stack3(item, z, GUI);
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("��6���� ��ŷ Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("��6���� ��ŷ Ȯ��", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
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
