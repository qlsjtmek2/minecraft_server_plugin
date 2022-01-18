// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import java.util.Iterator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.Plugin;
import java.io.Writer;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Arrays;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import org.bukkit.Bukkit;
import java.io.StringWriter;
import java.util.concurrent.Callable;

public class CraftCrashReport implements Callable
{
    public Object call() throws Exception {
        final StringWriter value = new StringWriter();
        try {
            value.append("\n   Running: ").append(Bukkit.getName()).append(" version ").append(Bukkit.getVersion()).append(" (Implementing API version ").append(Bukkit.getBukkitVersion()).append(") ").append(String.valueOf(MinecraftServer.getServer().getOnlineMode()));
            value.append("\n   Plugins: {");
            for (final Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                final PluginDescriptionFile description = plugin.getDescription();
                value.append(' ').append(description.getFullName()).append(' ').append(description.getMain()).append(' ').append(Arrays.toString(description.getAuthors().toArray())).append(',');
            }
            value.append("}\n   Warnings: ").append(Bukkit.getWarningState().name());
            value.append("\n   Threads: {");
            for (final Map.Entry<Thread, ? extends Object[]> entry : Thread.getAllStackTraces().entrySet()) {
                value.append(' ').append(entry.getKey().getState().name()).append(' ').append(entry.getKey().getName()).append(": ").append(Arrays.toString((Object[])(Object)entry.getValue())).append(',');
            }
            value.append("}\n   ").append(Bukkit.getScheduler().toString());
        }
        catch (Throwable t) {
            value.append("\n   Failed to handle CraftCrashReport:\n");
            final PrintWriter writer = new PrintWriter(value);
            t.printStackTrace(writer);
            writer.flush();
        }
        return value.toString();
    }
}
