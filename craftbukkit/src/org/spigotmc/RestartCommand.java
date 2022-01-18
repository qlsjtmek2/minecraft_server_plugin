// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc;

import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class RestartCommand extends Command
{
    public RestartCommand(final String name) {
        super(name);
        this.description = "Restarts the server";
        this.usageMessage = "/restart";
        this.setPermission("bukkit.command.restart");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (this.testPermission(sender)) {
            Spigot.restart();
        }
        return true;
    }
}
