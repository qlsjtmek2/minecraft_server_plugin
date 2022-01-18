package me.espoo.TrashCan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class TrashCan implements Listener
{
	public void TrashCan(Player p) 
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "쓰레기통");
		p.openInventory(inv);
	}
}