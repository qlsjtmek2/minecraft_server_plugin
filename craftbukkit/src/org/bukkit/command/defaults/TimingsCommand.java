// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.util.logging.Level;
import java.net.URLEncoder;
import java.net.URL;
import java.net.HttpURLConnection;
import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import java.io.IOException;
import org.bukkit.plugin.RegisteredListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import org.spigotmc.CustomTimingsHandler;
import org.bukkit.plugin.TimedRegisteredListener;
import org.bukkit.event.HandlerList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.List;

public class TimingsCommand extends BukkitCommand
{
    private static final List<String> TIMINGS_SUBCOMMANDS;
    public static long timingStart;
    
    public TimingsCommand(final String name) {
        super(name);
        this.description = "Records timings for all plugin events";
        this.usageMessage = "/timings <reset|merged|separate|on|off> [paste]";
        this.setPermission("bukkit.command.timings");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        if ("on".equals(args[0])) {
            ((SimplePluginManager)Bukkit.getPluginManager()).useTimings(true);
            sender.sendMessage("Enabled Timings");
        }
        else if ("off".equals(args[0])) {
            ((SimplePluginManager)Bukkit.getPluginManager()).useTimings(false);
            sender.sendMessage("Disabled Timings");
        }
        final boolean separate = "separate".equals(args[0]);
        final boolean paste = "paste".equals(args[0]);
        if ("on".equals(args[0]) || "reset".equals(args[0])) {
            if (!"on".equals(args[0]) && !Bukkit.getPluginManager().useTimings()) {
                sender.sendMessage("Please enable timings by typing /timings on");
                return true;
            }
            for (final HandlerList handlerList : HandlerList.getHandlerLists()) {
                for (final RegisteredListener listener : handlerList.getRegisteredListeners()) {
                    if (listener instanceof TimedRegisteredListener) {
                        ((TimedRegisteredListener)listener).reset();
                    }
                }
            }
            CustomTimingsHandler.reload();
            TimingsCommand.timingStart = System.nanoTime();
            sender.sendMessage("Timings reset");
        }
        else if ("merged".equals(args[0]) || separate || paste) {
            if (!Bukkit.getPluginManager().useTimings()) {
                sender.sendMessage("Please enable timings by typing /timings on");
                return true;
            }
            final long sampleTime = System.nanoTime() - TimingsCommand.timingStart;
            int index = 0;
            int pluginIdx = 0;
            final File timingFolder = new File("timings");
            timingFolder.mkdirs();
            File timings = new File(timingFolder, "timings.txt");
            File names = null;
            final ByteArrayOutputStream bout = paste ? new ByteArrayOutputStream() : null;
            while (timings.exists()) {
                timings = new File(timingFolder, "timings" + ++index + ".txt");
            }
            PrintStream fileTimings = null;
            PrintStream fileNames = null;
            try {
                fileTimings = (paste ? new PrintStream(bout) : new PrintStream(timings));
                if (separate) {
                    names = new File(timingFolder, "names" + index + ".txt");
                    fileNames = new PrintStream(names);
                }
                for (final Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    ++pluginIdx;
                    long totalTime = 0L;
                    if (separate) {
                        fileNames.println(pluginIdx + " " + plugin.getDescription().getFullName());
                        fileTimings.println("Plugin " + pluginIdx);
                    }
                    else {
                        fileTimings.println(plugin.getDescription().getFullName());
                    }
                    for (final RegisteredListener listener2 : HandlerList.getRegisteredListeners(plugin)) {
                        if (listener2 instanceof TimedRegisteredListener) {
                            final TimedRegisteredListener trl = (TimedRegisteredListener)listener2;
                            final long time = trl.getTotalTime();
                            final int count = trl.getCount();
                            if (count == 0) {
                                continue;
                            }
                            final long avg = time / count;
                            totalTime += time;
                            final Event event = trl.getEvent();
                            if (count <= 0 || event == null) {
                                continue;
                            }
                            fileTimings.println("    " + event.getClass().getSimpleName() + (trl.hasMultiple() ? " (and others)" : "") + " Time: " + time + " Count: " + count + " Avg: " + avg + " Violations: " + trl.violations);
                        }
                    }
                    fileTimings.println("    Total time " + totalTime + " (" + totalTime / 1000000000L + "s)");
                }
                CustomTimingsHandler.printTimings(fileTimings);
                fileTimings.println("Sample time " + sampleTime + " (" + sampleTime / 1.0E9 + "s)");
                if (paste) {
                    new PasteThread(sender, bout).start();
                    return true;
                }
                sender.sendMessage("Timings written to " + timings.getPath());
                sender.sendMessage("Paste contents of file into form at http://aikar.co/timings.php to read results.");
                if (separate) {
                    sender.sendMessage("Names written to " + names.getPath());
                }
            }
            catch (IOException e) {}
            finally {
                if (fileTimings != null) {
                    fileTimings.close();
                }
                if (fileNames != null) {
                    fileNames.close();
                }
            }
        }
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], TimingsCommand.TIMINGS_SUBCOMMANDS, new ArrayList<String>(TimingsCommand.TIMINGS_SUBCOMMANDS.size()));
        }
        return (List<String>)ImmutableList.of();
    }
    
    static {
        TIMINGS_SUBCOMMANDS = ImmutableList.of("merged", "reset", "separate");
        TimingsCommand.timingStart = 0L;
    }
    
    private static class PasteThread extends Thread
    {
        private final CommandSender sender;
        private final ByteArrayOutputStream bout;
        
        public PasteThread(final CommandSender sender, final ByteArrayOutputStream bout) {
            super("Timings paste thread");
            this.sender = sender;
            this.bout = bout;
        }
        
        public void run() {
            try {
                final HttpURLConnection con = (HttpURLConnection)new URL("http://paste.ubuntu.com/").openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setInstanceFollowRedirects(false);
                final OutputStream out = con.getOutputStream();
                out.write("poster=Spigot&syntax=text&content=".getBytes("UTF-8"));
                out.write(URLEncoder.encode(this.bout.toString("UTF-8"), "UTF-8").getBytes("UTF-8"));
                out.close();
                con.getInputStream().close();
                final String location = con.getHeaderField("Location");
                final String pasteID = location.substring("http://paste.ubuntu.com/".length(), location.length() - 1);
                this.sender.sendMessage(ChatColor.GREEN + "Your timings have been pasted to " + location);
                this.sender.sendMessage(ChatColor.GREEN + "You can view the results at http://aikar.co/timings.php?url=" + pasteID);
            }
            catch (IOException ex) {
                this.sender.sendMessage(ChatColor.RED + "Error pasting timings, check your console for more information");
                Bukkit.getServer().getLogger().log(Level.WARNING, "Could not paste timings", ex);
            }
        }
    }
}
