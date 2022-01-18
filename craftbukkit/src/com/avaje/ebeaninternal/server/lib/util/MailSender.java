// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

import java.net.UnknownHostException;
import java.io.IOException;
import java.net.InetAddress;
import java.io.Reader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.logging.Logger;

public class MailSender implements Runnable
{
    private static final Logger logger;
    int traceLevel;
    Socket sserver;
    String server;
    BufferedReader in;
    OutputStreamWriter out;
    MailMessage message;
    MailListener listener;
    private static final int SMTP_PORT = 25;
    
    public MailSender(final String server) {
        this.traceLevel = 0;
        this.listener = null;
        this.server = server;
    }
    
    public void setMailListener(final MailListener listener) {
        this.listener = listener;
    }
    
    public void run() {
        this.send(this.message);
    }
    
    public void sendInBackground(final MailMessage message) {
        this.message = message;
        final Thread thread = new Thread(this);
        thread.start();
    }
    
    public void send(final MailMessage message) {
        try {
            final Iterator<MailAddress> i = message.getRecipientList();
            while (i.hasNext()) {
                final MailAddress recipientAddress = i.next();
                this.send(message, this.sserver = new Socket(this.server, 25), recipientAddress);
                this.sserver.close();
                if (this.listener != null) {
                    final MailEvent event = new MailEvent(message, null);
                    this.listener.handleEvent(event);
                }
            }
        }
        catch (Exception ex) {
            if (this.listener != null) {
                final MailEvent event2 = new MailEvent(message, ex);
                this.listener.handleEvent(event2);
            }
            else {
                MailSender.logger.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void send(final MailMessage message, final Socket sserver, final MailAddress recipientAddress) throws IOException {
        final InetAddress localhost = sserver.getLocalAddress();
        final String localaddress = localhost.getHostAddress();
        final MailAddress sender = message.getSender();
        message.setCurrentRecipient(recipientAddress);
        if (message.getHeader("Date") == null) {
            message.addHeader("Date", new Date().toString());
        }
        if (message.getHeader("From") == null) {
            message.addHeader("From", sender.getAlias() + " <" + sender.getEmailAddress() + ">");
        }
        message.addHeader("To", recipientAddress.getAlias() + " <" + recipientAddress.getEmailAddress() + ">");
        this.out = new OutputStreamWriter(sserver.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(sserver.getInputStream()));
        final String sintro = this.readln();
        if (!sintro.startsWith("220")) {
            MailSender.logger.fine("SmtpSender: intro==" + sintro);
            return;
        }
        this.writeln("EHLO " + localaddress);
        if (!this.expect250()) {
            return;
        }
        this.writeln("MAIL FROM:<" + sender.getEmailAddress() + ">");
        if (!this.expect250()) {
            return;
        }
        this.writeln("RCPT TO:<" + recipientAddress.getEmailAddress() + ">");
        if (!this.expect250()) {
            return;
        }
        this.writeln("DATA");
        while (true) {
            final String line = this.readln();
            if (line.startsWith("3")) {
                final Iterator<String> hi = message.getHeaderFields();
                while (hi.hasNext()) {
                    final String key = hi.next();
                    this.writeln(key + ": " + message.getHeader(key));
                }
                this.writeln("");
                final Iterator<String> e = message.getBodyLines();
                while (e.hasNext()) {
                    String bline = e.next();
                    if (bline.startsWith(".")) {
                        bline = "." + bline;
                    }
                    this.writeln(bline);
                }
                this.writeln(".");
                this.expect250();
                this.writeln("QUIT");
                return;
            }
            if (!line.startsWith("2")) {
                MailSender.logger.fine("SmtpSender.send reponse to DATA: " + line);
            }
        }
    }
    
    private boolean expect250() throws IOException {
        final String line = this.readln();
        if (!line.startsWith("2")) {
            MailSender.logger.info("SmtpSender.expect250: " + line);
            return false;
        }
        return true;
    }
    
    private void writeln(final String s) throws IOException {
        if (this.traceLevel > 2) {
            MailSender.logger.fine("From client: " + s);
        }
        this.out.write(s + "\r\n");
        this.out.flush();
    }
    
    private String readln() throws IOException {
        final String line = this.in.readLine();
        if (this.traceLevel > 1) {
            MailSender.logger.fine("From server: " + line);
        }
        return line;
    }
    
    public void setTraceLevel(final int traceLevel) {
        this.traceLevel = traceLevel;
    }
    
    public String getLocalHostName() {
        try {
            final InetAddress ipaddress = InetAddress.getLocalHost();
            final String localHost = ipaddress.getHostName();
            if (localHost == null) {
                return "localhost";
            }
            return localHost;
        }
        catch (UnknownHostException e) {
            return "localhost";
        }
    }
    
    static {
        logger = Logger.getLogger(MailSender.class.getName());
    }
}
