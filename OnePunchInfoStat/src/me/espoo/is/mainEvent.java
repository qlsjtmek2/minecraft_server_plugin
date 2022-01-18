package me.espoo.is;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.guild.GuildGUI;
import me.espoo.rpg.party.PartyAPI;
import me.espoo.rpg.party.PartyGUI;

public class mainEvent extends JavaPlugin implements Listener {
	static me.espoo.rpg.main RPGMain;
	static me.espoo.pvp.main PVPMain;
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().contains("���� ����� ��û�߽��ϴ�.")) {
			if (main.InvitePvP.get(p) != null) {
				Player pl = main.InvitePvP.get(p);
				pl.sendMessage("��c" + p.getName() + " ���� ��� ��û�� �����ϼ̽��ϴ�.");
				p.sendMessage("��6���������� ��� ��û�� ��c���� ��6�ϼ̽��ϴ�.");
				main.InvitePvP.remove(p);
				p.closeInventory();
			}
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().contains("���� ����� ��û�߽��ϴ�.")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f����� �����ϰڽ��ϴ�.")) {
    					Player pl = main.InvitePvP.get(p);
    					if (PVPMain.oneList.contains(p)) PVPMain.oneList.remove(p);
    					if (PVPMain.oneList.contains(pl)) PVPMain.oneList.remove(pl);
    					
    					PVPMain.oneList.add(0, p);
    					PVPMain.oneList.add(0, pl);
    					pl.sendMessage("��c" + p.getName() + " ��6���� ��� ��û�� ��c���� ��6�ϼ̽��ϴ�!");
    					p.sendMessage("��6���������� ��� ��û�� ��c���� ��6�ϼ̽��ϴ�.");
    					main.InvitePvP.remove(p);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f����� �������� �ʰڽ��ϴ�.")) {
    					Player pl = main.InvitePvP.get(p);
    					pl.sendMessage("��c" + p.getName() + " ���� ��� ��û�� �����ϼ̽��ϴ�.");
    					p.sendMessage("��c�κ��丮 â�� �ݾ� ��� ��û�� �����Ͽ����ϴ�.");
    					main.InvitePvP.remove(p);
    					p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
         	if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("����:") || e.getInventory().getName().split(" ")[0].equalsIgnoreCase("����:")) {
        		Player p = Bukkit.getPlayerExact(e.getView().getPlayer().getName());
             	e.setCancelled(true);
             	
             	if (e.getCurrentItem().hasItemMeta()) {
    				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
             		if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
         				String name = e.getInventory().getName().split(" ")[1];
             			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(name)) {
             				Player pl = Method.getOnorOffLine(name);
             				
             				if (pl != null) {
             					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "���� ���� ����")) {
             						GUI.StatGUI(p, pl);
             					}
             				}
             			}
             			
             			else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��a��� �ʴ��ϱ�")) {
             				Player pl = Method.getOnorOffLine(name);
             				
             				if (pl != null) {
             					String guild = GuildAPI.getJoinGuild(p);
             					
             					if (GuildAPI.isWar(guild)) {
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						p.sendMessage("��c���� ���Ե� ��尡 �������� �����̹Ƿ� ��� �ʴ밡 �Ұ����մϴ�.");
             						e.setCancelled(true);
             						return;
             					}
             					
             					if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "��� �ʴ�")) {
             						e.setCancelled(true);
             						p.sendMessage("��c�� �÷��̾�� ��� �ʴ� �ź� �����Դϴ�.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (GuildAPI.getJoinGuild(pl) != null) {
             						e.setCancelled(true);
             						p.sendMessage("��c�� �÷��̾�� �̹� ��尡 �ֽ��ϴ�.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (RPGMain.OkInvite.get(pl) != null) {
             						e.setCancelled(true);
             						p.sendMessage("��c�� �÷��̾�� �̹� ��� �ʴ븦 ���� �����Դϴ�.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						p.closeInventory();
             						return;
             					}
             					
             					GuildGUI.InviteGUI(pl, GuildAPI.getJoinGuild(p));
             					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             					p.sendMessage("��6���������� ��c" + name + " ��6���� �ʴ��ϼ̽��ϴ�.");
             					RPGMain.OkInvite.put(pl, p);
             					e.setCancelled(true);
             					p.closeInventory();
             				}
             			}
             					
             			else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��b��Ƽ �ʴ��ϱ�")) {
             				Player pl = Method.getOnorOffLine(name);
             				
             				if (pl != null) {
             					if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "��Ƽ �ʴ�")) {
             						e.setCancelled(true);
             						p.sendMessage("��c�� �÷��̾�� ��Ƽ �ʴ� �ź� �����Դϴ�.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (PartyAPI.getJoinParty(pl) != null) {
             						e.setCancelled(true);
             						p.sendMessage("��c�� �÷��̾�� �̹� ��Ƽ�� �ֽ��ϴ�.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (RPGMain.OkInviteParty.get(pl) != null) {
             						e.setCancelled(true);
             						p.sendMessage("��c�� �÷��̾�� �̹� ��Ƽ �ʴ븦 ���� �����Դϴ�.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						p.closeInventory();
             						return;
             					}
             					
             					PartyGUI.InviteGUI(pl, PartyAPI.getJoinParty(p));
             					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             					p.sendMessage("��6���������� ��c" + name + " ��6���� �ʴ��ϼ̽��ϴ�.");
             					RPGMain.OkInviteParty.put(pl, p);
             					RPGMain.InvitePartyPlayer.remove(p);
             					e.setCancelled(true);
             					p.closeInventory();
             				}
             			}
             			
             			else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��c��� ��û�ϱ�")) {
             				Player pl = Method.getOnorOffLine(name);
             				e.setCancelled(true);
             				if (pl != null) {
             					if (PVPMain.oneVS) {
             						p.sendMessage("��c���� ���� 1:1 ����忡�� ����ϰ� �ֽ��ϴ�.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (main.InvitePvP.get(pl) != null) {
             						p.sendMessage("��c�� �÷��̾�� �̹� ��� ��û�� ���� �����Դϴ�.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						p.closeInventory();
             						return;
             					}

             					main.InvitePvP.put(pl, p);
             					GUI.CheckPvPGUI(pl, p.getName());
             					p.closeInventory();
             					p.sendMessage("��6���������� ��c" + pl.getName() + " ��6�Կ��� ��� ��û�� �߽��ϴ�.");
             					return;
             				}
             			}
             			
             			else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��d�ŷ� ��û�ϱ�")) {
         					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             				p.closeInventory();
             				p.chat("/��ȯ " + name);
             				return;
             			}
             		}
             	}
         	}	
     	}
	}
}
