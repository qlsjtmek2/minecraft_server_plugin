package me.espoo.Armor;

import me.espoo.main.main;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

public class armorListener implements Listener {

	@EventHandler
	public static void onPlayerInteract2(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		
	    int hand = p.getItemInHand().getTypeId();
	    
	    if ((hand == 268) || (hand == 270) || (hand == 271) || (hand == 269) || (hand == 290) || 
	    	(hand == 272) || (hand == 274) || (hand == 273) || (hand == 275) || (hand == 291) || 
	    	(hand == 267) || (hand == 257) || (hand == 258) || (hand == 256) || (hand == 283) || 
	    	(hand == 285) || (hand == 286) || (hand == 284) || (hand == 294) || (hand == 276) || 
	    	(hand == 278) || (hand == 279) || (hand == 277) || (hand == 259) || (hand == 261) || 
	    	(hand == 346) || (hand == 359))
	    {
	    	p.getItemInHand().setDurability((short) 0);
	    }
	}

	@EventHandler
	public static void onEntityDamage(EntityDamageEvent e) 
	{
		
		if ((e.getEntity() instanceof Player))
		{
			Player p = (Player)e.getEntity();
			PlayerInventory inv = p.getInventory();

			if (inv.getChestplate() != null) {
				inv.getChestplate().setDurability((short) 0);
			}
		
			if (inv.getHelmet() != null) {
				if (inv.getHelmet().getTypeId() != 397) {
					inv.getHelmet().setDurability((short) 0);
				}
			}

			if (inv.getLeggings() != null) {
	        inv.getLeggings().setDurability((short) 0);
			}

			if (inv.getBoots() != null)
				inv.getBoots().setDurability((short) 0);
      
		    if (e.getCause() == EntityDamageEvent.DamageCause.FALL) 
		    {
		        e.setDamage(0);
		        e.setCancelled(true);
		    }
		}
	}

	@EventHandler
	public void onPlayerItemBreak(PlayerItemBreakEvent e) {
		e.getBrokenItem().setAmount(1);
		e.getPlayer().updateInventory();

	}
}
