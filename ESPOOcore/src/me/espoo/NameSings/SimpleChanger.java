package me.espoo.NameSings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public abstract class SimpleChanger implements Listener {
	private final String key;
	private final String permissionsNode;

	public SimpleChanger(Plugin plugin, String key, String permissionsNode) {
		if ((plugin == null) || (!plugin.isEnabled())) {
			throw new IllegalArgumentException("The plugin must not be null and has to be enabled!");
		}
		if ((key == null) || (permissionsNode == null)) {
			throw new IllegalArgumentException("The key and the permissions node must not be null!");
		}
		if (key.length() > 15) {
			throw new IllegalArgumentException("The key must not be longer then 15!");
		}
		this.key = key;
		this.permissionsNode = permissionsNode;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public abstract String getValue(Player paramPlayer, Location paramLocation, String paramString);

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onSignSend(SignSendEvent event) {
		for (int i = 0; i < 4; i++) {
			String line = event.getLine(i);
			if (line.contains(this.key))
				event.setLine(i, line.replace(this.key, getValue(event.getPlayer(), event.getLocation(), line)));
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onSignCreate(SignChangeEvent event) {
		Player player = event.getPlayer();
		String[] lines = event.getLines();
		for (String line : lines) {
			if ((!line.contains(this.key)) || (player.hasPermission(this.permissionsNode)))
				continue;
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "당신은 '" + this.key + "' 이 문구룰 사용할 권한이 없습니다.");
		}
	}
}