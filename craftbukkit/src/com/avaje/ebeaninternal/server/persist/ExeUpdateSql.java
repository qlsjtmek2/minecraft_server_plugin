// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.SpiSqlUpdate;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.util.BindParamsParser;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.logging.Level;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import java.util.logging.Logger;

public class ExeUpdateSql
{
    private static final Logger logger;
    private final Binder binder;
    private final PstmtFactory pstmtFactory;
    private final PstmtBatch pstmtBatch;
    private int defaultBatchSize;
    
    public ExeUpdateSql(final Binder binder, final PstmtBatch pstmtBatch) {
        this.defaultBatchSize = 20;
        this.binder = binder;
        this.pstmtBatch = pstmtBatch;
        this.pstmtFactory = new PstmtFactory(pstmtBatch);
    }
    
    public int execute(final PersistRequestUpdateSql request) {
        final SpiTransaction t = request.getTransaction();
        final boolean batchThisRequest = t.isBatchThisRequest();
        PreparedStatement pstmt = null;
        try {
            pstmt = this.bindStmt(request, batchThisRequest);
            if (batchThisRequest) {
                if (this.pstmtBatch != null) {
                    this.pstmtBatch.addBatch(pstmt);
                }
                else {
                    pstmt.addBatch();
                }
                return -1;
            }
            final int rowCount = pstmt.executeUpdate();
            request.checkRowCount(rowCount);
            request.postExecute();
            return rowCount;
        }
        catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        finally {
            if (!batchThisRequest && pstmt != null) {
                try {
                    pstmt.close();
                }
                catch (SQLException e) {
                    ExeUpdateSql.logger.log(Level.SEVERE, null, e);
                }
            }
        }
    }
    
    private PreparedStatement bindStmt(final PersistRequestUpdateSql request, final boolean batchThisRequest) throws SQLException {
        final SpiSqlUpdate updateSql = request.getUpdateSql();
        final SpiTransaction t = request.getTransaction();
        String sql = updateSql.getSql();
        final BindParams bindParams = updateSql.getBindParams();
        sql = BindParamsParser.parse(bindParams, sql);
        final boolean logSql = request.isLogSql();
        PreparedStatement pstmt;
        if (batchThisRequest) {
            pstmt = this.pstmtFactory.getPstmt(t, logSql, sql, request);
            if (this.pstmtBatch != null) {
                int batchSize = t.getBatchSize();
                if (batchSize < 1) {
                    batchSize = this.defaultBatchSize;
                }
                this.pstmtBatch.setBatchSize(pstmt, batchSize);
            }
        }
        else {
            if (logSql) {
                t.logInternal(sql);
            }
            pstmt = this.pstmtFactory.getPstmt(t, sql);
        }
        if (updateSql.getTimeout() > 0) {
            pstmt.setQueryTimeout(updateSql.getTimeout());
        }
        String bindLog = null;
        if (!bindParams.isEmpty()) {
            bindLog = this.binder.bind(bindParams, new DataBind(pstmt));
        }
        request.setBindLog(bindLog);
        this.parseUpdate(sql, request);
        return pstmt;
    }
    
    private void determineType(final String word1, final String word2, final String word3, final PersistRequestUpdateSql request) {
        if (word1.equalsIgnoreCase("UPDATE")) {
            request.setType(PersistRequestUpdateSql.SqlType.SQL_UPDATE, word2, "UpdateSql");
        }
        else if (word1.equalsIgnoreCase("DELETE")) {
            request.setType(PersistRequestUpdateSql.SqlType.SQL_DELETE, word3, "DeleteSql");
        }
        else if (word1.equalsIgnoreCase("INSERT")) {
            request.setType(PersistRequestUpdateSql.SqlType.SQL_INSERT, word3, "InsertSql");
        }
        else {
            request.setType(PersistRequestUpdateSql.SqlType.SQL_UNKNOWN, null, "UnknownSql");
        }
    }
    
    private void parseUpdate(final String sql, final PersistRequestUpdateSql request) {
        final int start = this.ltrim(sql);
        final int[] pos = new int[3];
        int spaceCount = 0;
        for (int len = sql.length(), i = start; i < len; ++i) {
            final char c = sql.charAt(i);
            if (Character.isWhitespace(c)) {
                pos[spaceCount] = i;
                if (++spaceCount > 2) {
                    break;
                }
            }
        }
        final String firstWord = sql.substring(0, pos[0]);
        final String secWord = sql.substring(pos[0] + 1, pos[1]);
        String thirdWord;
        if (pos[2] == 0) {
            thirdWord = sql.substring(pos[1] + 1);
        }
        else {
            thirdWord = sql.substring(pos[1] + 1, pos[2]);
        }
        this.determineType(firstWord, secWord, thirdWord, request);
    }
    
    private int ltrim(final String s) {
        int len;
        int i;
        for (len = s.length(), i = 0, i = 0; i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return i;
            }
        }
        return 0;
    }
    
    static {
        logger = Logger.getLogger(ExeUpdateSql.class.getName());
    }
}
