// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.jdbc;

import com.avaje.ebeaninternal.api.ClassUtil;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.lang.reflect.Method;
import com.avaje.ebean.config.PstmtDelegate;
import com.avaje.ebeaninternal.server.core.PstmtBatch;

public class OraclePstmtBatch implements PstmtBatch
{
    private final PstmtDelegate pstmtDelegate;
    private static final Method METHOD_SET_EXECUTE_BATCH;
    private static final Method METHOD_SEND_BATCH;
    private static final RuntimeException INIT_EXCEPTION;
    
    public OraclePstmtBatch(final PstmtDelegate pstmtDelegate) {
        this.pstmtDelegate = pstmtDelegate;
    }
    
    public void setBatchSize(final PreparedStatement pstmt, final int batchSize) {
        if (OraclePstmtBatch.INIT_EXCEPTION != null) {
            throw OraclePstmtBatch.INIT_EXCEPTION;
        }
        try {
            OraclePstmtBatch.METHOD_SET_EXECUTE_BATCH.invoke(this.pstmtDelegate.unwrap(pstmt), batchSize + 1);
        }
        catch (IllegalAccessException e) {
            final String m = "Error with Oracle setExecuteBatch " + (batchSize + 1);
            throw new RuntimeException(m, e);
        }
        catch (InvocationTargetException e2) {
            final String m = "Error with Oracle setExecuteBatch " + (batchSize + 1);
            throw new RuntimeException(m, e2);
        }
    }
    
    public void addBatch(final PreparedStatement pstmt) throws SQLException {
        pstmt.executeUpdate();
    }
    
    public int executeBatch(final PreparedStatement pstmt, final int expectedRows, final String sql, final boolean occCheck) throws SQLException {
        if (OraclePstmtBatch.INIT_EXCEPTION != null) {
            throw OraclePstmtBatch.INIT_EXCEPTION;
        }
        int rows;
        try {
            rows = (int)OraclePstmtBatch.METHOD_SEND_BATCH.invoke(this.pstmtDelegate.unwrap(pstmt), new Object[0]);
        }
        catch (IllegalAccessException e) {
            final String msg = "Error invoking Oracle sendBatch method via reflection";
            throw new PersistenceException(msg, e);
        }
        catch (InvocationTargetException e2) {
            final String msg = "Error invoking Oracle sendBatch method via reflection";
            throw new PersistenceException(msg, e2);
        }
        if (occCheck && rows != expectedRows) {
            final String msg2 = "Batch execution expected " + expectedRows + " but got " + rows + "  sql:" + sql;
            throw new OptimisticLockException(msg2);
        }
        return rows;
    }
    
    static {
        RuntimeException initException = null;
        Method mSetExecuteBatch = null;
        Method mSendBatch = null;
        try {
            final Class<?> ops = ClassUtil.forName("oracle.jdbc.OraclePreparedStatement");
            mSetExecuteBatch = ops.getMethod("setExecuteBatch", Integer.TYPE);
            mSendBatch = ops.getMethod("sendBatch", (Class<?>[])new Class[0]);
        }
        catch (NoSuchMethodException e) {
            initException = new RuntimeException("problems initializing oracle reflection", e);
            initException.fillInStackTrace();
        }
        catch (ClassNotFoundException e2) {
            initException = new RuntimeException("problems initializing oracle reflection", e2);
            initException.fillInStackTrace();
        }
        INIT_EXCEPTION = initException;
        METHOD_SET_EXECUTE_BATCH = mSetExecuteBatch;
        METHOD_SEND_BATCH = mSendBatch;
    }
}
