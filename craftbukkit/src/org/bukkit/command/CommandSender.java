// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command;

import org.bukkit.Server;
import org.bukkit.permissions.Permissible;

public interface CommandSender extends Permissible
{
    void sendMessage(final String p0);
    
    void sendMessage(final String[] p0);
    
    Server getServer();
    
    String getName();
}
