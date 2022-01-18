// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import java.util.Iterator;
import org.bukkit.command.Command;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SaveOnCommand extends VanillaCommand
{
    public SaveOnCommand() {
        super("save-on");
        this.description = "Enables server autosaving";
        this.usageMessage = "/save-on";
        this.setPermission("bukkit.command.save.enable");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        for (final World world : Bukkit.getWorlds()) {
            world.setAutoSave(true);
        }
        Command.broadcastCommandMessage(sender, "Enabled level saving..");
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        return (List<String>)ImmutableList.of();
    }
}
