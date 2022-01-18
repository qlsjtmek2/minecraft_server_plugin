// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import java.util.Iterator;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.PreparedStatement;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class BatchedPstmtHolder
{
    private static final Logger logger;
    private LinkedHashMap<String, BatchedPstmt> stmtMap;
    private int maxSize;
    
    public BatchedPstmtHolder() {
        this.stmtMap = new LinkedHashMap<String, BatchedPstmt>();
    }
    
    public PreparedStatement getStmt(final String stmtKey, final BatchPostExecute postExecute) {
        final BatchedPstmt bs = this.stmtMap.get(stmtKey);
        if (bs == null) {
            return null;
        }
        bs.add(postExecute);
        final int bsSize = bs.size();
        if (bsSize > this.maxSize) {
            this.maxSize = bsSize;
        }
        return bs.getStatement();
    }
    
    public void addStmt(final BatchedPstmt bs, final BatchPostExecute postExecute) {
        bs.add(postExecute);
        this.stmtMap.put(bs.getSql(), bs);
    }
    
    public boolean isEmpty() {
        return this.stmtMap.isEmpty();
    }
    
    public void flush(final boolean getGeneratedKeys) throws PersistenceException {
        SQLException firstError = null;
        String errorSql = null;
        boolean isError = false;
        for (final BatchedPstmt bs : this.stmtMap.values()) {
            try {
                if (!isError) {
                    bs.executeBatch(getGeneratedKeys);
                }
            }
            catch (SQLException ex) {
                for (SQLException next = ex.getNextException(); next != null; next = next.getNextException()) {
                    BatchedPstmtHolder.logger.log(Level.SEVERE, "Next Exception during batch execution", next);
                }
                if (firstError == null) {
                    firstError = ex;
                    errorSql = bs.getSql();
                }
                else {
                    BatchedPstmtHolder.logger.log(Level.SEVERE, null, ex);
                }
                isError = true;
                try {
                    bs.close();
                }
                catch (SQLException ex) {
                    BatchedPstmtHolder.logger.log(Level.SEVERE, null, ex);
                }
            }
            finally {
                try {
                    bs.close();
                }
                catch (SQLException ex2) {
                    BatchedPstmtHolder.logger.log(Level.SEVERE, null, ex2);
                }
            }
        }
        this.stmtMap.clear();
        this.maxSize = 0;
        if (firstError != null) {
            final String msg = "Error when batch flush on sql: " + errorSql;
            throw new PersistenceException(msg, firstError);
        }
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    static {
        logger = Logger.getLogger(BatchedPstmtHolder.class.getName());
    }
}
