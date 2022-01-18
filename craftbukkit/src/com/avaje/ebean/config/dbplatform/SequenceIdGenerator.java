// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import java.util.logging.Level;
import com.avaje.ebean.Transaction;
import java.util.ArrayList;
import com.avaje.ebean.BackgroundExecutor;
import javax.sql.DataSource;
import java.util.logging.Logger;

public abstract class SequenceIdGenerator implements IdGenerator
{
    protected static final Logger logger;
    protected final Object monitor;
    protected final Object backgroundLoadMonitor;
    protected final String seqName;
    protected final DataSource dataSource;
    protected final BackgroundExecutor backgroundExecutor;
    protected final ArrayList<Integer> idList;
    protected int batchSize;
    protected int currentlyBackgroundLoading;
    
    public SequenceIdGenerator(final BackgroundExecutor be, final DataSource ds, final String seqName, final int batchSize) {
        this.monitor = new Object();
        this.backgroundLoadMonitor = new Object();
        this.idList = new ArrayList<Integer>(50);
        this.backgroundExecutor = be;
        this.dataSource = ds;
        this.seqName = seqName;
        this.batchSize = batchSize;
    }
    
    public abstract String getSql(final int p0);
    
    public String getName() {
        return this.seqName;
    }
    
    public boolean isDbSequence() {
        return true;
    }
    
    public void preAllocateIds(int allocateSize) {
        if (this.batchSize > 1 && allocateSize > this.batchSize) {
            if (allocateSize > 100) {
                allocateSize = 100;
            }
            this.loadLargeAllocation(allocateSize);
        }
    }
    
    protected void loadLargeAllocation(final int allocateSize) {
        this.backgroundExecutor.execute(new Runnable() {
            public void run() {
                SequenceIdGenerator.this.loadMoreIds(allocateSize, null);
            }
        });
    }
    
    public Object nextId(final Transaction t) {
        synchronized (this.monitor) {
            if (this.idList.size() == 0) {
                this.loadMoreIds(this.batchSize, t);
            }
            final Integer nextId = this.idList.remove(0);
            if (this.batchSize > 1 && this.idList.size() <= this.batchSize / 2) {
                this.loadBatchInBackground();
            }
            return nextId;
        }
    }
    
    protected void loadBatchInBackground() {
        synchronized (this.backgroundLoadMonitor) {
            if (this.currentlyBackgroundLoading > 0) {
                if (SequenceIdGenerator.logger.isLoggable(Level.FINE)) {
                    SequenceIdGenerator.logger.log(Level.FINE, "... skip background sequence load (another load in progress)");
                }
                return;
            }
            this.currentlyBackgroundLoading = this.batchSize;
            this.backgroundExecutor.execute(new Runnable() {
                public void run() {
                    SequenceIdGenerator.this.loadMoreIds(SequenceIdGenerator.this.batchSize, null);
                    synchronized (SequenceIdGenerator.this.backgroundLoadMonitor) {
                        SequenceIdGenerator.this.currentlyBackgroundLoading = 0;
                    }
                }
            });
        }
    }
    
    protected void loadMoreIds(final int numberToLoad, final Transaction t) {
        final ArrayList<Integer> newIds = this.getMoreIds(numberToLoad, t);
        if (SequenceIdGenerator.logger.isLoggable(Level.FINE)) {
            SequenceIdGenerator.logger.log(Level.FINE, "... seq:" + this.seqName + " loaded:" + numberToLoad + " ids:" + newIds);
        }
        synchronized (this.monitor) {
            for (int i = 0; i < newIds.size(); ++i) {
                this.idList.add(newIds.get(i));
            }
        }
    }
    
    protected ArrayList<Integer> getMoreIds(final int loadSize, final Transaction t) {
        final String sql = this.getSql(loadSize);
        final ArrayList<Integer> newIds = new ArrayList<Integer>(loadSize);
        final boolean useTxnConnection = t != null;
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            c = (useTxnConnection ? t.getConnection() : this.dataSource.getConnection());
            pstmt = c.prepareStatement(sql);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                final int val = rset.getInt(1);
                newIds.add(val);
            }
            if (newIds.size() == 0) {
                final String m = "Always expecting more than 1 row from " + sql;
                throw new PersistenceException(m);
            }
            return newIds;
        }
        catch (SQLException e) {
            if (e.getMessage().contains("Database is already closed")) {
                final String msg = "Error getting SEQ when DB shutting down " + e.getMessage();
                SequenceIdGenerator.logger.info(msg);
                System.out.println(msg);
                return newIds;
            }
            throw new PersistenceException("Error getting sequence nextval", e);
        }
        finally {
            if (useTxnConnection) {
                this.closeResources(null, pstmt, rset);
            }
            else {
                this.closeResources(c, pstmt, rset);
            }
        }
    }
    
    protected void closeResources(final Connection c, final PreparedStatement pstmt, final ResultSet rset) {
        try {
            if (rset != null) {
                rset.close();
            }
        }
        catch (SQLException e) {
            SequenceIdGenerator.logger.log(Level.SEVERE, "Error closing ResultSet", e);
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        catch (SQLException e) {
            SequenceIdGenerator.logger.log(Level.SEVERE, "Error closing PreparedStatement", e);
        }
        try {
            if (c != null) {
                c.close();
            }
        }
        catch (SQLException e) {
            SequenceIdGenerator.logger.log(Level.SEVERE, "Error closing Connection", e);
        }
    }
    
    static {
        logger = Logger.getLogger(SequenceIdGenerator.class.getName());
    }
}
