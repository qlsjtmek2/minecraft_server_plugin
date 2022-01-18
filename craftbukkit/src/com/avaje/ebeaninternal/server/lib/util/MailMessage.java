// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

public class MailMessage
{
    ArrayList<String> bodylines;
    MailAddress senderAddress;
    HashMap<String, String> header;
    MailAddress currentRecipient;
    ArrayList<MailAddress> recipientList;
    
    public MailMessage() {
        this.header = new HashMap<String, String>();
        this.recipientList = new ArrayList<MailAddress>();
        this.bodylines = new ArrayList<String>();
    }
    
    public void setCurrentRecipient(final MailAddress currentRecipient) {
        this.currentRecipient = currentRecipient;
    }
    
    public MailAddress getCurrentRecipient() {
        return this.currentRecipient;
    }
    
    public void addRecipient(final String alias, final String emailAddress) {
        this.recipientList.add(new MailAddress(alias, emailAddress));
    }
    
    public void setSender(final String alias, final String senderEmail) {
        this.senderAddress = new MailAddress(alias, senderEmail);
    }
    
    public MailAddress getSender() {
        return this.senderAddress;
    }
    
    public Iterator<MailAddress> getRecipientList() {
        return this.recipientList.iterator();
    }
    
    public void addHeader(final String key, final String val) {
        this.header.put(key, val);
    }
    
    public void setSubject(final String subject) {
        this.addHeader("Subject", subject);
    }
    
    public String getSubject() {
        return this.getHeader("Subject");
    }
    
    public void addBodyLine(final String line) {
        this.bodylines.add(line);
    }
    
    public Iterator<String> getBodyLines() {
        return this.bodylines.iterator();
    }
    
    public Iterator<String> getHeaderFields() {
        return this.header.keySet().iterator();
    }
    
    public String getHeader(final String key) {
        return this.header.get(key);
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder(100);
        sb.append("Sender: " + this.senderAddress + "\tRecipient: " + this.recipientList + "\n");
        for (final String key : this.header.keySet()) {
            final String hline = key + ": " + this.header.get(key) + "\n";
            sb.append(hline);
        }
        sb.append("\n");
        final Iterator<String> e = this.bodylines.iterator();
        while (e.hasNext()) {
            sb.append(e.next()).append("\n");
        }
        return sb.toString();
    }
}
