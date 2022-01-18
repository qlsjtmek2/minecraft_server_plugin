// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class BatchedPstmt
{
    private PreparedStatement pstmt;
    private final boolean isGenKeys;
    private final ArrayList<BatchPostExecute> list;
    private final String sql;
    private final PstmtBatch pstmtBatch;
    private final boolean occCheck;
    
    public BatchedPstmt(final PreparedStatement pstmt, final boolean isGenKeys, final String sql, final PstmtBatch pstmtBatch, final boolean occCheck) {
        this.list = new ArrayList<BatchPostExecute>();
        this.pstmt = pstmt;
        this.isGenKeys = isGenKeys;
        this.sql = sql;
        this.pstmtBatch = pstmtBatch;
        this.occCheck = occCheck;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public String getSql() {
        return this.sql;
    }
    
    public PreparedStatement getStatement() {
        return this.pstmt;
    }
    
    public void add(final BatchPostExecute batchExecute) {
        this.list.add(batchExecute);
    }
    
    public void executeBatch(final boolean getGeneratedKeys) throws SQLException {
        this.executeAndCheckRowCounts();
        if (this.isGenKeys && getGeneratedKeys) {
            this.getGeneratedKeys();
        }
        this.postExecute();
        this.close();
    }
    
    public void close() throws SQLException {
        if (this.pstmt != null) {
            this.pstmt.close();
            this.pstmt = null;
        }
    }
    
    private void postExecute() throws SQLException {
        for (int i = 0; i < this.list.size(); ++i) {
            this.list.get(i).postExecute();
        }
    }
    
    private void executeAndCheckRowCounts() throws SQLException {
        if (this.pstmtBatch != null) {
            final int rc = this.pstmtBatch.executeBatch(this.pstmt, this.list.size(), this.sql, this.occCheck);
            if (this.list.size() == 1) {
                this.list.get(0).checkRowCount(rc);
            }
            return;
        }
        final int[] results = this.pstmt.executeBatch();
        if (results.length != this.list.size()) {
            final String s = "results array error " + results.length + " " + this.list.size();
            throw new SQLException(s);
        }
        for (int i = 0; i < results.length; ++i) {
            this.list.get(i).checkRowCount(results[i]);
        }
    }
    
    private void getGeneratedKeys() throws SQLException {
        int index = 0;
        final ResultSet rset = this.pstmt.getGeneratedKeys();
        try {
            while (rset.next()) {
                final Object idValue = rset.getObject(1);
                this.list.get(index).setGeneratedKey(idValue);
                ++index;
            }
        }
        finally {
            if (rset != null) {
                rset.close();
            }
        }
    }
}
