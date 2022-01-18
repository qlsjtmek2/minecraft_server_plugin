// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public abstract class ExTCommand
{
    private final String cmd;
    private final String cmdhelp;
    private final String description;
    private final String permission;
    private final int minargs;
    private final boolean onlyPlayerUse;
    
    public ExTCommand(final String cmd, final String cmdhelp, final String description, final String permission, final int minargs, final boolean onlyPlayerUse) {
        this.cmd = cmd;
        this.cmdhelp = cmdhelp;
        this.description = description;
        this.permission = permission;
        this.minargs = minargs;
        this.onlyPlayerUse = onlyPlayerUse;
    }
    
    public String getCmd() {
        return this.cmd;
    }
    
    public String getCmdHelp() {
        return this.cmdhelp;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public boolean hasPermission(final CommandSender sender) {
        return this.permission == null || sender.hasPermission(this.permission);
    }
    
    public int getMinArgs() {
        return this.minargs + 1;
    }
    
    public boolean getOnlyPlayerUse() {
        return this.onlyPlayerUse;
    }
    
    public abstract void action(final CommandSender p0, final String[] p1, final CommandHandler p2) throws CommandException;
}
