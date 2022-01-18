package me.shinkhan.ccc;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import kr.tpsw.rsprefix.api.FileAPI;
import kr.tpsw.rsprefix.api.PrefixPlayer;

@SuppressWarnings("deprecation")
public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		List<String> list = main.list;
		
		if (list != null && !list.isEmpty() && list.contains(p.getName())) {
			PrefixPlayer pp = FileAPI.getPrefixPlayer(p.getName());
			e.setCancelled(true);
			
			if (!pp.getMainPrefix().equalsIgnoreCase(""))
				Bukkit.getConsoleSender().sendMessage(String.valueOf(pp.getMainPrefix()) + " 」r" + p.getDisplayName() + " 」7:: 」f" + e.getMessage());
			else
				Bukkit.getConsoleSender().sendMessage(p.getDisplayName() + " 」7:: 」f" + e.getMessage());
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.isOp() || list.contains(player.getName())) {
					String str = player.getName();
					if (!pp.getMainPrefix().equalsIgnoreCase(""))
						API.getOnorOffLine(str).sendMessage(String.valueOf(pp.getMainPrefix()) + " 」r" + p.getDisplayName() + " 」7:: 」f" + e.getMessage());
					else
						API.getOnorOffLine(str).sendMessage(p.getDisplayName() + " 」7:: 」f" + e.getMessage());
				}
			}
		}
	}
}
