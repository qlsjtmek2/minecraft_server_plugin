// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import java.util.logging.LogRecord;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PluginLogger extends Logger
{
    private String pluginName;
    
    public PluginLogger(final Plugin context) {
        super(context.getClass().getCanonicalName(), null);
        final String prefix = context.getDescription().getPrefix();
        this.pluginName = ((prefix != null) ? ("[" + prefix + "] ") : ("[" + context.getDescription().getName() + "] "));
        this.setParent(context.getServer().getLogger());
        this.setLevel(Level.ALL);
    }
    
    public void log(final LogRecord logRecord) {
        logRecord.setMessage(this.pluginName + logRecord.getMessage());
        super.log(logRecord);
    }
}
