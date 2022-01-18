// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command;

import java.util.List;

public interface CommandMap
{
    void registerAll(final String p0, final List<Command> p1);
    
    boolean register(final String p0, final String p1, final Command p2);
    
    boolean register(final String p0, final Command p1);
    
    boolean dispatch(final CommandSender p0, final String p1) throws CommandException;
    
    void clearCommands();
    
    Command getCommand(final String p0);
    
    List<String> tabComplete(final CommandSender p0, final String p1) throws IllegalArgumentException;
}
