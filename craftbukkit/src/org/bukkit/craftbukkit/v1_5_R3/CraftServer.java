// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.map.MapView;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.scoreboard.ScoreboardManager;
import java.net.InetSocketAddress;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemFactory;
import java.util.Comparator;
import java.util.Collections;
import org.bukkit.util.StringUtil;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.command.CommandException;
import net.minecraft.server.v1_5_R3.ICommandListener;
import org.bukkit.help.HelpMap;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryCustom;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.ChatColor;
import org.bukkit.plugin.messaging.Messenger;
import java.util.Arrays;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.GameMode;
import java.util.LinkedHashSet;
import java.util.HashSet;
import net.minecraft.server.v1_5_R3.BanEntry;
import java.io.FilenameFilter;
import org.bukkit.craftbukkit.v1_5_R3.util.DatFileFilter;
import net.minecraft.server.v1_5_R3.WorldNBTStorage;
import java.util.Set;
import org.bukkit.permissions.Permissible;
import net.minecraft.server.v1_5_R3.Item;
import net.minecraft.server.v1_5_R3.WorldMapCollection;
import net.minecraft.server.v1_5_R3.WorldMap;
import org.bukkit.craftbukkit.v1_5_R3.map.CraftMapView;
import org.bukkit.configuration.ConfigurationSection;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.v1_5_R3.RecipesFurnace;
import org.bukkit.craftbukkit.v1_5_R3.inventory.RecipeIterator;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.CraftingManager;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftFurnaceRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftShapelessRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftShapedRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftRecipe;
import org.bukkit.inventory.Recipe;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import java.util.UUID;
import net.minecraft.server.v1_5_R3.RegionFile;
import net.minecraft.server.v1_5_R3.RegionFileCache;
import net.minecraft.server.v1_5_R3.ExceptionWorldConflict;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import net.minecraft.server.v1_5_R3.ChunkCoordinates;
import net.minecraft.server.v1_5_R3.Convertable;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.Event;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;
import net.minecraft.server.v1_5_R3.IWorldAccess;
import net.minecraft.server.v1_5_R3.WorldManager;
import net.minecraft.server.v1_5_R3.EntityTracker;
import net.minecraft.server.v1_5_R3.IDataManager;
import net.minecraft.server.v1_5_R3.WorldSettings;
import net.minecraft.server.v1_5_R3.EnumGamemode;
import net.minecraft.server.v1_5_R3.ServerNBTManager;
import net.minecraft.server.v1_5_R3.IProgressUpdate;
import net.minecraft.server.v1_5_R3.ConvertProgressUpdater;
import net.minecraft.server.v1_5_R3.WorldLoaderServer;
import net.minecraft.server.v1_5_R3.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.WorldCreator;
import org.yaml.snakeyaml.error.MarkedYAMLException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import org.bukkit.scheduler.BukkitWorker;
import net.minecraft.server.v1_5_R3.WorldServer;
import net.minecraft.server.v1_5_R3.DedicatedServer;
import net.minecraft.server.v1_5_R3.PropertyManager;
import org.bukkit.conversations.Conversable;
import net.minecraft.server.v1_5_R3.ServerCommand;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.permissions.Permission;
import org.bukkit.util.permissions.DefaultPermissions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPluginLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.PluginLoadOrder;
import java.io.IOException;
import java.util.Collection;
import org.bukkit.craftbukkit.v1_5_R3.updater.BukkitDLUpdaterService;
import org.bukkit.configuration.Configuration;
import org.bukkit.craftbukkit.Main;
import org.bukkit.potion.PotionEffectType;
import net.minecraft.server.v1_5_R3.MobEffectList;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.potion.Potion;
import org.bukkit.craftbukkit.v1_5_R3.potion.CraftPotionBrewer;
import net.minecraft.server.v1_5_R3.Enchantment;
import org.bukkit.Bukkit;
import com.google.common.collect.MapMaker;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import java.util.LinkedHashMap;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.bukkit.craftbukkit.v1_5_R3.util.Versioning;
import net.minecraft.server.v1_5_R3.PlayerList;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.scoreboard.CraftScoreboardManager;
import org.bukkit.Warning;
import java.io.File;
import org.bukkit.craftbukkit.v1_5_R3.metadata.WorldMetadataStore;
import org.bukkit.craftbukkit.v1_5_R3.metadata.PlayerMetadataStore;
import org.bukkit.craftbukkit.v1_5_R3.metadata.EntityMetadataStore;
import org.bukkit.craftbukkit.v1_5_R3.updater.AutoUpdater;
import org.bukkit.OfflinePlayer;
import org.yaml.snakeyaml.Yaml;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.World;
import java.util.Map;
import net.minecraft.server.v1_5_R3.DedicatedPlayerList;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.craftbukkit.v1_5_R3.help.SimpleHelpMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_5_R3.scheduler.CraftScheduler;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.Server;

public final class CraftServer implements Server
{
    private final String serverName = "CraftBukkit";
    private final String serverVersion;
    private final String bukkitVersion;
    private final ServicesManager servicesManager;
    private final CraftScheduler scheduler;
    private final SimpleCommandMap commandMap;
    private final SimpleHelpMap helpMap;
    private final StandardMessenger messenger;
    private final PluginManager pluginManager;
    protected final MinecraftServer console;
    protected final DedicatedPlayerList playerList;
    private final Map<String, World> worlds;
    public YamlConfiguration configuration;
    private final Yaml yaml;
    private final Map<String, OfflinePlayer> offlinePlayers;
    private final AutoUpdater updater;
    private final EntityMetadataStore entityMetadata;
    private final PlayerMetadataStore playerMetadata;
    private final WorldMetadataStore worldMetadata;
    private int monsterSpawn;
    private int animalSpawn;
    private int waterAnimalSpawn;
    private int ambientSpawn;
    public int chunkGCPeriod;
    public int chunkGCLoadThresh;
    private File container;
    private Warning.WarningState warningState;
    private final BooleanWrapper online;
    public CraftScoreboardManager scoreboardManager;
    public boolean orebfuscatorEnabled;
    public int orebfuscatorEngineMode;
    public List<String> orebfuscatorDisabledWorlds;
    public List<Short> orebfuscatorBlocks;
    public String whitelistMessage;
    public String stopMessage;
    public boolean logCommands;
    public boolean commandComplete;
    public List<String> spamGuardExclusions;
    
