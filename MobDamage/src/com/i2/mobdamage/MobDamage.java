/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  ru.tehkode.permissions.bukkit.PermissionsEx
 */
package com.i2.mobdamage;

import com.i2.mobdamage.MobDamageListener;
import java.io.PrintStream;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MobDamage
extends JavaPlugin {
    MobDamageListener mobListener;
    public final Logger logger;
    public static MobDamage plugin;

    public MobDamage() {
        this.mobListener = new MobDamageListener(this);
        this.logger = Logger.getLogger("Minecraft");
    }

    public void onDisable() {
        PluginDescriptionFile pdfile = this.getDescription();
        this.logger.info("MobDamage has been disabled");
    }

    public void onEnable() {
        plugin = this;
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener)this.mobListener, (Plugin)this);
        this.loadConfig();
        this.setupPermissions();
        this.logger.info("MobDamage has been enabled");
    }

    private void setupPermissions() {
        Plugin PermissionsBukkit = this.getServer().getPluginManager().getPlugin("PermissionsBukkit");
        PluginDescriptionFile pdfFile = this.getDescription();
        if (PermissionsBukkit != null) {
            this.logger.info("[MobDamage] Found PermissionsBukkit Version " + PermissionsBukkit.getDescription().getVersion());
            this.logger.info("[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " is enabled!");
            return;
        }
        this.getServer().getPluginManager().disablePlugin((Plugin)this);
    }

    private void loadConfig() {
        getConfig().addDefault("MobHealth.Pig", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Cow", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Spider", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.CaveSpider", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Zombie", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Skeleton", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Creeper", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Ocelot", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Bat", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Chicken", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Mooshroom", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Sheep", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Squid", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Villager", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Enderman", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Wolf", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.ZombiePigman", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Blaze", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Ghast", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.MagmaCube", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Silverfish", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Slime", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.Witch", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.WitherSkeleton", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.ZombieVillager", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.IronGolem", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.SnowGolem", Integer.valueOf(-1));
        getConfig().addDefault("MobHealth.EnderDragon", Integer.valueOf(-1));

        getConfig().addDefault("MobDamage.Pig", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Cow", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Spider", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.CaveSpider", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Zombie", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Skeleton", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Creeper", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Ocelot", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Bat", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Chicken", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Mooshroom", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Sheep", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Squid", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Villager", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Enderman", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Wolf", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.ZombiePigman", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Blaze", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Ghast", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.MagmaCube", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Silverfish", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Slime", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.Witch", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.WitherSkeleton", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.ZombieVillager", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.IronGolem", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.SnowGolem", Integer.valueOf(-1));
        getConfig().addDefault("MobDamage.EnderDragon", Integer.valueOf(-1));
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = null;
        String pname = "(Console)";
        if (sender instanceof Player) {
            player = (Player)sender;
            pname = player.getName();
        }
        if ((player == null || player.hasPermission("mobdamage.reload")) && commandLabel.equalsIgnoreCase("mobdamage")) {
            if (args.length == 0) {
                this.respond(player, (Object)ChatColor.RED + "[MobDamage] Version 1.1 " + (Object)ChatColor.BLUE + "by island219 & Blabba_Labba");
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                this.reloadConfig();
                this.respond(player, (Object)ChatColor.RED + "[MobDamage] Config reloaded.");
                this.logger.info("[MobDamage] " + pname + " reloaded the config.");
            }
            return true;
        }
        player.sendMessage((Object)ChatColor.RED + "[MobDamage] Permission denied.");
        return false;
    }

    private void respond(Player player, String message) {
        if (player == null) {
            System.out.println(message);
        } else {
            player.sendMessage(message);
        }
    }
}

