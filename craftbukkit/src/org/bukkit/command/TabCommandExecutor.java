// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command;

import java.util.List;

@Deprecated
public interface TabCommandExecutor extends CommandExecutor
{
    List<String> onTabComplete();
}
