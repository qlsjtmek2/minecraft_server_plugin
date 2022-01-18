// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.CallableStatement;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PstmtBatch;

public class PstmtFactory
{
    private final PstmtBatch pstmtBatch;
    
    public PstmtFactory(final PstmtBatch pstmtBatch) {
        this.pstmtBatch = pstmtBatch;
    }
    
    public CallableStatement getCstmt(final SpiTransaction t, final String sql) throws SQLException {
        final Connection conn = t.getInternalConnection();
        return conn.prepareCall(sql);
    }
    
    public PreparedStatement getPstmt(final SpiTransaction t, final String sql) throws SQLException {
        final Connection conn = t.getInternalConnection();
        return conn.prepareStatement(sql);
    }
    
    public PreparedStatement getPstmt(final SpiTransaction t, final boolean logSql, final String sql, final BatchPostExecute batchExe) throws SQLException {
        final BatchedPstmtHolder batch = t.getBatchControl().getPstmtHolder();
        PreparedStatement stmt = batch.getStmt(sql, batchExe);
        if (stmt != null) {
            return stmt;
        }
        if (logSql) {
            t.logInternal(sql);
        }
        final Connection conn = t.getInternalConnection();
        stmt = conn.prepareStatement(sql);
        if (this.pstmtBatch != null) {
            this.pstmtBatch.setBatchSize(stmt, t.getBatchControl().getBatchSize());
        }
        final BatchedPstmt bs = new BatchedPstmt(stmt, false, sql, this.pstmtBatch, false);
        batch.addStmt(bs, batchExe);
        return stmt;
    }
    
    public CallableStatement getCstmt(final SpiTransaction t, final boolean logSql, final String sql, final BatchPostExecute batchExe) throws SQLException {
        final BatchedPstmtHolder batch = t.getBatchControl().getPstmtHolder();
        CallableStatement stmt = (CallableStatement)batch.getStmt(sql, batchExe);
        if (stmt != null) {
            return stmt;
        }
        if (logSql) {
            t.logInternal(sql);
        }
        final Connection conn = t.getInternalConnection();
        stmt = conn.prepareCall(sql);
        final BatchedPstmt bs = new BatchedPstmt(stmt, false, sql, this.pstmtBatch, false);
        batch.addStmt(bs, batchExe);
        return stmt;
    }
}
