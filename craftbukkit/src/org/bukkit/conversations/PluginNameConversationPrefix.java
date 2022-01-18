// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;

public class PluginNameConversationPrefix implements ConversationPrefix
{
    protected String separator;
    protected ChatColor prefixColor;
    protected Plugin plugin;
    private String cachedPrefix;
    
    public PluginNameConversationPrefix(final Plugin plugin) {
        this(plugin, " > ", ChatColor.LIGHT_PURPLE);
    }
    
    public PluginNameConversationPrefix(final Plugin plugin, final String separator, final ChatColor prefixColor) {
        this.separator = separator;
        this.prefixColor = prefixColor;
        this.plugin = plugin;
        this.cachedPrefix = prefixColor + plugin.getDescription().getName() + separator + ChatColor.WHITE;
    }
    
    public String getPrefix(final ConversationContext context) {
        return this.cachedPrefix;
    }
}
