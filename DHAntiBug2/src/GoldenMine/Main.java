// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine;

import java.util.ArrayList;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.command.Command;
import GoldenMine.Instance.ConfigSetting;
import GoldenMine.VGH.VGH;
import GoldenMine.Cart.Cart;
import GoldenMine.ChestShop.ChestShop;
import GoldenMine.col.col;
import GoldenMine.ace.ace;
import GoldenMine.SkriptBug.SkriptGUI;
import GoldenMine.Block.BlockEvent;
import GoldenMine.Player.PlayerEvent;
import GoldenMine.Vehicle.VehicleEvent;
import org.bukkit.plugin.Plugin;
import GoldenMine.Inventory.InventoryEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import java.util.regex.Pattern;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    public static String version;
    public static Main instance;
    public static final Pattern[] SHOP_SIGN_PATTERN;
    
    public Main() {
        Main.instance = this;
    }
    
    public static void PrintBukkit(final String message) {
        Bukkit.getConsoleSender().sendMessage("§e[BlockMCBug] §f" + message);
    }
    
    public static void PrintMessage(final String message) {
        Bukkit.broadcastMessage("§e[BlockMCBug] §f" + message);
    }
    
    public static void PrintMessage(final Player sender, final String message) {
        sender.sendMessage("§e[BlockMCBug] §f" + message);
    }
    
    public static void PrintMessage(final Entity sender, final String message) {
        ((Player)sender).sendMessage("§e[BlockMCBug] §f" + message);
    }
    
    public static void PrintMessage(final CommandSender sender, final String message) {
        sender.sendMessage("§e[BlockMCBug] §f" + message);
    }
    
    @EventHandler
    public void join(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (p.isOp() || p.hasPermission("*")) {
            PrintMessage(p, "\uac00\ub054\uc529 blog.naver.com/ehe123 \uc5d0 \ub4e4\ub7ec \uc5c5\ub370\uc774\ud2b8\ub97c \ud655\uc778\ud558\uace0 \uac00\uc2dc\uae30 \ubc14\ub78d\ub2c8\ub2e4. \ud604\uc7ac \ubc84\uc804\uc740 v" + Main.version + " \uc785\ub2c8\ub2e4.");
            PrintMessage(p, "(\ud604\uc7ac \uba54\uc138\uc9c0\ub294 \uc624\ud53c\uc5d0\uac8c\ub9cc \ucd9c\ub825\ub429\ub2c8\ub2e4.)");
        }
    }
    
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents((Listener)new InventoryEvent(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new VehicleEvent(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerEvent(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BlockEvent(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new SkriptGUI(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ace(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new col(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ChestShop(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new Cart(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new VGH(), (Plugin)this);
        ConfigSetting.LoadConfig();
        PrintBukkit("\ubc84\uadf8 \ubc29\uc9c0 \ud50c\ub7ec\uadf8\uc778 v" + Main.version + " by Golden_Mine");
        PrintBukkit("blog.naver.com/ehe123");
        PrintBukkit("\ucf58\ud53c\uadf8 \uc124\uc815 \ub85c\ub4dc \uc644\ub8cc!");
        PrintBukkit("RPGITEM \ubc84\uadf8 \ubc29\uc9c0\ub294 \uae40\uc131\ud6c8(SHGroup)\ub2d8\uaed8\uc11c NRC \ud50c\ub7ec\uadf8\uc778\uc758 \uc18c\uc2a4\ub97c \uc81c\uacf5\ud574\uc8fc\uc168\uc2b5\ub2c8\ub2e4.");
        PrintBukkit("\ud504\ub9ac\ucea0/\ud3ec\uc158\ubcf5\uc0ac\ubc84\uadf8\ub4f1 \ubc29\uc9c0\ub294 milkyway0308\ub2d8\uc5d0\uac8c\uc11c \ud301\uc744 \uc81c\uacf5\ubc1b\uc558\uc2b5\ub2c8\ub2e4.");
        final int k = 0;
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                if (0 != BroadCastThreadOuter.i) {
                    System.out.println("\ud50c\ub7ec\uadf8\uc778\uc758 \uc5b4\ub5a4 \ubd80\ubd84\uc774\ub77c\ub3c4 \ubb34\ub2e8 \uc218\uc815 \ub610\ub294 \uc18c\uc2a4\ub97c \ubb34\ub2e8 \uc0ac\uc6a9 \ud558\uc2dc\ub294 \uacbd\uc6b0 \ubc95\uc801\uc73c\ub85c \uac15\ub825 \ub300\uc751\ud560 \uc608\uc815\uc785\ub2c8\ub2e4.");
                }
                new Thread(new BroadCastThreadOuter()).start();
            }
        }, 50000L, 50000L);
        PrintBukkit("RPG\uc544\uc774\ud15c \ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0 \uc124\uc815: " + ConfigSetting.RPGITEM_COPY_BUG);
        PrintBukkit("RPG\uc544\uc774\ud15c \ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0 \uc124\uc815\uc744 true\ub85c \ud558\uc600\ub294\ub370 RPGitems \ud50c\ub7ec\uadf8\uc778\uc774 \uc5c6\ub2e4\uba74 \uc7a0\uc2dc\ud6c4 \uc624\ub958\uac00 \ub098\ud0c0\ub098\uc9c0\ub9cc \ubb34\uc2dc\ud558\uc154\ub3c4 \ub429\ub2c8\ub2e4.");
        if (ConfigSetting.RPGITEM_COPY_BUG) {
            new Thread(new BlockRPGitemThread()).start();
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        try {
            if (commandLabel.toLowerCase().equals("blockmcbug")) {
                if (!sender.isOp()) {
                    PrintMessage(sender, "\uc624\ud53c\uac00 \uc544\ub2c8\uc5ec\uc11c \uc124\uc815\ubcf4\uae30,\ubcc0\uacbd\uc774 \ubd88\uac00\ub2a5\ud569\ub2c8\ub2e4.");
                    return false;
                }
                final String s = args[0];
                switch (s) {
                    case "\ub9ac\ub85c\ub4dc":
                    case "reload": {
                        ConfigSetting.LoadConfig();
                        if (ConfigSetting.LOCALE.equals("us")) {
                            PrintMessage(sender, "§fReload complete!");
                            break;
                        }
                        PrintMessage(sender, "§f\ub9ac\ub85c\ub4dc \uc644\ub8cc!");
                        break;
                    }
                    case "\ub3c4\uc6c0\ub9d0":
                    case "help": {
                        this.PrintHelp(sender);
                        break;
                    }
                    case "\ud604\uc7ac\uc124\uc815":
                    case "Setting": {
                        if (ConfigSetting.LOCALE.equals("us")) {
                            PrintMessage(sender, "Current settings:");
                        }
                        else {
                            PrintMessage(sender, "\ud604\uc7ac\uc0c1\ud0dc:");
                        }
                        PrintMessage(sender, "KEEP_COERCIVE_EXCHANGE: " + ConfigSetting.KEEP_COERCIVE_EXCHANGE);
                        PrintMessage(sender, "SHIFT_EXCHANGE: " + ConfigSetting.SHIFT_EXCHANGE);
                        PrintMessage(sender, "RPGITEM_COPY_BUG: " + ConfigSetting.RPGITEM_COPY_BUG);
                        PrintMessage(sender, "LOOK_MESSAGE_SHOPKEEPERS: " + ConfigSetting.LOOK_MESSAGE_SHOPKEEPERS);
                        PrintMessage(sender, "ONE_COPY_BUG: " + ConfigSetting.ONE_COPY_BUG);
                        PrintMessage(sender, "HOPPER_CART_REMOVE: " + ConfigSetting.HOPPER_CART_REMOVE_CHEST);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            this.PrintHelp(sender);
        }
        return false;
    }
    
    public void PrintHelp(final CommandSender sender) {
        if (ConfigSetting.LOCALE.equals("us")) {
            sender.sendMessage("§a*** [BlockMCBug] Commands ***");
            sender.sendMessage("§e/BlockMCBug help - see a Help. ");
            sender.sendMessage("§e/BlockMCBug reload - reload your config settings.");
            sender.sendMessage("§e/BlockMCBug setting - see current settings.");
        }
        else {
            sender.sendMessage("§a*** [BlockMCBug] \uba85\ub839\uc5b4 ***");
            sender.sendMessage("§e/BlockMCBug \ub3c4\uc6c0\ub9d0 - \ub3c4\uc6c0\ub9d0\uc744 \ubd05\ub2c8\ub2e4. ");
            sender.sendMessage("§e/BlockMCBug \ub9ac\ub85c\ub4dc - \ucf58\ud53c\uadf8 \uc124\uc815\uc744 \ub9ac\ub85c\ub4dc\ud569\ub2c8\ub2e4.");
            sender.sendMessage("§e/BlockMCBug \ud604\uc7ac\uc124\uc815 - \ud604\uc7ac \uc124\uc815\uc744 \ubd05\ub2c8\ub2e4.");
        }
    }
    
    public static int getPlayers(final Location loc, final int range) {
        int player = 0;
        for (final Player p : getOnlinePlayers()) {
            if (loc.getWorld().getName().equals(p.getWorld().getName()) && loc.distance(p.getLocation()) <= range) {
                ++player;
            }
        }
        return player;
    }
    
    public static final Collection<? extends Player> getOnlinePlayers() {
        try {
            final Method m = Bukkit.class.getDeclaredMethod("getOnlinePlayers", (Class<?>[])new Class[0]);
            final Object o45 = m.invoke(null, new Object[0]);
            if (o45 instanceof Collection) {
                return (Collection<? extends Player>)o45;
            }
            return Arrays.asList((Player[])o45);
        }
        catch (NoSuchMethodException ex) {}
        catch (Exception ex2) {}
        return (Collection<? extends Player>)Collections.emptyList();
    }
    
    public void tepoimp(final int k, final long g) {
        final ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        list.stream().filter(num -> num % 2 == 0);
    }
    
    static {
        SHOP_SIGN_PATTERN = new Pattern[] { Pattern.compile("^[\\w -.]*$"), Pattern.compile("^[1-9][0-9]*$"), Pattern.compile("(?i)^[\\d.bs(free) :]+$"), Pattern.compile("^[\\w #:-]+$") };
        Main.version = "Fin update 10";
    }
}
