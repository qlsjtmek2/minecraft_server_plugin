// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command;

public class MultipleCommandAlias extends Command
{
    private Command[] commands;
    
    public MultipleCommandAlias(final String name, final Command[] commands) {
        super(name);
        this.commands = commands;
    }
    
    public Command[] getCommands() {
        return this.commands;
    }
    
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        boolean result = false;
        for (final Command command : this.commands) {
            result |= command.execute(sender, commandLabel, args);
        }
        return result;
    }
}
