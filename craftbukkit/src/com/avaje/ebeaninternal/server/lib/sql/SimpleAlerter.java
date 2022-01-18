// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebeaninternal.server.lib.util.MailSender;
import com.avaje.ebeaninternal.server.lib.util.MailMessage;
import com.avaje.ebean.config.GlobalProperties;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.lib.util.MailEvent;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.lib.util.MailListener;

public class SimpleAlerter implements DataSourceAlertListener, MailListener
{
    private static final Logger logger;
    
    public void handleEvent(final MailEvent event) {
        final Throwable e = event.getError();
        if (e != null) {
            SimpleAlerter.logger.log(Level.SEVERE, null, e);
        }
    }
    
    public void dataSourceDown(final String dataSourceName) {
        final String msg = this.getSubject(true, dataSourceName);
        this.sendMessage(msg, msg);
    }
    
    public void dataSourceUp(final String dataSourceName) {
        final String msg = this.getSubject(false, dataSourceName);
        this.sendMessage(msg, msg);
    }
    
    public void warning(final String subject, final String msg) {
        this.sendMessage(subject, msg);
    }
    
    private String getSubject(final boolean isDown, final String dsName) {
        String msg = "The DataSource " + dsName;
        if (isDown) {
            msg += " is DOWN!!";
        }
        else {
            msg += " is UP.";
        }
        return msg;
    }
    
    private void sendMessage(final String subject, final String msg) {
        final String fromUser = GlobalProperties.get("alert.fromuser", null);
        final String fromEmail = GlobalProperties.get("alert.fromemail", null);
        final String mailServerName = GlobalProperties.get("alert.mailserver", null);
        final String toEmail = GlobalProperties.get("alert.toemail", null);
        if (mailServerName == null) {
            return;
        }
        final MailMessage data = new MailMessage();
        data.setSender(fromUser, fromEmail);
        data.addBodyLine(msg);
        data.setSubject(subject);
        final String[] toList = toEmail.split(",");
        if (toList.length == 0) {
            throw new RuntimeException("alert.toemail has not been set?");
        }
        for (int i = 0; i < toList.length; ++i) {
            data.addRecipient(null, toList[i].trim());
        }
        final MailSender sender = new MailSender(mailServerName);
        sender.setMailListener(this);
        sender.sendInBackground(data);
    }
    
    static {
        logger = Logger.getLogger(SimpleAlerter.class.getName());
    }
}
