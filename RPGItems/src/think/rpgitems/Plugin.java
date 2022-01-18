// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import think.rpgitems.config.ConfigUpdater;
import org.bukkit.configuration.InvalidConfigurationException;
import java.io.FileInputStream;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Iterator;
import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.power.PowerTicker;
import think.rpgitems.commands.CommandHandler;
import think.rpgitems.commands.Commands;
import think.rpgitems.lib.org.mcstats.Metrics;
import think.rpgitems.item.ItemManager;
import org.bukkit.event.Listener;
import think.rpgitems.support.WorldGuard;
import think.rpgitems.data.Locale;
import think.rpgitems.power.PowerPotionTick;
import think.rpgitems.power.PowerSkyHook;
import think.rpgitems.power.PowerRumble;
import think.rpgitems.power.PowerUnbreaking;
import think.rpgitems.power.PowerUnbreakable;
import think.rpgitems.power.PowerConsume;
import think.rpgitems.power.PowerPotionSelf;
import think.rpgitems.power.PowerRush;
import think.rpgitems.power.PowerKnockup;
import think.rpgitems.power.PowerFireball;
import think.rpgitems.power.PowerTeleport;
import think.rpgitems.power.PowerPotionHit;
import think.rpgitems.power.PowerCommand;
import think.rpgitems.power.PowerIce;
import think.rpgitems.power.PowerLightning;
import think.rpgitems.power.PowerFlame;
import think.rpgitems.power.PowerRainbow;
import think.rpgitems.power.PowerTNTCannon;
import think.rpgitems.power.PowerArrow;
import think.rpgitems.power.Power;
import think.rpgitems.data.Font;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin
{
    public static Logger logger;
    public static Plugin plugin;
    private FileConfiguration config;
    
    static {
        Plugin.logger = Logger.getLogger("RPGItems");
    }
    
    public void onLoad() {
        (Plugin.plugin = this).reloadConfig();
        Font.load();
        Power.powers.put("arrow", PowerArrow.class);
        Power.powers.put("tntcannon", PowerTNTCannon.class);
        Power.powers.put("rainbow", PowerRainbow.class);
        Power.powers.put("flame", PowerFlame.class);
        Power.powers.put("lightning", PowerLightning.class);
        Power.powers.put("ice", PowerIce.class);
        Power.powers.put("command", PowerCommand.class);
        Power.powers.put("potionhit", PowerPotionHit.class);
        Power.powers.put("teleport", PowerTeleport.class);
        Power.powers.put("fireball", PowerFireball.class);
        Power.powers.put("knockup", PowerKnockup.class);
        Power.powers.put("rush", PowerRush.class);
        Power.powers.put("potionself", PowerPotionSelf.class);
        Power.powers.put("consume", PowerConsume.class);
        Power.powers.put("unbreakable", PowerUnbreakable.class);
        Power.powers.put("unbreaking", PowerUnbreaking.class);
        Power.powers.put("rumble", PowerRumble.class);
        Power.powers.put("skyhook", PowerSkyHook.class);
        Power.powers.put("potiontick", PowerPotionTick.class);
    }
    
    public void onEnable() {
        Locale.init(this);
        this.updateConfig();
        WorldGuard.init(this);
        final ConfigurationSection conf = (ConfigurationSection)this.getConfig();
        if (conf.getBoolean("autoupdate", true)) {
            new Updater((org.bukkit.plugin.Plugin)this, "rpg-items", this.getFile(), Updater.UpdateType.DEFAULT, false);
        }
        if (conf.getBoolean("localeInv", false)) {
            Events.useLocaleInv = true;
        }
        this.getServer().getPluginManager().registerEvents((Listener)new Events(), (org.bukkit.plugin.Plugin)this);
        ItemManager.load(this);
        try {
            final Metrics metrics = new Metrics((org.bukkit.plugin.Plugin)this);
            metrics.addCustomData(new Metrics.Plotter("Total Items") {
                @Override
                public int getValue() {
                    return ItemManager.itemByName.size();
                }
            });
            final Metrics.Graph graph = metrics.createGraph("Power usage");
            for (final String powerName : Power.powers.keySet()) {
                graph.addPlotter(new Metrics.Plotter(powerName) {
                    @Override
                    public int getValue() {
                        return Power.powerUsage.get(this.getColumnName());
                    }
                });
            }
            metrics.addGraph(graph);
            metrics.start();
        }
        catch (Exception ex) {}
        Commands.register(new Handler());
        Commands.register(new PowerHandler());
        new PowerTicker().runTaskTimer((org.bukkit.plugin.Plugin)this, 0L, 1L);
    }
    
    public void saveConfig() {
        final FileConfiguration config = this.getConfig();
        FileOutputStream out = null;
        try {
            final File f = new File(this.getDataFolder(), "config.yml");
            if (!f.exists()) {
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            out.write(config.saveToString().getBytes("UTF-8"));
        }
        catch (FileNotFoundException ex) {}
        catch (UnsupportedEncodingException ex2) {}
        catch (IOException ex3) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (out != null) {
                out.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void reloadConfig() {
        FileInputStream in = null;
        this.config = (FileConfiguration)new YamlConfiguration();
        try {
            final File f = new File(this.getDataFolder(), "config.yml");
            in = new FileInputStream(f);
            final byte[] data = new byte[(int)f.length()];
            in.read(data);
            final String str = new String(data, "UTF-8");
            this.config.loadFromString(str);
        }
        catch (FileNotFoundException ex) {}
        catch (IOException ex2) {}
        catch (InvalidConfigurationException ex3) {}
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (in != null) {
                in.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public FileConfiguration getConfig() {
        return this.config;
    }
    
    public void updateConfig() {
        ConfigUpdater.updateConfig((ConfigurationSection)this.getConfig());
        this.saveConfig();
    }
    
    public void onDisable() {
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final StringBuilder out = new StringBuilder();
        out.append(label).append(' ');
        for (final String arg : args) {
            out.append(arg).append(' ');
        }
        Commands.exec(sender, out.toString());
        return true;
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        final StringBuilder out = new StringBuilder();
        out.append(alias).append(' ');
        for (final String arg : args) {
            out.append(arg).append(' ');
        }
        return Commands.complete(sender, out.toString());
    }
}
