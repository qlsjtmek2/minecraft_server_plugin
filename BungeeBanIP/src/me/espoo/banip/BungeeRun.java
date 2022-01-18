package me.espoo.banip;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BungeeRun extends BukkitRunnable
{
	PlayerChannelEvent event;
	ArrayList<String> al;
	ArrayList<String> plip;
	ArrayList<String> plni;
  
	public BungeeRun(PlayerChannelEvent event2, ArrayList<String> al, ArrayList<String> plip, ArrayList<String> plni)
	{
		this.event = event2;
		this.al = al;
		this.plip = plip;
		this.plni = plni;
	}

	public void run()
	{
		for (int i = 0; i < this.al.size(); i++)
		{
			for (int p = 0; p < this.plip.size(); p++)
			{
				if (!((String)this.al.get(i)).equals(this.plip.get(p)))
					continue;
				if (!((String)this.plni.get(p)).equals(this.event.getPlayer().getName()))
					continue;
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + (String)this.plip.get(p) + " 의 IP " + (String)this.plni.get(p) + " 님이 접속을 시도합니다.");

				this.event.getPlayer().kickPlayer("BungeeCord Server Ban");
			}
		}

		cancel();
	}
}