// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.command;

import org.bukkit.conversations.ConversationCanceller;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.conversations.Conversation;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_5_R3.conversations.ConversationTracker;
import org.bukkit.command.ConsoleCommandSender;

public class CraftConsoleCommandSender extends ServerCommandSender implements ConsoleCommandSender
{
    protected final ConversationTracker conversationTracker;
    
    protected CraftConsoleCommandSender() {
        this.conversationTracker = new ConversationTracker();
    }
    
    public void sendMessage(final String message) {
        this.sendRawMessage(message);
    }
    
    public void sendRawMessage(final String message) {
        System.out.println(ChatColor.stripColor(message));
    }
    
    public void sendMessage(final String[] messages) {
        for (final String message : messages) {
            this.sendMessage(message);
        }
    }
    
    public String getName() {
        return "CONSOLE";
    }
    
    public boolean isOp() {
        return true;
    }
    
    public void setOp(final boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of server console");
    }
    
    public boolean beginConversation(final Conversation conversation) {
        return this.conversationTracker.beginConversation(conversation);
    }
    
    public void abandonConversation(final Conversation conversation) {
        this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
    }
    
    public void abandonConversation(final Conversation conversation, final ConversationAbandonedEvent details) {
        this.conversationTracker.abandonConversation(conversation, details);
    }
    
    public void acceptConversationInput(final String input) {
        this.conversationTracker.acceptConversationInput(input);
    }
    
    public boolean isConversing() {
        return this.conversationTracker.isConversing();
    }
}
