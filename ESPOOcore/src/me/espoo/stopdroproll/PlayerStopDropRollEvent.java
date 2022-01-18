package me.espoo.stopdroproll;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStopDropRollEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private Player player;

	public PlayerStopDropRollEvent(Player p) {
		this.player = p;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public Player getPlayer() {
		return this.player;
	}
}