package me.shinkhan.treasurechest;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase("보급 상자")) {
			HumanEntity h = e.getPlayer();
			Player p = Bukkit.getPlayerExact(h.getName());
			
			p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1.0F, 1.0F);
			return;
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("보급 상자 설정")) {
			HumanEntity h = e.getPlayer();
			Player p = Bukkit.getPlayerExact(h.getName());
			
			API.setGUI(e.getInventory());
			p.sendMessage("§6성공적으로 보급 상자를 설정하셨습니다.");
			
			return;
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Action act = e.getAction();
		Player p = e.getPlayer();
		
		if (act == Action.RIGHT_CLICK_BLOCK) {
	     	if (main.chest != null && main.chest.equals(e.getClickedBlock().getLocation())) {
				e.setCancelled(true);
				Inventory GUI = Bukkit.createInventory(null, 27, "보급 상자");
				GUI.setContents(main.gui.getContents());
	     		if (main.gui != null) p.openInventory(GUI);
	     		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
	     	}
		}
	}
}
