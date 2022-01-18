// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.util.List;
import java.util.HashMap;

public class IncomingPacketsLastAck
{
    private HashMap<String, MessageAck> lastAckMap;
    
    public IncomingPacketsLastAck() {
        this.lastAckMap = new HashMap<String, MessageAck>();
    }
    
    public String toString() {
        return this.lastAckMap.values().toString();
    }
    
    public void remove(final String memberHostPort) {
        this.lastAckMap.remove(memberHostPort);
    }
    
    public MessageAck getLastAck(final String memberHostPort) {
        return this.lastAckMap.get(memberHostPort);
    }
    
    public void updateLastAck(final AckResendMessages ackResendMessages) {
        final List<Message> messages = ackResendMessages.getMessages();
        for (int i = 0; i < messages.size(); ++i) {
            final Message msg = messages.get(i);
            if (msg instanceof MessageAck) {
                final MessageAck lastAck = (MessageAck)msg;
                this.lastAckMap.put(lastAck.getToHostPort(), lastAck);
            }
        }
    }
}
