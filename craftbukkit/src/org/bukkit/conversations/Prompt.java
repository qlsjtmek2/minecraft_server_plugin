// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

public interface Prompt extends Cloneable
{
    public static final Prompt END_OF_CONVERSATION = null;
    
    String getPromptText(final ConversationContext p0);
    
    boolean blocksForInput(final ConversationContext p0);
    
    Prompt acceptInput(final ConversationContext p0, final String p1);
}
