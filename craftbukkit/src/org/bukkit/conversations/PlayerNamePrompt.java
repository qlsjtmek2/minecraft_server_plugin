// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class PlayerNamePrompt extends ValidatingPrompt
{
    private Plugin plugin;
    
    public PlayerNamePrompt(final Plugin plugin) {
        this.plugin = plugin;
    }
    
    protected boolean isInputValid(final ConversationContext context, final String input) {
        return this.plugin.getServer().getPlayer(input) != null;
    }
    
    protected Prompt acceptValidatedInput(final ConversationContext context, final String input) {
        return this.acceptValidatedInput(context, this.plugin.getServer().getPlayer(input));
    }
    
    protected abstract Prompt acceptValidatedInput(final ConversationContext p0, final Player p1);
}
