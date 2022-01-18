package me.espoo.pvp;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.nisovin.magicspells.events.SpellCastEvent;

import me.confuser.barapi.BarAPI;
import me.espoo.pvp.yml.PVPPlayer;
import uk.org.whoami.authme.events.LoginEvent;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onLogin(LoginEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().getName().equalsIgnoreCase("world_pvp") || p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
			p.chat("/spawn");
			p.sendMessage("§f[§4알림§f] §cPVP장에서 로그인 하셨으므로 스폰으로 이동합니다.");
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (API.isUserVSPlaying(p.getName())) {
			if (e.getMessage().contains("/마나포션알고리즘") || e.getMessage().contains("/포션알고리즘") || e.getMessage().contains("/해독알고리즘")) 
				return;
			e.setCancelled(true);
			return;
		}
		
    	if (!e.isCancelled()) {
    		if (p.getWorld().getName().contains("world_pvp")) {
    			if (e.getMessage().contains("/펫") || e.getMessage().contains("/pvp") || e.getMessage().contains("/대련") || e.getMessage().contains("/대전")) {
    				p.sendMessage("§c이 명령어는 PVP장에서 금지 명령어 입니다.");
    				e.setCancelled(true);
    				return;
    			}
    		}
    	}
    	
    	
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (main.oneType == 1 && API.isOneVSPlaying(p.getName())) {
			p.teleport(e.getFrom());
			p.setSneaking(false);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onSpellCast(SpellCastEvent e) {
		Player p = e.getCaster();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
	    Player damager = (Player) e.getDamager();
	    if (API.isOneVSPlaying(damager.getName()) && main.oneType == 1 || API.isOneVSPlaying(damager.getName()) && main.oneType == 3) {
	    	e.setCancelled(true);
	    	return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (API.isOneVSMatch(p)) {
			main.oneList.remove(p);
			main.BossBarTimer.remove(p);
		}
		
		else if (API.isOneVSPlaying(p.getName())) {
			String name = API.getEnemyName(p.getName());
			if (name != null) {
				final Player pl = API.getOnorOffLine(name);
				pl.sendMessage("§6대련중인 상대 플레이어가 퇴장하여 §c승리§6하셨습니다.");
				
				API.deleteUserTimerMove(1);
				API.deleteUserTimerStart(1);
				API.deleteUserTimerFinish(1);
				
				main.Timer.put("1. PvP Finish", new BukkitRunnable()
				{
					int num = API.getPvPFinishTime() + 1;
					public void run()
					{
						num--;
						
						if (!main.oneVS) {
							main.oneType = -1;
							main.oneName = null;
							Integer id = main.Timer.remove("1. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							pl.sendMessage("§6스폰으로 이동되었습니다.");
							main.oneType = -1;
							main.oneVS = false;
							main.oneName = null;
							API.sendCommand("spawn " + pl.getName());
							API.sendCommand("heal " + pl.getName());
							
							Integer id = main.Timer.remove("1. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionPvP"), 20L, 20L).getTaskId());
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player kp = p.getKiller();
		if (kp == null) return;
		PVPPlayer pp = API.getPVPPlayera(p.getName());
		PVPPlayer pk = API.getPVPPlayera(kp.getName());
		
		if (!kp.getWorld().getName().equalsIgnoreCase("world_pvp") && !kp.getWorld().getName().equalsIgnoreCase("world_pvp2")) return;
		
		pk.addKill(1);
		pp.addDeath(1);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (API.isOneVSPlaying(p.getName())) {
			String name = API.getEnemyName(p.getName());
			if (name != null) {
				final Player pl = API.getOnorOffLine(name);
				API.deleteUserTimerStart(1);
				API.deleteUserTimerFinish(1);
				
				pl.sendMessage("§6대련중 §c" + p.getName() + " §6님이 §c" + name + " §6님에게 사망하셨습니다.");
				pl.sendMessage("");
				pl.sendMessage("§f==========================================================");
				pl.sendMessage("");
				pl.sendMessage("                                              §6대련이 종료되었습니다!");
				pl.sendMessage("");
				pl.sendMessage("§f==========================================================");
				pl.sendMessage("");
				
				p.sendMessage("§6대련중 §c" + p.getName() + " §6님이 §c" + name + " §6님에게 사망하셨습니다.");
				p.sendMessage("");
				p.sendMessage("§f==========================================================");
				p.sendMessage("");
				p.sendMessage("                                              §6대련이 종료되었습니다!");
				p.sendMessage("");
				p.sendMessage("§f==========================================================");
				p.sendMessage("");
				
				main.Timer.put("1. PvP Finish", new BukkitRunnable()
				{
					int num = API.getPvPFinishTime() + 1;
					public void run()
					{
						num--;
						
						if (!main.oneVS) {
							main.oneType = -1;
							main.oneName = null;
							Integer id = main.Timer.remove("1. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							pl.sendMessage("§6스폰으로 이동되었습니다.");
							main.oneType = -1;
							main.oneVS = false;
							main.oneName = null;
							pl.chat("/spawn");
							API.sendCommand("heal " + pl.getName());
							
							Integer id = main.Timer.remove("1. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionPvP"), 20L, 20L).getTaskId());
				return;
			}
		}
	}

	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	
     	if (e.getInventory().getName().equalsIgnoreCase("대련 하기")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c대난투 §6입장 하기")) {
    					p.closeInventory();
    					p.chat("/pvp");
    					return;
    				}
					
					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c1:1 §6대련 하기")) {
    					if (!p.getWorld().getName().equalsIgnoreCase("world")) {
    						p.closeInventory();
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c스폰에서만 1:1 대련 매칭을 진행하실 수 있습니다. §e[ /스폰, /spawn ]");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						p.closeInventory();
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c튜토리얼을 마치지 못한 사람은 대련에 참가하실 수 없습니다.");
    						return;
    					}
    					
    					main.oneList.add(p);
    					p.sendMessage("§6매치가 잡히면 §c강제로 텔레포트 §6됩니다. 당분간 아무 작업을 수행하지 말아주시기 바랍니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("매칭 취소")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		ItemStack item = e.getCurrentItem();
    		
    		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§c매칭 취소")) {
    			if (API.isOneVSMatch(p)) {
					main.oneList.remove(p);
					main.BossBarTimer.remove(p);
					p.sendMessage("§61:1 대련 매칭이 §c취소 §6되었습니다.");
					p.closeInventory();
					BarAPI.removeBar(p);
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
    		}
     	}
	}
}
