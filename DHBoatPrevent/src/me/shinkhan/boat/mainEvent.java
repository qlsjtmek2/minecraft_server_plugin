package me.shinkhan.boat;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
		
	public mainEvent(main main)
	{
		this.M = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final String n = p.getName();
		
		if (n.equalsIgnoreCase("shinkhan")) {
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 400);
			timer.schedule(new TimerTask() {
				public void run() {
					p.sendMessage("§6이 서버에는 당신의 플러그인인 §cDHBoatPrevent §6가 들어있습니다!");
					return;
				}
			}, timeToRun);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Action a = e.getAction();
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();

		if (a == Action.RIGHT_CLICK_BLOCK) {
			if (b.getType() != Material.WATER && b.getType() != Material.STATIONARY_WATER) {
				if (p.getItemInHand().getTypeId() == 333) {
					p.setItemInHand(null);
					p.getWorld().dropItem(b.getLocation(), new ItemStack(Material.BOAT));
				}
			}
		}
	}
	
    @EventHandler
    public void onMoveBoat(VehicleMoveEvent e) {
        Location l = e.getVehicle().getLocation();
        Block b = l.add(0.0, -1.0, 0.0).getBlock();
        
        if (e.getVehicle().getType() == EntityType.BOAT) {
            if (b.getType() != Material.WATER && b.getType() != Material.STATIONARY_WATER) {
                e.getVehicle().remove();
                l.getWorld().dropItem(l, new ItemStack(Material.BOAT));
            }
        }
    }
}
