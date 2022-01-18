package me.shinkhan.DHFallCancel;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DHEvent implements Listener
{
	private final DHMain plugin;

	public DHEvent(DHMain instance)
	{
		plugin = instance;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event2)
	{
		
		if(event2.getCause() == DamageCause.FALL)
		{
			if(plugin.getConfig().getBoolean("UserPermission") == true)
			{
				if(event2.getEntity() instanceof Player)
				{
					Player p = (Player)event2.getEntity();
					if(p.hasPermission("DHFallCancel.use") == true)
					{
						event2.setCancelled(plugin.getConfig().getBoolean("FallDamage"));
					}
					
					else
					{
						event2.setCancelled(false);
					}
				}
			}
			
			else
			{
				event2.setCancelled(plugin.getConfig().getBoolean("FallDamage"));
			}
		}
	}
}