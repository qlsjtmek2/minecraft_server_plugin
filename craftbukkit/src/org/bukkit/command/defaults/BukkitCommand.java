// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.util.List;
import org.bukkit.command.Command;

public abstract class BukkitCommand extends Command
{
    protected BukkitCommand(final String name) {
        super(name);
    }
    
    protected BukkitCommand(final String name, final String description, final String usageMessage, final List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
