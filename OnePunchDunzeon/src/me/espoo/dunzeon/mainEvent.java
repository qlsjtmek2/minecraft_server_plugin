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
			p.sendMessage("§3현재는 무적 시간이므로 스킬을 사용하실 수 없습니다.");
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
			API.sendCommand("warp Z시 " + p.getName());
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (main.pList.contains(p))
			API.subUser(p, "§e[ " + p.getName() + " ] §c님이 괴인 협회 진행 중 퇴장하셨습니다.");
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		
		if (main.pList.contains(p))
			API.subUser(p, "§e[ " + p.getName() + " ] §c님이 §4사망§c하셨습니다.");
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (main.onePosition.contains(p)) {
				main.onePosition.remove(p);
				String lo = API.LocationToString(e.getClickedBlock().getLocation());
				p.sendMessage("§6성공적으로 대기실 첫번째 좌표를 지정하셨습니다. §c( " + lo + " )");
				Position.setStangByOne(lo);
				return;
			}
			
			else if (main.twoPosition.contains(p)) {
				main.twoPosition.remove(p);
				String lo = API.LocationToString(e.getClickedBlock().getLocation());
				p.sendMessage("§6성공적으로 대기실 두번째 좌표를 지정하셨습니다. §c( " + lo + " )");
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
				if (e.getInventory().getName().equalsIgnoreCase("파티 장이 괴인 협회에 입장하자고 합니다.")) {
					String party = PartyAPI.getJoinParty(p);
					
					if (p.getName().equalsIgnoreCase(PartyAPI.getManager(party))) return;
					if (main.PartyCheck.get(party) != null) {
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						p.sendMessage("§c인벤토리 창을 닫아 자동으로 괴인 협회 입장을 거부하였습니다.");
						main.PartyCheck.remove(party);
						
						for (String user : PartyAPI.getUser(party)) {
							if (user.equalsIgnoreCase(p.getName())) continue;
							
							Player pl = Method.getOnorOffLine(user);
							pl.sendMessage("§c" + p.getName() + " 님이 입장을 거부하여 괴인 협회에 입장하지 않습니다.");
							pl.closeInventory();
							if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						}
					}
				}
			}	
		}
	}

	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("파티 장이 괴인 협회에 입장하자고 합니다.")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 괴인 협회에 입장하겠습니다.")) {
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
        						pl.sendMessage("§6모두가 입장을 수락하여 괴인 협회에 §c입장§6합니다.");
            					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					}
    					} else {
    						p.sendMessage("§6성공적으로 괴인 협회 입장을 §c수락§6하셨습니다.");
    						p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 괴인 협회에 입장하지 않겠습니다.")) {
    					String party = PartyAPI.getJoinParty(p);
        				if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					
    					for (String user : PartyAPI.getUser(party)) {
    						if (user.equalsIgnoreCase(p.getName())) continue;
    						
    						Player pl = Method.getOnorOffLine(user);
    						pl.closeInventory();
    						pl.sendMessage("§c" + p.getName() + " 님이 입장을 거부하여 괴인 협회에 입장하지 않습니다.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    					
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				p.sendMessage("§c괴인 협회 입장을 거부하였습니다.");
        				p.closeInventory();
    				}
    			}
    		}
     	}
	}
}
