// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import java.util.Iterator;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.plugin.Plugin;

public class ConversationFactory
{
    protected Plugin plugin;
    protected boolean isModal;
    protected boolean localEchoEnabled;
    protected ConversationPrefix prefix;
    protected Prompt firstPrompt;
    protected Map<Object, Object> initialSessionData;
    protected String playerOnlyMessage;
    protected List<ConversationCanceller> cancellers;
    protected List<ConversationAbandonedListener> abandonedListeners;
    
    public ConversationFactory(final Plugin plugin) {
        this.plugin = plugin;
        this.isModal = true;
        this.localEchoEnabled = true;
        this.prefix = new NullConversationPrefix();
        this.firstPrompt = Prompt.END_OF_CONVERSATION;
        this.initialSessionData = new HashMap<Object, Object>();
        this.playerOnlyMessage = null;
        this.cancellers = new ArrayList<ConversationCanceller>();
        this.abandonedListeners = new ArrayList<ConversationAbandonedListener>();
    }
    
    public ConversationFactory withModality(final boolean modal) {
        this.isModal = modal;
        return this;
    }
    
    public ConversationFactory withLocalEcho(final boolean localEchoEnabled) {
        this.localEchoEnabled = localEchoEnabled;
        return this;
    }
    
    public ConversationFactory withPrefix(final ConversationPrefix prefix) {
        this.prefix = prefix;
        return this;
    }
    
    public ConversationFactory withTimeout(final int timeoutSeconds) {
        return this.withConversationCanceller(new InactivityConversationCanceller(this.plugin, timeoutSeconds));
    }
    
    public ConversationFactory withFirstPrompt(final Prompt firstPrompt) {
        this.firstPrompt = firstPrompt;
        return this;
    }
    
    public ConversationFactory withInitialSessionData(final Map<Object, Object> initialSessionData) {
        this.initialSessionData = initialSessionData;
        return this;
    }
    
    public ConversationFactory withEscapeSequence(final String escapeSequence) {
        return this.withConversationCanceller(new ExactMatchConversationCanceller(escapeSequence));
    }
    
    public ConversationFactory withConversationCanceller(final ConversationCanceller canceller) {
        this.cancellers.add(canceller);
        return this;
    }
    
    public ConversationFactory thatExcludesNonPlayersWithMessage(final String playerOnlyMessage) {
        this.playerOnlyMessage = playerOnlyMessage;
        return this;
    }
    
    public ConversationFactory addConversationAbandonedListener(final ConversationAbandonedListener listener) {
        this.abandonedListeners.add(listener);
        return this;
    }
    
    public Conversation buildConversation(final Conversable forWhom) {
        if (this.playerOnlyMessage != null && !(forWhom instanceof Player)) {
            return new Conversation(this.plugin, forWhom, new NotPlayerMessagePrompt());
        }
        final Map<Object, Object> copiedInitialSessionData = new HashMap<Object, Object>();
        copiedInitialSessionData.putAll(this.initialSessionData);
        final Conversation conversation = new Conversation(this.plugin, forWhom, this.firstPrompt, copiedInitialSessionData);
        conversation.setModal(this.isModal);
        conversation.setLocalEchoEnabled(this.localEchoEnabled);
        conversation.setPrefix(this.prefix);
        for (final ConversationCanceller canceller : this.cancellers) {
            conversation.addConversationCanceller(canceller.clone());
        }
        for (final ConversationAbandonedListener listener : this.abandonedListeners) {
            conversation.addConversationAbandonedListener(listener);
        }
        return conversation;
    }
    
    private class NotPlayerMessagePrompt extends MessagePrompt
    {
        public String getPromptText(final ConversationContext context) {
            return ConversationFactory.this.playerOnlyMessage;
        }
        
        protected Prompt getNextPrompt(final ConversationContext context) {
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
