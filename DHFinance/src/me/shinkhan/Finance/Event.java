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
    	if (e.getInventory().getName().equalsIgnoreCase(G.getMessage(p, "���� GUI �̸�"))) {
    		e.setCancelled(true);
    		
    		if (e.getCurrentItem() == null) return;
    		
    		if (e.getCurrentItem().getTypeId() == G.getItemCode("���⼳��.������ �ڵ�") || e.getCurrentItem().getTypeId() == G.getItemCode("���ݼ���.������ �ڵ�") || 
    			e.getCurrentItem().getTypeId() == G.getItemCode("���ڼ���.������ �ڵ�")) {
    			
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(B.getBorrowString("default.�̸�")) ||
    				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(I.getInvestString("default.�̸�")) ||
    				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(D.getDepositString("default.�̸�"))) return;
    			
    			int x = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("����")) break;
    				else { x++; continue; }
    			}
    			
    			int y = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("����")) break;
    				else { y++; continue; }
    			}
    			
    			int z = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("����")) break;
    				else { z++; continue; }
    			}
    			
    			if (e.getCurrentItem().getItemMeta().getLore().size() != x) {
    				int i = PlayerYml.getInfoInt(p, "���Ⱚ") + 1;
    				P.setInfoInt(p, "���� Ÿ�̸�", B.getBorrowInt(i + ".��ȯ�ð� (��)"));
    				Main.economy.depositPlayer(p.getName(), B.getBorrowInt(i + ".���Ⱑ�� �ݾ�"));
    				p.closeInventory();
    				p.sendMessage(E.getMessage("���� ����"));
    				return;
    			}
    			
    			else if (e.getCurrentItem().getItemMeta().getLore().size() != y) {
    				int i = PlayerYml.getInfoInt(p, "���ݰ�") + 1;
    				
    				if (I.getInvestInt(i + ".���ݰ��� �ݾ�") <= Main.economy.getBalance(p.getName())) {
        				P.setInfoInt(p, "���� Ÿ�̸�", I.getInvestInt(i + ".����ð� (��)"));
        				Main.economy.withdrawPlayer(p.getName(), I.getInvestInt(i + ".���ݰ��� �ݾ�"));
        				p.closeInventory();
        				p.sendMessage(E.getMessage("���� ����"));
        				return;
    				} else {
    					p.closeInventory();
    					p.sendMessage(E.getMessage("���� ���"));
    					return;
    				}
    			}
    			
    			else if (e.getCurrentItem().getItemMeta().getLore().size() != z) {
    				int i = PlayerYml.getInfoInt(p, "���ڰ�") + 1;
    				
    				if (D.getDepositInt(i + ".���ڰ��� �ݾ�") <= Main.economy.getBalance(p.getName())) {
        				P.setInfoInt(p, "���� Ÿ�̸�", D.getDepositInt(i + ".����ð� (��)"));
        				Main.economy.withdrawPlayer(p.getName(), D.getDepositInt(i + ".���ڰ��� �ݾ�"));
        				p.closeInventory();
        				p.sendMessage(E.getMessage("���� ����"));
        				return;
    				} else {
    					p.closeInventory();
    					p.sendMessage(E.getMessage("���� ���"));
    					return;
    				}
    			}
    		}
    		
    		if (e.getCurrentItem().getTypeId() == G.getItemCode("������.������ �ڵ�")) {
        			
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(B.getBorrowString("default.�̸�")) ||
    				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(I.getInvestString("default.�̸�")) ||
        			e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(D.getDepositString("default.�̸�"))) return;
        			
    			int x = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("����")) break;
    				else { x++; continue; }
    			}
        			
    			if (e.getCurrentItem().getItemMeta().getLore().size() != x) {
    				int i = PlayerYml.getInfoInt(p, "���Ⱚ") + 1;
    				
    				if (B.getBorrowInt(i + ".��ȯ��") <= Main.economy.getBalance(p.getName())) {
    					int num = P.getInfoInt(p, "���Ⱚ") + 1;
        				P.setInfoInt(p, "���Ⱚ", num);
        				P.setInfoInt(p, "���� Ÿ�̸�", 0);
        				Main.economy.withdrawPlayer(p.getName(), B.getBorrowInt(i + ".��ȯ��"));
        				p.closeInventory();
        				p.sendMessage(E.getMessage("���� ����"));
        				return;
    				} else {
    					p.closeInventory();
    					p.sendMessage(E.getMessage("���� ���"));
    					return;
    				}
    			}
    		}
    		
    		else if (e.getCurrentItem().getTypeId() == G.getItemCode("����Ϸ�.������ �ڵ�") || e.getCurrentItem().getTypeId() == G.getItemCode("���ݿϷ�.������ �ڵ�") || 
        			e.getCurrentItem().getTypeId() == G.getItemCode("���ڿϷ�.������ �ڵ�")) {
    			
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(B.getBorrowString("default.�̸�")) ||
        				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(I.getInvestString("default.�̸�")) ||
        				e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(D.getDepositString("default.�̸�"))) return;
        			
    			int x = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("����")) break;
    				else { x++; continue; }
    			}
        			
    			int y = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("����")) break;
    				else { y++; continue; }
    			}
        			
    			int z = 0;
    			for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    				if (str.contains("����")) break;
    				else { z++; continue; }
    			}
    			
    			if (e.getCurrentItem().getItemMeta().getLore().size() != x) {
    				int i = PlayerYml.getInfoInt(p, "���Ⱚ") + 1;
    				
    				if (B.getBorrowInt(i + ".��ȯ����") <= Main.economy.getBalance(p.getName())) {
    					int num = P.getInfoInt(p, "���Ⱚ") + 1;
        				P.setInfoInt(p, "���Ⱚ", num);
        				P.setInfoInt(p, "���� Ÿ�̸�", 0);
        				P.setInfoBoolean(p, "���� �Ϸ�", false);
        				Main.economy.withdrawPlayer(p.getName(), B.getBorrowInt(i + ".��ȯ����"));
        				p.closeInventory();
        				p.sendMessage(E.getMessage("���� ����"));
        				return;
    				} else {
    					p.closeInventory();
    					p.sendMessage(E.getMessage("���� ���"));
    					return;
    				}
    			}
    			
    			else if (e.getCurrentItem().getItemMeta().getLore().size() != y) {
					int num = P.getInfoInt(p, "���ݰ�") + 1;
    				int i = PlayerYml.getInfoInt(p, "���ݰ�") + 1;
    				P.setInfoInt(p, "���ݰ�", num);
    				P.setInfoBoolean(p, "���� �Ϸ�", false);
    				P.setInfoInt(p, "���� Ÿ�̸�", 0);
    				Main.economy.depositPlayer(p.getName(), I.getInvestInt(i + ".���� ����"));
    				p.closeInventory();
    				p.sendMessage(E.getMessage("���� ����"));
    			}
    			
    			else if (e.getCurrentItem().getItemMeta().getLore().size() != z) {
					int num = P.getInfoInt(p, "���ڰ�") + 1;
    				int i = PlayerYml.getInfoInt(p, "���ڰ�") + 1;
    				P.setInfoInt(p, "���ڰ�", num);
    				P.setInfoBoolean(p, "���� �Ϸ�", false);
    				P.setInfoInt(p, "���� Ÿ�̸�", 0);
    				Main.economy.depositPlayer(p.getName(), D.getDepositInt(i + ".���� ���ڱ�"));
    				p.closeInventory();
    				p.sendMessage(E.getMessage("���� ����"));
    			}
    		}
    	} else {
    		return;
    	}
    }
}
