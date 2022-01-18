// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.messaging.Messenger;
import java.io.File;
import org.bukkit.command.ConsoleCommandSender;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import com.avaje.ebean.config.ServerConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import java.util.logging.Logger;
import org.bukkit.map.MapView;
import java.util.UUID;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.plugin.PluginManager;
import java.util.List;
import org.bukkit.entity.Player;

public final class Bukkit
{
    private static Server server;
    
    public static Server getServer() {
        return Bukkit.server;
    }
    
    public static void setServer(final Server server) {
        if (Bukkit.server != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton Server");
        }
        Bukkit.server = server;
        server.getLogger().info("This server is running " + getName() + " version " + getVersion() + " (Implementing API version " + getBukkitVersion() + ")");
    }
    
    public static String getName() {
        return Bukkit.server.getName();
    }
    
    public static String getVersion() {
        return Bukkit.server.getVersion();
    }
    
    public static String getBukkitVersion() {
        return Bukkit.server.getBukkitVersion();
    }
    
    public static Player[] getOnlinePlayers() {
        return Bukkit.server.getOnlinePlayers();
    }
    
    public static int getMaxPlayers() {
        return Bukkit.server.getMaxPlayers();
    }
    
    public static int getPort() {
        return Bukkit.server.getPort();
    }
    
    public static int getViewDistance() {
        return Bukkit.server.getViewDistance();
    }
    
    public static String getIp() {
        return Bukkit.server.getIp();
    }
    
    public static String getServerName() {
        return Bukkit.server.getServerName();
    }
    
    public static String getServerId() {
        return Bukkit.server.getServerId();
    }
    
    public static String getWorldType() {
        return Bukkit.server.getWorldType();
    }
    
    public static boolean getGenerateStructures() {
        return Bukkit.server.getGenerateStructures();
    }
    
    public static boolean getAllowNether() {
        return Bukkit.server.getAllowNether();
    }
    
    public static boolean hasWhitelist() {
        return Bukkit.server.hasWhitelist();
    }
    
    public static int broadcastMessage(final String message) {
        return Bukkit.server.broadcastMessage(message);
    }
    
    public static String getUpdateFolder() {
        return Bukkit.server.getUpdateFolder();
    }
    
    public static Player getPlayer(final String name) {
        return Bukkit.server.getPlayer(name);
    }
    
    public static List<Player> matchPlayer(final String name) {
        return Bukkit.server.matchPlayer(name);
    }
    
    public static PluginManager getPluginManager() {
        return Bukkit.server.getPluginManager();
    }
    
    public static BukkitScheduler getScheduler() {
        return Bukkit.server.getScheduler();
    }
    
    public static ServicesManager getServicesManager() {
        return Bukkit.server.getServicesManager();
    }
    
    public static List<World> getWorlds() {
        return Bukkit.server.getWorlds();
    }
    
    public static World createWorld(final WorldCreator options) {
        return Bukkit.server.createWorld(options);
    }
    
    public static boolean unloadWorld(final String name, final boolean save) {
        return Bukkit.server.unloadWorld(name, save);
    }
    
    public static boolean unloadWorld(final World world, final boolean save) {
        return Bukkit.server.unloadWorld(world, save);
    }
    
    public static World getWorld(final String name) {
        return Bukkit.server.getWorld(name);
    }
    
    public static World getWorld(final UUID uid) {
        return Bukkit.server.getWorld(uid);
    }
    
    public static MapView getMap(final short id) {
        return Bukkit.server.getMap(id);
    }
    
    public static MapView createMap(final World world) {
        return Bukkit.server.createMap(world);
    }
    
    public static void reload() {
        Bukkit.server.reload();
    }
    
    public static Logger getLogger() {
        return Bukkit.server.getLogger();
    }
    
    public static PluginCommand getPluginCommand(final String name) {
        return Bukkit.server.getPluginCommand(name);
    }
    
    public static void savePlayers() {
        Bukkit.server.savePlayers();
    }
    
    public static boolean dispatchCommand(final CommandSender sender, final String commandLine) {
        return Bukkit.server.dispatchCommand(sender, commandLine);
    }
    
    public static void configureDbConfig(final ServerConfig config) {
        Bukkit.server.configureDbConfig(config);
    }
    
    public static boolean addRecipe(final Recipe recipe) {
        return Bukkit.server.addRecipe(recipe);
    }
    
    public static List<Recipe> getRecipesFor(final ItemStack result) {
        return Bukkit.server.getRecipesFor(result);
    }
    
    public static Iterator<Recipe> recipeIterator() {
        return Bukkit.server.recipeIterator();
    }
    
    public static void clearRecipes() {
        Bukkit.server.clearRecipes();
    }
    
    public static void resetRecipes() {
        Bukkit.server.resetRecipes();
    }
    
    public static Map<String, String[]> getCommandAliases() {
        return Bukkit.server.getCommandAliases();
    }
    
