package me.espoo.PTweaks;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

import me.espoo.main.main;

public class ConfigManager {
	private main mPlugin;
	private File autoSaveStopperFile;
	private File monsterLimiterFile;
	private File chunkPersistanceFile;

	public ConfigManager(main plugin) {
		this.mPlugin = plugin;
		setupDirs();
		this.autoSaveStopperFile = setupFiles(this.autoSaveStopperFile, "AutoSaveStopper.yml");
		this.monsterLimiterFile = setupFiles(this.monsterLimiterFile, "MonsterLimiter.yml");
		this.chunkPersistanceFile = setupFiles(this.chunkPersistanceFile, "ChunkPersistance.yml");
		loadAutoSaveStopper();
		loadMonsterLimiter();
		loadChunkPersistance();
		addDefaultsAutoSaveStopper();
		addDefaultsMonsterLimiter();
		addDefaultsChunkPersitance();
	}

	private void loadMonsterLimiter() {
		this.mPlugin.monsterLimiter = YamlConfiguration.loadConfiguration(this.monsterLimiterFile);
	}

	private void loadChunkPersistance() {
		this.mPlugin.chunkPersistance = YamlConfiguration.loadConfiguration(this.chunkPersistanceFile);
	}

	private void loadAutoSaveStopper() {
		this.mPlugin.autoSaveStopper = YamlConfiguration.loadConfiguration(this.autoSaveStopperFile);
	}

	private void addDefaultsChunkPersitance() {
		this.mPlugin.chunkPersistance.addDefault("Enabled", Boolean.valueOf(true));
		this.mPlugin.chunkPersistance.addDefault("lifetime", Integer.valueOf(100000));
		this.mPlugin.chunkPersistance.addDefault("prune", Integer.valueOf(3));
		this.mPlugin.chunkPersistance.addDefault("spawnChunkRadius", Integer.valueOf(64));
		this.mPlugin.chunkPersistance.options().header(
				"LifeTime: The minimum lifetime of inactive chunks in seconds\nPrune: The cycle in which inactive chunks are cleared\nSpawnChunkRadius: The radius of chunks that load around the player. Don't change this!\nViewDistance: The distance in which chunks will be loaded. ");
		this.mPlugin.chunkPersistance.options().copyDefaults(true);
		try {
			this.mPlugin.chunkPersistance.save(this.chunkPersistanceFile);
		} catch (IOException e) {
			this.mPlugin.getLogger().warning("Failed to save the ChunkPersistance.yml! " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void addDefaultsMonsterLimiter() {
		this.mPlugin.monsterLimiter.addDefault("Enabled", Boolean.valueOf(true));
		this.mPlugin.monsterLimiter.addDefault("MaxMobs", Integer.valueOf(2500));
		for (World w : this.mPlugin.getServer().getWorlds()) {
			this.mPlugin.monsterLimiter.addDefault("worlds." + w.getName() + ".monsterLimit", Integer.valueOf(2400));
			this.mPlugin.monsterLimiter.addDefault("worlds." + w.getName() + ".animalLimit", Integer.valueOf(2400));
		}
		this.mPlugin.monsterLimiter.options().header(
				"MaxMobs: Max amount of total mobs allowed alive at any given time\nMonsterLimit: The max hostile mobs at any given time\nAnimalLimit: The max friendly mobs at any given time");
		this.mPlugin.monsterLimiter.options().copyDefaults(true);
		try {
			this.mPlugin.monsterLimiter.save(this.monsterLimiterFile);
		} catch (IOException e) {
			this.mPlugin.getLogger().warning("Failed to save the MonsterLimiter.yml! " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void addDefaultsAutoSaveStopper() {
		this.mPlugin.autoSaveStopper.addDefault("interval", Integer.valueOf(50000));
		this.mPlugin.autoSaveStopper.addDefault("Enabled", Boolean.valueOf(true));
		this.mPlugin.autoSaveStopper.options().header("Interval: How many ticks before each Min-Save");
		this.mPlugin.autoSaveStopper.options().copyDefaults(true);
		try {
			this.mPlugin.autoSaveStopper.save(this.autoSaveStopperFile);
		} catch (IOException e) {
			this.mPlugin.getLogger().warning("Failed to save the AutoSaveStopper.yml! " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void setupDirs() {
		if (!this.mPlugin.getDataFolder().exists())
			this.mPlugin.getDataFolder().mkdirs();
	}

	private File setupFiles(File file, String fileName) {
		file = new File(this.mPlugin.getDataFolder(), fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				this.mPlugin.getLogger().warning("Failed to create the " + fileName + " file: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return file;
	}
}