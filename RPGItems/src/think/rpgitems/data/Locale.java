// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.data;

import org.bukkit.entity.Player;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Iterator;
import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.item.RPGItem;
import think.rpgitems.item.ItemManager;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;
import java.io.File;
import think.rpgitems.Plugin;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.scheduler.BukkitRunnable;

public class Locale extends BukkitRunnable
{
    private static Method getHandle;
    private static Method getLocale;
    private static Field language;
    private static boolean canLocale;
    private static boolean firstTime;
    private static HashMap<String, HashMap<String, String>> localeStrings;
    private Plugin plugin;
    private long lastUpdate;
    private File dataFolder;
    private String version;
    private static final String localeUpdateURL = "http://198.199.127.128/rpgitems/index.php?page=localeget&lastupdate=";
    private static final String localeDownloadURL = "http://198.199.127.128/rpgitems/locale/%s/%s.lang";
    
    static {
        Locale.canLocale = true;
        Locale.firstTime = true;
        Locale.localeStrings = new HashMap<String, HashMap<String, String>>();
    }
    
    private Locale(final Plugin plugin) {
        this.lastUpdate = 0L;
        this.plugin = plugin;
        this.lastUpdate = plugin.getConfig().getLong("lastLocaleUpdate", 0L);
        this.version = plugin.getDescription().getVersion();
        if (!plugin.getConfig().getString("pluginVersion", "0.0").equals(this.version)) {
            this.lastUpdate = 0L;
            plugin.getConfig().set("pluginVersion", (Object)this.version);
            plugin.saveConfig();
        }
        this.dataFolder = plugin.getDataFolder();
        reloadLocales(plugin);
        if (!plugin.getConfig().contains("localeDownload")) {
            plugin.getConfig().set("localeDownload", (Object)true);
            plugin.saveConfig();
        }
    }
    
    public static Set<String> getLocales() {
        return Locale.localeStrings.keySet();
    }
    
    public void run() {
        if (!this.plugin.getConfig().getBoolean("localeDownload", true)) {
            this.cancel();
        }
        try {
            final URL updateURL = new URL("http://198.199.127.128/rpgitems/index.php?page=localeget&lastupdate=" + this.lastUpdate);
            this.lastUpdate = System.currentTimeMillis();
            final URLConnection conn = updateURL.openConnection();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            final ArrayList<String> locales = new ArrayList<String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                locales.add(line);
            }
            reader.close();
            final File localesFolder = new File(this.dataFolder, "locale/");
            localesFolder.mkdirs();
            for (final String locale : locales) {
                final URL downloadURL = new URL(String.format("http://198.199.127.128/rpgitems/locale/%s/%s.lang", this.version, locale));
                final File outFile = new File(this.dataFolder, "locale/" + locale + ".lang");
                final InputStream in = downloadURL.openStream();
                final FileOutputStream out = new FileOutputStream(outFile);
                final byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buf)) != -1) {
                    out.write(buf, 0, bytesRead);
                }
                in.close();
                out.close();
            }
        }
        catch (Exception e) {
            return;
        }
        new BukkitRunnable() {
            public void run() {
                final ConfigurationSection config = (ConfigurationSection)Locale.this.plugin.getConfig();
                config.set("lastLocaleUpdate", (Object)Locale.this.lastUpdate);
                Locale.this.plugin.saveConfig();
                Locale.reloadLocales(Locale.this.plugin);
                for (final RPGItem item : ItemManager.itemById.valueCollection()) {
                    item.rebuild();
                }
            }
        }.runTask((org.bukkit.plugin.Plugin)this.plugin);
    }
    
    public static void reloadLocales(final Plugin plugin) {
        Locale.localeStrings.clear();
        Locale.localeStrings.put("en_GB", loadLocaleStream(plugin.getResource("locale/en_GB.lang")));
        final File localesFolder = new File(plugin.getDataFolder(), "locale/");
        localesFolder.mkdirs();
        File[] listFiles;
        for (int length = (listFiles = localesFolder.listFiles()).length, i = 0; i < length; ++i) {
            final File file = listFiles[i];
            if (!file.isDirectory() && file.getName().endsWith(".lang")) {
                FileInputStream in = null;
                try {
                    final String locale = file.getName().substring(0, file.getName().lastIndexOf(46));
                    HashMap<String, String> map = Locale.localeStrings.get(locale);
                    map = ((map == null) ? new HashMap<String, String>() : map);
                    in = new FileInputStream(file);
                    Locale.localeStrings.put(locale, loadLocaleStream(in, map));
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    try {
                        in.close();
                    }
                    catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    continue;
                }
                finally {
                    try {
                        in.close();
                    }
                    catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                try {
                    in.close();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
    
    private static HashMap<String, String> loadLocaleStream(final InputStream in, final HashMap<String, String> map) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                final String[] args = line.split("=");
                map.put(args[0].trim(), args[1].trim());
            }
            return map;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return null;
    }
    
    private static HashMap<String, String> loadLocaleStream(final InputStream in) {
        return loadLocaleStream(in, new HashMap<String, String>());
    }
    
    public static String getPlayerLocale(final Player player) {
        if (Locale.firstTime) {
            try {
                Locale.getHandle = player.getClass().getMethod("getHandle", (Class<?>[])null);
                Locale.getLocale = Locale.getHandle.getReturnType().getMethod("getLocale", (Class<?>[])null);
                (Locale.language = Locale.getLocale.getReturnType().getDeclaredField("e")).setAccessible(true);
                if (!Locale.language.getType().equals(String.class)) {
                    Locale.canLocale = false;
                }
            }
            catch (Exception e) {
                Plugin.plugin.getLogger().warning("Failed to get player locale");
                Locale.canLocale = false;
            }
            Locale.firstTime = false;
        }
        if (!Locale.canLocale) {
            return "en_GB";
        }
        try {
            final Object minePlayer = Locale.getHandle.invoke(player, (Object[])null);
            final Object locale = Locale.getLocale.invoke(minePlayer, (Object[])null);
            return (String)Locale.language.get(locale);
        }
        catch (Exception e) {
            Plugin.plugin.getLogger().warning("Failed to get player locale");
            Locale.canLocale = false;
            return "en_GB";
        }
    }
    
    public static void init(final Plugin plugin) {
        new Locale(plugin).runTaskTimerAsynchronously((org.bukkit.plugin.Plugin)plugin, 0L, 1728000L);
    }
    
    public static String get(final String key, final String locale) {
        if (!Locale.localeStrings.containsKey(locale)) {
            return get(key);
        }
        final HashMap<String, String> strings = Locale.localeStrings.get(locale);
        if (strings == null || !strings.containsKey(key)) {
            return get(key);
        }
        return strings.get(key);
    }
    
    private static String get(final String key) {
        final HashMap<String, String> strings = Locale.localeStrings.get("en_GB");
        if (!strings.containsKey(key)) {
            return "!" + key + "!";
        }
        return strings.get(key);
    }
}
