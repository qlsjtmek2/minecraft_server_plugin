// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.cron;

import java.util.logging.Level;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class HelloWorld implements Runnable
{
    private static final Logger logger;
    
    public String toString() {
        return "Hello World";
    }
    
    public void run() {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS ");
            final String now = sdf.format(new Date());
            HelloWorld.logger.info("Hello World " + now + "  ... sleeping 20 secs");
            Thread.sleep(20000L);
            HelloWorld.logger.info("Hello World finished.");
        }
        catch (InterruptedException ex) {
            HelloWorld.logger.log(Level.SEVERE, "", ex);
        }
    }
    
    static {
        logger = Logger.getLogger(HelloWorld.class.getName());
    }
}
