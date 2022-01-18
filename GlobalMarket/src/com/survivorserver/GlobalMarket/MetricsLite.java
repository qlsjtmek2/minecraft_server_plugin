// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import org.bukkit.plugin.PluginDescriptionFile;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Proxy;
import java.net.URL;
import org.bukkit.configuration.InvalidConfigurationException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.scheduler.BukkitTask;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MetricsLite
{
    private static final int REVISION = 6;
    private static final String BASE_URL = "http://mcstats.org";
    private static final String REPORT_URL = "/report/%s";
    private static final int PING_INTERVAL = 10;
    private final Plugin plugin;
    private final YamlConfiguration configuration;
    private final File configurationFile;
    private final String guid;
    private final boolean debug;
    private final Object optOutLock;
    private volatile BukkitTask task;
    
    public MetricsLite(final Plugin plugin) throws IOException {
        this.optOutLock = new Object();
        this.task = null;
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        this.plugin = plugin;
        this.configurationFile = this.getConfigFile();
        (this.configuration = YamlConfiguration.loadConfiguration(this.configurationFile)).addDefault("opt-out", (Object)false);
        this.configuration.addDefault("guid", (Object)UUID.randomUUID().toString());
        this.configuration.addDefault("debug", (Object)false);
        if (this.configuration.get("guid", (Object)null) == null) {
            this.configuration.options().header("http://mcstats.org").copyDefaults(true);
            this.configuration.save(this.configurationFile);
        }
        this.guid = this.configuration.getString("guid");
        this.debug = this.configuration.getBoolean("debug", false);
    }
    
    public boolean start() {
        synchronized (this.optOutLock) {
            if (this.isOptOut()) {
                // monitorexit(this.optOutLock)
                return false;
            }
            if (this.task != null) {
                // monitorexit(this.optOutLock)
                return true;
            }
            this.task = this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin, (Runnable)new Runnable() {
                private boolean firstPost = true;
                
                @Override
                public void run() {
                    try {
                        synchronized (MetricsLite.this.optOutLock) {
                            if (MetricsLite.this.isOptOut() && MetricsLite.this.task != null) {
                                MetricsLite.this.task.cancel();
                                MetricsLite.access$2(MetricsLite.this, null);
                            }
                        }
                        // monitorexit(MetricsLite.access$0(this.this$0))
                        MetricsLite.this.postPlugin(!this.firstPost);
                        this.firstPost = false;
                    }
                    catch (IOException e) {
                        if (MetricsLite.this.debug) {
                            Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage());
                        }
                    }
                }
            }, 0L, 12000L);
            // monitorexit(this.optOutLock)
            return true;
        }
    }
    
    public boolean isOptOut() {
        synchronized (this.optOutLock) {
            try {
                this.configuration.load(this.getConfigFile());
            }
            catch (IOException ex) {
                if (this.debug) {
                    Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
                }
                // monitorexit(this.optOutLock)
                return true;
            }
            catch (InvalidConfigurationException ex2) {
                if (this.debug) {
                    Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex2.getMessage());
                }
                // monitorexit(this.optOutLock)
                return true;
            }
            // monitorexit(this.optOutLock)
            return this.configuration.getBoolean("opt-out", false);
        }
    }
    
    public void enable() throws IOException {
        synchronized (this.optOutLock) {
            if (this.isOptOut()) {
                this.configuration.set("opt-out", (Object)false);
                this.configuration.save(this.configurationFile);
            }
            if (this.task == null) {
                this.start();
            }
        }
        // monitorexit(this.optOutLock)
    }
    
    public void disable() throws IOException {
        synchronized (this.optOutLock) {
            if (!this.isOptOut()) {
                this.configuration.set("opt-out", (Object)true);
                this.configuration.save(this.configurationFile);
            }
            if (this.task != null) {
                this.task.cancel();
                this.task = null;
            }
        }
        // monitorexit(this.optOutLock)
    }
    
    public File getConfigFile() {
        final File pluginsFolder = this.plugin.getDataFolder().getParentFile();
        return new File(new File(pluginsFolder, "PluginMetrics"), "config.yml");
    }
    
    private void postPlugin(final boolean isPing) throws IOException {
        final PluginDescriptionFile description = this.plugin.getDescription();
        final String pluginName = description.getName();
        final boolean onlineMode = Bukkit.getServer().getOnlineMode();
        final String pluginVersion = description.getVersion();
        final String serverVersion = Bukkit.getVersion();
        final int playersOnline = Bukkit.getServer().getOnlinePlayers().length;
        final StringBuilder data = new StringBuilder();
        data.append(encode("guid")).append('=').append(encode(this.guid));
        encodeDataPair(data, "version", pluginVersion);
        encodeDataPair(data, "server", serverVersion);
        encodeDataPair(data, "players", Integer.toString(playersOnline));
        encodeDataPair(data, "revision", String.valueOf(6));
        final String osname = System.getProperty("os.name");
        String osarch = System.getProperty("os.arch");
        final String osversion = System.getProperty("os.version");
        final String java_version = System.getProperty("java.version");
        final int coreCount = Runtime.getRuntime().availableProcessors();
        if (osarch.equals("amd64")) {
            osarch = "x86_64";
        }
        encodeDataPair(data, "osname", osname);
        encodeDataPair(data, "osarch", osarch);
        encodeDataPair(data, "osversion", osversion);
        encodeDataPair(data, "cores", Integer.toString(coreCount));
        encodeDataPair(data, "online-mode", Boolean.toString(onlineMode));
        encodeDataPair(data, "java_version", java_version);
        if (isPing) {
            encodeDataPair(data, "ping", "true");
        }
        final URL url = new URL("http://mcstats.org" + String.format("/report/%s", encode(pluginName)));
        URLConnection connection;
        if (this.isMineshafterPresent()) {
            connection = url.openConnection(Proxy.NO_PROXY);
        }
        else {
            connection = url.openConnection();
        }
        connection.setDoOutput(true);
        final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(data.toString());
        writer.flush();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final String response = reader.readLine();
        writer.close();
        reader.close();
        if (response == null || response.startsWith("ERR")) {
            throw new IOException(response);
        }
    }
    
    private boolean isMineshafterPresent() {
        try {
            Class.forName("mineshafter.MineServer");
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    private static void encodeDataPair(final StringBuilder buffer, final String key, final String value) throws UnsupportedEncodingException {
        buffer.append('&').append(encode(key)).append('=').append(encode(value));
    }
    
    private static String encode(final String text) throws UnsupportedEncodingException {
        return URLEncoder.encode(text, "UTF-8");
    }
    
    static /* synthetic */ void access$2(final MetricsLite metricsLite, final BukkitTask task) {
        metricsLite.task = task;
    }
}
