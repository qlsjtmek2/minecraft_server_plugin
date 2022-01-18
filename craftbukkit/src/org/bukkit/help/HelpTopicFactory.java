// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.help;

import org.bukkit.command.Command;

public interface HelpTopicFactory<TCommand extends Command>
{
    HelpTopic createTopic(final TCommand p0);
}
