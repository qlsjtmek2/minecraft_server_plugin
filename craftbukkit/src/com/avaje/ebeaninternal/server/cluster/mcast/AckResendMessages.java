// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.util.List;
import java.util.ArrayList;

public class AckResendMessages
{
    ArrayList<Message> messages;
    
    public AckResendMessages() {
        this.messages = new ArrayList<Message>();
    }
    
    public String toString() {
        return this.messages.toString();
    }
    
    public int size() {
        return this.messages.size();
    }
    
    public void add(final MessageAck ack) {
        this.messages.add(ack);
    }
    
    public void add(final MessageResend resend) {
        this.messages.add(resend);
    }
    
    public List<Message> getMessages() {
        return this.messages;
    }
}
