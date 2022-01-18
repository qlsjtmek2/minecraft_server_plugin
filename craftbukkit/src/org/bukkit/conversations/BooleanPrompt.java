// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ArrayUtils;

public abstract class BooleanPrompt extends ValidatingPrompt
{
    protected boolean isInputValid(final ConversationContext context, final String input) {
        final String[] accepted = { "true", "false", "on", "off", "yes", "no" };
        return ArrayUtils.contains(accepted, input.toLowerCase());
    }
    
    protected Prompt acceptValidatedInput(final ConversationContext context, final String input) {
        return this.acceptValidatedInput(context, BooleanUtils.toBoolean(input));
    }
    
    protected abstract Prompt acceptValidatedInput(final ConversationContext p0, final boolean p1);
}
