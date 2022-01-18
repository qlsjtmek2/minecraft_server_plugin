package me.espoo.dunzeon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.nisovin.magicspells.events.SpellCastEvent;

import me.espoo.rpg.Method;
import me.espoo.rpg.party.PartyAPI;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if (main.Round && main.pList.contains(p) && main.oneRound == -1 && main.twoRound == -1 && main.threeRound == -1 && main.fourRound == -1) {
			p.teleport(e.getFrom());
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
		if (main.Round && main.pList.contains(p) && main.oneRound == -1 && main.twoRound == -1 && main.threeRound == -1 && main.fourRound == -1) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (main.Round && main.pList.contains(p) && main.oneRound == -1 && main.twoRound == -1 && main.threeRound == -1 && main.fourRound == -1) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		
		if (main.Round && main.pList.contains(p) && main.oneRound == -1 && main.twoRound == -1 && main.threeRound == -1 && main.fourRound == -1) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(SpellCastEvent e) {
		Player p = e.getCaster();
		
		if (main.Round && main.pList.contains(p) && main.oneRound == -1 && main.twoRound == -1 && main.threeRound == -1 && main.fourRound == -1) {
			e.setCancelled(true);
			p.sendMessage("��3����� ���� �ð��̹Ƿ� ��ų�� ����Ͻ� �� �����ϴ�.");
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (main.Round && main.pList.contains(p)) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (API.isInAssociation(p.getLocation())) {
			API.sendCommand("warp Z�� " + p.getName());
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (main.pList.contains(p))
			API.subUser(p, "��e[ " + p.getName() + " ] ��c���� ���� ��ȸ ���� �� �����ϼ̽��ϴ�.");
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		
		if (main.pList.contains(p))
			API.subUser(p, "��e[ " + p.getName() + " ] ��c���� ��4�����c�ϼ̽��ϴ�.");
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (main.onePosition.contains(p)) {
				main.onePosition.remove(p);
				String lo = API.LocationToString(e.getClickedBlock().getLocation());
				p.sendMessage("��6���������� ���� ù��° ��ǥ�� �����ϼ̽��ϴ�. ��c( " + lo + " )");
				Position.setStangByOne(lo);
				return;
			}
			
			else if (main.twoPosition.contains(p)) {
				main.twoPosition.remove(p);
				String lo = API.LocationToString(e.getClickedBlock().getLocation());
				p.sendMessage("��6���������� ���� �ι�° ��ǥ�� �����ϼ̽��ϴ�. ��c( " + lo + " )");
				Position.setStangByTwo(lo);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (PartyAPI.isParty(PartyAPI.getJoinParty(p))) {
			if (main.PartyCheck.get(PartyAPI.getJoinParty(p)) != null) {
				if (e.getInventory().getName().equalsIgnoreCase("��Ƽ ���� ���� ��ȸ�� �������ڰ� �մϴ�.")) {
					String party = PartyAPI.getJoinParty(p);
					
					if (p.getName().equalsIgnoreCase(PartyAPI.getManager(party))) return;
					if (main.PartyCheck.get(party) != null) {
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						p.sendMessage("��c�κ��丮 â�� �ݾ� �ڵ����� ���� ��ȸ ������ �ź��Ͽ����ϴ�.");
						main.PartyCheck.remove(party);
						
						for (String user : PartyAPI.getUser(party)) {
							if (user.equalsIgnoreCase(p.getName())) continue;
							
							Player pl = Method.getOnorOffLine(user);
							pl.sendMessage("��c" + p.getName() + " ���� ������ �ź��Ͽ� ���� ��ȸ�� �������� �ʽ��ϴ�.");
							pl.closeInventory();
							if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						}
					}
				}
			}	
		}
	}

	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("��Ƽ ���� ���� ��ȸ�� �������ڰ� �մϴ�.")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, ���� ��ȸ�� �����ϰڽ��ϴ�.")) {
    					String party = PartyAPI.getJoinParty(p);
    					int num = main.PartyCheck.get(party) + 1;
    					main.PartyCheck.put(party, num);
    					
    					if (num == PartyAPI.getUserInteger(party)) {
    						if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    						List<Player> pList = new ArrayList<Player>();
    						for (String name : PartyAPI.getUser(party)) {
    							Player pl = Bukkit.getPlayerExact(name);
    							if (pl != null) pList.add(pl);
    						}
    						
    						API.RoundStart(pList);
    						
    						for (String user : PartyAPI.getUser(party)) {
        						Player pl = Method.getOnorOffLine(user);
        						pl.closeInventory();
        						pl.sendMessage("��6��ΰ� ������ �����Ͽ� ���� ��ȸ�� ��c�����6�մϴ�.");
            					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					}
    					} else {
    						p.sendMessage("��6���������� ���� ��ȸ ������ ��c������6�ϼ̽��ϴ�.");
    						p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f�ƴϿ�, ���� ��ȸ�� �������� �ʰڽ��ϴ�.")) {
    					String party = PartyAPI.getJoinParty(p);
        				if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					
    					for (String user : PartyAPI.getUser(party)) {
    						if (user.equalsIgnoreCase(p.getName())) continue;
    						
    						Player pl = Method.getOnorOffLine(user);
    						pl.closeInventory();
    						pl.sendMessage("��c" + p.getName() + " ���� ������ �ź��Ͽ� ���� ��ȸ�� �������� �ʽ��ϴ�.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    					
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				p.sendMessage("��c���� ��ȸ ������ �ź��Ͽ����ϴ�.");
        				p.closeInventory();
    				}
    			}
    		}
     	}
	}
}