    public static int getSpawnRadius() {
        return Bukkit.server.getSpawnRadius();
    }
    
    public static void setSpawnRadius(final int value) {
        Bukkit.server.setSpawnRadius(value);
    }
    
    public static boolean getOnlineMode() {
        return Bukkit.server.getOnlineMode();
    }
    
    public static boolean getAllowFlight() {
        return Bukkit.server.getAllowFlight();
    }
    
    public static boolean isHardcore() {
        return Bukkit.server.isHardcore();
    }
    
    public static void shutdown() {
        Bukkit.server.shutdown();
    }
    
    public static int broadcast(final String message, final String permission) {
        return Bukkit.server.broadcast(message, permission);
    }
    
    public static OfflinePlayer getOfflinePlayer(final String name) {
        return Bukkit.server.getOfflinePlayer(name);
    }
    
    public static Player getPlayerExact(final String name) {
        return Bukkit.server.getPlayerExact(name);
    }
    
    public static Set<String> getIPBans() {
        return Bukkit.server.getIPBans();
    }
    
    public static void banIP(final String address) {
        Bukkit.server.banIP(address);
    }
    
    public static void unbanIP(final String address) {
        Bukkit.server.unbanIP(address);
    }
    
    public static Set<OfflinePlayer> getBannedPlayers() {
        return Bukkit.server.getBannedPlayers();
    }
    
    public static void setWhitelist(final boolean value) {
        Bukkit.server.setWhitelist(value);
    }
    
    public static Set<OfflinePlayer> getWhitelistedPlayers() {
        return Bukkit.server.getWhitelistedPlayers();
    }
    
    public static void reloadWhitelist() {
        Bukkit.server.reloadWhitelist();
    }
    
    public static ConsoleCommandSender getConsoleSender() {
        return Bukkit.server.getConsoleSender();
    }
    
    public static Set<OfflinePlayer> getOperators() {
        return Bukkit.server.getOperators();
    }
    
    public static File getWorldContainer() {
        return Bukkit.server.getWorldContainer();
    }
    
    public static Messenger getMessenger() {
        return Bukkit.server.getMessenger();
    }
    
    public static boolean getAllowEnd() {
        return Bukkit.server.getAllowEnd();
    }
    
    public static File getUpdateFolderFile() {
        return Bukkit.server.getUpdateFolderFile();
    }
    
    public static long getConnectionThrottle() {
        return Bukkit.server.getConnectionThrottle();
    }
    
    public static int getTicksPerAnimalSpawns() {
        return Bukkit.server.getTicksPerAnimalSpawns();
    }
    
    public static int getTicksPerMonsterSpawns() {
        return Bukkit.server.getTicksPerMonsterSpawns();
    }
    
    public static boolean useExactLoginLocation() {
        return Bukkit.server.useExactLoginLocation();
    }
    
    public static GameMode getDefaultGameMode() {
        return Bukkit.server.getDefaultGameMode();
    }
    
    public static void setDefaultGameMode(final GameMode mode) {
        Bukkit.server.setDefaultGameMode(mode);
    }
    
    public static OfflinePlayer[] getOfflinePlayers() {
        return Bukkit.server.getOfflinePlayers();
    }
    
    public static Inventory createInventory(final InventoryHolder owner, final InventoryType type) {
        return Bukkit.server.createInventory(owner, type);
    }
    
    public static Inventory createInventory(final InventoryHolder owner, final int size) {
        return Bukkit.server.createInventory(owner, size);
    }
    
    public static Inventory createInventory(final InventoryHolder owner, final int size, final String title) {
        return Bukkit.server.createInventory(owner, size, title);
    }
    
    public static HelpMap getHelpMap() {
        return Bukkit.server.getHelpMap();
    }
    
    public static int getMonsterSpawnLimit() {
        return Bukkit.server.getMonsterSpawnLimit();
    }
    
    public static int getAnimalSpawnLimit() {
        return Bukkit.server.getAnimalSpawnLimit();
    }
    
    public static int getWaterAnimalSpawnLimit() {
        return Bukkit.server.getWaterAnimalSpawnLimit();
    }
    
    public static int getAmbientSpawnLimit() {
        return Bukkit.server.getAmbientSpawnLimit();
    }
    
    public static boolean isPrimaryThread() {
        return Bukkit.server.isPrimaryThread();
    }
    
    public static String getMotd() {
        return Bukkit.server.getMotd();
    }
    
    public static String getShutdownMessage() {
        return Bukkit.server.getShutdownMessage();
    }
    
    public static Warning.WarningState getWarningState() {
        return Bukkit.server.getWarningState();
    }
    
    public static ItemFactory getItemFactory() {
        return Bukkit.server.getItemFactory();
    }
    
    public static ScoreboardManager getScoreboardManager() {
        return Bukkit.server.getScoreboardManager();
    }
}