    public CraftServer(final MinecraftServer console, final PlayerList playerList) {
        this.bukkitVersion = Versioning.getBukkitVersion();
        this.servicesManager = new SimpleServicesManager();
        this.scheduler = new CraftScheduler();
        this.commandMap = new SimpleCommandMap(this);
        this.helpMap = new SimpleHelpMap(this);
        this.messenger = new StandardMessenger();
        this.pluginManager = new SimplePluginManager(this, this.commandMap);
        this.worlds = new LinkedHashMap<String, World>();
        this.yaml = new Yaml(new SafeConstructor());
        this.offlinePlayers = (Map<String, OfflinePlayer>)new MapMaker().softValues().makeMap();
        this.entityMetadata = new EntityMetadataStore();
        this.playerMetadata = new PlayerMetadataStore();
        this.worldMetadata = new WorldMetadataStore();
        this.monsterSpawn = -1;
        this.animalSpawn = -1;
        this.waterAnimalSpawn = -1;
        this.ambientSpawn = -1;
        this.chunkGCPeriod = -1;
        this.chunkGCLoadThresh = 0;
        this.warningState = Warning.WarningState.DEFAULT;
        this.online = new BooleanWrapper();
        this.orebfuscatorEnabled = false;
        this.orebfuscatorEngineMode = 1;
        this.whitelistMessage = "You are not white-listed on this server!";
        this.stopMessage = "Server restarting. Brb";
        this.logCommands = true;
        this.commandComplete = true;
        this.console = console;
        this.playerList = (DedicatedPlayerList)playerList;
        this.serverVersion = CraftServer.class.getPackage().getImplementationVersion();
        this.online.value = console.getPropertyManager().getBoolean("online-mode", true);
        Bukkit.setServer(this);
        Enchantment.DAMAGE_ALL.getClass();
        org.bukkit.enchantments.Enchantment.stopAcceptingRegistrations();
        Potion.setPotionBrewer(new CraftPotionBrewer());
        MobEffectList.BLINDNESS.getClass();
        PotionEffectType.stopAcceptingRegistrations();
        if (!Main.useConsole) {
            this.getLogger().info("Console input is disabled due to --noconsole command argument");
        }
        this.configuration = YamlConfiguration.loadConfiguration(this.getConfigFile());
        this.configuration.options().copyDefaults(true);
        this.configuration.setDefaults(YamlConfiguration.loadConfiguration(this.getClass().getClassLoader().getResourceAsStream("configurations/bukkit.yml")));
        this.saveConfig();
        ((SimplePluginManager)this.pluginManager).useTimings(this.configuration.getBoolean("settings.plugin-profiling"));
        this.monsterSpawn = this.configuration.getInt("spawn-limits.monsters");
        this.animalSpawn = this.configuration.getInt("spawn-limits.animals");
        this.waterAnimalSpawn = this.configuration.getInt("spawn-limits.water-animals");
        this.ambientSpawn = this.configuration.getInt("spawn-limits.ambient");
        console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
        this.warningState = Warning.WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
        this.chunkGCPeriod = this.configuration.getInt("chunk-gc.period-in-ticks");
        this.chunkGCLoadThresh = this.configuration.getInt("chunk-gc.load-threshold");
        (this.updater = new AutoUpdater(new BukkitDLUpdaterService(this.configuration.getString("auto-updater.host")), this.getLogger(), this.configuration.getString("auto-updater.preferred-channel"))).setEnabled(false);
        this.updater.setSuggestChannels(this.configuration.getBoolean("auto-updater.suggest-channels"));
        this.updater.getOnBroken().addAll(this.configuration.getStringList("auto-updater.on-broken"));
        this.updater.getOnUpdate().addAll(this.configuration.getStringList("auto-updater.on-update"));
        this.updater.check(this.serverVersion);
        Spigot.initialize(this, this.commandMap, this.configuration);
        try {
            this.configuration.save(this.getConfigFile());
        }
        catch (IOException ex) {}
        this.loadPlugins();
        this.enablePlugins(PluginLoadOrder.STARTUP);
    }
    
    private File getConfigFile() {
        return (File)this.console.options.valueOf("bukkit-settings");
    }
    
