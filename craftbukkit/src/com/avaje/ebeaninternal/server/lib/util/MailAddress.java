// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

public class MailAddress
{
    String alias;
    String emailAddress;
    
    public MailAddress(final String alias, final String emailAddress) {
        this.alias = alias;
        this.emailAddress = emailAddress;
    }
    
    public String getAlias() {
        if (this.alias == null) {
            return "";
        }
        return this.alias;
    }
    
    public String getEmailAddress() {
        return this.emailAddress;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.getAlias()).append(" ").append("<").append(this.getEmailAddress()).append(">");
        return sb.toString();
    }
}
