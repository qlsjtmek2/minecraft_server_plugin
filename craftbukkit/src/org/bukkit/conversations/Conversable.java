// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

public interface Conversable
{
    boolean isConversing();
    
    void acceptConversationInput(final String p0);
    
    boolean beginConversation(final Conversation p0);
    
    void abandonConversation(final Conversation p0);
    
    void abandonConversation(final Conversation p0, final ConversationAbandonedEvent p1);
    
    void sendRawMessage(final String p0);
}
