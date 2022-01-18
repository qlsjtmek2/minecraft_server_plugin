package me.espoo.BRB;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerRun extends BukkitRunnable {
	private main main;
	private Player p;

	public SchedulerRun(main main, Player p)
	{
		this.main = main;
		this.p = p;
	}

	public void run()
	{
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("IP");

		this.p.sendPluginMessage(this.main, "BungeeCord", out.toByteArray());
		cancel();
	}
}