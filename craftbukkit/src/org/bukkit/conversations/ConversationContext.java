// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import org.bukkit.plugin.Plugin;
import java.util.Map;

public class ConversationContext
{
    private Conversable forWhom;
    private Map<Object, Object> sessionData;
    private Plugin plugin;
    
    public ConversationContext(final Plugin plugin, final Conversable forWhom, final Map<Object, Object> initialSessionData) {
        this.plugin = plugin;
        this.forWhom = forWhom;
        this.sessionData = initialSessionData;
    }
    
    public Plugin getPlugin() {
        return this.plugin;
    }
    
    public Conversable getForWhom() {
        return this.forWhom;
    }
    
    public Object getSessionData(final Object key) {
        return this.sessionData.get(key);
    }
    
    public void setSessionData(final Object key, final Object value) {
        this.sessionData.put(key, value);
    }
}
