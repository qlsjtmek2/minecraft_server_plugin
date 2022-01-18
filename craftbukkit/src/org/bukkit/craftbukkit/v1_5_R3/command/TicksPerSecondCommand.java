// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.command;

import org.bukkit.ChatColor;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class TicksPerSecondCommand extends Command
{
    public TicksPerSecondCommand(final String name) {
        super(name);
        this.description = "Gets the current ticks per second for the server";
        this.usageMessage = "/tps";
        this.setPermission("bukkit.command.tps");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        final double tps = Math.min(20.0, Math.round(MinecraftServer.currentTPS * 10.0) / 10.0);
        ChatColor color;
        if (tps > 19.2) {
            color = ChatColor.GREEN;
        }
        else if (tps > 17.4) {
            color = ChatColor.YELLOW;
        }
        else {
            color = ChatColor.RED;
        }
        sender.sendMessage(ChatColor.GOLD + "[TPS] " + color + tps);
        return true;
    }
}
