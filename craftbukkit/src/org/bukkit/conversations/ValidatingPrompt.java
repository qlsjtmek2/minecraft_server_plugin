// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import org.bukkit.ChatColor;

public abstract class ValidatingPrompt implements Prompt
{
    public Prompt acceptInput(final ConversationContext context, final String input) {
        if (this.isInputValid(context, input)) {
            return this.acceptValidatedInput(context, input);
        }
        final String failPrompt = this.getFailedValidationText(context, input);
        if (failPrompt != null) {
            context.getForWhom().sendRawMessage(ChatColor.RED + failPrompt);
        }
        return this;
    }
    
    public boolean blocksForInput(final ConversationContext context) {
        return true;
    }
    
    protected abstract boolean isInputValid(final ConversationContext p0, final String p1);
    
    protected abstract Prompt acceptValidatedInput(final ConversationContext p0, final String p1);
    
    protected String getFailedValidationText(final ConversationContext context, final String invalidInput) {
        return null;
    }
}
