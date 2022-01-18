// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import java.util.logging.Level;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebean.Transaction;
import javax.sql.DataSource;
import java.util.logging.Logger;

public class SimpleSequenceIdGenerator implements IdGenerator
{
    private static final Logger logger;
    private final String sql;
    private final DataSource dataSource;
    private final String seqName;
    
    public SimpleSequenceIdGenerator(final DataSource dataSource, final String sql, final String seqName) {
        this.dataSource = dataSource;
        this.sql = sql;
        this.seqName = seqName;
    }
    
    public String getName() {
        return this.seqName;
    }
    
    public boolean isDbSequence() {
        return true;
    }
    
    public void preAllocateIds(final int batchSize) {
    }
    
    public Object nextId(final Transaction t) {
        final boolean useTxnConnection = t != null;
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            c = (useTxnConnection ? t.getConnection() : this.dataSource.getConnection());
            pstmt = c.prepareStatement(this.sql);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                final int val = rset.getInt(1);
                return val;
            }
            final String m = "Always expecting 1 row from " + this.sql;
            throw new PersistenceException(m);
        }
        catch (SQLException e) {
            throw new PersistenceException("Error getting sequence nextval", e);
        }
        finally {
            if (useTxnConnection) {
                this.closeResources(rset, pstmt, null);
            }
            else {
                this.closeResources(rset, pstmt, c);
            }
        }
    }
    
    private void closeResources(final ResultSet rset, final PreparedStatement pstmt, final Connection c) {
        try {
            if (rset != null) {
                rset.close();
            }
        }
        catch (SQLException e) {
            SimpleSequenceIdGenerator.logger.log(Level.SEVERE, "Error closing ResultSet", e);
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        }
        catch (SQLException e) {
            SimpleSequenceIdGenerator.logger.log(Level.SEVERE, "Error closing PreparedStatement", e);
        }
        try {
            if (c != null) {
                c.close();
            }
        }
        catch (SQLException e) {
            SimpleSequenceIdGenerator.logger.log(Level.SEVERE, "Error closing Connection", e);
        }
    }
    
    static {
        logger = Logger.getLogger(SimpleSequenceIdGenerator.class.getName());
    }
}
