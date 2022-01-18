// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.sctp;

import com.sun.nio.sctp.Notification;

public final class SctpNotificationEvent
{
    private final Notification notification;
    private final Object attachment;
    
    public SctpNotificationEvent(final Notification notification, final Object attachment) {
        if (notification == null) {
            throw new NullPointerException("notification");
        }
        this.notification = notification;
        this.attachment = attachment;
    }
    
    public Notification notification() {
        return this.notification;
    }
    
    public Object attachment() {
        return this.attachment;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SctpNotificationEvent that = (SctpNotificationEvent)o;
        return this.attachment.equals(that.attachment) && this.notification.equals(that.notification);
    }
    
    @Override
    public int hashCode() {
        int result = this.notification.hashCode();
        result = 31 * result + this.attachment.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return "SctpNotification{notification=" + this.notification + ", attachment=" + this.attachment + '}';
    }
}
