package me.shinkhan.fatigue;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Event extends JavaPlugin implements Listener {
	main M;
	
	public Event(main main)
	{
		this.M = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/FatigueAPI");
		File folder2 = new File("plugins/FatigueAPI/Player");
		File f = new File("plugins/FatigueAPI/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) PlayerYml.CreatePlayerInfo(f, folder, folder2, config);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (API.isCheck(p)) {
			if (e.getBlock().getType() == Material.STONE) {
				API.subFatigue(p, 0.004D);
			}
			
			else if (e.getBlock().getTypeId() == 15 || e.getBlock().getTypeId() == 14 ||
					 e.getBlock().getTypeId() == 56 || e.getBlock().getTypeId() == 129 ||
					 e.getBlock().getTypeId() == 16 || e.getBlock().getTypeId() == 153 ||
					 e.getBlock().getTypeId() == 73 || e.getBlock().getTypeId() == 21) {
				API.subFatigue(p, 0.015D);
			}
			
			else {
				API.subFatigue(p, 0.01D);
			}
		} else {
			e.setCancelled(true);
			API.sayMessage(p, "피로도가 부족하여 블럭을 캐실 수 없습니다.");
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onCraftItem(CraftItemEvent e) {
		Player h = (Player) e.getView().getPlayer();
		Player p = Bukkit.getServer().getPlayerExact(h.getName());
		
		if (API.isCheck(p)) {
			API.subFatigue(p, 0.01D);
		} else {
			e.setCancelled(true);
			API.sayMessage(p, "피로도가 부족하여 조합을 하실 수 없습니다.");
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			if (API.isCheck(p)) {
				API.subFatigue(p, 0.0003D);
			} else {
				e.setCancelled(true);
				API.sayMessage(p, "피로도가 부족하여 PVP, 사냥을 하실 수 없습니다.");
			}
		}
	}
}
