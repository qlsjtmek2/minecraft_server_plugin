// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.bean.BeanCollection;
import java.util.logging.Level;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.logging.Logger;
import java.util.concurrent.Callable;

public class BackgroundFetch implements Callable<Integer>
{
    private static final Logger logger;
    private final CQuery<?> cquery;
    private final SpiTransaction transaction;
    
    public BackgroundFetch(final CQuery<?> cquery) {
        this.cquery = cquery;
        this.transaction = cquery.getTransaction();
    }
    
    public Integer call() {
        try {
            final BeanCollection<?> bc = this.cquery.continueFetchingInBackground();
            return bc.size();
        }
        catch (Exception e) {
            BackgroundFetch.logger.log(Level.SEVERE, null, e);
            return 0;
        }
        finally {
            try {
                this.cquery.close();
            }
            catch (Exception e2) {
                BackgroundFetch.logger.log(Level.SEVERE, null, e2);
            }
            try {
                this.transaction.rollback();
            }
            catch (Exception e2) {
                BackgroundFetch.logger.log(Level.SEVERE, null, e2);
            }
        }
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("BackgroundFetch ").append(this.cquery);
        return sb.toString();
    }
    
    static {
        logger = Logger.getLogger(BackgroundFetch.class.getName());
    }
}
