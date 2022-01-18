package me.espoo.PTweaks;

import java.util.logging.Logger;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import me.espoo.main.main;

public class AutoSaveStopper {
	private main mPlugin;
	private int defaultTime = 0;

	public AutoSaveStopper(main plugin) {
		this.mPlugin = plugin;
		this.defaultTime = ((CraftServer) this.mPlugin.getServer()).getServer().autosavePeriod;
	}

	public void onEnable() {
		try {
			int time = this.mPlugin.autoSaveStopper.getInt("interval", 50000);
			((CraftServer) this.mPlugin.getServer()).getServer().autosavePeriod = time;
			this.mPlugin.getLogger()
					.info(String.format("Packet Autosave set to %d", new Object[] { Integer.valueOf(time) }));
		} catch (Exception e) {
			this.mPlugin.getLogger().severe("AutoSaveStopper Failed! " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void onDisable() {
		((CraftServer) this.mPlugin.getServer()).getServer().autosavePeriod = this.defaultTime;
		this.mPlugin.getLogger().info("Packet Autosave reset!");
	}
}