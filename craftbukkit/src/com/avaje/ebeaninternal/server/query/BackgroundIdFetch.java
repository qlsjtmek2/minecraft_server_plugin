// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.sql.SQLException;
import java.util.logging.Level;
import com.avaje.ebeaninternal.api.BeanIdList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.util.concurrent.Callable;

public class BackgroundIdFetch implements Callable<Integer>
{
    private static final Logger logger;
    private final ResultSet rset;
    private final PreparedStatement pstmt;
    private final SpiTransaction transaction;
    private final DbReadContext ctx;
    private final BeanDescriptor<?> beanDescriptor;
    private final BeanIdList idList;
    
    public BackgroundIdFetch(final SpiTransaction transaction, final ResultSet rset, final PreparedStatement pstmt, final DbReadContext ctx, final BeanDescriptor<?> beanDescriptor, final BeanIdList idList) {
        this.ctx = ctx;
        this.transaction = transaction;
        this.rset = rset;
        this.pstmt = pstmt;
        this.beanDescriptor = beanDescriptor;
        this.idList = idList;
    }
    
    public Integer call() {
        try {
            final int startSize = this.idList.getIdList().size();
            int rowsRead = 0;
            while (this.rset.next()) {
                final Object idValue = this.beanDescriptor.getIdBinder().read(this.ctx);
                this.idList.add(idValue);
                this.ctx.getDataReader().resetColumnPosition();
                ++rowsRead;
            }
            if (BackgroundIdFetch.logger.isLoggable(Level.INFO)) {
                BackgroundIdFetch.logger.info("BG FetchIds read:" + rowsRead + " total:" + (startSize + rowsRead));
            }
            return rowsRead;
        }
        catch (Exception e) {
            BackgroundIdFetch.logger.log(Level.SEVERE, null, e);
            return 0;
        }
        finally {
            try {
                this.close();
            }
            catch (Exception e2) {
                BackgroundIdFetch.logger.log(Level.SEVERE, null, e2);
            }
            try {
                this.transaction.rollback();
            }
            catch (Exception e2) {
                BackgroundIdFetch.logger.log(Level.SEVERE, null, e2);
            }
        }
    }
    
    private void close() {
        try {
            if (this.rset != null) {
                this.rset.close();
            }
        }
        catch (SQLException e) {
            BackgroundIdFetch.logger.log(Level.SEVERE, null, e);
        }
        try {
            if (this.pstmt != null) {
                this.pstmt.close();
            }
        }
        catch (SQLException e) {
            BackgroundIdFetch.logger.log(Level.SEVERE, null, e);
        }
    }
    
    static {
        logger = Logger.getLogger(BackgroundIdFetch.class.getName());
    }
}
