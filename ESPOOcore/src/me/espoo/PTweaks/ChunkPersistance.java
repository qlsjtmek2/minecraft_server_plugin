package me.espoo.PTweaks;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import me.espoo.main.main;

public class ChunkPersistance implements Listener {
	private main mPlugin;
	private int mLifeTime = 100000;
	private int mPruneTime = 3;
	private int mSpawnChunkRadius = 64;

	private LinkedHashMap<String, Long> mChunks = new LinkedHashMap();

	public ChunkPersistance(main plugin) {
		this.mPlugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void onEnable() {
		this.mLifeTime = this.mPlugin.chunkPersistance.getInt("lifetime", 100000);
		this.mPruneTime = this.mPlugin.chunkPersistance.getInt("prune", 3);
		this.mSpawnChunkRadius = this.mPlugin.chunkPersistance.getInt("spawnChunkRadius", 64);

		this.mPlugin.getLogger()
				.info(String.format("Chunk Life Time: %dms", new Object[] { Integer.valueOf(this.mLifeTime) }));
		this.mPlugin.getLogger()
				.info(String.format("Chunk Prune Time: %dms", new Object[] { Integer.valueOf(this.mPruneTime) }));

		long time = System.currentTimeMillis();

		for (World world : this.mPlugin.getServer().getWorlds()) {
			for (Chunk chunk : world.getLoadedChunks()) {
				if (!isSpawnChunk(chunk)) {
					String s = chunk.getWorld().getName() + ";" + chunk.getX() + ";" + chunk.getZ();
					this.mChunks.put(s, Long.valueOf(time));
				}
			}
		}
		registerSchedulder();
	}

	public void onDisable() {
		for (Map.Entry entry : this.mChunks.entrySet()) {
			String[] key = ((String) entry.getKey()).split(";");
			String w = key[0];
			int x = Integer.valueOf(key[1]).intValue();
			int z = Integer.valueOf(key[2]).intValue();
			World world = this.mPlugin.getServer().getWorld(w);

			if (!world.isChunkLoaded(x, z))
				continue;
			world.unloadChunk(x, z);
		}
		clear();
	}

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		if (!isSpawnChunk(event.getChunk())) {
			Chunk c = event.getChunk();
			String s = c.getWorld().getName() + ";" + c.getX() + ";" + c.getZ();
			this.mChunks.put(s, Long.valueOf(System.currentTimeMillis()));
		}
	}

	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		if (isSpawnChunk(event.getChunk())) {
			event.setCancelled(true);
			return;
		}

		Chunk c = event.getChunk();
		String s = c.getWorld().getName() + ";" + c.getX() + ";" + c.getZ();

		if (!this.mChunks.containsKey(s)) {
			return;
		}

		long age = System.currentTimeMillis() - ((Long) this.mChunks.get(s)).longValue();

		if (age < this.mLifeTime) {
			event.setCancelled(true);
		} else
			this.mChunks.remove(s);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		long time = System.currentTimeMillis();
		Chunk chunk = event.getPlayer().getLocation().getChunk();
		if (!isSpawnChunk(chunk)) {
			String s = chunk.getWorld().getName() + ";" + chunk.getX() + ";" + chunk.getZ();
			this.mChunks.put(s, Long.valueOf(time));
		}
	}

	public void clear() {
		this.mChunks.clear();
	}

	private boolean isSpawnChunk(Chunk chunk) {
		Location spawn = chunk.getWorld().getSpawnLocation();
		int x = Math.abs(chunk.getX() - spawn.getBlockX());
		int z = Math.abs(chunk.getZ() - spawn.getBlockZ());
		return (x <= this.mSpawnChunkRadius) && (z <= this.mSpawnChunkRadius);
	}

	private void registerSchedulder() {
		this.mPlugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.mPlugin, new Runnable() {
			public void run() {
				long timeNow = System.currentTimeMillis();

				Iterator iterator = ChunkPersistance.this.mChunks.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					String[] key = ((String) entry.getKey()).split(";");
					String w = key[0];
					World world = ChunkPersistance.this.mPlugin.getServer().getWorld(w);
					long time = ((Long) entry.getValue()).longValue();
					long age = timeNow - time;
					if (age >= ChunkPersistance.this.mLifeTime) {
						int x = Integer.valueOf(key[1]).intValue();
						int z = Integer.valueOf(key[2]).intValue();
						if (!world.isChunkInUse(x, z)) {
							world.unloadChunk(x, z);
							iterator.remove();
						}
					}
				}
			}
		}, 20 * this.mPruneTime, 20 * this.mPruneTime);
	}
}