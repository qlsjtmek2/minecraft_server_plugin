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
		Inventory GUI = Bukkit.createInventory(null, 36, G.getMessage(p, "금융 GUI 이름"));
		
		if (G.getLoreList(p, "돈.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "돈.아이템 이름"), G.getItemCode("돈.아이템 코드"), G.getItemCode("돈.아이템 데이터코드"), 
				   G.getItemCode("돈.아이템 수량"), Arrays.asList(), 35, GUI);
		else
			Stack(G.getMessage(p, "돈.아이템 이름"), G.getItemCode("돈.아이템 코드"), G.getItemCode("돈.아이템 데이터코드"), 
				  G.getItemCode("돈.아이템 수량"), G.getLoreList(p, "돈.아이템 설명"), 35, GUI);
		
		
		if (G.getLoreList(p, "대출.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "대출.아이템 이름"), G.getItemCode("대출.아이템 코드"), G.getItemCode("대출.아이템 데이터코드"), 
				   G.getItemCode("대출.아이템 수량"), Arrays.asList(), 11, GUI);
		else
			Stack(G.getMessage(p, "대출.아이템 이름"), G.getItemCode("대출.아이템 코드"), G.getItemCode("대출.아이템 데이터코드"), 
				  G.getItemCode("대출.아이템 수량"), G.getLoreList(p, "대출.아이템 설명"), 11, GUI);
		if (G.getLoreList(p, "예금.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "예금.아이템 이름"), G.getItemCode("예금.아이템 코드"), G.getItemCode("예금.아이템 데이터코드"), 
				   G.getItemCode("예금.아이템 수량"), Arrays.asList(), 13, GUI);
		else
			Stack(G.getMessage(p, "예금.아이템 이름"), G.getItemCode("예금.아이템 코드"), G.getItemCode("예금.아이템 데이터코드"), 
				  G.getItemCode("예금.아이템 수량"), G.getLoreList(p, "예금.아이템 설명"), 13, GUI);
		if (G.getLoreList(p, "투자.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "투자.아이템 이름"), G.getItemCode("투자.아이템 코드"), G.getItemCode("투자.아이템 데이터코드"), 
				   G.getItemCode("투자.아이템 수량"), Arrays.asList(), 15, GUI);
		else
			Stack(G.getMessage(p, "투자.아이템 이름"), G.getItemCode("투자.아이템 코드"), G.getItemCode("투자.아이템 데이터코드"), 
				  G.getItemCode("투자.아이템 수량"), G.getLoreList(p, "투자.아이템 설명"), 15, GUI);
		
		if (PlayerYml.getInfoInt(p, "대출 타이머") == 0 && PlayerYml.getInfoBoolean(p, "대출 완료") == false) {
			int i = PlayerYml.getInfoInt(p, "대출값") + 1;
			if (B.getBorrowString(i + ".이름") != null) {
				if (G.getLoreBorrow(p, "대출설정.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "대출설정.아이템 이름색깔") + B.getBorrowString(i + ".이름"), G.getItemCode("대출설정.아이템 코드"), 
						   G.getItemCode("대출설정.아이템 데이터코드"), G.getItemCode("대출설정.아이템 수량"), Arrays.asList(), 20, GUI);
				else
					Stack(G.getMessage(p, "대출설정.아이템 이름색깔") + B.getBorrowString(i + ".이름"), G.getItemCode("대출설정.아이템 코드"), 
						  G.getItemCode("대출설정.아이템 데이터코드"), G.getItemCode("대출설정.아이템 수량"), G.getLoreBorrow(p, "대출설정.아이템 설명"), 20, GUI);
			} else {
				if (B.getLoreList(p, "default.설명").size() == 0)
					Stack2(B.getBorrowString("default.이름"), B.getBorrowInt("default.아이템 코드"), 
						   B.getBorrowInt("default.아이템 데이터코드"), B.getBorrowInt("default.아이템 수량"), Arrays.asList(), 20, GUI);
				else
					Stack(B.getBorrowString("default.이름"), B.getBorrowInt("default.아이템 코드"), 
						  B.getBorrowInt("default.아이템 데이터코드"), B.getBorrowInt("default.아이템 수량"), B.getLoreList(p, "default.설명"), 20, GUI);
			}
		} else if (PlayerYml.getInfoInt(p, "대출 타이머") != 0 && PlayerYml.getInfoBoolean(p, "대출 완료") == false) {
			int i = PlayerYml.getInfoInt(p, "대출값") + 1;
			if (B.getBorrowString(i + ".이름") != null) {
				if (G.getLoreBorrow(p, "대출중.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "대출중.아이템 이름색깔") + B.getBorrowString(i + ".이름"), G.getItemCode("대출중.아이템 코드"), 
						   G.getItemCode("대출중.아이템 데이터코드"), G.getItemCode("대출중.아이템 수량"), Arrays.asList(), 20, GUI);
				else
					Stack(G.getMessage(p, "대출중.아이템 이름색깔") + B.getBorrowString(i + ".이름"), G.getItemCode("대출중.아이템 코드"), 
						  G.getItemCode("대출중.아이템 데이터코드"), G.getItemCode("대출중.아이템 수량"), G.getLoreBorrow(p, "대출중.아이템 설명"), 20, GUI);
			}
		} else if (PlayerYml.getInfoBoolean(p, "대출 완료") == true) {
			int i = PlayerYml.getInfoInt(p, "대출값") + 1;
			if (B.getBorrowString(i + ".이름") != null) {
				if (G.getLoreBorrow(p, "대출완료.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "대출완료.아이템 이름색깔") + B.getBorrowString(i + ".이름"), G.getItemCode("대출완료.아이템 코드"), 
			   G.getItemCode("대출완료.아이템 데이터코드"), G.getItemCode("대출완료.아이템 수량"), Arrays.asList(), 20, GUI);
				else
					Stack(G.getMessage(p, "대출완료.아이템 이름색깔") + B.getBorrowString(i + ".이름"), G.getItemCode("대출완료.아이템 코드"), 
			  G.getItemCode("대출완료.아이템 데이터코드"), G.getItemCode("대출완료.아이템 수량"), G.getLoreBorrow(p, "대출완료.아이템 설명"), 20, GUI);
			}
		}
		
		if (PlayerYml.getInfoInt(p, "예금 타이머") == 0 && PlayerYml.getInfoBoolean(p, "예금 완료") == false) {
			int i = PlayerYml.getInfoInt(p, "예금값") + 1;
			if (I.getInvestString(i + ".이름") != null) {
				if (G.getLoreInvest(p, "예금설정.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "예금설정.아이템 이름색깔") + I.getInvestString(i + ".이름"), G.getItemCode("예금설정.아이템 코드"), 
						   G.getItemCode("예금설정.아이템 데이터코드"), G.getItemCode("예금설정.아이템 수량"), Arrays.asList(), 22, GUI);
				else
					Stack(G.getMessage(p, "예금설정.아이템 이름색깔") + I.getInvestString(i + ".이름"), G.getItemCode("예금설정.아이템 코드"), 
						  G.getItemCode("예금설정.아이템 데이터코드"), G.getItemCode("예금설정.아이템 수량"), G.getLoreInvest(p, "예금설정.아이템 설명"), 22, GUI);
			} else {
				if (I.getLoreList(p, "default.설명").size() == 0)
					Stack2(I.getInvestString("default.이름"), I.getInvestInt("default.아이템 코드"), 
						   I.getInvestInt("default.아이템 데이터코드"), I.getInvestInt("default.아이템 수량"), Arrays.asList(), 22, GUI);
				else
					Stack(I.getInvestString("default.이름"), I.getInvestInt("default.아이템 코드"), 
						  I.getInvestInt("default.아이템 데이터코드"), I.getInvestInt("default.아이템 수량"), I.getLoreList(p, "default.설명"), 22, GUI);
			}
		} else if (PlayerYml.getInfoInt(p, "예금 타이머") != 0 && PlayerYml.getInfoBoolean(p, "예금 완료") == false) {
			int i = PlayerYml.getInfoInt(p, "예금값") + 1;
			if (I.getInvestString(i + ".이름") != null) {
				if (G.getLoreInvest(p, "예금중.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "예금중.아이템 이름색깔") + I.getInvestString(i + ".이름"), G.getItemCode("예금중.아이템 코드"), 
						   G.getItemCode("예금중.아이템 데이터코드"), G.getItemCode("예금중.아이템 수량"), Arrays.asList(), 22, GUI);
				else
					Stack(G.getMessage(p, "예금중.아이템 이름색깔") + I.getInvestString(i + ".이름"), G.getItemCode("예금중.아이템 코드"), 
						  G.getItemCode("예금중.아이템 데이터코드"), G.getItemCode("예금중.아이템 수량"), G.getLoreInvest(p, "예금중.아이템 설명"), 22, GUI);
			}
		} else if (PlayerYml.getInfoBoolean(p, "예금 완료") == true) {
			int i = PlayerYml.getInfoInt(p, "예금값") + 1;
			if (I.getInvestString(i + ".이름") != null) {
				if (G.getLoreInvest(p, "예금완료.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "예금완료.아이템 이름색깔") + I.getInvestString(i + ".이름"), G.getItemCode("예금완료.아이템 코드"), 
			   G.getItemCode("예금완료.아이템 데이터코드"), G.getItemCode("예금완료.아이템 수량"), Arrays.asList(), 22, GUI);
				else
					Stack(G.getMessage(p, "예금완료.아이템 이름색깔") + I.getInvestString(i + ".이름"), G.getItemCode("예금완료.아이템 코드"), 
			  G.getItemCode("예금완료.아이템 데이터코드"), G.getItemCode("예금완료.아이템 수량"), G.getLoreInvest(p, "예금완료.아이템 설명"), 22, GUI);
			}
		}
		
		if (PlayerYml.getInfoInt(p, "투자 타이머") == 0 && PlayerYml.getInfoBoolean(p, "투자 완료") == false) {
			int i = PlayerYml.getInfoInt(p, "투자값") + 1;
			if (D.getDepositString(i + ".이름") != null) {
				if (G.getLoreDeposit(p, "투자설정.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "투자설정.아이템 이름색깔") + D.getDepositString(i + ".이름"), G.getItemCode("투자설정.아이템 코드"), 
						   G.getItemCode("투자설정.아이템 데이터코드"), G.getItemCode("투자설정.아이템 수량"), Arrays.asList(), 24, GUI);
				else
					Stack(G.getMessage(p, "투자설정.아이템 이름색깔") + D.getDepositString(i + ".이름"), G.getItemCode("투자설정.아이템 코드"), 
						  G.getItemCode("투자설정.아이템 데이터코드"), G.getItemCode("투자설정.아이템 수량"), G.getLoreDeposit(p, "투자설정.아이템 설명"), 24, GUI);
			} else {
				if (I.getLoreList(p, "default.설명").size() == 0)
					Stack2(D.getDepositString("default.이름"), D.getDepositInt("default.아이템 코드"), 
						   D.getDepositInt("default.아이템 데이터코드"), D.getDepositInt("default.아이템 수량"), Arrays.asList(), 24, GUI);
				else
					Stack(D.getDepositString("default.이름"), D.getDepositInt("default.아이템 코드"), 
						  D.getDepositInt("default.아이템 데이터코드"), D.getDepositInt("default.아이템 수량"), I.getLoreList(p, "default.설명"), 24, GUI);
			}
		} else if (PlayerYml.getInfoInt(p, "투자 타이머") != 0 && PlayerYml.getInfoBoolean(p, "투자 완료") == false) {
			int i = PlayerYml.getInfoInt(p, "투자값") + 1;
			if (D.getDepositString(i + ".이름") != null) {
				if (G.getLoreDeposit(p, "투자중.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "투자중.아이템 이름색깔") + D.getDepositString(i + ".이름"), G.getItemCode("투자중.아이템 코드"), 
						   G.getItemCode("투자중.아이템 데이터코드"), G.getItemCode("투자중.아이템 수량"), Arrays.asList(), 24, GUI);
				else
					Stack(G.getMessage(p, "투자중.아이템 이름색깔") + D.getDepositString(i + ".이름"), G.getItemCode("투자중.아이템 코드"), 
						  G.getItemCode("투자중.아이템 데이터코드"), G.getItemCode("투자중.아이템 수량"), G.getLoreDeposit(p, "투자중.아이템 설명"), 24, GUI);
			}
		} else if (PlayerYml.getInfoBoolean(p, "투자 완료") == true) {
			int i = PlayerYml.getInfoInt(p, "투자값") + 1;
			if (D.getDepositString(i + ".이름") != null) {
				if (G.getLoreDeposit(p, "투자완료.아이템 설명").size() == 0)
					Stack2(G.getMessage(p, "투자완료.아이템 이름색깔") + D.getDepositString(i + ".이름"), G.getItemCode("투자완료.아이템 코드"), 
			   G.getItemCode("투자완료.아이템 데이터코드"), G.getItemCode("투자완료.아이템 수량"), Arrays.asList(), 24, GUI);
				else
					Stack(G.getMessage(p, "투자완료.아이템 이름색깔") + D.getDepositString(i + ".이름"), G.getItemCode("투자완료.아이템 코드"), 
			  G.getItemCode("투자완료.아이템 데이터코드"), G.getItemCode("투자완료.아이템 수량"), G.getLoreDeposit(p, "투자완료.아이템 설명"), 24, GUI);
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
