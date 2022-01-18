// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.java;

import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import org.bukkit.plugin.Plugin;
import java.net.URLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.io.InputStream;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.configuration.file.FileConfiguration;
import com.avaje.ebean.EbeanServer;
import org.bukkit.plugin.PluginDescriptionFile;
import java.io.File;
import org.bukkit.Server;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginBase;

public abstract class JavaPlugin extends PluginBase
{
    private boolean isEnabled;
    private boolean initialized;
    private PluginLoader loader;
    private Server server;
    private File file;
    private PluginDescriptionFile description;
    private File dataFolder;
    private ClassLoader classLoader;
    private boolean naggable;
    private EbeanServer ebean;
    private FileConfiguration newConfig;
    private File configFile;
    private PluginLogger logger;
    
    public JavaPlugin() {
        this.isEnabled = false;
        this.initialized = false;
        this.loader = null;
        this.server = null;
        this.file = null;
        this.description = null;
        this.dataFolder = null;
        this.classLoader = null;
        this.naggable = true;
        this.ebean = null;
        this.newConfig = null;
        this.configFile = null;
        this.logger = null;
    }
    
    public final File getDataFolder() {
        return this.dataFolder;
    }
    
    public final PluginLoader getPluginLoader() {
        return this.loader;
    }
    
    public final Server getServer() {
        return this.server;
    }
    
    public final boolean isEnabled() {
        return this.isEnabled;
    }
    
    protected File getFile() {
        return this.file;
    }
    
    public final PluginDescriptionFile getDescription() {
        return this.description;
    }
    
    public FileConfiguration getConfig() {
        if (this.newConfig == null) {
            this.reloadConfig();
        }
        return this.newConfig;
    }
    
    public void reloadConfig() {
        this.newConfig = YamlConfiguration.loadConfiguration(this.configFile);
        final InputStream defConfigStream = this.getResource("config.yml");
        if (defConfigStream != null) {
            final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            this.newConfig.setDefaults(defConfig);
        }
    }
    
    public void saveConfig() {
        try {
            this.getConfig().save(this.configFile);
        }
        catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
        }
    }
    
    public void saveDefaultConfig() {
        if (!this.configFile.exists()) {
            this.saveResource("config.yml", false);
        }
    }
    
    public void saveResource(String resourcePath, final boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
        resourcePath = resourcePath.replace('\\', '/');
        final InputStream in = this.getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);
        }
        final File outFile = new File(this.dataFolder, resourcePath);
        final int lastIndex = resourcePath.lastIndexOf(47);
        final File outDir = new File(this.dataFolder, resourcePath.substring(0, (lastIndex >= 0) ? lastIndex : 0));
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        try {
            if (!outFile.exists() || replace) {
                final OutputStream out = new FileOutputStream(outFile);
                final byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
            else {
                this.logger.log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        }
        catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }
    
    public InputStream getResource(final String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }
        try {
            final URL url = this.getClassLoader().getResource(filename);
            if (url == null) {
                return null;
            }
            final URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    protected final ClassLoader getClassLoader() {
        return this.classLoader;
    }
    
    protected final void setEnabled(final boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            if (this.isEnabled) {
                this.onEnable();
            }
            else {
                this.onDisable();
            }
        }
    }
    
    protected final void initialize(final PluginLoader loader, final Server server, final PluginDescriptionFile description, final File dataFolder, final File file, final ClassLoader classLoader) {
        if (!this.initialized) {
            this.initialized = true;
            this.loader = loader;
            this.server = server;
            this.file = file;
            this.description = description;
            this.dataFolder = dataFolder;
            this.classLoader = classLoader;
            this.configFile = new File(dataFolder, "config.yml");
            this.logger = new PluginLogger(this);
            if (description.isDatabaseEnabled()) {
                final ServerConfig db = new ServerConfig();
                db.setDefaultServer(false);
                db.setRegister(false);
                db.setClasses(this.getDatabaseClasses());
                db.setName(description.getName());
                server.configureDbConfig(db);
                final DataSourceConfig ds = db.getDataSourceConfig();
                ds.setUrl(this.replaceDatabaseString(ds.getUrl()));
                dataFolder.mkdirs();
                final ClassLoader previous = Thread.currentThread().getContextClassLoader();
                Thread.currentThread().setContextClassLoader(classLoader);
                this.ebean = EbeanServerFactory.create(db);
                Thread.currentThread().setContextClassLoader(previous);
            }
        }
    }
    
    public List<Class<?>> getDatabaseClasses() {
        return new ArrayList<Class<?>>();
    }
    
    private String replaceDatabaseString(String input) {
        input = input.replaceAll("\\{DIR\\}", this.dataFolder.getPath().replaceAll("\\\\", "/") + "/");
        input = input.replaceAll("\\{NAME\\}", this.description.getName().replaceAll("[^\\w_-]", ""));
        return input;
    }
    
    public final boolean isInitialized() {
        return this.initialized;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return false;
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return null;
    }
    
    public PluginCommand getCommand(final String name) {
        final String alias = name.toLowerCase();
        PluginCommand command = this.getServer().getPluginCommand(alias);
        if (command != null && command.getPlugin() != this) {
            command = this.getServer().getPluginCommand(this.description.getName().toLowerCase() + ":" + alias);
        }
        if (command != null && command.getPlugin() == this) {
            return command;
        }
        return null;
    }
    
    public void onLoad() {
    }
    
    public void onDisable() {
    }
    
    public void onEnable() {
    }
    
    public ChunkGenerator getDefaultWorldGenerator(final String worldName, final String id) {
        return null;
    }
    
    public final boolean isNaggable() {
        return this.naggable;
    }
    
    public final void setNaggable(final boolean canNag) {
        this.naggable = canNag;
    }
    
    public EbeanServer getDatabase() {
        return this.ebean;
    }
    
    protected void installDDL() {
        final SpiEbeanServer serv = (SpiEbeanServer)this.getDatabase();
        final DdlGenerator gen = serv.getDdlGenerator();
        gen.runScript(false, gen.generateCreateDdl());
    }
    
    protected void removeDDL() {
        final SpiEbeanServer serv = (SpiEbeanServer)this.getDatabase();
        final DdlGenerator gen = serv.getDdlGenerator();
        gen.runScript(true, gen.generateDropDdl());
    }
    
    public final Logger getLogger() {
        return this.logger;
    }
    
    public String toString() {
        return this.description.getFullName();
    }
}
