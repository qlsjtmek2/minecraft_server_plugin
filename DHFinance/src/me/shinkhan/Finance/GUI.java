package me.shinkhan.Finance;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.shinkhan.Finance.config.Borrow;
import me.shinkhan.Finance.config.Deposit;
import me.shinkhan.Finance.config.GUIMessage;
import me.shinkhan.Finance.config.Invest;
import me.shinkhan.Finance.config.Message;
import me.shinkhan.Finance.config.PlayerYml;

public class GUI {
	static Message E;
	static GUIMessage G;
	static Borrow B;
	static Deposit D;
	static Invest I;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, G.getMessage(p, "���� GUI �̸�"));
		
		if (G.getLoreList(p, "��.������ ����").size() == 0)
			Stack2(G.getMessage(p, "��.������ �̸�"), G.getItemCode("��.������ �ڵ�"), G.getItemCode("��.������ �������ڵ�"), 
				   G.getItemCode("��.������ ����"), Arrays.asList(), 35, GUI);
		else
			Stack(G.getMessage(p, "��.������ �̸�"), G.getItemCode("��.������ �ڵ�"), G.getItemCode("��.������ �������ڵ�"), 
				  G.getItemCode("��.������ ����"), G.getLoreList(p, "��.������ ����"), 35, GUI);
		
		
		if (G.getLoreList(p, "����.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				   G.getItemCode("����.������ ����"), Arrays.asList(), 11, GUI);
		else
			Stack(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				  G.getItemCode("����.������ ����"), G.getLoreList(p, "����.������ ����"), 11, GUI);
		if (G.getLoreList(p, "����.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				   G.getItemCode("����.������ ����"), Arrays.asList(), 13, GUI);
		else
			Stack(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				  G.getItemCode("����.������ ����"), G.getLoreList(p, "����.������ ����"), 13, GUI);
		if (G.getLoreList(p, "����.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				   G.getItemCode("����.������ ����"), Arrays.asList(), 15, GUI);
		else
			Stack(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				  G.getItemCode("����.������ ����"), G.getLoreList(p, "����.������ ����"), 15, GUI);
		
		if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") == 0 && PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == false) {
			int i = PlayerYml.getInfoInt(p, "���Ⱚ") + 1;
			if (B.getBorrowString(i + ".�̸�") != null) {
				if (G.getLoreBorrow(p, "���⼳��.������ ����").size() == 0)
					Stack2(G.getMessage(p, "���⼳��.������ �̸�����") + B.getBorrowString(i + ".�̸�"), G.getItemCode("���⼳��.������ �ڵ�"), 
						   G.getItemCode("���⼳��.������ �������ڵ�"), G.getItemCode("���⼳��.������ ����"), Arrays.asList(), 20, GUI);
				else
					Stack(G.getMessage(p, "���⼳��.������ �̸�����") + B.getBorrowString(i + ".�̸�"), G.getItemCode("���⼳��.������ �ڵ�"), 
						  G.getItemCode("���⼳��.������ �������ڵ�"), G.getItemCode("���⼳��.������ ����"), G.getLoreBorrow(p, "���⼳��.������ ����"), 20, GUI);
			} else {
				if (B.getLoreList(p, "default.����").size() == 0)
					Stack2(B.getBorrowString("default.�̸�"), B.getBorrowInt("default.������ �ڵ�"), 
						   B.getBorrowInt("default.������ �������ڵ�"), B.getBorrowInt("default.������ ����"), Arrays.asList(), 20, GUI);
				else
					Stack(B.getBorrowString("default.�̸�"), B.getBorrowInt("default.������ �ڵ�"), 
						  B.getBorrowInt("default.������ �������ڵ�"), B.getBorrowInt("default.������ ����"), B.getLoreList(p, "default.����"), 20, GUI);
			}
		} else if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") != 0 && PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == false) {
			int i = PlayerYml.getInfoInt(p, "���Ⱚ") + 1;
			if (B.getBorrowString(i + ".�̸�") != null) {
				if (G.getLoreBorrow(p, "������.������ ����").size() == 0)
					Stack2(G.getMessage(p, "������.������ �̸�����") + B.getBorrowString(i + ".�̸�"), G.getItemCode("������.������ �ڵ�"), 
						   G.getItemCode("������.������ �������ڵ�"), G.getItemCode("������.������ ����"), Arrays.asList(), 20, GUI);
				else
					Stack(G.getMessage(p, "������.������ �̸�����") + B.getBorrowString(i + ".�̸�"), G.getItemCode("������.������ �ڵ�"), 
						  G.getItemCode("������.������ �������ڵ�"), G.getItemCode("������.������ ����"), G.getLoreBorrow(p, "������.������ ����"), 20, GUI);
			}
		} else if (PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == true) {
			int i = PlayerYml.getInfoInt(p, "���Ⱚ") + 1;
			if (B.getBorrowString(i + ".�̸�") != null) {
				if (G.getLoreBorrow(p, "����Ϸ�.������ ����").size() == 0)
					Stack2(G.getMessage(p, "����Ϸ�.������ �̸�����") + B.getBorrowString(i + ".�̸�"), G.getItemCode("����Ϸ�.������ �ڵ�"), 
			   G.getItemCode("����Ϸ�.������ �������ڵ�"), G.getItemCode("����Ϸ�.������ ����"), Arrays.asList(), 20, GUI);
				else
					Stack(G.getMessage(p, "����Ϸ�.������ �̸�����") + B.getBorrowString(i + ".�̸�"), G.getItemCode("����Ϸ�.������ �ڵ�"), 
			  G.getItemCode("����Ϸ�.������ �������ڵ�"), G.getItemCode("����Ϸ�.������ ����"), G.getLoreBorrow(p, "����Ϸ�.������ ����"), 20, GUI);
			}
		}
		
		if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") == 0 && PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == false) {
			int i = PlayerYml.getInfoInt(p, "���ݰ�") + 1;
			if (I.getInvestString(i + ".�̸�") != null) {
				if (G.getLoreInvest(p, "���ݼ���.������ ����").size() == 0)
					Stack2(G.getMessage(p, "���ݼ���.������ �̸�����") + I.getInvestString(i + ".�̸�"), G.getItemCode("���ݼ���.������ �ڵ�"), 
						   G.getItemCode("���ݼ���.������ �������ڵ�"), G.getItemCode("���ݼ���.������ ����"), Arrays.asList(), 22, GUI);
				else
					Stack(G.getMessage(p, "���ݼ���.������ �̸�����") + I.getInvestString(i + ".�̸�"), G.getItemCode("���ݼ���.������ �ڵ�"), 
						  G.getItemCode("���ݼ���.������ �������ڵ�"), G.getItemCode("���ݼ���.������ ����"), G.getLoreInvest(p, "���ݼ���.������ ����"), 22, GUI);
			} else {
				if (I.getLoreList(p, "default.����").size() == 0)
					Stack2(I.getInvestString("default.�̸�"), I.getInvestInt("default.������ �ڵ�"), 
						   I.getInvestInt("default.������ �������ڵ�"), I.getInvestInt("default.������ ����"), Arrays.asList(), 22, GUI);
				else
					Stack(I.getInvestString("default.�̸�"), I.getInvestInt("default.������ �ڵ�"), 
						  I.getInvestInt("default.������ �������ڵ�"), I.getInvestInt("default.������ ����"), I.getLoreList(p, "default.����"), 22, GUI);
			}
		} else if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") != 0 && PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == false) {
			int i = PlayerYml.getInfoInt(p, "���ݰ�") + 1;
			if (I.getInvestString(i + ".�̸�") != null) {
				if (G.getLoreInvest(p, "������.������ ����").size() == 0)
					Stack2(G.getMessage(p, "������.������ �̸�����") + I.getInvestString(i + ".�̸�"), G.getItemCode("������.������ �ڵ�"), 
						   G.getItemCode("������.������ �������ڵ�"), G.getItemCode("������.������ ����"), Arrays.asList(), 22, GUI);
				else
					Stack(G.getMessage(p, "������.������ �̸�����") + I.getInvestString(i + ".�̸�"), G.getItemCode("������.������ �ڵ�"), 
						  G.getItemCode("������.������ �������ڵ�"), G.getItemCode("������.������ ����"), G.getLoreInvest(p, "������.������ ����"), 22, GUI);
			}
		} else if (PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == true) {
			int i = PlayerYml.getInfoInt(p, "���ݰ�") + 1;
			if (I.getInvestString(i + ".�̸�") != null) {
				if (G.getLoreInvest(p, "���ݿϷ�.������ ����").size() == 0)
					Stack2(G.getMessage(p, "���ݿϷ�.������ �̸�����") + I.getInvestString(i + ".�̸�"), G.getItemCode("���ݿϷ�.������ �ڵ�"), 
			   G.getItemCode("���ݿϷ�.������ �������ڵ�"), G.getItemCode("���ݿϷ�.������ ����"), Arrays.asList(), 22, GUI);
				else
					Stack(G.getMessage(p, "���ݿϷ�.������ �̸�����") + I.getInvestString(i + ".�̸�"), G.getItemCode("���ݿϷ�.������ �ڵ�"), 
			  G.getItemCode("���ݿϷ�.������ �������ڵ�"), G.getItemCode("���ݿϷ�.������ ����"), G.getLoreInvest(p, "���ݿϷ�.������ ����"), 22, GUI);
			}
		}
		
		if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") == 0 && PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == false) {
			int i = PlayerYml.getInfoInt(p, "���ڰ�") + 1;
			if (D.getDepositString(i + ".�̸�") != null) {
				if (G.getLoreDeposit(p, "���ڼ���.������ ����").size() == 0)
					Stack2(G.getMessage(p, "���ڼ���.������ �̸�����") + D.getDepositString(i + ".�̸�"), G.getItemCode("���ڼ���.������ �ڵ�"), 
						   G.getItemCode("���ڼ���.������ �������ڵ�"), G.getItemCode("���ڼ���.������ ����"), Arrays.asList(), 24, GUI);
				else
					Stack(G.getMessage(p, "���ڼ���.������ �̸�����") + D.getDepositString(i + ".�̸�"), G.getItemCode("���ڼ���.������ �ڵ�"), 
						  G.getItemCode("���ڼ���.������ �������ڵ�"), G.getItemCode("���ڼ���.������ ����"), G.getLoreDeposit(p, "���ڼ���.������ ����"), 24, GUI);
			} else {
				if (I.getLoreList(p, "default.����").size() == 0)
					Stack2(D.getDepositString("default.�̸�"), D.getDepositInt("default.������ �ڵ�"), 
						   D.getDepositInt("default.������ �������ڵ�"), D.getDepositInt("default.������ ����"), Arrays.asList(), 24, GUI);
				else
					Stack(D.getDepositString("default.�̸�"), D.getDepositInt("default.������ �ڵ�"), 
						  D.getDepositInt("default.������ �������ڵ�"), D.getDepositInt("default.������ ����"), I.getLoreList(p, "default.����"), 24, GUI);
			}
		} else if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") != 0 && PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == false) {
			int i = PlayerYml.getInfoInt(p, "���ڰ�") + 1;
			if (D.getDepositString(i + ".�̸�") != null) {
				if (G.getLoreDeposit(p, "������.������ ����").size() == 0)
					Stack2(G.getMessage(p, "������.������ �̸�����") + D.getDepositString(i + ".�̸�"), G.getItemCode("������.������ �ڵ�"), 
						   G.getItemCode("������.������ �������ڵ�"), G.getItemCode("������.������ ����"), Arrays.asList(), 24, GUI);
				else
					Stack(G.getMessage(p, "������.������ �̸�����") + D.getDepositString(i + ".�̸�"), G.getItemCode("������.������ �ڵ�"), 
						  G.getItemCode("������.������ �������ڵ�"), G.getItemCode("������.������ ����"), G.getLoreDeposit(p, "������.������ ����"), 24, GUI);
			}
		} else if (PlayerYml.getInfoBoolean(p, "���� �Ϸ�") == true) {
			int i = PlayerYml.getInfoInt(p, "���ڰ�") + 1;
			if (D.getDepositString(i + ".�̸�") != null) {
				if (G.getLoreDeposit(p, "���ڿϷ�.������ ����").size() == 0)
					Stack2(G.getMessage(p, "���ڿϷ�.������ �̸�����") + D.getDepositString(i + ".�̸�"), G.getItemCode("���ڿϷ�.������ �ڵ�"), 
			   G.getItemCode("���ڿϷ�.������ �������ڵ�"), G.getItemCode("���ڿϷ�.������ ����"), Arrays.asList(), 24, GUI);
				else
					Stack(G.getMessage(p, "���ڿϷ�.������ �̸�����") + D.getDepositString(i + ".�̸�"), G.getItemCode("���ڿϷ�.������ �ڵ�"), 
			  G.getItemCode("���ڿϷ�.������ �������ڵ�"), G.getItemCode("���ڿϷ�.������ ����"), G.getLoreDeposit(p, "���ڿϷ�.������ ����"), 24, GUI);
			}
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
}
