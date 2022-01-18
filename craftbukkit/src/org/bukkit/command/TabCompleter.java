// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command;

import java.util.List;

public interface TabCompleter
{
    List<String> onTabComplete(final CommandSender p0, final Command p1, final String p2, final String[] p3);
}
