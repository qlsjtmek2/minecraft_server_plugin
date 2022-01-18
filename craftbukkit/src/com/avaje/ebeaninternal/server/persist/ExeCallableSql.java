// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.SpiCallableSql;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.util.BindParamsParser;
import java.sql.CallableStatement;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.logging.Level;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import java.util.logging.Logger;

public class ExeCallableSql
{
    private static final Logger logger;
    private final Binder binder;
    private final PstmtFactory pstmtFactory;
    
    public ExeCallableSql(final Binder binder, final PstmtBatch pstmtBatch) {
        this.binder = binder;
        this.pstmtFactory = new PstmtFactory(null);
    }
    
    public int execute(final PersistRequestCallableSql request) {
        final SpiTransaction t = request.getTransaction();
        final boolean batchThisRequest = t.isBatchThisRequest();
        CallableStatement cstmt = null;
        try {
            cstmt = this.bindStmt(request, batchThisRequest);
            if (batchThisRequest) {
                cstmt.addBatch();
                return -1;
            }
            final int rowCount = request.executeUpdate();
            request.postExecute();
            return rowCount;
        }
        catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        finally {
            if (!batchThisRequest && cstmt != null) {
                try {
                    cstmt.close();
                }
                catch (SQLException e) {
                    ExeCallableSql.logger.log(Level.SEVERE, null, e);
                }
            }
        }
    }
    
    private CallableStatement bindStmt(final PersistRequestCallableSql request, final boolean batchThisRequest) throws SQLException {
        final SpiCallableSql callableSql = request.getCallableSql();
        final SpiTransaction t = request.getTransaction();
        String sql = callableSql.getSql();
        final BindParams bindParams = callableSql.getBindParams();
        sql = BindParamsParser.parse(bindParams, sql);
        final boolean logSql = request.isLogSql();
        CallableStatement cstmt;
        if (batchThisRequest) {
            cstmt = this.pstmtFactory.getCstmt(t, logSql, sql, request);
        }
        else {
            if (logSql) {
                t.logInternal(sql);
            }
            cstmt = this.pstmtFactory.getCstmt(t, sql);
        }
        if (callableSql.getTimeout() > 0) {
            cstmt.setQueryTimeout(callableSql.getTimeout());
        }
        String bindLog = null;
        if (!bindParams.isEmpty()) {
            bindLog = this.binder.bind(bindParams, new DataBind(cstmt));
        }
        request.setBindLog(bindLog);
        request.setBound(bindParams, cstmt);
        return cstmt;
    }
    
    static {
        logger = Logger.getLogger(ExeCallableSql.class.getName());
    }
}
