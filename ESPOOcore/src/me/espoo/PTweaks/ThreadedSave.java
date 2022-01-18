package me.espoo.PTweaks;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import net.minecraft.server.v1_5_R3.Chunk;
import net.minecraft.server.v1_5_R3.ChunkProviderServer;
import net.minecraft.server.v1_5_R3.IChunkLoader;
import net.minecraft.server.v1_5_R3.WorldServer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.util.LongObjectHashMap;

import me.espoo.main.main;

public class ThreadedSave implements Runnable {
	private main mPlugin;
	private Thread mThread;
	private boolean mRunning;
	private Field mChunkLoader;

	public ThreadedSave(main plugin) {
		this.mPlugin = plugin;
		this.mThread = new Thread(this);
	}

	public void onEnable() {
		try {
			this.mChunkLoader = ChunkProviderServer.class.getField("d");
			this.mChunkLoader.setAccessible(true);

			this.mRunning = true;
			this.mThread.start();
		} catch (Exception e) {
			this.mPlugin.getLogger().warning("Failed to start ThreadedSave: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void run() {
		while ((this.mRunning) && (!this.mThread.isInterrupted()))
			try {
				Thread.sleep(30000L);
				doSave();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	private void doSave() {
		for (World bWorld : this.mPlugin.getServer().getWorlds())
			saveWorld(bWorld);
	}

	private void saveWorld(World bWorld) {
		WorldServer mWorld = ((CraftWorld) bWorld).getHandle();
		try {
			mWorld.saveLevel();
		} catch (Exception e) {
			this.mPlugin.getLogger().warning("Error saving level: " + e.getMessage());
			e.printStackTrace();
		}

		ChunkProviderServer chunkP = mWorld.chunkProviderServer;
		LongObjectHashMap chunks = chunkP.chunks;

		IChunkLoader chunkL = null;
		try {
			chunkL = (IChunkLoader) this.mChunkLoader.get(chunkP);
		} catch (Exception ex) {
			this.mPlugin.getLogger().warning("ThreadedSave: Could not get chunk loader: " + ex.getMessage());
			ex.printStackTrace();
		}

		if (chunkL == null) {
			this.mPlugin.getLogger().warning("ThreadedSave: Could not save, ChunkLoader is null");
			return;
		}

		Iterator iterator = chunks.values().iterator();
		while (iterator.hasNext()) {
			try {
				Chunk chunk = (Chunk) iterator.next();
				for (int x = 0; x < 3; x++) {
					try {
						if (!Chunk.a) {
							chunkL.b(mWorld, chunk);
						} else if (chunk.a(true)) {
							chunkL.a(mWorld, chunk);
							Chunk.a = false;
						}
					} catch (Exception e) {
						if (x == 2)
							throw e;
					}
				}
			} catch (Exception e) {
				this.mPlugin.getLogger().warning("Threaded Save, error saving chunk: " + e.getMessage());
				e.printStackTrace();
			}
		}
		chunkL.b();
	}
}