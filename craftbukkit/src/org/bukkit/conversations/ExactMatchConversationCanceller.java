// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

public class ExactMatchConversationCanceller implements ConversationCanceller
{
    private String escapeSequence;
    
    public ExactMatchConversationCanceller(final String escapeSequence) {
        this.escapeSequence = escapeSequence;
    }
    
    public void setConversation(final Conversation conversation) {
    }
    
    public boolean cancelBasedOnInput(final ConversationContext context, final String input) {
        return input.equals(this.escapeSequence);
    }
    
    public ConversationCanceller clone() {
        return new ExactMatchConversationCanceller(this.escapeSequence);
    }
}
