// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

public class MailEvent
{
    Throwable error;
    MailMessage message;
    
    public MailEvent(final MailMessage message, final Throwable error) {
        this.message = message;
        this.error = error;
    }
    
    public MailMessage getMailMessage() {
        return this.message;
    }
    
    public boolean wasSuccessful() {
        return this.error == null;
    }
    
    public Throwable getError() {
        return this.error;
    }
}