    private void saveConfig() {
        try {
            this.configuration.save(this.getConfigFile());
        }
        catch (IOException ex) {
            Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + this.getConfigFile(), ex);
        }
    }
    
    public void loadPlugins() {
        this.pluginManager.registerInterface(JavaPluginLoader.class);
        final File pluginFolder = (File)this.console.options.valueOf("plugins");
        if (pluginFolder.exists()) {
            final Plugin[] arr$;
            final Plugin[] plugins = arr$ = this.pluginManager.loadPlugins(pluginFolder);
            for (final Plugin plugin : arr$) {
                try {
                    final String message = String.format("Loading %s", plugin.getDescription().getFullName());
                    plugin.getLogger().info(message);
                    plugin.onLoad();
                }
                catch (Throwable ex) {
                    Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
                }
            }
        }
        else {
            pluginFolder.mkdir();
        }
    }
    
    public void enablePlugins(final PluginLoadOrder type) {
        if (type == PluginLoadOrder.STARTUP) {
            this.helpMap.clear();
            this.helpMap.initializeGeneralTopics();
        }
        final Plugin[] arr$;
        final Plugin[] plugins = arr$ = this.pluginManager.getPlugins();
        for (final Plugin plugin : arr$) {
            if (!plugin.isEnabled() && plugin.getDescription().getLoad() == type) {
                this.loadPlugin(plugin);
            }
        }
        if (type == PluginLoadOrder.POSTWORLD) {
            this.commandMap.registerServerAliases();
            this.loadCustomPermissions();
            DefaultPermissions.registerCorePermissions();
            this.helpMap.initializeCommands();
        }
    }
    
    public void disablePlugins() {
        this.pluginManager.disablePlugins();
    }
    
    private void loadPlugin(final Plugin plugin) {
        try {
            this.pluginManager.enablePlugin(plugin);
            final List<Permission> perms = plugin.getDescription().getPermissions();
            for (final Permission perm : perms) {
                try {
                    this.pluginManager.addPermission(perm);
                }
                catch (IllegalArgumentException ex) {
                    this.getLogger().log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", ex);
                }
            }
        }
        catch (Throwable ex2) {
            Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex2.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex2);
        }
    }
    
    public String getName() {
        return "CraftBukkit";
    }
    
    public String getVersion() {
        return this.serverVersion + " (MC: " + this.console.getVersion() + ")";
    }
    
    public String getBukkitVersion() {
        return this.bukkitVersion;
    }
    
    public Player[] getOnlinePlayers() {
        final List<EntityPlayer> online = (List<EntityPlayer>)this.playerList.players;
        final Player[] players = new Player[online.size()];
        for (int i = 0; i < players.length; ++i) {
            players[i] = online.get(i).playerConnection.getPlayer();
        }
        return players;
    }
    
    public Player getPlayer(final String name) {
        Validate.notNull(name, "Name cannot be null");
        final Player[] players = this.getOnlinePlayers();
        Player found = null;
        final String lowerName = name.toLowerCase();
        int delta = Integer.MAX_VALUE;
        for (final Player player : players) {
            if (player.getName().toLowerCase().startsWith(lowerName)) {
                final int curDelta = player.getName().length() - lowerName.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) {
                    break;
                }
            }
        }
        return found;
    }
    
    public Player getPlayerExact(final String name) {
        Validate.notNull(name, "Name cannot be null");
        final String lname = name.toLowerCase();
        for (final Player player : this.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(lname)) {
                return player;
            }
        }
        return null;
    }
    
    public int broadcastMessage(final String message) {
        return this.broadcast(message, "bukkit.broadcast.user");
    }
    
    public Player getPlayer(final EntityPlayer entity) {
        return entity.playerConnection.getPlayer();
    }
    
    public List<Player> matchPlayer(final String partialName) {
        Validate.notNull(partialName, "PartialName cannot be null");
        final List<Player> matchedPlayers = new ArrayList<Player>();
        for (final Player iterPlayer : this.getOnlinePlayers()) {
            final String iterPlayerName = iterPlayer.getName();
            if (partialName.equalsIgnoreCase(iterPlayerName)) {
                matchedPlayers.clear();
                matchedPlayers.add(iterPlayer);
                break;
            }
            if (iterPlayerName.toLowerCase().contains(partialName.toLowerCase())) {
                matchedPlayers.add(iterPlayer);
            }
        }
        return matchedPlayers;
    }
    
    public int getMaxPlayers() {
        return this.playerList.getMaxPlayers();
    }
    
    public int getPort() {
        return this.getConfigInt("server-port", 25565);
    }
    
    public int getViewDistance() {
        return this.getConfigInt("view-distance", 10);
    }
    
    public String getIp() {
        return this.getConfigString("server-ip", "");
    }
    
    public String getServerName() {
        return this.getConfigString("server-name", "Unknown Server");
    }
    
    public String getServerId() {
        return this.getConfigString("server-id", "unnamed");
    }
    
    public String getWorldType() {
        return this.getConfigString("level-type", "DEFAULT");
    }
    
    public boolean getGenerateStructures() {
        return this.getConfigBoolean("generate-structures", true);
    }
    
    public boolean getAllowEnd() {
        return this.configuration.getBoolean("settings.allow-end");
    }
    
    public boolean getAllowNether() {
        return this.getConfigBoolean("allow-nether", true);
    }
    
    public boolean getWarnOnOverload() {
        return this.configuration.getBoolean("settings.warn-on-overload");
    }
    
    public boolean getQueryPlugins() {
        return this.configuration.getBoolean("settings.query-plugins");
    }
    
    public boolean hasWhitelist() {
        return this.getConfigBoolean("white-list", false);
    }
    
    private String getConfigString(final String variable, final String defaultValue) {
        return this.console.getPropertyManager().getString(variable, defaultValue);
    }
    
    private int getConfigInt(final String variable, final int defaultValue) {
        return this.console.getPropertyManager().getInt(variable, defaultValue);
    }
    
    private boolean getConfigBoolean(final String variable, final boolean defaultValue) {
        return this.console.getPropertyManager().getBoolean(variable, defaultValue);
    }
    
    public String getUpdateFolder() {
        return this.configuration.getString("settings.update-folder", "update");
    }
    
    public File getUpdateFolderFile() {
        return new File((File)this.console.options.valueOf("plugins"), this.configuration.getString("settings.update-folder", "update"));
    }
    
    public int getPingPacketLimit() {
        return this.configuration.getInt("settings.ping-packet-limit", 100);
    }
    
    public long getConnectionThrottle() {
        return this.configuration.getInt("settings.connection-throttle");
    }
    
    public int getTicksPerAnimalSpawns() {
        return this.configuration.getInt("ticks-per.animal-spawns");
    }
    
    public int getTicksPerMonsterSpawns() {
        return this.configuration.getInt("ticks-per.monster-spawns");
    }
    
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }
    
    public CraftScheduler getScheduler() {
        return this.scheduler;
    }
    
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }
    
    public List<World> getWorlds() {
        return new ArrayList<World>(this.worlds.values());
    }
    
    public DedicatedPlayerList getHandle() {
        return this.playerList;
    }
    
    public boolean dispatchServerCommand(final CommandSender sender, final ServerCommand serverCommand) {
        if (sender instanceof Conversable) {
            final Conversable conversable = (Conversable)sender;
            if (conversable.isConversing()) {
                conversable.acceptConversationInput(serverCommand.command);
                return true;
            }
        }
        try {
            return this.dispatchCommand(sender, serverCommand.command);
        }
        catch (Exception ex) {
            this.getLogger().log(Level.WARNING, "Unexpected exception while parsing console command \"" + serverCommand.command + '\"', ex);
            return false;
        }
    }
    
    public boolean dispatchCommand(final CommandSender sender, final String commandLine) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(commandLine, "CommandLine cannot be null");
        if (this.commandMap.dispatch(sender, commandLine)) {
            return true;
        }
        sender.sendMessage("Unknown command. Type \"help\" for help.");
        return false;
    }
    
    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.getConfigFile());
        final PropertyManager config = new PropertyManager(this.console.options, this.console.getLogger());
        ((DedicatedServer)this.console).propertyManager = config;
        ((SimplePluginManager)this.pluginManager).useTimings(this.configuration.getBoolean("settings.plugin-profiling"));
        final boolean animals = config.getBoolean("spawn-animals", this.console.getSpawnAnimals());
        final boolean monsters = config.getBoolean("spawn-monsters", this.console.worlds.get(0).difficulty > 0);
        final int difficulty = config.getInt("difficulty", this.console.worlds.get(0).difficulty);
        this.online.value = config.getBoolean("online-mode", this.console.getOnlineMode());
        this.console.setSpawnAnimals(config.getBoolean("spawn-animals", this.console.getSpawnAnimals()));
        this.console.setPvP(config.getBoolean("pvp", this.console.getPvP()));
        this.console.setAllowFlight(config.getBoolean("allow-flight", this.console.getAllowFlight()));
        this.console.setMotd(config.getString("motd", this.console.getMotd()));
        this.monsterSpawn = this.configuration.getInt("spawn-limits.monsters");
        this.animalSpawn = this.configuration.getInt("spawn-limits.animals");
        this.waterAnimalSpawn = this.configuration.getInt("spawn-limits.water-animals");
        this.ambientSpawn = this.configuration.getInt("spawn-limits.ambient");
        this.warningState = Warning.WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
        this.console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
        this.chunkGCPeriod = this.configuration.getInt("chunk-gc.period-in-ticks");
        this.chunkGCLoadThresh = this.configuration.getInt("chunk-gc.load-threshold");
        this.playerList.getIPBans().load();
        this.playerList.getNameBans().load();
        for (final WorldServer world : this.console.worlds) {
            world.difficulty = difficulty;
            world.setSpawnFlags(monsters, animals);
            if (this.getTicksPerAnimalSpawns() < 0) {
                world.ticksPerAnimalSpawns = 400L;
            }
            else {
                world.ticksPerAnimalSpawns = this.getTicksPerAnimalSpawns();
            }
            if (this.getTicksPerMonsterSpawns() < 0) {
                world.ticksPerMonsterSpawns = 1L;
            }
            else {
                world.ticksPerMonsterSpawns = this.getTicksPerMonsterSpawns();
            }
        }
        this.pluginManager.clearPlugins();
        this.commandMap.clearCommands();
        this.resetRecipes();
        for (int pollCount = 0; pollCount < 50 && this.getScheduler().getActiveWorkers().size() > 0; ++pollCount) {
            try {
                Thread.sleep(50L);
            }
            catch (InterruptedException ex) {}
        }
        final List<BukkitWorker> overdueWorkers = this.getScheduler().getActiveWorkers();
        for (final BukkitWorker worker : overdueWorkers) {
            final Plugin plugin = worker.getOwner();
            String author = "<NoAuthorGiven>";
            if (plugin.getDescription().getAuthors().size() > 0) {
                author = plugin.getDescription().getAuthors().get(0);
            }
            this.getLogger().log(Level.SEVERE, String.format("Nag author: '%s' of '%s' about the following: %s", author, plugin.getDescription().getName(), "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin"));
        }
        Spigot.initialize(this, this.commandMap, this.configuration);
        this.loadPlugins();
        this.enablePlugins(PluginLoadOrder.STARTUP);
        this.enablePlugins(PluginLoadOrder.POSTWORLD);
    }
    
    private void loadCustomPermissions() {
        final File file = new File(this.configuration.getString("settings.permissions-file"));
        FileInputStream stream;
        try {
            stream = new FileInputStream(file);
        }
        catch (FileNotFoundException ex4) {
            try {
                file.createNewFile();
                return;
            }
            finally {
                return;
            }
        }
        Map<String, Map<String, Object>> perms;
        try {
            perms = (Map<String, Map<String, Object>>)this.yaml.load(stream);
        }
        catch (MarkedYAMLException ex) {
            this.getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString());
            return;
        }
        catch (Throwable ex2) {
            this.getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex2);
            return;
        }
        finally {
            try {
                stream.close();
            }
            catch (IOException ex5) {}
        }
        if (perms == null) {
            this.getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
            return;
        }
        final List<Permission> permsList = Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid", Permission.DEFAULT_PERMISSION);
        for (final Permission perm : permsList) {
            try {
                this.pluginManager.addPermission(perm);
            }
            catch (IllegalArgumentException ex3) {
                this.getLogger().log(Level.SEVERE, "Permission in " + file + " was already defined", ex3);
            }
        }
    }
    
    public String toString() {
        return "CraftServer{serverName=CraftBukkit,serverVersion=" + this.serverVersion + ",minecraftVersion=" + this.console.getVersion() + '}';
    }
    
    public World createWorld(final String name, final World.Environment environment) {
        return WorldCreator.name(name).environment(environment).createWorld();
    }
    
    public World createWorld(final String name, final World.Environment environment, final long seed) {
        return WorldCreator.name(name).environment(environment).seed(seed).createWorld();
    }
    
    public World createWorld(final String name, final World.Environment environment, final ChunkGenerator generator) {
        return WorldCreator.name(name).environment(environment).generator(generator).createWorld();
    }
    
    public World createWorld(final String name, final World.Environment environment, final long seed, final ChunkGenerator generator) {
        return WorldCreator.name(name).environment(environment).seed(seed).generator(generator).createWorld();
    }
    
    public World createWorld(final WorldCreator creator) {
        Validate.notNull(creator, "Creator may not be null");
        final String name = creator.name();
        ChunkGenerator generator = creator.generator();
        final File folder = new File(this.getWorldContainer(), name);
        final World world = this.getWorld(name);
        final WorldType type = WorldType.getType(creator.type().getName());
        final boolean generateStructures = creator.generateStructures();
        if (world != null) {
            return world;
        }
        if (folder.exists() && !folder.isDirectory()) {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        }
        if (generator == null) {
            generator = this.getGenerator(name);
        }
        final Convertable converter = new WorldLoaderServer(this.getWorldContainer());
        if (converter.isConvertable(name)) {
            this.getLogger().info("Converting world '" + name + "'");
            converter.convert(name, new ConvertProgressUpdater(this.console));
        }
        int dimension = 10 + this.console.worlds.size();
        boolean used = false;
        do {
            for (final WorldServer server : this.console.worlds) {
                used = (server.dimension == dimension);
                if (used) {
                    ++dimension;
                    break;
                }
            }
        } while (used);
        final boolean hardcore = false;
        final WorldServer internal = new WorldServer(this.console, new ServerNBTManager(this.getWorldContainer(), name, true), name, dimension, new WorldSettings(creator.seed(), EnumGamemode.a(this.getDefaultGameMode().getValue()), generateStructures, hardcore, type), this.console.methodProfiler, this.console.getLogger(), creator.environment(), generator);
        if (!this.worlds.containsKey(name.toLowerCase())) {
            return null;
        }
        internal.worldMaps = this.console.worlds.get(0).worldMaps;
        internal.scoreboard = this.getScoreboardManager().getMainScoreboard().getHandle();
        internal.tracker = new EntityTracker(internal);
        internal.addIWorldAccess(new WorldManager(this.console, internal));
        internal.difficulty = 1;
        internal.setSpawnFlags(true, true);
        this.console.worlds.add(internal);
        if (generator != null) {
            internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
        }
        this.pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));
        System.out.print("Preparing start region for level " + (this.console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");
        if (internal.getWorld().getKeepSpawnInMemory()) {
            final short short1 = 196;
            long i = System.currentTimeMillis();
            for (int j = -short1; j <= short1; j += 16) {
                for (int k = -short1; k <= short1; k += 16) {
                    final long l = System.currentTimeMillis();
                    if (l < i) {
                        i = l;
                    }
                    if (l > i + 1000L) {
                        final int i2 = (short1 * 2 + 1) * (short1 * 2 + 1);
                        final int j2 = (j + short1) * (short1 * 2 + 1) + k + 1;
                        System.out.println("Preparing spawn area for " + name + ", " + j2 * 100 / i2 + "%");
                        i = l;
                    }
                    final ChunkCoordinates chunkcoordinates = internal.getSpawn();
                    internal.chunkProviderServer.getChunkAt(chunkcoordinates.x + j >> 4, chunkcoordinates.z + k >> 4);
                }
            }
        }
        this.pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
        return internal.getWorld();
    }
    
    public boolean unloadWorld(final String name, final boolean save) {
        return this.unloadWorld(this.getWorld(name), save);
    }
    
    public boolean unloadWorld(final World world, final boolean save) {
        if (world == null) {
            return false;
        }
        final WorldServer handle = ((CraftWorld)world).getHandle();
        if (!this.console.worlds.contains(handle)) {
            return false;
        }
        if (handle.dimension <= 1) {
            return false;
        }
        if (handle.players.size() > 0) {
            return false;
        }
        final WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());
        this.pluginManager.callEvent(e);
        if (e.isCancelled()) {
            return false;
        }
        if (save) {
            try {
                handle.save(true, null);
                handle.saveLevel();
                final WorldSaveEvent event = new WorldSaveEvent(handle.getWorld());
                this.getPluginManager().callEvent(event);
            }
            catch (ExceptionWorldConflict ex) {
                this.getLogger().log(Level.SEVERE, null, ex);
            }
        }
        this.worlds.remove(world.getName().toLowerCase());
        this.console.worlds.remove(this.console.worlds.indexOf(handle));
        final File parentFolder = world.getWorldFolder().getAbsoluteFile();
        synchronized (RegionFileCache.class) {
            final Iterator<Map.Entry<File, RegionFile>> i = RegionFileCache.a.entrySet().iterator();
            while (i.hasNext()) {
                final Map.Entry<File, RegionFile> entry = i.next();
                for (File child = entry.getKey().getAbsoluteFile(); child != null; child = child.getParentFile()) {
                    if (child.equals(parentFolder)) {
                        i.remove();
                        try {
                            entry.getValue().c();
                        }
                        catch (IOException ex2) {
                            this.getLogger().log(Level.SEVERE, null, ex2);
                        }
                        break;
                    }
                }
            }
        }
        return true;
    }
    
    public MinecraftServer getServer() {
        return this.console;
    }
    
    public World getWorld(final String name) {
        Validate.notNull(name, "Name cannot be null");
        return this.worlds.get(name.toLowerCase());
    }
    
    public World getWorld(final UUID uid) {
        for (final World world : this.worlds.values()) {
            if (world.getUID().equals(uid)) {
                return world;
            }
        }
        return null;
    }
    
    public void addWorld(final World world) {
        if (this.getWorld(world.getUID()) != null) {
            System.out.println("World " + world.getName() + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + world.getName() + "'s world directory if you want to be able to load the duplicate world.");
            return;
        }
        this.worlds.put(world.getName().toLowerCase(), world);
    }
    
    public Logger getLogger() {
        return this.console.getLogger().getLogger();
    }
    
    public ConsoleReader getReader() {
        return this.console.reader;
    }
    
    public PluginCommand getPluginCommand(final String name) {
        final Command command = this.commandMap.getCommand(name);
        if (command instanceof PluginCommand) {
            return (PluginCommand)command;
        }
        return null;
    }
    
    public void savePlayers() {
        this.playerList.savePlayers();
    }
    
    public void configureDbConfig(final ServerConfig config) {
        Validate.notNull(config, "Config cannot be null");
        final DataSourceConfig ds = new DataSourceConfig();
        ds.setDriver(this.configuration.getString("database.driver"));
        ds.setUrl(this.configuration.getString("database.url"));
        ds.setUsername(this.configuration.getString("database.username"));
        ds.setPassword(this.configuration.getString("database.password"));
        ds.setIsolationLevel(TransactionIsolation.getLevel(this.configuration.getString("database.isolation")));
        if (ds.getDriver().contains("sqlite")) {
            config.setDatabasePlatform(new SQLitePlatform());
            config.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
        }
        config.setDataSourceConfig(ds);
    }
    
    public boolean addRecipe(final Recipe recipe) {
        CraftRecipe toAdd;
        if (recipe instanceof CraftRecipe) {
            toAdd = (CraftRecipe)recipe;
        }
        else if (recipe instanceof ShapedRecipe) {
            toAdd = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe)recipe);
        }
        else if (recipe instanceof ShapelessRecipe) {
            toAdd = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe)recipe);
        }
        else {
            if (!(recipe instanceof FurnaceRecipe)) {
                return false;
            }
            toAdd = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe)recipe);
        }
        toAdd.addToCraftingManager();
        CraftingManager.getInstance().sort();
        return true;
    }
    
    public List<Recipe> getRecipesFor(final ItemStack result) {
        Validate.notNull(result, "Result cannot be null");
        final List<Recipe> results = new ArrayList<Recipe>();
        final Iterator<Recipe> iter = this.recipeIterator();
        while (iter.hasNext()) {
            final Recipe recipe = iter.next();
            final ItemStack stack = recipe.getResult();
            if (stack.getType() != result.getType()) {
                continue;
            }
            if (result.getDurability() != -1 && result.getDurability() != stack.getDurability()) {
                continue;
            }
            results.add(recipe);
        }
        return results;
    }
    
    public Iterator<Recipe> recipeIterator() {
        return new RecipeIterator();
    }
    
    public void clearRecipes() {
        CraftingManager.getInstance().recipes.clear();
        RecipesFurnace.getInstance().recipes.clear();
    }
    
    public void resetRecipes() {
        CraftingManager.getInstance().recipes = new CraftingManager().recipes;
        RecipesFurnace.getInstance().recipes = new RecipesFurnace().recipes;
    }
    
    public Map<String, String[]> getCommandAliases() {
        final ConfigurationSection section = this.configuration.getConfigurationSection("aliases");
        final Map<String, String[]> result = new LinkedHashMap<String, String[]>();
        if (section != null) {
            for (final String key : section.getKeys(false)) {
                List<String> commands;
                if (section.isList(key)) {
                    commands = section.getStringList(key);
                }
                else {
                    commands = ImmutableList.of(section.getString(key));
                }
                result.put(key, commands.toArray(new String[commands.size()]));
            }
        }
        return result;
    }
    
    public void removeBukkitSpawnRadius() {
        this.configuration.set("settings.spawn-radius", null);
        this.saveConfig();
    }
    
    public int getBukkitSpawnRadius() {
        return this.configuration.getInt("settings.spawn-radius", -1);
    }
    
    public String getShutdownMessage() {
        return this.configuration.getString("settings.shutdown-message");
    }
    
    public int getSpawnRadius() {
        return ((DedicatedServer)this.console).propertyManager.getInt("spawn-protection", 16);
    }
    
    public void setSpawnRadius(final int value) {
        this.configuration.set("settings.spawn-radius", value);
        this.saveConfig();
    }
    
    public boolean getOnlineMode() {
        return this.online.value;
    }
    
    public boolean getAllowFlight() {
        return this.console.getAllowFlight();
    }
    
    public boolean isHardcore() {
        return this.console.isHardcore();
    }
    
    public boolean useExactLoginLocation() {
        return this.configuration.getBoolean("settings.use-exact-login-location");
    }
    
    public ChunkGenerator getGenerator(final String world) {
        ConfigurationSection section = this.configuration.getConfigurationSection("worlds");
        ChunkGenerator result = null;
        if (section != null) {
            section = section.getConfigurationSection(world);
            if (section != null) {
                final String name = section.getString("generator");
                if (name != null && !name.equals("")) {
                    final String[] split = name.split(":", 2);
                    final String id = (split.length > 1) ? split[1] : null;
                    final Plugin plugin = this.pluginManager.getPlugin(split[0]);
                    if (plugin == null) {
                        this.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
                    }
                    else if (!plugin.isEnabled()) {
                        this.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled yet (is it load:STARTUP?)");
                    }
                    else {
                        result = plugin.getDefaultWorldGenerator(world, id);
                        if (result == null) {
                            this.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' lacks a default world generator");
                        }
                    }
                }
            }
        }
        return result;
    }
    
    public CraftMapView getMap(final short id) {
        final WorldMapCollection collection = this.console.worlds.get(0).worldMaps;
        final WorldMap worldmap = (WorldMap)collection.get(WorldMap.class, "map_" + id);
        if (worldmap == null) {
            return null;
        }
        return worldmap.mapView;
    }
    
    public CraftMapView createMap(final World world) {
        Validate.notNull(world, "World cannot be null");
        final net.minecraft.server.v1_5_R3.ItemStack stack = new net.minecraft.server.v1_5_R3.ItemStack(Item.MAP, 1, -1);
        final WorldMap worldmap = Item.MAP.getSavedMap(stack, ((CraftWorld)world).getHandle());
        return worldmap.mapView;
    }
    
    public void shutdown() {
        this.console.safeShutdown();
    }
    
    public int broadcast(final String message, final String permission) {
        int count = 0;
        final Set<Permissible> permissibles = this.getPluginManager().getPermissionSubscriptions(permission);
        for (final Permissible permissible : permissibles) {
            if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
                final CommandSender user = (CommandSender)permissible;
                user.sendMessage(message);
                ++count;
            }
        }
        return count;
    }
    
    public OfflinePlayer getOfflinePlayer(final String name) {
        return this.getOfflinePlayer(name, false);
    }
    
    public OfflinePlayer getOfflinePlayer(String name, final boolean search) {
        Validate.notNull(name, "Name cannot be null");
        OfflinePlayer result = this.getPlayerExact(name);
        final String lname = name.toLowerCase();
        if (result == null) {
            result = this.offlinePlayers.get(lname);
            if (result == null) {
                if (search) {
                    final WorldNBTStorage storage = (WorldNBTStorage)this.console.worlds.get(0).getDataManager();
                    for (final String dat : storage.getPlayerDir().list(new DatFileFilter())) {
                        final String datName = dat.substring(0, dat.length() - 4);
                        if (datName.equalsIgnoreCase(name)) {
                            name = datName;
                            break;
                        }
                    }
                }
                result = new CraftOfflinePlayer(this, name);
                this.offlinePlayers.put(lname, result);
            }
        }
        else {
            this.offlinePlayers.remove(lname);
        }
        return result;
    }
    
    public Set<String> getIPBans() {
        return this.playerList.getIPBans().getEntries().keySet();
    }
    
    public void banIP(final String address) {
        Validate.notNull(address, "Address cannot be null.");
        final BanEntry entry = new BanEntry(address);
        this.playerList.getIPBans().add(entry);
        this.playerList.getIPBans().save();
    }
    
    public void unbanIP(final String address) {
        this.playerList.getIPBans().remove(address);
        this.playerList.getIPBans().save();
    }
    
    public Set<OfflinePlayer> getBannedPlayers() {
        final Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();
        for (final Object name : this.playerList.getNameBans().getEntries().keySet()) {
            result.add(this.getOfflinePlayer((String)name));
        }
        return result;
    }
    
    public void setWhitelist(final boolean value) {
        this.playerList.hasWhitelist = value;
        this.console.getPropertyManager().a("white-list", value);
    }
    
    public Set<OfflinePlayer> getWhitelistedPlayers() {
        final Set<OfflinePlayer> result = new LinkedHashSet<OfflinePlayer>();
        for (final Object name : this.playerList.getWhitelisted()) {
            if (((String)name).length() != 0) {
                if (((String)name).startsWith("#")) {
                    continue;
                }
                result.add(this.getOfflinePlayer((String)name));
            }
        }
        return result;
    }
    
    public Set<OfflinePlayer> getOperators() {
        final Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();
        for (final Object name : this.playerList.getOPs()) {
            result.add(this.getOfflinePlayer((String)name));
        }
        return result;
    }
    
    public void reloadWhitelist() {
        this.playerList.reloadWhitelist();
    }
    
    public GameMode getDefaultGameMode() {
        return GameMode.getByValue(this.console.worlds.get(0).getWorldData().getGameType().a());
    }
    
    public void setDefaultGameMode(final GameMode mode) {
        Validate.notNull(mode, "Mode cannot be null");
        for (final World world : this.getWorlds()) {
            ((CraftWorld)world).getHandle().worldData.setGameType(EnumGamemode.a(mode.getValue()));
        }
    }
    
    public ConsoleCommandSender getConsoleSender() {
        return this.console.console;
    }
    
    public EntityMetadataStore getEntityMetadata() {
        return this.entityMetadata;
    }
    
    public PlayerMetadataStore getPlayerMetadata() {
        return this.playerMetadata;
    }
    
    public WorldMetadataStore getWorldMetadata() {
        return this.worldMetadata;
    }
    
    public void detectListNameConflict(final EntityPlayer entityPlayer) {
        for (int i = 0; i < this.getHandle().players.size(); ++i) {
            final EntityPlayer testEntityPlayer = this.getHandle().players.get(i);
            if (testEntityPlayer != entityPlayer && testEntityPlayer.listName.equals(entityPlayer.listName)) {
                final String oldName = entityPlayer.listName;
                final int spaceLeft = 16 - oldName.length();
                if (spaceLeft <= 1) {
                    entityPlayer.listName = (Object)oldName.subSequence(0, oldName.length() - 2 - spaceLeft) + String.valueOf(System.currentTimeMillis() % 99L);
                }
                else {
                    entityPlayer.listName = oldName + String.valueOf(System.currentTimeMillis() % 99L);
                }
                return;
            }
        }
    }
    
    public File getWorldContainer() {
        if (this.getServer().universe != null) {
            return this.getServer().universe;
        }
        if (this.container == null) {
            this.container = new File(this.configuration.getString("settings.world-container", "."));
        }
        return this.container;
    }
    
    public OfflinePlayer[] getOfflinePlayers() {
        final WorldNBTStorage storage = (WorldNBTStorage)this.console.worlds.get(0).getDataManager();
        final String[] files = storage.getPlayerDir().list(new DatFileFilter());
        final Set<OfflinePlayer> players = new HashSet<OfflinePlayer>();
        for (final String file : files) {
            players.add(this.getOfflinePlayer(file.substring(0, file.length() - 4), false));
        }
        players.addAll(Arrays.asList(this.getOnlinePlayers()));
        return players.toArray(new OfflinePlayer[players.size()]);
    }
    
    public Messenger getMessenger() {
        return this.messenger;
    }
    
    public void sendPluginMessage(final Plugin source, final String channel, final byte[] message) {
        StandardMessenger.validatePluginMessage(this.getMessenger(), source, channel, message);
        for (final Player player : this.getOnlinePlayers()) {
            player.sendPluginMessage(source, channel, message);
        }
    }
    
    public Set<String> getListeningPluginChannels() {
        final Set<String> result = new HashSet<String>();
        for (final Player player : this.getOnlinePlayers()) {
            result.addAll(player.getListeningPluginChannels());
        }
        return result;
    }
    
    public void onPlayerJoin(final Player player) {
        if (this.updater.isEnabled() && this.updater.getCurrent() != null && player.hasPermission("bukkit.broadcast.admin")) {
            if (this.updater.getCurrent().isBroken() && this.updater.getOnBroken().contains("warn-ops")) {
                player.sendMessage(ChatColor.DARK_RED + "The version of CraftBukkit that this server is running is known to be broken. Please consider updating to the latest version at dl.bukkit.org.");
            }
            else if (this.updater.isUpdateAvailable() && this.updater.getOnUpdate().contains("warn-ops")) {
                player.sendMessage(ChatColor.DARK_PURPLE + "The version of CraftBukkit that this server is running is out of date. Please consider updating to the latest version at dl.bukkit.org.");
            }
        }
    }
    
    public Inventory createInventory(final InventoryHolder owner, final InventoryType type) {
        return new CraftInventoryCustom(owner, type);
    }
    
    public Inventory createInventory(final InventoryHolder owner, final int size) throws IllegalArgumentException {
        Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
        return new CraftInventoryCustom(owner, size);
    }
    
    public Inventory createInventory(final InventoryHolder owner, final int size, final String title) throws IllegalArgumentException {
        Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
        return new CraftInventoryCustom(owner, size, title);
    }
    
    public HelpMap getHelpMap() {
        return this.helpMap;
    }
    
    public SimpleCommandMap getCommandMap() {
        return this.commandMap;
    }
    
    public int getMonsterSpawnLimit() {
        return this.monsterSpawn;
    }
    
    public int getAnimalSpawnLimit() {
        return this.animalSpawn;
    }
    
    public int getWaterAnimalSpawnLimit() {
        return this.waterAnimalSpawn;
    }
    
    public int getAmbientSpawnLimit() {
        return this.ambientSpawn;
    }
    
    public boolean isPrimaryThread() {
        return Thread.currentThread().equals(this.console.primaryThread);
    }
    
    public String getMotd() {
        return this.console.getMotd();
    }
    
    public Warning.WarningState getWarningState() {
        return this.warningState;
    }
    
    public List<String> tabComplete(final ICommandListener sender, final String message) {
        if (!(sender instanceof EntityPlayer)) {
            return (List<String>)ImmutableList.of();
        }
        final Player player = ((EntityPlayer)sender).getBukkitEntity();
        if (message.startsWith("/")) {
            return this.tabCompleteCommand(player, message);
        }
        return this.tabCompleteChat(player, message);
    }
    
    public List<String> tabCompleteCommand(final Player player, final String message) {
        List<String> completions = null;
        try {
            completions = (this.commandComplete ? this.getCommandMap().tabComplete(player, message.substring(1)) : null);
        }
        catch (CommandException ex) {
            player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to tab-complete this command");
            this.getLogger().log(Level.SEVERE, "Exception when " + player.getName() + " attempted to tab complete " + message, ex);
        }
        return (List<String>)((completions == null) ? ImmutableList.of() : completions);
    }
    
    public List<String> tabCompleteChat(final Player player, final String message) {
        final Player[] players = this.getOnlinePlayers();
        final List<String> completions = new ArrayList<String>();
        final PlayerChatTabCompleteEvent event = new PlayerChatTabCompleteEvent(player, message, completions);
        final String token = event.getLastToken();
        for (final Player p : players) {
            if (player.canSee(p) && StringUtil.startsWithIgnoreCase(p.getName(), token)) {
                completions.add(p.getName());
            }
        }
        this.pluginManager.callEvent(event);
        final Iterator<?> it = completions.iterator();
        while (it.hasNext()) {
            final Object current = it.next();
            if (!(current instanceof String)) {
                it.remove();
            }
        }
        Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
        return completions;
    }
    
    public CraftItemFactory getItemFactory() {
        return CraftItemFactory.instance();
    }
    
    public CraftScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }
    
    public Collection<InetSocketAddress> getSecondaryHosts() {
        final Collection<InetSocketAddress> ret = new HashSet<InetSocketAddress>();
        final List<?> listeners = this.configuration.getList("listeners");
        if (listeners != null) {
            for (final Object o : listeners) {
                final Map<String, Object> sect = (Map<String, Object>)o;
                ret.add(new InetSocketAddress(sect.get("address"), sect.get("port")));
            }
        }
        return ret;
    }
    
    static {
        ConfigurationSerialization.registerClass(CraftOfflinePlayer.class);
        CraftItemFactory.instance();
    }
    
    private final class BooleanWrapper
    {
        private boolean value;
        
        private BooleanWrapper() {
            this.value = true;
        }
    }
}
