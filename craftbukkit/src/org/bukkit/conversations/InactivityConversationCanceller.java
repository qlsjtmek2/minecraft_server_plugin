// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import org.bukkit.plugin.Plugin;

public class InactivityConversationCanceller implements ConversationCanceller
{
    protected Plugin plugin;
    protected int timeoutSeconds;
    protected Conversation conversation;
    private int taskId;
    
    public InactivityConversationCanceller(final Plugin plugin, final int timeoutSeconds) {
        this.taskId = -1;
        this.plugin = plugin;
        this.timeoutSeconds = timeoutSeconds;
    }
    
    public void setConversation(final Conversation conversation) {
        this.conversation = conversation;
        this.startTimer();
    }
    
    public boolean cancelBasedOnInput(final ConversationContext context, final String input) {
        this.stopTimer();
        this.startTimer();
        return false;
    }
    
    public ConversationCanceller clone() {
        return new InactivityConversationCanceller(this.plugin, this.timeoutSeconds);
    }
    
    private void startTimer() {
        this.taskId = this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            public void run() {
                if (InactivityConversationCanceller.this.conversation.getState() == Conversation.ConversationState.UNSTARTED) {
                    InactivityConversationCanceller.this.startTimer();
                }
                else if (InactivityConversationCanceller.this.conversation.getState() == Conversation.ConversationState.STARTED) {
                    InactivityConversationCanceller.this.cancelling(InactivityConversationCanceller.this.conversation);
                    InactivityConversationCanceller.this.conversation.abandon(new ConversationAbandonedEvent(InactivityConversationCanceller.this.conversation, InactivityConversationCanceller.this));
                }
            }
        }, this.timeoutSeconds * 20);
    }
    
    private void stopTimer() {
        if (this.taskId != -1) {
            this.plugin.getServer().getScheduler().cancelTask(this.taskId);
            this.taskId = -1;
        }
    }
    
    protected void cancelling(final Conversation conversation) {
    }
}
