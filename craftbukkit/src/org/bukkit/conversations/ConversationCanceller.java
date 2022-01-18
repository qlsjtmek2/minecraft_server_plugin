// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

public interface ConversationCanceller extends Cloneable
{
    void setConversation(final Conversation p0);
    
    boolean cancelBasedOnInput(final ConversationContext p0, final String p1);
    
    ConversationCanceller clone();
}
