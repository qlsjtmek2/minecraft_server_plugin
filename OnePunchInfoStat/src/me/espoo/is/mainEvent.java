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
		
		if (e.getInventory().getName().contains("님이 대련을 신청했습니다.")) {
			if (main.InvitePvP.get(p) != null) {
				Player pl = main.InvitePvP.get(p);
				pl.sendMessage("§c" + p.getName() + " 님이 대련 신청을 거절하셨습니다.");
				p.sendMessage("§6성공적으로 대련 신청을 §c거절 §6하셨습니다.");
				main.InvitePvP.remove(p);
				p.closeInventory();
			}
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().contains("님이 대련을 신청했습니다.")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f대련을 수락하겠습니다.")) {
    					Player pl = main.InvitePvP.get(p);
    					if (PVPMain.oneList.contains(p)) PVPMain.oneList.remove(p);
    					if (PVPMain.oneList.contains(pl)) PVPMain.oneList.remove(pl);
    					
    					PVPMain.oneList.add(0, p);
    					PVPMain.oneList.add(0, pl);
    					pl.sendMessage("§c" + p.getName() + " §6님이 대련 신청을 §c수락 §6하셨습니다!");
    					p.sendMessage("§6성공적으로 대련 신청을 §c수락 §6하셨습니다.");
    					main.InvitePvP.remove(p);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f대련을 수락하지 않겠습니다.")) {
    					Player pl = main.InvitePvP.get(p);
    					pl.sendMessage("§c" + p.getName() + " 님이 대련 신청을 거절하셨습니다.");
    					p.sendMessage("§c인벤토리 창을 닫아 대련 신청을 거절하였습니다.");
    					main.InvitePvP.remove(p);
    					p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
         	if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("상태:") || e.getInventory().getName().split(" ")[0].equalsIgnoreCase("정보:")) {
        		Player p = Bukkit.getPlayerExact(e.getView().getPlayer().getName());
             	e.setCancelled(true);
             	
             	if (e.getCurrentItem().hasItemMeta()) {
    				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
             		if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
         				String name = e.getInventory().getName().split(" ")[1];
             			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(name)) {
             				Player pl = Method.getOnorOffLine(name);
             				
             				if (pl != null) {
             					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "상태 공개 여부")) {
             						GUI.StatGUI(p, pl);
             					}
             				}
             			}
             			
             			else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a길드 초대하기")) {
             				Player pl = Method.getOnorOffLine(name);
             				
             				if (pl != null) {
             					String guild = GuildAPI.getJoinGuild(p);
             					
             					if (GuildAPI.isWar(guild)) {
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 길드 초대가 불가능합니다.");
             						e.setCancelled(true);
             						return;
             					}
             					
             					if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "길드 초대")) {
             						e.setCancelled(true);
             						p.sendMessage("§c그 플레이어는 길드 초대 거부 상태입니다.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (GuildAPI.getJoinGuild(pl) != null) {
             						e.setCancelled(true);
             						p.sendMessage("§c그 플레이어는 이미 길드가 있습니다.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (RPGMain.OkInvite.get(pl) != null) {
             						e.setCancelled(true);
             						p.sendMessage("§c그 플레이어는 이미 길드 초대를 받은 상태입니다.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						p.closeInventory();
             						return;
             					}
             					
             					GuildGUI.InviteGUI(pl, GuildAPI.getJoinGuild(p));
             					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             					p.sendMessage("§6성공적으로 §c" + name + " §6님을 초대하셨습니다.");
             					RPGMain.OkInvite.put(pl, p);
             					e.setCancelled(true);
             					p.closeInventory();
             				}
             			}
             					
             			else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b파티 초대하기")) {
             				Player pl = Method.getOnorOffLine(name);
             				
             				if (pl != null) {
             					if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "파티 초대")) {
             						e.setCancelled(true);
             						p.sendMessage("§c그 플레이어는 파티 초대 거부 상태입니다.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (PartyAPI.getJoinParty(pl) != null) {
             						e.setCancelled(true);
             						p.sendMessage("§c그 플레이어는 이미 파티가 있습니다.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (RPGMain.OkInviteParty.get(pl) != null) {
             						e.setCancelled(true);
             						p.sendMessage("§c그 플레이어는 이미 파티 초대를 받은 상태입니다.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						p.closeInventory();
             						return;
             					}
             					
             					PartyGUI.InviteGUI(pl, PartyAPI.getJoinParty(p));
             					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             					p.sendMessage("§6성공적으로 §c" + name + " §6님을 초대하셨습니다.");
             					RPGMain.OkInviteParty.put(pl, p);
             					RPGMain.InvitePartyPlayer.remove(p);
             					e.setCancelled(true);
             					p.closeInventory();
             				}
             			}
             			
             			else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c대련 신청하기")) {
             				Player pl = Method.getOnorOffLine(name);
             				e.setCancelled(true);
             				if (pl != null) {
             					if (PVPMain.oneVS) {
             						p.sendMessage("§c현재 누가 1:1 대련장에서 대련하고 있습니다.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						return;
             					}
             					
             					if (main.InvitePvP.get(pl) != null) {
             						p.sendMessage("§c그 플레이어는 이미 대련 신청을 받은 상태입니다.");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             						p.closeInventory();
             						return;
             					}

             					main.InvitePvP.put(pl, p);
             					GUI.CheckPvPGUI(pl, p.getName());
             					p.closeInventory();
             					p.sendMessage("§6성공적으로 §c" + pl.getName() + " §6님에게 대련 신청을 했습니다.");
             					return;
             				}
             			}
             			
             			else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d거래 신청하기")) {
         					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             				p.closeInventory();
             				p.chat("/교환 " + name);
             				return;
             			}
             		}
             	}
         	}	
     	}
	}
}
