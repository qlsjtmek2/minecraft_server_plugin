// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.util.BindParamsParser;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiUpdate;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.logging.Level;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import java.util.logging.Logger;

public class ExeOrmUpdate
{
    private static final Logger logger;
    private final Binder binder;
    private final PstmtFactory pstmtFactory;
    
    public ExeOrmUpdate(final Binder binder, final PstmtBatch pstmtBatch) {
        this.pstmtFactory = new PstmtFactory(pstmtBatch);
        this.binder = binder;
    }
    
    public int execute(final PersistRequestOrmUpdate request) {
        final SpiTransaction t = request.getTransaction();
        final boolean batchThisRequest = t.isBatchThisRequest();
        PreparedStatement pstmt = null;
        try {
            pstmt = this.bindStmt(request, batchThisRequest);
            if (batchThisRequest) {
                final PstmtBatch pstmtBatch = request.getPstmtBatch();
                if (pstmtBatch != null) {
                    pstmtBatch.addBatch(pstmt);
                }
                else {
                    pstmt.addBatch();
                }
                return -1;
            }
            final SpiUpdate<?> ormUpdate = request.getOrmUpdate();
            if (ormUpdate.getTimeout() > 0) {
                pstmt.setQueryTimeout(ormUpdate.getTimeout());
            }
            final int rowCount = pstmt.executeUpdate();
            request.checkRowCount(rowCount);
            request.postExecute();
            return rowCount;
        }
        catch (SQLException ex) {
            final SpiUpdate<?> ormUpdate2 = request.getOrmUpdate();
            final String msg = "Error executing: " + ormUpdate2.getGeneratedSql();
            throw new PersistenceException(msg, ex);
        }
        finally {
            if (!batchThisRequest && pstmt != null) {
                try {
                    pstmt.close();
                }
                catch (SQLException e) {
                    ExeOrmUpdate.logger.log(Level.SEVERE, null, e);
                }
            }
        }
    }
    
    private String translate(final PersistRequestOrmUpdate request, final String sql) {
        final BeanDescriptor<?> descriptor = request.getBeanDescriptor();
        return descriptor.convertOrmUpdateToSql(sql);
    }
    
    private PreparedStatement bindStmt(final PersistRequestOrmUpdate request, final boolean batchThisRequest) throws SQLException {
        final SpiUpdate<?> ormUpdate = request.getOrmUpdate();
        final SpiTransaction t = request.getTransaction();
        String sql = ormUpdate.getUpdateStatement();
        sql = this.translate(request, sql);
        final BindParams bindParams = ormUpdate.getBindParams();
        sql = BindParamsParser.parse(bindParams, sql);
        ormUpdate.setGeneratedSql(sql);
        final boolean logSql = request.isLogSql();
        PreparedStatement pstmt;
        if (batchThisRequest) {
            pstmt = this.pstmtFactory.getPstmt(t, logSql, sql, request);
        }
        else {
            if (logSql) {
                t.logInternal(sql);
            }
            pstmt = this.pstmtFactory.getPstmt(t, sql);
        }
        String bindLog = null;
        if (!bindParams.isEmpty()) {
            bindLog = this.binder.bind(bindParams, new DataBind(pstmt));
        }
        request.setBindLog(bindLog);
        return pstmt;
    }
    
    static {
        logger = Logger.getLogger(ExeOrmUpdate.class.getName());
    }
}
