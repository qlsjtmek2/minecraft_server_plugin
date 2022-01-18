package me.shinkhan.Finance;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.shinkhan.Finance.config.Borrow;
import me.shinkhan.Finance.config.Deposit;
import me.shinkhan.Finance.config.GUIMessage;
import me.shinkhan.Finance.config.Invest;
import me.shinkhan.Finance.config.Message;
import me.shinkhan.Finance.config.PlayerYml;

public class Event extends JavaPlugin implements Listener {
	Main M;
	Message E;
	GUIMessage G;
	PlayerYml P;
	Borrow B;
	Invest I;
	Deposit D;
		
	public Event(Main main)
	{
		this.M = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/DHFinance");
		File folder2 = new File("plugins/DHFinance/Player");
		File f = new File("plugins/DHFinance/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) PlayerYml.CreatePlayerInfo(f, folder, folder2, config);
	}
	
    @SuppressWarnings("static-access")
	@EventHandler
    public void onClickItem(InventoryClickEvent e) {
    	Player p = (Player) e.getWhoClicked();
    	if (e.getInventory().getName().equalsIgnoreCase(G.getMessage(p, "금융 GUI 이름"))) {
    		e.setCancelled(true);
    		
    		if (e.getCurrentItem() == null) return;
    		
    		if (e.getCurrentItem().getTypeId() == G.getItemCode("대출설정.아이템 코드") || e.getCurrentItem().getTypeId() == G.getItemCode("예금설정.아이템 코드") || 
    			e.getCurrentItem().getTypeId() == G.getItemCode("투자설정.아이템 코드")) {
    			
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(B.getBorrowString("default.이름")) ||
    				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(I.getInvestString("default.이름")) ||
    				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(D.getDepositString("default.이름"))) return;
    			
    			int x = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("대출")) break;
    				else { x++; continue; }
    			}
    			
    			int y = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("예금")) break;
    				else { y++; continue; }
    			}
    			
    			int z = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("투자")) break;
    				else { z++; continue; }
    			}
    			
    			if (e.getCurrentItem().getItemMeta().getLore().size() != x) {
    				int i = PlayerYml.getInfoInt(p, "대출값") + 1;
    				P.setInfoInt(p, "대출 타이머", B.getBorrowInt(i + ".상환시간 (분)"));
    				Main.economy.depositPlayer(p.getName(), B.getBorrowInt(i + ".대출가능 금액"));
    				p.closeInventory();
    				p.sendMessage(E.getMessage("대출 시작"));
    				return;
    			}
    			
    			else if (e.getCurrentItem().getItemMeta().getLore().size() != y) {
    				int i = PlayerYml.getInfoInt(p, "예금값") + 1;
    				
    				if (I.getInvestInt(i + ".예금가능 금액") <= Main.economy.getBalance(p.getName())) {
        				P.setInfoInt(p, "예금 타이머", I.getInvestInt(i + ".만기시간 (분)"));
        				Main.economy.withdrawPlayer(p.getName(), I.getInvestInt(i + ".예금가능 금액"));
        				p.closeInventory();
        				p.sendMessage(E.getMessage("예금 시작"));
        				return;
    				} else {
    					p.closeInventory();
    					p.sendMessage(E.getMessage("예금 경고"));
    					return;
    				}
    			}
    			
    			else if (e.getCurrentItem().getItemMeta().getLore().size() != z) {
    				int i = PlayerYml.getInfoInt(p, "투자값") + 1;
    				
    				if (D.getDepositInt(i + ".투자가능 금액") <= Main.economy.getBalance(p.getName())) {
        				P.setInfoInt(p, "투자 타이머", D.getDepositInt(i + ".만기시간 (분)"));
        				Main.economy.withdrawPlayer(p.getName(), D.getDepositInt(i + ".투자가능 금액"));
        				p.closeInventory();
        				p.sendMessage(E.getMessage("투자 시작"));
        				return;
    				} else {
    					p.closeInventory();
    					p.sendMessage(E.getMessage("투자 경고"));
    					return;
    				}
    			}
    		}
    		
    		if (e.getCurrentItem().getTypeId() == G.getItemCode("대출중.아이템 코드")) {
        			
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(B.getBorrowString("default.이름")) ||
    				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(I.getInvestString("default.이름")) ||
        			e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(D.getDepositString("default.이름"))) return;
        			
    			int x = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("대출")) break;
    				else { x++; continue; }
    			}
        			
    			if (e.getCurrentItem().getItemMeta().getLore().size() != x) {
    				int i = PlayerYml.getInfoInt(p, "대출값") + 1;
    				
    				if (B.getBorrowInt(i + ".상환금") <= Main.economy.getBalance(p.getName())) {
    					int num = P.getInfoInt(p, "대출값") + 1;
        				P.setInfoInt(p, "대출값", num);
        				P.setInfoInt(p, "대출 타이머", 0);
        				Main.economy.withdrawPlayer(p.getName(), B.getBorrowInt(i + ".상환금"));
        				p.closeInventory();
        				p.sendMessage(E.getMessage("대출 종료"));
        				return;
    				} else {
    					p.closeInventory();
    					p.sendMessage(E.getMessage("대출 경고"));
    					return;
    				}
    			}
    		}
    		
    		else if (e.getCurrentItem().getTypeId() == G.getItemCode("대출완료.아이템 코드") || e.getCurrentItem().getTypeId() == G.getItemCode("예금완료.아이템 코드") || 
        			e.getCurrentItem().getTypeId() == G.getItemCode("투자완료.아이템 코드")) {
    			
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(B.getBorrowString("default.이름")) ||
        				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(I.getInvestString("default.이름")) ||
        				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(D.getDepositString("default.이름"))) return;
        			
    			int x = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("대출")) break;
    				else { x++; continue; }
    			}
        			
    			int y = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("예금")) break;
    				else { y++; continue; }
    			}
        			
    			int z = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("투자")) break;
    				else { z++; continue; }
    			}
    			
    			if (e.getCurrentItem().getItemMeta().getLore().size() != x) {
    				int i = PlayerYml.getInfoInt(p, "대출값") + 1;
    				
    				if (B.getBorrowInt(i + ".상환이자") <= Main.economy.getBalance(p.getName())) {
    					int num = P.getInfoInt(p, "대출값") + 1;
        				P.setInfoInt(p, "대출값", num);
        				P.setInfoInt(p, "대출 타이머", 0);
        				P.setInfoBoolean(p, "대출 완료", false);
        				Main.economy.withdrawPlayer(p.getName(), B.getBorrowInt(i + ".상환이자"));
        				p.closeInventory();
        				p.sendMessage(E.getMessage("대출 종료"));
        				return;
    				} else {
    					p.closeInventory();
    					p.sendMessage(E.getMessage("대출 경고"));
    					return;
    				}
    			}
    			
    			else if (e.getCurrentItem().getItemMeta().getLore().size() != y) {
					int num = P.getInfoInt(p, "예금값") + 1;
    				int i = PlayerYml.getInfoInt(p, "예금값") + 1;
    				P.setInfoInt(p, "예금값", num);
    				P.setInfoBoolean(p, "예금 완료", false);
    				P.setInfoInt(p, "예금 타이머", 0);
    				Main.economy.depositPlayer(p.getName(), I.getInvestInt(i + ".만기 예금"));
    				p.closeInventory();
    				p.sendMessage(E.getMessage("예금 종료"));
    			}
    			
    			else if (e.getCurrentItem().getItemMeta().getLore().size() != z) {
					int num = P.getInfoInt(p, "투자값") + 1;
    				int i = PlayerYml.getInfoInt(p, "투자값") + 1;
    				P.setInfoInt(p, "투자값", num);
    				P.setInfoBoolean(p, "투자 완료", false);
    				P.setInfoInt(p, "투자 타이머", 0);
    				Main.economy.depositPlayer(p.getName(), D.getDepositInt(i + ".만기 투자금"));
    				p.closeInventory();
    				p.sendMessage(E.getMessage("투자 종료"));
    			}
    		}
    	} else {
    		return;
    	}
    }
}
