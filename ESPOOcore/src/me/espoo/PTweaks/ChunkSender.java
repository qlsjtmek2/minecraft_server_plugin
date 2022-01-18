package me.espoo.PTweaks;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_5_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import net.minecraft.server.v1_5_R3.Packet51MapChunk;
import net.minecraft.server.v1_5_R3.PlayerConnection;
import net.minecraft.server.v1_5_R3.TileEntity;
import net.minecraft.server.v1_5_R3.WorldServer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import me.espoo.main.main;

public class ChunkSender implements Listener {
	private main plugin;
	private int loaderID = 0;

	private Map<String, Set<ChunkCoordIntPair>> waitinglist = new ConcurrentHashMap();
	private Map<String, Integer> sendTask = new ConcurrentHashMap();
	private Map<String, World> worldlist = new ConcurrentHashMap();

	private Method M = null;

	public ChunkSender(main plugin) {
		plugin = this.plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		try {
			this.M = EntityPlayer.class.getDeclaredMethod("a", new Class[] { TileEntity.class });
			this.M.setAccessible(true);
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error on startup!", e);
			return;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (this.sendTask.containsKey(p.getName())) {
			this.plugin.getServer().getScheduler().cancelTask(((Integer) this.sendTask.get(p.getName())).intValue());
		}

		Set waitset = new HashSet();

		if (p.getWorld() != this.worldlist.get(p.getName())) {
			this.worldlist.put(p.getName(), p.getWorld());
			waitset.clear();
		}

		EntityPlayer E = ((CraftPlayer) p).getHandle();

		if (E.chunkCoordIntPairQueue.size() > 0) {
			waitset.addAll(E.chunkCoordIntPairQueue);
			E.chunkCoordIntPairQueue.clear();
		}

		this.waitinglist.put(p.getName(), waitset);
		this.sendTask.put(p.getName(), Integer.valueOf(
				this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new sender(E), 2L)));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		this.waitinglist.remove(e.getPlayer());
		this.worldlist.remove(e.getPlayer().getName());
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (this.sendTask.containsKey(p.getName())) {
			return;
		}
		Set waitset;

		if (this.waitinglist.containsKey(p.getName()))
			waitset = (Set) this.waitinglist.remove(p.getName());
		else {
			waitset = new HashSet();
		}

		if (p.getWorld() != this.worldlist.get(p.getName())) {
			this.worldlist.put(p.getName(), p.getWorld());
			waitset.clear();
		}

		EntityPlayer E = ((CraftPlayer) p).getHandle();

		if (E.chunkCoordIntPairQueue.size() > 0) {
			waitset.addAll(E.chunkCoordIntPairQueue);
			E.chunkCoordIntPairQueue.clear();
		}

		this.waitinglist.put(p.getName(), waitset);
		this.sendTask.put(p.getName(), Integer.valueOf(
				this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new sender(E), 2L)));
	}

	public void onDisable() {
		if (this.loaderID != 0) {
			this.plugin.getServer().getScheduler().cancelTask(this.loaderID);
		}

		if (this.sendTask.size() > 0) {
			for (Integer tID : this.sendTask.values()) {
				this.plugin.getServer().getScheduler().cancelTask(tID.intValue());
			}
			this.sendTask.clear();
		}
	}

	public class loader implements Runnable {
		public loader() {
		}

		public void run() {
			for (Player p : ChunkSender.this.plugin.getServer().getOnlinePlayers()) {
				Set waitset;

				if (ChunkSender.this.waitinglist.containsKey(p.getName()))
					waitset = (Set) ChunkSender.this.waitinglist.remove(p.getName());
				else {
					waitset = new HashSet();
				}

				if (p.getWorld() != ChunkSender.this.worldlist.get(p.getName())) {
					ChunkSender.this.worldlist.put(p.getName(), p.getWorld());
					waitset.clear();
				}

				EntityPlayer E = ((CraftPlayer) p).getHandle();

				if (E.chunkCoordIntPairQueue.size() > 0) {
					waitset.addAll(E.chunkCoordIntPairQueue);
					E.chunkCoordIntPairQueue.clear();
				}

				ChunkSender.this.waitinglist.put(p.getName(), waitset);
			}
		}
	}

	public class sender implements Runnable {
		private EntityPlayer E;

		public sender(Player p) {
			this.E = ((CraftPlayer) p).getHandle();
		}

		public sender(EntityPlayer p) {
			this.E = p;
		}

		public void run() {
			String key = this.E.getName();
			if (!this.E.getBukkitEntity().isOnline()) {
				ChunkSender.this.waitinglist.remove(key);
				ChunkSender.this.sendTask.remove(key);
				return;
			}

			Set waitset = (Set) ChunkSender.this.waitinglist.remove(key);
			Set removeset = new HashSet();

			if (!waitset.isEmpty()) {
				ChunkCoordIntPair willsend = null;
				int x = (int) (this.E.locX + this.E.motX * 10.0D) >> 4;
				int z = (int) (this.E.locZ + this.E.motZ * 10.0D) >> 4;

				int distA = 99;
				int distB = 0;

				waitset.remove(willsend);

				if (distA <= 10) {
					try {
						WorldServer worldserver = this.E.server.getWorldServer(this.E.dimension);

						ChunkSender.this.plugin.getLogger().severe("==== DEBUG ====");
						ChunkSender.this.plugin.getLogger().severe("willsend.x: " + willsend.x);
						ChunkSender.this.plugin.getLogger().severe("willsend.z: " + willsend.z);

						this.E.playerConnection.sendPacket(
								new Packet51MapChunk(worldserver.getChunkAt(willsend.x, willsend.z), true, 0));

						List list = worldserver.getTileEntities(willsend.x, 0, willsend.z, willsend.x + 36, 256,
								willsend.z + 36);
						for (int j = 0; j < list.size(); j++)
							ChunkSender.this.M.invoke(this.E, new Object[] { list.get(j) });
					} catch (Exception e) {
						ChunkSender.this.plugin.getLogger().log(Level.SEVERE,
								"Error during chunksend for player <" + key + ">!", e);
					}
				}
			}

			if (waitset.size() > 0) {
				ChunkSender.this.waitinglist.put(key, waitset);
				ChunkSender.this.sendTask.put(key, Integer.valueOf(ChunkSender.this.plugin.getServer().getScheduler()
						.scheduleSyncDelayedTask(ChunkSender.this.plugin, new sender(this.E), 2L)));
			} else {
				ChunkSender.this.waitinglist.remove(key);
				ChunkSender.this.sendTask.remove(key);
			}
		}
	}
}